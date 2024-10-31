package ru.ylab.servlet;

import ru.ylab.annotations.Loggable;
import ru.ylab.dto.PersonDto;
import ru.ylab.dto.UserAuthDto;
import ru.ylab.service.PersonService;
import ru.ylab.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/profile")
@Loggable
public class UserServlet extends HttpServlet {
    private final UserService userService;

    private final PersonService personService;

    public UserServlet() {
        this.userService = new UserService();
        this.personService = new PersonService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long personId = Long.parseLong(req.getParameter("id"));
        PersonDto personDto = personService.getPersonById(personId);
        if (personDto != null) {
            req.setAttribute("id", personDto.getId());
            resp.setStatus(HttpServletResponse.SC_OK);
            req.getRequestDispatcher("/user.jsp").forward(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long personId = Long.parseLong(req.getParameter("id"));
        req.setAttribute("id", personId);
        resp.sendRedirect("profile");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long personId = Long.parseLong(req.getParameter("id"));
        PersonDto personDto = personService.getPersonById(personId);
        Long userId = personDto.getUserId();
        UserAuthDto userAuthDto = userService.getUserById(userId);

        personDto.setName(req.getParameter("name"));
        userAuthDto.setEmail(req.getParameter("email"));
        userAuthDto.setPassword(req.getParameter("password"));
        userService.update(userAuthDto);
        personService.update(personDto);

        resp.sendRedirect("profile");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long personId = Long.parseLong(req.getParameter("id"));
        PersonDto personDto = personService.getPersonById(personId);
        personService.delete(personDto);
        resp.sendRedirect("/api");
    }
}
