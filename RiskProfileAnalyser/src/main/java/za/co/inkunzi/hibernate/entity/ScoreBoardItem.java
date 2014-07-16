/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.inkunzi.hibernate.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "scoreboard_item")
public class ScoreBoardItem implements Serializable {
    
    @Id
    @GeneratedValue(generator = "score_board_item_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "score_board_item_id_seq", sequenceName = "score_board_item_id_seq",
            allocationSize = 1)
    @Column(name = "score_board_item_id")
    private int scoreBoardItemId;
    
    @Column(name = "start_range", nullable = false)
    private int startRange;
    
    @Column(name = "end_range", nullable = false)
    private int endRange;
    
    @Column(name = "title", nullable = false, unique = true)
    private String title;
    
    @Column(name = "score_board_item_desc", nullable = false)
    private String description;

    public ScoreBoardItem() {
    }

    public int getScoreBoardItemId() {
        return scoreBoardItemId;
    }

    public void setScoreBoardItemId(int scoreBoardItemId) {
        this.scoreBoardItemId = scoreBoardItemId;
    }

    public int getStartRange() {
        return startRange;
    }

    public void setStartRange(int startRange) {
        this.startRange = startRange;
    }

    public int getEndRange() {
        return endRange;
    }

    public void setEndRange(int endRange) {
        this.endRange = endRange;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    
}
