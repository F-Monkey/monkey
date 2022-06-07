package cn.monkeyframework.common.data.repository.jpa;

import cn.monkeyframework.common.data.cache.user.UserLocalCache;
import cn.monkeyframework.common.data.pojo.BaseEntity;
import cn.monkeyframework.common.data.pojo.User;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.lang.Nullable;

import javax.persistence.EntityManager;
import java.util.Date;

public class DefaultJpaRepository<T, ID> extends SimpleJpaRepository<T, ID> implements JpaRepository<T, ID> {

    protected UserLocalCache userLocalCache;

    public DefaultJpaRepository(JpaEntityInformation<T, ?> entityInformation,
                                EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public DefaultJpaRepository(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    public void setUserLocalCache(@Nullable UserLocalCache userLocalCache) {
        this.userLocalCache = userLocalCache;
    }

    protected <S extends BaseEntity<?>> void beforeSave(S entity) {
        long time = new Date().getTime();
        String uid = null;
        if (null != this.userLocalCache) {
            User user = this.userLocalCache.get();
            if (null != user) {
                uid = user.getId();
            }
        }
        entity.setUpdateTs(time);
        entity.setUpdaterId(uid);
        if (null == entity.getId()) {
            entity.setCreateTs(time);
            entity.setCreatorId(uid);
        }
    }


    @Override
    public <S extends T> S save(S entity) {
        if (entity instanceof BaseEntity) {
            this.beforeSave((BaseEntity<?>) entity);
        }
        return super.save(entity);
    }
}
