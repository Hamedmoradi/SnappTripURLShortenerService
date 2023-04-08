package snapp.trip.com.snapptripurlshortenerservice.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class URLConvertorTest {


    @Test
    void testCreateUniqueID() {
        assertThat(URLConvertor.createUniqueID(584216L)).isEqualTo("cB80");
    }

    @Test
    void testGetDictionaryKeyFromUniqueID() {
        assertThat(URLConvertor.getDictionaryKeyFromUniqueID("cB80")).isEqualTo(584216L);
    }
}
