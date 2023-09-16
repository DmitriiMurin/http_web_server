package server;

public class Request {

    private static final String DEFAULT_HTTP_VERSION = "HTTP/1.1";
    private Method method;

    private String addressLine;

    private String httpVersion;

    public Request(Method method, String addressLine, String httpVersion) {
        this.method = method;
        this.addressLine = addressLine;
        this.httpVersion = httpVersion;
    }

    public Request(Method method, String addressLine) {
        this.method = method;
        this.addressLine = addressLine;
        this.httpVersion = DEFAULT_HTTP_VERSION;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public RequestKey getRequestKey(){
        return new RequestKey(method, addressLine);
    }
}
