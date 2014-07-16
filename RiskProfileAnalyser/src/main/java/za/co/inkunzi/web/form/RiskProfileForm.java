/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.inkunzi.web.form;

import java.io.Serializable;
import java.util.ArrayList;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import za.co.inkunzi.hibernate.entity.PotentialInvestor;
import za.co.inkunzi.hibernate.entity.QuestionScorePoint;
import za.co.inkunzi.hibernate.entity.RiskProfileQuestionItem;
import za.co.inkunzi.hibernate.entity.ScoreBoardItem;

@Component
@Scope("session")
public class RiskProfileForm implements Serializable {
    
    // the questions
    private ArrayList<RiskProfileQuestionItem> questions;
    // the scoreboard info
    private ArrayList<ScoreBoardItem> scoreBoard;
    // the scores for each question
    private ArrayList<QuestionScorePoint> points;
    
    // the potential client
    private PotentialInvestor investor;
    
    public RiskProfileForm() {
        this.questions = null;
        this.scoreBoard = null;
        this.points = null;
        this.investor = null;
    }

    public ArrayList<RiskProfileQuestionItem> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<RiskProfileQuestionItem> questions) {
        this.questions = questions;
    }

    public ArrayList<ScoreBoardItem> getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(ArrayList<ScoreBoardItem> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public ArrayList<QuestionScorePoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<QuestionScorePoint> points) {
        this.points = points;
    }

    public PotentialInvestor getInvestor() {
        return investor;
    }

    public void setInvestor(PotentialInvestor investor) {
        this.investor = investor;
    }
    
    
    
}
