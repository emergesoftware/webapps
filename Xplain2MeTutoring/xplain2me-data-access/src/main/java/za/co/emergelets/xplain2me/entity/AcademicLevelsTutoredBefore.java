package za.co.emergelets.xplain2me.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "academic_levels_tutored_before")
public class AcademicLevelsTutoredBefore implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "academic_levels_tutored_before_id", nullable = false,
            unique = true)
    private long id;
    
    @OneToOne(targetEntity = AcademicLevel.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "academic_level_id", nullable = false)
    private AcademicLevel academicLevel;
    
    @OneToOne(targetEntity = BecomeTutorRequest.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "become_tutor_request_id", nullable = false)
    private BecomeTutorRequest request;
    
    public AcademicLevelsTutoredBefore() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AcademicLevel getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(AcademicLevel academicLevel) {
        this.academicLevel = academicLevel;
    }

    public BecomeTutorRequest getRequest() {
        return request;
    }

    public void setRequest(BecomeTutorRequest request) {
        this.request = request;
    }
    
    
    
}
