package ro.example.lab.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ro.example.lab.R;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardItemViewHolder> {

    /**
     * Dummy items to populate list
     */
    static final int[] imgList = {R.drawable.p1,
                     R.drawable.p2,
                     R.drawable.p3,
                     R.drawable.p4,
                     R.drawable.p5,
                     R.drawable.p6
    };

    String[] nameList = {"One", "Two", "Three", "Four", "Five"};

    @NonNull
    @Override
    public DashboardItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard, parent, false);
        DashboardItemViewHolder viewHolder = new DashboardItemViewHolder(layoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardItemViewHolder holder, int position) {
        holder.getTextView().setText(nameList[position]);
        holder.getImageView().setImageResource(imgList[position]);
    }

    @Override
    public int getItemCount() {
        return imgList.length;
    }
}