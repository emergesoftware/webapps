/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.inkunzi.hibernate.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "question_score_point")
public class QuestionScorePoint implements Serializable {
    
    @Id 
    @Column(name = "question_score_point_no")
    private int questionScorePointNumber;
    
    @Column(name = "option_a_score", nullable = false)
    private int optionAScore;
    
    @Column(name = "option_b_score", nullable = false)
    private int optionBScore;
    
    @Column(name = "option_c_score", nullable = false)
    private int optionCScore;
    
    @Column(name = "option_d_score", nullable = false)
    private int optionDScore;
    
    @Column(name = "option_e_score", nullable = false)
    private int optionEScore;
    
    public QuestionScorePoint(){
    }

    public int getQuestionScorePointNumber() {
        return questionScorePointNumber;
    }

    public void setQuestionScorePointNumber(int questionScorePointNumber) {
        this.questionScorePointNumber = questionScorePointNumber;
    }

    public int getOptionAScore() {
        return optionAScore;
    }

    public void setOptionAScore(int optionAScore) {
        this.optionAScore = optionAScore;
    }

    public int getOptionBScore() {
        return optionBScore;
    }

    public void setOptionBScore(int optionBScore) {
        this.optionBScore = optionBScore;
    }

    public int getOptionCScore() {
        return optionCScore;
    }

    public void setOptionCScore(int optionCScore) {
        this.optionCScore = optionCScore;
    }

    public int getOptionDScore() {
        return optionDScore;
    }

    public void setOptionDScore(int optionDScore) {
        this.optionDScore = optionDScore;
    }

    public int getOptionEScore() {
        return optionEScore;
    }

    public void setOptionEScore(int optionEScore) {
        this.optionEScore = optionEScore;
    }
    
}
