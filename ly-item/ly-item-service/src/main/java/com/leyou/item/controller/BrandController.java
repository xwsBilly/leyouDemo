package com.leyou.item.controller;

import com.leyou.common.vo.PageResultVO;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 分页查询品牌
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<PageResultVO<Brand>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key) {
        PageResultVO<Brand> resultVO=brandService.queryBrandByPage(page,rows,sortBy,desc,key);
        return ResponseEntity.ok(resultVO);
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     * @return 无返回值，使用ResponseEntity<Void>
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        brandService.saveBrand(brand,cids);
        // 无返回值使用build();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据分类Id查询品牌
     * @param cid   分类id
     * @return
     */
    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable("cid") Long cid){

        return ResponseEntity.ok(brandService.queryBrandByCid(cid));
    }

    /**
     * 根据品牌id查询品牌
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id){
        return ResponseEntity.ok(brandService.queryById(id));
    }
}
