package za.co.xplain2me.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "citizenship")
public class Citizenship implements Serializable {
    
    public static final long SOUTH_AFRICAN = 100;
    public static final long NON_SOUTH_AFRICAN = 200;
    
    
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
