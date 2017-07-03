package com.kan.base.web.actions.define;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.TaxTemplateDetailVO;
import com.kan.base.domain.define.TaxTemplateHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.TaxTemplateDetailService;
import com.kan.base.service.inf.define.TaxTemplateHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class TaxTemplateDetailAction extends BaseAction
{
   public static final String accessAction = "HRO_SALARY_INCOMETAXTEMPLATE";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );

         // ����Ƿ�Ajax����
         final String ajax = getAjax( request );

         // ���Action Form
         final TaxTemplateDetailVO taxTemplateDetailVO = ( TaxTemplateDetailVO ) form;

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ����SubAction
         dealSubAction( taxTemplateDetailVO, mapping, form, request, response );

         // ��ʼ��Service�ӿ�
         final TaxTemplateHeaderService taxTemplateHeaderService = ( TaxTemplateHeaderService ) getService( "taxTemplateHeaderService" );
         final TaxTemplateDetailService taxTemplateDetailService = ( TaxTemplateDetailService ) getService( "taxTemplateDetailService" );

         // �����������
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = taxTemplateDetailVO.getTemplateHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ����������
         final TaxTemplateHeaderVO taxTemplateHeaderVO = taxTemplateHeaderService.getTaxTemplateHeaderVOByTemplateHeaderId( headerId );

         // ˢ�¹��ʻ�
         taxTemplateHeaderVO.reset( null, request );

         // ���ʡ��
         if ( KANUtil.filterEmpty( taxTemplateHeaderVO.getCityId(), "0" ) != null )
         {
            taxTemplateHeaderVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( taxTemplateHeaderVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            taxTemplateHeaderVO.setCityIdTemp( taxTemplateHeaderVO.getCityId() );
         }

         // ����SubAction
         taxTemplateHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "taxTemplateHeaderForm", taxTemplateHeaderVO );

         // ����templateHeaderId
         taxTemplateDetailVO.setTemplateHeaderId( headerId );

         // ���û��ָ��������Ĭ�ϰ� �б��ֶ�˳������
         if ( taxTemplateDetailVO.getSortColumn() == null || taxTemplateDetailVO.getSortColumn().isEmpty() )
         {
            taxTemplateDetailVO.setSortColumn( "columnIndex" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder taxTemplateDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         taxTemplateDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         taxTemplateDetailHolder.setObject( taxTemplateDetailVO );
         // ����ҳ���¼����
         taxTemplateDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         taxTemplateDetailService.getTaxTemplateDetailVOsByCondition( taxTemplateDetailHolder, true );

         // ����TaxTemplateDetailForm��ʼֵ
         taxTemplateDetailVO.setColumnIndex( "0" );
         taxTemplateDetailVO.setFontSize( "13" );
         taxTemplateDetailVO.setAlign( TaxTemplateDetailVO.TRUE );
         taxTemplateDetailVO.setStatus( TaxTemplateDetailVO.TRUE );

         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( taxTemplateDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "taxTemplateDetailHolder", taxTemplateDetailHolder );

         // Ajax Table���ã�ֱ�Ӵ���Detail JSP
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listTaxTemplateDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listTaxTemplateDetail" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service �ӿ�
            final TaxTemplateDetailService taxTemplateDetailService = ( TaxTemplateDetailService ) getService( "taxTemplateDetailService" );

            // ���templateHeaderId
            final String templateHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateHeaderId" ), "UTF-8" ) );

            // ��õ�ǰForm
            final TaxTemplateDetailVO taxTemplateDetailVO = ( TaxTemplateDetailVO ) form;

            // ��ʼ��TaxTemplateDetailVO����
            taxTemplateDetailVO.setTemplateHeaderId( templateHeaderId );
            taxTemplateDetailVO.setCreateBy( getUserId( request, response ) );
            taxTemplateDetailVO.setModifyBy( getUserId( request, response ) );
            taxTemplateDetailVO.setAccountId( getAccountId( request, response ) );

            // ���TaxTemplateDetailVO
            taxTemplateDetailService.insertTaxTemplateDetail( taxTemplateDetailVO );

            // ���¼��س����е�TaxTemplateHeader
            constantsInit( "initTaxTemplateHeader", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, taxTemplateDetailVO, Operate.ADD, taxTemplateDetailVO.getTemplateDetailId(), null );
         }

         // ���form
         ( ( TaxTemplateDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡtemplateDetailId
         final String templateDetailId = KANUtil.decodeString( request.getParameter( "id" ) );

         // ��ʼ��Service�ӿ�
         final TaxTemplateHeaderService taxTemplateHeaderService = ( TaxTemplateHeaderService ) getService( "taxTemplateHeaderService" );
         final TaxTemplateDetailService taxTemplateDetailService = ( TaxTemplateDetailService ) getService( "taxTemplateDetailService" );

         // ��ȡTaxTemplateDetailVO
         final TaxTemplateDetailVO taxTemplateDetailVO = taxTemplateDetailService.getTaxTemplateDetailVOByTemplateDetailId( templateDetailId );

         // ��ȡTaxTemplateHeaderVO
         final TaxTemplateHeaderVO taxTemplateHeaderVO = taxTemplateHeaderService.getTaxTemplateHeaderVOByTemplateHeaderId( taxTemplateDetailVO.getTemplateHeaderId() );

         // ���ʻ���ֵ
         taxTemplateDetailVO.reset( null, request );
         // ����SubAction
         taxTemplateDetailVO.setSubAction( VIEW_OBJECT );

         // ����request����
         request.setAttribute( "taxTemplateHeaderForm", taxTemplateHeaderVO );
         request.setAttribute( "taxTemplateDetailForm", taxTemplateDetailVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax Form���ã�ֱ�Ӵ���Form JSP
      return mapping.findForward( "manageTaxTemplateDetailForm" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ������ȡ - �����
            final String templateDetailId = KANUtil.decodeString( request.getParameter( "templateDetailId" ) );

            // ��ʼ�� Service�ӿ�
            final TaxTemplateDetailService taxTemplateDetailService = ( TaxTemplateDetailService ) getService( "taxTemplateDetailService" );

            // ��ȡTaxTemplateDetailVO
            final TaxTemplateDetailVO taxTemplateDetailVO = taxTemplateDetailService.getTaxTemplateDetailVOByTemplateDetailId( templateDetailId );

            // װ�ؽ��洫ֵ
            taxTemplateDetailVO.update( ( TaxTemplateDetailVO ) form );

            // �޸�TaxTemplateDetailVO
            taxTemplateDetailVO.setModifyBy( getUserId( request, response ) );
            taxTemplateDetailService.updateTaxTemplateDetail( taxTemplateDetailVO );

            // ���¼��س����е�TaxTemplateHeader
            constantsInit( "initTaxTemplateHeader", getAccountId( request, response ) );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, taxTemplateDetailVO, Operate.MODIFY, taxTemplateDetailVO.getTemplateDetailId(), null );
         }

         // ���Form
         ( ( TaxTemplateDetailVO ) form ).reset();
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
         final TaxTemplateDetailService taxTemplateDetailService = ( TaxTemplateDetailService ) getService( "taxTemplateDetailService" );

         // ���Action Form
         TaxTemplateDetailVO taxTemplateDetailVO = ( TaxTemplateDetailVO ) form;
         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( taxTemplateDetailVO.getSelectedIds() ) != null )
         {
            insertlog( request, taxTemplateDetailVO, Operate.DELETE, null, taxTemplateDetailVO.getSelectedIds() );
            // �ָ�
            for ( String selectedId : taxTemplateDetailVO.getSelectedIds().split( "," ) )
            {
               // ���ɾ������
               taxTemplateDetailVO = taxTemplateDetailService.getTaxTemplateDetailVOByTemplateDetailId( selectedId );
               taxTemplateDetailVO.setModifyBy( getUserId( request, response ) );
               taxTemplateDetailVO.setModifyDate( new Date() );
               taxTemplateDetailService.deleteTaxTemplateDetail( taxTemplateDetailVO );
            }
         }

         // ���¼��س����е�TaxTemplateHeader
         constantsInit( "initTaxTemplateHeader", getAccountId( request, response ) );

         // ���Selected IDs����Action
         ( ( TaxTemplateDetailVO ) form ).setSelectedIds( "" );
         ( ( TaxTemplateDetailVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}
