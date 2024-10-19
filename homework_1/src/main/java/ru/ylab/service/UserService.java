package ru.ylab.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.ylab.dto.PersonDto;
import ru.ylab.dto.RegUser;
import ru.ylab.dto.UserAuthDto;
import ru.ylab.repository.UserRepository;

@AllArgsConstructor
@NoArgsConstructor
public class UserService {
    private PersonDto person;

    public void updateEmail(String email) {
        UserRepository userRepository = new UserRepository();
        UserAuthDto userAuthDto = userRepository.updateUserEmail(person.getUserId(), email);
        System.out.println("Email пользователя изменен " + person + " " + userAuthDto);
    }

    public void updatePassword(String password) {
        UserRepository userRepository = new UserRepository();
        UserAuthDto userAuthDto = userRepository.updateUserPassword(person.getUserId(), password);
        System.out.println("Пароль пользователя изменен " + person + " " + userAuthDto);
    }

    public UserAuthDto getUserByEmail(RegUser regUser) {
        UserRepository userRepository = new UserRepository();
        return (regUser != null) ? userRepository.getUserAuthDtoByEmail(regUser.getEmail()) : null;
    }
}
