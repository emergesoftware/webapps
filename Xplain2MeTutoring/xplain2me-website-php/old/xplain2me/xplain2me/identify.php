		<?php	
		
		
		$_SESSION['user_Type']; 
		$_SESSION['user_FName'];
		$_SESSION['LastName'];
		
       		 $type=$_SESSION['user_Type'];
		$name=$_SESSION['user_FName'];
		$surname=$_SESSION['LastName'];
	 switch ($type) {
		 
		    case "1"://admin 

       echo "<strong><h4>Admin</h4></strong>";

       break;

   case "4": // instructor 

       echo "<strong><h4>Tutor</h4></strong>";

       break;

   case "3": // course Author 
      echo "<strong><h3>Course Author</h3></strong>";

       break;
	   
	 case "2":  //student 
	  echo "<strong><h3>Student</h3></strong>";    
	    break;
		 
		 }
		
		
		
       //echo" <br><hr>";
        
		//echo"<strong> <h3>Welcome<br></strong></h3>";
		echo"<strong> $name $surname</strong>";
		
	   echo" <br>";
	
		?>		
        