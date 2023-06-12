package com.springboot.surveywebapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;


import com.springboot.surveywebapp.model.Question;
import com.springboot.surveywebapp.model.Survey;

@Component
public class SurveyService {
	
	private static List<Survey> surveys = new ArrayList<>();
	static {
		Question question1 = new Question("Question1","Largest Country in the world?","Russia",Arrays.asList("India","China","Russia","USA"));
		Question question2 = new Question("Question2","Most populus Country in the world?","China",Arrays.asList("India","China","Russia","USA"));
		Question question3 = new Question("Question3","Highest GDP in the world?","USA",Arrays.asList("India","China","Russia","USA"));
		Question question4 = new Question("Question4","Second largest english speaking country?","India",Arrays.asList("India","China","Russia","USA"));
		
	    List<Question> questions=new ArrayList<>(Arrays.asList(question1,question2,question3,question4));
	    
	    Survey survey = new Survey("Survey1","My favourite Survey","Description of the Survey" , questions);
	    surveys.add(survey);
	
	}
	
	public List<Question> retrieveQuestions(String surveyId){
		
		Survey survey = retrieveSurvey(surveyId);
		
		if(survey == null) {
			return null;
		}
		
		return survey.getQuestions();
		
	}
	
	public Survey retrieveSurvey(String surveyId) {
		for(Survey survey: surveys) {
			if(survey.getId().equals(surveyId)) {
				return survey;
			}
		}
		
		return null;
	}
	
	public Question retrieveQuestion(String surveyId,String questionId) {
		Survey survey=retrieveSurvey(surveyId);
		if(survey == null) {
			return null;
		}
		
		for(Question question: survey.getQuestions()) {
			if(question.getId().equals(questionId)) {	
				return question;
			}
		}
		
		return null;
	}
	
	public Question addQuestion(String surveyId, Question question) {
		Survey survey= retrieveSurvey(surveyId);
		if(survey==null) {
			return null;
		}
		
		survey.getQuestions().add(question);
		return question;
	}

}
