package com.hessapp.api.repository;

import com.hessapp.api.model.Spend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpendRepository extends MongoRepository<Spend, String> {
    List<Spend> findByGroupId(String groupId);
    void deleteByGroupId(String groupId);
}
