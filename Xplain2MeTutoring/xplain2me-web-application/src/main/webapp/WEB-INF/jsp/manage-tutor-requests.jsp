
<%@page import="za.co.xplain2me.util.DateTimeUtils"%>
<%@page import="za.co.xplain2me.entity.Audit"%>
<%@page import="za.co.xplain2me.entity.TutorRequestSubject"%>
<%@page import="za.co.xplain2me.entity.TutorRequest"%>
<%@page import="za.co.xplain2me.webapp.component.TutorRequestsManagementForm"%>

<%
    // get the form
    TutorRequestsManagementForm form = (TutorRequestsManagementForm)session
            .getAttribute(TutorRequestsManagementForm.class.getName());
    
    if (form == null) {
        form = new TutorRequestsManagementForm();
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
                
                <h2>Tutor Requests</h2>
                
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <div class="row">
                    <div class="col-md-12">
                        <!-- START: new tutor requests -->
                        <div class="panel panel-primary">

                            <div class="panel-heading">
                               <h3 class="panel-title">New Tutor Requests</h3>
                            </div>

                            <div class="panel-body">

                                 <%
                            int count = form.getUnreadTutorRequests().size();
                            if (count == 0) {

                                %>
                                <p>
                                    <span class="glyphicon glyphicon-bell"></span>
                                    <span>&nbsp;</span>
                                    <span>You have no new or unread tutor requests.</span>
                                </p>
                            <%
                            }

                            else {

                                %>

                                <p>
                                    <span class="glyphicon glyphicon-bell"></span>
                                    <span>&nbsp;</span>
                                    <span>You have <%= count %> new or unread tutor requests.</span>
                                </p>

                                <table class="table table-condensed table-hover">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>#</th>
                                            <th>Reference</th>
                                            <th>Full Names</th>
                                            <th>Email Address</th>
                                            <th>Contact Number</th>
                                            <th>Address</th>
                                            <th>Academic Level</th>
                                            <th>Subjects</th>
                                            
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            for (Long key : form.getUnreadTutorRequests().keySet()) {
                                                TutorRequest item = form.getUnreadTutorRequests().get(key); 

                                                %>
                                            <tr id="tutorRequestRowEntryNo_<%= item.getId() %>">
                                                
                                                <!-- actions -->
                                                <td class="text-primary">

                                                    <div class="btn-group">

                                                        <button type="button" data-toggle="dropdown" 
                                                                class="btn btn-primary btn-sm dropdown-toggle">
                                                            <span class="glyphicon glyphicon-menu-down"></span>
                                                        </button>

                                                        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
                                                            <li>
                                                                <a  title="Mark This Request As Read"
                                                                    href="<%=request.getContextPath() %>/portal/tutor-requests/mark-as-read?requestId=<%= item.getId() %>">
                                                                    <span class="glyphicon glyphicon-bookmark"></span>
                                                                    &nbsp;Mark As Read
                                                                </a>
                                                            </li>
                                                             <li>
                                                                <a  title="Show more details"
                                                                    href="<%=request.getContextPath() %>/portal/tutor-requests/details?requestId=<%= item.getId() %>">
                                                                    <span class="glyphicon glyphicon-th-list"></span>
                                                                    &nbsp;Show Details
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a title="Send requestee an email" href="mailto:<%= item.getEmailAddress() %>"
                                                                   target="_blank">
                                                                    <span class="glyphicon glyphicon-envelope"></span>
                                                                    &nbsp;Compose Email
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a title="Remove this request"
                                                                    href="<%=request.getContextPath() %>/portal/tutor-requests/remove?requestId=<%= item.getId() %>">
                                                                    <span class="glyphicon glyphicon-bookmark"></span>
                                                                    &nbsp;Remove
                                                                </a>
                                                            </li>
                                                        </ul>

                                                    </div>
                                                </td>
                                                
                                                <td>
                                                    <%= item.getId() %>
                                                </td>
                                                <td>
                                                    <%= item.getReferenceNumber() %>
                                                </td>
                                                <td>
                                                    <%= item.getLastName() + ", " 
                                                            + item.getFirstNames() %>
                                                </td>
                                                <td>
                                                    <%= item.getEmailAddress() %>
                                                </td>
                                                <td>
                                                    <%= item.getContactNumber() %>
                                                </td>
                                                <td>
                                                    <%= item.getPhysicalAddress() + ", " + 
                                                            item.getSuburb() + ", " + 
                                                            item.getCity() + " " + 
                                                            item.getAreaCode() %>
                                                </td>
                                                <td>
                                                    <%= item.getGradeLevel().getDescription() %>
                                                </td>
                                                <td>
                                                    <ul>
                                                    <% 
                                                        for (TutorRequestSubject subject : item.getSubjects()) {
                                                            %>
                                                            <li><%= subject.getSubject().getName() %></li>
                                                        <%
                                                        }
                                                    %>
                                                    </ul>
                                                </td>

                                                
                                            </tr>

                                        <%

                                            }
                                        %> 

                                    </tbody>
                                </table>

                                <%
                            }
                        %>

                             </div>
                         </div>
                        <!-- END: new tutor requests -->
                    </div>
                </div>
                        
                <div class="row">
                    
                    <div class="col-md-12">
                        <!-- START: previous tutor requests audits -->
                        <div class="panel panel-primary">

                            <div class="panel-heading">
                               <h3 class="panel-title">Previous Tutor Request Audits</h3>
                            </div>

                            <div class="panel-body">
                                <br/>
                                
                                <%
                                    int auditTrailCount = form.getAuditTrail().size();
                                    if (auditTrailCount == 0) {
                                        %>
                                        <p>You have no audit trail for tutor request management.</p>
                                    <%
                                    }
                                    
                                    else {
                                    %>
                                    <table border="0" class="table table-stripped">
                                        <thead>
                                            <tr>
                                                <th>Sequence #</th>
                                                <th>Date + Time</th>
                                                <th>Event Code</th>
                                                <th>Description</th>
                                                <th>Tutor Request #</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            
                                            <%
                                                for (Audit audit : form.getAuditTrail()) {
                                                    %>
                                            
                                                    <tr>
                                                        <td><%= audit.getId() %></td>
                                                        <td><%= DateTimeUtils.formatDateTime(audit.getTimestamp()) %></td>
                                                        <td><%= audit.getEvent().getType() %></td>
                                                        <td><%= audit.getEvent().getShortDescription() %></td>
                                                        <td><%= audit.getReference() %></td>
                                                    </tr>
                                                    
                                            <%
                                                    
                                                }
                                            %>
                                            
                                        </tbody>
                                        
                                    </table>
                                        <%
                                    }
                                %>
                                
                            </div>
                        </div>
                        <!-- END: previous tutor requests audits -->
                    </div>
                   
                </div>
                    
                <br/>
                
            </div>
             
            <%@include file="../jspf/template/default-manager-footer.jspf" %>          
            
        </div>
        
    </body>
</html>
