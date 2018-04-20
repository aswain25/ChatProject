package com.example.admin.chatproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.admin.chatproject.model.Message;
import com.example.admin.chatproject.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesViewActivity extends AppCompatActivity implements DBManager.IMessagesDataSetListener
{
    @BindView(R.id.etMessage) EditText etMessage;
    @BindView(R.id.lvMessages) ListView lvMessages;

    AuthenticationManager authenticationManager;
    DBManager databaseManager = new DBManager();
    String senderEmail;
    String receiverEmail;

    ArrayAdapter<Message> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_view);
        AuthenticationManager.initialize(this);
        ButterKnife.bind(this);

        authenticationManager = AuthenticationManager.getDefault();
        senderEmail = getIntent().getStringExtra("sender");
        receiverEmail = authenticationManager.user.getEmail();
        databaseManager.addMessagesDataSetListener(this);

        adapter = new ArrayAdapter<Message>(this, android.R.layout.simple_list_item_1, new ArrayList<Message>());
        lvMessages.setAdapter(adapter);
    }

    public void btnSend_Clicked(View view)
    {
        databaseManager.sendMessage(new Message(receiverEmail, senderEmail, etMessage.getText().toString(), new Date().getTime()));
    }

    @Override
    public void onMessagesChanged(List<Message> newDataSet)
    {
        adapter.clear();
        adapter.addAll(newDataSet);
        adapter.notifyDataSetChanged();
    }

    @Override
    public String getReceiver() {
        return receiverEmail;
    }

    @Override
    public String getSender() {
        return senderEmail;
    }
}
