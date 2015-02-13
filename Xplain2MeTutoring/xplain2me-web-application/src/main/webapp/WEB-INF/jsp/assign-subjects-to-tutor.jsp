
<%@page import="java.util.TreeMap"%>
<%@page import="za.co.xplain2me.webapp.component.TutorManagementForm"%>
<%@page import="za.co.xplain2me.entity.Tutor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="za.co.xplain2me.entity.Subject"%>
<%
    
    // the form
    TutorManagementForm form = (TutorManagementForm)session
            .getAttribute(TutorManagementForm.class.getName());
    if (form == null) {
        
        response.sendRedirect(request.getContextPath() + 
                RequestMappings.BROWSE_TUTORS + 
                "?invalid-request=1");
        return;
    }
    
    // the tutor
    Tutor tutor = form.getTutors()
            .firstEntry().getValue();
            
    // subjects to choose from
    TreeMap<Long, Subject> subjectsToChooseFrom = form.getSubjectsToChooseFrom();
    if (subjectsToChooseFrom == null)
        subjectsToChooseFrom = new TreeMap<Long, Subject>();
    
    // subjects already assigned
    TreeMap<Long, Subject> subjectsAlreadyAssigned = form.getSubjectsAlreadyAssigned();
    if (subjectsAlreadyAssigned == null)
        subjectsAlreadyAssigned = new TreeMap<Long, Subject>();
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Assign Subjects to Tutor</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        <%@include file="../jspf/template/form-validation-script.jspf" %>
        
        <script type="text/javascript">
            
            function filterSearch(item) {
                
                if (item === null) return; 
                
                // the subjects list
                var list = document.getElementById("subjects_list");
                
                // check if there is at least 3 characters
                if ($.trim($(item).val().length) < 3) {
                    // show the error text
                    $("#filter_text").css({ display : 'block' });
                    
                    // show all the elements
                    $(list).find(".list-group-item").each(function() {
                        $(this).show();
                    });
                    return;
                }
                
                // get the search text
                var text = $.trim($(item).val());
                
                // hide the filter text warning
                $("#filter_text").css({ display : 'none' });
                
                // iterate through the elements 
                $(list).find(".list-group-item").each(function() {
                    
                    var contents = $(this)
                            .find(".list-group-item-heading").first().html();
                    
                    if (contents.toLowerCase().indexOf(
                            text.toLowerCase()) >= 0) 
                        $(this).show();
                    
                    else $(this).hide();
                    
                });
            }
            
            function appendToSubjectsSelected(item) {
                
                if (item === null) 
                    return;
                
                // get the selected subjects panel
                var selectedSubjectsPanel = document.getElementById("selected_subjects_panel");
                
                // clear all contents if the child is a P
                if ($(selectedSubjectsPanel).children("p").size() > 0) { 
                    
                    $(selectedSubjectsPanel).html("");
                    $("#button_area").css({ display : 'block' });
                }
                
                // append the item to the panel
                selectedSubjectsPanel.appendChild(item);
                
                // bind a new click event
                $(item).attr("onclick", "removeSubjectFromSelected(this)");
                
                // get the original subjects list
                var subjectsList = document.getElementById("subjects_list");
                // remove the child from old list
                $(subjectsList).find(item).remove();
                
                // change the hidden element name
                $(item).find("input[type='hidden']").each(function(){
                    $(this).attr("name", "add_subject");
                });
                
            }
            
            function removeSubjectFromSelected(item) {
                
                if (item === null) 
                    return;
                
                var index = parseInt($(item).attr("index"));
                
                // get the original subjects list
                var subjectsList = document.getElementById("subjects_list");
                // add the item back to the list
                
                if (index === 0) {
                    $(item).prependTo(subjectsList);
                }
                
                else {
                    
                    var previousElement = null;
                    
                    $(subjectsList).find(".list-group-item").each(function() {
                        
                        var elementIndex = parseInt($(this).attr("index"))
                        var difference = index - elementIndex;
                        
                        if (difference > 0) {
                            previousElement = $(this);
                        }
                        
                        else if (difference < 0) {
                            $(item).insertAfter(previousElement);
                            return false;
                        }

                    }); 
                }
                
                // bind the a new click event
                $(item).attr("onclick", "appendToSubjectsSelected(this)");
                
                // get the selected subjects panel
                var selectedSubjectsPanel = document.getElementById("selected_subjects_panel");
                // remove the item from old list
                $(selectedSubjectsPanel).find(item).remove();
                
                // if the list is empty
                if ($(selectedSubjectsPanel).children("a").size() === 0) {
                    
                    $(selectedSubjectsPanel)
                            .html("<p class='text-primary'>None selected at the moment</p>");
                    
                    // check if there are subjects to be 
                    // removed
                    if (document.getElementsByName("remove_subject").length > 0) {

                        // show the button to update
                        $("#button_area").css({ display : 'block' });

                    }
                    else 
                        $("#button_area").css({ display : 'none' });
                }
                
                // change the hidden element name
                $(item).find("input[type='hidden']").each(function(){
                    $(this).attr("name", "ignore_subject");
                });
                
            }
            
            function alreadyAssignedSubject(item) {
                
                if (item === null) return;
                
                // if the subject is checked - mark it
                // for removal
                if ($(item).prop('checked') === true) {
                    $(item).attr("name", "remove_subject");
                }
                
                // if the subject is unchecked
                // mark it to be ignored
                else {
                    $(item).attr("name", "ignore_subject");
                }
                
                // check if there are subjects to be 
                // removed
                if (document.getElementsByName("remove_subject").length > 0) {
                    
                    // show the button to update
                    $("#button_area").css({ display : 'block' });
                        
                }
                
            }
            
        </script>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>

            <div class="container-fluid" id="page-wrapper">

                <div class="row">
                    <div class="col-md-6">
                        <h2>Assign Subjects to Tutor</h2>
                        <p class="text-primary">
                            Assign new or remove already assigned subjects
                            to / from this tutor in the briefing.
                        </p>
                        <hr/>
                        
                        <%@include file="../jspf/template/default-alert-block.jspf" %>
                        
                        <div class="panel panel-info">
                            
                            <div class="panel-heading">
                                Tutor Briefing
                            </div>
                            
                            <div class="panel-body">
                                <table class="table table-condensed table-hover">
                                    <tr>
                                        <td><b>Tutor ID</b></td>
                                        <td><%= tutor.getId() %></td>
                                    </tr>
                                    <tr>
                                        <td><b>Full Names</b></td>
                                        <td>
                                            <%= tutor.getProfile().getPerson().getFirstNames() + " " +
                                                    tutor.getProfile().getPerson().getLastName() %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><b>Gender</b></td>
                                        <td><%= tutor.getProfile().getPerson().getGender().getDescription() %></td>
                                    </tr>
                                    <tr>
                                        <td><b>Identity Number</b></td>
                                        <td><%= tutor.getProfile().getPerson().getIdentityNumber() %></td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                        
                    </div>
                </div>
                
                <div class="row">

                    <div class="col-md-6">

                        <form id="tutorForm" id="tutorForm"
                              method="post" action="<%= RequestMappings.ASSIGN_SUBJECTS_TO_TUTOR %>">
                            
                            <input type="hidden" name="tutor_id" value="<%= tutor.getId() %>" >
                            
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    Subjects Assignment Briefing
                                </div>
                                <div class="panel-body">
                                    
                                    <strong class="text-info">
                                        Subjects already assigned to this tutor:
                                    </strong><br/>
                                    <span class="text-muted">
                                        Tick the subject to remove from this tutor 
                                        in the box on the right of the subject.
                                    </span>
                                    
                                    <hr/>
                                    
                                    <%
                                        if (subjectsAlreadyAssigned.isEmpty()) {
                                            %>
                                            <p>
                                                <span class="glyphicon glyphicon-bell"></span>
                                                <span>&nbsp;</span>
                                                <span>No subjects have been assigned to this 
                                                    tutor yet.</span>
                                            </p>
                                    <%
                                        }
                                        
                                        else {
                                        %>
                                    
                                        <table class="table table-condensed table-hover">
                                            <thead>
                                                <th>Subject Code</th>
                                                <th>Subject Name</th>
                                                <th>Grade</th>
                                                <th>Academic Phase</th>
                                                <th class="text-danger">Remove?</th>
                                            </thead>
                                            <tbody>
                                                
                                                <%
                                                    for (Subject subject : subjectsAlreadyAssigned.values()) {
                                                    
                                                        %>
                                                        
                                                        <tr>
                                                            <td><%= subject.getId() %></td>
                                                            <td><%= subject.getName() %></td>
                                                            <td><%= (subject.getGrade() == 13) ? "N/A" : subject.getGrade() %></td>
                                                            <td><%= subject.getAcademicLevel().getDescription() %></td>
                                                            <td>
                                                                <input type="checkbox" name="ignore_subject" 
                                                                       value="<%= subject.getId() %>"
                                                                       onchange="alreadyAssignedSubject(this)"/>
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
                                    
                                    <hr/>
                                    
                                    <strong class="text-info">
                                        Subjects you have selected to be assigned to this tutor:
                                    </strong><br/>
                                    <span class="text-muted">
                                        To add a subject to this list, simply tap the
                                        subject from the list to below. To remove
                                        a subject here, simply tap the subject.
                                    </span>
                                    
                                    <hr/>
                                    
                                    <div id="selected_subjects_panel" class="list-group">
                                        <p class="text-primary">None selected at the moment</p>
                                    </div>
                                    
                                    <div id="button_area" class="form-group" style="display: none">
                                        <input type="submit" value="Save Changes"
                                               class="btn btn-primary"
                                               ondblclick="return false" />
                                    </div>
                                    
                                </div>
                            </div>
                                    
                            <div class="panel panel-primary">
                                
                                <div class="panel-heading">
                                    <h3 class="panel-title">Select subjects to assign to tutor</h3>
                                </div>
                                
                                <div class="panel-body">
                                    
                                    <div class="form-group">
                                        <label>Filter subject names:</label>
                                        <input type="text" id="filterSubjectNames"
                                               class="form-control" 
                                               placeholder="Type your text here to start filtering"
                                               onkeyup="filterSearch(this)"
                                               autocomplete="off">
                                        
                                        <p id="filter_text" class="text-warning" style="display: none">
                                            Type in at least 3 characters to begin filtering subjects.
                                        </p>
                                    </div>
                                    <br/>
                                    
                                    <div id="subjects_list" class="list-group">
                                        
                                    <%
                                        int counter = -1;
                                        
                                        for (Subject subject : subjectsToChooseFrom.values()) {
                                            
                                            counter++;
                                            
                                            %>
                                    
                                            
                                                <a title="<%= subject.getName() %>"
                                                   class="list-group-item"
                                                   onclick="appendToSubjectsSelected(this)"
                                                   index="<%= counter %>">
                                                    
                                                    <h4 class="list-group-item-heading">
                                                        <%= subject.getName() %>
                                                    </h4>
                                                    
                                                    <p class="list-group-item-text">
                                                        <span><strong>Grade: </strong></span>
                                                        <span><%= (subject.getGrade() == 13) ? "N/A" : subject.getGrade() %></span>
                                                        <br/>
                                                        <span><strong>Academic Phase: </strong></span>
                                                        <span><%= subject.getAcademicLevel().getDescription() %></span>
                                                    </p>
                                                    
                                                    <input type="hidden" name="ignore_subject" 
                                                           value="<%= subject.getId() %>" />
                                                    
                                                </a>
                                            
                                    
                                    <%
                                            
                                        }
                                    %>
                                    
                                    </div>
                                </div>
                                
                            </div>
                            
                        </form>
                        
                    </div>

                </div>
                        
            </div>
                        
            <%@include file="../jspf/template/default-manager-footer.jspf" %>           
        </div>
                        
        
    </body>
</html>
