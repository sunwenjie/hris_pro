package com.kan.hro.web.actions.biz.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.finance.SystemInvoiceBatchVO;
import com.kan.hro.domain.biz.finance.SystemInvoiceHeaderVO;
import com.kan.hro.service.inf.biz.finance.SystemInvoiceBatchService;
import com.kan.hro.service.inf.biz.finance.SystemInvoiceHeaderService;

public class SystemInvoiceHeaderAction extends BaseAction
{
   /**
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final SystemInvoiceBatchService systemInvoiceBatchService = ( SystemInvoiceBatchService ) getService( "systemInvoiceBatchService" );
         final SystemInvoiceHeaderService systemInvoiceHeaderService = ( SystemInvoiceHeaderService ) getService( "systemInvoiceHeaderService" );

         // 获得当前主键（批次信息）
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 获得当前主键对象
         final SystemInvoiceBatchVO  systemInvoiceBatchVO =systemInvoiceBatchService.getInvoiceBatchVOByBatchId( batchId );

         // 刷新VO对象，初始化对象列表及国际化
         systemInvoiceBatchVO.reset( null, request );
         request.setAttribute( "batchForm", systemInvoiceBatchVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder headerListHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         
         final String pageFlag = request.getParameter( "pageFlag" );
         // 传入当前页
         headerListHolder.setPage( page );
         // 初始化查询对象
         SystemInvoiceHeaderVO systemInvoiceHeaderVO = ( SystemInvoiceHeaderVO ) form;
         
         if(ajax!=null&&ajax.equals( "true" )){
            decodedObject( systemInvoiceHeaderVO );
         }
         // 设置相关属性值
         systemInvoiceHeaderVO.setBatchId( systemInvoiceBatchVO.getBatchId() );
         systemInvoiceHeaderVO.setCorpId( getCorpId( request, response ) );
         systemInvoiceHeaderVO.setAccountId( getAccountId( request, response ) );
         // 传入排序相关字段
         systemInvoiceHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
         systemInvoiceHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );
         
         // 默认按 BatchId排序
         if ( systemInvoiceHeaderVO.getSortColumn() == null || systemInvoiceHeaderVO.getSortColumn().trim().equals( "" ) )
         {
            systemInvoiceHeaderVO.setSortColumn( "invoiceId" );
         }

         // 传入当前值对象
         headerListHolder.setObject( systemInvoiceHeaderVO );
         // 设置页面记录条数
         headerListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         systemInvoiceHeaderService.getInvoiceHeaderVOsByCondition( headerListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( headerListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "headerListHolder", headerListHolder );
         
         request.setAttribute( "pageFlag", pageFlag );
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listHeader" );
   }
   
   /**
    *  拆分列表
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_objectByHeaderId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final SystemInvoiceHeaderService systemInvoiceHeaderService = ( SystemInvoiceHeaderService ) getService( "systemInvoiceHeaderService" );

         // 获得当前主键
         final String invoiceId = KANUtil.decodeStringFromAjax( request.getParameter( "invoiceId" ) );
         
         SystemInvoiceHeaderVO  systemInvoiceHeaderVO=new SystemInvoiceHeaderVO();
         systemInvoiceHeaderVO.setInvoiceId( invoiceId );
         systemInvoiceHeaderVO.setCorpId( getCorpId( request, response ) );
         systemInvoiceHeaderVO.setAccountId( getAccountId( request, response ) );
         systemInvoiceHeaderVO.setExtendInvoiceId( invoiceId );
         // 获得当前主键对象
         final SystemInvoiceHeaderVO  invoiceHeaderVO =systemInvoiceHeaderService.getSystemInvoiceHeaderByInvoiceId( systemInvoiceHeaderVO );
         
         request.setAttribute( "headerForm", invoiceHeaderVO );
         
         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            invoiceHeaderVO.setCorpId( getCorpId( request, response ) );
            // 发送帐套列表
            passClientOrders( request, response );
         }
         
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder headerListHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
        
         // 传入当前页
         headerListHolder.setPage( page );
         // 初始化查询对象
         SystemInvoiceHeaderVO systemInvoiceHeader = new SystemInvoiceHeaderVO();
         
         if(ajax!=null&&ajax.equals( "true" )){
            decodedObject( systemInvoiceHeader );
         }
         
         this.saveToken( request );
         // 设置相关属性值
         systemInvoiceHeader.setCorpId( getCorpId( request, response ) );
         systemInvoiceHeader.setAccountId( getAccountId( request, response ) );
         systemInvoiceHeader.setParentInvoiceId( invoiceHeaderVO.getInvoiceId() );
         systemInvoiceHeader.setExtendInvoiceId( invoiceHeaderVO.getInvoiceId() );
         systemInvoiceHeader.setBatchId( invoiceHeaderVO.getBatchId() );
         // 传入排序相关字段
         systemInvoiceHeader.setSortColumn( request.getParameter( "sortColumn" ) );
         systemInvoiceHeader.setSortOrder( request.getParameter( "sortOrder" ) );

         // 默认按 BatchId排序
         if ( systemInvoiceHeader.getSortColumn() == null || systemInvoiceHeader.getSortColumn().trim().equals( "" ) )
         {
            systemInvoiceHeader.setSortColumn( "invoiceId" );
         }

         // 传入当前值对象
         headerListHolder.setObject( systemInvoiceHeader );
         // 设置页面记录条数
         headerListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         systemInvoiceHeaderService.getSubSystemInvoiceHeaderByHeaderId( headerListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( headerListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "headerListHolder", headerListHolder );
         
         request.setAttribute( "pageFlag", "Split" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      //跳到拆分界面
      return mapping.findForward( "listSubInvoice" );
   }
   
   /**
    * 拆分
    */
   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      final SystemInvoiceHeaderService systemInvoiceHeaderService = ( SystemInvoiceHeaderService ) getService( "systemInvoiceHeaderService" );

