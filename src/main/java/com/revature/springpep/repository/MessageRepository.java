package com.revature.springpep.repository;

import com.revature.springpep.model.Account;
import com.revature.springpep.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByPostedBy(Account postedBy);
}
