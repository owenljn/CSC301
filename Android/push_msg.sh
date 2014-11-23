#!/bin/bash

curl -X POST \
    -H "Authorization:key=AIzaSyA82yCJnslrBvS7RQqr0VwBTtRZ0GPryqQ" \
    -H "Content-Type:application/json" \
    -d '{"registration_ids":[
"APA91bH70aNVvJXIMD-NyasaokMWVp-t4934pFMOjE8O9XRJqVl3Wzr4XS4swDgZF-IRVXCTF0WpN-PW8acNIDe2bPyJO8xL96taexMm7C9e_6q_iNasrHnkVLfLr4J5MUhcs2ynzm0ZblkqRnjU0wkKiHffloIHQSNl-4IjXm7dny01hthG8Lg"
            ],
            "data":{
                "payload":"{\"days\":[{\"year\":2014,\"month\":10,\"day\":10,\"events\":[{\"eventId\":1,\"eventTitle\":\"Project\",\"year\":2014,\"month\":10,\"dayOfMonth\":10,\"dayOfWeek\":2,\"startTime\":\"12:30:00\",\"endTime\":\"13:00:00\",\"location\":\"40 St George St, Toronto, ON, Canada\",\"description\":null,\"inviteeEmails\":null,\"recurring\":false,\"recursOn\":null,\"isAllDay\":false},{\"eventId\":2,\"eventTitle\":\"Test\",\"year\":2014,\"month\":10,\"dayOfMonth\":10,\"dayOfWeek\":2,\"startTime\":\"13:00:00\",\"endTime\":\"14:00:00\",\"location\":null,\"description\":null,\"inviteeEmails\":null,\"recurring\":false,\"recursOn\":null,\"isAllDay\":false},{\"eventId\":3,\"eventTitle\":\"Gym\",\"year\":2014,\"month\":10,\"dayOfMonth\":10,\"dayOfWeek\":2,\"startTime\":\"17:00:00\",\"endTime\":\"18:00:00\",\"location\":null,\"description\":null,\"inviteeEmails\":null,\"recurring\":false,\"recursOn\":null,\"isAllDay\":false}]},{\"year\":2014,\"month\":10,\"day\":11,\"events\":[{\"eventId\":4,\"eventTitle\":\"Project\",\"year\":2014,\"month\":10,\"dayOfMonth\":11,\"dayOfWeek\":3,\"startTime\":\"12:30:00\",\"endTime\":\"13:00:00\",\"location\":null,\"description\":null,\"inviteeEmails\":null,\"recurring\":false,\"recursOn\":null,\"isAllDay\":false},{\"eventId\":5,\"eventTitle\":\"Appointment\",\"year\":2014,\"month\":10,\"dayOfMonth\":11,\"dayOfWeek\":3,\"startTime\":\"16:00:00\",\"endTime\":\"16:15:00\",\"location\":null,\"description\":null,\"inviteeEmails\":null,\"recurring\":false,\"recursOn\":null,\"isAllDay\":false}]}]}"
            }
        }' \
    https://android.googleapis.com/gcm/send




