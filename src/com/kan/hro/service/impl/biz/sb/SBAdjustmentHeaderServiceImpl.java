package com.kan.hro.service.impl.biz.sb;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentHeaderDao;
import com.kan.hro.domain.biz.sb.SBAdjustmentDTO;
import com.kan.hro.domain.biz.sb.SBAdjustmentDetailVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentHeaderService;

public class SBAdjustmentHeaderServiceImpl extends ContextService implements SBAdjustmentHeaderService
{
   // 对象类名（例如，com.kan.base.domain.BaseVO）
   public static final String OBJECT_CLASS = "com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO";

   // Service Name（例如，Spring定义的Bean。即 spring配置文件中 service对应Bean的ID ）
   public static final String SERVICE_BEAN = "sbAdjustmentHeaderService";

   // 注入WorkflowService
   private WorkflowService workflowService;

   // 注入SBAdjustmentDetailDao
   private SBAdjustmentDetailDao sbAdjustmentDetailDao;

   public WorkflowService getWorkflowService()
   {
      return workflowService;
   }

   public void setWorkflowService( WorkflowService workflowService )
   {
      this.workflowService = workflowService;
   }

   public SBAdjustmentDetailDao getSbAdjustmentDetailDao()
   {
      return sbAdjustmentDetailDao;
   }

   public void setSbAdjustmentDetailDao( SBAdjustmentDetailDao sbAdjustmentDetailDao )
   {
      this.sbAdjustmentDetailDao = sbAdjustmentDetailDao;
   }

