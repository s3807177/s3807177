package com.mycompany.app;

/**
 *
 */
public class Book {
    Book(String bookName, String author, Boolean iseBook, int bookNum) {
        this.bookName = bookName;
        this.iseBook = iseBook;
        this.author = author;
        this.bookNum = bookNum;
    }

    @Override
    public String toString() {
        return bookName + "(" + author + ")," + bookNum + " copies," + (iseBook ? " ebook available" : "no ebook");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Book) {
            return ((Book) obj).bookName.equals(this.bookName);
        } else {
            return super.equals(obj);
        }
    }

    private String bookName;
    private String author;
    private Boolean iseBook;
    private int bookNum;

    String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    Boolean getIseBook() {
        return iseBook;
    }

    public void setIseBook(Boolean iseBook) {
        this.iseBook = iseBook;
    }

    int getBookNum() {
        return bookNum;
    }

    void setBookNum(int bookNum) {
        this.bookNum = bookNum;
    }

}
