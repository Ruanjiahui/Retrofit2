package ruan.com.View;

import ruan.com.View.BaseRecycleView.BaseAdapterResp;

/**
 * recyclerView 点击事件
 *
 * Created by 19820 on 2018/4/24.
 */

public interface OnItemClickListener {

    /**
     * 列表点击
     * @param resp          返回对象
     * @param Type          类型
     * @param position      点击列表标识
     */
    public void OnItemClick(BaseAdapterResp resp , int Type , int position);

}
