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
    
    /*
    Table: audit
    
    Columns:    
        audit_id int not null unique default nextval('audit_id_sequence'),
        audit_timestamp timestamp without time zone not null default now(),
        event_type int not null,
        user_name varchar(32) not null,
        audit_reference int not null default 0,
        audit_xml text,
        audit_ip_address varchar(256) not null default '127.0.0.1',
        audit_authority_code int,
        audit_authorised boolean not null default true
    */
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    
    
}
