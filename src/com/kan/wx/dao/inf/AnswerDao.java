package com.kan.wx.dao.inf;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.wx.domain.AnswerVO;

public interface AnswerDao
{
   public abstract int countAnswerVOsByCondition( AnswerVO answerVO ) throws KANException;

   public abstract List< Object > getAnswerVOsByCondition( AnswerVO answerVO ) throws KANException;

   public abstract List< Object > getAnswerVOsByCondition( final AnswerVO answerVO, final RowBounds rowBounds ) throws KANException;

   public abstract int insertAnswer( AnswerVO answerVO ) throws KANException;

   public abstract void deleteAnswer( AnswerVO answerVO ) throws KANException;

   public abstract List< Object > getAnswerVOByAnswerId( final String answerId ) throws KANException;

   public abstract List< Object > getAnswerVOByWXUserIdAndQuestionId( AnswerVO answerVO ) throws KANException;

   public abstract List< Object > getAnswerVOsByHeaderId( final String headerId ) throws KANException;
}
