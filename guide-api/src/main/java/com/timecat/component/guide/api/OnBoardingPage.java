package com.timecat.component.guide.api;

/**
 * @author 林学渊
 * @email linxy59@mail2.sysu.edu.cn
 * @date 2021/11/17
 * @description null
 * @usage null
 */
public class OnBoardingPage {
    private final String titleResource;
    private final String subTitleResource;
    private final String descriptionResource;
    private final String logoResource;

    public final String getTitleResource() {
        return this.titleResource;
    }

    public final String getSubTitleResource() {
        return this.subTitleResource;
    }

    public final String getDescriptionResource() {
        return this.descriptionResource;
    }

    public final String getLogoResource() {
        return this.logoResource;
    }

    public OnBoardingPage(String titleResource, String subTitleResource, String descriptionResource, String logoResource) {
        this.titleResource = titleResource;
        this.subTitleResource = subTitleResource;
        this.descriptionResource = descriptionResource;
        this.logoResource = logoResource;
    }

    public String toString() {
        return "OnBoardingPage(titleResource=" + this.titleResource + ", subTitleResource=" + this.subTitleResource + ", descriptionResource=" + this.descriptionResource + ", logoResource=" + this.logoResource + ")";
    }

}
