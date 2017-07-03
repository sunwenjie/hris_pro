/*
 * Created on 2013-05-13 TODO To change the template for this generated file go
 * to Window - Preferences - Java - Code Style - Code Templates
 */
package com.kan.base.web.actions.workflow;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.system.AccountModuleDTO;
import com.kan.base.domain.workflow.WorkflowDefineRequirementsVO;
import com.kan.base.domain.workflow.WorkflowDefineStepsVO;
import com.kan.base.domain.workflow.WorkflowDefineVO;
import com.kan.base.domain.workflow.WorkflowModuleDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.workflow.WorkflowDefineService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class WorkflowDefineAction extends BaseAction
{

   public static String accessAction = "HRO_WORKFLOW_CONFIGURATION";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );
         // 获得Action Form
         final WorkflowDefineVO workflowDefineVO = ( WorkflowDefineVO ) form;

         boolean isDelete = workflowDefineVO != null && workflowDefineVO.getSubAction() != null && workflowDefineVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS );

         // 如果子Action是删除信息模板
         if ( isDelete )
         {
            // 调用删除信息模板的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( workflowDefineVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder workflowDefineHolder = new PagedListHolder();

         // 传入当前页
         workflowDefineHolder.setPage( page );
         // 传入当前值对象
         workflowDefineHolder.setObject( workflowDefineVO );
         // 设置页面记录条数
         workflowDefineHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         workflowDefineService.getWorkflowDefineVOsByCondition( workflowDefineHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( workflowDefineHolder, request );

         //Holder需写入Request对象
         request.setAttribute( "workflowDefineHolder", workflowDefineHolder );

         // 如果是ajax调用或者删除操作则返回
         if ( new Boolean( ajax ) || isDelete )
         {
            if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
            {
               return mapping.findForward( "listWorkflowDefineTableInHouse" );
            }
            else
            {
               return mapping.findForward( "listWorkflowDefineTable" );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
      {
         return mapping.findForward( "listWorkflowDefineInHouse" );
      }
      else
      {
         return mapping.findForward( "listWorkflowDefine" );
      }
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );
      WorkflowDefineVO workflowDefineVO = ( ( WorkflowDefineVO ) form );
      // 设置Sub Action
      workflowDefineVO.setStatus( WorkflowDefineVO.TRUE );
      workflowDefineVO.setSubAction( CREATE_OBJECT );

      /* // 账户存在了非停用的工作流就不加载
       KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
       for ( WorkflowModuleDTO workflowModuleDTO : accountConstants.WORKFLOW_MODULE_DTO )
       {
          for ( WorkflowDefineDTO WorkflowDefineDTO : workflowModuleDTO.getWorkflowDefineDTO() )
          {
             // 启用状态下的工作流要从下拉框里面过滤掉
             if ( "1".equals( WorkflowDefineDTO.getWorkflowDefineVO().getStatus() ) )
             {
                for ( int i = 0; i < workflowDefineVO.getWorkflowModules().size(); )
                {
                   MappingVO workflowModul = workflowDefineVO.getWorkflowModules().get( i );
                   if ( workflowModul.getMappingId().equals( WorkflowDefineDTO.getWorkflowDefineVO().getWorkflowModuleId() ) )
                   {
                      workflowDefineVO.getWorkflowModules().remove( i );
                   }
                   else
                   {
                      i++;
                   }
                }
             }
          }
       }*/

      // 跳转到新建界面
      if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
      {
         return mapping.findForward( "manageWorkflowDefineInHouse" );
      }
      else
      {
         return mapping.findForward( "manageWorkflowDefine" );
      }
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );
            // 获得ActionForm
            final WorkflowDefineVO workflowDefineVO = ( WorkflowDefineVO ) form;
            // 设置systeId
            workflowDefineVO.setSystemId( KANConstants.SYSTEM_ID );
            // 获取登录用户  - 设置创建用户id
            workflowDefineVO.setCreateBy( getUserId( request, response ) );
            // 获取登录用户账户  设置账户id
            workflowDefineVO.setAccountId( getAccountId( request, response ) );
            workflowDefineVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            workflowDefineService.insertWorkflowDefine( workflowDefineVO );

            //重新初始化AccounConstance的WorkflowModuleDTO
            constantsInit( "initWorkflow", getAccountId( request, response ) );

            insertlog( request, workflowDefineVO, Operate.ADD, workflowDefineVO.getDefineId(), null );
         }
         else
         {
            // 重复提交警告
            ( ( WorkflowDefineVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到修改界面
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );

         // 获得主表主键
         String workflowDefineId = request.getParameter( "defineId" );
         if ( workflowDefineId == null || "".equals( workflowDefineId ) )
         {
            workflowDefineId = ( ( WorkflowDefineVO ) form ).getDefineId();
         }
         else
         {
            workflowDefineId = KANUtil.decodeString( workflowDefineId );
         }

         // 获得主键对应对象
         final WorkflowDefineVO workflowDefineVO = workflowDefineService.getWorkflowDefineVOByDefineId( workflowDefineId );
         // 刷新对象，初始化对象列表及国际化
         workflowDefineVO.reset( null, request );
         workflowDefineVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "workflowDefineForm", workflowDefineVO );

         loadColumnVOs_toRequest( request, workflowDefineVO.getWorkflowModuleId() );

         // loadWorkflowDefineStepsForm 
         loadWorkflowDefineStepsForm( request, workflowDefineVO, "workflowDefineStepsForm", "" );

         // loadWorkflowDefineRequirementsForm
         loadWorkflowDefineRequirementsForm( request, workflowDefineVO, "workflowDefineRequirementsForm", "" );

         // 初始化WorkflowDefineStepsVO
         final WorkflowDefineStepsVO workflowDefineStepsVO = new WorkflowDefineStepsVO();
         workflowDefineStepsVO.setDefineId( workflowDefineId );

         // 加载WorkflowDefineStepsVO列表
         new WorkflowDefineStepsAction().list_object( mapping, workflowDefineStepsVO, request, response );

         // 初始化WorkflowDefineRequirementsVO
         final WorkflowDefineRequirementsVO workflowDefineRequirementsVO = new WorkflowDefineRequirementsVO();
         workflowDefineRequirementsVO.setDefineId( workflowDefineId );

         // 加载WorkflowDefineRequirementsVO列表
         new WorkflowDefineRequirementsAction().list_object( mapping, workflowDefineRequirementsVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到新建界面
      if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
      {
         return mapping.findForward( "manageWorkflowDefineInHouse" );
      }
      else
      {
         return mapping.findForward( "manageWorkflowDefine" );
      }
   }

   // 加载WorkflowDefineStepsForm到request
   private void loadWorkflowDefineStepsForm( final HttpServletRequest request, final WorkflowDefineVO workflowDefineVO, final String attributeName, final String subAction )
   {
      final WorkflowDefineStepsVO workflowDefineStepsVO = new WorkflowDefineStepsVO();
      workflowDefineStepsVO.setDefineId( workflowDefineVO.getDefineId() );
      workflowDefineStepsVO.setStatus( WorkflowDefineStepsVO.TRUE );
      workflowDefineStepsVO.setSubAction( subAction );
      // 默认为步骤为1
      workflowDefineStepsVO.setStepIndex( 1 );
      workflowDefineStepsVO.reset( null, request );
      request.setAttribute( attributeName, workflowDefineStepsVO );
   }

   private void loadWorkflowDefineRequirementsForm( final HttpServletRequest request, final WorkflowDefineVO workflowDefineVO, final String attributeName, final String subAction )
         throws KANException
   {
      final WorkflowDefineRequirementsVO workflowDefineRequirementsVO = new WorkflowDefineRequirementsVO();
      workflowDefineRequirementsVO.setDefineId( workflowDefineVO.getDefineId() );
      // 默认状态1，启用
      workflowDefineRequirementsVO.setStatus( WorkflowDefineStepsVO.TRUE );
      workflowDefineRequirementsVO.setSubAction( subAction );
      // 默认组合条件1 并且
      workflowDefineRequirementsVO.setCombineType( "1" );
      workflowDefineRequirementsVO.setCompareType( "1" );
      workflowDefineRequirementsVO.reset( null, request );
      request.setAttribute( attributeName, workflowDefineRequirementsVO );
   }

   public static void loadColumnVOs_toRequest( final HttpServletRequest request, final String workflowModuleId ) throws KANException
   {
      // workflowDefineRequirementsVO 装载columns
      KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, null ) );
      WorkflowModuleDTO workflowModuleDTO = accountConstants.getWorkflowModuleDTOByWorkflowModuleId( workflowModuleId );
      if ( workflowModuleDTO != null )
      {
         AccountModuleDTO accountModuleDTO = accountConstants.getAccountModuleDTOByModuleId( workflowModuleDTO.getWorkflowModuleVO().getModuleId() );
         if ( accountModuleDTO != null )
         {
            // accessAction 可能是{HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT,HRO_BIZ_EMPLOYEE_LABOR_CONTRACT_IN_HOUSE,HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_IN_HOUSE} 格式
            String accessAction = accountModuleDTO.getModuleVO().getAccessAction();
            List< String > accessActions = KANUtil.jasonArrayToStringList( accessAction );
            TableDTO tableDTO = accountConstants.getTableDTOByAccessAction( accessActions, getRole( request, null ) );
            if ( tableDTO != null )
            {
               List< MappingVO > columns = tableDTO.getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );
               request.setAttribute( "REQUEST_CACHE_COLUMNVOS", columns );
            }
         }
      }
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );
            // 获得当前主键
            final String workflowDefineId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "defineId" ), "GBK" ) );
            // 获得主键对应对象
            final WorkflowDefineVO workflowDefineVO = workflowDefineService.getWorkflowDefineVOByDefineId( workflowDefineId );
            // 装载界面传值
            workflowDefineVO.update( ( WorkflowDefineVO ) form );
            // 获取登录用户 设置修改账户id
            workflowDefineVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            workflowDefineService.updateWorkflowDefine( workflowDefineVO );

            // 返回编辑成功的标记 
            success( request, MESSAGE_TYPE_UPDATE, null, "MESSAGE_DEFINE" );

            //重新初始化AccounConstance的WorkflowModuleDTO
            constantsInit( "initWorkflow", getAccountId( request, response ) );

            insertlog( request, workflowDefineVO, Operate.MODIFY, workflowDefineVO.getDefineId(), null );
         }

         // 清空Form条件
         ( ( WorkflowDefineVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );
         // 获得当前主键
         final String defineId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "defineId" ), "GBK" ) );
         // 删除主键对应对象
         workflowDefineService.deleteWorkflowDefineByDefineId( getUserId( request, response ), defineId );

         //重新初始化AccounConstance的WorkflowModuleDTO
         constantsInit( "initWorkflow", getAccountId( request, response ) );

         insertlog( request, form, Operate.DELETE, defineId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final WorkflowDefineService workflowDefineService = ( WorkflowDefineService ) getService( "workflowDefineService" );
         // 获得Action Form
         WorkflowDefineVO workflowDefineVO = ( WorkflowDefineVO ) form;
         // 存在选中的ID
         if ( workflowDefineVO.getSelectedIds() != null && !workflowDefineVO.getSelectedIds().equals( "" ) )
         {
            workflowDefineService.deleteWorkflowDefineByDefineId( getUserId( request, response ), workflowDefineVO.getSelectedIds().split( "," ) );
            success( request, MESSAGE_TYPE_DELETE );

            insertlog( request, workflowDefineVO, Operate.DELETE, null, workflowDefineVO.getSelectedIds() );
         }
         // 清除Selected IDs和子Action
         workflowDefineVO.setSelectedIds( "" );
         workflowDefineVO.setSubAction( "" );

         //重新初始化AccounConstance的WorkflowModuleDTO
         constantsInit( "initWorkflow", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}