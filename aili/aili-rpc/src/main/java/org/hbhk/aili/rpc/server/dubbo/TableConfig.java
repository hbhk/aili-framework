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

import java.util.Map;


/**
 * 表格设置
 * 
 * @author benjamin
 * 
 */
public interface TableConfig {
    /**
     * 获得当前Table每列的显示名
     * 
     * @return
     */
    String[] getColumnNames();

    /**
     * 设置当前Table每列的显示名
     * 
     * @param columnNames
     */
    void setColumnNames(String... columnNames);

    /**
     * 获得当前Table每列的数据属性名，如 user.name
     * 
     * @return
     */
    String[] getColumns();

    /**
     * 设置当前Table每列的数据属性名
     * 
     * @param columns
     */
    void setColumns(String... columns);

    /**
     * 获得当前Table每列的排序名， 如 usr.USER_NAME
     * 
     * @return
     */
    // String[] getSortNames();

    /**
     * 设置当前Table每列的排序名
     * 
     * @param sortNames
     */
    // void setSortNames(String... sortNames);

    /**
     * 获取当前Table的排序字符串
     * 
     * @return
     */
    // String getSortString();

    /**
     * 设置当前Table的排序字符串
     * 
     * @param sortStr
     */
    // void setSortString(String sortStr);

    /**
     * 是否分页
     * 
     * @return
     */
    boolean isPagable();

    /**
     * 设置分页
     * 
     * @param pagable
     */
    void setPagable(boolean pagable);

    /**
     * 设置分页
     * 
     * @param pagable
     * @param pageSize
     */
    void setPagable(boolean pagable, int pageSize);

    /**
     * 分页时每页记录数
     * 
     * @return
     */
    int getPageSize();

    /**
     * 设置每页记录数
     * 
     * @param pageSize
     */
    void setPageSize(int pageSize);

    /**
     * Table当前显示页
     * 
     * @return
     */
    int getCurrentPage();

    /**
     * 设置Table当前显示页
     * 
     * @param currentPage
     */
    void setCurrentPage(int currentPage);

    Sort[] getSorts();

    void setSorts(String sortStr);

    void setSorts(String... sortNames);

    int getStart();

    /**
     * JQGrid.caption
     * 
     * @return
     */
    String getCaption();

    void setCaption(String caption);

    /**
     * JQGrid/Excel
     * 
     * @param isExcel
     */
    void setIsExcel(Boolean isExcel);

    Boolean getIsExcel();

    /**
     * ChooseOption.categoryCode
     * 
     * @return
     */
    Map<String, Object> getColumnOption();

    void setColumnOption(Map<String, Object> columnOption);
}
