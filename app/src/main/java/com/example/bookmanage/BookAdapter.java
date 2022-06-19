package com.example.bookmanage;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmanage.http.HttpRequestUtil;
import com.example.bookmanage.http.tool.HttpReqData;
import com.example.bookmanage.http.tool.HttpRespData;

import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> mBookList;
    //静态内部类， 每个条目对应的布局
    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView bookName;
        TextView author;
        TextView publish_club;
        TextView publish_year;
        TextView code;
        ImageView book_image;

        public ViewHolder (View view)
        {
            super(view);
            bookName =  view.findViewById(R.id.book_name);
            author =  view.findViewById(R.id.author);
            publish_club =  view.findViewById(R.id.publish_club);
            publish_year =  view.findViewById(R.id.publish_year);
            code = view.findViewById(R.id.code);
            book_image = view.findViewById(R.id.book_image);
        }

    }

    public  BookAdapter (List <Book> bookList){
        mBookList = bookList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_info,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        Book book = mBookList.get(position);
        if(book.getImage_url()!="null"){
            Thread thread = new Thread(){
                public void run() {
                    try{
                        HttpReqData req_data = new HttpReqData(book.getImage_url());
                        HttpRespData resp_data;
                        HttpRequestUtil requestUtil = new HttpRequestUtil();
                        resp_data = requestUtil.getImage(req_data);
                        holder.book_image.setImageBitmap(resp_data.bitmap);
                    }catch (Exception e){
                    }
                };
            };
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        holder.bookName.setText(book.getBook_name());
        holder.author.setText(book.getAuthor());
        holder.publish_club.setText(book.getPublish_club());
        holder.publish_year.setText(book.getPublish_year());
        holder.code.setText(book.getISBN());


    }
    //返回RecyclerView的子项数目
    @Override
    public int getItemCount(){
        return mBookList.size();
    }

}

