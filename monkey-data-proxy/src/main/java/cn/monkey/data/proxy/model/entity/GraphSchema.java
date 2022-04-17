package cn.monkey.data.proxy.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class GraphSchema implements Serializable {
    private String id;
    private String content;
}
