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

// Decode the data so plus signs are converted back to space chars
$rawData = urldecode($rawData);

//Delimiter array by "=" sign
$eventname = explode("=", $rawData)[1];

$dRow = 'SELECT * FROM events '.
       'WHERE eventname = "'.$eventname.'"';
$query = 'DELETE FROM events '.
       'WHERE eventname = "'.$eventname.'"';
	   
mysql_select_db('TaskManager');

// First fetch the event to be deleted then get it's startYear, startMonth, startDay attributes
$resultD = mysql_query($dRow, $conn);

$sy="";
$sm="";
$sd="";
while($d=mysql_fetch_assoc($resultD)){
	$sy=$sy.$d['StartYear'];
	$sm=$sm.$d['StartMonth'];
	$sd=$sd.$d['StartDay'];
}

// Then Delete this event
mysql_query($query, $conn);

// Now select all the events that start on the same day with the deleted event
$returnAll = 'SELECT * FROM events '.
       'WHERE startYear = "'.$sy.'" AND startMonth =  "'.$sm.'" AND startDay =  "'.$sd.'" ';
$result = mysql_query($returnAll, $conn);

// Now loop through the returned rows and format them to a JSON payload
$JSONevents = "";
$JSONpayload = "";
while($e=mysql_fetch_assoc($result)) {
		$JSONevents = $JSONevents . "{\"eventID\":".$e['id'] . ",\"eventTitle\":\"".$e['eventname'] . "\",\"startYear\":".$e['StartYear'] . ",\"startMonth\":".$e['StartMonth'] .",\"startDayOfMonth\":".$e['StartDay'] .",\"startTime\":\"".$e['StartTime'] . "\",\"endYear\":".$e['EndYear'] . ",\"endMonth\":".$e['EndMonth'] .",\"endDayOfMonth\":".$e['EndDay'] . ",\"endTime\":\"".$e['FinishTime'] . "\",\"location\":\"".$e['Location'] . "\",\"description\":\"".$e['description'] . "\",\"isAllDay\":".$e['settings'] . "},";
		$JSONpayload ="{\"days\":[{\"year\":".$e['StartYear'] . ",\"month\":".$e['StartMonth']. ",\"day\":".$e['StartDay']. ",\"events\":[";
}
$JSONpayload = $JSONpayload.$JSONevents;
$JSONpayload = substr($JSONpayload,0,-1);
if ($JSONevents != "") {
	$JSONpayload = $JSONpayload . "]}]}";
} else {
	$JSONpayload = $JSONpaylaod."{\"days\":[{\"year\":".$sy.",\"month\":".$sm.",\"day\":".$sd.",\"events\":[]}]}\"";
}
$message = array($JSONpayload);
$registrationId = array("APA91bH70aNVvJXIMD-NyasaokMWVp-t4934pFMOjE8O9XRJqVl3Wzr4XS4swDgZF-IRVXCTF0WpN-PW8acNIDe2bPyJO8xL96taexMm7C9e_6q_iNasrHnkVLfLr4J5MUhcs2ynzm0ZblkqRnjU0wkKiHffloIHQSNl-4IjXm7dny01hthG8Lg");
$apiKey = "AIzaSyA82yCJnslrBvS7RQqr0VwBTtRZ0GPryqQ";

    //Sending Push Notification
function send_push_notification($registration_ids, $message, $apiKey) {
         
 
	// Set POST variables
    $url = 'https://android.googleapis.com/gcm/send';
 
    $fields = array(
        'registration_ids' => $registration_ids,
        'data' => array('payload' => $message),
    );
 
    $headers = array(
        'Authorization: key=' . $apiKey,
        'Content-Type: application/json'
    );
    //print_r($headers);
    // Open connection
    $ch = curl_init();
 
    // Set the url, number of POST vars, POST data
    curl_setopt($ch, CURLOPT_URL, $url);
 
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
 
    // Disabling SSL Certificate support temporarily
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
 
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
 
    // Execute post
    $result = curl_exec($ch);
    if ($result === FALSE) {
        die('Curl failed: ' . curl_error($ch));
    }
 
    // Close connection
    curl_close($ch);
//    echo $result;
	return $result;
}
$response = send_push_notification($registrationId, $JSONpayload, $apiKey);
mysql_close($conn);
?>