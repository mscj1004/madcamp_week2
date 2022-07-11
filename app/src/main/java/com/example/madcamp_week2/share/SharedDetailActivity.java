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
        Top = Top.concat(Top_color);

        Bottom = getIntent().getStringExtra("bottom");
        Bottom_color = getIntent().getStringExtra("bottom_color");
        if(Bottom==null||Bottom_color==null){
            Bottom = "없음";
            Bottom_color = "";
        }
        else if(Bottom!=null && Bottom_color!=null){
            Bottom = Bottom.concat(Bottom_color);
        }

        Accessory = getIntent().getStringExtra("accessory");
        Accessory_color = getIntent().getStringExtra("accessory_color");
        if(Accessory==null||Accessory_color==null){
            Accessory = "없음";
            Accessory_color = "";
        }
        else if(Accessory!=null && Accessory_color!=null){
            Accessory = Accessory.concat(Accessory_color);
        }

        Outer = getIntent().getStringExtra("outer");
        Outer_color = getIntent().getStringExtra("outer_color");
        if(Outer==null||Outer_color==null){
            Outer = "없음";
            Outer_color = "";
        }
        else if(Outer!=null && Outer_color!=null){
            Outer = Outer.concat(Outer_color);
        }

        Top_view = (TextView)findViewById(R.id.top);
        Bottom_view = (TextView)findViewById(R.id.bottom);
        Outer_view = (TextView)findViewById(R.id.outer);
        Accessory_view = (TextView)findViewById(R.id.outer);
        Nickname_view = (TextView)findViewById(R.id.nick_name);

        Top_view.setText(Top);
        Bottom_view.setText(Bottom);
        Outer_view.setText(Outer);
        Accessory_view.setText(Accessory);
        Nickname_view.setText(Nickname);

    }
}