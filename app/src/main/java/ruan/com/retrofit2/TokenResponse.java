package ruan.com.retrofit2;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tanxinye on 2016/10/23.
 */
public class TokenResponse extends BaseResponse {

    @SerializedName("token")
    private String token;

    @SerializedName("game_status")
    private String game_status;

    public String getGame_status() {
        return game_status;
    }

    public void setGame_status(String game_status) {
        this.game_status = game_status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
