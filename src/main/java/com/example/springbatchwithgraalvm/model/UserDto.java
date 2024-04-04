package com.example.springbatchwithgraalvm.model;

import lombok.Builder;

@Builder
public record UserDto(
        Long userId,
        String firstName,
        String lastName,
        String emailAddress,
        String phoneNumber,
        String address,
        String birthDate) {
}
