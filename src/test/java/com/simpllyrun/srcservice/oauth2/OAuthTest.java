package com.simpllyrun.srcservice.oauth2;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OAuthTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = 8080;
    }

    @Test
    @DisplayName("OAuth 테스트")
    void oAuthTest() {
        RestAssured.given().log().all()
                .when()
                .redirects().follow(false) // 리다이렉트 방지
                .get("/oauth2/authorization/naver")
                .then()
                .log().all()
                .assertThat().statusCode(302);
    }
}
