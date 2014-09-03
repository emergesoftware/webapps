package za.co.metrobus.hibernate.entity;

import javax.persistence.*;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tsepo Maleka
 */
@Entity
@Table(name = BusZone.TABLE_NAME)
@XmlRootElement
public class BusZone implements Serializable {
    
    // the table name
    public static final String TABLE_NAME = "bus_zone";
    // the columns
    public static final String ZONE_NUMBER_COL = "zone_number";
    public static final String ZONE_DESCRIPTION_COL = "zone_desc";
    
    // variables
    
    @Id
    @Column(name = ZONE_NUMBER_COL, nullable = false)
    private int zoneNumber;
    
    @Column(name = ZONE_DESCRIPTION_COL, nullable = false, unique = true)
    private String zoneDescription;
    
    public BusZone() {
    }

    public int getZoneNumber() {
        return zoneNumber;
    }

    public void setZoneNumber(int zoneNumber) {
        this.zoneNumber = zoneNumber;
    }

    public String getZoneDescription() {
        return zoneDescription;
    }

    public void setZoneDescription(String zoneDescription) {
        this.zoneDescription = zoneDescription;
    }

    @Override
    public String toString() {
        return "BusZone{" + "zoneNumber=" + zoneNumber + ", zoneDescription=" + zoneDescription + '}';
    }
    
    
    
}
