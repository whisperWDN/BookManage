package com.example.bookmanage;

public class Book {
    private String book_name;
    private String author;
    private String ISBN;
    private String publish_club;
    private String publish_year;
    String getBook_name(){
        return book_name;
    }
    void setBook_name(String book_name){
        this.book_name=book_name;
    }
    String getAuthor(){
        return author;
    }
    void setAuthor(String author){
        this.author=author;
    }
    String getPublish_club(){
        return publish_club;
    }
    void setPublish_club(String publish_club){
        this.publish_club=publish_club;
    }
    String getISBN(){
        return ISBN;
    }
    void setISBN(String ISBN){
        this.ISBN=ISBN;
    }
    String getPublish_year(){
        return publish_year;
    }
    void setPublish_year(String publish_year){
        this.publish_year=publish_year;
    }


}
