package za.co.metrobus.hibernate.entity;

import javax.persistence.*;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tsepo Maleka
 */
@Entity
@Table (name = BusArrival.TABLE_NAME)
@XmlRootElement
public class BusArrival implements Serializable {
    
    // the table name
    public static final String TABLE_NAME = "bus_arrivals";
    // the sequence name
    public static final String SEQUENCE_NAME = "bus_arrivals_id_sequence";
    // the columns
    public static final String BUS_ARRIVAL_ID_COL = "bus_arrivals_id";
    public static final String BUS_ARRIVAL_SEQUENCE_COL = "bus_arrival_sequence";
    public static final String BUS_ARRIVAL_LOCATION_ID_COL = "bus_arrival_location_id";
    public static final String IS_VIA_LOCATION_COL = "is_via_location";
    public static final String IS_TURN_AROUND_LOCATION_COL = "is_turn_around_location";
    
    // the variables
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    @Column(name = BUS_ARRIVAL_ID_COL, unique = true, nullable = false)
    private int busArrivalId;
    
    @ManyToOne(targetEntity = BusDeparture.class, fetch = FetchType.EAGER)
    @JoinColumn(name = BusDeparture.BUS_DEPARTURES_ID_COL, nullable = false)
    private BusDeparture busDeparture;
    
    @Column(name = BUS_ARRIVAL_SEQUENCE_COL, nullable = false)
    private int arrivalSequence;
    
    @OneToOne(targetEntity = Location.class, fetch = FetchType.EAGER)
    @JoinColumn(name = BUS_ARRIVAL_LOCATION_ID_COL, nullable = false)
    private Location arrivalLocation;
    
    @Column(name = IS_VIA_LOCATION_COL, nullable = false)
    private boolean viaLocation;
    
    @Column(name = IS_TURN_AROUND_LOCATION_COL, nullable = false)
    private boolean turnAroundLocation;
    
    public BusArrival() {
        
    }

    public int getBusArrivalId() {
        return busArrivalId;
    }

    public void setBusArrivalId(int busArrivalId) {
        this.busArrivalId = busArrivalId;
    }

    public void setArrivalSequence(int arrivalSequence) {
        this.arrivalSequence = arrivalSequence;
    }

    public Location getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(Location arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public BusDeparture getBusDeparture() {
        return busDeparture;
    }

    public void setBusDeparture(BusDeparture busDeparture) {
        this.busDeparture = busDeparture;
    }

    public boolean isViaLocation() {
        return viaLocation;
    }

    public int getArrivalSequence() {
        return arrivalSequence;
    }

    
    public void setViaLocation(boolean viaLocation) {
        this.viaLocation = viaLocation;
    }

    public boolean isTurnAroundLocation() {
        return turnAroundLocation;
    }

    public void setTurnAroundLocation(boolean turnAroundLocation) {
        this.turnAroundLocation = turnAroundLocation;
    }

    
    
    
}
