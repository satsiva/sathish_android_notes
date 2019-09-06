package com.sathish_android_notes.myapplication.ApacheHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sathish_android_notes.myapplication.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//https://medium.com/@suragch/updating-data-in-an-android-recyclerview-842e56adbfd8
//https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example/40584425#40584425

public class RecycleActivity extends AppCompatActivity {

    RecyclerView recycler_view_deals_list;
    private RecycleAdapterrecycle dashboardAdapter;
    private ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> arraylist,arraylist2,socialarraylist;
    private String jsonStr="",homes="";
    ImageView grid_id,list_id;
    JSONArray jObjhomes,jsonallimg;
    String total_record="";

String listgrid="grid";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        //getLayoutInflater().inflate(R.layout.activity_recycle, view_stub);
        recycler_view_deals_list = (RecyclerView) findViewById(R.id.rvid);
        arraylist = new ArrayList<HashMap<String, String>>();


        grid_id=(ImageView)findViewById(R.id.grid_id);
        list_id=(ImageView)findViewById(R.id.list_id);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId","1");
        map.put("id","1");
        map.put("title","Travel Insurance");
        map.put("body","Travel Insurance");
        map.put("image","https://avatars2.githubusercontent.com/u/18204850?s=400&u=44bd7c497eb0cb6bde0254e36c89c4470b29b09a&v=4");
        arraylist.add(map);



        map = new HashMap<String, String>();
        map.put("userId","1");
        map.put("id","1");
        map.put("title","Travel Insurance");
        map.put("body","Travel Insurance");
        map.put("image","https://avatars2.githubusercontent.com/u/18204850?s=400&u=44bd7c497eb0cb6bde0254e36c89c4470b29b09a&v=4");
        arraylist.add(map);

        map = new HashMap<String, String>();
        map.put("userId","1");
        map.put("id","1");
        map.put("title","Travel Insurance");
        map.put("body","Travel Insurance");
        map.put("image","https://avatars2.githubusercontent.com/u/18204850?s=400&u=44bd7c497eb0cb6bde0254e36c89c4470b29b09a&v=4");
        arraylist.add(map);

        map = new HashMap<String, String>();
        map.put("userId","1");
        map.put("id","1");
        map.put("title","Travel Insurance");
        map.put("body","Travel Insurance");
        map.put("image","https://avatars2.githubusercontent.com/u/18204850?s=400&u=44bd7c497eb0cb6bde0254e36c89c4470b29b09a&v=4");
        arraylist.add(map);

        map = new HashMap<String, String>();
        map.put("userId","1");
        map.put("id","1");
        map.put("title","Travel Insurance");
        map.put("body","Travel Insurance");
        map.put("image","https://avatars2.githubusercontent.com/u/18204850?s=400&u=44bd7c497eb0cb6bde0254e36c89c4470b29b09a&v=4");
        arraylist.add(map);


