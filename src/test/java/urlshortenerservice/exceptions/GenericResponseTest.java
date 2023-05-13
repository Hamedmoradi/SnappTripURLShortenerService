package urlshortenerservice.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GenericResponseTest {

    private GenericResponse genericResponseUnderTest;

    @BeforeEach
    void setUp() {
        genericResponseUnderTest = new GenericResponse(Map.ofEntries(Map.entry("value", "value")));
    }

    @Test
    void testEquals() {
        assertThat(genericResponseUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() {
        assertThat(genericResponseUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(genericResponseUnderTest.hashCode()).isEqualTo(59);
    }

    @Test
    void testToString() {
        assertThat(genericResponseUnderTest.toString()).isEqualTo("GenericResponse(data={value=value})");
    }
}
