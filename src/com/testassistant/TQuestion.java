package com.testassistant;
/*
 * TQuestion.java
 * 01/02/2020
 * @Jason and Nathan 
 * Version 1.0
 * Class outline for a thinking question object
 */

import java.util.ArrayList;

public class TQuestion extends Question { 
    //not mc 
    TQuestion(String t,int n) {
        super(t,n);
    }
    
    //mc 
    TQuestion(String t,int n,ArrayList<String> c) {
        super(t,n,c);
    }
}