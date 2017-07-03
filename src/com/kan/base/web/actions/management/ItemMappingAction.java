package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ItemMappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ItemMappingService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ItemMappingAction extends BaseAction
{
   public static final String accessAction = "HRO_MGT_ITEM_MAPPING";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final ItemMappingService itemMappingService = ( ItemMappingService ) getService( "itemMappingService" );
         // ���Action Form
         final ItemMappingVO itemMappingVO = ( ItemMappingVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         itemMappingVO.setAccountId( getAccountId( request, response ) );
         // ����ɾ������
         if ( itemMappingVO.getSubAction() != null && itemMappingVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( itemMappingVO );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder itemMappingHolder = new PagedListHolder();
         // ���뵱ǰҳ
         itemMappingHolder.setPage( page );
         // ���뵱ǰֵ����
         itemMappingHolder.setObject( itemMappingVO );
         // ����ҳ���¼����
         itemMappingHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         itemMappingService.getItemMappingVOsByCondition( itemMappingHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( itemMappingHolder, request );
         // Holder��д��Request����
         request.setAttribute( "itemMappingHolder", itemMappingHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            return mapping.findForward( "listItemMappingTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listItemMapping" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( ItemMappingVO ) form ).setStatus( ItemMappingVO.TRUE );
      ( ( ItemMappingVO ) form ).setSubAction( CREATE_OBJECT );
      // ��ת���½�����  
      return mapping.findForward( "manageItemMapping" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ItemMappingService itemMappingService = ( ItemMappingService ) getService( "itemMappingService" );
            // ��õ�ǰFORM
            final ItemMappingVO itemMappingVO = ( ItemMappingVO ) form;
            itemMappingVO.setCreateBy( getUserId( request, response ) );
            itemMappingVO.setModifyBy( getUserId( request, response ) );
            itemMappingVO.setAccountId( getAccountId( request, response ) );
            itemMappingService.insertItemMapping( itemMappingVO );

            // ��ʼ�������־ö���
            constantsInit( "initItemMapping", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, itemMappingVO, Operate.ADD, itemMappingVO.getMappingId(), null );
         }

         // ���Form
         ( ( ItemMappingVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   public ActionForward add_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final String itemId = request.getParameter( "itemId" );

         final String entityId = request.getParameter( "entityId" );

         final String businessTypeId = request.getParameter( "businessTypeId" );
         // ��õ�ǰ�˻�����ItemMapping
         final List< ItemMappingVO > itemMappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).ITEM_MAPPING_VO;
         Integer flag = 1;
         if ( itemMappingVOs != null && itemMappingVOs.size() > 0 )
         {
            for ( Object itemMappingVOObject : itemMappingVOs )
            {
               if ( ( ( ItemMappingVO ) itemMappingVOObject ).getItemId().equals( itemId ) )
               {
                  if ( ( ( ItemMappingVO ) itemMappingVOObject ).getEntityId().equals( entityId ) )
                  {
                     if ( ( ( ItemMappingVO ) itemMappingVOObject ).getBusinessTypeId().equals( businessTypeId ) )
                     {
                        flag = 2;
                     }
                  }
               }
            }
         }
         // AJAX����
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         final PrintWriter out = response.getWriter();
         out.println( "<input type=\"hidden\" name=\"isFeasible\" id=\"isFeasible\" value=\"" + flag + "\"/>" );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final ItemMappingService itemMappingService = ( ItemMappingService ) getService( "itemMappingService" );
         // ������ȡ�����
         final String mappingId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "mappingId" ), "UTF-8" ) );
         // ���ItemMappingVO����                                                                                          
         final ItemMappingVO itemMappingVO = itemMappingService.getItemMappingVOByMappingId( mappingId );
         // ����Add��Update
         itemMappingVO.setSubAction( VIEW_OBJECT );
         itemMappingVO.reset( null, request );
         // ��ItemMappingVO����request����
         request.setAttribute( "itemMappingForm", itemMappingVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageItemMapping" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ItemMappingService itemMappingService = ( ItemMappingService ) getService( "itemMappingService" );
            // ������ȡ�����
            final String mappingId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "itemMappingId" ), "UTF-8" ) );
            // ��ȡItemMappingVO����
            final ItemMappingVO itemMappingVO = itemMappingService.getItemMappingVOByMappingId( mappingId );
            // װ�ؽ��洫ֵ
            itemMappingVO.update( ( ItemMappingVO ) form );
            // ��ȡ��¼�û�
            itemMappingVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            itemMappingService.updateItemMapping( itemMappingVO );

            // ��ʼ�������־ö���
            constantsInit( "initItemMapping", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, itemMappingVO, Operate.MODIFY, itemMappingVO.getMappingId(), null );
         }
         // ���Form
         ( ( ItemMappingVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
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
         // ��ʼ��Service�ӿ�
         final ItemMappingService itemMappingService = ( ItemMappingService ) getService( "itemMappingService" );
         // ���Action Form
         final ItemMappingVO itemMappingVO = ( ItemMappingVO ) form;
         // ����ѡ�е�ID
         if ( itemMappingVO.getSelectedIds() != null && !itemMappingVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : itemMappingVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               itemMappingVO.setMappingId( selectedId );
               itemMappingVO.setAccountId( getAccountId( request, response ) );
               itemMappingVO.setModifyBy( getUserId( request, response ) );
               itemMappingService.deleteItemMapping( itemMappingVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initItemMapping", getAccountId( request, response ) );

            insertlog( request, itemMappingVO, Operate.DELETE, null, itemMappingVO.getSelectedIds() );
         }
         // ���Selected IDs����Action
         itemMappingVO.setSelectedIds( "" );
         itemMappingVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
