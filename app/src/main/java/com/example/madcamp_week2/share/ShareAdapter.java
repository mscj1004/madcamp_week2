package com.example.madcamp_week2.share;

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

public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.MyViewHolder> {
    private OnItemClickListener iListener = null;
    //리스트는 무조건 데이터를 필요로함
    private List<Share> items = new ArrayList<>();
    private Context context;

    interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.iListener = listener;
    }

    public void addItem(Share share) {
        items.add(share);
    }

    //껍데기만 만듬. 1번 실행
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.share_item_view, parent, false);
        return new MyViewHolder(view);
    }

    //껍데기에 데이터 바인딩. 2번 실행
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Share share = items.get(position);
        holder.setItem(share);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //ViewHolder : 뷰들의 책꽂이
    public class MyViewHolder extends RecyclerView.ViewHolder {

        //규칙1
        private TextView nickname;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.nick_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                int position = getAdapterPosition();
                                                if (position != RecyclerView.NO_POSITION) {
                                                    if (iListener != null) {
                                                        iListener.onItemClick(v, position);
                                                    }
                                                }
                                            }
                                        }
            );

        }

        //규칙3
        public void setItem(Share share) {
            nickname.setText(share.getNickname());
        }
    }
}

