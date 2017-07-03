package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ContractTypeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ContractTypeService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;

public class ContractTypeAction extends BaseAction
{

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
         final ContractTypeService contractTypeService = ( ContractTypeService ) getService( "contractTypeService" );
         // 初始化Service接口
         final ContractTypeVO contractTypeVO = ( ContractTypeVO ) form;
         // 需要设置当前用户AccountId
         contractTypeVO.setAccountId( getAccountId( request, response ) );

         // 调用删除方法
         if ( contractTypeVO.getSubAction() != null && contractTypeVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder contractTypeHolder = new PagedListHolder();
         // 传入当前页
         contractTypeHolder.setPage( page );
         // 传入当前值对象
         contractTypeHolder.setObject( contractTypeVO );
         // 设置页面记录条数
         contractTypeHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页   
         contractTypeService.getContractTypeVOsByCondition( contractTypeHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( contractTypeHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "contractTypeHolder", contractTypeHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回ContractType JSP
            return mapping.findForward( "listContractTypeTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listContractType" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( ContractTypeVO ) form ).setStatus( ContractTypeVO.TRUE );
      ( ( ContractTypeVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageContractType" );
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
            final ContractTypeService contractTypeService = ( ContractTypeService ) getService( "contractTypeService" );

            // 获得当前FORM
            final ContractTypeVO contractTypeVO = ( ContractTypeVO ) form;
            contractTypeVO.setCreateBy( getUserId( request, response ) );
            contractTypeVO.setModifyBy( getUserId( request, response ) );
            contractTypeVO.setAccountId( getAccountId( request, response ) );
            contractTypeService.insertContractType( contractTypeVO );

            // 初始化常量持久对象
            constantsInit( "initContractType", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request );
         }

         // 清空Action Form
         ( ( ContractTypeVO ) form ).reset();
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
         // 添加页面Token
         this.saveToken( request );
         // 初始化 Service接口
         final ContractTypeService contractTypeService = ( ContractTypeService ) getService( "contractTypeService" );
         // 主键获取需解码
         final String typeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "typeId" ), "UTF-8" ) );
         // 获得ContractTypeVO对象
         final ContractTypeVO contractTypeVO = contractTypeService.getContractTypeVOByTypeId( typeId );
         contractTypeVO.reset( null, request );
         // 区分Add和Update
         contractTypeVO.setSubAction( VIEW_OBJECT );
         // 将ContractTypeVO传入Request对象
         request.setAttribute( "contractTypeForm", contractTypeVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageContractType" );
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
            // 初始化Service接口
            final ContractTypeService contractTypeService = ( ContractTypeService ) getService( "contractTypeService" );

            // 获得当前主键
            final String typeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "typeId" ), "UTF-8" ) );
            // 获取要修改的对象
            final ContractTypeVO contractTypeVO = contractTypeService.getContractTypeVOByTypeId( typeId );
            // 装载界面传值
            contractTypeVO.update( ( ContractTypeVO ) form );
            // 获取登录用户
            contractTypeVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            contractTypeService.updateContractType( contractTypeVO );
            // 初始化常量持久对象
            constantsInit( "initContractType", getAccountId( request, response ) );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // 清空Action Form
         ( ( ContractTypeVO ) form ).reset();
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
         final ContractTypeService contractTypeService = ( ContractTypeService ) getService( "contractTypeService" );

         // 获得Action Form
         ContractTypeVO contractTypeVO = ( ContractTypeVO ) form;
         // 存在选中的ID
         if ( contractTypeVO.getSelectedIds() != null && !contractTypeVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : contractTypeVO.getSelectedIds().split( "," ) )
            {
               contractTypeVO.setTypeId( selectedId );
               contractTypeVO.setModifyBy( getUserId( request, response ) );
               // 调用删除接口
               contractTypeService.deleteContractType( contractTypeVO );
            }

            // 初始化常量持久对象
            constantsInit( "initContractType", getAccountId( request, response ) );
         }

         // 清除Selected IDs和子Action
         contractTypeVO.setSelectedIds( "" );
         contractTypeVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
