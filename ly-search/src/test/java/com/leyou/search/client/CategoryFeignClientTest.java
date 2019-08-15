package com.leyou.search.client;

import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.pojo.SpuDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * CategoryFeignClientTest
 *
 * @author xieweisong
 * @date 2019/8/9 17:04
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryFeignClientTest {

    @Autowired
    private CategoryFeignClient categoryFeignClient;

    @Autowired
    private BrandFeignClient brandFeignClient;

    @Autowired
    private GoodsFeignClient goodsFeignClient;

    /**
     * 测试远程调用 根据多个id查询商品分类 接口
     */
    @Test
    public void queryCategoryByIds() {
        List<Category> categories = categoryFeignClient.queryCategoryByIds(Arrays.asList(1L, 2L, 3L));
        Assert.assertEquals(3, categories.size());
        for (Category category : categories) {
            System.out.println("category = " + category);
        }

        Brand brand = brandFeignClient.queryBrandById(1L);
        System.out.println("brand = " + brand.toString());

        SpuDetail spuDetail = goodsFeignClient.querySpuDetailBySpuId(1L);
        System.out.println("spuDetail = " + spuDetail.toString());
    }

}