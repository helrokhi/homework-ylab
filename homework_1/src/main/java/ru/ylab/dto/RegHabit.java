package ru.ylab.dto;

import lombok.Getter;
import ru.ylab.dto.enums.Frequency;

import java.time.OffsetDateTime;

@Getter
public class RegHabit {
    private final String title;
    private final String text;
    private final Frequency frequency;
    private final OffsetDateTime time;

    public RegHabit(String title, String text, String frequency) {
        this.title = title;
        this.text = text;
        this.frequency = setFrequency(frequency);
        this.time = OffsetDateTime.now();
    }

    public Frequency setFrequency(String query) {
        if (query.equals("weekly")) {
            return Frequency.WEEKLY;
        }
        return Frequency.DAILY;
    }

    @Override
    public String toString() {
        return "RegHabit{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", frequency=" + frequency +
                ", time=" + time +
                '}';
    }
}
