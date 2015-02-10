package za.co.xplain2me.test;

import java.util.List;
import za.co.xplain2me.dao.ProfileDAO;
import za.co.xplain2me.dao.ProfileDAOImpl;
import za.co.xplain2me.dao.SubjectDAO;
import za.co.xplain2me.dao.SubjectDAOImpl;
import za.co.xplain2me.dao.TutorDao;
import za.co.xplain2me.dao.TutorDaoImpl;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.entity.Subject;
import za.co.xplain2me.entity.Tutor;


public class TestClass {
    
    public static void main(String... args) throws Exception {
       
        TutorDao tutorDao = new TutorDaoImpl();
        SubjectDAO subjectDao = new SubjectDAOImpl();
        ProfileDAO profileDao = new ProfileDAOImpl();
        
        Subject subject = subjectDao.getSubject(1039);
        Profile profile = profileDao.getProfileById(1, 100);
        
        List<Tutor> tutors = tutorDao.searchTutor(TutorDao.SEARCH_BY_SUBJECT_TUTORING, 
                subject, profile);
        
        for (Tutor tutor : tutors) {
            
            System.out.println("\r\n - name : " + tutor.getProfile().getPerson().getFirstNames());
            
        }
    }
    
}