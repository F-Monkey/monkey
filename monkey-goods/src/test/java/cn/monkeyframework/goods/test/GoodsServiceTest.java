package cn.monkeyframework.goods.test;

import cn.monkeyframework.commons.data.ObjectStatus;
import cn.monkeyframework.commons.data.dto.GoodsDto;
import cn.monkeyframework.commons.data.pojo.Goods;
import cn.monkeyframework.commons.data.pojo.HotGoodsInfo;
import cn.monkeyframework.commons.data.vo.GoodsVo;
import cn.monkeyframework.commons.data.vo.Result;
import cn.monkeyframework.goods.GoodsApplication;
import cn.monkeyframework.goods.repository.GoodsRepository;
import cn.monkeyframework.goods.repository.HotGoodsRepository;
import cn.monkeyframework.goods.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = GoodsApplication.class)
@Slf4j
public class GoodsServiceTest {
    @Autowired
    IGoodsService goodsService;

    static GoodsDto buildRice() {
        GoodsDto goodsDto = new GoodsDto();
        goodsDto.setType("rice");
        String[] imgSrc = {"http://192.168.126.146:8000/rice_1.jpg"};
        goodsDto.setImgSrc(imgSrc);
        goodsDto.setName("米");
        goodsDto.setPrice(2.22);
        return goodsDto;
    }

    static GoodsDto buildFish() {
        GoodsDto goodsDto = new GoodsDto();
        goodsDto.setType("fish");
        String[] imgSrc = {"http://192.168.126.146:8000/fish_2.jpg"};
        goodsDto.setImgSrc(imgSrc);
        goodsDto.setName("小鱼2");
        goodsDto.setPrice(2.22);
        return goodsDto;
    }


    @Test
    public void addGoods() {
        GoodsDto goodsDto = buildFish();
        Result<GoodsVo> add = this.goodsService.add(goodsDto);
        log.info("addResult: {}", add);
    }

    @Autowired
    HotGoodsRepository hotGoodsRepository;

    @Test
    public void addHotGoods() {
        HotGoodsInfo hotGoodsInfo = new HotGoodsInfo();
        hotGoodsInfo.setStatus(ObjectStatus.VALID);
        hotGoodsInfo.setGoodsId("6236a262b64795094617cfba");
        this.hotGoodsRepository.save(hotGoodsInfo);
    }

    static String replaceUrlHost(String url) {
        String newHost = "//cn-hk-1.msrx.online:8999/image";
        String[] split = url.split("/");
        return split[0] + ":" + newHost + ":" + split[2].split("/")[1];
        //return "http://192.168.126.147:8000/" + split[1];
    }

    @Autowired
    GoodsRepository goodsRepository;

    public void updateGoods(Goods goods) {
        String[] imgSrc = goods.getImgSrc();
        if ("米".equals(goods.getName())) {
            for (int i = 0; i < imgSrc.length; i++) {
                imgSrc[i] = "http://cn-hk-1.msrx.online:8999/image/rice_1.jpg";
            }
        }
        if ("小雨1".equals(goods.getName())) {
            for (int i = 0; i < imgSrc.length; i++) {
                imgSrc[i] = "http://cn-hk-1.msrx.online:8999/image/fish_1.jpg";
            }
        }
        if ("小鱼2".equals(goods.getName())) {
            for (int i = 0; i < imgSrc.length; i++) {
                imgSrc[i] = "http://cn-hk-1.msrx.online:8999/image/fish_2.jpg";
            }
        }
        goods.setImgSrc(imgSrc);
        goods.setDesc("这是一个不少于10个字的测试描述，吼吼吼吼吼吼");
        goodsRepository.save(goods);
    }

    @Test
    public void updateAllGoods() {
        List<Goods> all = this.goodsRepository.findAll();
        for (Goods goods : all) {
            updateGoods(goods);
        }
    }

    @Test
    public void updateGoods() {
        String id = "6236a262b64795094617cfba";
        GoodsDto goodsDto = new GoodsDto();
        goodsDto.setType("rice");
        String[] imgSrc = {"http://192.168.126.146:8000/rice_1.jpg"};
        goodsDto.setImgSrc(imgSrc);
        goodsDto.setName("米");
        goodsDto.setPrice(2.22);
        Result<GoodsVo> update = this.goodsService.update(id, goodsDto);
        log.info("update result: {}", update);
    }
}
