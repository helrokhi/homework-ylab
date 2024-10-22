package ru.ylab.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.dto.HabitDto;
import ru.ylab.dto.RegHabit;
import ru.ylab.dto.enums.Frequency;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class HabitRepositoryTest {

    @InjectMocks
    private HabitRepository habitRepository;

    @Test
    void getHabits() {
        ArrayList<HabitDto> habits = habitRepository.getHabits(10000L);
        System.out.println(habits);
    }

    @Test
    void createHabit() {
        RegHabit regHabit = new RegHabit("title", "text", "DAILY");
        HabitDto habitDto = habitRepository.createHabit(10010L, regHabit);
        System.out.println(habitDto);
    }

    @Test
    void updateHabitTitle() {
        HabitDto habitDto = habitRepository.updateHabitTitle(1010L, "new title");
        System.out.println(habitDto);
    }

    @Test
    void updateHabitText() {
        HabitDto habitDto = habitRepository.updateHabitText(1010L, "new text");
        System.out.println(habitDto);
    }

    @Test
    void updateHabitFrequency() {
        HabitDto habitDto = habitRepository.updateHabitFrequency(1010L, "WEEKLY");
        System.out.println(habitDto);
    }

    @Test
    void getHabitDtoById() {
        HabitDto habitDto = habitRepository.getHabitDtoById(1010L);
        System.out.println(habitDto);
    }

    @Test
    void deleteHabit() {
        habitRepository.deleteHabit(1010L);
        HabitDto habitDto = habitRepository.getHabitDtoById(1010L);
        System.out.println(habitDto);
    }
}