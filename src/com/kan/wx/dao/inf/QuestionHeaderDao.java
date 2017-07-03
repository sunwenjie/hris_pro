package com.kan.wx.dao.inf;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.wx.domain.QuestionHeaderVO;

public interface QuestionHeaderDao
{
   public abstract int countQuestionHeaderVOsByCondition( final QuestionHeaderVO questionHeaderVO ) throws KANException;

   public abstract List< Object > getQuestionHeaderVOsByCondition( final QuestionHeaderVO questionHeaderVO ) throws KANException;

   public abstract List< Object > getQuestionHeaderVOsByCondition( final QuestionHeaderVO questionHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract QuestionHeaderVO getQuestionHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertQuestionHeader( final QuestionHeaderVO questionHeaderVO ) throws KANException;

   public abstract int updateQuestionHeader( final QuestionHeaderVO questionHeaderVO ) throws KANException;

   public abstract void deleteQuestionHeader( final QuestionHeaderVO questionHeaderVO ) throws KANException;

}
