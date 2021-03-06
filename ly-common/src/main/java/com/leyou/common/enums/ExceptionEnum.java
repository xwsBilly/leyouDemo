package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    // 枚举是具有固定个数的实例
    // 枚举类的对象直接用","隔开，最后一个对象后跟上";"
    // 枚举类的对象必须在最前
    PRICE_CAN_NOT_BE_NULL(400, "价格不能为空"),
    NAME_CAN_NOT_BE_NULL(400, "名称不能为空"),
    INVALID_USER_DATA_TYPE(400, "用户数据类型无效"),
    INVALID_VERIFY_CODE(400, "无效的验证码"),
    INVALID_USERNAME_PASSWORD(400, "无效的用户名和密码"),
    CATEGORY_NOT_FOUND(404, "商品分类未找到"),
    BRAND_NOT_FOUND(404, "商品品牌未找到"),
    SPECIFICATION_GROUP_NOT_FOUND(404, "商品规格组未找到"),
    SPECIFICATION_PARAM_NOT_FOUND(404, "商品规格参数未找到"),
    GOODS_NOT_FOUND(404, "商品未找到"),
    GOODS_DETAIL_NOT_FOUND(404, "商品详情未找到"),
    GOODS_SKU_NOT_FOUND(404, "商品SKU未找到"),
    GOODS_STOCK_NOT_FOUND(404, "商品库存未找到"),

    INVALID_FILE_TYPE(400, "无效的文件类型"),

    UPLOAD_FILE_ERROR(500, "上传文件失败"),
    BRAND_SAVE_ERROR(500, "新增品牌失败"),
    CATEGORY_BRAND_SAVE_ERROR(500, "新增品牌分类中间表失败"),
    SAVE_GOODS_ERROR(500, "新增商品失败"),
    SEND_RABBITMQ_ERROR(500, "发送rabbitMQ消息出错"),
    CREATE_TOKEN_ERROR(500, "生成用户凭证失败"),
    UPDATE_GOODS_ERROR(500, "修改商品失败");
    private int code;
    private String msg;
}
