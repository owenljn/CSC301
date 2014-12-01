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

//Delimiter array by "&" sign
$array = explode("&", $rawData);

// Get the name:value pairs
$name = explode("=", $array[0]);
$description = explode("=", $array[1]);
$location = explode("=", $array[2]);
$startYear = explode("=", $array[3]);
$startMonth = explode("=", $array[4]);
$startDay = explode("=", $array[5]);
$startTime = explode("=", $array[6]);
$EndYear = explode("=", $array[7]);
$EndMonth = explode("=", $array[8]);
$EndDay = explode("=", $array[9]);
$finishTime = explode("=", $array[10]);
$setting = explode("=", $array[11]);

// Grab the value
$eventname = $name[1];
$des = $description[1];
$loc = $location[1];
$sy = $startYear[1];
$sm = $startMonth[1];
$sd = $startDay[1];
$st = $startTime[1];
$fy = $EndYear[1];
$fm = $EndMonth[1];
$fd = $EndDay[1];
$ft = $finishTime[1];
$settings = $setting[1];

// Execute query
$query = 'INSERT INTO events '.
      '(eventname, description, Location, StartYear, StartMonth, StartDay, StartTime, EndYear, EndMonth, EndDay, FinishTime, settings) '.
       'VALUES ("'.$eventname.'", "'.$des.'", "'.$loc.'", "'.$sy.'", "'.$sm.'", "'.$sd.'", "'.$st.'", "'.$fy.'", "'.$fm.'", "'.$fd.'", "'.$ft.'", "'.$settings.'")';
// Execute this query to check if successfully inserted into database
$check = 'SELECT * FROM events '.
	   'WHERE startYear = "'.$sy.'" AND startMonth =  "'.$sm.'" AND startDay =  "'.$sd.'"';
mysql_select_db('TaskManager');
mysql_query($query, $conn);
$result = mysql_query($check, $conn);
if(!$result)
{
  die('Could not enter data: ' . mysql_error());
}
$f = fopen("createEvent.txt", "w");
// Reformat the string into a JSON object
$JSONevents = "";
$JSONpaylaod = "";
while($e=mysql_fetch_assoc($result)) {
		$JSONevents = $JSONevents . "{\"eventID\":".$e['id'] . ",\"eventTitle\":\"".$e['eventname'] . "\",\"startYear\":".$e['StartYear'] . ",\"startMonth\":".$e['StartMonth'] .",\"startDayOfMonth\":".$e['StartDay'] .",\"startTime\":\"".$e['StartTime'] . "\",\"endYear\":".$e['EndYear'] . ",\"endMonth\":".$e['EndMonth'] .",\"endDayOfMonth\":".$e['EndDay'] . ",\"endTime\":\"".$e['FinishTime'] . "\",\"location\":\"".$e['Location'] . "\",\"description\":\"".$e['description'] . "\",\"isAllDay\":".$e['settings'] . "},";
		$JSONpayload ="{\"days\":[{\"year\":".$e['StartYear'] . ",\"month\":".$e['StartMonth']. ",\"day\":".$e['StartDay']. ",\"events\":[";
}
$JSONpayload = $JSONpayload.$JSONevents;
$JSONpayload = substr($JSONpayload,0,-1);
$JSONpayload = $JSONpayload . "]}]}";
fwrite($f, $JSONpayload);
fclose($f);
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