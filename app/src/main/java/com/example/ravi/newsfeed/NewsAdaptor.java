package com.example.ravi.newsfeed;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ravi on 20/12/16.
 */
public class NewsAdaptor extends ArrayAdapter<NewsDetails> {

    private NewsAdaptor newsAdaptor;
    ImageView imageView;

    public NewsAdaptor(Context context, ArrayList<NewsDetails> newsDetailses) {
        super(context,0,newsDetailses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list, parent, false);

        }

        NewsDetails newsDetails=getItem(position);

        TextView title=(TextView)listItemView.findViewById(R.id.title);
        TextView description=(TextView)listItemView.findViewById(R.id.des);
        imageView=(ImageView)listItemView.findViewById(R.id.iv);

        title.setText(newsDetails.getTitle());
        description.setText(newsDetails.getDescription());

        String url=newsDetails.getImageUrl();

        ImageBitmap imageBitmap=new ImageBitmap();
        imageBitmap.execute(url);

        return listItemView;
    }

    private class ImageBitmap extends AsyncTask<String,Void,Bitmap>
    {
        @Override
        protected Bitmap doInBackground(String... url) {
            try {
                URL furl=new URL(url[0]);
                Bitmap bmp = BitmapFactory.decodeStream(furl.openConnection().getInputStream());
                 return bmp;
            }
            catch (MalformedURLException e)
            {
                Log.e("My error","Error creating image URL",e);
            }
            catch (IOException e)
            {
                Log.e("My error","Error connecting image",e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            imageView.setImageBitmap(bitmap);
        }
    }
}
