package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.IndustryTypeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.IndustryTypeService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class IndustryTypeAction extends BaseAction
{
	public static final String accessAction = "HRO_MGT_INDUSTRY_TYPE";
   /**  
    * List Object
    *	 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
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
         final IndustryTypeService industryTypeService = ( IndustryTypeService ) getService( "industryTypeService" );
         // ���Action Form
         final IndustryTypeVO industryTypeVO = ( IndustryTypeVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         industryTypeVO.setAccountId( getAccountId( request, response ) );
         // ����ɾ������
         if ( industryTypeVO.getSubAction() != null && industryTypeVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( industryTypeVO );
         }

         // ���û��ָ��������Ĭ�ϰ� typeId����
         if ( industryTypeVO.getSortColumn() == null || industryTypeVO.getSortColumn().isEmpty() )
         {
            industryTypeVO.setSortColumn( "typeId" );
            industryTypeVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder industryTypeHolder = new PagedListHolder();
         // ���뵱ǰҳ
         industryTypeHolder.setPage( page );
         // ���뵱ǰֵ����
         industryTypeHolder.setObject( industryTypeVO );
         // ����ҳ���¼����
         industryTypeHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         industryTypeService.getIndustryTypeVOsByCondition( industryTypeHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( industryTypeHolder, request );
         // Holder��д��Request����
         request.setAttribute( "industryTypeHolder", industryTypeHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            return mapping.findForward( "listIndustryTypeTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listIndustryType" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( IndustryTypeVO ) form ).setStatus( IndustryTypeVO.TRUE );
      ( ( IndustryTypeVO ) form ).setSubAction( CREATE_OBJECT );
      // ��ת���½�����  
      return mapping.findForward( "manageIndustryType" );
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
            final IndustryTypeService industryTypeService = ( IndustryTypeService ) getService( "industryTypeService" );

            // ��õ�ǰFORM
            final IndustryTypeVO industryTypeVO = ( IndustryTypeVO ) form;
            industryTypeVO.setCreateBy( getUserId( request, response ) );
            industryTypeVO.setModifyBy( getUserId( request, response ) );
            industryTypeVO.setAccountId( getAccountId( request, response ) );
            industryTypeService.insertIndustryType( industryTypeVO );

            // ��ʼ������
            constantsInit( "initIndustryType", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }

         // ���Form
         ( ( IndustryTypeVO ) form ).reset();
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
         final IndustryTypeService industryTypeService = ( IndustryTypeService ) getService( "industryTypeService" );
         // ������ȡ�����
         final String industryTypeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "typeId" ), "UTF-8" ) );
         // ���IndustryTypeVO����                                                                                          
         final IndustryTypeVO industryTypeVO = industryTypeService.getIndustryTypeVOByIndustryTypeId( industryTypeId );
         // ����Add��Update
         industryTypeVO.setSubAction( VIEW_OBJECT );
         industryTypeVO.reset( null, request );
         // ��IndustryTypeVO����request����
         request.setAttribute( "industryTypeForm", industryTypeVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageIndustryType" );
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
            final IndustryTypeService industryTypeService = ( IndustryTypeService ) getService( "industryTypeService" );
            // ������ȡ�����
            final String industryTypeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "typeId" ), "UTF-8" ) );
            // ��ȡIndustryTypeVO����
            final IndustryTypeVO industryTypeVO = industryTypeService.getIndustryTypeVOByIndustryTypeId( industryTypeId );
            // װ�ؽ��洫ֵ
            industryTypeVO.update( ( IndustryTypeVO ) form );
            // ��ȡ��¼�û�
            industryTypeVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            industryTypeService.updateIndustryType( industryTypeVO );

            // ��ʼ������
            constantsInit( "initIndustryType", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }
         // ���Form
         ( ( IndustryTypeVO ) form ).reset();
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
         final IndustryTypeService industryTypeService = ( IndustryTypeService ) getService( "industryTypeService" );
         // ���Action Form
         final IndustryTypeVO industryTypeVO = ( IndustryTypeVO ) form;
         // ����ѡ�е�ID
         if ( industryTypeVO.getSelectedIds() != null && !industryTypeVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : industryTypeVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               industryTypeVO.setTypeId( selectedId );
               industryTypeVO.setAccountId( getAccountId( request, response ) );
               industryTypeVO.setModifyBy( getUserId( request, response ) );
               industryTypeService.deleteIndustryType( industryTypeVO );
            }
         }

         // ��ʼ������
         constantsInit( "initIndustryType", getAccountId( request, response ) );

         // ���Selected IDs����Action
         industryTypeVO.setSelectedIds( "" );
         industryTypeVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * List Object Options Ajax(��ʾ��ҵ����������)  
    * @param   name  
    * @param  @return    �趨�ļ�  
    * @return String    DOM����  
    * @Exception �쳣����  
    * @since  CodingExample��Ver(���뷶���鿴) 1.1  
    */
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
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         // ��ȡtypeId
         final String typeId = request.getParameter( "typeId" );

         // ���typeId��Ϊ��
         if ( typeId != null && !typeId.equals( "" ) )
         {
            // ��ʼ��Service�ӿ�
            final IndustryTypeService industryTypeService = ( IndustryTypeService ) getService( "industryTypeService" );
            // ʵ����industryTypeVO���ڲ�ѯ
            IndustryTypeVO industryTypeVO = new IndustryTypeVO();
            industryTypeVO.setAccountId( getAccountId( request, response ) );
            final List< IndustryTypeVO > industryTypeVOs = industryTypeService.getIndustryTypeVOsByIndustryTypeVO( industryTypeVO );
            // �����ϵ�˴������������ӵ�����ѡ����
            if ( industryTypeVOs != null && industryTypeVOs.size() > 0 )
            {
               for ( IndustryTypeVO industryTypeVOTemp : industryTypeVOs )
               {
                  // ��������Ļ���
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( industryTypeVOTemp.getTypeId() );
                     mappingVO.setMappingValue( industryTypeVOTemp.getNameZH() );
                     mappingVOs.add( mappingVO );
                  }
                  // ��������Ļ���
                  else
                  {
                     MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( industryTypeVOTemp.getTypeId() );
                     mappingVO.setMappingValue( industryTypeVOTemp.getNameEN() );
                     mappingVOs.add( mappingVO );
                  }
               }
            }
         }
         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "typeId", typeId ) );
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

}
