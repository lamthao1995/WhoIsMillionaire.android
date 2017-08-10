package com.example.demoaltp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by nhatcuong1 on 8/23/15.
 */
public class ListUserActivity extends Activity {
    private BaseAdapterUsers bs;
    private ListView listUsers;
    private ArrayList<User> arrUsers=new ArrayList<User>();
    private static int numbersOfUsers;
    private static final String USERNAME="username";
    private static final String SOTRANTHANG="sotranthang";
    private static final String TONGSOTRAN="tongsotran";
    private static final String DATE_JOIN="date_join";
    private static final String VCOIN="vcoin";
    private static final String TAG="ListUserActivity..";
    private TextView txt_tongsonguoichoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_userinfo);
        listUsers=(ListView)findViewById(R.id.listview_users);
        txt_tongsonguoichoi=(TextView)findViewById(R.id.txt_tongsonguoichoi);
        Toast.makeText(getApplicationContext(),"Chananananan",Toast.LENGTH_LONG).show();
        new DoGetuser().execute();
    }




  private   class DoGetuser extends AsyncTask<String,String,String>{

        String url="http://quenhuvahung.esy.es/returnuser.php";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            bs=new BaseAdapterUsers(getApplicationContext(),arrUsers);
            listUsers.setAdapter(bs);
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                //InputStream inputStream=OpenHttpConnection("");
                JSONArray jsonArrayUsers=new JSONArray(OpenHttpConnection(this.url));
                User tp;
                Log.i(TAG,"........   "+jsonArrayUsers.length());
                for(int i=0;i<jsonArrayUsers.length();i++) {
                    tp=new User();
                    JSONObject c=jsonArrayUsers.getJSONObject(i);
                    tp.setDate_join(c.getString(DATE_JOIN));
                    tp.setSotranthang(Integer.parseInt(c.getString(SOTRANTHANG)));
                    tp.setTongsotran(Integer.parseInt(c.getString(TONGSOTRAN)));
                    tp.setUsername(c.getString(USERNAME));
                    tp.setVcoin(Integer.parseInt(c.getString(VCOIN)));
                    arrUsers.add(tp);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;

        }
        private String OpenHttpConnection(String urlString)
                throws IOException {
            String result="";
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
            BufferedReader read=new BufferedReader(new InputStreamReader(in));
                //result=read.readLine();
                StringBuffer bf=new StringBuffer();
            while ((result=read.readLine())!=null){
                bf.append(result);
            }
            Log.e(TAG,"....."+bf.toString());
            return bf.toString();

        }
    }
}
