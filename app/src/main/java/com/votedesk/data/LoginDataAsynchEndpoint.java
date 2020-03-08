package com.votedesk.data;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// "http://ec2-3-9-170-154.eu-west-2.compute.amazonaws.com/api/auth/"
public class LoginDataAsynchEndpoint {
    private String baseUrl;
    private JSONObject payload;
    private int responseCode;
    private String token;
    private String username;
    private String password;
    private Map<String, List<String>> headerFields;

    public LoginDataAsynchEndpoint(String username, String password)
    {
        SetBaseUrl("http://ec2-3-9-170-154.eu-west-2.compute.amazonaws.com/api/auth/");

        this.token = "";
        this.username = username;
        this.password = password;
        this.headerFields = new HashMap<>();
        this.responseCode = -1;
    }

    private LoginDataAsynchEndpoint SetBaseUrl(String url)
    {
        this.baseUrl = url;
        return this;
    }

    private LoginDataAsynchEndpoint CreatePayloadJsonObject(){
        try {
            this.payload = new JSONObject();
            this.payload.put("username", username);
            this.payload.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public String execute() {
        StringBuilder response = new StringBuilder();
        String responseLine = null;

        try {
            URL requestUrl = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            try {
                CreatePayloadJsonObject();

                OutputStream os = connection.getOutputStream();
                byte[] input = payload.toString().getBytes("utf-8");
                os.write(input, 0, input.length);

                headerFields = connection.getHeaderFields();

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            } finally {
                responseCode = connection.getResponseCode();
                connection.disconnect();
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
