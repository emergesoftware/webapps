<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US" xml:lang="en">
<head>
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Xplain2me</title>
	<meta name="generator" content="TextMate http://macromates.com/">
	<meta name="author" content="Shepherd Siduli">
	<!-- Date: 2014-01-08 -->
	<link rel="stylesheet" href="../style.css" type="text/css" media="screen" />
        
	<style type="text/css">
	    #apDiv1 {
		position: absolute;
		width: 257px;
		height: 261px;
		z-index: 1;
		left: 253px;
		top: 565px;
	}
	.wrapper .post {
		-moz-border-radius:7px 7px 7px 7px;
		/*border:1px solid silver; */
		float:left;
		margin:10px;
		min-height:100px;
		padding:5px;
		width:200px;
	}
	    </style>
	    <!--[if IE 6]><link rel="stylesheet" href="style.ie6.css" type="text/css" media="screen" /><![endif]-->
	    <!--[if IE 7]><link rel="stylesheet" href="style.ie7.css" type="text/css" media="screen" /><![endif]-->

	    <script type="text/javascript" src="../script.js"></script>
	    <script type="text/javascript">
		    function beTutor() {
				newwindow2=window.open('','name','height=800,width=500');
				var tmp = newwindow2.document;
				tmp.write('<html><head><title>Tutor Form</title>');
				tmp.write('<link rel="stylesheet" href="../style.css" type="text/css" media="screen" />');
				tmp.write('<style type="text/css">');
				tmp.write('label {display:block; position:relative;}');
				tmp.write('label span {font-weight:bold;position:absolute;left: 3px;}');
				tmp.write('label input, label textarea, label select {margin-left: 120px; }');
				tmp.write('</style>');
				tmp.write('<form action="?" method="?">');
				tmp.write('<label><span>First name: </span><input type="text" name="firstname"/></label>');
				tmp.write('<label><span>Last name: </span><input type="text" name="lastname"></label>');
				tmp.write('<label><span>Email Address: </span><input type="text" name="email"/></label>');
				tmp.write('<label><span>Contact Number: </span><input type="text" name="contactNumber"/></label>');
				tmp.write('<label><span>Physical Address: </span><input type="text" name="physicalAddress"/></label>');
				tmp.write('<label><span>City: </span><input type="text" name="city"/></label>');
				tmp.write('<label><span>Surburb: </span><input type="text" name="surburb"/></label>');
				tmp.write('<label><span>Street: </span><input type="text" name="street"/></label>');
				tmp.write('<label><span>ID / Passport: </span><input type="text" name="id"/></label>');
				tmp.write('<label><span>Date of Birth: </span><input type="text" name="dob"/></label>');
				tmp.write('<label><span>Citizenship: </span><input type="text" name="citizenship"/></label>');
				tmp.write('<label><span>Gender: </span><select> <option value="Male">Male</option><option value="Female">Female</option></select></label>');
				tmp.write('<input type="checkbox" name="TOS" value="Accept" onClick="EnableSubmit()"> I agree to <a href="terms.pdf" target="_blank" >Terms of Service agreement</a>.');
				tmp.write('<br/>');
				tmp.write('<input type="submit" value="Submit" onclick="javascript:self.close()"> ');
				tmp.write('</form>');
				tmp.write('</body></html>');
				tmp.close();
			}

			function getTutor() {
				newwindow2=window.open('','name','height=600,width=500');
				var tmp = newwindow2.document;
				tmp.write('<html><head><title>Get a Tutor Form</title>');
				tmp.write('<link rel="stylesheet" href="../style.css" type="text/css" media="screen" />');
				tmp.write('<style type="text/css">');
				tmp.write('label {display:block; position:relative;}');
				tmp.write('label span {font-weight:bold;position:absolute;left: 6px;}');
				tmp.write('label input, label textarea, label select {margin-left: 200px; }');
				tmp.write('</style>');
				tmp.write('<form>');
				tmp.write('<label><span>First name: </span><input type="text" name="firstname"/></label>');
				tmp.write('<label><span>Last name: </span><input type="text" name="lastname"></label>');
				tmp.write('<label><span>Email Address: </span><input type="text" name="email"/></label>');
				tmp.write('<label><span>Contact Number: </span><input type="text" name="contactNumber"/></label>');
				tmp.write('<label><span>Physical Address: </span><input type="text" name="physicalAddress"/></label>');
				tmp.write('<label><span>City: </span><input type="text" name="city"/></label>');
				tmp.write('<label><span>Surburb: </span><input type="text" name="surburb"/></label>'); 
				tmp.write('<label><span>Street: </span><input type="text" name="street"/></label>');
				tmp.write('<label><span>Gender: </span><select> <option value="Male">Male</option><option value="Female">Female</option></select></label>');
				tmp.write('<label><span>Student Level: </span><select> <option value="Grade 1">Grade 1</option><option value="Grade 2">Grade 2</option><option value="Grade 3">Grade 3</option><option value="Grade 4">Grade 4</option><option value="Grade 5">Grade 5</option><option value="Grade 6">Grade 6</option><option value="Grade 7">Grade 7</option><option value="Grade 8">Grade 8</option><option value="First Year">First Year</option><option value="Second Year">Second Year</option><option value="Third Year">Third Year</option><option value="Fourth Year">Fourth Year</option></select></label>');
				tmp.write('<label><span>Subject(s): </span><textarea row="40"></textarea></label>');
				tmp.write('<label><span>Learner problem areas and <br>necessary additional information : </span><textarea style="width:250px; height:150px;"></textarea></label>');
				tmp.write('<br/>');
				tmp.write('<input type="checkbox" name="TOS" value="Accept" onClick="EnableSubmit()"> I agree to <a href="terms.pdf" target="_blank" >Terms of Service agreement</a>.');
				tmp.write('<br/>');
				tmp.write('<input type="submit" value="Submit" onclick="javascript:self.close()"> ');
				tmp.write('</form>');
				tmp.write('</body></html>');
				tmp.close();
			}


			/*function login(){

				newwindow2=window.open('','name','height=600,width=500');
				var tmp = newwindow2.document;
				tmp.write('<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">');
				tmp.write('<html>');
				tmp.write('<head>');
				tmp.write('<title></title>');
				tmp.write('  <style type="text/css">');
				tmp.write('  #popupbox{');
				tmp.write('  margin: 0; ');
				tmp.write('  margin-left: 40%; ');
				tmp.write('  margin-right: 40%;');
				tmp.write('  margin-top: 50px; ');
				tmp.write('  padding-top: 10px; ');
				tmp.write('  width: 20%; ');
				tmp.write('  height: 150px; ');
				tmp.write('  position: absolute;'); 
				tmp.write('  background: #FBFBF0; ');
				tmp.write('  border: solid #000000 2px;'); 
				tmp.write('  z-index: 9; ');
				tmp.write('  font-family: arial;'); 
				tmp.write('  visibility: hidden; ');
				tmp.write('  }');
				tmp.write('  </style>');
				tmp.write('  <script language="JavaScript" type="text/javascript">');
				tmp.write('  function login(showhide){');
				tmp.write('    if(showhide == "show"){');
				tmp.write('        document.getElementById("popupbox").style.visibility="visible";');
				tmp.write('    }else if(showhide == "hide"){');
				tmp.write('        document.getElementById("popupbox").style.visibility="hidden";'); 
				tmp.write('    }');
				tmp.write('  }');
				tmp.write('  </scrip>');
				tmp.write('</head>');
				tmp.write('<body>');
				tmp.write('<div id="popupbox">'); 
				tmp.write('<form name="login" action="" method="post">');
				tmp.write('<center>Username:</center>');
				tmp.write('<center><input name="username" size="14" /></center>');
				tmp.write('<center>Password:</center>');
				tmp.write('<center><input name="password" type="password" size="14" /></center>');
				tmp.write('<center><input type="submit" name="submit" value="login" /></center>');
				tmp.write('</form>');
				tmp.write('<br />');
				tmp.write('<center><a href="javascript:login("hide");">close</a></center> ');
				tmp.write('</div>');
				tmp.write('<p><a href="javascript:login("show");">login</a></p>');
				tmp.write('</body>');
				tmp.write('</html>'); 
			}  */

			var myMarquee = document.getElementById('myMarquee'); 
			run();
			function run() {
			    setTimeout(function() {
			        myMarquee.stop();
			        setTimeout(function(){
			            myMarquee.start();
			            run();    
			        },3000);   
			    },3000);
			}  

			function login(showhide){
			    if(showhide == "show"){
			        document.getElementById('popupbox').style.visibility="visible";
			    }else if(showhide == "hide"){
			        document.getElementById('popupbox').style.visibility="hidden"; 
			    }
			 }  






		</script>
