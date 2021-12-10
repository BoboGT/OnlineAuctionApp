package com.sda.onlineAuctionApp.service;

import com.sda.onlineAuctionApp.dto.UserDto;
import com.sda.onlineAuctionApp.mapper.UserMapper;
import com.sda.onlineAuctionApp.model.User;
import com.sda.onlineAuctionApp.model.UserRole;
import com.sda.onlineAuctionApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public void add(UserDto userDto) {
        // obtinem un user pe baza unui UserDto
        User user = userMapper.map(userDto);
        // salvam un user in baza de date cu datele din userDto
        userRepository.save(user);

    }
}
