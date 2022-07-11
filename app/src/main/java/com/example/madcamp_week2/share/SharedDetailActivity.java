package com.example.madcamp_week2.share;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.madcamp_week2.R;

import org.w3c.dom.Text;

public class SharedDetailActivity extends AppCompatActivity {

    String Nickname;
    String Top;
    String Top_color;
    String Bottom;
    String Bottom_color;
    String Accessory;
    String Accessory_color;
    String Outer;
    String Outer_color;

    TextView Nickname_view;
    TextView Top_view;
    TextView Bottom_view;
    TextView Accessory_view;
    TextView Outer_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_detail);

        Nickname = getIntent().getStringExtra("nickname");
        Top = getIntent().getStringExtra("top");
        Top_color = getIntent().getStringExtra("top_color");
        Top = Top_color.concat(Top);

        Bottom = getIntent().getStringExtra("bottom");
        Bottom_color = getIntent().getStringExtra("bottom_color");
        if(Bottom.equals("없음")||Bottom_color.equals("없음")){
            Bottom = "없음";
            Bottom_color = "";
            Bottom = Bottom_color.concat(Bottom);
        }
        else if(!Bottom.equals("없음") && !Bottom_color.equals("없음")){
            Bottom = Bottom_color.concat(Bottom);
        }

        Accessory = getIntent().getStringExtra("accessory");
        Accessory_color = getIntent().getStringExtra("accessory_color");
        if(Accessory.equals("없음")||Accessory_color.equals("없음")){
            Accessory = "없음";
            Accessory_color = "";
            Accessory = Accessory_color.concat(Accessory);
        }
        else if(!Accessory.equals("없음") && !Accessory_color.equals("없음")){
            Accessory = Accessory_color.concat(Accessory);
        }

        Outer = getIntent().getStringExtra("outer");
        Outer_color = getIntent().getStringExtra("outer_color");
        if(Outer.equals("없음")||Outer_color.equals("없음")){
            Outer = "없음";
            Outer_color = "";
            Outer = Outer_color.concat(Outer);
        }
        else if(!Outer.equals("없음") && !Outer_color.equals("없음")){
            Outer = Outer_color.concat(Outer);
        }

        Top_view = (TextView)findViewById(R.id.top);
        Bottom_view = (TextView)findViewById(R.id.bottom);
        Outer_view = (TextView)findViewById(R.id.outer);
        Accessory_view = (TextView)findViewById(R.id.accessory);
        Nickname_view = (TextView)findViewById(R.id.nick_name);

        Top_view.setText(Top);
        Bottom_view.setText(Bottom);
        Outer_view.setText(Outer);
        Accessory_view.setText(Accessory);
        Nickname_view.setText(Nickname);

    }
}