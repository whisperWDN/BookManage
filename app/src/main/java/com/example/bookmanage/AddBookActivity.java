package com.example.bookmanage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookmanage.database.BookDBHelper;
import com.example.bookmanage.http.HttpRequestUtil;
import com.example.bookmanage.http.tool.HttpReqData;
import com.example.bookmanage.http.tool.HttpRespData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("DefaultLocale")
public class AddBookActivity extends AppCompatActivity {
    private BookDBHelper mHelper;
    private final String apikey = "12775.2411e68a143df74f82e7654e0c6c5d17.a9c9149f33c5af2f565e092ea7db63ec";
    private String bookName;
    private String author;
    private String publishing;
    private String published;
    private String code;


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

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 获得用户数据库帮助器的一个实例
        mHelper = BookDBHelper.getInstance(this, 2);
        // 恢复页面，则打开数据库连接
        mHelper.openWriteLink();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 暂停页面，则关闭数据库连接
        mHelper.closeLink();
    }

    private void showToast(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
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
                Book book = new Book();
                book.setBook_name(bookName);
                book.setAuthor(author);
                book.setISBN(code);
                book.setPublish_club(published);
                book.setPublish_year(publishing);
                int result = mHelper.insert(book);
                if(result==1){
                    showToast("已存在该书籍的记录");
                }else if(result==0){
                    showToast("数据已写入SQLite数据库");
                }
            }
        }
    }

    public void SearchBook(String ISBN){
        Button addBookToList = findViewById(R.id.add_book_to_list);
        new Thread(new Runnable() {
            BufferedReader br = null;
            String url = "https://api.jike.xyz/situ/book/isbn/" + ISBN + "?apikey=" + apikey;

            @Override
            public void run() {
                try {
                    HttpReqData req_data = new HttpReqData(url);
                    HttpRespData resp_data;
                    HttpRequestUtil requestUtil = new HttpRequestUtil();
                    resp_data = requestUtil.getData(req_data);
                    JSONObject jsonObject = new JSONObject(resp_data.content);
                    if(Integer.parseInt(jsonObject.getString("ret"))==0){
                        addBookToList.setTextColor(0xFFFFFFFF);
                        addBookToList.setEnabled(true);
                        bookName = jsonObject.getJSONObject("data").getString("name");
                        author = jsonObject.getJSONObject("data").getString("author");
                        publishing = jsonObject.getJSONObject("data").getString("publishing");
                        published = jsonObject.getJSONObject("data").getString("published");
                        code = jsonObject.getJSONObject("data").getString("code");
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
                    }else{
                        addBookToList.setTextColor(0xFFD0EFC6);
                        addBookToList.setEnabled(false);
                        showToast(jsonObject.getString("msg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }

            }
        }).start();


    }
}