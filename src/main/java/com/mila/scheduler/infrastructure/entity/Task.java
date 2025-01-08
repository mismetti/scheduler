package com.mila.scheduler.infrastructure.entity;

import com.mila.scheduler.infrastructure.enums.NotificationStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("task")
public class Tasks {

    @Id
    private String id;
    private String taskName;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime eventDate;
    private String userEmail;
    private LocalDateTime modificationDate;
    private NotificationStatus notificationStatus;


}
