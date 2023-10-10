# Serious Games

DISCLAIMER: Please keep in mind that this project was made for adademic purposes (Degree Thesis).
Apologies for the lack of comments. Feel free to contact me for any questions.

-----------------Summary---------------------


The present bachelor thesis refers to the design, the development and the evaluation of an
Android mobile application. The application contains 12 Serious Games. These games were
designed to activate some various cognitive skills of the brain such as caution, memory,
visual and acoustic perception. The app is intended for users of advanced age (55+) who aim
to improve their cognitive skills. This Thesis was made by two students (Vasilis Kassikos,
Antonis Antonopoulos). At the first stage the Android environment and the technologies that it
offers had been studied, so we could use them later in our development. At the same time,
we studied the impact that these games can do for the medical treatment of some cognitive
disorders (early stages of dementia and Alzheimers disease). Because of that we understood
the ultimate goal of this Thesis from the beginning. Therefore, the design of the application
started, the main screens and the navigation between them were approached. Furthermore,
the relational (SQL) database that the app used was designed. The last step of the design
was the definition of the 12 games and their difficulty levels. The next phase was the
development of the application were the main concern was its user-centric appearance. First
of all, the main functionalities of the app were developed, then the development and the
integration of one game at a time took place. After the completion of the development, we
made some alterations that our supervisor professor suggested, before we reached out to the
final version of the application. The last phase of this Thesis was the evaluation of the app
from 6 users of advanced age, they did a full use of the application and they filled some
related questionnaires. Their answers provided some useful results and conclusions for the
application and more generally the impact of Serious Games to the medical apps field.



-----------------AppDataBase class---------------------

This class is an abstract and extends the RoomDatabase because in this way we define our db
The annotation Database create the tables we provide in the curly brackets in our case the tables 
are the following ({User.class, Game.class, GameEvent.class, Statistic.class}). In adition if we want to grow up the db the we hae to 
change the version
The type converters are used to "create" sqlite datatypes.
In order to guarantee that there is no way for conflicts we create a sinchonized singeton. We also use the "on Create" callback to create
the game instances when the user signup.

-----------------AudioPersonPick activity-------------

The AudioPersonPick Activity is one of the app games. In this game you have to choose the right desciption of the displayed objects.
For listening the description you have to tap the play button which is displayed at the bottom of the screen. We support the game in landscape and portrait mode. While playing or listening the audio you are able to do any screen configuration for example if the game detect screen orientation then the audio keep playing and the new layout replace the old one. The "OnsaveInstanse State method" is required for saving variables the time that activity detect any configuration. In this way when the "Oncreate" method is called we assign the variables which we saved. So the activity continues running normally. There is also the "createRound" method where we define the number of rounds and which mode we will display. we create three modes "easy", "medium" and "advance" so there are three display functions. 

The activity also implements the "OnClickListener" in order to handle the clicks using one "onClick" method. The "nextRound" function handles the rounds, the timer between the rounds and the messages.

The "showTutorialPopUp" display the Turorial video with  the equivalent resource calling the Tutorial class.

The "shopPopUp" functionraise the allertDialog that inform user about his progress.

The "countPoints" function is responsible for counting the points and display the points of every single round 

-----------------BucketGame activity-------------

All games have the the same template. In this game there are some diferences as the player have to drag and drop the items. Every object implements a "custom TouchListener". The TouchListener detects the motion so we create a shadow of the objech which is selected from the user. From the other side the "buckets" objects have to implement a "custom DragListener" to interact with the objects we mention before. The DragListener has the following states
 "ACTION_DRAG_STARTED:" this state inform us that an object picked from the User.

"ACTION_DRAG_ENTERED" this state is activated when the object which is dragged interact with the specific area.

"ACTION_DRAG_EXITED" this state is activated when the object which is dragged left the specific area.

"ACTION_DROP" this state is activated when the object is dropped in the specific area

"ACTION_DRAG_ENDED" this state is activated when the motion is completed


