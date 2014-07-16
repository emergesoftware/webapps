/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.inkunzi.hibernate.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "person_identity")
public class PersonIdentity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_identity_entry_id_seq")
    @SequenceGenerator(name = "person_identity_entry_id_seq", sequenceName = "person_identity_entry_id_seq",
            allocationSize = 1)
    @Column(name = "person_identity_entry_id")
    private int personIdentityEntryId;
    
    @Column(name = "id_number", nullable = false, unique = true)
    private String identityNumber; 
    
    @OneToOne(targetEntity = IdentificationForm.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "identity_form_id")
    private IdentificationForm formOfIdentity;
    
    @Column(name = "is_south_african_id", nullable = false)
    private boolean isSouthAfricanId;
    
    public PersonIdentity(){
    }

    public int getPersonIdentityEntryId() {
        return personIdentityEntryId;
    }

    public void setPersonIdentityEntryId(int personIdentityEntryId) {
        this.personIdentityEntryId = personIdentityEntryId;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public IdentificationForm getFormOfIdentity() {
        return formOfIdentity;
    }

    public void setFormOfIdentity(IdentificationForm formOfIdentity) {
        this.formOfIdentity = formOfIdentity;
    }

    public boolean isIsSouthAfricanId() {
        return isSouthAfricanId;
    }

    public void setIsSouthAfricanId(boolean isSouthAfricanId) {
        this.isSouthAfricanId = isSouthAfricanId;
    }
    
    
    
}
