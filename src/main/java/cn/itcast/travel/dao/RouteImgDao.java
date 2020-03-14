package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    /**
     * 根据route的rid查询其图片：显示详情功能
     */
    public List<RouteImg> findByRid(int rid);
}
