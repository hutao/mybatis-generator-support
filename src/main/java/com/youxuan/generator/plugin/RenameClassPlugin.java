/*
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2017 All Rights Reserved.
 */
package com.youxuan.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * @author hutao
 * @version $Id: RenameClassPlugin.java, v 0.1 2017/4/15 22:30 hutao Exp $
 */
public class RenameClassPlugin extends PluginAdapter {
    private String prefixString;

    public RenameClassPlugin() {
    }

    public boolean validate(List<String> warnings) {
        prefixString = properties.getProperty("prefixString");
        boolean valid = stringHasValue(prefixString);
        return valid;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        introspectedTable.setMyBatis3JavaMapperType(replaceClassName(introspectedTable
                .getMyBatis3JavaMapperType()));
        introspectedTable.setBaseRecordType(replaceClassName(introspectedTable.getBaseRecordType()) + "Entity");
        introspectedTable.setExampleType(replaceClassName(introspectedTable.getExampleType()));
        introspectedTable.setMyBatis3XmlMapperFileName(replaceName(introspectedTable
                .getMyBatis3XmlMapperFileName()));

    }

    private String replaceClassName(String oldType) {
        String packageName = oldType.substring(0, oldType.lastIndexOf("."));
        String simpleName = oldType.substring(oldType.lastIndexOf(".") + 1);

        if (simpleName.toUpperCase().startsWith(prefixString.toUpperCase())) {
            simpleName = simpleName.substring(prefixString.length());
            oldType = packageName + "." + simpleName;
        }
        return oldType;
    }

    private String replaceName(String oldType) {
        if (oldType.toUpperCase().startsWith(prefixString.toUpperCase())) {
            oldType = oldType.substring(prefixString.length());
        }
        return oldType;
    }

}
