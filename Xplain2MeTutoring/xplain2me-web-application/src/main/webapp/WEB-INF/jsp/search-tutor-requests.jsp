
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
        <%@include file="../jspf/template/form-validation-script.jspf" %>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <h2>Search Tutor Requests</h2>
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <div class="row">
                    
                    <div class="col-md-6">
                        
                        <form role="search" name="searchForm" id="searchForm"
                              method="post">
                            
                            <div class="form-group">
                                <label for="searchType">Search Using:</label>
                                <select id="searchType" name="searchType" 
                                        class="form-control">
                                    <option value="1" selected>Reference Number</option>
                                    <option value="2">Request ID</option>
                                    <option value="3">Email Address</option>
                                    <option value="4">Contact Number</option>
                                </select>
                            </div>
                            
                            <div class="form-group">
                                <label for="searchKeyword">Search Keyword:</label>
                                <input type="text" id="searchKeyword" name="searchKeyword"
                                       class="form-control" 
                                       placeholder="Enter the search keyword (Required)"
                                       data-validation="length" data-validation-length="1-32" 
                                       value=""/>
                            </div>
                            
                            <div class="form-group">
                                <input type="submit" name="submit" value="Search Tutor Requests"
                                       ondblclick="return false" 
                                       class="btn btn-primary"/>
                            </div>
                            
                        </form>
                        
                    </div>
                    
                </div>
            </div>
            
            <%@include file="../jspf/template/default-manager-footer.jspf" %>
            
        </div>
    </body>
</html>
