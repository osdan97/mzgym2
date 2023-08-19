package com.idat.mzgym.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Notification")
public class Notification {
    @Id
    @Column(name="notification_uuid",nullable = false,unique = true)
    private String notificationUuid;
    @Column(name="title",nullable = false)
    private String title ;
    @Column(name="message",nullable = false)
    private String message;
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    public Notification(String title,String message){
        this.notificationUuid= UUID.randomUUID().toString();
        this.title=title;
        this.message=message;
        this.createdDate=LocalDateTime.now();
    }
}
