
<%@page import="za.co.xplain2me.entity.Profile"%>
<%@page import="za.co.xplain2me.entity.Tutor"%>
<%@page import="java.util.TreeMap"%>
<%@page import="za.co.xplain2me.webapp.component.TutorManagementForm"%>

<%
    // the form
    TutorManagementForm form = (TutorManagementForm)
            session.getAttribute(TutorManagementForm.class.getName());
    
    if (form == null) {
        out.println("Invalid request detected.");
        return;
    }
    
    // map of tutors
    TreeMap<Long, Tutor> tutors = form.getTutors();
    
    // the date formatter
    final DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
    <head>
        <title>Browse Tutors</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        
        <script type="text/javascript">
            
            function browseTutors(value) {
                
                var startFrom = document.getElementById("startFrom");
                var resultsPerPage = document.getElementById("resultsPerPage");
                
                var start = parseInt($(startFrom).val());
                var limit = parseInt($(resultsPerPage).val());
                
                if (value === "back") {
                    var difference = start - limit;
                    if (difference < 0)
                        difference = 0;
                    
                    $(startFrom).val(difference);
                }
                
                document.forms[0].submit();
            }
            
        </script>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <div class="row">
                    <div class="col-md-6">
                        
                        <h2>Browse Tutors</h2>
                        <p class="text-primary">
                            Browse a list of existing tutors.
                        </p>
                        <hr/>

                        <%@include file="../jspf/template/default-alert-block.jspf" %>
                        
                    </div>
                </div>
                
                <div class="row">
                    
                    <%
                        if (tutors.isEmpty()) {
                            %>
                            
                            <div class="col-md-6">
                                <div class="alert alert-warning">
                                    <h4>No Tutors Found</h4>
                                    <p>
                                        No tutors were found that you are authorised to manage or browse
                                        at this moment.
                                    </p>
                                </div>
                            </div>
                    <%
                        }
                        
                        else {
                            %>
                            <div class="col-md-12">
                                
                                <form id="tutorForm" name="tutorForm"
                                      method="get"
                                      action="<%= request.getContextPath() + RequestMappings.BROWSE_TUTORS %>">
                                
                                <div class="panel panel-primary">
                                    
                                    <div class="panel-heading">
                                        Tutors
                                    </div>
                                    
                                    <div class="panel-body">
                                        
                                        <table border="0" class="table table-condensed table-hover" style="font-size: 100%">
                                            <thead>
                                                <tr>
                                                    <th></th>
                                                    <th>User ID</th>
                                                    <th>Username</th>
                                                    <th>Date Added</th>
                                                    <th>Full Names</th>
                                                    <th>Profile Type</th>
                                                    <th>Profile verified</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                            <%
                                    for (Tutor tutor : tutors.values()) {
                                        
                                        Profile profile = tutor.getProfile();

                                        %>
                                        <tr>
                                            <td>
                                                <%
                                                    // configure the links

                                                    String editUserLink = request.getContextPath() + 
                                                            RequestMappings.EDIT_USER_PROFILE + 
                                                            "?profile-id=" + profile.getId() + 
                                                            "&rand=" + System.currentTimeMillis();

                                                    String viewFullProfile = request.getContextPath() + 
                                                            RequestMappings.VIEW_USER_PROFILE + 
                                                            "?profile-id=" + profile.getId();
                                                    
                                                    String assignSubjectsToTutor = 
                                                            request.getContextPath() + RequestMappings.ASSIGN_SUBJECTS_TO_TUTOR +
                                                            "?tutor_id=" + tutor.getId();
                                                            
                                                %>

                                                <div class="btn-group">

                                                    <button type="button" data-toggle="dropdown" 
                                                            class="btn btn-primary btn-sm dropdown-toggle">
                                                        <span class="glyphicon glyphicon-menu-down"></span>
                                                    </button>

                                                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu" >
                                                        <li>
                                                            <a  title="View Full Profile"
                                                                href="<%= viewFullProfile %>">
                                                                <span class="glyphicon glyphicon-th-list"></span>
                                                                &nbsp;View Profile
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a  title="Edit user"
                                                                href="<%= editUserLink %>">
                                                                <span class="glyphicon glyphicon-pencil"></span>
                                                                &nbsp;Edit Details
                                                            </a>
                                                        </li>
                                                         <li>
                                                            <a  title="Change / Assign Subjects"
                                                                href="<%= assignSubjectsToTutor %>">
                                                                <span class="glyphicon glyphicon-book"></span>
                                                                &nbsp;Assign / Remove Subjects
                                                            </a>
                                                        </li>
                                                        
                                                    </ul>

                                                </div>
                                                        
                                            </td>
                                            <td>
                                                <%= profile.getPerson().getUser().getId() %>
                                            </td>
                                            <td>
                                                <%= profile.getPerson().getUser().getUsername() %>
                                            </td>
                                            <td>
                                                <%= dateFormat.format(profile.getDateCreated()) %>
                                            </td>
                                            <td>
                                                <%= profile.getPerson().getLastName() + 
                                                    ", " + profile.getPerson().getFirstNames()
                                                %>
                                            </td>
                                            <td>
                                                <%= profile.getProfileType().getDescription() %>
                                            </td>
                                            <td>
                                                <%= (profile.isVerified()) ? "Yes" : "No" %>
                                            </td>
                                        </tr>
                                                <%
                                        }
                                    %>
                                            </tbody>
                                        </table>
                                    
                                        <input id="startFrom" type="hidden" name="start_from" value="<%= form.getBrowseStartingFrom() %>">
                                            
                                    </div>
                                    <div class="panel-footer">
                                        
                                        <div class="text-center">
                                            <p>
                                                <span>Showing </span>
                                                <span>
                                                    <input type="text" id="resultsPerPage" name="results_per_page"
                                                       value="<%= form.getBrowseLimitPerPage() %>"> 
                                                </span>
                                                <span> tutors per page.</span>
                                                <br/>
                                            </p>
                                        </div>
                                        <hr/>
                                        
                                        <nav>
                                            <ul class="pager">
                                                <li>
                                                    <a href="#" onclick="browseTutors('back')">
                                                        Previous
                                                    </a>
                                                </li>
                                                <li>
                                                    <a href="#" onclick="browseTutors('forward')">
                                                        Next
                                                    </a>
                                                </li>
                                            </ul>
                                        </nav>
                                        
                                    </div>
                                </div>
                                            
                                </form>
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
