package com.mycompany.debugging.sample;

/**
 *
 * Just a simple app to show how to debug applications inside docker.
 * 
 * @author fabiane
 */
public class App {

    public static void main(String[] args) {
        double number = Math.random();
        double anotherNumber = Math.random();
        double sum = number + anotherNumber;
        System.out.println("The sum is "+sum);
    }

}
