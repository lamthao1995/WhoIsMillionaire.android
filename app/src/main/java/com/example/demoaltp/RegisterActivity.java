package com.example.demoaltp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by nhatcuong1 on 8/22/15.
 */
public class RegisterActivity extends Activity implements View.OnClickListener{
    private static final String TAG="RegisterActivity";
    private Button btn_agree;
    private EditText edt_username_register;
    private EditText edt_password_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initComponents();

    }
    public void initComponents(){
        btn_agree=(Button)findViewById(R.id.btn_agree);
        btn_agree.setOnClickListener(this);
        edt_password_register=(EditText)findViewById(R.id.edt_password_register);
        edt_username_register=(EditText)findViewById(R.id.edt_username_register);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            default:break;
            case R.id.btn_agree:
                Log.i(TAG,"Click agreee");
                new DoRegister().execute();

                break;
        }
    }
    private class DoRegister extends AsyncTask<String,String,String>{
        private String username;
        private String password;
        private String url;
        boolean check=false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.username=edt_username_register.getText().toString();
            this.password=edt_password_register.getText().toString();
            this.url="http://quenhuvahung.esy.es/register.php?username="+username+"&&password="+password;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(check==true){
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"Error to create user",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                check=checkRegister();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        public boolean checkRegister() throws IOException {
            InputStream inputStream=OpenHttpConnection(this.url);
            BufferedReader read=new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuffer bf=new StringBuffer();
            while ((line=read.readLine())!=null){
                bf.append(line);
            }
            line=bf.toString();
            Log.i(TAG,"......"+line);
            if(line.contains("complete")) return true;

            return false;
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
