package com.hassapp.api;

import com.hassapp.api.client.group.dto.GroupListRequestDTO;
import com.hassapp.api.client.group.dto.GroupListResponseDTO;
import com.hassapp.api.repository.UserRepository;
import com.hassapp.api.service.FileStorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceApplicationTests {

    @Test
    public void name() {
    }
}
