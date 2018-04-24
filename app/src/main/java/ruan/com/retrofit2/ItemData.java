package ruan.com.retrofit2;

import com.google.gson.Gson;

import ruan.com.View.BaseRecycleView.BaseAdapterResp;

/**
 * Created by 19820 on 2018/4/22.
 */
public class ItemData extends BaseAdapterResp {

    private String item_t;

    public String getItem_t() {
        return item_t;
    }

    public void setItem_t(String item_t) {
        this.item_t = item_t;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
