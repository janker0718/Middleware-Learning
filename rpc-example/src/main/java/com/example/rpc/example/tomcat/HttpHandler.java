package com.example.rpc.example.tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpHandler {

    private boolean cors;

    public HttpHandler(boolean cors) {
        this.cors = cors;
    }

    public void handle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String uri = request.getRequestURI();
        //针对uri响应
    }
}
