package com.kan.wx.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.wx.dao.inf.AnswerDao;
import com.kan.wx.domain.AnswerVO;
import com.kan.wx.service.inf.AnswerService;

public class AnswerServiceImpl extends ContextService implements AnswerService
{

   @Override
   public PagedListHolder getAnswerVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final AnswerVO answerVO = ( AnswerVO ) pagedListHolder.getObject();
      final AnswerDao dao = ( AnswerDao ) getDao();
      pagedListHolder.setHolderSize( dao.countAnswerVOsByCondition( answerVO ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( dao.getAnswerVOsByCondition( answerVO, new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( dao.getAnswerVOsByCondition( answerVO ) );
      }
      return pagedListHolder;
   }

   @Override
   public int insertAnswer( AnswerVO answerVO ) throws KANException
   {
      return ( ( AnswerDao ) getDao() ).insertAnswer( answerVO );
   }

   @Override
   public List< Object > getAnswerVOByAnswerId( String answerId ) throws KANException
   {
      return ( ( AnswerDao ) getDao() ).getAnswerVOByAnswerId( answerId );
   }

   @Override
   public AnswerVO getAnswerVOByWXUserIdAndQuestionId( AnswerVO answerVO ) throws KANException
   {
      List< Object > list = ( ( AnswerDao ) getDao() ).getAnswerVOByWXUserIdAndQuestionId( answerVO );
      if ( list != null && list.size() > 0 )
      {
         return ( AnswerVO ) list.get( 0 );
      }
      return null;
   }

}
