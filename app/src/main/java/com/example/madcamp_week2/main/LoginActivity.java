package com.example.madcamp_week2.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madcamp_week2.R;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    String Nickname;
    String Gender;
    String ID;
    Button Login;
    Button MoveSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login = (Button) findViewById(R.id.login_button);
        MoveSignUp = (Button) findViewById(R.id.sign_up_button);

        Login.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  RequestThread thread = new RequestThread();
                  thread.start();
                  }
              }
        );

        MoveSignUp.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                  startActivity(intent);
                }
              }
        );

    }

    class RequestThread extends Thread {

        EditText id;
        EditText password;

        @Override
        public void run() {
            try {
                id = (EditText) findViewById(R.id.id_edit);
                password = (EditText) findViewById(R.id.password_edit);

                try {
                    //JSONObject??? ????????? key value ???????????? ?????? ???????????????.
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("id", id.getText().toString());
                    jsonObject.accumulate("password", password.getText().toString());

                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try {
                        URL url = new URL("http://13.125.182.78:5000/api/user1"); //????????? ???
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");//POST???????????? ??????
                        con.setRequestProperty("Cache-Control", "no-cache");//?????? ??????
                        con.setRequestProperty("Content-Type", "application/json");//application JSON ???????????? ??????

                        con.setRequestProperty("Accept", "application/json");
                        con.setDoOutput(true);//Outstream?????? post ???????????? ?????????????????? ??????

                        con.setDoInput(true);//Inputstream?????? ??????????????? ????????? ???????????? ??????

                        con.connect(); //????????? ?????????????????? ????????? ??????

                        OutputStream outStream = con.getOutputStream(); //????????? ???????????? ??????

                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));

                        writer.write(jsonObject.toString());
                        writer.flush();
                        writer.close();//????????? ?????????

                        //????????? ?????? ???????????? ??????
                        InputStream stream = con.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(stream));
                        StringBuffer buffer = new StringBuffer();

                        String line = "";

                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            buffer.append(line);
                        }

                        String reqData = buffer.toString();
                        JSONTokener tokener = new JSONTokener(reqData);

                        JSONObject object = (JSONObject) tokener.nextValue();
                        String loginSuccess = object.getString("loginSuccess");
                        String message = object.getString("message");

                        if(loginSuccess=="true"){
                            Nickname = object.getString("nickname");
                            Gender = object.getString("gender");
                            ID = object.getString("id");

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("nickname",Nickname);
                            intent.putExtra("gender",Gender);
                            intent.putExtra("id",ID);
                            startActivity(intent);
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run()
                                {
                                    Toast.makeText(getApplicationContext(),"???????????????.",Toast.LENGTH_SHORT).show();
                                }
                            }, 0);
                        }

                        else if(loginSuccess=="false"){
                            System.out.println("??????");
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run()
                                {
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                                }
                            }, 0);
                        }

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (con != null) {
                            con.disconnect();
                        }
                        try {
                            if (reader != null) {
                                reader.close();//????????? ?????????
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}