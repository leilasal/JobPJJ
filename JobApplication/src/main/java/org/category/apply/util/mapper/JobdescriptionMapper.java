package org.category.apply.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.category.apply.model.Jobdescription;
import org.category.apply.to.JobdescriptionTo;

import java.util.List;

@Mapper(componentModel="spring")
public interface JobdescriptionMapper {

    @Mapping(target = "categoryId", source = "jobdescription.category.id")
    JobdescriptionTo toTo(Jobdescription jobdescription);

    Jobdescription toEntity(JobdescriptionTo jobdescriptionTo);

    List<JobdescriptionTo> getEntityList(List<Jobdescription> jobdescription);
}
