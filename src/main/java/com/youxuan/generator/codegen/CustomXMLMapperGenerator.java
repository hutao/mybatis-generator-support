package com.youxuan.generator.codegen;

import com.youxuan.generator.codegen.elements.AddFindByConditionElementGenerator;
import com.youxuan.generator.codegen.elements.AddFindListByConditionElementGenerator;
import com.youxuan.generator.codegen.elements.SelectByPrimaryKeyForUpdateElementGenerator;
import com.youxuan.generator.codegen.elements.WhereClauseElementGenerator;

import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * @author hutao
 * @version $Id: CustomXMLMapperGenerator.java, v 0.1 2018年05月05日 15:38 Exp $
 */
public class CustomXMLMapperGenerator extends XMLMapperGenerator {

    @Override
    protected XmlElement getSqlMapElement() {
        XmlElement answer = super.getSqlMapElement();
        addFindByConditionElement(answer);
        addFindListByConditionElement(answer);
        addSelectByPrimaryKeyForUpdateElement(answer);
        addWhereClauseElement(answer);
        return answer;
    }

    private void addSelectByPrimaryKeyForUpdateElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new SelectByPrimaryKeyForUpdateElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }


    protected void addFindByConditionElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new AddFindByConditionElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }

    private void addFindListByConditionElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new AddFindListByConditionElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }


    private void addWhereClauseElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new WhereClauseElementGenerator();
        initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
}
