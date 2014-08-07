//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2014.06.23 时间 01:35:03 PM CST 
//


package org.hbhk.aili.orm.share.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.hbhk.orm.sql package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Orm_QNAME = new QName("http://www.hbhk.org/orm/sql", "orm");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.hbhk.orm.sql
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Orm }
     * 
     */
    public Orm createOrm() {
        return new Orm();
    }

    /**
     * Create an instance of {@link Update }
     * 
     */
    public Update createUpdate() {
        return new Update();
    }

    /**
     * Create an instance of {@link Select }
     * 
     */
    public Select createSelect() {
        return new Select();
    }

    /**
     * Create an instance of {@link Delete }
     * 
     */
    public Delete createDelete() {
        return new Delete();
    }

    /**
     * Create an instance of {@link Insert }
     * 
     */
    public Insert createInsert() {
        return new Insert();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Orm }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.hbhk.org/orm/sql", name = "orm")
    public JAXBElement<Orm> createOrm(Orm value) {
        return new JAXBElement<Orm>(_Orm_QNAME, Orm.class, null, value);
    }

}
