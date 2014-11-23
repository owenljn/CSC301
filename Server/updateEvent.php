<?php
$dbhost = 'csc301project.chyn5keoy4yh.us-east-1.rds.amazonaws.com:3306';
$dbuser = 'CSC301Team6';
$dbpass = 'CSC301Team6';
$conn = mysql_connect($dbhost, $dbuser, $dbpass);
if(! $conn )
{
  die('Could not connect: ' . mysql_error());
}

$rawData = file_get_contents("php://input");

$array = explode("&", $rawData);

$name = explode("=", $array[0]);
$description = explode("=", $array[1]);
$location = explode("=", $array[2]);
$startTime = explode("=", $array[3]);
$finishTime = explode("=", $array[4]);
$setting = explode("=", $array[5]);


$eventname = $name[1];
$des = $description[1];
$loc = $location[1];
$st = $startTime[1];
$ft = $finishTime[1];
$settings = $setting[1];

	   
$query = 'Update events '.
      'SET eventname = "'.$eventname.'", description = "'.$des.'", Location = "'. $loc.'", StartTime = "'. $st.'", FinishTime = "'. $ft.'", settings = "'. $settings.'" '.
       'WHERE eventname = "'.$eventname.'" ';
	   
mysql_select_db('TaskManager');
mysql_query($query, $conn);
mysql_close($conn);
?>