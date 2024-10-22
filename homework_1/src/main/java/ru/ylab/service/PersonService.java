package ru.ylab.service;

import lombok.NoArgsConstructor;
import ru.ylab.annotations.Loggable;
import ru.ylab.dto.PersonDto;
import ru.ylab.repository.PersonRepository;

@NoArgsConstructor
@Loggable
public class PersonService {

    public void updateName(PersonDto person, String name) {
        PersonRepository personRepository = new PersonRepository();
        person = personRepository.updatePersonName(person.getId(), name);
        System.out.println("Имя пользователя изменено " + person);
    }

    public void update(PersonDto personDto) {
        PersonRepository personRepository = new PersonRepository();
        personRepository.updatePerson(personDto);
        System.out.println("Данные пользователя обновлены" + personDto);
    }

    public void delete(PersonDto personDto) {
        PersonRepository personRepository = new PersonRepository();
        personRepository.deletePerson(personDto);
    }

    public PersonDto getPersonById (Long personId) {
        PersonRepository personRepository = new PersonRepository();
        return personRepository.getPersonDtoById(personId);
    }
}
