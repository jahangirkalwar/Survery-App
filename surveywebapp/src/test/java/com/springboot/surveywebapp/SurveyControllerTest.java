package com.springboot.surveywebapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.springboot.surveywebapp.controller.SurveyController;
import com.springboot.surveywebapp.model.Question;
import com.springboot.surveywebapp.service.SurveyService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SurveyController.class)
@AutoConfigureMockMvc(addFilters = false)  //disabling authorization to get succeed.
class SurveyControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SurveyService surveyService; 
	
	@Test
	public void retrieveDetailsForQuestion() throws Exception {
	
		Question mockQuestion = new Question("Question1",
				"Largest Country in the world?","Russia",Arrays.asList("India",
						"China","Russia","USA"));
		
		Mockito.when(surveyService.retrieveQuestion(Mockito.anyString(),Mockito.anyString())).thenReturn(mockQuestion);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/surveys/Survey1/questions/Question1}").accept(
				MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected= "{\"id\":\"Question1\",\"description\":\"Largest Country in the world?\",\"correctAnswer\":\"Russia\"}";
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		
		//expect this response
	}
	
	@Test
	public void retrieveSurveyQuestions() throws Exception {
		List<Question> mockList= Arrays.asList(
				new Question("Question1","First Alphabet","A",Arrays.asList("A","B","C","D")),
				new Question("Question2","Last Alphabet","Z",Arrays.asList("W","X","Y","Z"))
				);
		when(surveyService.retrieveQuestions(anyString())).thenReturn(mockList);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get("/surveys/Survey1/questions")
					.accept(MediaType.APPLICATION_JSON))
		
				.andExpect(status().isOk()).andReturn();
		
		String expected= "["
				+ "{\"id\":\"Question1\",\"description\":\"First Alphabet\",\"correctAnswer\":\"A\",\"options\":[\"A\",\"B\",\"C\",\"D\"]},"
				+ "{\"id\":\"Question2\",\"description\":\"Last Alphabet\",\"correctAnswer\":\"Z\",\"options\":[\"W\",\"X\",\"Y\",\"Z\"]}"
				+ "]";
		
		JSONAssert.assertEquals(expected,result.getResponse().getContentAsString(), false);
		
		
	}
	
	@Test
	public void createSurveyQuestion() throws Exception {
		
		Question mockQuestion = new Question("1", "Smallest Number", "1",
				Arrays.asList("1", "2", "3", "4"));

		String questionJson = "{\"description\":\"Smallest Number\",\"correctAnswer\":\"1\",\"options\":[\"1\",\"2\",\"3\",\"4\"]}";
		//surveyService.addQuestion to respond back with mockQuestion
		Mockito.when(
				surveyService.addQuestion(Mockito.anyString(), Mockito
						.any(Question.class))).thenReturn(mockQuestion);

		//Send question as body to /surveys/Survey1/questions
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/surveys/Survey1/questions")
				.accept(MediaType.APPLICATION_JSON).content(questionJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals("http://localhost/surveys/Survey1/questions/1", response.getHeader(org.springframework.http.HttpHeaders.LOCATION)); 

	}
	
	
}
