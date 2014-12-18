package za.co.emergelets.xplain2me.dao;

import za.co.emergelets.xplain2me.entity.PhysicalAddress;

public interface PhysicalAddressDAO {
    
    /**
     * Gets the physical address entity
     * by its ID.
     * 
     * @param id
     * @return
     * @throws DataAccessException 
     */
    public PhysicalAddress getPhysicalAddress(long id)
            throws DataAccessException;
    
    /**
     * Updates the physical address entity.
     * 
     * @param address
     * @return
     * @throws DataAccessException 
     */
    public PhysicalAddress updatePhysicalAddress(PhysicalAddress address)
            throws DataAccessException;
}
