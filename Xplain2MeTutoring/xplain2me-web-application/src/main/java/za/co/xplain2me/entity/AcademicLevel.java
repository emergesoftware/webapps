package za.co.xplain2me.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "academic_level")
public class AcademicLevel implements Serializable {
    
    @Id
    @Column(name = "academic_level_id", unique = true, nullable = false)
    private long id;
    
    @Column(name = "academic_level_desc", unique = true, nullable = false)
    private String description;
    
    public AcademicLevel() {
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
