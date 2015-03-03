//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2014.08.05 时间 03:18:34 PM CST 
//


package org.hbhk.aili.support.server.excel.jxl.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.example.foo package. 
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

    private final static QName _Models_QNAME = new QName("http://www.hbhk.org/orm/excel", "models");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.foo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Models }
     * 
     */
    public Models createModels() {
        return new Models();
    }

    /**
     * Create an instance of {@link Model }
     * 
     */
    public Model createModel() {
        return new Model();
    }

    /**
     * Create an instance of {@link Entry }
     * 
     */
    public Entry createEntry() {
        return new Entry();
    }

    /**
     * Create an instance of {@link Map }
     * 
     */
    public Map createMap() {
        return new Map();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Models }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.hbhk.org/orm/excel", name = "models")
    public JAXBElement<Models> createModels(Models value) {
        return new JAXBElement<Models>(_Models_QNAME, Models.class, null, value);
    }

}
