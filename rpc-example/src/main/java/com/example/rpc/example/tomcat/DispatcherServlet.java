package com.example.rpc.example.tomcat;

import javax.servlet.http.HttpServlet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DispatcherServlet extends HttpServlet {
    private static final Map<Integer, HttpHandler> handlers = new ConcurrentHashMap<Integer, HttpHandler>();
    private static final long serialVersionUID = 1146195200795979533L;


    public static void addHttpHandler(int port, HttpHandler processor) {
        handlers.put(port, processor);
    }

}
