package za.co.emergelets.xplain2me.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "audit")
public class Audit implements Serializable {
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "audit_id")
    private long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "audit_timestamp", nullable = false)
    private Date timestamp;
    
    @OneToOne(targetEntity = Event.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "event_type", nullable = false)
    private Event event;
    
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_name", nullable = false)
    private User user;
    
    @Column(name = "audit_reference")
    private long reference;
    
    @Column(name = "audit_xml")
    private String auditXml;
    
    @Column(name = "audit_ip_address", nullable = false)
    private String sourceIpAddress;
    
    @Column(name = "audit_user_agent", nullable = false)
    private String userAgent;
    
    @Column(name = "audit_authority_code")
    private long authorityCode;
    
    @Column(name = "audit_authorised", nullable = false)
    private boolean authorised;
    
    public Audit() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getReference() {
        return reference;
    }

    public void setReference(long reference) {
        this.reference = reference;
    }

    public String getAuditXml() {
        return auditXml;
    }

    public void setAuditXml(String auditXml) {
        this.auditXml = auditXml;
    }

    public String getSourceIpAddress() {
        return sourceIpAddress;
    }

    public void setSourceIpAddress(String sourceIpAddress) {
        this.sourceIpAddress = sourceIpAddress;
    }

    public long getAuthorityCode() {
        return authorityCode;
    }

    public void setAuthorityCode(long authorityCode) {
        this.authorityCode = authorityCode;
    }

    public boolean isAuthorised() {
        return authorised;
    }

    public void setAuthorised(boolean authorised) {
        this.authorised = authorised;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public String toString() {
        return "Audit {" + "id=" + id + 
                ", timestamp=" + timestamp + 
                ", event=" + event + 
                ", user=" + user + 
                ", reference=" + reference + 
                ", auditXml=" + auditXml + 
                ", sourceIpAddress=" + sourceIpAddress + 
                ", userAgent=" + userAgent + 
                ", authorityCode=" + authorityCode + 
                ", authorised=" + authorised + "}\n";
    }
    
    
    
}