-----------------Charts activity-------------

The graphic charts in our app are created with the powerfull library "implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'"
The library is on github and gives you the chance to create easily graphic charts. We retrieve the data to display from our database  and create a "List<PieEntry>" then we pass the List<PieEntry> in a "PieDataSet" to edit the graphics. In the end we created the PieChart.


-----------------DialogMsg  class-------------

The DialogMsg class handle the allert dialog when the game is over. This class extends the "DialogFragment" but in our case a new .xml file is created which overrides the standard layout. In "onDismiss" method an intent will transfer the user in the gameActivity


  
-----------------Game class-------------

This class is a model. The annotation "Entity" is required in order to be a table in the db. The annotation @PrimaryKey(autoGenerate = true) is used to autogenerate the id of each game


-----------------GameAdapter class-------------

The GameAdapter Class is required for the recycler view. We pass the data we need then we count the data and "onCreateViewHolder" create a Holder for each item. The "onBindViewHolder" binds the position with the holder. It was necessary to create a clickInterface beacuse we wanted each item to be clickable


-----------------GameDao Interface-------------

This interface hold the queries which are related with the games


-----------------GameEvent class-------------

This class is a model. It holds information about the relation between user and  game for every round. That is why we use the annotation "ForeignKye"


-----------------GameEventViewModel  class-------------

This class send data to the Asynk

-----------------GameHelper  class-------------

The GameHelper class is a helper class. This class holds some global information which are common everywhere in the app for example the  games instances, a map of greek - english game name and the csv writer class.

-----------------GameList Activity-------------

This Activity is the main Activity of the app. It holds the menu and the games. This activity also implements SharedPreferences.OnSharedPreferenceChangeListener, NavigationView.OnNavigationItemSelectedListener. The first one implementation is to save user's preferences and the second one is the menu item listener.
In this activity we ask for permission to read and write in storage. In addition we handle user's game choise "gameAdapter.setOnClickListener" with this statement. We also override "onBackPressed" to display an alert dialog that exits the app.
The " setupPreferences" function holds the user's preferences and we can retrieve them every single time that the user join our app.
The "onSharedPreferenceChanged" save and display instantly if the user make some changes in his preefrences.
The "onNavigationItemSelected" function handles the user's option in the menu.
There is also a class ExportDatabaseCSVTask which extends Asynctask and create a .csv file with the user's stats.


-----------------GameViewModel class-------------

This class extends the "AndroidViewModel" and it holds a single game or a list of games it depends on the function which is called. 


-----------------Login Activity-------------

This activity is the first activity the user face up when join the app. First of all we check if his preference is to stay logged in , if so an intent will transfer him in the GameList otherwise he has to fill his username and if it exists in the db will successfully log in.

-----------------MemoryMatrix Activity-------------

MemoryMatrix activity is a game. As we mention before all games have the same template so we will not analyze all of the functions again. We will analyse the specialities. In this game there is a great difference in the layout between the difficulties. So it was necessary to create separate fragments for each difficulty. So in "checkMode" function we check the choosen difficulty and load the appropriate fragment. We load fragment using "fragment manager" then we create a "fragment transaction" and we provide the tag of the fragment in order to handle it later. The fragments communicate with the activity via an interface which the activity implements called "OnDataPass". It is an interface which pass data from fragment to the activity in order to handle the rounds and the points. There is also a "checkIfEnds" method which check if the game is over.

The fragments we mention before are  "MemoryMatrixAdv" and "MemoryMatriEz"

-----------------MemoryMatrixViewModel class-------------

This class is a helper storage. It extends the ViewModel and we set and get the timer we use. It is a good way to handle the timer as it is keep running asynchronous which means if the fragment is destroyed the timer keep running until it run out of time. So if the fragment is destroyed we cancel the  timer and in this way there is no memory and cpu leaks.


-----------------MenuChart Activity-------------

We mention everything about charts in charts activity above. See Charts Activity.

-----------------ObjectSelector Activity-------------

