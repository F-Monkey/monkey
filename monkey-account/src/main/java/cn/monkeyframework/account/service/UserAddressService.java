package cn.monkeyframework.account.service;

import cn.monkeyframework.commons.data.AddressUtil;
import cn.monkeyframework.commons.data.vo.Result;
import cn.monkeyframework.commons.data.dto.AddressDto;
import cn.monkeyframework.commons.data.pojo.UserAddress;
import cn.monkeyframework.commons.data.vo.AddressVo;
import cn.monkeyframework.account.repository.mongo.AddressRepository;
import cn.monkeyframework.account.repository.mongo.UserAddressRepository;
import cn.monkeyframework.commons.data.vo.Results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAddressService implements IUserAddressService {

    private final UserAddressRepository userAddressRepository;

    private final AddressRepository addressRepository;

    public UserAddressService(UserAddressRepository userAddressRepository, AddressRepository addressRepository) {
        this.userAddressRepository = userAddressRepository;
        this.addressRepository = addressRepository;
    }


    @Override
    public Mono<Result<Page<AddressVo>>> select(String userId, Pageable pageable) {
        return this.userAddressRepository.findByUid(userId, pageable).flatMap(page -> {
            if (page.isEmpty()) {
                return Mono.just(Results.ok(Page.empty(page.getPageable())));
            }
            List<String> addressIds = page.stream().map(UserAddress::getAddressId).collect(Collectors.toList());
            return this.addressRepository.findAllById(addressIds)
                    .map(AddressUtil::copy)
                    .collect(Collectors.toList())
                    .map(addresses -> Results.ok(new PageImpl<>(addresses, pageable, page.getTotalElements())));
        });
    }

    @Override
    public Mono<Result<AddressVo>> selectById(String id) {
        return this.addressRepository.findById(id).map(AddressUtil::copy).map(Results::ok).switchIfEmpty(Mono.just(Results.fail("can not find by id: " + id)));
    }

    @Override
    public Mono<Result<AddressVo>> add(AddressDto addressDto) {
        return Mono.just(addressDto).map(AddressUtil::copy).flatMap(this.addressRepository::save).flatMap(address -> {
            String id = address.getId();
            UserAddress userAddress = new UserAddress();
            userAddress.setAddressId(id);
            userAddress.setUid(addressDto.getUid());
            return this.userAddressRepository.save(userAddress).map(ua -> Tuples.of(ua, address));
        }).map(t -> AddressUtil.copy(t.getT2())).map(Results::ok);
    }

    @Override
    public Mono<Result<AddressVo>> update(String id, AddressDto addressDto) {
        return this.addressRepository.findById(id)
                .map(address -> AddressUtil.copy(address.getId(), addressDto))
                .flatMap(this.addressRepository::save)
                .map(AddressUtil::copy)
                .map(Results::ok)
                .switchIfEmpty(Mono.just(Results.fail("can not find addressInfo by id: " + id)));
    }

    @Override
    public Mono<Result<Void>> del(String ids) {
        List<String> list = Arrays.stream(ids.split(",")).collect(Collectors.toList());
        return this.addressRepository.deleteAllById(list)
                .map(v -> Results.ok());
    }
}
