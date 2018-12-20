package com.bijiemaifang.estate.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 *
 *
 * @author ace
 * @email 408709561@qq.com
 * @version 2018-10-24 20:43:44
 */
@Table(name = "estate")
public class Estate implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    @Id
    private Integer id;

    //类型
    @Column(name = "type")
    private String type;

    //面积
    @Column(name = "area")
    private String area;

    //结构
    @Column(name = "structure")
    private String structure;

    //装修程度
    @Column(name = "fitment_level")
    private String fitmentLevel;

    //商圈
    @Column(name = "district")
    private String district;

    //小区
    @Column(name = "plot")
    private String plot;

    //单元楼
    @Column(name = "apartment")
    private String apartment;

    //门牌号
    @Column(name = "house_number")
    private String houseNumber;


    /**
     * 设置：
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 获取：
     */
    public Integer getId() {
        return id;
    }
    /**
     * 设置：类型
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * 获取：类型
     */
    public String getType() {
        return type;
    }
    /**
     * 设置：面积
     */
    public void setArea(String area) {
        this.area = area;
    }
    /**
     * 获取：面积
     */
    public String getArea() {
        return area;
    }
    /**
     * 设置：结构
     */
    public void setStructure(String structure) {
        this.structure = structure;
    }
    /**
     * 获取：结构
     */
    public String getStructure() {
        return structure;
    }
    /**
     * 设置：装修程度
     */
    public void setFitmentLevel(String fitmentLevel) {
        this.fitmentLevel = fitmentLevel;
    }
    /**
     * 获取：装修程度
     */
    public String getFitmentLevel() {
        return fitmentLevel;
    }
    /**
     * 设置：商圈
     */
    public void setDistrict(String district) {
        this.district = district;
    }
    /**
     * 获取：商圈
     */
    public String getDistrict() {
        return district;
    }
    /**
     * 设置：小区
     */
    public void setPlot(String plot) {
        this.plot = plot;
    }
    /**
     * 获取：小区
     */
    public String getPlot() {
        return plot;
    }
    /**
     * 设置：单元楼
     */
    public void setApartment(String apartment) {
        this.apartment = apartment;
    }
    /**
     * 获取：单元楼
     */
    public String getApartment() {
        return apartment;
    }
    /**
     * 设置：门牌号
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
    /**
     * 获取：门牌号
     */
    public String getHouseNumber() {
        return houseNumber;
    }
}
