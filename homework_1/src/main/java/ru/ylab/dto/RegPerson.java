package ru.ylab.dto;

import lombok.Getter;

@Getter
public class RegPerson {
    private final String name;
    private final Boolean isBlocked;

    public RegPerson() {
        this.name = "";
        this.isBlocked = false;
    }
}
