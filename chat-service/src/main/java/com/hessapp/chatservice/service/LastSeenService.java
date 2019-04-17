package com.hessapp.chatservice.service;

import com.hessapp.chatservice.dao.LastSeen;
import com.hessapp.chatservice.repositoriy.LastSeenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LastSeenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LastSeenService.class);

    private LastSeenRepository lastSeenRepository;

    LastSeenService(LastSeenRepository lastSeenRepository) {
        this.lastSeenRepository = lastSeenRepository;
    }

    public void changeLastSeen(String nickname) {
        try {
            List<LastSeen> lastSeens = lastSeenRepository.findByNickname(nickname);
            //sunucu saatleri ile türkiye saati uyuşmadığından 3 saat eklendi.
            if (lastSeens.isEmpty()) {
                LastSeen lastSeen = new LastSeen(nickname, LocalDateTime.now().plusHours(1));
                lastSeenRepository.save(lastSeen);
            } else {
                LastSeen lastSeen = lastSeens.get(0);
                lastSeen.setLastSeen(LocalDateTime.now().plusHours(3));
                lastSeenRepository.save(lastSeen);
            }
        }catch (Exception e) {
            LOGGER.error(e.toString());
        }
    }
}
