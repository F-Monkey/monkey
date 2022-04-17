package cn.monkeyframework.goods.test;

import cn.monkeyframework.goods.GoodsApplication;
import cn.monkeyframework.goods.context.GoodsSystemCodeContext;
import cn.monkeyframework.goods.data.pojo.SystemCode;
import cn.monkeyframework.goods.repository.SystemCodeRepository;
import cn.monkeyframework.goods.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = GoodsApplication.class)
@Slf4j
public class SystemCodeTest {
    @Autowired
    SystemCodeRepository systemCodeRepository;

    public SystemCode buildRice() {
        SystemCode systemCode = new SystemCode();
        systemCode.setSystemId(GoodsSystemCodeContext.SYSTEM_ID);
        systemCode.setType("goodsType");
        systemCode.setCode("rice");
        systemCode.setName("大米");
        return systemCode;
    }

    public SystemCode buildFish() {
        SystemCode systemCode = new SystemCode();
        systemCode.setSystemId(GoodsSystemCodeContext.SYSTEM_ID);
        systemCode.setType("goodsType");
        systemCode.setCode("fish");
        systemCode.setName("观赏鱼");
        return systemCode;
    }

    public SystemCode buildCurrencyCode(){
        SystemCode systemCode = new SystemCode();
        systemCode.setSystemId(GoodsSystemCodeContext.SYSTEM_ID);
        systemCode.setType(IGoodsService.CURRENCY_CODE);
        systemCode.setCode("CNY");
        systemCode.setName("元");
        return systemCode;
    }


    @Test
    public void testAddSystemCode() {
        SystemCode systemCode = buildCurrencyCode();
        this.systemCodeRepository.save(systemCode);
    }
}
