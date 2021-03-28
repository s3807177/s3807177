package com.mycompany.app;

import java.text.DecimalFormat;
import java.util.ArrayList;


class ShoppingCart {

    void show() {
        System.out.println("\n");
        if (shoppingCart.size() != 0) {
            System.out.println("Your Shopping Cart contains the following:");
            for (int i = 0; i < shoppingCart.size(); i++) {
                BookGoods book = shoppingCart.get(i);
                if (book.isBuyEBook) {
                    System.out.println(String.format("%d.eBook:%s,Number of %d", i + 1, book.book.getBookName(), book.num));
                } else {
                    System.out.println(String.format("%d.%s,Number of %d", i + 1, book.book.getBookName(), book.num));
                }
            }
        } else {
            System.out.println("Your Shopping Cart is empty!");
        }
    }

    /**
     * 返回 书本信息
     */
     ArrayList<BookGoods> checkOut() {
        ArrayList<BookGoods> deleteList = new ArrayList<>();
        double count = 0;
        for (BookGoods bookGoods : shoppingCart) {
            if (bookGoods.isBuyEBook) {
                count += 8 * bookGoods.num;
            } else {
                deleteList.add(bookGoods);
                //实际去操作   -》 书店中的书本数量了   -》
                count += 30 * bookGoods.num;
            }
        }
        //清除购物车
        shoppingCart.clear();
        if(count == 0){
            System.out.println("Do not buy any books!");
            return deleteList;
        }
        System.out.println(String.format("\nYou have purchased items to the total value of $%s \nThanks for shopping with Daintree!",
                new DecimalFormat(".00").format(count)));
        return deleteList;
    }

    /**
     * 购物车中的书本类
     */
    public class BookGoods {
        private Book book;
        private Boolean isBuyEBook;
        private int num;

        int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        BookGoods(Book book, Boolean isBuyEBook) {
            this.book = book;
            this.isBuyEBook = isBuyEBook;
            num = 1;
        }

        Book getBook() {
            return book;
        }

        public void setBook(Book book) {
            this.book = book;
        }

        Boolean getBuyEBook() {
            return isBuyEBook;
        }

        public void setBuyEBook(Boolean buyEBook) {
            isBuyEBook = buyEBook;
        }
    }

    private ArrayList<BookGoods> shoppingCart = new ArrayList<>();


    /**
     * 添加加入购物车
     * @param book 书本
     * @param isEBook 是否是电子书
     */
    void addShoppingCart(Book book, boolean isEBook){
        BookGoods bookGod = null;
        for (BookGoods bookGoods : shoppingCart) {
            //购物车；购物车空的
            if (bookGoods.book.equals(book) && bookGoods.isBuyEBook == isEBook) {
                bookGod = bookGoods;
            }
        }
        if (bookGod != null) {
            bookGod.num++;
        } else {
            shoppingCart.add(new BookGoods(book, isEBook));
            if (isEBook) {
                System.out.println(String.format("“%s” eBook has been added to your Cart", book.getBookName()));
            } else {
                System.out.println(String.format("“%s” has been added to your Cart", book.getBookName()));
            }
        }
    }

    /**
     * 移除购物车
     * @param book 书本
     */
    void removeShoppingCart(BookGoods book){
        shoppingCart.remove(book);
    }



    ArrayList<BookGoods> getAllShoppingCart(){
        return shoppingCart;
    }


}
