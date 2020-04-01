/*
 * TestManager.java
 * Version 1.0
 * @Jason and Nathan 
 * 01/13/2020
 */
package com.testassistant; 

import javax.swing.*;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;

import java.util.ArrayList;
import java.io.IOException;
import java.lang.Math;

public class TestManager { 
    private QuestionDatabase qb;
    private Test test;
    
    TestManager(QuestionDatabase qb) {
        this.qb = qb;
        this.collectNumTestQuestions();
    }
    /*
     * editMutlipleChoiceText 
     * @param Question,String,String 
     * calls editMCText method in Test 
     * return boolean whether the test could edit the choice text in test 
     */
    public boolean editMultipleChoiceText(Question question, String newText, String choiceText){ 
         return this.test.editMCText(question,newText,choiceText);
     }
    /*
     * regenerateTest 
     * @param void 
     * regenerates a test by replacing current test with a new test by creating a new test
     * with this test's current parameters 
     * return void
     */
    public void regenerateTest() {
        this.test = this.createTest(this.qb,this.test.getKQuestions().size(),this.test.getAQuestions().size(),this.test.getTQuestions().size(),this.test.getCQuestions().size());
    }
    /*
     * collectNumTestQuestions 
     * @param void 
     * collects user input for the number of questions of each type they wish to contain 
     * return void 
     */
    private void collectNumTestQuestions() {
        int numKnowledge;
        int numThinking;
        int numApp;
        int numComm;
        do {
            numKnowledge = Integer.parseInt(JOptionPane.showInputDialog(null,"How many knowledge questions would you like to contain?"));
        }while(numKnowledge > qb.getKQuestions().size());
        do {
            numThinking = Integer.parseInt(JOptionPane.showInputDialog(null,"How many thinking questions would you like to contain?"));
        }while(numThinking > qb.getTQuestions().size());
        do {
            numApp = Integer.parseInt(JOptionPane.showInputDialog(null,"How many application questions would you like to contain?"));
        }while(numApp > qb.getAQuestions().size());
        do {
            numComm = Integer.parseInt(JOptionPane.showInputDialog(null,"How many communication questions would you like to contain?"));
        }while(numComm > qb.getCQuestions().size());
        this.test = createTest(qb,numKnowledge,numThinking,numApp,numComm);
    }
    /*
     * countKMarks 
     * @param void 
     * counts total number of marks for knowledge section 
     * return int 
     */
    public int countKMarks() { 
    	int total = 0; 
    	for(int question = 0; question < test.getKQuestions().size(); question++) { 
    		total = total + test.getKQuestions().get(question).getNumMarks(); 
    	}
    	return total;
    	
    }
    /*
     * countTMarks 
     * @param void 
     * counts total number of marks for thinking section 
     * return int 
     */
    public int countTMarks() { 
    	int total = 0; 
    	for(int question = 0; question < test.getTQuestions().size(); question++) { 
    		total = total + test.getTQuestions().get(question).getNumMarks(); 
    	}
    	return total;
    }
    /*
     * countAMarks 
     * @param void 
     * counts total number of marks for application section 
     * return int 
     */
    public int countAMarks() { 
    	int total = 0; 
    	for(int question = 0; question < test.getAQuestions().size(); question++) { 
    		total = total + test.getAQuestions().get(question).getNumMarks(); 
    	}
    	return total;
    }
    /*
     * countCMarks 
     * @param void 
     * counts total number of marks for communication section 
     * return int 
     */
    public int countCMarks() { 
    	int total = 0; 
    	for(int question = 0; question < test.getCQuestions().size(); question++) { 
    		total = total + test.getCQuestions().get(question).getNumMarks(); 
    	}
    	return total;
    }
    /*
     * createTest 
     * @param QuestionDatabase,int,int,int,int
     * creates a test by randomly picking x number of questions for each category 
     * return Test 
     */
    private Test createTest(QuestionDatabase qb,int numK,int numT,int numA,int numC) {
        Test newTest;
        ArrayList<KQuestion> kTestQuestions = new ArrayList<KQuestion>();
        ArrayList<TQuestion> tTestQuestions = new ArrayList<TQuestion>();
        ArrayList<AQuestion> aTestQuestions = new ArrayList<AQuestion>();
        ArrayList<CQuestion> cTestQuestions = new ArrayList<CQuestion>();
        if(numK > 0) {
            for(int question = 0;question < numK;question++) {
                ArrayList<KQuestion> kQBQuestions = new ArrayList<KQuestion>();
                for(int qbQuestion = 0;qbQuestion < qb.getKQuestions().size();qbQuestion++) {
                    kQBQuestions.add(qb.getKQuestions().get(qbQuestion));
                }
                kTestQuestions.add(kQBQuestions.remove((int)(Math.random()*kQBQuestions.size())));
            }
        }
        if(numT > 0) {
            for(int question = 0;question < numT;question++) {
                ArrayList<TQuestion> tQBQuestions = new ArrayList<TQuestion>();
                for(int qbQuestion = 0;qbQuestion < qb.getTQuestions().size();qbQuestion++) {
                    tQBQuestions.add(qb.getTQuestions().get(qbQuestion));
                }
                tTestQuestions.add(tQBQuestions.remove((int)(Math.random()*tQBQuestions.size())));
            }
        }
        if(numA > 0) {
            for(int question = 0;question < numA;question++) {
                ArrayList<AQuestion> aQBQuestions = new ArrayList<AQuestion>();
                for(int qbQuestion = 0;qbQuestion < qb.getAQuestions().size();qbQuestion++) {
                    aQBQuestions.add(qb.getAQuestions().get(qbQuestion));
                }
            aTestQuestions.add(aQBQuestions.remove((int)(Math.random()*aQBQuestions.size())));
            }
        }
        
        if(numC > 0) {
            for(int question = 0;question < numC;question++) {
                ArrayList<CQuestion> cQBQuestions = new ArrayList<CQuestion>();
                for(int qbQuestion = 0;qbQuestion < qb.getCQuestions().size();qbQuestion++) {
                    cQBQuestions.add(qb.getCQuestions().get(question));
                }
                cTestQuestions.add(cQBQuestions.remove((int)(Math.random()*cQBQuestions.size())));
            }
        }
        String testName = JOptionPane.showInputDialog(null,"What would you like the name of your test to be?"); 
        newTest = new Test(testName,kTestQuestions,tTestQuestions,aTestQuestions,cTestQuestions);
        return newTest;
    }
    /*
     * exportToPDF 
     * @param void 
     * exports the test into a PDF 
     */
    public boolean exportToPDF() throws IOException { 
		if(this.test.getTestName() == null) {
			String newTestName = null;
			while(newTestName == null) {
				newTestName = JOptionPane.showInputDialog(null,"Your test has no name, what would you like to name your test");
			}
			this.test.setTestName(newTestName);
		}
    	String path = System.getProperty("user.dir");
    	path =  path.substring(0,path.lastIndexOf("\\")) + "/Tests/" + this.test.getTestName() + ".pdf";
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(path));
		//fonts
		PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
		Document document = new Document(pdfDoc); 
		//mark table
		int markCells = 0; 
		int numKMarks = countKMarks(); 
		int numTMarks = countTMarks(); 
		int numAMarks = countAMarks(); 
		int numCMarks = countCMarks(); 
		if(numKMarks > 0) { 
			markCells++;
		}
		if(numTMarks > 0) { 
			markCells++;
		}
		if(numAMarks > 0) { 
			markCells++;
		}
		if(numCMarks > 0) { 
			markCells++;
		}
		Table table = new Table(markCells);
		if(numKMarks > 0) { 
			table.addCell("K:   /" +numKMarks ); 
		}
		if(numTMarks > 0) { 
			table.addCell("T:   /" +numTMarks ); 
		}
		if(numAMarks > 0) { 
			table.addCell("A:   /" +numAMarks ); 
		}
		if(numCMarks > 0) { 
			table.addCell("C:   /" +numCMarks ); 
		}
		document.add(table);
		//test header
		Text testName = new Text(this.test.getTestName()).setFont(bold);
		Paragraph p = new Paragraph(testName);
		p.setFontSize(25);
		p.setTextAlignment(TextAlignment.CENTER);
		Paragraph newP = new Paragraph("Name:"); 
		newP.setFontSize(13);
		newP.setTextAlignment(TextAlignment.LEFT); 
		document.add(newP); 
		document.add(p);
		int questionCounter = 1;
		Text kSection = new Text("Knowledge Section -" + this.test.getKQuestions().size() + " Questions");
		Paragraph kParagraph = new Paragraph(kSection);
		ArrayList<KQuestion> kQuestionsMC = new ArrayList<KQuestion>();
		ArrayList<KQuestion> kQuestions = new ArrayList<KQuestion>();
		for(KQuestion kQ : this.test.getKQuestions()) {
			if(kQ.isMultipleChoice()) {
				kQuestionsMC.add(kQ);
			}else {
				kQuestions.add(kQ);
			}
		}
		kParagraph.setFontSize(20);
		kParagraph.setTextAlignment(TextAlignment.LEFT);
		document.add(kParagraph);
        for(int question = 0; question < kQuestionsMC.size(); question++) {
            Paragraph kQuestion = new Paragraph(kQuestionsMC.get(question).pdfFormat(questionCounter));
            questionCounter++;
            document.add(kQuestion);
        }
        for(int question = 0; question < kQuestions.size(); question++) {
            Paragraph kQuestion = new Paragraph(kQuestions.get(question).pdfFormat(questionCounter));
            questionCounter++;
            document.add(kQuestion);
        }
        
