package com.example.madcamp_week2.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madcamp_week2.R;

import java.util.ArrayList;
import java.util.List;

public class RecFashionAdapter extends RecyclerView.Adapter<RecFashionAdapter.MyViewHolder>{
    private OnItemClickListener iListener = null;
    //리스트는 무조건 데이터를 필요로함
    private List<Fashion> items=new ArrayList<>();
    private Context context;

    interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.iListener = listener;
    }
    public void addItem(Fashion fashion){
        items.add(fashion);
    }

    //껍데기만 만듬. 1번 실행
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.fashion_item_view,parent,false);
        return new MyViewHolder(view);
    }

    //껍데기에 데이터 바인딩. 2번 실행
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Fashion fashion=items.get(position);
        holder.setItem(fashion);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //ViewHolder : 뷰들의 책꽂이
    public class MyViewHolder extends RecyclerView.ViewHolder {

        //규칙1
        private TextView topColor;
        private TextView top;
        private TextView bottomColor;
        private TextView bottom;
        private TextView accessoryColor;
        private TextView accessory;
        private TextView outerColor;
        private TextView outer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            topColor=itemView.findViewById(R.id.top_color);
            top=itemView.findViewById(R.id.top);
            bottomColor=itemView.findViewById(R.id.bottom_color);
            bottom=itemView.findViewById(R.id.bottom);
            accessoryColor=itemView.findViewById(R.id.accessory_color);
            accessory=itemView.findViewById(R.id.accessory);
            outerColor=itemView.findViewById(R.id.outer_color);
            outer=itemView.findViewById(R.id.outer);

            itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     int position = getAdapterPosition();
                     if(position!=RecyclerView.NO_POSITION){
                      if(iListener!=null){
                          iListener.onItemClick(v, position);
                      }
                     }
                    }
                }
            );

        }

        //규칙3
        public void setItem(Fashion fashion){
            topColor.setText(fashion.getTop_color());
            top.setText(fashion.getTop());
            bottomColor.setText(fashion.getBottom_color());
            bottom.setText(fashion.getBottom());
            accessoryColor.setText(fashion.getAccessory_color());
            accessory.setText(fashion.getAccessory());
            outerColor.setText(fashion.getOuter_color());
            outer.setText(fashion.getOuter());
        }
    }
}