package ru.ylab.service;

import lombok.AllArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.repository.HabitRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class HabitService {
    private PersonDto person;

    public HabitDto create(RegHabit regHabit) {
        HabitRepository habitRepository = new HabitRepository();
        HabitDto habitDto = habitRepository.createHabit(person, regHabit);
        System.out.println("Создана привычка " + habitDto);
        return habitDto;
    }

    public HabitDto getHabitByIndex(Long habitId) {
        HabitRepository habitRepository = new HabitRepository();
        return habitRepository.getHabitDtoById(habitId);
    }

    public void delete(HabitDto habit) {
        HabitRepository habitRepository = new HabitRepository();
        habitRepository.deleteHabit(habit.getId());
        System.out.println("Привычка удалена " + habit);
    }

    public ArrayList<HabitDto> getHabits() {
        HabitRepository habitRepository = new HabitRepository();
        return habitRepository.getHabits(person.getId());
    }

    public void toStringListHabits(List<HabitDto> habits) {
        if (habits.isEmpty()) {
            System.out.println("Нет привычек у пользователя");
        } else {
            System.out.println("Список привычек пользователя");
            for (HabitDto habit : habits) {
                System.out.println("\t" + habit.getId() + ": " + habit);
            }
        }
    }

    public List<HabitDto> getSortHabitsByTime(ArrayList<HabitDto> habits) {
        return habits
                .stream()
                .sorted(Comparator.comparing(HabitDto::getTime))
                .collect(Collectors.toList());
    }
}
