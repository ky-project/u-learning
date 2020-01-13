package com.ky.ulearning.common.core.api.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;

/**
 * 基础service类，包含service通用方法
 *
 * @author luyuhao
 * @since 20/01/13 23:58
 */
public class BaseService {

    /**
     * 设置分页对象的分页参数
     *
     * @param pageBean  分页对象
     * @param pageParam 分页参数
     * @return 返回分页对象
     */
    protected <T> PageBean<T> setPageBeanProperties(PageBean<T> pageBean, PageParam pageParam) {
        if (pageParam.getPageSize() != null && pageParam.getCurrentPage() != null) {
            //设置当前页
            pageBean.setCurrentPage(pageParam.getCurrentPage())
                    //设置页大小
                    .setPageSize(pageParam.getPageSize())
                    //设置总页数 {(总记录数 + 页大小 - 1) / 页大小}
                    .setTotalPage((pageBean.getTotal() + pageBean.getPageSize() - 1) / pageBean.getPageSize())
                    //设置是否有后一页
                    .setHasNext(pageBean.getCurrentPage() < pageBean.getTotalPage())
                    //设置是否有前一页
                    .setHasPre(pageBean.getCurrentPage() > 1);
        }
        return pageBean;
    }
}
