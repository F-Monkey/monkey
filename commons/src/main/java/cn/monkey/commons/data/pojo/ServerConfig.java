package cn.monkey.commons.data.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServerConfig implements Serializable {
    private String id;
    private String type;
    private String name;
    private String url;
    private int maxUserSize;
    private int currentUserSize;
}
