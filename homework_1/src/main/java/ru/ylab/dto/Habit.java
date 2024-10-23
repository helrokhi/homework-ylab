package ru.ylab.dto;

import ru.ylab.dto.enums.Frequency;
import ru.ylab.dto.enums.StatusType;

import java.time.OffsetDateTime;
import java.time.Period;
import java.util.ArrayList;

public class Habit {
    private String title;
    private String text;
    private Frequency frequency;

    private OffsetDateTime time;
    private ArrayList<Status> history;

    public Habit(String title, String text, String frequency) {
        this.title = title;
        this.text = text;
        this.frequency = getFrequency(frequency);
        this.time = OffsetDateTime.now();
        this.history = new ArrayList<>(0);
    }

    private Frequency getFrequency(String query) {
        switch (query) {
            case "daily":
                return Frequency.DAILY;
            case "weekly":
                return Frequency.WEEKLY;
            default:
                return Frequency.DAILY;
        }
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public Status getStatus() {
        Status lastStatus = getLastElement();
        Status newStatus = new Status(StatusType.NO, OffsetDateTime.now());
        int dayLastStatus = lastStatus.getTime().getDayOfMonth();
        int dayNewStatus = newStatus.getTime().getDayOfMonth();
        return (dayLastStatus < dayNewStatus) ? newStatus : lastStatus;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFrequency(String query) {
        this.frequency = getFrequency(query);
    }

    public void updateStatusHistory(Status status) {
        history.add(status);
    }

    public ArrayList<Status> getHistory() {
        return history;
    }

    public Status getLastElement() {
        return history.get(history.size() - 1);
    }

    public int getNumberOfWeeksBetweenDates() {
        return getNumberOfDaysBetweenDates() / 7;
    }

    public int getNumberOfDaysBetweenDates() {
        OffsetDateTime startDate = time;
        OffsetDateTime endDate = OffsetDateTime.now();
        Period period = Period.between(startDate.toLocalDate(), endDate.toLocalDate());
        return period.getDays();
    }

    public int getNumberOfRuns(String period) {
        switch (period) {
            case "день": {
            }
            case "неделя": {
            }
            case "месяц": {
            }
            default: {
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", frequency=" + frequency +
                ", time=" + time +
                ", history=" + history +
                '}';
    }

}
