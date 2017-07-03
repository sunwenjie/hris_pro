package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.EducationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.EducationService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class EducationAction extends BaseAction
{
   public final static String accessAction = "HRO_EMPLOYEE_EDUCATION";

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
         final EducationService educationService = ( EducationService ) getService( "educationService" );
         // ���Action Form
         final EducationVO educationVO = ( EducationVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         educationVO.setAccountId( getAccountId( request, response ) );

         // ����ɾ������
         if ( educationVO.getSubAction() != null && educationVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( educationVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder educationHolder = new PagedListHolder();
         // ���뵱ǰҳ
         educationHolder.setPage( page );
         // ���뵱ǰֵ����
         educationHolder.setObject( educationVO );
         // ����ҳ���¼����
         educationHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         educationService.getEducationVOsByCondition( educationHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( educationHolder, request );

         // Holder��д��Request����
         request.setAttribute( "educationHolder", educationHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEducationTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "listEducation" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ����Sub Action
      ( ( EducationVO ) form ).setStatus( EducationVO.TRUE );
      ( ( EducationVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageEducation" );
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
            final EducationService educationService = ( EducationService ) getService( "educationService" );

            // ��õ�ǰFORM
            final EducationVO educationVO = ( EducationVO ) form;
            educationVO.setCreateBy( getUserId( request, response ) );
            educationVO.setModifyBy( getUserId( request, response ) );
            educationVO.setAccountId( getAccountId( request, response ) );
            educationService.insertEducation( educationVO );

            // ��ʼ�������־ö���
            constantsInit( "initEducation", getAccountId( request, response ) );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, educationVO, Operate.ADD, educationVO.getEducationId(), null );
         }
         else
         {
            // ���FORM
            ( ( EducationVO ) form ).reset();
            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            return list_object( mapping, form, request, response );
         }

         // ���Action Form
         ( ( EducationVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final EducationService educationService = ( EducationService ) getService( "educationService" );
         // ������ȡ�����
         String educationId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( educationId ) == null )
         {
            educationId = ( ( EducationVO ) form ).getEducationId();
         }
         // ���EducationVO����
         final EducationVO educationVO = educationService.getEducationVOByEducationId( educationId );
         educationVO.reset( null, request );
         // ����Add��Update
         educationVO.setSubAction( VIEW_OBJECT );
         // ��EducationVO����request����
         request.setAttribute( "educationForm", educationVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageEducation" );
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
            final EducationService educationService = ( EducationService ) getService( "educationService" );

            // ������ȡ�����
            final String entityId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
            // ���EducationVO����
            final EducationVO educationVO = educationService.getEducationVOByEducationId( entityId );
            // װ�ؽ��洫ֵ
            educationVO.update( ( EducationVO ) form );
            // ��ȡ��¼�û�
            educationVO.setModifyBy( getUserId( request, response ) );
            // �����޸Ľӿ�
            educationService.updateEducation( educationVO );

            // ��ʼ�������־ö���
            constantsInit( "initEducation", getAccountId( request, response ) );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, educationVO, Operate.MODIFY, educationVO.getEducationId(), null );
         }

         // ���Action Form
         ( ( EducationVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
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
         final EducationService educationService = ( EducationService ) getService( "educationService" );

         // ���Action Form
         final EducationVO educationVO = ( EducationVO ) form;
         // ����ѡ�е�ID
         if ( educationVO.getSelectedIds() != null && !educationVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : educationVO.getSelectedIds().split( "," ) )
            {
               educationVO.setEducationId( selectedId );
               educationVO.setModifyBy( getUserId( request, response ) );
               // ����ɾ���ӿ�
               educationService.deleteEducation( educationVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initEducation", getAccountId( request, response ) );

            insertlog( request, educationVO, Operate.DELETE, null, educationVO.getSelectedIds() );
         }

         // ���Selected IDs����Action
         educationVO.setSelectedIds( "" );
         educationVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
