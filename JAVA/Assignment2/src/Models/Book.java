package Models;

public class Book{
    public int id;
    public String title;
    public String author;
    public int available_copies;

    public Book(int id,String title,String author,int available_copies){
        this.id = id;
        this.title = title;
        this.author = author;
        this.available_copies = available_copies;
    }

    public Book(String title,String author,int available_copies){
        this.title = title;
        this.author = author;
        this.available_copies = available_copies;
    }

    public String toString(){

        return id + " " + title + " " + author + " " + available_copies;
    }
}