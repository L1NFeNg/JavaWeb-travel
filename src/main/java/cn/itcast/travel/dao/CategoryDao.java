package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    /**
     * 查询所有主页分类导航，以便日后改变修改
     * @return 所有主页分类导航
     */
    public List<Category> findAll();
}
