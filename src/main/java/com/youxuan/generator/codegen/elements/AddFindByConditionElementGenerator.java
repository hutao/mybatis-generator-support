package com.youxuan.generator.codegen.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * @author hutao
 * @version $Id: AddFindByConditionElementGenerator.java, v 0.1 2018年05月05日 15:42 Exp $
 */
public class AddFindByConditionElementGenerator extends AbstractXmlElementGenerator {
    public AddFindByConditionElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");

        answer.addAttribute(new Attribute(
                "id", "findByCondition"));
        answer.addAttribute(new Attribute("resultMap",
                introspectedTable.getBaseResultMapId()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getBaseColumnListElement());
        sb.setLength(0);
        sb.append("from ");
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        XmlElement whereElement = new XmlElement(
                "where");
        answer.addElement(whereElement);

        XmlElement includeElement = new XmlElement("include");
        includeElement.addAttribute(new Attribute("refid", "Base_Where"));
        whereElement.addElement(includeElement);


        String orderByClause = introspectedTable.getTableConfigurationProperty(PropertyRegistry.TABLE_SELECT_ALL_ORDER_BY_CLAUSE);
        boolean hasOrderBy = StringUtility.stringHasValue(orderByClause);
        if (hasOrderBy) {
            sb.setLength(0);
            sb.append("order by ");
            sb.append(orderByClause);
            answer.addElement(new TextElement(sb.toString()));
        }

        if (context.getPlugins().sqlMapSelectAllElementGenerated(
                answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
