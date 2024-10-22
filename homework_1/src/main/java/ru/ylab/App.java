package ru.ylab;

import ru.ylab.controller.AuthController;
import ru.ylab.servlet.AuthServlet;

public class App {
    public static void main(String[] args) {
        new AuthController().start();
        //new AuthServlet().getServletConfig();
    }
}
