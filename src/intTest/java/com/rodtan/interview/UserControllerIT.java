package com.rodtan.interview;

import com.rodtan.interview.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT {
    @Autowired private TestRestTemplate restTemplate;

    private JacksonTester<User> UserJacksonTester;

    private JacksonTester<List<User>> UserListJacksonTester;

    private User createUser() {
        User user = new User(1, "Rod", "rodtest@example.com");
        user.setId(1);
        return user;
    }

    @Test
    public void returnsNotFoundIfUserNotFound() throws Exception {
        User expected = new User(null, null, null);
        ResponseEntity<User> responseEntity = restTemplate.getForEntity("/users/1", User.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo(expected);
    }

    @Test
    public void happyFlow() throws Exception {
        User user = createUser();
        ResponseEntity<User> responseEntity = restTemplate.postForEntity("/users", user, User.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(user);

        ResponseEntity<User> responseEntity2 = restTemplate.getForEntity("/users/1", User.class);
        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity2.getBody()).isEqualTo(user);

        ResponseEntity<User[]> responseEntity3 = restTemplate.getForEntity("/users", User[].class);
        assertThat(responseEntity3.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity3.getBody()).isNotEmpty();
        assertThat(responseEntity3.getBody()).contains(user);

        user.setName("RodTan");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        Map<String, String> param = new HashMap<>();
        ResponseEntity<User> responseEntity4 = restTemplate.exchange("/users/1", HttpMethod.PUT, requestEntity, User.class, param);
        assertThat(responseEntity4.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity4.getBody()).isEqualTo(user);

        ResponseEntity<User> responseEntity5 = restTemplate.exchange("/users/1", HttpMethod.DELETE, requestEntity, User.class);
        assertThat(responseEntity5.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }


}
