<%-- 
    Document   : redirect
    Created on : 15 Dec 2014, 8:47:07 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String hostName = request.getHeader("Host");
    
    if (hostName.equalsIgnoreCase("portal.xplain2me.co.za")) {
        response.sendRedirect("http://" + hostName + "/xplain2me");
        return;
    }
    
    if (hostName.equalsIgnoreCase("davidanddavis.com")) {
        response.sendRedirect("http://" + hostName + "/davidAndDavis");
        return;
    } 
%>
