//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2014.06.23 时间 01:35:03 PM CST 
//


package org.hbhk.aili.orm.share.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>orm complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="orm">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="insert" type="{http://www.hbhk.org/orm/sql}insert" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="delete" type="{http://www.hbhk.org/orm/sql}delete" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="update" type="{http://www.hbhk.org/orm/sql}update" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="select" type="{http://www.hbhk.org/orm/sql}select" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orm", propOrder = {
    "insert",
    "delete",
    "update",
    "select"
})
public class Orm implements Serializable{
	private static final long serialVersionUID = -6182547750119647956L;
	protected List<Insert> insert;
    protected List<Delete> delete;
    protected List<Update> update;
    protected List<Select> select;

    /**
     * Gets the value of the insert property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the insert property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInsert().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Insert }
     * 
     * 
     */
    public List<Insert> getInsert() {
        if (insert == null) {
            insert = new ArrayList<Insert>();
        }
        return this.insert;
    }

    /**
     * Gets the value of the delete property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delete property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelete().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Delete }
     * 
     * 
     */
    public List<Delete> getDelete() {
        if (delete == null) {
            delete = new ArrayList<Delete>();
        }
        return this.delete;
    }

    /**
     * Gets the value of the update property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the update property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpdate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Update }
     * 
     * 
     */
    public List<Update> getUpdate() {
        if (update == null) {
            update = new ArrayList<Update>();
        }
        return this.update;
    }

    /**
     * Gets the value of the select property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the select property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSelect().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Select }
     * 
     * 
     */
    public List<Select> getSelect() {
        if (select == null) {
            select = new ArrayList<Select>();
        }
        return this.select;
    }

	public void setInsert(List<Insert> insert) {
		this.insert = insert;
	}

	public void setDelete(List<Delete> delete) {
		this.delete = delete;
	}

	public void setUpdate(List<Update> update) {
		this.update = update;
	}

	public void setSelect(List<Select> select) {
		this.select = select;
	}

    
}
