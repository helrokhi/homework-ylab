package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.PersonDto;
import ru.ylab.service.ScannerService;

@AllArgsConstructor
public class AdministrationController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void administration() {

    }
}
