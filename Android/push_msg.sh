#!/bin/bash

curl -X POST \
    -H "Authorization:key=AIzaSyA82yCJnslrBvS7RQqr0VwBTtRZ0GPryqQ" \
    -H "Content-Type:application/json" \
    -d '{"registration_ids":[
"APA91bGBYgdsMTJnQGhgH2BBDGc4CQ4mnt4UayN60qwKPwPytzoPPUfK65bbgn_8p8Vc2CxcUdvHGz7sXhRkFru_RrIbmQxRpTvLsutUaKrsjU2l6fBiAfd8kkTESLLpXR3l9zO-j_6I2NsrMGqiEthSG2yuF4jcECdX9cBHUTjvQWLuOlyvY5c"
            ],
            "data":{
                "payload":"{\"days\":[{\"year\":2014,\"month\":10,\"day\":8,\"events\":[{\"eventId\":1,\"eventTitle\":\"Project\",\"year\":2014,\"month\":10,\"dayOfMonth\":8,\"dayOfWeek\":7,\"startTime\":\"12:30:00\",\"endTime\":\"13:00:00\",\"location\":null,\"description\":null,\"inviteeEmails\":null,\"recurring\":false,\"recursOn\":null,\"isAllDay\":false},{\"eventId\":2,\"eventTitle\":\"Test\",\"year\":2014,\"month\":10,\"dayOfMonth\":8,\"dayOfWeek\":7,\"startTime\":\"13:00:00\",\"endTime\":\"14:00:00\",\"location\":null,\"description\":null,\"inviteeEmails\":null,\"recurring\":false,\"recursOn\":null,\"isAllDay\":false},{\"eventId\":3,\"eventTitle\":\"Gym\",\"year\":2014,\"month\":10,\"dayOfMonth\":9,\"dayOfWeek\":7,\"startTime\":\"17:00:00\",\"endTime\":\"18:00:00\",\"location\":null,\"description\":null,\"inviteeEmails\":null,\"recurring\":false,\"recursOn\":null,\"isAllDay\":false}]},{\"year\":2014,\"month\":10,\"day\":9,\"events\":[{\"eventId\":4,\"eventTitle\":\"Project\",\"year\":2014,\"month\":10,\"dayOfMonth\":9,\"dayOfWeek\":7,\"startTime\":\"12:30:00\",\"endTime\":\"13:00:00\",\"location\":null,\"description\":null,\"inviteeEmails\":null,\"recurring\":false,\"recursOn\":null,\"isAllDay\":false},{\"eventId\":5,\"eventTitle\":\"Appointment\",\"year\":2014,\"month\":10,\"dayOfMonth\":9,\"dayOfWeek\":7,\"startTime\":\"16:00:00\",\"endTime\":\"16:15:00\",\"location\":null,\"description\":null,\"inviteeEmails\":null,\"recurring\":false,\"recursOn\":null,\"isAllDay\":false}]}]}"
            }
        }' \
    https://android.googleapis.com/gcm/send




