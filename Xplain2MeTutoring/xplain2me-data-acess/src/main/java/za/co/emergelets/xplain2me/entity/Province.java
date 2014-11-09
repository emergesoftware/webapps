package za.co.emergelets.xplain2me.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "province")
public class Province implements Serializable {
    
    @Id
    @Column(name = "province_id", nullable = false, unique = true)
    private long id;
    
    @Column(name = "province_desc", nullable = false, unique = true)
    private String description;
    
    public Province() {
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
