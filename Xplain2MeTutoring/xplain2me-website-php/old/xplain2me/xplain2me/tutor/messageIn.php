<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US" xml:lang="en">
<head>
   
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Xplain2me Tutoring</title>

    <link rel="stylesheet" href="../style.css" type="text/css" media="screen" />
    <!--[if IE 6]><link rel="stylesheet" href="style.ie6.css" type="text/css" media="screen" /><![endif]-->
    <!--[if IE 7]><link rel="stylesheet" href="style.ie7.css" type="text/css" media="screen" /><![endif]-->

    <script type="text/javascript" src="../script.js"></script>
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
                        <h1 id="name-text" class="art-logo-name"><a href="#"> &nbsp;&nbsp;&nbsp;Xplain2me Tutoring <br> <h5>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"Simply your learning"</h5></a></h1>
                        <div id="slogan-text" class="art-logo-text"></div>
                    </div>
                </div>
                <div class="art-nav">
                	<div class="l"></div>
                	<div class="r"></div>
                	<ul class="art-menu">
                        <li>
                            <a href="edit.php" ><span class="l"></span><span class="r"></span><span class="t">Profile</span></a>
                        </li>
                                
                        <li>
                            <a href="message.php"><span class="active"></span><span class="r"></span><span class="t">Messages</span></a>
                        </li>
                         <li>
                            <a href="submission.php"><span class="l"></span><span class="r"></span><span class="t">Submissions</span></a>
                        </li>
                    <li>
                      <a href="progress.php"><span class="l"></span><span class="r"></span><span class="t">Progress Report</span></a>
                    </li>                  
                    </ul>
                </div>
                <div class="art-content-layout">
                    <div class="art-content-layout-row">
                    &nbsp;<div class="art-layout-cell art-content">
  <?php
  
  include '../connect.php';
  
  
  if(isset($_SESSION['signed_in']) && $_SESSION['signed_in'] == true)
{
 $pool=$_SESSION['user_id'];
 $cool=$_SESSION['user_name'];
 $user_id=$_SESSION['signed_in'];
$Email= $_SESSION['user_Email'];
}
else{
	echo"$user_id";
	}
	

$query = "SELECT * FROM  messages WHERE Msg_To ='".$Email."' ORDER BY MessageID DESC LIMIT 0, 10"; 

     

$result = mysql_query($query) or die(mysql_error());





while($row = mysql_fetch_array($result)){


//echo $Email;
echo '<div><strong>from:</strong> '.$row["Msg_From"].'</div>';

  

echo '<div><strong>Subject:</strong> '.$row["MessageSub"].'</div>';
echo '<div><strong></strong> '.$row["MessageDesc"].'</div>';
echo '<div><strong>DATE:</strong> '.$row["MessageDate"].'</div>';


echo "<hr width=100%>";


}

?>
                        <div class="art-post"></div>
                      </div>
                        <div class="art-layout-cell art-sidebar1" style="height: 555px">
                        <?php
						
						 include '../identify.php'; ?>
                        <hr />
                        <strong><span class="auto-style1">Announcements</span></strong>
    <hr />
    <marquee behavior="scroll" direction="up" scrollamount="3" height="250px">
    <p>
      <?php

  

$query = "SELECT * FROM  system_announce_s ORDER BY Sys_AnnounceID DESC LIMIT 0, 1"; 

     

$result = mysql_query($query) or die(mysql_error());





while($row = mysql_fetch_array($result)){
	


//echo '<div><strong>Topic:</strong> '.$row["Sys_AnnounceSubj"].'</div>';
echo '<div>'.$row["Sys_Announce"].'</div>';

//echo '<div><strong>Posted by:</strong> '.$row["name"].'</div>';
//echo $row['Sys_AnnounceDate']. " <br><br> ";






}

?>
    </p>
    
        <p></p></marquee>
        <hr />
         <div class="art-vmenublockcontent-body">
         <ul class="art-vmenu">

    <!-- <li><a href="../admin/surveyStudent.php"><span class="l"></span><span class="r"></span><span class="t">Survey</span></a>   </li> -->
       <li><a href="edit.php"><span class="l"></span><span class="r"></span><span class="t">Edit Profile</span></a> </a></li>
       <li> <a href="message.php"><span class="l"></span><span class="r"></span><span class="t">New Message</span></a> </li>
 <li> <a href="messageIn.php"><span class="l"></span><span class="r"></span><span class="t">Inbox</span></a> </li>
          <li> <a href="Allmessage.php"><span class="l"></span><span class="r"></span><span class="t">All message</span></a> </li>
  
     
    </ul>
                          <p>&nbsp;</p>
                          <p>&nbsp;</p>
                           </div> 
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
                          <p><span class="footer">
                          <a href="../home/About us.php"><strong>About us</strong></a>
                          <strong> | <a href="../home/contact.php">Contact us</a> |
                           <a href="../home/siteMap.php">Sitemap  </a> | 
                          </strong></span><strong><a href="../Logout.php">Sign Out</a></strong><br />
                                Copyright &copy; 2012 ---. All Rights Reserved.</p>
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