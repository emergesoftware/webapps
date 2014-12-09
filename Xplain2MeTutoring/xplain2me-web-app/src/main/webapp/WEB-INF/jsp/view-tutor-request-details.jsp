<%@page import="za.co.emergelets.util.BooleanToText"%>
<%@page import="za.co.emergelets.xplain2me.webapp.controller.RequestMappings"%>
<%@page import="za.co.emergelets.util.DateTimeUtils"%>
<%@page import="za.co.emergelets.xplain2me.entity.TutorRequestSubject"%>
<%@page import="za.co.emergelets.xplain2me.entity.TutorRequest"%>
<%
    // get the tutor request from the
    // request scope
    TutorRequest tutorRequest = (TutorRequest)request
            .getAttribute(TutorRequest.class.getName());
    
    if (tutorRequest == null) {
        response.sendRedirect(RequestMappings.LOGOUT);
        return;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Tutor Requests</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        
    </head>
    <body>
        
      
        
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <h2>Tutor Request Details</h2>
                
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <br/>
                
                <div class="row">
                    
                    <div class="col-md-12">
                        
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">
                                    Actions:
                                </h3>
                            </div>
                            
                            <div class="panel-body">

                                <a  title="Mark This Request As Read" 
                                    class="btn btn-primary btn-sm"
                                    href="<%=request.getContextPath() %>/portal/tutor-requests/mark-as-read?requestId=<%= tutorRequest.getId() %>">
                                    <span class="glyphicon glyphicon-bookmark"></span>
                                    <span>&nbsp;Mark As Read</span>
                                </a>

                                <a title="Remove this request" 
                                   class="btn btn-primary btn-sm"
                                    href="<%=request.getContextPath() %>/portal/tutor-requests/remove?requestId=<%= tutorRequest.getId() %>">
                                    <span class="glyphicon glyphicon-remove"></span>
                                    <span>&nbsp;Remove</span>
                                </a>
                            
                        </div>
                            
                        </div>
                        
                        
                    </div>
                    
                    <div class="col-md-12">
                        <!-- START: search tutor requests -->
                        <div class="panel panel-primary">

                            <div class="panel-heading">
                               <h3 class="panel-title">
                                   Tutor request #<%= tutorRequest.getId() %>
                                   details:
                               </h3>
                            </div>
                                   
                            <div class="panel-body">
                                <br/>
                                
                                
                                
                                <br/>
                                <table border="0" class="table table-stripped">
                                    <tr>
                                        <td><strong>Sequence #</strong></td>
                                        <td><%= tutorRequest.getId() %></td>
                                    </tr>
                                    <tr>
                                        <td><strong>First Names</strong></td>
                                        <td><%= tutorRequest.getFirstNames() %></td>
                                    </tr>
                                    <tr>
                                        <td><strong>Last Name</strong></td>
                                        <td><%= tutorRequest.getLastName() %></td>
                                    </tr>
                                    <tr>
                                        <td><strong>Gender</strong></td>
                                        <td><%= BooleanToText.format(tutorRequest.isGender(), 
                                                BooleanToText.GENDER_FORMAT) %></td>
                                    </tr>
                                    <tr>
                                        <td><strong>Email Address</strong></td>
                                        <td>
                                            <span><%= tutorRequest.getEmailAddress() %></span>
                                            <span>&nbsp;&nbsp;</span>
                                            <span>
                                                <a title="Send requestee an email" href="mailto:<%= tutorRequest.getEmailAddress() %>"
                                                   class="btn btn-primary btn-sm" target="_blank">
                                                    <span class="glyphicon glyphicon-envelope"></span>
                                                    <span>&nbsp;Email</span>
                                                </a>
                                            </span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><strong>Contact Number</strong></td>
                                        <td>
                                            <span><%= tutorRequest.getContactNumber() %></span>
                                            <span>&nbsp;&nbsp;</span>
                                            <span>
                                                <a title="Call the requestee" href="tel:<%= ("27" + tutorRequest.getContactNumber().substring(1)) %>"
                                                   class="btn btn-primary btn-sm" target="_blank">
                                                    <span class="glyphicon glyphicon-earphone"></span>
                                                    <span>&nbsp;Call</span>
                                                </a>
                                            </span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><strong>Physical Address</strong></td>
                                        <td>
                                            <%= 
                                                tutorRequest.getPhysicalAddress() + "<br/>" +
                                                tutorRequest.getSuburb() + "<br/>" +
                                                tutorRequest.getCity() + "<br/>" + 
                                                tutorRequest.getAreaCode()
                                            %>
                                            <br/><br/>
                                            
                                            <%
                                                String googleMapsUrl = "http://maps.google.com/?q=" + 
                                                        tutorRequest.getPhysicalAddress() + ", " + 
                                                        tutorRequest.getSuburb() + ", " + 
                                                        tutorRequest.getCity();
                                            %>
                                            
                                            <a title="Find on map" href="<%= googleMapsUrl %>"
                                                class="btn btn-primary btn-sm" target="_blank">
                                                 <span class="glyphicon glyphicon-map-marker"></span>
                                                 <span>&nbsp;Locate</span>
                                             </a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><strong>Academic Level</strong></td>
                                        <td><%= tutorRequest.getGradeLevel().getDescription() %></td>
                                    </tr>
                                    <tr>
                                        <td><strong>Date + Time</strong></td>
                                        <td><%= DateTimeUtils.formatDateTime(
                                                tutorRequest.getDateReceived()
                                        ) %></td>
                                    </tr>
                                    <tr>
                                        <td><strong>Reference #</strong></td>
                                        <td><%= tutorRequest.getReferenceNumber() %></td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <strong>Subjects</strong>
                                        </td>
                                        <td>
                                            <ul>
                                            <%
                                                for (TutorRequestSubject subject : tutorRequest.getSubjects()) {
                                                    %>
                                                    <li><%= subject.getSubject().getName() %></li>
                                                <%
                                                }
                                            %>
                                            </ul>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <strong>
                                                Learner special needs <br/>
                                                and other specified <br/>
                                                additional information
                                            </strong>
                                        </td>
                                        <td>
                                            <% 
                                                String content = tutorRequest.getAdditionalInformation();
                                                if (content == null || content.isEmpty())
                                                    content = "[NOT SPECIFIED]";
                                            %>
                                            <p><%= content %></p>
                                        </td>
                                    </tr> 
                                </table>
                                
                            </div>
                            
                        </div>
                    </div>
                    
                   
                   
                </div>
                
              
                <br/>
                
            </div>
             
            <%@include file="../jspf/template/default-footer.jspf" %>              
            
        </div>
        
    </body>
</html>
