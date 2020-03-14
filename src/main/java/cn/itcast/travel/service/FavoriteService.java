package cn.itcast.travel.service;

public interface FavoriteService {
    /**
     * 判断是否收藏
     * @param rid 路线名称
     * @param uid 当前登录的用户名称
     * @return bool
     */
    public boolean isFavorite(String rid, int uid);
}
