package snapp.trip.com.snapptripurlshortenerservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import snapp.trip.com.snapptripurlshortenerservice.domain.ShortenRequest;
import snapp.trip.com.snapptripurlshortenerservice.dto.UrlLongRequestDto;
import snapp.trip.com.snapptripurlshortenerservice.mapper.ShortenRequestToUrlLongRequestDtoMapper;
import snapp.trip.com.snapptripurlshortenerservice.repository.URLRepository;
import snapp.trip.com.snapptripurlshortenerservice.utilities.URLConvertor;

import javax.persistence.EntityNotFoundException;
import java.util.Date;


@Service
@AllArgsConstructor
public class URLConverterService {

    private final URLRepository urlRepository;
    private ShortenRequestToUrlLongRequestDtoMapper mapper;

    //    @Cacheable(value="url", key="#request")
    public String convertToShortUrl(UrlLongRequestDto request) {
        var url = new ShortenRequest();
        url.setLongUrl(request.getLongUrl());
        url.setExpiresDate(request.getExpiresDate());
        url.setCreatedDate(new Date());
        var entity = urlRepository.save(url);
        return URLConvertor.createUniqueID(entity.getId());

    }

    public @ResponseBody UrlLongRequestDto getOriginalUrl(String shortUrl) {
        var id = URLConvertor.getDictionaryKeyFromUniqueID(shortUrl);
        var entity = urlRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no entity with " + shortUrl));

        if (entity.getExpiresDate() != null && entity.getExpiresDate().before(new Date())) {
            throw new EntityNotFoundException("Link expired!");
        }
        return mapper.destinationToSource(entity);

    }

}
