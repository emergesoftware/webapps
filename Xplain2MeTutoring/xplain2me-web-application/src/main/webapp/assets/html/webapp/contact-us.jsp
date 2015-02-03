<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta name="description" content="Shows various ways in which the company
              can be contacted." >
        
        <title>Xplain2Me Tutoring | Contact Us | Simplify your learning</title>
        
        
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
                                    
                                   <h2>Contact Us</h2>
                                   
                                   <strong>
                                       We would like to hear from you, to contact us - please
                                       feel free to explore the options below:
                                   </strong>
                                   <br/><br/>
                                   
                                   <strong>
                                       Email:
                                   </strong>
                                   <a href="mailto:info@xplain2me.co.za"
                                      target="_blank"
                                      title="Send us an email">
                                          info@xplain2me.co.za
                                   </a> <br/>
                                   
                                   <strong>
                                       Contact Number:
                                   </strong>
                                   <a href="tel:+27728931542"
                                      target="_blank"
                                      title="Give us a call">
                                          +27(0)72 893 1542
                                   </a> <br/>
                                   
                                   
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
