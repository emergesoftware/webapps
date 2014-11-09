/**
* Validates the form
* 
* @param {type} form
* @returns {Boolean}
*/
function validateVerificationCodeForm(form) {

   if (form === null) {
       form = document.getElementById("verificationCodeForm");
   }

   if (form.verificationCode.value === null || 
           form.verificationCode.value.length !== 9 || 
           isNaN(form.verificationCode.value) === true) {

       $("#verificationCodeFormGroup").addClass("has-error");
       return false;
   }

   else {

       $("#verificationCodeFormGroup").removeClass("has-error");
       return true;
   }

}

