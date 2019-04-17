package com.hessapp.chatservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ConnectedNativeHeadersDTO {
    private List<String> nickname;

    @JsonProperty("accept-version")
    private List<Double> acceptVersion;

    @JsonProperty("heart-beat")
    private List<Integer> heartBeat;

    public ConnectedNativeHeadersDTO() {
    }

    public ConnectedNativeHeadersDTO(List<String> nickname, List<Double> acceptVersion, List<Integer> heartBeat) {

        this.nickname = nickname;
        this.acceptVersion = acceptVersion;
        this.heartBeat = heartBeat;
    }

    public List<String> getNickname() {

        return nickname;
    }

    public void setNickname(List<String> nickname) {
        this.nickname = nickname;
    }

    public List<Double> getAcceptVersion() {
        return acceptVersion;
    }

    public void setAcceptVersion(List<Double> acceptVersion) {
        this.acceptVersion = acceptVersion;
    }

    public List<Integer> getHeartBeat() {
        return heartBeat;
    }

    public void setHeartBeat(List<Integer> heartBeat) {
        this.heartBeat = heartBeat;
    }
}
