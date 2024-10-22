package ru.ylab.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.dto.HabitDto;
import ru.ylab.dto.StatusDto;
import ru.ylab.dto.enums.StatusType;
import ru.ylab.repository.HabitHistoryRepository;
import ru.ylab.repository.HabitRepository;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@ExtendWith(MockitoExtension.class)
class HabitServiceTest {
    @InjectMocks
    private HabitService habitService;


    @Mock
    private HabitRepository habitRepository;

    @Mock
    private HabitHistoryRepository habitHistoryRepository;

    @Test
    void getHabitStatistics() {
    }

    @Test
    void getLastStatus() {
        OffsetDateTime timeStatus = OffsetDateTime.parse("2024-10-22T00:00:05.829402900Z");
        StatusDto statusDto = new StatusDto(1L, 1000L, StatusType.EXECUTE, timeStatus);
        Mockito.lenient().when(habitHistoryRepository.getLastStatusByHabitId(1000L))
                .thenReturn(statusDto);

        Assertions.assertEquals(StatusType.EXECUTE, statusDto.getStatusType());
    }

    @Test
    void getStatusType() {
//        HabitDto habitDto = habitService.getHabitByIndex(1000L);
//        System.out.println(habitDto);
//        StatusDto statusDto = habitService.getLastStatus(1000L);
//        System.out.println(statusDto);
//        LocalDate dayLast = statusDto.getTime().toLocalDate();
//        LocalDate dayNew = OffsetDateTime.now().toLocalDate();
//
//        System.out.println(dayNew.equals(dayLast));
//        StatusType statusType = habitService.getStatusType(1000L);
//        if ((!dayLast.equals(dayNew))) {
//            Assertions.assertEquals(StatusType.NO, statusType);
//        } else {
//            Assertions.assertEquals(StatusType.EXECUTE, statusType);
//        }
    }

    @Test
    void getNumberOfHabitRunsPerPeriod() {
    }

    @Test
    void getHabitByIndex() {
        HabitDto habitDto = habitService.getHabitByIndex(1000L);
        System.out.println(habitDto);
    }

    @Test
    void getHabits() {

    }
}