package com.example.springbatchwithgraalvm.job.config;


import com.example.springbatchwithgraalvm.job.repositories.UsersRepository;
import com.example.springbatchwithgraalvm.model.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;


@Slf4j
public class UsersItemWriter implements ItemWriter<Users> {

    private final UsersRepository visitorsRepository;

    public UsersItemWriter(UsersRepository visitorsRepository) {
        this.visitorsRepository = visitorsRepository;
    }

    @Override
    public void write(Chunk<? extends Users> users) {
        log.info("++++Writing to database {} items", users.getItems().size());
        users.getItems().forEach(visitorsRepository::save);
    }
}