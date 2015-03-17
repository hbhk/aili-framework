
package com.baozun.bizhub.cxfiservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.baozun.bizhub.cxfiservice package. 
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

    private final static QName _GetNewName2Response_QNAME = new QName("http://cxfIService.bizhub.baozun.com/", "getNewName2Response");
    private final static QName _GetNewName1_QNAME = new QName("http://cxfIService.bizhub.baozun.com/", "getNewName1");
    private final static QName _GetNewName2_QNAME = new QName("http://cxfIService.bizhub.baozun.com/", "getNewName2");
    private final static QName _GetNewName1Response_QNAME = new QName("http://cxfIService.bizhub.baozun.com/", "getNewName1Response");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.baozun.bizhub.cxfiservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetNewName1 }
     * 
     */
    public GetNewName1 createGetNewName1() {
        return new GetNewName1();
    }

    /**
     * Create an instance of {@link GetNewName2Response }
     * 
     */
    public GetNewName2Response createGetNewName2Response() {
        return new GetNewName2Response();
    }

    /**
     * Create an instance of {@link GetNewName2 }
     * 
     */
    public GetNewName2 createGetNewName2() {
        return new GetNewName2();
    }

    /**
     * Create an instance of {@link OrderDetail }
     * 
     */
    public OrderDetail createOrderDetail() {
        return new OrderDetail();
    }

    /**
     * Create an instance of {@link GetNewName1Response }
     * 
     */
    public GetNewName1Response createGetNewName1Response() {
        return new GetNewName1Response();
    }

    /**
     * Create an instance of {@link LogInfo }
     * 
     */
    public LogInfo createLogInfo() {
        return new LogInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNewName2Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cxfIService.bizhub.baozun.com/", name = "getNewName2Response")
    public JAXBElement<GetNewName2Response> createGetNewName2Response(GetNewName2Response value) {
        return new JAXBElement<GetNewName2Response>(_GetNewName2Response_QNAME, GetNewName2Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNewName1 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cxfIService.bizhub.baozun.com/", name = "getNewName1")
    public JAXBElement<GetNewName1> createGetNewName1(GetNewName1 value) {
        return new JAXBElement<GetNewName1>(_GetNewName1_QNAME, GetNewName1 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNewName2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cxfIService.bizhub.baozun.com/", name = "getNewName2")
    public JAXBElement<GetNewName2> createGetNewName2(GetNewName2 value) {
        return new JAXBElement<GetNewName2>(_GetNewName2_QNAME, GetNewName2 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNewName1Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cxfIService.bizhub.baozun.com/", name = "getNewName1Response")
    public JAXBElement<GetNewName1Response> createGetNewName1Response(GetNewName1Response value) {
        return new JAXBElement<GetNewName1Response>(_GetNewName1Response_QNAME, GetNewName1Response.class, null, value);
    }

}
