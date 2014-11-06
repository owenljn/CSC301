For our project, aside from meeting every Thursday in tutorial, we had weekly meetings at the laptop lounge at Bahen Center. Before each sprint, we would all meet as a team and discuss the plan for the next sprint and would decide how to split up the tasks. We used a google doc to document all matters that we discussed during the sprint meetings and how we would split up the work for the next sprint. At the beginning of the first sprint, we decided the frameworks we would use and chose to use Android, Google Cloud, TomCat, AWS, PostGreSQL for our project. At the end of each assigned sprint, we would meet up together and discuss what each member had contributed and decide what to do for the next sprint using the same process.

After planning and delegating tasks, each group member would work on their assigned tasks during the sprint. We split the work into classes and methods, assigning groups of members to each job. However, we chose to work together as a team rather than have separate scrum runs due to platform issues. Many of us had trouble importing the Android project on our machines so we met up to work together. So rather than meet as an entire team for scrum meetings, we would meet frequently in our smaller teams to work on the project. Whenever a larger problem would arise, a member would bring up that issue to the entire group and we would gather as a group to brainstorm ideas to resolve the issue. For example, when we discovered an error causing our program to terminate immediately, the entire group met up to resolve the problem together. Overall, we would always plan everything out carefully, stick to the plan and meet up as a group after each sprint. In the end, we were not able to implement notifications for this phase, but have a working prototype which can store and display events.

For this project, we did not focus as much on using the GitHub issue management system as much as we did in the exercise. We did not use it as prevalently to split up the tasks but we used it to bring up major errors. This was actually a poor decision because we found, from the exercise, that the issue system was a very effective way to organize each member’s tasks. However, after each issue was raised, although we did not use it often, the entire group would be immediately aware of the bug and we would all work to fix it, and as stated above, would strive to meet up. 


Full Notes (Please see if you would like to see how we planned to  split our tasks) :https://docs.google.com/document/d/1SEjOwupErPjjRO_Y1ZFOKwrBb-rUjXesonZFpRu2eZ0/edit?usp=sharing

Meeting Notes

First Meeting
Sat 1PM October 25, 2014

1.	Architecture And Research

2.	Matters to Discuss

Server - Install Tomcat + PostgreSQL on the AWS server.

Server Registration with Google - Research how to register server with the Google c2dm service.
Android Cloud to Device Messaging Framework - Google ...

Database Design - Create a relation diagram for the different data tables.
Storing tasks locally vs. storing tasks on server.
Syncing after loss of internet connection?

Skeleton Code - Empty Classes + Packages as per the CRC cards.

3.	Task Assignment

Skeleton Code - Alina (Monday)
Database Design - Matthew, Tim (https://drive.google.com/file/d/0B_PlG8tow-MaYXQ4RGZOS1RIcWc/view)
Research Tomcat Installation on AWS - everyone.
(http://alextheedom.wordpress.com/cloud/amazon-free-usage-tier-installing-tomcat-7-on-an-ec2-linux-instance/)
Research Server Registration with Google - everyone.
Research How to Sync Local vs. Remote Database - everyone.

4.	Next Meeting Date & Discussion Topics

Skeleton code review & modification.
Database design review.
Assign coding tasks.
define functions
Next meeting Monday, October 27, 2014 at 4:00 p.m.


Second Meeting
Mon 4-6PM October 27, 2014

1.	Items Completed

Skeleton Code - Completed. Needs to be reviewed/modified by everyone.
Database Installation/Configuration - AWS instance created, in process of installing Tomcat (will be completed today in the evening).
Tomcat Installation Instructions - See below (also we’re installing Tomcat 8, not 7) =). (http://alextheedom.wordpress.com/cloud/amazon-free-usage-tier-installing-tomcat-7-on-an-ec2-linux-instance/)
Database Design - .
Server Registration with Google - It appears that c2dm is deprecated, gcm is used instead. They simplified everything from mobile app registration to server registration, so this shouldn’t be a problem. 

2.	Matters to Discuss

Local and Remote Databases - Will we allow task manipulations when offline for our MVP? Syncing local and remote databases could get really complicated, so we might want to stay away from it as much as we can, and maybe implement it if we have time.
Skeleton Code Review/Changes - Everyone look over the code.
Database Design Review - Everyone look over the file.
Imitate or Implement Server - Will we have the time to implement the server for the demos? There is a lot of code involved as most of our application logic is server-side. Perhaps we should focus on development of the mobile application (which will also be quite time consuming), and imitate push notifications via sh script (curl).

3.	Task Assignment


4.	Next Meeting Date 

Third Sprint Meeting - Saturday, November 1, 2014



Third Meeting
Saturday 2-6PM Novemer 2, 2014
Many members had problems importing/installing the project on their machines. Most issues were solved by the end of the meeting




