package za.co.emergelets.xplain2me.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "event")
public class Event implements Serializable {
    
    /*
    Table: event
    Columns:    
        event_type int not null unique,
        event_short_desc varchar(128) not null,
        event_desc varchar(256) not null unique,
        event_is_financial boolean not null default false
    */
    
    @Id
    @Column(name = "event_type", nullable = false, unique = true)
    private long type;
    
    @Column(name = "event_short_desc", nullable = false)
    private String shortDescription;
    
    @Column(name = "event_desc", nullable = false, unique = true)
    private String longDescription;
    
    @Column(name = "event_is_financial", nullable = false)
    private boolean isFinancial;
    
    public Event() {
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public boolean isIsFinancial() {
        return isFinancial;
    }

    public void setIsFinancial(boolean isFinancial) {
        this.isFinancial = isFinancial;
    }
    
    
}
