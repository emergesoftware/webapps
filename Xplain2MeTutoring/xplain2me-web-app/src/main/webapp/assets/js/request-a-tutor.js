
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
* Validates the form data before the
* submission
* 
* @param {type} form
* @returns {undefined}
*/
function validateRequestTutorForm(form) {
    
   if (form === null) {
       form = document.getElementById("requestTutorForm");
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

   // validate the physical address
   if (form.physicalAddress.value === null || form.physicalAddress.value.length < 5) {
       errors += "<strong>Physical Address:</strong> value too short.<br/>";
       addClass("physicalAddressFormGroup", "has-error");
   }

   else {
        removeClass("physicalAddressFormGroup", "has-error");
        trimElementValue(form.physicalAddress);
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
   
   // validate if at least one subject has been
   // selected
   var atLeastOneSubjectSelected = false;
   for (var i = 0; i < form.subjects.options.length; i++) {
       if (form.subjects.options[i].selected === true) {
           atLeastOneSubjectSelected = true;
           break;
       }
   }
   
   if (atLeastOneSubjectSelected === false) {
       errors += "<strong>Subjects: </strong> must select at least one.<br/>";
        addClass("subjectsFormGroup", "has-error");
   }
   
   else {
        removeClass("subjectsFormGroup", "has-error");
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

   if (errors === "") return true;
   else {
       alertBlock.innerHTML = errors;
       $(alertBlock).css({ display : 'block' });
       return false;
   }

   return true;
}

