package com.mzl.incomeexpensemanagesystem.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @ClassName :   BannedConverter
 * @Description: 禁用自定义Converter
 * @Author: mzl
 * @CreateDate: 2022/2/8 11:53
 * @Version: 1.0
 */
public class BannedConverter implements Converter<Integer> {

    /**
     * 在java中的类型是什么
     * @return
     */
    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    /**
     * 在excel中的类型是什么
     * @return
     */
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 将excel的数据类型转为java数据类型
     * @param cellData
     * @param excelContentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if ("未被禁用".equals(cellData.getStringValue())){
            return 1;
        }
        if ("已被禁用".equals(cellData.getStringValue())){
            return 2;
        }
        return null;
    }

    /**
     * 将java的数据类型转为excel数据类型
     * @param integer
     * @param excelContentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public CellData convertToExcelData(Integer integer, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (integer == 1){
            return new CellData("未被禁用");
        }
        if (integer == 2){
            return new CellData("已被禁用");
        }
        return null;
    }
}
