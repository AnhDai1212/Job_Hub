package java_spring.job_hub.mapper;

import java_spring.job_hub.dto.response.FavoriteResponse;
import java_spring.job_hub.entity.Favorites;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FavoriteMapper {

    @Mapping(source = "user.id", target = "userId") // Nếu cần ánh xạ userId
    @Mapping(source = "jobs.jobId", target = "jobId")  // Nếu cần ánh xạ jobId
    FavoriteResponse toFavoriteResponse(Favorites favorites);
}
