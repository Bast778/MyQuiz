package com.blot.bastien.myquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mQuestionTextView;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore;
    private int mNumberOfQuestions;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";

    private boolean mEnableTouchEvents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionBank= this.generateQuestions();

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        }else{
            mScore = 0;
            mNumberOfQuestions = 4;
        }
        mEnableTouchEvents = true;

        //Wire Widgets
        mQuestionTextView= findViewById(R.id.activity_game_question_text);
        mAnswerButton1= findViewById(R.id.activity_game_answer1_btn);
        mAnswerButton2= findViewById(R.id.activity_game_answer2_btn);
        mAnswerButton3= findViewById(R.id.activity_game_answer3_btn);
        mAnswerButton4= findViewById(R.id.activity_game_answer4_btn);

        // Use the tag property to 'name' the buttons
        mAnswerButton1.setTag(0);
        mAnswerButton2.setTag(1);
        mAnswerButton3.setTag(2);
        mAnswerButton4.setTag(3);

        mAnswerButton1.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton4.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getquestion();
        this.displayQuestion(mCurrentQuestion);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_EXTRA_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            //Good answer
            Toast.makeText(this,"Bonne réponse", Toast.LENGTH_SHORT).show();
            mScore++;

        }else{
            //Wrong answer
            Toast.makeText(this, "Mauvaise Réponse", Toast.LENGTH_SHORT).show();
        }
        mEnableTouchEvents = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents =true;

                // If this is the last question, ends the game.
                // Else, display the next question.
                if (--mNumberOfQuestions == 0) {
                    //End of the game
                    endGame();
                }else{
                    mCurrentQuestion = mQuestionBank.getquestion();
                    displayQuestion(mCurrentQuestion);
                }
            }
        }, 2000); // LENGTH_SHORT is usually 2 second long

        }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Bien joué !")
                .setMessage("Votre score est de " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // End the activity
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    private void displayQuestion(final Question question ) {
        mQuestionTextView.setText(question.getQuestion());
        mAnswerButton1.setText(question.getChoiceList().get(0));
        mAnswerButton2.setText(question.getChoiceList().get(1));
        mAnswerButton3.setText(question.getChoiceList().get(2));
        mAnswerButton4.setText(question.getChoiceList().get(3));
    }
    private QuestionBank generateQuestions() {
        Question question1 = new Question("Quelle est la plus grande pyramide d’Egypte ?",
                Arrays.asList("La pyramide de Khéops" , "La pyramide de Djéser" , "La pyramide de Khéphren" , "La pyramide du Louvre"),
                0);

        Question question2 = new Question("Selon la mythologie égyptienne, quelle divinité règne sur le royaume des morts ?",
                Arrays.asList("Mâat" , "Anubis" , "Osiris" , "Toutatis"),
                2);

        Question question3 = new Question("Quelle fibre était utilisée pour confectionner les bandelettes servant à la momification ?",
                Arrays.asList("Le coton" , "Le lin" , "Le papyrus", "Le papier"),
                1);

        Question question4 = new Question("Quel célèbre égyptologue est parvenu à déchiffrer les hiéroglyphes ?",
                Arrays.asList("Jean-François Champollion" , "Auguste Mariette" , "Howard Carter" , "Ramses"),
                0);

        Question question5 = new Question("Quel site, menacé d’être submergé, a été démonté au XXe siècle puis reconstruit sur une zone protégée ?",
                Arrays.asList("Edfou (le temple d'Horus)" , "Le complexe religieux de Karnak", "Abou Simbel (les temples de Ramsès II)" , "L'Atlantide"),
                2);

        Question question6 = new Question("A quel âge le pharaon Toutânkhamon est-il mort ?",
                Arrays.asList("8 ans", "19 ans", "32 ans", "100 ans"),
                1);

        Question question7 = new Question("De quel temple provient l'obélisque qui orne la place de la Condorde, à Paris, depuis 1836 ?",
                Arrays.asList("Du temple d'Amon à Louxor", "Du temple d'Isis à Philæ", "Du temple d'Osiris à Karnak", "Du temple d'Angkor"),
                0);

        Question question8 = new Question("Quel pharaon a été surnommé le Bâtisseur ?",
                Arrays.asList("Akhenaton", "Ramsès I", "Ramsès II", "Ramsès III"),
                2);

        Question question9 = new Question("Quel fleuve traverse l'Égypte ?",
                Arrays.asList("Le Kibo", "Le Danube", "Le Nil", "La Seine"),
                2);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9));


    }
}

