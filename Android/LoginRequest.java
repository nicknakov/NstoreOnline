package com.example.nstore.taxitracker;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest{

    private static final String LOGIN_URL = "http://192.168.0.101:8080/geo/loginuser";
    Map<String, String> params;
    public LoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_URL, listener, null);
        params = new HashMap<>();
        params.put("Content-Type", "application/json");
        params.put("username",username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
