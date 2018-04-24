package ruan.com.View.BaseRecycleView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ruan.com.View.OnItemClickListener;
import ruan.com.retrofit2.ItemData;
import ruan.com.retrofit2.R;

/**
 * Created by 19820 on 2018/4/22.
 */

public class BaseViewHodler extends RecyclerView.ViewHolder {


    private TextView item;

    public BaseViewHodler(View itemView, int ViewType) {
        super(itemView);

        if (ViewType == 1) {
            item = itemView.findViewById(R.id.item);
        }

    }


    public void setItemView(BaseAdapterResp resp, int ViewType, int position) {
        if (ViewType == 1) {
            ItemData itemData = (ItemData) resp;
            item.setText(itemData.getItem_t());
        }
    }

    /**
     * 列表点击事件
     *
     * @param resp                点击事件返回数据
     * @param onItemClickLisneter 点击事件触发接口
     * @param ViewType            点击事件类型
     * @param position            点击事件的标识
     */
    public void setItemClick(final BaseAdapterResp resp, final OnItemClickListener onItemClickLisneter, final int ViewType, final int position) {
        if (ViewType == 1) {
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickLisneter != null) {
                        onItemClickLisneter.OnItemClick(resp, ViewType, position);
                    }
                }
            });
        }
    }
}
