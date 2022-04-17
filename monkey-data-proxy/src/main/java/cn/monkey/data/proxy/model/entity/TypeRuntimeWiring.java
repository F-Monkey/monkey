package cn.monkey.data.proxy.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TypeRuntimeWiring implements Serializable {
    private String id;
    private String typeName;
    private String fieldName;
}
