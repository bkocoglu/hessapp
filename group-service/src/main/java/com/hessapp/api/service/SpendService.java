package com.hessapp.api.service;

import com.hessapp.api.dto.request.CreateSpendRequest;
import com.hessapp.api.exception.DatabaseException;
import com.hessapp.api.model.Activity;
import com.hessapp.api.model.Group;
import com.hessapp.api.model.Spend;
import com.hessapp.api.repository.ActivityRepository;
import com.hessapp.api.repository.SpendRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;


@Service
@PropertySource("classpath:message.properties")
public class SpendService {
    private static final Logger log = LoggerFactory.getLogger(SpendService.class);

    private SpendRepository spendRepository;
    private ActivityRepository activityRepository;
    private GroupService groupService;

    public SpendService(SpendRepository spendRepository,
                        ActivityRepository activityRepository,
                        GroupService groupService){
        this.spendRepository = spendRepository;
        this.activityRepository = activityRepository;
        this.groupService = groupService;
    }

    public Spend createSpend(CreateSpendRequest createSpendRequest) throws IllegalAccessException {
        Group group = groupService.getGroupById(createSpendRequest.getGroupId());


        boolean ownership = false;

        for (String participant: group.getParticipants()) {
            if (participant.equals(createSpendRequest.getFrom()))
                ownership=true;
        }

        if (!ownership){
            log.error(illegalSpendMessage);
            throw new IllegalAccessException(illegalSpendMessage);
        }

        Spend spend = new Spend();
        spend.setDate(LocalDateTime.now());
        spend.setDescription(createSpendRequest.getDescription());
        spend.setFrom(createSpendRequest.getFrom());
        spend.setGroupId(createSpendRequest.getGroupId());
        spend.setTotalAmount(createSpendRequest.getTotalAmount());

        spend = spendRepository.save(spend);

        if (spend == null){
            log.error("Spend veritabanına kaydedilemedi !");
            throw new DatabaseException();
        }else {
            log.info(spend.getId() + " saved !");
            return spend;
        }
    }

    public List<Activity> createActivities(Spend spend){
        HashSet<Activity> activitiesHash = new HashSet<>();

        Group group = groupService.getGroupById(spend.getGroupId());

        double amount = spend.getTotalAmount() / group.getParticipants().size();

        for (String participant:group.getParticipants()) {
            if (!participant.equals(spend.getFrom())){
                activitiesHash.add(Activity
                        .builder()
                        .amount(amount)
                        .destination(participant)
                        .from(spend.getFrom())
                        .spendId(spend.getId())
                        .date(spend.getDate())
                        .build());
            }
        }

        List<Activity> activities = activityRepository.saveAll((Iterable<Activity>)activitiesHash);

        return activities;

    }

    @Value("${illegal.spend}")
    private String illegalSpendMessage;
}
