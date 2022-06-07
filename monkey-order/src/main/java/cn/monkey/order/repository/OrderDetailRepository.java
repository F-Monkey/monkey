package cn.monkey.order.repository;

import cn.monkey.order.model.pojo.Order;
import cn.monkey.order.model.pojo.OrderDetail;
import cn.monkeyframework.common.data.repository.jpa.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    @Query(value = "SELECT * FROM " + OrderDetail.TABLE_NAME + " WHERE orderId IN (:order_id)", nativeQuery = true)
    List<OrderDetail> findAllByOrderId(@Param("order_id") List<String> orderId);

    @Query(value = "SELECT * FROM " + OrderDetail.TABLE_NAME + " WHERE orderId = :orderId", nativeQuery = true)
    List<OrderDetail> findAllByOrderId(@Param("orderId") String orderId);

    /**
     * find first 5 data
     */
    @Query(value = "SELECT DISTINCT od.goodsName FROM " + OrderDetail.TABLE_NAME + " AS od JOIN " + Order.TABLE_NAME + " AS o ON o.id = od.orderId" +
            " WHERE o.uid = :uid and od.goodsName LIKE CONCAT('%', :goodsName, '%') LIMIT 5", nativeQuery = true)
    List<String> findDistinctGoodsName(@Param("uid") String uid,
                                       @Param("goodsName") String goodsName);
}
