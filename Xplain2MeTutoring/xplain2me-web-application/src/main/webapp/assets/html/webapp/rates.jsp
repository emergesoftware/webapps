<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="description" content="Outlines a brief on how the rates are
              charged per client and how clients can obtain a qoutation." >
        
        <title>Xplain2Me Tutoring | Rates | Simplify your learning</title>
        
        <%@include file="scripts/template/header.jspf" %>
   
    </head>
    
    <body>
        
       <%@include file="scripts/template/main_art_background.jspf" %>
        
        <div id="art-main">
            <div class="art-sheet">
                
                <%@include file="scripts/template/main_art_sheets.jspf" %>
                
                <div class="art-sheet-body">
                    
                    <%@include file="scripts/template/main_art_header.jspf" %>
                    <%@include file="scripts/template/main_art_navigation.jspf" %>
                    
                    <div class="art-content-layout">
                        <div class="art-content-layout-row">
                            
                            <div class="art-layout-cell art-content">
                                
                                <%@include file="scripts/template/form_block.jspf" %>
                                
                                <div style="width:600px; margin-bottom: 30px">
                                    
                                   <h2>Rates</h2>
                                   
                                    <p>
                                        Our rates are affordable yet we offer the best service with utilization of 
                                        qualified and trained tutors.We ensure our clients get value for their 
                                        money by following up regularly on student performance and ensuring progress 
                                        is being made. We encourage our tutors to motivate students to ensure both 
                                        the tutor and the student are working to achieve desired results.
                                        <br/> <br/>
                                        <a href="<%= request.getContextPath() + RequestMappings.QUOTE_REQUEST %>" 
                                           color = "blue">Request a quote</a> now and it will be to be sent to you.<br/>
                                           
                                        <br/>
                                    </p>
                                    
                                    <div class="art-post"></div>
                                </div>
                                
                            </div>
                            
                            <div class="art-layout-cell art-sidebar1">
                                
                               <%@include file="scripts/template/main_art_sidebar.jspf" %>
                                
                            </div>
                            
                        </div>
                    </div>
                    
                    <%@include file="scripts/template/main_art_footer.jspf" %>
                    
                </div>
                
                
            </div>
            
        </div>
        
    </body>
</html>
