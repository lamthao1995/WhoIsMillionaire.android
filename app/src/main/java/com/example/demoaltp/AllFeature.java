package com.example.demoaltp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by nhatcuong1 on 8/22/15.
 */
public class AllFeature extends Activity implements View.OnClickListener{
    private Button btn_start,btn_userinfo,btn_exit,btn_gamerstastistic;
    private static final String TAG="AllFeature";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initComponents();
    }
    public void initComponents(){
        btn_exit=(Button)findViewById(R.id.btn_exit);
        btn_gamerstastistic=(Button)findViewById(R.id.btn_gamerstatistic_allfeature);
        btn_start=(Button)findViewById(R.id.btn_startgame_allfeature);
        btn_userinfo=(Button)findViewById(R.id.btn_userinfo_allfeature);
        btn_userinfo.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_gamerstastistic.setOnClickListener(this);
        //btn_gamerstastistic.set
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            default:break;
            case R.id.btn_gamerstatistic_allfeature:
                Intent myIntent1=new Intent(this,ListUserActivity.class);
                startActivity(myIntent1);
                break;
            case R.id.btn_startgame_allfeature:
                Intent myIntent=new Intent(this,GameActivity.class);
                startActivity(myIntent);
                break;
            case R.id.btn_exit:
                SharedPreferences preferences=getSharedPreferences(MainActivity.PREFERENCE_USER, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.remove(MainActivity.pass_pass);
                editor.remove(MainActivity.pass_user);
                editor.apply();
                Log.i(TAG, ".......remove all preferencess....");
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.btn_userinfo_allfeature:

                break;

        }

    }
}
