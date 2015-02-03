package za.co.xplain2me.webapp.controller.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Component;
import za.co.xplain2me.dao.ProfileDAO;
import za.co.xplain2me.dao.ProfileDAOImpl;
import za.co.xplain2me.dao.StudentDao;
import za.co.xplain2me.dao.StudentDaoImpl;
import za.co.xplain2me.entity.AcademicLevel;
import za.co.xplain2me.entity.Lesson;
import za.co.xplain2me.entity.LessonStatus;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.entity.Student;
import za.co.xplain2me.entity.Subject;
import za.co.xplain2me.util.AdvancedDataStructuresFactory;
import za.co.xplain2me.util.DateTimeUtils;
import za.co.xplain2me.util.NumericUtils;
import za.co.xplain2me.webapp.component.LessonManagementForm;
import za.co.xplain2me.webapp.component.UserContext;
import za.co.xplain2me.webapp.controller.GenericController;

@Component
public class LessonManagementControllerHelper extends GenericController implements Serializable {
    
    private static final Logger LOG = 
            Logger.getLogger(LessonManagementControllerHelper.class.getName(), null);
    
    public LessonManagementControllerHelper() {
    }
    
    /**
     * Validates the add new lesson parameters
     * 
     * @param request
     * @return 
     */
    public List<String> validateAddNewLessonParameters(HttpServletRequest request) {
        
        List<String> errorsEncountered = 
                AdvancedDataStructuresFactory.createArrayList();
        
        // validate the number of lessons
        String numberOfLessons = getParameterValue(request, "numberOfLessons");
        if (numberOfLessons == null || numberOfLessons.isEmpty() ||
                NumericUtils.isNaN(numberOfLessons) || 
                !NumericUtils.isBetween(Integer.parseInt(numberOfLessons), 1, 99)) {
            
            errorsEncountered.add("The number of lessons must be a valid numerical value "
                    + "that lies in between 1 and 99.");
        }
        
        // validate the student
        String student = getParameterValue(request, "student");
        if (student == null || student.isEmpty()) {
            errorsEncountered.add("A student must be assigned to the lessons");
        }
        
        // validate if should find next available tutors
        String findNextAvailableTutors = getParameterValue(request, "findNextAvailableTutors");
        if (findNextAvailableTutors == null || 
                findNextAvailableTutors.isEmpty() ||
                (!findNextAvailableTutors.equalsIgnoreCase("yes") &&
                !findNextAvailableTutors.equalsIgnoreCase("no"))) {
            
            errorsEncountered.add("Indicate if you want to have the lessons assigned to the "
                    + "next available tutors.");
        }
        
        // validate the lessons start date
        String lessonsStartDate = getParameterValue(request, "lessonsStartDate");
        Date today = new Date();
        if (lessonsStartDate == null || 
                lessonsStartDate.isEmpty() || 
                DateTimeUtils.parseDate(lessonsStartDate) == null || 
                DateTimeUtils.parseDate(lessonsStartDate).before(today) || 
                Days.daysBetween(new DateTime(today),
                        new DateTime(DateTimeUtils.parseDate(lessonsStartDate))).getDays() > (30 * 6)) {
            
            errorsEncountered.add("The start date for the lessons does not appear valid and "
                    + "the date should not be after 180 days from today.");
        
        }
        
        // validate the selected days of the week
        String[] selectedDaysOfWeek = getParameterValues(request, "selectedDaysOfWeek");
        if (selectedDaysOfWeek == null || 
                selectedDaysOfWeek.length == 0) {
            errorsEncountered.add("You need to select the days of the week at which "
                    + "these lessons will be taking place.");
        }
        
        // validate the times for each selected day of the week
        if (selectedDaysOfWeek != null) {
            for (String dayOfWeek : selectedDaysOfWeek) {
                
                String hourEntered = dayOfWeek + "_hour";
                String minuteEntered = dayOfWeek + "_minute";
                
                if (getParameterValue(request, hourEntered) == null || 
                        getParameterValue(request, minuteEntered) == null) {
                    
                    errorsEncountered.add("Ensure that you have entered valid times for "
                            + "the day of the week selected: " 
                            + DateTimeUtils.getDayOfWeek(Integer.parseInt(dayOfWeek)));
                    
                }
                
            }
        }
        
        // validate the length of the lessons
        String lengthOfLessons = getParameterValue(request, "lengthOfLessons");
        if (lengthOfLessons == null || 
                lengthOfLessons.isEmpty() || 
                NumericUtils.isNaN(lengthOfLessons) || 
                !NumericUtils.isBetween(Double.parseDouble(lengthOfLessons), 0.5, 6.0)) {
            
            errorsEncountered.add("The length in hours for the lessons is not valid.");
            
        }
        
        return errorsEncountered;
    }

