package com.kan.hro.service.impl.biz.employee;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.workflow.WorkflowActualDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.impl.workflow.WorkflowService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDetailDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBTempDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractTempDao;
import com.kan.hro.dao.inf.biz.vendor.VendorDao;
import com.kan.hro.dao.inf.biz.vendor.VendorServiceDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDTO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDetailVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBTempVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBTempService;
import com.kan.hro.web.actions.biz.employee.EmployeeContractSBAction;

public class EmployeeContractSBTempServiceImpl extends ContextService implements EmployeeContractSBTempService
{
   public final String OBJECT_CLASS = "com.kan.hro.domain.biz.employee.EmployeeContractSBVO";

   public final String SERVICE_BEAN = "employeeContractSBService";

   // 注入VendorDao
   private VendorDao vendorDao;

   // 注入VendorServiceDao
   private VendorServiceDao vendorServiceDao;

   // 注入EmployeeContractSBDetailDao
   private EmployeeContractSBDetailDao employeeContractSBDetailDao;

   // 注入WorkflowService
   private WorkflowService workflowService;

   // 注入EmployeeContractDao
   private EmployeeContractTempDao employeeContractTempDao;

   public EmployeeContractTempDao getEmployeeContractTempDao()
   {
      return employeeContractTempDao;
   }

   public void setEmployeeContractTempDao( EmployeeContractTempDao employeeContractTempDao )
   {
      this.employeeContractTempDao = employeeContractTempDao;
   }

   public final VendorDao getVendorDao()
   {
      return vendorDao;
   }

   public final void setVendorDao( VendorDao vendorDao )
   {
      this.vendorDao = vendorDao;
   }

   public final VendorServiceDao getVendorServiceDao()
   {
      return vendorServiceDao;
   }

   public final void setVendorServiceDao( VendorServiceDao vendorServiceDao )
   {
      this.vendorServiceDao = vendorServiceDao;
   }

   public EmployeeContractSBDetailDao getEmployeeContractSBDetailDao()
   {
      return employeeContractSBDetailDao;
   }

   public void setEmployeeContractSBDetailDao( EmployeeContractSBDetailDao employeeContractSBDetailDao )
   {
      this.employeeContractSBDetailDao = employeeContractSBDetailDao;
   }

   public WorkflowService getWorkflowService()
   {
      return workflowService;
   }

   public void setWorkflowService( WorkflowService workflowService )
   {
      this.workflowService = workflowService;
   }

