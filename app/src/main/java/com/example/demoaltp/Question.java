package com.example.demoaltp;

public class Question {
	private String questionID, level, caseA, caseB, caseC, caseD,result;

	public Question(String questionID, String level, String caseA,
			String caseB, String caseC, String caseD) {
		super();
		this.questionID = questionID;
		this.level = level;
		this.caseA = caseA;
		this.caseB = caseB;
		this.caseC = caseC;
		this.caseD = caseD;
	}
	public Question(){

	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public String getQuestionID() {
		return questionID;
	}

	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCaseA() {
		return caseA;
	}

	public void setCaseA(String caseA) {
		this.caseA = caseA;
	}

	public String getCaseB() {
		return caseB;
	}

	public void setCaseB(String caseB) {
		this.caseB = caseB;
	}

	public String getCaseC() {
		return caseC;
	}

	public void setCaseC(String caseC) {
		this.caseC = caseC;
	}

	public String getCaseD() {
		return caseD;
	}

	public void setCaseD(String caseD) {
		this.caseD = caseD;
	}
	
}
