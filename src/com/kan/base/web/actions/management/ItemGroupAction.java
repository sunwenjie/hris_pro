package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ItemGroupVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ItemGroupService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ItemGroupAction extends BaseAction
{

   public final static String accessAction = "HRO_MGT_ITEM_GROUP";

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
         final ItemGroupService itemGroupService = ( ItemGroupService ) getService( "itemGroupService" );
         // ���Action Form
         final ItemGroupVO itemGroupVO = ( ItemGroupVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         itemGroupVO.setAccountId( getAccountId( request, response ) );
         // ����ɾ������
         if ( itemGroupVO.getSubAction() != null && itemGroupVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( itemGroupVO );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder itemGroupHolder = new PagedListHolder();
         // ���뵱ǰҳ
         itemGroupHolder.setPage( page );
         // ���뵱ǰֵ����
         itemGroupHolder.setObject( itemGroupVO );
         // ����ҳ���¼����
         itemGroupHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         itemGroupService.getItemGroupVOsByCondition( itemGroupHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( itemGroupHolder, request );
         // Holder��д��Request����
         request.setAttribute( "itemGroupHolder", itemGroupHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            return mapping.findForward( "listItemGroupTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listItemGroup" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( ItemGroupVO ) form ).setListMerge( ItemGroupVO.FALSE );
      ( ( ItemGroupVO ) form ).setReportMerge( ItemGroupVO.FALSE );
      ( ( ItemGroupVO ) form ).setInvoiceMerge( ItemGroupVO.FALSE );
      ( ( ItemGroupVO ) form ).setStatus( ItemGroupVO.TRUE );
      ( ( ItemGroupVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����  
      return mapping.findForward( "manageItemGroup" );
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
            final ItemGroupService itemGroupService = ( ItemGroupService ) getService( "itemGroupService" );
            // ��õ�ǰFORM
            final ItemGroupVO itemGroupVO = ( ItemGroupVO ) form;
            itemGroupVO.setCreateBy( getUserId( request, response ) );
            itemGroupVO.setModifyBy( getUserId( request, response ) );
            itemGroupVO.setAccountId( getAccountId( request, response ) );
            itemGroupService.insertItemGroup( itemGroupVO );

            // ��ʼ�������־ö���
            constantsInit( "initItemGroup", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, itemGroupVO, Operate.ADD, itemGroupVO.getItemGroupId(), null );
         }
         // ���Form
         ( ( ItemGroupVO ) form ).reset();
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
         final ItemGroupService itemGroupService = ( ItemGroupService ) getService( "itemGroupService" );
         // ������ȡ�����
         final String itemGroupId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
         // ���ItemGroupVO����
         final ItemGroupVO itemGroupVO = itemGroupService.getItemGroupVOByItemGroupId( itemGroupId );
         // ����Add��Update
         itemGroupVO.setSubAction( VIEW_OBJECT );
         itemGroupVO.reset( null, request );
         // ��ItemGroupVO����request����
         request.setAttribute( "itemGroupForm", itemGroupVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageItemGroup" );
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
            final ItemGroupService itemGroupService = ( ItemGroupService ) getService( "itemGroupService" );
            // ������ȡ�����
            final String itemGroupId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
            // ��ȡItemGroupVO����
            final ItemGroupVO itemGroupVO = itemGroupService.getItemGroupVOByItemGroupId( itemGroupId );
            // װ�ؽ��洫ֵ
            itemGroupVO.update( ( ItemGroupVO ) form );
            // ��ȡ��¼�û�
            itemGroupVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            itemGroupService.updateItemGroup( itemGroupVO );

            // ��ʼ�������־ö���
            constantsInit( "initItemGroup", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, itemGroupVO, Operate.MODIFY, itemGroupVO.getItemGroupId(), null );
         }
         // ���Form
         ( ( ItemGroupVO ) form ).reset();
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
         final ItemGroupService itemGroupService = ( ItemGroupService ) getService( "itemGroupService" );
         // ���Action Form
         final ItemGroupVO itemGroupVO = ( ItemGroupVO ) form;
         // ����ѡ�е�ID
         if ( itemGroupVO.getSelectedIds() != null && !itemGroupVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : itemGroupVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               itemGroupVO.setItemGroupId( selectedId );
               itemGroupVO.setAccountId( getAccountId( request, response ) );
               itemGroupVO.setModifyBy( getUserId( request, response ) );
               itemGroupService.deleteItemGroup( itemGroupVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initItemGroup", getAccountId( request, response ) );

            insertlog( request, itemGroupVO, Operate.DELETE, null, itemGroupVO.getSelectedIds() );
         }
         // ���Selected IDs����Action
         itemGroupVO.setSelectedIds( "" );
         itemGroupVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
