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
        // 恢复页面，则打开数据库连接
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
        layoutManager .setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        BookAdapter adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);

    }

    private void initBooks() {
        mHelper = BookDBHelper.getInstance(this, 2);
        try{
            mHelper.onCreate(mHelper.openWriteLink());
        }catch (Exception e){
            showToast("出现异常");
        }

        if (mHelper == null) {
            showToast("数据库连接为空");
            return;
        }else{
            showToast("建表成功");
//            Book book = new Book();
//            book.setBook_name("西游记");
//            book.setAuthor("吴承恩");
//            book.setISBN("9787020024759");
//            book.setPublish_club("人教社");
//            book.setPublish_year("2022");
//            int result = mHelper.insert(book);
//            if(result==1){
//                showToast("已存在该书籍的记录");
//            }else if(result==0){
//                showToast("数据已写入SQLite数据库");
//            }

        }
//        bookList = mHelper.query("1=1");
    }

}