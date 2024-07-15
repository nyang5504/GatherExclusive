package com.yang.gatherexclusive.service;

import com.yang.gatherexclusive.dto.UserDto;
import com.yang.gatherexclusive.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
}
