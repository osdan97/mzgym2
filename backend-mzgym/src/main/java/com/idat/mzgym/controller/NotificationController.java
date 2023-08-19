package com.idat.mzgym.controller;

import com.idat.mzgym.dto.LocationRegisterDto;
import com.idat.mzgym.dto.Mensaje;
import com.idat.mzgym.model.Location;
import com.idat.mzgym.model.Notification;
import com.idat.mzgym.repository.LocationRepository;
import com.idat.mzgym.repository.NotificationRepository;
import com.idat.mzgym.service.LocationService;
import com.idat.mzgym.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin(origins="*")
public class NotificationController {

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    NotificationService notificationService;

    @GetMapping("/list")
    public ResponseEntity<List<Location>> list() {
        List<Notification> list = notificationService.getNotification();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Notification notification) {
        if (notification.getTitle() == null || notification.getTitle().isEmpty()) {
            return new ResponseEntity<>("Title can't be empty", HttpStatus.BAD_REQUEST);
        }
        if (notification.getMessage() == null || notification.getMessage().isEmpty()) {
            return new ResponseEntity<>("Message can't be empty", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(notificationService.createNotification(notification) ,HttpStatus.CREATED);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody Notification notification) {
        if (!notificationRepository.existsById(id))
            return new ResponseEntity(new Mensaje(" notification does not exist"), HttpStatus.NOT_FOUND);


        Notification notificationUpdated = notificationService.getOne(id).get();
        notificationUpdated.setTitle(notification.getTitle());
        notificationUpdated.setMessage(notification.getMessage());
        notificationRepository.save(notificationUpdated);
        return new ResponseEntity(new Mensaje("notification updated successfully"), HttpStatus.OK);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<?>findById(@PathVariable("id") String id){
        if (!notificationRepository.existsById(id))
            return new ResponseEntity(new Mensaje("notification does not exist"), HttpStatus.NOT_FOUND);
        Notification notificationFound = notificationService.getOne(id).get();
        return new ResponseEntity(notificationFound,HttpStatus.OK);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!notificationRepository.existsById(id))
            return new ResponseEntity(new Mensaje("notification does not exist"), HttpStatus.NOT_FOUND);
        notificationService.deleteNotification(id);
        return new ResponseEntity(new Mensaje("notification deleted successfully"), HttpStatus.OK);
    }
}
