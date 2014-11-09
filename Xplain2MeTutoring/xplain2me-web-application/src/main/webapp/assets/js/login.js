
/**
 * Clears the password field
 * 
 * @returns {undefined}
 */
function clearPasswordField() {
    var password = document.getElementById("password");
    if (password !== null) {
        password.value = "";
    }
}

/**
 * Performs initialisation
 * 
 * @returns {undefined}
 */
function initialisation() {
    
    $(document).ready(function(){
        
        // clear the password field
        clearPasswordField();
        
    });
    
}

/**
 * Validates the login form fields
 * 
 * @param {type} form
 * @returns {Boolean}
 */
function validateLoginForm(form) {
    
    if (form === null) {
        form = document.getElementById("loginForm");
    }
    
    // the alert block
    var alertBlock = document.getElementById("alertBlock");
    alertBlock.innerHTML = "";
    
    // errors inner html content
    var errors = "";
    
    // check the username
    if (form.username.value === null || form.username.value.length < 8) {
        errors += "<strong>Username: </strong> must have at least 8 characters.<br/>";
        $("#usernameFormGroup").addClass("has-error");
    }
    
    else {
        $("#usernameFormGroup").removeClass("has-error");
    }
    
    // check the password
    if (form.password.value === null || form.password.value.length === 0) {
        errors += "<strong>Password: </strong> provided value is too short.<br/>";
        $("#passwordFormGroup").addClass("has-error");
    }
    
    else {
        $("#passwordFormGroup").removeClass("has-error");
    }
    
    // if there are no errors - hide the 
    // alert block
    if (errors === ""){
        $(alertBlock).css({ display : "none" });
        return true;
    }
    
    // else if there are, show the
    // alert block and prevent form
    // submission
    else {
        alertBlock.innerHTML = errors;
        $(alertBlock).css({ display : "block" });
        return false;
    }
    
}


