/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.inkunzi.hibernate.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table (name = "risk_profile_question_item")
public class RiskProfileQuestionItem implements Serializable {
    
    @Id
    @Column(name = "risk_profile_question_item_no")
    private int riskProfileQuestionNumber;
    
    @Column(name = "risk_profile_question_text", unique = true, nullable = false)
    private String questionText;
    
    @Column(name = "risk_profile_opt_a", nullable = false)
    private String optionA;
    
    @Column(name = "risk_profile_opt_b", nullable = false)
    private String optionB;
    
    @Column(name = "risk_profile_opt_c", nullable = false)
    private String optionC;
     
    @Column(name = "risk_profile_opt_d", nullable = false)
    private String optionD;
    
    @Column(name = "risk_profile_opt_e")
    private String optionE;
    
    @OneToOne(targetEntity = QuestionScorePoint.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_score_point_no", nullable = false)
    private QuestionScorePoint scorePoints;

    public RiskProfileQuestionItem() {
    }

    public int getRiskProfileQuestionNumber() {
        return riskProfileQuestionNumber;
    }

    public void setRiskProfileQuestionNumber(int riskProfileQuestionNumber) {
        this.riskProfileQuestionNumber = riskProfileQuestionNumber;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getOptionE() {
        return optionE;
    }

    public void setOptionE(String optionE) {
        this.optionE = optionE;
    }

    public QuestionScorePoint getScorePoints() {
        return scorePoints;
    }

    public void setScorePoints(QuestionScorePoint scorePoints) {
        this.scorePoints = scorePoints;
    }
    
    
    
}
