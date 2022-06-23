package cn.monkeyframework.commons.data;

import cn.monkeyframework.commons.data.dto.AddressDto;
import cn.monkeyframework.commons.data.pojo.Address;
import cn.monkeyframework.commons.data.vo.AddressVo;

public abstract class AddressUtil {
    public static AddressVo copy(Address address) {
        AddressVo addressVo = new AddressVo();
        addressVo.setAddress(address.getAddress());
        addressVo.setId(address.getId());
        addressVo.setPhone(address.getPhone());
        addressVo.setLatitude(address.getLatitude());
        addressVo.setLongitude(address.getLongitude());
        addressVo.setHouseNumber(address.getHouseNumber());
        addressVo.setName(address.getName());
        addressVo.setUserName(address.getUserName());
        return addressVo;
    }

    public static Address copy(AddressDto addressDto) {
        Address address = new Address();
        address.setAddress(addressDto.getAddress());
        address.setHouseNumber(addressDto.getHouseNumber());
        address.setPhone(addressDto.getPhone());
        address.setLatitude(addressDto.getLatitude());
        address.setLongitude(addressDto.getLongitude());
        address.setName(addressDto.getName());
        address.setUserName(addressDto.getUserName());
        return address;
    }

    public static Address copy(String id, AddressDto addressDto) {
        Address copy = copy(addressDto);
        copy.setId(id);
        return copy;
    }
}
