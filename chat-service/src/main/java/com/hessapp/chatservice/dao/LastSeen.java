package com.hessapp.chatservice.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "LastSeens")
public class LastSeen {
    @Id
    private String id;
    private String nickname;
    private LocalDateTime lastSeen;

    public LastSeen() {
    }

    public LastSeen(String id, String nickname, LocalDateTime lastSeen) {
        this.id = id;
        this.nickname = nickname;
        this.lastSeen = lastSeen;
    }

    public LastSeen(String nickname, LocalDateTime lastSeen) {
        this.nickname = nickname;
        this.lastSeen = lastSeen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    @Override
    public String toString() {
        return "LastSeen{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", lastSeen=" + lastSeen +
                '}';
    }
}
