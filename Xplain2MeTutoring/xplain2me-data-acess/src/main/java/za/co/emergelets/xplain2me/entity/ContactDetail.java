package za.co.emergelets.xplain2me.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "contact_detail")
public class ContactDetail implements Serializable {
    
    /*
    Table: contact_detail
    
    Columns:
        contact_detail_id int not null unique default nextval('contact_detail_id_sequence'),
        contact_detail_cell_number varchar(32) not null unique,
        contact_detail_email_address varchar(256) not null unique
    */
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_detail_id")
    private long id;
    
    @Column(name = "contact_detail_cell_number", nullable = false, unique = true)
    private String cellphoneNumber;
    
    @Column(name = "contact_detail_email_address", unique = true, nullable = false)
    private String emailAddress;
    
    public ContactDetail() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    
}
