package ru.ylab.service;

import lombok.NoArgsConstructor;
import ru.ylab.annotations.Loggable;
import ru.ylab.controller.AccountController;
import ru.ylab.controller.AdminAccountController;
import ru.ylab.dto.PersonDto;
import ru.ylab.dto.RegUser;
import ru.ylab.dto.UserAuthDto;
import ru.ylab.dto.enums.Role;
import ru.ylab.repository.UserRepository;

@NoArgsConstructor
@Loggable
public class UserService {

    public void updateEmail(PersonDto person, String email) {
        UserRepository userRepository = new UserRepository();
        UserAuthDto userAuthDto = userRepository.updateUserEmail(person.getUserId(), email);
        System.out.println("Email пользователя изменен " + person + " " + userAuthDto);
    }

    public void updatePassword(PersonDto person, String password) {
        UserRepository userRepository = new UserRepository();
        UserAuthDto userAuthDto = userRepository.updateUserPassword(person.getUserId(), password);
        System.out.println("Пароль пользователя изменен " + person + " " + userAuthDto);
    }

    public void update(UserAuthDto userAuthDto) {
        UserRepository userRepository = new UserRepository();
        userRepository.updateUser(userAuthDto);
    }

    public UserAuthDto getUserByEmail(RegUser regUser) {
        UserRepository userRepository = new UserRepository();
        return (regUser != null) ? userRepository.getUserAuthDtoByEmail(regUser.getEmail()) : null;
    }

    public UserAuthDto getUserById(Long userId) {
        UserRepository userRepository = new UserRepository();
        return userRepository.getUserAuthDtoById(userId);
    }

    public void account(PersonDto person) {
        AccountController accountController = new AccountController();
        AdminAccountController adminAccountController = new AdminAccountController();
        Long userId = person.getUserId();
        UserAuthDto userAuthDto = getUserById(userId);
        if ((userAuthDto.getRole().equals(Role.ADMIN))) {
            adminAccountController.admin(person);
        } else {
            accountController.account(person);
        }
    }
    public RegUser createRegUser(String email, String password) {
        RegUser regUser = new RegUser(email, password);
        return (!regUser.getEmail().isBlank() && !regUser.getPassword().isBlank()) ? regUser : null;
    }
}
