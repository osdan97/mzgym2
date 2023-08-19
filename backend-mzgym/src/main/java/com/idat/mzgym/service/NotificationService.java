package com.idat.mzgym.service;

import com.idat.mzgym.dto.LocationRegisterDto;
import com.idat.mzgym.model.Location;
import com.idat.mzgym.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    public Notification createNotification(Notification notification);
    public Notification updateNotifications(String id, Notification notification);

    public List<Notification> getNotification();

    public void deleteNotification(String id);

    public Optional<Notification> getOne(String id);
}
