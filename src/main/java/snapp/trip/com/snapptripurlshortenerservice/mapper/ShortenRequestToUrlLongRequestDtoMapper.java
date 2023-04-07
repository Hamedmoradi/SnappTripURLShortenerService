package snapp.trip.com.snapptripurlshortenerservice.mapper;

import org.mapstruct.Mapper;
import snapp.trip.com.snapptripurlshortenerservice.domain.ShortenRequest;
import snapp.trip.com.snapptripurlshortenerservice.dto.UrlLongRequestDto;

@Mapper(componentModel = "spring")
public interface ShortenRequestToUrlLongRequestDtoMapper {
    UrlLongRequestDto destinationToSource(ShortenRequest shortenRequest);
}