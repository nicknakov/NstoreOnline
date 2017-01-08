package com.example.nstore.taxitracker;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DataRequest extends StringRequest{

    private static final String DATA_URL = "http://192.168.0.101:8080/geo/locationandroid";
    Map<String, String> params;

    public DataRequest(String coords, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, DATA_URL, listener, errorListener);
        params = new HashMap<>();
        params.put("Content-Type", "application/json");
        params.put("coord", coords);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
