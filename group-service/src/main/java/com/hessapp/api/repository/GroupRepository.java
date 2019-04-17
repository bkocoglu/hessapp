package com.hessapp.api.repository;

import com.hessapp.api.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {
    List<Group> findAllByParticipantsContains(String nickname);
}
