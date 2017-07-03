package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ReportColumnVO;
import com.kan.base.domain.define.ReportDTO;
import com.kan.base.domain.define.ReportDetailVO;
import com.kan.base.domain.define.ReportHeaderVO;
import com.kan.base.domain.define.ReportRelationVO;
import com.kan.base.domain.define.ReportSearchDetailVO;
import com.kan.base.domain.define.TableColumnSubVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.define.TableRelationSubVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.page.PagedReportListHolder;
import com.kan.base.service.inf.define.ReportHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.define.ReportRender;

public class ReportHeaderAction extends BaseAction
{
   public static String accessAction = "HRO_DEFINE_REPORT";

   //   private void report_clicks_increase( final HttpServletRequest request, final ReportHeaderService reportHeaderService ) throws KANException
   //   {
   //      // ��ȡ����
   //      final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );
   //
   //      // ��ȡReportHeaderVO
   //      final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );
   //
   //      if ( reportHeaderVO != null && KANUtil.filterEmpty( reportHeaderVO.getClicks() ) != null )
   //      {
   //         reportHeaderVO.setClicks( String.valueOf( Integer.valueOf( reportHeaderVO.getClicks() ) + 1 ) );
   //      }
   //
   //      reportHeaderService.updateReportHeader( reportHeaderVO, null );
   //   }

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
         final ReportHeaderVO reportHeaderVO = ( ReportHeaderVO ) form;