</head>
<body>
	    <div id="art-page-background-gradient"></div>
	    <div id="art-page-background-glare">
	        <div id="art-page-background-glare-image"></div>
	    </div>
	    <div id="art-main">
	        <div class="art-sheet">
	            <div class="art-sheet-tl"></div>
	            <div class="art-sheet-tr"></div>
	            <div class="art-sheet-bl"></div>
	            <div class="art-sheet-br"></div>
	            <div class="art-sheet-tc"></div>
	            <div class="art-sheet-bc"></div>
	            <div class="art-sheet-cl"></div>
	            <div class="art-sheet-cr"></div>
	            <div class="art-sheet-cc"></div>
				
	            <div class="art-sheet-body">
	                      <div class="art-header">
	                    <div class="art-header-jpeg"></div>
	                  <script type="text/javascript" src="../swfobject.js"></script>
	                       <div id="art-flash-area">
	                    <div id="art-flash-container">
	                    <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="900" height="225" id="art-flash-object">
	                      <param name="movie" value="../images/flash.swf">
	                        <param name="quality" value="high">
	                    	<param name="scale" value="exactfit">
	                    	<param name="wmode" value="transparent">
	                    	<param name="flashvars" value="color1=0xFFFFFF&amp;alpha1=.70&amp;framerate1=24&amp;loop=true">
	                        <param name="swfliveconnect" value="true">
	                        <!--[if !IE]>-->
	                      <object type="application/x-shockwave-flash" data="../images/flash.swf" width="900" height="225">
	                            <param name="quality" value="high">
	                    	    <param name="scale" value="exactfit">
	                    	    <param name="wmode" value="transparent">
	                    	    <param name="flashvars" value="color1=0xFFFFFF&amp;alpha1=.70&amp;framerate1=24&amp;loop=true">
	                            <param name="swfliveconnect" value="true">
	                        <!--<![endif]-->
	                          	<div class="art-flash-alt"><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player"></a></div>
	                        <!--[if !IE]>-->
	                        </object>
	                        <!--<![endif]-->
	                    </object>
	                    </div>
	                    </div>
	                    <script type="text/javascript">swfobject.switchOffAutoHideShow();swfobject.registerObject("art-flash-object", "9.0.0", "expressInstall.swf");</script>
	                    <div class="art-logo">
	                        <h1 id="name-text" class="art-logo-name"><a href="#"> &nbsp;&nbsp;&nbsp;Xplain2me Tutoring <br> <h5>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"Simplify your learning"</h5></a></h1>
	                        <div id="slogan-text" class="art-logo-text"></div>
	                    </div>
	                </div>
	                <div class="art-nav">
	                	<div class="l"></div>
	                	<div class="r"></div>
	                	<ul class="art-menu">
	                		<li>
	                			<a href="#"><span class="l"></span><span class="r"></span><span class="t">Home</span></a>
	                		</li>

	                		<li>
	                			<a href="about.php"  class="active"><span class="l"></span><span class="r"></span><span class="t">About</span></a>
	                		</li>
							<li>
	                			<a href="subjects.php"  class="active"><span class="l"></span><span class="r"></span><span class="t">Subjects</span></a>
	                		</li>
							<li>
	                			<a href="rates.php"  class="active"><span class="l"></span><span class="r"></span><span class="t">Rates</span></a>
	                		</li>
	                        <li>
	                			<a href="contactus.php" class="active"><span class="l"></span><span class="r"></span><span class="t">Contact us</span></a>
	                		</li>
	                	</ul>
	                </div>
	                <div class="art-content-layout">
	                    <div class="art-content-layout-row">
	                    &nbsp;<div class="art-layout-cell art-content">
	                    <br/>
	                    <a onclick="getTutor()">Get a tutor </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="beTutor()">Becoma a tutor </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="login()">Tutor login</a> 
	                     <!-- <p><h2><strong>Our Tutors</strong></h2></p>
                            <p>Xplain2me tutors are selectively sourced out from different universities throughout the country. Our tutors are either graduates or university students on their course to graduating. All of our tutors go through a screening process by qualification competence review and interviews</p>
							<p><h2><strong>Our Services</strong></h2></p>
                        <p>Our tutors travel to you and offer tutorials at the comfort of your own homes and at your request, we replace our tutors at no additional cost. The progress of students is monitored and corrective steps and timely adjustments are made where necessary to ensure set performance targets are met                        </p>
							<p><h2><strong>Our Rates </strong></p></h2>
                    <p>We offer affordable rates to our clients.The more lessons the client buys, the cheaper the package</p>
                    <p><h2><strong>What we do</strong></h2></p>
                <li>Providing appropriate study methods to enhance easy learning.</li>
                <li>Breaking down complex concepts to an understandable level </li>
                <li>Pinpointing the important things in the modules and chapters for tests and exam preparations.</li>
                <li>Unpacking and explaining difficult components of the modules to improve understanding.</li> -->

                <div class="wrapper">
