
<%@page import="za.co.xplain2me.util.BooleanToText"%>
<%@page import="java.util.ArrayList"%>
<%@page import="za.co.xplain2me.entity.Profile"%>
<%@page import="za.co.xplain2me.entity.Tutor"%>

<%
    // get the list of tutor profiles
    List<Profile> tutorProfiles = (List)request.getAttribute("tutorProfiles");
    
    if (tutorProfiles == null)
        tutorProfiles = new ArrayList<Profile>();
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Create Tutor Selection</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        <%@include file="../jspf/template/form-validation-script.jspf" %>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>

            <div class="container-fluid" id="page-wrapper">

                <h2>Create Tutor Selection</h2>
                <hr/>

                <%@include file="../jspf/template/default-alert-block.jspf" %>

                <div class="row">

                    <div class="col-md-12">

                        <%
                            if (!tutorProfiles.isEmpty()) {

                                String formAction = request.getContextPath() + 
                                        RequestMappings.CREATE_TUTOR;

                            %>

                            <form id="tutorForm" name="tutorForm" method="get"
                                  action="<%= formAction %>">

                                <p class="text-muted">
                                    <strong>
                                        Select one user profile account below to create a tutor 
                                        from.
                                    </strong>
                                </p>

                                <div class="panel panel-default">
                                    <div class="panel-body">

                                    <table class="table table-condensed">
                                        <thead style="font-weight: bold">
                                            <th></th>
                                            <th>Profile No.</th>
                                            <th>Full Name</th>
                                            <th>Identity No.</th>
                                            <th>Gender</th>
                                            <th>Verified</th>
                                            <th>Email</th>
                                            <th>Group</th>
                                        </thead>
                                        <tbody>

                                        <%
                                            int index = -1;
                                            for (Profile profile : tutorProfiles) {
                                                index++;
                                        %>
                                            <tr>
                                                <td>
                                                    <input type="radio" name="profile_id" 
                                                           value="<%= profile.getId() %>" 
                                                           <%= (index == 0) ? "class='required'" : "" %>/>
                                                </td>
                                                <td>
                                                    <%= profile.getId() %>
                                                </td>
                                                <td>
                                                    <%= profile.getPerson().getFirstNames() + " " 
                                                            + profile.getPerson().getLastName() %>
                                                </td>
                                                <td>
                                                    <%= profile.getPerson().getIdentityNumber() %>
                                                </td>
                                                <td>
                                                    <%= profile.getPerson().getGender().getDescription() %>
                                                </td>
                                                <td>
                                                    <%= BooleanToText.format(profile.isVerified(), BooleanToText.YES_NO_FORMAT) %>
                                                </td>
                                                <td>
                                                    <%= profile.getPerson().getContactDetail().getEmailAddress() %> 
                                                </td>
                                                <td>
                                                    <%= profile.getProfileType().getDescription() %>
                                                </td>
                                            </tr>
                                        <%
                                            }
                                        %>
                                        </tbody>
                                    </table>

                                    </div>
                                </div>

                                <input type="submit" class="btn btn-primary"
                                       value="Proceed to create tutor" ondblclick="return false" />

                            </form>
                        <%
                            }
                        %>

                    </div>

                </div>
                        
            </div>
                        
            <%@include file="../jspf/template/default-footer.jspf" %>           
        </div>
                        
        
    </body>
</html>
