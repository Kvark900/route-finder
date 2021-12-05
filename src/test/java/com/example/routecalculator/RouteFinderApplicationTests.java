package com.example.routecalculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RouteFinderApplicationTests
{
	@Autowired
	private TestRestTemplate restTemplate;

	@Value ("${URL}")
	String url;

	private static String BASE_URL;
	private static HttpHeaders headers;

	@BeforeAll
	static void init()
	{
		BASE_URL = "/routing";
		headers = new HttpHeaders();
		headers.setContentType(APPLICATION_JSON);
	}

	@Test
	void contextLoads()
	{
		Assertions.assertNotNull(url);
	}

	@Test
	void routeTest()
	{
		assertDoesNotThrow(
				() -> {
					ResponseEntity<String> forEntity = restTemplate.getForEntity(BASE_URL + "/CZE/ITA", String.class);
					Assertions.assertTrue(forEntity.getStatusCode().is2xxSuccessful());
					Assertions.assertNotNull(forEntity);
					Assertions.assertNotNull(forEntity.getBody());
					System.out.println(forEntity.getBody());
				}
		);
	}

	@Test
	void shouldResultInCountryNotFoundException()
	{
		assertDoesNotThrow(
				() -> {
					ResponseEntity<String> forEntity = restTemplate.getForEntity(BASE_URL + "/CZasE/IsTA", String.class);
					Assertions.assertTrue(forEntity.getStatusCode().is4xxClientError());
					System.out.println(forEntity.getBody());
				}
		);
	}

	@Test
	void shouldResultInRouteNotFoundException()
	{
		assertDoesNotThrow(
				() -> {
					ResponseEntity<String> forEntity = restTemplate.getForEntity(BASE_URL + "/ABW/ITA", String.class);
					Assertions.assertTrue(forEntity.getStatusCode().is4xxClientError());
					System.out.println(forEntity.getBody());
				}
		);
	}

}
