package com.laofan.iantha.domain.enumeration;

/**
 * 标签分类
 */
public enum LabelCate {
    SEARCH("搜索类"),
    STAT("统计类"),
    RULE("规则类"),
    ACTIVITY("活动类"),
    NONE("其它");

    private final String value;

    LabelCate(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
