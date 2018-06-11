package com.team1614.lower.consultant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class QueAnsAdapter extends RecyclerView.Adapter<QueAnsAdapter.MyViewHolder> {

    public Context mContext;
    private List<QueAns> albumList;
    public String aa;
    CardView cd;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView que, ans,txt;
        private ImageView share, comment;


        public MyViewHolder(View view) {
            super(view);
            que = (TextView) view.findViewById(R.id.que);
            txt = (TextView) view.findViewById(R.id.txtt1);
            ans = (TextView) view.findViewById(R.id.ans);
            share = (ImageView) view.findViewById(R.id.share);
            comment = (ImageView) view.findViewById(R.id.comment);
            ans = (TextView) view.findViewById(R.id.ans);
            cd=(CardView)view.findViewById(R.id.card_view);
        }
    }

    public QueAnsAdapter(Context mContext, List<QueAns> albumList, String aa) {
        this.mContext = mContext;
        this.albumList = albumList;
        this.aa = aa;
    }

    public QueAnsAdapter(Context mContext, List<QueAns> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ques_ans_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final QueAns album = albumList.get(position);
        final CategoryDatabaseHandler family = new CategoryDatabaseHandler(mContext);
        holder.que.setText(album.getQue());
        holder.ans.setText(album.getAns());
        holder.share.setImageResource(R.drawable.ic_share_black_24dp);
        SharedPreferences mSettings = mContext.getSharedPreferences("fontsize", 0);
        int i = mSettings.getInt("font", 0);
        Log.i("SELECTED SIZE : ", "" + i);
        if (i == 0) {
            holder.que.setTextSize(16);
            holder.ans.setTextSize(14);
        } else if (i == 1) {
            holder.que.setTextSize(19);
            holder.ans.setTextSize(17);
        } else if (i == 2) {
            holder.que.setTextSize(22);
            holder.ans.setTextSize(20);
        } else if (i == 3) {
            holder.que.setTextSize(25);
            holder.ans.setTextSize(23);
        } else if (i == 4) {
            holder.que.setTextSize(28);
            holder.ans.setTextSize(25);
        }
        if (album.getFavourate().equals("true"))
            holder.comment.setImageResource(R.drawable.ic_favorite_black_24dp);
        else holder.comment.setImageResource(R.drawable.ic_favorite_border_black_24dp);



        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ans.getVisibility() == View.GONE) {
                    holder.ans.setVisibility(View.VISIBLE);
                    holder.share.setVisibility(View.VISIBLE);
                    holder.txt.setText("Fold back");
                    if (aa == "NULL") {
                    } else holder.comment.setVisibility(View.VISIBLE);
                } else {
                    holder.ans.setVisibility(View.GONE);
                    holder.share.setVisibility(View.GONE);
                    holder.comment.setVisibility(View.GONE);
                    holder.txt.setText("အေျဖ ၾကည့္မည္ ");
                }
            }
        });

        final String shareTxt = album.getQue() + "\n\n" + album.getAns();
        holder.share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i("Share : ", shareTxt);
                share(mContext, "", shareTxt, "", "Share");
            }


        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Favourite : ", album.getFavourate() + "");
                if (album.getFavourate().equalsIgnoreCase("true") && album.getFavourate() != null) {
                    holder.comment.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    album.setFavourate("false");
                    family.updateFav(album, aa);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Remove from favourite", Toast.LENGTH_SHORT).show();
                } else {

                    holder.comment.setImageResource(R.drawable.ic_favorite_black_24dp);
                    album.setFavourate("true");
                    family.updateFav(album, aa);
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();


                }

            }
        });

    }

    //To Share Txet
    public static void share(Context context, String text, String subject, String title, String dialogHeaderText) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, subject);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        context.startActivity(Intent.createChooser(intent, dialogHeaderText));
    }

    public int getItemCount() {
        return albumList.size();
    }
}
