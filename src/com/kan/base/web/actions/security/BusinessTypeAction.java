package com.kan.base.web.actions.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.BusinessTypeBaseView;
import com.kan.base.domain.security.BusinessTypeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.BusinessTypeService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class BusinessTypeAction extends BaseAction
{
   public static String accessAction = "HRO_SYS_BUSINESSTYPE";

   /**
    * list object json
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
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

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         // �����л������ BusinessTypeVOs 
         final List< BusinessTypeVO > account_businessTypeVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).BUSINESS_TYPE_VO;
         BusinessTypeBaseView businessTypeBaseView = null;
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            for ( BusinessTypeVO vo : account_businessTypeVOs )
            {
               if ( getCorpId( request, response ).equals( vo.getCorpId() ) )
               {
                  businessTypeBaseView = new BusinessTypeBaseView();
                  businessTypeBaseView.setAccountId( vo.getAccountId() );
                  businessTypeBaseView.setId( vo.getBusinessTypeId() );
                  businessTypeBaseView.setName( vo.getNameZH() + " - " + vo.getNameEN() );
                  array.add( businessTypeBaseView );
               }
            }
         }
         else
         {
            for ( BusinessTypeVO vo : account_businessTypeVOs )
            {
               if ( vo.getCorpId() == null || "".equals( vo.getCorpId() ) )
               {
                  businessTypeBaseView = new BusinessTypeBaseView();
                  businessTypeBaseView.setAccountId( vo.getAccountId() );
                  businessTypeBaseView.setId( vo.getBusinessTypeId() );
                  businessTypeBaseView.setName( vo.getNameZH() + " - " + vo.getNameEN() );
                  array.add( businessTypeBaseView );
               }
            }
         }

         // Send to businessTypeGroup
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

   /**
    * List Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
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
         final BusinessTypeService businessTypeService = ( BusinessTypeService ) getService( "businessTypeService" );
         // ���Action Form
         final BusinessTypeVO buesinessTypeVO = ( BusinessTypeVO ) form;
         // ����ɾ������
         if ( buesinessTypeVO.getSubAction() != null && buesinessTypeVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( buesinessTypeVO );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder businessTypeHolder = new PagedListHolder();
         // ���뵱ǰҳ
         businessTypeHolder.setPage( page );
         // ���뵱ǰֵ����
         businessTypeHolder.setObject( buesinessTypeVO );
         // ����ҳ���¼����
         businessTypeHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         businessTypeService.getBusinessTypeVOsByCondition( businessTypeHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( businessTypeHolder, request );
         // Holder��д��Request����
         request.setAttribute( "businessTypeHolder", businessTypeHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            return mapping.findForward( "listBusinessTypeTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listBusinessType" );
   }

   /**
    * To Object New
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( BusinessTypeVO ) form ).setStatus( BusinessTypeVO.TRUE );
      ( ( BusinessTypeVO ) form ).setSubAction( CREATE_OBJECT );
      // ��ת���½�����  
      return mapping.findForward( "manageBusinessType" );
   }

   /**
    * Add Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final BusinessTypeService businessTypeService = ( BusinessTypeService ) getService( "businessTypeService" );

            // ��õ�ǰFORM
            final BusinessTypeVO buesinessTypeVO = ( BusinessTypeVO ) form;
            buesinessTypeVO.setCreateBy( getUserId( request, response ) );
            buesinessTypeVO.setModifyBy( getUserId( request, response ) );
            buesinessTypeVO.setAccountId( getAccountId( request, response ) );
            businessTypeService.insertBusinessType( buesinessTypeVO );

            // ��ʼ�������־ö���
            constantsInit( "initBusinessType", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, buesinessTypeVO, Operate.ADD, buesinessTypeVO.getBusinessTypeId(), null );
         }
         else
         {
            // ���Form
            ( ( BusinessTypeVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         // ���Form
         ( ( BusinessTypeVO ) form ).reset();
         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * To Object Modify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final BusinessTypeService businessTypeService = ( BusinessTypeService ) getService( "businessTypeService" );
         // ������ȡ�����
         String businessTypeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( businessTypeId ) == null )
         {
            businessTypeId = ( ( BusinessTypeVO ) form ).getBusinessTypeId();
         }
         // ���BusinessTypeVO����                                                                                          
         final BusinessTypeVO buesinessTypeVO = businessTypeService.getBusinessTypeVOByBusinessTypeId( businessTypeId );
         // ����Add��Update
         buesinessTypeVO.setSubAction( VIEW_OBJECT );
         buesinessTypeVO.reset( null, request );
         // ��BusinessTypeVO����request����
         request.setAttribute( "businessTypeForm", buesinessTypeVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageBusinessType" );
   }

   /**
    * Modify Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
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
            final BusinessTypeService businessTypeService = ( BusinessTypeService ) getService( "businessTypeService" );

            // ������ȡ�����
            final String businessTypeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ��ȡBusinessTypeVO����
            final BusinessTypeVO buesinessTypeVO = businessTypeService.getBusinessTypeVOByBusinessTypeId( businessTypeId );
            // װ�ؽ��洫ֵ
            buesinessTypeVO.update( ( BusinessTypeVO ) form );
            // ��ȡ��¼�û�
            buesinessTypeVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            businessTypeService.updateBusinessType( buesinessTypeVO );

            // ��ʼ�������־ö���
            constantsInit( "initBusinessType", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, buesinessTypeVO, Operate.MODIFY, buesinessTypeVO.getBusinessTypeId(), null );
         }
         // ���Form
         ( ( BusinessTypeVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   /**
    * Delete Object List
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final BusinessTypeService businessTypeService = ( BusinessTypeService ) getService( "businessTypeService" );

         // ���Action Form
         final BusinessTypeVO buesinessTypeVO = ( BusinessTypeVO ) form;
         // ����ѡ�е�ID
         if ( buesinessTypeVO.getSelectedIds() != null && !buesinessTypeVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : buesinessTypeVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               buesinessTypeVO.setBusinessTypeId( selectedId );
               buesinessTypeVO.setAccountId( getAccountId( request, response ) );
               buesinessTypeVO.setModifyBy( getUserId( request, response ) );
               businessTypeService.deleteBusinessType( buesinessTypeVO );
            }

            insertlog( request, buesinessTypeVO, Operate.DELETE, null, buesinessTypeVO.getSelectedIds() );
         }

         // ��ʼ�������־ö���
         constantsInit( "initBusinessType", getAccountId( request, response ) );

         // ���Selected IDs����Action
         buesinessTypeVO.setSelectedIds( "" );
         buesinessTypeVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_object_options_ajax_byClientIdForInHouse( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ��ʼ������ѡ��
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // ��ʼ��Service�ӿ�
         final BusinessTypeService businessTypeService = ( BusinessTypeService ) getService( "businessTypeService" );
         // ����clientContractVO���������������Ҫ��clientContractVOs
         List< Object > businessTypes = businessTypeService.getBusinessTypeVOsByAccountId( getAccountId( request, response ) );
         List< Object > businessTypesInHouse = new ArrayList< Object >();

         final String corpId = getCorpId( request, response );

         for ( Object o : businessTypes )
         {
            if ( corpId.equals( ( ( BusinessTypeVO ) o ).getCorpId() ) )
            {
               businessTypesInHouse.add( o );
            }
         }

         if ( businessTypesInHouse != null && businessTypesInHouse.size() > 0 )
         {
            for ( Object object : businessTypesInHouse )
            {
               final BusinessTypeVO businessTypeVO = ( BusinessTypeVO ) object;
               // ��������Ļ���
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( businessTypeVO.getBusinessTypeId() );
                  mappingVO.setMappingValue( businessTypeVO.getNameZH() );
                  mappingVOs.add( mappingVO );
               }
               else if ( request.getLocale().getLanguage().equalsIgnoreCase( "EN" ) )
               {
                  MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( businessTypeVO.getBusinessTypeId() );
                  mappingVO.setMappingValue( businessTypeVO.getNameEN() );
                  mappingVOs.add( mappingVO );
               }

            }
         }
         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "businessTypeId", null ) );
         out.flush();
         out.close();
         businessTypes = null;
         businessTypesInHouse = null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "" );
   }

   public void getBusinessTypeList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      response.setContentType( "application/json;charset=UTF-8" );
      response.setCharacterEncoding( "UTF-8" );

      List< BusinessTypeVO > businessTypeVOList = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).BUSINESS_TYPE_VO;
      List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
      for ( BusinessTypeVO businessTypeVO : businessTypeVOList )
      {
         if ( StringUtils.equals( businessTypeVO.getCorpId(), BaseAction.getCorpId( request, response ) ) )
         {
            Map< String, String > map = new HashMap< String, String >();
            map.put( "id", businessTypeVO.getBusinessTypeId() );
            map.put( "name", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? businessTypeVO.getNameZH() : businessTypeVO.getNameEN() );
            listReturn.add( map );
         }
      }

      JSONArray json = JSONArray.fromObject( listReturn );
      try
      {
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( IOException e )
      {
         e.printStackTrace();
      }
   }
}