         // �����Action��ɾ��
         if ( reportHeaderVO.getSubAction() != null && reportHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( reportHeaderVO );
         }
         // ��ʼ��Service�ӿ�
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder reportHeaderPagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         reportHeaderPagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         reportHeaderPagedListHolder.setObject( reportHeaderVO );
         // ����ҳ���¼����
         reportHeaderPagedListHolder.setPageSize( listPageSize );
         // ˢ��Holder�����ʻ���ֵ
         reportHeaderVO.reset( null, request );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         reportHeaderService.getReportHeaderVOsByCondition( reportHeaderPagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( reportHeaderPagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "reportHeaderPagedListHolder", reportHeaderPagedListHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            request.setAttribute( "accountId", getAccountId( request, null ) );
            return mapping.findForward( "listReportHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listReportHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ��ʼ��Form����
      ( ( ReportHeaderVO ) form ).setStatus( ReportHeaderVO.TRUE );
      ( ( ReportHeaderVO ) form ).setExportExcelType( "3" );
      ( ( ReportHeaderVO ) form ).setIsExportPDF( ReportHeaderVO.FALSE );
      ( ( ReportHeaderVO ) form ).setIsSearchFirst( ReportHeaderVO.FALSE );
      ( ( ReportHeaderVO ) form ).setUsePagination( "on" );
      ( ( ReportHeaderVO ) form ).setPageSize( "15" );
      ( ( ReportHeaderVO ) form ).setLoadPages( String.valueOf( this.loadPages ) );
      ( ( ReportHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      request.setAttribute( "reportDetailPagedListHolder", new PagedListHolder() );

      // ��ת���½�����
      return mapping.findForward( "manageReportHeader" );
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
            final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

            // ��õ�ǰFORM
            final ReportHeaderVO reportHeaderVO = ( ReportHeaderVO ) form;
            // ����SubAction
            dealSubAction( reportHeaderVO, mapping, form, request, response );
            // �趨��ǰ�û�
            reportHeaderVO.setCreateBy( getUserId( request, response ) );
            reportHeaderVO.setModifyBy( getUserId( request, response ) );
            // Checkbox����
            if ( reportHeaderVO.getUsePagination() != null && reportHeaderVO.getUsePagination().equalsIgnoreCase( "on" ) )
            {
               reportHeaderVO.setUsePagination( ReportHeaderVO.TRUE );
            }
            else
            {
               reportHeaderVO.setUsePagination( ReportHeaderVO.FALSE );
            }
            // ������ӷ���
            reportHeaderService.insertReportHeader( reportHeaderVO );
            // д��request����
            request.setAttribute( "reportHeaderForm", reportHeaderVO );

            // ���ر���ɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, reportHeaderVO, Operate.ADD, reportHeaderVO.getReportHeaderId(), null );
         }
         else
         {
            // ���form
            ( ( ReportHeaderVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ӳɹ�ֱ��ȥ�޸Ľ���
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ������趨һ���Ǻţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         // ������ȡ����request��form��
         String headerId = request.getParameter( "id" );
         ReportHeaderVO reportHeaderVO = null;
         // �޸�
         if ( KANUtil.filterEmpty( headerId ) != null )
         {
            headerId = Cryptogram.decodeString( URLDecoder.decode( headerId, "UTF-8" ) );
            reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );
         }
         else
         {
            reportHeaderVO = ( ReportHeaderVO ) form;
            headerId = reportHeaderVO.getReportHeaderId();
         }

         // װ���ӱ�
         this.loadSubTable( reportHeaderVO, request, response );
         // װ��column �����ȳ�ʼ�ӱ�
         this.loadColumn( reportHeaderVO, request, response );
         // ���ReportHeaderVO
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         reportHeaderVO.reset( null, request );
         // �����޸����
         reportHeaderVO.setSubAction( VIEW_OBJECT );

         // ��ö�ӦReportSearchDetailVO�б�д��request
         final ReportSearchDetailVO reportSearchDetailVO = new ReportSearchDetailVO();
         reportSearchDetailVO.setReportHeaderId( headerId );
         new ReportSearchDetailAction().list_object( mapping, reportSearchDetailVO, request, response );

         // ���÷�ҳ�ֶε�Checkbox
         reportHeaderVO.setUsePagination( reportHeaderVO.getUsePagination() != null && reportHeaderVO.getUsePagination().equals( ReportHeaderVO.TRUE ) ? "on" : "" );
         // д��request����
         request.setAttribute( "reportHeaderForm", reportHeaderVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageReportHeader" );
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
            final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

            // ������ȡ�����
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // �����������
            final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );
            // �޸�ʱtableId �����޸�
            // String tableId = reportHeaderVO.getTableId();
            // װ�ؽ��洫ֵ
            reportHeaderVO.update( ( ( ReportHeaderVO ) form ) );
            // Checkbox����
            if ( reportHeaderVO.getUsePagination() != null && reportHeaderVO.getUsePagination().equalsIgnoreCase( "on" ) )
            {
               reportHeaderVO.setUsePagination( ReportHeaderVO.TRUE );
            }
            else
            {
               reportHeaderVO.setUsePagination( ReportHeaderVO.FALSE );
            }
            // ��ȡ��¼�û�
            reportHeaderVO.setModifyBy( getUserId( request, response ) );

            // װ�����б��ӱ��column ������ɾ���ӱ�ʱ����ɾ�� ������麯��
            Map< String, Map< String, ColumnVO >> tableColumnMap = null;

            if ( StringUtils.isNotBlank( reportHeaderVO.getSortColumns() ) )
            {

               getSubTableColumnsMap( reportHeaderVO, request, response );

            }
            // �����޸ķ���
            reportHeaderService.updateReportHeader( reportHeaderVO, tableColumnMap );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, reportHeaderVO, Operate.MODIFY, reportHeaderVO.getReportHeaderId(), null );
         }
         // ���Form
         ( ( ReportHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * ��������ֶ�
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object_add_sort( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ�� Service�ӿ�
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         // �������
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "reportHeaderId" ) );

         // �����������
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );

         // ���ActionForm
         final ReportHeaderVO reportHeaderForm = ( ReportHeaderVO ) form;

         // ����ֶ�ID
         final String columnId = request.getParameter( "columnId" );

         if ( KANUtil.filterEmpty( reportHeaderVO.getSortColumns() ) != null )
         {
            final JSONObject json = JSONObject.fromObject( reportHeaderVO.getSortColumns() );
            json.accumulate( columnId, reportHeaderForm.getDecodeSortColumns() );
            reportHeaderVO.setSortColumns( json.toString() );
         }
         else
         {
            final String sortString = "{" + columnId + ":\"" + reportHeaderForm.getDecodeSortColumns() + "\"}";
            reportHeaderVO.setSortColumns( JSONObject.fromObject( sortString ).toString() );
         }

         // ��ȡ��¼�û�
         reportHeaderVO.setModifyBy( getUserId( request, response ) );
         reportHeaderVO.setModifyDate( new Date() );
         // �����޸ķ���
         reportHeaderService.updateReportHeader( reportHeaderVO, null );

         reportHeaderVO.reset( null, request );
         request.setAttribute( "reportHeaderForm", reportHeaderVO );

         // ������ӳɹ����
         success( request, MESSAGE_TYPE_UPDATE, null, "MESSAGE_HEADER_SORT" );

         insertlog( request, reportHeaderVO, Operate.MODIFY, reportHeaderVO.getReportHeaderId(), null );

         // ���Form
         ( ( ReportHeaderVO ) form ).reset();

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����ֱ�ӷ���JSPҳ��
      return mapping.findForward( "manageReportHeaderSpecifySort" );
   }

   /**
    * �Ƴ������ֶ�
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object_remove_sort( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ�� Service�ӿ�
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         // �������
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "reportHeaderId" ) );

         // ����ֶ�ID
         final String columnId = request.getParameter( "columnId" );

         // �����������
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );

         final JSONObject jsonObject = JSONObject.fromObject( reportHeaderVO.getSortColumns() );
         jsonObject.remove( columnId );

         if ( jsonObject.keySet().size() == 0 )
         {
            reportHeaderVO.setSortColumns( null );
         }
         else
         {
            reportHeaderVO.setSortColumns( jsonObject.toString() );
         }

         // ��ȡ��¼�û�
         reportHeaderVO.setModifyBy( getUserId( request, response ) );
         reportHeaderVO.setModifyDate( new Date() );
         // �����޸ķ���
         reportHeaderService.updateReportHeader( reportHeaderVO, null );

         reportHeaderVO.reset( null, request );
         request.setAttribute( "reportHeaderForm", reportHeaderVO );

         // ����ɾ���ɹ����
         success( request, MESSAGE_TYPE_DELETE, null, "MESSAGE_HEADER_SORT" );

         insertlog( request, reportHeaderVO, Operate.MODIFY, reportHeaderVO.getReportHeaderId(), "modify_object_remove_sort" );

         // ���Form
         ( ( ReportHeaderVO ) form ).reset();

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����ֱ�ӷ���JSPҳ��
      return mapping.findForward( "manageReportHeaderSpecifySort" );
   }

   /**
    * ��ӷ����ֶ�
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object_add_group( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ�� Service�ӿ�
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         // �������
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "reportHeaderId" ) );

         // ���ActionForm
         final ReportHeaderVO reportHeaderForm = ( ReportHeaderVO ) form;

         // ����ֶ�ID
         final String columnId = request.getParameter( "columnId" );

         // �����������
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );

         if ( KANUtil.filterEmpty( columnId ) != null )
         {
            // ԭ������ڷ����ֶ�
            if ( KANUtil.filterEmpty( reportHeaderVO.getGroupColumns() ) != null )
            {
               final String oldStr = reportHeaderVO.getGroupColumns().replace( "{", "" ).replace( "}", "" ) + ":" + columnId;
               reportHeaderVO.setGroupColumns( KANUtil.toJasonArray( oldStr.split( ":" ) ) );
            }
            else
            {
               reportHeaderVO.setGroupColumns( "{" + columnId + "}" );
            }

            JSONObject jsonObject = null;
            // ԭ�������ͳ���ֶ�
            if ( KANUtil.filterEmpty( reportHeaderVO.getStatisticsColumns() ) != null )
            {
               jsonObject = JSONObject.fromObject( reportHeaderVO.getStatisticsColumns() );
               jsonObject.put( columnId, reportHeaderForm.getDecodeStatisticsColumns() );
            }
            else
            {
               jsonObject = new JSONObject();
               jsonObject.put( columnId, reportHeaderForm.getDecodeStatisticsColumns() );
            }

            reportHeaderVO.setStatisticsColumns( jsonObject.toString() );

            // ��ȡ��¼�û�
            reportHeaderVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            reportHeaderService.updateReportHeader( reportHeaderVO, null );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, "MESSAGE_HEADER_GROUP" );

            insertlog( request, reportHeaderVO, Operate.MODIFY, reportHeaderVO.getReportHeaderId(), "modify_object_add_group" );
         }

         reportHeaderVO.reset( null, request );
         request.setAttribute( "reportHeaderForm", reportHeaderVO );

         // ���Form
         ( ( ReportHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����ֱ�ӷ���JSPҳ��
      return mapping.findForward( "manageReportHeaderSpecifyGroup" );
   }

   /**
    * �Ƴ������ֶ�
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object_remove_group( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ�� Service�ӿ�
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         // �������
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "reportHeaderId" ) );

         // ��ȡcolumnId
         final String columnId = request.getParameter( "columnId" );

         // �����������
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );

         final List< String > groupColumns = KANUtil.jasonArrayToStringList( reportHeaderVO.getGroupColumns() );

         final List< String > tempGroupColumns = new ArrayList< String >();

         if ( groupColumns != null && groupColumns.size() > 0 )
         {
            for ( String groupColumn : groupColumns )
            {
               if ( !groupColumn.equals( columnId ) )
               {
                  tempGroupColumns.add( groupColumn );
               }
            }
         }

         reportHeaderVO.setGroupColumns( KANUtil.stringListToJasonArray( tempGroupColumns ) );

         // ���ԭ�������ͳ���ֶ�
         if ( KANUtil.filterEmpty( reportHeaderVO.getStatisticsColumns() ) != null )
         {
            final JSONObject jsonObject = JSONObject.fromObject( reportHeaderVO.getStatisticsColumns() );
            for ( Object objKey : jsonObject.keySet() )
            {
               if ( String.valueOf( objKey ).equals( columnId ) )
               {
                  jsonObject.remove( objKey );
                  if ( jsonObject.size() == 0 )
                  {
                     reportHeaderVO.setStatisticsColumns( null );
                  }
                  else
                  {
                     reportHeaderVO.setStatisticsColumns( jsonObject.toString() );
                  }
                  break;
               }
            }
         }

         // ��ȡ��¼�û�
         reportHeaderVO.setModifyBy( getUserId( request, response ) );
         reportHeaderVO.setModifyDate( new Date() );
         // �����޸ķ���
         reportHeaderService.updateReportHeader( reportHeaderVO, null );

         reportHeaderVO.reset( null, request );
         request.setAttribute( "reportHeaderForm", reportHeaderVO );

         // ����ɾ���ɹ����
         success( request, MESSAGE_TYPE_DELETE, null, "MESSAGE_HEADER_GROUP" );

         insertlog( request, reportHeaderVO, Operate.MODIFY, reportHeaderVO.getReportHeaderId(), "modify_object_remove_group" );

         // ���Form
         ( ( ReportHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����ֱ�ӷ���JSPҳ��
      return mapping.findForward( "manageReportHeaderSpecifyGroup" );
   }

   /**
    * ȷ�Ϸ���
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object_publish( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ�� Service�ӿ�
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         // �������
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "reportHeaderId" ) );

         // �����������
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );

         // ��õ�ǰform
         final ReportHeaderVO reportHeaderForm = ( ReportHeaderVO ) form;

         reportHeaderVO.setIsPublic( reportHeaderForm.getIsPublic() );
         reportHeaderVO.setModuleType( reportHeaderForm.getModuleType() );
         reportHeaderVO.setPositionIds( KANUtil.toJasonArray( reportHeaderForm.getPositionIdArray() ) );
         reportHeaderVO.setPositionGradeIds( KANUtil.toJasonArray( reportHeaderForm.getPositionGradeIdArray() ) );
         reportHeaderVO.setPositionGroupIds( KANUtil.toJasonArray( reportHeaderForm.getGroupIdArray() ) );

         // ����Ϊ����״̬
         reportHeaderVO.setStatus( "2" );
         // ��ȡ��¼�û�
         reportHeaderVO.setModifyBy( getUserId( request, response ) );
         // �����޸ķ���
         reportHeaderService.updateReportHeader( reportHeaderVO, null );

         reportHeaderVO.reset( null, request );
         // �޸ĺ����д��request������
         request.setAttribute( "reportHeaderForm", reportHeaderVO );

         // ������ӳɹ����
         success( request, MESSAGE_TYPE_UPDATE, "�����ɹ���", "MESSAGE_HEADER_PUBLISH" );

         insertlog( request, reportHeaderVO, Operate.MODIFY, reportHeaderVO.getReportHeaderId(), "modify_object_publish" );

         // ��ʼ�������־ö���
         String[] parametersT = { getAccountId( request, response ), reportHeaderVO.getTableId() };
         constantsInit( "initTableReport", parametersT );
         String[] parametersM = { getAccountId( request, response ), reportHeaderVO.getReportHeaderId() };
         // ��ʼ�������־ö���
         constantsInit( "initStaffMenu", parametersM );
         // ���Form
         ( ( ReportHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax���÷���JSPҳ��
      return mapping.findForward( "manageReportHeaderConfirmPublish" );
   }

   /**
    * ִ�б���
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward execute_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ�� Service�ӿ�
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );
         // final TableService tableService = (TableService)
         // getService("tableService");
         // ���������
         // report_clicks_increase( request, reportHeaderService );

         // ��ȡģ�����ƣ�����CSS��ʽ
         final String moduleName = request.getParameter( "moduleName" );

         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ��ǰForm
         final ReportHeaderVO reportHeaderForm = ( ReportHeaderVO ) form;

         // ���SubAction
         String subAction = getSubAction( form );
         if ( StringUtils.isBlank( subAction ) )
         {
            subAction = request.getParameter( "subAction" );
            if ( StringUtils.isBlank( subAction ) )
            {
               subAction = "searchObject";
            }
         }

         // ����SubAction
         dealSubAction( reportHeaderForm, mapping, form, request, response );

         // ����Ƿ�Ajax����
         String ajax = getAjax( request );

         // ��ȡ����
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );

         // �����������
         final ReportHeaderVO reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( headerId );

         request.setAttribute( "headerId", headerId );
         request.setAttribute( "tableId", reportHeaderVO.getTableId() );
         reportHeaderVO.setAccountId( getAccountId( request, response ) );
         reportHeaderVO.setCorpId( getCorpId( request, response ) );

         //д������ 
         request.setAttribute( "sortColumn", reportHeaderForm.getSortColumn() );
         request.setAttribute( "sortOrder", reportHeaderForm.getSortOrder() );
         //�����Ĭ������ json 
         request.setAttribute( "defaultSortColumn", reportHeaderVO.getSortColumns() );
         // ��׼����
         if ( subAction.equals( APPROVE_OBJECT ) && StringUtils.isBlank( page ) )
         {
            // ��һ��ִ�г�ʼ��ʼ����
            // ����ʱ�ڸ��»���
            String[] parameters = { getAccountId( request, response ), reportHeaderVO.getTableId() };
            constantsInit( "initTableReport", parameters );
            // ��ȡTableDTO ���ݿ���ȡ������
            // tableDTO =
            // tableService.getTableDTOByTableId(BaseAction.getAccountId(request,
            // null), reportHeaderVO.getTableId());
         }

         final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByTableId( reportHeaderVO.getTableId() );

         // ����DTO
         final ReportDTO tempReportDTO = tableDTO.getReportDTOByReportHeaderId( headerId );
         // �˵�ѡ��
         if ( KANUtil.filterEmpty( moduleName ) != null )
         {
            request.setAttribute( "moduleName", moduleName );
         }

         // ��ʼ��PagedListHolder
         final PagedReportListHolder pagedListHolder = new PagedReportListHolder();

         // // ����õ���ǰ�������
         // final Class<?> clazz = Class.forName(tableDTO.getTableVO()
         // .getClassName());
         // Object object = clazz.newInstance();

         // ��ʼ���ӱ��ϵ��
         List< ReportRelationVO > reportRelationList = tempReportDTO.getReportRelationVOs();
         Map< String, TableDTO > subTableMap = new HashMap< String, TableDTO >();

         Map< String, ColumnVO > columnMap = new HashMap< String, ColumnVO >();

         TableDTO subTableDTO = null;
         // ת������columnVO ���� �ӱ�
         // ����
         for ( ColumnVO columnVO : tableDTO.getAllColumnVO() )
         {
            columnMap.put( columnVO.getColumnId(), columnVO );
         }

         // װ���ӱ�
         for ( ReportRelationVO reportRelationVO : reportRelationList )
         {

            subTableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( reportRelationVO.getSlaveTableId() );
            subTableMap.put( reportRelationVO.getSlaveTableId(), subTableDTO );
            // �ӱ� ��
            for ( ColumnVO columnVO : subTableDTO.getAllColumnVO() )
            {
               columnMap.put( columnVO.getColumnId(), columnVO );
            }

         }

         // final ReportDTO tempReportDTO =
         // tableDTO.getReportDTOByReportHeaderId( headerId );

         // ��ȡReportSearchDetailVO�б�
         final List< ReportSearchDetailVO > reportSearchDetailVOs = tableDTO.getReportDTOByReportHeaderId( headerId ).getReportSearchDetailVOs();

         // ��������
         // KANUtil.setValue(object, "sortColumn",
         // reportHeaderForm.getSortColumn());
         // KANUtil.setValue(object, "sortOrder",
         // reportHeaderForm.getSortOrder());

         // �������������
         if ( !tempReportDTO.isSearchFirst() || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS )
               || subAction.equalsIgnoreCase( APPROVE_OBJECT ) )
         {
            // ��ʼ����ʱReportDTO
            ReportDTO reportDTO = null;
            // ��ʼ����ʱReportSearchDetailVO�б�
            List< ReportSearchDetailVO > tempReportSearchDetailVOs = new ArrayList< ReportSearchDetailVO >();
            reportDTO = new ReportDTO();
            // ҳ������
            String sortColumns = null;
            // ���������
            if ( ( subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( APPROVE_OBJECT ) || new Boolean( getAjax( request ) ) )
                  && !tempReportDTO.isExportFirstAndSearchFirst() )
            {

               // ���������ֶ�
               if ( KANUtil.filterEmpty( reportHeaderForm.getSortColumn() ) != null && KANUtil.filterEmpty( reportHeaderForm.getSortOrder() ) != null )
               {
                  sortColumns = reportHeaderForm.getSortColumn() + " " + reportHeaderForm.getSortOrder();
                  // reportHeaderVO.setSortColumns();
               }

               // װ��ReportHeaderVO
               reportDTO.setReportHeaderVO( reportHeaderVO );

               // List<ReportSearchDetailVO>
               if ( reportSearchDetailVOs != null && reportSearchDetailVOs.size() > 0 )
               {
                  // ����������ReportSearchDetailVO�б�
                  for ( ReportSearchDetailVO reportSearchDetailVO : reportSearchDetailVOs )
                  {
                     // ��ʼ���ֶζ���
                     final ColumnVO columnVO = columnMap.get( reportSearchDetailVO.getColumnId() );

                     // ��ʼ����ʱReportSearchDetailVO
                     final ReportSearchDetailVO tempReportSearchDetailVO = new ReportSearchDetailVO();

                     // // װ����ʱReportSearchDetailVO
                     tempReportSearchDetailVO.update( reportSearchDetailVO );

                     // request�������ȡ��ǰ�ֶ�
                     String id = "T_" + columnVO.getTableId() + "_" + columnVO.getColumnId();
                     String columnValue = request.getParameter( id );
                     String provinceId = null;
                     // �����ʡ��
                     if ( columnVO.getNameDB().equals( "cityId" ) )
                     {
                        // ���ѡ���˳��� ΪcityId ֻѡ����ʡΪѡ���� ֵ����ʡ��ǰ׺
                        provinceId = request.getParameter( id + "_provinceId" );
                        tempReportSearchDetailVO.setTempStr( provinceId );

                        request.setAttribute( id + "_provinceId", provinceId );
                     }

                     if ( KANUtil.filterEmpty( columnValue ) != null )
                     {
                        columnValue = URLDecoder.decode( URLDecoder.decode( columnValue, "UTF-8" ), "UTF-8" );
                     }

                     // ����д��request
                     request.setAttribute( id, columnValue == null ? "" : columnValue );

                     // ��request���û�ȡ��ֵ
                     tempReportSearchDetailVO.setContent( columnValue );
                     // tempReportSearchDetailVO.setSortColumn(sortColumns);
                     tempReportSearchDetailVOs.add( tempReportSearchDetailVO );
                  }
               }

               // װ��ReportSearchDetailVO�б�
               // reportDTO
               // .setReportSearchDetailVOs(tempReportSearchDetailVOs);
               // ����SQL���
            }
            //ReportRender.generateReportSQL( request, response, reportHeaderVO, tableDTO, subTableMap, columnMap, tempReportSearchDetailVOs, sortColumns );
            final String sql = getDecodeSQL();
            //getSQL() ����

            // ���뵱ǰҳ
            pagedListHolder.setPage( page );
            // ���뵱ǰform
            pagedListHolder.setObject( HashMap.class );
            // ����ҳ���¼����
            pagedListHolder.setPageSize( Integer.valueOf( reportHeaderVO.getPageSize() ) );
            // ����Service����
            reportHeaderService.executeReportHeader( sql, pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : tempReportDTO.isPaged() );
            // ���Թ��ʻ�
            // refreshHolsder(pagedListHolder, request);
            // }
         }
         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "columnMap", columnMap );

         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            if ( subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               return new DownloadFileAction().exportReport( mapping, form, request, response );
            }
            else
            {
               // Config the response
               response.setContentType( "text/html" );
               response.setCharacterEncoding( "UTF-8" );
               // ��ʼ��PrintWrite����
               final PrintWriter out = response.getWriter();

               // Send to client
               out.println( ReportRender.generateReportListTable( request, tableDTO, headerId ) );
               printlnUserDefineMessageForAjaxPage( request, response );
               out.flush();
               out.close();

               return null;
            }
         }

         // ��תJSPִ��ҳ��
         return mapping.findForward( "generateReportList" );
         // } else {
         // error(request, null, "���������ִ�У�", MESSAGE_HEADER);
         // return to_objectModify(mapping, form, request, response);
         // }
      }
      catch ( final Exception e )
      {
         error( request, null, "��������ʧ��,���鱨��", MESSAGE_HEADER );
         // System.out.println();
         LogFactory.getLog( ReportHeaderAction.class ).info( "Generate report error :" + e.getMessage() );
         return to_objectModify( mapping, form, request, response );
      }
   }

   /**
    * ԭ����hro_biz_employee_full_view 
    * ������Ҫ����
    * @return
    */
   private String getDecodeSQL()
   {
      StringBuffer sb = new StringBuffer();
      sb.append( "SELECT T_76.employeeId AS T_76_7654,T_76.nameZH AS T_76_7601,T_76.salutation AS T_76_7602,T_76.contractId AS T_76_7663,T_76.contractStatus AS T_76_7667,T_76.salary AS T_76_7668,T_76.allowance AS T_76_7669,T_76.subsidy AS T_76_7670  FROM  " );
      sb.append( " ( SELECT " );
      sb.append( "`a`.`employeeId` AS `employeeId`," );
      sb.append( "`a`.`accountId` AS `accountId`," );
      sb.append( "`a`.`corpId` AS `corpId`," );
      sb.append( "`a`.`employeeNo` AS `employeeNo`," );
      sb.append( "`a`.`nameZH` AS `nameZH`," );
      sb.append( "`a`.`nameEN` AS `nameEN`," );
      sb.append( "`a`.`salutation` AS `salutation`," );
      sb.append( "`a`.`birthday` AS `birthday`," );
      sb.append( "`a`.`maritalStatus` AS `maritalStatus`," );
      sb.append( "`a`.`nationNality` AS `nationNality`," );
      sb.append( "`a`.`birthdayPlace` AS `birthdayPlace`," );
      sb.append( "`a`.`residencyCityId` AS `residencyCityId`," );
      sb.append( "`a`.`residencyAddress` AS `residencyAddress`," );
      sb.append( "`a`.`personalAddress` AS `personalAddress`," );
      sb.append( "`a`.`personalPostcode` AS `personalPostcode`," );
      sb.append( "`a`.`highestEducation` AS `highestEducation`," );
      sb.append( "`a`.`recordNo` AS `recordNo`," );
      sb.append( "`a`.`recordAddress` AS `recordAddress`," );
      sb.append( "`a`.`residencyType` AS `residencyType`," );
      sb.append( "`a`.`graduationDate` AS `graduationDate`," );
      sb.append( "`a`.`startWorkDate` AS `startWorkDate`," );
      sb.append( "`a`.`hasForeignerWorkLicence` AS `hasForeignerWorkLicence`," );
      sb.append( "`a`.`foreignerWorkLicenceNo` AS `foreignerWorkLicenceNo`," );
      sb.append( "`a`.`foreignerWorkLicenceEndDate` AS `foreignerWorkLicenceEndDate`," );
      sb.append( "`a`.`hasResidenceLicence` AS `hasResidenceLicence`," );
      sb.append( "`a`.`residenceNo` AS `residenceNo`," );
      sb.append( "`a`.`residenceStartDate` AS `residenceStartDate`," );
      sb.append( "`a`.`residenceEndDate` AS `residenceEndDate`," );
      sb.append( "`a`.`certificateType` AS `certificateType`," );
      sb.append( "`a`.`certificateNumber` AS `certificateNumber`," );
      sb.append( "`a`.`certificateStartDate` AS `certificateStartDate`," );
      sb.append( "`a`.`certificateEndDate` AS `certificateEndDate`," );
      sb.append( "`a`.`certificateAwardFrom` AS `certificateAwardFrom`," );
      sb.append( "`a`.`bankId` AS `bankId`," );
      sb.append( "`a`.`bankAccount` AS `bankAccount`," );
      sb.append( "`a`.`phone1` AS `phone1`," );
      sb.append( "`a`.`mobile1` AS `mobile1`," );
      sb.append( "`a`.`email1` AS `email1`," );
      sb.append( "`a`.`website1` AS `website1`," );
      sb.append( "`a`.`phone2` AS `phone2`," );
      sb.append( "`a`.`mobile2` AS `mobile2`," );
      sb.append( "`a`.`email2` AS `email2`," );
      sb.append( "`a`.`website2` AS `website2`," );
      sb.append( "`a`.`im1Type` AS `im1Type`," );
      sb.append( "`a`.`im1` AS `im1`," );
      sb.append( "`a`.`im2Type` AS `im2Type`," );
      sb.append( "`a`.`im2` AS `im2`," );
      sb.append( "`a`.`im3Type` AS `im3Type`," );
      sb.append( "`a`.`im3` AS `im3`," );
      sb.append( "`a`.`im4Type` AS `im4Type`," );
      sb.append( "`a`.`im4` AS `im4`," );
      sb.append( "`a`.`branch` AS `branch`," );
      sb.append( "`a`.`owner` AS `owner`," );
      sb.append( "`a`.`photo` AS `photo`," );
      sb.append( "`a`.`attachment` AS `attachment`," );
      sb.append( "`a`.`resumeZH` AS `resumeZH`," );
      sb.append( "`a`.`resumeEN` AS `resumeEN`," );
      sb.append( "`a`.`description` AS `description`," );
      sb.append( "`a`.`deleted` AS `deleted`," );
      sb.append( "`a`.`status` AS `status`," );
      sb.append( "`a`.`remark1` AS `remark1`," );
      sb.append( "`a`.`remark2` AS `remark2`," );
      sb.append( "`a`.`remark3` AS `remark3`," );
      sb.append( "`a`.`remark4` AS `remark4`," );
      sb.append( "`a`.`remark5` AS `remark5`," );
      sb.append( "`a`.`createBy` AS `createBy`," );
      sb.append( "`a`.`createDate` AS `createDate`," );
      sb.append( "`a`.`modifyBy` AS `modifyBy`," );
      sb.append( "`a`.`modifyDate` AS `modifyDate`," );
      sb.append( "`b`.`contractId` AS `contractId`," );
      sb.append( "`b`.`startDate` AS `startDate`," );
      sb.append( "`b`.`endDate` AS `endDate`," );
      sb.append( "`b`.`resignDate` AS `resignDate`," );
      sb.append( "`b`.`status` AS `contractStatus`," );
      //
      sb.append( "SUM((CASE WHEN ((`c`.`itemId` = 1) OR (`c`.`itemId` = 2))" );
      sb.append( "THEN (`c`.`base` - GETINCREMENT(GETPUBLICCODE(`a`.`employeeId`)," );
      sb.append( "'" + KANConstants.PRIVATE_CODE + "')) ELSE 0 END)) AS `salary`," );
      //
      sb.append( "SUM((CASE WHEN ((`c`.`itemId` = 6) OR (`c`.`itemId` = 7)" );
      sb.append( "OR (`c`.`itemId` = 8) OR (`c`.`itemId` = 9))" );
      sb.append( "THEN (`c`.`base` - GETINCREMENT(GETPUBLICCODE(`a`.`employeeId`)," );
      sb.append( "'" + KANConstants.PRIVATE_CODE + "')) ELSE 0 END)) AS `allowance`," );
      //
      sb.append( "SUM((CASE  WHEN ((`c`.`itemId` = 10)" );
      sb.append( "OR (`c`.`itemId` = 11) OR (`c`.`itemId` = 12) OR (`c`.`itemId` = 13))" );
      sb.append( "THEN (`c`.`base` - GETINCREMENT(GETPUBLICCODE(`a`.`employeeId`)," );
      sb.append( "'" + KANConstants.PRIVATE_CODE + "')) ELSE 0 END)) AS `subsidy`" );
      //
      sb.append( "FROM  ((`hro_biz_employee` `a`" );
      sb.append( "LEFT JOIN `hro_biz_employee_contract` `b` ON ((`a`.`employeeId` = `b`.`employeeId`)))" );
      sb.append( "LEFT JOIN `hro_biz_employee_contract_salary` `c` ON ((`b`.`contractId` = `c`.`contractId`)))" );
      sb.append( "WHERE (ISNULL(`b`.`contractId`) OR `b`.`contractId` IN (SELECT " );
      sb.append( "MAX(`hro_biz_employee_contract`.`contractId`)" );
      sb.append( "FROM `hro_biz_employee_contract`" );
      sb.append( "WHERE (`hro_biz_employee_contract`.`status` IN (3 , 5, 6))" );
      sb.append( "GROUP BY `hro_biz_employee_contract`.`employeeId`))" );
      sb.append( "GROUP BY `a`.`employeeId`" );
      sb.append( " ) T_76  WHERE T_76.deleted = 1  AND T_76.accountId = 100017" );
      return sb.toString();
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
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         // ���Action Form
         ReportHeaderVO reportHeaderVO = ( ReportHeaderVO ) form;
         // ����ѡ�е�ID
         if ( reportHeaderVO.getSelectedIds() != null && !reportHeaderVO.getSelectedIds().equals( "" ) )
         {
            insertlog( request, reportHeaderVO, Operate.DELETE, null, reportHeaderVO.getSelectedIds() );
            // �ָ�
            for ( String selectedId : reportHeaderVO.getSelectedIds().split( "," ) )
            {
               reportHeaderVO = reportHeaderService.getReportHeaderVOByReportHeaderId( selectedId );
               reportHeaderVO.setModifyBy( getUserId( request, response ) );
               reportHeaderVO.setModifyDate( new Date() );
               reportHeaderService.deleteReportHeader( reportHeaderVO );
            }

            // ��ʼ�������־ö���
            String[] parametersT = { getAccountId( request, response ), reportHeaderVO.getTableId() };
            constantsInit( "initTableReport", parametersT );
            String[] parametersM = { getAccountId( request, response ), reportHeaderVO.getReportHeaderId() };
            // ��ʼ�������־ö���
            constantsInit( "initStaffMenu", parametersM );
         }

         // ���Selected IDs����Action
         reportHeaderVO.setSelectedIds( "" );
         reportHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward tableId_change_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ���tableId
         final String tableId = request.getParameter( "tableId" );
         // ���TableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( tableId );
         // ��ʼ��JSONObject
         final JSONObject jsonObject = new JSONObject();

         if ( tableDTO != null && tableDTO.getTableVO() != null )
         {
            jsonObject.put( "success", "true" );
            jsonObject.put( "nameZH", tableDTO.getTableVO().getNameZH() );
            jsonObject.put( "nameEN", tableDTO.getTableVO().getNameEN() );

            // �������� ��Ӧ�Ĵӱ� modify steven 2014-04025 tableRelationVO �������Զ�
            // ��ҳ����������ʹ��tableRelationSubVO
            jsonObject.put( "unSelectTables", tableDTO.getTableRelationSubVOs() );
         }
         else
         {
            jsonObject.put( "success", "false" );
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

   public ActionForward tableId_change_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ���tableId
         final String tableId = request.getParameter( "id" );

         // ��ʼ��ReportSearchHeaderVO�б�
         final List< MappingVO > searchHeaderVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSearchHeadersByTableId( tableId, KANUtil.filterEmpty( getCorpId( request, null ) ), request.getLocale().getLanguage() );
         searchHeaderVOs.add( 0, ( ( ReportHeaderVO ) form ).getEmptyMappingVO() );

         // Send to client
         out.println( KANUtil.getSelectHTML( searchHeaderVOs, "searchHeaderId", "searchHeaderId", null, null, null ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   @SuppressWarnings("unchecked")
   public ActionForward condition_change_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ���columnId
         final String columnId = request.getParameter( "columnId" );

         // ���ColumnVO
         final ColumnVO columnVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getColumnVOByColumnId( columnId );

         // ��ʼ��MappingVO�б�
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

         // ��ʼ��StringBuffer
         final StringBuffer rs = new StringBuffer();

         if ( columnVO != null )
         {
            // ���������� - ϵͳ����
            if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "1" ) )
            {
               // ���ϵͳ����ѡ���б�
               final List< MappingVO > systemOptions = KANUtil.getMappings( request.getLocale(), "def.column.option.type.system" );
               // ����ϵͳ����ѡ��
               if ( systemOptions != null && systemOptions.size() > 0 )
               {
                  for ( MappingVO systemOption : systemOptions )
                  {
                     // ���ϵͳ����ѡ��
                     if ( systemOption.getMappingId() != null && systemOption.getMappingId().trim().equals( columnVO.getOptionValue() ) )
                     {
                        mappingVOs = KANUtil.getMappings( request.getLocale(), systemOption.getMappingTemp() );
                        break;
                     }
                  }
               }
            }
            // ���������� - �˻�����
            else if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "2" ) )
            {
               // ����˻�����ѡ���б�
               final List< MappingVO > accountOptions = KANUtil.getMappings( request.getLocale(), "def.column.option.type.account" );
               // �����˻�����ѡ��
               if ( accountOptions != null && accountOptions.size() > 0 )
               {
                  for ( MappingVO accountOption : accountOptions )
                  {
                     // ����˻�����ѡ��
                     if ( accountOption.getMappingId() != null && accountOption.getMappingId().trim().equals( columnVO.getOptionValue() ) )
                     {
                        // ��ʼ��Parameter Array
                        String parameters[];

                        if ( KANUtil.filterEmpty( getClientId( request, response ) ) != null )
                        {
                           parameters = new String[] { request.getLocale().getLanguage(), getClientId( request, response ) };
                        }
                        else
                        {
                           parameters = new String[] { request.getLocale().getLanguage() };
                        }

                        mappingVOs = ( List< MappingVO > ) KANUtil.getValue( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ), accountOption.getMappingTemp(), parameters );
                        // ��ӿյ�MappingVO����
                        mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
                        break;
                     }
                  }
               }
            }
            // ���������� - �û��Զ���
            else if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "3" ) )
            {
               mappingVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getColumnOptionDTOByOptionHeaderId( columnVO.getOptionValue() ).getOptions( request.getLocale().getLanguage() );
            }
            // ���������� - ֱ��ֵ
            else if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "4" ) )
            {
               // ����û������ֱ��ֵ���Ҳ�Ϊ��
               if ( columnVO.getOptionValue() != null && !columnVO.getOptionValue().trim().equals( "" ) )
               {
                  // ���û������ֱ��ֵתΪJSONObject
                  final JSONObject optionsJSONObject = JSONObject.fromObject( columnVO.getOptionValue().replace( "[{", "{" ).replace( "}]", "}" ) );
                  // ����JSONObject
                  final Iterator< ? > keyIterator = optionsJSONObject.keys();
                  while ( keyIterator.hasNext() )
                  {
                     final String key = ( String ) keyIterator.next();
                     // ��ʼ��MappingVO
                     final MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( key );
                     mappingVO.setMappingValue( optionsJSONObject.getString( key ) );
                     // ���MappingVO��List
                     mappingVOs.add( mappingVO );
                  }
               }
            }

            rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "define.report.detail.condition" ) + " </label>" );

            if ( mappingVOs != null && mappingVOs.size() > 0 )
            {
               // ���⴦�� ����ֶ����������š�ʡ�� - ����
               if ( KANUtil.filterEmpty( columnVO.getNameDB() ) != null && columnVO.getNameDB().equals( "branch" ) )
               {
                  rs.append( KANUtil.getSelectHTML( mappingVOs, "branch", "small branch", null, null, null ) );

                  // ��ʼ��Ԥ��������
                  final List< MappingVO > tempMappingVOs = new ArrayList< MappingVO >();
                  tempMappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

                  rs.append( KANUtil.getSelectHTML( tempMappingVOs, "owner", "small owner", null, null, null ) );
               }
               else if ( KANUtil.filterEmpty( columnVO.getNameDB() ) != null && columnVO.getNameDB().equals( "provinceId" ) )
               {
                  rs.append( KANUtil.getSelectHTML( mappingVOs, "provinceId", "provinceId", null, null, null ) );
               }
               else
               {
                  rs.append( "<select name=\"content\" id=\"\" class=\"manageReportSearchDetail_content\">" );
                  rs.append( KANUtil.getOptionHTML( mappingVOs, "content", null ) );
                  rs.append( "</select>" );
               }
            }
            else
            {
               rs.append( "<input type=\"text\" name=\"content\" maxlength=\"100\" class=\"manageReportSearchDetail_content\" />" );
            }
         }

         // Send to client
         out.print( rs.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   public ActionForward list_options_ajax_byAccountId( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ�� Service
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         final PagedListHolder pagedListHolder = new PagedListHolder();

         final ReportHeaderVO reportHeaderVO = new ReportHeaderVO();

         reportHeaderVO.setAccountId( getAccountId( request, response ) );

         pagedListHolder.setObject( reportHeaderVO );

         reportHeaderService.getReportHeaderVOsByCondition( pagedListHolder, false );

         // ��ʼ������ѡ��
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         MappingVO mappingVO = null;

         ReportHeaderVO tempReportHeaderVO = null;
         if ( pagedListHolder != null && pagedListHolder.getSource().size() > 0 )
         {
            for ( Object object : pagedListHolder.getSource() )
            {
               mappingVO = new MappingVO();
               tempReportHeaderVO = ( ReportHeaderVO ) object;
               // ��������Ļ���
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingId( tempReportHeaderVO.getReportHeaderId() );
                  mappingVO.setMappingValue( tempReportHeaderVO.getNameZH() );
                  mappingVOs.add( mappingVO );
               }
               else if ( request.getLocale().getLanguage().equalsIgnoreCase( "EN" ) )
               {
                  mappingVO.setMappingId( tempReportHeaderVO.getReportHeaderId() );
                  mappingVO.setMappingValue( tempReportHeaderVO.getNameEN() );
                  mappingVOs.add( mappingVO );
               }
            }
         }

         // Send to client
         final String reportId = request.getParameter( "reportId" );
         out.println( KANUtil.getOptionHTML( mappingVOs, "reportId", KANUtil.filterEmpty( reportId ) == null ? "0" : reportId ) );
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
    * �޸�װ���ӱ�
    * 
    * @param reportHeaderVO
    * @param request
    * @param response
    * @throws KANException
    * @throws InvocationTargetException
    * @throws IllegalAccessException
    */
   private void loadSubTable( ReportHeaderVO reportHeaderVO, final HttpServletRequest request, final HttpServletResponse response ) throws KANException, IllegalAccessException,
         InvocationTargetException
   {

      // ��ʼ��Service�ӿ�
      final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

      // ��ѡ���ӱ�
      List< Object > reportRelationVOs = reportHeaderService.getReportRelationVOsByReportHeaderId( reportHeaderVO.getReportHeaderId() );

      final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( reportHeaderVO.getTableId() );
      // �����ӱ�
      List< TableRelationSubVO > tableRelationSubVOs = tableDTO.getTableRelationSubVOs();

      // װ��
      List< TableRelationSubVO > unSelectTableList = new ArrayList< TableRelationSubVO >();
      List< TableRelationSubVO > selectTableList = new ArrayList< TableRelationSubVO >();
      ReportRelationVO reportRelationVO = null;
      TableRelationSubVO tempTableRelationSubVO = null;
      if ( reportRelationVOs != null && reportRelationVOs.size() > 0 )
      {

         int flag = 0;
         for ( TableRelationSubVO tableRelationSubVO : tableRelationSubVOs )
         {
            flag = 0;
            tempTableRelationSubVO = new TableRelationSubVO();
            BeanUtils.copyProperties( tempTableRelationSubVO, tableRelationSubVO );
            for ( Object object : reportRelationVOs )
            {
               reportRelationVO = ( ReportRelationVO ) object;
               if ( tempTableRelationSubVO.getSlaveTableId() != null && reportRelationVO.getSlaveTableId() != null
                     && tempTableRelationSubVO.getSlaveTableId().equals( reportRelationVO.getSlaveTableId() ) )
               {
                  tempTableRelationSubVO.setReportRelationId( reportRelationVO.getReportRelationId() );
                  selectTableList.add( tempTableRelationSubVO );
                  flag = 1;
                  break;
               }
            }
            if ( 0 == flag )
            {
               unSelectTableList.add( tempTableRelationSubVO );
            }
         }
      }
      else
      {
         unSelectTableList = tableRelationSubVOs;
      }
      reportHeaderVO.setSelectTablesJson( JSONArray.fromObject( selectTableList ).toString() );
      reportHeaderVO.setUnSelectTablesJson( JSONArray.fromObject( unSelectTableList ).toString() );
   }

   /**
    * ��ʼ��columns
    * 
    * @param reportHeaderVO
    * @param request
    * @param response
    * @throws KANException
    */
   private void loadColumn( ReportHeaderVO reportHeaderVO, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );
      final List< Object > reportColumnList = reportHeaderService.getReportColumnVOsByReportHeaderId( reportHeaderVO.getReportHeaderId() );
      final List< TableColumnSubVO > tableColumnSubVOList = new ArrayList< TableColumnSubVO >();
      // �� �й�ϵ ����ҳ����ʾ
      TableColumnSubVO tableColumnSubVO = null;
      List< ColumnVO > clolumnVOList = null;
      ReportDetailVO reportDetailVO = null;
      // ����
      String masterTableId = reportHeaderVO.getTableId();
      tableColumnSubVO = new TableColumnSubVO();

      tableColumnSubVO.setTableId( reportHeaderVO.getTableId() );
      // ������
      tableColumnSubVO.setIsMasterTable( 1 );
      // ��ȡ�������
      TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( masterTableId );

      tableColumnSubVO.setNameEN( tableDTO.getTableVO().getNameEN() );
      tableColumnSubVO.setNameZH( tableDTO.getTableVO().getNameZH() );

      ReportColumnVO reportColumnVO = null;
      if ( tableDTO != null )
      {
         clolumnVOList = tableDTO.getAllColumnVO();

         if ( clolumnVOList != null && clolumnVOList.size() > 0 )
         {
            for ( ColumnVO columnVO : clolumnVOList )
            {
               // �����ݿ��ֶ�
               if ( columnVO != null && ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) || columnVO.getAccountId().equals( getAccountId( request, null ) ) ) )
               {
                  reportColumnVO = new ReportColumnVO();
                  reportColumnVO.setValue( columnVO );
                  for ( Object object : reportColumnList )
                  {
                     reportDetailVO = ( ReportDetailVO ) object;
                     // ��id��ȣ�
                     if ( reportDetailVO.getColumnId().equals( columnVO.getColumnId() ) )
                     {
                        reportColumnVO.setValue( reportDetailVO );
                     }
                  }
                  // �����е�װ��
                  tableColumnSubVO.getReportColumnVOList().add( reportColumnVO );
               }
            }
         }
      }
      // �������
      tableColumnSubVOList.add( tableColumnSubVO );

      if ( reportHeaderVO.getSelectTablesJson() != null && StringUtils.isNotBlank( reportHeaderVO.getSelectTablesJson() ) )
      {

         JSONArray array = JSONArray.fromObject( reportHeaderVO.getSelectTablesJson() );
         TableRelationSubVO[] selectTables = ( TableRelationSubVO[] ) JSONArray.toArray( array, TableRelationSubVO.class );

         if ( selectTables != null && selectTables.length > 0 )
         {
            // �ӱ�
            for ( TableRelationSubVO tableRelationSubVO : selectTables )
            {
               tableColumnSubVO = new TableColumnSubVO();
               tableColumnSubVO.setNameEN( tableRelationSubVO.getSlaveTableNameEN() );
               tableColumnSubVO.setNameZH( tableRelationSubVO.getSlaveTableNameZH() );
               tableColumnSubVO.setTableId( tableRelationSubVO.getSlaveTableId() );
               masterTableId = tableRelationSubVO.getSlaveTableId();
               // ��ȡ�ӱ����
               tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( masterTableId );
               if ( tableDTO != null )
               {
                  clolumnVOList = tableDTO.getAllColumnVO();

                  if ( clolumnVOList != null && clolumnVOList.size() > 0 )
                  {
                     for ( ColumnVO columnVO : clolumnVOList )
                     {
                        if ( columnVO != null
                              && ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) || columnVO.getAccountId().equals( getAccountId( request, null ) ) ) )
                        {

                           reportColumnVO = new ReportColumnVO();
                           reportColumnVO.setValue( columnVO );
                           for ( Object object : reportColumnList )
                           {
                              reportDetailVO = ( ReportDetailVO ) object;
                              // ��id��ȣ�
                              if ( reportDetailVO.getColumnId().equals( columnVO.getColumnId() ) )
                              {
                                 reportColumnVO.setValue( reportDetailVO );
                              }
                           }
                           // �����е�װ��
                           tableColumnSubVO.getReportColumnVOList().add( reportColumnVO );
                        }
                     }
                  }
               }
               // �������
               tableColumnSubVOList.add( tableColumnSubVO );
            }
         }
      }

      // ����columns
      reportHeaderVO.setUnSelectColumnsJson( JSONArray.fromObject( tableColumnSubVOList ).toString() );
      reportHeaderVO.setSelectColumnsJson( reportHeaderVO.getUnSelectColumnsJson() );
   }

   public ActionForward add_column( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύs
         // if ( this.isTokenValid( request, true ) )
         // {
         // ��ʼ��Service �ӿ�
         final ReportHeaderService reportHeaderService = ( ReportHeaderService ) getService( "reportHeaderService" );

         // // ���headerId
         // final String headerId = KANUtil.decodeStringFromAjax(
         // request.getParameter( "reportHeaderId" ) );

         // ��������������������������ã���form��ȡ������ajax��������Σ�
         String headerId = request.getParameter( "reportHeaderId" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = ( ( ReportDetailVO ) form ).getReportHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }
         // ��õ�ǰform
         ReportHeaderVO reportHeaderVO = ( ReportHeaderVO ) form;
         reportHeaderVO.setReportHeaderId( headerId );
         // ����SubAction
         dealSubAction( reportHeaderVO, mapping, form, request, response );
         // ��ȡ��¼�û�
         reportHeaderVO.setCreateBy( getUserId( request, response ) );
         reportHeaderVO.setModifyBy( getUserId( request, response ) );
         reportHeaderVO.setModifyDate( new Date() );
         // ������
         reportHeaderService.updateReportColumn( reportHeaderVO );
         // ���ReportHeaderVO
         // reportHeaderVO =
         // reportHeaderService.getReportHeaderVOByReportHeaderId( headerId
         // );

         // ����columns ��״̬
         this.loadColumn( reportHeaderVO, request, response );
         // ���������ɹ����
         success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

         // ��ʼ�������־ö���
         String[] parameters = { getAccountId( request, response ), reportHeaderVO.getTableId() };
         constantsInit( "initTableReport", parameters );

         request.setAttribute( "reportHeaderForm", reportHeaderVO );

         // // ���Form
         // ((ReportHeaderVO) form).reset();
         // }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageReportDetail" );
   }

   // Refresh the page holder for message resource

   /**
    * ��ȡ�ӱ��column �ļ���
    * 
    * @param reportHeaderVO
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   private Map< String, Map< String, ColumnVO >> getSubTableColumnsMap( final ReportHeaderVO reportHeaderVO, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      Map< String, Map< String, ColumnVO >> tableColumnMap = new HashMap< String, Map< String, ColumnVO >>();
      // ��ʼ��Service�ӿ�

      final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( reportHeaderVO.getTableId() );
      // �����ӱ�
      List< TableRelationSubVO > tableRelationSubVOs = tableDTO.getTableRelationSubVOs();
      Map< String, ColumnVO > columnMap = null;
      List< ColumnVO > columnVOList = null;
      ColumnVO columnVO = null;
      // װ�������ӱ��column
      if ( tableRelationSubVOs != null && tableRelationSubVOs.size() > 0 )
      {
         for ( TableRelationSubVO tableRelationSubVO : tableRelationSubVOs )
         {
            columnVOList = tableDTO.getAllColumnVO();
            if ( columnVOList != null && columnVOList.size() > 0 )
            {
               columnMap = new HashMap< String, ColumnVO >();
               for ( Object object : columnVOList )
               {
                  columnVO = ( ColumnVO ) object;
                  columnMap.put( columnVO.getColumnId(), columnVO );
               }
               tableColumnMap.put( tableRelationSubVO.getSlaveTableId(), columnMap );
            }
         }
      }

      return tableColumnMap;

   }
}
