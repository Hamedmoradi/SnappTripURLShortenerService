package urlshortenerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortenerservice.domain.HitRateRequest;
import urlshortenerservice.dto.UrlLongRequestDto;
import urlshortenerservice.repository.HitRateRequestRepository;
import urlshortenerservice.service.URLConverterService;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


class URLControllerTest {

    @Mock
    private URLController urlController;
    private MockMvc mockMvc;
    @Mock
    private RedisTemplate mockRedisTemplate;
    @Mock
    private URLConverterService mockUrlConverterService;
    @Mock
    private HitRateRequestRepository mockHitRateRequestRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(urlController).build();
    }

    @Test
    void testShortenUrl() throws Exception {


        UrlLongRequestDto urlLongRequestDto = UrlLongRequestDto.builder().longUrl("http://www.google.com").build();

        ResultActions result = mockMvc.perform(post("http://localhost:8080/snapptrip/hub/create-shortener-url")
                .content(objectMapper.writeValueAsString(urlLongRequestDto))
                .contentType(MediaType.APPLICATION_JSON));

        assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.andReturn().getResponse().getContentAsString()).isEqualTo("");
    }

    @Test
    void testGetAndRedirect() throws Exception {

        MockHttpServletResponse result = mockMvc.perform(get("http://localhost:8080/snapptrip/hub/HFvd")
                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();

        final UrlLongRequestDto urlLongRequestDto = new UrlLongRequestDto(0L, "http://www.google.com",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockUrlConverterService.getOriginalUrl("HFvd")).thenReturn(urlLongRequestDto);
        when(mockRedisTemplate.opsForValue()).thenReturn(null);
        when(mockHitRateRequestRepository.save(new HitRateRequest(0L, 0L, "HFvd")))
                .thenReturn(new HitRateRequest(0L, 0L, "HFvd"));

        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getContentAsString()).isEqualTo("");

    }

    @Test
    void testGetAndRedirect_URLConverterServiceReturnsNull() throws Exception {


        MockHttpServletResponse result = mockMvc.perform(get("http://localhost:8080/snapptrip/hub/HFvd")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        final UrlLongRequestDto urlLongRequestDto = new UrlLongRequestDto(0L, "http://www.google.com",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        when(mockUrlConverterService.getOriginalUrl("HFvd")).thenReturn(urlLongRequestDto);
        when(mockRedisTemplate.opsForValue()).thenReturn(null);
        when(mockHitRateRequestRepository.save(new HitRateRequest(0L, 0L, "HFvd")))
                .thenReturn(new HitRateRequest(0L, 0L, "HFvd"));
        when(mockUrlConverterService.getOriginalUrl("HFvd")).thenReturn(null);

        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getContentAsString()).isEqualTo("");
    }

    @Test
    void testGetAndRedirect_HitRateRequestRepositoryThrowsOptimisticLockingFailureException() throws Exception {

        final UrlLongRequestDto urlLongRequestDto = new UrlLongRequestDto(0L, "http://www.google.com",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockUrlConverterService.getOriginalUrl("HFvd")).thenReturn(urlLongRequestDto);
        when(mockRedisTemplate.opsForValue()).thenReturn(null);
        when(mockHitRateRequestRepository.save(new HitRateRequest(0L, 0L, "HFvd")))
                .thenThrow(OptimisticLockingFailureException.class);

        mockMvc.perform(get("http://localhost:8080/snapptrip/hub/HFvd")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResolvedException();

    }

    @Test
    void testGetUrlHitRate() throws Exception {

        when(mockHitRateRequestRepository.findByShorterUrl("HFvd"))
                .thenReturn(new HitRateRequest(0L, 0L, "HFvd"));


        final MockHttpServletResponse response = mockMvc.perform(get("/http://localhost:8080/snapptrip/hub/HFvd" )
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    }
}