        Text tSection = new Text("Thinking Section -" + this.test.getTQuestions().size() + " Questions");
		Paragraph tParagraph = new Paragraph(tSection);
		ArrayList<TQuestion> tQuestionsMC = new ArrayList<TQuestion>();
		ArrayList<TQuestion> tQuestions = new ArrayList<TQuestion>();
		for(TQuestion tQ : this.test.getTQuestions()) {
			if(tQ.isMultipleChoice()) {
				tQuestionsMC.add(tQ);
			}else {
				tQuestions.add(tQ);
			}
		}
		tParagraph.setFontSize(20);
		tParagraph.setTextAlignment(TextAlignment.LEFT);
		document.add(tParagraph);
		for(int question = 0; question < tQuestionsMC.size(); question++) {
            Paragraph tQuestion = new Paragraph(tQuestionsMC.get(question).pdfFormat(questionCounter));
            questionCounter++;
            document.add(tQuestion);
        }
        for(int question = 0; question < tQuestions.size(); question++) {
            Paragraph tQuestion = new Paragraph(tQuestions.get(question).pdfFormat(questionCounter));
            questionCounter++;
            document.add(tQuestion);
        }
        Text aSection = new Text("Application Section -" + this.test.getAQuestions().size() + " Questions");
		Paragraph aParagraph = new Paragraph(aSection);
		ArrayList<AQuestion> aQuestionsMC = new ArrayList<AQuestion>();
		ArrayList<AQuestion> aQuestions = new ArrayList<AQuestion>();
		for(AQuestion aQ : this.test.getAQuestions()) {
			if(aQ.isMultipleChoice()) {
				aQuestionsMC.add(aQ);
			}else {
				aQuestions.add(aQ);
			}
		}
		aParagraph.setFontSize(20);
		aParagraph.setTextAlignment(TextAlignment.LEFT);
		document.add(aParagraph);
		for(int question = 0; question < aQuestionsMC.size(); question++) {
            Paragraph aQuestion = new Paragraph(aQuestionsMC.get(question).pdfFormat(questionCounter));
            questionCounter++;
            document.add(aQuestion);
        }
        for(int question = 0; question < aQuestions.size(); question++) {
            Paragraph aQuestion = new Paragraph(aQuestions.get(question).pdfFormat(questionCounter));
            questionCounter++;
            document.add(aQuestion);
        }
        Text cSection = new Text("Communication Section -" + this.test.getCQuestions().size() + " Questions");
		Paragraph cParagraph = new Paragraph(cSection);
		ArrayList<CQuestion> cQuestionsMC = new ArrayList<CQuestion>();
		ArrayList<CQuestion> cQuestions = new ArrayList<CQuestion>();
		for(CQuestion cQ : this.test.getCQuestions()) {
			if(cQ.isMultipleChoice()) {
				cQuestionsMC.add(cQ);
			}else {
				cQuestions.add(cQ);
			}
		}
		cParagraph.setFontSize(20);
		cParagraph.setTextAlignment(TextAlignment.LEFT);
		document.add(cParagraph);
		for(int question = 0; question < cQuestionsMC.size(); question++) {
            Paragraph cQuestion = new Paragraph(cQuestionsMC.get(question).pdfFormat(questionCounter));
            questionCounter++;
            document.add(cQuestion);
        }
        for(int question = 0; question < cQuestions.size(); question++) {
            Paragraph cQuestion = new Paragraph(cQuestions.get(question).pdfFormat(questionCounter));
            questionCounter++;
            document.add(cQuestion);
        }
		document.close();
		return true;
    }
    /*
     * getDatabase
     * @param void 
     * return QuestionDatabase qb
     */
    public QuestionDatabase getDatabase() {
        return this.qb;
    }
    /*
     * getTest 
     * @param void 
     * return Test test
     */
    public Test getTest() {
        return this.test;
    }
    /*
     * editText 
     * @param String,Question 
     * calls editText method in Questiondatabase to edit text of question 
     * return boolean if the database could edit text or not
     */
    public boolean editText(String text,Question question) {
        return this.qb.setText(text,question);
    }
    /*
     * addQuestion
     * @param Question 
     * adds desired question to test 
     * return boolean depending on if test could add question or not
     */
    public boolean addQuestion(Question question) {
        return this.test.addQuestion(question);
    }
    /*
     * removeQuestion 
     * @param Question
     * removes desired question from test
     * return boolean depending on if test could remove question or not 
     */
    public boolean removeQuestion(Question question) {
        return this.test.removeQuestion(question);
    }    
}