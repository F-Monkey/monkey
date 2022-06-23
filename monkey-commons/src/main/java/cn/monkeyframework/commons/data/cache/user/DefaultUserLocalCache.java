package cn.monkeyframework.commons.data.cache.user;

import cn.monkeyframework.commons.data.cache.AbstractThreadLocalCache;
import cn.monkeyframework.commons.data.pojo.User;

public class DefaultUserLocalCache extends AbstractThreadLocalCache<User> implements UserLocalCache {
}
