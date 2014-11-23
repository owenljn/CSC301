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

$name = explode("=", $rawData);

$eventname = $name[1];

$query = 'DELETE FROM events '.
       'WHERE eventname = "'.$eventname.'" ';
	   
mysql_select_db('TaskManager');
mysql_query($query2, $conn);
mysql_close($conn);
?>