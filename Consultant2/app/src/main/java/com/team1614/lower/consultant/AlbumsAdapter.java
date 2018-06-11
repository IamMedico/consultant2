package com.team1614.lower.consultant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    public Context mContext;
    private List<Album> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ques, ans;
        public ImageButton share, like;


        public MyViewHolder(View view) {
            super(view);
            share = (ImageButton) view.findViewById(R.id.main_share);
            like = (ImageButton) view.findViewById(R.id.like);
            ques = (TextView) view.findViewById(R.id.question);
            ans = (TextView) view.findViewById(R.id.answer);

        }
    }

    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questionform, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        SharedPreferences mSettings = mContext.getSharedPreferences("fontsize", 0);
        final int i = mSettings.getInt("font", 0);
        Log.i("SELECTED SIZE : ", "" + i);
        if (i == 0) {
            holder.ques.setTextSize(16);
            holder.ans.setTextSize(14);
        } else if (i == 1) {
            holder.ques.setTextSize(19);
            holder.ans.setTextSize(17);
        } else if (i == 2) {
            holder.ques.setTextSize(22);
            holder.ans.setTextSize(20);
        } else if (i == 3) {
            holder.ques.setTextSize(25);
            holder.ans.setTextSize(23);
        } else if (i == 4) {
            holder.ques.setTextSize(28);
            holder.ans.setTextSize(25);
        }
        final Album album = albumList.get(position);
        final CategoryDatabaseHandler family = new CategoryDatabaseHandler(mContext);
        holder.ques.setText(album.getQuestion());
        holder.ans.setText(album.getAnswer());
        holder.share.setImageResource(R.drawable.ic_share_black_24dp);
        if (album.getFavourate().equals("true"))
            holder.like.setImageResource(R.drawable.ic_favorite_black_24dp);
        else holder.like.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        final String shareTxt = album.getQuestion() + "\n\n" + album.getAnswer();
        holder.share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i("Share : ", shareTxt);
                share(mContext, "", shareTxt, "", "Share");
            }


        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Favourite : ", album.getFavourate() + "");
                if (album.getFavourate().equalsIgnoreCase("true") && album.getFavourate() != null) {
                    holder.like.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    album.setFavourate("false");
                    family.updateFav(album);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Remove from favourite", Toast.LENGTH_SHORT).show();
                } else {

                    holder.like.setImageResource(R.drawable.ic_favorite_black_24dp);
                    album.setFavourate("true");
                    family.updateFav(album);
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();

                }

            }


        });
        holder.ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.ans.getMaxLines()==3)
                holder.ans.setMaxLines(20);

                else  holder.ans.setMaxLines(3);
            }
        });


    }

    public static void share(Context context, String text, String subject, String title, String dialogHeaderText) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, subject);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        context.startActivity(Intent.createChooser(intent, dialogHeaderText));
    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
