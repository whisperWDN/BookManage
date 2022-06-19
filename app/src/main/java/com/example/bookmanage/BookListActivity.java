package com.example.bookmanage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmanage.database.BookDBHelper;

import java.util.ArrayList;

@SuppressLint("DefaultLocale")
public class BookListActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<Book> bookList = new ArrayList<>();
    private BookDBHelper mHelper;
    RecyclerView recyclerView;
    BookAdapter adapter;
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_book_by_author) {
            // 打开数据库帮助器的写连接
            mHelper.openWriteLink();
            EditText Edit_author = findViewById(R.id.Edit_author);
            String author = Edit_author.getText().toString();
            bookList = mHelper.queryByAuthor(author);
            showToast(String.format("搜索到%s条记录",bookList.size()));
            adapter = new BookAdapter(bookList);
            recyclerView.setAdapter(adapter);
            // 关闭数据库连接
            mHelper.closeLink();
        }
    }

    private void showToast(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        initBooks();
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);
        EditText Edit_author = findViewById(R.id.Edit_author);
        Edit_author.addTextChangedListener(new TheTextWatcher(Edit_author));
        Button searchBook = findViewById(R.id.search_book_by_author);
        searchBook.setOnClickListener(this);

    }

    private void initBooks() {
        mHelper = BookDBHelper.getInstance(this, 2);
        mHelper.openReadLink();

        if (mHelper == null) {
            showToast("数据库连接为空");
            return;
        }
        try{
            bookList = mHelper.query();
        }catch (Exception e){
            showToast("出现异常");
        }
        mHelper.closeLink();
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

}