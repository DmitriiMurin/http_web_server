package utils;

import exception.InvalidPathException;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import server.Method;
import server.Request;

import java.nio.charset.Charset;
import java.util.List;

public class PathUtils {

    public static Request parsePathLine(String pathLine){


        final String[] parts = pathLine.split(" ");
        if (parts.length != 3) {
            throw new InvalidPathException();
        }
        String substringWithParams = parts[1].substring(parts[1].indexOf('?') + 1);
        String pathWithoutParams = parts[1].substring(0, parts[1].indexOf('?'));

        return new Request(Method.getMethodByName(parts[0]), pathWithoutParams, parts[2], URLEncodedUtils.parse(substringWithParams, Charset.defaultCharset()));
    }
}
