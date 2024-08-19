package com.laofan.iantha.domain.enumeration;

/**
 * The ProdStatus enumeration.
 */
public enum ProdStatus {
    PRE("预约发布"),
    HOT("热门"),
    EXP("正常"),
    DOWN("售罄");

    private final String value;

    ProdStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
