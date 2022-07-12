package com.example.madcamp_week2.share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.madcamp_week2.R;
import com.example.madcamp_week2.weather.Detail_Activity;
import com.example.madcamp_week2.weather.Fashion;
import com.example.madcamp_week2.weather.RecFashionAdapter;
import com.example.madcamp_week2.weather.Shopping;
import com.example.madcamp_week2.weather.ShoppingAdapter;

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

public class SharedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);

        RequestThread thread = new RequestThread();
        thread.start();
    }

    class RequestThread extends Thread {

        private RecyclerView rv;
        ShareAdapter adapter = new ShareAdapter();

        ArrayList<Share> share_list = new ArrayList<Share>();
        String nickname;
        String top;
        String top_color;
        String bottom;
        String bottom_color;
        String accessory;
        String accessory_color;
        String outer;
        String outer_color;

        @Override
        public void run() {
            try {
                try {
                    //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try {
                        URL url = new URL("http://13.125.182.78:5000/api/public"); //연결을 함
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
                            buffer.append(line);
                        }

                        String reqData = buffer.toString();
                        JSONTokener tokener = new JSONTokener(reqData);
                        JSONObject shopping_tokener = (JSONObject) tokener.nextValue();
                        System.out.println(shopping_tokener);
                        System.out.println(shopping_tokener.getJSONArray("data"));
                        JSONArray sharedArray = shopping_tokener.getJSONArray("data");

                        for (int i=0; i<sharedArray.length(); i++){

                            nickname = sharedArray.getJSONObject(i).getString("nickname");
                            top = sharedArray.getJSONObject(i).getString("top");
                            top_color = sharedArray.getJSONObject(i).getString("top_color");
                            bottom = sharedArray.getJSONObject(i).getString("bottom");
                            bottom_color = sharedArray.getJSONObject(i).getString("bottom_color");
                            accessory = sharedArray.getJSONObject(i).getString("accessory");
                            accessory_color = sharedArray.getJSONObject(i).getString("accessory_color");
                            outer = sharedArray.getJSONObject(i).getString("outer");
                            outer_color = sharedArray.getJSONObject(i).getString("outer_color");


                            share_list.add(new Share(nickname, top, top_color, bottom, bottom_color, accessory, accessory_color, outer, outer_color));
                            adapter.addItem(new Share(nickname, top, top_color, bottom, bottom_color, accessory, accessory_color, outer, outer_color));
                            adapter.notifyDataSetChanged();
                            adapter.setOnItemClickListener(new ShareAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    Intent intent = new Intent(getApplicationContext(), SharedDetailActivity.class);
                                    intent.putExtra("nickname",share_list.get(position).getNickname());
                                    intent.putExtra("top", share_list.get(position).getTop());
                                    intent.putExtra("top_color",share_list.get(position).getTop_color());
                                    intent.putExtra("bottom", share_list.get(position).getBottom());
                                    intent.putExtra("bottom_color",share_list.get(position).getBottom_color());
                                    intent.putExtra("accessory",share_list.get(position).getAccessory());
                                    intent.putExtra("accessory_color",share_list.get(position).getAccessory_color());
                                    intent.putExtra("outer",share_list.get(position).getOuter());
                                    intent.putExtra("outer_color",share_list.get(position).getOuter_color());
                                    startActivity(intent);
                                }
                            });
                        }

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              rv = findViewById(R.id.recycler_view);
                                              RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                              rv.setLayoutManager(layoutManager);
                                              rv.setAdapter(adapter);
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
        }
    }
}