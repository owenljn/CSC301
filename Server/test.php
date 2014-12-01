<?php
$dbhost = 'csc301project.chyn5keoy4yh.us-east-1.rds.amazonaws.com:3306';
$dbuser = 'CSC301Team6';
$dbpass = 'CSC301Team6';
$conn = mysql_connect($dbhost, $dbuser, $dbpass);
if(! $conn )
{
  die('Could not connect: ' . mysql_error());
}
/* $query = 'INSERT INTO person '.
      '(first, last, login, password, email) '.
       'VALUES ( "Tim","Chu","tckool","123", "tc@gmail.com" )';
	   
	   $sql = 'SELECT * FROM person';
$eventname = "Test+url+decode+and+startingTime";
$eventname = urldecode($eventname); */
$st = urldecode($st);
$event1 = 'INSERT INTO events '.
      '(eventname, description, Location, StartYear, StartMonth, StartDay, StartTime, EndYear, EndMonth, EndDay, FinishTime, settings) '.
       'VALUES ("Test event1", "Test", "Bahen centre", "2014", "Nov", "21", "23:00", "2014", "Nov", "22", "12", "True")';
$event2 = 'INSERT INTO events '.
      '(eventname, description, Location, StartYear, StartMonth, StartDay, StartTime, EndYear, EndMonth, EndDay, FinishTime, settings) '.
       'VALUES ("Test event2", "Test", "Bahen centre", "2014", "Nov", "21", "22:00", "2014", "Nov", "22", "12", "True")';
$event3 = 'INSERT INTO events '.
      '(eventname, description, Location, StartYear, StartMonth, StartDay, StartTime, EndYear, EndMonth, EndDay, FinishTime, settings) '.
       'VALUES ("Test event3", "Test", "Bahen centre", "2014", "Nov", "21", "21:00", "2014", "Nov", "22", "12", "True")';
$event4 = 'INSERT INTO events '.
      '(eventname, description, Location, StartYear, StartMonth, StartDay, StartTime, EndYear, EndMonth, EndDay, FinishTime, settings) '.
       'VALUES ("Test event4", "Test", "Bahen centre", "2014", "Nov", "22", "00:00", "2014", "Nov", "22", "12", "True")';

	   
mysql_select_db('TaskManager');
// First fetch the event to be deleted then get it's startYear, startMonth, startDay attributes
$query = 'SELECT * FROM events '.
	   'WHERE eventname = "Test event1"';
$resultD = mysql_query($query, $conn);
mysql_query($event1, $conn);
mysql_query($event2, $conn);
mysql_query($event3, $conn);
mysql_query($event4, $conn);
$sy="";
$sm="";
$sd="";
while($e=mysql_fetch_assoc($resultD)){
	$sy=$sy.$e['StartYear'];
	$sm=$sm.$e['StartMonth'];
	$sd=$sd.$e['StartDay'];
}

// This query tests update
$update = 'Update events '.
      'SET StartDay = "'.$sd.'"'.
       'WHERE eventname = "Test event4" ';
/* $query2 = 'DELETE FROM events '.
       'WHERE eventname = "Test event4" '; */ //This query tests delete functionality
mysql_query( $update, $conn );
//mysql_query( $query2, $conn ); Test the delete functionality
$check = 'SELECT * FROM events '.
	   'WHERE startYear = "'.$sy.'" AND startMonth =  "'.$sm.'" AND startDay =  "'.$sd.'"';
/* $check = 'SELECT * FROM events '.
	   'WHERE startYear = "2014" AND startMonth =  "Nov" AND startDay =  "21"'; */
//mysql_query($query2, $conn);
$result = mysql_query( $check, $conn );
if(!$result )
{
  die('Could not enter data: ' . mysql_error());
}

