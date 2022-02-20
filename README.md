# TODO-App-Android-App

This TODO App has been developed making sure to take right measures to improve the user experience from the android projects I have worked so far. 

## Working
<ul>
  <li>When the user first opens the app, he/she will be redirected to username page where they need to provide their username, then tha app will stop to make changes in the backend
   and user will be needing to restart the app</li>
  <li>After opening app again, user will find the main Task page with their name greeted by the app</li>
  <li>Now user can use the app as they want. they just need to click on '+' button at the bottom right of the screen to add their todo task and they can not only use it for giving
   task but also for providng the date by what they want to complete it and priority</li>
  <li>Priority of the task could be seen in each tasks via different colors: red --> high priority, yellowishGreen --> medium priority, blue --> low priority</li>
  <li>User after completing the werk can use the radio button to mark it as complete in which task will be cut through and can click it again to remove it if they have pressed
  by mistake. This affect will remain even after stop app and restart again</li>
  <li>The garbage button for each task is provided which when pressed will delete the task for which it has been clicked</li>
  <li>User if want to change the username, can use change username item from menu in the toolbar on the top right corner. They can also see the about page</li>
</ul>

## Implementation
<ul>
  <li>Database used: ROOM Database for task management, Shared preferences to save username</li>
  <li>The app has been developed by dividing the working and follows structured development of the app</li>
  <li>Fragment is also used to allow user to add and update task which will be shown from the bottom of the page following the priciples of BottomSheetFragment </li>
  <li>Menu item for the toolbar for easy access to edit username and see the main Task page</li>
  <li>Image Buttons have been used to make it easily understandable system for the user.</li>
  <li>Scroll view and Recycler View Adapter to showcase the tasks to the user</li>
  <li>The colors have been chosen from particular pallete for a better user experience.</li>
</ul>
