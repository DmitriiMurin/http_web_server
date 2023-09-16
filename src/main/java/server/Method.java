package server;

import exception.InvalidPathException;

public enum Method {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE;

    public static Method getMethodByName(String name){
        try {
            return Method.valueOf(name);
        } catch (Exception e){
            throw new InvalidPathException();
        }

    }
}
