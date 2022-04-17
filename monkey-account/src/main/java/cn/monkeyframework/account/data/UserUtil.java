package cn.monkeyframework.account.data;

import cn.monkeyframework.account.data.dto.UserDto;
import cn.monkeyframework.account.data.pojo.User;
import cn.monkeyframework.account.data.pojo.WechatUser;
import cn.monkeyframework.account.data.vo.UserVo;

public abstract class UserUtil {
    public static UserVo copy(User user) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setNickName(user.getNickName());
        userVo.setPassword(user.getPassword());
        userVo.setPhone(user.getPhone());
        userVo.setEmail(user.getEmail());
        userVo.setStatus(user.getStatus());
        userVo.setCreateTs(user.getCreateTs());
        userVo.setUpdateTs(user.getUpdateTs());
        return userVo;
    }

    public static User copy(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setNickName(userDto.getNickName());
        user.setStatus(userDto.getStatus());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public static UserVo copy(WechatUser wechatUser) {
        UserVo userVo = new UserVo();
        userVo.setAppKey(wechatUser.getAppKey());
        return userVo;
    }
}
