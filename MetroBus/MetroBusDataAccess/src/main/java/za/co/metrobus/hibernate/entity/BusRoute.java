package za.co.metrobus.hibernate.entity;

import javax.persistence.*;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = BusRoute.TABLE_NAME)
@XmlRootElement
public class BusRoute implements Serializable {
    
    /* ------------ Table Name ----------------*/
    public static final String TABLE_NAME = "bus_route";
    /* ------------ Columns -------------------*/
    public static final String ROUTE_NUMBER_COL = "route_number";
    public static final String ROUTE_DESC_COL = "route_desc";
    public static final String WEEKDAY_SERVICE_COL = "weekday_service";
    public static final String SATURDAY_SERVICE_COL = "saturday_service";
    public static final String SUNDAY_SERVICE_COL = "sunday_service";
    
    @Id
    @Column(name = ROUTE_NUMBER_COL, unique = true, nullable = false)
    private String routeNumber;
    
    @Column(name = ROUTE_DESC_COL, unique = true, nullable = false)
    private String routeDescription;
    
    @Column(name = WEEKDAY_SERVICE_COL)
    private boolean weekdayService;
    
    @Column(name = SATURDAY_SERVICE_COL)
    private boolean saturdayService;
    
    @Column(name = SUNDAY_SERVICE_COL)
    private boolean sundayService;
    
    public BusRoute() {
        this.routeNumber = null;
        this.routeDescription = null;
        this.weekdayService = true;
        this.saturdayService = false;
        this.sundayService = false;
    }

    public BusRoute(String routeNumber, String routeDescription) {
        this();
        this.routeNumber = routeNumber;
        this.routeDescription = routeDescription;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getRouteDescription() {
        return routeDescription;
    }

    public void setRouteDescription(String routeDescription) {
        this.routeDescription = routeDescription;
    }

    public boolean isWeekdayService() {
        return weekdayService;
    }

    public void setWeekdayService(boolean weekdayService) {
        this.weekdayService = weekdayService;
    }

    public boolean isSaturdayService() {
        return saturdayService;
    }

    public void setSaturdayService(boolean saturdayService) {
        this.saturdayService = saturdayService;
    }

    public boolean isSundayService() {
        return sundayService;
    }

    public void setSundayService(boolean sundayService) {
        this.sundayService = sundayService;
    }

}
