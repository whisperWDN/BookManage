package com.example.bookmanage;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookmanage.http.HttpRequestUtil;
import com.example.bookmanage.http.tool.HttpReqData;
import com.example.bookmanage.http.tool.HttpRespData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddBookActivity extends AppCompatActivity {
    String apikey = "12775.2411e68a143df74f82e7654e0c6c5d17.a9c9149f33c5af2f565e092ea7db63ec";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        EditText Edit_ISBN = findViewById(R.id.Edit_ISBN);
        Edit_ISBN.addTextChangedListener(new TheTextWatcher(Edit_ISBN));
        Button searchBook = findViewById(R.id.search_book);
        searchBook.setOnClickListener(new TheClickListener());
        Button addBookToList = findViewById(R.id.add_book_to_list);
        addBookToList.setOnClickListener(new TheClickListener());
        addBookToList.setTextColor(0xFFD0EFC6);
        addBookToList.setEnabled(false);
    }

    class TheTextWatcher implements android.text.TextWatcher {
        private EditText mView;
        private CharSequence mStr;
        public TheTextWatcher(EditText v){
            super();
            mView = v;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count){
            mStr = s;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(mStr==null||mStr.length()==0) return;
        }

    }
    class TheClickListener implements View.OnClickListener{
        EditText EditISBN =  findViewById(R.id.Edit_ISBN);
        @Override
        public void onClick(View v){
            if(v.getId()==R.id.search_book) {
                String ISBN = EditISBN.getText().toString();
                SearchBook(ISBN);
            }else if(v.getId()==R.id.add_book_to_list) {

            }
        }
    }

    public void SearchBook(String ISBN){
        new Thread(new Runnable() {
            BufferedReader br = null;
            String url = "https://api.jike.xyz/situ/book/isbn/" + ISBN + "?apikey=" + apikey;

            @Override
            public void run() {
                try {
                    HttpReqData req_data = new HttpReqData(url);
                    HttpRespData resp_data = new HttpRespData();
                    HttpRequestUtil requestUtil = new HttpRequestUtil();
                    resp_data = requestUtil.getData(req_data);
                    JSONObject jsonObject = new JSONObject(resp_data.content);
                    if(Integer.parseInt(jsonObject.getString("ret"))==0){
                        String bookName = jsonObject.getJSONObject("data").getString("name");
                        String author = jsonObject.getJSONObject("data").getString("author");
                        String publishing = jsonObject.getJSONObject("data").getString("publishing");
                        String published = jsonObject.getJSONObject("data").getString("published");
                        String[] keys = new String[]{"书名:          ","作者:          ","出版社:      ","出版年份:   "};
                        String[] values=new String[]{bookName,author,published,publishing};
                        ListView listView=findViewById(R.id.listview);
                        List<Map<String,String>> listItems = new ArrayList<>();
                        for(int i=0;i< keys.length;i++){
                            Map<String,String> map = new HashMap<>();
                            map.put("key",keys[i]);
                            map.put("value",values[i]);
                            listItems.add(map);
                        }
                        SimpleAdapter adapter = new SimpleAdapter(AddBookActivity.this,listItems,R.layout.book_fields,
                                new String[]{"key","value"},new int[]{R.id.key,R.id.value});
                        listView.setAdapter(adapter);
                        Button addBookToList = findViewById(R.id.add_book_to_list);
                        addBookToList.setTextColor(0xFFFFFFFF);
                        addBookToList.setEnabled(true);
                    }else{
                        String[] keys = new String[]{"error:"};
                        String message = jsonObject.getString("msg");
                        String[] values=new String[]{message};
                        ListView listView=findViewById(R.id.listview);
                        List<Map<String,String>> listItems = new ArrayList<>();
                        for(int i=0;i< keys.length;i++){
                            Map<String,String> map = new HashMap<>();
                            map.put("key",keys[i]);
                            map.put("value",values[i]);
                            listItems.add(map);
                        }
                        SimpleAdapter adapter = new SimpleAdapter(AddBookActivity.this,listItems,R.layout.book_fields,
                                new String[]{"key","value"},new int[]{R.id.key,R.id.value});
                        listView.setAdapter(adapter);
                        Button addBookToList = findViewById(R.id.add_book_to_list);
                        addBookToList.setTextColor(0xFFD0EFC6);
                        addBookToList.setEnabled(false);
                    }


                } catch (Exception e) {

                } finally {

                }

            }
        }).start();


    }
}