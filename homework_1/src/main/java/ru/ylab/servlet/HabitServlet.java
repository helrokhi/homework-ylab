package ru.ylab.servlet;

import ru.ylab.annotations.Loggable;
import ru.ylab.dto.HabitDto;
import ru.ylab.dto.PersonDto;
import ru.ylab.dto.RegHabit;
import ru.ylab.dto.enums.Frequency;
import ru.ylab.service.HabitService;
import ru.ylab.service.PersonService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/habits")
@Loggable
public class HabitServlet extends HttpServlet {
    private final PersonService personService;

    private final HabitService habitService;

    public HabitServlet() {
        this.personService = new PersonService();
        this.habitService = new HabitService();
    }

    public void init(ServletConfig servletConfig) {
        try {
            super.init(servletConfig);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        List<HabitDto> habits = habitService.getHabits(id);
        req.setAttribute("id", id);
        req.setAttribute("habits", habits);
        req.getRequestDispatcher("/showHabits.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        String title = req.getParameter("title");
        String text = req.getParameter("text");
        String frequency = req.getParameter("frequency");
        System.out.println(id + "/" + text +"/" +  title + "/" + frequency);
        if (frequency != null) {
            RegHabit regHabit = new RegHabit(title, text, frequency);
            PersonDto personDto = personService.getPersonById(id);

            habitService.create(personDto, regHabit);
        }

        String newUrl = "habits?id=" + id;
        resp.sendRedirect(newUrl);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long habit = Long.parseLong(req.getParameter("habit"));
        String id = req.getParameter("id");
        HabitDto habitDto = habitService.getHabitByIndex(habit);

        habitDto.setTitle(req.getParameter("title"));
        habitDto.setText(req.getParameter("text"));
        habitDto.setFrequency(Frequency.valueOf(req.getParameter("frequency")));
        habitService.update(habitDto);

        String newUrl = "habits?id=" + id;
        resp.sendRedirect(newUrl);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long habit = Long.parseLong(req.getParameter("habit"));
        String id = req.getParameter("id");
        habitService.delete(habitService.getHabitByIndex(habit));

        req.setAttribute("id", id);

        String newUrl = "habits?id=" + id;
        resp.sendRedirect(newUrl);

    }
}
