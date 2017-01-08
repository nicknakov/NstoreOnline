package com.example.nstore.taxitracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public String userString, passString;
    TextView userName, password;
    AlertDialog alert = null;
    public static boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            if(!isLoggedIn){
                showLoginScreen();
            }else{
                Toast.makeText(MainActivity.this, "You are Logged In!\nPlease Logg Out first!", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        if(id == R.id.action_logOut){
            if(isLoggedIn){
                logoutFromDb();
            }else{
                Toast.makeText(MainActivity.this, "You are NOT Logged", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void showLoginScreen() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.login_layout, null);
        userName = (TextView) v.findViewById(R.id.etUsername);
        password = (TextView) v.findViewById(R.id.etPassword);

        // create buttons new
        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setView(v);
        alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userString = userName.getText().toString();
                passString = password.getText().toString();
                if(validateUser(userString)){
                    userName.setError("Username mast be 2 and more characters!");
                    userName.requestFocus();
                }else if(valdatePass(passString)){
                    password.setError("Password mast be 2 and more characters!");
                    password.requestFocus();
                }else {
                    loginInDb(userString, passString);
                }
            }
        });
    }

    private boolean valdatePass(String pass) {
        if(pass.length() < 4 || pass.contains(" ")){
            return true;
        }
        return false;
    }

    private boolean validateUser(String user) {
        if(user.length() < 2 || user.contains(" ")){
            return true;
        }
        userName.setFocusable(false);
        return false;
    }

    private void loginInDb(String user, String pass) {

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success){
                            Intent i = new Intent(MainActivity.this, CarListActivity.class);

                            alert.dismiss();
                            isLoggedIn = true;
                            MainActivity.this.startActivity(i);
                        }else{
                            Toast.makeText(MainActivity.this, "Wrong Username or Password!\nPlease try again!", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        };

        LoginRequest loginRequest = new LoginRequest(user, pass, listener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(loginRequest);

    }

    public  void logoutFromDb() {
        isLoggedIn = false;
        Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(MainActivity.this, MyService.class);
            stopService(i);

    }
}
