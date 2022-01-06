package com.mzl.incomeexpensemanagesystem.enums;

import lombok.Getter;

/**
 * @ClassName :   CollectStatusEnum
 * @Description: TODO
 * @Author: v_ktlema
 * @CreateDate: 2022/1/5 22:04
 * @Version: 1.0
 */
@Getter
public enum CollectStatusEnum {

    COLLECT(1, "收藏"),
    UN_COLLECT(0, "取消收藏/未收藏"),
    ;


    private Integer code;
    private String message;

    CollectStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (CollectStatusEnum item :CollectStatusEnum.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (CollectStatusEnum item : CollectStatusEnum.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
