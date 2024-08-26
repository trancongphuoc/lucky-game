/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.database.dao;

import java.sql.Timestamp;

/**
 *
 * @author hanhnv62
 */
public class IvrCommandsBO {


   private String id;
    
    private String content;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String answerCorrect;
    private String questionCode;
    private String difficult;
   
    Timestamp updated;

    public IvrCommandsBO(String id, String content, String answer1, String answer2, String answer3, String answer4,
            String answerCorrect, String questionCode, Timestamp updated, String difficult) {
        this.id = id;
        this.content = content;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.answerCorrect = answerCorrect;
        this.questionCode = questionCode;
        this.updated = updated;
        this.difficult = difficult;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public String getAnswerCorrect() {
        return answerCorrect;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public String getDifficult() {
        return difficult;
    }
    
    
}