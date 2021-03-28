package com.mycompany.app;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Daintree {

    private static final String YES = "yes";
    private static final String NO = "no";
    /**
     * 输入命令
     */
    private Scanner scanner;
    private BookStore bookStore;
    private ShoppingCart shoppingCart;


    void initSystem() {
        scanner = new Scanner(System.in);
        bookStore = new BookStore();
        shoppingCart = new ShoppingCart();
        //
        bookStore.addBookGoods(new Book("Absolute Java", "Savitch", true, 5));
        bookStore.addBookGoods(new Book("JAVA: How to Program", "Deitel and Deitel", true, 0));
        bookStore.addBookGoods(new Book("Computing Concepts with JAVA 8 Essentials", "Horstman", false, 5));
        bookStore.addBookGoods(new Book("Java Software Solutions", "Lewis and Loftus", false, 5));
        bookStore.addBookGoods(new Book("Java Program Design", "Cohoon and Davidson", true, 1));
    }

    void menu() {
        while (true) {
            printInfo();
            switch (scanner.nextInt()) {
                case 1:
                    searchBook();
                    break;
                case 2:
                    showShoppingCart();
                    break;
                case 3:
                    removeBookByName();
                    break;
                case 4:
                    checkOut();
                    break;
                case 5:
                    showAllBook();
                    break;
                case 0:
                    System.out.println("\nGoodbye!");
                    return;
                default:
                    System.out.println("error");
                    break;
            }
            System.out.println("\n");
        }
    }

    private void showAllBook() {
        for (int i = 0; i < bookStore.getAllBook().size(); i++) {
            System.out.println(String.format("%d.%s",i+1,bookStore.getAllBook().get(i).toString()));
        }
    }

    private void checkOut() {
        //结算购物车
        ArrayList<ShoppingCart.BookGoods> bookGoods = shoppingCart.checkOut();
        //减商店
        for (ShoppingCart.BookGoods goods :bookGoods){
            for (Book book : bookStore.getAllBook()) {
                if (book.equals(goods.getBook())) {
                    //减去数量
                    book.setBookNum(book.getBookNum() - goods.getNum());
                }
            }
        }
    }

    private void removeBookByName() {
        System.out.println("\n");
        ArrayList<ShoppingCart.BookGoods> allShoppingCart = shoppingCart.getAllShoppingCart();
        //显示所有书籍
        if (allShoppingCart.size() != 0) {
            System.out.println("Your Shopping Cart contains the following");
            for (int i = 0; i < allShoppingCart.size(); i++) {
                System.out.println(String.format("%d.%s", i + 1, allShoppingCart.get(i).getBook().getBookName()));
            }
            System.out.println("0. cancel");
            System.out.print("What do you want to remove:");
            int index = scanner.nextInt();
            if (index > 0) {
                if (index > allShoppingCart.size()) {
                    //越界
                    System.out.println("out for index size.");
                } else {
                    //TODO("确认操作")
                    shoppingCart.removeShoppingCart(allShoppingCart.get(index - 1));
                    System.out.println("Item removed from Shopping Cart");
                }
            }
        } else {
            System.out.println("Your Shopping Cart is empty!");
        }
    }

    private void showShoppingCart() {
        shoppingCart.show();
    }

    private void searchBook() {
        System.out.print("\nEnter title to search for: ");
        //查找图书1
        //多写一个用于接收/n
        scanner.nextLine();
        List<Book> books = bookStore.searchForBooks(scanner.nextLine());
        if (books.size() != 0) {
            System.out.println("The following title is a match: ");
            for (int i = 0; i < books.size(); i++) {
                System.out.println(String.format("%d.%s -- %s", i + 1, books.get(i).getBookName(), books.get(i).getAuthor()));
            }
            System.out.println("0. cancel");
            System.out.print("What is your selection:");
            int index = scanner.nextInt();
            //如果索引大于0，也就是菜单中选了0以为的选项
            if (index > 0) {
                if (index > books.size()) {
                    //越界
                    System.out.println("out for index size.");
                } else {
                    Book currentBook = books.get(index - 1);
                    System.out.print("Do you want to buy this as an ebook:");
                    while (true) {
                        String command = scanner.next();
                        if (YES.equalsIgnoreCase(command)) {
                            beforeShopping(currentBook, true);
                            break;
                        } else if (NO.equalsIgnoreCase(command)) {
                            beforeShopping(currentBook, false);
                            break;
                        } else {
                            System.out.println("print is error!replay!");
                            System.out.print("Do you want to buy this as an ebook:");
                        }
                    }

                }
            }
        } else {
            System.out.println("There is no title starting with that");
        }
    }

    /**
     * 构面前的判断
     *
     * @param currentBook 当前书本
     * @param iseBook  是否是电子书
     */
    private void beforeShopping(Book currentBook, boolean iseBook) {
        //是否有库存
        //判断当前的书是没有电子书的，但是需要购买电子书
        if(!currentBook.getIseBook() && iseBook){
            System.out.println("There is no ebook available for that title.");
            return;
        }
        //还有没有库存
        boolean isHave = true;
        ShoppingCart.BookGoods bookGod = null;
        for (ShoppingCart.BookGoods bookGoods : shoppingCart.getAllShoppingCart()) {
            //购物车；购物车空的
            if (bookGoods.getBook().equals(currentBook) && bookGoods.getBuyEBook() == iseBook) {
                bookGod = bookGoods;
            }
        }
        //购物车 //电子书 直接跳过检测是否有库存
        for (Book book : bookStore.getAllBook()) {
            if (book.equals(currentBook)) {
                isHave = iseBook || (book.getBookNum() - (bookGod != null ? bookGod.getNum() : 0)) > 0;
            }
        }
        if (isHave) {
            shoppingCart.addShoppingCart(currentBook,iseBook);
        } else {
            //无库存，无实体书了
            System.out.println("There are no physical copies of that book available!");
        }

    }


    void exitSystem() {
        scanner.close();
        //退出进程
        System.exit(0);
    }

    /**
     * 打印头信息
     */
    private void printInfo() {
        String[] option = {"Choose an option:", "1. Add a book to shopping cart", "2. View shopping cart",
                "3. Remove a book from shopping cart", "4. Checkout", "5. List all books", "0. Quit"};
        for (String i : option) {
            System.out.println(i);
        }
        System.out.print("Please make a selection:");
    }
}
