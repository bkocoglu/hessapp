package com.hessapp.chatservice.repositoriy;

import com.hessapp.chatservice.dao.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String>{
    List<Message> findByGroupIdOrderBySendDate(int groupId);

    List<Message> findByGroupIdOrderBySendDateDesc(int groupId);

    void deleteAllByGroupId(int groupId);
}
