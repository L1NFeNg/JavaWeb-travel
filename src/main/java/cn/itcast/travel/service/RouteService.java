package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
    /**
     * 根据类别进行分页查询：搜索框功能
     *
     * @param cid         类别名称
     * @param currentPage 当前页面
     * @param pageSize    每页显示条数
     * @param rname       查询名词
     * @return 页面类
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 根据id查询：查看详情功能
     *
     * @param rid
     * @return
     */
    Route findOne(String rid);
}
