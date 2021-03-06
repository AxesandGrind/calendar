package com.company.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.activities.EventInfoActivity;
import com.company.R;
import com.company.models.Event;
import com.company.models.User;

import java.util.ArrayList;

/**
 * Created by abdul on 17-Jun-17.
 */

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.EventItemViewHolder> {

    private ArrayList<Event> eventsSet;
    private Context context;

    public EventRecyclerViewAdapter(ArrayList<Event> eventsSet, Context context) {
        this.eventsSet = eventsSet;
        this.context = context;
    }

    @Override
    public EventItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.listitem_events_list, parent, false);
        return new EventItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EventItemViewHolder holder, int position) {
        final Event singleEvent = eventsSet.get(position);

        holder.eventName.setText(singleEvent.getTitle() + "  ( Owner:  " + User.decodeString(singleEvent.getOwnerEmail()) + " )");
        holder.eventName.setOnClickListener(getEventNameClickListener(singleEvent));
    }

    @NonNull
    private View.OnClickListener getEventNameClickListener(final Event singleEvent) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EventInfoActivity.class);
                i.putExtra(EventInfoActivity.EVENT_ID, singleEvent.getId());
                i.putExtra(Event.START_ALARM_ID_FIELD, singleEvent.getStartAlarmId());
                i.putExtra(Event.END_ALARM_ID_FIELD, singleEvent.getEndAlarmId());
                i.putExtra(Event.OWNER_EMAIL_FIELD, singleEvent.getOwnerEmail());
                context.startActivity(i);
            }
        };
    }

    @Override
    public int getItemCount() {
        return eventsSet.size();
    }

    static class EventItemViewHolder extends RecyclerView.ViewHolder {

        private TextView eventName;

        public EventItemViewHolder(View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.event_name);
        }
    }
}
