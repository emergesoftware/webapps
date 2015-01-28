
<%@page import="za.co.xplain2me.webapp.controller.RequestMappings"%>

<%

    // construct the form action
    String formAction = request.getContextPath() + RequestMappings.ADD_NEW_LESSONS;
    
    // construct url for AJAX to find students async
    String findStudentAsyncUrl = request.getContextPath() + 
            RequestMappings.FIND_STUDENT_ASYNC;
            
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
    <head>
        <title>Create New Lessons</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        <%@include file="../jspf/template/form-validation-script.jspf" %>
        
        <script type="text/javascript">
            
            $(document).ready(function() {
                // the async HTTP request
                var request = null;
                // assign an onClick event for searchStudentBtn button
                 $("#searchStudentBtn").click(function(event){
                     
                      // abort any running requests
                    if (request)
                        request.abort();
                    
                    // send the async HTTP post request
                    var request = $.ajax({
                        
                        url : '<%= findStudentAsyncUrl %>',
                        type: 'GET',
                        async: true,
                        data: {
                            'search-field' : $.trim($("#student").val())
                        },
                        success : function(response) {
                            alert("Results: " + response.outcome);
                        }
                        
                    });
                    
                    event.preventDefault();
                     
                 });
                
            });
            
          
        </script>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <h2>Add New Lessons</h2>
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <div class="row">
                    
                    <div class="col-md-6">
                        
                        <form id="lessonForm" name="lessonForm"
                              action="<%= formAction %>" method="post">
                        
                            <div class="form-group">
                                <label>Number of lessons:</label><br/>
                                <p class="text-muted">
                                    <span><strong>NB:</strong></span>
                                    <span>These lessons must below to one particular student
                                        and only one particular subject.</span>
                                </p>
                                <input type="text" id="numberOfLessons" name="numberOfLessons"
                                       class="form-control" max-length="2"
                                       placeholder="Enter the number of lessons (Required)"
                                       data-validation="number" data-validation-allowing="range[1;100]"
                                       data-validation-error-msg="You can only enter number of lessons between 1 and 100." />
                                
                            </div>
                            
                            <div class="form-group">
                                <label>Assign to student:</label>
                                
                                <div class="input-group">
                                    <input type="text" id="student" name="student"
                                           class="form-control" maxlength="24"
                                           placeholder="Enter the student's name"
                                           data-validation="length" data-validation-length="6-24"
                                           data-validation-error-msg="The student has to be assigned to the lessons." />
                                    
                                    <span class="input-group-btn">
                                        <button id="searchStudentBtn" class="btn btn-default" type="button">Fetch Student</button>
                                    </span>
                                </div>
                            </div>
                            
                        </form>
                        
                    </div>
                    
                </div>
                
            </div>
            
            <%@include file="../jspf/template/default-footer.jspf" %> 
            
        </div>
    </body>
</html>
