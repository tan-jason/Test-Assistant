/* 
 * Test.java
 * January 2nd, 2020 
 * Nathan and Jason 
 * Version 1.0
 * Test object which stores all question in current test
 */ 
package com.testassistant; 

import java.util.ArrayList; 

public class Test{ 
    private String testName;
    private ArrayList<Question> questions;
    private ArrayList<KQuestion> kQuestions; 
    private ArrayList<TQuestion> tQuestions; 
    private ArrayList<AQuestion> aQuestions; 
    private ArrayList<CQuestion> cQuestions; 
    
    public Test(String testName, ArrayList<KQuestion> kQuestions, ArrayList<TQuestion> tQuestions,ArrayList<AQuestion> aQuestions,
                    ArrayList<CQuestion> cQuestions){
        this.testName = testName; 
        this.kQuestions = kQuestions;
        this.tQuestions = tQuestions;
        this.aQuestions = aQuestions;
        this.cQuestions = cQuestions;
        this.questions = this.combineQuestions();
    }
    
    private ArrayList<Question> combineQuestions() {
        ArrayList<Question> tempQuestions = new ArrayList<Question>();
        tempQuestions.addAll(this.kQuestions);
        tempQuestions.addAll(this.aQuestions);
        tempQuestions.addAll(this.tQuestions);
        tempQuestions.addAll(this.cQuestions);
        return tempQuestions;
    }
    /*
     * getTestName
     * @param void
     * return String the name of test
     */
    public String getTestName(){ 
        return this.testName; 
    }
    /*
     * setTestName
     * @param String 
     * sets the name of the test to String passed in 
     * return void
     */
    public void setTestName(String testName) { 
    	this.testName = testName;
    }
    /*
     * getQuestions
     * @param void 
     * returns ArrayList of Question which stores all questions
     */
    public ArrayList<Question> getQuestions(){ 
        return this.questions; 
    }
    /*
     * getKQuestions
     * @param void 
     * returns ArrayList of KQuestion which stores all knowledge questions
     */
    public ArrayList<KQuestion> getKQuestions(){ 
        return this.kQuestions; 
    }
    /*
     * getTQuestions
     * @param void 
     * returns ArrayList of TQuestion which stores all thinking questions
     */
    public ArrayList<TQuestion> getTQuestions(){ 
        return this.tQuestions; 
    }
    /*
     * getAQuestions
     * @param void 
     * returns ArrayList of AQuestion which stores all application questions
     */
    public ArrayList<AQuestion> getAQuestions(){ 
        return this.aQuestions; 
    }
    /*
     * getCQuestions
     * @param void 
     * returns ArrayList of CQuestion which stores all communication questions
     */
    public ArrayList<CQuestion> getCQuestions(){ 
        return this.cQuestions; 
    }
    /*
     * editMCText
     * @param Question,String,String 
     * locates the question in QuestionDatabase and changes the choice passed in 
     * as a parameter with the new choice, also passed in as a parameter
     * return boolean whether it could set new text or not
     */
    public boolean editMCText(Question question, String newText, String choiceText){ 
        if(question instanceof KQuestion){ //make this for instance of T,C,A questions also
            for(int questionIndex = 0; questionIndex < kQuestions.size(); questionIndex++){ 
                if(kQuestions.get(questionIndex) == question){ 
                    int index = kQuestions.get(questionIndex).getChoiceIndex(choiceText);
                    kQuestions.get(questionIndex).setChoiceText(index,newText);

                    return true;
                }
            }
        }else if(question instanceof TQuestion) {
            for(int questionIndex = 0; questionIndex < tQuestions.size(); questionIndex++){ 
                if(tQuestions.get(questionIndex) == question){ 
                    int index = tQuestions.get(questionIndex).getChoiceIndex(choiceText);
                    tQuestions.get(questionIndex).setChoiceText(index,newText);
                    return true;
                }
            }
        }else if(question instanceof AQuestion) {
            for(int questionIndex = 0; questionIndex < tQuestions.size(); questionIndex++){ 
                if(aQuestions.get(questionIndex) == question){ 
                    int index = aQuestions.get(questionIndex).getChoiceIndex(choiceText);
                    aQuestions.get(questionIndex).setChoiceText(index,newText);
                    return true;
                }
            }
        }else if(question instanceof CQuestion) {
            for(int questionIndex = 0; questionIndex < tQuestions.size(); questionIndex++){ 
                if(cQuestions.get(questionIndex) == question){ 
                    int index = cQuestions.get(questionIndex).getChoiceIndex(choiceText);
                    cQuestions.get(questionIndex).setChoiceText(index,newText);
                    return true;
                }
            }
        }
        return false;
    }
    /*
     * removeQuestion
     * @param Question
     * removes question passed in from the database
     * return boolean 
     */
    public boolean removeQuestion(Question question){ 
        if(question instanceof KQuestion) { 
            if(questions.remove(question) && kQuestions.remove(question)){ 
                return true;
            }
        }else if(question instanceof TQuestion) {
            if(questions.remove(question) && tQuestions.remove(question)){ 
                return true;
            }
        }else if(question instanceof AQuestion) {
            if(questions.remove(question) && aQuestions.remove(question)){ 
                return true;
            }
        }else if(question instanceof CQuestion) {
            if(questions.remove(question) && cQuestions.remove(question)){ 
                return true;
            }
            
        }
        return false;
    }
    /*
     * addQuestion 
     * @param Question 
     * adds question passed in to the database 
     * return boolean 
     */
    public boolean addQuestion(Question question) {
        if(question instanceof KQuestion) { 
            if(questions.add(question) && kQuestions.add((KQuestion)question)){ 
                return true;
            }
        }else if(question instanceof TQuestion) {
            if(questions.add(question) && tQuestions.add((TQuestion)question)){ 
                return true;
            }
        }else if(question instanceof AQuestion) {
            if(questions.add(question) && aQuestions.add((AQuestion)question)){ 
                return true;
            }
        }else if(question instanceof CQuestion) {
            if(questions.add(question) && cQuestions.add((CQuestion)question)){ 
                return true;
            }
            
        }
        return false;
    }
}