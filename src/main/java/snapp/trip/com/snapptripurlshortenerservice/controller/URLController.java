package snapp.trip.com.snapptripurlshortenerservice.controller;


import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import snapp.trip.com.snapptripurlshortenerservice.domain.HitRateRequest;
import snapp.trip.com.snapptripurlshortenerservice.domain.ShortenRequest;
import snapp.trip.com.snapptripurlshortenerservice.dto.UrlLongRequestDto;
import snapp.trip.com.snapptripurlshortenerservice.repository.HitRateRequestRepository;
import snapp.trip.com.snapptripurlshortenerservice.service.URLConverterService;
import snapp.trip.com.snapptripurlshortenerservice.utilities.URLValidator;

import javax.validation.Valid;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/snapptrip/hub/")
@AllArgsConstructor
public class URLController {
    private static final Logger LOGGER = LoggerFactory.getLogger(URLController.class);
    private final URLConverterService urlConverterService;

    private final RedisTemplate redisTemplate;

    private final HitRateRequestRepository hitRateRequestRepository;

    @SneakyThrows
    @PostMapping(value = "/create-shortener-url")
    public String shortenUrl(@RequestBody @Valid final ShortenRequest shortenRequest) {
        log.info("Received url to shorten: " + shortenRequest.getLongUrl());
        String longUrl = shortenRequest.getLongUrl();
        if (URLValidator.INSTANCE.validateURL(longUrl)) {
            String shortenedUrl = urlConverterService.convertToShortUrl(
                    UrlLongRequestDto
                            .builder()
                            .longUrl(longUrl)
                            .expiresDate(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2)))
                            .build());
            log.info("Shortened url to: " + shortenedUrl);
            return shortenedUrl;
        }
        throw new Exception("Please enter a valid URL");
    }


    //@ApiOperation(value = "Redirect", notes = "Finds original url from short url and redirects")
    @CachePut(value = "hitRateRequests", key = "#p0")
    @GetMapping(value = "{shortUrl}")
    public String getAndRedirect(@PathVariable String shortUrl) {
        var url = urlConverterService.getOriginalUrl(shortUrl);
        if (url != null) {
            log.info("URL is " + url);
            ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
            Long rate = valueOps.increment(url.getLongUrl());
            log.info("THE RATE OF  " + shortUrl + " (SHORT-URL) IS : " + rate);
            hitRateRequestRepository.save(HitRateRequest.builder().hitRate(rate).shorterUrl(shortUrl).id(url.getId()).build());
            return "redirect to " + url.getLongUrl() + " successful";

        }
        return "url not found";
    }

    @GetMapping(value = "/hit-rate/{shortUrl}")
    public Long getUrlHitRate(@PathVariable String shortUrl) {
        return hitRateRequestRepository.findByShorterUrl(shortUrl).getHitRate();
    }
}



