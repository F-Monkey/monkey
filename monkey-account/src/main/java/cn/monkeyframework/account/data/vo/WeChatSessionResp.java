package cn.monkeyframework.account.data.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class WeChatSessionResp implements Serializable {
    @SerializedName(value = "openId", alternate = "openid")
    private String openId;
    @SerializedName(value = "sessionKey", alternate = "session_key")
    private String sessionKey;
    @SerializedName(value = "unionId", alternate = "unionid")
    private String unionId;
    @SerializedName(value = "errCode", alternate = "errcode")
    private Integer errCode;
    @SerializedName(value = "errMsg", alternate = "errmsg")
    private String errMsg;
}
