
/**
 * Adds a class to the element
 * 
 * @param {type} elementId
 * @param {type} className
 * @returns {undefined}
 */
function addClass(elementId, className) {
    
    var element = document.getElementById(elementId);
    if (element !== null)
        $(element).addClass(className);
}

/**
 * Removes a class from the element
 * 
 * @param {type} elementId
 * @param {type} className
 * @returns {undefined}
 */
function removeClass(elementId, className) {
    
    var element = document.getElementById(elementId);
    if (element !== null)
        $(element).removeClass(className);
}

/**
 * Trims off the white spaces from
 * an element's value
 * 
 * @param {type} element
 * @returns {undefined}
 */
function trimElementValue(element) {
    
    if (element !== null && element.value !== null)
        element.value = element.value.trim();
    
}

/**
 * Ends with string function
 * @param {type} str
 * @param {type} suffix
 * @returns {Boolean}
 */
function endsWith(str, suffix) {
    return (str.indexOf(suffix, 
            str.length - suffix.length) !== -1);
}

/**
* Validates the form data before the
* submission
* 
* @param {type} form
* @returns {undefined}
*/
function validateBecomeTutorForm(form) {
    
   if (form === null) {
       form = document.getElementById("becomeTutorForm");
   }

   var alertBlock = document.getElementById("alertBlock");
   alertBlock.innerHTML = "";

   var errors = "";

   // validate the first names
   if (form.firstNames.value === null || form.firstNames.value.length < 2) {
       errors += "<strong>First Names:</strong> value too short.<br/>";
       addClass("firstNamesFormGroup", "has-error");
   }
   else {
        removeClass("firstNamesFormGroup", "has-error");
        trimElementValue(form.firstNames);
   }

   // validate the last name 
   if (form.lastName.value === null || form.lastName.value.length < 2) {
       errors += "<strong>Last Name:</strong> value too short.<br/>";
       addClass("lastNameFormGroup", "has-error");
   }
   else {
        removeClass("lastNameFormGroup", "has-error");
        trimElementValue(form.lastName);
   }

   // validate the email address
   if (form.emailAddress.value === null || form.emailAddress.value.length < 8) {
       errors += "<strong>Email Address:</strong> value too short.<br/>";
       addClass("emailAddressFormGroup", "has-error");
   }

   else {
        removeClass("emailAddressFormGroup", "has-error");
        trimElementValue(form.emailAddress);
   }

   // validate the contact number
   if (form.contactNumber.value === null || form.contactNumber.value.length < 10) {
       errors += "<strong>Contact Number:</strong> value too short.<br/>";
       addClass("contactNumberFormGroup", "has-error");
   }

   else {
        removeClass("contactNumberFormGroup", "has-error");
        trimElementValue(form.contactNumber);
   }
   
   // validate the ID number
   if (form.idNumber.value === null || form.idNumber.value.length < 3) {
       errors += "<strong>Identity/Passport Number:</strong> value too short.<br/>";
       addClass("idNumberFormGroup", "has-error");
   }

   else {
        removeClass("idNumberFormGroup", "has-error");
        trimElementValue(form.idNumber);
   }
   
   // validate the date of birth
   if (form.dateOfBirth.value === null || form.dateOfBirth.value.length !== 10 || 
           form.dateOfBirth.value.indexOf("-") < 0) {
       errors += "<strong>Date Of Birth:</strong> invalid format.<br/>";
       addClass("dateOfBirthFormGroup", "has-error");
   }

   else {
        removeClass("dateOfBirthFormGroup", "has-error");
        trimElementValue(form.dateOfBirth);
   }

   // validate the street address
   if (form.streetAddress.value === null || form.streetAddress.value.length < 5) {
       errors += "<strong>Street Address:</strong> value too short.<br/>";
       addClass("streetAddressFormGroup", "has-error");
   }

   else {
        removeClass("streetAddressFormGroup", "has-error");
        trimElementValue(form.streetAddress);
   }


   // validate the suburb
   if (form.suburb.value === null || form.suburb.value.length < 3) {
       errors += "<strong>Suburb / Town:</strong> value too short.<br/>";
       addClass("suburbFormGroup", "has-error");
   }

   else {
        removeClass("suburbFormGroup", "has-error");
        trimElementValue(form.suburb);
   }

   // validate the city
   if (form.city.value === null || form.city.value.length < 3) {
       errors += "<strong>City:</strong> value too short.<br/>";
       addClass("cityFormGroup", "has-error");
   }

   else {
        removeClass("cityFormGroup", "has-error");
        trimElementValue(form.city);
   }

   // validate the area code
   if (form.areaCode.value === null || form.areaCode.value.length < 4) {
       errors += "<strong>Area Code:</strong> value too short.<br/>";
       addClass("areaCodeFormGroup", "has-error");
   }

   else {
        removeClass("areaCodeFormGroup", "has-error");
        trimElementValue(form.areaCode);
   }
   
   // validate if the user selected to have tutored before
   // that, at least one academic level is selected
   
   removeClass("academicLevelFormGroup", "has-error");
   
   if (form.tutoredBefore.value === "Yes") {
       
       var academicLevels = document.forms["becomeTutorForm"]
                .getElementsByTagName("input");
       
       var atLeastOneAcademicLevelSelected = false;
       
       for (var i = 0; i < academicLevels.length; i++) {
           if (academicLevels[i].type === "checkbox" &&
                   academicLevels[i].checked) {
               atLeastOneAcademicLevelSelected = true;
               break;
           }
       }
       
       if (atLeastOneAcademicLevelSelected === false) {
            errors += "<strong>Academic Levels Tutored: </strong> at least one " + 
                   "academic level must be selected if you specified to have " + 
                   "tutored before.<br/>";
            addClass("academicLevelFormGroup", "has-error");
       }
  
   }
   
    removeClass("documentsFormGroup", "has-error");
   
    // check that the CV is uploaded
    if (form.curriculumVitaeFile.value === null || form.curriculumVitaeFile.value.length === 0) {
        errors += "<strong>Curriculum Vitae: </strong> is required for upload.<br/>";
        addClass("documentsFormGroup", "has-error");
    }
   
    // check that the matric certificate is uploaded
    if (form.matricCertificateFile.value === null || form.matricCertificateFile.value.length === 0) {
        errors += "<strong>Copy of Matric Certificate: </strong> is required for upload.<br/>";
        addClass("documentsFormGroup", "has-error");
    }
    
    // check that the ID or passport is uploaded
    if (form.copyOfIDorPassportFile.value === null || form.copyOfIDorPassportFile.value.length === 0) {
        errors += "<strong>Copy of ID / Passport: </strong> is required for upload.<br/>";
        addClass("documentsFormGroup", "has-error");
    }
   
   // validate the motivation text
   if (form.motivation.value === null || form.motivation.value.trim().length === 0) {
        errors += "<strong>Motivational Text:</strong> please write some text to " + 
                "motivate your interest.<br/>";
        addClass("motivationFormGroup", "has-error");
   }

   else {
        removeClass("motivationFormGroup", "has-error");
   }
   
   
   // validate if the reCPATCHA challenge was
   // attempted
   var reCaptchaResponse = document.getElementById("recaptcha_response_field");
   if (reCaptchaResponse === null || reCaptchaResponse.value === null ||
           reCaptchaResponse.value.length === 0) {
       errors += "<strong>reCAPTCHA Challenge: </strong> need to complete the " +
            "challenge to prove that you are a human being.<br/>";
   }

   // validate if the applicant agreed to 
   // agreeToTermsOfService
   if (form.agreeToTermsOfService.checked === false) {
       errors += "<strong>Terms of Service:</strong> must agree before you submit.<br/>";
   }

   if (errors === "") {
       $(alertBlock).css({ display : 'none' });
       return true;
   }
   
   else {
        alertBlock.innerHTML = "<h4>The following errors were encountered:</h4>" +
                                "<p>" + 
                                    errors + 
                                "</p>";
        $(alertBlock).css({ display : 'block' });
       
        $('html, body').animate({
            scrollTop: $(alertBlock).offset().top
        }, "fast");
       
       return false;
   }

   return true;
}

function removeSupportingDocument(elementId) {
    var row = document.getElementById(elementId);
    if (row !== null)
        $(row).remove();
}

function addAnotherSupportingDocument() {
    
    var date = new Date();
    var elementId = date.getTime();
    
    var innerHTML = "<tr id='" + elementId + "'> " + 
                    "        <td>" +
                    "            <a href='#supportingDocumentsTable' class='close' " + 
                    "                 onclick='removeSupportingDocument(\"" + elementId +"\")'" + 
                    "                 style='font-weight: normal; text-transform: lowercase' " + 
                    "                 title='Remove'>" + 
                    "                  x" +
                    "            </a><br/><br/>" +
                    
                    "            <input type='text' name='supportingDocumentLabel'" +
                    "                  placeholder='Give this document a name'" +
                    "                   class='form-control' maxlength='24'/><br/>" +
                    
                    "            <input type='file' name='supportingDocumentFile'" + 
                    "                   value=''/><br/>" +
                    "        </td>" +
                    "    </tr>";
    
    var supportingDocumentsTable = document.getElementById("supportingDocumentsTable");
    supportingDocumentsTable.innerHTML += innerHTML;
    
}
