package com.example.ravi.newsfeed;

import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.graphics.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class NewsList extends AppCompatActivity {

    String url=null;
    String log="abcd";

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        Toolbar toolbar=(Toolbar)findViewById(R.id.tb);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Trending Now");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        url=getIntent().getExtras().getString("url");

        // Progress dialog for loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Refreshing Feed...");
        progressDialog.show();


       // Toast.makeText(this,url,Toast.LENGTH_LONG).show();
        // main method to execute
        Network network=new Network();
        network.execute(url);


    }

    // on pressing the home button in toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    // Asynchronus task for network activity
    private class Network extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            // getting the input stream
           HttpURLConnection httpURLConnection=null;
            String json=null;
            InputStream inputStream;
            try {
                URL url_final=new URL(url);
                httpURLConnection=(HttpURLConnection)url_final.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                inputStream=httpURLConnection.getInputStream();
                json=ReadfromStream(inputStream);
            }
            catch (MalformedURLException e)
            {
                Log.e(log,"Error creating URL object",e);
            }
            catch (IOException e)
            {
                Log.e(log,"Error making HTTP Request",e);
            }
            finally {
                httpURLConnection.disconnect();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String json) {

            ArrayList<NewsDetails> newsDetailses=ExtractDetails(json);
            UpdateUI(newsDetailses);
        }
    }

    // reading the data from the input stream
    private String ReadfromStream(InputStream inputStream) throws IOException
    {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    // extracting data from the JSON file
    private ArrayList<NewsDetails> ExtractDetails(String json)
    {
        String date=null;
        String numTime=null;

        ArrayList<NewsDetails> newsDetails=new ArrayList<>();
        try {
            JSONObject root=new JSONObject(json);
            JSONArray articles=root.getJSONArray("articles");

            for(int i=0;i<7;i++)
            {
                JSONObject index=articles.getJSONObject(i);
                String title=index.getString("title");
                String description=index.getString("description");
                String Contenturl=index.getString("url");
                String Imageurl=index.getString("urlToImage");
                String time=index.getString("publishedAt");

                if(time.contains("T"))
                {
                    int end=time.length();
                    int temp=time.indexOf("T");
                    date=time.substring(0,temp+1);
                    numTime=time.substring(temp+1,end-1);
                }

                newsDetails.add(new NewsDetails(title,description,Contenturl,Imageurl,date,numTime));
            }
        }
        catch (JSONException e)
        {
           Log.e(log,"Error parsing JSON response",e);
        }
        return newsDetails;
    }

    // Setting the news to the custom list view
    private void UpdateUI(final ArrayList<NewsDetails> newsDetailses)
    {
        ListView listView=(ListView)findViewById(R.id.listview);

         final NewsAdaptor newsAdaptor=new NewsAdaptor(this,newsDetailses);

         progressDialog.dismiss();

        listView.setAdapter(newsAdaptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsDetails newsDetails=newsAdaptor.getItem(position);
                Uri uri=Uri.parse(newsDetails.getContentUrl());
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
    }
}
