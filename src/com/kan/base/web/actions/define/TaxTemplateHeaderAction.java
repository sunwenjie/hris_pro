package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.TaxTemplateDTO;
import com.kan.base.domain.define.TaxTemplateDetailVO;
import com.kan.base.domain.define.TaxTemplateHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.TaxTemplateHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class TaxTemplateHeaderAction extends BaseAction
{
   public static final String accessAction = "HRO_SALARY_INCOMETAXTEMPLATE";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ���Action Form
         final TaxTemplateHeaderVO taxTemplateHeaderVO = ( TaxTemplateHeaderVO ) form;

         // �����Action��ɾ��
         if ( taxTemplateHeaderVO.getSubAction() != null && taxTemplateHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( taxTemplateHeaderVO );
         }

         // ��ʼ��Service�ӿ�
         final TaxTemplateHeaderService taxTemplateHeaderService = ( TaxTemplateHeaderService ) getService( "taxTemplateHeaderService" );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder taxTemplateHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         taxTemplateHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         taxTemplateHeaderHolder.setObject( taxTemplateHeaderVO );
         // ����ҳ���¼����
         taxTemplateHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         taxTemplateHeaderService.getTaxTemplateHeaderVOsByCondition( taxTemplateHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( taxTemplateHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "taxTemplateHeaderHolder", taxTemplateHeaderHolder );

         // Ajax���ã�ֱ�Ӵ�ֵ��table jspҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listTaxTemplateHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listTaxTemplateHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ���ó�ʼ���ֶ�
      ( ( TaxTemplateHeaderVO ) form ).setStatus( TaxTemplateHeaderVO.TRUE );
      ( ( TaxTemplateHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageTaxTemplateHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��TaxTemplateDetailVO
         final TaxTemplateDetailVO taxTemplateDetailVO = new TaxTemplateDetailVO();

         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final TaxTemplateHeaderService taxTemplateHeaderService = ( TaxTemplateHeaderService ) getService( "taxTemplateHeaderService" );

            // ��õ�ǰFORM
            final TaxTemplateHeaderVO taxTemplateHeaderVO = ( TaxTemplateHeaderVO ) form;
            // ��ȡ��¼�û����˻�
            taxTemplateHeaderVO.setCreateBy( getUserId( request, response ) );
            taxTemplateHeaderVO.setModifyBy( getUserId( request, response ) );
            taxTemplateHeaderVO.setAccountId( getAccountId( request, response ) );

            // ���TaxTemplateHeaderVO
            taxTemplateHeaderService.insertTaxTemplateHeader( taxTemplateHeaderVO );

            taxTemplateDetailVO.setTemplateHeaderId( taxTemplateHeaderVO.getTemplateHeaderId() );

            // ���¼��س����е�TaxTemplateHeader
            constantsInit( "initTaxTemplateHeader", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, taxTemplateHeaderVO, Operate.ADD, taxTemplateHeaderVO.getTemplateHeaderId(), null );
         }
         else
         {
            // ���form
            ( ( TaxTemplateHeaderVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         // ��תList TaxTemplateDetail����
         return new TaxTemplateDetailAction().list_object( mapping, taxTemplateDetailVO, request, response );
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
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final TaxTemplateHeaderService taxTemplateHeaderService = ( TaxTemplateHeaderService ) getService( "taxTemplateHeaderService" );

            // �������
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // ���TaxTemplateHeaderVO����
            final TaxTemplateHeaderVO taxTemplateHeaderVO = taxTemplateHeaderService.getTaxTemplateHeaderVOByTemplateHeaderId( headerId );

            // װ�ؽ��洫ֵ
            taxTemplateHeaderVO.update( ( TaxTemplateHeaderVO ) form );
            // ��ȡ��¼�û�
            taxTemplateHeaderVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            taxTemplateHeaderService.updateTaxTemplateHeader( taxTemplateHeaderVO );

            // ���¼��س����е�TaxTemplateHeader
            constantsInit( "initTaxTemplateHeader", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, taxTemplateHeaderVO, Operate.MODIFY, taxTemplateHeaderVO.getTemplateHeaderId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תList TaxTemplateDetail����
      return new TaxTemplateDetailAction().list_object( mapping, new TaxTemplateDetailVO(), request, response );
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
         final TaxTemplateHeaderService taxTemplateHeaderService = ( TaxTemplateHeaderService ) getService( "taxTemplateHeaderService" );

         // ���Action Form
         TaxTemplateHeaderVO taxTemplateHeaderVO = ( TaxTemplateHeaderVO ) form;
         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( taxTemplateHeaderVO.getSelectedIds() ) != null )
         {
            insertlog( request, taxTemplateHeaderVO, Operate.DELETE, null, taxTemplateHeaderVO.getSelectedIds() );
            // �ָ�
            for ( String selectedId : taxTemplateHeaderVO.getSelectedIds().split( "," ) )
            {
               // ���ɾ������
               taxTemplateHeaderVO = taxTemplateHeaderService.getTaxTemplateHeaderVOByTemplateHeaderId( selectedId );
               taxTemplateHeaderVO.setModifyBy( getUserId( request, response ) );
               taxTemplateHeaderVO.setModifyDate( new Date() );
               taxTemplateHeaderService.deleteTaxTemplateHeader( taxTemplateHeaderVO );
            }

         }

         // ���¼��س����е�TaxTemplateHeader
         constantsInit( "initTaxTemplateHeader", getAccountId( request, response ) );

         // ���Selected IDs����Action
         ( ( TaxTemplateHeaderVO ) form ).setSelectedIds( "" );
         ( ( TaxTemplateHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * 
   * cityId_change_ajax
   * ����change�¼�����
   * �ж���ǰѡ�������Ƿ�����ڸ�˰ģ���б� 
    */
   public ActionForward cityId_change_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡcityId
         final String cityId = request.getParameter( "cityId" );

         // ��ȡ��ǰaccountId - client��TaxTemplate Mapping��ʽ
         final List< MappingVO > taxTemplateMappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxTemplate( getLocale( request ).getLanguage(), KANUtil.filterEmpty( getCorpId( request, response ) ) );

         // ��ʼ��JSONObject
         final JSONObject jsonObject = new JSONObject();
         jsonObject.put( "success", "true" );

         // �������TaxTemplate Mapping List����������
         if ( taxTemplateMappingVOs != null && taxTemplateMappingVOs.size() > 0 )
         {
            for ( MappingVO taxTemplateMappingVO : taxTemplateMappingVOs )
            {
               // ��ȡTaxTemplateDTO
               final TaxTemplateDTO taxTemplateDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxTemplateDTOByTemplateHeaderId( taxTemplateMappingVO.getMappingId() );

               if ( taxTemplateDTO != null && taxTemplateDTO.getTaxTemplateHeaderVO() != null && taxTemplateDTO.getTaxTemplateHeaderVO().getCityId() != null
                     && taxTemplateDTO.getTaxTemplateHeaderVO().getCityId().equals( cityId ) )
               {
                  jsonObject.remove( "success" );
                  jsonObject.put( "success", "false" );
                  break;
               }
            }
         }

         // Send to client
         out.println( jsonObject.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }
}
