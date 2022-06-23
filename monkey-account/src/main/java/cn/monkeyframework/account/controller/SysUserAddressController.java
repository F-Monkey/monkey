package cn.monkeyframework.account.controller;

import cn.monkeyframework.commons.data.vo.Result;
import cn.monkeyframework.commons.data.dto.AddressDto;
import cn.monkeyframework.commons.data.vo.AddressVo;
import cn.monkeyframework.account.service.UserAddressService;
import cn.monkeyframework.commons.data.vo.Results;
import cn.monkeyframework.commons.spring.mvc.Uid;
import com.google.common.base.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/address")
public class SysUserAddressController {

    private final UserAddressService userAddressService;

    public SysUserAddressController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    @GetMapping(value = {"/{id}", ""})
    Mono<? extends Result<?>> get(@PathVariable(value = "id", required = false) String id,
                                  @Uid String uid,
                                  @PageableDefault Pageable pageable) {
        if (!Strings.isNullOrEmpty(id)) {
            return this.userAddressService.selectById(id);
        }
        if (!Strings.isNullOrEmpty(uid)) {
            return this.userAddressService.select(uid, pageable);
        }
        return Mono.just(Results.fail("empty arguments"));
    }

    @PostMapping
    Mono<Result<AddressVo>> add(@RequestBody AddressDto addressDto,
                                @Uid String uid) {
        addressDto.setUid(uid);
        return this.userAddressService.add(addressDto);
    }

    @PutMapping("/{id}")
    Mono<Result<AddressVo>> update(@PathVariable("id") String id,
                                   @Uid String uid,
                                   @RequestBody AddressDto addressDto) {
        addressDto.setUid(uid);
        return this.userAddressService.update(id, addressDto);
    }

    @DeleteMapping("/{ids}")
    Mono<Result<Void>> delete(@PathVariable("ids") String ids) {
        return this.userAddressService.del(ids);
    }
}
