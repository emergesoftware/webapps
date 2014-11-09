/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.emergelets.metrobus.webservice;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;
import za.co.metrobus.hibernate.dao.BusServiceDAO;
import za.co.metrobus.hibernate.dao.BusServiceDAOImpl;
import za.co.metrobus.hibernate.entity.BusRoute;
import za.co.metrobus.hibernate.entity.BusServiceType;
import za.co.metrobus.hibernate.entity.Location;

/**
 *
 * @author user
 */
@WebService(serviceName = "MetroBusWebService")
public class MetroBusWebService {
    
    private BusServiceDAO data;
    
    public MetroBusWebService() {
        this.data = new BusServiceDAOImpl(null);
    }
    
    /**
     * Gets a list of all buses that operate on the 
     * specified day of service
     * 
     * @param type
     * @return
     * @throws WebServiceException 
     */
    @WebMethod(operationName = "getAllBusRoutesOperatingOn")
    public List<BusRoute> getAllBusRoutesOperatingOn(
            @WebParam(name = "type")BusServiceType type) 
            throws WebServiceException {
        
        return data.getAllBusRoutes(type);
        
    }
    
    /**
     * Gets the bus route information provided its
     * route number is specified
     * 
     * @param routeNumber
     * @return
     * @throws WebServiceException 
     */
    @WebMethod(operationName = "getBusRouteByRouteNumber")
    public BusRoute getBusRouteByRouteNumber(
            @WebParam(name = "routeNumber")String routeNumber)
            throws WebServiceException {
        
        if (routeNumber == null || routeNumber.isEmpty())
            return null;
        
        BusRoute route = data.getBusRouteByRouteNumber(routeNumber);
        return route;
    }
    
    /**
     * Gets a list of all the locations
     * from which the bus route departs from
     * 
     * @param routeNumber
     * @return
     * @throws WebServiceException 
     */
    public List<Location> getDepartureLocationsFromBusRoute(String routeNumber) 
            throws WebServiceException {
        
        if (routeNumber == null || routeNumber.isEmpty()) 
            return null;
       
        return data.getDepartureLocationsFromBusRoute(routeNumber);
        
    }
    
}
