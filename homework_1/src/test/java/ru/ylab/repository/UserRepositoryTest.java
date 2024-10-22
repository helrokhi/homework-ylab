package ru.ylab.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.dto.RegUser;
import ru.ylab.dto.UserAuthDto;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @InjectMocks
    private UserRepository userRepository;

    @Test
    void getUserAuthDto() {
        String email = "thuel@yahoo.com";
        String password = "00000000";
        UserAuthDto userAuthDto = userRepository.getUserAuthDto(email, password);
        System.out.println(userAuthDto);
    }

    @Test
    void getUserAuthDtoByEmail() {
        String email = "thuel@yahoo.com";
        UserAuthDto userAuthDto = userRepository.getUserAuthDtoByEmail(email);
        System.out.println(userAuthDto);
    }

    @Test
    void getUserAuthDtoById() {
        UserAuthDto userAuthDto = userRepository.getUserAuthDtoById(1000L);
        System.out.println(userAuthDto);
    }

    @Test
    void createUser() {
        RegUser regUser = new RegUser("159ernie04@heller.biz", "!123asdQWE");
        UserAuthDto userAuthDto = userRepository.createUser(regUser);
        System.out.println(userAuthDto);
    }

    @Test
    void updateUserEmail() {
        UserAuthDto userAuthDto = userRepository.updateUserEmail(1010L, "159ernie@heller.biz");
        System.out.println(userAuthDto);
    }

    @Test
    void updateUserPassword() {
        UserAuthDto userAuthDto = userRepository.updateUserPassword(1010L, "!123asdQWE123");
        System.out.println(userAuthDto);
    }
}