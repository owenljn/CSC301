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
$result = 'SELECT * FROM(SELECT * FROM events '.
       'WHERE eventname = "'.$eventname.'")
	   WHERE ';
$result = 'SELECT * FROM events '.
       'WHERE startYear = "'.$sy.'" AND startMonth =  "'.$sm.'" AND startDay =  "'.$sd.'" ';
$query = 'DELETE FROM events '.
       'WHERE eventname = "'.$eventname.'" ';
	   
mysql_select_db('TaskManager');
mysql_query($query2, $conn);
mysql_close($conn);

function sendNotification( $apiKey, $registrationIdsArray, $messageData )
{   
    $headers = array("Content-Type:" . "application/json", "Authorization:" . "key=" . $apiKey);
    $data = array(
        'data' => $messageData,
        'registration_ids' => $registrationIdsArray
    );
 
    $ch = curl_init();
 
    curl_setopt( $ch, CURLOPT_HTTPHEADER, $headers ); 
    curl_setopt( $ch, CURLOPT_URL, "https://android.googleapis.com/gcm/send" );
    curl_setopt( $ch, CURLOPT_SSL_VERIFYHOST, 0 );
    curl_setopt( $ch, CURLOPT_SSL_VERIFYPEER, 0 );
    curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
    curl_setopt( $ch, CURLOPT_POSTFIELDS, json_encode($data) );
 
    $response = curl_exec($ch);
    curl_close($ch);
 
    return $response;
}
// Message to send
$message      = "the test message";
$tickerText   = "ticker text message";
$contentTitle = "content title";
$contentText  = "content body";
 
$registrationId = 'APA91bH70aNVvJXIMD-NyasaokMWVp-t4934pFMOjE8O9XRJqVl3Wzr4XS4swDgZF-IRVXCTF0WpN-PW8acNIDe2bPyJO8xL96taexMm7C9e_6q_iNasrHnkVLfLr4J5MUhcs2ynzm0ZblkqRnjU0wkKiHffloIHQSNl-4IjXm7dny01hthG8Lg';
$apiKey = "AIzaSyA82yCJnslrBvS7RQqr0VwBTtRZ0GPryqQ";
 
$response = sendNotification( 
                $apiKey, 
                array($registrationId), 
                array('message' => $message, 'tickerText' => $tickerText, 'contentTitle' => $contentTitle, "contentText" => $contentText) );
 
echo $response;
?>