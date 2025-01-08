package com.mila.scheduler.business.mapper;

import com.mila.scheduler.business.dto.TaskDTO;
import com.mila.scheduler.infrastructure.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConverter {
    Task toTaskEntity (TaskDTO dto);
    TaskDTO toTaskDTO(Task entity);
}