        if( listgrid.equals("grid"))
        {
            int numberOfColumns = 2;//recycle grid
            recycler_view_deals_list.setLayoutManager(new GridLayoutManager( this, numberOfColumns));
            dashboardAdapter = new  RecycleAdapterrecycle(arraylist);
            recycler_view_deals_list.setAdapter(dashboardAdapter);
        }else
        {
//                dashboardAdapter=new RecycleAdapterrecycle(arraylist);//recycle list horizontal
//                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false);
//                recycler_view_deals_list.setLayoutManager(horizontalLayoutManagaer);
//                recycler_view_deals_list.setAdapter(dashboardAdapter);

            dashboardAdapter=new  RecycleAdapterrecycle(arraylist);
            LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false);
            recycler_view_deals_list.setLayoutManager(horizontalLayoutManagaer);
            recycler_view_deals_list.setAdapter(dashboardAdapter);

        }

        grid_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 listgrid="grid";

                int numberOfColumns = 2;//recycle grid
                recycler_view_deals_list.setLayoutManager(new GridLayoutManager( RecycleActivity.this, numberOfColumns));
                dashboardAdapter = new  RecycleAdapterrecycle(arraylist);
                recycler_view_deals_list.setAdapter(dashboardAdapter);

            }
        });

        list_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 listgrid="";

                dashboardAdapter=new  RecycleAdapterrecycle(arraylist);
                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager( RecycleActivity.this, LinearLayoutManager.VERTICAL, false);
                recycler_view_deals_list.setLayoutManager(horizontalLayoutManagaer);
                recycler_view_deals_list.setAdapter(dashboardAdapter);
            }
        });




        if(isNetworkAvailable( this))
            new  DashboardViewActivity().execute();
        else
            Toast.makeText( this, "No internet connection",Toast.LENGTH_LONG).show();


    }



    private class DashboardViewActivity extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog( RecycleActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();



//for post
           /* List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("token","gccgscs8okowckkwog8o8soss40wcw0cc0o0sgcoos0400s84skkkskow4kcwg40"));
            nameValuePairs.add(new BasicNameValuePair("type","" ));
            nameValuePairs.add(new BasicNameValuePair("page_count","0" ));
            nameValuePairs.add(new BasicNameValuePair("q","" ));
            nameValuePairs.add(new BasicNameValuePair("country","Bahrain" ));
            nameValuePairs.add(new BasicNameValuePair("user_id","" ));
            jsonStr = sh.makeServiceCall("http://urbansoft.us/kpmg/DocumentsService/getDocuments", 2,nameValuePairs);
            */




            jsonStr = sh.makeServiceCall("http://api.myjson.com/bins/1aha2n", 1);
            Log.d("Response: ", "> " + jsonStr);

            return null;
        }






        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

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



            arraylist = new ArrayList<HashMap<String, String>>();
            if (jsonStr != null)
            {
                try {
                   // JSONObject myobj = new JSONObject(jsonStr);

                    JSONArray jarray=new JSONArray(jsonStr);
                    for(int i=0;i<jarray.length();i++)
                    {
                        HashMap<String,String> map=new HashMap<>();
                        JSONObject curr=jarray.getJSONObject(i);
                        map.put("userId",curr.getString("userId"));
                        map.put("id",curr.getString("id"));
                        map.put("title",curr.getString("title"));
                        map.put("body",curr.getString("body"));
                        map.put("image",curr.getString("image"));
                        arraylist.add(map);
                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }
            else
            {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }


            if( listgrid.equals("grid"))
            {
                int numberOfColumns = 2;//recycle grid
                recycler_view_deals_list.setLayoutManager(new GridLayoutManager( RecycleActivity.this, numberOfColumns));
                dashboardAdapter = new  RecycleAdapterrecycle(arraylist);
                recycler_view_deals_list.setAdapter(dashboardAdapter);
            }else
            {
//                dashboardAdapter=new RecycleAdapterrecycle(arraylist);//recycle list horizontal
//                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false);
//                recycler_view_deals_list.setLayoutManager(horizontalLayoutManagaer);
//                recycler_view_deals_list.setAdapter(dashboardAdapter);

                dashboardAdapter=new  RecycleAdapterrecycle(arraylist);

                LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager( RecycleActivity.this, LinearLayoutManager.VERTICAL, false);
                recycler_view_deals_list.setLayoutManager(horizontalLayoutManagaer);
                recycler_view_deals_list.setAdapter(dashboardAdapter);

            }




        }
    }





    public class RecycleAdapterrecycle extends RecyclerView.Adapter< RecycleAdapterrecycle.MyViewHolder> {
        ArrayList<HashMap<String,String>> dataSet;
        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView img;
            TextView txt1,txt2,txt3;

            Button insertsingleitem,insertmultipleitem,removesingleitem,removemultipleitems,removeallitems,replaceoldlistwithnewlist,
                    updatesingleitem,movesingleitem;


            public MyViewHolder(View itemView) {
                super(itemView);
                this.txt1 = (TextView) itemView.findViewById(R.id.title);
                this.img = (ImageView) itemView.findViewById(R.id.image);
                this.insertsingleitem = (Button) itemView.findViewById(R.id.insertsingleitem);
                this.insertmultipleitem = (Button) itemView.findViewById(R.id.insertmultipleitem);
                this.removesingleitem = (Button) itemView.findViewById(R.id.removesingleitem);
                this.removemultipleitems = (Button) itemView.findViewById(R.id.removemultipleitems);
                this.removeallitems = (Button) itemView.findViewById(R.id.removeallitems);
                this.replaceoldlistwithnewlist = (Button) itemView.findViewById(R.id.replaceoldlistwithnewlist);
                this.updatesingleitem = (Button) itemView.findViewById(R.id.updatesingleitem);
                this.movesingleitem = (Button) itemView.findViewById(R.id.movesingleitem);




                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        int Current_Postion=getAdapterPosition();

                    }
                });

            }
        }

        public RecycleAdapterrecycle(ArrayList<HashMap<String,String>> socialarraylistnew) {
            this.dataSet = socialarraylistnew;
        }

        @Override
        public  RecycleAdapterrecycle.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if( listgrid.equals("grid"))
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
                 RecycleAdapterrecycle.MyViewHolder myViewHolder = new  RecycleAdapterrecycle.MyViewHolder(view);
                return myViewHolder;
            }else
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
                 RecycleAdapterrecycle.MyViewHolder myViewHolder = new  RecycleAdapterrecycle.MyViewHolder(view);
                return myViewHolder;
            }


        }

        @Override
        public void onBindViewHolder(final  RecycleAdapterrecycle.MyViewHolder holder, final int listPosition) {
            ImageView img_str = holder.img;
            TextView txt1_str = holder.txt1;
            Button insertsingleitem=holder.insertsingleitem;
            insertsingleitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("userId","10000");
                    map.put("id","1");
                    map.put("title","Insert singleitem");
                    map.put("body","Travel Insurance");
                    map.put("image","https://avatars2.githubusercontent.com/u/18204850?s=400&u=44bd7c497eb0cb6bde0254e36c89c4470b29b09a&v=4");
                    //dataSet.add(map);

                    //String item = "Pig";
                    //int insertIndex = 2;
                    dataSet.add(listPosition, map);
                    dashboardAdapter.notifyItemInserted(listPosition);

                }
            });




            Button insertmultipleitem=holder.insertmultipleitem;
            insertmultipleitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {




                    ArrayList<HashMap<String, String>> newarraylist = new ArrayList<HashMap<String, String>>();


                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("userId","20000");
                    map.put("id","1");
                    map.put("title","Insert multipleitem 1");
                    map.put("body","Travel Insurance");
                    map.put("image","https://avatars2.githubusercontent.com/u/18204850?s=400&u=44bd7c497eb0cb6bde0254e36c89c4470b29b09a&v=4");

                    newarraylist.add(map);

                    map = new HashMap<String, String>();
                    map.put("userId","30000");
                    map.put("id","1");
                    map.put("title","Insert multipleitem 2");
                    map.put("body","Travel Insurance");
                    map.put("image","https://avatars2.githubusercontent.com/u/18204850?s=400&u=44bd7c497eb0cb6bde0254e36c89c4470b29b09a&v=4");
                    newarraylist.add(map);

                    map = new HashMap<String, String>();
                    map.put("userId","40000");
                    map.put("id","1");
                    map.put("title","Insert multipleitem 3");
                    map.put("body","Travel Insurance");
                    map.put("image","https://avatars2.githubusercontent.com/u/18204850?s=400&u=44bd7c497eb0cb6bde0254e36c89c4470b29b09a&v=4");

                    newarraylist.add(map);


                    int insertIndex = listPosition;
                    dataSet.addAll(insertIndex, newarraylist);
                    dashboardAdapter.notifyItemRangeInserted(insertIndex, newarraylist.size());



                }
            });







            Button removesingleitem=holder.removesingleitem;
            removesingleitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    int removeIndex = listPosition;
                    dataSet.remove(removeIndex);
                    dashboardAdapter.notifyItemRemoved(removeIndex);

                }
            });



            Button removemultipleitems=holder.removemultipleitems;
            removemultipleitems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    int startIndex = 2; // inclusive
                    int endIndex = 4;   // exclusive
                    int count = endIndex - startIndex; // 2 items will be removed
                    dataSet.subList(startIndex, endIndex).clear();
                    dashboardAdapter.notifyItemRangeRemoved(startIndex, count);



                }
            });



            Button removeallitems=holder.removeallitems;
            removeallitems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    dataSet.clear();
                    dashboardAdapter.notifyDataSetChanged();



                }
            });



            Button replaceoldlistwithnewlist=holder.replaceoldlistwithnewlist;
            replaceoldlistwithnewlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    dataSet.clear();


                    ArrayList<HashMap<String, String>> newarraylist = new ArrayList<HashMap<String, String>>();


                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("userId","20000");
                    map.put("id","1");
                    map.put("title","Replace New 1");
                    map.put("body","Travel Insurance");
                    map.put("image","https://avatars2.githubusercontent.com/u/18204850?s=400&u=44bd7c497eb0cb6bde0254e36c89c4470b29b09a&v=4");

                    newarraylist.add(map);

                    map = new HashMap<String, String>();
                    map.put("userId","30000");
                    map.put("id","1");
                    map.put("title","Replace New 2");
                    map.put("body","Travel Insurance");
                    map.put("image","https://avatars2.githubusercontent.com/u/18204850?s=400&u=44bd7c497eb0cb6bde0254e36c89c4470b29b09a&v=4");
                    newarraylist.add(map);



                    dataSet.addAll(newarraylist);
                    dashboardAdapter.notifyDataSetChanged();



                }
            });








            Button updatesingleitem=holder.updatesingleitem;
            updatesingleitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    ArrayList<HashMap<String, String>> newarraylist = new ArrayList<HashMap<String, String>>();


                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("userId",dataSet.get(listPosition).get("userId"));
                    map.put("id",dataSet.get(listPosition).get("id"));
                    map.put("title",dataSet.get(listPosition).get("title")+"---"+"update New value");
                    map.put("body",dataSet.get(listPosition).get("body"));
                    map.put("image",dataSet.get(listPosition).get("image"));


                    int updateIndex = listPosition;
                    dataSet.set(updateIndex, map);
                    dashboardAdapter.notifyItemChanged(updateIndex);

                }
            });


            Button movesingleitem=holder.movesingleitem;
            movesingleitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int fromPosition = listPosition;
                    int toPosition = 1;
