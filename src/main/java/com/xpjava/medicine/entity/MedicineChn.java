package com.xpjava.medicine.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @since 2019-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MedicineChn implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String goodsId;

    private String goodsName;

    private Date produceDate;

    private Date expireDate;

    private BigDecimal bidMoney;

    private BigDecimal saleMoney;

    private Integer quantity;

    private String producer;

    @TableField(exist = false)
    private String produceDateTemp;

    @TableField(exist = false)
    private String expireDateTemp;


}
