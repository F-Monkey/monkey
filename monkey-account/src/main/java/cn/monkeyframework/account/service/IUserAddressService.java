package cn.monkeyframework.account.service;

import cn.monkeyframework.commons.data.vo.Result;
import cn.monkeyframework.commons.data.dto.AddressDto;
import cn.monkeyframework.commons.data.vo.AddressVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface IUserAddressService {

    Mono<Result<Page<AddressVo>>> select(String userId, Pageable pageable);

    Mono<Result<AddressVo>> selectById(String id);

    Mono<Result<AddressVo>> add(AddressDto addressDto);

    Mono<Result<AddressVo>> update(String id, AddressDto addressDto);

    Mono<Result<Void>> del(String ids);

}
