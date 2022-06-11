package com.example.bookmanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Add Book Button
        Button addBook = findViewById(R.id.add_book);
        addBook.setOnClickListener(new TheClickListener());
        Button bookList = findViewById(R.id.book_list);
        bookList.setOnClickListener(new TheClickListener());
    }

    class TheClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(v.getId()==R.id.add_book){
                Intent intent = new Intent(MainActivity.this,AddBookActivity.class);
                startActivity(intent);
            }else if(v.getId()==R.id.book_list){
                Intent intent = new Intent(MainActivity.this,BookListActivity.class);
                startActivity(intent);
            }
        }
    }


}