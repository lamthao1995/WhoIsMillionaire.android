package com.example.demoaltp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by nhatcuong1 on 8/22/15.
 */
public class GameActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "ALLFEATURE";
    private Button btn_A, btn_B, btn_C, btn_D, btn_5050_help, btn_call_help, btn_answer_spectacular;
    private TextView txt_question;
    private ArrayList<Question> arrQuestionLevel1 = new ArrayList<Question>();
    private ArrayList<Question> arrQuestionLevel2 = new ArrayList<Question>();
    private ArrayList<Question> arrQuestionLevel3 = new ArrayList<Question>();
    private DatabaseManager DBMgr;
    Question questionAsk = new Question();
    private int position = 1;
    private String sqlUser = "Select * from User";
    private String sqlQuestion = "Select * from (SELECT * FROM Question WHERE level=1 ORDER BY Random() LIMIT 6)"
            + " UNION Select * from (SELECT * FROM Question WHERE level=2 ORDER BY Random() LIMIT 6)"
            + " UNION Select * from (SELECT * FROM Question WHERE level=3 ORDER BY Random() LIMIT 6)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamestart_activity);
        initComponents();
        DBMgr = new DatabaseManager(this);
        getQuestion();
        this.questionAsk=randomResultofQuestion(arrQuestionLevel1.get(position));
        showQuestion();

    }

    public void initComponents() {
        btn_5050_help = (Button) findViewById(R.id.btn_50_50);
        btn_5050_help.setOnClickListener(this);
        btn_answer_spectacular = (Button) findViewById(R.id.btn_answer_spectacular);
        btn_answer_spectacular.setOnClickListener(this);
        btn_call_help = (Button) findViewById(R.id.btn_call);
        btn_call_help.setOnClickListener(this);
        btn_A = (Button) findViewById(R.id.btn_A);
        btn_A.setOnClickListener(this);
        btn_B = (Button) findViewById(R.id.btn_B);
        btn_B.setOnClickListener(this);
        btn_C = (Button) findViewById(R.id.btn_C);
        btn_C.setOnClickListener(this);
        btn_D = (Button) findViewById(R.id.btn_D);
        btn_D.setOnClickListener(this);

        txt_question = (TextView) findViewById(R.id.txt_question);

    }

    ///Get question from sqlite
    public void getQuestion() {
        ArrayList<Question> arrQ = DBMgr.getQuestion(sqlQuestion);

        if (arrQ == null) {

            Toast.makeText(this,
                    "Database is null, please insert value first!",
                    Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this,
                    "Database is not null",
                    Toast.LENGTH_SHORT).show();
            for (int i = 0; i < arrQ.size(); i++) {
                Question question = arrQ.get(i);
                Log.i(TAG, "Question: " + question.getQuestionID());
                Log.i(TAG, "A: " + question.getCaseA());
                Log.i(TAG, "B: " + question.getCaseB());
                Log.i(TAG, "C: " + question.getCaseC());
                Log.i(TAG, "D: " + question.getCaseD());
                Log.i(TAG, "Level: " + question.getLevel());
                if (question.getLevel().equals("1")) {
                    arrQuestionLevel1.add(question);
                } else if (question.getLevel().equals("2")) {
                    arrQuestionLevel2.add(question);
                } else {
                    arrQuestionLevel3.add(question);
                }
            }
        }
    }

    public void questionToAsk() {
        if (position <= 5) {
            this.questionAsk = arrQuestionLevel1.get(position);
        } else if (position > 5 && position <= 10) {
            this.questionAsk = arrQuestionLevel2.get(position - 5);
        } else {
            this.questionAsk = arrQuestionLevel3.get(position - 10);
        }
    }

    public Question randomResultofQuestion(Question question) {
        Question tpQuestion = new Question();
        tpQuestion.setQuestionID(question.getQuestionID());
        Random rd = new Random();
        int resultInt = rd.nextInt(15);
        if (resultInt <= 4) {
            tpQuestion = question;
            tpQuestion.setResult("A");
        } else if (resultInt > 4 && resultInt <= 8) {
            tpQuestion.setCaseA(question.getCaseB());
            tpQuestion.setCaseB(question.getCaseA());
            tpQuestion.setCaseC(question.getCaseC());
            tpQuestion.setCaseD(question.getCaseD());
            tpQuestion.setResult("B");
        } else if (resultInt > 8 && resultInt <= 12) {
            tpQuestion.setCaseA(question.getCaseC());
            tpQuestion.setCaseB(question.getCaseB());
            tpQuestion.setCaseC(question.getCaseA());
            tpQuestion.setCaseD(question.getCaseD());
            tpQuestion.setResult("C");
        } else {
            tpQuestion.setCaseA(question.getCaseD());
            tpQuestion.setCaseB(question.getCaseB());
            tpQuestion.setCaseC(question.getCaseC());
            tpQuestion.setCaseD(question.getCaseA());
            tpQuestion.setResult("D");
        }
        return tpQuestion;
    }

    public boolean checkAnswer(String result) {
        if (this.questionAsk.getResult().equals(result) == true) {
            position++;
            return true;
        } else {
            return false;
        }
    }
    public void showQuestion(){
        btn_A.setText(this.questionAsk.getCaseA()+"");
        btn_B.setText(this.questionAsk.getCaseB()+"");
        btn_C.setText(this.questionAsk.getCaseC()+"");
        btn_D.setText(this.questionAsk.getCaseD()+"");
        txt_question.setText(this.questionAsk.getQuestionID());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.btn_A:
                if(checkAnswer("A")==true){
                    questionToAsk();
                    this.questionAsk=randomResultofQuestion(this.questionAsk);
                    showQuestion();

                }else{
                    Toast.makeText(getApplicationContext(),"YOU LOSE",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_B:
                if(checkAnswer("B")==true){
                    questionToAsk();
                    this.questionAsk=randomResultofQuestion(this.questionAsk);
                    showQuestion();

                }else{
                    Toast.makeText(getApplicationContext(),"YOU LOSE",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_C:
                if(checkAnswer("C")==true){
                    questionToAsk();
                    this.questionAsk=randomResultofQuestion(this.questionAsk);
                    showQuestion();

                }else{
                    Toast.makeText(getApplicationContext(),"YOU LOSE",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_D:
                if(checkAnswer("D")==true){
                    questionToAsk();
                    this.questionAsk=randomResultofQuestion(this.questionAsk);
                    showQuestion();

                }else{
                    Toast.makeText(getApplicationContext(),"YOU LOSE",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_call:
                break;
            case R.id.btn_50_50:
                break;
            case R.id.btn_answer_spectacular:
                break;
        }
    }
}
