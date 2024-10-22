package ru.ylab.service;

import lombok.AllArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.dto.enums.Frequency;
import ru.ylab.dto.enums.StatusType;
import ru.ylab.repository.HabitHistoryRepository;
import ru.ylab.repository.HabitRepository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class HabitService {
    private PersonDto person;

    public HabitDto create(PersonDto personDto, RegHabit regHabit) {
        HabitRepository habitRepository = new HabitRepository();
        HabitDto habitDto = habitRepository.createHabit(personDto.getId(), regHabit);
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

    public ArrayList<HabitDto> getHabits(Long personId) {
        HabitRepository habitRepository = new HabitRepository();
        return habitRepository.getHabits(personId);
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

    public List<HabitDto> getSortHabitsByStatus(ArrayList<HabitDto> habits, StatusType type) {
        ArrayList<HabitDto> sortList = new ArrayList<>(0);
        for (HabitDto habitDto : habits) {
            Long habitId = habitDto.getId();
            StatusType statusType = getStatusType(habitId);
            if (statusType.equals(type)) {
                sortList.add(habitDto);
            }
        }
        return sortList;
    }

    public String getHabitStatistics(HabitDto habitDto, int days) {
        StringBuilder sb = new StringBuilder();
        HabitHistoryRepository habitHistoryRepository = new HabitHistoryRepository();
        ArrayList<StatusDto> history = habitHistoryRepository.getHistory(habitDto.getId());
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days);;
        int count;
        int period = 0;

        if (!habitDto.getFrequency().equals(Frequency.DAILY) && days == 1) {
            sb.append("Для данной привычки нет статистики за день");
        }

        if (habitDto.getFrequency().equals(Frequency.DAILY)) {
            period = Period.between(startDate, endDate).getDays();
        }

        if (habitDto.getFrequency().equals(Frequency.WEEKLY) && days == 30) {
            period = Period.between(startDate, endDate).getDays()/7;
        }

        count = getNumberOfHabitRunsPerPeriod(history, startDate, endDate);

        sb.append("Период с ")
                .append(startDate)
                .append(" по ")
                .append(endDate)
                .append(" Необходимое выполнить ")
                .append(period)
                .append(" Фактически выполнено ")
                .append(count);

        return sb.toString();
    }

    public StatusDto getLastStatus(Long habitId) {
        HabitHistoryRepository habitHistoryRepository = new HabitHistoryRepository();
        return habitHistoryRepository.getLastStatusByHabitId(habitId);
    }

    public StatusType getStatusType(Long habitId) {
        StatusDto lastStatus = getLastStatus(habitId);

        LocalDate dayLast = lastStatus.getTime().toLocalDate();
        LocalDate dayNew = OffsetDateTime.now().toLocalDate();
        return (!dayLast.equals(dayNew)) ? StatusType.NO : lastStatus.getStatusType();
    }

    public int getNumberOfHabitRunsPerPeriod(ArrayList<StatusDto> history,
                                             LocalDate startDate,
                                             LocalDate endDate) {
        ArrayList<StatusDto> dtos = new ArrayList<>(0);
        for (StatusDto statusDto : history) {
            LocalDate date = statusDto.getTime().toLocalDate();
            if ((startDate.isBefore(date) || startDate.equals(date)) &&
                    (date.isBefore(endDate) || date.equals(endDate))) {
                dtos.add(statusDto);
            }
        }
        return dtos.size();
    }
}
