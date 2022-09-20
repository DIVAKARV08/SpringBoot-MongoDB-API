package com.springBootMongoDB.springBootMongoDB.Exception;

public class TodoException extends Exception{
    public static final long serialVersionUID=1L;
    public TodoException(String message){
        super(message);
    }
    public static String NotFoundException(String id){
        return "Todo with "+id+" not found";
    }
    public static String TodoAlreadyExist(){
        return "Todo with name already exist";
    }
}
