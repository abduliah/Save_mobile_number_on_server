package com.app3.imsaiful.saif_contact;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class add_info extends AppCompatActivity {
    EditText Name,Email,Mobile;
    String name,email,mobile;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_info_layout);
        Name=(EditText)findViewById(R.id.etname);
        Email=(EditText)findViewById(R.id.etemail);
        Mobile=(EditText)findViewById(R.id.etmob);
        b1=(Button) findViewById(R.id.sbtn);
    }
    public void saveData(View view)
    {
        name=Name.getText().toString();
        email=Email.getText().toString();
        mobile=Mobile.getText().toString();
        BackgroundTask backgroundTask=new BackgroundTask();
        backgroundTask.execute(name,mobile,email);
    }
    class BackgroundTask extends AsyncTask<String,Void,String>
    {
        String add_url;
        @Override
        protected void onPreExecute() {
           add_url="http://shortinfo.info/saifulData/insert_script.php";
        }
        @Override
        protected String doInBackground(String... args) {

            String name,email,moblie;
            name=args[0];
            moblie=args[1];
            email=args[2];
            try {
                    URL url=new URL(add_url);
                    HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data_string= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                            URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(moblie,"UTF-8")+"&"+
                            URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
                    bufferedWriter.write(data_string);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return "One row is inserted";
                }


            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
        }


    }
}
