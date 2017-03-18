package com.reena.jombaytest.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reena.jombaytest.R;
import com.reena.jombaytest.models.PhotoGalleryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by RA on 3/18/2017.
 */

public class PhotosRecyclerAdapter extends RecyclerView.Adapter<PhotosRecyclerAdapter.PhotoViewHolder> {

    private ArrayList<PhotoGalleryModel> mArrPhotos;
    private Context mContext;

    /**
     * With Dataset and Reference of Activity
     *
     * @param context   - Reference of Activity
     * @param arrPhotos - Dataset Collection
     */
    public PhotosRecyclerAdapter(Context context, ArrayList<PhotoGalleryModel> arrPhotos) {
        this.mArrPhotos = arrPhotos;
        this.mContext = context;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {

        // Description of photos
        String description = mArrPhotos.get(position).getTitle();
        // Photos Url
        String imgUrl = mArrPhotos.get(position).getThumbnailUrl();
        if ((null != description) || (null != imgUrl)) {
            holder.tvDescription.setText(mArrPhotos.get(position).getTitle());
            Picasso.with(mContext)
                    .load(imgUrl)
                    .error(ContextCompat.getDrawable(mContext, R.drawable.ic_launcher))
                    .into(holder.ivPhotos);
        }
    }


    @Override
    public int getItemCount() {
        return mArrPhotos.size();
    }

    /**
     * ViewHolder class fo
     */
    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        TextView tvDescription;
        ImageView ivPhotos;

        /***
         * Constructor who holds view
         * @param view
         */
        public PhotoViewHolder(View view) {
            super(view);
            tvDescription = (TextView) view.findViewById(R.id.tvDescription);
            ivPhotos = (ImageView) view.findViewById(R.id.ivPhoto);
        }

    }
}
