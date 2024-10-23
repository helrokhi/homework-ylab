package ru.ylab.service;

import lombok.AllArgsConstructor;
import ru.ylab.dto.PersonDto;
import ru.ylab.repository.PersonRepository;

@AllArgsConstructor
public class PersonService {
    private PersonDto person;

    public void updateName(String name) {
        PersonRepository personRepository = new PersonRepository();
        person = personRepository.updatePersonName(person.getId(), name);
        System.out.println("Имя пользователя изменено " + person);
    }

    public void delete(PersonDto personDto) {
        PersonRepository personRepository = new PersonRepository();
        personRepository.deletePerson(personDto);
    }
}