This activity is a game. There is nothing special in this activity. It follows the template of games.


-----------------OrderGame Activity-------------

This activity is a game. There is nothing special in this activity. It follows the template of games.


-----------------Questionnaire Activity-------------

The Questionaire Activity is the rate tab in out menu. This activity load the fragmanets which display the questions and the rate. There is a "loadFragment" method where the fragment manager replace the activity .xml file with the .xml file of the first question. So in this way when the user tap the next question button the new fragment display the old one. 


-----------------Questions Fragments-------------

Question1 ,Question2, Question3, Question4, Question5, Question6, Question7, Question8, Question9, Question10.

The fragments above are the questions that the questionnaire activity display.
There is a speciality in question10 where we dont load any fragment but we display an alertDialog to thanks the user for his time.


-----------------Register Activity-------------

In register activity user have to fill some fields. In education field there is a editTextFilledExposedDropdown where user has to pick one of the displayed values. In order to create the dropDown menu we use an editTextFilledExposedDropdown which is an option of the materialDesign library. This option get as parameter an arrayadapter in order to set the values. We set an "setOnItemClickListener" to get the picked value and set it to the field. We use a radioGroup component for gender option. RadioGroup has "setOnCheckedChangeListener" to display instantly any changes on the radioGroup. RadioGroup is also suitable for this reason because  you are not able to pick both of them.

The datapickerDialog is used in order to help the user pick easily his birthdate and then we use a formatter in order to format the date for our database. 

On Register button we check if the user fill the fields and if the user exists. If so then an error message is displayed otherwise the user is registered successfully and the login page replace the register one.



-----------------RockPaperScissors Activity-------------
This activity is a game. There is nothing special in this activity. It follows the template of games.


-----------------Rotation Activity-------------
This activity is a game. There is nothing special in this activity. It follows the template of games.


-----------------ScallingGame Activity-------------
This activity is a game. There is nothing special in this activity. It follows the template of games. In this game we do not support screen orientation.

-----------------Session class-------------

The session class is a storage class. We create a sharedPreferenceManager "PreferenceManager.getDefaultSharedPreferences()"in order to save user's options. 
This line of code "prefs.edit().putInt("USERID", userid).apply()" save the userName in an .xml file. We use apply keyword to save asynchronous the data in the file.


-----------------Settings Activity-------------

This activity displays the setting's .xml file

-----------------SettingsClass Class-------------

The settingsClass class help us to edit the settings file. We get any option of the file using the option's key and the we are able to edit it. We also use a "setTheme(R.style.settingsTheme)" to set our custom theme.




-----------------ShadowGame Activity , SoundImage Activity, SoundWordActivity, Suitcase Activity-------------

They are games. There is nothing special to refer. They follow the template of games.


-----------------Statistic Class-------------

This class is a model. We mention everything about models in Game Class.


-----------------StatisticHelper Class-------------

This class is the same class as gameHelper we refer previously


-----------------Tutorial Class-------------

The tutorial class extends "DialogFragment" because it is displayed as a pop up window. The class also implements "SharedPreferences.OnSharedPreferenceChangeListener" because we have to save user's option to turn off the tutorials.
 The "MediaPlayer.OnCompletionListener" help us to handle the  video when it is over.
 
 
 -----------------User Class-------------
 
 This class is a model. We mention everything about models in Game Class.
 
 
 -----------------UserDao Interface-------------
 
 The annotation Dao is to insert objects in the database. The dao interface communicates with the database and retrieves data. All the queries which are related with the user are executed within this file
 
  -----------------UserGameStats Class-------------
  
  It is a helper model. We use ths class in order to retrieve data from different files. We needed data from statistic model and game model so we had to create this class. The Embedded annotation is used beacuse we need all the data from statistic model.
  
  
   -----------------UserRepository Class-------------
   
  UserRepository is used in order to execute Async taks. All the queries about User are executed using different thread no the main one. The main thread supports the UI. All the other operations are executed from other threads.







