package com.teeqee.spring.dispatcher.servlet.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: 超宠建筑
 * @author : zhengsongjie
 * @Project: java_chaochong
 */
@Data
@ToString
public class BuildingData {
    @JSONField(name="buildingid")
    private Integer id;
    @JSONField(name="buildinglv")
    private Integer lv;
    @JSONField(name="successodds")
    private Integer ss;

    public BuildingData() {
    }

    public BuildingData(Integer id, Integer lv) {
        this.id = id;
        this.lv = lv;
    }
}
