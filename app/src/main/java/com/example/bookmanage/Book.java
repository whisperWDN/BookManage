package com.example.bookmanage;

public class Book {
    private String book_name;
    private String author;
    private String ISBN;
    private String publish_club;
    private String publish_year;
    public String getBook_name(){
        return book_name;
    }
    public void setBook_name(String book_name){
        this.book_name=book_name;
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author=author;
    }
    public String getPublish_club(){
        return publish_club;
    }
    public void setPublish_club(String publish_club){
        this.publish_club=publish_club;
    }
    public String getISBN(){
        return ISBN;
    }
    public void setISBN(String ISBN){
        this.ISBN=ISBN;
    }
    public String getPublish_year(){
        return publish_year;
    }
    public void setPublish_year(String publish_year){
        this.publish_year=publish_year;
    }
}
