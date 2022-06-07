package cn.monkey.order.repository;

import cn.monkey.order.model.dto.OrderQueryRequest;
import cn.monkey.order.model.pojo.Order;
import cn.monkey.order.model.pojo.OrderDetail;
import cn.monkeyframework.common.data.repository.jpa.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    @Query(value =
            "SELECT o.* FROM " + Order.TABLE_NAME + " AS o " +
                    "WHERE o.uid = :#{#queryRequest.uid} " +
                    "and (if (:#{#queryRequest.state} IS NOT NULL, o.state = :#{#queryRequest.state}, 1=1)) " +
                    "and (if (:#{#queryRequest.queryKey} IS NOT NULL AND :#{#queryRequest.queryKey} != '', " +
                    "   o.id IN (SELECT od.orderId FROM " + OrderDetail.TABLE_NAME + " AS od WHERE od.orderId = o.id AND od.goodsName LIKE CONCAT('%',:#{#queryRequest.queryKey},'%')), 1=1)) " +
                    "ORDER BY o.updateTs DESC ",
            countQuery =
                    "SELECT o.* FROM " + Order.TABLE_NAME + " AS o " +
                            "WHERE o.uid = :#{#queryRequest.uid} " +
                            "and (if (:#{#queryRequest.state} IS NOT NULL, o.state = :#{#queryRequest.state}, 1=1)) " +
                            "and (if (:#{#queryRequest.queryKey} IS NOT NULL AND :#{#queryRequest.queryKey} != '', " +
                            "   o.id IN (SELECT od.orderId FROM order_detail AS od WHERE od.orderId = o.id AND od.goodsName LIKE CONCAT('%',:#{#queryRequest.queryKey},'%')), 1=1))",
            nativeQuery = true)
        /*
         * todo
         * 这边有个bug，分页数< 5 时会抛出 "Could not locate named parameter [__$synthetic$__1], expecting one of [__$synthetic$__6]" 异常
         */
    Page<Order> findByGoodsName(@Param("queryRequest") OrderQueryRequest queryRequest,
                                Pageable pageable);
}
