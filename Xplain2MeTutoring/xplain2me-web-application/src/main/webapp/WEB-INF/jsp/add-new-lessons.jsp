
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
                     
                    // validate the field
                    if ($.trim($("#student").val()).length < 3) {
                        
                        var findStudentResults = document.getElementById("findStudentResults");
                        $(findStudentResults).html("");
                        
                        // add the has-error class to the form group
                        $("#assignStudentFormGroup").addClass("has-error");
                        
                        // create an error panel
                        var errorPanel = document.createElement("div");
                        $(errorPanel).css({ display : 'none' });
                        $(errorPanel).addClass("alert alert-warning");
                        $(errorPanel).html("You must type in a minimum of 3 \n\
                        characters of the name of the student.");
                        
                        // append the error panel
                        findStudentResults.appendChild(errorPanel);
                        $(errorPanel).show('fast');
                        
                        return;
                    }
                    
                    // check the button inner html
                    if ($(this).html() === "Clear student") {
                        
                        $("#student").val("");
                        $("#student").removeAttr("readonly");
                        $(this).html("Search student");
                        
                        return;
                    }
                    
                    // remove the error classes and 
                    // error panels
                    $("#assignStudentFormGroup").removeClass("has-error");
                    $("#findStudentResults").html("");
                    
                    // clean off whitespaces
                    $("#student").val($.trim( $("#student").val()));
        
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
                        beforeSend: function (xhr) {
                            
                            var findStudentResults = document.getElementById("findStudentResults");
                            $(findStudentResults).html("");
                            
                            // create the loading pabel
                            var loadingPanel = document.createElement("div");
                            $(loadingPanel).attr("id", "loadingPanel");
                            $(loadingPanel).css({ display : 'none' });
                            $(loadingPanel).addClass("alert alert-primary");
                            $(loadingPanel).html("<i>Fetching students, please wait...</i>");

                            // append the loading panel
                            findStudentResults.appendChild(loadingPanel);
                            $(loadingPanel).fadeIn('fast');
                            
                        },
                        success : function(data) {
                            
                            // clear the results pane
                            var findStudentResults = document.getElementById("findStudentResults");
                            $(findStudentResults).html("");
                            
                            // parse the JSON object
                            var object = JSON.parse(data);
                            
                            // get the code to determine if there were students found
                            var code = object.code;
                            
                            // create an info panel
                            var infoPanel = document.createElement("div");
                            $(infoPanel).addClass("panel panel-primary");
                            $(infoPanel).css({ display : 'none' });
                            findStudentResults.appendChild(infoPanel);
                            
                            // create a info panel heading + title
                            var infoPanelTitle = document.createElement("div");
                            $(infoPanelTitle).addClass("panel-heading");
                            infoPanel.appendChild(infoPanelTitle);
                            
                            var heading = document.createElement("h3");
                            $(heading).addClass("panel-title");
                            $(heading).html(object.outcome);
                            infoPanelTitle.appendChild(heading);
                            
                            // create the info panel body
                            var infoPanelBody = document.createElement("div");
                            $(infoPanelBody).addClass("panel-body");
                            infoPanel.appendChild(infoPanelBody);
                            
                            // if there were students found
                            if (code === 1) {
                                
                                // add an instructive message
                                var instruction = document.createElement("strong");
                                $(instruction).html("Click the student you want to add below:");
                                infoPanelBody.appendChild(instruction);
                                
                                infoPanelBody.innerHTML = infoPanelBody.innerHTML + "<br/>";
         
                                // create a list
                                var unorderedList = document.createElement("div");
                                $(unorderedList).addClass("list-group");

                                // append the unordered list to the info panel
                                infoPanelBody.appendChild(unorderedList);

                                // for each of the found profiles - 
                                // add them to the list
                                var profiles = object.profiles;
                                
                                for (var i = 0; i < profiles.length; i++) {

                                    // create a list item
                                    var listItem = document.createElement("a");
                                    $(listItem).addClass("list-group-item");

                                    // assign attributes
                                    $(listItem).attr("id", profiles[i].id);
                                    $(listItem).attr("href", "#");

                                    // add content
                                    var content = profiles[i].id + " - " +
                                            profiles[i].person.firstNames + " " + 
                                            profiles[i].person.lastName + " (ID No: " + 
                                            profiles[i].person.identityNumber + ")";

                                    $(listItem).html(content);

                                    // add a click event
                                    $(listItem).click(function(event) {
                                        
                                        $("#student").val($(this).html());
                                        $("#student").attr("readonly", "");
                                        $("#searchStudentBtn").html("Clear student");
                                        
                                        event.preventDefault();
                                    });

                                    //append the list item 
                                    unorderedList.appendChild(listItem);

                                }
                            }
                            
                            // show the info panel
                            $(infoPanel).show('fast');
                            
                            // add a button to close this panel
                            var closeButton = document.createElement("input");
                            $(closeButton).attr("type", "button");
                            $(closeButton).val("Dismiss");
                            $(closeButton).addClass("btn btn-primary btn-sm");
                            infoPanelBody.appendChild(closeButton);
                            
                            // add a click event
                            $(closeButton).click(function(event) {
                                
                                $(infoPanel).hide('fast', function(){
                                     $(this).remove();
                                });
                                
                                event.preventDefault();
                            });
                        },
                        error: function(xhr, status, error) {
                            $("#findStudentResults").html("<p>An error occured.</p>");
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
                            
                            <div id="assignStudentFormGroup" class="form-group">
                                <label>Assign to student:</label>
                                <p class="text-muted">
                                    <strong>NB:</strong>
                                    You can type just a part of the student's full name. The search
                                    will go and find student's with the similar part in their name.
                                </p>
                                <div class="input-group">
                                    <input type="text" id="student" name="student"
                                           class="form-control" maxlength="24"
                                           placeholder="Enter the student's first name(s)"
                                           data-validation="length" data-validation-length="min3"
                                           data-validation-error-msg="The student has to be assigned to the lessons." />
                                    
                                    <span class="input-group-btn">
                                        <button id="searchStudentBtn" class="btn btn-default" type="button">Search student</button>
                                    </span>
                                </div>
                                <br/>
                                <div id="findStudentResults"></div>
                                
                            </div>
                            
                        </form>
                        
                    </div>
                    
                </div>
                
            </div>
            
            <%@include file="../jspf/template/default-footer.jspf" %> 
            
        </div>
    </body>
</html>
