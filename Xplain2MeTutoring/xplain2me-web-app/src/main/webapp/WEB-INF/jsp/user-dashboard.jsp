
<%@page import="za.co.emergelets.xplain2me.webapp.component.DashboardForm"%>
<%@page import="za.co.emergelets.util.DateTimeUtils"%>
<%@page import="za.co.emergelets.xplain2me.entity.Audit"%>
<%
    // get the form
    DashboardForm form = (DashboardForm)session.getAttribute(
            DashboardForm.class.getName());
    
    if (form == null) {
        response.sendRedirect(request.getContextPath() 
                + RequestMappings.DASHBOARD_OVERVIEW);
        return;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Dashboard | Xplain2Me Tutoring</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        
    </head>
    <body>
            
        <%@include file="../jspf/template/default-manager-navigation.jspf" %>

        <div class="container-fluid" id="page-wrapper">

            <h2>Dashboard</h2>
            <hr/>
            <p>
                <span>Welcome back, </span>
                <span><strong><%= userContext.getProfile().getPerson().getUser().getUsername() %>.</strong> </span>
                <br/>
                <span class="text-muted">
                    [PROFILE: 
                    <strong>
                        <%= userContext.getProfile().getProfileType().getDescription() %>
                    </strong>
                    ]
                </span>
            </p>
            <hr/>

            <!-- audit trail -->
            <div class="col-md-12">
                    <!-- START: previous tutor requests audits -->
                    <div class="panel panel-primary">

                        <div class="panel-heading">
                           <h3 class="panel-title">Recent Audit Trail</h3>
                        </div>

                        <div class="panel-body">
                            <br/>

                            <%
                                int auditTrailCount = form.getAuditTrail().size();
                                if (auditTrailCount == 0) {
                                    %>
                                    <p>You have no audit trail in your recent activities.</p>
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
                                            <th>Reference #</th>
                                            <th>Source IP Address</th>
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
                                                    <td><%= audit.getSourceIpAddress() %></td>
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
                </div>


        </div>

        <%@include file="../jspf/template/default-footer.jspf" %>              
       
    </body>
</html>
