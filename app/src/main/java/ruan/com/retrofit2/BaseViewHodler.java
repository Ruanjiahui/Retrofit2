package ruan.com.retrofit2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 19820 on 2018/4/22.
 */

public class BaseViewHodler extends RecyclerView.ViewHolder {


    private TextView item;

    public BaseViewHodler(View itemView , int ViewType) {
        super(itemView);

        if (ViewType == 1){
            item = itemView.findViewById(R.id.item);
        }

    }


    public void setItemView(BaseAdapterResp resp , int ViewType , int position){
        if (ViewType == 1){
            ItemData itemData = (ItemData) resp;
            item.setText(itemData.getItem_t());
        }
    }
}
