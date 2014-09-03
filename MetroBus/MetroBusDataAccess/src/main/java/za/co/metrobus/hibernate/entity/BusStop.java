/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.metrobus.hibernate.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "route_bus_stop")
public class BusStop implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_bus_stop_id_sequence")
    @SequenceGenerator(name = "route_bus_stop_id_sequence", sequenceName = "route_bus_stop_id_sequence", 
            allocationSize = 1)
    @Column(name = "route_bus_stop_id")
    private int busStopId;
    
    @OneToOne(targetEntity = BusRoute.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "zone_number", nullable = false)
    private BusRoute route;
    
    @Column(name = "bus_stop_short_desc", nullable = false)
    private String shortDescription;
    
    @Column(name = "bus_stop_full_desc", nullable = false)
    private String fullDescription;
    
    @Column(name = "gps_latitude_coordinate", nullable = false)
    private double gpsLatitude;
  
    @Column(name = "gps_longitude_coordinate", nullable = false)
    private double gpsLongitude;
    
    public BusStop() {
    }

    public int getBusStopId() {
        return busStopId;
    }

    public void setBusStopId(int busStopId) {
        this.busStopId = busStopId;
    }

    public BusRoute getRoute() {
        return route;
    }

    public void setRoute(BusRoute route) {
        this.route = route;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
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
    
    
}
