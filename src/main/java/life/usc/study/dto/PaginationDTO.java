package life.usc.study.dto;

import life.usc.study.moel.Question;
import lombok.Data;
import org.h2.store.Page;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean firstPage;
    private boolean previousPage;
    private boolean nextPage;
    private boolean lastPage;
    private Integer curPage;
    private Integer totalPage;
    private LinkedList<Integer> pages;

    public void setPagination(List<QuestionDTO> questions, Integer pageNum, Integer totalPage) {
        this.questions = questions;
        this.curPage = pageNum;
        this.totalPage =totalPage;

        /*
        * 分页组件的逻辑
        * */
        this.pages = new LinkedList<>();
        pages.add(curPage);
        for (int i = 1; i <= 3; i ++) {
            if (curPage - i >= 1) {
                pages.addFirst(curPage - i);
            }
            if (curPage + i <= totalPage) {
                pages.addLast(curPage + i);
            }
        }


        this.previousPage = curPage == 1 ? false : true;
        this.nextPage = curPage == totalPage ? false : true;
        this.firstPage = pages.contains(1) ? false : true;
        this.lastPage = pages.contains(totalPage) ? false : true;
    }
}
