
<%@page import="za.co.xplain2me.entity.AcademicLevel"%>
<%@page import="za.co.xplain2me.entity.LessonStatus"%>
<%@page import="za.co.xplain2me.entity.Subject"%>
<%@page import="java.util.TreeMap"%>
<%@page import="za.co.xplain2me.webapp.component.LessonManagementForm"%>
<%@page import="za.co.xplain2me.webapp.controller.RequestMappings"%>

<%

    LessonManagementForm form = (LessonManagementForm)session
            .getAttribute(LessonManagementForm.class.getName());
    
    if (form == null) {
        
        response.sendRedirect(request.getContextPath() + 
                RequestMappings.ADD_NEW_LESSONS + 
                "?rand=" + System.currentTimeMillis());
        return;
    }
    
    // the map of subjects
    TreeMap<Long, Subject> subjects = form.getSubjects();
    if (subjects == null)
        subjects = new TreeMap<Long, Subject>();
    
    // the different lesson statuses
    TreeMap<Long, LessonStatus> lessonStatus = form.getLessonStatus();
    if (lessonStatus == null)
        lessonStatus = new TreeMap<Long, LessonStatus>();
    
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
        <%@include file="../jspf/template/bootstrap-datepicker.jspf" %>
        <%@include file="../jspf/template/form-validation-script.jspf" %>
        
        <script type="text/javascript">
            
            $(document).ready(function() {
                
                // basic counter for add-on IDs
                var counter = 0;
                
                // the async HTTP request for fetching
                // student profiles
                var studentRequest = null;
                
                // the async HTTP request for fetching
                // tutor profiles
                var tutorRequest = null;
                
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
                    if (studentRequest)
                        studentRequest.abort();
                    
                    // send the async HTTP post request
                    studentRequest = $.ajax({
                        
                        url : '<%= findStudentAsyncUrl %>',
                        type: 'GET',
                        async: true,
                        data: {
                            'search-field' : $.trim($("#student").val()),
                            'profile-type' : 'student'
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
                                var students = object.students;
                                
                                for (var i = 0; i < students.length; i++) {

                                    // create a list item
                                    var listItem = document.createElement("a");
                                    $(listItem).addClass("list-group-item");

                                    // assign attributes
                                    $(listItem).attr("id", students[i].profile.id);
                                    $(listItem).attr("href", "#");

                                    // add content
                                    var content = students[i].id + " - " +
                                            students[i].profile.person.firstNames + " " + 
                                            students[i].profile.person.lastName + " (ID No: " + 
                                            students[i].profile.person.identityNumber + ")";

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
                
                // add an event to create elements after the user has
                // selected their days of the week
                $("#selectedDaysOfWeek").change(function(event) {
                    
                    // get the wrapper
                    var enterTimesWrapper = document.getElementById("enterTimesWrapper");
                    // clear the wrapper
                    $(enterTimesWrapper).html("");
                    
                    // for each selection made
                    
                    $("#selectedDaysOfWeek :selected").each(function() {
                        
                        counter++;
                        
                        // create an input group div
                        var inputGroup = document.createElement("div");
                        $(inputGroup).addClass("input-group");
                        enterTimesWrapper.appendChild(inputGroup);
                        
                        // create a glyphicon span
                        var glyphicon = document.createElement("span");
                        $(glyphicon).addClass("glyphicon glyphicon-time");
                        $(glyphicon).css({ float : "left" });
                        
                        // create text span
                        var text = document.createElement("span");
                        $(text).css({ float : "left" });
                        $(text).html("&nbsp;Enter time for <strong>" + 
                                $(this).html() + "</strong>:")
                        
                        // create a label add-on span element
                        var labelAddOn = document.createElement("span");
                        $(labelAddOn).addClass("input-group-addon");
                        $(labelAddOn).css({ width : "35%" });
                        inputGroup.appendChild(labelAddOn);
                        
                        // add the glyphicon + text
                        labelAddOn.appendChild(glyphicon);
                        labelAddOn.appendChild(text);
                        
                        // assign this add-on a unique ID
                        $(labelAddOn).attr("id", "basic-addon" + counter);
                        
                        // create an input for an hour
                        var hourInput = document.createElement("input");
                        $(hourInput).attr("type", "text");
                        $(hourInput).attr("maxlength", "2");
                        $(hourInput).attr("placeholder", "HH");
                        $(hourInput).attr("aria-describedby", $(labelAddOn).attr("id"));
                        $(hourInput).addClass("form-control");
                        $(hourInput).attr("name", $(this).val() + "_hour");
                        $(hourInput).attr("autocomplete", "off");
                        
                        // add formvalidator validations
                        $(hourInput).attr("data-validation", "number");
                        $(hourInput).attr("data-validation-allowing", "range[6;20]");
                        $(hourInput).attr("data-validation-error-msg", 
                                "The hour for " + $(this).html() + " must be between 06 and 20.");
                        
                        inputGroup.appendChild(hourInput);
                        
                        // add a mouseup event
                        $(hourInput).mouseup(function() {
                            $(this).select();
                        });
                        
                        // create the time separator span
                        var separator = document.createElement("span");
                        $(separator).addClass("input-group-addon");
                        $(separator).html("<strong>:</strong>");
                        inputGroup.appendChild(separator);
                        
                        // assign a uniquw ID to the separator
                        counter++;
                        $(separator).attr("id", "basic-addon" + counter);
                        
                        // create an input for minute
                        var minuteInput = document.createElement("input");
                        $(minuteInput).attr("type", "text");
                        $(minuteInput).attr("maxlength", "2");
                        $(minuteInput).attr("placeholder", "MM");
                        $(minuteInput).attr("aria-describedby", $(labelAddOn).attr("id"));
                        $(minuteInput).addClass("form-control");
                        $(minuteInput).attr("name", $(this).val() + "_minute");
                        $(minuteInput).attr("autocomplete", "off");
                        
                        // add formvalidator validations
                        $(minuteInput).attr("data-validation", "number");
                        $(minuteInput).attr("data-validation-allowing", "range[0;59]");
                        $(minuteInput).attr("data-validation-error-msg",
                                "The minute for " + $(this).html() + 
                                        " must be between 00 and 59.");
                        
                        inputGroup.appendChild(minuteInput);
                        
                        // add a mouseup event
                        $(minuteInput).mouseup(function() {
                            $(this).select();
                        });
                        
                    });
                    
                    // check if there were children elements added
                    if ($("#enterTimesWrapper div,span,input").size() === 0) {
                        $("#enterTimesWrapper").html("<strong>No days of the week were selected.</strong>");
                    }
                    
                    else {
                        
                        var mutedText = document.createElement("p");
                        $(mutedText).addClass("text-muted");
                        $(mutedText).html("<strong>NB: </strong>Use 24-hour format.");
                        $(mutedText).prependTo(enterTimesWrapper);
                        
                        var instructionLabel = document.createElement("label");
                        $(instructionLabel).html("Enter the time for each day of week selected:");
                        $(instructionLabel).prependTo(enterTimesWrapper);
                        
                        // re-initialise the form validator
                        initialiseFormValidator();
                        
                    }
                    
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
                            
                            <hr/>
                            
                            <div id="assignStudentFormGroup" class="form-group">
                                <label>Assign to student:</label>
                                <p class="text-muted">
                                    <strong>NB:</strong>
                                    You can type just a part of the student's full name. The search
                                    will go and find student's with the similar part in their name.
                                </p>
                                <div class="input-group">
                                    <input type="text" id="student" name="student"
                                           class="form-control" autocomplete="off"
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
                            
                            <hr/>
                            
                            <div class="form-group">
                                
                                <label>Find the available tutors?</label>
                                
                                <div class="radio-inline">
                                    <label>
                                        <input type="radio" name="findNextAvailableTutors" selected value="yes"> Yes
                                    </label>
                                </div>
                                <div class="radio-inline">
                                    <label>
                                        <input type="radio" name="findNextAvailableTutors" value="no"> No
                                    </label>
                                </div>
                                
                            </div>
                            
                            <hr/>
                            
                            <div class="form-group">
                                <label>Enter the date the lessons will begin:</label>
                                <input type="text" id="lessonsStartDate" name="lessonsStartDate"
                                       class="form-control datepicker" autocomplete="off"
                                       placeholder="Click to select date (YYYY-MM-DD)" readonly
                                       data-validation="date" data-validation-format="yyyy-mm-dd" 
                                       data-validation-error-msg="Enter the date the lessons will begin."
                                       style="cursor: default; background-color: white; border-radius: 0px"/>
                            </div>
                            
                            <hr/>
                            
                            <div class="form-group">
                                <label>The days of the week in which the lessons will take place:</label>
                                <p class="text-muted">To select more than one day of the week, 
                                    hold <b>Ctrl</b> key and click your selection.</p>
                                <select id="selectedDaysOfWeek" name="selectedDaysOfWeek"
                                        class="form-control" size="7" multiple>
                                    
                                    <option value="2">Mondays</option>
                                    <option value="3">Tuesdays</option>
                                    <option value="4">Wednesdays</option>
                                    <option value="5">Thursdays</option>
                                    <option value="6">Fridays</option>
                                    <option value="7">Saturdays</option>
                                    <option value="1">Sundays</option>
                                    
                                </select>
                            </div>
                            
                            <div id="enterTimesWrapper" class="form-group">
                                   <!-- 
                                    Elements will go here if a user selected at least one day
                                   -->
                            </div>
                            
                            <hr/>
                            
                            <div class="form-group">
                                <label>Length in hours per lesson:</label>
                                <p class="text-muted"><b>Note:</b> To enter half an hour, enter <b>0.5</b> and
                                    for 1 hour and 45 minutes enter <b>1.75</b>.</p>
                                <input type="text" id="lengthOfLessons" name="lengthOfLessons"
                                           class="form-control" value="1.0" autocomplete="off"
                                           placeholder="Enter the length in hours per lesson"
                                           data-validation="number" data-validation-allowing="range[0.5;6.0],float"
                                           data-validation-error-msg="Enter correct length in hours per lesson."/>
                            </div>
                            
                            <hr/>
                            
                            <div class="form-group">
                                <label>Subject:</label>
                                <select id="subject" name="subject"
                                        class="form-control">
                                    
                                    <%
                                        for (Subject subject : subjects.values()){
                                        %>
                                        <option value="<%= subject.getId() %>">
                                            <%= subject.getName() %>
                                        </option>
                                    <%
                                        }
                                    %>
                                    
                                </select>
                            </div>
                             
                            <hr/>
                            
                            <div class="form-group">
                                <label>Lesson Status:</label>
                                <p class="text-muted">
                                    The default lesson status for newly created lessons
                                    is <strong>Pending</strong>.
                                </p>
                                <select id="lessonStatus" name="lessonStatus"
                                        class="form-control"
                                        style="background-color: white">
                                    
                                    <%
                                        long id = 1;
                                        LessonStatus pending = lessonStatus.get(id);
                                    %>
                                    
                                    <option value="<%= pending.getId() %>">
                                        <%= pending.getDescription() %>
                                    </option>
                                    
                                </select>
                            </div>
                                    
                            <hr/>
                                
                            <br/>
                            
                            <div class="form-group">
                                <input type="submit" class="btn btn-primary"
                                       value="Add Lessons" ondblclick="return false;"/>
                            </div>
                            
                        </form>
                        
                        <br/>
                        <br/>
                    </div>
                    
                </div>
                
            </div>
            
            <%@include file="../jspf/template/default-footer.jspf" %> 
            
        </div>
    </body>
</html>
