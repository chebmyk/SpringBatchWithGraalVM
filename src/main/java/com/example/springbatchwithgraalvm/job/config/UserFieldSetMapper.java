package com.example.springbatchwithgraalvm.job.config;

import com.example.springbatchwithgraalvm.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

@Slf4j
public class UserFieldSetMapper implements FieldSetMapper<UserDto> {
    @Override
    public UserDto mapFieldSet(final FieldSet fieldSet) throws BindException {
        log.info("+++Mapping fields");
        return UserDto.builder()
                .userId(fieldSet.readLong("userId"))
                .firstName(fieldSet.readString("firstName"))
                .lastName(fieldSet.readString("lastName"))
                .emailAddress(fieldSet.readString("emailAddress"))
                .phoneNumber(fieldSet.readString("phoneNumber"))
                .address(fieldSet.readString("address"))
                .birthDate(fieldSet.readString("birthDate"))
                .build();
    }
}