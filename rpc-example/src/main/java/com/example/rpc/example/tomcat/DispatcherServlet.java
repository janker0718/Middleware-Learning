package com.example.rpc.example.tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DispatcherServlet extends HttpServlet {
    private static final Map<Integer, HttpHandler> handlers = new ConcurrentHashMap<Integer, HttpHandler>();
    private static final long serialVersionUID = 1146195200795979533L;


    public static void addHttpHandler(int port, HttpHandler processor) {
        handlers.put(port, processor);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpHandler handler = handlers.get(request.getLocalPort());
        if (handler == null) {// service not found.
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Service not found.");
        } else {
            handler.handle(request, response);
        }
    }
}
