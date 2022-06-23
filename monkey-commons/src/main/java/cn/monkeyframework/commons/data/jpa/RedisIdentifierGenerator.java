package cn.monkeyframework.commons.data.jpa;

import com.google.common.base.Strings;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class RedisIdentifierGenerator extends IdentityGenerator {

    protected final RedisTemplate<String, Long> redisTemplate;

    protected final ConcurrentHashMap<Class<?>, Field> idMap;

    public RedisIdentifierGenerator(RedisTemplate<String, Long> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.idMap = new ConcurrentHashMap<>();
    }

    protected Field getKeyFiled(Object object) {
        return this.idMap.computeIfAbsent(object instanceof Class ? (Class<?>) object : object.getClass(), (c) -> {
            Supplier<Stream<Field>> supplier = () -> Arrays.stream(c.getDeclaredFields()).filter(field -> field.getAnnotation(Key.class) != null);
            if (supplier.get().count() > 1) {
                throw new IllegalArgumentException("multipart fields annotated with [Key]");
            }
            Optional<Field> optional;
            if ((optional = supplier.get().findFirst()).isEmpty()) {
                throw new IllegalArgumentException("can not find any field annotated with [Key]");
            }
            return optional.get();
        });
    }

    protected String generateKey(Key k) {
        Long increment = this.redisTemplate.opsForValue().increment(k.key());
        return k.prefix() + Strings.padStart(String.valueOf(increment == null ? 0 : increment), k.length(), '0');
    }

    @Override

    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Field keyFiled = this.getKeyFiled(object);
        String s = this.generateKey(keyFiled.getAnnotation(Key.class));
        if (Strings.isNullOrEmpty(s)) {
            return super.generate(session, object);
        }
        return s;
    }
}
