package com.example.nstore.taxitracker;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class CarListActivity extends AppCompatActivity {

    private Button btn_stop;
    private TextView textView;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Object coordinates = intent.getExtras().get("coordinates");
                    String s = coordinates.toString();
                    if(s != null){
                        sendData(s);
                    }
                    textView.append("\n" + s);
                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("location_updates"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_stop = (Button) findViewById(R.id.btn_stop);
        textView = (TextView) findViewById(R.id.textView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(!runtime_permissions())
            enable_buttons();
    }

    private void enable_buttons() {

        Intent i = new Intent(CarListActivity.this, MyService.class);
        startService(i);

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CarListActivity.this, MyService.class);
                stopService(i);
            }
        });
    }

    private boolean runtime_permissions() {

        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enable_buttons();
            }else{
                runtime_permissions();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logOut) {
            if(MainActivity.isLoggedIn){
                logoutFromDb();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    private void logoutFromDb() {
        Toast.makeText(CarListActivity.this, "Logging Out", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(CarListActivity.this, MyService.class);
        stopService(i);
        MainActivity.isLoggedIn = false;
        Intent intent = new Intent(CarListActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void sendData(String value) {

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(CarListActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CarListActivity.this, error.toString(),Toast.LENGTH_LONG).show();
            }
        };
        DataRequest dataRequest = new DataRequest(value, listener, error);
        RequestQueue queue = Volley.newRequestQueue(CarListActivity.this);
        queue.add(dataRequest);
    }
}
