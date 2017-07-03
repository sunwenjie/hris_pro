package com.kan.hro.service.impl.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.management.TaxVO;
import com.kan.base.domain.workflow.WorkflowActualDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.settlement.AdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.settlement.AdjustmentHeaderDao;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.settlement.AdjustmentDetailVO;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;
import com.kan.hro.service.inf.biz.settlement.AdjustmentHeaderService;

public class AdjustmentHeaderServiceImpl extends ContextService implements AdjustmentHeaderService
{

   // 对象类名（例如，com.kan.base.domain.BaseVO）
   public static final String OBJECT_CLASS = "com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO";

   // Service Name（例如，Spring定义的Bean。即 spring配置文件中 service对应Bean的ID ）
   public static final String SERVICE_BEAN = "adjustmentHeaderService";

   // 工作流service
   private WorkflowService workflowService;

   public WorkflowService getWorkflowService()
   {
      return workflowService;
   }

   public void setWorkflowService( WorkflowService workflowService )
   {
      this.workflowService = workflowService;
   }
   // 注入结算 - 账单调整 - 详情 DAO
   private AdjustmentDetailDao adjustmentDetailDao;

   public AdjustmentDetailDao getAdjustmentDetailDao()
   {
      return adjustmentDetailDao;
   }

   public void setAdjustmentDetailDao( AdjustmentDetailDao adjustmentDetailDao )
   {
      this.adjustmentDetailDao = adjustmentDetailDao;
   }

   // 注入客户 - 服务订单DAO
   private ClientOrderHeaderDao clientOrderHeaderDao;

   public ClientOrderHeaderDao getClientOrderHeaderDao()
   {
      return clientOrderHeaderDao;
   }

   public void setClientOrderHeaderDao( ClientOrderHeaderDao clientOrderHeaderDao )
   {
      this.clientOrderHeaderDao = clientOrderHeaderDao;
   }

