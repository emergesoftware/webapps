
<%@page import="za.co.xplain2me.entity.SubjectsTutoredBefore"%>
<%@page import="za.co.xplain2me.util.BooleanToText"%>
<%@page import="za.co.xplain2me.util.DateTimeUtils"%>
<%@page import="za.co.xplain2me.entity.BecomeTutorRequest"%>

<%
    
    BecomeTutorRequest becomeTutorRequest = (BecomeTutorRequest)request
            .getAttribute(BecomeTutorRequest.class.getName());
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
    <head>
        <title>View Tutor Job Application Details</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <h2>View Tutor Job Application Details</h2>
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <div class="row">
                    
                    <%
                        if (becomeTutorRequest != null) {
                    %>
                    
                    <div class="col-md-12">
                        
                        <br/>

                        <table border="0" class="table table-stripped" style="font-size: 90%">
                            <tbody>
                                <tr>
                                    <td><strong>SEQUENCE ID:</strong></td>
                                    <td><%= becomeTutorRequest.getId() %></td>
                                </tr>
                                <tr>
                                    <td><strong>LAST NAME:</strong></td>
                                    <td><%= becomeTutorRequest.getLastName() %></td>
                                </tr>
                                <tr>
                                    <td><strong>FIRST NAMES:</strong></td>
                                    <td><%= becomeTutorRequest.getFirstNames() %></td>
                                </tr>
                                <tr>
                                    <td><strong>CONTACT NUMBER:</strong></td>
                                    <td><%= becomeTutorRequest.getContactNumber() %></td>
                                </tr>
                                <tr>
                                    <td><strong>EMAIL ADDRESS:</strong></td>
                                    <td><%= becomeTutorRequest.getEmailAddress() %></td>
                                </tr>
                                <tr>
                                    <td><strong>STREET ADDRESS:</strong></td>
                                    <td><%= becomeTutorRequest.getStreetAddress() %></td>
                                </tr>
                                <tr>
                                    <td><strong>TOWN OR SUBURB:</strong></td>
                                    <td><%= becomeTutorRequest.getSuburb() %></td>
                                </tr>
                                <tr>
                                    <td><strong>CITY:</strong></td>
                                    <td><%= becomeTutorRequest.getCity() %></td>
                                </tr>
                                <tr>
                                    <td><strong>AREA CODE:</strong></td>
                                    <td><%= becomeTutorRequest.getAreaCode() %></td>
                                </tr>
                                <tr>
                                    <td><strong>ID / PASSPORT:</strong></td>
                                    <td><%= becomeTutorRequest.getIdentityNumber() %></td>
                                </tr>
                                <tr>
                                    <td><strong>DATE OF BIRTH:</strong></td>
                                    <td><%= DateTimeUtils.formatDateTime(becomeTutorRequest.getDateOfBirth()) %></td>
                                </tr>
                                <tr>
                                    <td><strong>CITIZENSHIP:</strong></td>
                                    <td><%= becomeTutorRequest.getCitizenship().getDescription() %></td>
                                </tr>
                                <tr>
                                    <td><strong>GENDER:</strong></td>
                                    <td><%= becomeTutorRequest.getGender().getDescription() %></td>
                                </tr>
                                <tr>
                                    <td><strong>DATE SUBMITTED:</strong></td>
                                    <td><%= DateTimeUtils.formatDateTime(becomeTutorRequest.getDateSubmitted()) %></td>
                                </tr>
                                <tr>
                                    <td><strong>HAS TUTORED BEFORE:</strong></td>
                                    <td><%= BooleanToText.format(becomeTutorRequest.isTutoredBefore(), BooleanToText.YES_NO_FORMAT) %></td>
                                </tr>
                                <tr>
                                    <td><strong>MOTIVATIONAL TEXT:</strong></td>
                                    <td><%= becomeTutorRequest.getMotivationalText() %></td>
                                </tr>
                                <tr>
                                    <td><strong>ACADEMIC LEVELS TUTORED BEFORE:</strong></td>
                                    <td>
                                        <% 
                                            if (becomeTutorRequest.isTutoredBefore() == false) {
                                                %>
                                        Has no tutoring experience.
                                        <%
                                            }
                                            else {
                                                %>
                                                <ul>
                                        <%
                                                for (SubjectsTutoredBefore tutoredBefore : 
                                                        becomeTutorRequest.getSubjectsTutoredBefore()  ) {
                                                    
                                                    %>
                                                    <li><%= tutoredBefore.getSubject().getName() %></li>
                                                    <%
                                                }
                                                
                                                %>
                                                </ul>
                                        <%
                                            }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        
                                        <a  title="Download supporting documents" 
                                            class="btn btn-primary btn-sm" target="_blank"
                                            href="<%= request.getContextPath() + RequestMappings.DOWNLOAD_TUTOR_JOB_APPLICATION_SUPPORTING_DOCUMENTS + "?id=" + becomeTutorRequest.getId() %>">
                                            <span class="glyphicon glyphicon-save"></span>
                                            <span>&nbsp;Download supporting documents</span>
                                        </a>

                                        <a title="Approve / Accept" 
                                           class="btn btn-primary btn-sm"
                                            href="<%= request.getContextPath() + RequestMappings.APPROVE_TUTOR_JOB_APPLICATION + "?id=" + becomeTutorRequest.getId() %>">
                                            <span class="glyphicon glyphicon-hand-right"></span>
                                            <span>&nbsp;Approve / Accept</span>
                                        </a>
                                        
                                        <a title="Compose Email" 
                                           class="btn btn-primary btn-sm" target="_blank"
                                            href="mailto:<%= becomeTutorRequest.getEmailAddress().toLowerCase() %>">
                                            <span class="glyphicon glyphicon-envelope"></span>
                                            <span>&nbsp;Compose Email</span>
                                        </a>
                                            
                                        <a title="Call Applicant" 
                                           class="btn btn-primary btn-sm" target="_blank"
                                            href="tel:<%= becomeTutorRequest.getContactNumber() %>">
                                            <span class="glyphicon glyphicon-earphone"></span>
                                            <span>&nbsp;Call Applicant</span>
                                        </a>
                                        
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        
                    </div>
                    
                    <%
                        }
                    %>
                </div>
                
            </div>
            
            <%@include file="../jspf/template/default-footer.jspf" %> 
            
        </div>
    </body>
</html>
