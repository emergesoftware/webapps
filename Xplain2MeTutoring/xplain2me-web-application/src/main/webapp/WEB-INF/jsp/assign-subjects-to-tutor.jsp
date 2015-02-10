

<%@page import="za.co.xplain2me.entity.Tutor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="za.co.xplain2me.entity.Subject"%>
<%
    
    // the tutor
    Tutor tutor = (Tutor)request.getAttribute("tutor");
    if (tutor == null) {
        
        response.sendRedirect(request.getContextPath() + 
                RequestMappings.DASHBOARD_OVERVIEW + 
                "?invalid-request=1");
        return;
    }
    
    // subjects to choose from
    List<Subject> subjectsToChooseFrom = (List)
            request.getAttribute("subjectsToChooseFrom");
    if (subjectsToChooseFrom == null)
        subjectsToChooseFrom = new ArrayList<Subject>();
    
    // subjects already assigned
    List<Subject> subjectsAlreadyAssigned = (List)
            request.getAttribute("subjectsAlreadyAssigned");
    if (subjectsAlreadyAssigned == null)
        subjectsAlreadyAssigned = new ArrayList<Subject>();
    
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
                    
                    $("#button_area").css({ display : 'none' });
                }
                
                // change the hidden element name
                $(item).find("input[type='hidden']").each(function(){
                    $(this).attr("name", "ignore_subject");
                });
                
            }
            
            function alreadyAssignedSubject(item) {
                
                if (item === null) return;
                
                if ($(item).prop('checked') === true) {
                    $(item).attr("name", "remove_subject");
                }
                
                else {
                    $(item).attr("name", "ignore_subject");
                }
                
            }
            
        </script>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>

            <div class="container-fluid" id="page-wrapper">

                <h2>Assign Subjects to tutor</h2>
                <hr/>

                <%@include file="../jspf/template/default-alert-block.jspf" %>

                <div class="row">

                    <div class="col-md-6">

                        <form id="tutorForm" id="tutorForm"
                              method="post" action="<%= RequestMappings.ASSIGN_SUBJECTS_TO_TUTOR %>">
                            
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    Subjects already assigned to tutor
                                </div>
                                <div class="panel-body">
                                    
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
                                                    for (Subject subject : subjectsAlreadyAssigned) {
                                                    
                                                        %>
                                                        
                                                        <tr>
                                                            <td><%= subject.getId() %></td>
                                                            <td><%= subject.getName() %></td>
                                                            <td><%= subject.getGrade() %></td>
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
                                        Subjects you selected to assign:
                                    </strong>
                                    
                                    <div id="selected_subjects_panel" class="list-group">
                                        <p class="text-primary">None selected at the moment</p>
                                    </div>
                                    
                                    <div id="button_area" class="form-group" style="display: none">
                                        <input type="submit" value="Update"
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
                                               onkeyup="filterSearch(this)">
                                        
                                        <p id="filter_text" class="text-warning" style="display: none">
                                            Type in at least 3 characters to begin filtering subjects.
                                        </p>
                                    </div>
                                    <br/>
                                    
                                    <div id="subjects_list" class="list-group">
                                        
                                    <%
                                        int counter = -1;
                                        
                                        for (Subject subject : subjectsToChooseFrom) {
                                            
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
                                                        <span><%= subject.getGrade() %></span>
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
                        
            <%@include file="../jspf/template/default-footer.jspf" %>           
        </div>
                        
        
    </body>
</html>
