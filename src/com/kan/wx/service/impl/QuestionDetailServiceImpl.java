package com.kan.wx.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.wx.dao.inf.QuestionDetailDao;
import com.kan.wx.domain.QuestionDetailVO;
import com.kan.wx.service.inf.QuestionDetailService;

public class QuestionDetailServiceImpl extends ContextService implements QuestionDetailService
{

   @Override
   public PagedListHolder getQuestionDetailVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final QuestionDetailVO questionDetailVO = ( QuestionDetailVO ) pagedListHolder.getObject();
      final QuestionDetailDao dao = ( QuestionDetailDao ) getDao();
      pagedListHolder.setHolderSize( dao.countQuestionDetailVOsByCondition( questionDetailVO ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( dao.getQuestionDetailVOsByCondition( questionDetailVO, new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( dao.getQuestionDetailVOsByCondition( questionDetailVO ) );
      }
      return pagedListHolder;
   }

   @Override
   public QuestionDetailVO getQuestionDetailVOByDetailId( String detailId ) throws KANException
   {
      return ( ( QuestionDetailDao ) getDao() ).getQuestionDetailVOByDetailId( detailId );
   }

   @Override
   public int insertQuestionDetail( QuestionDetailVO questionDetailVO ) throws KANException
   {
      return ( ( QuestionDetailDao ) getDao() ).insertQuestionDetail( questionDetailVO );
   }

   @Override
   public int updateQuestionDetail( QuestionDetailVO questionDetailVO ) throws KANException
   {
      return ( ( QuestionDetailDao ) getDao() ).updateQuestionDetail( questionDetailVO );
   }

   @Override
   public void deleteQuestionDetail( QuestionDetailVO questionDetailVO ) throws KANException
   {
      ( ( QuestionDetailDao ) getDao() ).deleteQuestionDetail( questionDetailVO );
   }

   @Override
   public List< Object > getQuestionDetailVOsByHeaderId( String headerId ) throws KANException
   {
      return ( ( QuestionDetailDao ) getDao() ).getQuestionDetailVOsByHeaderId( headerId );
   }

   @Override
   public String getMaxOptionIndexByHeaderId( String headerId ) throws KANException
   {
      return ( ( QuestionDetailDao ) getDao() ).getMaxOptionIndexByHeaderId( headerId );
   }

}
