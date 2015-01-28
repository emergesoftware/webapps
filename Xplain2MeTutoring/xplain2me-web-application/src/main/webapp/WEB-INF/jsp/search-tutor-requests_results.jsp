
<%@page import="za.co.xplain2me.entity.TutorRequestSubject"%>
<%@page import="za.co.xplain2me.entity.TutorRequest"%>
<%@page import="za.co.xplain2me.webapp.component.TutorRequestsManagementForm"%>

<%
    TutorRequestsManagementForm form = (TutorRequestsManagementForm)
            session.getAttribute(TutorRequestsManagementForm.class.getName());
    
    if (form == null) {
        form = new TutorRequestsManagementForm();
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
    <head>
        <title>Search Tutor Requests</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <h2>Tutor Requests Search Results</h2>
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <div class="row">
                    
                    <div class="col-md-12">
                      
                        <!-- START: search tutor requests results-->
                        <div class="panel panel-primary">

                            <div class="panel-heading">
                               <h3 class="panel-title">Search Results</h3>
                            </div>

                            <div class="panel-body">

                                 <%
                            int count = form.getSearchResults().size();
                            if (count == 0) {

                                %>
                                <p>
                                    <span>No tutor requests could be found from your search.</span>
                                </p>
                            <%
                            }

                            else {

                                %>

                                <p>
                                    <span>Tutor Requests Found: <%= count %>.</span>
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
                                            for (Long key : form.getSearchResults().keySet()) {
                                                TutorRequest item = form.getSearchResults().get(key); 

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
                        <!-- END: search tutor requests results -->
                        
                    </div>
                    
                </div>
            </div>
            
            <%@include file="../jspf/template/default-footer.jspf" %> 
            
        </div>
    </body>
</html>