    /**
     * Creates a list of lesson entities
     * from the HTTP request parameters.
     * 
     * @param request 
     * @param form 
     * @return  
     * @throws java.lang.IllegalAccessException  
     */
    public List<Lesson> createLessonsFromParameters(HttpServletRequest request, 
            LessonManagementForm form) throws IllegalAccessException {
        
        List<Lesson> lessons = new ArrayList<>();
        
        // get the number of lessons
        int numberOfLessons = Integer.parseInt(getParameterValue(
                request, "numberOfLessons"));
        // get the start date of the lessons
        Date startDate = DateTimeUtils.parseDate(getParameterValue(
                request, "lessonsStartDate"));
        // get the user context
        UserContext context = (UserContext)getFromSessionScope(request,
                UserContext.class);
        
        // get the learner assigned to the lessons
        String studentParam = getParameterValue(request, "student");
        long studentId = Long.parseLong(studentParam.substring(0, 
                studentParam.indexOf("-") - 1));
        
        StudentDao studentDao = new StudentDaoImpl();
        Student student = studentDao.getStudentById(studentId, context.getProfile());
        
        // get the subject
        String subjectId = getParameterValue(request, "subject");
        Subject subject = form.getSubjects().get(Long.parseLong(subjectId));
        
        // get the academic level
        AcademicLevel academicLevel = subject.getAcademicLevel();
        
        // get the lesson status
        String lessonStatusId = getParameterValue(request, "lessonStatus");
        LessonStatus lessonStatus = form.getLessonStatus()
                .get(Long.parseLong(lessonStatusId));
        
        // get the length of the lessons
        double lengthOfLessons = Double.parseDouble(getParameterValue(request, "lengthOfLessons"));
        
        // get the selected days of week
        String[] selectedDaysOfWeek = getParameterValues(request, "selectedDaysOfWeek");
        
        // get the calendar instance
        // and set the start date of the lessons
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate); 
        int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        
        // for the each lesson of the number of lessons
        int dayOfWeekCursor = -1;
        boolean startDayIsCurrentDay = false;
        
        for (int i = 0; i < numberOfLessons; i++) {
            
            dayOfWeekCursor++;
            
            // assert the cursor
            if (dayOfWeekCursor >= selectedDaysOfWeek.length)
                dayOfWeekCursor = 0;
            
            Lesson lesson = new Lesson();
            // set the student in this lesson
            lesson.setStudent(student); 
            // set the subject
            lesson.setAcademicLevel(academicLevel);
            // set the lesson status - as pending
            lesson.setLessonStatus(lessonStatus);
            // set the subject
            lesson.setSubject(subject);
            // set the lesson as being unclaimed
            lesson.setClaimed(false);
            // set the length of the lesson
            lesson.setScheduledLengthOfLesson(lengthOfLessons);
            
            // determine the next date from the 
            // start date of the lessons
            int nextDayOfWeekForLesson = Integer.parseInt(
                    selectedDaysOfWeek[dayOfWeekCursor]);
            
            if (startDayIsCurrentDay == false && 
                    calendar.get(Calendar.DAY_OF_WEEK) == nextDayOfWeekForLesson) {
                
                // get the hour of the lesson for the day
                int hour = getHourForDaysLesson(nextDayOfWeekForLesson, request);
                int minute = getMinuteForDaysLesson(nextDayOfWeekForLesson, request);
                
                // set the time
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                
                // set the date of the lesson
                lesson.setScheduledDateAndTime(calendar.getTime());
                
                startDayIsCurrentDay = true;
            }
            
            else {
                
                // determine the date of the next lesson
                int add = 0;
                
                if (currentDayOfWeek < nextDayOfWeekForLesson) {
                    
                    add = (Math.abs(currentDayOfWeek - nextDayOfWeekForLesson));
                    calendar.add(Calendar.DAY_OF_WEEK, add);
                    
                }
                
                else if ((currentDayOfWeek > nextDayOfWeekForLesson) || 
                        (currentDayOfWeek == nextDayOfWeekForLesson)) {
                    
                    add = 7 - (Math.abs(currentDayOfWeek - nextDayOfWeekForLesson));
                    calendar.add(Calendar.DAY_OF_WEEK, add);
                    
                }

                // get the time for this lesson
                // get the hour of the lesson for the day
                int hour = getHourForDaysLesson(nextDayOfWeekForLesson, request);
                int minute = getMinuteForDaysLesson(nextDayOfWeekForLesson, request);
                
                // set the time
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                
                // set the date of the lesson
                lesson.setScheduledDateAndTime(calendar.getTime());
                
            }
            
            // set this next day of week as current
            // for the next iteration
            currentDayOfWeek = nextDayOfWeekForLesson;
            
            // set the date this lesson was last updated
            lesson.setLastUpdated(new Date()); 
            
            // add the lesson
            lessons.add(lesson);
            
        }
        
        return lessons;
    }
    
    /**
     * Gets the hour of the lesson for the day of 
     * week
     * 
     * @param dayOfWeek
     * @param request
     * @return 
     */
    private int getHourForDaysLesson(int dayOfWeek, HttpServletRequest request) {
        
        String value = getParameterValue(request, dayOfWeek + "_hour");
        
        if (value == null || value.isEmpty() || 
                NumericUtils.isNaN(value)) 
            return 15;
        else return Integer.parseInt(value);
        
    }
    
    /**
     * Gets the hour of the lesson for the day of 
     * week
     * 
     * @param dayOfWeek
     * @param request
     * @return 
     */
    private int getMinuteForDaysLesson(int dayOfWeek, HttpServletRequest request) {
        
        String value = getParameterValue(request, dayOfWeek + "_minute");
        
        if (value == null || value.isEmpty() || 
                NumericUtils.isNaN(value)) 
            return 0;
        
        else return Integer.parseInt(value);
        
    }
}
