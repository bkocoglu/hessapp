package com.hessapp.chatservice.repositoriy;

import com.hessapp.chatservice.dao.LastSeen;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LastSeenRepository extends MongoRepository<LastSeen, String>{
    List<LastSeen> findByNickname(String nickname);
}

