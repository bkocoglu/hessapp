package com.hessapp.api.kafka.dto;

import lombok.ToString;

@ToString
public class KafkaModelDto {
    private String mail;
    private String activationUrl;
    private String newPass;

    public KafkaModelDto() {
    }

    public KafkaModelDto(String mail, String activationUrl, String newPass) {
        this.mail = mail;
        this.activationUrl = activationUrl;
        this.newPass = newPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getActivationUrl() {
        return activationUrl;
    }

    public void setActivationUrl(String activationUrl) {
        this.activationUrl = activationUrl;
    }

}
