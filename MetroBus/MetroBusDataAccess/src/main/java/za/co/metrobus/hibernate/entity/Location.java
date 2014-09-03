package za.co.metrobus.hibernate.entity;

import javax.persistence.*;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = Location.TABLE_NAME)
@XmlRootElement
public class Location implements Serializable {
    
    /*------------ Table Name ------------------*/
    public static final String TABLE_NAME = "locations";
    /*------------ Columns ---------------------*/
    public static final String LOCATION_ID_COL = "location_id";
    public static final String LOCATION_SHORT_NAME_COL = "location_short_name";
    public static final String LOCATION_FULL_NAME_COL = "location_full_name";
    public static final String GPS_LATITUDE_COL = "gps_latitude";
    public static final String GPS_LONGITUDE_COL = "gps_longitude";
    
    @Id
    @Column(name = LOCATION_ID_COL, unique = true)
    private int locationId;
    
    @Column(name = LOCATION_SHORT_NAME_COL, unique = true)
    private String shortName;
    
    @Column(name = LOCATION_FULL_NAME_COL, unique = true)
    private String fullName;
    
    @Column(name = GPS_LATITUDE_COL)
    private double gpsLatitude;
    
    @Column(name = GPS_LONGITUDE_COL)
    private double gpsLongitude;
    
    public Location() {
        this.shortName = null;
        this.fullName = null;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(double gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public double getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(double gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    @Override
    public String toString() {
        return "Location {" + "locationId=" + locationId + 
                ", shortName=" + shortName + 
                ", fullName=" + fullName + 
                ", gpsLatitude=" + gpsLatitude + 
                ", gpsLongitude=" + gpsLongitude + "}\n";
    }
    
    
}
