package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.TaxVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.TaxService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class TaxAction extends BaseAction
{

   public static String accessAction = "HRO_MGT_TAX";

   /**  
    * List Object Options Ajax
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-05
   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ������ѡ��
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // ��ȡTaxId
         final String taxId = request.getParameter( "taxId" );

         // ��ʼ��Service�ӿ�
         final TaxService taxService = ( TaxService ) getService( "taxService" );

         // ��ȡEntityId��businessTypeId
         final String entityId = request.getParameter( "entityId" );
         final String businessTypeId = request.getParameter( "businessTypeId" );

         // ��ʼ��TaxVO
         final TaxVO taxVO = new TaxVO();
         taxVO.setAccountId( getAccountId( request, response ) );
         taxVO.setEntityId( entityId );
         taxVO.setBusinessTypeId( businessTypeId );

         // ��ȡTaxVO�б�
         final List< Object > taxVOs = taxService.getTaxVOsByTaxVO( taxVO );

         // ����������
         if ( taxVOs != null && taxVOs.size() > 0 )
         {
            for ( Object taxVOObject : taxVOs )
            {
               final TaxVO tempTaxVO = ( TaxVO ) taxVOObject;

               // ��ʼ��MappingVO
               final MappingVO mappingVO = new MappingVO();

               mappingVO.setMappingId( tempTaxVO.getTaxId() );
               // ����
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( tempTaxVO.getNameZH() );
               }
               // ������
               else
               {
                  mappingVO.setMappingValue( tempTaxVO.getNameEN() );
               }
               mappingVOs.add( mappingVO );
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "taxId", taxId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "" );
   }

   /**
    * Get Object JSON
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
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

         // ��ȡTaxId
         final String taxId = request.getParameter( "taxId" );

         // ��ȡEntityId
         final String entityId = request.getParameter( "entityId" );

         // ��ȡBusinessTypeId
         final String businessTypeId = request.getParameter( "businessTypeId" );

         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();

         if ( taxId != null && !taxId.trim().isEmpty() )
         {
            // ��ȡTaxVO
            final TaxVO taxVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxVOByTaxId( taxId );

            if ( taxVO != null )
            {
               taxVO.reset( mapping, request );
               jsonObject = JSONObject.fromObject( taxVO );
               jsonObject.put( "success", "true" );
            }
            else
            {
               jsonObject.put( "success", "false" );
            }
         }
         else if ( entityId != null && !entityId.trim().isEmpty() && businessTypeId != null && !businessTypeId.trim().isEmpty() )
         {
            // ��ȡTaxVO�б�
            final List< TaxVO > taxVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxVOs( entityId, businessTypeId );

            if ( taxVOs != null && taxVOs.size() == 1 )
            {
               taxVOs.get( 0 ).reset( mapping, request );
               jsonObject = JSONObject.fromObject( taxVOs.get( 0 ) );
               jsonObject.put( "success", "true" );
            }
            else
            {
               jsonObject.put( "success", "false" );
            }
         }

         // Send to client
         out.println( jsonObject != null ? jsonObject.toString() : "" );
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
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final TaxService taxService = ( TaxService ) getService( "taxService" );
         // ���Action Form
         final TaxVO taxVO = ( TaxVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         taxVO.setAccountId( getAccountId( request, response ) );
         // ����ɾ������
         if ( taxVO.getSubAction() != null && taxVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( taxVO );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder taxHolder = new PagedListHolder();
         // ���뵱ǰҳ
         taxHolder.setPage( page );
         // ���뵱ǰֵ����
         taxHolder.setObject( taxVO );
         // ����ҳ���¼����
         taxHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         taxService.getTaxVOsByCondition( taxHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( taxHolder, request );
         // Holder��д��Request����
         request.setAttribute( "taxHolder", taxHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            return mapping.findForward( "listTaxTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listTax" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( TaxVO ) form ).setStatus( TaxVO.TRUE );
      ( ( TaxVO ) form ).setSubAction( CREATE_OBJECT );
      // ��ת���½�����  
      return mapping.findForward( "manageTax" );
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
            final TaxService taxService = ( TaxService ) getService( "taxService" );
            // ��õ�ǰFORM
            final TaxVO taxVO = ( TaxVO ) form;
            taxVO.setCreateBy( getUserId( request, response ) );
            taxVO.setModifyBy( getUserId( request, response ) );
            taxVO.setAccountId( getAccountId( request, response ) );
            taxService.insertTax( taxVO );

            // ��ʼ�������־ö���
            constantsInit( "initTax", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }

         // ���Form
         ( ( TaxVO ) form ).reset();
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
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final TaxService taxService = ( TaxService ) getService( "taxService" );
         // ������ȡ�����
         final String taxId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "taxId" ), "UTF-8" ) );
         // ���TaxVO����                                                                                          
         final TaxVO taxVO = taxService.getTaxVOByTaxId( taxId );
         // ����Add��Update
         taxVO.setSubAction( VIEW_OBJECT );
         taxVO.reset( null, request );
         // ��TaxVO����request����
         request.setAttribute( "taxForm", taxVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageTax" );
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
            final TaxService taxService = ( TaxService ) getService( "taxService" );
            // ������ȡ�����
            final String taxId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "taxId" ), "UTF-8" ) );
            // ��ȡTaxVO����
            final TaxVO taxVO = taxService.getTaxVOByTaxId( taxId );
            // װ�ؽ��洫ֵ
            taxVO.update( ( TaxVO ) form );
            // ��ȡ��¼�û�
            taxVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            taxService.updateTax( taxVO );

            // ��ʼ�������־ö���
            constantsInit( "initTax", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }
         // ���Form
         ( ( TaxVO ) form ).reset();
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
         final TaxService taxService = ( TaxService ) getService( "taxService" );
         // ���Action Form
         final TaxVO taxVO = ( TaxVO ) form;
         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( taxVO.getSelectedIds() ) != null )
         {
            // �ָ�
            for ( String selectedId : taxVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               taxVO.setTaxId( selectedId );
               taxVO.setAccountId( getAccountId( request, response ) );
               taxVO.setModifyBy( getUserId( request, response ) );
               taxService.deleteTax( taxVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initTax", getAccountId( request, response ) );
         }
         // ���Selected IDs����Action
         taxVO.setSelectedIds( "" );
         taxVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
