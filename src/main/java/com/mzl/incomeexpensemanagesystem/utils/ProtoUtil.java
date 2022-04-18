package com.mzl.incomeexpensemanagesystem.utils;

import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;

/**
 * @ClassName :   ProtoUtil
 * @Description: grpc请求结果格式化辅助类ProtoUtil，将结果各个属性变量组装格式化成json字符串
 * @Author: v_ktlema
 * @CreateDate: 2022/4/18 18:49
 * @Version: 1.0
 */
public class ProtoUtil {

    private static JsonFormat JSON_FORMAT;

    static {
        JSON_FORMAT = new JsonFormat();
    }

    public static String toStr(Message message) {
        return JSON_FORMAT.printToString(message);
    }

}
