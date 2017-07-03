package com.kan.hro.web.actions.biz.employee;

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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.HistoryService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSalaryService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeeSalaryAdjustmentService;

public class EmployeeSalaryAdjustmentAction extends BaseAction
{
   public static String accessAction = "HRO_EMPLOYEE_SALARY_ADJUSTMENT";

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
         final EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService = ( EmployeeSalaryAdjustmentService ) getService( "employeeSalaryAdjustmentService" );
         // 获得Action Form
         final EmployeeSalaryAdjustmentVO salaryAdjustmentVO = ( EmployeeSalaryAdjustmentVO ) form;

         // 如果子Action是删除用户列表
         if ( salaryAdjustmentVO.getSubAction() != null && salaryAdjustmentVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( salaryAdjustmentVO );
         }

         // 如果没有指定排序则默认按employeeId排序
         if ( salaryAdjustmentVO.getSortColumn() == null || salaryAdjustmentVO.getSortColumn().isEmpty() )
         {
            salaryAdjustmentVO.setSortColumn( "salaryAdjustmentId" );
            salaryAdjustmentVO.setSortOrder( "desc" );
         }

         setDataAuth( request, response, salaryAdjustmentVO );
         // 获得SubAction
         final String subAction = getSubAction( form );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder salaryAdjustmentHolder = new PagedListHolder();

         // 传入当前页
         salaryAdjustmentHolder.setPage( page );
         // 传入当前值对象
         salaryAdjustmentHolder.setObject( salaryAdjustmentVO );
         // 设置页面记录条数
         salaryAdjustmentHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeSalaryAdjustmentService.getSalaryAdjustmentVOsByCondition( salaryAdjustmentHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // 刷新Holder，国际化传值
         refreshHolder( salaryAdjustmentHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "salaryAdjustmentHolder", salaryAdjustmentHolder );
         request.setAttribute( "isPaged", "1" );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // 这个导出的excel是手动。
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               return new DownloadFileAction().exportExcel4EmployeeSalaryAdjustment( mapping, form, request, response );
            }
            // Ajax调用无跳转
            return mapping.findForward( "listSalaryAdjustmentTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listSalaryAdjustment" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加页面Token
      this.saveToken( request );
      request.setAttribute( "employeePositionList", new ArrayList< PositionVO >() );
      // 设置Sub Action
      ( ( EmployeeSalaryAdjustmentVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( EmployeeSalaryAdjustmentVO ) form ).setStatus( "1" );

      // 跳转到新建界面
      return mapping.findForward( "manageSalaryAdjustment" );
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
            final EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService = ( EmployeeSalaryAdjustmentService ) getService( "employeeSalaryAdjustmentService" );

            // 获得ActionForm
            final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO = ( EmployeeSalaryAdjustmentVO ) form;
            // 获取登录用户
            employeeSalaryAdjustmentVO.setAccountId( getAccountId( request, response ) );
            employeeSalaryAdjustmentVO.setCreateBy( getUserId( request, response ) );
            employeeSalaryAdjustmentVO.setModifyBy( getUserId( request, response ) );
            employeeSalaryAdjustmentVO.setEmployeeSalaryId( KANUtil.decodeString( employeeSalaryAdjustmentVO.getEmployeeSalaryId() ) );
            employeeSalaryAdjustmentVO.setEffectiveDate( employeeSalaryAdjustmentVO.getNewStartDate() );

            // 新建对象
            employeeSalaryAdjustmentService.insertEmployeeSalaryAdjustment( employeeSalaryAdjustmentVO );
            final EmployeeSalaryAdjustmentVO tempEmployeeSalaryAdjustmentVO = employeeSalaryAdjustmentService.getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId( employeeSalaryAdjustmentVO.getSalaryAdjustmentId() );
            tempEmployeeSalaryAdjustmentVO.reset( null, request );
            if ( employeeSalaryAdjustmentVO.getSubmitFlag() == 1 )
            {
               tempEmployeeSalaryAdjustmentVO.setStatus( "3" );
               tempEmployeeSalaryAdjustmentVO.setRole( getRole( request, response ) );
               employeeSalaryAdjustmentService.generateHistoryVOForWorkflow( tempEmployeeSalaryAdjustmentVO );
               employeeSalaryAdjustmentService.submitEmployeeSalaryAdjustment( tempEmployeeSalaryAdjustmentVO );

               // 返回添加成功标记
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, tempEmployeeSalaryAdjustmentVO, Operate.SUBMIT, employeeSalaryAdjustmentVO.getSalaryAdjustmentId(), null );
            }
            else
            {
               // 返回添加成功标记
               success( request, MESSAGE_TYPE_ADD );
               insertlog( request, tempEmployeeSalaryAdjustmentVO, Operate.ADD, employeeSalaryAdjustmentVO.getSalaryAdjustmentId(), null );
            }
         }
         else
         {
            // 清空Form
            ( ( EmployeeSalaryAdjustmentVO ) form ).reset();
            ( ( EmployeeSalaryAdjustmentVO ) form ).setEmployeeCertificateNumber( "" );

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
         final EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService = ( EmployeeSalaryAdjustmentService ) getService( "employeeSalaryAdjustmentService" );
         // 主键获取需解码
         String salaryAdjustmentId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( salaryAdjustmentId ) == null )
         {
            salaryAdjustmentId = ( ( EmployeeSalaryAdjustmentVO ) form ).getSalaryAdjustmentId();
         }
         final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO = employeeSalaryAdjustmentService.getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId( salaryAdjustmentId );
         employeeSalaryAdjustmentVO.setEmployeeSalaryId( employeeSalaryAdjustmentVO.encodedField( employeeSalaryAdjustmentVO.getEmployeeSalaryId() ) );
         // 刷新对象，初始化对象列表及国际化
         employeeSalaryAdjustmentVO.reset( null, request );

         employeeSalaryAdjustmentVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "employeeSalaryAdjustmentForm", employeeSalaryAdjustmentVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageSalaryAdjustment" );
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
            final EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService = ( EmployeeSalaryAdjustmentService ) getService( "employeeSalaryAdjustmentService" );
            // 主键获取需解码
            String salaryAdjustmentId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            if ( KANUtil.filterEmpty( salaryAdjustmentId ) == null )
            {
               salaryAdjustmentId = ( ( EmployeeSalaryAdjustmentVO ) form ).getSalaryAdjustmentId();
            }
            // 获得EmployeeSalaryAdjustmentVO
            final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO = employeeSalaryAdjustmentService.getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId( salaryAdjustmentId );
            // 获取登录用户
            employeeSalaryAdjustmentVO.update( ( EmployeeSalaryAdjustmentVO ) form );
            employeeSalaryAdjustmentVO.setModifyBy( getUserId( request, response ) );
            employeeSalaryAdjustmentVO.setEmployeeSalaryId( KANUtil.decodeString( employeeSalaryAdjustmentVO.getEmployeeSalaryId() ) );
            employeeSalaryAdjustmentVO.setEffectiveDate( employeeSalaryAdjustmentVO.getNewStartDate() );
            if ( employeeSalaryAdjustmentVO.getSubmitFlag() == 1 )
            {
               // 先修改对象
               employeeSalaryAdjustmentService.updateEmployeeSalaryAdjustment( employeeSalaryAdjustmentVO );
               // 再提交对象
               employeeSalaryAdjustmentVO.setStatus( "3" );
               employeeSalaryAdjustmentVO.setRole( getRole( request, response ) );
               employeeSalaryAdjustmentService.generateHistoryVOForWorkflow( employeeSalaryAdjustmentVO );
               employeeSalaryAdjustmentService.submitEmployeeSalaryAdjustment( employeeSalaryAdjustmentVO );
               // 返回提交成功标记
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeeSalaryAdjustmentVO, Operate.SUBMIT, employeeSalaryAdjustmentVO.getSalaryAdjustmentId(), null );
            }
            else
            {
               employeeSalaryAdjustmentVO.setStatus( "1" );
               employeeSalaryAdjustmentService.updateEmployeeSalaryAdjustment( employeeSalaryAdjustmentVO );
               // 返回编辑成功标记
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeeSalaryAdjustmentVO, Operate.MODIFY, employeeSalaryAdjustmentVO.getSalaryAdjustmentId(), null );
            }
         }

         // 清空Form条件
         ( ( EmployeeSalaryAdjustmentVO ) form ).reset();
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
         final EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService = ( EmployeeSalaryAdjustmentService ) getService( "employeeSalaryAdjustmentService" );

         final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO = new EmployeeSalaryAdjustmentVO();
         // 获得当前主键
         String salaryAdjustmentId = request.getParameter( "salaryAdjustmentId" );

         // 删除主键对应对象
         employeeSalaryAdjustmentVO.setSalaryAdjustmentId( salaryAdjustmentId );
         employeeSalaryAdjustmentVO.setModifyBy( getUserId( request, response ) );
         // 删除对象
         employeeSalaryAdjustmentService.deleteEmployeeSalaryAdjustment( employeeSalaryAdjustmentVO );
         insertlog( request, employeeSalaryAdjustmentVO, Operate.DELETE, salaryAdjustmentId, null );
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
         final EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService = ( EmployeeSalaryAdjustmentService ) getService( "employeeSalaryAdjustmentService" );

         // 获得Action Form
         EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO = ( EmployeeSalaryAdjustmentVO ) form;
         // 存在选中的ID
         if ( employeeSalaryAdjustmentVO.getSelectedIds() != null && !employeeSalaryAdjustmentVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeSalaryAdjustmentVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               employeeSalaryAdjustmentVO.setSalaryAdjustmentId( selectedId );
               employeeSalaryAdjustmentVO.setModifyBy( getUserId( request, response ) );

               employeeSalaryAdjustmentService.deleteEmployeeSalaryAdjustment( employeeSalaryAdjustmentVO );
            }

            insertlog( request, employeeSalaryAdjustmentVO, Operate.DELETE, null, employeeSalaryAdjustmentVO.getSelectedIds() );
         }
         // 清除Selected IDs和子Action
         employeeSalaryAdjustmentVO.setSelectedIds( "" );
         employeeSalaryAdjustmentVO.setSubAction( "" );
         employeeSalaryAdjustmentVO.setSalaryAdjustmentId( "" );
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
         final EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService = ( EmployeeSalaryAdjustmentService ) getService( "employeeSalaryAdjustmentService" );

         // 获得Action Form
         EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO = ( EmployeeSalaryAdjustmentVO ) form;
         // 存在选中的ID
         if ( employeeSalaryAdjustmentVO.getSelectedIds() != null && !employeeSalaryAdjustmentVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : employeeSalaryAdjustmentVO.getSelectedIds().split( "," ) )
            {
               EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVODB = employeeSalaryAdjustmentService.getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId( selectedId );
               employeeSalaryAdjustmentVODB.setStatus( "3" );
               employeeSalaryAdjustmentVODB.setModifyBy( getUserId( request, response ) );
               employeeSalaryAdjustmentVODB.setRole( getRole( request, response ) );
               employeeSalaryAdjustmentService.generateHistoryVOForWorkflow( employeeSalaryAdjustmentVODB );
               employeeSalaryAdjustmentService.submitEmployeeSalaryAdjustment( employeeSalaryAdjustmentVODB );
            }

            success( request, MESSAGE_TYPE_SUBMIT );

            insertlog( request, employeeSalaryAdjustmentVO, Operate.BATCH_SUBMIT, employeeSalaryAdjustmentVO.getSelectedIds(), null );
         }
         // 清除Selected IDs和子Action
         employeeSalaryAdjustmentVO.setSelectedIds( "" );
         employeeSalaryAdjustmentVO.setSubAction( "" );
         employeeSalaryAdjustmentVO.setSubmitFlag( 0 );
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
         final EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService = ( EmployeeSalaryAdjustmentService ) getService( "employeeSalaryAdjustmentService" );
         employeeSalaryAdjustmentService.synchronizedEmployeeSalaryContract();
         success( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.synchronized.success" ) );
         insertlog( request, form, Operate.SUBMIT, null, "synchronized_objects" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   public void getSalaryItemByContractId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );

         String contractId = request.getParameter( "contractId" );
         final List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();

         final Map< String, String > emptyMap = new HashMap< String, String >();
         emptyMap.put( "id", KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingId() );
         emptyMap.put( "name", KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() );
         listReturn.add( emptyMap );

         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         List< Object > salaryContractList = employeeContractSalaryService.getEmployeeContractSalaryVOsByContractId( contractId );
         for ( Object object : salaryContractList )
         {
            Map< String, String > map = new HashMap< String, String >();
            EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) object;
            employeeContractSalaryVO.reset( mapping, request );
            map.put( "id", employeeContractSalaryVO.getEncodedId() );
            map.put( "name", employeeContractSalaryVO.getDecodeItemId() );
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

   public void getEmployeeAndContractInfo( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         String contractId = request.getParameter( "contractId" );
         Map< String, String > mapReturn = new HashMap< String, String >();
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         if ( employeeContractVO != null )
         {
            if ( request.getLocale().getLanguage().equalsIgnoreCase( "EN" ) )
            {
               mapReturn.put( "contractName", employeeContractVO.getNameEN() );
               mapReturn.put( "employeeName", employeeContractVO.getEmployeeNameEN() );
            }
            else
            {
               mapReturn.put( "contractName", employeeContractVO.getNameZH() );
               mapReturn.put( "employeeName", employeeContractVO.getEmployeeNameZH() );
            }
            mapReturn.put( "employeeId", employeeContractVO.getEmployeeId() );
            mapReturn.put( "employeeNo", employeeContractVO.getEmployeeNo() );
            mapReturn.put( "employeeCertificateNumber", employeeContractVO.getCertificateNumber() );
            mapReturn.put( "contractStartDate", employeeContractVO.getStartDate() );
            mapReturn.put( "contractEndDate", employeeContractVO.getEndDate() );
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

   public void getBaseSalaryByItemId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         String employeeSalaryId = KANUtil.decodeString( request.getParameter( "employeeSalaryId" ) );
         Map< String, String > mapReturn = new HashMap< String, String >();
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( employeeSalaryId );
         if ( employeeContractSalaryVO != null )
         {
            mapReturn.put( "itemId", employeeContractSalaryVO.getItemId() );
            mapReturn.put( "base", KANUtil.formatNumber( employeeContractSalaryVO.getBase(), getAccountId( request, null ) ) );
            mapReturn.put( "startDate", employeeContractSalaryVO.getStartDate() );
            mapReturn.put( "endDate", employeeContractSalaryVO.getEndDate() );
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

   public void checkHasContainsSalaryAdjustmentData( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         final PrintWriter out = response.getWriter();
         String salaryAdjustmentId = KANUtil.decodeString( request.getParameter( "salaryAdjustmentId" ) );
         ;
         String employeeSalaryId = KANUtil.decodeString( request.getParameter( "employeeSalaryId" ) );
         String contractId = request.getParameter( "contractId" );
         final EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService = ( EmployeeSalaryAdjustmentService ) getService( "employeeSalaryAdjustmentService" );
         EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO = new EmployeeSalaryAdjustmentVO();
         employeeSalaryAdjustmentVO.setEmployeeSalaryId( employeeSalaryId );
         employeeSalaryAdjustmentVO.setContractId( contractId );
         employeeSalaryAdjustmentVO.setSalaryAdjustmentId( salaryAdjustmentId );
         int count = employeeSalaryAdjustmentService.getEmployeeSalaryAdjustmentVOCountBySalaryIdAndContractId( employeeSalaryAdjustmentVO );
         if ( count > 0 )
         {
            out.print( "1" );
         }
         else
         {
            out.print( "0" );
         }
         out.flush();
         out.close();
      }
      catch ( final Exception e )
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
         final EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService = ( EmployeeSalaryAdjustmentService ) getService( "employeeSalaryAdjustmentService" );

         HistoryVO historyVO = historyService.getHistoryVOByHistoryId( historyId );

         // 初始化SBBatchVO
         final String objectClassStr = historyVO.getObjectClass();
         Class< ? > objectClass = Class.forName( objectClassStr );

         String passObjStr = historyVO.getPassObject();
         final EmployeeSalaryAdjustmentVO employeeSalaryAdjustmentVO = ( EmployeeSalaryAdjustmentVO ) JSONObject.toBean( JSONObject.fromObject( passObjStr ), objectClass );
         if ( employeeSalaryAdjustmentVO != null )
         {
            final EmployeeSalaryAdjustmentVO tempEmployeeSalaryAdjustmentVO = employeeSalaryAdjustmentService.getEmployeeSalaryAdjustmentVOBySalaryAdjustmentId( employeeSalaryAdjustmentVO.getSalaryAdjustmentId() );

            // 刷新对象，初始化对象列表及国际化
            tempEmployeeSalaryAdjustmentVO.reset( null, request );
            request.setAttribute( "employeeSalaryAdjustmentForm", tempEmployeeSalaryAdjustmentVO );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // Ajax调用
      return mapping.findForward( "manageSalaryAdjustmentWorkFlow" );
   }

   /***
    * loadEmployeeSalaryInfo_ajax
    * ajax加载员工薪酬信息
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @throws KANException
    */
   public void loadEmployeeSalaryInfo_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      String contractId = request.getParameter( "contractId" );
      String itemId = request.getParameter( "itemId" );

      Map< String, String > mapReturn = new HashMap< String, String >();
      mapReturn.put( "employeeSalaryId", "" );
      mapReturn.put( "oldBase", "0.00" );
      mapReturn.put( "oldStartDate", "" );
      mapReturn.put( "oldEndDate", "" );
      mapReturn.put( "itemNameZH", "" );
      mapReturn.put( "itemNameEN", "" );

      try
      {
         if ( KANUtil.filterEmpty( contractId ) != null && KANUtil.filterEmpty( itemId, "0" ) != null )
         {
            final Map< String, Object > parameters = new HashMap< String, Object >();
            parameters.put( "contractId", contractId );
            parameters.put( "itemId", itemId );
            final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
            List< Object > lists = employeeContractSalaryService.getEmployeeContractSalaryVOsByContractIdAndItemId( parameters );
            if ( lists != null && lists.size() > 0 )
            {
               EmployeeContractSalaryVO temp = null;
               long tempLong = Long.MIN_VALUE;
               if ( lists != null && lists.size() > 0 )
               {
                  for ( Object obj : lists )
                  {
                     final EmployeeContractSalaryVO tempVO = ( EmployeeContractSalaryVO ) obj;
                     if ( KANUtil.filterEmpty( tempVO.getStartDate() ) != null && KANUtil.createDate( tempVO.getStartDate() ).getTime() > tempLong )
                     {
                        temp = tempVO;
                        tempLong = KANUtil.createDate( tempVO.getStartDate() ).getTime();
                     }
                  }
               }

               if ( temp != null )
               {
                  ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getItemVOByItemId( itemId );
                  mapReturn.put( "employeeSalaryId", temp.getEncodedId() );
                  mapReturn.put( "oldBase", KANUtil.formatNumber( temp.getBase(), getAccountId( request, null ) ) );
                  mapReturn.put( "oldStartDate", temp.getStartDate() );
                  mapReturn.put( "oldEndDate", temp.getEndDate() );
                  if ( itemVO != null )
                  {
                     mapReturn.put( "itemNameZH", itemVO.getNameZH() );
                     mapReturn.put( "itemNameEN", itemVO.getNameEN() );
                  }
               }
            }
         }

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // Send to front
         out.println( JSONObject.fromObject( mapReturn ).toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
}
