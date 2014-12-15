package za.co.emergelets.xplain2me.dao;

import za.co.emergelets.xplain2me.entity.Person;

public interface PersonDAO {
    
    public Person getPerson(String username) throws DataAccessException;
    public Person updatePerson(Person person) throws DataAccessException;
    
}
