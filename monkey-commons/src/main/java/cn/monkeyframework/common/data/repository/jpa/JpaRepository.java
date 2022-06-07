package cn.monkeyframework.common.data.repository.jpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaRepository<T, ID> extends JpaRepositoryImplementation<T, ID>, JpaSpecificationExecutor<T> {
}
