package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.CertificationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.CertificationService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class CertificationAction extends BaseAction
{
   public final static String accessAction = "HRO_EMPLOYEE_LICENSES";

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
         final CertificationService certificationService = ( CertificationService ) getService( "certificationService" );
         // 获得Action Form
         final CertificationVO certificationVO = ( CertificationVO ) form;
         // 需要设置当前用户AccountId
         certificationVO.setAccountId( getAccountId( request, response ) );
         // 调用删除方法
         if ( certificationVO.getSubAction() != null && certificationVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( certificationVO );
         }
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder certificationHolder = new PagedListHolder();
         // 传入当前页
         certificationHolder.setPage( page );
         // 传入当前值对象
         certificationHolder.setObject( certificationVO );
         // 设置页面记录条数
         certificationHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         certificationService.getCertificationVOsByCondition( certificationHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( certificationHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "certificationHolder", certificationHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Item JSP
            return mapping.findForward( "listCertificationTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listCertification" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );
      // 设置Sub Action
      ( ( CertificationVO ) form ).setStatus( CertificationVO.TRUE );
      ( ( CertificationVO ) form ).setSubAction( CREATE_OBJECT );
      // 跳转到新建界面  
      return mapping.findForward( "manageCertification" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final CertificationService certificationService = ( CertificationService ) getService( "certificationService" );
            // 获得当前FORM
            final CertificationVO certificationVO = ( CertificationVO ) form;
            certificationVO.setCreateBy( getUserId( request, response ) );
            certificationVO.setModifyBy( getUserId( request, response ) );
            certificationVO.setAccountId( getAccountId( request, response ) );
            certificationService.insertCertification( certificationVO );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            // 初始化常量持久对象
            constantsInit( "initCertification", getAccountId( request, response ) );

            insertlog( request, certificationVO, Operate.ADD, certificationVO.getCertificationId(), null );
         }

         // 清空Form
         ( ( CertificationVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化 Service接口
         final CertificationService certificationService = ( CertificationService ) getService( "certificationService" );
         // 主键获取需解码
         String certificationId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "certificationId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( certificationId ) == null )
         {
            certificationId = ( ( CertificationVO ) form ).getCertificationId();
         }
         // 获得CertificationVO对象                                                                                          
         final CertificationVO certificationVO = certificationService.getCertificationVOByCertificationId( certificationId );
         // 区分Add和Update
         certificationVO.setSubAction( VIEW_OBJECT );
         certificationVO.reset( null, request );
         // 将CertificationVO传入request对象
         request.setAttribute( "certificationForm", certificationVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageCertification" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final CertificationService certificationService = ( CertificationService ) getService( "certificationService" );
            // 主键获取需解码
            final String certificationId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "certificationId" ), "UTF-8" ) );
            // 获取CertificationVO对象
            final CertificationVO certificationVO = certificationService.getCertificationVOByCertificationId( certificationId );
            // 装载界面传值
            certificationVO.update( ( CertificationVO ) form );
            // 获取登录用户
            certificationVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            certificationService.updateCertification( certificationVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            // 初始化常量持久对象
            constantsInit( "initCertification", getAccountId( request, response ) );

            insertlog( request, certificationVO, Operate.MODIFY, certificationVO.getCertificationId(), null );
         }
         // 清空Form
         ( ( CertificationVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final CertificationService certificationService = ( CertificationService ) getService( "certificationService" );
         // 获得Action Form
         final CertificationVO certificationVO = ( CertificationVO ) form;
         // 存在选中的ID
         if ( certificationVO.getSelectedIds() != null && !certificationVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : certificationVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               certificationVO.setCertificationId( selectedId );
               certificationVO.setAccountId( getAccountId( request, response ) );
               certificationVO.setModifyBy( getUserId( request, response ) );
               certificationService.deleteCertification( certificationVO );
            }

            // 初始化常量持久对象
            constantsInit( "initCertification", getAccountId( request, response ) );
            insertlog( request, certificationVO, Operate.DELETE, null, certificationVO.getSelectedIds() );
         }
         // 清除Selected IDs和子Action
         certificationVO.setSelectedIds( "" );
         certificationVO.setSubAction( "" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化CertificationService Service
         final CertificationService certificationService = ( CertificationService ) getService( "certificationService" );

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( certificationService.getCertificationBaseViewsByAccountId( getAccountId( request, response ) ) );
         // Send to client
         out.println( array.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "" );
   }
}
