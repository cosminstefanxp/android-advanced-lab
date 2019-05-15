package ro.example.lab.dashboard;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import ro.example.lab.R;

public class DashboardItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImageView;
    private TextView mTextView;

    public DashboardItemViewHolder(View v) {
        super(v);

        mImageView = v.findViewById(R.id.imageView_item);
        mTextView = v.findViewById(R.id.textView_item);
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setImageView(ImageView mImageView) {
        this.mImageView = mImageView;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setTextView(TextView mTextView) {
        this.mTextView = mTextView;
    }
}
