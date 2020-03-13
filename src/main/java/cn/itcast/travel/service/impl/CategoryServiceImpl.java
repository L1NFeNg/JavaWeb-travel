package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        // 1    从redis中查询
        // 1.1  获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        // 1.2  可使用sortedset排序查询
        Set<String> categorys = jedis.zrange("category", 0, -1);
        // 2    判断查询的集合是否为空
        List<Category> cs = null;
        if (categorys == null || categorys.size() == 0) {
            System.out.println("---从数据库查询了！---");
            // 3.   如果为空
            // 3.1  从数据库中查询
            cs = categoryDao.findAll();
            // 3.2  再将数据传入redis(key为category)中
            for (Category c : cs) {
                jedis.zadd("category", c.getCid(), c.getCname());
            }
        } else {
            System.out.println("---从redis中查询！---");
            // 4.如果不为空，将set的数据存入List
            cs = new ArrayList<Category>();
            for (String name : categorys) {
                Category category = new Category();
                category.setCname(name);
                cs.add(category);
            }
        }
        return cs;
    }
}
