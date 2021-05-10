package org.fta.Exceptions;

public class BlankFieldException extends Exception{
    public BlankFieldException(){
        super(String.format("Please complete all fields"));
    }
}
