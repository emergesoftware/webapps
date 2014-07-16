/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.inkunzi.hibernate.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table (name = "potential_investor")
public class PotentialInvestor implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "potential_investor_id_seq")
    @SequenceGenerator(name = "potential_investor_id_seq", sequenceName = "potential_investor_id_seq",
            allocationSize = 1)
    @Column(name = "potential_investor_id")
    private int potentialnvestorId;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "first_names", nullable = false)
    private String firstNames;
    
    @OneToOne(targetEntity = ContactDetail.class, fetch = FetchType.EAGER, mappedBy = "contactDetailId")
    @JoinColumn(name = "contact_detail_id", nullable = false)
    private ContactDetail contactDetail;
    
    @OneToOne(targetEntity = PersonIdentity.class, fetch = FetchType.EAGER, mappedBy = "personIdentityEntryId")
    @JoinColumn(name = "person_identity_entry_id", nullable = false, unique = true)
    private PersonIdentity identity;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_evaluated", nullable = false)
    private Date dateEvaluated;

    public PotentialInvestor() {
    }

    public int getPotentialnvestorId() {
        return potentialnvestorId;
    }

    public void setPotentialnvestorId(int potentialnvestorId) {
        this.potentialnvestorId = potentialnvestorId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstNames() {
        return firstNames;
    }

    public void setFirstNames(String firstNames) {
        this.firstNames = firstNames;
    }

    public ContactDetail getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(ContactDetail contactDetail) {
        this.contactDetail = contactDetail;
    }

    public PersonIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(PersonIdentity identity) {
        this.identity = identity;
    }

    public Date getDateEvaluated() {
        return dateEvaluated;
    }

    public void setDateEvaluated(Date dateEvaluated) {
        this.dateEvaluated = dateEvaluated;
    }
    
    
    
}
