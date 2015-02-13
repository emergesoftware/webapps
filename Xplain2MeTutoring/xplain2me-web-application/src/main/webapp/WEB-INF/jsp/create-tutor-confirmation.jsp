
<%@page import="za.co.xplain2me.entity.Tutor"%>

<%
    // GET THE TUTOR FROM 
    // THE REQUEST SCOPE
    
    Tutor tutor = (Tutor)request.getAttribute(Tutor.class.getName());
    boolean foundTutor = (tutor != null);
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Create Tutor Confirmation</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        
    </head>
    <body>
          
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>

            <div class="container-fluid" id="page-wrapper">

                <h2>Create Tutor Confirmation</h2>
                <hr/>

                <%@include file="../jspf/template/default-alert-block.jspf" %>

                <hr/>

                <div class="row">

                    <div class="col-md-6">

                        <%
                            if (foundTutor) {
                        %>

                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">
                                    New Tutor Briefing
                                </h3>
                            </div>
                            <div class="panel-body">
                                <table border="0" class="table table-condensed">
                                    <tr>
                                        <td><b>Tutor Sequence #</b></td>
                                        <td><%= tutor.getId() %></td>
                                    </tr>
                                    <tr>
                                        <td><b>Tutor Full Name</b></td>
                                        <td><%= tutor.getProfile().getPerson().getFirstNames() + " " + 
                                               tutor.getProfile().getPerson().getLastName() %></td>
                                    </tr>
                                    <tr>
                                        <td><b>Tutor Identity #</b></td>
                                        <td><%= tutor.getProfile().getPerson().getIdentityNumber() %></td>
                                    </tr>
                                    <tr>
                                        <td><b>Gender</b></td>
                                        <td><%= tutor.getProfile().getPerson().getGender().getDescription() %></td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                        <p>
                            <strong>
                                Other available options:
                            </strong>
                        </p>

                        <a href="<%= request.getContextPath() + RequestMappings.ASSIGN_SUBJECTS_TO_TUTOR + "?tutor_id=" + tutor.getId() %>" style="margin: 8px; display: block"
                           title="Assign subjects to tutor"
                           class="btn btn-primary">
                               Assign subjects to this tutor
                        </a>

                        <a href="<%= request.getContextPath() + RequestMappings.ASSIGN_LESSONS_TO_TUTOR + "?tutor_id=" + tutor.getId() %>"
                           title="Assign lesson to tutor" style="margin: 8px; display: block"
                           class="btn btn-primary">
                               Assign lessons to tutor
                        </a>

                        <%
                            }
                        %>

                    </div>

                </div>


            </div>

            <%@include file="../jspf/template/default-manager-footer.jspf" %>  
            
        </div>
    </body>
</html>
