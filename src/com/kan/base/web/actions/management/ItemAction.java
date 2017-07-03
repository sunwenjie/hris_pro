package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ItemService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ItemAction extends BaseAction
{
   public final static String accessAction = "HRO_MGT_ITEM";

   /**
    * Get Object Json
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2014-05-21
   public ActionForward get_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡItemId
         final String itemId = request.getParameter( "itemId" );

         // ��ȡItemVO
         final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( itemId );

         // ��ʼ�� JSONObject
         final JSONObject jsonObject = JSONObject.fromObject( itemVO );

         // Send to front
         out.println( jsonObject.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "" );
   }

   public ActionForward list_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ��ĿType 0�����У�1���������籣
         final String type = request.getParameter( "type" );
         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         // ��ȡItemVO List
         final List< ItemVO > itemVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOsByType( type );

         if ( itemVOs != null && itemVOs.size() > 0 )
         {
            for ( ItemVO itemVO : itemVOs )
            {
               JSONObject jsonObject = new JSONObject();
               jsonObject.put( "id", itemVO.getItemId() );
               jsonObject.put( "name", itemVO.getNameZH() + " - " + itemVO.getNameEN() );
               jsonObject.put( "accountId", itemVO.getAccountId() );
               array.add( jsonObject );
            }
         }

         // Send to client
         out.println( array.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "" );
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final ItemService itemService = ( ItemService ) getService( "itemService" );
         // ���Action Form
         final ItemVO itemVO = ( ItemVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         itemVO.setAccountId( getAccountId( request, response ) );
         // ����ɾ������
         if ( itemVO.getSubAction() != null && itemVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( itemVO );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder itemHolder = new PagedListHolder();
         // ���뵱ǰҳ
         itemHolder.setPage( page );
         // ���뵱ǰֵ����
         itemHolder.setObject( itemVO );
         // ����ҳ���¼����
         itemHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         itemService.getItemVOsByCondition( itemHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( itemHolder, request );
         // Holder��д��Request����
         request.setAttribute( "itemHolder", itemHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            request.setAttribute( "accountId", getAccountId( request, null ) );
            return mapping.findForward( "listItemTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listItem" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( ItemVO ) form ).setStatus( ItemVO.TRUE );
      ( ( ItemVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( ItemVO ) form ).setBillRatePersonal( "0" );
      ( ( ItemVO ) form ).setBillRateCompany( "0" );
      ( ( ItemVO ) form ).setCostRatePersonal( "0" );
      ( ( ItemVO ) form ).setCostRateCompany( "0" );
      // ��ת���½�����  
      return mapping.findForward( "manageItem" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ItemService itemService = ( ItemService ) getService( "itemService" );
            // ��õ�ǰFORM
            final ItemVO itemVO = ( ItemVO ) form;
            itemVO.setCreateBy( getUserId( request, response ) );
            itemVO.setModifyBy( getUserId( request, response ) );
            itemVO.setAccountId( getAccountId( request, response ) );
            int returnVal = itemService.insertItem( itemVO );

            // ��ʼ�������־ö���
            constantsInit( "initItem", getAccountId( request, response ) );

            if ( returnVal > 1 )
            {
               constantsInit( "initTable", getAccountId( request, response ) );
               constantsInit( "initListHeader", getAccountId( request, response ) );
               constantsInit( "initImportHeader", getAccountId( request, response ) );
            }

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, itemVO, Operate.ADD, itemVO.getItemId(), null );
         }

         // ���Form
         ( ( ItemVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final ItemService itemService = ( ItemService ) getService( "itemService" );
         // ������ȡ�����
         final String itemId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // ���ItemVO����
         final ItemVO itemVO = itemService.getItemVOByItemId( itemId );
         // ����Add��Update
         itemVO.setSubAction( VIEW_OBJECT );
         // ˢ�¹��ʻ�
         itemVO.reset( null, request );
         // ��ItemVO����request����
         request.setAttribute( "itemForm", itemVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageItem" );
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ItemService itemService = ( ItemService ) getService( "itemService" );

            // ������ȡ�����
            final String itemId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
            // ��ȡItemVO����
            final ItemVO itemVO = itemService.getItemVOByItemId( itemId );
            // װ�ؽ��洫ֵ
            itemVO.update( ( ItemVO ) form );
            itemVO.setIsCascade( ( ( ItemVO ) form ).getIsCascade() );
            // ��ȡ��¼�û�
            itemVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            int returnVal = itemService.updateItem( itemVO );

            // ��ʼ�������־ö���
            constantsInit( "initItem", getAccountId( request, response ) );

            if ( returnVal > 1 )
            {
               constantsInit( "initTable", getAccountId( request, response ) );
               constantsInit( "initListHeader", getAccountId( request, response ) );
               constantsInit( "initImportHeader", getAccountId( request, response ) );
            }

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, itemVO, Operate.MODIFY, itemVO.getItemId(), null );
         }
         // ���Form
         ( ( ItemVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ItemService itemService = ( ItemService ) getService( "itemService" );
         // ���Action Form
         final ItemVO itemVO = ( ItemVO ) form;

         // ����ѡ�е�ID
         if ( itemVO.getSelectedIds() != null && !itemVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : itemVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               itemVO.setItemId( selectedId );
               itemVO.setAccountId( getAccountId( request, response ) );
               itemVO.setModifyBy( getUserId( request, response ) );
               itemService.deleteItem( itemVO );
            }

            insertlog( request, itemVO, Operate.DELETE, null, itemVO.getSelectedIds() );
         }

         // ��ʼ�������־ö���
         constantsInit( "initItem", getAccountId( request, response ) );

         // ���Selected IDs����Action
         itemVO.setSelectedIds( "" );
         itemVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final StringBuffer sb = new StringBuffer();
         final boolean isZH = "zh".equalsIgnoreCase( request.getLocale().getLanguage() ) ? true : false;
         final String id = request.getParameter( "id" );
         final String itemId = request.getParameter( "itemId" );
         List< ItemVO > itemVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).ITEM_VO;
         String selected = "";
         for ( ItemVO itemVO : itemVOs )
         {
            if ( KANUtil.filterEmpty( id ) != null && KANUtil.filterEmpty( itemId ) != null && itemId.equals( itemVO.getItemId() ) )
            {
               selected = "selected";
            }
            final String itemName = isZH ? itemVO.getNameZH() : itemVO.getNameEN();
            sb.append( "<option " + selected + " id='option_itemId_" + itemVO.getItemId() + "' value='" + itemVO.getItemId() + "'>" + itemName + "</option>" );
         }
         out.print( sb.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "" );
   }

   /**
    * ��֤Ԫ�����Ƿ����
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward checkNameExist( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final String _name = request.getParameter( "name" );
         final String name = KANUtil.filterEmpty( _name ) == null ? "" : URLDecoder.decode( URLDecoder.decode( _name, "UTF-8" ), "UTF-8" );
         List< ItemVO > itemVOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).ITEM_VO;
         boolean exist = false;
         if ( KANUtil.filterEmpty( name.trim() ) != null )
         {
            for ( ItemVO item : itemVOs )
            {
               if ( name.equalsIgnoreCase( item.getNameZH().trim() ) || name.equalsIgnoreCase( item.getNameEN().trim() ) )
               {
                  exist = true;
                  break;
               }
            }
         }
         out.print( exist );
         // Send to front
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "" );
   }
}
