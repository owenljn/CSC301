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
//$rawData = str_replace('+', ' ', $rawData);
$rawData = urldecode($rawData);

//Delimiter array by "&" sign
$array = explode("&", $rawData);

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
$st = str_replace('%3A', ':', $st);
$ft = str_replace('%3A', ':', $ft);	 

	   
$query = 'Update events '.
      'SET eventname = "'.$eventname.'", description = "'.$des.'", Location = "'. $loc.'", StartYear = "'. $sy.'", StartMonth = "'. $sm.'", StartDay = "'. $sd.'", StartTime = "'. $st.'", EndYear = "'. $fy.'", EndMonth = "'. $fm.'", EndDay = "'. $fd.'", FinishTime = "'. $ft.'", settings = "'. $settings.'" '.
       'WHERE eventname = "'.$eventname.'" ';
	   
mysql_select_db('TaskManager');
mysql_query($query, $conn);
$result = mysql_query($check, $conn);
if(! $result )
{
  die('Could not enter data: ' . mysql_error());
}

// Reformat the string into a JSON object
$JSONevents = "";
$JSONpayload = "";
/* while($e=mysql_fetch_assoc($result)) {
		$JSONevents = $JSONevents . "\"eventID\":".$e['id'] . ",\"eventTitle\":".$e['eventname'] . ",\"startYear\":".$e['StartYear'] . ",\"startMonth\":".$e['StartMonth'] .",\"startDayOfMonth\":".$e['StartDay'] .",\"startTime\":".$e['StartTime'] . ",\"endYear\":".$e['EndYear'] . ",\"endMonth\":".$e['EndMonth'] .",\"endDayOfMonth\":".$e['EndDay'] . ",\"endTime\":".$e['FinishTime'] . ",\"location\":".$e['Location'] . ",\"description\":".$e['description'] . ",\"isAllDay\":".$e['settings'] . "}]}";
		$JSONpayload ="\"data\":{\"payload\":\"{\"days\":[{\"year\":".$e['StartYear'] . "\"month\":".$e['StartMonth']. "\"day\":".$e['StartDay']. "\"events\":[{";
} */
while($e=mysql_fetch_assoc($result)) {
		$JSONevents = $JSONevents . "\"eventID\":".$e['id'] . ",\"eventTitle\":".$e['eventname'] . ",\"startYear\":".$e['StartYear'] . ",\"startMonth\":".$e['StartMonth'] .",\"startDayOfMonth\":".$e['StartDay'] .",\"startTime\":".$e['StartTime'] . ",\"endYear\":".$e['EndYear'] . ",\"endMonth\":".$e['EndMonth'] .",\"endDayOfMonth\":".$e['EndDay'] . ",\"endTime\":".$e['FinishTime'] . ",\"location\":".$e['Location'] . ",\"description\":".$e['description'] . ",\"isAllDay\":".$e['settings'] . "}]}";
		$JSONpayload ="\"payload\":\"{\"days\":[{\"year\":".$e['StartYear'] . "\"month\":".$e['StartMonth']. "\"day\":".$e['StartDay']. "\"events\":[{";
}
$JSONpayload = $JSONpayload.$JSONevents."]}\"";
//$registrationId = 'APA91bH70aNVvJXIMD-NyasaokMWVp-t4934pFMOjE8O9XRJqVl3Wzr4XS4swDgZF-IRVXCTF0WpN-PW8acNIDe2bPyJO8xL96taexMm7C9e_6q_iNasrHnkVLfLr4J5MUhcs2ynzm0ZblkqRnjU0wkKiHffloIHQSNl-4IjXm7dny01hthG8Lg';

/* function EncodeJSON($JSONpayload,$registrationId) {
	$result = "{\"registration_ids\" :[".$registrationId."],".$JSONpayload;
	return $result;
} */
$registrationId = 'APA91bH70aNVvJXIMD-NyasaokMWVp-t4934pFMOjE8O9XRJqVl3Wzr4XS4swDgZF-IRVXCTF0WpN-PW8acNIDe2bPyJO8xL96taexMm7C9e_6q_iNasrHnkVLfLr4J5MUhcs2ynzm0ZblkqRnjU0wkKiHffloIHQSNl-4IjXm7dny01hthG8Lg';
$apiKey = "AIzaSyA82yCJnslrBvS7RQqr0VwBTtRZ0GPryqQ";

/* // Now the GCM part, this is version 1
$url = 'https://android.googleapis.com/gcm/send';

// $fields = array(
//                'registration_ids'  => array($_POST['registrationIDs']),
//                'data'              => array( "message" => $message ),
//                ); 
$fileds = EncodeJSON($JSONpayload,$registrationId); 
$headers = array( 
                    'Authorization: key=' . $apiKey],
                    'Content-Type: application/json'
                );

// Open connection
$ch = curl_init();

// Set the url, number of POST vars, POST data
curl_setopt( $ch, CURLOPT_URL, $url );

curl_setopt( $ch, CURLOPT_POST, true );
curl_setopt( $ch, CURLOPT_HTTPHEADER, $headers);
curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );

//curl_setopt( $ch, CURLOPT_POSTFIELDS, json_encode( $fields ) ); 
curl_setopt( $ch, CURLOPT_POSTFIELDS, $fields );

// Execute post
$response = curl_exec($ch);

// Close connection
curl_close($ch);
 */
// This is the version 2
    //Sending Push Notification
function send_push_notification($registatoin_ids, $message, $apiKey) {
         
 
	// Set POST variables
    $url = 'https://android.googleapis.com/gcm/send';
 
    $fields = array(
        'registration_ids' => $registatoin_ids,
        'data' => $message,
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
 
    // Disabling SSL Certificate support temporarly
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
 
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
 
    // Execute post
    $result = curl_exec($ch);
    if ($result === FALSE) {
        die('Curl failed: ' . curl_error($ch));
    }
 
    // Close connection
    curl_close($ch);
    echo $result;
}

send_push_notification($registrationId, $JSONpayload, $apiKey);
mysql_close($conn);
/* function sendNotification( $apiKey, $registrationIdsArray, $messageData )
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
 */ 
/*  
$response = sendNotification( 
                $apiKey, 
                array($registrationId), 
                array('message' => $message, 'tickerText' => $tickerText, 'contentTitle' => $contentTitle, "contentText" => $contentText) );
 */ 
?>