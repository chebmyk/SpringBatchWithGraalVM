package com.example.springbatchwithgraalvm.job.config;

import com.example.springbatchwithgraalvm.model.Users;
import com.example.springbatchwithgraalvm.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;


@Slf4j
public class ObjectItemProcessor  implements ItemProcessor<UserDto, Users> {

    @Override
    public Users process(final UserDto user) {
        log.info("+++Received Item for Processing {}", user);

        Users u =  new Users();
        u.setId(user.userId());
        u.setAddress(user.address());
        u.setEmailAddress(user.emailAddress());
        u.setFirstName(user.firstName());
        u.setLastName(user.lastName());
        return u;
    }
}
