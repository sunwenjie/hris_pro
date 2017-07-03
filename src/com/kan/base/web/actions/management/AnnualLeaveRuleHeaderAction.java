package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.AnnualLeaveRuleDetailVO;
import com.kan.base.domain.management.AnnualLeaveRuleHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.AnnualLeaveRuleHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class AnnualLeaveRuleHeaderAction extends BaseAction
{
   // Module ASSCESS_ACTION
   public static String ACCESS_ACTION = "HRO_SALARY_ANNUALLEAVE_RULE";

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
         final AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService = ( AnnualLeaveRuleHeaderService ) getService( "annualLeaveRuleHeaderService" );
         // ���Action Form
         final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO = ( AnnualLeaveRuleHeaderVO ) form;

         // ����ɾ������
         if ( annualLeaveRuleHeaderVO.getSubAction() != null && annualLeaveRuleHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( annualLeaveRuleHeaderVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder annualLeaveRuleHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         annualLeaveRuleHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         annualLeaveRuleHeaderHolder.setObject( annualLeaveRuleHeaderVO );
         // ����ҳ���¼����
         annualLeaveRuleHeaderHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         annualLeaveRuleHeaderVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         annualLeaveRuleHeaderService.getAnnualLeaveRuleHeaderVOsByCondition( annualLeaveRuleHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( annualLeaveRuleHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "annualLeaveRuleHeaderHolder", annualLeaveRuleHeaderHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "accountId", getAccountId( request, null ) );
            // Ajax Table���ã�ֱ�Ӵ���ShiftHeader JSP
            return mapping.findForward( "listAnnualLeaveRuleHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listAnnualLeaveRuleHeader" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      ( ( AnnualLeaveRuleHeaderVO ) form ).reset( mapping, request );

      // ����Sub Action
      ( ( AnnualLeaveRuleHeaderVO ) form ).setStatus( BaseVO.TRUE );
      ( ( AnnualLeaveRuleHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����  
      return mapping.findForward( "manageAnnualLeaveRuleHeader" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO = new AnnualLeaveRuleDetailVO();

         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService = ( AnnualLeaveRuleHeaderService ) getService( "annualLeaveRuleHeaderService" );
            // ��õ�ǰFORM
            final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO = ( AnnualLeaveRuleHeaderVO ) form;
            annualLeaveRuleHeaderVO.setCreateBy( getUserId( request, response ) );
            annualLeaveRuleHeaderVO.setModifyBy( getUserId( request, response ) );
            annualLeaveRuleHeaderService.insertAnnualLeaveRuleHeader( annualLeaveRuleHeaderVO );

            // ���¼��ػ���
            constantsInit( "initAnnualLeaveRule", getAccountId( request, response ) );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, annualLeaveRuleHeaderVO, Operate.ADD, annualLeaveRuleHeaderVO.getHeaderId(), null );

            annualLeaveRuleDetailVO.setHeaderId( annualLeaveRuleHeaderVO.getHeaderId() );
         }
         else
         {
            // ���Action Form
            ( ( AnnualLeaveRuleHeaderVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return new AnnualLeaveRuleDetailAction().list_object( mapping, annualLeaveRuleDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService = ( AnnualLeaveRuleHeaderService ) getService( "annualLeaveRuleHeaderService" );
            // ������ȡ�����
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ��ȡShiftHeaderVO����
            final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO = annualLeaveRuleHeaderService.getAnnualLeaveRuleHeaderVOByHeaderId( headerId );
            // װ�ؽ��洫ֵ
            annualLeaveRuleHeaderVO.update( ( AnnualLeaveRuleHeaderVO ) form );
            // ��ȡ��¼�û�
            annualLeaveRuleHeaderVO.setModifyBy( getUserId( request, response ) );

            // �����޸ķ���
            annualLeaveRuleHeaderService.updateAnnualLeaveRuleHeader( annualLeaveRuleHeaderVO );

            // ���¼��ػ���
            // constantsInit( "initAnnualLeaveRule", getAccountId( request, response ) );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
         }

         // ���Action Form
         ( ( AnnualLeaveRuleHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new AnnualLeaveRuleDetailAction().list_object( mapping, new AnnualLeaveRuleDetailVO(), request, response );
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
            final AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService = ( AnnualLeaveRuleHeaderService ) getService( "annualLeaveRuleHeaderService" );
            // ������ȡ�����
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ��ȡShiftHeaderVO����
            final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO = annualLeaveRuleHeaderService.getAnnualLeaveRuleHeaderVOByHeaderId( headerId );
            // װ�ؽ��洫ֵ
            annualLeaveRuleHeaderVO.update( ( AnnualLeaveRuleHeaderVO ) form );
            // ��ȡ��¼�û�
            annualLeaveRuleHeaderVO.setModifyBy( getUserId( request, response ) );

            // �����޸ķ���
            annualLeaveRuleHeaderService.updateAnnualLeaveRuleHeader( annualLeaveRuleHeaderVO );

            // ���¼��ػ���
            constantsInit( "initAnnualLeaveRule", getAccountId( request, response ) );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, annualLeaveRuleHeaderVO, Operate.MODIFY, annualLeaveRuleHeaderVO.getHeaderId(), null );
         }

         // ���Action Form
         ( ( AnnualLeaveRuleHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new AnnualLeaveRuleDetailAction().list_object( mapping, new AnnualLeaveRuleDetailVO(), request, response );
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
         final AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService = ( AnnualLeaveRuleHeaderService ) getService( "annualLeaveRuleHeaderService" );
         // ���Action Form
         AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO = ( AnnualLeaveRuleHeaderVO ) form;

         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( annualLeaveRuleHeaderVO.getSelectedIds() ) != null )
         {
            insertlog( request, annualLeaveRuleHeaderVO, Operate.DELETE, null, annualLeaveRuleHeaderVO.getSelectedIds() );
            // �ָ�
            for ( String selectedId : annualLeaveRuleHeaderVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               annualLeaveRuleHeaderVO = annualLeaveRuleHeaderService.getAnnualLeaveRuleHeaderVOByHeaderId( selectedId );
               annualLeaveRuleHeaderVO.setHeaderId( selectedId );
               annualLeaveRuleHeaderVO.setAccountId( getAccountId( request, response ) );
               annualLeaveRuleHeaderVO.setModifyBy( getUserId( request, response ) );
               annualLeaveRuleHeaderService.deleteAnnualLeaveRuleHeader( annualLeaveRuleHeaderVO );
            }
         }

         // ���Selected IDs����Action
         annualLeaveRuleHeaderVO.setSelectedIds( "" );
         annualLeaveRuleHeaderVO.setSubAction( "" );

         // ���¼��ػ���
         constantsInit( "initAnnualLeaveRule", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
