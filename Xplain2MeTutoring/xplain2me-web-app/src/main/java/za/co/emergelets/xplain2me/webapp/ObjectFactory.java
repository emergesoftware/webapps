package za.co.emergelets.xplain2me.webapp;

import za.co.emergelets.xplain2me.entity.Citizenship;
import za.co.emergelets.xplain2me.entity.ContactDetail;
import za.co.emergelets.xplain2me.entity.Gender;
import za.co.emergelets.xplain2me.entity.Person;
import za.co.emergelets.xplain2me.entity.PhysicalAddress;

public interface ObjectFactory {
    
    public Person createPersonEntity(Citizenship citizenship, Gender gender);
    public Gender createGenderEntity();
    public Citizenship createCitizenshipEntity();
    public ContactDetail createContactDetailEntity();
    public PhysicalAddress createPhysicalAddressEntity();
    
}
