package server;

import java.util.Objects;

public class RequestKey {
    private Method method;

    private String addressLine;

    public RequestKey(Method method, String addressLine) {
        this.method = method;
        this.addressLine = addressLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestKey requestKey = (RequestKey) o;
        return method == requestKey.method && Objects.equals(addressLine, requestKey.addressLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, addressLine);
    }

    @Override
    public String toString() {
        return "RequestKey{" +
                "method=" + method +
                ", addressLine='" + addressLine + '\'' +
                '}';
    }
}
