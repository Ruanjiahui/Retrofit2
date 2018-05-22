package ruan.com.retrofit2;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;
import ruan.com.View.BaseRecycleView.BaseAdapterResp;

/**
 * Created by 19820 on 2018/4/22.
 */
@Setter
@Getter
public class ItemData extends BaseAdapterResp {

    private String item_t;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
