package com.testassistant;
/*
 * CQuestion.java
 * 01/02/2020
 * @Jason and Nathan
 * Version 1.0
 * Class outline for a communication question object
 */

import java.util.ArrayList;

public class CQuestion extends Question {
    //not mc
    CQuestion(String t,int n) {
        super(t,n);
    }
    
    //mc
    CQuestion(String t,int n,ArrayList<String> c) {
        super(t,n,c);
    }
}