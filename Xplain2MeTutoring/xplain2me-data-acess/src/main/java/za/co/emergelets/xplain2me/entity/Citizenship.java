package za.co.emergelets.xplain2me.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "citizenship")
public class Citizenship implements Serializable {
    
    /*
        
    Table: citizenship
    
    Columns:
        citizenship_id int not null unique,
        citizenship_desc varchar(128) not null unique
    
    */
    
    @Id
    @Column(name = "citizenship_id", unique = true, nullable = false)
    private long id;
    
    @Column(name = "citizenship_desc", unique = true, nullable = false)
    private String description;
    
    public Citizenship(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
            
    
}
