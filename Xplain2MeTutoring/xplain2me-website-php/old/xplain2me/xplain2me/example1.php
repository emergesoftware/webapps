<?php

// $ch = curl_init("http://www.example.com/");
// $fp = fopen("example_homepage.txt", "w");

// curl_setopt($ch, CURLOPT_FILE, $fp);
// curl_setopt($ch, CURLOPT_HEADER, 0);

// curl_exec($ch);
// curl_close($ch);
// echo "Successful";
// fclose($fp);


// create a new cURL resource
$ch = curl_init();

// set URL and other appropriate options
curl_setopt($ch, CURLOPT_URL, "http://www.example.com/");
curl_setopt($ch, CURLOPT_HEADER, 1);

// grab URL and pass it to the browser
$p = curl_exec($ch);
echo $ch;

// close cURL resource, and free up system resources
curl_close($ch);

?>