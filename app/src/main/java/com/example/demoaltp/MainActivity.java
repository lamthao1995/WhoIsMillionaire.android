package com.example.demoaltp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button btn_login, btn_register;
    private EditText edt_username;
    private EditText edt_password;
    private static final String TAG="MAINACTIVITY";
    public static final String PREFERENCE_USER="DATA_SAVING_PREF";
    public static final String pass_user="user";
    public static final String pass_pass="pass";

    //private final String URL=""

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if(checkOldLogin()==true){
            Intent intent=new Intent(this,AllFeature.class);
            startActivity(intent);
        }else{
            initComponent();
        }

       // new DoLogin().execute("safasf");
    }

    public void initComponent() {
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_username = (EditText) findViewById(R.id.edt_username);

//        Bitmap bitmap= BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.help_50_50);
//        //BitmapDrawable dr=new BitmapDrawable(getApplicationContext().getResources(), bitmap);
//        //btn_register.setBackgroundDrawable(dr);
//        int width=bitmap.getWidth();
//        int height=bitmap.getHeight();
//        int newWidth=50;
//        int newHeight=25;
//        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
//        Matrix matrix = new Matrix();
//        // resize the bit map
//        matrix.postScale(scaleWidth, scaleHeight);
//        // rotate the Bitmap
//        //matrix.postRotate(0);
//
//        // recreate the new Bitmap
//        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
//                width, height, matrix, true);
//        BitmapDrawable bmd=new BitmapDrawable(resizedBitmap);
//        btn_register.setBackgroundDrawable(bmd);
    }
    public boolean checkOldLogin(){
        SharedPreferences preferences=getSharedPreferences(PREFERENCE_USER, Context.MODE_PRIVATE);
        String username=preferences.getString(pass_user, "");
        String password=preferences.getString(pass_pass,"");
        if(username.equals("")==false || password.equals("")==false){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.btn_login:
                 new DoLogin().execute();
                //Toast.makeText(getApplicationContext(),"sfasf",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_register:
                Intent myIntentToStartActivityregister=new Intent(this,RegisterActivity.class);
                startActivity(myIntentToStartActivityregister);
                break;
        }
    }

     private class DoLogin extends AsyncTask<String, String, String> {
        private String username;
        private String password;
        private String url = "http://quenhuvahung.esy.es/index.php?";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.username = new String(edt_username.getText() + "");
            this.password = new String(edt_password.getText() + "");
            this.url = this.url + "username=" + this.username + "&&password=" + this.password;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            if(s.equals("yes")==true){
                SharedPreferences preferences=getSharedPreferences(PREFERENCE_USER,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString(pass_user,this.username);
                editor.putString(pass_pass,this.password);
                editor.apply();
                Log.i(TAG,"....... COmplete put String editor....");
                Intent myIntent=new Intent(getApplicationContext(),AllFeature.class);
                startActivity(myIntent);
            }else{

            }

        }

        @Override
        protected String doInBackground(String... strings) {
            boolean check = false;
            try {
                check = checkLogin(this.username, this.password);
                //Toast.makeText(getApplicationContext(),check+" ",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(check==true){
                return "yes";
            }
            return "no";
        }

        public boolean checkLogin(String username, String password) throws IOException {
            InputStream inputStream = OpenHttpConnection(this.url);
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line ;
            StringBuffer jSonBuffer=new StringBuffer();
            while ((line=in.readLine())!=null){
                jSonBuffer.append(line);
            }
           line=jSonBuffer.toString();
            if(line.contains("3")){
                Log.i(TAG,".... ........trueeee");
                return true;

            }else{
                Log.i(TAG,".... ........false");
                return false;
            }

            // Toast.makeText(getApplication(),line,Toast.LENGTH_SHORT).show();
            //int kq = Integer.parseInt(line);


        }

        private InputStream OpenHttpConnection(String urlString)
                throws IOException {
            InputStream in = null;
            int response = -1;

            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();

            if (!(conn instanceof HttpURLConnection))
                throw new IOException("Not an HTTP connection");
            try {
                HttpURLConnection httpConn = (HttpURLConnection) conn;
                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setRequestMethod("GET");
                httpConn.connect();
                response = httpConn.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    in = httpConn.getInputStream();
                }
            } catch (Exception ex) {
                Log.d("Networking", ex.getLocalizedMessage());
                throw new IOException("Error connecting");
            }
            return in;
        }
    }
}


//Users/nhatcuong1/Downloads/DemoALTP/app/src/main/java/com/example/demoaltp