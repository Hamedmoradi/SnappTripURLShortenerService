package urlshortenerservice.exceptions;

import org.junit.jupiter.api.BeforeEach;

class InvalidUrlExceptionTest {

    private InvalidUrlException invalidUrlExceptionUnderTest;

    @BeforeEach
    void setUp() {
        invalidUrlExceptionUnderTest = new InvalidUrlException("message");
    }
}
