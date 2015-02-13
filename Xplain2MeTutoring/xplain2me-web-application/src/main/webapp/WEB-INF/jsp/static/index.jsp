
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="tutoring, private home tuition, tutors">
    <meta name="author" content="Tsepo Maleka">
    
    <title>Xplain2Me Tutoring | You've stepped in | Professional tutoring services | Simplify your learning</title>
    
    <%@include file="../../jspf/template/default-header.jspf" %>
    
</head>

<body data-spy="scroll" data-offset="0" data-target="#navbar-main">
  
  
<div id="navbar-main">
    <!-- Fixed navbar -->
    <div class="navbar navbar-inverse navbar-fixed-top">
        
        <div class="container">
            
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" 
                        data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" 
                   href="/welcome" title="Xplain2Me Tutoring" hreflang="en">
                    <span style="display: block; font-size: 90%">
                        <strong>XPLAIN2ME TUTORING</strong>
                    </span>
                </a>
            </div>
          
            <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li> 
                            <a href="#home" class="smoothScroll">
                                <span class="icon icon-home"></span>
                                <span>&nbsp;</span>
                                <span>Home</span>
                            </a>
                        </li>
                        
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" 
                               role="button" aria-expanded="false">
                                <span class="icon icon-people"></span>
                                <span>&nbsp;</span>
                                <span>About</span>
                                <span class="caret"></span>
                            </a>
                            
                            <ul class="dropdown-menu" role="menu">
                                
                                <li><a href="/about" title="About" hreflang="en">About Us</a></li>
                                <li><a href="#services" class="smoothScroll">Services + Rates</a></li>
                                <li><a href="#why_choose_us" class="smoothScroll">Why Choose Us</a></li>
                                <li><a href="#how_we_do_it" class="smoothScroll">What We Do</a></li>
                                <li><a href="#subjects" class="smoothScroll">Subjects Offered</a></li>
                                
                            </ul>
                        </li>
                        
                        <li> 
                            <a title="Request a tutor" href="/request-a-tutor" hreflang="en"> 
                                <span class="icon icon-accessibility"></span>
                                <span>&nbsp;</span>
                                <span>Get a tutor</span>
                            </a>
                        </li>
                        
                        <li> 
                            <a title="Become a tutor" href="/become-a-tutor" hreflang="en"> 
                                <span class="icon icon-enter"></span>
                                <span>&nbsp;</span>
                                <span>Become a tutor</span>
                            </a>
                        </li>
                        
                        <li> 
                            <a title="Request Quote" href="/quote-request" hreflang="en"> 
                                <span class="icon icon-cart-checkout"></span>
                                <span>&nbsp;</span>
                                <span>Request Quote</span>
                            </a>
                        </li>
                        
                        <li> 
                            <a  title="Contact Us" href="#contact" class="smoothScroll"> 
                                <span class="icon icon-envelop"></span>
                                <span>&nbsp;</span>
                                <span>Contact</span>
                            </a>
                        </li>
                        
                        <li> 
                            <a href="/account/login" title="Login" hreflang="en">
                                <span class="icon icon-lock"></span>
                                <span>&nbsp;</span>
                                <span>Login</span>
                            </a>
                        </li>
                       
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>
    </div>

    <!-- Home -->
    <div id="home" name="home"></div>
    <div id="headerwrap">
        <header class="clearfix">
            
            <img alt="company logo" src="/assets/landing-page-theme/img/logo.png" 
                     style="height: 90px; margin: 0px auto"/>
            
            <p style="font-size: 95%">You've stepped in, welcome...</p>
            <!--h1>Xplain2Me</h1-->
            <p>Professional tutoring services to <br>
                <strong>simplify your learning</strong></p>
            <br/>
            <br/>
            <br/>
            
            <a href="/request-a-tutor" title="Get a tutor"
                class="btn btn-primary btn-lg btn-blue" style="margin: 5px">
                <span class="glyphicon glyphicon-bullhorn"></span>
                <span>&nbsp;Get a tutor</span>
            </a>
            
            <a href="/become-a-tutor" title="Become a tutor"
                class="btn btn-danger btn-lg btn-red" style="margin: 5px">
                <span class="glyphicon glyphicon-user"></span>
                <span>&nbsp;Become a tutor</span>
            </a>
            
            <a href="/quote-request" title="Get a quote"
                class="btn btn-success btn-lg btn-green" style="margin: 5px">
                <span class="glyphicon glyphicon-credit-card"></span>
                <span>&nbsp;Get a quote</span>
            </a>
            
        </header>	    
    </div>
    
    <div id="footerwrap">
        <div class="container">
            <div class="text-center">
                
                <p>
                    <span class="text-primary">
                        <strong>CONNECT WITH US</strong>
                    </span>
                </p>
                
                <p style="font-size: 120%">
                    
                    <a href="tel:+27728931542" class="btn btn-default"
                       target="_blank" style="margin-left: 5px; margin-right: 5px"
                       title="Give us a call">
                        <span class="icon icon-phone"></span>
                        <span>&nbsp;Call</span>
                    </a>
                    
                    <a href="mailto:info@xplain2me.co.za" class="btn btn-default"
                       target="_blank" style="margin-left: 5px; margin-right: 5px"
                       title="Send us an email">
                        <span class="icon icon-envelop"></span>
                        <span>&nbsp;Email</span>
                    </a>
                    
                    <a title="Follow us on Twitter" class="btn btn-default"
                       style="margin-left: 5px; margin-right: 5px"
                        href="https://twitter.com/xplain2me1" target="_blank">
                        <span class="icon icon-twitter"></span> 
                        <span>&nbsp;Follow</span>
                    </a>
                    
                    <a title="Like our Facebook page" class="btn btn-default"
                       target="_blank" style="margin-left: 5px; margin-right: 5px"
                       href="https://www.facebook.com/pages/Xplain2me-Tutoring-company/1539808466275946?ref=ts&fref=ts"> 
                        <span class="icon icon-facebook"></span> 
                        <span>&nbsp;Like</span>
                    </a>
                    
                </p>
            </div>
        </div>
    </div>

    <!-- About -->
    <div id="services" name="services"></div>
    <div id="greywrap">
        <div class="row">
                <div class="col-lg-4 callout">
                        <span class="icon icon-lab"></span>
                        <h2>Our Tutors</h2>
                        <p>
                            Xplain2Me tutors are selectively sourced out from 
                            different universities throughout the country. 
                            Our tutors are either graduates or university 
                            students on their course to graduating. All of our 
                            tutors go through a screening process by qualification 
                            competence review and interviews.
                        </p>
                </div><!-- col-lg-4 -->

                <div class="col-lg-4 callout">
                        <span class="icon icon-tools"></span>
                        <h2>Our Services</h2>
                        <p>
                            Our tutors travel to you and offer tutorials at the comfort 
                            of your own homes and at your request, we replace our tutors 
                            at no additional cost. The progress of students is monitored 
                            and corrective steps and timely adjustments are made where 
                            necessary to ensure set performance targets are met.
                        </p>
                </div><!-- col-lg-4 -->	

                <div class="col-lg-4 callout">
                        <span class="icon icon-cart"></span>
                        <h2>Our Rates</h2>
                        <p>
                            Our rates are affordable yet we offer the best service with 
                            utilization of qualified and trained tutors.We ensure our 
                            clients get value for their money by following up regularly on 
                            student performance and ensuring progress is being made. 
                        </p>
                        <a href="/quote-request" title="Get a quote"
                            class="btn btn-primary btn-lg" style="margin: 5px">
                            <span>&nbsp;Get a quote</span>
                        </a>
                </div>
        </div>
    </div>
    
    <section class="section-divider textdivider divider6">
        <div class="container" style="background: black; opacity: 0.6">

            <h2 style="color: white">
                <span>
                    <img alt="open quotes" src="/assets/landing-page-theme/img/open_quotes.png"
                         style="width: 42px" />
                </span>
                <span>
                    The most influential of all educational factors<br>
                    is the conversation in a child's home.
                </span>
                <span>
                    <img alt="open quotes" src="/assets/landing-page-theme/img/close_quotes.png"
                         style="width: 42px" />
                </span>
            </h2>
            <hr>

            <h3 style="color: white; text-transform: uppercase">
                William Temple (1881 - 1944)
            </h3>

        </div>
    </section>
    
    <!-- Why Choose Us -->
    <div id="why_choose_us" name="why_choose_us"></div>
    <div class="container">
        <div class="row white">
            <br>
            <h1 class="centered">WHY CHOOSE US</h1>
            <hr>
            
            <h3 class="centered"> University students/graduates offering one to one tutoring services at the comfort of your 
            homes.</h3>
                    
            <div class="col-lg-6">
                
                
                <p>Get one on one tutorial for your child and avail them to:</p>
                <ul>
                    <li>Improved pass rate through selectively pinpointing student problem areas and resolving them.</li>
                    <li>Individual attention given by tutors to students increases the scope of concentration, thereby enabling profound understanding.</li>
                    <li>Easy revision assisted by study and revision material handed out by our tutors. </li>
                    <li>Different rates apply for different student levels and the more the lessons you purchase the 
                                less our rates are for you.</li>
                </ul>

            </div>
            
            <div class="col-lg-6">
                <p>All of our tutors go through a screening process and are selectively chosen to meet your peculiar 
                    needs and will work to make your child:</p>
                <ul>
                    <li>Improve understanding by unpacking the finer details in modules.</li>
                    <li>Enhance easy learning through provision of appropriate study methods.</li>
                    <li>Break down complex concepts to an understandable level</li>
                    <li>Pinpoint the important aspects and factors in modules and chapters for tests and exam 
                        preparation.</li>
                </ul>
            </div>
        </div>
        
                   
    </div>
		
    <section class="section-divider textdivider divider1">
        <div class="container" style="background: black; opacity: 0.6">

            <h2 style="color: white">
                <span>
                    <img alt="open quotes" src="/assets/landing-page-theme/img/open_quotes.png"
                         style="width: 42px" />
                </span>
                <span>
                    Education is the most powerful weapon which you <br>
                    can use to change the world.
                </span>
                <span>
                    <img alt="open quotes" src="/assets/landing-page-theme/img/close_quotes.png"
                         style="width: 42px" />
                </span>
            </h2>
            <hr>

            <h3 style="color: white">NELSON MANDELA (1918 -2013)</h3>

        </div>
    </section>	
   
    <!-- What We Do -->
    <div id="how_we_do_it" name="how_we_do_it"></div>
    <div class="container">
        <br>
        <br>
        <div class="row">
                <h2 class="centered">WHAT WE DO</h2>
                <hr>
                <br>
                <div class="col-lg-offset-2 col-lg-8">
                    
                    <ul>
                        <li>
                            <h4>Providing appropriate study methods to enhance easy learning.</h4>
                        </li>
                        <li>
                            <h4>Breaking down complex concepts to an understandable level.</h4>
                            </li>
                        <li>
                            <h4>Pinpointing the important things in the modules and chapters for tests and exam preparations.</h4>
                        </li>
                        <li>
                            <h4>Unpacking and explaining difficult components of the modules to improve understanding.</h4>
                        </li>
                    </ul>
                    
                </div>
        </div>
    </div>
  		
    <section class="section-divider textdivider divider2">
        <div class="container" style="background: black; opacity: 0.6">

            <h2 style="color: white">
                <span>
                    <img alt="open quotes" src="/assets/landing-page-theme/img/open_quotes.png"
                         style="width: 42px" />
                </span>
                <span>
                    Develop a passion for learning. If you <br/>
                    do, you will never cease to grow.
                </span>
                <span>
                    <img alt="open quotes" src="/assets/landing-page-theme/img/close_quotes.png"
                         style="width: 42px" />
                </span>
            </h2>
            <hr>

            <h3 style="color: white">ANTHONY J. D'ANGELO (1924 -2004)</h3>

        </div>
    </section>

    <div id="greywrap">
        <div class="container">
            <div class="row">
                
                <div class="col-lg-6">
                    <h2>We Are Hiring!</h2>
                    <p>
                        Do you want to join our team of tutors? Sure you want, because we are an awesome team!
                        Our team works hard every day to spread the love of education and 
                        making a difference in someone's academic future.</p>
                    <p>
                        <a title="Become a tutor" href="/become-a-tutor"
                           class="btn btn-success btn-lg">
                            Become a tutor
                        </a>
                    </p>
                </div>		
                <div class="col-lg-6 divider3" style="height: 300px">
                    
                </div>
            </div>
        </div>
    </div>
		
    <section class="section-divider textdivider divider4">
        <div class="container" style="background: black; opacity: 0.6">

            <h2 style="color: white">
                <span>
                    <img alt="open quotes" src="/assets/landing-page-theme/img/open_quotes.png"
                         style="width: 42px" />
                </span>
                <span>
                    Education is simply the soul of a society as it <br>
                    passes from one generation to another.
                </span>
                <span>
                    <img alt="open quotes" src="/assets/landing-page-theme/img/close_quotes.png"
                         style="width: 42px" />
                </span>
            </h2>
            <hr>

            <h3 style="color: white">GILBERT K. CHESTERTON (1874 -1936)</h3>

        </div>
    </section>

    <!-- Subjects -->
    <div id="subjects" name="subjects"></div>
    <div id="greywrap">
        <div class="container">
            <div class="row">
                <h1 class="centered">SUBJECTS WE OFFER</h1>
                <hr>
                <h4>We offer a wide range of subjects from Grade 1 to 12 
                    and beyond to college and university students:</h4>
                <br>
                <br>
                
                <div class="col-lg-8 col-lg-offset-2" style="font-size: 160%">
                    <label class="label label-warning">Mathematics</label>
                    <label class="label label-primary">English</label>
                    <label class="label label-success">Natural Sciences</label>
                    <label class="label label-warning">Social Sciences</label>
                    <label class="label label-primary">Technology</label>
                    <label class="label label-success">Afrikaans</label>
                    <label class="label label-warning">Physical Science</label>
                    <label class="label label-danger">Life Sciences</label>
                    <label class="label label-primary">Accounting</label>
                    <label class="label label-success">Economics</label>
                    <label class="label label-warning">Business Studies</label>
                    <label class="label label-danger">Information Technology</label>
                    <label class="label label-primary">Computer Applications Technology</label>
                    <label class="label label-success">Agricultural Sciences</label>
                    <label class="label label-warning">Civil Technology</label>
                    <label class="label label-danger">Electrical Technology</label>
                    <label class="label label-primary">Mechanical Technology</label>
                    <label class="label label-success">Agricultural Studies</label>
                    <label class="label label-warning">Technical Drawing</label>
                    <label class="label label-danger">Mathematical Literacy</label>
                    <label class="label label-primary">Geography</label>
                    <label class="label label-success">Arts and Culture</label>
                    <label class="label label-primary">Office Administration</label>
                    <label class="label label-success">Bible Studies</label>
                    
                    <label class="label label-warning">Physics</label>
                    <label class="label label-danger">Chemistry</label>
                    <label class="label label-primary">Calculus</label>
                    <label class="label label-success">Abstract Algebra</label>
                    
                    <label class="label label-warning">Statistics</label>
                    <label class="label label-danger">Applied Mathematics</label>
                    <label class="label label-primary">Finance</label>
                    <label class="label label-success">Business Management</label>
                    
                    <label class="label label-warning">Electronics</label>
                    <label class="label label-danger">Taxation</label>
                    <label class="label label-primary">Mercantile Law</label>
                    <label class="label label-success">Software Engineering & Development</label>

                    <br>
                    <br>
                    
                    <h4>... and more.</h4>
                    <br>
                    <br>
                </div>		
               
            </div>
        </div>
    </div>
    
    <section class="section-divider textdivider divider5">
        <div class="container" style="background: black; opacity: 0.6">

            <h2 style="color: white">
                <span>
                    <img alt="open quotes" src="/assets/landing-page-theme/img/open_quotes.png"
                         style="width: 42px" />
                </span>
                <span>
                    Don't limit a child to your own learning, <br>
                    for he was born in another time.
                </span>
                <span>
                    <img alt="open quotes" src="/assets/landing-page-theme/img/close_quotes.png"
                         style="width: 42px" />
                </span>
            </h2>
            <hr>

            <h3 style="color: white; text-transform: uppercase">
                Rabindranath Tagore (1861 - 1941)
            </h3>

        </div>
    </section>
    
    <!-- Contact -->
    <div id="contact" name="contact"></div>
    <div class="container">
        
        <div class="row">
            <br>
            <h1 class="centered">THANKS FOR VISITING US</h1>
            <hr>
            <br>
            <br>
            <div class="col-lg-4">
                
                
                <h3>Contact Information</h3>
                
                <img alt="company logo" src="/assets/landing-page-theme/img/logo.png" 
                     style="width: 50%; margin: 0px auto"/>
                
                <p>
                    <span class="icon icon-home"></span>&nbsp;Johannesburg, South Africa<br/>
                    <span class="icon icon-phone"></span>&nbsp;+27(0) 72 893 1542<br/>
                    <span class="icon icon-mobile"></span>&nbsp;+27(0) 72 893 1542 <br/>
                    
                    <span class="icon icon-envelop"></span>
                    <a href="mailto:info@xplain2me.co.za" target="_blank"
                       title="Send us an email">&nbsp;info@xplain2me.co.za</a><br/>
                    
                    <span class="icon icon-twitter"></span> 
                    <a title="Follow us on Twitter"
                        href="https://twitter.com/xplain2me1" target="_blank">
                        &nbsp;@xplain2me1 </a> <br/>
                    
                    <span class="icon icon-facebook">
                    </span> <a title="Like our Facebook page" target="_blank"
                       href="https://www.facebook.com/pages/Xplain2me-Tutoring-company/1539808466275946?ref=ts&fref=ts"> 
                        &nbsp;Xplain2Me Tutoring</a> <br/>
                </p>
            </div>

            <div class="col-lg-4">
                <h3>Portal Login</h3>
                <br/>
                <p>Tutors and registered students can login in here:</p>
                <p>
                    <form role="form" id="loginForm"
                          method="post" action="/account/login">

                        <!-- Username -->
                    <div id="usernameFormGroup" class="form-group">
                        <label>Username</label>
                        <input type="text" class="form-control" id="username"
                               name="username" maxlength="16"
                               placeholder="Enter Username"
                               data-validation="length" data-validation-length="min8"
                               data-validation-error-msg="You did not enter a username."/>
                    </div>

                    <!-- Password -->
                    <div id="passwordFormGroup" class="form-group">
                        <label>Password</label>
                        <input type="password" class="form-control" id="password"
                               name="password" maxlength="32"
                               placeholder="Enter Password"
                               data-validation="required"
                               data-validation-error-msg="You did not enter a password."/>
                    </div>

                    <input type="submit" id="loginButton"
                        name="loginButton" class="btn btn-primary"
                        value="Login"/>

                    </form>
                </p>
            </div>
            
            <div class="col-lg-4">
                <h3>Navigation</h3>
             
                <ul>
                    <li> <a href="#home" class="smoothScroll">Home</a></li>
                    <li> <a href="/about" title="About Us" hreflang="en"> About Us</a></li>
                    <li> <a href="/request-a-tutor" title="Request a tutor" hreflang="en"> Get a Tutor</a></li>
                    <li> <a href="/become-a-tutor" title="Become a tutor" hreflang="en"> Become a Tutor</a></li>
                    <li> <a href="/quote-request" title="Request Quote" hreflang="en"> Request Quote</a></li>
                    <li> <a href="/account/login" hreflang="en" title="Login"> Login</a></li>
                </ul>
                
                <h3>Legal</h3>
             
                <ul>
                    <li> <a href="#" title="Terms of Service" hreflang="en"> Terms of Service</a></li>
                    <li> <a href="#" title="Usage Policy" hreflang="en"> Usage Policy</a></li>
                    <li> <a href="#" title="Privacy Policy" hreflang="en"> Privacy Policy</a></li>
                </ul>
                
            </div>
        </div>

    </div>

    <div id="footerwrap">
        <div class="container">
            <h4>
                <span class="text-primary">
                    <strong>Xplain2Me Tutoring (Pty) Ltd (ZA)</strong><br/>
                    <small class="text-muted">Simplify your learning</small>
                </span>
                <br>
                <br/>
                <span style="font-size: 90%">
                    Copyright&copy; 2010-2015 - All Rights Reserved.
                </span>
            </h4>
        </div>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
		

    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/landing-page-theme/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/landing-page-theme/js/retina.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/landing-page-theme/js/jquery.easing.1.3.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/landing-page-theme/js/smoothscroll.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/assets/landing-page-theme/js/jquery-func.js"></script>
    
    <%@include file="../../jspf/template/form-validation-script.jspf" %>
    
    <script type="text/javascript">

        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

        ga('create', 'UA-58992054-1', 'auto');
        ga('send', 'pageview');

    </script>
    
  </body>
</html>

