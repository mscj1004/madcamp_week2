package com.example.madcamp_week2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class WeatherActivity extends AppCompatActivity {

    String tmp_raw;
    String Gender;
    Boolean Real_Gender;
    String Snow;
    String Rain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Intent intent = getIntent();
        Gender=intent.getStringExtra("gender");

        RequestThread thread = new RequestThread();
        thread.start();

    }

    class RequestThread extends Thread {

        TextView degree;
        TextView rain;
        TextView snow;

        @Override
        public void run() {
            try {
                try {
                    //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try {
                        URL url = new URL("http://13.125.182.78:5000/api/test"); //연결을 함
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                        con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송

                        con.setRequestProperty("Accept", "application/json");
                        con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미

                        //서버로 부터 데이터를 받음
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
                        tmp_raw = object.getString("tmp");
                        String tmp = tmp_raw.concat("도");
                        Snow = object.getString("snow");
                        Rain = object.getString("rain");

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              degree = (TextView)findViewById(R.id.degree);
                                              rain = (TextView)findViewById(R.id.rain);
                                              snow = (TextView)findViewById(R.id.snow);

                                              degree.setText(tmp);
                                              rain.setText(Rain);
                                              snow.setText(Snow);
                                          }
                                      }
                        );

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
                                reader.close();//버퍼를 닫아줌
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
            try {
                try {
                    if(Gender.equals("true")){
                        Real_Gender=true;
                    }
                    else if(Gender.equals("false")){
                        Real_Gender=false;
                    }

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("temp", tmp_raw);

                    jsonObject.accumulate("gender",Real_Gender);
                    jsonObject.accumulate("snow",Snow);
                    jsonObject.accumulate("rain",Rain);

                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try {
                        URL url = new URL("http://13.125.182.78:5000/api/rec"); //연결을 함
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");//POST방식으로 보냄
                        con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                        con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송

                        con.setRequestProperty("Accept", "application/json");
                        con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                        con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미

                        con.connect(); //서버로 보내기위해서 스트림 만듬

                        OutputStream outStream = con.getOutputStream(); //버퍼를 생성하고 넣음

                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));

                        writer.write(jsonObject.toString());
                        writer.flush();
                        writer.close();//버퍼를 받아줌

                        //서버로 부터 데이터를 받음
                        InputStream stream = con.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(stream));
                        StringBuffer buffer = new StringBuffer();

                        String line = "";

                        while ((line = reader.readLine()) != null) {
                            buffer.append(line);
                        }

                        String reqData = buffer.toString();
                        JSONTokener tokener = new JSONTokener(reqData);


                        System.out.println(reqData);
                        //JSONObject object = (JSONObject) tokener.nextValue();

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
                                reader.close();//버퍼를 닫아줌
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
    class RecommendThread extends Thread {

        @Override
        public void run() {
            try {

                try {
                    //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("temp", tmp_raw);
                    jsonObject.accumulate("gender",Gender);
                    jsonObject.accumulate("snow",Snow);
                    jsonObject.accumulate("rain",Rain);

                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try {
                        URL url = new URL("http://13.125.182.78:5000/api/user"); //연결을 함
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");//POST방식으로 보냄
                        con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                        con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송

                        con.setRequestProperty("Accept", "application/json");
                        con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                        con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미

                        con.connect(); //서버로 보내기위해서 스트림 만듬

                        OutputStream outStream = con.getOutputStream(); //버퍼를 생성하고 넣음

                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));

                        writer.write(jsonObject.toString());
                        writer.flush();
                        writer.close();//버퍼를 받아줌

                        //서버로 부터 데이터를 받음
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
                        System.out.println("????");
                        System.out.println(reqData);
                        //JSONObject object = (JSONObject) tokener.nextValue();



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
                                reader.close();//버퍼를 닫아줌
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