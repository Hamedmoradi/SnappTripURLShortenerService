package snapp.trip.com.snapptripurlshortenerservice.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;

class URLValidatorTest {
    @Mock
    private URLValidator urlValidatorUnderTest;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateURL() {
        assertThat(urlValidatorUnderTest.validateURL("http://www.google.com")).isFalse();
    }
}
