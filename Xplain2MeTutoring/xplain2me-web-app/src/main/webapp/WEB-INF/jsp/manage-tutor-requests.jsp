<%-- 
    Document   : view-tutor-requests
    Created on : 30 Sep 2014, 8:08:34 PM
    Author     : tsepo maleka
--%>

<%@page import="za.co.emergelets.util.DateTimeUtils"%>
<%@page import="za.co.emergelets.xplain2me.entity.Audit"%>
<%@page import="za.co.emergelets.xplain2me.entity.TutorRequestSubject"%>
<%@page import="za.co.emergelets.xplain2me.entity.TutorRequest"%>
<%@page import="za.co.emergelets.xplain2me.webapp.component.TutorRequestsManagementForm"%>
<%
    // get the form
    TutorRequestsManagementForm form = (TutorRequestsManagementForm)session
            .getAttribute(TutorRequestsManagementForm.class.getName());
    
    if (form == null) {
        response.sendRedirect("/logout");
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
                
                <h2>Tutor Requests</h2>
                
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <br/>
                
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

                                <table border="0" class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Reference</th>
                                            <th>Full Names</th>
                                            <th>Email Address</th>
                                            <th>Contact Number</th>
                                            <th>Address</th>
                                            <th>Academic Level</th>
                                            <th>Subjects</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            for (Long key : form.getUnreadTutorRequests().keySet()) {
                                                TutorRequest item = form.getUnreadTutorRequests().get(key); 

                                                %>
                                            <tr id="tutorRequestRowEntryNo_<%= item.getId() %>">
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

                                                <!-- actions -->
                                                <td class="text-primary" 
                                                    style="font-size: 90%; min-width: 135px">

                                                    <div class="btn-group">

                                                        <button type="button" data-toggle="dropdown" 
                                                                class="btn btn-primary dropdown-toggle">
                                                            Action <span class="caret"></span>
                                                        </button>

                                                        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
                                                            <li>
                                                                <a  ark This Request As Read" style=coration: none"
                                                                    href="<%=request.getContextPath() %>/portal/manager/tutor-requests/mark-as-read?requestId=<%= item.getId() %>">
                                                                    Mark As Read
                                                                </a>
                                                            </li>
                                                             <li>
                                                                <a  title="Show more details" style="text-decoration: none"
                                                                    href="<%=request.getContextPath() %>/portal/manager/tutor-requests/details?requestId=<%= item.getId() %>">
                                                                    Show Details
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a title="Send requestee an email" href="mailto:<%= item.getEmailAddress() %>"
                                                                   target="_blank" style="text-decoration: none">
                                                                    Compose Email
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a title="Remove this request" style="text-decoration: none"
                                                                    href="<%=request.getContextPath() %>/portal/manager/tutor-requests/remove?requestId=<%= item.getId() %>">
                                                                    Remove
                                                                </a>
                                                            </li>
                                                        </ul>

                                                    </div>
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
                    
                    <div class="col-md-6">
                        <!-- START: search tutor requests -->
                        <div class="panel panel-primary">

                            <div class="panel-heading">
                               <h3 class="panel-title">Search Tutor Requests</h3>
                            </div>

                            <div class="panel-body">
                                
                                <br/>
                                <form role="form" method="get" 
                                      action="<%=request.getContextPath() %>/portal/manager/tutor-requests/search">
                                    <div class="form-group">
                                        
                                        <label>Search keyword:</label>
                                        <input type="text" maxlength="24" name="searchKeyword"
                                               id="searchKeyword" placeholder="Enter the keyword"
                                               class="form-control"/>
                                    </div>

                                    <div class="form-group">
                                        <label>Search Type:</label>
                                        <select id="searchType" name="searchType" class="form-control">
                                            <optgroup label="Pick search type by">
                                                <option value="1">Reference Number</option>
                                                <option value="2">Request ID</option>
                                                <option value="3">Email Address</option>
                                                <option value="4">Contact Number</option>
                                            </optgroup>
                                        </select>
                                    </div>

                                    <input type="submit" value="Search" 
                                           class="btn btn-primary form-control" />
                                </form>

                            </div>
                        </div>
                        <!-- END: search using keyword -->
                    </div>
                    
                    <div class="col-md-6">
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
             
            <%@include file="../jspf/template/default-footer.jspf" %>              
            
        </div>
        
    </body>
</html>
