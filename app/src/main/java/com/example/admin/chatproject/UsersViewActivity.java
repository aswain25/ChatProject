package com.example.admin.chatproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.admin.chatproject.model.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersViewActivity extends AppCompatActivity implements DBManager.IUsersDataSetListener
{
    @BindView(R.id.lvUsers) ListView lvUsers;

    AuthenticationManager authenticationManager;
    DBManager databaseManager;

    ArrayAdapter<User> adapter;
    List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_view);
        ButterKnife.bind(this);

        authenticationManager =  AuthenticationManager.getDefault();
        databaseManager = new DBManager();
        databaseManager.addUsersDataSetListener(this);

        adapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, users);
        lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                User clickedUser = users.get(position);
                Intent intent = new Intent(getApplicationContext(), MessagesViewActivity.class);
                intent.putExtra("sender", clickedUser.email);
                startActivity(intent);
            }
        });
        lvUsers.setAdapter(adapter);

    }

    public void tvLogOut_Clicked(View view)
    {
        authenticationManager.signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onUsersChanged(List<User> newDataSet)
    {
        adapter.clear();
        adapter.addAll(newDataSet);
        adapter.notifyDataSetChanged();
    }
}
