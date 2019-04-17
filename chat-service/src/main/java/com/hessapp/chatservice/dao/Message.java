package com.hessapp.chatservice.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Messages")
public class Message {
    @Id
    private String id;
    private int groupId;
    private String from;
    private String body;
    private LocalDateTime sendDate;

    public Message() {
    }

    public Message(String id, int groupId, String from, String body, LocalDateTime sendDate) {
        this.id = id;
        this.groupId = groupId;
        this.from = from;
        this.body = body;
        this.sendDate = sendDate;
    }

    public Message(int groupId, String from, String body, LocalDateTime sendDate) {
        this.groupId = groupId;
        this.from = from;
        this.body = body;
        this.sendDate = sendDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", groupId=" + groupId +
                ", from='" + from + '\'' +
                ", body='" + body + '\'' +
                ", sendDate=" + sendDate +
                '}';
    }
}
