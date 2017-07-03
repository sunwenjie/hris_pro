package com.kan.hro.web.actions.biz.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.common.CommonBatchService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractResignDTO;
import com.kan.hro.domain.biz.employee.EmployeeContractResignVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractCBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractResignService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.web.actions.biz.importExcel.EmployeeContractResignImportAction;
import com.kan.hro.web.actions.biz.importExcel.EmployeeInsuranceNoImportBatchAction;

public class EmployeeContractResignAction extends BaseAction
{

   // 服务协议
   public static String EMPLOYEE_CONTRACT_ACCESS_ACTION_SERVICE = "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT";
   // 劳动合同（In House）
   public static String EMPLOYEE_CONTRACT_ACCESS_ACTION_SERVICE_IN_HOUSE = "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_IN_HOUSE";

   // 当前Action对应的Access Action
   public static String getEmployeeContractAccessAction( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      if ( KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
      {
         return EMPLOYEE_CONTRACT_ACCESS_ACTION_SERVICE_IN_HOUSE;
      }
      else
      {
         return EMPLOYEE_CONTRACT_ACCESS_ACTION_SERVICE;
      }
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = getPage( request );
         // 获得是否Ajax调用
         final String ajax = getAjax( request );
         // 初始化Service接口
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );
         String batchId = new String();

         // 获得批次主键ID
         if ( new Boolean( ajax ) )
         {
            batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         }
         else
         {
            batchId = KANUtil.decodeString( request.getParameter( "batchId" ) );
         }

         final CommonBatchVO commonBatchVO = commonBatchService.getCommonBatchVOByBatchId( batchId );
         commonBatchVO.reset( null, request );
         request.setAttribute( "commonBatchVO", commonBatchVO );

         // 初始化Service接口
         final EmployeeContractResignService employeeContractResignService = ( EmployeeContractResignService ) getService( "employeeContractResignService" );
         final EmployeeContractResignVO employeeContractResignVO = ( EmployeeContractResignVO ) form;

         if ( new Boolean( ajax ) )
         {
            decodedObject( employeeContractResignVO );
            // 如果子SubAction是删除列表操作deleteObjects
            if ( getSubAction( form ).equalsIgnoreCase( ROLLBACK_OBJECTS ) )
            {
               // 调用删除列表的SubAction
               rollback_objectList( mapping, form, request, response );
            }
            // 如果子SubAction是更新列表操作submitObjects
            else if ( getSubAction( form ).equalsIgnoreCase( SUBMIT_OBJECTS ) )
            {
               // 调用修改列表的SubAction
               submit_objectList( mapping, form, request, response );
            }
            employeeContractResignVO.setBatchId( KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) ) );
         }
         else
         {
            employeeContractResignVO.setBatchId( batchId );
         }

         // 如果没有指定排序则默认按  EmployeeContractResignId 排序
         if ( employeeContractResignVO.getSortColumn() == null || employeeContractResignVO.getSortColumn().isEmpty() )
         {
            employeeContractResignVO.setSortColumn( "employeeContractResignId" );
            employeeContractResignVO.setSortOrder( "desc" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( employeeContractResignVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeContractResignService.getEmployeeContractResignVOsByCondition( pagedListHolder, true );
         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "employeeContractResignHolder", pagedListHolder );

         // 如果批次下为空则跳转到批次页面
         if ( pagedListHolder == null || pagedListHolder.getHolderSize() == 0 )
         {
            request.setAttribute( "messageInfo", true );
            return new EmployeeContractResignImportAction().list_object( mapping, new CommonBatchVO(), request, response );
         }

         // 如果是Ajax请求
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listHeaderTempTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listHeaderTemp" );
   }

   /**  
    * To EmployeeContractResign
    *	供应商社保临时表Header页面
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward to_employeeContractResign( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 获得批次主键ID
      final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );

      // 初始化Service接口
      final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "employeeContractResignService" );
      final EmployeeContractResignService employeeContractResignService = ( EmployeeContractResignService ) getService( "employeeContractResignService" );

      final CommonBatchVO commonBatchVO = commonBatchService.getCommonBatchVOByBatchId( batchId );

      commonBatchVO.reset( null, request );
      request.setAttribute( "commonBatchVO", commonBatchVO );

      // 获得当前页
      final String page = getPage( request );
      // 获得是否Ajax调用
      final String ajax = getAjax( request );
      final EmployeeContractResignVO employeeContractResignVO = ( EmployeeContractResignVO ) form;
      employeeContractResignVO.setBatchId( batchId );

      // 设置当前用户AccountId
      employeeContractResignVO.setAccountId( getAccountId( request, response ) );
      // 设置当前的clientId
      employeeContractResignVO.setClientId( getCorpId( request, response ) );
      // 初始化PagedListHolder，用于引用方式调用Service
      final PagedListHolder employeeContractResignHolder = new PagedListHolder();
      // 传入当前页
      employeeContractResignHolder.setPage( page );

      // 如果没有指定排序则默认按 BatchId排序
      if ( employeeContractResignVO.getSortColumn() == null || employeeContractResignVO.getSortColumn().isEmpty() )
      {
         employeeContractResignVO.setSortColumn( "headerId" );
         employeeContractResignVO.setSortOrder( "desc" );
      }

      // 传入当前值对象
      employeeContractResignHolder.setObject( employeeContractResignVO );
      // 设置页面记录条数
      employeeContractResignHolder.setPageSize( listPageSize_medium );
      // 调用Service方法，引用对象返回，第二个参数说明是否分页
      employeeContractResignService.getEmployeeContractResignVOsByCondition( employeeContractResignHolder, true );
      // 刷新Holder，国际化传值
      refreshHolder( employeeContractResignHolder, request );
      // Holder需写入Request对象
      request.setAttribute( "employeeContractResignHolder", employeeContractResignHolder );

      if ( new Boolean( ajax ) )
      {
         // 写入Role
         return mapping.findForward( "listEmployeeContractResignTable" );
      }

      return mapping.findForward( "listEmployeeContractResignBody" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   public ActionForward modify_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

   protected void submit_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractResignService employeeContractResignService = ( EmployeeContractResignService ) getService( "employeeContractResignService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
         final CommonBatchService commonBatchService = ( CommonBatchService ) getService( "commonBatchService" );

         // 获得Action Form
         final EmployeeContractResignVO employeeContractResignVO = ( EmployeeContractResignVO ) form;

         // 存在选中的ID
         if ( employeeContractResignVO.getSelectedIds() != null && !employeeContractResignVO.getSelectedIds().trim().isEmpty() )
         {
            // 初始化要修改的 EmployeeContractResignDTO 集合
            List< EmployeeContractResignDTO > employeeContractResignDTOs = new ArrayList< EmployeeContractResignDTO >();

            // 分割
            for ( String selectedId : employeeContractResignVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // 初始化EmployeeContractResignDTO
                  EmployeeContractResignDTO employeeContractResignDTO = new EmployeeContractResignDTO();
                  // 初始化是否需要添加
                  boolean needAdd = false;

                  // 初始化EmployeeContractResignVO
                  final EmployeeContractResignVO tempEmployeeContractResignVO = employeeContractResignService.getEmployeeContractResignVOByEmployeeContractResignId( KANUtil.decodeStringFromAjax( selectedId ) );

                  if ( tempEmployeeContractResignVO != null && tempEmployeeContractResignVO.getContractId() != null )
                  {
                     employeeContractResignDTO.setEmployeeContractResignId( employeeContractResignVO.getEmployeeContractResignId() );

                     // 获得 EmployeeContractVO
                     EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( tempEmployeeContractResignVO.getContractId() );

                     // 员工离职
                     if ( KANUtil.filterEmpty( tempEmployeeContractResignVO.getResignDate() ) != null )
                     {
                        employeeContractVO.reset( null, request );
                        employeeContractVO.setResignDate( tempEmployeeContractResignVO.getResignDate() );
                        employeeContractVO.setLeaveReasons( tempEmployeeContractResignVO.getLeaveReasons() );
                        employeeContractVO.setLastWorkDate( tempEmployeeContractResignVO.getLastWorkDate() );
                        employeeContractVO.setEmployStatus( tempEmployeeContractResignVO.getEmployStatus() );
                        employeeContractVO.setModifyBy( getUserId( request, response ) );
                        employeeContractVO.setModifyDate( new Date() );
                        employeeContractVO.getHistoryVO().setAccessAction( getEmployeeContractAccessAction( request, response ) );

                        // 离职审核
                        needAdd = true;
                        employeeContractResignDTO.setEmployeeContractVO( employeeContractVO );
                     }

                     // 社保1退保
                     if ( tempEmployeeContractResignVO.getSb1Id() != null && tempEmployeeContractResignVO.getSb1EndDate() != null )
                     {
                        // 初始化查询
                        final EmployeeContractSBVO employeeContractSBVO = new EmployeeContractSBVO();
                        employeeContractSBVO.setContractId( tempEmployeeContractResignVO.getContractId() );
                        employeeContractSBVO.setSbSolutionId( tempEmployeeContractResignVO.getSb1Id() );
                        employeeContractSBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                        employeeContractSBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );

                        // 查询派送协议对应的社保方案
                        final List< Object > employeeContractSBVOObjects = employeeContractSBService.getEmployeeContractSBVOsByCondition( employeeContractSBVO );

                        // 派送协议一个社保方案理应只能对应出现一次
                        if ( employeeContractSBVOObjects != null && employeeContractSBVOObjects.size() > 0 )
                        {
                           final EmployeeContractSBVO tempEmployeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOObjects.get( 0 );
                           tempEmployeeContractSBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                           tempEmployeeContractSBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );
                           tempEmployeeContractSBVO.setEndDate( tempEmployeeContractResignVO.getSb1EndDate() );
                           tempEmployeeContractSBVO.setModifyBy( getUserId( request, response ) );
                           tempEmployeeContractSBVO.setModifyDate( new Date() );

                           // 社保退保
                           needAdd = true;
                           employeeContractResignDTO.getEmployeeContractSBVOs().add( tempEmployeeContractSBVO );
                        }
                     }

                     // 社保2退保
                     if ( tempEmployeeContractResignVO.getSb2Id() != null && tempEmployeeContractResignVO.getSb2EndDate() != null )
                     {
                        // 初始化查询
                        final EmployeeContractSBVO employeeContractSBVO = new EmployeeContractSBVO();
                        employeeContractSBVO.setContractId( tempEmployeeContractResignVO.getContractId() );
                        employeeContractSBVO.setSbSolutionId( tempEmployeeContractResignVO.getSb2Id() );
                        employeeContractSBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                        employeeContractSBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );

                        // 查询派送协议对应的社保方案
                        final List< Object > employeeContractSBVOObjects = employeeContractSBService.getEmployeeContractSBVOsByCondition( employeeContractSBVO );

                        // 派送协议一个社保方案理应只能对应出现一次
                        if ( employeeContractSBVOObjects != null && employeeContractSBVOObjects.size() > 0 )
                        {
                           final EmployeeContractSBVO tempEmployeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOObjects.get( 0 );
                           tempEmployeeContractSBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                           tempEmployeeContractSBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );
                           tempEmployeeContractSBVO.setEndDate( tempEmployeeContractResignVO.getSb2EndDate() );
                           tempEmployeeContractSBVO.setModifyBy( getUserId( request, response ) );
                           tempEmployeeContractSBVO.setModifyDate( new Date() );

                           // 社保退保
                           needAdd = true;
                           employeeContractResignDTO.getEmployeeContractSBVOs().add( tempEmployeeContractSBVO );
                        }
                     }

                     // 社保3退保
                     if ( tempEmployeeContractResignVO.getSb3Id() != null && tempEmployeeContractResignVO.getSb3EndDate() != null )
                     {
                        // 初始化查询
                        final EmployeeContractSBVO employeeContractSBVO = new EmployeeContractSBVO();
                        employeeContractSBVO.setContractId( tempEmployeeContractResignVO.getContractId() );
                        employeeContractSBVO.setSbSolutionId( tempEmployeeContractResignVO.getSb3Id() );
                        employeeContractSBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                        employeeContractSBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );

                        // 查询派送协议对应的社保方案
                        final List< Object > employeeContractSBVOObjects = employeeContractSBService.getEmployeeContractSBVOsByCondition( employeeContractSBVO );

                        // 派送协议一个社保方案理应只能对应出现一次
                        if ( employeeContractSBVOObjects != null && employeeContractSBVOObjects.size() > 0 )
                        {
                           final EmployeeContractSBVO tempEmployeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOObjects.get( 0 );
                           tempEmployeeContractSBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                           tempEmployeeContractSBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );
                           tempEmployeeContractSBVO.setEndDate( tempEmployeeContractResignVO.getSb3EndDate() );
                           tempEmployeeContractSBVO.setModifyBy( getUserId( request, response ) );
                           tempEmployeeContractSBVO.setModifyDate( new Date() );

                           // 社保退保
                           needAdd = true;
                           employeeContractResignDTO.getEmployeeContractSBVOs().add( tempEmployeeContractSBVO );
                        }
                     }

                     // 商保1退保
                     if ( tempEmployeeContractResignVO.getCb1Id() != null && tempEmployeeContractResignVO.getCb1EndDate() != null )
                     {
                        // 初始化查询
                        final EmployeeContractCBVO employeeContractCBVO = new EmployeeContractCBVO();
                        employeeContractCBVO.setContractId( tempEmployeeContractResignVO.getContractId() );
                        employeeContractCBVO.setSolutionId( tempEmployeeContractResignVO.getCb1Id() );
                        employeeContractCBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                        employeeContractCBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );

                        // 查询派送协议对应的商保方案
                        final List< Object > employeeContractCBVOObjects = employeeContractCBService.getEmployeeContractCBVOsByCondition( employeeContractCBVO );

                        // 派送协议一个商保方案理应只能对应出现一次
                        if ( employeeContractCBVOObjects != null && employeeContractCBVOObjects.size() > 0 )
                        {
                           final EmployeeContractCBVO tempEmployeeContractCBVO = ( EmployeeContractCBVO ) employeeContractCBVOObjects.get( 0 );
                           tempEmployeeContractCBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                           tempEmployeeContractCBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );
                           tempEmployeeContractCBVO.setEndDate( tempEmployeeContractResignVO.getCb1EndDate() );
                           tempEmployeeContractCBVO.setModifyBy( getUserId( request, response ) );
                           tempEmployeeContractCBVO.setModifyDate( new Date() );

                           // 商保退保
                           needAdd = true;
                           employeeContractResignDTO.getEmployeeContractCBVOs().add( tempEmployeeContractCBVO );
                        }
                     }

                     // 商保2退保
                     if ( tempEmployeeContractResignVO.getCb2Id() != null && tempEmployeeContractResignVO.getCb2EndDate() != null )
                     {
                        // 初始化查询
                        final EmployeeContractCBVO employeeContractCBVO = new EmployeeContractCBVO();
                        employeeContractCBVO.setContractId( tempEmployeeContractResignVO.getContractId() );
                        employeeContractCBVO.setSolutionId( tempEmployeeContractResignVO.getCb2Id() );
                        employeeContractCBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                        employeeContractCBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );

                        // 查询派送协议对应的商保方案
                        final List< Object > employeeContractCBVOObjects = employeeContractCBService.getEmployeeContractCBVOsByCondition( employeeContractCBVO );

                        // 派送协议一个商保方案理应只能对应出现一次
                        if ( employeeContractCBVOObjects != null && employeeContractCBVOObjects.size() > 0 )
                        {
                           final EmployeeContractCBVO tempEmployeeContractCBVO = ( EmployeeContractCBVO ) employeeContractCBVOObjects.get( 0 );
                           tempEmployeeContractCBVO.setAccountId( tempEmployeeContractResignVO.getAccountId() );
                           tempEmployeeContractCBVO.setCorpId( tempEmployeeContractResignVO.getCorpId() );
                           tempEmployeeContractCBVO.setEndDate( tempEmployeeContractResignVO.getCb2EndDate() );
                           tempEmployeeContractCBVO.setModifyBy( getUserId( request, response ) );
                           tempEmployeeContractCBVO.setModifyDate( new Date() );

                           // 商保退保
                           needAdd = true;
                           employeeContractResignDTO.getEmployeeContractCBVOs().add( tempEmployeeContractCBVO );
                        }
                     }
                  }

                  // 如果有数据更新
                  if ( needAdd )
                  {
                     employeeContractResignDTOs.add( employeeContractResignDTO );
                     // 更新 EmployeeContractResignVO 、CommonBatchVO 为“已更新”
                     tempEmployeeContractResignVO.setStatus( "2" );
                     employeeContractResignService.updateEmployeeContractResign( tempEmployeeContractResignVO );
                  }
               }
            }

            if ( employeeContractResignDTOs.size() > 0 )
            {
               // 初始化有问题的  EmployeeContractResignVO的ID集合
               List< String > employeeContractResignIds = new ArrayList< String >();

               for ( EmployeeContractResignDTO employeeContractResignDTO2 : employeeContractResignDTOs )
               {
                  try
                  {
                     employeeContractResignService.submitEmployeeContractResignDTO( employeeContractResignDTO2 );
                  }
                  catch ( Exception e )
                  {
                     employeeContractResignIds.add( employeeContractResignDTO2.getEmployeeContractResignId() );
                  }
               }

               if ( employeeContractResignIds.size() > 0 )
               {
                  warning( request, null, "离职或退保未成功。", MESSAGE_HEADER );
                  //                  warning( request, null, "序号ID为：" + employeeContractResignIds.toString() + "的项目离职或退保未成功。", MESSAGE_HEADER );
               }
               else
               {

                  // 尝试更新对应的 CommonBatch
                  String batchId = employeeContractResignVO.getBatchId();

                  if ( new Boolean( getAjax( request ) ) && KANUtil.filterEmpty( batchId ) != null )
                  {
                     batchId = KANUtil.decodeStringFromAjax( batchId );
                  }
                  final CommonBatchVO commonBatchVO = commonBatchService.getCommonBatchVOByBatchId( batchId );

                  // 初始化查询对象
                  final EmployeeContractResignVO tempEmployeeContractResignVO = new EmployeeContractResignVO();
                  tempEmployeeContractResignVO.setBatchId( batchId );
                  tempEmployeeContractResignVO.setAccountId( employeeContractResignVO.getAccountId() );
                  tempEmployeeContractResignVO.setCorpId( employeeContractResignVO.getCorpId() );

                  final List< Object > employeeContractResignVOObjects = employeeContractResignService.getEmployeeContractResignVOsByCondition( tempEmployeeContractResignVO );

                  my: if ( employeeContractResignVOObjects != null && employeeContractResignVOObjects.size() > 0 )
                  {
                     for ( Object object : employeeContractResignVOObjects )
                     {
                        EmployeeContractResignVO tempEmployeeContractResignVO2 = ( EmployeeContractResignVO ) object;

                        if ( !"2".equals( tempEmployeeContractResignVO2.getStatus() ) )
                        {
                           break my;
                        }
                     }
                     // 如果全部更新过了更新 CommonBatchVO
                     commonBatchVO.setStatus( "2" );
                     commonBatchService.updateCommonBatch( commonBatchVO );
                  }

                  success( request, null, "离职或退保操作成功，如果有数据未修改成功请检查工作流。", MESSAGE_HEADER );
               }
            }

            // 清除Selected IDs和子Action
            employeeContractResignVO.setSelectedIds( "" );
            employeeContractResignVO.setSubAction( "" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   protected void rollback_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeContractResignService employeeContractResignService = ( EmployeeContractResignService ) getService( "employeeContractResignService" );
         // 获得Action Form
         final EmployeeContractResignVO employeeContractResignVO = ( EmployeeContractResignVO ) form;

         // 存在选中的ID
         if ( employeeContractResignVO.getSelectedIds() != null && !employeeContractResignVO.getSelectedIds().trim().isEmpty() )
         {
            // 分割
            for ( String selectedId : employeeContractResignVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  final String employeeContractResignId = KANUtil.decodeStringFromAjax( selectedId );

                  final String batchId = KANUtil.decodeStringFromAjax( employeeContractResignVO.getBatchId() );

                  final EmployeeContractResignVO tempEmployeeContractResignVO = employeeContractResignService.getEmployeeContractResignVOByEmployeeContractResignId( employeeContractResignId );

                  tempEmployeeContractResignVO.setBatchId( batchId );
                  // 调用删除接口
                  employeeContractResignService.deleteEmployeeContractResign( tempEmployeeContractResignVO );
               }
            }

            success( request, MESSAGE_TYPE_DELETE );
         }

         // 清除Selected IDs和子Action
         employeeContractResignVO.setSelectedIds( "" );
         employeeContractResignVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward rollback_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      final EmployeeContractResignService employeeContractResignService = ( EmployeeContractResignService ) getService( "employeeContractResignService" );
      String selectedIds = request.getParameter( "selectedIds" );
      if ( StringUtils.isNotEmpty( selectedIds ) )
      {
         String batchId = request.getParameter( "batchId" );
         String[] ids = selectedIds.split( "," );
         for ( int i = 0; i < ids.length; i++ )
         {
            ids[ i ] = KANUtil.decodeStringFromAjax( ids[ i ] );
         }
         int count = employeeContractResignService.backUpRecord( ids, KANUtil.decodeStringFromAjax( batchId ) );
         if ( count == 0 )
         {
            CommonBatchVO commonBatchVO = new CommonBatchVO();
            commonBatchVO.reset( mapping, request );
            return new EmployeeContractResignImportAction().list_object( mapping, commonBatchVO, request, response );
         }
         else
         {
            final EmployeeContractResignVO employeeContractResignVO = ( EmployeeContractResignVO ) form;
            employeeContractResignVO.setBatchId( batchId );
            employeeContractResignVO.setSelectedIds( "" );
            return list_object( mapping, employeeContractResignVO, request, response );
         }
      }
      else
      {
         CommonBatchVO commonBatchVO = new CommonBatchVO();
         commonBatchVO.reset( mapping, request );
         return new EmployeeInsuranceNoImportBatchAction().list_object( mapping, commonBatchVO, request, response );
      }
   }

}
