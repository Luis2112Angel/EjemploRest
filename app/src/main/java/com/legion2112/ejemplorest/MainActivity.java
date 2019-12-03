package com.legion2112.ejemplorest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView txtMsg=null;
    private EditText textClave = null;
    private EditText textNombre = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMsg = findViewById(R.id.txtMsg);
        textClave = findViewById(R.id.txtClave);
        textNombre = findViewById(R.id.txtNombre);
    }

    public void btoAceptar_onClick(View view) {
        //Codigo al presionar el boton
        getData();
    }

    public void getData(){
        String sql = "https://total-name-256003.appspot.com/pokemons";
        //String sql = "http://10.0.0.12:8084/EmpresaRest/empresa/departamentos";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn;

        try {
            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            String json = "";
            String mensaje = "";

            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            json = response.toString();
            JSONArray jsonArr = null;
            jsonArr = new JSONArray(json);

            for(int i = 0; i < jsonArr.length(); i++){

                JSONObject jsonObject = jsonArr.getJSONObject(i);

                mensaje += "id: " + jsonObject.optString("id")
                        + " Name: " + jsonObject.optString("name")
                        + " Description: " + jsonObject.optString("des")
                        + " power" + jsonObject.optString("power") + "\n ";

                textClave.setText(jsonObject.optString("id"));
                textNombre.setText(jsonObject.optString("name"));

            }

            txtMsg.setText(mensaje);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
