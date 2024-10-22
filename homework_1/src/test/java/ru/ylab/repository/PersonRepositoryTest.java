package ru.ylab.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.dto.PersonDto;
import ru.ylab.dto.RegPerson;
import ru.ylab.dto.UserAuthDto;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryTest {

    @InjectMocks
    private PersonRepository personRepository;

    @InjectMocks
    private UserRepository userRepository;

    @Test
    void getPersonDto() {
        String email = "thuel@yahoo.com";
        String password = "00000000";
        PersonDto personDto = personRepository.getPersonDto(email, password);
        System.out.println(personDto);
    }

    @Test
    void getPersonDtoById() {
        PersonDto personDto = personRepository.getPersonDtoById(10000L);
        System.out.println(personDto);
    }

    @Test
    void createPerson() {
        UserAuthDto userAuthDto = userRepository.getUserAuthDtoById(1010L);
        RegPerson regPerson = new RegPerson();

        PersonDto personDto = personRepository.createPerson(userAuthDto, regPerson);
        System.out.println(personDto);
    }

    @Test
    void updatePersonName() {
        PersonDto personDto = personRepository.updatePersonName(10010L, "John Doe");
        System.out.println(personDto);
    }

    @Test
    void deletePerson() {
        PersonDto personDto = personRepository.getPersonDtoById(10010L);
        personRepository.deletePerson(personDto);
        personDto = personRepository.getPersonDtoById(10010L);
        System.out.println(personDto);
    }
}