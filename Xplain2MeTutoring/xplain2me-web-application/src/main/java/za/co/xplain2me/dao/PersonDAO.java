package za.co.xplain2me.dao;

import za.co.xplain2me.entity.Person;

public interface PersonDAO {
    
    /**
     * Checks if the Person entity is completely unique.
     * 
     * @param person - the Person entity to evaluate
     * @param inlcudeInCheck - If set to TRUE then the current Person will be
     *                        included in the check - if set to FALSE other
     *                        entities will be checked except for this one.
     * @return
     * @throws DataAccessException 
     */
    public boolean isPersonCompletelyUnique(Person person, boolean inlcudeInCheck)
            throws DataAccessException;
    
    /**
     * Gets a person information provided their User username.
     * 
     * @param username
     * @return
     * @throws DataAccessException 
     */
    public Person getPerson(String username) throws DataAccessException;
    
    /**
     * Updates the Person entity.
     * 
     * @param person
     * @return
     * @throws DataAccessException 
     */
    public Person updatePerson(Person person) throws DataAccessException;
    
}
