/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csci5520_teamproject;

import java.util.ArrayList;

/**
 *
 * @author Dustin Dacanay
 */
public class Question {

    int questionNumber;
    private ArrayList<String> questionStringArray = new ArrayList<String>();
    private ArrayList<String> abcdArray = new ArrayList<String>();
    private ArrayList<String> keyArray = new ArrayList<String>();
    private ArrayList<String> hintArray = new ArrayList<String>();

    public Question(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public Question() {
    }

    public void addLineToQuestion(String line) {
        questionStringArray.add(line);
    }

    public ArrayList<String> getQuestionStringArray() {
        return this.questionStringArray;
    }

    public void addOption(String line) {
        abcdArray.add(line);
    }

    public ArrayList<String> getOptions() {
        return this.abcdArray;
    }

    public void addKey(String line) {
        keyArray.add(line);
    }

    public ArrayList<String> getKeyArray() {
        return this.keyArray;
    }

    public void addHint(String line) {
        hintArray.add(line);
    }

    public ArrayList<String> getHintArray() {
        return this.hintArray;
    }

}
