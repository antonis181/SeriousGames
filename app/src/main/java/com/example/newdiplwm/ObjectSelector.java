package com.example.newdiplwm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ObjectSelector extends AppCompatActivity implements View.OnClickListener{

    private static  final int THRESHOLD_EASY = 120;
    private static  final int THRESHOLD_ALL = 180;

    private ImageView imagebutton1, imagebutton2, imagebutton3, imagebutton4, imagebutton5, imagebutton6,exit ,replayTutorial;
    private LinearLayout logoLinear, textsLinear;
    private MaterialButton startButton;
    private TextView rounds , animPointsText, textMsg , textMsgTime;
    private String msgHelper;

    private static final String GAME_ID = "GAME_ID";
    private static final String USER_ID = "USER_ID";
    private static final String DIFFICULTY = "DIFFICULTY";
    private static final String MATCH = "MATCH";
    private static final String PICKED = "PICKED";
    private static final String UNPICKED = "UNPICKED";
    private static final String IMAGEVIEWS = "IMAGEVIEWS";
    private static final String CURRENTDIFFICULTY = "CURRENTDIFFICULTY";
    private static final String TOTALROUNDS = "TOTALROUNDS";
    private static final String COUNTER = "COUNTER";
    private static final String CLICK = "CLICK";
    private static final String MENUDIFFICULTY = "MENUDIFFICULTY";
    private static final String PALIO = "PALIO";
    private static final String MISSPOINTS = "MISSPOINTS";
    private static final String HIT = "HIT";
    private static final String MISS = "MISS";
    private static final String TOTALPOINTS = "TOTALPOINTS";
    private static final String TRUECOUNTER = "TRUECOUNTER";
    private static final String TOTALSPEED = "TOTALSPEED";
    private static final String STARTTIME = "STARTTIME";
    private static final String ENDTIME = "ENDTIME";
    private static final String STARTSPEED = "STARTSPEED";
    private static final String ENDSPEED = "ENDSPEED";
    private static final String CURRENTROUND = "CURRENTROUND";
    private static final String HELPER = "HELPER";
    private static final String GAMEINIT = "GAMEINIT";
    private static final String LOSEHELPER = "LOSEHELPER";
    private static final String CLOCK = "CLOCK";
    private static final String NEXTROUND = "NEXTROUND";
    private static final String MSGHELPER = "MSGHELPER";

    private int game_id, user_id;
    private int TotalRounds=0 , currentRound=0, counter=0 ,click=0;
    private String menuDifficulty, currentDifficulty;
    private boolean palio =true;

    private HashMap<String,Integer> pointsHashMap = new HashMap<String, Integer>();
    private GameEventViewModel gameEventViewModel;
    private UserViewModel userViewModel;
    private int hit = 0 , miss = 0 , totalPoints = 0, trueCounter = 0;
    private boolean missPoints = false;

    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp startspeed;
    private Timestamp endspeed;
    private double totalspeed = 0;

    private int helper=0, vibeduration = 1000;
    private boolean gameInit = false , loseHelper = false;
    private Vibrator vibe;
    private CountDownTimer Timer, nextRoundTimer;
    private static final long START_TIME_IN_MILLIS = 1000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS, timeLeftInMillisNextRound = 0;



    private HashMap<Integer, Integer> imageIDS = new HashMap<Integer, Integer>();
    private ArrayList<Integer> unpicked = new ArrayList<Integer>();
    private ArrayList<Integer> picked = new ArrayList<Integer>();
    private ArrayList<Integer> imageviews = new ArrayList<Integer>();

    private Session session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_selector);
        assignAllButtons();
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        session = new Session(getApplicationContext());

        pointsHashMap.put(getResources().getString(R.string.easyValue), 0);
        pointsHashMap.put(getResources().getString(R.string.mediumValue), 5);
        pointsHashMap.put(getResources().getString(R.string.advancedValue), 10);

        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        if (savedInstanceState != null) {
            gameInit = savedInstanceState.getBoolean(GAMEINIT);
            if (gameInit)
            {
                startButton.setVisibility(View.INVISIBLE);
                logoLinear.setVisibility(View.GONE);
                user_id = savedInstanceState.getInt(USER_ID);
                game_id = savedInstanceState.getInt(GAME_ID);
                imageIDS = (HashMap<Integer, Integer>) savedInstanceState.getSerializable(MATCH);
                for (int key : imageIDS.keySet())
                {
                    ImageView v = findViewById(key) ;
                    v.setImageResource(imageIDS.get(key));

                }
                picked = savedInstanceState.getIntegerArrayList(PICKED);
                //unpicked = savedInstanceState.getIntegerArrayList(UNPICKED);
                imageviews = savedInstanceState.getIntegerArrayList(IMAGEVIEWS);
                currentDifficulty = savedInstanceState.getString(CURRENTDIFFICULTY);
                menuDifficulty = savedInstanceState.getString(MENUDIFFICULTY);
                TotalRounds = savedInstanceState.getInt(TOTALROUNDS);
                counter =savedInstanceState.getInt(COUNTER);
                click =savedInstanceState.getInt(CLICK);
                hit =savedInstanceState.getInt(HIT);
                miss =savedInstanceState.getInt(MISS);
                totalPoints = savedInstanceState.getInt(TOTALPOINTS);
                trueCounter = savedInstanceState.getInt(TRUECOUNTER);
                totalspeed = savedInstanceState.getDouble(TOTALSPEED);
                palio = savedInstanceState.getBoolean(PALIO);
                missPoints = savedInstanceState.getBoolean(MISSPOINTS);
                startTime = (Timestamp) savedInstanceState.getSerializable(STARTTIME);
                endTime = (Timestamp) savedInstanceState.getSerializable(ENDTIME);
                startspeed = (Timestamp) savedInstanceState.getSerializable(STARTSPEED);
                endspeed = (Timestamp) savedInstanceState.getSerializable(ENDSPEED);
                helper = savedInstanceState.getInt(HELPER);
                currentRound = savedInstanceState.getInt(CURRENTROUND);
                loseHelper = savedInstanceState.getBoolean(LOSEHELPER);
                mTimeLeftInMillis = savedInstanceState.getLong(CLOCK);
                rounds.setText((currentRound ) + " / "+TotalRounds);
                timeLeftInMillisNextRound = savedInstanceState.getLong(NEXTROUND);
                msgHelper = savedInstanceState.getString(MSGHELPER);
                if ((helper == 3 && currentDifficulty.equals(getResources().getString(R.string.easyValue))) || (helper == 4 && currentDifficulty.equals(getResources().getString(R.string.mediumValue))) || (helper == 5 &&currentDifficulty.equals(getResources().getString(R.string.advancedValue))) || loseHelper)
                {
//                    startButton.setVisibility(View.VISIBLE);
//                    startButton.setText(getResources().getString(R.string.nextRound));

                    if (timeLeftInMillisNextRound > 1000)
                    {
                        disableReplayTut();
                        textMsg.setText(msgHelper);
                        textMsg.setTextColor(getResources().getColor(R.color.greenStrong));
                        textsLinear.setVisibility(View.VISIBLE);
                        nextRoundTimer = userViewModel.getNextRoundTimer();
                        nextRoundTimer.cancel();
                        nextRoundTimer = new CountDownTimer(timeLeftInMillisNextRound,1000) {
                            @Override
                            public void onTick(long l) {
                                timeLeftInMillisNextRound = l;

                                if (currentRound == TotalRounds)
                                {
                                    textMsgTime.setText("");
                                }
                                else
                                {
                                    textMsgTime.setText(getResources().getString(R.string.nextRound)+l/1000);
                                }


                            }

                            @Override
                            public void onFinish() {

                                timeLeftInMillisNextRound = 0;

                                if (currentRound == TotalRounds)
                                {
                                    textsLinear.setVisibility(View.INVISIBLE);
                                    disableReplayTut();
                                }
                                else
                                {
                                    enableReplayTut();
                                    textMsgTime.setText("");
                                    textsLinear.setVisibility(View.INVISIBLE);
                                    helper = 0;
                                    gameInit = true;
                                    loseHelper = false;
                                    initializeUnpickedList();
                                    createRound();
                                    nextRoundTimer = null;
                                }

                            }
                        }.start();
                        userViewModel.setNextRoundTimer(nextRoundTimer);
                    }
                }
                else{
                    startButton.setVisibility(View.INVISIBLE);}

            }
            else
            {

         //       startButton.setVisibility(View.VISIBLE);
                user_id = savedInstanceState.getInt(USER_ID);
                game_id = savedInstanceState.getInt(GAME_ID);
                currentDifficulty = savedInstanceState.getString(CURRENTDIFFICULTY);
                menuDifficulty = savedInstanceState.getString(MENUDIFFICULTY);
                imageviews = savedInstanceState.getIntegerArrayList(IMAGEVIEWS);
                picked = savedInstanceState.getIntegerArrayList(PICKED);
                imageIDS = (HashMap<Integer, Integer>) savedInstanceState.getSerializable(MATCH);
                unclick();
            }
            if (currentRound >0)
            {
                logoLinear.setVisibility(View.GONE);
            }

        }
        else
        {

            user_id = session.getUserIdSession();
            game_id = session.getGameIdSession();
            menuDifficulty = session.getModeSession();
            fillListImageview();
            unclick();
        }


        if (!session.getPlayAgainVideo() && currentRound == 0) {

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                showTutorialPopUp(R.raw.tutorial_objectselector_landscape);

            }
            else
            {
                showTutorialPopUp(R.raw.tutorial_objectselector_portrait);
            }

        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        Fragment prev = fm.findFragmentByTag("TutorialObjectSelector");
        if (prev != null) {

            fragmentTransaction.remove(prev);
            fragmentTransaction.commit();
        }

        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                helper = 0;
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                Fragment prev = fm.findFragmentByTag("TutorialObjectSelector");
                if (prev != null) {

                    fragmentTransaction.remove(prev);
                    fragmentTransaction.commit();
                    fm.popBackStack();
                    //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
                }
                logoLinear.setVisibility(View.GONE);
                gameInit = true;
                loseHelper = false;
                initializeUnpickedList();
                createRound();
                startButton.setVisibility(View.INVISIBLE);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onbackAndExitCode();
            }
        });

        replayTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    showTutorialPopUp(R.raw.tutorial_objectselector_landscape);

                }
                else
                {
                    showTutorialPopUp(R.raw.tutorial_objectselector_portrait);
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        onbackAndExitCode();
    }

    private void onbackAndExitCode(){
        if (nextRoundTimer != null)
        {
            nextRoundTimer.cancel();
        }

        if (currentRound == 0 || click ==0 )
        {
            startTime = new Timestamp(System.currentTimeMillis());
            endTime = new Timestamp(System.currentTimeMillis());
            GameEvent gameEvent = new GameEvent(game_id, user_id, 0, 0, 1, 0, 0, 0, 0, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            finish();

        }
        else
        {
            if (startspeed == null || endspeed==null)
            {
                totalspeed +=0;
            }
            endTime = new Timestamp(System.currentTimeMillis());
            long longTime = endTime.getTime() - startTime.getTime();
            float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
            if (totalPlayInSeconds > THRESHOLD_EASY && currentDifficulty.equals(getResources().getString(R.string.easyValue)))
            {
                totalPlayInSeconds = THRESHOLD_EASY;
            }
            else if (totalPlayInSeconds > THRESHOLD_ALL)
            {
                totalPlayInSeconds = THRESHOLD_ALL;
            }
            GameEvent gameEvent = new GameEvent(game_id, user_id, hit, miss , 1, totalPoints, (double) hit / TotalRounds, totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            finish();

        }

    }


    private void createRound(){

        if (currentRound == 0)
        {
            startTime = new Timestamp(System.currentTimeMillis());
        }

        if (menuDifficulty.equals(getResources().getString(R.string.easyValue))){
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            displayGameEz();
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.mediumValue)))
        {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            displayGameMedium();
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.advancedValue)))
        {
            currentDifficulty = menuDifficulty;
            TotalRounds=3;
            displayGameAdv();
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.easymediumValue)))
        {
            TotalRounds = 4;

            if (currentRound<=1){
                currentDifficulty = getResources().getString(R.string.easyValue);
                displayGameEz();
            }
            else
            {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                displayGameMedium();
            }
        }
        else
        {
            TotalRounds =5;
            if (currentRound<1)
            {
                currentDifficulty = getResources().getString(R.string.easyValue);
                displayGameEz();
            }
            else if (currentRound>=1 && currentRound<=2)
            {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                displayGameMedium();
            }
            else
            {
                currentDifficulty = getResources().getString(R.string.advancedValue);
                displayGameAdv();
            }
        }
        currentRound++;
        rounds.setText((currentRound ) +" / "+TotalRounds);

    }

    private void displayGameEz(){
        unclick();
        palio =true;
        Random rand = new Random();
        int spot = rand.nextInt(3);
        for (int i=0;i<=2; i++)
        {

            if (picked.size()!=0 && palio && spot == i)
            {
                Collections.shuffle(picked);
                ImageView v = findViewById(imageviews.get(i)) ;
                v.setImageResource(picked.get(0));
                imageIDS.put(imageviews.get(i),picked.get(0));
                palio = false;

            }
            else
            {
                Collections.shuffle(unpicked);
                ImageView v = findViewById(imageviews.get(i));
                v.setImageResource(unpicked.get(i));
                imageIDS.put(imageviews.get(i),unpicked.get(i));
                unpicked.remove(i);
            }



        }
        unpicked.clear();
        startspeed = new Timestamp(System.currentTimeMillis());
        click();

    }

    private void displayGameMedium(){

        unclick();
        palio =true;
        Random rand = new Random();
        int spot = rand.nextInt(4);
        int spotmedrange = rand.nextInt(2);
        int spotmedrange1 = rand.nextInt(2)+2;

        for (int i=0;i<=3; i++)
        {

            if (picked.size() == 1 && palio && spot == i)
            {
               // Collections.shuffle(picked);
                ImageView v = findViewById(imageviews.get(i)) ;
                v.setImageResource(picked.get(0));
                imageIDS.put(imageviews.get(i),picked.get(0));
                palio = false;

            }
            else if (picked.size() ==2 && palio  && counter<=1 && ((spotmedrange == i)|| spotmedrange1==i))
            {
                ImageView v = findViewById(imageviews.get(i)) ;
                v.setImageResource(picked.get(counter));
                imageIDS.put(imageviews.get(i),picked.get(counter));
                counter++;
                palio = counter <= 1;

            }
            else if (picked.size() ==3 && palio  && counter<=1 && ((spotmedrange == i)|| spotmedrange1==i))
            {
                if (counter == 0){
                    Collections.shuffle(picked);
                }
                ImageView v = findViewById(imageviews.get(i)) ;
                v.setImageResource(picked.get(counter));
                imageIDS.put(imageviews.get(i),picked.get(counter));
                counter++;
                palio = counter <= 1;

            }
            else
            {
                Collections.shuffle(unpicked);
                ImageView v = findViewById(imageviews.get(i));
                v.setImageResource(unpicked.get(i));
                imageIDS.put(imageviews.get(i),unpicked.get(i));
                unpicked.remove(i);
            }

        }
        counter =0;
        unpicked.clear();
        startspeed = new Timestamp(System.currentTimeMillis());
        click();
    }

    private void displayGameAdv(){

        unclick();
        palio =true;
        Random rand = new Random();

        ArrayList<Integer> randomNums = new ArrayList<Integer>();
        for (int i = 0 ; i<=4; i++)
        {
            randomNums.add(i);

        }
        Collections.shuffle(randomNums);


        for (int i=0;i<=4; i++)
        {

            if (picked.size() == 1 && palio && randomNums.get(counter) == i)
            {
                // Collections.shuffle(picked);
                ImageView v = findViewById(imageviews.get(i)) ;
                v.setImageResource(picked.get(0));
                imageIDS.put(imageviews.get(i),picked.get(0));
                palio = false;

            }
            else if (picked.size() == 2 && palio  && ((randomNums.get(0) == i) || (randomNums.get(1) == i)))
            {
                ImageView v = findViewById(imageviews.get(i)) ;
                v.setImageResource(picked.get(counter));
                imageIDS.put(imageviews.get(i),picked.get(counter));
                counter++;
                palio = counter <= 1;

            }
            else if (picked.size() == 3 && palio && ((randomNums.get(0) == i) || (randomNums.get(1) == i) || (randomNums.get(2) == i)))
            {

                ImageView v = findViewById(imageviews.get(i)) ;
                v.setImageResource(picked.get(counter));
                imageIDS.put(imageviews.get(i),picked.get(counter));
                counter++;
                palio = counter <= 2;

            }
            else if (picked.size() ==4 && palio && ((randomNums.get(0) == i) || (randomNums.get(1) == i) || (randomNums.get(2) == i) || (randomNums.get(3) == i)))
            {
                ImageView v = findViewById(imageviews.get(i)) ;
                v.setImageResource(picked.get(counter));
                imageIDS.put(imageviews.get(i),picked.get(counter));
                counter++;
                palio = counter <= 3;

            }
            else
            {
                Collections.shuffle(unpicked);
                ImageView v = findViewById(imageviews.get(i));
                v.setImageResource(unpicked.get(i));
                imageIDS.put(imageviews.get(i),unpicked.get(i));
                unpicked.remove(i);
            }

        }
        counter = 0;
        unpicked.clear();
        startspeed = new Timestamp(System.currentTimeMillis());
        click();
    }



    @Override
    public void onClick(View view) {

        Timer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                for (int key : imageIDS.keySet()) {
                    ImageView v = findViewById(key);
                    v.setImageResource(0);


                }
//                startButton.setText(getResources().getString(R.string.nextRound));
//                startButton.setVisibility(View.VISIBLE);

                picked.clear();
                unpicked.clear();
                imageIDS.clear();

                if (currentRound == TotalRounds)
                {
                    startButton.setVisibility(View.INVISIBLE);
                    endTime = new Timestamp(System.currentTimeMillis());
                    long longTime = endTime.getTime() - startTime.getTime();
                    float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
                    if (totalPlayInSeconds > THRESHOLD_EASY && currentDifficulty.equals(getResources().getString(R.string.easyValue)))
                    {
                        totalPlayInSeconds = THRESHOLD_EASY;
                    }
                    else if (totalPlayInSeconds > THRESHOLD_ALL)
                    {
                        totalPlayInSeconds = THRESHOLD_ALL;
                    }
                    GameEvent gameEvent = new GameEvent(game_id,user_id,hit,miss,0,totalPoints,(double)hit/(hit+miss),totalspeed/click,totalPlayInSeconds,menuDifficulty,startTime,endTime);
                    gameEventViewModel.insertGameEvent(gameEvent);
                    userViewModel.updatestatsTest(user_id,game_id);
                    shopPopUp();
                }
            }
        };

        click++;

        endspeed = new Timestamp(System.currentTimeMillis());
        long diffspeed = endspeed.getTime() - startspeed.getTime();
        double speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);
        totalspeed += speedseconds;

        if (imageIDS.containsKey(view.getId()))
        {


            if (picked.contains(imageIDS.get(view.getId())))
            {
                unclick();
                startAnimation();
                Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
                view.startAnimation(animShake);
                vibe.vibrate(vibeduration);
                //edw xanei
                loseHelper = true;
                miss++;
                missPoints =true;
                countPoints();
                Timer.start();
                nextRound();

            }
            else {
                initializeUnpickedList();
                picked.add(imageIDS.get(view.getId()));

                for (int i:picked)
                {
                    ListIterator  itr = unpicked.listIterator();
                    while (itr.hasNext())
                    {
                        int temp = (int) itr.next();
                        if (temp == i)
                        {
                            itr.remove();
                            break;
                        }
                    }

                }

                if (picked.size() == 3 && currentDifficulty.equals(getResources().getString(R.string.easyValue)))
                {
                    unclick();
                    startAnimation();
                    helper = picked.size();
                    totalspeed = totalspeed/picked.size();
                    hit++;
                    trueCounter++;
                    //edw nikise
                    countPoints();

                    Timer.start();
                  nextRound();
                }
                else if (currentDifficulty.equals(getResources().getString(R.string.easyValue)))
                {
                    displayGameEz();
                }
                if (picked.size() == 4 && currentDifficulty.equals(getResources().getString(R.string.mediumValue)))
                {
                    unclick();
                    startAnimation();
                    helper = picked.size();
                    hit++;
                    trueCounter++;
                    countPoints();
                    Timer.start();
                    nextRound();
                }
                else if (currentDifficulty.equals(getResources().getString(R.string.mediumValue)))
                {
                    displayGameMedium();
                }
                if (picked.size() == 5 && currentDifficulty.equals(getResources().getString(R.string.advancedValue)))
                {
                    unclick();
                    startAnimation();
                    helper = picked.size();
                    hit++;
                    trueCounter++;
                    countPoints();
                    Timer.start();
                    nextRound();
                }
                else if (currentDifficulty.equals(getResources().getString(R.string.advancedValue)))
                {
                    displayGameAdv();
                }

            }

        }

    }

    private void enableReplayTut(){
        replayTutorial.setEnabled(true);
        replayTutorial.setAlpha(1f);
    }
    private void disableReplayTut(){
        replayTutorial.setEnabled(false);
        replayTutorial.setAlpha(0.5f);
    }

    private void showTutorialPopUp(int res){
        DialogFragment dialogFragment = new Tutorial(ObjectSelector.this,res,getPackageName());
        dialogFragment.show(getSupportFragmentManager(),"TutorialObjectSelector");
    }

    private void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id,ObjectSelector.this,hit,totalPoints);
        newFragment.show(getSupportFragmentManager(), "ObjectSelector");
    }



    private void countPoints() {

        int currentPoints = 0;

        if (!missPoints && trueCounter == 1) {
            currentPoints += 10;
            currentPoints += pointsHashMap.get(currentDifficulty);
            textMsg.setText(R.string.win);
        } else if (!missPoints && trueCounter == 2) {
            currentPoints += 20;
            currentPoints += pointsHashMap.get(currentDifficulty);
            textMsg.setText(R.string.win1);
        } else if (!missPoints && trueCounter >= 3) {
            currentPoints += 30;
            currentPoints += pointsHashMap.get(currentDifficulty);
            textMsg.setText(R.string.win2);
        } else if (missPoints) {
            currentPoints += 0;
            trueCounter = 0;
            missPoints = false;
            textMsg.setText(R.string.lose);
        }
        msgHelper = textMsg.getText().toString();
        //msgHelper = textMsg.getText().toString();
        totalPoints += currentPoints;
        animPointsText.setText("+ " + currentPoints);
        if (currentPoints == 0) {
            animPointsText.setTextColor(Color.RED);
        } else
            animPointsText.setTextColor(Color.GREEN);
    }


    private void nextRound(){
        textsLinear.setVisibility(View.VISIBLE);
        textMsg.setTextColor(getResources().getColor(R.color.greenStrong));

        disableReplayTut();
        nextRoundTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillisNextRound = l;

                if (currentRound == TotalRounds)
                {
                    textMsgTime.setText("");
                }
                else
                {
                    textMsgTime.setText(getResources().getString(R.string.nextRound)+l/1000);
                }


            }

            @Override
            public void onFinish() {

                timeLeftInMillisNextRound = 0;

                if (currentRound == TotalRounds)
                {
                    textsLinear.setVisibility(View.INVISIBLE);
                    disableReplayTut();
                }
                else
                {
                    enableReplayTut();
                    textMsgTime.setText("");
                    textsLinear.setVisibility(View.INVISIBLE);
                    helper = 0;
                    gameInit = true;
                    loseHelper = false;
                    initializeUnpickedList();
                    createRound();
                    nextRoundTimer = null;
                }

            }
        }.start();
        userViewModel.setNextRoundTimer(nextRoundTimer);

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MATCH,imageIDS);
        outState.putIntegerArrayList(PICKED,picked);
        //outState.putIntegerArrayList(UNPICKED,unpicked);
        outState.putIntegerArrayList(IMAGEVIEWS,imageviews);
        outState.putString(CURRENTDIFFICULTY,currentDifficulty);
        outState.putInt(GAME_ID,game_id);
        outState.putInt(USER_ID,user_id);
        outState.putInt(TOTALROUNDS,TotalRounds);
        outState.putInt(COUNTER,counter);
        outState.putInt(CLICK,click);
        outState.putString(MENUDIFFICULTY,menuDifficulty);
        outState.putBoolean(PALIO,palio);
        outState.putBoolean(MISSPOINTS,missPoints);
        outState.putInt(HIT,hit);
        outState.putInt(MISS,miss);
        outState.putInt(TOTALPOINTS,totalPoints);
        outState.putInt(TRUECOUNTER,trueCounter);
        outState.putDouble(TOTALSPEED,totalspeed);
        outState.putSerializable(STARTTIME,startTime);
        outState.putSerializable(ENDTIME,endTime);
        outState.putSerializable(STARTSPEED,startspeed);
        outState.putSerializable(ENDSPEED,endspeed);
        outState.putInt(CURRENTROUND,currentRound);
        outState.putInt(HELPER,helper);
        outState.putBoolean(GAMEINIT,gameInit);
        outState.putBoolean(LOSEHELPER,loseHelper);
        outState.putLong(CLOCK,mTimeLeftInMillis);
        outState.putLong(NEXTROUND,timeLeftInMillisNextRound);
        outState.putString(MSGHELPER,msgHelper);

    }

    private void startAnimation(){
        long duration = 2000;
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(animPointsText,"y",500f,-500f);
        objectAnimatorY.setDuration(duration);

        ObjectAnimator alpha =  ObjectAnimator.ofFloat(animPointsText,View.ALPHA,1.0f,0.0f);
        alpha.setDuration(duration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorY,alpha);
        animatorSet.start();
    }

    private void assignAllButtons() {
        imagebutton1 = findViewById(R.id.imageView1OS);
        imagebutton2 = findViewById(R.id.imageView2OS);
        imagebutton3 = findViewById(R.id.imageView3OS);
        imagebutton4 = findViewById(R.id.imageView4OS);
        imagebutton5 = findViewById(R.id.imageView5OS);



        imagebutton6 = findViewById(R.id.imageView6OS);
        startButton = findViewById(R.id.startButtonOS);
        rounds = findViewById(R.id.textRoundsOS);
        animPointsText = findViewById(R.id.AnimTextPoints);
        exit = findViewById(R.id.ExitOS);
        replayTutorial = findViewById(R.id.ReplayTutorialOS);
        logoLinear = findViewById(R.id.imageLogoDisplayOS);
        textsLinear = findViewById(R.id.textsOS);
        textMsg = findViewById(R.id.msgOS);
        textMsgTime = findViewById(R.id.msgOS1);



        imagebutton1.setOnClickListener(this);
        imagebutton2.setOnClickListener(this);
        imagebutton3.setOnClickListener(this);
        imagebutton4.setOnClickListener(this);
        imagebutton5.setOnClickListener(this);
        //imagebutton6.setOnClickListener(this);

    }
    private  void fillListImageview(){
        imageviews.add(R.id.imageView1OS);
        imageviews.add(R.id.imageView2OS);
        imageviews.add(R.id.imageView3OS);
        imageviews.add(R.id.imageView4OS);
        imageviews.add(R.id.imageView5OS);
    }

    private void unclick(){
        imagebutton1.setClickable(false);
        imagebutton2.setClickable(false);
        imagebutton3.setClickable(false);
        imagebutton4.setClickable(false);
        imagebutton5.setClickable(false);
    }
    private void click(){
        imagebutton1.setClickable(true);
        imagebutton2.setClickable(true);
        imagebutton3.setClickable(true);
        imagebutton4.setClickable(true);
        imagebutton5.setClickable(true);
    }

    private void initializeUnpickedList(){
        unpicked.add(R.drawable.os_chair);
        unpicked.add(R.drawable.os_candle);
        unpicked.add(R.drawable.os_ball);
        unpicked.add(R.drawable.os_balloon);
        unpicked.add(R.drawable.os_bucket);
        unpicked.add(R.drawable.os_glass);
        unpicked.add(R.drawable.os_glasses);
        unpicked.add(R.drawable.os_hat);
        unpicked.add(R.drawable.os_keys);
        unpicked.add(R.drawable.os_pencil);
        unpicked.add(R.drawable.os_sandwitch);
        unpicked.add(R.drawable.os_saw);
        unpicked.add(R.drawable.os_umbrella);
    }
}

