package com.hessapp.api.service;

import com.hessapp.api.repository.ActivityRepository;
import com.hessapp.api.repository.SpendRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.stream.Stream.of;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SpendServiceTest {

    private SpendRepository spendRepository;
    private ActivityRepository activityRepository;
    private GroupService groupService;

    private SpendService spendService;

    @Before
    public void setUp() throws Exception {
        spendRepository = mock(SpendRepository.class);
        activityRepository = mock(ActivityRepository.class);
        groupService = mock(GroupService.class);

        spendService = new SpendService(spendRepository, activityRepository, groupService);
    }

    @Test
    public void createSpend() {

    }

    @Test
    public void createActivities() {

    }
}