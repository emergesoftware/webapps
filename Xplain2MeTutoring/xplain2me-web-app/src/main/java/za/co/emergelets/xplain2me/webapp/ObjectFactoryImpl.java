package za.co.emergelets.xplain2me.webapp;

import javax.servlet.http.HttpServletRequest;
import za.co.emergelets.util.DateTimeUtils;
import za.co.emergelets.xplain2me.dao.CitizenshipDAO;
import za.co.emergelets.xplain2me.dao.CitizenshipDAOImpl;
import za.co.emergelets.xplain2me.dao.GenderDAO;
import za.co.emergelets.xplain2me.dao.GenderDAOImpl;
import za.co.emergelets.xplain2me.entity.Citizenship;
import za.co.emergelets.xplain2me.entity.ContactDetail;
import za.co.emergelets.xplain2me.entity.Gender;
import za.co.emergelets.xplain2me.entity.Person;
import za.co.emergelets.xplain2me.entity.PhysicalAddress;

public class ObjectFactoryImpl extends GenericObjectFactoryImplementor {
    
    public ObjectFactoryImpl(HttpServletRequest request) {
        super(request);
    }
    
    @Override
    public Person createPersonEntity(Citizenship citizenship, Gender gender) {
        
        Person person = new Person();
        person.setLastName(this.getParameterValue("lastName"));
        person.setFirstNames(this.getParameterValue("firstNames"));
        person.setDateOfBirth(DateTimeUtils.parseDate(this.getParameterValue("dateOfBirth")));
        person.setIdentityNumber(getParameterValue("idNumber"));
        person.setCitizenship(citizenship);
        person.setGender(gender);
        return person;
    }

    @Override
    public Gender createGenderEntity() {
        GenderDAO genderDao = new GenderDAOImpl();
        return genderDao.getGender(getParameterValue("gender"));
    }

    @Override
    public Citizenship createCitizenshipEntity() {
        Long id = Long.parseLong(getParameterValue("citizenship"));
        CitizenshipDAO citizenshipDao = new CitizenshipDAOImpl();
        return citizenshipDao.getCitizenship(id);
    }

    @Override
    public ContactDetail createContactDetailEntity() {
        ContactDetail contactDetail = new ContactDetail();
        contactDetail.setCellphoneNumber(this.getParameterValue("contactNumber"));
        contactDetail.setEmailAddress(this.getParameterValue("emailAddress")); 
        return contactDetail;
    }

    @Override
    public PhysicalAddress createPhysicalAddressEntity() {
        PhysicalAddress address = new PhysicalAddress();
        
        address.setAddressLine1(getParameterValue("physicalAddressLine1"));
        address.setAddressLine2(getParameterValue("physicalAddressLine2"));
        address.setSuburb(getParameterValue("suburb"));
        address.setCity(getParameterValue("city"));
        address.setAreaCode(getParameterValue("areaCode"));
        
        return address;
    }
    
}
