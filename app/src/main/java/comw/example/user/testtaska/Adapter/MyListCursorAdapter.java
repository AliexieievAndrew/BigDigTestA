package comw.example.user.testtaska.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import comw.example.user.testtaska.Body.ImageLinkObject;
import comw.example.user.testtaska.R;

public class MyListCursorAdapter extends CursorRecyclerViewAdapter<MyListCursorAdapter.ViewHolder> {

    private static Context context;

    private final static String EXTRA_ID = "id";

    public MyListCursorAdapter(Context context, Cursor cursor){
        super(context,cursor);

        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textTimeOfUse;
        public TextView textLink;

        public ViewHolder(View view) {
            super(view);
            textTimeOfUse = view.findViewById(R.id.text_time_of_use);
            textLink = view.findViewById(R.id.text_link);
            textLink.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent("IntentFilterChangeImageStatusActivity");
            intent.putExtra(EXTRA_ID,String.valueOf(getItemId()));

            MyListCursorAdapter.context.startActivity(intent);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_link, parent, false);
        ViewHolder vh = new ViewHolder(itemView);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        ImageLinkObject myListItem = ImageLinkObject.fromCursor(cursor);
        viewHolder.textLink.setText(myListItem.getLink());
        viewHolder.textTimeOfUse.setText(getSimpleDate(myListItem.getTime()));

        int status = myListItem.getStatus();
        switch (status) {
            case 1:
                viewHolder.textLink.setTextColor(Color.GREEN);
                break;
            case 2:
                viewHolder.textLink.setTextColor(Color.RED);
                break;
            case 3:
                viewHolder.textLink.setTextColor(Color.GRAY);
                break;
        }

    }
    private String getSimpleDate(long time) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy/HH:mm");

        return dateFormat.format(new Date(time));
    }
}