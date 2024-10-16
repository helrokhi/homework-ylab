package ru.ylab.dto;

import lombok.Getter;
import ru.ylab.dto.enums.Role;

@Getter
public class RegUser {
    private final String email;
    private final String password;
    private final Role role;

    public RegUser(String email, String password) {
        this.email = setEmail(email);
        this.password = password;
        this.role = Role.USER;
    }

    public String setEmail(String email) {
        return  (isValidEmail(email))? email : "";
    }

    public String setPassword(String password) {
        return (isValidPassword(password))? password : "";
    }


    public boolean isValidEmail(String email) {
        String regexEmail = "^[a-z0-9._%+\\-]+@[a-z0-9.\\-]+(\\.[a-z]{2,}|\\.xn--[a-z0-9]+)$";
        return email.matches(regexEmail);
    }

    public boolean isValidPassword(String password) {
        String regexPassword = "(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}";
        return password.matches(regexPassword);
    }

    @Override
    public String toString() {
        return "RegUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
