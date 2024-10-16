package ru.ylab.service;

import lombok.NoArgsConstructor;
import ru.ylab.dto.HabitDto;
import ru.ylab.dto.RegStatus;
import ru.ylab.dto.StatusDto;
import ru.ylab.repository.HabitHistoryRepository;

import java.util.ArrayList;
@NoArgsConstructor
public class HabitHistoryService {

    public ArrayList<StatusDto> getHistory(Long habitId) {
        HabitHistoryRepository habitHistoryRepository = new HabitHistoryRepository();

        return habitHistoryRepository.getHistory(habitId);
    }

    public StatusDto createStatus(HabitDto habitDto, RegStatus regStatus) {
        System.out.println("HabitDto " + habitDto);
        System.out.println("RegStatus " + regStatus);
        HabitHistoryRepository habitHistoryRepository = new HabitHistoryRepository();
        return habitHistoryRepository.createStatus(habitDto, regStatus);
    }
}
