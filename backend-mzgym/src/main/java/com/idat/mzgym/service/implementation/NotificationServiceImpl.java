package com.idat.mzgym.service.implementation;

import com.idat.mzgym.model.Location;
import com.idat.mzgym.model.Notification;
import com.idat.mzgym.repository.NotificationRepository;
import com.idat.mzgym.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;
    @Override
    public Notification createNotification(Notification notification) {
        String title=notification.getTitle();
        String message=notification.getMessage();
        Notification notificationCreate=new Notification(title,message);
        notificationRepository.save(notificationCreate);
        return notificationCreate;
    }

    @Override
    public Notification updateNotifications(String id, Notification notification) {
        if(!notificationRepository.existsById(id)){
            throw new IllegalStateException("Notification does not exists");
        }
        Notification updateNotification= notificationRepository.findById(id).get();
        updateNotification.setTitle(notification.getTitle());
        updateNotification.setMessage(notification.getMessage());

       notificationRepository.save(updateNotification);
        return updateNotification;
    }

    @Override
    public List<Notification> getNotification() {
        List<Notification> listNotifications=notificationRepository.findAll();
        return listNotifications;
    }

    @Override
    public void deleteNotification(String id) {
        if(!notificationRepository.existsById(id)){
            throw new IllegalStateException("Notification does not exists");
        }
        notificationRepository.deleteById(id);

    }

    @Override
    public Optional<Notification> getOne(String id) {
        return notificationRepository.findById(id);
    }
}
