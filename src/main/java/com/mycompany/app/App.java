package com.mycompany.app;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    { 
        System.out.println("Welcome to Daintree!");
        Daintree display = new Daintree();

        display.initSystem();

        display.menu();

        display.exitSystem();
    }
}
