<%@page import="com.emergelets.metrobus.mobisite.component.SearchForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    SearchForm searchForm = (SearchForm)session.getAttribute(SearchForm.class.getName());
    if (searchForm == null)
        searchForm = new SearchForm();
    
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        
        <%@include file="../jspf/templating/header.jspf" %>
        
        <script type="text/javascript">
            
            /**
             * Initialisation
             * @type @exp;document@call;getElementById
             */
            function initialise() {
                
                $(document).ready(function(){
                    
                    // check if there is any errors - 
                    // display them on the alert panel
                    
                    var errors = document.getElementById("errors");
                    
                    if (errors.value.toLowerCase() != "null") {
                        
                        var alertPanel = document.getElementById("alertPanel");
                        
                        $(alertPanel).removeClass("alert-info");
                        $(alertPanel).addClass("alert-danger");

                        var alertTitle = document.getElementById("alertTitle");
                        var alertContent = document.getElementById("alertContent");

                        $(alertTitle).html("<strong>ERROR:</strong>");
                        $(alertContent).html(errors.value);
                    }
                    
                }); 
                
            }
            
            /**
             * Validates the search form
             * 
             * @returns {Boolean}
             */
            function validateSearchAvailableRoutesForm() {
                
                // the to location text input
                var toLocation = document.getElementById("toLocation");
                // the alert panel
                var alertPanel = document.getElementById("alertPanel");
                
                // the from and to form groups
                var toLocationFormGroup = document.getElementById("toLocationFormGroup");
                
                // check if these DOM elements exist
                // or else exit
                if (toLocation == null || 
                        alertPanel == null ||
                        toLocationFormGroup == null)
                    return false;
                
                
                // check if either of these fields is filled
                var oneFieldFilled = false;
                if (toLocation.value.trim().length > 0) {
                    oneFieldFilled = true;
                }
                
                // exit if none of the fields are filled.
                if (oneFieldFilled == false) {
                    
                    $(alertPanel).removeClass("alert-info");
                    $(alertPanel).addClass("alert-danger");
                    
                    var alertTitle = document.getElementById("alertTitle");
                    var alertContent = document.getElementById("alertContent");
                    
                    $(alertTitle).html("<strong>ERROR:</strong>");
                    $(alertContent).html("You must fill in all required fields - " +
                                        "specify the destination location.");
                                
                    $(toLocationFormGroup).addClass("has-error");
                    
                    return false;
                    
                }
                
                else {
                    
                    $(toLocationFormGroup).removeClass("has-error");
                    
                    $(alertTitle).html("<strong>ERRORS FIXED:</strong>");
                    $(alertContent).html("It's all looking good, submitting your request.");
                    
                    // trim the data first
                    toLocation.value = toLocation.value.trim();
                    
                    return true;
                    
                }
                
            }
            
            
        </script>
        
    </head>

    <body onload="initialise()">
        
        <!-- the loading panel -->
        <%@include file="../jspf/templating/loadingPanel.jspf" %>

        <!-- wrap the page navigation here -->
        <%@include file="../jspf/templating/responsive-navigation-bar.jspf" %>

        <!--wrap the page content do not style this-->
        <div id="page-content">
            <div class="container" >
                
                <div>
                    <input type="hidden" id="errors" value="<%= searchForm.getErrors() %>" />
                </div>
                
                <br/>
                
                <div id="alertPanel" class="alert alert-info">
                    <p>
                        <span id="alertTitle"><strong>NOTE:</strong></span>
                        <span id="alertContent">
                            This search will only search for a bus route that
                            goes past the location you are searching for.
                        </span>
                    </p>
                </div>
                
                <hr/>
                
                <form role="form" id="searchAvailableBusRoutesForm" method="post"
                      action="<%= request.getContextPath() %>/search/retrieve-results"
                      onsubmit="return validateSearchAvailableRoutesForm()">
                    
                    <h4>Looking for buses going to: </h4>
                    
                    <div id="toLocationFormGroup" class="form-group">
                        <input type="text" class="form-control" id="toLocation" 
                               name="to" placeholder="Enter your departure location" 
                               maxlength="32" />
                    </div>
                    
                    <div class="form-group">
                        <input type="submit" name="submitSearchRequest" 
                               class="btn btn-primary form-control" value="Search"
                               ondbondblclick="return false" />
                    </div>
                    
                </form>
                
                <h4></h4>
                
            </div>
        </div>

              
    </body>
</html>
