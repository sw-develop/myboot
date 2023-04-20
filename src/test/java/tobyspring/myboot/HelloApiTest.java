package tobyspring.myboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloApiTest {

    @Test
    void hello() {
        TestRestTemplate restTemplate = new TestRestTemplate();

        String url = "http://localhost:8080/hello?name={name}";
        ResponseEntity<String> res = restTemplate.getForEntity(url, String.class, "Spring");

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE);
        assertThat(Objects.requireNonNull(res.getBody()).trim()).isEqualTo("Hello Spring");
    }

    @Test
    void failsHelloApi() {
        TestRestTemplate restTemplate = new TestRestTemplate();

        String url = "http://localhost:8080/hello?name=";
        ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
