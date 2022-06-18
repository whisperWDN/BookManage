package com.example.bookmanage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmanage.database.BookDBHelper;

import java.util.ArrayList;

@SuppressLint("DefaultLocale")
public class BookListActivity extends AppCompatActivity {

    private ArrayList<Book> bookList = new ArrayList<>();
    private BookDBHelper mHelper;

    @Override
    protected void onStart() {
        super.onStart();
        // 获得用户数据库帮助器的一个实例
//        mHelper = BookDBHelper.getInstance(this, 2);
////         恢复页面，则打开数据库连接
//        mHelper.openReadLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 暂停页面，则关闭数据库连接
        mHelper.closeLink();
    }

//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.btn_delete) {
//            // 关闭数据库连接
//            mHelper.closeLink();
//            // 打开数据库帮助器的写连接
//            mHelper.openWriteLink();
//            // 删除所有记录
//            mHelper.deleteAll();
//            // 关闭数据库连接
//            mHelper.closeLink();
//            // 打开数据库帮助器的读连接
//            mHelper.openReadLink();
//            readSQLite();
//        }
//    }

    private void showToast(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        initBooks();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        BookAdapter adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);

    }

    private void initBooks() {
        mHelper = BookDBHelper.getInstance(this, 2);
        mHelper.openReadLink();

        if (mHelper == null) {
            showToast("数据库连接为空");
            return;
        }
        try{
            bookList = mHelper.query("1=1");
        }catch (Exception e){
            showToast("出现异常");
        }
    }

}