package urlshortenerservice.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HitRateRequestTest {

    private HitRateRequest hitRateRequestUnderTest;

    @BeforeEach
    void setUp() {
        hitRateRequestUnderTest = new HitRateRequest(10L, 5L, "http://googlecom");
    }

    @Test
    void testEquals() {
        assertThat(hitRateRequestUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() {
        assertThat(hitRateRequestUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(hitRateRequestUnderTest.hashCode()).isEqualTo(1377796666);
    }

    @Test
    void testToString() {
        assertThat(hitRateRequestUnderTest.toString()).isEqualTo("HitRateRequest(id=10, hitRate=5, shorterUrl=http://googlecom)");
    }

    @Test
    void testBuilder() {
        final HitRateRequest.HitRateRequestBuilder result = HitRateRequest.builder();
    }
}
