package com.team1614.lower.consultant.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.team1614.lower.consultant.R;
import com.team1614.lower.consultant.models.Post;


public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public ImageView commemtView;
    public TextView numStarsView;
    public TextView numComment;
    public TextView bodyView;
    public TextView categoryView;

    public PostViewHolder(View itemView) {
        super(itemView);

        categoryView = (TextView) itemView.findViewById(R.id.post_category);
        titleView = (TextView) itemView.findViewById(R.id.post_title);
        authorView = (TextView) itemView.findViewById(R.id.post_author);
        starView = (ImageView) itemView.findViewById(R.id.star);
        commemtView=(ImageView)itemView.findViewById(R.id.online_comment);
        numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);
        numComment = (TextView) itemView.findViewById(R.id.post_num_comment);

        bodyView = (TextView) itemView.findViewById(R.id.post_body);

    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText("အေၾကာင္းရာ :");
        titleView.setText(post.title);
        authorView.setText(post.author);
        numStarsView.setText(String.valueOf(post.starCount));
        numComment.setText(String.valueOf(post.commentCount));
        bodyView.setText(post.body);
        starView.setOnClickListener(starClickListener);
    }
}
