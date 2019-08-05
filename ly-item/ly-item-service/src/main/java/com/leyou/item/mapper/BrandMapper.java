package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface BrandMapper extends Mapper<Brand> {
    /**
     * 插入商品品牌和分类中间表
     *
     * @param cid 分类id
     * @param bid 品牌id
     * @return
     */
    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid}, #{bid})")
    int insetCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     * 根据分类Id查询品牌
     * @param cid   分类Id
     * @return
     */
    @Select("SELECT b.* FROM tb_brand AS b INNER JOIN tb_category_brand AS cb ON b.id = cb.category_id WHERE cb.category_id = #{cid}")
    List<Brand> queryByCategoryId(@Param("cid") Long cid);
}
