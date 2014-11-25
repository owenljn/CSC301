<?php
$dbhost = 'csc301project.chyn5keoy4yh.us-east-1.rds.amazonaws.com:3306';
$dbuser = 'CSC301Team6';
$dbpass = 'CSC301Team6';
$conn = mysql_connect($dbhost, $dbuser, $dbpass);
if(! $conn )
{
  die('Could not connect: ' . mysql_error());
}
$query = 'INSERT INTO person '.
      '(first, last, login, password, email) '.
       'VALUES ( "Tim","Chu","tckool","123", "tc@gmail.com" )';
	   
	   $sql = 'SELECT * FROM person';
$eventname = "Test+url+decode+and+startingTime";
$st = "23%3A00";
$eventname = urldecode($eventname);
$st = urldecode($st);
/* $eventname = str_replace('+', ' ', $eventname);
$st = str_replace('%3A', ':', $st); */
$query2 = 'INSERT INTO events '.
      '(eventname, description, Location, StartYear, StartMonth, StartDay, StartTime, EndYear, EndMonth, EndDay, FinishTime, settings) '.
       'VALUES ("'.$eventname.'", "Test", "Bahen centre", "2014", "Nov", "21", "'.$st.'", "2014", "Nov", "22", "12", "True")';
$check = 'SELECT * FROM events '.
       'WHERE eventname = "'.$eventname.'" ';
mysql_select_db('TaskManager');
mysql_query($query2, $conn);
$retval = mysql_query( $check, $conn );
if(! $retval )
{
  die('Could not enter data: ' . mysql_error());
}
$JSONevents = "";
while($e=mysql_fetch_assoc($retval)) {
		$JSONevents = $JSONevents . "\"eventID\":".$e['id'] . ",\"eventTitle\":".$e['eventname'] . ",\"startYear\":".$e['StartYear'] . ",\"startMonth\":".$e['StartMonth'] .",\"startDayOfMonth\":".$e['StartDay'] .",\"startTime\":".$e['StartTime'] . ",\"endYear\":".$e['EndYear'] . ",\"endMonth\":".$e['EndMonth'] .",\"endDayOfMonth\":".$e['EndDay'] . ",\"endTime\":".$e['FinishTime'] . ",\"location\":".$e['Location'] . ",\"description\":".$e['description'] . ",\"isAllDay\":".$e['settings'] . "}]}";
//        $output[]=$e;
		$JSONpayload ="\"data\":{\"payload\":\"{\"days\":[{\"year\":".$e['StartYear'] . "\"month\":".$e['StartMonth']. "\"day\":".$e['StartDay']. "\"events\":[{";
}
$JSONpayload = $JSONpayload.$JSONevents."]}\"";
//$JSONstr = json_encode($output);
/*
'{"registration_ids" :[
"APA91bH70aNVvJXIMD-NyasaokMWVp-t4934pFMOjE8O9XRJqVl3Wzr4XS4swDgZF-IRVXCTF0WpN-PW8acNIDe2bPyJO8xL96taexMm7C9e_6q_iNasrHnkVLfLr4J5MUhcs2ynzm0ZblkqRnjU0wkKiHffloIHQSNl-4IjXm7dny01hthG8Lg"
            ],
            "data":{
                "payload":"{"days":[{"year":2014,"month":10,"day":23,"events":[{"eventId":1,"eventTitle":"CSC373H1F Test","startYear":2014,"startMonth":10,"startDayOfMonth":23,"startTime":"20:00","endYear":2014,"endMonth":10,"endDayOfMonth":23,"endTime":"21:00","location":"Bahen","description":"Bleh","isAllDay":false}]},{"year":2014,"month":10,"day":24,"events":[{"eventId":1,"eventTitle":"Doctor Appointment","startYear":2014,"startMonth":10,"startDayOfMonth":24,"startTime":"15:00","endYear":2014,"endMonth":10,"endDayOfMonth":24,"endTime":"16:00","location":null,"description":"fsdf","isAllDay":false}]}]}"
            }
        }'*/
$registrationId = 'APA91bH70aNVvJXIMD-NyasaokMWVp-t4934pFMOjE8O9XRJqVl3Wzr4XS4swDgZF-IRVXCTF0WpN-PW8acNIDe2bPyJO8xL96taexMm7C9e_6q_iNasrHnkVLfLr4J5MUhcs2ynzm0ZblkqRnjU0wkKiHffloIHQSNl-4IjXm7dny01hthG8Lg';

function EncodeJSON($JSONpayload,$registrationId) {
	$result = "{\"registration_ids\" :[".$registrationId."],".$JSONpayload;
	return $result;
}
print(EncodeJSON($JSONpayload, $registrationId));
//[{"id":"2","eventname":"Event title","description":"Test","Location":"Bahen centre","StartYear":"2014","StartMonth":"Nov","StartDay":"21","StartTime":"12","EndYear":"2014","EndMonth":"Nov","EndDay":"22","FinishTime":"12","settings":"True"}]
//{"days":[{"year":2014,"month":10,"day":23,"events":[{"eventId":1,"eventTitle":"CSC373H1F Test","startYear":2014,"startMonth":10,"startDayOfMonth":23,"startTime":"20:00","endYear":2014,"endMonth":10,"endDayOfMonth":23,"endTime":"21:00","location":"Bahen","description":"Bleh","isAllDay":false}]},{"year":2014,"month":10,"day":24,"events":[{"eventId":1,"eventTitle":"Doctor Appointment","startYear":2014,"startMonth":10,"startDayOfMonth":24,"startTime":"15:00","endYear":2014,"endMonth":10,"endDayOfMonth":24,"endTime":"16:00","location":null,"description":"fsdf","isAllDay":false}]}]}
echo "Entered data successfully\n";
mysql_close($conn);
?>