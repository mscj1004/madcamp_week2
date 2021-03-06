package com.example.madcamp_week2.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.madcamp_week2.R;
import com.example.madcamp_week2.weather.Shopping;
import com.example.madcamp_week2.weather.ShoppingAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Detail_Activity extends AppCompatActivity {

    TextView Top_Text;
    TextView Bottom_Text;
    TextView Outer_Text;
    TextView Accessory_Text;

    String Top;
    String Bottom;
    String Outer;
    String Accessory;

    ArrayList<Shopping> shopping_list = new ArrayList<Shopping>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Top = getIntent().getStringExtra("Top");
        if(Top!=null){
            Top_Text=(TextView)findViewById(R.id.top);
            Top_Text.setText(Top);
            TopThread thread = new TopThread();
            thread.start();
        }
        Bottom = getIntent().getStringExtra("Bottom");
        if(Bottom!=null){
            Bottom_Text=(TextView)findViewById(R.id.bottom);
            Bottom_Text.setText(Bottom);
            BottomThread thread = new BottomThread();
            thread.start();
        }
        Outer = getIntent().getStringExtra("Outer");
        if(Outer!=null){
            Outer_Text=(TextView)findViewById(R.id.outer);
            Outer_Text.setText(Outer);
            OuterThread thread = new OuterThread();
            thread.start();
        }
        Accessory = getIntent().getStringExtra("Accessory");
        if(Accessory!=null){
            Accessory_Text=(TextView)findViewById(R.id.accessory);
            Accessory_Text.setText(Accessory);
            AccessoryThread thread = new AccessoryThread();
            thread.start();
        }
    }


    class TopThread extends Thread {

        String title;
        String price;
        String link;
        private RecyclerView rvTop;

        ShoppingAdapter adapter = new ShoppingAdapter();

        @Override
        public void run() {
            try {
                try {
                    //JSONObject??? ????????? key value ???????????? ?????? ???????????????.
                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try {
                        URL url = new URL("http://13.125.182.78:5000/api/shopping/"+Top); //????????? ???
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        con.setRequestProperty("Cache-Control", "no-cache");//?????? ??????
                        con.setRequestProperty("Content-Type", "application/json");//application JSON ???????????? ??????

                        con.setRequestProperty("Accept", "application/json");
                        con.setDoInput(true);//Inputstream?????? ??????????????? ????????? ???????????? ??????

                        //????????? ?????? ???????????? ??????
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
                        System.out.println(shopping_tokener.getJSONArray("data"));
                        JSONArray shoppingArray = shopping_tokener.getJSONArray("data");

                        for (int i=0; i<shoppingArray.length(); i++){
                            title = shoppingArray.getJSONObject(i).getString("title");
                            price = shoppingArray.getJSONObject(i).getString("price");
                            link = shoppingArray.getJSONObject(i).getString("url");
                            shopping_list.add(new Shopping(title,price,link));
                            adapter.addItem(new Shopping(title,price,link));
                            adapter.notifyDataSetChanged();
                            adapter.setOnItemClickListener(new ShoppingAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(shopping_list.get(position).getLink()));
                                    startActivity(intent);
                                }
                            });
                        }

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              rvTop = findViewById(R.id.top_recycler_view);
                                              RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
                                              rvTop.setLayoutManager(layoutManager);
                                              rvTop.setAdapter(adapter);
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
    class BottomThread extends Thread {

        String title;
        String price;
        String link;
        private RecyclerView rvBottom;

        ShoppingAdapter adapter = new ShoppingAdapter();

        @Override
        public void run() {
            try {
                try {
                    //JSONObject??? ????????? key value ???????????? ?????? ???????????????.
                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try {
                        URL url = new URL("http://13.125.182.78:5000/api/shopping/"+Bottom); //????????? ???
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        con.setRequestProperty("Cache-Control", "no-cache");//?????? ??????
                        con.setRequestProperty("Content-Type", "application/json");//application JSON ???????????? ??????

                        con.setRequestProperty("Accept", "application/json");
                        con.setDoInput(true);//Inputstream?????? ??????????????? ????????? ???????????? ??????

                        //????????? ?????? ???????????? ??????
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
                        System.out.println(shopping_tokener.getJSONArray("data"));
                        JSONArray shoppingArray = shopping_tokener.getJSONArray("data");

                        for (int i=0; i<shoppingArray.length(); i++){
                            title = shoppingArray.getJSONObject(i).getString("title");
                            price = shoppingArray.getJSONObject(i).getString("price");
                            link = shoppingArray.getJSONObject(i).getString("url");
                            adapter.addItem(new Shopping(title,price,link));
                            adapter.notifyDataSetChanged();
                            adapter.setOnItemClickListener(new ShoppingAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(shopping_list.get(position).getLink()));
                                    startActivity(intent);
                                }
                            });
                        }

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              rvBottom = findViewById(R.id.bottom_recycler_view);
                                              RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
                                              rvBottom.setLayoutManager(layoutManager);
                                              rvBottom.setAdapter(adapter);
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
    class AccessoryThread extends Thread {

        String title;
        String price;
        String link;
        private RecyclerView rvAccessory;

        ShoppingAdapter adapter = new ShoppingAdapter();

        @Override
        public void run() {
            try {
                try {
                    //JSONObject??? ????????? key value ???????????? ?????? ???????????????.
                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try {
                        URL url = new URL("http://13.125.182.78:5000/api/shopping/"+Accessory); //????????? ???
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        con.setRequestProperty("Cache-Control", "no-cache");//?????? ??????
                        con.setRequestProperty("Content-Type", "application/json");//application JSON ???????????? ??????

                        con.setRequestProperty("Accept", "application/json");
                        con.setDoInput(true);//Inputstream?????? ??????????????? ????????? ???????????? ??????

                        //????????? ?????? ???????????? ??????
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
                        System.out.println(shopping_tokener.getJSONArray("data"));
                        JSONArray shoppingArray = shopping_tokener.getJSONArray("data");

                        for (int i=0; i<shoppingArray.length(); i++){
                            title = shoppingArray.getJSONObject(i).getString("title");
                            price = shoppingArray.getJSONObject(i).getString("price");
                            link = shoppingArray.getJSONObject(i).getString("url");
                            adapter.addItem(new Shopping(title,price,link));
                            adapter.notifyDataSetChanged();
                            adapter.setOnItemClickListener(new ShoppingAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(shopping_list.get(position).getLink()));
                                    startActivity(intent);
                                }
                            });
                        }

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              rvAccessory = findViewById(R.id.accessory_recycler_view);
                                              RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
                                              rvAccessory.setLayoutManager(layoutManager);
                                              rvAccessory.setAdapter(adapter);
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
    class OuterThread extends Thread {

        String title;
        String price;
        String link;
        private RecyclerView rvOuter;

        ShoppingAdapter adapter = new ShoppingAdapter();

        @Override
        public void run() {
            try {
                try {
                    //JSONObject??? ????????? key value ???????????? ?????? ???????????????.
                    HttpURLConnection con = null;
                    BufferedReader reader = null;

                    try {
                        URL url = new URL("http://13.125.182.78:5000/api/shopping/"+Outer); //????????? ???
                        con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        con.setRequestProperty("Cache-Control", "no-cache");//?????? ??????
                        con.setRequestProperty("Content-Type", "application/json");//application JSON ???????????? ??????

                        con.setRequestProperty("Accept", "application/json");
                        con.setDoInput(true);//Inputstream?????? ??????????????? ????????? ???????????? ??????

                        //????????? ?????? ???????????? ??????
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
                        System.out.println(shopping_tokener.getJSONArray("data"));
                        JSONArray shoppingArray = shopping_tokener.getJSONArray("data");

                        for (int i=0; i<shoppingArray.length(); i++){
                            title = shoppingArray.getJSONObject(i).getString("title");
                            price = shoppingArray.getJSONObject(i).getString("price");
                            link = shoppingArray.getJSONObject(i).getString("url");
                            adapter.addItem(new Shopping(title,price,link));
                            adapter.notifyDataSetChanged();
                            adapter.setOnItemClickListener(new ShoppingAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(shopping_list.get(position).getLink()));
                                    startActivity(intent);
                                }
                            });
                        }

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              rvOuter = findViewById(R.id.bottom_recycler_view);
                                              RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
                                              rvOuter.setLayoutManager(layoutManager);
                                              rvOuter.setAdapter(adapter);
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