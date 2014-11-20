<%-- 
    Document   : dashboard
    Created on : 30 Sep 2014, 8:08:34 PM
    Author     : user
--%>

<%@page import="za.co.emergelets.xplain2me.entity.TutorRequestSubject"%>
<%@page import="za.co.emergelets.xplain2me.entity.TutorRequest"%>
<%@page import="za.co.emergelets.xplain2me.webapp.component.ManagerTutorRequestsForm"%>
<%
    // get the form
    ManagerTutorRequestsForm form = (ManagerTutorRequestsForm)session
            .getAttribute(ManagerTutorRequestsForm.class.getName());
    
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
        
        <%@include file="../../jspf/template/default-manager-header.jspf" %>
        
    </head>
    <body>
        
      
        
        <div id="wrapper">
            
            <%@include file="../../jspf/template/default-manager-navigation.jspf" %>
            
            <div id="page-wrapper">
                
                <h2 class="text-primary">Tutor Requests</h2>
                <p class="text-primary">
                    Management of all the requests for a tutor by clients.
                </p>
                
                <hr/>
                
                <!-- START: search tutor requests -->
                
                  <div class="panel panel-primary">
            
                    <div class="panel-heading">
                       <h3 class="panel-title">Search Tutor Requests</h3>
                    </div>

                    <div class="panel-body">

                        <div class="row">
                            <!-- START: search using keyword -->
                            <div class="col-md-4">
                                
                                <p><strong>Search using keyword:</strong></p>
                                
                                <form role="form" method="get" 
                                      action="<%=request.getContextPath() %>/portal/manager/tutor-requests/search">
                                    <div class="form-group">
                                        <input type="text" maxlength="24" name="searchKeyword"
                                               id="searchKeyword" placeholder="Enter the keyword"
                                               class="form-control"/>
                                    </div>

                                    <div class="form-group">
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
                            <!-- END: search using keyword -->
                            
                            <!-- START: search by read or unread -->
                            <div class="col-md-4">
                                
                                
                            </div>
                            <!-- END: search by read or unread -->
                        </div>

                    </div>
                </div>
                
                <!-- END: search tutor requests -->
                
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
                                                        <a  title="Mark This Request As Read" style="text-decoration: none"
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
                
                <br/>
                
            </div>
             
            <%@include file="../../jspf/template/default-footer.jspf" %>              
            
        </div>
        
    </body>
</html>
