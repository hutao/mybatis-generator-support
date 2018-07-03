package com.youxuan.generator.codegen;

import com.youxuan.generator.codegen.elements.FindByConditionMethodGenerator;
import com.youxuan.generator.codegen.elements.FindListByConditionMethodGenerator;
import com.youxuan.generator.codegen.elements.SelectByPrimaryKeyForUpdateMethodGenerator;

import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

import java.util.List;

/**
 * @author hutao
 * @version $Id: CustomJavaMapperGenerator.java, v 0.1 2018年05月05日 14:02 Exp $
 */
public class CustomJavaMapperGenerator extends JavaMapperGenerator {
    @Override
    public List<CompilationUnit> getCompilationUnits() {

        List<CompilationUnit> compilationUnits = super.getCompilationUnits();

        if (compilationUnits.size() > 0) {
            CompilationUnit compilationUnit = compilationUnits.get(0);
            if (compilationUnit instanceof Interface) {
                Interface interfaze = (Interface) compilationUnit;
                addFindByConditionMethod(interfaze);
                addFindListByConditionMethod(interfaze);
                addFindListByConditionForPageMethod(interfaze);
                addSelectByPrimaryKeyForWithLockMethod(interfaze);
            }
        }
        return compilationUnits;
    }

    private void addFindByConditionMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new FindByConditionMethodGenerator(false);
        initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    private void addFindListByConditionMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new FindListByConditionMethodGenerator(false);
        initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    private void addFindListByConditionForPageMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new FindListByConditionMethodGenerator(true);
        initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    private void addSelectByPrimaryKeyForWithLockMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new SelectByPrimaryKeyForUpdateMethodGenerator(true);
        initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        return new CustomXMLMapperGenerator();
    }

}