$f = fopen("response.txt", "w");
$JSONevents = "";
$JSONpayload ="{\"days\":[";
/* while($e=mysql_fetch_assoc($retval)) {
		$JSONevents = $JSONevents . "\"eventID\":".$e['id'] . ",\"eventTitle\":".$e['eventname'] . ",\"startYear\":".$e['StartYear'] . ",\"startMonth\":".$e['StartMonth'] .",\"startDayOfMonth\":".$e['StartDay'] .",\"startTime\":".$e['StartTime'] . ",\"endYear\":".$e['EndYear'] . ",\"endMonth\":".$e['EndMonth'] .",\"endDayOfMonth\":".$e['EndDay'] . ",\"endTime\":".$e['FinishTime'] . ",\"location\":".$e['Location'] . ",\"description\":".$e['description'] . ",\"isAllDay\":".$e['settings'] . "}]}";
//        $output[]=$e;
		$JSONpayload ="\"data\":{\"payload\":\"{\"days\":[{\"year\":".$e['StartYear'] . "\"month\":".$e['StartMonth']. "\"day\":".$e['StartDay']. "\"events\":[{";
} */
while($e=mysql_fetch_assoc($result)) {
		$JSONevents = $JSONevents . "{\"year\":".$e['StartYear'] . ",\"month\":".$e['StartMonth']. ",\"day\":".$e['StartDay']. ",\"events\":[{\"eventID\":".$e['id'] . ",\"eventTitle\":\"".$e['eventname'] . "\",\"startYear\":".$e['StartYear'] . ",\"startMonth\":".$e['StartMonth'] .",\"startDayOfMonth\":".$e['StartDay'] .",\"startTime\":\"".$e['StartTime'] . "\",\"endYear\":".$e['EndYear'] . ",\"endMonth\":".$e['EndMonth'] .",\"endDayOfMonth\":".$e['EndDay'] . ",\"endTime\":\"".$e['FinishTime'] . "\",\"location\":\"".$e['Location'] . "\",\"description\":\"".$e['description'] . "\",\"isAllDay\":".$e['settings'] . "}]},";
}
$JSONpayload = $JSONpayload.$JSONevents;
$JSONpayload = substr($JSONpayload,0,-1) ;
$JSONpayload = $JSONpayload . "]}";
fwrite($f, $JSONpayload);
fclose($f);
/*
'{"registration_ids" :[
"APA91bH70aNVvJXIMD-NyasaokMWVp-t4934pFMOjE8O9XRJqVl3Wzr4XS4swDgZF-IRVXCTF0WpN-PW8acNIDe2bPyJO8xL96taexMm7C9e_6q_iNasrHnkVLfLr4J5MUhcs2ynzm0ZblkqRnjU0wkKiHffloIHQSNl-4IjXm7dny01hthG8Lg"
            ],
            "data":{
                "payload":"{"days":[{"year":2014,"month":10,"day":23,"events":[{"eventId":1,"eventTitle":"CSC373H1F Test","startYear":2014,"startMonth":10,"startDayOfMonth":23,"startTime":"20:00","endYear":2014,"endMonth":10,"endDayOfMonth":23,"endTime":"21:00","location":"Bahen","description":"Bleh","isAllDay":false}]},{"year":2014,"month":10,"day":24,"events":[{"eventId":1,"eventTitle":"Doctor Appointment","startYear":2014,"startMonth":10,"startDayOfMonth":24,"startTime":"15:00","endYear":2014,"endMonth":10,"endDayOfMonth":24,"endTime":"16:00","location":null,"description":"fsdf","isAllDay":false}]}]}"
            }
        }'*/
$registrationId = 'APA91bH70aNVvJXIMD-NyasaokMWVp-t4934pFMOjE8O9XRJqVl3Wzr4XS4swDgZF-IRVXCTF0WpN-PW8acNIDe2bPyJO8xL96taexMm7C9e_6q_iNasrHnkVLfLr4J5MUhcs2ynzm0ZblkqRnjU0wkKiHffloIHQSNl-4IjXm7dny01hthG8Lg';

/* function EncodeJSON($JSONpayload,$registrationId) {
	$result = "{\"registration_ids\" :[".$registrationId."],".$JSONpayload;
	return $result;
}
print(EncodeJSON($JSONpayload, $registrationId)); */
//[{"id":"2","eventname":"Event title","description":"Test","Location":"Bahen centre","StartYear":"2014","StartMonth":"Nov","StartDay":"21","StartTime":"12","EndYear":"2014","EndMonth":"Nov","EndDay":"22","FinishTime":"12","settings":"True"}]
//{"days":[{"year":2014,"month":10,"day":23,"events":[{"eventId":1,"eventTitle":"CSC373H1F Test","startYear":2014,"startMonth":10,"startDayOfMonth":23,"startTime":"20:00","endYear":2014,"endMonth":10,"endDayOfMonth":23,"endTime":"21:00","location":"Bahen","description":"Bleh","isAllDay":false}]},{"year":2014,"month":10,"day":24,"events":[{"eventId":1,"eventTitle":"Doctor Appointment","startYear":2014,"startMonth":10,"startDayOfMonth":24,"startTime":"15:00","endYear":2014,"endMonth":10,"endDayOfMonth":24,"endTime":"16:00","location":null,"description":"fsdf","isAllDay":false}]}]}
echo "\n\n\n\n\n";
//$/* response = "This is a test string";
// Open the text file
//	$f = fopen("response.txt", "w");

	// Write text
//	fwrite($f, $response); 

	// Close the text file
//	fclose($f); */
mysql_close($conn);
?>