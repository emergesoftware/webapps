package za.co.xplain2me.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "app_client")
public class AppClient implements Serializable {
    
    /*
    Table: app_client
    
    Columns:
        app_client_id int not null unique,
        app_client_name varchar(128) not null unique,
        app_client_trading_name varchar(128) not null unique,
        app_client_business_reg_number varchar(64),
        app_client_contact_number varchar(24) not null unique,
        app_client_contact_person varchar(128) not null,
        app_client_physical_address text,
        app_client_postal_address text,
        app_client_email_address varchar(256) not null unique,
        app_client_date_added timestamp without time zone not null default now(),
        app_client_active boolean not null default true,
        app_client_date_inactive timestamp without time zone
    
    */
    
    @Id
    @Column(name = "app_client_id", unique = true)
    private long clientId;
    
    @Column(name = "app_client_name", unique = true, nullable = false)
    private String name;
    
    @Column(name = "app_client_trading_name")
    private String tradingName;
    
    @Column(name = "app_client_business_reg_number")
    private String businessRegistrationNumber;
    
    @Column(name = "app_client_contact_number", nullable = false, unique = true)
    private String contactNumber;
    
    @Column(name = "app_client_contact_person", nullable = false)
    private String contactPerson;
    
    @Column(name = "app_client_physical_address")
    private String physicalAddress;
    
    @Column(name = "app_client_postal_address")
    private String postalAddress;
    
    @Column(name = "app_client_email_address", unique = true, nullable = false)
    private String emailAddress;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "app_client_date_added", nullable = false)
    private Date clientAdded;
    
    @Column(name = "app_client_active", nullable = false)
    private boolean active;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "app_client_date_inactive")
    private Date dateInactive;
    
    public AppClient() {
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getBusinessRegistrationNumber() {
        return businessRegistrationNumber;
    }

    public void setBusinessRegistrationNumber(String businessRegistrationNumber) {
        this.businessRegistrationNumber = businessRegistrationNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getClientAdded() {
        return clientAdded;
    }

    public void setClientAdded(Date clientAdded) {
        this.clientAdded = clientAdded;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDateInactive() {
        return dateInactive;
    }

    public void setDateInactive(Date dateInactive) {
        this.dateInactive = dateInactive;
    }
    
    
    
}
