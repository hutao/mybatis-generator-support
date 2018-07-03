package com.youxuan.generator.types;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author hutao
 * @version $Id: CustomJavaTypeResolver.java, v 0.1 2018年05月07日 10:16 Exp $
 */
public class CustomJavaTypeResolver extends JavaTypeResolverDefaultImpl {
    protected List<String> moneyKeyWords = new ArrayList<String>();

    @Override
    public void addConfigurationProperties(Properties properties) {

        super.addConfigurationProperties(properties);
        String keyWords = properties
                .getProperty("moneyKeyWords");

        if (StringUtils.isBlank(keyWords)) {
            moneyKeyWords = new ArrayList<String>(0);
        } else {
            String[] keys = keyWords.split(",");
            for (int i = 0; i < keys.length; i++) {
                if (StringUtils.isNotBlank(keys[i])) {
                    moneyKeyWords.add(keys[i]);
                }
            }
        }
    }

    @Override
    protected FullyQualifiedJavaType calculateBigDecimalReplacement(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
        String actualColumnName = column.getActualColumnName();
        boolean isMoney = isMoney(actualColumnName);
        if (isMoney) {
            return new FullyQualifiedJavaType("com.youxuan.bff.appbff.common.util.Money");
        } else {
            return super.calculateBigDecimalReplacement(column, defaultType);
        }
    }

    private boolean isMoney(String actualColumnName) {
        boolean isMoney = false;
        for (String keyWord : moneyKeyWords) {
            if (actualColumnName.toUpperCase().contains(keyWord)) {
                isMoney = true;
                return isMoney;
            }
        }
        return isMoney;
    }
}
