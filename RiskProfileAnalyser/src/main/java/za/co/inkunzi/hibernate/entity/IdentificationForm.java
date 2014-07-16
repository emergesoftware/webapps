/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.inkunzi.hibernate.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "form_of_identification")
public class IdentificationForm implements Serializable {
    
    @Id
    @Column(name = "identity_form_id")
    private int identityFormId;
    
    @Column(name = "identity_form_desc", nullable = false, unique = true)
    private String description;
    
    @Column(name = "identity_form_legal", nullable = false)
    private boolean legitimate;
    
    @Column(name = "identity_form_active", nullable = false)
    private boolean active;

    public IdentificationForm() {
    }

    public IdentificationForm(int identityFormId, String description, boolean legitimate, boolean active) {
        this.identityFormId = identityFormId;
        this.description = description;
        this.legitimate = legitimate;
        this.active = active;
    }

    public int getIdentityFormId() {
        return identityFormId;
    }

    public void setIdentityFormId(int identityFormId) {
        this.identityFormId = identityFormId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLegitimate() {
        return legitimate;
    }

    public void setLegitimate(boolean legitimate) {
        this.legitimate = legitimate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
    
}
