package com.mycompany.app;

import java.util.ArrayList;

/**
 * 书店
 */
class BookStore {

    private ArrayList<Book> bookList = new ArrayList<>();

    /**
     * 添加图书
     *
     * @param book 图书
     */
    void addBookGoods(Book book) {
        bookList.add(book);
    }


    /**
     * 搜索书本方法
     *
     * @param bookName 书名
     * @return 书本实体类
     */
    ArrayList<Book> searchForBooks(String bookName) {
        ArrayList<Book> bookResult = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getBookName().toLowerCase().startsWith(bookName.toLowerCase())) {
                bookResult.add(book);
            }
        }
        return bookResult;
    }


    ArrayList<Book> getAllBook() {
        return bookList;
    }
}
