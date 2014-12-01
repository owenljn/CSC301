#!/bin/bash

curl -X POST \
    -H "Authorization:key=AIzaSyA82yCJnslrBvS7RQqr0VwBTtRZ0GPryqQ" \
    -H "Content-Type:application/json" \
    -d '{"registration_ids":[
"APA91bH70aNVvJXIMD-NyasaokMWVp-t4934pFMOjE8O9XRJqVl3Wzr4XS4swDgZF-IRVXCTF0WpN-PW8acNIDe2bPyJO8xL96taexMm7C9e_6q_iNasrHnkVLfLr4J5MUhcs2ynzm0ZblkqRnjU0wkKiHffloIHQSNl-4IjXm7dny01hthG8Lg"
            ],
            "data":{
                "payload":"{\"days\":[{\"year\":2014,\"month\":10,\"day\":23,\"events\":[{\"eventId\":1,\"eventTitle\":\"CSC373H1F Test\",\"startYear\":2014,\"startMonth\":10,\"startDayOfMonth\":23,\"startTime\":\"20:00\",\"endYear\":2014,\"endMonth\":10,\"endDayOfMonth\":23,\"endTime\":\"21:00\",\"location\":\"Bahen\",\"description\":\"Bleh\",\"isAllDay\":false}]},{\"year\":2014,\"month\":10,\"day\":24,\"events\":[{\"eventId\":1,\"eventTitle\":\"Doctor Appointment\",\"startYear\":2014,\"startMonth\":10,\"startDayOfMonth\":24,\"startTime\":\"15:00\",\"endYear\":2014,\"endMonth\":10,\"endDayOfMonth\":24,\"endTime\":\"16:00\",\"location\":null,\"description\":\"fsdf\",\"isAllDay\":false}]}]}"
            }
        }' \
    https://android.googleapis.com/gcm/send




