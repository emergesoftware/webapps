package za.co.emergelets.xplain2me.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "physical_address")
public class PhysicalAddress implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "physical_address_id")
    private long id;
    
    @Column(name = "physical_address_line_1", nullable = false)
    private String addressLine1;
    
    @Column(name = "physical_address_line_2")
    private String addressLine2;
    
    @Column(name = "physical_address_suburb", nullable = false)
    private String suburb;
    
    @Column(name = "physical_address_city", nullable = false)
    private String city;
    
    @Column(name = "physical_address_area_code")
    private String areaCode;
    
    public PhysicalAddress() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
    public String toHtmlString() {
        
        StringBuilder builder = new StringBuilder();
        
        builder.append(addressLine1).append("<br/>");
        
        if (addressLine2 != null && addressLine2.isEmpty() == false)
            builder.append(addressLine2).append("<br/>");
        
        builder.append(suburb).append("<br/>");
        builder.append(city).append("<br/>");
        builder.append(areaCode).append("<br/>");
        
        return builder.toString();
    }
}
