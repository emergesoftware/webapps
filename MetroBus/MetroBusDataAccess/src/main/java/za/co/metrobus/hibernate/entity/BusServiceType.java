package za.co.metrobus.hibernate.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tsepo
 */
@XmlRootElement
public enum BusServiceType {
    WeekdayService, SaturdayService, SundayService,
    HolidayService
}
