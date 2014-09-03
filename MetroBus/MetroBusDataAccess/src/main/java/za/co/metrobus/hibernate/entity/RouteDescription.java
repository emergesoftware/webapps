package za.co.metrobus.hibernate.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = RouteDescription.TABLE_NAME)
public class RouteDescription implements Serializable {
    
    // the table name
    public static final String TABLE_NAME = "bus_route_desc";
    // the sequence
    public static final String SEQUENCE_NAME = "route_desc_id_sequence";
    // the columns
    public static final String BUS_ROUTE_DESC_ID_COL = "bus_route_desc_id";
    public static final String BUS_ROUTE_DESC_TEXT_COL = "bus_route_desc_text";
    public static final String START_LOCATION_ID_COL = "start_location_id";
    public static final String END_LOCATION_ID_COL = "end_location_id";
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, 
            allocationSize = 1)
    @Column(name = BUS_ROUTE_DESC_ID_COL)
    private int id;
    
    @Column(name = BUS_ROUTE_DESC_TEXT_COL, nullable = false, unique = true)
    private String text;
    
    @OneToOne(targetEntity = BusRoute.class, fetch = FetchType.EAGER)
    @JoinColumn(name = BusRoute.ROUTE_NUMBER_COL, nullable = false)
    private BusRoute route;
    
    @OneToOne(targetEntity = Location.class, fetch = FetchType.EAGER)
    @JoinColumn(name = START_LOCATION_ID_COL, nullable = false)
    private Location startLocation;
    
    @OneToOne(targetEntity = Location.class, fetch = FetchType.EAGER)
    @JoinColumn(name = END_LOCATION_ID_COL, nullable = false)
    private Location endLocation;
    
    public RouteDescription() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BusRoute getRoute() {
        return route;
    }

    public void setRoute(BusRoute route) {
        this.route = route;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }
    
    
    
}
