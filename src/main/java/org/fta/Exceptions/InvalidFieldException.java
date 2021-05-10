package org.fta.Exceptions;

public class InvalidFieldException extends Exception{
    public InvalidFieldException(){
        super(String.format("Please complete all fields correctly"));
    }
}
