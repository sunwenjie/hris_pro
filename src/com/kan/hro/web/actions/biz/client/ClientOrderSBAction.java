package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderSBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBService;

/**  
 * 项目名称：HRO_V1  
 * 类名称：ClientOrderSBAction  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-8-19  
 */
public class ClientOrderSBAction extends BaseAction
{
   // 当前Action对应的Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT_ORDER_SB";
   public final static String accessAction_in_house = "HRO_BIZ_CLIENT_ORDER_SB_IN_HOUSE";

   /**
    * List client Order Header
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );
         // 获得Action Form
         final ClientOrderSBVO clientOrderSBVO = ( ClientOrderSBVO ) form;
         clientOrderSBVO.setAccountId( getAccountId( request, response ) );

         // 如果子Action是删除用户列表
         if ( clientOrderSBVO.getSubAction() != null && clientOrderSBVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( clientOrderSBVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // 传入当前页
         pageListHolder.setPage( page );
         // 传入当前值对象
         pageListHolder.setObject( clientOrderSBVO );
         // 设置页面记录条数
         pageListHolder.setPageSize( listPageSize );
         // 获得SubAction
         String subAction = "";

         // 如果子SubAction不为空
         if ( clientOrderSBVO.getSubAction() != null )
         {
            subAction = clientOrderSBVO.getSubAction().trim();
         }

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         clientOrderSBService.getClientOrderSBVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // 刷新Holder，国际化传值
         refreshHolder( pageListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pageListHolder );

         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            if ( subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               return new DownloadFileAction().exportList( mapping, form, request, response );
            }
            else
            {
               // Config the response
               response.setContentType( "text/html" );
               response.setCharacterEncoding( "UTF-8" );
               // 初始化PrintWrite对象
               final PrintWriter out = response.getWriter();

               // Send to client
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_ORDER_SB" ) );
               out.flush();
               out.close();
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listClientOrderSB" );
   }

   /**
    * To object New
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 获得OrderHeaderId
      final String orderHeaderId = KANUtil.decodeString( request.getParameter( "orderHeaderId" ) );

      // TODO先从社保方案取

      // 初始化Service
      final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

      // 获取是否公司承担社保
      if ( KANUtil.filterEmpty( orderHeaderId ) != null )
      {
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );
         if ( clientOrderHeaderVO != null )
         {
            ( ( ClientOrderSBVO ) form ).setPersonalSBBurden( clientOrderHeaderVO.getPersonalSBBurden() );
         }
      }

      ( ( ClientOrderSBVO ) form ).setOrderHeaderId( orderHeaderId );
      ( ( ClientOrderSBVO ) form ).setStatus( ClientOrderSBVO.TRUE );
      ( ( ClientOrderSBVO ) form ).setSubAction( CREATE_OBJECT );

      // 国际化
      ( ( ClientOrderSBVO ) form ).reset( null, request );

      // 跳转到新建界面
      return mapping.findForward( "manageClientOrderSB" );
   }

   /**
    * To Object Modify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );

         // 获得当前主键
         String orderSBId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( orderSBId == null || orderSBId.trim().isEmpty() )
         {
            orderSBId = ( ( ClientOrderSBVO ) form ).getOrderSbId();
         }

         // 获得ClientOrderSBVO
         final ClientOrderSBVO clientOrderSBVO = clientOrderSBService.getClientOrderSBVOByClientOrderSBId( orderSBId );

         // 刷新VO对象，初始化对象列表及国际化
         clientOrderSBVO.reset( null, request );
         // 设置Sub Action
         clientOrderSBVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientOrderSBForm", clientOrderSBVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageClientOrderSB" );
   }

   /**  
    * Add Object
    *	 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );
            // 获得ActionForm
            final ClientOrderSBVO clientOrderSBVO = ( ClientOrderSBVO ) form;
            // 获取登录用户
            clientOrderSBVO.setCreateBy( getUserId( request, response ) );
            clientOrderSBVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            clientOrderSBVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // 新建对象
            clientOrderSBService.insertClientOrderSB( clientOrderSBVO );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, clientOrderSBVO, Operate.ADD, clientOrderSBVO.getOrderSbId(), null );
         }

         // 清空Form条件
         ( ( ClientOrderSBVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );
            // 获得当前主键
            final String orderSBId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final ClientOrderSBVO clientOrderSBVO = clientOrderSBService.getClientOrderSBVOByClientOrderSBId( orderSBId );
            // 获取登录用户
            clientOrderSBVO.update( ( ClientOrderSBVO ) form );
            // 保存自定义Column
            clientOrderSBVO.setRemark1( saveDefineColumns( request, accessAction ) );
            clientOrderSBVO.setModifyBy( getUserId( request, response ) );
            // 修改对象
            clientOrderSBService.updateClientOrderSB( clientOrderSBVO );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, clientOrderSBVO, Operate.MODIFY, clientOrderSBVO.getOrderSbId(), null );
         }

         // 清空Form条件
         ( ( ClientOrderSBVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // no use
   }

   /**
    * Delete Object Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-10-19
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );

         // 获取主键
         final String clientOrderSBId = KANUtil.decodeStringFromAjax( request.getParameter( "clientOrderSBId" ) );

         // 根据主键获得对应VO
         final ClientOrderSBVO clientOrderSBVO = clientOrderSBService.getClientOrderSBVOByClientOrderSBId( clientOrderSBId );
         clientOrderSBVO.setModifyBy( getUserId( request, response ) );
         clientOrderSBVO.setModifyDate( new Date() );

         // 调用删除接口
         final long rows = clientOrderSBService.deleteClientOrderSB( clientOrderSBVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, clientOrderSBVO, Operate.DELETE, clientOrderSBVO.getOrderSbId(), "ajax delete" );
         }
         else
         {
            deleteFailedAjax( out, null );
         }

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Object List
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );
         // 获得Action Form
         ClientOrderSBVO clientOrderSBVO = ( ClientOrderSBVO ) form;

         // 存在选中的ID
         if ( clientOrderSBVO.getSelectedIds() != null && !clientOrderSBVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : clientOrderSBVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // 主键转码
                  final String clientOrderSBId = KANUtil.decodeStringFromAjax( selectedId );
                  // 根据主键获得对应VO
                  final ClientOrderSBVO clientOrderSBVOForDel = clientOrderSBService.getClientOrderSBVOByClientOrderSBId( clientOrderSBId );
                  clientOrderSBVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderSBVOForDel.setModifyDate( new Date() );
                  // 调用删除接口
                  clientOrderSBService.deleteClientOrderSB( clientOrderSBVOForDel );
               }
            }

            insertlog( request, clientOrderSBVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( clientOrderSBVO.getSelectedIds() ) );
         }

         // 清除Selected IDs和子Action
         clientOrderSBVO.setSelectedIds( "" );
         clientOrderSBVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Object Json
    * 完整的下拉框
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         final String sbSolutionId = request.getParameter( "sbSolutionId" );

         // 初始化
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

         // 如果是In House登录
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            mappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, response ) ) );
         }
         // 如果是Hr Service登录
         else
         {
            mappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutions( request.getLocale().getLanguage() ) );
         }
         // 添加super的
         mappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getSocialBenefitSolutions( request.getLocale().getLanguage() ) );

         if ( mappingVOs != null && mappingVOs.size() > 0 )
         {
            // 移出合同已有其他社保
            if ( KANUtil.filterEmpty( request.getParameter( "contractId" ) ) != null )
            {
               final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );

               // 获得ContractId已有社保
               final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
               final List< Object > employeeContractSBVOs = employeeContractSBService.getEmployeeContractSBVOsByContractId( contractId );

               if ( employeeContractSBVOs != null && employeeContractSBVOs.size() > 0 )
               {
                  // 初始化已有社保
                  for ( Object object : employeeContractSBVOs )
                  {
                     EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) object;

                     // 如果社保已存在且不为当前修改项则移出
                     for ( Iterator< MappingVO > iterator = mappingVOs.iterator(); iterator.hasNext(); )
                     {
                        MappingVO mappingVO = ( MappingVO ) iterator.next();

                        // 如果
                        if ( employeeContractSBVO.getSbSolutionId().equals( mappingVO.getMappingId() ) && !employeeContractSBVO.getSbSolutionId().equals( sbSolutionId ) )
                        {
                           iterator.remove();
                        }
                     }
                  }
               }
            }

         }

         mappingVOs.add( 0, ( ( ClientOrderSBVO ) form ).getEmptyMappingVO() );
         out.println( KANUtil.getOptionHTML( mappingVOs, "sbSolutionId", sbSolutionId ) );

         // Send to client
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   // 去除已有的社保
   public ActionForward list_object_options_manage_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Service接口
         final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );

         // 获得客户订单ID
         String orderHeaderId = request.getParameter( "orderHeaderId" );

         if ( KANUtil.filterEmpty( orderHeaderId ) != null )
         {
            orderHeaderId = KANUtil.decodeStringFromAjax( orderHeaderId );
         }

         // 获得已有社保
         final List< Object > clientOrderSBVOs = clientOrderSBService.getClientOrderSBVOsByClientOrderHeaderId( orderHeaderId );

         // 添加所有社保
         final List< MappingVO > allMappingVOs = new ArrayList< MappingVO >();

         // 如果是In House登录
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            allMappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, response ) ) );
         }
         // 如果是Hr Service登录
         else
         {
            allMappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutions( request.getLocale().getLanguage() ) );
         }

         // 获取重复的。
         final List< MappingVO > existMappingVOs = new ArrayList< MappingVO >();
         ClientOrderSBVO clientOrderSBVO = null;
         for ( int i = 0; i < clientOrderSBVOs.size(); i++ )
         {
            for ( int j = 0; j < allMappingVOs.size(); j++ )
            {
               clientOrderSBVO = ( ClientOrderSBVO ) clientOrderSBVOs.get( i );
               if ( clientOrderSBVO.getSbSolutionId().equals( ( allMappingVOs.get( j ) ).getMappingId() ) )
               {
                  existMappingVOs.add( allMappingVOs.get( j ) );
               }
            }
         }

         allMappingVOs.removeAll( existMappingVOs );
         allMappingVOs.add( 0, ( ( ClientOrderSBVO ) form ).getEmptyMappingVO() );
         out.println( KANUtil.getOptionHTML( allMappingVOs, "sbSolutionId", null ) );

         // Send to client
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   // 根据结算规则ID，获取社保。
   public ActionForward list_object_options_byOrderHeaderId_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Service接口
         final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );

         // 获得服务协议ID
         String orderHeaderId = request.getParameter( "orderHeaderId" );

         // 获得已有社保
         final List< Object > clientOrderSBVOs = clientOrderSBService.getClientOrderSBVOsByClientOrderHeaderId( orderHeaderId );
         // 所有社保
         final List< MappingVO > allMappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, response ) );
         // 添加目标社保
         final List< MappingVO > targetMappingVOs = new ArrayList< MappingVO >();

         if ( clientOrderSBVOs != null && clientOrderSBVOs.size() > 0 )
         {
            // 如果结算规则中有社保，则按照结算规则来，如果没有则添加账户下所有的社保
            for ( int i = 0; i < clientOrderSBVOs.size(); i++ )
            {
               for ( MappingVO mappingVO : allMappingVOs )
               {
                  if ( mappingVO.getMappingId().equals( ( ( ClientOrderSBVO ) clientOrderSBVOs.get( i ) ).getSbSolutionId() ) )
                  {
                     targetMappingVOs.add( mappingVO );
                  }
               }

            }
         }
         else
         {
            // 添加所有社保
            targetMappingVOs.addAll( allMappingVOs );

         }

         targetMappingVOs.add( 0, ( ( ClientOrderSBVO ) form ).getEmptyMappingVO() );
         out.println( KANUtil.getOptionHTML( targetMappingVOs, "sbSolutionId", null ) );

         // Send to client
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }
}
