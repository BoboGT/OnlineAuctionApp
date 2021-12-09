package com.sda.onlineAuctionApp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String userRole;

}
