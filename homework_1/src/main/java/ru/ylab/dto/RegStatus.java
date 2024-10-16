package ru.ylab.dto;

import lombok.Getter;
import ru.ylab.dto.enums.StatusType;

import java.time.OffsetDateTime;

@Getter
public class RegStatus {
    private final StatusType statusType;
    private final OffsetDateTime time;

    public RegStatus(String type) {
        this.statusType = setStatusType(type);
        this.time = OffsetDateTime.now();
    }

    public StatusType setStatusType(String type) {
        return StatusType.valueOf(type);
    }
}
