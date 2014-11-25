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


/* $myfile = fopen("newfile.txt", "w") or die("Unable to open file!");
fwrite($myfile, $array);
fclose($myfile); */



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
	   
$query = 'INSERT INTO events '.
      '(eventname, description, Location, StartYear, StartMonth, StartDay, StartTime, EndYear, EndMonth, EndDay, FinishTime, settings) '.
       'VALUES ("'.$eventname.'", "'.$des.'", "'.$loc.'", "'.$sy.'", "'.$sm.'", "'.$sd.'", "'.$st.'", "'.$fy.'", "'.$fm.'", "'.$fd.'", "'.$ft.'", "'.$settings.'")';
$check = 'SELECT * FROM events'.
       'WHERE eventname = "'.$eventname.'" ';

mysql_select_db('TaskManager');
mysql_query($query, $conn);
$result = mysql_query($check, $conn);
if(! $result )
{
  die('Could not enter data: ' . mysql_error());
}
$JSONevents = "";
$JSONpayload = "";
while($e=mysql_fetch_assoc($result)) {
		$JSONevents = $JSONevents . "\"eventID\":".$e['id'] . ",\"eventTitle\":".$e['eventname'] . ",\"startYear\":".$e['StartYear'] . ",\"startMonth\":".$e['StartMonth'] .",\"startDayOfMonth\":".$e['StartDay'] .",\"startTime\":".$e['StartTime'] . ",\"endYear\":".$e['EndYear'] . ",\"endMonth\":".$e['EndMonth'] .",\"endDayOfMonth\":".$e['EndDay'] . ",\"endTime\":".$e['FinishTime'] . ",\"location\":".$e['Location'] . ",\"description\":".$e['description'] . ",\"isAllDay\":".$e['settings'] . "}]}";
//        $output[]=$e;
		$JSONpayload ="\"data\":{\"payload\":\"{\"days\":[{\"year\":".$e['StartYear'] . "\"month\":".$e['StartMonth']. "\"day\":".$e['StartDay']. "\"events\":[{";
}
$JSONpayload = $JSONpayload.$JSONevents."]}\"";
$registrationId = 'APA91bH70aNVvJXIMD-NyasaokMWVp-t4934pFMOjE8O9XRJqVl3Wzr4XS4swDgZF-IRVXCTF0WpN-PW8acNIDe2bPyJO8xL96taexMm7C9e_6q_iNasrHnkVLfLr4J5MUhcs2ynzm0ZblkqRnjU0wkKiHffloIHQSNl-4IjXm7dny01hthG8Lg';

function EncodeJSON($JSONpayload,$registrationId) {
	$result = "{\"registration_ids\" :[".$registrationId."],".$JSONpayload;
	return $result;
}

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
                //EncodeJSON($JSONpayload,$registrationId));
				array('message' => $message, 'tickerText' => $tickerText, 'contentTitle' => $contentTitle, "contentText" => $contentText));
 $file = 'newfile.txt';
 file_put_contents($file, $response);
echo $response;

mysql_close($conn);
?>