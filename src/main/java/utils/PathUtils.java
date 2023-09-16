package utils;

import exception.InvalidPathException;
import server.Method;
import server.Request;

public class PathUtils {

    public static Request parsePathLine(String pathLine){

        final String[] parts = pathLine.split(" ");
        if (parts.length != 3) {
            throw new InvalidPathException();
        }
        return new Request(Method.getMethodByName(parts[0]), parts[1], parts[2]);
    }
}
