package com.example.madcamp_week2.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.madcamp_week2.R;

import org.json.JSONArray;
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
import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity {

    String tmp_raw;
    String Gender;
    Boolean Real_Gender;
    String Snow;
    String Rain;

    String Top;
    String Bottom;
    String Top_color;
    String Bottom_color;
    String Accessory;
    String Accessory_color;
    String Outer;
    String Outer_color;

    private RecyclerView rvFashion;
    ArrayList<Fashion> fashion_list = new ArrayList<Fashion>();

    RecFashionAdapter adapter = new RecFashionAdapter();

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
                        JSONArray fashionArray = new JSONArray(tokener);
                        for (int i=0; i<fashionArray.length(); i++){
                            Top = fashionArray.getJSONObject(i).getString("top");
                            Top_color = fashionArray.getJSONObject(i).getString("top_color");
                            Bottom = fashionArray.getJSONObject(i).getString("bottom");

                            if(Bottom.equals("없음")){
                                Bottom="";
                            }
                            Bottom_color = fashionArray.getJSONObject(i).getString("bottom_color");
                            if(Bottom_color.equals("없음")){
                                Bottom_color="";
                            }
                            Accessory = fashionArray.getJSONObject(i).getString("accessory");
                            if(Accessory.equals("없음")){
                                Accessory="";
                            }
                            Accessory_color = fashionArray.getJSONObject(i).getString("accessory_color");
                            if(Accessory_color.equals("없음")){
                                Accessory_color="";
                            }
                            Outer = fashionArray.getJSONObject(i).getString("outer");
                            if(Outer.equals("없음")){
                                Outer="";
                            }
                            Outer_color = fashionArray.getJSONObject(i).getString("outer_color");
                            if(Outer_color.equals("없음")){
                                Outer_color="";
                            }
                            fashion_list.add(new Fashion(Top, Top_color, Bottom, Bottom_color, Accessory, Accessory_color, Outer, Outer_color));
                            adapter.addItem(new Fashion(Top, Top_color, Bottom, Bottom_color, Accessory, Accessory_color, Outer, Outer_color));
                            adapter.notifyDataSetChanged();
                            adapter.setOnItemClickListener(new RecFashionAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {

                                    Intent intent = new Intent(getApplicationContext(), Detail_Activity.class);
                                    if(!Bottom_color.equals("")){
                                        intent.putExtra("Bottom",fashion_list.get(position).getBottom_color().concat(fashion_list.get(position).getBottom()));
                                    }
                                    if(!Top_color.equals("")){
                                        intent.putExtra("Top",fashion_list.get(position).getTop_color().concat(fashion_list.get(position).getTop()));
                                    }
                                    if(!Accessory_color.equals("")){
                                        intent.putExtra("Accessory",fashion_list.get(position).getAccessory_color().concat(fashion_list.get(position).getAccessory()));
                                    }
                                    if(!Outer_color.equals("")){
                                        intent.putExtra("Outer",fashion_list.get(position).getOuter().concat(fashion_list.get(position).getOuter()));
                                    }
                                    startActivity(intent);
                                }
                            });
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
                                reader.close();//버퍼를 닫아줌
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rvFashion = findViewById(R.id.recycler_view);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
                                rvFashion.setLayoutManager(layoutManager);
                                rvFashion.setAdapter(adapter);
                            }
                        });
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