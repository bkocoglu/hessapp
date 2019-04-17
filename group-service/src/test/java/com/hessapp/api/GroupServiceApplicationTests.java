package com.hessapp.api;

import com.hessapp.api.repository.GroupRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupServiceApplicationTests {

	@Autowired
	GroupRepository groupRepository;

	@Test
	public void contextLoads() {
	}
}

