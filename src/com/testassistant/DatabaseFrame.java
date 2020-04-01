	package com.testassistant;
	/* 
	 * DatabaseFrame.java
	 * Nathan and Jason
	 * January 13th
	 * Version 1.0
	 */ 
	
	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.*;
	import java.util.ArrayList;
	
	public class DatabaseFrame extends JFrame{
	    private DatabaseManager dm; 
	    private DatabasePanel databasePanel;
	    private KQuestionPanel kPanel; 
	    private TQuestionPanel tPanel; 
	    private CQuestionPanel cPanel;
	    private AQuestionPanel aPanel; 
	    private FramePanel fPanel;
	    private DatabaseFrame thisFrame; 
	    
	    public DatabaseFrame(DatabaseManager dm){ 
	        super("Database Frame"); 
	        this.dm = dm; 
	        this.setSize(1200,800); 
	        this.setResizable(true);
	        this.setBackground(new Color(255,255,255));
	        this.databasePanel = new DatabasePanel(); 
	        this.kPanel = new KQuestionPanel(); 
	        this.tPanel = new TQuestionPanel(); 
	        this.cPanel = new CQuestionPanel(); 
	        this.aPanel = new AQuestionPanel();
	        this.fPanel = new FramePanel();
	        databasePanel.setLayout(new GridLayout(1,4));
	        databasePanel.add(new JScrollPane(kPanel));
	        databasePanel.add(new JScrollPane(tPanel));
	        databasePanel.add(new JScrollPane(cPanel));
	        databasePanel.add(new JScrollPane(aPanel));
	        this.add(databasePanel,BorderLayout.CENTER);
	        this.add(fPanel,BorderLayout.SOUTH);
	        this.setVisible(true);
	        this.thisFrame = this;
	    }
	    /*
	     * class QuestionListener
	     * Version 1.0
	     * Jason and Nathan 
	     * each question contains an actionlistener which, when clicked allows
	     * the user to perform certain actions 
	     */
	    public class QuestionListener implements ActionListener{ 
	        Question question;  
	        JButton button;
	        JLabel label;
	        JPanel currentPanel;
	        
	        QuestionListener(Question question,JButton button,JPanel currentPanel){  
	            this.question = question; 
	            this.button = button;
	            this.currentPanel = currentPanel;
	        } 
	        QuestionListener(Question question,JButton button,JLabel label,JPanel currentPanel){  
	            this.question = question; 
	            this.button = button;
	            this.label = label;
	            this.currentPanel = currentPanel;
	        } 
	        String [] choices = {"EDIT TEXT", "EDIT MARK" ,"REMOVE", "EXIT"}; 
	        final int EDIT_TEXT = 0;
	        final int EDIT_MARK = 1;
	        final int REMOVE = 2; 
	        final int EXIT = 3; 
	        /*
	         * actionPerformed
	         * @param ActionEvent 
	         * implemented from Action Listener interface: allows the user to perform the following actions 
	         * 1. Edit Text of question 
	         * 2. Edit the number of marks the question is worth 
	         * 3. Remove the question from the database 
	         * 4. exit
	         * return void 
	         */
	        public void actionPerformed(ActionEvent event){  
	            int choice = JOptionPane.showOptionDialog(null, "What would you like to do?", "Edit", 
	                                                      JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,choices, null); 
	            
	            if(choice == REMOVE){
	                if(this.question.isMultipleChoice()){ 
	                    currentPanel.remove(label);
	                }
	                dm.removeQuestion(question);
	                currentPanel.remove(button);
	                currentPanel.revalidate();
	                currentPanel.repaint();
	            }else if(choice == EDIT_TEXT){  
	                if(this.question.isMultipleChoice() == false){ 
	                    String newText = (String)(JOptionPane.showInputDialog("Edit question",button.getText()));
	                    if(newText != null) {
	                        if(dm.editText(newText,question)) {
	                            button.setText(newText);
	                        }
	                    }
	                }else if(this.question.isMultipleChoice() == true){ 
	                    ArrayList<String> editingOptionsList = question.getChoices(); 
	                    editingOptionsList.add(0,question.getText());
	                    String [] editingOptionsArray = editingOptionsList.toArray(new String[0]);
	                    String userInput = (String)JOptionPane.showInputDialog(null,"","",JOptionPane.QUESTION_MESSAGE,null,editingOptionsArray,editingOptionsArray[0]);
	                    if(this.question.getText().equals(userInput)){ 
	                        String newText = (String)(JOptionPane.showInputDialog("Edit question",button.getText()));
	                        if(newText != null) {
	                            if(dm.editText(newText,question)) {
	                                button.setText(newText);
	                                dm.getDatabase().getKQuestions();
	                                this.question.removeChoice(0);
	                            }
	                        }else{
	                            this.question.removeChoice(0);
	                        }
	                    }else{
	                        if(userInput != null) {
	                            String newText = (String)(JOptionPane.showInputDialog("Edit field name",userInput));
	                            dm.editMultipleChoiceText(this.question, newText, userInput);
	                            this.question.removeChoice(0);
	                            this.label.setText("<html>" + this.question.returnMCChoices() + "</html>") ;
	                        }else{
	                            this.question.removeChoice(0);
	                        }
	                        
	                    }
	                }
	            }else if (choice == EDIT_MARK){ 
	            	int newMark = Integer.parseInt(JOptionPane.showInputDialog(null,"Change mark",question.getNumMarks()));
	                question.setNumMarks(newMark);
	            }else if(choice == EXIT){
	            }
	        } 
	        
	    }
	    /*
	     * class FramePanel 
	     * Version 1.0
	     * Jason and Nathan 
	     * inner class of Databaseframe which holds the buttons which may be applied to the entire frame
	     */
	    private class FramePanel extends JPanel {
	    	JButton addButton;
	    	JButton exitButton;
	        public FramePanel() {
	            addButton = new JButton("ADD");
	            exitButton = new JButton("EXIT");
	            exitButton.addActionListener(new ExitButtonListener());
	            addButton.addActionListener(new AddButtonListener());
	            this.add(addButton);
	            this.add(exitButton);
	            this.setBackground(new Color(255,255,255));
	        }
	        /*
	         * class ExitButtonListener
	         * Version 1.0
	         * Jason and Nathan 
	         * Inner class of FramePanel which allows the user to exit the database and is asked to save
	         */
	        private class ExitButtonListener implements ActionListener{ 
	            int saveChanges;
	            final String[] YES_NO = {"yes","no"};
	            final int NO = 1;
	            /*
	             * actionPerformed
	             * @param ActionEvent 
	             * implemented from Action Listener interface: allows the user to perform the following actions 
	             * 1. Save database to an existing or new directory
	             * 2. Exit the program 
	             * return void
	             */
	            public void actionPerformed(ActionEvent event) {
	                saveChanges = JOptionPane.showOptionDialog(null, "Save to new database?", "", 
	                                                           JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,YES_NO, null); 
	                if(saveChanges == NO) {
	                    dm.getDatabase().getSource().getName(); 
	                    try {
	                    	dm.saveFiles(dm.getDatabase().getDatabaseName());
	                    	thisFrame.dispose();
	                    	new StartingMenu();
	                    }catch (Exception e) { 
	                    }
	                }else{ 
	                    try{
	                        dm.saveFiles();
	                    }catch(Exception e) {
	                    }
	                    try {
							new StartingMenu();
						} catch (Exception e) {
							e.printStackTrace();
						}
	                }
	            }
	        }
	        /*
	         * class AddButtonListener 
	         * Version 1.0
	         * Jason and Nathan 
	         * Inner class of FramePanel for the AddButton 
	         * An action listener which allows the user to add a question to the database
	         */
	        private class AddButtonListener implements ActionListener {
	            String questionType;
	            int numMarks;
	            String questionText;
	            int isMultipleChoice;
	            Question newQuestion;
	            JButton newQuestionButton;
	            ArrayList<String> choices = new ArrayList<String>();
	            final String[] questionTypes = {"Knowledge","Thinking","Application","Communication"};
	            final String[] yesNo = {"yes","no"};
	            final String KNOWLEDGE_QUESTION = questionTypes[0];
	            final String THINKING_QUESTION = questionTypes[1];
	            final String APPLICATION_QUESTION = questionTypes[2];
	            final String COMMUNICATION_QUESTION = questionTypes[3];
	            final int NO = 1;
	            /*
	             * actionPerformed
	             * @param ActionEvent 
	             * implemented from Action Listener interface: allows the user to perform the following actions 
	             * 1. Add a type of question with their desired number of marks  
	             * return void 
	             */
	            public void actionPerformed(ActionEvent event) {
	                questionType = (String)JOptionPane.showInputDialog(null,"What is the type of question you wish to add?","",JOptionPane.QUESTION_MESSAGE, null,
	                                                                   questionTypes,questionTypes[0]);
	                numMarks = Integer.parseInt(JOptionPane.showInputDialog(null,"How many marks is your question worth?"));
	                questionText = JOptionPane.showInputDialog(null,"What is your question?");
	                isMultipleChoice = JOptionPane.showOptionDialog(null, "Would you like your question to be a multiple choice question?", "", 
	                                                                JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,yesNo, null); 
	                if(questionType.equals(KNOWLEDGE_QUESTION)) {
	                    if(isMultipleChoice == NO) {
	                        newQuestion = new KQuestion(questionText,numMarks);
	                        if(dm.addQuestion(newQuestion)) {
	                            newQuestionButton = new JButton(questionText + " (" + newQuestion.getNumMarks() + ")");
	                            newQuestionButton.addActionListener(new QuestionListener(newQuestion,newQuestionButton,kPanel));
	                            kPanel.add(newQuestionButton);
	                            kPanel.revalidate();
	                            kPanel.repaint();
	                        }
	                    }else{
	                        boolean adding = false;
	                        String input;
	                        do {
	                            try{ 
	                                input = JOptionPane.showInputDialog(null,"Enter choice: ");
	                                if(!input.equals(null)) {
	                                    choices.add(input);
	                                    adding = true;
	                                }
	                            } catch (NullPointerException e){ 
	                                adding = false;
	                            }
	                        }while(adding);
	                        newQuestion = new KQuestion(questionText,numMarks,choices);
	                        if(dm.addQuestion(newQuestion)) {
	                            JLabel label = new JLabel("<html>" + newQuestion.returnMCChoices() + "</html>",SwingConstants.LEFT);
	                            JButton button = new JButton(questionText + " (" + newQuestion.getNumMarks() + ")"); 
	                            button.addActionListener(new QuestionListener(newQuestion,button,label,kPanel));
	                            kPanel.add(button);
	                            kPanel.add(label);           
	                            kPanel.revalidate();
	                        }
	                    }
	                }else if(questionType.equals(THINKING_QUESTION)) {
	                    if(isMultipleChoice == NO) {
	                        newQuestion = new TQuestion(questionText,numMarks);
	                        if(dm.addQuestion(newQuestion)) {
	                            newQuestionButton = new JButton(questionText + " (" + newQuestion.getNumMarks() + ")");
	                            newQuestionButton.addActionListener(new QuestionListener(newQuestion,newQuestionButton,tPanel));
	                            tPanel.add(newQuestionButton);
	                            tPanel.revalidate();
	                            tPanel.repaint();
	                        }
	                    }else{
	                        boolean adding = false;
	                        String input;
	                        do {
	                            try{ 
	                                input = JOptionPane.showInputDialog(null,"Enter choice: ");
	                                if(!input.equals(null)) {
	                                    choices.add(input);
	                                    adding = true;
	                                }
	                            } catch (NullPointerException e){ 
	                                adding = false;
	                            }
	                            
	                        }while(adding);
	                        newQuestion = new KQuestion(questionText,numMarks,choices);
	                        if(dm.addQuestion(newQuestion)) {
	                            JLabel label = new JLabel("<html>" + newQuestion.returnMCChoices() + "</html>",SwingConstants.LEFT);
	                            newQuestionButton = new JButton(questionText + " (" + newQuestion.getNumMarks() + ")");
	                            newQuestionButton.addActionListener(new QuestionListener(newQuestion,newQuestionButton,label,tPanel));
	                            tPanel.add(newQuestionButton);
	                            tPanel.add(label);
	                            tPanel.revalidate();
	                            tPanel.repaint();
	                        }
	                    }
	                }else if(questionType.equals(APPLICATION_QUESTION)) {
	                    if(isMultipleChoice == NO) {
	                        newQuestion = new AQuestion(questionText,numMarks);
	                        if(dm.addQuestion(newQuestion)) {
	                            newQuestionButton = new JButton(questionText + " (" + newQuestion.getNumMarks() + ")");
	                            newQuestionButton.addActionListener(new QuestionListener(newQuestion,newQuestionButton,aPanel));
	                            aPanel.add(newQuestionButton);
	                            aPanel.revalidate();
	                            aPanel.repaint();
	                        }
	                    }else{
	                       boolean adding = false;
	                        String input;
	                        do {
	                            try{ 
	                                input = JOptionPane.showInputDialog(null,"Enter choice: ");
	                                if(!input.equals(null)) {
	                                    choices.add(input);
	                                    adding = true;
	                                }
	                            } catch (NullPointerException e){ 
	                                adding = false;
	                            }
	                            
	                        }while(adding);
	                        newQuestion = new KQuestion(questionText,numMarks,choices);
	                        if(dm.addQuestion(newQuestion)) {
	                            JLabel label = new JLabel("<html>" + newQuestion.returnMCChoices() + "</html>",SwingConstants.LEFT);
	                            newQuestionButton = new JButton(questionText + " (" + newQuestion.getNumMarks() + ")");
	                            newQuestionButton.addActionListener(new QuestionListener(newQuestion,newQuestionButton,label,aPanel));
	                            aPanel.add(newQuestionButton);
	                            aPanel.add(label);
	                            aPanel.revalidate();
	                            aPanel.repaint();
	                        }
	                    }
	                }else if(questionType.equals(COMMUNICATION_QUESTION)) {
	                    if(isMultipleChoice == NO) {
	                        newQuestion = new CQuestion(questionText,numMarks);
	                        if(dm.addQuestion(newQuestion)) {
	                            newQuestionButton = new JButton(questionText + " (" + newQuestion.getNumMarks() + ")");
	                            newQuestionButton.addActionListener(new QuestionListener(newQuestion,newQuestionButton,cPanel));
	                            cPanel.add(newQuestionButton);
	                            cPanel.revalidate();
	                            cPanel.repaint();
	                        }
	                    }else{
	                        boolean adding = false;
	                        String input;
	                        do {
	                            try{ 
	                                input = JOptionPane.showInputDialog(null,"Enter choice: ");
	                                if(!input.equals(null)) {
	                                    choices.add(input);
	                                    adding = true;
	                                }
	                            } catch (NullPointerException e){ 
	                                adding = false;
	                            }
	                        }while(adding);
	                        newQuestion = new KQuestion(questionText,numMarks,choices);
	                        if(dm.addQuestion(newQuestion)) {
	                            JLabel label = new JLabel("<html>" + newQuestion.returnMCChoices() + "</html>",SwingConstants.LEFT);
	                            newQuestionButton = new JButton(questionText + " (" + newQuestion.getNumMarks() + ")");
	                            newQuestionButton.addActionListener(new QuestionListener(newQuestion,newQuestionButton,label,cPanel));
	                            cPanel.add(newQuestionButton);
	                            cPanel.add(label);
	                            cPanel.revalidate();
	                            cPanel.repaint();
	                        }
	                    }
	                }
	            }
	        }
	    }
	    /*
	     * class DatabasePanel 
	     * Version 1.0
	     * Jason and Nathan 
	     * Inner class of DatabaseFrame, a JPanel which organizes the question panels
	     */
	    private class DatabasePanel extends JPanel{ 
	        public DatabasePanel(){ 
	            this.setBackground(new Color(255,255,255));
	        }
	    }
	    /*
	     * class KQuestionPanel 
	     * Version 1.0
	     * Jason and Nathan 
	     * Inner class of DatabaseFrame, stores and displays all the knowledge questions as JButtons
	     */
	    private class KQuestionPanel extends JPanel{ 
	        ArrayList<KQuestion> kQuestionsArray;
	        public KQuestionPanel(){
	            JLabel questionsTypeText = new JLabel("Knowledge Questions");
	            questionsTypeText.setFont(new Font("Sans Serif",Font.BOLD,20));
	            questionsTypeText.setHorizontalAlignment(JLabel.CENTER);
	            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	            this.setBackground(new Color(255,255,255));
	            this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0))); 
	            this.setVisible(true);
	            this.kQuestionsArray = dm.getDatabase().getKQuestions();
	            this.add(questionsTypeText);
	            for(int question = 0; question < kQuestionsArray.size();question++){
	                if(kQuestionsArray.get(question).isMultipleChoice() == false){ 
	                    JButton button = new JButton(kQuestionsArray.get(question).getText() + " (" + kQuestionsArray.get(question).getNumMarks() + ")");
	                    button.addActionListener(new QuestionListener(kQuestionsArray.get(question),button,this));
	                    this.add(button);
	                }else{ 
	                    JButton button = new JButton(kQuestionsArray.get(question).getText() + " (" + kQuestionsArray.get(question).getNumMarks() + ")");
	                    JLabel label= new JLabel("<html>" + kQuestionsArray.get(question).returnMCChoices() + "</html>",SwingConstants.LEFT);
	                    button.addActionListener(new QuestionListener(kQuestionsArray.get(question),button,label,this));
	                    this.add(button);
	                    this.add(label);
	                }
	            }
	        }
	        
	    }
	    /*
	     * class TQuestionPanel 
	     * Version 1.0
	     * Jason and Nathan 
	     * Inner class of DatabaseFrame, stores and displays all the thinking questions as JButtons
	     */
	    private class TQuestionPanel extends JPanel{ 
	        ArrayList<TQuestion> tQuestionsArray;
	        public TQuestionPanel(){ 
	            JLabel questionsTypeText = new JLabel("Thinking Questions");
	            questionsTypeText.setFont(new Font("Sans Serif",Font.BOLD,20));
	            questionsTypeText.setHorizontalAlignment(JLabel.CENTER);
	            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	            this.setBackground(new Color(255,255,255));
	            this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0))); 
	            this.setVisible(true);
	            this.tQuestionsArray = dm.getDatabase().getTQuestions();
	            this.add(questionsTypeText);
	            for(int question = 0; question < tQuestionsArray.size();question++){
	                if(tQuestionsArray.get(question).isMultipleChoice() == false){ 
	                    JButton button = new JButton(tQuestionsArray.get(question).getText() + " (" + tQuestionsArray.get(question).getNumMarks() + ")");
	                    button.addActionListener(new QuestionListener(tQuestionsArray.get(question),button,this));
	                    this.add(button);
	                }else{ 
	                    JButton button = new JButton(tQuestionsArray.get(question).getText() + " (" + tQuestionsArray.get(question).getNumMarks() + ")");
	                    JLabel label= new JLabel("<html>" + tQuestionsArray.get(question).returnMCChoices() + "</html>",SwingConstants.LEFT);
	                    button.addActionListener(new QuestionListener(tQuestionsArray.get(question),button,label,this));
	                    this.add(button);
	                    this.add(label);
	                }
	            }
	        }
	        
	    }
	    /*
	     * class AQuestionPanel 
	     * Version 1.0
	     * Jason and Nathan 
	     * Inner class of DatabaseFrame, stores and displays all the application questions as JButtons
	     */
	    private class AQuestionPanel extends JPanel{ 
	        ArrayList<AQuestion> aQuestionsArray;
	        public AQuestionPanel(){ 
	            JLabel questionsTypeText = new JLabel("Application Questions");
	            questionsTypeText.setFont(new Font("Sans Serif",Font.BOLD,20));
	            questionsTypeText.setHorizontalAlignment(JLabel.CENTER);
	            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	            this.setBackground(new Color(255,255,255));
	            this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0))); 
	            this.setVisible(true);
	            this.aQuestionsArray = dm.getDatabase().getAQuestions();
	            this.add(questionsTypeText);
	            for(int question = 0; question < aQuestionsArray.size();question++){
	                if(aQuestionsArray.get(question).isMultipleChoice() == false){ 
	                    JButton button = new JButton(aQuestionsArray.get(question).getText() + " (" + aQuestionsArray.get(question).getNumMarks() + ")");
	                    button.addActionListener(new QuestionListener(aQuestionsArray.get(question),button,this));
	                    this.add(button);
	                }else{ 
	                    JButton button = new JButton(aQuestionsArray.get(question).getText() + " (" + aQuestionsArray.get(question).getNumMarks() + ")");
	                    JLabel label= new JLabel("<html>" + aQuestionsArray.get(question).returnMCChoices() + "</html>",SwingConstants.LEFT);
	                    button.addActionListener(new QuestionListener(aQuestionsArray.get(question),button,label,this));
	                    this.add(button);
	                    this.add(label);
	                }
	            }
	        }
	    }
	    /*
	     * class CQuestionPanel 
	     * Version 1.0
	     * Jason and Nathan 
	     * Inner class of DatabaseFrame, stores and displays all the communication questions as JButtons
	     */
	    private class CQuestionPanel extends JPanel{ 
	    	ArrayList<CQuestion> cQuestionsArray;
	        public CQuestionPanel(){ 
	            JLabel questionsTypeText = new JLabel("Communication Questions");
	            questionsTypeText.setFont(new Font("Sans Serif",Font.BOLD,20));
	            questionsTypeText.setHorizontalAlignment(JLabel.CENTER);
	            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	            this.setBackground(new Color(255,255,255));
	            this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0))); 
	            this.setVisible(true);
	            this.cQuestionsArray = dm.getDatabase().getCQuestions();
	            this.add(questionsTypeText);
	            for(int question = 0; question < cQuestionsArray.size();question++){
	                if(cQuestionsArray.get(question).isMultipleChoice() == false){ 
	                    JButton button = new JButton(cQuestionsArray.get(question).getText() + " (" + cQuestionsArray.get(question).getNumMarks() + ")");
	                    button.addActionListener(new QuestionListener(cQuestionsArray.get(question),button,this));
	                    this.add(button);
	                }else{ 
	                    JButton button = new JButton(cQuestionsArray.get(question).getText() + " (" + cQuestionsArray.get(question).getNumMarks() + ")");
	                    JLabel label= new JLabel("<html>" + cQuestionsArray.get(question).returnMCChoices() + "</html>",SwingConstants.LEFT);
	                    button.addActionListener(new QuestionListener(cQuestionsArray.get(question),button,label,this));
	                    this.add(button);
	                    this.add(label);
	                }
	            }
	        }
	    }
	}