package cn.monkeyframework.commons.data.pojo;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> implements Serializable {
    @Transient
    protected ID id;
    protected String creatorId;
    protected String updaterId;
    protected Long createTs;
    protected Long updateTs;
}
