package com.sathish_android_notes.myapplication.ReadXMLdata;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sathish_android_notes.myapplication.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class NewsList extends AppCompatActivity {

    private ProgressDialog pDialog;
    private String jsonStr;
    ArrayList<HashMap<String, String>> arraylist,arraylist1;
    Context context=NewsList.this;
    private static DisplayImageOptions imageOptions2;
    ListView my_listview;
    CustomAdapter cadapter;
    ImageView back,bac,newstop;
    TextView empty,newsheadtxt,created_date;
    String news ="";
    JSONArray jObjhomes;
    String title ="",date ="",img ="",cont="";
    RelativeLayout newsheader;
    ScrollView scrollView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        initImageLoader(context);


        my_listview=(ListView) findViewById(R.id.listView3);
        newstop=(ImageView) findViewById(R.id.newstop);
        newsheadtxt=(TextView) findViewById(R.id.newsheadtxt);
        created_date=(TextView) findViewById(R.id.created_date);
        newsheader=(RelativeLayout) findViewById(R.id.newsheader);
        scrollView3=(ScrollView) findViewById(R.id.scrollView3);



        if(LibraryHelper.isNetworkAvailable(context))
            new NewsListData().execute();
        else
            Toast.makeText(context, Constants.networkfailuremsg, Toast.LENGTH_LONG).show();
    }



    @Override
    public void onPause() {
        super.onPause();

        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            pDialog = null;
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {
            pDialog = null;
        }
    }

    private class NewsListData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewsList.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance

            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

            String curr_locale1= Locale.getDefault().getDisplayLanguage();
            String url="";
            if(!Constants.curr_lang.equals("en")) {
                url = "http://www.bahrainbourse.com/Services/newsxml.aspx?language=ar";
            } else {
                url = "http://www.bahrainbourse.com/Services/NewsXML.aspx";
            }

            // Making a request to url and getting response
            jsonStr = sh.makeServiceCall(url, 2, nameValuePairs);
            Log.d("Response: ", "> " + jsonStr);
            arraylist = new ArrayList<HashMap<String, String>>();
            arraylist1 = new ArrayList<HashMap<String, String>>();
            if (jsonStr != null) {
                XMLParser parser = new XMLParser();
                Document doc = parser.getDomElement(jsonStr); // getting DOM element
                if(doc!=null) {
                    NodeList nl = doc.getElementsByTagName("NewsStart");
                    for (int i = 0; i < nl.getLength(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        Element e = (Element) nl.item(i);
                        // adding each child node to HashMap key => value
                        map.put("id", parser.getValue(e, "NewsID"));

                        NodeList title = e.getElementsByTagName("NewsHeading");
                        Element line = (Element) title.item(0);
                        String headingstr = getCharacterDataFromElement(line);
                        map.put("head", headingstr);

                        map.put("image", parser.getValue(e, "NewsImage"));
                        map.put("date", parser.getValue(e, "NewsDate"));

                        title = e.getElementsByTagName("NewsShortDescription");
                        line = (Element) title.item(0);
                        headingstr = getCharacterDataFromElement(line);
                        map.put("shortdesc", headingstr);


                        title = e.getElementsByTagName("NewsLongDescription");
                        line = (Element) title.item(0);
                        headingstr = getCharacterDataFromElement(line);
                        map.put("longdesc", headingstr);
                        if (i == 0) {
                            arraylist1.add(map);
                        } else {
                            arraylist.add(map);
                        }
                    }
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
                NewsList.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(NewsList.this,Constants.networkfailuremsg,Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            try {
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                pDialog = null;
            } catch (final IllegalArgumentException e) {
                // Handle or log or ignore
            } catch (final Exception e) {
                // Handle or log or ignore
            } finally {
                pDialog = null;
            }
            if (arraylist.isEmpty()) {
               // empty.setVisibility(View.VISIBLE);
            }
            else
            {
                String imgstr=arraylist1.get(0).get("image");
                String headingstr=arraylist1.get(0).get("head");
                String created_date1=arraylist1.get(0).get("date");
                created_date.setText(created_date1);
                created_date.setText(LibraryHelper.ChangeDateFormat(created_date1, "EEEE,MMMM dd,yyyy", "EEEE, dd MMM , yyyy"));

                newsheadtxt.setText(headingstr);
                if(imgstr.equals("")||imgstr.equals(null)||imgstr.equals("null")){

                }
                else{
                    ImageLoader.getInstance().displayImage(imgstr, newstop,imageOptions2);
                }
            }


            cadapter = new CustomAdapter(NewsList.this, arraylist);
            my_listview.setAdapter(cadapter);
            Helper.getListViewSize(my_listview);

            my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                    //here intent move to detailed realted page
                   /* Intent in = new Intent(context, BhbNews.class);
                    String ids=arraylist.get(position).get("id");
                    String head=arraylist.get(position).get("head");
                    String image=arraylist.get(position).get("image");
                    String date=arraylist.get(position).get("date");
                    String shortdesc=arraylist.get(position).get("shortdesc");
                    String longdesc=arraylist.get(position).get("longdesc");
                    in.putExtra("NewsID",ids);
                    in.putExtra("NewsHeading",head);
                    in.putExtra("NewsImage",image);
                    in.putExtra("NewsDate",date);
                    in.putExtra("NewsShortDescription",shortdesc);
                    in.putExtra("NewsLongDescription",longdesc);

                    startActivity(in);*/

                }
            });

            scrollView3.fullScroll(ScrollView.FOCUS_UP);

        }
    }
    public static String    getCharacterDataFromElement(Element e) {
        String ret="";
        for(int i=0;i<e.getChildNodes().getLength();i++)
        {
            Node n=e.getChildNodes().item(i);

            if (n instanceof CharacterData) {
                    CharacterData cd = (CharacterData) n;
                    ret+=cd.getData();
                    //return cd.getData();
            }

        }
        return ret;
        //Node child = e.getFirstChild();

       // return "";
    }

    public class CustomAdapter extends BaseAdapter {

        Context context;
        ArrayList<HashMap<String,String>> data1;
        LayoutInflater inflater;

        private  class ViewHolder{
            public ImageView news_image;
            public TextView created_date,news_head,news_date,news_short,news_long;

        }
        public CustomAdapter(Activity context, ArrayList<HashMap<String,String>> arrayList1){

            this.context=context;
            this.data1=arrayList1;
            inflater=LayoutInflater.from(this.context);
        }

        public int getCount() {
            return data1.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            View rowView2=convertView;
            if(rowView2==null){
                rowView2 =inflater.inflate(R.layout.activity_news_list_items,null);
                ViewHolder viewHolder=new ViewHolder();
                viewHolder.news_head=(TextView) rowView2.findViewById(R.id.news_title);
                viewHolder.news_image=(ImageView) rowView2.findViewById(R.id.news_image);
                viewHolder.created_date=(TextView) rowView2.findViewById(R.id.created_date);
              //  viewHolder.news_dat=(TextView) rowView2.findViewById(R.id.news_date);
               // viewHolder.news_cont=(TextView) rowView2.findViewById(R.id.news_cont);
               // viewHolder.news_image=(ImageView) rowView2.findViewById(R.id.news_image);
                rowView2.setTag(viewHolder);

            }
            ViewHolder holder=(ViewHolder) rowView2.getTag();
            String s1 = data1.get(position).get("head");
            String s2 = data1.get(position).get("image");
            String s3 = data1.get(position).get("date");
           // String s3 = data1.get(position).get("cat_cn");

           //s3=LibraryHelper.ChangeDateFormat(s3,"EEEE, MMM dd,yyyy","EEEE, dd MMM, yyyy");

            holder.news_head.setText(s1);
           // holder.created_date.setText(s3);yyyy-MM-dd'T'HH:mm:ss.SSS
            holder.created_date.setText(LibraryHelper.ChangeDateFormat(s3, "EEEE,MMMM dd,yyyy", "EEEE, dd MMM, yyyy"));
           // holder.news_dat.setText(s2);
           // holder.news_cont.setText(s3);
          //  holder.news_title.setText(s1);
         if(s2.equals("")||s2.equals(null)||s2.equals("null")){

}
            else{
         ImageLoader.getInstance().displayImage(s2, holder.news_image,imageOptions2);
}
          //  ImageLoader.getInstance().displayImage(s2, holder.news_image,imageOptions2);
            return rowView2;
        }

    }

    public static void initImageLoader(Context context) {
        imageOptions2 = new DisplayImageOptions.Builder()//display option
                .cacheInMemory(true)
                .resetViewBeforeLoading(true)

                //.displayer(new RoundedBitmapDisplayer(0))
                .build();
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.defaultDisplayImageOptions(imageOptions2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        ImageLoader.getInstance().init(config.build());
    }

}
