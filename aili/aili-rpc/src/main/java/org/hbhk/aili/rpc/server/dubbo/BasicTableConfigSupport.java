/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package org.hbhk.aili.rpc.server.dubbo;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author wanghua
 * 
 * @param <T>
 */
public class BasicTableConfigSupport implements TableConfig, Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2025925168272833825L;
    /**
     * Table每列的显示名
     */
    protected String[] columnNames;
    /**
     * Table每列的数据属性名，如 user.name
     */
    protected String[] columns;
    /**
     * 当前页是第几页
     */
    protected int currentPage;
    /**
     * 分页时每页记录数
     */
    protected int pageSize;
    /**
     * Table的排序字符串,多个排序用','分割,e.g:user.USER_NAME asc,user.FULL_NAME asc
     */
    protected Sort[] sorts;
    /**
     * 是否分页
     */
    protected boolean pagable;

    private String caption;

    private Boolean isExcel;

    private Map<String, Object> columnOption;
    
    public BasicTableConfigSupport( String caption) {
    	this.caption = caption;
	}

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String... columnNames) {
        this.columnNames = columnNames;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String... columns) {
        this.columns = columns;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setSorts(String... sortNames) {
        if (sortNames != null) {
            sorts = new Sort[sortNames.length];
            for (int i = 0; i < sortNames.length; i++) {
                sorts[i] = new Sort(sortNames[i]);
            }
        }

    }

    public void setSorts(String sortString) {
        if (sortString != null && sortString.trim().length() > 0) {
            String[] strs = sortString.split(",");
            sorts = new Sort[strs.length];
            for (int i = 0; i < strs.length; i++) {
                sorts[i] = new Sort(strs[i]);
            }
        }
    }

    public boolean isPagable() {
        return pagable;
    }

    public void setPagable(boolean pagable) {
        this.pagable = pagable;
    }

    public void setPagable(boolean pagable, int pageSize) {
        setPagable(pagable);
        setPageSize(pageSize);
    }

    public BasicTableConfigSupport(String[] columns) {
        this.columns = columns;
    }

    public Sort[] getSorts() {
        return this.sorts;
    }

    public int getStart() {
        return (getCurrentPage() - 1) * getPageSize();
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Boolean getIsExcel() {
        return isExcel;
    }

    public void setIsExcel(Boolean isExcel) {
        this.isExcel = isExcel;
    }

    public Map<String, Object> getColumnOption() {
        return this.columnOption;
    }

    public void setColumnOption(Map<String, Object> columnOption) {
        this.columnOption = columnOption;
    }

}
