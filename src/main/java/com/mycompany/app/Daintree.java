package com.mycompany.app;


import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;


import java.util.ArrayList;


public class Daintree {

    private static final String YES = "yes";
    private static final String NO = "no";
    /**
     * 书本类
     */
    class Book {
        Book(String bookName,String author, Boolean iseBook, int bookNum) {
            this.bookName = bookName;
            this.iseBook = iseBook;
            this.bookNum = bookNum;
            this.author = author;
        }

        @Override
        public String toString() {
            return bookName + "("+author+"),"+bookNum+" copies," + (iseBook?" ebook available":"no ebook");
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

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public Boolean getIseBook() {
            return iseBook;
        }

        public void setIseBook(Boolean iseBook) {
            this.iseBook = iseBook;
        }

        public int getBookNum() {
            return bookNum;
        }

        public void setBookNum(int bookNum) {
            this.bookNum = bookNum;
        }
    }

    /**
     * 购物车中的书本类
     */
    class BookGoods {
        private Book book;
        private Boolean isBuyEBook;
        private int num;

        public int getNum() {
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

        public Book getBook() {
            return book;
        }

        public void setBook(Book book) {
            this.book = book;
        }

        public Boolean getBuyEBook() {
            return isBuyEBook;
        }

        public void setBuyEBook(Boolean buyEBook) {
            isBuyEBook = buyEBook;
        }
    }

    /**
     * 书本列表
     */
    private ArrayList<Book> bookList = new ArrayList<>();

    /**
     * 购物车
     */
    private ArrayList<BookGoods> shoppingCart = new ArrayList<>();

    /**
     * 输入命令
     */
    private Scanner scanner;

    private int state = 0;


    /**
     * 初始化系统
     */
    public void initSystem() {
        scanner = new Scanner(System.in);
        bookList.add(new Book("Absolute Java","Savitch", true, 5));
        bookList.add(new Book("JAVA: How to Program","Deitel and Deitel", true, 0));
        bookList.add(new Book("Computing Concepts with JAVA 8 Essentials","Horstman", false, 5));
        bookList.add(new Book("Java Software Solutions","Lewis and Loftus",false, 5));
        bookList.add(new Book("Java Program Design", "Cohoon and Davidson",true, 1));
        //
    }

    /**
     * 退出系统
     */
    public void exitSystem() {
        scanner.close();
        bookList.clear();
        shoppingCart.clear();
        System.exit(0);
    }


    public static void main(String[] args) {
        System.out.println("Welcome to Daintree!");
        Daintree display = new Daintree();

        display.initSystem();

        display.menu();

        display.exitSystem();
    }

