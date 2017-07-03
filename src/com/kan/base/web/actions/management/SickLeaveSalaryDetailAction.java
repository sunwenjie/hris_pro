package com.kan.base.web.actions.management;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.SickLeaveSalaryDetailVO;
import com.kan.base.domain.management.SickLeaveSalaryHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.SickLeaveSalaryDetailService;
import com.kan.base.service.inf.management.SickLeaveSalaryHeaderService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class SickLeaveSalaryDetailAction extends BaseAction
{
   public static String accessAction = "HRO_SALARY_SICKLEAVE";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ���Action Form
         final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO = ( SickLeaveSalaryDetailVO ) form;

         // ����SubAction
         dealSubAction( sickLeaveSalaryDetailVO, mapping, form, request, response );

         // ��ʼ��Service�ӿ�
         final SickLeaveSalaryHeaderService sickLeaveSalaryHeaderService = ( SickLeaveSalaryHeaderService ) getService( "sickLeaveSalaryHeaderService" );
         final SickLeaveSalaryDetailService sickLeaveSalaryDetailService = ( SickLeaveSalaryDetailService ) getService( "sickLeaveSalaryDetailService" );

         // �����������
         String headerId = request.getParameter( "id" );

         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = sickLeaveSalaryDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ����������
         final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO = sickLeaveSalaryHeaderService.getSickLeaveSalaryHeaderVOByHeaderId( headerId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         sickLeaveSalaryHeaderVO.reset( null, request );
         // �����޸ġ��鿴
         sickLeaveSalaryHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "sickLeaveSalaryHeaderForm", sickLeaveSalaryHeaderVO );
         sickLeaveSalaryDetailVO.setHeaderId( headerId );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sickLeaveSalaryDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         sickLeaveSalaryDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         sickLeaveSalaryDetailHolder.setObject( sickLeaveSalaryDetailVO );
         // ����ҳ���¼����
         sickLeaveSalaryDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sickLeaveSalaryDetailService.getSickLeaveSalaryDetailVOsByCondition( sickLeaveSalaryDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( sickLeaveSalaryDetailHolder, request );

         sickLeaveSalaryDetailVO.setStatus( BaseVO.TRUE );
         sickLeaveSalaryDetailVO.reset( null, request );
         // Holder��д��Request����
         request.setAttribute( "sickLeaveSalaryDetailHolder", sickLeaveSalaryDetailHolder );
         request.setAttribute( "sickLeaveSalaryDetailForm", sickLeaveSalaryDetailVO );

         List< MappingVO > salaryItems = KANConstants.getKANAccountConstants( sickLeaveSalaryHeaderVO.getAccountId() ).getItemsByType( "1", request.getLocale().getLanguage(), sickLeaveSalaryHeaderVO.getCorpId() );
         request.setAttribute( "salaryItems", salaryItems );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���SickLeaveSalaryDetail JSP
            return mapping.findForward( "listSickLeaveSalaryDetailTable" );
         }

         // ���subAction
         ( ( SickLeaveSalaryDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listsickLeaveSalaryDetail" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
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
            final SickLeaveSalaryDetailService sickLeaveSalaryDetailService = ( SickLeaveSalaryDetailService ) getService( "sickLeaveSalaryDetailService" );

            // ��õ�ǰFORM
            final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO = ( SickLeaveSalaryDetailVO ) form;

            // �������ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            List< Object > list = sickLeaveSalaryDetailService.getSickLeaveSalaryDetailByHeaderId( headerId );
            boolean flag = true;
            if ( list != null && list.size() > 0 )
            {
               for ( int i = 0; i < list.size(); i++ )
               {
                  SickLeaveSalaryDetailVO sickLeaveSalaryDetail = ( SickLeaveSalaryDetailVO ) list.get( i );

                  int rangeTo = Integer.parseInt( sickLeaveSalaryDetail.getRangeTo() == null || sickLeaveSalaryDetail.getRangeTo().equals( "0" ) ? "1000000"
                        : sickLeaveSalaryDetail.getRangeTo() );
                  int rangeFrom = Integer.parseInt( sickLeaveSalaryDetail.getRangeFrom() );
                  int rangeToVO = Integer.parseInt( sickLeaveSalaryDetailVO.getRangeTo() == null || sickLeaveSalaryDetailVO.getRangeTo().equals( "0" ) ? "1000000"
                        : sickLeaveSalaryDetailVO.getRangeTo() );
                  int rangeFromVO = Integer.parseInt( sickLeaveSalaryDetailVO.getRangeFrom() );

                  if ( rangeTo == rangeToVO || rangeToVO == rangeFrom )
                  {
                     flag = false;
                     break;
                  }
                  if ( rangeTo == rangeFromVO || rangeFromVO == rangeFrom )
                  {
                     flag = false;
                     break;
                  }
                  if ( sickLeaveSalaryDetail.getRangeTo() != null && sickLeaveSalaryDetail.getRangeFrom() != null )
                  {

                     if ( rangeFrom < rangeToVO && rangeToVO < rangeTo )
                     {
                        flag = false;
                        break;
                     }
                     if ( rangeFrom < rangeFromVO && rangeFromVO < rangeTo )
                     {
                        flag = false;
                        break;
                     }
                  }
               }
            }
            if ( flag )
            {
               sickLeaveSalaryDetailVO.setHeaderId( headerId );
               sickLeaveSalaryDetailVO.setCreateBy( getUserId( request, response ) );
               sickLeaveSalaryDetailVO.setModifyBy( getUserId( request, response ) );
               sickLeaveSalaryDetailVO.setAccountId( getAccountId( request, response ) );
               // ������ӷ���
               sickLeaveSalaryDetailService.insertSickLeaveSalaryDetail( sickLeaveSalaryDetailVO );

               // ���¼��ػ���
               constantsInit( "initSickLeaveSalary", getAccountId( request, response ) );

               // ���ر���ɹ��ı��
               success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

               insertlog( request, sickLeaveSalaryDetailVO, Operate.ADD, sickLeaveSalaryDetailVO.getDetailId(), null );
            }
            else
            {
               error( request, null, "���ʧ�ܣ��������Ѵ��ڣ�", MESSAGE_DETAIL );
            }
         }

         // ���Form
         ( ( SickLeaveSalaryDetailVO ) form ).reset();
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
      // No Use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final SickLeaveSalaryDetailService sickLeaveSalaryDetailService = ( SickLeaveSalaryDetailService ) getService( "sickLeaveSalaryDetailService" );
            // ������ȡ�����
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            // ��ȡ��������
            final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO = sickLeaveSalaryDetailService.getSickLeaveSalaryDetailVOByDetailId( detailId );

            List< Object > list = sickLeaveSalaryDetailService.getSickLeaveSalaryDetailByHeaderId( headerId );
            boolean flag = true;
            if ( list != null && list.size() > 0 )
            {
               for ( int i = 0; i < list.size(); i++ )
               {
                  SickLeaveSalaryDetailVO sickLeaveSalaryDetail = ( SickLeaveSalaryDetailVO ) list.get( i );
                  if ( sickLeaveSalaryDetail.getDetailId().equals( sickLeaveSalaryDetailVO.getDetailId() ) )
                  {
                     continue;
                  }
                  int rangeTo = Integer.parseInt( sickLeaveSalaryDetail.getRangeTo() == null || sickLeaveSalaryDetail.getRangeTo().equals( "0" ) ? "1000000"
                        : sickLeaveSalaryDetail.getRangeTo() );
                  int rangeFrom = Integer.parseInt( sickLeaveSalaryDetail.getRangeFrom() );
                  int rangeToVO = Integer.parseInt( sickLeaveSalaryDetailVO.getRangeTo() == null || sickLeaveSalaryDetailVO.getRangeTo().equals( "0" ) ? "1000000"
                        : sickLeaveSalaryDetailVO.getRangeTo() );
                  int rangeFromVO = Integer.parseInt( sickLeaveSalaryDetailVO.getRangeFrom() );

                  if ( rangeTo == rangeToVO || rangeToVO == rangeFrom )
                  {
                     flag = false;
                     break;
                  }
                  if ( rangeTo == rangeFromVO || rangeFromVO == rangeFrom )
                  {
                     flag = false;
                     break;
                  }
                  if ( sickLeaveSalaryDetail.getRangeTo() != null && sickLeaveSalaryDetail.getRangeFrom() != null )
                  {

                     if ( rangeFrom < rangeToVO && rangeToVO < rangeTo )
                     {
                        flag = false;
                        break;
                     }
                     if ( rangeFrom < rangeFromVO && rangeFromVO < rangeTo )
                     {

                        flag = false;
                        break;
                     }
                  }
               }
            }

            if ( flag )
            {
               // װ�ؽ��洫ֵ
               sickLeaveSalaryDetailVO.setHeaderId( headerId );
               sickLeaveSalaryDetailVO.update( ( SickLeaveSalaryDetailVO ) form );
               sickLeaveSalaryDetailVO.setModifyBy( getUserId( request, response ) );
               // �����޸Ľӿ�
               sickLeaveSalaryDetailService.updateSickLeaveSalaryDetail( sickLeaveSalaryDetailVO );

               // ���¼��ػ���
               constantsInit( "initSickLeaveSalary", getAccountId( request, response ) );

               // ���ر༭�ɹ��ı��
               success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

               insertlog( request, sickLeaveSalaryDetailVO, Operate.MODIFY, sickLeaveSalaryDetailVO.getDetailId(), null );
            }
            else
            {
               error( request, null, "�༭ʧ�ܣ��������Ѵ��ڣ�", MESSAGE_DETAIL );
            }
         }

         // ���Form
         ( ( SickLeaveSalaryDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final SickLeaveSalaryHeaderService sickLeaveSalaryHeaderService = ( SickLeaveSalaryHeaderService ) getService( "sickLeaveSalaryHeaderService" );
         final SickLeaveSalaryDetailService sickLeaveSalaryDetailService = ( SickLeaveSalaryDetailService ) getService( "sickLeaveSalaryDetailService" );
         // ��������ID
         final String detailId = request.getParameter( "detailId" );
         // ���SickLeaveSalaryDetailVO����
         final SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO = sickLeaveSalaryDetailService.getSickLeaveSalaryDetailVOByDetailId( detailId );
         // ���SickLeaveSalaryHeaderVO����
         final SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO = sickLeaveSalaryHeaderService.getSickLeaveSalaryHeaderVOByHeaderId( sickLeaveSalaryDetailVO.getHeaderId() );
         // ���ʻ���ֵ
         sickLeaveSalaryDetailVO.reset( null, request );
         // �����޸����
         sickLeaveSalaryDetailVO.setSubAction( VIEW_OBJECT );

         // ����request����
         request.setAttribute( "sickLeaveSalaryHeaderForm", sickLeaveSalaryHeaderVO );
         request.setAttribute( "sickLeaveSalaryDetailForm", sickLeaveSalaryDetailVO );

         // Ajax Form���ã�ֱ�Ӵ���Form JSP
         return mapping.findForward( "manageSickLeaveSalaryDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
         final SickLeaveSalaryDetailService sickLeaveSalaryDetailService = ( SickLeaveSalaryDetailService ) getService( "sickLeaveSalaryDetailService" );

         // ��õ�ǰform
         SickLeaveSalaryDetailVO sickLeaveSalaryDetailVO = ( SickLeaveSalaryDetailVO ) form;

         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( sickLeaveSalaryDetailVO.getSelectedIds() ) != null )
         {
            insertlog( request, sickLeaveSalaryDetailVO, Operate.DELETE, null, sickLeaveSalaryDetailVO.getSelectedIds() );
            // �ָ�
            for ( String selectedId : sickLeaveSalaryDetailVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               sickLeaveSalaryDetailVO = sickLeaveSalaryDetailService.getSickLeaveSalaryDetailVOByDetailId( selectedId );
               sickLeaveSalaryDetailVO.setModifyBy( getUserId( request, response ) );
               sickLeaveSalaryDetailVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               sickLeaveSalaryDetailService.deleteSickLeaveSalaryDetail( sickLeaveSalaryDetailVO );
            }

            // ���¼��ػ���
            constantsInit( "initSickLeaveSalary", getAccountId( request, response ) );
         }

         // ���Selected IDs����Action
         ( ( SickLeaveSalaryDetailVO ) form ).setSelectedIds( "" );
         ( ( SickLeaveSalaryDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
