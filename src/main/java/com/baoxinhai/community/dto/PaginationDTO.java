package com.baoxinhai.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;    //判断是否展示前一页  这里根据自己的分页逻辑实现即可
    private boolean showFirstPage;   //判断是否展示第一页  这里根据自己的分页逻辑实现即可
    private boolean showNext;        //判断是否展示下一页  这里根据自己的分页逻辑实现即可
    private boolean showLastPage;    //判断是否展示最后一页 这里根据自己的分页逻辑实现即可
    private Integer totalPage;       //总页数
    private Integer currentPage;     //当前页
    private Integer previousPage;    //当前页上一页
    private Integer nextPage;        //当前页后一页
    private List<Integer> pages = new ArrayList<>();

    public void setPagination(Integer totalCount, Integer page, Integer limit) {
        currentPage=page;
        this.previousPage=page-1;
        this.nextPage=page+1;
        Integer totalPage;
        if (totalCount % limit == 0) {
            totalPage = totalCount / limit;
        } else {
            totalPage = totalCount / limit + 1;
        }
        this.totalPage=totalPage;
        //向pages里面插入数据  先插入page之前页 最多三页
        if (page <= 3) {
            for (int i = 1; i < page; i++) {
                pages.add(i);
            }
        } else {
            pages.add(page - 3);
            pages.add(page - 2);
            pages.add(page - 1);
        }
        //插入page
        pages.add(page);
        //插入page后的页数 最多三页
        if (totalPage - page <= 3) {
            for (int i = page + 1; i <= totalPage; i++) {
                pages.add(i);
            }
        } else {
            pages.add(page + 1);
            pages.add(page + 2);
            pages.add(page + 3);
        }

        //是否展示前一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        //是否展示后一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }
        //如果当前显示的页数集合里面有第一页 则不需要展示第一页的按钮
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        //如果当前显示的页数集合里面有最后一页，则不需要展示最后一页的按钮
        if (pages.contains(totalPage)) {
            showLastPage = false;
        } else {
            showLastPage = true;
        }
    }
}
