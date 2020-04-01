/* 
 * Question.java
 * January 2nd, 2020 
 * Nathan and Jason 
 * Version 1.0
 * Parent question class outline to create any type of question object 
 */ 
package com.testassistant;

import java.util.ArrayList;

public class Question implements Comparable<Question>{ 
    private String text; 
    private int numMarks; 
    private boolean isMCQuestion; 
    private ArrayList<String> choices;
      
//**********************************************************************************
    //constructor for non MC Question 
    public Question(String text, int numMarks){ 
        this.isMCQuestion = false; 
        this.text = text; 
        this.numMarks = numMarks; 
        
    }
    
    //constructor for MC Question 
    public Question(String text, int numMarks, ArrayList<String> choices){ 
        this.isMCQuestion = true; 
        this.text = text; 
        this.numMarks = numMarks; 
        this.choices = choices;
    }
      
//**********************************************************************************
    /*
     * getNumMarks
     * @param void
     * returns the number of marks the question is worth 
     */
    public int getNumMarks(){ 
        return this.numMarks; 
    }
    /*
     * setNumMarks 
     * @param int 
     * takes in an integer and sets the current number of marks as the param 
     * return void 
     */
    public void setNumMarks(int newMark) {
        this.numMarks = newMark;
    }
    /*
     * isMultipleChoice 
     * @param void
     * returns the boolean of whether the current question is multiple choice or not 
     */
    public boolean isMultipleChoice(){ 
        return this.isMCQuestion; 
    }
    /*
     * getMultipleChoiceLetter
     * @param void 
     * checks if the current question is a multiple choice question and returns M is it is 
     * and nothing if it isn't 
     * return String 
     */
    public String getMultipleChoiceLetter() {
        if(this.isMCQuestion) {
            return "M";
        }else{
            return " ";
        }
    }
    /*
     * getText
     * @param void
     * returns the question text
     * return String 
     */
    public String getText(){ 
        return this.text; 
    }
    /*
     * getChoices 
     * @param void 
     * returns the ArrayList<String> of choices 
     */
    public ArrayList<String> getChoices(){ 
        return this.choices; 
    }
    /*
     * getChoiceIndex
     * @param String 
     * returns the index of the text in the param 
     */
    public int getChoiceIndex(String choiceText){
        for(int choiceNum = 0; choiceNum < choices.size(); choiceNum++){ 
            if(choices.get(choiceNum).equals(choiceText)){ 
                return choiceNum; 
            }
        }
        return -1;
    }
    /*
     * removeChoice
     * @param int 
     * removes the choice at index in param in ArrayList<String> choices array list
     * return void 
     */
    public void removeChoice(int index){ 
        this.choices.remove(index);
    }
    /*
     * getChoice 
     * @param int 
     * returns the choice at index in param in ArrayList<String> choices array list
     * return String
     */
    public String getChoice(int index){ 
        return this.choices.get(index);
    }
    /*
     * setChoiceText
     * @param int, String 
     * sets the text of a choice at index in ArrayList<String> choices array list
     * return void
     */
    public void setChoiceText(int index, String newText){ 
        this.choices.set(index,newText); 
    }
    /*
     * setText
     * @param String 
     * sets the text of current to newText as passed in as param
     * return void
     */
    public void setText(String newText) {
        this.text = newText;
    }
    /*
     * returnMCChoices 
     * @param void 
     * returns all choices in ArrayList<String> choices array list as a single String 
     * return String 
     */
    public String returnMCChoices(){
        String mcText = "";
        if(this.isMCQuestion == true){ 
            for(int questionNumber = 0; questionNumber < this.choices.size(); questionNumber++) {
                mcText = mcText + (questionNumber+1) + ". " + choices.get(questionNumber) + "<br/>";
            }
        }
        return mcText; 
    }
    /*
     * pdfFormat
     * @param int 
     * changes the format of question so it will display accordingly on the pdf 
     * return String 
     */
    public String pdfFormat(int questionNum) {
    	if(this.isMCQuestion) {
    		final char A_ASCII_VALUE = 97;
    		String choiceString = "\n";
    		for(int choice = 0; choice < choices.size();choice++) {
    			choiceString += Character.toString((char) (A_ASCII_VALUE + choice)) + ") " + choices.get(choice) + "\n";
    		}
    		if(this instanceof KQuestion) { 
    			return questionNum + ". " + this.text + " (K:" + this.numMarks + ")" + choiceString;
    		}else if(this instanceof CQuestion) { 
    			return questionNum + ". " + this.text + " (C:" + this.numMarks + ")" + choiceString;
    		}else if(this instanceof AQuestion) { 
    			return questionNum + ". " + this.text + " (A:" + this.numMarks + ")" + choiceString;
    		}else if(this instanceof TQuestion) { 
    			return questionNum + ". " + this.text + " (T:" + this.numMarks + ")" + choiceString;
    		}else { 
    			return "";
    		}
    	}else {
    		if(this instanceof KQuestion) { 
    			return "\n" + questionNum + ". " + this.text + " (K:" + this.numMarks + ")" + "\n\n";
    		}else if(this instanceof CQuestion) { 
    			return "\n" + questionNum + ". " + this.text + " (C:" + this.numMarks + ")" + "\n\n";
    		}else if(this instanceof AQuestion) { 
    			return "\n" + questionNum + ". " + this.text + " (A:" + this.numMarks + ")" + "\n\n";
    		}else if(this instanceof TQuestion) { 
    			return "\n" + questionNum + ". " + this.text + " (T:" + this.numMarks + ")" + "\n\n";
    		}else { 
    			return "";
    		}
    	}
    }
    /*
     * toString 
     * @param void 
     * override the toString method 
     * return String 
     */
    @Override
    public String toString() {
        if(this.isMCQuestion) {
            String returnText = "(";
            for(String s : choices) {
                returnText += s + ",";
            }
            returnText = returnText.substring(0,returnText.length()-1);
            returnText += ")";
            return "[" + this.text + "]"  + returnText;
        }

        return "[" + this.text + "]";
    }
    /*
     * compareTo
     * @param Question 
     * overrides the compareTo method when sorting 
     * return int 
     */
    @Override 
    public int compareTo(Question other){ 
        if(this.numMarks > other.numMarks){ 
            return 1;
        }else if  (this.numMarks < other.numMarks){ 
            return -1; 
        }else{
            return 0; 
        }
    }
          
//**********************************************************************************
}