   @Override
   public PagedListHolder getEmployeeContractSBTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractSBTempDao employeeContractSBTempDao = ( EmployeeContractSBTempDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractSBTempDao.countEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractSBTempDao.getEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractSBTempDao.getEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractSBTempVO getEmployeeContractSBTempVOByEmployeeSBId( final String employeeSBId ) throws KANException
   {
      return ( ( EmployeeContractSBTempDao ) getDao() ).getEmployeeContractSBTempVOByEmployeeSBId( employeeSBId );
   }

   @Override
   public PagedListHolder getFullEmployeeContractSBTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractSBTempDao employeeContractSBDao = ( EmployeeContractSBTempDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractSBDao.countFullEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractSBDao.getFullEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractSBDao.getFullEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractSBTempVO getFullEmployeeContractSBTempVOByEmployeeSBId( final String employeeSBId ) throws KANException
   {
      return ( ( EmployeeContractSBTempDao ) getDao() ).getFullEmployeeContractSBTempVOByEmployeeSBId( employeeSBId );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-21
   public int insertEmployeeContractSBTemp( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      try
      {
         int rows = 0;

         // 开启事务
         startTransaction();

         rows = ( ( EmployeeContractSBTempDao ) getDao() ).insertEmployeeContractSBTemp( employeeContractSBVO );

         final String[] solutionDetailIdArray = employeeContractSBVO.getSolutionDetailIdArray();
         final String[] baseCompanyArray = employeeContractSBVO.getBaseCompanyArray();
         final String[] basePersonalArray = employeeContractSBVO.getBasePersonalArray();

         if ( solutionDetailIdArray != null && baseCompanyArray != null && basePersonalArray != null && solutionDetailIdArray.length > 0 && baseCompanyArray.length > 0
               && basePersonalArray.length > 0 )
         {
            // 直接插入社保明细
            for ( int i = 0; i < solutionDetailIdArray.length; i++ )
            {
               if ( baseCompanyArray.length > i && basePersonalArray.length > i )
               {
                  final EmployeeContractSBDetailVO employeeContractSBDetailVO = new EmployeeContractSBDetailVO();
                  employeeContractSBDetailVO.setEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );
                  employeeContractSBDetailVO.setSolutionDetailId( solutionDetailIdArray[ i ].trim() );
                  employeeContractSBDetailVO.setBaseCompany( baseCompanyArray[ i ].trim() );
                  employeeContractSBDetailVO.setBasePersonal( basePersonalArray[ i ].trim() );
                  employeeContractSBDetailVO.setCreateBy( employeeContractSBVO.getCreateBy() );
                  employeeContractSBDetailVO.setCreateDate( new Date() );
                  employeeContractSBDetailVO.setModifyBy( employeeContractSBVO.getModifyBy() );
                  employeeContractSBDetailVO.setModifyDate( new Date() );
                  employeeContractSBDetailVO.setStatus( EmployeeContractSBDetailVO.TRUE );
                  employeeContractSBDetailDao.insertEmployeeContractSBDetail( employeeContractSBDetailVO );
               }
            }
         }

         // 提交事务
         commitTransaction();

         if ( employeeContractSBVO.getSubAction() != null && employeeContractSBVO.getSubAction().trim().equals( BaseAction.SUBMIT_OBJECT ) )
         {
            employeeContractSBVO.setDeleted( "1" );
            rows = submitEmployeeContractSBTemp( employeeContractSBVO );
         }

         return rows;
      }
      catch ( Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-21
   public int updateEmployeeContractSBTemp( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      try
      {
         int rows = 0;

         // 开启事务
         startTransaction();

         rows = ( ( EmployeeContractSBTempDao ) getDao() ).updateEmployeeContractSBTemp( employeeContractSBVO );

         //查询社保方案明细
         final List< Object > employeeContractSBDetailVOs = this.employeeContractSBDetailDao.getEmployeeContractSBDetailVOsByEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );

         if ( employeeContractSBDetailVOs != null && employeeContractSBDetailVOs.size() > 0 )
         {

            final String[] solutionDetailIdArray = employeeContractSBVO.getSolutionDetailIdArray();
            final String[] baseCompanyArray = employeeContractSBVO.getBaseCompanyArray();
            final String[] basePersonalArray = employeeContractSBVO.getBasePersonalArray();

            if ( solutionDetailIdArray != null && baseCompanyArray != null && basePersonalArray != null && solutionDetailIdArray.length > 0 && baseCompanyArray.length > 0
                  && basePersonalArray.length > 0 )
            {
               boolean exist[] = new boolean[ solutionDetailIdArray.length ];
               for ( Object employeeContractSBDetailVOObject : employeeContractSBDetailVOs )
               {
                  final EmployeeContractSBDetailVO employeeContractSBDetailVO = ( EmployeeContractSBDetailVO ) employeeContractSBDetailVOObject;
                  String solutionDetailId = employeeContractSBDetailVO.getSolutionDetailId();
                  int index = -1;
                  for ( int i = 0; i < solutionDetailIdArray.length; i++ )
                  {

                     if ( solutionDetailId.equals( solutionDetailIdArray[ i ].trim() ) )
                     {
                        index = i;
                        exist[ i ] = true;
                        break;
                     }
                  }

                  if ( index > -1 )
                  {
                     // 更新
                     employeeContractSBDetailVO.setBaseCompany( baseCompanyArray[ index ].trim() );
                     employeeContractSBDetailVO.setBasePersonal( basePersonalArray[ index ].trim() );
                     employeeContractSBDetailVO.setModifyBy( employeeContractSBVO.getCreateBy() );
                     employeeContractSBDetailVO.setModifyDate( new Date() );
                     employeeContractSBDetailDao.updateEmployeeContractSBDetail( employeeContractSBDetailVO );
                  }
                  else
                  {
                     // 逻辑删除
                     employeeContractSBDetailVO.setDeleted( EmployeeContractSBDetailVO.FALSE );
                     employeeContractSBDetailVO.setModifyDate( new Date() );
                     employeeContractSBDetailDao.updateEmployeeContractSBDetail( employeeContractSBDetailVO );
                  }

               }

               // 添加新增的科目
               for ( int i = 0; i < exist.length; i++ )
               {
                  if ( !exist[ i ] )
                  {
                     final EmployeeContractSBDetailVO employeeContractSBDetailVO = new EmployeeContractSBDetailVO();
                     employeeContractSBDetailVO.setEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );
                     employeeContractSBDetailVO.setSolutionDetailId( solutionDetailIdArray[ i ].trim() );
                     employeeContractSBDetailVO.setBaseCompany( baseCompanyArray[ i ].trim() );
                     employeeContractSBDetailVO.setBasePersonal( basePersonalArray[ i ] );
                     employeeContractSBDetailVO.setCreateBy( employeeContractSBVO.getCreateBy() );
                     employeeContractSBDetailVO.setCreateDate( new Date() );
                     employeeContractSBDetailVO.setModifyBy( employeeContractSBVO.getModifyBy() );
                     employeeContractSBDetailVO.setModifyDate( new Date() );
                     employeeContractSBDetailVO.setStatus( EmployeeContractSBDetailVO.TRUE );
                     employeeContractSBDetailDao.insertEmployeeContractSBDetail( employeeContractSBDetailVO );
                  }
               }
            }
         }

         // 提交事务
         commitTransaction();

         return rows;
      }
      catch ( Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-21
   public int deleteEmployeeContractSBTemp( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      employeeContractSBVO.setDeleted( EmployeeContractSBVO.FALSE );
      return updateEmployeeContractSBTemp( employeeContractSBVO );
   }

   @Override
   // AddAdded Kevin Jin at 2013-11-21
   public int submitEmployeeContractSBTemp( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      if ( !WorkflowService.isPassObject( employeeContractSBVO ) )
      {
         final HistoryVO historyVO = generateHistoryVO( employeeContractSBVO );

         // 权限Id
         historyVO.setRightId( KANConstants.MODULE_RIGHT_SUBMIT );

         // 原始社保状态
         final String originalStatus = employeeContractSBVO.getStatus();

         final WorkflowActualDTO workflowActualDTO = workflowService.getValidWorkflowActualDTO( employeeContractSBVO );
         // 存在工作流
         if ( workflowActualDTO != null )
         {
            if ( originalStatus != null )
            {
               if ( originalStatus.trim().equals( "0" ) )
               {
                  // 状态改为申报提交
                  employeeContractSBVO.setStatus( "1" );
               }
               else if ( originalStatus.trim().equals( "2" ) || originalStatus.trim().equals( "3" ) )
               {
                  // 状态改为退保提交
                  employeeContractSBVO.setStatus( "4" );
               }

               updateEmployeeContractSBTemp( employeeContractSBVO );

               // Service的方法
               historyVO.setServiceMethod( "submitEmployeeContractSB" );
               historyVO.setObjectId( employeeContractSBVO.getEmployeeSBId() );
            }

            String passObject = null;
            String failObject = null;

            // 无社保
            if ( originalStatus.trim().equals( "0" ) )
            {
               // 加保批准
               employeeContractSBVO.setStatus( "2" );
               passObject = KANUtil.toJSONObject( employeeContractSBVO ).toString();
               // 加保拒绝
               employeeContractSBVO.setStatus( "0" );
               failObject = KANUtil.toJSONObject( employeeContractSBVO ).toString();
            }
            // 待申报加保
            else if ( originalStatus.trim().equals( "2" ) )
            {
               // 退保批准
               employeeContractSBVO.setStatus( "5" );
               passObject = KANUtil.toJSONObject( employeeContractSBVO ).toString();
               // 退保拒绝
               employeeContractSBVO.setStatus( "2" );
               failObject = KANUtil.toJSONObject( employeeContractSBVO ).toString();
            }
            // 正常缴纳
            else if ( originalStatus.trim().equals( "3" ) )
            {
               //  退保批准
               employeeContractSBVO.setStatus( "5" );
               passObject = KANUtil.toJSONObject( employeeContractSBVO ).toString();
               //  退保拒绝
               employeeContractSBVO.setStatus( "3" );
               failObject = KANUtil.toJSONObject( employeeContractSBVO ).toString();
            }

            historyVO.setPassObject( passObject );
            historyVO.setFailObject( failObject );

            workflowService.createWorkflowActual( workflowActualDTO, employeeContractSBVO );

            return -1;
         }
         // 没有工作流
         else
         {
            // 无社保
            if ( originalStatus.trim().equals( "0" ) )
            {
               employeeContractSBVO.setStatus( "2" );
            }
            // 待申报加保和正常缴纳状态
            else if ( originalStatus.trim().equals( "2" ) || originalStatus.trim().equals( "3" ) )
            {
               employeeContractSBVO.setStatus( "5" );
            }

            return updateEmployeeContractSBTemp( employeeContractSBVO );
         }
      }

      return updateEmployeeContractSBTemp( employeeContractSBVO );
   }

   @Override
   public List< Object > getEmployeeContractSBTempVOsByContractId( final String contractId ) throws KANException
   {
      return ( ( EmployeeContractSBTempDao ) getDao() ).getEmployeeContractSBTempVOsByContractId( contractId );
   }

   @Override
   public List< EmployeeContractSBDTO > getEmployeeContractSBTempDTOsByContractId( final String contractId ) throws KANException
   {
      // 初始化EmployeeContractSBDTO List
      final List< EmployeeContractSBDTO > employeeContractSBDTOs = new ArrayList< EmployeeContractSBDTO >();

      // 按照条件获取EmployeeContractSBVO List
      final List< Object > employeeContractSBVOs = ( ( EmployeeContractSBTempDao ) getDao() ).getEmployeeContractSBTempVOsByContractId( contractId );

      // 如果存在EmployeeContractSBVO List数据，遍历
      if ( employeeContractSBVOs != null && employeeContractSBVOs.size() > 0 )
      {
         for ( Object employeeContractSBVOObject : employeeContractSBVOs )
         {
            final EmployeeContractSBVO tempEmployeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOObject;
            // 初始化EmployeeContractSBDTO对象
            final EmployeeContractSBDTO employeeContractSBDTO = new EmployeeContractSBDTO();

            /** 装载DTO数据对象 */
            // 装载供应商
            employeeContractSBDTO.setVendorVO( this.getVendorDao().getVendorVOByVendorId( tempEmployeeContractSBVO.getVendorId() ) );

            // 装载供应商服务
            employeeContractSBDTO.setVendorServiceVO( this.getVendorServiceDao().getVendorServiceVOByServiceId( tempEmployeeContractSBVO.getVendorServiceId() ) );

            // 装载EmployeeContractSBVO
            employeeContractSBDTO.setEmployeeContractSBVO( tempEmployeeContractSBVO );

            // 装载EmployeeContractSBDetailVO
            final List< Object > employeeContractSBDetailVOs = this.getEmployeeContractSBDetailDao().getEmployeeContractSBDetailVOsByEmployeeSBId( ( tempEmployeeContractSBVO ).getEmployeeSBId() );
            if ( employeeContractSBDetailVOs != null && employeeContractSBDetailVOs.size() > 0 )
            {
               for ( Object employeeContractSBDetailVOObject : employeeContractSBDetailVOs )
               {
                  employeeContractSBDTO.getEmployeeContractSBDetailVOs().add( ( EmployeeContractSBDetailVO ) employeeContractSBDetailVOObject );
               }
            }

            employeeContractSBDTOs.add( employeeContractSBDTO );
         }
      }

      return employeeContractSBDTOs;
   }

   // Reviewed by Kevin Jin at 2013-11-21
   private HistoryVO generateHistoryVO( final EmployeeContractSBVO employeeContractSBVO ) throws KANException
   {
      final HistoryVO history = employeeContractSBVO.getHistoryVO();
      history.setAccessAction( EmployeeContractSBAction.accessAction );
      history.setModuleId( KANConstants.getModuleIdByAccessAction( EmployeeContractSBAction.accessAction ) );
      history.setObjectClass( OBJECT_CLASS );
      history.setServiceBean( SERVICE_BEAN );
      history.setServiceGetObjByIdMethod( "getEmployeeContractSBVOByEmployeeSBId" );
      // 这里先给方案的ID
      history.setObjectType( "2" );
      history.setAccountId( employeeContractSBVO.getAccountId() );
      history.setNameZH( employeeContractSBVO.getDecodeSbSolutionId() );
      history.setNameEN( employeeContractSBVO.getDecodeSbSolutionId() );
      return history;
   }

   /**  
    * modifyEmployeeContractSBVO
    *  模态框 修改 合同社保方案(单条记录)
    * @param employeeContractSBVO
    * @return
    * @throws KANException
    * @throws ParseException
    */
   @Override
   public String modifyEmployeeContractSBTempVO( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException, ParseException
   {
      String actFlag = "";
      // 获取需要修改的派送协议Contract ID及所要修改的社保方案ID
      employeeContractSBVO.setContractId( KANUtil.decodeStringFromAjax( employeeContractSBVO.getContractId() ) );
      final String solutionId = employeeContractSBVO.getSbSolutionId();

      // 初始化查询对象
      final EmployeeContractSBTempVO employeeContractSBVOTemp = new EmployeeContractSBTempVO();
      employeeContractSBVOTemp.setContractId( employeeContractSBVO.getContractId() );
      employeeContractSBVOTemp.setSbSolutionId( solutionId );
      employeeContractSBVOTemp.setAccountId( employeeContractSBVO.getAccountId() );

      //  如果不存在该社保方案类型则添加
      if ( ( ( EmployeeContractSBTempDao ) getDao() ).countFullEmployeeContractSBTempVOsByCondition( employeeContractSBVOTemp ) == 0 )
      {
         // 更改操作为添加
         actFlag = "addObject";
         employeeContractSBVO.setCreateBy( employeeContractSBVO.getModifyBy() );
         // 清空“退保日期”
         employeeContractSBVO.setEndDate( null );

         employeeContractSBVO.setStatus( "0" );

         // 获取SubAction判断是否需要提交
         final String subAction = employeeContractSBVO.getSubAction();

         if ( subAction != null && subAction.equals( BaseAction.SUBMIT_OBJECT ) )
         {
            actFlag = "submitObject";
            employeeContractSBVO.setStatus( "2" );
         }

         // 更新日期
         updateDateByCondition( employeeContractSBVO );
         // 新增
         ( ( EmployeeContractSBTempDao ) getDao() ).insertEmployeeContractSBTemp( employeeContractSBVO );

         final String[] solutionDetailIdArray = employeeContractSBVO.getSolutionDetailIdArray();
         final String[] baseCompanyArray = employeeContractSBVO.getBaseCompanyArray();
         final String[] basePersonalArray = employeeContractSBVO.getBasePersonalArray();

         if ( solutionDetailIdArray != null && baseCompanyArray != null && basePersonalArray != null && solutionDetailIdArray.length > 0 && baseCompanyArray.length > 0
               && basePersonalArray.length > 0 )
         {
            // 直接插入社保明细
            for ( int i = 0; i < solutionDetailIdArray.length; i++ )
            {
               if ( baseCompanyArray.length > i && basePersonalArray.length > i )
               {
                  final EmployeeContractSBDetailVO employeeContractSBDetailVO = new EmployeeContractSBDetailVO();
                  employeeContractSBDetailVO.setEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );
                  employeeContractSBDetailVO.setSolutionDetailId( solutionDetailIdArray[ i ].trim() );
                  employeeContractSBDetailVO.setBaseCompany( baseCompanyArray[ i ].trim() );
                  employeeContractSBDetailVO.setBasePersonal( basePersonalArray[ i ].trim() );
                  employeeContractSBDetailVO.setCreateBy( employeeContractSBVO.getCreateBy() );
                  employeeContractSBDetailVO.setCreateDate( new Date() );
                  employeeContractSBDetailVO.setModifyBy( employeeContractSBVO.getModifyBy() );
                  employeeContractSBDetailVO.setModifyDate( new Date() );
                  employeeContractSBDetailVO.setStatus( EmployeeContractSBDetailVO.TRUE );
                  employeeContractSBDetailDao.insertEmployeeContractSBDetail( employeeContractSBDetailVO );
               }
            }
         }

      }
      // 如果存在则修改
      else
      {
         // 更改操作为添加
         actFlag = "updateObject";

         final List< Object > employeeContractSBVOs = ( ( EmployeeContractSBTempDao ) getDao() ).getFullEmployeeContractSBTempVOsByCondition( employeeContractSBVOTemp );

         // 遍历
         for ( Object object : employeeContractSBVOs )
         {
            final EmployeeContractSBTempVO employeeContractSBVOObject = ( EmployeeContractSBTempVO ) object;
            employeeContractSBVOObject.setModifyBy( employeeContractSBVO.getModifyBy() );

            // 获得EmployeeContractSBVO 未修改前的状态
            final String status = employeeContractSBVOObject.getStatus();

            // 根据用户选择判断是否需要更新方案数据
            if ( employeeContractSBVO.getApplyToAllHeader() != null && employeeContractSBVO.getApplyToAllHeader().equals( "1" ) )
            {
               employeeContractSBVOObject.update( employeeContractSBVO );
               // 更新日期
               updateDateByCondition( employeeContractSBVOObject );
               // 状态还原
               employeeContractSBVOObject.setStatus( status );
            }

            // 获取SubAction判断是否需要提交
            final String subAction = employeeContractSBVO.getSubAction();

            if ( subAction != null && subAction.equals( BaseAction.SUBMIT_OBJECT ) )
            {

               // 如果是无社保则“待申报加保”
               if ( status.equals( "0" ) )
               {
                  actFlag = "submitObject";
                  employeeContractSBVOObject.setStatus( "2" );
               }
               // 如果是“待退购”或“正常购买”则“待申报退保”
               else if ( status.equals( "2" ) || status.equals( "3" ) )
               {
                  actFlag = "submitObject";
                  employeeContractSBVOObject.setStatus( "5" );
               }

            }

            // 根据用户选择判断是否需要更新方案明细数据
            if ( employeeContractSBVO.getApplyToAllDetail() != null && employeeContractSBVO.getApplyToAllDetail().equals( "1" ) )
            {
               updateEmployeeContractSBTemp( employeeContractSBVOObject );
               // 更新
            }
            else
            {
               // 只更新Header信息
               updateEmployeeContractSBPopup( employeeContractSBVOObject );
            }

         }
      }

      return actFlag;
   }

   /**  
    * ModifyEmployeeContractSBVOs
    *  模态框 修改 合同社保方案(多条记录)
    * @param employeeContractSBVO
    * @return
    * @throws KANException
    * @throws ParseException
    */
   @Override
   public String modifyEmployeeContractSBTempVOs( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException, ParseException
   {
      final String selectIds = employeeContractSBVO.getSelectedIds();
      // 遍历
      for ( String selectedId : selectIds.split( "," ) )
      {

         if ( selectedId != null && !selectedId.equals( "null" ) && !selectedId.isEmpty() )
         {
            employeeContractSBVO.setContractId( selectedId );

            modifyEmployeeContractSBTempVO( employeeContractSBVO );
         }

      }
      return null;
   }

   /**  
    * UpdateDateByCondition
    * 根据派送协议开始日期和结束日期设置社保日期
    * @param employeeContractSBVO
    * @throws KANException
    * @throws ParseException 
    */
   private void updateDateByCondition( final EmployeeContractSBVO employeeContractSBVO ) throws KANException, ParseException
   {
      final EmployeeContractVO employeeContractVO = this.employeeContractTempDao.getEmployeeContractTempVOByContractId( employeeContractSBVO.getContractId() );

      if ( employeeContractVO.getStartDate() != null )
      {
         // 日期格式
         final DateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );

         // 判断社保方案开始时间是否在派送信息之前
         if ( employeeContractSBVO.getStartDate() != null && !employeeContractSBVO.getStartDate().isEmpty() )
         {

            final Date sbStartDate = df.parse( employeeContractSBVO.getStartDate() );
            final Date contractStartDate = df.parse( employeeContractVO.getStartDate() );

            if ( sbStartDate.getTime() < contractStartDate.getTime() )
            {
               employeeContractSBVO.setStartDate( employeeContractVO.getStartDate() );
            }

         }

         // 判断社保方案结束时间是否在派送信息之后
         if ( employeeContractSBVO.getEndDate() != null && !employeeContractSBVO.getEndDate().isEmpty() )
         {
            final Date sbEndDate = df.parse( employeeContractSBVO.getEndDate() );
            final Date contractEndDate = df.parse( employeeContractVO.getEndDate() );

            if ( sbEndDate.getTime() > contractEndDate.getTime() )
            {
               employeeContractSBVO.setEndDate( employeeContractVO.getEndDate() );
            }

         }

      }

   }

   /**  
    * updateEmployeeContractSBPopup
    *	模态框更新商保Header信息
    *	@param employeeContractSBVO
    *	@return
    *	@throws KANException
    */
   private int updateEmployeeContractSBPopup( final EmployeeContractSBTempVO employeeContractSBVO ) throws KANException
   {
      try
      {
         int rows = 0;

         // 开启事务
         startTransaction();

         rows = ( ( EmployeeContractSBTempDao ) getDao() ).updateEmployeeContractSBTemp( employeeContractSBVO );
         // 提交事务
         commitTransaction();

         return rows;
      }
      catch ( Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }
   }

   @Override
   public PagedListHolder getVendorEmployeeContractSBTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final EmployeeContractSBTempDao employeeContractSBTempDao = ( EmployeeContractSBTempDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractSBTempDao.countVendorEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractSBTempDao.getVendorEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractSBTempDao.getVendorEmployeeContractSBTempVOsByCondition( ( EmployeeContractSBTempVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }
}