   @Override
   public PagedListHolder getSBAdjustmentHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SBAdjustmentHeaderDao sbAdjustmentHeaderDao = ( SBAdjustmentHeaderDao ) getDao();
      pagedListHolder.setHolderSize( sbAdjustmentHeaderDao.countSBAdjustmentHeaderVOsByCondition( ( SBAdjustmentHeaderVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( sbAdjustmentHeaderDao.getSBAdjustmentHeaderVOsByCondition( ( SBAdjustmentHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbAdjustmentHeaderDao.getSBAdjustmentHeaderVOsByCondition( ( SBAdjustmentHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public SBAdjustmentHeaderVO getSBAdjustmentHeaderVOByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException
   {
      return ( ( SBAdjustmentHeaderDao ) getDao() ).getSBAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );
   }

   @Override
   public int updateSBAdjustmentHeader( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         // 存在选中的ID - 批量更改
         if ( KANUtil.filterEmpty( sbAdjustmentHeaderVO.getSelectedIds() ) != null )
         {
            // 分割
            for ( String selectedId : sbAdjustmentHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获得操作对象
               final SBAdjustmentHeaderVO tempSBAdjustmentHeaderVO = ( ( SBAdjustmentHeaderDao ) getDao() ).getSBAdjustmentHeaderVOByAdjustmentHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );
               tempSBAdjustmentHeaderVO.setModifyBy( sbAdjustmentHeaderVO.getModifyBy() );
               tempSBAdjustmentHeaderVO.setModifyDate( sbAdjustmentHeaderVO.getModifyDate() );
               tempSBAdjustmentHeaderVO.setStatus( sbAdjustmentHeaderVO.getStatus() );
               ( ( SBAdjustmentHeaderDao ) getDao() ).updateSBAdjustmentHeader( tempSBAdjustmentHeaderVO );
            }
         }
         else
         {
            // 更改主表
            ( ( SBAdjustmentHeaderDao ) getDao() ).updateSBAdjustmentHeader( sbAdjustmentHeaderVO );

            // 初始化SBAdjustmentDetailVO列表
            final List< Object > sbAdjustmentDetailVOs = this.sbAdjustmentDetailDao.getSBAdjustmentDetailVOsByAdjustmentHeaderId( sbAdjustmentHeaderVO.getAdjustmentHeaderId() );

            // 更改从表
            if ( sbAdjustmentDetailVOs != null && sbAdjustmentDetailVOs.size() > 0 )
            {
               for ( Object sbAdjustmentDetailVOObject : sbAdjustmentDetailVOs )
               {
                  final SBAdjustmentDetailVO sbAdjustmentDetailVO = ( SBAdjustmentDetailVO ) sbAdjustmentDetailVOObject;
                  sbAdjustmentDetailVO.setMonthly( sbAdjustmentHeaderVO.getMonthly() );
                  sbAdjustmentDetailVO.setModifyBy( sbAdjustmentHeaderVO.getModifyBy() );
                  sbAdjustmentDetailVO.setModifyDate( sbAdjustmentHeaderVO.getModifyDate() );

                  this.sbAdjustmentDetailDao.updateSBAdjustmentDetail( sbAdjustmentDetailVO );
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
   public int insertSBAdjustmentHeader( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException
   {
      return ( ( SBAdjustmentHeaderDao ) getDao() ).insertSBAdjustmentHeader( sbAdjustmentHeaderVO );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-12-06
   public int deleteSBAdjustmentHeader( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         // 标记删除主表
         sbAdjustmentHeaderVO.setDeleted( SBAdjustmentHeaderVO.FALSE );
         ( ( SBAdjustmentHeaderDao ) getDao() ).updateSBAdjustmentHeader( sbAdjustmentHeaderVO );

         // 初始化SBAdjustmentDetailVO列表
         final List< Object > sbAdjustmentDetailVOs = this.sbAdjustmentDetailDao.getSBAdjustmentDetailVOsByAdjustmentHeaderId( sbAdjustmentHeaderVO.getAdjustmentHeaderId() );

         // 标记删除从表
         if ( sbAdjustmentDetailVOs != null && sbAdjustmentDetailVOs.size() > 0 )
         {
            for ( Object sbAdjustmentDetailVOObject : sbAdjustmentDetailVOs )
            {
               final SBAdjustmentDetailVO sbAdjustmentDetailVO = ( SBAdjustmentDetailVO ) sbAdjustmentDetailVOObject;
               sbAdjustmentDetailVO.setModifyBy( sbAdjustmentHeaderVO.getModifyBy() );
               sbAdjustmentDetailVO.setModifyDate( sbAdjustmentHeaderVO.getModifyDate() );
               sbAdjustmentDetailVO.setDeleted( SBAdjustmentHeaderVO.FALSE );

               this.sbAdjustmentDetailDao.updateSBAdjustmentDetail( sbAdjustmentDetailVO );
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
   public List< Object > getSBAdjustmentHeaderVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( SBAdjustmentHeaderDao ) getDao() ).getSBAdjustmentHeaderVOsByAccountId( accountId );
   }

   @Override
   public List< SBAdjustmentDTO > getSBAdjustmentDTOsByCondition( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException
   {
      // 初始化SBAdjustmentDTO List
      final List< SBAdjustmentDTO > sbAdjustmentDTOs = new ArrayList< SBAdjustmentDTO >();
      // 初始化SBAdjustmentHeaderVO List
      final List< Object > sbAdjustmentHeaderVOs = ( ( SBAdjustmentHeaderDao ) getDao() ).getSBAdjustmentHeaderVOsByCondition( sbAdjustmentHeaderVO );

      if ( sbAdjustmentHeaderVOs != null && sbAdjustmentHeaderVOs.size() > 0 )
      {
         for ( Object sbAdjustmentHeaderVOObject : sbAdjustmentHeaderVOs )
         {
            // 初始化缓存对象
            final SBAdjustmentHeaderVO tempSBAdjustmentHeaderVO = ( SBAdjustmentHeaderVO ) sbAdjustmentHeaderVOObject;
            // 初始化SBDTO
            final SBAdjustmentDTO sbAdjustmentDTO = new SBAdjustmentDTO();

            // 装载SBAdjustmentHeaderVO
            sbAdjustmentDTO.setSbAdjustmentHeaderVO( tempSBAdjustmentHeaderVO );

            // 装载SBAdjustmentDetailVO List
            fetchSBAdjustmentDetail( sbAdjustmentDTO, tempSBAdjustmentHeaderVO );

            sbAdjustmentDTOs.add( sbAdjustmentDTO );
         }
      }

      return sbAdjustmentDTOs;
   }

   // 装载社保调整明细
   private void fetchSBAdjustmentDetail( final SBAdjustmentDTO sbAdjustmentDTO, final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException
   {
      // 初始化并装载社保调整明细
      final List< Object > sbAdjustmentDetailVOs = this.getSbAdjustmentDetailDao().getSBAdjustmentDetailVOsByAdjustmentHeaderId( sbAdjustmentHeaderVO.getAdjustmentHeaderId() );

      if ( sbAdjustmentDetailVOs != null && sbAdjustmentDetailVOs.size() > 0 )
      {
         for ( Object sbAdjustmentDetailVOObject : sbAdjustmentDetailVOs )
         {
            // 初始化SBAdjustmentDetailVO
            final SBAdjustmentDetailVO sbAdjustmentDetailVO = ( SBAdjustmentDetailVO ) sbAdjustmentDetailVOObject;

            if ( sbAdjustmentDetailVO.getStatus().equals( "1" ) )
            {
               sbAdjustmentDTO.getSbAdjustmentDetailVOs().add( sbAdjustmentDetailVO );
            }
         }
      }
   }

}
