package com.example.admin.chatproject;

import com.example.admin.chatproject.model.Message;
import com.example.admin.chatproject.model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DBManager
{
    FirebaseDatabase database;
    DatabaseReference messagesRef;
    DatabaseReference usersRef;

    public DBManager()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        messagesRef = database.getReference("messages");
        usersRef = database.getReference("users");
    }

    public void sendMessage(Message message)
    {
        messagesRef.push().setValue(message);
    }

    public void addUser(User user)
    {
        usersRef.push().setValue(user);
    }

    public void addUsersDataSetListener(final IUsersDataSetListener listener)
    {
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                List<User> users = new ArrayList<>();
                for (DataSnapshot snapShot : dataSnapshot.getChildren())
                {
                    HashMap<String, String> value = (HashMap<String, String>)snapShot.getValue();
                    User user = new User(value.get("email"), value.get("firstName"), value.get("lastName"), value.get("age"));
                    users.add(user);
                }
                listener.onUsersChanged(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addMessagesDataSetListener(final IMessagesDataSetListener listener)
    {//"lilbreh@gucci.com"
        String senderEmail = listener.getSender();
        //Query query = messagesRef.orderByChild("senderID").equalTo(senderEmail);
        messagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                List<Message> messages = new ArrayList<>();
                for (DataSnapshot snapShot : dataSnapshot.getChildren())
                {
                    HashMap<String, Object> value = (HashMap<String, Object>)snapShot.getValue();
                    Message message = new Message((String)value.get("senderID"), (String)value.get("receiverID"), (String)value.get("message"), (long)value.get("date"));
                    messages.add(message);
                }

                List<Message> result = new ArrayList<>();
                for (Message message : messages)
                    if ((message.getSenderID().equals(listener.getSender()) && message.getReceiverID().equals(listener.getReceiver())) || (message.getSenderID().equals(listener.getReceiver()) && message.getReceiverID().equals(listener.getSender())))
                        result.add(message);
                Collections.sort(result, new Comparator<Message>() {
                    public int compare(Message o1, Message o2) {
                        return new Date(o1.getDate()).compareTo(new Date(o2.getDate()));
                    }
                });

                listener.onMessagesChanged(result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public interface IUsersDataSetListener
    {
        void onUsersChanged(List<User> newDataSet);
    }
    public interface IMessagesDataSetListener
    {
        void onMessagesChanged(List<Message> newDataSet);
        String getReceiver();
        String getSender();
    }
}
