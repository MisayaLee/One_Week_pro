package com.example.misaya.one_week_pro.One;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.misaya.one_week_pro.R;

import java.util.List;

/**
 * Created by Misaya on 2018/7/8.
 */

public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.ViewHolder> {
    private Context context;
    private List<ContactEntity>list;

    public MyRvAdapter(Context context, List<ContactEntity> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRvAdapter.ViewHolder holder, final int position) {
        holder.user_name.setText(list.get(position).getName());
        holder.user_phone.setText(list.get(position).getNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContactFragment.class);
//                intent.putExtra("Pic",list.get(position).getPic());
//                intent.putExtra("name",list.get(position).getTitle());
//                intent.putExtra("pirce",list.get(position).getId());
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("phone",list.get(position).getNumber());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView user_phone;
        private TextView user_name;

        public ViewHolder(View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            user_phone = itemView.findViewById(R.id.user_phone);
        }
    }
}
