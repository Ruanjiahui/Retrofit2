package ruan.com.retrofit2;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;
import ruan.com.Net.HttpManager.HttpResponse;

/**
 * Created by 19820 on 2018/4/30.
 */
@Getter
@Setter
public class HttpBaseResp extends HttpResponse {

    private String msg;

    private int code;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
