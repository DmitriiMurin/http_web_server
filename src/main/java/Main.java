import server.Handler;
import server.Request;
import server.Server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        final var server = new Server();
        // код инициализации сервера (из вашего предыдущего ДЗ)

        // добавление хендлеров (обработчиков)
        server.addHandler("GET", "/index.html", new Handler() {
            public void handle(Request request, BufferedOutputStream responseStream) throws IOException {

                    Path filePath = Path.of(".", "public", "resources", request.getAddressLine());
                    long length = Files.size(filePath);
                    String mimeType = Files.probeContentType(filePath);
                    responseStream.write((
                            "HTTP/1.1 200 OK\r\n" +
                                    "Content-Type: " + mimeType + "\r\n" +
                                    "Content-Length: " + length + "\r\n" +
                                    "Connection: close\r\n" +
                                    "\r\n"
                    ).getBytes()); // начальная часть запроса отправляется в выходной поток
                    Files.copy(filePath, responseStream); // копируем файл по байтам в выходной поток
                    responseStream.flush();
            }
        });
        server.addHandler("GET", "/events.html", new Handler() {
            public void handle(Request request, BufferedOutputStream responseStream)  throws IOException  {
                Path filePath = Path.of(".", "public", "resources", request.getAddressLine());
                long length = Files.size(filePath);
                String mimeType = Files.probeContentType(filePath);
                responseStream.write((
                        "HTTP/1.1 200 OK\r\n" +
                                "Content-Type: " + mimeType + "\r\n" +
                                "Content-Length: " + length + "\r\n" +
                                "Connection: close\r\n" +
                                "\r\n"
                ).getBytes()); // начальная часть запроса отправляется в выходной поток
                Files.copy(filePath, responseStream); // копируем файл по байтам в выходной поток
                responseStream.flush();
            }
        });

        server.start(9999);
    }
}