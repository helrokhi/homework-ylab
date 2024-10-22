package ru.ylab.servlet;

import ru.ylab.annotations.Loggable;
import ru.ylab.dto.PersonDto;
import ru.ylab.dto.RegUser;
import ru.ylab.service.AuthService;
import ru.ylab.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/auth")
@Loggable
public class AuthServlet extends HttpServlet {
    private final UserService userService;
    private final AuthService authService;

    public AuthServlet() {
        this.userService = new UserService();
        this.authService = new AuthService();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        RegUser regUser = userService.createRegUser(email, password);
        userService.getUserByEmail(regUser);
        PersonDto personDto = authService.personAuthorization(regUser);
        request.setAttribute("person", personDto);
        request.getRequestDispatcher("/authUser.jsp").forward(request, response);

    }

    @Override
    public void destroy() {
    }
}
