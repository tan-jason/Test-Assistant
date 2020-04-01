package com.testassistant;
/*
 * StartingMenu.java
 * Version 1.0
 * @Jason and Nathan 
 * 01/13/2020
 * Starting menu which contains main method
 */

import javax.swing.*;
import java.io.File;

public class StartingMenu { 
    public static void main(String[] args) throws Exception{
        String userInput;
        final String[] programSelect = {"Generate test with database","Choose a database to edit","Create database"};
        userInput = (String)JOptionPane.showInputDialog(null,"","",JOptionPane.QUESTION_MESSAGE, null,
                                                        programSelect,programSelect[0]);
        final String CREATE_TEST = programSelect[0];
        final String EDIT_DATABASE = programSelect[1];
        
        JFileChooser fileChooser = new JFileChooser();
        File folderPathAddress = null;
        File sourceFolder;
        String path = System.getProperty("user.dir");
        if(userInput.equals(CREATE_TEST)) { //CREATE TEST
            path = path.substring(0,path.lastIndexOf("\\")) + "\\Questions\\Databases";
            sourceFolder = new File(path);
            fileChooser.setCurrentDirectory(sourceFolder);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int result = fileChooser.showOpenDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){ 
                folderPathAddress = fileChooser.getSelectedFile(); 
            }
            QuestionDatabase qb = new QuestionDatabase(folderPathAddress);
            TestManager tm = new TestManager(qb);
            new TestFrame(tm);
        }else if(userInput.equals(EDIT_DATABASE)){ //EDITING DATABASE
            path = path.substring(0,path.lastIndexOf("\\")) + "\\Questions\\Databases";
            sourceFolder = new File(path);
            fileChooser.setCurrentDirectory(sourceFolder);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int result = fileChooser.showOpenDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){ 
                folderPathAddress = fileChooser.getSelectedFile(); 
            }
            QuestionDatabase qb = new QuestionDatabase(folderPathAddress);
            DatabaseManager manager = new DatabaseManager(qb);new DatabaseFrame(manager);
        }else{ //CREATE DATABASE
        	new DatabaseManager();
        }
    }
}