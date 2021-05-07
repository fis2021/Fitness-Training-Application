package org.fta.Exceptions;

public class InvalidCredentialsException extends Exception{
    public InvalidCredentialsException()
    {
        super(String.format("Invalid username/password. Try again!"));
    }
}
