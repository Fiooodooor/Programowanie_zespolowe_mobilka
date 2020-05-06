package com.votedesk.data;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// "http://ec2-3-9-170-154.eu-west-2.compute.amazonaws.com/api/auth/"
// 200 ok, 201 created, 202 accepted, 204 no content;

public class AsynchEndpointComm {

    public enum requestMethods {
        NOT_SET(""),
        GET("GET"),
        HEAD("HEAD"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE"),
        CONNECT("CONNECT"),
        OPTION("OPTION"),
        TRACE("TRACE");

        private final String strForm;

        requestMethods(String theStringForm) {
            this.strForm = theStringForm;
        }
        public final String strForm() {
            return this.strForm;
        }
    }
    private static volatile String token;
    private String baseUrl;
    private String apiUrl;
    private requestMethods apiMethod;
    private JSONObject payload;
    private int responseCode;
    private Map<String, List<String>> headerFields;

    public AsynchEndpointComm(String apiUrl, requestMethods apiMethod) {
        SetBaseUrl("http://ec2-3-9-170-154.eu-west-2.compute.amazonaws.com/api/");
        SetApiUrl(apiUrl);
        SetApiMethod(apiMethod);
        this.headerFields = new HashMap<>();
        this.responseCode = 0;
    }

    private AsynchEndpointComm SetBaseUrl(String url) {
        this.baseUrl = url;
        if(!url.endsWith("/")) {
            this.baseUrl += "/";
        }
        return this;
    }
    protected String GetBaseUrl() {
        return this.baseUrl;
    }
    protected AsynchEndpointComm SetApiUrl(String theApiUrl) {
        if(theApiUrl.startsWith("/") && theApiUrl.endsWith("/")) {
            this.apiUrl = theApiUrl.substring(1);
        }
        else {
            this.apiUrl = theApiUrl;
        }
        if(!this.apiUrl.endsWith("/")) {
            this.apiUrl.concat("/");
        }
        return this;
    }
    protected String GetApiUrl() {
        return this.apiUrl;
    }
    protected String GetFullUrl() {
        return this.GetBaseUrl().concat(this.GetApiUrl());
    }

    protected AsynchEndpointComm SetApiMethod(requestMethods theMethod) {
        this.apiMethod = theMethod;
        return this;
    }
    protected requestMethods GetApiMethod() {
        return this.apiMethod;
    }
    public int GetResponceCode() {
        return this.responseCode;
    }
    protected void SetResponceCode(int code) {
        this.responseCode = code;
    }

    static protected void SetToken(String tokenValue) {
        if(token == null || token.isEmpty()) {
            token = "Token ".concat(tokenValue);
        }
    }
    static protected String GetToken() {
        if(token == null || token.isEmpty())
            return "";
        else return token;
    }

    protected AsynchEndpointComm CreatePayloadJsonObject(JSONObject object){
        this.payload = object;
        return this;
    }

    protected String execute() {
        StringBuilder response = new StringBuilder();
        String responseLine;

        try {
            URL requestUrl = new URL(this.GetFullUrl());
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod(this.GetApiMethod().strForm());

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            if(!GetToken().isEmpty()) {
                connection.setRequestProperty("Authorization", GetToken());
            }
            try {
                if(this.payload != null) {
                    connection.setDoOutput(true);
                    byte[] input = payload.toString().getBytes("utf-8");
                    connection.getOutputStream().write(input, 0, input.length);
                }

                SetResponceCode(connection.getResponseCode());
                if(GetResponceCode() != 200)
                    throw new IOException("Wrong responce code from HTTP network. Failed to get result.");

                headerFields = connection.getHeaderFields();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                connection.disconnect();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }

}
