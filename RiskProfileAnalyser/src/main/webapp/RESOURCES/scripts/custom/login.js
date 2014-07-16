
function validateForm(f) {

    var valid = true;

    var usernameText = document.getElementsByName("username")[0];
    var passwordText = document.getElementsByName("password")[0];

    var alertBox = document.getElementById("alertBox");
    alertBox.style.display = "none";
    alertBox.innerHTML = "";

    if (usernameText.value == null || usernameText.value.length < 6) {
        alertBox.innerHTML += "<p>Username must have 6 characters or more.</p>";
        valid = false;
    }

    if (passwordText.value == null || passwordText.value.length == 0) {
        alertBox.innerHTML += "<p>Password cannot be empty.</p>";
        valid = false;
    }

    if (!valid) {
         alertBox.style.display = "block";
    }

    return valid;
}


