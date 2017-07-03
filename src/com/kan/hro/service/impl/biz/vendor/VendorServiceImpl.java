package com.kan.hro.service.impl.biz.vendor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.management.BankVO;
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.domain.workflow.WorkflowActualDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.vendor.VendorContactDao;
import com.kan.hro.dao.inf.biz.vendor.VendorDao;
import com.kan.hro.dao.inf.biz.vendor.VendorServiceDao;
import com.kan.hro.domain.biz.vendor.VendorContactVO;
import com.kan.hro.domain.biz.vendor.VendorDTO;
import com.kan.hro.domain.biz.vendor.VendorServiceVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.vendor.VendorService;
import com.kan.hro.web.actions.biz.vendor.VendorAction;

public class VendorServiceImpl extends ContextService implements VendorService
{

   // 对象类名（例如，com.kan.base.domain.BaseVO）
   public static final String OBJECT_CLASS = "com.kan.hro.domain.biz.vendor.VendorVO";

   // Service Name（例如，Spring定义的Bean。即 spring配置文件中 service对应Bean的ID ）
   public static final String SERVICE_BEAN = "vendorService";

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

   // 注入供应商联系人DAO
   private VendorContactDao vendorContactDao;

   // 注入供应商服务DAO
   private VendorServiceDao vendorServiceDao;

   public VendorContactDao getVendorContactDao()
   {
      return vendorContactDao;
   }

   public void setVendorContactDao( VendorContactDao vendorContactDao )
   {
      this.vendorContactDao = vendorContactDao;
   }

   public VendorServiceDao getVendorServiceDao()
   {
      return vendorServiceDao;
   }

   public void setVendorServiceDao( VendorServiceDao vendorServiceDao )
   {
      this.vendorServiceDao = vendorServiceDao;
   }

