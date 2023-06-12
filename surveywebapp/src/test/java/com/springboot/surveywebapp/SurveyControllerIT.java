package com.springboot.surveywebapp;


import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.springboot.surveywebapp.model.Question;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SurveyControllerIT {
	
	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate= new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();
	
	@BeforeEach
	public void before() {
		headers.add("Authorization", createHttpAuthenticationHeaderValue("user1","user123"));
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	}
	
 
	@Test
	public void testRetrieveSurveyQuestion() {
			
		HttpEntity entity= new HttpEntity<String>(null,headers);
		ResponseEntity<String> response= restTemplate.exchange(createURLWithPort("/surveys/Survey1/questions/Question1"),HttpMethod.GET, entity,String.class);
		
		
//		System.out.println("Response : "+response.getBody());
//		assertTrue(response.getBody().contains("\"id\":\"Question1\""));
//		assertTrue(response.getBody().contains("\"description\":\"Largest Country in the world?\""));
		String expected= "{\"id\":\"Question1\",\"description\":\"Largest Country in the world?\",\"correctAnswer\":\"Russia\"}";
		try {
			JSONAssert.assertEquals(expected, response.getBody(), false);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
    
    @Test
	public void testAddQuestion() {
		
		Question question= new Question("DOESN'T MATTER","Largest Country","Russia",Arrays.asList("India","China","Russia","USA"));
		
		
		ResponseEntity<String> response= restTemplate.exchange(createURLWithPort("/surveys/Survey1/questions"),HttpMethod.POST, 
				new HttpEntity<Question>(question,headers),String.class);
		
		//assertThat(response.getHeaders().get(HttpHeaders.LOCATION).get(0),containsString("/surveys/Survey1/questions"));
		
		String actual= response.getHeaders().get(HttpHeaders.LOCATION).get(0);
		System.out.println(actual);
		
		assertTrue(actual.contains("/surveys/Survey1/questions"));
		
    }

	private String createURLWithPort(String uri) {
		return "http://localhost:"+port+uri;
	}
	
	  private String createHttpAuthenticationHeaderValue(String userId, String password) {
			
	    	
	    	String userIdAndPassword = userId + ":" + password;
	    	
	        byte[] encodedAuth = Base64.encode(userIdAndPassword.getBytes(Charset.forName("US-ASCII")));
	        
	        String headerValue= "Basic " + new String (encodedAuth);
	        
	        
	        
			return headerValue;
		}


}
