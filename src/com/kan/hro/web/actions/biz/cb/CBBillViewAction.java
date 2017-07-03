package com.kan.hro.web.actions.biz.cb;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.cb.CBBillVO;
import com.kan.hro.service.inf.biz.cb.CBBillService;

public class CBBillViewAction extends BaseAction
{

   // 当前Action对应的Access Action
   public static final String accessAction = "HRO_CB_BILL";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         /**
          * 获取批次信息和服务协议信息
          */
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final CBBillService cbBillService = ( CBBillService ) getService( "cbBillService" );

         // 初始化CBBillVO
         CBBillVO cbBillVO = ( CBBillVO ) form;
         cbBillVO.setAccountId( getAccountId( request, response ) );

         /**
          * 获取商保方案列表
          */
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder cbBillHolder = new PagedListHolder();
         // 传入当前页
         cbBillHolder.setPage( page );

         // 设置排序
         cbBillVO.setSortColumn( request.getParameter( "sortColumn" ) );
         cbBillVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            cbBillVO.setCorpId( getCorpId( request, response ) );
            passClientOrders( request, response );
         }
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            cbBillVO.setClientId( BaseAction.getClientId( request, response ) );
            cbBillVO.setRole( KANConstants.ROLE_CLIENT );
         }

         // 如果没有指定排序则默认按 商保方案流水号排序
         if ( cbBillVO.getSortColumn() == null || cbBillVO.getSortColumn().trim().equals( "" ) )
         {
            cbBillVO.setSortColumn( "employeeCBId,monthly" );
            cbBillVO.setSortOrder( "" );
         }
         // 国际化
         cbBillVO.reset( null, request );
         if ( KANUtil.filterEmpty( cbBillVO.getCbStatusArray() ) != null && cbBillVO.getCbStatusArray().length > 0 )
         {
            cbBillVO.setCbStatus( KANUtil.toJasonArray( cbBillVO.getCbStatusArray(), "," ).replace( "{", "" ).replace( "}", "" ) );
         }
         cbBillVO.getCbStatuses().remove( 0 );
         // 传入当前值对象
         cbBillHolder.setObject( cbBillVO );
         // 设置页面记录条数
         cbBillHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         cbBillService.getCBBillVOsByCondition( cbBillHolder, true );
         refreshHolder( cbBillHolder, request );

         List< Object > list = cbBillHolder.getSource();
         List< String > itemNameList = new ArrayList< String >();
         List< MappingVO > itemNameMappings = getItemNameMappingVOs( cbBillService, list, getLocale( request ).getLanguage() );
         if ( list.size() > 0 )
         {
            //setDetailItem( cbBillService, itemNameList, list, request );
            setDetailItemValues( cbBillService, itemNameMappings, list, request );
         }
         request.setAttribute( "cbBillDetailListHeader", itemNameList );
         request.setAttribute( "itemNameMappings", itemNameMappings );

         // Holder需写入Request对象
         request.setAttribute( "cbBillListHolder", cbBillHolder );

         // 是否显示导出按钮
         showExportButton( mapping, form, request, response );
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listCBBillTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "listCBBill" );
   }

   private List< MappingVO > getItemNameMappingVOs( final CBBillService cbBillService, final List< Object > list, final String lang ) throws KANException
   {
      String existItemIdStrs = "";
      final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
      for ( int i = 0; i < list.size(); i++ )
      {
         final CBBillVO cbBillVO = ( CBBillVO ) list.get( i );
         final List< Object > detailVOs = cbBillService.getCBBillDetailByHeaderId( cbBillVO.getHeaderId() );
         for ( Object detailVO : detailVOs )
         {
            if ( !existItemIdStrs.contains( ( ( CBBillVO ) detailVO ).getItemId() ) )
            {
               final ItemVO itemVO = KANConstants.getKANAccountConstants( cbBillVO.getAccountId() ).getItemVOByItemId( ( ( CBBillVO ) detailVO ).getItemId() );
               mappingVOs.add( new MappingVO( itemVO.getItemId(), "ZH".equalsIgnoreCase( lang ) ? itemVO.getNameZH() : itemVO.getNameEN() ) );
               existItemIdStrs += ( ( ( CBBillVO ) detailVO ).getItemId() + "##" );
            }
         }
      }
      return mappingVOs;
   }

   public ActionForward exportReport( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final CBBillService cbBillService = ( CBBillService ) getService( "cbBillService" );

         // 初始化PagedListHolder
         final PagedListHolder cbBillHolder = new PagedListHolder();

         // 初始化考勤详情查询条件
         final CBBillVO cbBillVO = ( CBBillVO ) form;
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            cbBillVO.setClientId( BaseAction.getClientId( request, response ) );
            cbBillVO.setRole( KANConstants.ROLE_CLIENT );
         }
         cbBillHolder.setObject( cbBillVO );
         cbBillService.getCBBillVOsByCondition( cbBillHolder, false );
         final List< Object > list = cbBillHolder.getSource();
         final List< MappingVO > itemNameMappings = getItemNameMappingVOs( cbBillService, list, getLocale( request ).getLanguage() );
         final List< String > itemNameList = new ArrayList< String >();
         for ( MappingVO mappingVO : itemNameMappings )
         {
            itemNameList.add( mappingVO.getMappingValue() );
         }
         if ( list.size() > 0 )
         {
            setDetailItemValues( cbBillService, itemNameMappings, list, request );
         }

         final SXSSFWorkbook workbook = cbBillService.cbBillReport( itemNameList, cbBillHolder, cbBillVO, request );
         // 初始化OutputStream
         final OutputStream os = response.getOutputStream();

         // 设置返回文件下载
         response.setContentType( "application/x-msdownload" );

         // 解决文件中文名下载问题
         response.setHeader( "Content-Disposition", "attachment;filename=" + new String( URLDecoder.decode( "商保单.xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) );

         // Excel文件写入OutputStream
         workbook.write( os );

         // 输出OutputStream
         os.flush();
         //关闭流  
         os.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );

      }
      return mapping.findForward( "" );

   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

   }

   /*private void setDetailItem( final CBBillService cbBillService, List< String > itemNameList, List< Object > list, final HttpServletRequest request ) throws KANException
   {
      List< String > headerIdList = new ArrayList< String >();
      for ( Object object : list )
      {
         headerIdList.add( ( ( CBBillVO ) object ).getHeaderId() );
      }
      List< Object > detailList = cbBillService.getCBBillDetailByHeaderIds( headerIdList );

      Map< String, String > titleNameMap = new HashMap< String, String >();
      for ( Object detail : detailList )
      {
         CBBillVO detailVO = ( CBBillVO ) detail;
         titleNameMap.put( detailVO.getItemId(), detailVO.getNameZH() );

      }
      List< String > itemIdList = new ArrayList< String >( titleNameMap.keySet() );
      Collections.sort( itemIdList );
      for ( String itemKey : itemIdList )
      {
         itemNameList.add( titleNameMap.get( itemKey ) );
      }
      for ( Object header : list )
      {
         List< Object > newList = new ArrayList< Object >();
         CBBillVO headerVO = ( CBBillVO ) header;
         for ( Object detail : detailList )
         {
            CBBillVO detailVO = ( CBBillVO ) detail;
            if ( detailVO.getHeaderId().equals( headerVO.getHeaderId() ) )
            {
               detailVO.setAccountId( BaseAction.getAccountId( request, null ) );

               newList.add( detailVO );
            }
         }

         int i = 0;
         while ( newList.size() < itemIdList.size() )
         {
            if ( i < newList.size() )
            {
               if ( !itemIdList.get( i ).equals( ( ( CBBillVO ) newList.get( i ) ).getItemId() ) )
               {
                  newList.add( i, new CBBillVO() );
               }
            }
            else
            {
               newList.add( new CBBillVO() );
            }

            i++;
         }
         headerVO.setDetailList( newList );
      }
   }*/

   // 设置科目的值
   private void setDetailItemValues( final CBBillService cbBillService, List< MappingVO > itemNameMappings, List< Object > list, final HttpServletRequest request )
         throws KANException
   {
      for ( int i = 0; i < list.size(); i++ )
      {
         final CBBillVO cbBillVO = ( CBBillVO ) list.get( i );
         final List< Object > detailList = cbBillService.getCBBillDetailByHeaderId( cbBillVO.getHeaderId() );
         final List< Object > targetDetailList = new ArrayList< Object >();

         for ( int j = 0; j < itemNameMappings.size(); j++ )
         {
            final MappingVO tempMappingVO = itemNameMappings.get( j );
            boolean flag = false;
            for ( int k = 0; k < detailList.size(); k++ )
            {
               final CBBillVO detailVO = ( CBBillVO ) detailList.get( k );
               if ( tempMappingVO.getMappingId().equals( detailVO.getItemId() ) )
               {
                  detailVO.setAccountId( cbBillVO.getAccountId() );
                  targetDetailList.add( detailVO );
                  flag = true;
                  break;
               }

            }
            // 如果没找到
            if ( !flag )
            {
               final CBBillVO tempVO = new CBBillVO();
               tempVO.setAccountId( cbBillVO.getAccountId() );
               targetDetailList.add( tempVO );
            }

         }
         cbBillVO.getDetailList().addAll( targetDetailList );
      }
   }
}
