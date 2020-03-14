package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    /**
     * 根据sid查询对象：查看详情功能
     */
    public Seller findById(int sid);
}