   @Override
   public PagedListHolder getVendorVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final VendorDao vendorDao = ( VendorDao ) getDao();
      pagedListHolder.setHolderSize( vendorDao.countVendorVOsByCondition( ( VendorVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( vendorDao.getVendorVOsByCondition( ( VendorVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( vendorDao.getVendorVOsByCondition( ( VendorVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public VendorVO getVendorVOByVendorId( final String vendorId ) throws KANException
   {
      return ( ( VendorDao ) getDao() ).getVendorVOByVendorId( vendorId );
   }

   @Override
   public int insertVendor( final VendorVO vendorVO ) throws KANException
   {
      int rows = 0;
      final BankVO bankVO = KANConstants.getKANAccountConstants( vendorVO.getAccountId() ).getBankVOByBankId( vendorVO.getBankId() );
      if ( bankVO != null )
      {
         vendorVO.setBankName( bankVO.getNameZH() );
      }

      rows = ( ( VendorDao ) getDao() ).insertVendor( vendorVO );

      if ( vendorVO.getSubAction() != null && vendorVO.getSubAction().trim().equals( BaseAction.SUBMIT_OBJECT ) )
      {
         rows = submitVendor( vendorVO );
      }

      return rows;
   }

   @Override
   public int updateVendor( final VendorVO vendorVO ) throws KANException
   {
      final BankVO bankVO = KANConstants.getKANAccountConstants( vendorVO.getAccountId() ).getBankVOByBankId( vendorVO.getBankId() );
      if ( bankVO != null )
      {
         vendorVO.setBankName( bankVO.getNameZH() );
      }
      else
      {
         vendorVO.setBankName( null );
      }
      return ( ( VendorDao ) getDao() ).updateVendor( vendorVO );
   }

   @Override
   public int deleteVendor( final VendorVO vendorVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         // 标记删除VendorVO
         vendorVO.setDeleted( VendorVO.FALSE );
         ( ( VendorDao ) getDao() ).updateVendor( vendorVO );

         // 准备搜索条件
         final VendorContactVO vendorContactVO = new VendorContactVO();
         vendorContactVO.setAccountId( vendorVO.getAccountId() );
         vendorContactVO.setVendorId( vendorVO.getVendorId() );
         vendorContactVO.setStatus( VendorVO.TRUE );

         // 获取VendorContactVO列表
         final List< Object > vendorContactVOs = this.vendorContactDao.getVendorContactVOsByCondition( vendorContactVO );

         // 循环标记删除联系人
         if ( vendorContactVOs != null && vendorContactVOs.size() > 0 )
         {
            for ( Object vendorContactVOObject : vendorContactVOs )
            {
               ( ( VendorContactVO ) vendorContactVOObject ).setDeleted( VendorVO.FALSE );
               ( ( VendorContactVO ) vendorContactVOObject ).setModifyBy( vendorVO.getModifyBy() );
               ( ( VendorContactVO ) vendorContactVOObject ).setModifyDate( new Date() );
               this.vendorContactDao.updateVendorContact( ( ( VendorContactVO ) vendorContactVOObject ) );
            }
         }

         // 准备搜索条件
         final VendorServiceVO vendorServiceVO = new VendorServiceVO();
         vendorServiceVO.setAccountId( vendorServiceVO.getAccountId() );
         vendorServiceVO.setVendorId( vendorVO.getVendorId() );
         vendorServiceVO.setStatus( VendorVO.TRUE );

         // 获取VendorServiceVO列表
         final List< Object > vendorServiceVOs = this.vendorServiceDao.getVendorServiceVOsByCondition( vendorServiceVO );

         // 循环标记删除服务
         if ( vendorServiceVOs != null && vendorServiceVOs.size() > 0 )
         {
            for ( Object vendorServiceVOObject : vendorServiceVOs )
            {
               ( ( VendorServiceVO ) vendorServiceVOObject ).setDeleted( VendorVO.FALSE );
               ( ( VendorServiceVO ) vendorServiceVOObject ).setModifyBy( vendorVO.getModifyBy() );
               ( ( VendorServiceVO ) vendorServiceVOObject ).setModifyDate( new Date() );
               this.vendorServiceDao.updateVendorService( ( ( VendorServiceVO ) vendorServiceVOObject ) );
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
   public List< VendorDTO > getVendorDTOsByAccountId( final String accountId ) throws KANException
   {
      // 初始化DTO列表对象
      final List< VendorDTO > vendorDTOs = new ArrayList< VendorDTO >();

      final VendorVO vendorVO = new VendorVO();
      vendorVO.setAccountId( accountId );
      // vendorVO.setStatus( VendorVO.TRUE );
      // 获取所有有效的Vendor
      final List< Object > vendorVOs = ( ( VendorDao ) getDao() ).getVendorVOsByCondition( vendorVO );

      // 遍历VendorVO列表
      if ( vendorVOs != null && vendorVOs.size() > 0 )
      {
         for ( Object vendorVOObject : vendorVOs )
         {
            // 初始化VendorDTO对象
            final VendorDTO vendorDTO = new VendorDTO();

            // 装载VendorVO
            vendorDTO.setVendorVO( ( VendorVO ) vendorVOObject );

            // 获取VendorContactVO List
            final List< Object > vendorContactVOs = this.vendorContactDao.getVendorContactVOsByVendorId( vendorDTO.getVendorVO().getVendorId() );

            // 装载VendorContactVO
            if ( vendorContactVOs != null && vendorContactVOs.size() > 0 )
            {
               for ( Object vendorContactVOObject : vendorContactVOs )
               {
                  vendorDTO.getVendorContactVOs().add( ( VendorContactVO ) vendorContactVOObject );
               }
            }

            // 获取VendorServiceVO List
            final List< Object > vendorServiceVOs = this.vendorServiceDao.getVendorServiceVOsByVendorId( vendorDTO.getVendorVO().getVendorId() );

            // 装载VendorServiceVO
            if ( vendorServiceVOs != null && vendorServiceVOs.size() > 0 )
            {
               for ( Object vendorServiceVOObject : vendorServiceVOs )
               {
                  vendorDTO.getVendorServiceVOs().add( ( VendorServiceVO ) vendorServiceVOObject );
               }
            }

            vendorDTOs.add( vendorDTO );
         }
      }
      return vendorDTOs;
   }

   @Override
   public List< Object > getVendorBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return ( ( VendorDao ) getDao() ).getVendorBaseViewsByAccountId( accountId );
   }

   @Override
   public List< Object > getVendorVOsBySBHeaderId( String headerId ) throws KANException
   {
      return ( ( VendorDao ) getDao() ).getVendorVOsBySBHeaderId( headerId );
   }

   @Override
   public VendorDTO getVendorDTOByCondition( final VendorVO vendorVO ) throws KANException
   {
      // 获得当前账户供应商DTO列表
      final List< VendorDTO > vendorDTOs = getVendorDTOsByAccountId( vendorVO.getAccountId() );
      if ( vendorDTOs != null && vendorDTOs.size() > 0 )
      {
         for ( VendorDTO vendorDTO : vendorDTOs )
         {
            if ( vendorDTO.getVendorVO().getVendorId().equals( vendorVO.getVendorId() ) )
            {
               return vendorDTO;
            }
         }
      }
      return null;
   }

   @Override
   public int submitVendor( final VendorVO vendorVO ) throws KANException
   {
      if ( !WorkflowService.isPassObject( vendorVO ) )
      {
         final HistoryVO historyVO = generateHistoryVO( vendorVO );
         // 权限Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( vendorVO );
         // 存在工作流
         if ( workflowActualDTO != null )
         {
            if ( vendorVO.getStatus() != null && !vendorVO.getStatus().trim().equals( "3" ) )
            {
               // 状态改为待审核
               vendorVO.setStatus( "2" );
               updateVendor( vendorVO );
            }

            // Service的方法
            historyVO.setServiceMethod( "submitVendor" );
            historyVO.setObjectId( vendorVO.getVendorId() );

            // 批准状态
            vendorVO.setStatus( "3" );
            final String passObject = KANUtil.toJSONObject( vendorVO ).toString();

            // 退回状态
            vendorVO.setStatus( "4" );
            final String failObject = KANUtil.toJSONObject( vendorVO ).toString();

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, vendorVO );

            return -1;
         }
         // 没有工作流
         else
         {
            vendorVO.setStatus( "3" );
            return updateVendor( vendorVO );
         }
      }

      return updateVendor( vendorVO );
   }

   public HistoryVO generateHistoryVO( final VendorVO vendorVO ) throws KANException
   {
      HistoryVO history = vendorVO.getHistoryVO();
      history.setAccessAction( VendorAction.accessAction );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( VendorAction.accessAction ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getVendorVOByVendorId" );
      history.setAccountId( vendorVO.getAccountId() );
      history.setObjectType( "2" );
      history.setNameZH( vendorVO.getNameZH() );
      history.setNameEN( vendorVO.getNameEN() );

      return history;
   }

   @Override
   public List< Object > getVendorVOsByCondition( final VendorVO vendorVO ) throws KANException
   {
      return ( ( VendorDao ) getDao() ).getVendorVOsByCondition( vendorVO );
   }

   @Override
   public List< Object > getVendorVOsBySBSolutionHeaderVO( final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO ) throws KANException
   {
      return ( ( VendorDao ) getDao() ).getVendorVOsBySBSolutionHeaderVO( socialBenefitSolutionHeaderVO );
   }
}