   @Override
   public PagedListHolder getAdjustmentHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final AdjustmentHeaderDao adjustmentHeaderDao = ( AdjustmentHeaderDao ) getDao();
      pagedListHolder.setHolderSize( adjustmentHeaderDao.countAdjustmentHeaderVOsByCondition( ( AdjustmentHeaderVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( adjustmentHeaderDao.getAdjustmentHeaderVOsByCondition( ( AdjustmentHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( adjustmentHeaderDao.getAdjustmentHeaderVOsByCondition( ( AdjustmentHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public AdjustmentHeaderVO getAdjustmentHeaderVOByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException
   {
      return ( ( AdjustmentHeaderDao ) getDao() ).getAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );
   }

   @Override
   public int updateAdjustmentHeader( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         // 存在选中的ID - 批量更改
         if ( KANUtil.filterEmpty( adjustmentHeaderVO.getSelectedIds() ) != null )
         {
            // 分割
            for ( String selectedId : adjustmentHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获得操作对象
               final AdjustmentHeaderVO tempAdjustmentHeaderVO = ( ( AdjustmentHeaderDao ) getDao() ).getAdjustmentHeaderVOByAdjustmentHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );
               tempAdjustmentHeaderVO.setModifyBy( adjustmentHeaderVO.getModifyBy() );
               tempAdjustmentHeaderVO.setModifyDate( adjustmentHeaderVO.getModifyDate() );
               tempAdjustmentHeaderVO.setStatus( adjustmentHeaderVO.getStatus() );
               ( ( AdjustmentHeaderDao ) getDao() ).updateAdjustmentHeader( setTaxInfo( tempAdjustmentHeaderVO ) );
            }
         }
         else
         {
            // 更改主表
            ( ( AdjustmentHeaderDao ) getDao() ).updateAdjustmentHeader( setTaxInfo( adjustmentHeaderVO ) );

            final AdjustmentDetailVO adjustmentDetailVO = new AdjustmentDetailVO();
            adjustmentDetailVO.setAdjustmentHeaderId( adjustmentHeaderVO.getAdjustmentHeaderId() );
            adjustmentDetailVO.setStatus( AdjustmentHeaderVO.TRUE );
            // 初始化AdjustmentDetailVO列表
            final List< Object > adjustmentDetailVOs = this.adjustmentDetailDao.getAdjustmentDetailVOsByCondition( adjustmentDetailVO );

            // 更改从表
            if ( adjustmentDetailVOs != null && adjustmentDetailVOs.size() > 0 )
            {
               for ( Object adjustmentDetailVOObject : adjustmentDetailVOs )
               {
                  final AdjustmentDetailVO tempAdjustmentDetailVO = ( AdjustmentDetailVO ) adjustmentDetailVOObject;
                  tempAdjustmentDetailVO.setModifyBy( adjustmentHeaderVO.getModifyBy() );
                  tempAdjustmentDetailVO.setModifyDate( adjustmentHeaderVO.getModifyDate() );

                  this.adjustmentDetailDao.updateAdjustmentDetail( tempAdjustmentDetailVO );
               }
            }
         }

         // 提交事务
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }

      return 1;
   }

   @Override
   public int insertAdjustmentHeader( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      int rows = 0;
      rows = ( ( AdjustmentHeaderDao ) getDao() ).insertAdjustmentHeader( setTaxInfo( adjustmentHeaderVO ) );
      if ( adjustmentHeaderVO.getSubAction() != null && adjustmentHeaderVO.getSubAction().trim().equals( BaseAction.SUBMIT_OBJECT ) )
      {
         rows = submitAdjustmentHeader( adjustmentHeaderVO );
      }
      return rows;
   }

   @Override
   public int deleteAdjustmentHeader( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         // 标记删除主表
         adjustmentHeaderVO.setDeleted( AdjustmentHeaderVO.FALSE );
         ( ( AdjustmentHeaderDao ) getDao() ).updateAdjustmentHeader( adjustmentHeaderVO );

         final AdjustmentDetailVO adjustmentDetailVO = new AdjustmentDetailVO();
         adjustmentDetailVO.setAdjustmentHeaderId( adjustmentHeaderVO.getAdjustmentHeaderId() );
         adjustmentDetailVO.setStatus( AdjustmentHeaderVO.TRUE );

         // 初始化AdjustmentDetailVO列表
         final List< Object > adjustmentDetailVOs = this.adjustmentDetailDao.getAdjustmentDetailVOsByCondition( adjustmentDetailVO );

         // 标记删除从表
         if ( adjustmentDetailVOs != null && adjustmentDetailVOs.size() > 0 )
         {
            for ( Object adjustmentDetailVOObject : adjustmentDetailVOs )
            {
               final AdjustmentDetailVO tempAdjustmentDetailVOObject = ( AdjustmentDetailVO ) adjustmentDetailVOObject;
               tempAdjustmentDetailVOObject.setModifyBy( adjustmentHeaderVO.getModifyBy() );
               tempAdjustmentDetailVOObject.setModifyDate( adjustmentHeaderVO.getModifyDate() );
               tempAdjustmentDetailVOObject.setDeleted( AdjustmentHeaderVO.FALSE );

               this.adjustmentDetailDao.updateAdjustmentDetail( tempAdjustmentDetailVOObject );
            }
         }

         // 提交事务
         commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public List< Object > getAdjustmentHeaderVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( AdjustmentHeaderDao ) getDao() ).getAdjustmentHeaderVOsByAccountId( accountId );
   }

   private AdjustmentHeaderVO setTaxInfo( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      final ClientOrderHeaderVO clientOrderHeaderVO = this.clientOrderHeaderDao.getClientOrderHeaderVOByOrderHeaderId( adjustmentHeaderVO.getOrderId() );
      if ( clientOrderHeaderVO != null && KANUtil.filterEmpty( clientOrderHeaderVO.getTaxId() ) != null )
      {
         final TaxVO taxVO = KANConstants.getKANAccountConstants( adjustmentHeaderVO.getAccountId() ).getTaxVOByTaxId( clientOrderHeaderVO.getTaxId() );
         adjustmentHeaderVO.setTaxId( clientOrderHeaderVO.getTaxId() );
         if ( taxVO != null )
         {
            adjustmentHeaderVO.setTaxNameZH( taxVO.getNameZH() );
            adjustmentHeaderVO.setTaxNameEN( taxVO.getNameEN() );
         }
      }

      return adjustmentHeaderVO;
   }

   @Override
   public int submitAdjustmentHeader( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      if ( !WorkflowService.isPassObject( adjustmentHeaderVO ) )
      {
         final HistoryVO historyVO = generateHistoryVO( adjustmentHeaderVO );
         // 权限Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( adjustmentHeaderVO );
         // 存在工作流
         if ( workflowActualDTO != null )
         {
            if ( adjustmentHeaderVO.getStatus() != null && !adjustmentHeaderVO.getStatus().trim().equals( "3" ) )
            {
               // 状态改为待审核
               adjustmentHeaderVO.setStatus( "2" );
               updateAdjustmentHeader( adjustmentHeaderVO );
            }

            // Service的方法
            historyVO.setServiceMethod( "submitAdjustmentHeader" );
            historyVO.setObjectId( adjustmentHeaderVO.getAdjustmentHeaderId() );

            // 批准状态
            adjustmentHeaderVO.setStatus( "3" );
            final String passObject = KANUtil.toJSONObject( adjustmentHeaderVO ).toString();

            // 退回状态
            adjustmentHeaderVO.setStatus( "4" );
            final String failObject = KANUtil.toJSONObject( adjustmentHeaderVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, adjustmentHeaderVO );

            return -1;
         }
         // 没有工作流
         else
         {
            adjustmentHeaderVO.setStatus( "2" );
            return updateAdjustmentHeader( adjustmentHeaderVO );
         }
      }

      return updateAdjustmentHeader( adjustmentHeaderVO );
   }

   public HistoryVO generateHistoryVO( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException
   {
      HistoryVO history = adjustmentHeaderVO.getHistoryVO();
      history.setAccessAction( "HRO_SETTLE_ADJUSTMENT_HEADER" );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( "HRO_SETTLE_ADJUSTMENT_HEADER" ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getAdjustmentHeaderVOByAdjustmentHeaderId" );
      history.setAccountId( adjustmentHeaderVO.getAccountId() );
      history.setObjectType( "2" );

      return history;
   }

}
