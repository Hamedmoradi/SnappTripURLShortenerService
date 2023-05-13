package urlshortenerservice.mapper;

import org.mapstruct.Mapper;
import urlshortenerservice.domain.ShortenRequest;
import urlshortenerservice.dto.UrlLongRequestDto;

@Mapper(componentModel = "spring")
public interface ShortenRequestToUrlLongRequestDtoMapper {
    UrlLongRequestDto destinationToSource(ShortenRequest shortenRequest);
}