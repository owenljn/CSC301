#!/bin/bash

curl -X POST \
    -H "Authorization:key=AIzaSyA82yCJnslrBvS7RQqr0VwBTtRZ0GPryqQ" \
    -H "Content-Type:application/json" \
    -d '{"registration_ids":[
"APA91bGBYgdsMTJnQGhgH2BBDGc4CQ4mnt4UayN60qwKPwPytzoPPUfK65bbgn_8p8Vc2CxcUdvHGz7sXhRkFru_RrIbmQxRpTvLsutUaKrsjU2l6fBiAfd8kkTESLLpXR3l9zO-j_6I2NsrMGqiEthSG2yuF4jcECdX9cBHUTjvQWLuOlyvY5c"
            ],
            "data":{
                "payload":"Test payload message"
            }
        }' \
    https://android.googleapis.com/gcm/send




