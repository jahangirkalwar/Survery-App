package com.springboot.surveywebapp.model;

import java.util.List;
import java.util.Objects;

public class Question {
	
private String id;
	
	private String description;
	private String correctAnswer;
	private List<String> options;
	
	
	public Question() {
		
	}
	
	public Question(String id, String description,String correctAnswer, List<String> options) {
		super();
		this.id=id;
		this.correctAnswer=correctAnswer;
		this.description=description;
		this.options=options;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		return Objects.equals(id, other.id);
	}
	
	

}
