package za.co.metrobus.hibernate.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = BusDeparture.TABLE_NAME)
@XmlRootElement
public class BusDeparture implements Serializable {
    
    // the table name
    public static final String TABLE_NAME = "bus_departures";
    // the sequence name
    public static final String SEQUENCE_NAME = "bus_departures_id_sequence";
    // the columns
    public static final String BUS_DEPARTURES_ID_COL = "bus_departures_id";
    public static final String BUS_DEPARTURE_LOCATION_ID_COL = "bus_departure_location_id";
    public static final String BUS_DEPARTURE_TIME_COL = "bus_departure_time";
    public static final String IS_WEEKDAY_SERVICE_COL = "is_weekday_service";
    public static final String IS_SATURDAY_SERVICE_COL = "is_saturday_service";
    public static final String IS_SUNDAY_SERVICE_COL = "is_sunday_service";
    
    // variables
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    @Column(name = BUS_DEPARTURES_ID_COL)
    private int busDepartureId;
    
    @OneToOne(targetEntity = BusRoute.class, fetch = FetchType.EAGER)
    @JoinColumn(name = BusRoute.ROUTE_NUMBER_COL, nullable = false)
    private BusRoute route;
    
    @OneToOne(targetEntity = Location.class, fetch = FetchType.EAGER)
    @JoinColumn(name = BUS_DEPARTURE_LOCATION_ID_COL, nullable = false)
    private Location departureLocation;
    
    @Temporal(TemporalType.TIME)
    @Column(name = BUS_DEPARTURE_TIME_COL, nullable = false)
    private Date departureTime;
    
    @OneToMany(targetEntity = BusArrival.class, fetch = FetchType.EAGER, 
            mappedBy = "busDeparture")
    private List<BusArrival> busArrivals;
    
    @Column(name = IS_WEEKDAY_SERVICE_COL, nullable = false)
    private boolean weekdayService;
    
    @Column(name = IS_SATURDAY_SERVICE_COL, nullable = false)
    private boolean saturdayService;
    
    @Column(name = IS_SUNDAY_SERVICE_COL, nullable = false)
    private boolean sundayService;

    @Transient
    private boolean isNextDeparture;
    
    public BusDeparture() {
        
        this.busArrivals = new ArrayList<BusArrival>();
        
        this.weekdayService = true;
        this.saturdayService = false;
        this.sundayService = false;
        this.isNextDeparture = false;
    }

    public int getBusDepartureId() {
        return busDepartureId;
    }

    public void setBusDepartureId(int busDepartureId) {
        this.busDepartureId = busDepartureId;
    }

    public BusRoute getRoute() {
        return route;
    }

    public void setRoute(BusRoute route) {
        this.route = route;
    }

    public Location getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(Location departureLocation) {
        this.departureLocation = departureLocation;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
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

    @XmlTransient
    public List<BusArrival> getBusArrivals() {
        return busArrivals;
    }

    public void setBusArrivals(List<BusArrival> busArrivals) {
        this.busArrivals = busArrivals;
    }

    public boolean isNextDeparture() {
        return isNextDeparture;
    }

    public void setNextDeparture(boolean isNextDeparture) {
        this.isNextDeparture = isNextDeparture;
    }
}
