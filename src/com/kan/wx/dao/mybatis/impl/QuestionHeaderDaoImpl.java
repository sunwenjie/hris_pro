package com.kan.wx.dao.mybatis.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.wx.dao.inf.QuestionHeaderDao;
import com.kan.wx.domain.QuestionHeaderVO;

public class QuestionHeaderDaoImpl extends Context implements QuestionHeaderDao
{

   @Override
   public int countQuestionHeaderVOsByCondition( QuestionHeaderVO questionHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countQuestionHeaderVOsByCondition", questionHeaderVO );
   }

   @Override
   public List< Object > getQuestionHeaderVOsByCondition( QuestionHeaderVO questionHeaderVO ) throws KANException
   {
      return selectList( "getQuestionHeaderVOsByCondition", questionHeaderVO );
   }

   @Override
   public List< Object > getQuestionHeaderVOsByCondition( QuestionHeaderVO questionHeaderVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getQuestionHeaderVOsByCondition", questionHeaderVO, rowBounds );
   }

   @Override
   public int insertQuestionHeader( QuestionHeaderVO questionHeaderVO ) throws KANException
   {
      return insert( "insertQuestionHeader", questionHeaderVO );
   }

   @Override
   public int updateQuestionHeader( QuestionHeaderVO questionHeaderVO ) throws KANException
   {
      return update( "updateQuestionHeader", questionHeaderVO );
   }

   @Override
   public void deleteQuestionHeader( QuestionHeaderVO questionHeaderVO ) throws KANException
   {
      delete( "deleteQuestionHeader", questionHeaderVO );
   }

   @Override
   public QuestionHeaderVO getQuestionHeaderVOByHeaderId( String headerId ) throws KANException
   {
      return ( QuestionHeaderVO ) select( "getQuestionHeaderVOByHeaderId", headerId );
   }

}
