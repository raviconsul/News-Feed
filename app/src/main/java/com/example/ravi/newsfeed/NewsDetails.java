package com.example.ravi.newsfeed;

/**
 * Created by ravi on 20/12/16.
 */
public class NewsDetails {

    private String title,description,ContentUrl,ImageUrl,date,time;

    public NewsDetails(String ltitle,String ldescription,String lcontenturl,String limageurl,String ldate,String ltime)
    {
        title=ltitle;
        description=ldescription;
        ContentUrl=lcontenturl;
        ImageUrl=limageurl;
        date=ldate;
        time=ltime;
    }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public String getContentUrl() { return ContentUrl; }

    public String getImageUrl() { return ImageUrl; }

    public String getDate() { return date; }

    public String getTime() { return time; }

}
