package com.youxuan.generator.codegen.elements;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author hutao
 * @version $Id: FindListByConditionMethodGenerator.java, v 0.1 2018年05月05日 14:11 Exp $
 */
public class FindListByConditionMethodGenerator extends
        AbstractJavaMapperMethodGenerator {
    private boolean pagination;

    public FindListByConditionMethodGenerator(boolean pagination) {
        super();
        this.pagination = pagination;
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

        Method method = new Method("findListByCondition");
        method.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(List.class.getName());
        returnType.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        method.setReturnType(returnType);
        method.addParameter(new Parameter(new FullyQualifiedJavaType(Map.class.getSimpleName()), "param"));
        if (pagination) {
            method.addParameter(new Parameter(new FullyQualifiedJavaType(RowBounds.class.getSimpleName()), "rowBounds"));
            interfaze.addImportedType(new FullyQualifiedJavaType(RowBounds.class.getName()));
        }

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        addMapperAnnotations(interfaze, method);

        if (context.getPlugins().clientSelectAllMethodGenerated(method,
                interfaze, introspectedTable)) {
            addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    public void addMapperAnnotations(Interface interfaze, Method method) {
    }

    public void addExtraImports(Interface interfaze) {
        interfaze.addImportedType(new FullyQualifiedJavaType(Map.class.getName()));
        interfaze.addImportedType(new FullyQualifiedJavaType(List.class.getName()));
    }
}
