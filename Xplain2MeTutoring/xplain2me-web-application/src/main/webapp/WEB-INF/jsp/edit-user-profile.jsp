
<%@page import="za.co.xplain2me.webapp.component.ProfileManagementForm"%>
<%@page import="za.co.xplain2me.util.BooleanToText"%>
<%@page import="za.co.xplain2me.entity.PhysicalAddress"%>
<%@page import="za.co.xplain2me.entity.Citizenship"%>
<%@page import="za.co.xplain2me.entity.Gender"%>
<%@page import="za.co.xplain2me.util.DateTimeUtils"%>
<%@page import="za.co.xplain2me.entity.ProfileType"%>
<%@page import="za.co.xplain2me.entity.Profile"%>

<%
    
    ProfileManagementForm form = (ProfileManagementForm)session
            .getAttribute(ProfileManagementForm.class.getName());
    
    if (form == null) {
        response.sendRedirect(request.getContextPath() + 
                RequestMappings.BROWSE_EXISTING_USERS + 
                "?invalid-request=1");
        return;
    }
    
    Profile profile = form.getProfile();
    boolean found = (profile != null);
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
    <head>
        <title>Edit User Profile</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        <%@include file="../jspf/template/bootstrap-datepicker.jspf" %>
        <%@include file="../jspf/template/form-validation-script.jspf" %>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <h2>Edit User Profile</h2>
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <%
                    if (found) {
                %>
                
                <div class="row">
                    <div class="col-md-6">
                        
                        <!-- start: Profile Type -->
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">
                                    PROFILE INFORMATION
                                </h3>
                            </div>

                            <div class="panel-body">
                                
                                <table border="0" class="table table-stripped" style="font-size: 100%">
                                    <tbody>
                                        <tr>
                                            <td><strong>Profile ID</strong></td>
                                            <td><%= profile.getId() %></td>
                                        </tr>
                                        <tr>
                                            <td><strong>Username</strong></td>
                                            <td><%= profile.getPerson().getUser().getUsername() %></td>
                                        </tr>
                                        <tr>
                                            <td><strong>Active</strong></td>
                                            <td><%= BooleanToText.format(profile.getPerson().getUser()
                                                    .isActive(), BooleanToText.YES_NO_FORMAT) %></td>
                                        </tr>
                                        <tr>
                                            <td><strong>Profile Type:</strong></td>
                                            <td>
                                                <%= profile.getProfileType().getDescription() %>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><strong>Date Created:</strong></td>
                                            <td><%= DateTimeUtils.formatDateTime(profile.getDateCreated()) %></td>
                                        </tr>
                                        <tr>
                                            <td><strong>Profile Verified</strong></td>
                                            <td><%= BooleanToText.format(profile.isVerified(), BooleanToText.YES_NO_FORMAT) %></td>
                                        </tr>
                                    </tbody>
                                </table>
                                        
                            </div>

                        </div>
                        <!-- end: Profile Type -->
                        
                        <!-- start: Personal Information -->
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">
                                    UPDATE PERSONAL INFORMATION
                                </h3>
                            </div>

                            <div class="panel-body">
                                
                                <form id="personForm" name="personForm" method="post"
                                      action="<%= request.getContextPath() + RequestMappings.EDIT_USER_PROFILE %>">
                                    
                                    <div class="form-group">
                                        <label>Last Name:</label>
                                        <input type="text" name="lastName" id="lastName"
                                               value="<%= profile.getPerson().getLastName() %>"
                                               class="form-control"
                                               data-validation="length" data-validation-length="3-32" />
                                    </div>
                                               
                                    <div class="form-group">
                                        <label>First Names:</label>
                                        <input type="text" name="firstNames" id="firstNames"
                                               value="<%= profile.getPerson().getFirstNames() %>"
                                               class="form-control"
                                               data-validation="length" data-validation-length="3-64" />
                                    </div>
                                               
                                    <div class="form-group">
                                        <label>Date of Birth:</label>
                                        <input type="text" name="dateOfBirth" id="dateOfBirth"
                                               value="<%= DateTimeUtils.formatDate(profile.getPerson().getDateOfBirth()) %>"
                                               class="form-control datepicker"
                                               data-validation="birthdate" data-validation-format="yyyy-mm-dd" />
                                    </div>
                                               
                                    <div class="form-group">
                                        <label>ID / Passport Number:</label>
                                        <input type="text" name="idNumber" id="idNumber"
                                               value="<%= profile.getPerson().getIdentityNumber() %>"
                                               class="form-control" maxlength="24"
                                               data-validation="length" data-validation-length="6-24" />
                                    </div>
                                               
                                    <div class="form-group">
                                        <label>Gender:</label>
                                        <select name="gender" id="gender"
                                               class="form-control">
                                            
                                            <%
                                                String selected = profile.getPerson().getGender().getDescription();
                                                for (Gender gender : form.getGender().values()) {
                                                    %>
                                                    <option value="<%= gender.getId() %>" <%= (selected.equalsIgnoreCase(gender.getDescription())) ? "selected" : "" %>>
                                                        <%= gender.getDescription() %>
                                                    </option>
                                            <%
                                                }
                                            %>
                                            
                                        </select>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label>Citizenship:</label>
                                        <select name="citizenship" id="citizenship"
                                               class="form-control">
                                            
                                            <%
                                                selected = profile.getPerson().getCitizenship().getDescription();
                                                for (Citizenship c : form.getCitizenship().values()) {
                                                    
                                                    %>
                                                    <option value="<%= c.getId() %>" <%= (selected.equalsIgnoreCase(c.getDescription())) ? "selected" : "" %>>
                                                        <%= c.getDescription() %>
                                                    </option>
                                            <%
                                                }
                                            %>
                                            
                                        </select>
                                    </div>
                                            
                                    <div class="form-group">
                                        <input type="submit"
                                               value="Update Personal Details" ondblclick="return false"
                                               class="btn btn-primary" />
                                    </div>
                                    
                                </form>
                                
                               
                            </div>

                        </div>
                        <!-- end: Personal Information -->
                        
                        <!-- start: Contact Details -->
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">
                                    EDIT CONTACT INFORMATION
                                </h3>
                            </div>

                            <div class="panel-body">
                                
                                <form id="contactInfoForm" name="contactInfoForm"
                                      method="post" 
                                      action="<%= request.getContextPath() + RequestMappings.EDIT_USER_PROFILE %>">
                                    
                                    <div class="form-group">
                                        <label>Email Address:</label>
                                        <input type="email" id="emailAddress" name="emailAddress"
                                               placeholder="Enter your email address (Required)"
                                               class="form-control" 
                                               value="<%= profile.getPerson().getContactDetail().getEmailAddress() %>"/>
                                    </div>
                                    
                                    <%
                                        String contactNumber = profile.getPerson()
                                                .getContactDetail().getCellphoneNumber();
                                    %>
                                    
                                    <div class="form-group">
                                        <label>Contact Number:</label>
                                        <input type="text" id="contactNumber" name="contactNumber"
                                            class="form-control" placeholder="Enter your contact number (Required)" 
                                            data-validation="number"
                                            value="<%= contactNumber %>"/>
                                        
                                    </div>
                                    
                                    <div class="form-group">
                                        <input type="submit" value="Update Contact Details"
                                               class="btn btn-primary"
                                               ondblclick="return false"/>
                                    </div> 
                                    
                                </form>
                            </div>

                        </div>
                        <!-- end: Contact Details -->
                        
                        <!-- start: Physical Address -->
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">
                                    EDIT RESIDENTIAL ADDRESS
                                </h3>
                            </div>

                            <div class="panel-body">
                                
                                <%
                                    PhysicalAddress address = profile.getPerson().getPhysicalAddress();
                                %>

                                <form id="addressForm" id="addressForm"
                                    method="post"
                                    action="<%= request.getContextPath() + RequestMappings.EDIT_USER_PROFILE %>">
                                    
                                    <!-- Physical Address Line 1 to 4 -->
                                    <div class="form-group">
                                        <label>Physical Address:</label>

                                        <input type="text" id="physicalAddressLine1" name="physicalAddressLine1"
                                               class="form-control" placeholder="Enter street number / street name / unit number etc. (Required)"
                                               value="<%= (address.getAddressLine1() == null) ? "" : address.getAddressLine1() %>"
                                               max-length="36" />

                                        <input type="text" id="physicalAddressLine2" name="physicalAddressLine2"
                                               class="form-control" placeholder="Enter complex name / section / extension etc. (Optional)"
                                               value="<%= (address.getAddressLine2() == null) ? "" : address.getAddressLine2() %>"
                                               max-length="36" />

                                        <input type="text" id="suburb" name="suburb"
                                               class="form-control" placeholder="Enter suburb / town (Required)"
                                               value="<%= (address.getSuburb() == null) ? "" : address.getSuburb() %>"
                                               max-length="36" />

                                        <input type="text" id="city" name="city"
                                               class="form-control" placeholder="Enter city / municipality (Required)"
                                               value="<%= (address.getCity() == null) ? "" : address.getCity() %>"
                                               max-length="36" />
                                    </div>

                                    <!-- Area Code -->
                                    <div class="form-group">
                                        <label>Area Code:</label>
                                        <input type="text" id="areaCode" name="areaCode"
                                               class="form-control" placeholder="Enter area code (Required)"
                                               value="<%= (address.getAreaCode() == null) ? "" 
                                                       : address.getAreaCode() %>" max-length="6" />
                                    </div>

                                    <div class="form-group">
                                            <input type="submit" value="Update Residential Address"
                                                   class="btn btn-primary"
                                                   ondblclick="return false"/>
                                    </div> 
                                    
                                </form>
                            </div>

                        </div>
                        <!-- end: Physical Address -->
                        
                    </div>
                </div>
                                    
                <%
                    }
                %>
                
            </div>
            
            <%@include file="../jspf/template/default-footer.jspf" %> 
            
        </div>
    </body>
</html>
