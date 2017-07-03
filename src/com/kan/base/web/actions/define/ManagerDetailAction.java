package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ManagerDetailVO;
import com.kan.base.domain.define.ManagerHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ColumnService;
import com.kan.base.service.inf.define.ManagerDetailService;
import com.kan.base.service.inf.define.ManagerHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ManagerDetailAction extends BaseAction
{
   public static String accessAction = "HRO_DEFINE_PAGE";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );

         // ����Ƿ�Ajax����
         final String ajax = getAjax( request );

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ���Action Form
         final ManagerDetailVO managerDetailVO = ( ManagerDetailVO ) form;

         // ����SubAction
         dealSubAction( managerDetailVO, mapping, form, request, response );

         // ��ʼ��Service�ӿ�
         final ManagerHeaderService managerHeaderService = ( ManagerHeaderService ) getService( "managerHeaderService" );
         final ManagerDetailService managerDetailService = ( ManagerDetailService ) getService( "managerDetailService" );

         // �����������
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = managerDetailVO.getManagerHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ����������
         final ManagerHeaderVO managerHeaderVO = managerHeaderService.getManagerHeaderVOByManagerHeaderId( headerId );

         // ˢ�¹��ʻ�
         managerHeaderVO.reset( null, request );
         // ����SubAction
         managerHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "managerHeaderForm", managerHeaderVO );

         // ����managerHeaderId
         managerDetailVO.setManagerHeaderId( headerId );

         // ���û��ָ��������Ĭ�ϰ� �б��ֶ�˳������
         if ( managerDetailVO.getSortColumn() == null || managerDetailVO.getSortColumn().isEmpty() )
         {
            managerDetailVO.setSortColumn( "columnIndex" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder managerDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         managerDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         managerDetailHolder.setObject( managerDetailVO );
         // ����ҳ���¼����
         managerDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         managerDetailService.getManagerDetailVOsByCondition( managerDetailHolder, true );

         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( managerDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "managerDetailHolder", managerDetailHolder );

         // ��ʼ���ֶ�
         final List< MappingVO > columns = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( managerHeaderVO.getTableId() ).getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );

         managerDetailVO.setColumns( columns );
         managerDetailVO.setColumnIndex( "0" );
         managerDetailVO.setDisplay( ManagerDetailVO.TRUE );
         managerDetailVO.setAlign( ManagerDetailVO.TRUE );
         managerDetailVO.setUseTitle( ManagerDetailVO.FALSE );
         managerDetailVO.setStatus( ManagerDetailVO.TRUE );
         managerDetailVO.reset( null, request );
         request.setAttribute( "managerDetailForm", managerDetailVO );

         // ��ʼ���б���ManagerDetailVO��Columns
         for ( Object managerDetailVOObject : managerDetailHolder.getSource() )
         {
            ( ( ManagerDetailVO ) managerDetailVOObject ).setColumns( columns );
         }

         // Ajax Table���ã�ֱ�Ӵ���Detail JSP
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listManagerDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listManagerDetail" );
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
            final ManagerDetailService managerDetailService = ( ManagerDetailService ) getService( "managerDetailService" );

            // ���managerHeaderId
            final String managerHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "managerHeaderId" ), "UTF-8" ) );

            // ��õ�ǰForm
            final ManagerDetailVO managerDetailVO = ( ManagerDetailVO ) form;
            // ��ʼ��ManagerDetailVO����
            managerDetailVO.setManagerHeaderId( managerHeaderId );
            managerDetailVO.setCreateBy( getUserId( request, response ) );
            managerDetailVO.setModifyBy( getUserId( request, response ) );
            managerDetailVO.setAccountId( getAccountId( request, response ) );

            // ���ManagerDetailVO
            managerDetailService.insertManagerDetail( managerDetailVO );

            // ��ʼ�������־ö���
            constantsInit( "initManagerHeader", getAccountId( request, response ) );
            constantsInit( "initTable", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, managerDetailVO, Operate.ADD, managerDetailVO.getManagerDetailId(), null );
         }

         // ���form
         ( ( ManagerDetailVO ) form ).reset();
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
         // ��ȡmanagerDetailId
         final String managerDetailId = KANUtil.decodeString( request.getParameter( "id" ) );

         // ��ʼ��Service�ӿ�
         final ManagerHeaderService managerHeaderService = ( ManagerHeaderService ) getService( "managerHeaderService" );
         final ManagerDetailService managerDetailService = ( ManagerDetailService ) getService( "managerDetailService" );
         final ColumnService columnService = ( ColumnService ) getService( "columnService" );

         // ��ȡManagerDetailVO
         final ManagerDetailVO managerDetailVO = managerDetailService.getManagerDetailVOByManagerDetailId( managerDetailId );

         // ��ȡManagerHeaderVO
         final ManagerHeaderVO managerHeaderVO = managerHeaderService.getManagerHeaderVOByManagerHeaderId( managerDetailVO.getManagerHeaderId() );

         // ��ȡColumnVO
         final ColumnVO columnVO = columnService.getColumnVOByColumnId( managerDetailVO.getColumnId() );

         request.setAttribute( "system_isRequired", ( columnVO != null && columnVO.getIsRequired().equals( "1" ) ) ? "1" : "2" );
         // ���ʻ���ֵ
         managerDetailVO.reset( null, request );
         // ����SubAction
         managerDetailVO.setSubAction( VIEW_OBJECT );

         // ����request����
         request.setAttribute( "managerHeaderForm", managerHeaderVO );
         request.setAttribute( "managerDetailForm", managerDetailVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax Form���ã�ֱ�Ӵ���Form JSP
      return mapping.findForward( "manageManagerDetailForm" );
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
            final String managerDetailId = KANUtil.decodeString( request.getParameter( "managerDetailId" ) );

            // ��ʼ�� Service�ӿ�
            final ManagerDetailService managerDetailService = ( ManagerDetailService ) getService( "managerDetailService" );

            // ��ȡManagerDetailVO
            final ManagerDetailVO managerDetailVO = managerDetailService.getManagerDetailVOByManagerDetailId( managerDetailId );

            // װ�ؽ��洫ֵ
            managerDetailVO.update( ( ManagerDetailVO ) form );

            // �޸�ManagerDetailVO
            managerDetailVO.setModifyBy( getUserId( request, response ) );
            managerDetailService.updateManagerDetail( managerDetailVO );

            // ��ʼ�������־ö���
            constantsInit( "initManagerHeader", getAccountId( request, response ) );
            constantsInit( "initTable", getAccountId( request, response ) );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, managerDetailVO, Operate.MODIFY, managerDetailVO.getManagerDetailId(), null );
         }

         // ���Form
         ( ( ManagerDetailVO ) form ).reset();
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
         final ManagerDetailService managerDetailService = ( ManagerDetailService ) getService( "managerDetailService" );

         // ���Action Form
         ManagerDetailVO managerDetailVO = ( ManagerDetailVO ) form;
         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( managerDetailVO.getSelectedIds() ) != null )
         {
            // �ָ�
            for ( String selectedId : managerDetailVO.getSelectedIds().split( "," ) )
            {
               // ���ɾ������
               final ManagerDetailVO tempManagerDetailVO = managerDetailService.getManagerDetailVOByManagerDetailId( selectedId );
               tempManagerDetailVO.setModifyBy( getUserId( request, response ) );
               tempManagerDetailVO.setModifyDate( new Date() );
               managerDetailService.deleteManagerDetail( tempManagerDetailVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initManagerHeader", getAccountId( request, response ) );
            constantsInit( "initTable", getAccountId( request, response ) );

            insertlog( request, managerDetailVO, Operate.DELETE, null, managerDetailVO.getSelectedIds() );
         }

         // ���Selected IDs����Action
         ( ( ManagerDetailVO ) form ).setSelectedIds( "" );
         ( ( ManagerDetailVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   /***
    * 
    * ��ȡҳ����Ч�ֶ��б�
    */
   public ActionForward list_column_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡsubAction
         final String subAction = request.getParameter( "subAction" );

         // ��ȡtableId
         final String tableId = request.getParameter( "tableId" );

         // ��ȡcolumnId
         final String columnId = request.getParameter( "columnId" );

         // ��ȡmanagerHeaderId
         final String managerHeaderId = request.getParameter( "managerHeaderId" );

         // ��ȡtableId��Ӧ�ֶ��б�
         final List< MappingVO > columns = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( tableId ).getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ) );

         // ��ʼ��Service
         final ManagerDetailService managerDetailService = ( ManagerDetailService ) getService( "managerDetailService" );

         // ��ȡManagerDetailVO�б�
         final List< Object > managerDetailVOs = managerDetailService.getManagerDetailVOsByManagerHeaderId( managerHeaderId );

         // ��ʼ��MappingVO�б�
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

         // �����б��ֶ�
         if ( columns != null && columns.size() > 0 )
         {
            // �������޳��Ѿ�����MangerDetailVO�б���ֶ�
            if ( KANUtil.filterEmpty( subAction ) != null && subAction.equalsIgnoreCase( CREATE_OBJECT ) )
            {
               for ( MappingVO mappingVO : columns )
               {
                  // ������
                  int count = 0;
                  // ����ҳ���ֶ�
                  if ( managerDetailVOs != null && managerDetailVOs.size() > 0 )
                  {
                     for ( Object managerDetailVOObject : managerDetailVOs )
                     {
                        if ( mappingVO.getMappingId().equals( ( ( ManagerDetailVO ) managerDetailVOObject ).getColumnId() ) )
                        {
                           count++;
                           break;
                        }
                     }
                  }

                  if ( count == 0 )
                  {
                     mappingVOs.add( mappingVO );
                  }
               }
            }
            // �޸�
            else
            {
               mappingVOs.addAll( columns );
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "columnId", columnId ) );
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
