package cn.monkey.order.controller;

import cn.monkeyframework.commons.data.dto.OrderDto;
import cn.monkeyframework.commons.data.dto.OrderQueryRequest;
import cn.monkeyframework.commons.data.vo.OrderVo;
import cn.monkey.order.service.IOrderService;
import cn.monkeyframework.commons.data.vo.Result;
import cn.monkeyframework.commons.spring.mvc.Uid;
import com.google.common.base.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping({"/{id}", ""})
    Result<?> get(@PathVariable(value = "id", required = false) String id,
                  @RequestParam(value = "query_key", required = false) String queryKey,
                  @RequestParam(value = "state", required = false) Integer state,
                  @Uid String uid,
                  @PageableDefault Pageable pageable) {
        if (!Strings.isNullOrEmpty(id)) {
            return this.orderService.select(id);
        }
        return this.orderService.select(OrderQueryRequest.of(uid, queryKey, state), pageable);
    }


    @PostMapping
    Result<OrderVo> add(@Uid String uid,
                        @RequestBody OrderDto dto) {
        dto.setUid(uid);
        return this.orderService.add(dto);
    }

    @PutMapping("/{id:O_*}")
    Result<OrderVo> update(@PathVariable(value = "id") String id,
                           @RequestBody OrderDto orderDto) {
        return this.orderService.update(id, orderDto);
    }

    @DeleteMapping("/{id:O_*}")
    Result<Void> delete(@PathVariable(value = "id") String id) {
        return this.orderService.delete(id);
    }

    @GetMapping(value = "/detail", params = {"action=distinctGoodsName"})
    Result<List<String>> getDistinctGoodsName(@Uid String uid,
                                              @RequestParam("goodsName") String goodsName) {
        return this.orderService.selectDistinctGoodsName(uid, goodsName);
    }
}
