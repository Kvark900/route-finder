package com.example.routecalculator.exception;

public class RouteNotFoundException extends Throwable {

    public RouteNotFoundException() {
        super("Land route not found!");
    }

    public RouteNotFoundException(String smg) {
        super(smg);
    }
}
