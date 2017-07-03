package com.kan.wx.dao.mybatis.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.wx.dao.inf.QuestionDetailDao;
import com.kan.wx.domain.QuestionDetailVO;

public class QuestionDetailDaoImpl extends Context implements QuestionDetailDao
{

   @Override
   public int countQuestionDetailVOsByCondition( QuestionDetailVO questionDetailVO ) throws KANException
   {
      return ( int ) select( "countQuestionDetailVOsByCondition", questionDetailVO );
   }

   @Override
   public List< Object > getQuestionDetailVOsByCondition( QuestionDetailVO questionDetailVO ) throws KANException
   {
      return selectList( "getQuestionDetailVOsByCondition", questionDetailVO );
   }

   @Override
   public List< Object > getQuestionDetailVOsByCondition( QuestionDetailVO questionDetailVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getQuestionDetailVOsByCondition", questionDetailVO, rowBounds );
   }

   @Override
   public int insertQuestionDetail( QuestionDetailVO questionDetailVO ) throws KANException
   {
      return insert( "insertQuestionDetail", questionDetailVO );
   }

   @Override
   public int updateQuestionDetail( QuestionDetailVO questionDetailVO ) throws KANException
   {
      return update( "updateQuestionDetail", questionDetailVO );
   }

   @Override
   public void deleteQuestionDetail( QuestionDetailVO questionDetailVO ) throws KANException
   {
      delete( "deleteQuestionDetail", questionDetailVO );
   }

   @Override
   public List< Object > getQuestionDetailVOsByHeaderId( String headerId ) throws KANException
   {
      return selectList( "getQuestionDetailVOsByHeaderId", headerId );
   }

   @Override
   public QuestionDetailVO getQuestionDetailVOByDetailId( String detailId ) throws KANException
   {
      return ( QuestionDetailVO ) select( "getQuestionDetailVOByDetailId", detailId );
   }

   @Override
   public String getMaxOptionIndexByHeaderId( String headerId ) throws KANException
   {
      return ( String ) select( "getMaxOptionIndexByHeaderId", headerId );
   }

}