    public void menu() {
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

    /**
     * 显示所有数量入口
     */
    private void showAllBook() {
        for (int i = 0; i < bookList.size(); i++) {
            System.out.println(String.format("%d.%s",i+1,bookList.get(i).toString()));
        }
    }

    /**
     * 付钱入口
     */
    private void checkOut() {
        double count = 0;
        for (BookGoods bookGoods : shoppingCart) {
            if (bookGoods.isBuyEBook) {
                count += 8 * bookGoods.num;
            } else {
                for (Book book : bookList) {
                    if (book.equals(bookGoods.book)) {
                        //减去数量
                        book.bookNum -= bookGoods.num;
                    }
                }
                count += 30 * bookGoods.num;
            }
        }
        //清除购物车
        shoppingCart.clear();
        if(count == 0){
            System.out.println("Do not buy any books!");
            return;
        }
        System.out.println(String.format("\nYou have purchased items to the total value of $%s \nThanks for shopping with Daintree!",
                new DecimalFormat(".00").format(count)));
    }

    /**
     * 根据书籍名称移除购物车中的书入口
     */
    private void removeBookByName() {
        System.out.println("\n");
        //显示所有书籍
        if (shoppingCart.size() != 0) {
            System.out.println("Your Shopping Cart contains the following");
            for (int i = 0; i < shoppingCart.size(); i++) {
                System.out.println(String.format("%d.%s", i + 1, shoppingCart.get(i).book.bookName));
            }
            System.out.println("0. cancel");
            System.out.print("What do you want to remove:");
            int index = scanner.nextInt();
            if (index > 0) {
                if (index > shoppingCart.size()) {
                    //越界
                    System.out.println("out for index size.");
                } else {
                    //TODO("确认操作")
                    shoppingCart.remove(index - 1);
                    System.out.println("Item removed from Shopping Cart");
                }
            }
        } else {
            System.out.println("Your Shopping Cart is empty!");
        }
    }

    /**
     * 显示购物车入口
     */
    private void showShoppingCart() {
        System.out.println("\n");
        if (shoppingCart.size() != 0) {
            System.out.println("Your Shopping Cart contains the following:");
            for (int i = 0; i < shoppingCart.size(); i++) {
                BookGoods book = shoppingCart.get(i);
                if (book.isBuyEBook) {
                    System.out.println(String.format("%d.eBook:%s,Number of %d", i + 1, book.book.bookName, book.num));
                } else {
                    System.out.println(String.format("%d.%s,Number of %d", i + 1, book.book.bookName, book.num));
                }
            }
        } else {
            System.out.println("Your Shopping Cart is empty!");
        }
    }


    /**
     * 搜索书本入口
     */
    private void searchBook() {
        System.out.print("\nEnter title to search for: ");
        //查找图书1
        //多写一个用于接收/n
        scanner.nextLine();
        List<Book> books = searchForBooks(scanner.nextLine());
        if (books.size() != 0) {
            System.out.println("The following title is a match: ");
            for (int i = 0; i < books.size(); i++) {
                System.out.println(String.format("%d.%s", i + 1, books.get(i).bookName));
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
                    //有电子书
                    if (currentBook.iseBook) {
                        System.out.print("Do you want to buy this as an ebook:");
                        while (true) {
                            String command = scanner.next();
                            if (YES.equalsIgnoreCase(command)) {
                                addShoppingCart(currentBook, true);
                                break;
                            } else if (NO.equalsIgnoreCase(command)) {
                                addShoppingCart(currentBook, false);
                                break;
                            } else {
                                System.out.println("print is error!replay!");
                                System.out.print("Do you want to buy this as an ebook:");
                            }
                        }
                        //无电子书
                    } else {
                        addShoppingCart(currentBook, false);
                    }
                }
            }
        } else {
            System.out.println("There is no title starting with that");
        }

    }

    /**
     * 添加书籍进入购物车
     *
     * @param curBook : 书籍
     * @param iseBook : 是否是电子书
     */
    private void addShoppingCart(Book curBook, boolean iseBook) {
        boolean isHave = true;
        BookGoods bookGod = null;
        for (BookGoods bookGoods : shoppingCart) {
            if (bookGoods.book.equals(curBook) && bookGoods.isBuyEBook == iseBook) {
                bookGod = bookGoods;
            }
        }
        for (Book book : bookList) {
            if (book.equals(curBook)) {
                isHave = iseBook || (book.bookNum - (bookGod != null ? bookGod.num : 0)) > 0;
            }
        }
        if (isHave) {
            if (bookGod != null) {
                bookGod.num++;
            } else {
                shoppingCart.add(new BookGoods(curBook, iseBook));
            }
            if (iseBook) {
                System.out.println(String.format("“%s” eBook has been added to your Cart", curBook.bookName));
            } else {
                System.out.println(String.format("“%s” has been added to your Cart", curBook.bookName));
            }
        } else {
            //无库存，无实体书了
            System.out.println("There are no physical copies of that book available!");
        }
    }

    /**
     * 搜索书本方法
     *
     * @param bookName 书名
     * @return 书本实体类
     */
    private List<Book> searchForBooks(String bookName) {
        List bookResult = new ArrayList<Book>();
        for (Book book : bookList) {
            if (book.bookName.toLowerCase().contains(bookName.toLowerCase())) {
                bookResult.add(book);
            }
        }
        return bookResult;
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