<?php
// start the application session
ini_set('session.cookie_lifetime', 60 * 15);
session_start(); 

// variables
$username = null;
$is_signed_in = false;
$user_type = 0;

// now check if there is any valid session
if ((isset($_SESSION['is_signed_in']) && $_SESSION['is_signed_in'] == true) &&
        ((isset($_SESSION['username'])) && $_SESSION['username'] != null) && 
        (isset($_SESSION['user_type']) && $_SESSION['user_type'] > 0)) {
    
    // get the session values
    $is_signed_in = $_SESSION['is_signed_in'];
    $username = $_SESSION['username'];
    $user_type = $_SESSION['user_type'];
    
    
}

else {

    // else destroy the session
    // anyway
    session_destroy();
    
}
?>

