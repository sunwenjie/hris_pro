package com.kan.wx.service.inf;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.wx.domain.QuestionDetailVO;

public interface QuestionDetailService
{
   public abstract PagedListHolder getQuestionDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract QuestionDetailVO getQuestionDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertQuestionDetail( final QuestionDetailVO questionDetailVO ) throws KANException;

   public abstract int updateQuestionDetail( final QuestionDetailVO questionDetailVO ) throws KANException;

   public abstract void deleteQuestionDetail( final QuestionDetailVO questionDetailVO ) throws KANException;

   public abstract List< Object > getQuestionDetailVOsByHeaderId( final String headerId ) throws KANException;

   public abstract String getMaxOptionIndexByHeaderId( final String headerId ) throws KANException;

}
