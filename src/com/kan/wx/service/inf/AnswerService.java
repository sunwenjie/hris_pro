package com.kan.wx.service.inf;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.wx.domain.AnswerVO;

public interface AnswerService
{
   public abstract PagedListHolder getAnswerVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract int insertAnswer( AnswerVO answerVO ) throws KANException;

   public abstract List< Object > getAnswerVOByAnswerId( final String answerId ) throws KANException;

   public abstract AnswerVO getAnswerVOByWXUserIdAndQuestionId( AnswerVO answerVO ) throws KANException;
}
