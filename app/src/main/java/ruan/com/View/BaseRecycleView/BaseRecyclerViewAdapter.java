package ruan.com.View.BaseRecycleView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ruan.com.View.OnItemClickListener;

/**
 * Created by 19820 on 2018/4/22.
 */

public class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHodler> {

    private ArrayList<BaseAdapterResp> arrayList;

    private Context context;

    private int layoutID;

    private int Type;

    private OnItemClickListener onItemClickLisneter;

    public BaseRecyclerViewAdapter(Context context , int layoutID , int Type , ArrayList<BaseAdapterResp> arrayList){
        this.context = context;
        this.layoutID = layoutID;
        this.Type = Type;
        this.arrayList = arrayList;
    }

    /**
     * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * . Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary  calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(RecyclerView.ViewHolder, int)
     */
    @Override
    public BaseViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutID , parent , false);
        return new BaseViewHodler(view , Type);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link RecyclerView.ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override  instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(BaseViewHodler holder, int position) {
        //设置显示
        holder.setItemView(arrayList.get(position) , Type , position);
        //点击事件
        holder.setItemClick(arrayList.get(position) , onItemClickLisneter , Type , position);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    /**
     * adpter 点击事件
     * @param onItemClickLisneter
     */
    public void setOnItemClickLisneter(OnItemClickListener onItemClickLisneter){
        this.onItemClickLisneter = onItemClickLisneter;
    }

}
