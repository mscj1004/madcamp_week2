package com.example.madcamp_week2.style;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madcamp_week2.R;
import com.example.madcamp_week2.main.MainActivity;

import org.json.JSONException;
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
import java.net.ProtocolException;
import java.net.URL;

public class StyleActivity extends AppCompatActivity {

    String gender;
    String nickname;
    String id;
    Button add_btn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style);

        Intent intent = getIntent();
        gender = intent.getStringExtra("gender");
        nickname = intent.getStringExtra("nickname");
        id = intent.getStringExtra("id");

        TextView style_text = findViewById(R.id.style_text);
        style_text.setText(nickname + "님의 코디를 골라보세요");

        //gender 0
        LinearLayout top_m = findViewById(R.id.top_m);
        LinearLayout bottom_m = findViewById(R.id.bottom_m);
        LinearLayout acc_m = findViewById(R.id.accessory_m);
        LinearLayout outer_m =findViewById(R.id.outer_m);

        //gender 1
        LinearLayout top_w = findViewById(R.id.top_w);
        LinearLayout bottom_w = findViewById(R.id.bottom_w);
        LinearLayout acc_w = findViewById(R.id.accessory_w);
        LinearLayout outer_w =findViewById(R.id.outer_w);

        Log.d("test", gender);

        add_btn = findViewById(R.id.add_btn);

        if(gender.equals("true")){
            top_m.setVisibility(View.INVISIBLE);
            bottom_m.setVisibility(View.INVISIBLE);
            acc_m.setVisibility(View.INVISIBLE);
            outer_m.setVisibility(View.INVISIBLE);

            top_w.setVisibility(View.VISIBLE);
            bottom_w.setVisibility(View.VISIBLE);
            acc_w.setVisibility(View.VISIBLE);
            outer_w.setVisibility(View.VISIBLE);

            add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addWMThread thread = new addWMThread();
                    thread.start();
                }
            });
        }
        else{
            add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addManThread thread = new addManThread();
                    thread.start();
                }
            });


        }

    }

    class addManThread extends Thread {

        CheckBox checkBox;
        Spinner ttm;
        Spinner tcm;
        Spinner btm;
        Spinner bcm;
        Spinner atm;
        Spinner acm;
        Spinner otm;
        Spinner ocm;


        @Override
        public void run(){
            try{
                checkBox = findViewById(R.id.style_cb);
                ttm = findViewById(R.id.sp_top_type_m);
                tcm = findViewById(R.id.sp_top_color_m);
                btm = findViewById(R.id.sp_bottom_type_m);
                bcm = findViewById(R.id.sp_bottom_color_m);
                atm = findViewById(R.id.sp_accessory_type_m);
                acm = findViewById(R.id.sp_accessory_color_m);
                otm = findViewById(R.id.sp_outer_type_m);
                ocm = findViewById(R.id.sp_outer_color_m);



                try{
                    JSONObject jsonobject= new JSONObject();
                    jsonobject.accumulate("userid", id);
                    jsonobject.accumulate("gender", false);
                    jsonobject.accumulate("public", checkBox.isChecked());
                    jsonobject.accumulate("top", ttm.getSelectedItem());
                    jsonobject.accumulate("top_color", tcm.getSelectedItem());
                    jsonobject.accumulate("bottom", btm.getSelectedItem());
                    jsonobject.accumulate("bottom_color", bcm.getSelectedItem());
                    jsonobject.accumulate("accessory", atm.getSelectedItem());
                    jsonobject.accumulate("accessory_color", acm.getSelectedItem());
                    jsonobject.accumulate("outer", otm.getSelectedItem());
                    jsonobject.accumulate("outer_color", ocm.getSelectedItem());

                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try{
                        URL url = new URL("http://13.125.182.78:5000/api/add");
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setRequestProperty("Cache-Control", "no-cache");
                        con.setRequestProperty("Content-Type", "application/json");

                        con.setRequestProperty("Accept", "application/json");
                        con.setDoOutput(true);

                        con.setDoInput(true);
                        con.connect();

                        OutputStream outStream = con.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));

                        writer.write(jsonobject.toString());
                        writer.flush();
                        writer.close();

                        //서버로 부터 데이터륿 받음
                        InputStream stream = con.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(stream));
                        StringBuffer buffer = new StringBuffer();

                        String line = "";

                        while ((line = reader.readLine())!= null){
                            System.out.println(line);
                            buffer.append(line);
                        }


                        String reqData = buffer.toString();
                        JSONTokener tokener = new JSONTokener(reqData);

                        JSONObject object = (JSONObject) tokener.nextValue();
                        String addSuccess = object.getString("addSuccess");
                        String message = object.getString("message");

                        if(addSuccess == "true"){
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("nickname",nickname);
                                    intent.putExtra("gender",gender);
                                    intent.putExtra("id",id);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                                }
                            },0);
                        }
                        else if(addSuccess == "false"){
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                                }
                            }, 0);
                        }

                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class addWMThread extends Thread {

        CheckBox checkBox;
        Spinner ttw;
        Spinner tcw;
        Spinner btw;
        Spinner bcw;
        Spinner atw;
        Spinner acw;
        Spinner otw;
        Spinner ocw;


        @Override
        public void run(){
            try{
                checkBox = findViewById(R.id.style_cb);
                ttw = findViewById(R.id.sp_top_type_w);
                tcw = findViewById(R.id.sp_top_color_w);
                btw = findViewById(R.id.sp_bottom_type_w);
                bcw = findViewById(R.id.sp_bottom_color_w);
                atw = findViewById(R.id.sp_accessory_type_w);
                acw = findViewById(R.id.sp_accessory_color_w);
                otw = findViewById(R.id.sp_outer_type_w);
                ocw = findViewById(R.id.sp_outer_color_w);


                try{
                    JSONObject jsonobject= new JSONObject();
                    jsonobject.accumulate("userid", id);
                    jsonobject.accumulate("gender", true);
                    jsonobject.accumulate("public", checkBox.isChecked());
                    jsonobject.accumulate("top", ttw.getSelectedItem());
                    jsonobject.accumulate("top_color", tcw.getSelectedItem());
                    jsonobject.accumulate("bottom", btw.getSelectedItem());
                    jsonobject.accumulate("bottom_color", bcw.getSelectedItem());
                    jsonobject.accumulate("accessory", atw.getSelectedItem());
                    jsonobject.accumulate("accessory_color", acw.getSelectedItem());
                    jsonobject.accumulate("outer", otw.getSelectedItem());
                    jsonobject.accumulate("outer_color", ocw.getSelectedItem());

                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try{
                        URL url = new URL("http://13.125.182.78:5000/api/add");
                        Log.d("test1", "success");
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setRequestProperty("Cache-Control", "no-cache");
                        con.setRequestProperty("Content-Type", "application/json");

                        con.setRequestProperty("Accept", "application/json");
                        con.setDoOutput(true);

                        con.setDoInput(true);
                        con.connect();

                        OutputStream outStream = con.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));

                        writer.write(jsonobject.toString());
                        writer.flush();
                        writer.close();

                        //서버로 부터 데이터륿 받음
                        InputStream stream = con.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(stream));
                        StringBuffer buffer = new StringBuffer();

                        String line = "";

                        while ((line = reader.readLine())!= null){
                            System.out.println(line);
                            buffer.append(line);
                        }


                        String reqData = buffer.toString();
                        JSONTokener tokener = new JSONTokener(reqData);

                        JSONObject object = (JSONObject) tokener.nextValue();
                        String addSuccess = object.getString("addSuccess");
                        String message = object.getString("message");

                        if(addSuccess == "true"){
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("nickname",nickname);
                                    intent.putExtra("gender",gender);
                                    intent.putExtra("id",id);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                                }
                            },0);
                        }
                        else if(addSuccess == "false"){
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                                }
                            }, 0);
                        }

                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
