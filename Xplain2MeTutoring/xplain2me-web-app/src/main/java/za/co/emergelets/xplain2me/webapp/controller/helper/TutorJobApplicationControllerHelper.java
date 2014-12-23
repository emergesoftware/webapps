package za.co.emergelets.xplain2me.webapp.controller.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import za.co.emergelets.util.NumericUtils;
import za.co.emergelets.xplain2me.dao.BecomeTutorRequestDAO;
import za.co.emergelets.xplain2me.dao.BecomeTutorRequestDAOImpl;
import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;
import za.co.emergelets.xplain2me.entity.BecomeTutorSupportingDocument;
import za.co.emergelets.xplain2me.model.TutorJobApproval;
import za.co.emergelets.xplain2me.webapp.component.AlertBlock;
import za.co.emergelets.xplain2me.webapp.component.TutorJobApplicationForm;
import za.co.emergelets.xplain2me.webapp.controller.GenericController;
import za.co.emergelets.xplain2me.webapp.controller.TutorJobApplicationController;

@Component
public class TutorJobApplicationControllerHelper extends GenericController implements Serializable {
    
    // the logger
    private static final Logger LOG = 
            Logger.getLogger(TutorJobApplicationControllerHelper.class.getName(), null);
    
    private final BecomeTutorRequestDAO becomeTutorRequestDAO;
    
    public TutorJobApplicationControllerHelper() {
        this.becomeTutorRequestDAO = new BecomeTutorRequestDAOImpl();
    }

    /**
     * Loads the tutor job requests onto the
     * form.
     * 
     * @param form 
     */
    public void loadTutorJobApplications(TutorJobApplicationForm form) {
        
        if (form == null) {
            LOG.warning(" the form is null - aborted...");
            return;
        }
        
        List<BecomeTutorRequest> list = becomeTutorRequestDAO
                .getBecomeTutorRequests(form.getCurrentPageNumber());
        TreeMap<Long, BecomeTutorRequest> map = new TreeMap<>();
        
        if (list != null && !list.isEmpty()) {
            for (BecomeTutorRequest request : list) 
                map.put(request.getId(), request);
            
            form.setTutorJobApplications(map);
            form.setCannotGoForward(false);
        }
        
        else {
            
            form.setCurrentPageNumber(form.getCurrentPageNumber() - 1);
            form.setCannotGoForward(true);
        }
    }

    /**
     * Creates an instance of the Tutor Job Approval 
     * model.
     * 
     * @param request
     * @param form
     * @return 
     */
    public TutorJobApproval createTutorJobApproval(HttpServletRequest request, 
            TutorJobApplicationForm form) {
        
        TutorJobApproval approval = new TutorJobApproval();
        
        // try to parse the date of interview
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            approval.setDateOfInterview(formatter.parse(
                    getParameterValue(request, "dateOfInterview")
            ));
        }
        catch (ParseException e) {
            LOG.severe("... an error occured while attempting to parse the "
                        + "date of the interview... error: " + e.getMessage());
            approval.setDateOfInterview(null);
        }
        
        // try to parse the time of interview
        formatter = new SimpleDateFormat("HH:mm");
        try {
            
            approval.setTimeOfInterview(formatter.parse(
                    getParameterValue(request, "hourOfInterview") + ":" + 
                            getParameterValue(request, "minuteOfInterview")
            ));
            
        }
        catch (ParseException e) {
            LOG.severe("... an error occured while attempting to parse the "
                        + "time of the interview... error: " + e.getMessage());
            approval.setDateOfInterview(null);
        }
        
        // location of the interview
        approval.setLocation(getParameterValue(request, "location"));
        
        // additional notes
        approval.setAdditionalNotes(getParameterValue(request, "additionalNotes"));
        
        // attach the original application
        approval.setAttachedJobApplication(form.getTutorJobApplication());
        
