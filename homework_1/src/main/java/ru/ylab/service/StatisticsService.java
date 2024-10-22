package ru.ylab.service;

import ru.ylab.repository.HabitRepository;

import java.time.OffsetDateTime;
import java.time.Period;

public class StatisticsService {


    public int getNumberOfWeeksBetweenDates(Long habitId) {
        return getNumberOfDaysBetweenDates(habitId) / 7;
    }

    public int getNumberOfDaysBetweenDates(Long habitId) {
        HabitRepository habitRepository = new HabitRepository();
        OffsetDateTime startDate = habitRepository.getHabitDtoById(habitId).getTime();
        OffsetDateTime endDate = OffsetDateTime.now();
        Period period = Period.between(startDate.toLocalDate(), endDate.toLocalDate());
        return period.getDays();
    }
}
