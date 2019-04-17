package com.hessapp.api.repository;

import com.hessapp.api.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {
    List<Activity> findBySpendId(String spendId);
    void deleteBySpendId(String spendId);
}
