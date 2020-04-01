package com.testassistant;
/* 
 * DatabaseManager.java
 * 01/02/2020
 * @Jason and Nathan 
 * Version 1.0
 * Class object which manages the question database
 */
import javax.swing.JOptionPane;
import java.io.PrintWriter;
import java.io.File;


public class DatabaseManager{ 
    QuestionDatabase qb; 
    
    //read a file and create a database
    public DatabaseManager() throws Exception { 
        this.createDatabase();
    }
    
    //editing a database
    public DatabaseManager(QuestionDatabase qb) throws Exception{ 
        this.qb = qb;
    }
    /*
     *getDatabase 
     *@param void
     *returns the QuestionDatabase 
     */
    public QuestionDatabase getDatabase(){ 
        return this.qb; 
    }
    /*
     * addQuestion
     * @param Question 
     * returns a boolean true or false depending on if the questiondatabase could add the question or not
     */
    public boolean addQuestion(Question question) {
        return qb.addQuestion(question);
    }
    /*
     * removeQuestion
     * @param Question 
     * returns a boolean true or false depending on if the questiondatabase could remove the question or not
     */
    public boolean removeQuestion(Question question){ 
        return qb.removeQuestion(question);
    }
    /*
     * editText
     * @param String,Question 
     * returns a boolean true or false depending on if the questiondatabase could edit the question or not
     */
    public boolean editText(String text,Question question) {
        return this.qb.setText(text,question);
    }
    /*
     * saveFiles
     * @param void 
     * saves the files of current database to an exisiting or new directory 
     * return void
     */
    public void saveFiles() throws Exception {
        this.outputFiles(this.qb);
    }
    /*
     * saveFiles 
     * @param String 
     * saveFiles to original database 
     * return void
     */
    public void saveFiles(String databaseName) throws Exception {
        this.outputFiles(databaseName);
    	JOptionPane.showMessageDialog(null, "Changes saved to original database: " + this.getDatabase().getDatabaseName());

    }
    /*
     * createDatabase 
     * @param void 
     * creates a database using data in the QuestionInput.txt file and then removes 
     * all data from the file
     * return void
     */
    public void createDatabase() throws Exception {
        //creating question database
        String path = System.getProperty("user.dir");
        path = path.substring(0,path.lastIndexOf("\\")) + "/Questions/QuestionInput.txt";
        File inputFile = new File(path);
        QuestionDatabase qb = new QuestionDatabase(inputFile);
        //creating new directory with questions from question database 
        this.outputFiles(qb);
        //removing content of QuestionInput.txt
        PrintWriter clearFile = new PrintWriter(inputFile);
        clearFile.print("");
        clearFile.close();
    }
    /*
     * outputFiles
     * @param QuestionDatabase 
     * outputs data from QuestionDatabase into an existing or new directory 
     */
     public void outputFiles(QuestionDatabase qb) throws Exception{ 
        String databaseName = JOptionPane.showInputDialog(null,"What is the name of the exisiting or new directory you wish to save data to?"); 
         
        //creating new directory with database of questions 
        String path = System.getProperty("user.dir");  
        File databaseFolder = new File(path +"/Questions/Databases"); 
        databaseFolder.mkdir(); 
        path = path.substring(0,path.lastIndexOf("\\")) + "/Questions/Databases/"; 
 
        File newFile = new File(path + databaseName); 
        newFile.mkdirs(); 
        //create files  
        PrintWriter knowledgeWriter = new PrintWriter(path + databaseName + "/Knowledge Questions.txt"); 
        PrintWriter thinkingWriter = new PrintWriter(path + databaseName + "/Thinking Questions.txt"); 
        PrintWriter appWriter = new PrintWriter(path + databaseName + "/Application Questions.txt"); 
        PrintWriter commWriter = new PrintWriter(path + databaseName + "/Communication Questions.txt"); 
        File copy = new File(path + databaseName + "/Unit Questions.txt"); 
        PrintWriter allQuestionsWriter = new PrintWriter(copy); 
        for(int i = 0;i < qb.getKQuestions().size();i++) { 
            knowledgeWriter.println((i+1)+". "+qb.getKQuestions().get(i)); 
        } 
        for(int i = 0;i < qb.getTQuestions().size();i++) { 
            thinkingWriter.println((i+1)+". "+ qb.getTQuestions().get(i)); 
        } 
        for(int i = 0;i < qb.getAQuestions().size();i++) { 
            appWriter.println((i+1)+". "+ qb.getAQuestions().get(i)); 
        } 
        for(int i = 0;i < qb.getCQuestions().size();i++) { 
            commWriter.println((i+1)+". "+ qb.getCQuestions().get(i)); 
        } 
        for(int i = 0;i < qb.getQuestions().size();i++) {
            Question currentQuestion = qb.getQuestions().get(i);
            if(currentQuestion instanceof KQuestion) {
                allQuestionsWriter.println("K" + currentQuestion.getNumMarks() + currentQuestion.getMultipleChoiceLetter() + currentQuestion.toString());
            }else if(currentQuestion instanceof TQuestion) {
                allQuestionsWriter.println("T" + currentQuestion.getNumMarks() + currentQuestion.getMultipleChoiceLetter() + currentQuestion.toString());
            }else if(currentQuestion instanceof CQuestion) {
                allQuestionsWriter.println("C" + currentQuestion.getNumMarks() + currentQuestion.getMultipleChoiceLetter() + currentQuestion.toString());
            }else if(currentQuestion instanceof AQuestion) {
                allQuestionsWriter.println("A" + currentQuestion.getNumMarks() + currentQuestion.getMultipleChoiceLetter() + currentQuestion.toString());
            }
        }
        knowledgeWriter.close(); 
        thinkingWriter.close(); 
        appWriter.close(); 
        commWriter.close(); 
        allQuestionsWriter.close(); 
    } 
     /*
      * outputFiles
      * @param String 
      * outputs data from QuestionDatabase into pre-existing database
      */
     public void outputFiles(String databaseName) throws Exception{  
         //creating new directory with database of questions 
         String path = System.getProperty("user.dir");  
         File databaseFolder = new File(path +"/Questions/Databases"); 
         databaseFolder.mkdir(); 
         path = path.substring(0,path.lastIndexOf("\\")) + "/Questions/Databases/"; 
         File newFile = new File(path + databaseName); 
         newFile.mkdirs(); 
         //create files  
         PrintWriter knowledgeWriter = new PrintWriter(path + databaseName + "/Knowledge Questions.txt"); 
         PrintWriter thinkingWriter = new PrintWriter(path + databaseName + "/Thinking Questions.txt"); 
         PrintWriter appWriter = new PrintWriter(path + databaseName + "/Application Questions.txt"); 
         PrintWriter commWriter = new PrintWriter(path + databaseName + "/Communication Questions.txt"); 
         File copy = new File(path + databaseName + "/Unit Questions.txt"); 
         PrintWriter allQuestionsWriter = new PrintWriter(copy); 
         for(int i = 0;i < qb.getKQuestions().size();i++) { 
             knowledgeWriter.println((i+1)+". "+qb.getKQuestions().get(i)); 
         } 
         for(int i = 0;i < qb.getTQuestions().size();i++) { 
             thinkingWriter.println((i+1)+". "+ qb.getTQuestions().get(i)); 
         } 
         for(int i = 0;i < qb.getAQuestions().size();i++) { 
             appWriter.println((i+1)+". "+ qb.getAQuestions().get(i)); 
         } 
         for(int i = 0;i < qb.getCQuestions().size();i++) { 
             commWriter.println((i+1)+". "+ qb.getCQuestions().get(i)); 
         } 
         for(int i = 0;i < qb.getQuestions().size();i++) {
             Question currentQuestion = qb.getQuestions().get(i);
             if(currentQuestion instanceof KQuestion) {
                 allQuestionsWriter.println("K" + currentQuestion.getNumMarks() + currentQuestion.getMultipleChoiceLetter() + currentQuestion.toString());
             }else if(currentQuestion instanceof TQuestion) {
                 allQuestionsWriter.println("T" + currentQuestion.getNumMarks() + currentQuestion.getMultipleChoiceLetter() + currentQuestion.toString());
             }else if(currentQuestion instanceof CQuestion) {
                 allQuestionsWriter.println("C" + currentQuestion.getNumMarks() + currentQuestion.getMultipleChoiceLetter() + currentQuestion.toString());
             }else if(currentQuestion instanceof AQuestion) {
                 allQuestionsWriter.println("A" + currentQuestion.getNumMarks() + currentQuestion.getMultipleChoiceLetter() + currentQuestion.toString());
             }
         }
         knowledgeWriter.close(); 
         thinkingWriter.close(); 
         appWriter.close(); 
         commWriter.close(); 
         allQuestionsWriter.close(); 
          
     } 
     /*
      * editMultipleChoiceText 
      * @param Question,String,String 
      * returns a boolean: true or false if the QuestionDatabase can edit the text of a choice or not  
      */
     public boolean editMultipleChoiceText(Question question, String newText, String choiceText){ 
         return this.qb.editMCText(question,newText,choiceText);
     }
}
