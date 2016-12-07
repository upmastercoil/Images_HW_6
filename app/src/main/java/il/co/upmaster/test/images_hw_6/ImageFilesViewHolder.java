package il.co.upmaster.test.images_hw_6;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class ImageFilesViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageViewFile;

    public ImageFilesViewHolder(View itemView) {

        super(itemView);
        imageViewFile = (ImageView) itemView.findViewById(R.id.linearListItemImageView);
    }
}
