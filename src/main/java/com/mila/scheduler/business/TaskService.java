package com.mila.scheduler.business;

import com.mila.scheduler.business.dto.TaskDTO;
import com.mila.scheduler.business.mapper.TaskConverter;
import com.mila.scheduler.business.mapper.TaskUpdateConverter;
import com.mila.scheduler.infrastructure.entity.Task;
import com.mila.scheduler.infrastructure.enums.NotificationStatus;
import com.mila.scheduler.infrastructure.exceptions.ResourceNotFoundException;
import com.mila.scheduler.infrastructure.repository.TaskRepository;
import com.mila.scheduler.infrastructure.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskConverter taskConverter;
    private final JwtUtil jwtUtil;
    private final TaskUpdateConverter taskUpdateConverter;

    public TaskDTO saveTask(String token, TaskDTO dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        dto.setCreationDate(LocalDateTime.now());
        dto.setNotificationStatus(NotificationStatus.WAITING);
        dto.setUserEmail(email);
        Task taskEntity = taskConverter.toTaskEntity(dto);
        return taskConverter.toTaskDTO(
                taskRepository.save(taskEntity));

    }

    public List<TaskDTO> searchByScheduledDate (LocalDateTime initialDate, LocalDateTime finalDate){

        return taskConverter.toTaskDTOList(
                taskRepository.findByEventDateBetween(initialDate, finalDate)
        );

    }

    public List<TaskDTO> searchTasksByEmail(String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        List<Task> taskList = taskRepository.findByUserEmail(email);

        return taskConverter.toTaskDTOList(taskList);
    }

    public void deleteTaskById(String id){
        try {
            taskRepository.deleteById(id);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("ID do not found" + id + e.getCause());
        }
    }

    public TaskDTO changeStatus(NotificationStatus status, String id){
        try {
            Task entity = taskRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Task not found" + id));
            entity.setNotificationStatus(status);
            return taskConverter.toTaskDTO(taskRepository.save(entity));
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Unable to change task status." + e.getCause());
        }
    }

    public TaskDTO updateTasks(TaskDTO dto, String id){
        try{
            Task entity = taskRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Task not found" + id));
            taskUpdateConverter.updateTasks(dto, entity);
            return taskConverter.toTaskDTO(taskRepository.save(entity));
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Unable to change task status "+ e.getCause());
        }
    }

}
