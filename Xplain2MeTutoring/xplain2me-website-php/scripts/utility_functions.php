<?php

/**
 * Sends a redirect to another URL.
 * Uses Permanent redirect (303) by default.
 * 
 * @param type $url
 * @param type $redirect_code
 */
function redirect($url, $redirect_code = 303) {
    header('Location:' . $url, $redirect_code);
    exit;
}

/**
 * Prints the appropriate HTML contents - for
 * a logged in and not logged in session
 * 
 * @param type $is_logged_in
 */
function printAccountSection($username, $is_logged_in) {
    
    if ($is_logged_in == true) {
        echo '<hr/>';
        echo '<br/>';
        echo '<h4>You are signed in.</h4>';
        echo '<p>Welcome back, <strong>' . $username .'</strong></p>';
        echo '<br/>';
        
    }
    
    else {
        
        echo '<hr/>';
        echo '<br/>';
        echo '<h4>You are not signed in.</h4>';
        echo '<br/>';
        
    }
    
}

?>