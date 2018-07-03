package com.youxuan.generator.codegen.elements;

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
 * 查询唯一条件的结果
 *
 * @author hutao
 * @version $Id: FindByConditionMethodGenerator.java, v 0.1 2018年05月05日 14:11 Exp $
 */
public class FindByConditionMethodGenerator extends
        AbstractJavaMapperMethodGenerator {
    private boolean isSimple;

    public FindByConditionMethodGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {


        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

        Method method = new Method("findByCondition");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        method.addParameter(new Parameter(new FullyQualifiedJavaType(Map.class.getSimpleName()), "param"));

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
