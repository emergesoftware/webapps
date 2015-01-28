<?php
// select
 if(isset($_SESSION['signed_in']) && $_SESSION['signed_in'] == true)
{
   $ID_No			= $_SESSION['user_id']; 
   $pool			   = $_SESSION['user_id'];
   $cool			   = $_SESSION['user_name'];
   $user_id			= $_SESSION['signed_in'];
   $Email			= $_SESSION['user_Email'];
   $name 			= $_SESSION['user_FName'];
   $midName 		= $_SESSION['midName'] ;
   $LastName		= $_SESSION['LastName'];
   $gender 			= $_SESSION['user_Gender'] ;
   $address 		= $_SESSION['user_Address'];
   $PostalAddress = $_SESSION['user_PostalAdd'];
   $user_pass 		= $_SESSION['user_Password'];
   $contact		   = $_SESSION['user_Contact'];
   
   
  
 
}
else{
	echo"$user_id";
	}
	

$query = "SELECT * FROM  tutors WHERE Email ='".$Email."' ";





?>