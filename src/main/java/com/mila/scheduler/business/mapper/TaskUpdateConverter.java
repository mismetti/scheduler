package com.mila.scheduler.business.mapper;

import com.mila.scheduler.business.dto.TaskDTO;
import com.mila.scheduler.infrastructure.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskUpdateConverter {

    void updateTasks(TaskDTO dto, @MappingTarget Task entity);

}
