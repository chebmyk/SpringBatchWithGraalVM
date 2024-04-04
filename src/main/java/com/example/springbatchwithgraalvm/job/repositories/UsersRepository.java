package com.example.springbatchwithgraalvm.job.repositories;

import com.example.springbatchwithgraalvm.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
}