package com.springboot.surveywebapp.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.surveywebapp.model.Question;
import com.springboot.surveywebapp.service.SurveyService;

@RestController
public class SurveyController {
	
	@Autowired
	private SurveyService surveyService;
	
	
	//GET
	
	@RequestMapping(method = RequestMethod.GET, value="surveys/{surveyId}/questions")
	public List<Question> retrieveQuestions(@PathVariable String surveyId){
		return surveyService.retrieveQuestions(surveyId);
	}
	
	//GET
	@GetMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question retrieveQuestion(@PathVariable String surveyId,@PathVariable String questionId) {
		return surveyService.retrieveQuestion(surveyId,questionId);
	}
	
	//POST
	
	@RequestMapping(method = RequestMethod.POST, value="surveys/{surveyId}/questions")
	public ResponseEntity<Void> addQuestion(@PathVariable String surveyId, @RequestBody Question newQuestion){
		 //ResponseEntity<Question>
		
		Question question= surveyService.addQuestion(surveyId,newQuestion);
		
		
		URI locationUri= ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(question.getId())
				.toUri();
		return ResponseEntity.created(locationUri).build();
		//return ResponseEntity.ok(question);
	}


}
