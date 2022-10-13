package cn.monkey.commons.data.repository;

import cn.monkey.commons.bean.BeanContext;
import cn.monkey.commons.data.pojo.ServerConfig;
import cn.monkey.commons.data.pojo.ServerTypes;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import reactor.core.publisher.Mono;

import java.util.List;

public class ReactiveRedisServerRepository implements ReactiveServerRepository {

    private final ReactiveRedisTemplate<String, Object> redisTemplate;

    private final BeanContext<ServerTypes> serverTypesContext;

    private final RedisScript<String> script;

    public ReactiveRedisServerRepository(ReactiveRedisTemplate<String, Object> redisTemplate,
                                         BeanContext<ServerTypes> serverTypesContext) {
        this.redisTemplate = redisTemplate;
        this.serverTypesContext = serverTypesContext;
        String lua = """
                local result = {};
                for i =1, #(KEYS) do
                    result[i] = redis.call('get', KEYS[i]);
                end
                return result;""";
        this.script = new DefaultRedisScript<>(lua, String.class);
    }

    @Override
    public Mono<List<ServerConfig>> getServerConfig(String type) {
        ServerTypes bean = this.serverTypesContext.getBean(type);
        List<String> names = bean.getNames();
        return this.redisTemplate.execute(this.script, names)
                .map(s -> new ServerConfig())
                .collectList();
    }
}
