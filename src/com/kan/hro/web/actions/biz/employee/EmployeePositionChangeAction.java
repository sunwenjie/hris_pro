package com.kan.hro.web.actions.biz.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.BranchDTO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.HistoryService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeePositionChangeService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

public class EmployeePositionChangeAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_POSITION_CHANGES";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );
         // 获得Action Form
         final EmployeePositionChangeVO positionChangeVO = ( EmployeePositionChangeVO ) form;

         // 如果子Action是删除用户列表
         if ( positionChangeVO.getSubAction() != null && positionChangeVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( positionChangeVO );
         }

         // 如果没有指定排序则默认按employeeId排序
         if ( positionChangeVO.getSortColumn() == null || positionChangeVO.getSortColumn().isEmpty() )
         {
            positionChangeVO.setSortColumn( "positionChangeId" );
            positionChangeVO.setSortOrder( "desc" );
         }

         setDataAuth( request, response, positionChangeVO );

         // 获得SubAction
         final String subAction = getSubAction( form );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder positionChangeHolder = new PagedListHolder();

         // 传入当前页
         positionChangeHolder.setPage( page );
         // 传入当前值对象
         positionChangeHolder.setObject( positionChangeVO );
         // 设置页面记录条数
         positionChangeHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         positionChangeService.getPositionChangeVOsByCondition( positionChangeHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // 刷新Holder，国际化传值
         refreshHolder( positionChangeHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "positionChangeHolder", positionChangeHolder );
         request.setAttribute( "isPaged", "1" );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               // 这个导出的excel是手动。
               return new DownloadFileAction().exportExcel4EmployeePositionChange( mapping, form, request, response );
            }
            else
            {
               // Ajax调用无跳转
               return mapping.findForward( "listPositionChangeTable" );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listPositionChange" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加页面Token
      this.saveToken( request );
      request.setAttribute( "employeePositionList", new ArrayList< PositionVO >() );
      // 设置Sub Action
      ( ( EmployeePositionChangeVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "managePositionChange" );
   }

   // 快速异动
   public ActionForward to_objectQuick( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加页面Token
      this.saveToken( request );
      // 设置Sub Action
      ( ( EmployeePositionChangeVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( EmployeePositionChangeVO ) form ).setSubmitFlag( 3 );
      ( ( EmployeePositionChangeVO ) form ).setStatus( "1" );
      // 跳转到新建界面
      return mapping.findForward( "quickPositionChange" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            String accountId = getAccountId( request, response );
            KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
            final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );

            // 获得ActionForm
            final EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) form;
            // 获取登录用户
            employeePositionChangeVO.setAccountId( getAccountId( request, response ) );
            employeePositionChangeVO.setCreateBy( getUserId( request, response ) );
            employeePositionChangeVO.setModifyBy( getUserId( request, response ) );
            employeePositionChangeVO.setLocale( request.getLocale() );
            employeePositionChangeVO.setIsImmediatelyEffective( StringUtils.isNotEmpty( employeePositionChangeVO.getIsImmediatelyEffective() ) ? "1" : "0" );

            List< StaffDTO > staffList = accountConstants.getStaffDTOsByEmployeeId( employeePositionChangeVO.getEmployeeId() );
            if ( staffList != null && staffList.size() > 0 )
            {
               StaffDTO staffDTO = staffList.get( 0 );
               if ( staffDTO != null )
               {
                  StaffVO staffVO = staffDTO.getStaffVO();
                  if ( staffVO != null )
                  {
                     employeePositionChangeVO.setStaffId( staffVO.getStaffId() );
                  }
               }
            }

            // 新建对象
            positionChangeService.insertEmployeePositionChange( employeePositionChangeVO );

            final EmployeePositionChangeVO tempEmployeePositionChangeVO = positionChangeService.getEmployeePositionChangeVOByPositionChangeId( employeePositionChangeVO.getPositionChangeId() );
            tempEmployeePositionChangeVO.reset( null, request );
            if ( employeePositionChangeVO.getSubmitFlag() == 1 )
            {
               tempEmployeePositionChangeVO.setStatus( "3" );
               tempEmployeePositionChangeVO.setRole( getRole( request, response ) );
               positionChangeService.generateHistoryVOForWorkflow( tempEmployeePositionChangeVO );
               positionChangeService.submitEmployeePositionChange( tempEmployeePositionChangeVO );

               // 返回添加成功标记
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, tempEmployeePositionChangeVO, Operate.SUBMIT, employeePositionChangeVO.getPositionChangeId(), null );
            }
            else
            {
               // 返回添加成功标记
               success( request, MESSAGE_TYPE_ADD );
               insertlog( request, tempEmployeePositionChangeVO, Operate.ADD, employeePositionChangeVO.getPositionChangeId(), null );
            }
         }
         else
         {
            // 清空Form
            ( ( EmployeePositionChangeVO ) form ).reset();
            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   public ActionForward add_objectQuick( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );

            // 获得ActionForm
            final EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) form;
            // 获取登录用户
            employeePositionChangeVO.setAccountId( getAccountId( request, response ) );
            employeePositionChangeVO.setCreateBy( getUserId( request, response ) );
            employeePositionChangeVO.setModifyBy( getUserId( request, response ) );
            employeePositionChangeVO.setLocale( request.getLocale() );
            employeePositionChangeVO.setIsImmediatelyEffective( StringUtils.isNotEmpty( employeePositionChangeVO.getIsImmediatelyEffective() ) ? "1" : "0" );

            // 新建对象
            positionChangeService.insertEmployeePositionChange( employeePositionChangeVO );

            final EmployeePositionChangeVO tempEmployeePositionChangeVO = positionChangeService.getEmployeePositionChangeVOByPositionChangeId( employeePositionChangeVO.getPositionChangeId() );
            tempEmployeePositionChangeVO.reset( null, request );
            if ( employeePositionChangeVO.getSubmitFlag() == 4 )
            {
               tempEmployeePositionChangeVO.setStatus( "3" );
               tempEmployeePositionChangeVO.setRole( getRole( request, response ) );
               positionChangeService.generateHistoryVOForWorkflow( tempEmployeePositionChangeVO );
               positionChangeService.submitEmployeePositionChange( tempEmployeePositionChangeVO );

               // 返回添加成功标记
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, tempEmployeePositionChangeVO, Operate.SUBMIT, employeePositionChangeVO.getPositionChangeId(), null );
            }
            else
            {
               // 返回添加成功标记
               success( request, MESSAGE_TYPE_ADD );
               insertlog( request, tempEmployeePositionChangeVO, Operate.ADD, employeePositionChangeVO.getPositionChangeId(), null );
            }
         }
         else
         {
            // 清空Form
            ( ( EmployeePositionChangeVO ) form ).reset();
            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

         // 主键获取需解码
         String positionChangeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( positionChangeId ) == null )
         {
            positionChangeId = ( ( EmployeePositionChangeVO ) form ).getPositionChangeId();
         }

         final EmployeePositionChangeVO employeePositionChangeVO = positionChangeService.getEmployeePositionChangeVOByPositionChangeId( positionChangeId );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeePositionChangeVO.getEmployeeId() );
         final PositionDTO positionDTO = accountConstants.getPositionDTOByPositionId( employeePositionChangeVO.getOldPositionId() );
         final BranchDTO branchDTO = accountConstants.getBranchDTOByBranchId( employeePositionChangeVO.getOldBranchId() );
         BranchVO branchVO = new BranchVO();
         PositionVO positionVO = new PositionVO();
         if ( branchDTO != null )
         {
            branchVO = branchDTO.getBranchVO();
            branchVO.reset( null, request );
         }
         if ( positionDTO != null )
         {
            positionVO = positionDTO.getPositionVO();
            positionVO.reset( null, request );
         }

         // 刷新对象，初始化对象列表及国际化
         employeePositionChangeVO.reset( null, request );

         employeePositionChangeVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "branchVO", branchVO );
         request.setAttribute( "positionVO", positionVO );
         request.setAttribute( "employeeVO", employeeVO );

         if ( employeePositionChangeVO.getSubmitFlag() == 3 || employeePositionChangeVO.getSubmitFlag() == 4 )
         {
            String oldParentPositionOwnersName = employeePositionChangeVO.getOldParentPositionNameZH() + " / " + employeePositionChangeVO.getOldParentPositionNameEN() + " - "
                  + employeePositionChangeVO.getOldParentPositionOwnersName();
            employeePositionChangeVO.setOldParentPositionOwners( oldParentPositionOwnersName );
            request.setAttribute( "employeePositionChangeForm", employeePositionChangeVO );
            return mapping.findForward( "quickPositionChange" );
         }

         request.setAttribute( "employeePositionChangeForm", employeePositionChangeVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "managePositionChange" );
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
            final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );

            // 主键获取需解码
            final String positionChangeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得EmployeePositionChangeVO
            final EmployeePositionChangeVO employeePositionChangeVO = positionChangeService.getEmployeePositionChangeVOByPositionChangeId( positionChangeId );
            // 获取登录用户
            employeePositionChangeVO.update( ( EmployeePositionChangeVO ) form );
            employeePositionChangeVO.setModifyBy( getUserId( request, response ) );
            employeePositionChangeVO.setLocale( request.getLocale() );

            final List< StaffDTO > staffList = accountConstants.getStaffDTOsByEmployeeId( employeePositionChangeVO.getEmployeeId() );
            if ( staffList != null && staffList.size() > 0 )
            {
               if ( staffList != null && staffList.size() > 0 )
               {
                  StaffDTO staffDTO = staffList.get( 0 );
                  if ( staffDTO != null )
                  {
                     StaffVO staffVO = staffDTO.getStaffVO();
                     if ( staffVO != null )
                     {
                        employeePositionChangeVO.setStaffId( staffVO.getStaffId() );
                     }
                  }
               }
            }
            // 修改对象
            positionChangeService.updateEmployeePositionChange( employeePositionChangeVO );

            if ( employeePositionChangeVO.getSubmitFlag() == 1 )
            {
               employeePositionChangeVO.setStatus( "3" );
               employeePositionChangeVO.setRole( getRole( request, response ) );
               positionChangeService.generateHistoryVOForWorkflow( employeePositionChangeVO );
               positionChangeService.submitEmployeePositionChange( employeePositionChangeVO );

               // 返回编辑成功标记
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeePositionChangeVO, Operate.SUBMIT, employeePositionChangeVO.getPositionChangeId(), null );
            }
            else
            {
               // 返回编辑成功标记
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeePositionChangeVO, Operate.MODIFY, employeePositionChangeVO.getPositionChangeId(), null );
            }

         }

         // 清空Form条件
         ( ( EmployeePositionChangeVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   public ActionForward modify_objectQuick( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );

            // 主键获取需解码
            final String positionChangeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得EmployeePositionChangeVO
            final EmployeePositionChangeVO employeePositionChangeVO = positionChangeService.getEmployeePositionChangeVOByPositionChangeId( positionChangeId );
            // 获取登录用户
            employeePositionChangeVO.update( ( EmployeePositionChangeVO ) form );
            employeePositionChangeVO.setModifyBy( getUserId( request, response ) );
            employeePositionChangeVO.setLocale( request.getLocale() );

            // 修改对象
            positionChangeService.updateEmployeePositionChange( employeePositionChangeVO );

            if ( employeePositionChangeVO.getSubmitFlag() == 4 )
            {
               employeePositionChangeVO.setStatus( "3" );
               employeePositionChangeVO.setRole( getRole( request, response ) );
               positionChangeService.generateHistoryVOForWorkflow( employeePositionChangeVO );
               positionChangeService.submitEmployeePositionChange( employeePositionChangeVO );

               // 返回编辑成功标记
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeePositionChangeVO, Operate.SUBMIT, employeePositionChangeVO.getPositionChangeId(), null );
            }
            else
            {
               // 返回编辑成功标记
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeePositionChangeVO, Operate.MODIFY, employeePositionChangeVO.getPositionChangeId(), null );
            }
         }

         // 清空Form条件
         ( ( EmployeePositionChangeVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );

         final EmployeePositionChangeVO employeePositionChangeVO = new EmployeePositionChangeVO();
         // 获得当前主键
         String positionChangeId = request.getParameter( "positionChangeId" );

         // 删除主键对应对象
         employeePositionChangeVO.setPositionChangeId( positionChangeId );
         employeePositionChangeVO.setModifyBy( getUserId( request, response ) );
         // 删除对象
         positionChangeService.deleteEmployeePositionChange( employeePositionChangeVO );
         insertlog( request, employeePositionChangeVO, Operate.DELETE, positionChangeId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );

         // 获得Action Form
         EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) form;
         // 存在选中的ID
         if ( employeePositionChangeVO.getSelectedIds() != null && !employeePositionChangeVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeePositionChangeVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeePositionChangeVO.setPositionChangeId( selectedId );
               employeePositionChangeVO.setModifyBy( getUserId( request, response ) );

               positionChangeService.deleteEmployeePositionChange( employeePositionChangeVO );
            }

            insertlog( request, employeePositionChangeVO, Operate.DELETE, null, employeePositionChangeVO.getSelectedIds() );
         }
         // 清除Selected IDs和子Action
         employeePositionChangeVO.setSelectedIds( "" );
         employeePositionChangeVO.setSubAction( "" );
         employeePositionChangeVO.setPositionChangeId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward submit_objects( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );

         // 获得Action Form
         EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) form;
         // 存在选中的ID
         if ( employeePositionChangeVO.getSelectedIds() != null && !employeePositionChangeVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeePositionChangeVO.getSelectedIds().split( "," ) )
            {
               EmployeePositionChangeVO employeePositionChangeVODB = positionChangeService.getEmployeePositionChangeVOByPositionChangeId( selectedId );
               if ( employeePositionChangeVODB.getWorkflowId() != null )
            	   continue;
               
               employeePositionChangeVODB.setStatus( "3" );
               employeePositionChangeVODB.setModifyBy( getUserId( request, response ) );
               employeePositionChangeVODB.setRole( getRole( request, response ) );
               positionChangeService.generateHistoryVOForWorkflow( employeePositionChangeVODB );
               employeePositionChangeVODB.setLocale( request.getLocale() );
               positionChangeService.submitEmployeePositionChange( employeePositionChangeVODB );
            }

            success( request, MESSAGE_TYPE_SUBMIT );

            insertlog( request, employeePositionChangeVO, Operate.BATCH_SUBMIT, employeePositionChangeVO.getSelectedIds(), null );
         }
         // 清除Selected IDs和子Action
         employeePositionChangeVO.setSelectedIds( "" );
         employeePositionChangeVO.setSubAction( "" );
         employeePositionChangeVO.setSubmitFlag( 0 );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   public ActionForward synchronized_objects( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );
         positionChangeService.synchronizedEmployeePosition();
         success( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.synchronized.success" ) );
         insertlog( request, form, Operate.DELETE, null, "synchronized_objects" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   public void getPositionsByEmployeeId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         String accountId = getAccountId( request, response );
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
         String employeeId = request.getParameter( "employeeId" );
         String oldPositionId = request.getParameter( "oldPositionId" );
         boolean has = false;
         List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
         if ( StringUtils.isNotEmpty( employeeId ) )
         {
            final Map< String, String > emptyMap = new HashMap< String, String >();
            emptyMap.put( "id", "0" );
            emptyMap.put( "name", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "请选择" : "Please Select" );
            listReturn.add( emptyMap );

            List< StaffDTO > staffList = accountConstants.getStaffDTOsByEmployeeId( employeeId );
            if ( staffList != null && staffList.size() > 0 )
            {
               StaffDTO staffDTO = staffList.get( 0 );
               if ( staffDTO != null )
               {
                  StaffVO staffVO = staffDTO.getStaffVO();
                  if ( staffVO != null )
                  {
                     List< PositionDTO > positionList = accountConstants.getPositionDTOsByStaffId( staffVO.getStaffId() );
                     for ( PositionDTO positionDTO : positionList )
                     {
                        PositionVO positionVO = positionDTO.getPositionVO();
                        if ( positionVO != null )
                        {
                           Map< String, String > map = new HashMap< String, String >();
                           map.put( "id", positionVO.getPositionId() );
                           map.put( "name", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? positionVO.getTitleZH() : positionVO.getTitleEN() );
                           listReturn.add( map );

                           if ( StringUtils.isNotEmpty( oldPositionId ) && positionVO.getPositionId().equals( oldPositionId ) )
                           {
                              has = true;
                           }
                        }
                     }
                  }
               }
            }

            if ( StringUtils.isNotEmpty( oldPositionId ) && !has )
            {
               PositionVO positionVO = accountConstants.getPositionVOByPositionId( oldPositionId );
               Map< String, String > map = new HashMap< String, String >();
               map.put( "id", positionVO.getPositionId() );
               map.put( "name", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? positionVO.getTitleZH() : positionVO.getTitleEN() );
               listReturn.add( map );
            }
         }
         JSONArray json = JSONArray.fromObject( listReturn );
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void getBranchInfoByPositionId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         String accountId = getAccountId( request, response );
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
         String employeeId = request.getParameter( "employeeId" );
         String positionId = request.getParameter( "positionId" );
         Map< String, String > mapReturn = new HashMap< String, String >();
         if ( StringUtils.isNotEmpty( positionId ) )
         {
            //获取职位信息
            PositionVO positionVO = accountConstants.getPositionVOByPositionId( positionId );
            if ( positionVO != null )
            {
               PositionVO parentPositionVO = accountConstants.getPositionVOByPositionId( positionVO.getParentPositionId() );
               if ( parentPositionVO != null )
               {
                  mapReturn.put( "parentPositionName", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? parentPositionVO.getTitleZH() : parentPositionVO.getTitleEN() );
               }
               mapReturn.put( "positionId", positionVO.getPositionId() );
               mapReturn.put( "positionName", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? positionVO.getTitleZH() : positionVO.getTitleEN() );
               mapReturn.put( "parentPositionOwnersName", accountConstants.getStaffNamesByPositionId( request.getLocale().getLanguage(), positionVO.getParentPositionId() ) );

               //获取部门信息
               BranchVO branchVO = accountConstants.getBranchVOByBranchId( positionVO.getBranchId() );
               if ( branchVO != null )
               {
                  BranchVO parentBranchVO = accountConstants.getBranchVOByBranchId( branchVO.getParentBranchId() );
                  if ( parentBranchVO != null )
                  {
                     mapReturn.put( "parentBranchName", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? parentBranchVO.getNameZH() : parentBranchVO.getNameEN() );
                  }
                  mapReturn.put( "branchId", branchVO.getBranchId() );
                  mapReturn.put( "branchName", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? branchVO.getNameZH() : branchVO.getNameEN() );
               }

               //获取职级信息
               PositionGradeVO positionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( positionVO.getPositionGradeId() );
               if ( positionGradeVO != null )
               {
                  mapReturn.put( "positionGradeName", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? positionGradeVO.getGradeNameZH()
                        : positionGradeVO.getGradeNameEN() );
               }

               //获取员工职级关系信息
               String staffPositionRelationId = "";
               if ( StringUtils.isNotEmpty( employeeId ) )
               {
                  String staffId = "";
                  PositionDTO positionDTO = accountConstants.getPositionDTOByPositionId( positionId );
                  List< StaffDTO > staffList = accountConstants.getStaffDTOsByEmployeeId( employeeId );
                  if ( staffList != null && staffList.size() > 0 )
                  {
                     if ( staffList != null && staffList.size() > 0 )
                     {
                        StaffDTO staffDTO = staffList.get( 0 );
                        if ( staffDTO != null )
                        {
                           StaffVO staffVO = staffDTO.getStaffVO();
                           if ( staffVO != null )
                           {
                              staffId = staffVO.getStaffId();
                           }
                        }
                     }
                  }
                  if ( positionDTO != null )
                  {
                     List< PositionStaffRelationVO > positionStaffRelationList = positionDTO.getPositionStaffRelationVOs();
                     for ( PositionStaffRelationVO positionStaffRelationVO : positionStaffRelationList )
                     {
                        if ( StringUtils.equals( staffId, positionStaffRelationVO.getStaffId() ) )
                        {
                           staffPositionRelationId = positionStaffRelationVO.getRelationId();
                        }
                     }
                  }
               }
               mapReturn.put( "staffPositionRelationId", staffPositionRelationId );
            }
         }
         JSONObject json = JSONObject.fromObject( mapReturn );
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void getEmployeeInfoByEmployeeId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         String employeeId = request.getParameter( "employeeId" );
         Map< String, String > mapReturn = new HashMap< String, String >();
         //获取员工信息
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
         if ( employeeVO != null )
         {
            mapReturn.put( "employeeId", employeeVO.getEmployeeId() );
            mapReturn.put( "employeeNo", employeeVO.getEmployeeNo() );
            mapReturn.put( "employeeName", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? employeeVO.getNameZH() : employeeVO.getNameEN() );
            mapReturn.put( "employeeNameZH", employeeVO.getNameZH() );
            mapReturn.put( "employeeNameEN", employeeVO.getNameEN() );
            mapReturn.put( "employeeCertificateNumber", employeeVO.getCertificateNumber() );
         }

         JSONObject json = JSONObject.fromObject( mapReturn );
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void getBranchForPage( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         String accountId = getAccountId( request, response );
         String corpId = getCorpId( request, response );
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
         List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
         final Map< String, String > emptyMap = new HashMap< String, String >();
         emptyMap.put( "id", "0" );
         emptyMap.put( "name", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "请选择" : "Please Select" );
         listReturn.add( emptyMap );

         List< MappingVO > mappingVOList = accountConstants.getBranchs( request.getLocale().getLanguage(), corpId );
         for ( MappingVO mappingVO : mappingVOList )
         {
            Map< String, String > map = new HashMap< String, String >();
            map.put( "id", mappingVO.getMappingId() );
            map.put( "name", StringUtils.replace( mappingVO.getMappingValue(), "&nbsp;", "    " ) );
            listReturn.add( map );
         }
         JSONArray json = JSONArray.fromObject( listReturn );
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void getPositionIdByBranchId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         String accountId = getAccountId( request, response );
         String corpId = getCorpId( request, response );
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
         String branchId = request.getParameter( "branchId" );
         List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
         List< PositionVO > positionList = accountConstants.getPositionVOsByBranchId( branchId );

         final Map< String, String > emptyMap = new HashMap< String, String >();
         emptyMap.put( "id", "0" );
         emptyMap.put( "name", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "请选择" : "Please Select" );
         emptyMap.put( "full", "0" );
         listReturn.add( emptyMap );

         for ( PositionVO positionVO : positionList )
         {
            if ( corpId.equals( positionVO.getCorpId() ) )
            {
               int staffNum = accountConstants.getStaffNumByPositionId( request.getLocale().getLanguage(), positionVO.getPositionId() );
               int isVacant = Integer.parseInt( positionVO.getIsVacant() );
               // 职级名称
               positionVO.reset( null, request );
               final String positionGradeName = ( KANUtil.filterEmpty( positionVO.getPositionGradeId(), "0" ) == null ? ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "[暂无职级]"
                     : "[No Position Grade]" )
                     : "[" + positionVO.getDecodePositionGradeId() + "]" );

               // 职位名称
               final String positionName = request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? positionVO.getTitleZH() : positionVO.getTitleEN();

               // 编制已满提示
               String isVacantTips = "";
               if ( isVacant != 0 && staffNum > isVacant )
               {
                  isVacantTips = request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "[编制已满]" : "[All positions are filled.]";
               }

               // 获取上级职位staff
               final List< StaffDTO > parentStaffDTOs = accountConstants.getStaffDTOsByPositionId( positionVO.getPositionId() );

               // 上级职位所属人
               String parenrPositionOwner = "";
               if ( parentStaffDTOs != null && parentStaffDTOs.size() > 0 )
               {
                  for ( StaffDTO parentStaffDTO : parentStaffDTOs )
                  {
                     if ( KANUtil.filterEmpty( parenrPositionOwner ) == null )
                     {
                        parenrPositionOwner = request.getLocale().getLanguage().equals( "zh" ) ? parentStaffDTO.getStaffVO().getNameZH() : parentStaffDTO.getStaffVO().getNameEN();
                     }
                     else
                     {
                        parenrPositionOwner = parenrPositionOwner + "、"
                              + ( request.getLocale().getLanguage().equals( "zh" ) ? parentStaffDTO.getStaffVO().getNameZH() : parentStaffDTO.getStaffVO().getNameEN() );
                     }
                  }
               }
               Map< String, String > map = new HashMap< String, String >();
               map.put( "id", positionVO.getPositionId() );
               map.put( "name", positionGradeName + " " + positionName + " " + isVacantTips + " " + parenrPositionOwner );
               if ( isVacant != 0 && staffNum > isVacant )
               {
                  map.put( "full", "1" );
               }
               else
               {
                  map.put( "full", "0" );
               }
               listReturn.add( map );
            }
         }
         JSONArray json = JSONArray.fromObject( listReturn );
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void checkPositionChangeStatus( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );
         final EmployeePositionChangeVO employeePositionChangeVO = ( EmployeePositionChangeVO ) form;
         employeePositionChangeVO.setStatus( "5" );
         int count = positionChangeService.getEffectivePositionChangeVOCountByEmployeeId( employeePositionChangeVO );
         Map< String, String > mapReturn = new HashMap< String, String >();
         mapReturn.put( "count", count + "" );
         JSONObject json = JSONObject.fromObject( mapReturn );
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( IOException e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_object_workflow_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {

         // 获得批次主键ID
         String historyId = request.getParameter( "historyId" );

         // 初始化Service接口
         final HistoryService historyService = ( HistoryService ) getService( "historyService" );
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeePositionChangeService positionChangeService = ( EmployeePositionChangeService ) getService( "employeePositionChangeService" );

         HistoryVO historyVO = historyService.getHistoryVOByHistoryId( historyId );

         // 初始化SBBatchVO
         final String objectClassStr = historyVO.getObjectClass();
         Class< ? > objectClass = Class.forName( objectClassStr );

         String passObjStr = historyVO.getPassObject();
         final EmployeePositionChangeVO employeePositionChange = ( EmployeePositionChangeVO ) JSONObject.toBean( JSONObject.fromObject( passObjStr ), objectClass );
         if ( employeePositionChange != null )
         {
            final EmployeePositionChangeVO employeePositionChangeVO = positionChangeService.getEmployeePositionChangeVOByPositionChangeId( employeePositionChange.getPositionChangeId() );
            final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeePositionChangeVO.getEmployeeId() );

            // 刷新对象，初始化对象列表及国际化
            employeePositionChangeVO.reset( null, request );

            employeePositionChangeVO.setSubAction( VIEW_OBJECT );
            request.setAttribute( "employeeVO", employeeVO );
            request.setAttribute( "employeePositionChangeForm", employeePositionChangeVO );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // Ajax调用
      return mapping.findForward( "managePositionChangeWorkFlow" );
   }

   public void loadPositionInfo_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         final String employeeId = request.getParameter( "employeeId" );
         final String positionId = request.getParameter( "positionId" );
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         final boolean lang_ch = request.getLocale().getLanguage().equalsIgnoreCase( "ZH" );

         final Map< String, String > mapReturn = new HashMap< String, String >();

         if ( StringUtils.isNotEmpty( positionId ) )
         {
            // 获取PositionVO
            final PositionVO positionVO = accountConstants.getPositionVOByPositionId( positionId );
            if ( positionVO != null )
            {
               // 保存职位信息到Map
               mapReturn.put( "oldPositionId", positionVO.getPositionId() );
               mapReturn.put( "oldPositionName", lang_ch ? positionVO.getTitleZH() : positionVO.getTitleEN() );
               mapReturn.put( "oldPositionNameZH", positionVO.getTitleZH() );
               mapReturn.put( "oldPositionNameEN", positionVO.getTitleEN() );
               mapReturn.put( "oldPositionGradeId", positionVO.getPositionGradeId() );
               mapReturn.put( "oldParentPositionId", positionVO.getParentPositionId() );
               mapReturn.put( "oldBranchId", positionVO.getBranchId() );

               // 获取上级PositionVO
               final PositionVO parentPositionVO = accountConstants.getPositionVOByPositionId( positionVO.getParentPositionId() );
               if ( parentPositionVO != null )
               {
                  // 保存上级职位信息到Map
                  mapReturn.put( "oldParentPositionName", lang_ch ? parentPositionVO.getTitleZH() : parentPositionVO.getTitleEN() );
                  mapReturn.put( "oldParentPositionNameZH", parentPositionVO.getTitleZH() );
                  mapReturn.put( "oldParentPositionNameEN", parentPositionVO.getTitleEN() );

                  // 保存上级职位所属人信息到Map
                  mapReturn.put( "oldParentPositionOwners", accountConstants.getStaffNamesByPositionId( request.getLocale().getLanguage(), positionVO.getParentPositionId() ) );
                  mapReturn.put( "oldParentPositionOwnersZH", accountConstants.getStaffNamesByPositionId( "zh", positionVO.getParentPositionId() ) );
                  mapReturn.put( "oldParentPositionOwnersEN", accountConstants.getStaffNamesByPositionId( "en", positionVO.getParentPositionId() ) );
               }

               // 获取BranchVO
               final BranchVO branchVO = accountConstants.getBranchVOByBranchId( positionVO.getBranchId() );
               if ( branchVO != null )
               {
                  // 保存部门信息到Map
                  mapReturn.put( "oldBranchName", lang_ch ? branchVO.getNameZH() : branchVO.getNameEN() );
                  mapReturn.put( "oldBranchNameZH", branchVO.getNameZH() );
                  mapReturn.put( "oldBranchNameEN", branchVO.getNameEN() );
                  mapReturn.put( "oldParentBranchId", branchVO.getParentBranchId() );

                  // 获取上级部门BranchVO
                  final BranchVO parentBranchVO = accountConstants.getBranchVOByBranchId( branchVO.getParentBranchId() );
                  if ( parentBranchVO != null )
                  {
                     // 保存上级部门信息到Map
                     mapReturn.put( "oldParentBranchName", lang_ch ? parentBranchVO.getNameZH() : parentBranchVO.getNameEN() );
                     mapReturn.put( "oldParentBranchNameZH", parentBranchVO.getNameZH() );
                     mapReturn.put( "oldParentBranchNameEN", parentBranchVO.getNameEN() );
                  }
               }

               // 获取职级PositionGradeVO
               final PositionGradeVO positionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( positionVO.getPositionGradeId() );
               if ( positionGradeVO != null )
               {
                  mapReturn.put( "oldPositionGradeName", lang_ch ? positionGradeVO.getGradeNameZH() : positionGradeVO.getGradeNameEN() );
                  mapReturn.put( "oldPositionGradeNameZH", positionGradeVO.getGradeNameZH() );
                  mapReturn.put( "oldPositionGradeNameEN", positionGradeVO.getGradeNameEN() );
               }

               // 获取staffPositionRelationId
               String tempStaffId = "";
               String oldStaffPositionRelationId = "0";
               if ( StringUtils.isNotEmpty( employeeId ) )
               {
                  final PositionDTO positionDTO = accountConstants.getPositionDTOByPositionId( positionId );
                  final List< StaffDTO > staffDTOs = accountConstants.getStaffDTOsByEmployeeId( employeeId );
                  if ( staffDTOs != null && staffDTOs.size() > 0 )
                  {
                     StaffDTO staffDTO = staffDTOs.get( 0 );
                     if ( staffDTO != null && staffDTO.getStaffVO() != null )
                     {
                        tempStaffId = staffDTO.getStaffVO().getStaffId();
                     }
                  }

                  if ( positionDTO != null )
                  {
                     List< PositionStaffRelationVO > positionStaffRelationList = positionDTO.getPositionStaffRelationVOs();
                     for ( PositionStaffRelationVO positionStaffRelationVO : positionStaffRelationList )
                     {
                        if ( StringUtils.equals( tempStaffId, positionStaffRelationVO.getStaffId() ) )
                        {
                           oldStaffPositionRelationId = positionStaffRelationVO.getRelationId();
                           break;
                        }
                     }
                  }
               }

               mapReturn.put( "staffId", tempStaffId );
               mapReturn.put( "oldStaffPositionRelationId", oldStaffPositionRelationId );
            }
         }

         out.println( JSONObject.fromObject( mapReturn ).toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 上级职位改变
   public void parentPositionChange_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         final String newParentPositionId = request.getParameter( "newParentPositionId" );
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         final Map< String, String > mapReturn = new HashMap< String, String >();

         String newParentPositionNameZH = "";
         String newParentPositionNameEN = "";
         String newParentPositionOwnersZH = "";
         String newParentPositionOwnersEN = "";
         if ( StringUtils.isNotEmpty( newParentPositionId ) )
         {
            // 获取上级PositionVO
            final PositionVO parentPositionVO = accountConstants.getPositionVOByPositionId( newParentPositionId );
            if ( parentPositionVO != null )
            {
               newParentPositionNameZH = parentPositionVO.getTitleZH();
               newParentPositionNameEN = parentPositionVO.getTitleEN();
               newParentPositionOwnersZH = accountConstants.getStaffNamesByPositionId( "zh", newParentPositionId );
               newParentPositionOwnersEN = accountConstants.getStaffNamesByPositionId( "en", newParentPositionId );
            }
         }

         // 保存上级职位信息到Map
         mapReturn.put( "newParentPositionNameZH", newParentPositionNameZH );
         mapReturn.put( "newParentPositionNameEN", newParentPositionNameEN );
         // 保存上级职位所属人信息到Map
         mapReturn.put( "newParentPositionOwnersZH", newParentPositionOwnersZH );
         mapReturn.put( "newParentPositionOwnersEN", newParentPositionOwnersEN );

         out.println( JSONObject.fromObject( mapReturn ).toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 部门改变
   public void branchChange_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         final String newBranchId = request.getParameter( "newBranchId" );
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         final Map< String, String > mapReturn = new HashMap< String, String >();
         if ( StringUtils.isNotEmpty( newBranchId ) )
         {
            // 获取BranchVO
            final BranchVO branchVO = accountConstants.getBranchVOByBranchId( newBranchId );
            if ( branchVO != null )
            {
               mapReturn.put( "newBranchNameZH", branchVO.getNameZH() );
               mapReturn.put( "newBranchNameEN", branchVO.getNameEN() );
               mapReturn.put( "newParentBranchId", branchVO.getParentBranchId() );

               // 获取上级BranchVO
               final BranchVO parentBranchVO = accountConstants.getBranchVOByBranchId( branchVO.getParentBranchId() );
               if ( parentBranchVO != null )
               {
                  mapReturn.put( "newParentBranchNameZH", parentBranchVO.getNameZH() );
                  mapReturn.put( "newParentBranchNameEN", parentBranchVO.getNameEN() );
               }
            }
         }

         out.println( JSONObject.fromObject( mapReturn ).toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 职级改变
   public void positionGradeChange_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         final String positionGradeId = request.getParameter( "newPositionGradeId" );
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         final Map< String, String > mapReturn = new HashMap< String, String >();
         if ( StringUtils.isNotEmpty( positionGradeId ) )
         {
            // 获取PositionGradeVO
            final PositionGradeVO positionGradeVO = accountConstants.getPositionGradeVOByPositionGradeId( positionGradeId );
            if ( positionGradeVO != null )
            {
               mapReturn.put( "newPositionGradeNameZH", positionGradeVO.getGradeNameZH() );
               mapReturn.put( "newPositionGradeNameEN", positionGradeVO.getGradeNameEN() );
            }
         }

         out.println( JSONObject.fromObject( mapReturn ).toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 检查职位上是否存在多名员工
   public void checkPositionStaffNum_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "html/text;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         final String positionId = request.getParameter( "positionId" );
         final List< StaffDTO > staffDTOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getStaffDTOsByPositionId( positionId );

         String result = "1";
         if ( staffDTOs != null && staffDTOs.size() > 1 )
         {
            result = "2";
         }

         out.print( result );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }
}
