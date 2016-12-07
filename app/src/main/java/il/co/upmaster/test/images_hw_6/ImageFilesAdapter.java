package il.co.upmaster.test.images_hw_6;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ImageFilesAdapter extends RecyclerView.Adapter<ImageFilesViewHolder> {

    private List<ImageFile> files;
    private Context context;

    public ImageFilesAdapter(List<ImageFile> files, Context context) {
        this.files = files;
        this.context = context;
    }

    @Override
    public ImageFilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_list_item, parent, false);
        return new ImageFilesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageFilesViewHolder holder, int position) {

        //throw new Exception();

        // In anonymous class variables must be final. to not change the method's signature we create a final copy of the position and call it
        final int pos = position;
        ImageFile file = files.get(position);
        //holder.titleTextView.setText(book.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //bookClickListener.onBookClicked(pos);
            }
        });





    }

    @Override
    public int getItemCount() {
        return files.size();
    }
}
