package com.kan.wx.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.wx.dao.inf.AnswerDao;
import com.kan.wx.dao.inf.QuestionDetailDao;
import com.kan.wx.dao.inf.QuestionHeaderDao;
import com.kan.wx.domain.AnswerVO;
import com.kan.wx.domain.QuestionDetailVO;
import com.kan.wx.domain.QuestionHeaderVO;
import com.kan.wx.service.inf.QuestionHeaderService;

public class QuestionHeaderServiceImpl extends ContextService implements QuestionHeaderService
{
   private QuestionDetailDao questionDetailDao;

   private AnswerDao answerDao;

   public QuestionDetailDao getQuestionDetailDao()
   {
      return questionDetailDao;
   }

   public void setQuestionDetailDao( QuestionDetailDao questionDetailDao )
   {
      this.questionDetailDao = questionDetailDao;
   }

   public AnswerDao getAnswerDao()
   {
      return answerDao;
   }

   public void setAnswerDao( AnswerDao answerDao )
   {
      this.answerDao = answerDao;
   }

   @Override
   public PagedListHolder getQuestionHeaderVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final QuestionHeaderVO questionHeaderVO = ( QuestionHeaderVO ) pagedListHolder.getObject();
      final QuestionHeaderDao dao = ( QuestionHeaderDao ) getDao();
      pagedListHolder.setHolderSize( dao.countQuestionHeaderVOsByCondition( questionHeaderVO ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( dao.getQuestionHeaderVOsByCondition( questionHeaderVO, new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( dao.getQuestionHeaderVOsByCondition( questionHeaderVO ) );
      }
      return pagedListHolder;
   }

   @Override
   public QuestionHeaderVO getQuestionHeaderVOByHeaderId( String headerId ) throws KANException
   {
      return ( ( QuestionHeaderDao ) getDao() ).getQuestionHeaderVOByHeaderId( headerId );
   }

   @Override
   public int insertQuestionHeader( QuestionHeaderVO questionHeaderVO ) throws KANException
   {
      return ( ( QuestionHeaderDao ) getDao() ).insertQuestionHeader( questionHeaderVO );
   }

   @Override
   public int updateQuestionHeader( QuestionHeaderVO questionHeaderVO ) throws KANException
   {
      return ( ( QuestionHeaderDao ) getDao() ).updateQuestionHeader( questionHeaderVO );
   }

   @Override
   public void deleteQuestionHeader( QuestionHeaderVO questionHeaderVO ) throws KANException
   {
      try
      {
         startTransaction();

         List< Object > questionDetailVOs = this.getQuestionDetailDao().getQuestionDetailVOsByHeaderId( questionHeaderVO.getHeaderId() );

         List< Object > answerVOs = this.getAnswerDao().getAnswerVOsByHeaderId( questionHeaderVO.getHeaderId() );

         if ( questionDetailVOs != null && questionDetailVOs.size() > 0 )
         {
            for ( Object o : questionDetailVOs )
            {
               this.getQuestionDetailDao().deleteQuestionDetail( ( QuestionDetailVO ) o );
            }
         }
         if ( answerVOs != null && answerVOs.size() > 0 )
         {
            for ( Object o : answerVOs )
            {
               this.getAnswerDao().deleteAnswer( ( AnswerVO ) o );
            }
         }

         ( ( QuestionHeaderDao ) getDao() ).deleteQuestionHeader( questionHeaderVO );

         commitTransaction();
      }
      catch ( Exception e )
      {
         rollbackTransaction();
         throw new KANException( e );
      }
   }

}
