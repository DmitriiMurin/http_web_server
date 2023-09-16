package server;

import org.apache.commons.collections.map.MultiValueMap;
import org.apache.http.NameValuePair;

import java.util.List;

public class Request {

    private static final String DEFAULT_HTTP_VERSION = "HTTP/1.1";
    private Method method;

    private String addressLine;

    private MultiValueMap paramsMap = new MultiValueMap();
    private String httpVersion;

    public Request(Method method, String addressLine, String httpVersion) {
        this.method = method;
        this.addressLine = addressLine;
        this.httpVersion = httpVersion;
    }

    public Request(Method method, String addressLine, String httpVersion, List<NameValuePair> paramsList) {
        this.method = method;
        this.addressLine = addressLine;
        this.httpVersion = httpVersion;
        paramsList.forEach((item) -> paramsMap.put(item.getName(), item.getValue()));
    }

    public Request(Method method, String addressLine) {
        this.method = method;
        this.addressLine = addressLine;
        this.httpVersion = DEFAULT_HTTP_VERSION;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public RequestKey getRequestKey() {
        return new RequestKey(method, addressLine);
    }

    public Object getQueryParam(String name) {
        return paramsMap.get(name);
    }

    public MultiValueMap getQueryParams() {
        return paramsMap;
    }
}
