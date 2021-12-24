package com.mzl.incomeexpensemanagesystem.generator;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * @EnumName :   SeniorityEnum
 * @Description: 资历枚举
 * @Author: v_ktlema
 * @CreateDate: 2021/12/22 17:30
 * @Version: 1.0
 */
public enum SeniorityEnum implements IEnum {

    /**
     * 大佬
     */
    MOGUL(0),

    /**
     * 新手
     */
    NOVICE(1);

    @EnumValue
    private final int value;

    SeniorityEnum(final int value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public Serializable getValue() {
        return this.value;
    }

}