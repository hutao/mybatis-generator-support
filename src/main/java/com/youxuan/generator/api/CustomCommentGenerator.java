/*
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2017 All Rights Reserved.
 */
package com.youxuan.generator.api;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @author hutao
 * @version $Id: CustomCommentGenerator.java, v 0.1 2017/4/13 21:14 hutao Exp $
 */
public class CustomCommentGenerator extends DefaultCommentGenerator {
    private Properties properties = new Properties();
    private boolean suppressDate = false;
    private boolean suppressAllComments = false;
    private boolean addRemarkComments = false;

    private String pattern = "yyyy-MM-dd HH:mm:ss";

    private Properties env;

    public CustomCommentGenerator() {
        super();
        properties = new Properties();
        env = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        this.suppressDate = StringUtility.isTrue(properties.getProperty("suppressDate"));
        this.suppressAllComments = StringUtility.isTrue(properties
                .getProperty("suppressAllComments"));
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));

    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        innerEnum.addJavaDocLine("/**");
        //      addJavadocTag(innerEnum, false);
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString());
        innerEnum.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        field.addJavaDocLine("/**");
        addJavadocTag(field, false);
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        field.addJavaDocLine(sb.toString());
        field.addJavaDocLine(" */");

    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        // add no file level comments by default
        String year = new SimpleDateFormat("yyyy").format(new Date());
        compilationUnit.addFileCommentLine("/*");
        compilationUnit.addFileCommentLine(" * kunsheng.com Inc.");
        compilationUnit.addFileCommentLine(" * Copyright (c) 2013-" + year
                + " All Rights Reserved.");
        compilationUnit.addFileCommentLine(" */");
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * This method was generated by MyBatis Generator.");

        sb.append(" * This method corresponds to the database table ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        method.addJavaDocLine(sb.toString());

        addJavadocTag(method, false);

        method.addJavaDocLine(" */");
    }

    @Override
    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge");
        }
        String s = getDateString();
        if (s != null) {
            sb.append(' ');
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**");
        addJavadocTag(method, false);
        sb.append(" * @return ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */");
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        StringBuilder sb = new StringBuilder();

        method.addJavaDocLine("/**");
        addJavadocTag(method, false);

        Parameter parm = method.getParameters().get(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        sb.append(" ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */");
    }

    @Override
    protected String getDateString() {
        return null;
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass,
                                     IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("/**");

        StringBuilder sb0 = new StringBuilder();
        sb0.append(" * ");
        sb0.append(introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(sb0.toString());

        StringBuilder sb1 = new StringBuilder();
        sb1.append(" * ");
        sb1.append("@author ");
        sb1.append(env.getProperty("user.name"));
        topLevelClass.addJavaDocLine(sb1.toString());

        StringBuilder sb2 = new StringBuilder();
        sb2.append(" * ");
        sb2.append("@version $Id: " + topLevelClass.getType().getShortName() + ".java, v 0.1 ");
        if (!suppressDate) {
            sb2.append(getDateString());
        }
        sb2.append(" " + env.getProperty("user.name") + " Exp $");
        topLevelClass.addJavaDocLine(sb2.toString());

        topLevelClass.addJavaDocLine(" */");
    }

    @Override
    public void addComment(XmlElement xmlElement) {
        xmlElement.addElement(new TextElement("<!--"));

        StringBuilder sb = new StringBuilder();
        sb.append("  WARNING - 自动生成的代码，请勿修改。");
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        xmlElement.addElement(new TextElement(sb.toString()));

        if (!suppressDate) {
            String s = getDateString();
            if (s != null) {
                sb.setLength(0);
                sb.append("  This element was generated on ");
                sb.append(s);
                sb.append('.');
                xmlElement.addElement(new TextElement(sb.toString()));
            }
        }
        xmlElement.addElement(new TextElement("-->"));
    }
}
