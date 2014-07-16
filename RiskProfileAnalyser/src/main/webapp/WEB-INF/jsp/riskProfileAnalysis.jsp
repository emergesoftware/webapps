<%-- 
    Document   : riskProfileAnalysis.jsp
    Created on : 12 Jul 2014, 9:06:06 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Risk Profile Analysis | Inkunzi Investments</title>
        
        <%@include file="../jspf/header.jspf" %>
        
    </head>
    <body>
        <div id="container" class="container-fluid">
            
            <form id="riskProfileForm" name="riskProfileForm" method="post"
                  onsubmit="return true">
            
                <br/>
                <img src="http://www.inkunziinvest.co.za/inkunzi/templates/inkunzi/images/logo_inkunzi.pnga" 
                     alt="inkunzi-investments-logo" style="height:120px" /><br/>
                <hr/>
                <h1>Are you thinking, invest?</h1>
                <img src="${pageContext.request.contextPath}/RESOURCES/images/are-you-thinking-invest-banner.png" 
                     alt="are-you-thinking-invest-banner" style="width: fit-content" /><br/>
                <br/>
                
                <h3>Personal Information:</h3>
                <p>Tell us a little about yourself...</p>
                <hr/>
                <table id="personalInformationTable" border="0" style="margin-left:25px; width:50%">
                    <tr>
                        <td><label>Last Name:</label></td>
                        <td><input type="text" placeholder="Enter Last Name" name="personalInfo_lastName" /></td>
                    </tr>
                     <tr>
                        <td><label>First Names:</label></td>
                        <td><input type="text" placeholder="Enter First Names" name="personalInfo_firstNames" /></td>
                    </tr>
                     <tr>
                        <td><label>Identity Number:</label></td>
                        <td><input type="text" placeholder="Enter ID Number" maxlength="13" name="personalInfo_idNumber" /></td>
                    </tr>
                     <tr>
                        <td><label>Email Address:</label></td>
                        <td><input type="text" placeholder="Enter Email Address" name="personalInfo_emailAddress" /></td>
                    </tr>
                     <tr>
                        <td><label>Contact Number:</label></td>
                        <td><input type="text" placeholder="Enter Contact Number" 
                                   maxlength="10"
                                   name="personalInfo_mobileNumber"/></td>
                    </tr>
                    
                </table>
            
                <hr/>
               
                <h3>Risk Profile Analysis Test:</h3>
                <p>Now, take the risk profile analysis test, to see what 
                    kind of an investor you are...</p>
                <br/>
                <div>
                    <img src="${pageContext.request.contextPath}/RESOURCES/images/dividers/question_divider_1.png" 
                         alt="question-divider-1" style="height: 32px"/><br/>
                    <blockquote>My current age is:</blockquote>
                    <table class="table table-condensed" style="width: 50%">
                        <tbody>
                            <tr><td>
                                    <label class="checkbox-inline">
                                        <input type="radio" name="riskProfile_question_1" /> 
                                        31 to 40
                                    </label>
                                </td>
                            </tr>
                            <tr><td>
                                    <label class="checkbox-inline">
                                        <input type="radio" name="riskProfile_question_2" /> 
                                        41 to 50
                                    </label>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    
                    <hr/>
                    
                    <p>By taking the risk profile analysis test and showing interest to invest
                       with <a href="#" title="Inkunzi Investments" target="_blank">Inkunzi Investments</a>,<br/>
                        you agree to the <a href="#" title="Terms and Conditions">Terms and Conditions</a>.</p>
                    
                    <br/>
                    
                    <input type="submit" name="submitRiskProfileData" class="btn btn-primary btn-lg" value="Submit"
                           ondblclick="return false"/>
                    
                    <br/><br/>
                    
                </div> 
            
            </form>
        </div>
    </body>
</html>
