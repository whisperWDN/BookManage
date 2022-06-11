package com.example.bookmanage;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
        String[] keys = new String[]{"书名:          ","作者:          ","出版社:      ","出版年份:   "};
        @Override
        public void onClick(View v){
            if(v.getId()==R.id.search_book) {
                String ISBN = EditISBN.getText().toString();

                SearchBook(ISBN);
                String[] values=new String[]{"西游记","吴承恩","人教社","2022"};

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
            }
        }
    }

    public void SearchBook(String ISBN){

        new Thread(new Runnable() {
            HttpURLConnection conn = null;
            BufferedReader br = null;
            String s = "https://api.jike.xyz/situ/book/isbn/" + ISBN+"?apikey="+apikey;
            @Override
            public void run() {
                try {
                    URL url = new URL(s);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(10000);
                    conn.setRequestProperty("accept", "*/*");
                    conn.setRequestProperty("connection", "Keep-Alive");
                    conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//                    conn.setDoInput(true);
                    conn.connect();
//                    conn.getInputStream();
//            br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
//            String buf = null;
//            buf = br.readLine();
                    TextView is = findViewById(R.id.Text_ISBN);
//            is.setText(buf);
                    is.setText("success");

                }catch (Exception e){
                    TextView is = findViewById(R.id.Text_ISBN);
                    is.setText(e.getMessage());
                }
                finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
                }

            }).start();


    }
}