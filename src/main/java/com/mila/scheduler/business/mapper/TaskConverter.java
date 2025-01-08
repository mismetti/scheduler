package com.mila.scheduler.business.mapper;

import com.mila.scheduler.business.dto.TaskDTO;
import com.mila.scheduler.infrastructure.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskConverter {

    @Mapping(source = "id", target = "id")
    Task toTaskEntity (TaskDTO dto);

    TaskDTO toTaskDTO(Task entity);

    List<Task> toTaskEntityList(List<TaskDTO> dtos);

    List<TaskDTO> toTaskDTOList(List<Task> entities);
}