<div class="post">
    <div>
        <p><h2><strong>Our Tutors</strong></h2></p>
        <p>Xplain2me tutors are selectively sourced out from different universities throughout the country. Our tutors are either graduates or university students on their course to graduating. All of our tutors go through a screening process by qualification competence review and interviews</p>
        <br/>
        <p><h2><strong>What we do</strong></h2></p>
        <li>Providing appropriate study methods to enhance easy learning.</li>
        <li>Breaking down complex concepts to an understandable level </li>
        <li>Pinpointing the important things in the modules and chapters for tests and exam preparations.</li>
        <li>Unpacking and explaining difficult components of the modules to improve understanding.</li>
    </div>
</div>

<div class="post">
    <div>
        <p><h2><strong>Our Services</strong></h2></p>
         <p>Our tutors travel to you and offer tutorials at the comfort of your own homes and at your request, we replace our tutors at no additional cost. The progress of students is monitored and corrective steps and timely adjustments are made where necessary to ensure set performance targets are met </p>
        <br/>
        <p><h2><strong>Our Rates </strong></p></h2>
        <p>We offer affordable rates to our clients.The more lessons the client buys, the cheaper the package</p>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>

        <a href="#" color = "blue">Request a qoute </a> for a quotation to be sent to you

    </div>
</div>
</div>

