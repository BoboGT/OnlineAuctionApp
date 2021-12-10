package com.sda.onlineAuctionApp.mapper;

import com.sda.onlineAuctionApp.dto.UserDto;
import com.sda.onlineAuctionApp.model.User;
import com.sda.onlineAuctionApp.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User map(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        String passwordCoded = bCryptPasswordEncoder.encode(userDto.getPassword());

        user.setPassword(passwordCoded);
        user.setUserRole(UserRole.valueOf(userDto.getUserRole()));

        return user;

    }
}
