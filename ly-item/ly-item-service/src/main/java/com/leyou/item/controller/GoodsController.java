package com.leyou.item.controller;

import com.leyou.common.vo.PageResultVO;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * GoodsController
 *
 * @author xieweisong
 * @date 2019/8/4 18:11
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 分页查询Spu
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    @GetMapping("/spu/page")
    public ResponseEntity<PageResultVO<Spu>> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "key", required = false) String key){
        return ResponseEntity.ok(goodsService.querySpuByPage(page,rows,saleable,key));
    }

    /**
     * 新增商品spu
     * @param spu
     * @return
     */
    @PostMapping("/goods")
    public ResponseEntity<Void> saveGoods(@RequestBody Spu spu){
        goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改商品spu
     * @param spu
     * @return
     */
    @PutMapping("/goods")
    public ResponseEntity<Void> updateGoods(@RequestBody Spu spu){
        goodsService.updateGoods(spu);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 根据spuId查询商品详情SpuDetail
     * @param spuId
     * @return
     */
    @GetMapping("/spu/detail/{id}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("id") Long spuId){
        return ResponseEntity.ok(goodsService.querySpuDetailBySpuId(spuId));
    }

    /**
     * 根据spuId查询下面所有的sku
     * @param spuId
     * @return
     */
    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long spuId){
        return ResponseEntity.ok(goodsService.querySkuBySpuId(spuId));
    }

    /**
     * 根据spuId查询spu
     * @param id
     * @return
     */
    @GetMapping("/spu/{id}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id") Long id){
        return ResponseEntity.ok(goodsService.querySpuById(id));
    }
}