<div class="post">
<br/>
<p><h2><strong>Why choose us?</strong></h2></p>
 <p> University students/graduates offering  &nbsp;&nbsp;one to one tutoring services at the comfort of your homes.</p>
 <p>Get one on one tutorial for your child and avail them to:</p>
 <li>Improved pass rate through selectively pinpointing student problem areas and resolving them.</li>
 <li>Individual attention given by tutors to students increases the scope of concentration, thereby enabling profound understanding.</li>
 <li>Easy revision assisted by study and revision material handed out by our tutors. </li>
 <p>All of our tutors go through a screening process and are selectively chosen to meet your peculiar needs and will work to make your child:</p>
 <li>Improve understanding by unpacking the finer details in modules.</li>
 <li>Enhance easy learning through provision of appropriate study methods.</li>
 <li>Break down complex concepts to an understandable level</li>
 <li>Pinpoint the important aspects and factors in modules and chapters for tests and exam preparation.</li>   
 
 </div>                                                

	                      <div class="art-post"></div>
	                      </div>
	                        <div class="art-layout-cell art-sidebar1" style="height: 555px"> <!--onMouseOver="this.stop();" onMouseOut="this.start();" -->
	                          <p>&nbsp;</p>
	                          <!-- it happens here -->
	                         <marquee behavior="scroll" direction="up" id="myMarquee"><div>"Education is the most powerful weapon which you can use to change the world."</div><br><br><div>"Don't limit a child to your own learning, for he was born in another time."</div><br><br><div>"Develop a passion for learning. If you do, you will never cease to grow."</div><br><br><div>"Education is simply the soul of a society as it passes from one generation to another."</div></marquee>
								<!--<marquee direction="up" id="myMarquee">"Education is the most powerful weapon which you can use to change the world."</marquee> -->
	                            <!-- Login starts here -->

	                            <p>
                      <?php
//signin.php
//session_start();
include '../connect.php';
echo '<h3>Sign in</h3><br />';

