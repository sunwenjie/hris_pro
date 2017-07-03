package com.kan.wx.service.inf;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.wx.domain.QuestionHeaderVO;

public interface QuestionHeaderService
{
   public abstract PagedListHolder getQuestionHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract QuestionHeaderVO getQuestionHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertQuestionHeader( final QuestionHeaderVO questionHeaderVO ) throws KANException;

   public abstract int updateQuestionHeader( final QuestionHeaderVO questionHeaderVO ) throws KANException;

   public abstract void deleteQuestionHeader( final QuestionHeaderVO questionHeaderVO ) throws KANException;
}
