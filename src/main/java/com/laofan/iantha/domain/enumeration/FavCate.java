package com.laofan.iantha.domain.enumeration;

/**
 * The FavCate enumeration.
 */
public enum FavCate {
    /**
     * 收藏商品
     */
    PROD("商品"),
    /**
     * 收藏好友
     */
    FRIEND("好友");

    private final String value;

    FavCate(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