      // 获得当前主键
      final String invoiceId =KANUtil.decodeStringFromAjax(request.getParameter( "invoiceId" )) ;
      
      SystemInvoiceHeaderVO  invoiceHeaderVO=new SystemInvoiceHeaderVO();
      invoiceHeaderVO.setInvoiceId( invoiceId );
      invoiceHeaderVO.setCorpId( getCorpId( request, response ) );
      invoiceHeaderVO.setAccountId( getAccountId( request, response ) );
      invoiceHeaderVO.setExtendInvoiceId( invoiceId );
      // 获得当前主发票
      final SystemInvoiceHeaderVO  systemInvoiceHeader =systemInvoiceHeaderService.getSystemInvoiceHeaderByInvoiceId( invoiceHeaderVO );
      // 避免重复提交
      if ( this.isTokenValid( request ,true) && systemInvoiceHeader!=null)
      {
         // 获得ActionForm
         SystemInvoiceHeaderVO systemInvoiceHeaderVO = ( SystemInvoiceHeaderVO ) form;
         systemInvoiceHeaderVO.setAccountId( getAccountId( request, response ) );
         systemInvoiceHeaderVO.setExtendInvoiceId( systemInvoiceHeader.getInvoiceId());
         systemInvoiceHeaderVO.setParentInvoiceId( systemInvoiceHeader.getInvoiceId());
         systemInvoiceHeaderVO.setInvoiceId( systemInvoiceHeaderService.getMaxInvoiceId()+1+"" );
         if(systemInvoiceHeaderVO.getBranch()==null)
         systemInvoiceHeaderVO.setBranch( systemInvoiceHeader.getBranch() );
         if(systemInvoiceHeaderVO.getOwner()==null)
         systemInvoiceHeaderVO.setOwner( systemInvoiceHeader.getOwner() );
         systemInvoiceHeaderVO.setBatchId( systemInvoiceHeader.getBatchId() );
         systemInvoiceHeaderVO.setCreateBy( getUserId( request, response ) );
         systemInvoiceHeaderVO.setModifyBy( getUserId( request, response ) );
         // 新建对象
         systemInvoiceHeaderService.insertInvoiceHeader( systemInvoiceHeaderVO );
         
         //创建冲账记录
         SystemInvoiceHeaderVO strikeInfo=systemInvoiceHeaderVO;
         strikeInfo.setBillAmountCompany( "-"+systemInvoiceHeaderVO.getBillAmountCompany() );
         strikeInfo.setCostAmountCompany("-"+ systemInvoiceHeaderVO.getCostAmountCompany() );
         strikeInfo.setTaxAmount( "-"+systemInvoiceHeaderVO.getTaxAmount() );
         strikeInfo.setInvoiceId( systemInvoiceHeaderService.getMaxInvoiceId()+1+"" );
         strikeInfo.setParentInvoiceId(null);
         //新增冲账记录
         systemInvoiceHeaderService.insertInvoiceHeader( strikeInfo );
         // 返回添加成功标记
         success( request );
      }
      // 清空Form条件
      ( ( SystemInvoiceHeaderVO ) form ).reset();
      return list_objectByHeaderId( mapping, form, request, response );
   }

   /**
    * 合并发票列表
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward combineInvoice( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final SystemInvoiceHeaderService systemInvoiceHeaderService = ( SystemInvoiceHeaderService ) getService( "systemInvoiceHeaderService" );

         final PagedListHolder headerListHolder = new PagedListHolder();
         String invoiceId="";
         // 获得当前主键
         final String invoiceIdArray =request.getParameter( "invoiceId" ) ;
         for ( String string : invoiceIdArray.split( "," ) )
         {
            if(invoiceId!=""&&string!=""){
               invoiceId=invoiceId+","+ KANUtil.decodeStringFromAjax(string);
            }else{
               invoiceId= KANUtil.decodeStringFromAjax(string);
            }
         }
        
         // 初始化查询对象
         SystemInvoiceHeaderVO systemInvoiceHeader = new SystemInvoiceHeaderVO();
         // 设置相关属性值
         systemInvoiceHeader.setInvoiceId( invoiceId );
         systemInvoiceHeader.setCorpId( getCorpId( request, response ) );
         systemInvoiceHeader.setAccountId( getAccountId( request, response ) );
         // 传入排序相关字段
         systemInvoiceHeader.setSortColumn( request.getParameter( "sortColumn" ) );
         systemInvoiceHeader.setSortOrder( request.getParameter( "sortOrder" ) );

         // 默认按 BatchId排序
         if ( systemInvoiceHeader.getSortColumn() == null || systemInvoiceHeader.getSortColumn().trim().equals( "" ) )
         {
            systemInvoiceHeader.setSortColumn( "invoiceId" );
         }
         systemInvoiceHeader.reset( null, request );
         
         SystemInvoiceHeaderVO systemInvoiceHeaderVO=systemInvoiceHeaderService.getSystemInvoiceHeaderById(systemInvoiceHeader);
         if(systemInvoiceHeaderVO!=null){
            // 刷新VO对象，初始化对象列表及国际化
            systemInvoiceHeaderVO.reset( null, request );
            request.setAttribute( "headerForm", systemInvoiceHeaderVO );
         }
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         
         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            systemInvoiceHeader.setCorpId( getCorpId( request, response ) );
            // 发送帐套列表
            passClientOrders( request, response );
         }
         
         // 传入当前页
         headerListHolder.setPage( page );
         // 传入当前值对象
         headerListHolder.setObject( systemInvoiceHeader );
         // 设置页面记录条数
         headerListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         systemInvoiceHeaderService.getComSystemInvoiceHeaderById( headerListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( headerListHolder, request );
         
         this.saveToken( request );
         
         // Holder需写入Request对象
         request.setAttribute( "headerListHolder", headerListHolder );
         
         request.setAttribute( "pageFlag", "Merge");
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listMagerHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      //跳到合并页面
      return mapping.findForward( "listCompound" );
   }
   
   
   /**
    * 合并系统发票
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_objectBycombine( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      final SystemInvoiceHeaderService systemInvoiceHeaderService = ( SystemInvoiceHeaderService ) getService( "systemInvoiceHeaderService" );

      // 避免重复提交
      if ( this.isTokenValid( request, true ))
      {
         // 获得ActionForm
         final SystemInvoiceHeaderVO systemInvoiceHeaderVO = ( SystemInvoiceHeaderVO ) form;
         //转码
         decodedObject( systemInvoiceHeaderVO );
         systemInvoiceHeaderVO.setAccountId( getAccountId( request, response ) );
         systemInvoiceHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( systemInvoiceHeaderVO.getBatchId()) );
         if(systemInvoiceHeaderVO.getBranch()==null)
            systemInvoiceHeaderVO.setBranch( "0" );
         if(systemInvoiceHeaderVO.getOwner()==null)
            systemInvoiceHeaderVO.setOwner( "0");
         systemInvoiceHeaderVO.setInvoiceId( systemInvoiceHeaderService.getMaxInvoiceId()+1+"" );
         systemInvoiceHeaderVO.setExtendInvoiceId( systemInvoiceHeaderService.getMaxInvoiceId()+1+"" );
         systemInvoiceHeaderVO.setCreateBy( getUserId( request, response ) );
         systemInvoiceHeaderVO.setModifyBy( getUserId( request, response ) );
         // 新建对象
         systemInvoiceHeaderService.insertInvoiceHeader( systemInvoiceHeaderVO );
            
         //创建冲账记录
         SystemInvoiceHeaderVO strikeInfo=systemInvoiceHeaderVO;
         strikeInfo.setBillAmountCompany( "-"+systemInvoiceHeaderVO.getBillAmountCompany() );
         strikeInfo.setCostAmountCompany("-"+ systemInvoiceHeaderVO.getCostAmountCompany() );
         strikeInfo.setInvoiceId( systemInvoiceHeaderService.getMaxInvoiceId()+1+"" );
         strikeInfo.setExtendInvoiceId( systemInvoiceHeaderService.getMaxInvoiceId()+1+"" );
         strikeInfo.setTaxAmount("-"+ systemInvoiceHeaderVO.getTaxAmount() );
         strikeInfo.setParentInvoiceId( null);
         //新增冲账记录
         systemInvoiceHeaderService.insertInvoiceHeader( strikeInfo );
         // 返回添加成功标记
         success( request );
         // 清空Form条件
         ( ( SystemInvoiceHeaderVO ) form ).reset();
      }  
      request.setAttribute( "pageFlag", "Merge" );
      return  list_object( mapping, form, request, response );
   }
   
   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }
   
   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
   }

}
