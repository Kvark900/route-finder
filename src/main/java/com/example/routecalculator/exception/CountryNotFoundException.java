package com.example.routecalculator.exception;

public class CountryNotFoundException extends Throwable {

    public CountryNotFoundException() {
        super("Country not found - bad country cca3");
    }

    public CountryNotFoundException(String s) {
        super(s);
    }
}
