package com.example.krishna.youtube;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishna.youtube.pojo.Item;
import com.example.krishna.youtube.pojo.Thumbnails;
import com.example.krishna.youtube.pojo.example;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.id.list;
import static java.security.AccessController.getContext;

public class MainActivity extends YouTubeBaseActivity {
    RecyclerView recyclerView;
    ArrayList<Item> data;
    ListViewAdapter adapter;
    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.edit);
        button=(Button)findViewById(R.id.button);
        recyclerView=(RecyclerView)findViewById(R.id.recycle);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=editText.getText().toString();
                retrofit2.Retrofit retrofit=new retrofit2.Retrofit.Builder()
                        .baseUrl("https://www.googleapis.com/youtube/v3/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final APicall service=retrofit.create(APicall.class);
                Call<example> call=service.getJSON(25,s);
                call.enqueue(new Callback<example>() {
                    @Override
                    public void onResponse(Call<example> call, Response<example> response) {
                        example json=response.body();
                        //  Thumbnails json1=response.body();
                        data=new ArrayList<Item>(json.getItems());
                        Log.d("ss", String.valueOf(data.size()));
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                   Decorator decorate=new Decorator(MainActivity.this);
                        //                  recyclerView.addItemDecoration(decorate);
                        adapter = new ListViewAdapter(data);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<example> call, Throwable t) {

                    }
                });



            }
        });


}

    private class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {
        ArrayList<Item> data=new ArrayList<>();
        public ListViewAdapter(ArrayList<Item> data) {
            this.data=data;
        }

        @Override
        public ListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
            return new ViewHolder(view);


        }

        @Override
        public void onBindViewHolder(ListViewAdapter.ViewHolder holder, int position) {
            holder.text.setText(data.get(position).getSnippet().getTitle());
            Picasso.with(MainActivity.this).load(data.get(position).getSnippet().getThumbnails().getDefault().getUrl()).fit().into(holder.image);
holder.text1.setText(data.get(position).getId().getVideoId());
        }

        @Override
        public int getItemCount() {
          return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView text,text1;
            public ViewHolder(final View itemView) {
                super(itemView);
                image=(ImageView)itemView.findViewById(R.id.image);
                text=(TextView)itemView.findViewById(R.id.text);
                text1=(TextView)itemView.findViewById(R.id.text2) ;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainActivity.this,SecondActivtiy.class);
intent.putExtra("id",text1.getText().toString());
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,image,"profile");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            startActivity(intent,options.toBundle());
                        }
                    }
                });
            }
        }
    }
}
