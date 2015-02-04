
/**
 * Pops out the Login Form in a 
 * separate window.
 * 
 * @param {type} returnUrl
 * @returns {undefined}
 */
function popOutLoginForm(returnUrl) {
    window.open('account/login.php?return_url=' + returnUrl,
    'LoginForm','height=660,width=900');
}