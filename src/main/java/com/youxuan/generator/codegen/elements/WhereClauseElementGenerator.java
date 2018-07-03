package com.youxuan.generator.codegen.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * @author hutao
 * @version $Id: WhereClauseElementGenerator.java, v 0.1 2018年05月05日 16:51 Exp $
 */
public class WhereClauseElementGenerator extends
        AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute(
                "id", "Base_Where"));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        for (IntrospectedColumn introspectedColumn : ListUtilities.removeGeneratedAlwaysColumns(introspectedTable
                .getNonPrimaryKeyColumns())) {
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null");
            XmlElement isNotNullElement = new XmlElement("if");
            isNotNullElement.addAttribute(new Attribute("test", sb.toString()));


            sb.setLength(0);
            sb.append(" and ");
            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn));

            isNotNullElement.addElement(new TextElement(sb.toString()));

            answer.addElement(isNotNullElement);
        }


        parentElement.addElement(answer);
    }
}
