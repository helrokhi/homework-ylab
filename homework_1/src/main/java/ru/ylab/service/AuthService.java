package ru.ylab.service;

import lombok.AllArgsConstructor;
import ru.ylab.annotations.Loggable;
import ru.ylab.dto.*;
import ru.ylab.repository.UserRepository;
import ru.ylab.repository.PersonRepository;

@AllArgsConstructor
@Loggable
public class AuthService {
    public PersonDto personRegistration(RegUser regUser) {
        UserAuthDto userAuthDto = userAuthorization(regUser);
        PersonRepository personRepository = new PersonRepository();
        return (regUser != null) ? personRepository.createPerson(userAuthDto, new RegPerson()) : null;
    }

    public PersonDto personAuthorization(RegUser user) {
        PersonRepository personRepository = new PersonRepository();
        return personRepository.getPersonDto(user.getEmail(), user.getPassword());
    }

    public UserAuthDto userAuthorization(RegUser user) {
        UserRepository userRepository = new UserRepository();
        return (user != null) ? userRepository.getUserAuthDto(user.getEmail(), user.getPassword()) : null;
    }

    public UserAuthDto userRegistration(RegUser user) {
        UserRepository userRepository = new UserRepository();
        return (user != null) ? userRepository.createUser(user) : null;
    }
}
