package edu.upc.whatsapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.upc.whatsapp.R;
import entity.Message;
import entity.UserInfo;

public class MyAdapter_messagesRecycleView extends RecyclerView.Adapter<MyAdapter_messagesRecycleView.MyViewHolder>  {


    private Context mContext;
    private List<Message> messages;
    private List<Integer> date_visibility;
    private UserInfo my_user;
    private String last_date_shown;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView rowDate;
        public TextView row_content;
        public TextView row_hour;
        public MyViewHolder(View itemView) {
            super(itemView);
            row_content = (TextView) itemView.findViewById(R.id.row_content);
            rowDate = (TextView) itemView.findViewById(R.id.row_date);
            row_hour = (TextView) itemView.findViewById(R.id.row_hour);
        }
    }

    public MyAdapter_messagesRecycleView(Context mContext, List<Message> messages, UserInfo my_user) {
        this.mContext = mContext;
        this.messages = messages;
        this.my_user = my_user;
        last_date_shown = "none";
        date_visibility = new ArrayList<Integer>();
        for (Message message : messages) {
            set_date_visibility(message);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v=null;

            if (getItemViewType(i) == 0) {
                v = LayoutInflater.from(mContext).inflate(R.layout.row_whatsapp_right_bubble, viewGroup, false);
            }
            if (getItemViewType(i) == 1) {
                v = LayoutInflater.from(mContext).inflate(R.layout.row_whatsapp_left_bubble, viewGroup, false);
            }


        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        Date date = messages.get(i).getDate();

        if(date_visibility.get(i)==View.VISIBLE)
            myViewHolder.rowDate.setVisibility(View.VISIBLE);
        else
            myViewHolder.rowDate.setVisibility(View.GONE);

        myViewHolder.row_content.setText(messages.get(i).getContent());
        myViewHolder.row_hour.setText(sdf.format(date));
        myViewHolder.rowDate.setText(sdf2.format(date));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(Message new_message){
        messages.add(new_message);
        set_date_visibility(new_message);
    }
    public void addMessages(List<Message> new_messages){
        for(Message new_message : new_messages){
            messages.add(new_message);
            set_date_visibility(new_message);
        }
    }
    public boolean isEmpty(){
        return messages.isEmpty();
    }

    public Message getLastMessage(){
        if(messages.size()>0){
            return messages.get(messages.size() - 1);
        }
        return null;
    }

    public int getCount() {
        return messages.size();
    }

    public Object getItem(int arg0) {
        return messages.get(arg0);
    }

    public long getItemId(int arg0) {
        return messages.get(arg0).getId();
    }

    @Override
    public int getItemViewType(int position) {

        if(messages.get(position).getUserSender().getId() == my_user.getId() ){
            return 0;
        }else {
            return 1;
        }
    }


    private void set_date_visibility(Message message) {
        Date date = message.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String day_of_year = calendar.get(Calendar.DAY_OF_YEAR)+"-"+calendar.get(Calendar.YEAR);
        if (last_date_shown.equals("none")) {
            last_date_shown = day_of_year;
            date_visibility.add(View.VISIBLE);
        } else if (!last_date_shown.equals(day_of_year)) {
            last_date_shown = day_of_year;
            date_visibility.add(View.VISIBLE);
        } else {
            date_visibility.add(View.GONE);
        }
    }


}