        return approval;
        
    }
    
    /**
     * Determines the working folder for compressing
     * files.
     * 
     * @return 
     */
    public String determineCompressionWorkingFolder() {
        
        String directory = null;
        if (BecomeTutorControllerHelper.isWindowsOperationSystem())
            directory = BecomeTutorControllerHelper.WIN_FILE_REPOSITORY.replace("{0}", 
                    System.getProperty("user.name")) + "\\";
        else
            directory = BecomeTutorControllerHelper.LINUX_FILE_REPOSITORY + "/";
        
        return directory;
        
    }
    
    /**
     * Writes the supporting document's byte arrays into a 
     * PDF files.
     * 
     * @param documents
     * @param directory
     * @return
     * @throws IOException 
     */
    public Map<String, File> writeSupportingDocumentsToFile(List<BecomeTutorSupportingDocument> documents, String directory) throws IOException {
        
        Map<String, File> files = new HashMap<>();
        
        if ((documents == null || documents.isEmpty()) || 
                (directory == null || directory.isEmpty())) { 
            
            LOG.warning("... either the directory is null or the"
                    + " supporting documents list is empty ...");
            return files;
        }
        
        for (BecomeTutorSupportingDocument document : documents) {
            
            // build the filename as follows
            // {DIRECTORY}/{LABEL}_{ID NUMBER}.pdf
            String label = document.getLabel().replace("/", "").replace("\\", "");
            String filename = (directory + label + "_" +
                    document.getRequest().getIdentityNumber() + 
                    ".pdf").replace(" ", "_");
            
            // get the byte array
            byte[] byteArray = document.getDocument();
            
            // continue if the byte array is empty
            if (byteArray == null || byteArray.length == 0) 
                continue;
            
            // create new file
            File file = new File(filename);
            if (file.exists()) file.delete();
            
            file.createNewFile();
            
            // write the bytes to the file
            FileOutputStream output = new FileOutputStream(file);
            output.write(byteArray); 
            output.close();
            
            // put the file in the map
            files.put(filename, file);
        }
        
        return files;
    }
    
    /**
     * Adds the filename to the ZIP archive.
     * 
     * @param filename
     * @param zipOutputStream
     * @throws IOException 
     */
    public void addFileEntryToZipArchive(String filename, ZipOutputStream zipOutputStream) throws IOException {
        
        if ((filename == null || filename.isEmpty()) ||
                zipOutputStream == null) {
            LOG.warning(" ... either the file or the zip output stream is null ...");
            return;
        }
        
        byte[] buffer = new byte[1024];
        
        // get the next zip file entry
        ZipEntry entry = new ZipEntry(filename);
        zipOutputStream.putNextEntry(entry); 

        // write the file to the zip file
        FileInputStream fileInputStream = new FileInputStream(filename);
        int bytesRead = 0;

        while ((bytesRead = fileInputStream.read(buffer)) >= 0) {
            zipOutputStream.write(buffer, 0, bytesRead);
        }

        zipOutputStream.closeEntry();
        fileInputStream.close();
    }

    /**
     * Appends the HTTP Headers for a ZIP file
     * download.
     * 
     * @param response
     * @param compressedFile 
     */
    public void appendHttpHeadersForZipArchiveFile(HttpServletResponse response, File compressedFile) {
        
        response.setContentType("application/zip, application/octet-stream");
        response.setContentLength((int)compressedFile.length());
        response.setHeader("Content-Disposition", 
                String.format("attachment; filename=\"%s\"", compressedFile.getName()));
    }
    
    /**
     * Resolves any message parameters for
     * tutor job application search
     * 
     * @param request 
     */
    public void resolveAnySearchParameterMessages(HttpServletRequest request) {
        
        String message = getParameterValue(request, "message");
        if (message != null && message.isEmpty() == false &&
                NumericUtils.isNaN(message) == false) {

            AlertBlock alertBlock = new AlertBlock();

            switch (Integer.parseInt(message)) {

                case TutorJobApplicationController.SEARCH_TUTOR_JOB_APPLICATIONS_FAILED:
                    
                    alertBlock = (AlertBlock)getFromSessionScope(request, AlertBlock.class);
                    removeFromSessionScope(request, AlertBlock.class);
                    break;

                case TutorJobApplicationController.SEARCH_TUTOR_JOB_APPLICATIONS_NOT_FOUND:
                    
                    alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING);
                    alertBlock.addAlertBlockMessage("The tutor job application with the "
                            + "previously stated search criteria could not be found.");
                    break;

            }

            if (alertBlock != null && alertBlock.getAlertBlockMessages().isEmpty() == false)
                saveToRequestScope(request, alertBlock);

        }
        
    }
}
