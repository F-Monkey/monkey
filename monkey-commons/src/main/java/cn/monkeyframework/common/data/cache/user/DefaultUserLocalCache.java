package cn.monkeyframework.common.data.cache.user;

import cn.monkeyframework.common.data.cache.AbstractThreadLocalCache;
import cn.monkeyframework.common.data.pojo.User;

public class DefaultUserLocalCache extends AbstractThreadLocalCache<User> implements UserLocalCache {
}
