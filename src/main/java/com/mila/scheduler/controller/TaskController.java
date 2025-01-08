package com.mila.scheduler.controller;

import com.mila.scheduler.business.TaskService;
import com.mila.scheduler.business.dto.TaskDTO;
import com.mila.scheduler.infrastructure.enums.NotificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> saveTasks(@RequestBody TaskDTO dto,
                                             @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(taskService.saveTask(token, dto));
    }

    @GetMapping("/events")
    public ResponseEntity<List<TaskDTO>> searchByTaskByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime initialDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime finalDate){

        return ResponseEntity.ok(taskService.searchByScheduledDate(initialDate, finalDate));

    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> searchTaskByEmail (@RequestHeader("Authorization") String token){
        List<TaskDTO> tasks = taskService.searchTasksByEmail(token);
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTaskById(@RequestParam("id") String id){
        taskService.deleteTaskById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<TaskDTO> changeNotificationStatus (@RequestParam("status")NotificationStatus notificationStatus,
                                                             @RequestParam("id") String id){
        return ResponseEntity.ok(taskService.changeStatus(notificationStatus, id));
    }

    @PutMapping
    public ResponseEntity<TaskDTO> updateTasks(@RequestBody TaskDTO dto, @RequestParam("id") String id){
        return ResponseEntity.ok(taskService.updateTasks(dto, id));
    }
}