// update data array
                    HashMap item = dataSet.get(fromPosition);
                    dataSet.remove(fromPosition);
                    dataSet.add(toPosition, item);
// notify adapter
                    dashboardAdapter.notifyItemMoved(fromPosition, toPosition);

                }
            });








            String s1 = dataSet.get(listPosition).get("userId");
            String s2 = dataSet.get(listPosition).get("id");
            String s3 = dataSet.get(listPosition).get("title");
            String s4 = dataSet.get(listPosition).get("body");
            String s5 = dataSet.get(listPosition).get("image");

            Glide.with(getApplicationContext())
                    .load(s5)
                    .asBitmap()
                    .animate(R.anim.abc_fade_in)
                    .into(img_str);

            txt1_str.setText(s3);

        }
        @Override
        public int getItemCount() {
            return dataSet.size();
        }}






    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null) {
            return false;
        } else {
            if (info.isConnected()) {
                return true;
            } else {
                return true;
            }

        }
    }
}




/*
[
  {
    "userId": 1,
    "id": 1,
    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto",
    "image": "https://avatars2.githubusercontent.com/u/18204850?s=400&u=44bd7c497eb0cb6bde0254e36c89c4470b29b09a&v=4"
  },
  {
    "userId": 1,
    "id": 2,
    "title": "qui est esse",
    "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla",
    "image": "https://avatars2.githubusercontent.com/u/18204850?s=400&u=44bd7c497eb0cb6bde0254e36c89c4470b29b09a&v=4"
  },
  {
    "userId": 1,
    "id": 3,
    "title": "ea molestias quasi exercitationem repellat qui ipsa sit aut",
    "body": "et iusto sed quo iure\nvoluptatem occaecati omnis eligendi aut ad\nvoluptatem doloribus vel accusantium quis pariatur\nmolestiae porro eius odio et labore et velit aut",
    "image": "https://avatars2.githubusercontent.com/u/18204850?s=400&u=44bd7c497eb0cb6bde0254e36c89c4470b29b09a&v=4"
  },
  {
    "userId": 1,
    "id": 4,
    "title": "eum et est occaecati",
    "body": "ullam et saepe reiciendis voluptatem adipisci\nsit amet autem assumenda provident rerum culpa\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\nquis sunt voluptatem rerum illo velit",
    "image": "https://avatars2.githubusercontent.com/u/18204850?s=400&u=44bd7c497eb0cb6bde0254e36c89c4470b29b09a&v=4"
  },
  {
    "userId": 1,
    "id": 5,
    "title": "nesciunt quas odio",
    "body": "repudiandae veniam quaerat sunt sed\nalias aut fugiat sit autem sed est\nvoluptatem omnis possimus esse voluptatibus quis\nest aut tenetur dolor neque",
    "image": "https://avatars2.githubusercontent.com/u/18204850?s=400&u=44bd7c497eb0cb6bde0254e36c89c4470b29b09a&v=4"
  }
]*/