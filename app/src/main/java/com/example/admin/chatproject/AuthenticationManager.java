package com.example.admin.chatproject;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationManager
{
    private FirebaseAuth mAuth;
    Activity activity;
    IAuthenticationHandler listener;
    FirebaseUser user;

    public static AuthenticationManager instance;



    private AuthenticationManager(Activity activity) {
        this.mAuth = FirebaseAuth.getInstance();
        this.activity = activity;
        this.listener = (IAuthenticationHandler)activity;
    }

    public static AuthenticationManager getDefault()
    {
        return instance;
    }

    public static void initialize(Activity activity)
    {
        if (instance == null)
            instance = new AuthenticationManager(activity);
    }

    public void register(String email, String password)
    {
        user = null;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AUTHTAG", "createUserWithEmail:success");
                            user = mAuth.getCurrentUser();
                            listener.onLoginSuccess(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AUTHTAG", "createUserWithEmail:failure", task.getException());
                            //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                            //        Toast.LENGTH_SHORT).show();
                            listener.onLoginError(task.getException().toString());
                        }

                        // ...
                    }
                });
    }

    public void sgnIn(String email, String password)
    {
        user = null;
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AuthTag", "signInWithEmail:success");
                            user = mAuth.getCurrentUser();
                            listener.onLoginSuccess(user);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AUTHTAG", "signInWithEmail:failure", task.getException());
                            //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                            //        Toast.LENGTH_SHORT).show();
                            listener.onLoginSuccess(user);
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
    public void signOut()
    {

        mAuth.signOut();
        if (user != null)
            listener.onSignOut(false);
        else
            listener.onSignOut(true);
    }
    public interface IAuthenticationHandler
    {
        void onLoginSuccess(FirebaseUser user);
        void onSignOut(boolean isSignedOut);
        void onLoginError(String error);
    }
}