//first, check if the user is already signed in. If that is the case, there is no need to display this page
if(isset($_SESSION['signed_in']) && $_SESSION['signed_in'] == true)
{
	echo 'You are already signed in, you can <a href="../logout.php">sign out</a> if you want.';
}
else
{
	if($_SERVER['REQUEST_METHOD'] != 'POST')
	{
		/*the form hasn't been posted yet, display it
		  note that the action="" will cause the form to post to the same page it is on */
		echo '<form method="post" action="">
			Username: <input type="text" name="user_name" /><br />
			Password: <input type="password" name="user_pass"><br />
			<br />
			<input type="submit" value="Sign in" class="art-button" />
		 </form>';
	}
	else
	{
		/* so, the form has been posted, we'll process the data in three steps:
			1.	Check the data
			2.	Let the user refill the wrong fields (if necessary)
			3.	Varify if the data is correct and return the correct response
		*/
		$errors = array(); /* declare the array for later use */
		
		if(!isset($_POST['user_name']))
		{
			$errors[] = 'The username field must not be empty.';
		}
		
		if(!isset($_POST['user_pass']))
		{
			$errors[] = 'The password field must not be empty.';
		}
		
		if(!empty($errors)) /*check for an empty array, if there are errors, they're in this array (note the ! operator)*/
		{
			echo 'Uh-oh.. a couple of fields are not filled in correctly..<br /><br />';
			echo '<ul>';
			foreach($errors as $key => $value) /* walk through the array so all the errors get displayed */
			{
				echo '<li>' . $value . '</li>'; /* this generates a nice error list */
			}
			echo '</ul>';
		}
		else
		{
			//the form has been posted without errors, so save it
			//notice the use of mysql_real_escape_string, keep everything safe!
			//also notice the sha1 function which hashes the password
			$myusername=$_POST['user_name']; 
			$mypassword=$_POST['user_pass'];
			$sql="SELECT * FROM `xplainme_xplain2me`.`tutors` WHERE Username='$myusername' and Password='$mypassword'";
						
			$result = mysql_query($sql);
			if(!$result)
			{
				//something went wrong, display the error
				echo 'Something went wrong while signing in. Please try again later.';
				echo mysql_error(); //debugging purposes
			}
			else
			{
				//the query was successfully executed, there are 2 possibilities
				//1. the query returned data, the user can be signed in
				//2. the query returned an empty result set, the credentials were wrong
				if(mysql_num_rows($result) == 0)
				{
					echo 'You have supplied a wrong user/password combination. Please try again.';
				}
				else
				{
					//set the $_SESSION['signed_in'] variable to TRUE
					$_SESSION['signed_in'] = true;
					
					// put the user_id and user_name values in the $_SESSION, so we can use it at various pages
					while($row = mysql_fetch_assoc($result))
					{
					
					  // $_SESSION['user_id'] 	 	= $row['ID_No'];
						$_SESSION['user_FirstName'] 	= $row['FirstName'];
						$_SESSION['user_LastName'] 	= $row['LastName'];
						$_SESSION['user_Email'] 	= $row['Email'];
						$_SESSION['user_Contact'] 	= $row['ContactNumber'];
						$_SESSION['user_Address'] 	= $row['Address'];
						$_SESSION['user_City']  	= $row['City'];
						$_SESSION['user_Surburb'] 	= $row['Surburb'];
						$_SESSION['user_Street'] 	= $row['Street'];
						$_SESSION['user_Passport'] 	= $row['ID_Passport'];
						$_SESSION['user_DateOfBirth'] 	= $row['DateOfBirth'];
						$_SESSION['user_Citizenship'] 	= $row['Citizenship'];
						$_SESSION['user_Gender'] 	= $row['Gender'];
						$_SESSION['user_name'] 		= $row['Username'];
						$_SESSION['user_Password'] 	= $row['Password'];
						$_SESSION['user_Type']  	= $row['TypeID'];
		
						
					
					}
					
					
		//switch case to dertemine user login page
$sql="SELECT * FROM types";
while($row = mysql_fetch_assoc($result))
					{
$_SESSION['user_Type'] = $row['TypeID'];
					}

$Type=$_SESSION['user_Type'];

	
	switch ($Type) {

   case "1"://admin login

       echo "<meta http-equiv=\"REFRESH\" Content=\"0; URL=..\admin\survey.php\">";

       break;

   case "0": // tutor login

       echo "<meta http-equiv=\"REFRESH\" Content=\"0; URL= ..\/tutor\message.php\">";

       break;

   case "3": // course Author login

      echo "<meta http-equiv=\"REFRESH\" Content=\"0; URL=..\courseA\Create Course.php\">";

       break;
	   
	 case "2":  //student login
	  echo " <meta http-equiv=\"REFRESH\" Content=\"0; URL= ..\student\messageIn.php\">";    
	    break;

}
				}
			}
		}
	}
}


?>
                          </p>

                          	<p><a href="forgotPasswod.php"><strong>Forgot password</strong></a></p>

	                            <!-- Login ends here -->

	                            </div> 
	                        </div>
	                    </div>
	                </div>
					
	                <div class="cleared"></div><div class="art-footer">
	                    <div class="art-footer-t"></div>
	                    <div class="art-footer-l"></div>
	                    <div class="art-footer-b"></div>
	                    <div class="art-footer-r"></div>
	                    <div class="art-footer-body">
	                      <div class="art-footer-text">
	                          <p><span class="footer"><a href="about.php"><strong>About us</strong></a><strong> | <a href="contactus.php">Contact us</a> | <a href="#">Sitemap</a></strong></span><br />
	                                Copyright &copy; 2010 - 2014 --All Rights Reserved.</p>
	                        </div>
	                		<div class="cleared"></div>
	                    </div>
	                </div>
	        		<div class="cleared"></div>
	            </div>
	        </div>
	        <div class="cleared"></div>
	        <p class="art-page-footer">&nbsp;</p>
	    </div>
	
</body>
</html>