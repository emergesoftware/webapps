<%@page import="za.co.emergelets.util.DateTimeUtils"%>
<%@page import="za.co.emergelets.util.BooleanToText"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.TreeMap"%>
<%@page import="za.co.emergelets.xplain2me.entity.BecomeTutorRequest"%>
<%@page import="za.co.emergelets.xplain2me.webapp.component.TutorJobApplicationForm"%>
<%
    TutorJobApplicationForm form = (TutorJobApplicationForm)
            session.getAttribute(TutorJobApplicationForm.class.getName());
    if (form == null) {
        form = new TutorJobApplicationForm();
    }
    
    TreeMap<Long, BecomeTutorRequest> requests = form.getTutorJobApplications();
    int currentPage = form.getCurrentPageNumber();
    int nextPage = currentPage + 1;
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
    <head>
        <title>Browse Tutor Job Applications | Xplain2Me Tutoring</title>
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        
    </head>
    <body>
        <div id="wrapper">
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <h2>Tutor Job Applications</h2>
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <div class="row">
                    <!-- start: BROWSE TUTOR JOB APPLICATIONS -->
                    <div class="col-md-12">
                        
                        <h4>BROWSE TUTOR JOB APPLICATIONS</h4>
                        <hr/>
                        
                        <%
                            if (requests.isEmpty()) {
                                %>
                                <p>
                                    <span class="glyphicon glyphicon-bullhorn"></span>
                                    <span>&nbsp;</span>
                                    <span>There are no tutor job applications made so far.</span>
                                </p>
                        <%
                            }
                            
                            else {
                                
                                %>
                                
                                <div class="panel panel-primary">
                                    
                                    <div class="panel-heading">
                                        Tutor Job Applications
                                    </div>
                                    
                                    <div class="panel-body">
                                
                                        <p>
                                            <span class="glyphicon glyphicon-bullhorn"></span>
                                            <span>&nbsp;</span>
                                            <span>Showing <strong><%= requests.size() %></strong> 
                                                tutor job applications on page <strong><%= currentPage %></strong>.
                                            </span>
                                        </p>
                                        <table class="table table-bordered" style="font-size: 100%">
                                            <thead>
                                                <tr>
                                                    <th>Actions</th>
                                                    <th>Sequence #</th>
                                                    <th>Last Name</th>
                                                    <th>First Names</th>
                                                    <th>Contact Number</th>
                                                    <th>Email Address</th>
                                                    <th>Address</th>
                                                    <th>ID / Passport</th>
                                                    <th>Gender</th>
                                                    <th>Submitted on</th>

                                                </tr>
                                            </thead>

                                            <tbody>
                                        <%

                                        for (Long id : requests.keySet()) {

                                            BecomeTutorRequest item = requests.get(id);
                                        %>
                                        <tr>
                                            <td>
                                                <div class="btn-group">

                                                    <button type="button" data-toggle="dropdown" 
                                                            class="btn btn-primary btn-sm dropdown-toggle">
                                                        <span class="glyphicon glyphicon-list"></span>
                                                        <span class="caret"></span>
                                                    </button>

                                                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
                                                        <li>
                                                            <a  title="View all details"
                                                                href="<%=request.getContextPath() + RequestMappings.VIEW_TUTOR_JOB_APPLICATION_DETAILS + "?id=" + item.getId() %>">
                                                                <span class="glyphicon glyphicon-th-list"></span>
                                                                &nbsp;View Details
                                                            </a>
                                                        </li>
                                                         <li>
                                                            <a  title="Download supporting documents" target="_blank"
                                                                href="<%=request.getContextPath() + RequestMappings.DOWNLOAD_TUTOR_JOB_APPLICATION_SUPPORTING_DOCUMENTS + "?id=" + item.getId() %>">
                                                                <span class="glyphicon glyphicon-th-list"></span>
                                                                &nbsp;Download supporting documents
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a title="Send applicant an email" href="mailto:<%= item.getEmailAddress() %>"
                                                               target="_blank">
                                                                <span class="glyphicon glyphicon-envelope"></span>
                                                                &nbsp;Compose Email
                                                            </a>
                                                        </li>
                                                    </ul>

                                                </div>
                                            </td>

                                            <td>
                                                <%= item.getId() %>
                                            </td>
                                            <td>
                                                <%= item.getLastName() %>
                                            </td>
                                            <td>
                                                <%= item.getFirstNames() %>
                                            </td>
                                            <td>
                                                <%= item.getContactNumber() %>
                                            </td>
                                            <td>
                                                <%= item.getEmailAddress() %>
                                            </td>
                                            <td>
                                                <%= item.getStreetAddress() + "," +
                                                    item.getSuburb() + "," +
                                                    item.getCity() + " " + 
                                                    item.getAreaCode() %>
                                            </td>
                                            <td>
                                                <%= item.getIdentityNumber() %>
                                            </td>
                                            <td>
                                                <%= item.getGender().getDescription() %>
                                            </td>
                                            <td>
                                                <%= DateTimeUtils
                                                        .formatDateTime(item.getDateSubmitted()) %>
                                            </td>

                                        </tr>


                                        <%
                                        }

                                        %>
                                            </tbody>
                                        </table>
                                            
                                    </div>
                                </div>
                                
                                <!-- start: Pagination -->
                                <div id="pagination">
                                    <hr/>
                                    <p>
                                        <span>Current Page: </span>
                                        <span>
                                            <strong>
                                                <%= form.getCurrentPageNumber() %>
                                            </strong>
                                        </span>
                                    </p>
                                    <ul class="pager">
                                        
                                        <%
                                            if (form.getCurrentPageNumber() == 1) {
                                                %>
                                        <li class="previous disabled">
                                            <a href="#">
                                                ← Previous
                                            </a>
                                        </li>
                                        <%
                                            }
                                            
                                            else {
                                                %>
                                        <li class="previous">
                                            <a href="<%= request.getContextPath() + RequestMappings.BROWSE_TUTOR_JOB_APPLICATIONS + "?page=" + (form.getCurrentPageNumber() - 1) %>">
                                                ← Previous
                                            </a>
                                        </li>
                                        <%
                                            }
      
                                        %>
                                        
                                        
                                        <li class="next <%= (form.isCannotGoForward()) ? "disabled" : "" %>">
                                            <a href="<%= request.getContextPath() + RequestMappings.BROWSE_TUTOR_JOB_APPLICATIONS + "?page=" + (form.getCurrentPageNumber() + 1) %>">
                                                Next →
                                            </a>
                                        </li>
                                    </ul>
                                    <hr/>
                                </div>
                                <!-- end: Pagination -->
                                
                                    <%
                            }
                        %>
                        
                    </div>
                    <!-- end: BROWSE TUTOR JOB APPLICATIONS -->    
                    
                    <!-- start: TUTOR JOB APPLICATIONS AUDIT TRAIL -->
                    <div class="col-md-12">
                        <h4>RECENT RELATED AUDIT TRAIL</h4>
                        <hr/>
                        
                        <p>
                            <span class="glyphicon glyphicon-bullhorn"></span>
                            <span>&nbsp;</span>
                            <span>
                                <label class="label label-info">Coming soon</label> 
                                <br/>
                                In the near future, you will be able
                                to see the most recent audit trail related to events
                                within tutor job applications - done by you.
                            </span>
                        </p>
                        
                    </div>
                    <!-- end: TUTOR JOB APPLICATIONS AUDIT TRAIL -->
                    
                </div>
                
            </div>
                        
            <%@include file="../jspf/template/default-footer.jspf" %> 
            
        </div>
    </body>
</html>
