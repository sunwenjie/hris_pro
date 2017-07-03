package com.kan.wx.dao.inf;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.wx.domain.QuestionDetailVO;

public interface QuestionDetailDao
{
   public abstract int countQuestionDetailVOsByCondition( final QuestionDetailVO questionDetailVO ) throws KANException;

   public abstract List< Object > getQuestionDetailVOsByCondition( final QuestionDetailVO questionDetailVO ) throws KANException;

   public abstract List< Object > getQuestionDetailVOsByCondition( final QuestionDetailVO questionDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract QuestionDetailVO getQuestionDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertQuestionDetail( final QuestionDetailVO questionDetailVO ) throws KANException;

   public abstract int updateQuestionDetail( final QuestionDetailVO questionDetailVO ) throws KANException;

   public abstract void deleteQuestionDetail( final QuestionDetailVO questionDetailVO ) throws KANException;

   public abstract List< Object > getQuestionDetailVOsByHeaderId( final String headerId ) throws KANException;
   
   public abstract String getMaxOptionIndexByHeaderId( final String headerId ) throws KANException;
   
}
