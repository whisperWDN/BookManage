package com.example.bookmanage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Add Book Button
        Button addBook = findViewById(R.id.add_book);
        addBook.setOnClickListener(new AddBookListener());
        Button bookList = findViewById(R.id.book_list);
        addBook.setOnClickListener(new BookListListener());
    }

    class AddBookListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(v.getId()==R.id.add_book){
                Toast.makeText(MainActivity.this,"您点击了控件："+((TextView)v).getText(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    class BookListListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if(v.getId()==R.id.add_book){
                Toast.makeText(MainActivity.this,"您点击了控件："+((TextView)v).getText(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}