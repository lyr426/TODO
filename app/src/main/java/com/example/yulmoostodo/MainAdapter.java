package com.example.yulmoostodo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<MainData> arrayList;

    public MainAdapter(Context context, ArrayList<MainData> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.CustomViewHolder holder, int position) {
        final MainData item = arrayList.get(position);
        holder.chbox.setOnCheckedChangeListener(null);
        holder.chbox.setChecked(item.getCh_box());
        holder.chbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { //체크 버튼을 누르면
                item.setCh_box(isChecked);
                if (isChecked == true){
                    updateChk(holder.getAdapterPosition(), 1);
                    MainActivity.chk_progress(1);
                }
                else if(isChecked == false){
                    updateChk(holder.getAdapterPosition(), 0);
                    MainActivity.chk_progress(0);
                }
            }

        });

        holder.tv_content.setText(arrayList.get(position).getTv_content());

        boolean chkImp = arrayList.get(position).getCh_imp();

        if (chkImp==true){
            System.out.println("visible");
            holder.imv_imp.setVisibility(View.VISIBLE);
        }
        else{holder.imv_imp.setVisibility(View.GONE);}


        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder.tv_content.getText().toString();
                Toast.makeText(v.getContext(),curName, Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() { //길게 누르면 삭제
            @Override
            public boolean onLongClick(View v) {

               //DB에서 삭제하기
                final MainData data = arrayList.get(position);
                myDBHelper myHelper = new myDBHelper(context);
                myHelper.open();
                myHelper.deleteColumn(data.getId());
                System.out.println(data.getId());
                myHelper.close();

                //notifyDataSetChanged();
                System.out.println(data.getCh_box());
                if (data.getCh_box() == true){ MainActivity.chk_progress(2);}
                else if (data.getCh_box() == false){ MainActivity.chk_progress(3);}
                remove(holder.getAdapterPosition());

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position){
        try{
            arrayList.remove(position);
           // notifyItemRemoved(position);
            notifyDataSetChanged();
        } catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imv_imp;
        protected CheckBox chbox;
        protected TextView tv_content;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.chbox = (CheckBox) itemView.findViewById(R.id.chbox);
            this.tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            this.imv_imp = (ImageView)itemView.findViewById(R.id.imv_imp);
        }
    }

    public void updateChk(int position, int TF){
        final MainData data = arrayList.get(position);
        myDBHelper myHelper = new myDBHelper(context);
        myHelper.open();
        myHelper.updateColumn_chk(data.getId(), TF);
        System.out.println(data.getId());
        myHelper.close();

        notifyDataSetChanged();
    }

}
