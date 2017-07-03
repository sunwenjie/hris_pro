package com.kan.base.web.actions.define;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.BankTemplateDTO;
import com.kan.base.domain.define.BankTemplateDetailVO;
import com.kan.base.domain.define.BankTemplateHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.BankTemplateHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class BankTemplateHeaderAction extends BaseAction
{
   public static final String accessAction = "HRO_SALARY_TEMPLATE";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 获得Action Form
         final BankTemplateHeaderVO bankTemplateHeaderVO = ( BankTemplateHeaderVO ) form;

         // 如果子Action是删除
         if ( bankTemplateHeaderVO.getSubAction() != null && bankTemplateHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( bankTemplateHeaderVO );
         }

         // 初始化Service接口
         final BankTemplateHeaderService bankTemplateHeaderService = ( BankTemplateHeaderService ) getService( "bankTemplateHeaderService" );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder bankTemplateHeaderHolder = new PagedListHolder();
         // 传入当前页
         bankTemplateHeaderHolder.setPage( page );
         // 传入当前值对象
         bankTemplateHeaderHolder.setObject( bankTemplateHeaderVO );
         // 设置页面记录条数
         bankTemplateHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         bankTemplateHeaderService.getBankTemplateHeaderVOsByCondition( bankTemplateHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( bankTemplateHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "bankTemplateHeaderHolder", bankTemplateHeaderHolder );

         // Ajax调用，直接传值给table jsp页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listBankTemplateHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listBankTemplateHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 获取当前accountId - client下BankTemplate Mapping形式
      final List< MappingVO > bankTemplateMappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getBankTemplate( getLocale( request ).getLanguage(), KANUtil.filterEmpty( getCorpId( request, response ) ) );

      // 如果bankId已经存在，则移除。
      if ( ( ( BankTemplateHeaderVO ) form ).getBanks() != null && ( ( BankTemplateHeaderVO ) form ).getBanks().size() > 0 && bankTemplateMappingVOs != null
            && bankTemplateMappingVOs.size() > 0 )
      {
         for ( int i = ( ( BankTemplateHeaderVO ) form ).getBanks().size() - 1; i > 0; i-- )
         {
            for ( MappingVO bankTemplateMappingVO : bankTemplateMappingVOs )
            {
               final BankTemplateDTO bankTemplateDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getBankTemplateDTOByTemplateHeaderId( bankTemplateMappingVO.getMappingId() );

               if ( bankTemplateDTO != null && bankTemplateDTO.getBankTemplateHeaderVO() != null && bankTemplateDTO.getBankTemplateHeaderVO().getBankId() != null
                     && ( ( BankTemplateHeaderVO ) form ).getBanks().get( i ).getMappingId().equals( bankTemplateDTO.getBankTemplateHeaderVO().getBankId() ) )
               {
                  ( ( BankTemplateHeaderVO ) form ).getBanks().remove( i );
                  break;
               }
            }
         }
      }

      // 设置初始化字段
      ( ( BankTemplateHeaderVO ) form ).setStatus( BankTemplateHeaderVO.TRUE );
      ( ( BankTemplateHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageBankTemplateHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化BankTemplateDetailVO
         final BankTemplateDetailVO bankTemplateDetailVO = new BankTemplateDetailVO();

         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final BankTemplateHeaderService bankTemplateHeaderService = ( BankTemplateHeaderService ) getService( "bankTemplateHeaderService" );

            // 获得当前FORM
            final BankTemplateHeaderVO bankTemplateHeaderVO = ( BankTemplateHeaderVO ) form;
            // 获取登录用户及账户
            bankTemplateHeaderVO.setCreateBy( getUserId( request, response ) );
            bankTemplateHeaderVO.setModifyBy( getUserId( request, response ) );
            bankTemplateHeaderVO.setAccountId( getAccountId( request, response ) );

            // 添加BankTemplateHeaderVO
            bankTemplateHeaderService.insertBankTemplateHeader( bankTemplateHeaderVO );

            bankTemplateDetailVO.setTemplateHeaderId( bankTemplateHeaderVO.getTemplateHeaderId() );

            // 重新加载常量中的BankTemplateHeader
            constantsInit( "initBankTemplateHeader", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, bankTemplateHeaderVO, Operate.ADD, bankTemplateHeaderVO.getTemplateHeaderId(), null );
         }
         else
         {
            // 清空Form
            ( ( BankTemplateHeaderVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
         // 跳转List BankTemplateDetail界面
         return new BankTemplateDetailAction().list_object( mapping, bankTemplateDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
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
            final BankTemplateHeaderService bankTemplateHeaderService = ( BankTemplateHeaderService ) getService( "bankTemplateHeaderService" );

            // 获得主键
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // 获得BankTemplateHeaderVO对象
            final BankTemplateHeaderVO bankTemplateHeaderVO = bankTemplateHeaderService.getBankTemplateHeaderVOByTemplateHeaderId( headerId );

            // 装载界面传值
            bankTemplateHeaderVO.update( ( BankTemplateHeaderVO ) form );
            // 获取登录用户
            bankTemplateHeaderVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            bankTemplateHeaderService.updateBankTemplateHeader( bankTemplateHeaderVO );

            // 重新加载常量中的BankTemplateHeader
            constantsInit( "initBankTemplateHeader", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, bankTemplateHeaderVO, Operate.MODIFY, bankTemplateHeaderVO.getTemplateHeaderId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转List BankTemplateDetail界面
      return new BankTemplateDetailAction().list_object( mapping, new BankTemplateDetailVO(), request, response );
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
         final BankTemplateHeaderService bankTemplateHeaderService = ( BankTemplateHeaderService ) getService( "bankTemplateHeaderService" );

         // 获得Action Form
         BankTemplateHeaderVO bankTemplateHeaderVO = ( BankTemplateHeaderVO ) form;
         // 存在选中的ID
         if ( KANUtil.filterEmpty( bankTemplateHeaderVO.getSelectedIds() ) != null )
         {
            insertlog( request, bankTemplateHeaderVO, Operate.DELETE, null, bankTemplateHeaderVO.getSelectedIds() );
            // 分割
            for ( String selectedId : bankTemplateHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获得删除对象
               bankTemplateHeaderVO = bankTemplateHeaderService.getBankTemplateHeaderVOByTemplateHeaderId( selectedId );
               bankTemplateHeaderVO.setModifyBy( getUserId( request, response ) );
               bankTemplateHeaderVO.setModifyDate( new Date() );
               bankTemplateHeaderService.deleteBankTemplateHeader( bankTemplateHeaderVO );
            }
         }

         // 重新加载常量中的BankTemplateHeader
         constantsInit( "initBankTemplateHeader", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         ( ( BankTemplateHeaderVO ) form ).setSelectedIds( "" );
         ( ( BankTemplateHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}
