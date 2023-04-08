package snapp.trip.com.snapptripurlshortenerservice.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

class ShortenRequestTest {

    private ShortenRequest shortenRequestUnderTest;

    @BeforeEach
    void setUp() {
        shortenRequestUnderTest = new ShortenRequest(0L, "http://googlecom",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    }

    @Test
    void testBuilder() {

        final ShortenRequest.ShortenRequestBuilder result = ShortenRequest.builder();

    }
}
