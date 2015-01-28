package za.co.xplain2me.entity;

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
@Table(name = "become_tutor_supporting_documents")
public class BecomeTutorSupportingDocument implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "become_tutor_supporting_documents_id", nullable = false, 
            unique = true)
    private long id;
    
    @Column(name = "become_tutor_supporting_documents_label", nullable = false)
    private String label;
    
    @Column(name = "become_tutor_supporting_documents_file")
    private byte[] document;
    
    @OneToOne(targetEntity = BecomeTutorRequest.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "become_tutor_request_id", nullable = false)
    private BecomeTutorRequest request;
    
    
    public BecomeTutorSupportingDocument(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public BecomeTutorRequest getRequest() {
        return request;
    }

    public void setRequest(BecomeTutorRequest request) {
        this.request = request;
    }

}
