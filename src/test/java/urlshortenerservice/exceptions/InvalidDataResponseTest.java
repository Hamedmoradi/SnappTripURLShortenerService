package urlshortenerservice.exceptions;

import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Map;

class InvalidDataResponseTest {

    private InvalidDataResponse invalidDataResponseUnderTest;

    @BeforeEach
    void setUp() {
        invalidDataResponseUnderTest = new InvalidDataResponse(
                Map.ofEntries(Map.entry("value", Map.ofEntries(Map.entry("value", List.of("value"))))));
    }
}
