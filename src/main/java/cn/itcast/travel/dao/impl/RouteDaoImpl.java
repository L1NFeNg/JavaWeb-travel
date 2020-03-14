package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid, String rname) {
        // 1.   定义一个sql模板
        String sql = "SELECT COUNT(*) FROM tab_route WHERE 1 = 1";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();      // 条件们
        // 2.  判断参数是否有值
        if (cid != 0) {
            sb.append(" AND cid = ? ");
            params.add(cid);                // 添加问号对应的值
        }
        if (rname != null && rname.length() > 0) {
            sb.append(" AND rname like ? ");
            params.add("%" + rname + "%");
        }
        sql = sb.toString();

        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
//        String sql = "SELECT * FROM tab_route WHERE cid = ? LIMIT ? , ?";
        String sql = "SELECT * FROM tab_route WHERE 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();      // 条件们
        // 2.  判断参数是否有值
        if (cid != 0) {
            sb.append(" AND cid = ? ");
            params.add(cid);                // 添加问号对应的值
        }
        if (rname != null && rname.length() > 0) {
            sb.append(" AND rname like ? ");
            params.add("%" + rname + "%");
        }
        sb.append(" LIMIT ? , ? ");         // 分页条件
        sql = sb.toString();
        params.add(start);
        params.add(pageSize);

        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "SELECT * FROM tab_route WHERE rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }
}
