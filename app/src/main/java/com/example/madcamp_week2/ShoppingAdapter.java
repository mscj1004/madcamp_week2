package com.example.madcamp_week2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.MyViewHolder>{
    private OnItemClickListener iListener = null;
    //리스트는 무조건 데이터를 필요로함
    private List<Shopping> items=new ArrayList<>();
    private Context context;

    interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.iListener = listener;
    }
    public void addItem(Shopping shopping){
        items.add(shopping);
    }

    //껍데기만 만듬. 1번 실행
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.shopping_item_view,parent,false);
        return new MyViewHolder(view);
    }

    //껍데기에 데이터 바인딩. 2번 실행
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Shopping shopping=items.get(position);
        holder.setItem(shopping);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //ViewHolder : 뷰들의 책꽂이
    public class MyViewHolder extends RecyclerView.ViewHolder {

        //규칙1
        private TextView title;
        private TextView price;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);

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
        public void setItem(Shopping shopping){
                title.setText(shopping.getTitle());
                price.setText(shopping.getPrice());
        }
    }
}