package urlshortenerservice.service;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import urlshortenerservice.domain.ShortenRequest;
import urlshortenerservice.dto.UrlLongRequestDto;
import urlshortenerservice.mapper.ShortenRequestToUrlLongRequestDtoMapper;
import urlshortenerservice.repository.ShortenRequestRepository;
import urlshortenerservice.utilities.URLConvertor;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service
@AllArgsConstructor
public class URLConverterService {

    private RedisTemplate redisTemplate;
    private ShortenRequestToUrlLongRequestDtoMapper mapper;
    private final ShortenRequestRepository shortenRequestRepository;

    public String convertToShortUrl(UrlLongRequestDto request) {
        var url = new ShortenRequest();
        url.setLongUrl(request.getLongUrl());
        url.setExpiresDate(request.getExpiresDate());
        url.setCreatedDate(new Date());
        var entity = shortenRequestRepository.save(url);
        redisTemplate.opsForValue().set(entity.getId().toString(), entity, 5, TimeUnit.SECONDS);
        return URLConvertor.createUniqueID(entity.getId());

    }

    public @ResponseBody UrlLongRequestDto getOriginalUrl(String shortUrl) {
        var id = URLConvertor.getDictionaryKeyFromUniqueID(shortUrl);
        var entity = shortenRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no entity with " + shortUrl));

        if (entity.getExpiresDate() != null && entity.getExpiresDate().before(new Date())) {
            throw new EntityNotFoundException("Link expired!");
        }
        return mapper.destinationToSource(entity);

    }

}
