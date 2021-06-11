package com.example.safood.Model;

public class Notification {
    private String date;
    private String notificationContent;
    private String subject;
    private String img;
    
    public Notification() {

    }

    public Notification(String date, String notificationContent, String subject,String img) {
        this.date = date;
        this.notificationContent = notificationContent;
        this.subject = subject;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
