package server;

import exception.InvalidPathException;
import utils.PathUtils;

import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Server {
    private static final ConcurrentHashMap<RequestKey, Handler> handlersMap = new ConcurrentHashMap<>();

    public void addHandler(String method, String addressLine, Handler handler){
        handlersMap.put(new Request(Method.valueOf(method), addressLine).getRequestKey(), handler);
    }

    private final ExecutorService es = Executors.newFixedThreadPool(64);
    private static Socket socket;

    public void start(int port) {
        try (final var serverSocket = new ServerSocket(port)) {
            while (true) {
                socket = serverSocket.accept();
                es.submit(() -> newConnect(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void newConnect(Socket socket) {
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             final BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream())
        ) {
            Request request;
            try {
                request = PathUtils.parsePathLine(in.readLine());
            } catch (InvalidPathException e){
                out.write((
                        "HTTP/1.1 400 Bad Request\r\n" +
                                "Content-Length: 0\r\n" +
                                "Connection: close\r\n" +
                                "\r\n"
                ).getBytes());
                out.flush();
                return;
            }

            Handler handler = handlersMap.get(request.getRequestKey());
            if(handler == null){
                out.write((
                        "HTTP/1.1 404 Not Found\r\n" +
                                "Content-Length: 0\r\n" +
                                "Connection: close\r\n" +
                                "\r\n"
                ).getBytes());
                out.flush();
                return;
            }
            System.out.println(request.getQueryParam("last"));
            System.out.println(request.getQueryParams());
            handler.handle(request, out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}