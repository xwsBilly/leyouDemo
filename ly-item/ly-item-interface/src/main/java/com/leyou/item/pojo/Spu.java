package com.leyou.item.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Spu
 *
 * @author xieweisong
 * @date 2019/8/4 18:00
 */
@Data
@Table(name = "tb_spu")
public class Spu {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long brandId;
    /**
     * 1级类目
      */
    private Long cid1;
    /**
     * 2级类目
     */
    private Long cid2;
    /**
     * 3级类目
     */
    private Long cid3;
    /**
     * 标题
     */
    private String title;
    /**
     * 子标题
     */
    private String subTitle;
    /**
     * 是否上架
     */
    private Boolean saleable;
    /**
     * 是否有效，逻辑删除用
     */
    @JsonIgnore
    private Boolean valid;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后修改时间
     */
    @JsonIgnore
    private Date lastUpdateTime;

    @Transient
    private String bname;
    @Transient
    private String cname;

    @Transient
    private List<Sku> skus;
    @Transient
    private SpuDetail spuDetail;
}
