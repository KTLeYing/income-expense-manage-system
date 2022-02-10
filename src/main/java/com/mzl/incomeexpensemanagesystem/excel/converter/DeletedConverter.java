package com.mzl.incomeexpensemanagesystem.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @ClassName :   DeletedConverter
 * @Description: 删除自定义Converter
 * @Author: mzl
 * @CreateDate: 2022/2/8 11:54
 * @Version: 1.0
 */
public class DeletedConverter implements Converter<Boolean> {

    /**
     * 在java中的类型是什么
     * @return
     */
    @Override
    public Class supportJavaTypeKey() {
        return Boolean.class;
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
    public Boolean convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if ("未删除".equals(cellData.getStringValue())){
            return true;
        }
        if ("已删除".equals(cellData.getStringValue())){
            return false;
        }
        return null;
    }

    /**
     * 将java的数据类型转为excel数据类型
     * @param
     * @param excelContentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public CellData convertToExcelData(Boolean aBoolean, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (aBoolean == true){
            return new CellData("未删除");
        }
        if (aBoolean == false){
            return new CellData("已删除");
        }
        return null;
    }
}
