package com.company.calendar.managers;

import com.company.calendar.models.Event;
import com.company.calendar.models.EventSubscription;
import com.company.calendar.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by abdul on 18-Jun-17.
 */

public class EventSubscriptionManager {

    public static void addSubscriptionToDb(ArrayList<String> users, String eventId, String currUser) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(EventSubscription.EVENT_SUBSCRIPTION_TABLE);

        for (String usr : users) {
            String key = db.push().getKey();
            final EventSubscription subscription = new EventSubscription(usr, eventId, Event.UNCONFIRMED);
            db.child(key).setValue(subscription);
        }
        //add current user as confirmed
        String k = db.push().getKey();
        db.child(k).setValue(new EventSubscription(currUser, eventId, Event.GOING));
    }

    public static ArrayList<EventSubscription> getAllSubscriptionsFromDb(Map<String,Object> map) {

        ArrayList<EventSubscription> subs = new ArrayList<>();

        for (Map.Entry<String, Object> entry : map.entrySet()){
            Map singleSub = (Map) entry.getValue();
            final EventSubscription sub = new EventSubscription((String) singleSub.get(EventSubscription.USER_EMAIL_FIELD),
                    (String) singleSub.get(EventSubscription.EVENT_ID_FIELD), (String) singleSub.get(EventSubscription.STATUS_FIELD));
            subs.add(sub);
        }
        return subs;
    }

    public static ArrayList<EventSubscription> filterCurrentUserSubs(ArrayList<EventSubscription> allSubs, String currUser) {

        currUser = User.encodeString(currUser);
        ArrayList<EventSubscription> fileredList = new ArrayList<>();

        for (EventSubscription sub : allSubs) {
            if (sub.getUserEmail().equals(currUser)) {
                fileredList.add(sub);
            }
        }
        return fileredList;
    }
}