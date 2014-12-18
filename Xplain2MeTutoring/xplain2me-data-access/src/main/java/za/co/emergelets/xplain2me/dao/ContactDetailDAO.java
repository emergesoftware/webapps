package za.co.emergelets.xplain2me.dao;

import za.co.emergelets.xplain2me.entity.ContactDetail;

public interface ContactDetailDAO {
    
    /**
     * GETS THE CONTACT DETAIL ENTITY
     * BY ID
     * 
     * @param contactDetailId
     * @return
     * @throws DataAccessException 
     */
    public ContactDetail getContactDetailById(long contactDetailId)
            throws DataAccessException;
    
    /**
     * GETS THE CONTACT DETAIL ENTITY
     * BY EMAIL ADDRESS
     * 
     * @param emailAddress
     * @return
     * @throws DataAccessException 
     */
    public ContactDetail getContactDetailByEmailAddress(String emailAddress)
            throws DataAccessException;
    
    /**
     * GETS THE CONTACT DETAIL ENTITY
     * BY CONTACT NUMBER
     * 
     * @param contactNumber
     * @return
     * @throws DataAccessException 
     */
    public ContactDetail getContactDetailByContactNumber(String contactNumber)
            throws DataAccessException;
    
    /**
     * CHECKS IF THE CONTACT DETAIL ENTITY
     * CONTAINS COMPLETELY UNIQUE 
     * EITHER INCLUSIVE OF OTHERS OR NOT.
     * 
     * @param contactDetail - The ContactDetail entity to check
     * @param includeInCheck - If TRUE then the entity will be included in the
     *                        check, then if FALSE then the entity will be excluded.
     * @return
     * @throws DataAccessException 
     */
    public boolean isContactDetailCompletelyUnique(ContactDetail contactDetail, 
            boolean includeInCheck)
            throws DataAccessException;
    
    /**
     * UPDATES AN EXISTING CONTACT
     * DETAIL ENTITY
     * 
     * @param contactDetail - the ContactDetail entity to update or merge.
     * @return
     * @throws DataAccessException 
     */
    public ContactDetail updateContactDetail(ContactDetail contactDetail)
            throws DataAccessException;
    
}
