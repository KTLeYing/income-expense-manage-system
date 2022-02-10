package com.mzl.incomeexpensemanagesystem.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @ClassName :   SexConverter
 * @Description: 性别自定义Converter
 * @Author: mzl
 * @CreateDate: 2022/2/8 11:51
 * @Version: 1.0
 */
public class SexConverter implements Converter<Integer> {

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
        if ("男".equals(cellData.getStringValue())){
            return 1;
        }
        if ("女".equals(cellData.getStringValue())){
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
            return new CellData("男");
        }
        if (integer == 2){
            return new CellData("女");
        }
        return null;
    }
}
