package com.kan.wx.dao.mybatis.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.wx.dao.inf.AnswerDao;
import com.kan.wx.domain.AnswerVO;

public class AnswerDaoImpl extends Context implements AnswerDao
{
   @Override
   public int countAnswerVOsByCondition( AnswerVO answerVO ) throws KANException
   {
      return ( Integer ) select( "countAnswerVOsByCondition", answerVO );
   }

   @Override
   public List< Object > getAnswerVOsByCondition( AnswerVO answerVO ) throws KANException
   {
      return selectList( "getAnswerVOsByCondition", answerVO );
   }

   @Override
   public List< Object > getAnswerVOsByCondition( AnswerVO answerVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getAnswerVOsByCondition", answerVO, rowBounds );
   }

   @Override
   public int insertAnswer( AnswerVO answerVO ) throws KANException
   {
      return insert( "insertAnswer", answerVO );
   }

   @Override
   public List< Object > getAnswerVOByAnswerId( String answerId ) throws KANException
   {
      return selectList( "getAnswerVOByAnswerId", answerId );
   }

   @Override
   public List< Object > getAnswerVOByWXUserIdAndQuestionId( AnswerVO answerVO ) throws KANException
   {
      return selectList( "getAnswerVOByWXUserIdAndQuestionId", answerVO );
   }

   @Override
   public List< Object > getAnswerVOsByHeaderId( String headerId ) throws KANException
   {
      return selectList( "getAnswerVOsByHeaderId", headerId );
   }

   @Override
   public void deleteAnswer( AnswerVO answerVO ) throws KANException
   {
      delete( "deleteAnswer", answerVO );
   }
}
