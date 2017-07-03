package com.kan.base.web.renders.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ImportDTO;
import com.kan.base.domain.define.ImportHeaderVO;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.domain.define.ListHeaderVO;
import com.kan.base.domain.define.OptionDTO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.security.BranchDTO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionDTO;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.system.ModuleDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.tag.AuthConstants;
import com.kan.base.tag.AuthUtils;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeReportVO;
import com.kan.hro.domain.biz.employee.IClickEmployeeReportView;
import com.kan.hro.domain.biz.sb.SpecialDTO;
import com.kan.hro.web.actions.biz.employee.EmployeeSecurityAction;
import com.kan.hro.web.actions.biz.payment.IncomeTaxAction;
import com.kan.hro.web.actions.biz.payment.PayslipViewAction;
import com.kan.hro.web.actions.biz.sb.SBBillViewAction;

/**
 * Render for List Section
 * 
 * @author Kevin
 */
public class ListRender
{
   private final static String[] CROUP_BY_CLIENT_SHOW_COLUMN = new String[] { "clientId", "clientNo", "clientNameZH", "amountCompany", "amountPersonal" };

   private final static String[] CROUP_BY_SOLUTION_SHOW_COLUMN = new String[] { "employeeSBNameZH", "amountCompany", "amountPersonal" };

   public static String generateSpecialList( final HttpServletRequest request, final String javaObjectName, final String accessAction ) throws KANException
   {
      // ��ʼ��corpId
      final String corpId = KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) );

      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();

      // ��ʼ��ListDTO
      final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, corpId );

      // ��ʼ��ModuleDTO
      final ModuleDTO moduleDTO = KANConstants.getModuleDTOByAccessAction( accessAction );

      rs.append( "<div class=\"box noHeader\" id=\"search-results\">" );
      rs.append( "<div class=\"inner\">" );
      rs.append( "<div class=\"top\">" );
      //jason.ji add 2014-05-30 �Y�������������̎�����LIST���o
      if ( "com.kan.hro.domain.biz.settlement.SettlementDTO".equals( javaObjectName ) && "JAVA_OBJECT_SETTLEMENT".equals( accessAction ) )
      {
         rs.append( "<input type=\"button\" class=\"reset\" name=\"btnList\" id=\"btnList\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.list" )
               + "\" onclick=\"link('orderBillHeaderViewAction.do?proc=list_object');\"/>" );
      }
      rs.append( "<a id=\"filterRecords\" name=\"filterRecords\">" + KANUtil.getProperty( request.getLocale(), "set.filerts" ) + "</a>" );

      // �ж��б��Ƿ���Ҫ��ӵ�������
      if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getExportExcel() != null
            && listDTO.getListHeaderVO().getExportExcel().trim().equals( "1" ) )
      {
         if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_EXPORT, "", request, null ) )
         {
            rs.append( "<a id=\"exportExcel\" name=\"exportExcel\" class=\"commonTools\" title=\"" + KANUtil.getProperty( request.getLocale(), "img.title.tips.export.excel" )
                  + "\" onclick=\"popupSelectTemplate('" + javaObjectName + "');\">" );
            rs.append( "<img src=\"images/appicons/excel_16.png\" /></a>" );
         }
      }

      // ����apc(���ޡ�iClick���Ĺ��ʵ��͹��ʱ���)
      if ( BaseAction.getAccountId( request, null ).equals( "100017" )
            && ( javaObjectName.equals( PayslipViewAction.JAVA_OBJECT_NAME ) || javaObjectName.equals( PayslipViewAction.JAVA_OBJECT_NAME_REPORT ) ) )
      {
         rs.append( "<a id=\"exportApc\" name=\"exportApc\" class=\"commonTools\" title=\"" + KANUtil.getProperty( request.getLocale(), "img.title.tips.export.apc" )
               + "\" onclick=\"exportApc();\"><img src=\"images/appicons/txt_16.png\" style=\"margin-right:10px;\" /></a>&nbsp;" );
      }

      // ���������б�˳��
      rs.append( "<a id=\"quickColumnIndex\" name=\"quickColumnIndex\" class=\"commonTools\" title=\""
            + KANUtil.getProperty( request.getLocale(), "img.title.tips.quick.set.list.sequence" )
            + "\"  onclick=\"popupIndexQuick();\";><img src=\"images/trans.png\" style=\"margin-right:10px;\" /></a>&nbsp;" );

      request.setAttribute( "pageAccessAction", accessAction );
      rs.append( "</div>" );
      rs.append( "<div id=\"tableWrapper\">" );
      rs.append( ListRender.generateSpecialListTable( request, javaObjectName ) );
      rs.append( "</div>" );
      rs.append( " <div class=\"bottom\"><p></div>" );
      rs.append( "</div>" );
      rs.append( "</div>" );

      // ������������б�˳��
      final String listAction = ( moduleDTO != null && moduleDTO.getModuleVO() != null ) ? moduleDTO.getModuleVO().getListAction() : "";
      rs.append( generateQuickColumnIndexPopup( request, getListDetailVOs( request, listDTO ), listAction ) );

      return rs.toString();
   }

   public static String generateList( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      return generateList( request, accessAction, false );
   }

   public static String generateList( final HttpServletRequest request, final String accessAction, final boolean useFixTable ) throws KANException
   {
      // ���Log Start����
      LogFactory.getLog( accessAction ).info( "List Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") Start." );

      // ��ȡ��ǰ��Ҫ���ɿؼ���������TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );

      // ��ʼ��ListDTO
      final ListDTO listDTO = tableDTO.getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );

      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();
      final String successMessage = ( String ) request.getAttribute( "MESSAGE" );
      final String successClass = ( String ) request.getAttribute( "MESSAGE_CLASS" );

      // ��ʼ��ModuleDTO
      final ModuleDTO moduleDTO = KANConstants.getModuleDTOByAccessAction( accessAction );

      rs.append( "<div class=\"box noHeader\" id=\"search-results\">" );
      rs.append( "<div class=\"inner\">" );
      rs.append( "<div id=\"messageWrapper\">" );

      if ( successMessage != null && !successMessage.trim().equals( "" ) )
      {
         rs.append( "<div class=\"message " + successClass + " fadable\">" );
         rs.append( successMessage );
         rs.append( "<a onclick=\"$('div.fadable').remove();\" class=\"messageCloseButton\">&nbsp;</a>" );
         rs.append( "</div>" );
      }

      rs.append( "</div>" );

      // ��ʼ����Ӱ�ť�����ӣ���ת�����ҳ�棩
      String toNewAction = "";
      if ( moduleDTO != null && moduleDTO.getModuleVO() != null && moduleDTO.getModuleVO().getToNewAction() != null && !moduleDTO.getModuleVO().getToNewAction().equals( "" ) )
      {
         toNewAction = " onclick=\"link('" + moduleDTO.getModuleVO().getToNewAction() + "');\" ";
      }
      rs.append( "<div class=\"top\">" );
      if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_NEW, "", request, null ) )
      {
         rs.append( "<input type=\"button\" class=\"save\" id=\"btnAdd\" name=\"btnAdd\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.add" ) + "\" " + toNewAction
               + "/>" );
      }

      rs.append( "<input type=\"button\" style=\"display :none;\" class=\"function\" id=\"btnSubmit\" name=\"btnSubmit\" value=\""
            + KANUtil.getProperty( request.getLocale(), "button.submit" ) + "\" />" );

      // ��ӿ��ٴ���Ա����ť
      if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_QUICK_CREATE, "", request, null ) )
      {
         rs.append( "<input type=\"button\" style=\"display :none;\" class=\"function\" id=\"btnQuick\" name=\"btnQuick\" value=\""
               + KANUtil.getProperty( request.getLocale(), "button.quick.create" ) + "\" onclick=\"link('employeeAction.do?proc=to_objectNew&newType=quick');\" />" );
      }

      if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_DELETE, "", request, null ) )
      {
         rs.append( "<input type=\"button\" class=\"delete\" id=\"btnDelete\" name=\"btnDelete\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.delete" )
               + "\" onclick=\"if (kanList_deleteConfirm( null, '" + KANUtil.getProperty( request.getLocale(), "popup.select.delete.records" ) + "', '"
               + KANUtil.getProperty( request.getLocale(), "popup.confirm.delete.records" )
               + "' )) submitForm('list_form', 'deleteObjects', null, null, null, 'tableWrapper');\" />" );
      }
      //��Ա��½����ʾ��ѯ����
      if ( !KANConstants.ROLE_EMPLOYEE.equals( EmployeeSecurityAction.getRole( request, null ) ) )
      {
         rs.append( "<a id=\"filterRecords\" name=\"filterRecords\">" + KANUtil.getProperty( request.getLocale(), "set.filerts" ) + "</a>" );
      }

      // �ж��б��Ƿ���Ҫ��ӵ�������
      if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getExportExcel() != null
            && listDTO.getListHeaderVO().getExportExcel().trim().equals( "1" ) )
      {
         if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_EXPORT, "", request, null ) )
         {
            rs.append( "<a id=\"exportExcel\" name=\"exportExcel\" class=\"commonTools\" title=\"" + KANUtil.getProperty( request.getLocale(), "img.title.tips.export.excel" )
                  + "\" onclick=\"linkForm('list_form', 'downloadObjects', null, 'fileType=excel&accessAction=" + accessAction
                  + "');\"><img src=\"images/appicons/excel_16.png\" /></a>" );
         }
      }

      // ���뵼��Popup
      rs.append( generateImportPopup( request, tableDTO ) );

      // ���������б�˳��
      if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_SORT_LIST, "", request, null ) )
      {
         rs.append( "<a id=\"quickColumnIndex\" name=\"quickColumnIndex\" class=\"commonTools\" title=\""
               + KANUtil.getProperty( request.getLocale(), "img.title.tips.quick.set.list.sequence" )
               + "\"  onclick=\"popupIndexQuick();\";><img src=\"images/trans.png\" style=\"margin-right:10px;\" /></a>&nbsp;" );
      }

      rs.append( "</div>" );
      rs.append( "<div id=\"tableWrapper\">" );
      rs.append( ListRender.generateListTable( request, accessAction, useFixTable ) );
      rs.append( "</div>" );
      rs.append( " <div class=\"bottom\"><p></div>" );
      rs.append( "</div>" );
      rs.append( "</div>" );

      // ������������б�˳��
      final String listAction = ( moduleDTO != null && moduleDTO.getModuleVO() != null ) ? moduleDTO.getModuleVO().getListAction() : "";
      rs.append( generateQuickColumnIndexPopup( request, listDTO.getListDetailVOs(), listAction ) );

      // ���Log End����
      LogFactory.getLog( accessAction ).info( "List Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") End." );

      return rs.toString();
   }

   public static boolean hasImportRight( final HttpServletRequest request, final String tableId ) throws KANException
   {
      final String accountId = BaseAction.getAccountId( request, null );
      final String staffId = BaseAction.getStaffId( request, null );
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );

      final ImportDTO importDTO = accountConstants.getImportDTOByTableId( tableId, BaseAction.getCorpId( request, null ) );
      final List< PositionDTO > positionDTOs = accountConstants.getPositionDTOsByStaffId( staffId );

      // ���Role�ж��Ƿ�Ӧ�̵�¼
      final String roleId = BaseAction.getRole( request, null );

      // �����Ӧ�̵�¼��֧�ֵ���
      if ( roleId.equals( KANConstants.ROLE_VENDOR ) )
      {
         return true;
      }

      if ( positionDTOs != null && importDTO != null )
      {
         final String[] positionIdArray = KANUtil.jasonArrayToStringArray( importDTO.getImportHeaderVO().getPositionIds() );
         final String[] positionGradeIdArray = KANUtil.jasonArrayToStringArray( importDTO.getImportHeaderVO().getPositionGradeIds() );
         final String[] positionGroupIdArray = KANUtil.jasonArrayToStringArray( importDTO.getImportHeaderVO().getPositionGroupIds() );

         for ( PositionDTO positionDTO : positionDTOs )
         {
            PositionVO positionVO = positionDTO.getPositionVO();

            if ( ArrayUtils.contains( positionIdArray, positionVO.getPositionId() ) || ArrayUtils.contains( positionGradeIdArray, positionVO.getPositionGradeId() ) )
            {

               return true;
            }
            for ( PositionGroupRelationVO PositionGroupRelationVO : positionDTO.getPositionGroupRelationVOs() )
            {
               if ( ArrayUtils.contains( positionGroupIdArray, PositionGroupRelationVO.getGroupId() ) )
               {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @SuppressWarnings("unchecked")
   private static boolean isUseFlat( final PagedListHolder pagedListHolder, final ListDTO listDTO ) throws KANException
   {
      // ��ʶ�Ƿ�����
      boolean use = false;

      // ���ListDTO�����Ӷ���
      if ( listDTO != null && listDTO.getSubListDTOs() != null && listDTO.getSubListDTOs().size() > 0 )
      {
         use = true;
      }

      if ( use )
      {
         if ( pagedListHolder != null )
         {
            if ( pagedListHolder.getAdditionalObject() != null )
            {
               final List< ItemVO > items = ( List< ItemVO > ) pagedListHolder.getAdditionalObject();
               if ( items.size() > 0 )
               {
                  for ( ItemVO itemVO : items )
                  {
                     if ( "7".equals( itemVO.getItemType() ) )
                     {
                        return true;
                     }
                  }
               }
            }
            // ������ݲ�Ϊ��
            else if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               for ( Object source : pagedListHolder.getSource() )
               {
                  // ��ʼ��SpecialDTO
                  final SpecialDTO< Object, List< ? > > specialDTO = ( SpecialDTO< Object, List< ? >> ) source;

                  final List< ? > detailVOs = specialDTO.getDetailVOs();

                  if ( detailVOs != null && detailVOs.size() > 0 )
                  {
                     for ( Object objectDetailVO : detailVOs )
                     {
                        final String itemType = ( String ) KANUtil.getValue( objectDetailVO, "itemType" );

                        // Ϊ�籣��Ŀ
                        if ( KANUtil.filterEmpty( itemType ) != null && itemType.equals( "7" ) )
                        {
                           return true;
                        }
                     }
                  }
               }
            }

            return false;
         }

         return false;
      }

      return use;
   }

   // ������ͨTable��Title
   // Add by Siuvan Xia at 2014-5-27
   private static String generateCommonTableTitle( final HttpServletRequest request, final PagedListHolder pagedListHolder, final List< ListDetailVO > listDetailVOs,
         final String pageAccessAction, final String javaObjectName ) throws KANException
   {
      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();

      // �Ҳ����������
      //final String groupColumn = ( String ) KANUtil.getValue( pagedListHolder.getObject(), "groupColumn" );
      final String groupColumn = "0";
      boolean show = true;
      if ( KANUtil.filterEmpty( groupColumn, "0" ) != null )
      {
         show = false;
      }

      rs.append( "<tr>" );
      // ��������
      for ( ListDetailVO listDetailVO : listDetailVOs )
      {
         // �Ƿ���ʾ
         if ( listDetailVO.getDisplay() == null || !listDetailVO.getDisplay().trim().equals( "2" ) )
         {
            if ( isShowColumnId( pageAccessAction, null, listDetailVO.getPropertyName(), request ) )
            {
               continue;
            }

            // ˢ�¹��ʻ�
            listDetailVO.reset( null, request );
            if ( !show
                  && ( ( groupColumn.equals( "1" ) && !ArrayUtils.contains( CROUP_BY_CLIENT_SHOW_COLUMN, listDetailVO.getPropertyName() ) ) || ( groupColumn.equals( "2" ) && !ArrayUtils.contains( CROUP_BY_SOLUTION_SHOW_COLUMN, listDetailVO.getPropertyName() ) ) ) )
            {
               if ( listDetailVO.getLength() == 0 )
               {
                  continue;
               }
            }

            if ( KANUtil.filterEmpty( listDetailVO.getPropertyName() ) != null )
            {

               // ��ʾ���
               String thStyle = "";
               if ( KANUtil.filterEmpty( listDetailVO.getColumnWidth() ) != null )
               {
                  // ��ʼ��Width Type��Ĭ���ǰٷֱ�
                  String widthType = "%";
                  if ( listDetailVO.getColumnWidthType() != null )
                  {
                     // �п���������ʾ
                     if ( listDetailVO.getColumnWidthType().trim().equals( "2" ) )
                     {
                        widthType = "px";
                     }
                  }
                  thStyle = "width: " + listDetailVO.getColumnWidth() + widthType + ";";
               }

               // �����С
               String valueStyle = "";
               if ( KANUtil.filterEmpty( listDetailVO.getFontSize() ) != null && !listDetailVO.getFontSize().trim().equals( "0" ) )
               {
                  valueStyle = valueStyle + "font-size: " + listDetailVO.getFontSize() + "px;";
               }
               if ( KANUtil.filterEmpty( valueStyle ) != null )
               {
                  valueStyle = " style=\"" + valueStyle + "\" ";
               }

               // �Ƿ�����
               boolean sort = true;
               if ( KANUtil.filterEmpty( listDetailVO.getSort() ) != null && listDetailVO.getSort().trim().equals( "2" ) )
               {
                  sort = false;
               }

               // �б���
               rs.append( "<th " + thStyle + " class=\"" );

               // ��������
               if ( sort )
               {
                  rs.append( "header " );
               }
               else
               {
                  rs.append( "header-nosort " );
               }

               rs.append( pagedListHolder.getCurrentSortClass( listDetailVO.getPropertyName() ) + "\">" );

               // �޹�����������������
               if ( sort )
               {
                  rs.append( "<a onclick=\"submitForm('list_form', null, null, '" + listDetailVO.getPropertyName() + "', '"
                        + pagedListHolder.getNextSortOrder( listDetailVO.getPropertyName() ) + "', 'tableWrapper');\">" );
               }

               // �����Ҫ������ʽ
               if ( KANUtil.filterEmpty( valueStyle ) != null )
               {
                  rs.append( "<span " + valueStyle + ">" );
               }

               // ��ͷ
               rs.append( listDetailVO.getHeaderName( request ) );

               // �����Ҫ������ʽ
               if ( KANUtil.filterEmpty( valueStyle ) != null )
               {
                  rs.append( "</span>" );
               }

               // �޹�����������������
               if ( sort )
               {
                  rs.append( "</a>" );
               }

               rs.append( "</th>" );

            }
         }
      }
      rs.append( "</tr>" );

      return rs.toString();
   }

   private static int getSBItemNumber( final List< ListDetailVO > listDetailVOs )
   {
      int sbNumber = 0;

      if ( listDetailVOs != null && listDetailVOs.size() > 0 )
      {
         for ( ListDetailVO listDetailVO : listDetailVOs )
         {
            // �Ƿ���ʾ
            if ( listDetailVO.getDisplay() == null || !listDetailVO.getDisplay().trim().equals( "2" ) )
            {
               if ( listDetailVO.isSBItem() )
               {
                  sbNumber++;
               }
            }
         }
      }

      return sbNumber / 2;
   }

   // ��������Table��Title
   // Add by Siuvan Xia at 2014-5-27
   private static String generateSpecialTableTitle( final HttpServletRequest request, final PagedListHolder pagedListHolder, final List< ListDetailVO > listDetailVOs,
         final String pageAccessAction, final String javaObjectName ) throws KANException
   {
      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();

      // ��ʼ��Ҫ�ϲ�������
      int colspan = getSBItemNumber( listDetailVOs );

      // ��ǡ���˾���������ˡ��Ѿ�����
      boolean exists_c = false;
      boolean exists_p = false;

      // ������籣��
      boolean isSBBill = javaObjectName.equals( SBBillViewAction.JAVA_OBJECT_NAME );
      if ( isSBBill )
      {
         colspan = colspan * 3;
      }

      final String groupColumn = ( String ) KANUtil.getValue( pagedListHolder.getObject(), "groupColumn" );
      boolean show = true;
      if ( KANUtil.filterEmpty( groupColumn, "0" ) != null )
      {
         show = false;
      }

      rs.append( "<tr>" );
      // �������ɷ��籣��Ŀ����
      for ( ListDetailVO listDetailVO : listDetailVOs )
      {
         // �Ƿ���ʾ
         if ( listDetailVO.getDisplay() == null || !listDetailVO.getDisplay().trim().equals( "2" ) )
         {
            // ˢ�¹��ʻ�
            listDetailVO.reset( null, request );

            if ( isShowColumnId( pageAccessAction, javaObjectName, listDetailVO.getPropertyName(), request ) )
            {
               continue;
            }
            if ( !show
                  && ( ( groupColumn.equals( "1" ) && !ArrayUtils.contains( CROUP_BY_CLIENT_SHOW_COLUMN, listDetailVO.getPropertyName() ) ) || ( groupColumn.equals( "2" ) && !ArrayUtils.contains( CROUP_BY_SOLUTION_SHOW_COLUMN, listDetailVO.getPropertyName() ) ) ) )
            {
               if ( listDetailVO.getLength() == 0 )
               {
                  continue;
               }
            }

            if ( KANUtil.filterEmpty( listDetailVO.getPropertyName() ) != null )
            {
               // �����ǰ���籣���ϲ��С�rowspan��
               if ( !listDetailVO.isSBItem() )
               {
                  // ��ʾ���
                  String thStyle = "";
                  if ( KANUtil.filterEmpty( listDetailVO.getColumnWidth() ) != null )
                  {
                     // ��ʼ��Width Type��Ĭ���ǰٷֱ�
                     String widthType = "%";
                     if ( listDetailVO.getColumnWidthType() != null )
                     {
                        // �п���������ʾ
                        if ( listDetailVO.getColumnWidthType().trim().equals( "2" ) )
                        {
                           widthType = "px";
                        }
                     }
                     thStyle = "width: " + listDetailVO.getColumnWidth() + widthType + ";";
                  }

                  // �����С
                  String valueStyle = "";
                  if ( KANUtil.filterEmpty( listDetailVO.getFontSize() ) != null && !listDetailVO.getFontSize().trim().equals( "0" ) )
                  {
                     valueStyle = valueStyle + "font-size: " + listDetailVO.getFontSize() + "px;";
                  }
                  if ( KANUtil.filterEmpty( valueStyle ) != null )
                  {
                     valueStyle = " style=\"" + valueStyle + "\" ";
                  }

                  // �Ƿ�����
                  boolean sort = true;
                  if ( listDetailVO.getLength() > 1 || ( KANUtil.filterEmpty( listDetailVO.getSort() ) != null && listDetailVO.getSort().trim().equals( "2" ) ) )
                  {
                     sort = false;
                  }

                  // �б���
                  rs.append( "<th style=\"" + thStyle + "\" class=\"" );

                  // ��������
                  if ( sort )
                  {
                     rs.append( "header " );
                  }
                  else
                  {
                     rs.append( "header-nosort " );
                  }

                  rs.append( pagedListHolder.getCurrentSortClass( listDetailVO.getPropertyName() ) + "\"" + " rowspan=\"" + ( isSBBill ? "3" : "2" ) + "\" >" );

                  // �޹�����������������
                  if ( sort )
                  {
                     rs.append( "<a onclick=\"submitForm('list_form', null, null, '" + listDetailVO.getPropertyName() + "', '"
                           + pagedListHolder.getNextSortOrder( listDetailVO.getPropertyName() ) + "', 'tableWrapper');\">" );
                  }

                  // �����Ҫ������ʽ
                  if ( KANUtil.filterEmpty( valueStyle ) != null )
                  {
                     rs.append( "<span " + valueStyle + ">" );
                  }

                  // ��ͷ
                  rs.append( listDetailVO.getHeaderName( request ) );

                  // �����Ҫ������ʽ
                  if ( KANUtil.filterEmpty( valueStyle ) != null )
                  {
                     rs.append( "</span>" );
                  }

                  // �޹�����������������
                  if ( sort )
                  {
                     rs.append( "</a>" );
                  }

                  rs.append( "</th>" );
               }
               // ����籣��Ŀ���ϲ��С�colspan��
               else
               {
                  // ����ǹ�˾
                  if ( !exists_c && "c".equals( listDetailVO.getLastCharOfPropertyName() ) )
                  {
                     // ���ı��
                     exists_c = true;

                     // ��ʼ����ƽ����
                     String companyTitle = request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "��˾" : "Company";

                     rs.append( "<th class=\"header-nosort center\" colspan=\"" + colspan + "\" >" );
                     rs.append( companyTitle );
                     rs.append( "</th>" );
                  }
                  else if ( !exists_p && "p".equals( listDetailVO.getLastCharOfPropertyName() ) )
                  {
                     // ���ı��
                     exists_p = true;

                     // ��ʼ����ƽ����
                     String personalTitle = request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "����" : "Personal";

                     rs.append( "<th class=\"header-nosort center\" colspan=\"" + colspan + "\" >" );
                     rs.append( personalTitle );
                     rs.append( "</th>" );
                  }
               }
            }
         }
      }
      rs.append( "</tr>" );

      rs.append( "<tr>" );
      // ���������籣��Ŀ����
      for ( ListDetailVO listDetailVO : listDetailVOs )
      {
         // �Ƿ���ʾ
         if ( listDetailVO.getDisplay() == null || !listDetailVO.getDisplay().trim().equals( "2" ) )
         {
            // ˢ�¹��ʻ�
            listDetailVO.reset( null, request );

            // �籣��Ŀ
            if ( KANUtil.filterEmpty( listDetailVO.getPropertyName() ) != null && listDetailVO.isSBItem() )
            {
               // ��ʾ���
               String thStyle = "";
               if ( KANUtil.filterEmpty( listDetailVO.getColumnWidth() ) != null )
               {
                  // ��ʼ��Width Type��Ĭ���ǰٷֱ�
                  String widthType = "%";
                  if ( listDetailVO.getColumnWidthType() != null )
                  {
                     // �п���������ʾ
                     if ( listDetailVO.getColumnWidthType().trim().equals( "2" ) )
                     {
                        widthType = "px";
                     }
                  }
                  thStyle = "width: " + listDetailVO.getColumnWidth() + widthType + ";";
               }

               // �����С
               String valueStyle = "";
               if ( KANUtil.filterEmpty( listDetailVO.getFontSize() ) != null && !listDetailVO.getFontSize().trim().equals( "0" ) )
               {
                  valueStyle = valueStyle + "font-size: " + listDetailVO.getFontSize() + "px;";
               }
               if ( KANUtil.filterEmpty( valueStyle ) != null )
               {
                  valueStyle = " style=\"" + valueStyle + "\" ";
               }

               // �б���
               rs.append( "<th " + thStyle + " class=\"header-nosort center\"" );
               rs.append( isSBBill ? "colspan=\"3\">" : ">" );

               // �����Ҫ������ʽ
               if ( KANUtil.filterEmpty( valueStyle ) != null )
               {
                  rs.append( "<span " + valueStyle + ">" );
               }

               // ��ͷ
               rs.append( listDetailVO.getHeaderName( request ) );

               // �����Ҫ������ʽ
               if ( KANUtil.filterEmpty( valueStyle ) != null )
               {
                  rs.append( "</span>" );
               }

               rs.append( "</th>" );
            }
         }
      }
      rs.append( "</tr>" );

      if ( isSBBill )
      {
         rs.append( "<tr>" );
         // ���������籣��Ŀ����
         for ( ListDetailVO listDetailVO : listDetailVOs )
         {
            // �Ƿ���ʾ
            if ( listDetailVO.getDisplay() == null || !listDetailVO.getDisplay().trim().equals( "2" ) )
            {
               // ˢ�¹��ʻ�
               listDetailVO.reset( null, request );

               // �籣��Ŀ
               if ( KANUtil.filterEmpty( listDetailVO.getPropertyName() ) != null && listDetailVO.isSBItem() )
               {
                  // �б���
                  rs.append( "<th class=\"header-nosort center\">" + KANUtil.getProperty( request.getLocale(), "sb.base" ) + "</th>" );
                  rs.append( "<th class=\"header-nosort center\">" + KANUtil.getProperty( request.getLocale(), "sb.rate" ) + "</th>" );
                  rs.append( "<th class=\"header-nosort center\">" + KANUtil.getProperty( request.getLocale(), "sb.amount" ) + "</th>" );
               }
            }
         }
         rs.append( "</tr>" );
      }

      return rs.toString();
   }

   public static String generateSpecialListTable( final HttpServletRequest request, final String javaObjectName ) throws KANException
   {
      final String pageAccessAction = KANUtil.filterEmpty( request.getAttribute( "pageAccessAction" ) );
      return generateSpecialListTable( request, javaObjectName, pageAccessAction );
   }

   // ��ȡ����ListDTO�ϲ����ListDetailVO�б�
   private static List< ListDetailVO > getListDetailVOs( final HttpServletRequest request, final ListDTO listDTO ) throws KANException
   {
      // ��ʼ�����ϲ�����ListDetailVO��ListDetailVO�б�
      final List< ListDetailVO > mergeListDetailVOs = new ArrayList< ListDetailVO >();

      // װ����ListDetailVO
      mergeListDetailVOs.addAll( listDTO.getListDetailVOs() );

      // ������ڴ�ListDetailVO
      if ( listDTO.getSubListDTOs() != null && listDTO.getSubListDTOs().size() > 0 )
      {

         // ��ʼ��Sub ListDetailVO
         final List< ListDetailVO > subListDetailVOs = mergeListDetailVO( request, listDTO.getSubListDTOs() );

         if ( subListDetailVOs != null && subListDetailVOs.size() > 0 )
         {
            mergeListDetailVOs.addAll( subListDetailVOs );
         }

         // ����
         if ( mergeListDetailVOs.size() > 0 )
         {
            Collections.sort( mergeListDetailVOs, new ComparatorListDetail() );
         }
      }

      return mergeListDetailVOs;
   }

   // ���������table
   public static String generateSpecialListTable( final HttpServletRequest request, final String javaObjectName, final String pageAccessAction ) throws KANException
   {
      boolean hasExport = true;
      if ( KANUtil.filterEmpty( pageAccessAction ) != null )
      {
         hasExport = AuthUtils.hasAuthority( ( String ) request.getAttribute( "pageAccessAction" ), AuthConstants.RIGHT_EXPORT, "", request, null );
      }

      // ��ʼ��corpId
      final String corpId = KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) );

      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();

      // ��ȡListDTO
      final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, corpId );

      // ��ʼ��PagedListHolder
      final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );

      // ������籣��
      boolean isSBBill = javaObjectName.equals( SBBillViewAction.JAVA_OBJECT_NAME );

      // �Ҳ�������
      //final String groupColumn = ( String ) KANUtil.getValue( pagedListHolder.getObject(), "groupColumn" );
      final String groupColumn = "0";
      boolean show = true;
      if ( KANUtil.filterEmpty( groupColumn, "0" ) != null )
      {
         show = false;
      }

      // �Ƿ����ñ�ƽ
      final boolean isUseFlat = isUseFlat( pagedListHolder, listDTO );

      int columnSize = 0;
      // ��������Table
      if ( listDTO != null && pagedListHolder != null )
      {
         rs.append( "<table class=\"table hover\" id=\"resultTable\">" );

         // ��ʼ�����ϲ�����ListDetailVO��ListDetailVO�б�
         final List< ListDetailVO > mergeListDetailVOs = new ArrayList< ListDetailVO >();

         // �����б��ֶ�
         if ( listDTO.getListDetailVOs() != null && listDTO.getListDetailVOs().size() > 0 )
         {
            // װ����ListDetailVO
            mergeListDetailVOs.addAll( getListDetailVOs( request, listDTO ) );

            // ��ʼ��ListHeaderVO
            final ListHeaderVO listHeaderVO = listDTO.getListHeaderVO();

            rs.append( "<thead>" );

            if ( isUseFlat )
            {
               rs.append( generateSpecialTableTitle( request, pagedListHolder, mergeListDetailVOs, pageAccessAction, javaObjectName ) );
            }
            else
            {
               rs.append( generateCommonTableTitle( request, pagedListHolder, mergeListDetailVOs, pageAccessAction, javaObjectName ) );
            }

            rs.append( "</thead>" );
            // ��������Table Body
            if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               rs.append( "<tbody>" );

               // Index �����жϵ�ǰ���кţ������б�ͬ�е���ʽ
               int index = 0;
               // ������
               for ( Object object : pagedListHolder.getSource() )
               {

                  // ��ʼ��Object - DTO[Header]
                  Object objectHeader = null;
                  // ��ʼ��List< Object > - DTO[Details]
                  List< ? > objectDetails = null;
                  if ( object instanceof SpecialDTO )
                  {
                     // ��ȡSpecialDTO
                     @SuppressWarnings("unchecked")
                     final SpecialDTO< Object, List< ? > > specialDTO = ( SpecialDTO< Object, List< ? > > ) object;

                     objectDetails = specialDTO.getDetailVOs();
                     objectHeader = specialDTO.getHeaderVO();
                     // ���ʻ���ֵ    
                     ( ( ActionForm ) objectHeader ).reset( null, request );
                  }

                  // ����ʽ
                  final String trClass = index % 2 == 1 ? "odd" : "even";
                  rs.append( "<tr class='" + trClass + "'>" );

                  // ������
                  for ( ListDetailVO listDetailVO : mergeListDetailVOs )
                  {
                     if ( listDetailVO.getDisplay() == null || !listDetailVO.getDisplay().trim().equals( "2" ) )
                     {
                        // ��ʼ����������
                        final String propertyName = listDetailVO.getPropertyName();

                        if ( isShowColumnId( pageAccessAction, javaObjectName, propertyName, request ) )
                        {
                           continue;
                        }
                        if ( !show
                              && ( ( groupColumn.equals( "1" ) && !ArrayUtils.contains( CROUP_BY_CLIENT_SHOW_COLUMN, listDetailVO.getPropertyName() ) ) || ( groupColumn.equals( "2" ) && !ArrayUtils.contains( CROUP_BY_SOLUTION_SHOW_COLUMN, listDetailVO.getPropertyName() ) ) ) )
                        {
                           if ( listDetailVO.getLength() == 0 )
                           {
                              continue;
                           }
                        }

                        Object objHeaderVO = null;
                        // ��ȡSpecialDTO
                        if ( object instanceof SpecialDTO )
                        {
                           @SuppressWarnings("unchecked")
                           final SpecialDTO< Object, List< ? > > specialDTO = ( SpecialDTO< Object, List< ? > > ) object;
                           objHeaderVO = specialDTO.getHeaderVO();
                        }

                        // �п�
                        String valueStyle = "";
                        if ( KANUtil.filterEmpty( listDetailVO.getFontSize() ) != null && !listDetailVO.getFontSize().trim().equals( "0" ) )
                        {
                           valueStyle = valueStyle + "font-size: " + listDetailVO.getFontSize() + "px;";
                        }
                        if ( KANUtil.filterEmpty( valueStyle ) != null )
                        {
                           valueStyle = " style=\"" + valueStyle + "\" ";
                        }

                        // ��ʼ�����ӷ�ʽ
                        String target = "target=\"_self\"";
                        if ( listDetailVO.getLinkedTarget() != null && listDetailVO.getLinkedTarget().trim().equals( "2" ) )
                        {
                           target = "target=\"_blank\"";
                        }

                        // ��ʼ����̬����
                        String properties[] = null;
                        if ( listDetailVO.getProperties() != null && !listDetailVO.getProperties().trim().equals( "" ) )
                        {
                           properties = listDetailVO.getProperties().trim().split( "," );
                        }

                        // ��˰С�ڡ�0���ľ���
                        String tdClass = "";

                        if ( javaObjectName.equals( IncomeTaxAction.JAVA_OBJECT_NAME ) && listDetailVO.getPropertyName().equals( "taxAmountPersonal" ) )
                        {
                           tdClass = "class=\"highlight\"";
                        }

                        // Added by siuvan @2014-08-07
                        // ������籣��Ŀ�����Object�еõ� ������������������ �ٻ�ȡ ����
                        if ( isSBBill && listDetailVO.isSBItem() )
                        {
                           columnSize++;
                           rs.append( "<td align=\"" + listDetailVO.getDecodeAlignTemp() + "\" >" );
                           rs.append( "<span>" + getColumnValue( request, listDetailVO, objectHeader, objectDetails, listDetailVO.filterCompany() ? "baseCompany" : "basePersonal" )
                                 + "</span>" );
                           rs.append( "</td>" );

                           columnSize++;
                           rs.append( "<td align=\"" + listDetailVO.getDecodeAlignTemp() + "\" >" );
                           rs.append( "<span>" + getColumnValue( request, listDetailVO, objectHeader, objectDetails, listDetailVO.filterCompany() ? "rateCompany" : "ratePersonal" )
                                 + "</span>" );
                           rs.append( "</td>" );
                        }

                        rs.append( "<td align=\"" + listDetailVO.getDecodeAlignTemp() + "\" " );

                        if ( propertyName != null )
                        {
                           String value = getColumnValue( request, listDetailVO, objectHeader, objectDetails, null );

                           // �����Ҫ����TD CLASS
                           if ( KANUtil.filterEmpty( tdClass ) != null && Float.valueOf( value ) < 0 )
                           {
                              rs.append( tdClass );
                           }

                           rs.append( ">" );

                           // �����ǰ�ֶ��Ǵ����ӵ�
                           if ( listDetailVO.getIsLinked() != null && listDetailVO.getIsLinked().equals( ListDetailVO.TRUE ) && listDetailVO.getLinkedAction() != null
                                 && !listDetailVO.getLinkedAction().trim().equals( "" ) )
                           {
                              // ��õ�ǰ�ֶε�����
                              String link = listDetailVO.getLinkedAction();

                              // ��������к��в���
                              if ( link.contains( "${" ) )
                              {
                                 // �������滻�����еĲ���
                                 if ( properties != null && properties.length > 0 )
                                 {
                                    for ( int i = 0; i < properties.length; i++ )
                                    {
                                       final Object propertyValueObject = KANUtil.getValue( objHeaderVO, properties[ i ] );
                                       link = link.replace( "${" + i + "}", propertyValueObject != null ? propertyValueObject.toString() : "" );
                                    }
                                 }
                              }

                              // �����ǰ������Ϊ��������ID
                              if ( !link.contains( "&id=" ) )
                              {
                                 link = link + "&id=" + KANUtil.getValue( objHeaderVO, "encodedId" );
                              }

                              // ������ӵ�ַ
                              rs.append( "<a href=\"" + link + "\" " + target + ">" );
                           }

                           // �����Ҫ������ʽ
                           if ( KANUtil.filterEmpty( valueStyle ) != null )
                           {
                              rs.append( "<span " + valueStyle + ">" );
                           }

                           rs.append( value );

                           // �����Ҫ������ʽ
                           if ( KANUtil.filterEmpty( valueStyle ) != null )
                           {
                              rs.append( "</span>" );
                           }

                           // �����ǰ�ֶ��Ǵ����ӵ�
                           if ( listDetailVO.getIsLinked() != null && listDetailVO.getIsLinked().equals( ListDetailVO.TRUE ) && listDetailVO.getLinkedAction() != null
                                 && !listDetailVO.getLinkedAction().trim().equals( "" ) )
                           {
                              rs.append( "</a>" );
                           }
                        }

                        // ������ڸ�������
                        if ( listDetailVO.getAppendContent() != null && !listDetailVO.getAppendContent().trim().equals( "" ) )
                        {
                           // ��õ�ǰ�ֶεĸ�������
                           String appendContent = listDetailVO.getAppendContent();

                           // ��������к��в���
                           if ( appendContent.contains( "${" ) )
                           {
                              // �������滻���������еĲ���
                              if ( properties != null && properties.length > 0 )
                              {
                                 for ( int i = 0; i < properties.length; i++ )
                                 {
                                    if ( properties[ i ].equals( "decodeOrderDTOStatus4Page" ) )
                                    {
                                       if ( hasExport )
                                       {
                                          final Object propertyValueObject = KANUtil.getValue( objHeaderVO, properties[ i ] );
                                          appendContent = appendContent.replace( "${" + i + "}", propertyValueObject != null ? propertyValueObject.toString() : "" );
                                       }
                                       else
                                       {
                                          appendContent = appendContent.replace( "${" + i + "}", "" );
                                       }
                                    }
                                    else
                                    {
                                       final Object propertyValueObject = KANUtil.getValue( objHeaderVO, properties[ i ] );
                                       appendContent = appendContent.replace( "${" + i + "}", propertyValueObject != null ? propertyValueObject.toString() : "" );
                                    }
                                 }
                              }
                           }

                           rs.append( appendContent );
                        }

                        rs.append( "</td>" );

                        columnSize++;
                     }
                  }

                  rs.append( "</tr>" );

                  index++;
               }

               rs.append( "</tbody>" );
            }

            // ����Table Foot
            if ( listHeaderVO.getUsePagination() != null && listHeaderVO.getUsePagination().trim().equals( "1" ) )
            {
               rs.append( "<tfoot>" );
               rs.append( "<tr class=\"total\">" );
               rs.append( "<td colspan=\"" + ( ( columnSize == 0 ? mergeListDetailVOs.size() : columnSize ) + 1 ) + "\" class=\"left\">" );
               rs.append( "<label>&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.total" ) + "�� " + pagedListHolder.getHolderSize() + " " );
               rs.append( "<label>&nbsp;&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.current" ) + "��" + pagedListHolder.getIndexStart() + " - "
                     + pagedListHolder.getIndexEnd() + "</label> " );
               rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getFirstPage() + "', null, null, 'tableWrapper', null, "
                     + ( useFixColumn( javaObjectName ) ? "'useFixColumn();'" : "'null'" ) + ");\">" + KANUtil.getProperty( request.getLocale(), "page.first" ) + "</a></label> " );
               rs.append( "<label>&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getPreviousPage() + "', null, null, 'tableWrapper', null, "
                     + ( useFixColumn( javaObjectName ) ? "'useFixColumn();'" : "'null'" ) + ");\">" + KANUtil.getProperty( request.getLocale(), "page.previous" )
                     + "</a></label> " );
               rs.append( "<label>&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getNextPage() + "', null, null, 'tableWrapper', null, "
                     + ( useFixColumn( javaObjectName ) ? "'useFixColumn();'" : "'null'" ) + " );\">" + KANUtil.getProperty( request.getLocale(), "page.next" ) + "</a></label> " );
               rs.append( "<label>&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getLastPage() + "', null, null, 'tableWrapper', null, "
                     + ( useFixColumn( javaObjectName ) ? "'useFixColumn();'" : "'null'" ) + ");\">" + KANUtil.getProperty( request.getLocale(), "page.last" ) + "</a></label> " );
               rs.append( "<label>&nbsp;&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.pagination" ) + "��" + pagedListHolder.getRealPage() + "/"
                     + pagedListHolder.getPageCount() + "</label>&nbsp;" );
               rs.append( "<label>&nbsp;&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.jump.to" )
                     + "��<input type=\"text\" id=\"forwardPage_render\" class=\"forwardPage_render\" style=\"width:23px;\" value=\"" + pagedListHolder.getRealPage()
                     + "\" onkeydown=\"if(event.keyCode == 13){pageForward();}\" />" + KANUtil.getProperty( request.getLocale(), "page.page" ) + "</label>&nbsp;" );
               rs.append( "</td>" );
               rs.append( "</tr>" );
               rs.append( "</tfoot>" );
            }
         }

         rs.append( "</table>" );
      }

      return rs.toString();
   }

   // ����������˳��  �Զ��� - �б� - detail
   public static String generateQuickColumnIndexPopup( final HttpServletRequest request, final String listHeaderId, final String linkAction ) throws KANException
   {
      // ��ʼ��ListDTO
      final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByListHeaderId( listHeaderId );

      // ��ʼ��ListDetailVO�б�
      final List< ListDetailVO > listDetailVOs = new ArrayList< ListDetailVO >();

      if ( listDTO != null && listDTO.getListHeaderVO() != null )
      {
         // �������java����
         if ( KANUtil.filterEmpty( listDTO.getListHeaderVO().getUseJavaObject() ) != null && listDTO.getListHeaderVO().getUseJavaObject().equals( "1" ) )
         {
            listDetailVOs.addAll( getListDetailVOs( request, listDTO ) );
         }
         else
         {
            listDetailVOs.addAll( listDTO.getListDetailVOs() );
         }
      }

      return generateQuickColumnIndexPopup( request, listDetailVOs, linkAction );
   }

   // ����������˳�� ����ҳ��
   public static String generateQuickColumnIndexPopup( final HttpServletRequest request, final List< ListDetailVO > listDetailVOs, final String linkAction ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      if ( listDetailVOs != null && listDetailVOs.size() > 0 )
      {
         rs.append( "<div class=\"modal midsize content hide\" id=\"indexQuickModuleId\">" );
         rs.append( "<div class=\"modal-header\" id=\"indexQuickHeader\"> " );
         rs.append( "<a class=\"close\" data-dismiss=\"modal\" onclick=\"closeBox();\">��</a>" );
         rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "img.title.tips.quick.set.list.sequence" ) + "</label>" );
         rs.append( "</div>" );
         rs.append( "<div class=\"modal-body\">" );
         rs.append( "<div class=\"top\">" );
         rs.append( "<input type=\"button\" class=\"save\" name=\"btnConfirm\" id=\"btnConfirm\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.confirm" )
               + "\" onclick=\"resetIndex();\" />" );
         rs.append( "<input type=\"button\" class=\"reset\" name=\"btnCancel\" id=\"btnCancel\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.cancel" )
               + "\" onclick=\"closeBox();\" />" );
         rs.append( "</div>" );
         rs.append( "<form action=\"listDetailAction.do?proc=quick_column_index\" method=\"post\" class=\"quickIndexForm\">" );
         rs.append( "<fieldset>" );
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<div id=\"select\">" );
         rs.append( "<input type=\"hidden\" name=\"newColumnIndex\" id=\"newColumnIndex\" />" );
         rs.append( "<select id=\"columns\" size=\"12\" style=\"width: 200px; height: 200px;\" >" );

         for ( ListDetailVO listDetailVO : listDetailVOs )
         {
            String optionName = "";
            // ������籣��Ŀ��ɸѡ��˾����
            if ( listDetailVO.isSBItem() )
            {
               if ( !listDetailVO.filterCompany() )
               {
                  continue;
               }

               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  optionName = listDetailVO.getNameZH().split( "-" ).length == 2 ? listDetailVO.getNameZH().split( "-" )[ 0 ] : listDetailVO.getNameZH();
               }
               else
               {
                  optionName = listDetailVO.getNameEN().split( "-" ).length == 2 ? listDetailVO.getNameZH().split( "-" )[ 0 ] : listDetailVO.getNameEN();
               }
            }
            else
            {
               optionName = request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? listDetailVO.getNameZH() : listDetailVO.getNameEN();
            }

            rs.append( "<option value=\"" + listDetailVO.getListDetailId() + "\">" + optionName + "</option>" );
         }

         rs.append( "</select>" );
         rs.append( "<div id=\"moveButton\" style=\"float: right;\" align=\"center\">" );
         rs.append( "<a id=\"upMove\" class=\"upMove\" title=\"��\" onmousedown=\"setTimeStart('up');\" onmouseup=\"clearTimeout(x);\" onclick=\"listObj=document.getElementById('columns');upListItem();clearTimeout(x);\" ><img id=\"_up_link\" src='images/up.png' /></a>" );
         rs.append( "<br/><br/><br/><br/>" );
         rs.append( "<a id=\"downMove\" class=\"downMove\" title=\"��\" onmousedown=\"setTimeStart('down');\" onmouseup=\"clearTimeout(x);\" onclick=\"listObj=document.getElementById('columns');downListItem();clearTimeout(x);\"><img id=\"_down_link\" src='images/down.png' /></a>" );
         rs.append( "</div>" );
         rs.append( "</div>" );
         rs.append( "</li>" );
         rs.append( "</ol>" );
         rs.append( "</fieldset>" );
         rs.append( "</form>" );
         rs.append( "</div>" );
         rs.append( "</div>" );

         // js ����
         rs.append( "<script type=\"text/javascript\"> " );
         rs.append( "function popupIndexQuick(){" );
         rs.append( "$('#indexQuickModuleId').removeClass('hide');$('#shield').show();};" );

         rs.append( "function closeBox(){" );
         rs.append( "$('#indexQuickModuleId').addClass('hide');$('#shield').hide();" );
         rs.append( "link('" + linkAction + "');};" );

         rs.append( "function resetIndex(){" );
         rs.append( "var str = '';" );
         rs.append( "$('#columns option').each( function(i){" );
         rs.append( "if( str == ''){str = $(this).val() + '_' + (i + 1);}else{str = str + ',' + $(this).val() + '_' + (i + 1);}});" );
         rs.append( "$('#newColumnIndex').val(str);" );
         rs.append( "$('#indexQuickModuleId').addClass('hide');" );
         rs.append( "submitForm('quickIndexForm', 'confirmObject', null, null, null, 'null', null, 'closeBox();');};" );
         rs.append( "</script>" );
      }

      return rs.toString();
   }

   public static String generateImportPopup( final HttpServletRequest request, final TableDTO tableDTO ) throws KANException
   {
      final ListDTO listDTO = tableDTO.getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );
      final StringBuilder rs = new StringBuilder();

      if ( listDTO != null && listDTO.getListHeaderVO() != null )
      {
         // �ж��Ƿ��е��빦��
         if ( hasImportRight( request, listDTO.getListHeaderVO().getTableId() ) )
         {
            // ��ʼ��ModuleDTO
            final ImportDTO importDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getImportDTOByTableId( listDTO.getListHeaderVO().getTableId(), ( String ) BaseAction.getCorpId( request, null ) );

            if ( importDTO != null && importDTO.getImportHeaderVO() != null && KANUtil.filterEmpty( importDTO.getImportHeaderVO().getImportHeaderId() ) != null )
            {
               final ImportHeaderVO importHeaderVO = importDTO.getImportHeaderVO();
               rs.append( "<a id=\"importExcel\" name=\"importExcel\" class=\"commonTools\" title=\""
                     + ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? importHeaderVO.getNameZH() : importHeaderVO.getNameEN() )
                     + "\" onclick=\"$('#importDIVModalId').show();$('#shield').show();\" ><img id=\"_importExcel_link\" src='images/import.png' /></a>" );
               // ����ģ�尴ť�͵��밴ť
               rs.append( "<div class=\"modal hide\" id=\"importDIVModalId\">" );
               rs.append( "<div class=\"modal-header\">" );
               rs.append( "<a class=\"close\" data-dismiss=\"modal\"  onclick=\"$('#importDIVModalId').hide();$('#shield').hide();window.location.reload();\">��</a>" );
               rs.append( "<label> " + importHeaderVO.getNameZH() + "</label>" );
               rs.append( " </div>" );

               rs.append( "<div class=\"modal-body\">" );

               if ( KANUtil.filterEmpty( importHeaderVO.getDescription() ) != null )
               {
                  rs.append( "<div id=\"importDescription\" class=\"message success importDescription\" style=\"font-size: 10px;\"> <div style=\"margin-right: 25px;\"> "
                        + importHeaderVO.getDescription() + " </div> <div><a class=\"messageCloseButton\"  onclick=\"$('#importDescription').hide();\">��</a></div></div>" );
               }
               rs.append( "<input type=\"button\" id=\"importExcelFile2\" value=\"�ϴ��ļ�\" onclick=\"uploadObjectExcel.submit();\" class='save'>" );
               rs.append( "<a href=\"#\" onclick=\"link('downloadFileAction.do?proc=exportImportTemplate&fileType=excel&importHeaderId=" + importHeaderVO.getEncodedId()
                     + "')\" class='commonlink'>����ģ��</a>" );
               rs.append( "<ol id=\"attachmentsOL\" class=\"auto\"></ol>" );
               rs.append( "<div id=\"uploadProgressMsgDIV\"><div id=\"msgTitle\"></div><div id=\"msgContent\"></div></div>" );
               rs.append( "</div>" );
               String importConfirmMsg = null;
               if ( importDTO.getPrimaryKeyImportDetailVO() != null )
               {
                  importConfirmMsg = "�����ļ�������" + importDTO.getPrimaryKeyImportDetailVO().getNameZH() + "����" + tableDTO.getTableVO().getNameZH() + "��Ϣ���ᱻ����";
                  rs.append( "<div class=\"modal-footer\"><font color='red'>*</font>" + importConfirmMsg + "</div>" );
               }
               else
               {
                  importConfirmMsg = "";
               }
               rs.append( "</div>" );
               // js ����
               rs.append( "<script type=\"text/javascript\"> " );
               rs.append( "var uploadObjectExcel = createUploadExcel('importExcelFile2','uploadFileAction.do?proc=importExcel&importHeaderId=" + importHeaderVO.getEncodedId()
                     + "&accessAction=" + tableDTO.getTableVO().getAccessAction() + "','uploadFileAction.do?proc=getStatusMessage','common', 'tempForExcel','" + importConfirmMsg
                     + "');" );
               rs.append( "</script>" );
            }
         }
      }
      return rs.toString();
   }

   public static String generateListTable( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      return generateListTable( request, accessAction, false );
   }

   public static String generateListTable( final HttpServletRequest request, final String accessAction, final boolean useFixColumn ) throws KANException
   {
      boolean hasSubmit = AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_SUBMIT, "", request, null );
      boolean hasOperate = AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_DIMISSION, "", request, null );
      boolean hasPrint = AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_PRINT, "", request, null );
      String contractAccessAction = "{HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT,HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_IN_HOUSE}";

      // ��ȡ��ǰ��Ҫ���ɿؼ���������TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );
      // ��ʼ��PagedListHolder
      final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );
      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();

      int appendTdSize = 0;

      // ����Column Group
      if ( tableDTO != null && tableDTO.getListDTOs() != null && pagedListHolder != null && tableDTO.getListDTOs().size() > 0 )
      {
         // ��ʼ��ListDTO
         final ListDTO listDTO = tableDTO.getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );
         rs.append( "<table class=\"table hover\" id=\"resultTable\">" );

         if ( listDTO != null && listDTO.getListDetailVOs() != null && listDTO.getListDetailVOs().size() > 0 )
         {
            // ��ʼ��ListHeaderVO
            final ListHeaderVO listHeaderVO = listDTO.getListHeaderVO();

            // ��������Table Head
            rs.append( "<thead>" );
            rs.append( "<tr>" );
            //��Ա��½����ʾ
            if ( !KANConstants.ROLE_EMPLOYEE.equals( EmployeeSecurityAction.getRole( request, null ) ) )
            {
               rs.append( "<th class=\"checkbox-col\">" );
               rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectAll\" name=\"chkSelectAll\" value=\"\" />" );
               rs.append( "</th>" );
            }

            for ( ListDetailVO listDetailVO : listDTO.getListDetailVOs() )
            {
               if ( listDetailVO.getDisplay() == null || !listDetailVO.getDisplay().trim().equals( "2" ) )
               {
                  // ��ʼ��ListDetailVO
                  listDetailVO.reset( null, request );

                  // ��ʼ���ֶζ���
                  final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( listDetailVO.getColumnId() );
                  if ( isShowColumnId( accessAction, null, columnVO, request ) )
                  {
                     continue;
                  }

                  if ( columnVO != null && columnVO.getColumnId().equals( "8" ) )
                  {
                     appendTdSize = ( ( IClickEmployeeReportView ) pagedListHolder.getObject() ).getSalarys().size();
                     rs.append( appendSalaryInfo( request, pagedListHolder.getObject(), null, listDetailVO, 1 ) );
                     continue;
                  }

                  // ��ʼ���б��ֶ���ʽ
                  String thStyle = "";
                  if ( listDetailVO.getColumnWidth() != null && !listDetailVO.getColumnWidth().trim().equals( "" ) )
                  {
                     // ��ʼ��Width Type��Ĭ���ǰٷֱ�
                     String widthType = "%";
                     if ( listDetailVO.getColumnWidthType() != null )
                     {
                        // �п���������ʾ
                        if ( listDetailVO.getColumnWidthType().trim().equals( "2" ) )
                        {
                           widthType = "px";
                        }
                     }
                     thStyle = "width: " + listDetailVO.getColumnWidth() + widthType + ";";
                  }
                  if ( thStyle != null && !thStyle.trim().equals( "" ) )
                  {
                     thStyle = " style=\"" + thStyle + "\" ";
                  }

                  String valueStyle = "";
                  if ( listDetailVO.getFontSize() != null && !listDetailVO.getFontSize().trim().equals( "" ) && !listDetailVO.getFontSize().trim().equals( "0" ) )
                  {
                     valueStyle = valueStyle + "font-size: " + listDetailVO.getFontSize() + "px;";
                  }
                  if ( valueStyle != null && !valueStyle.trim().equals( "" ) )
                  {
                     valueStyle = " style=\"" + valueStyle + "\" ";
                  }

                  boolean sort = true;
                  // ��ϵͳ�ֶβ�������
                  if ( ( columnVO != null && !KANConstants.SUPER_ACCOUNT_ID.equals( columnVO.getAccountId() ) )
                        || ( listDetailVO != null && listDetailVO.getSort() != null && listDetailVO.getSort().trim().equals( "2" ) ) )
                  {
                     sort = false;
                  }

                  // �б���
                  rs.append( "<th " + thStyle + " class=\"" );

                  // ��������
                  if ( sort )
                  {
                     rs.append( "header " );
                  }
                  else
                  {
                     rs.append( "header-nosort " );
                  }

                  // �޹��������������
                  if ( columnVO != null )
                  {
                     rs.append( pagedListHolder.getCurrentSortClass( columnVO.getNameDB() ) );
                  }
                  rs.append( "\">" );

                  // �޹�����������������
                  if ( columnVO != null && sort )
                  {
                     rs.append( "<a onclick=\"submitForm('list_form', null, null, '" + columnVO.getNameDB() + "', '" + pagedListHolder.getNextSortOrder( columnVO.getNameDB() )
                           + "', 'tableWrapper');\">" );
                  }

                  // �����Ҫ������ʽ
                  if ( valueStyle != null && !valueStyle.trim().equals( "" ) )
                  {
                     rs.append( "<span " + valueStyle + ">" );
                  }

                  // �����ڲ������������
                  rs.append( getColumnName( request, listDetailVO ) );

                  // �����Ҫ������ʽ
                  if ( valueStyle != null && !valueStyle.trim().equals( "" ) )
                  {
                     rs.append( "</span>" );
                  }

                  // �޹�����������������
                  if ( columnVO != null && sort )
                  {
                     rs.append( "</a>" );
                  }
                  rs.append( "</th>" );
               }
            }

            rs.append( "</tr>" );
            rs.append( "</thead>" );

            // ��������Table Body
            if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               rs.append( "<tbody>" );

               // Index �����жϵ�ǰ���кţ������б�ͬ�е���ʽ
               int index = 0;
               // ������
               for ( Object object : pagedListHolder.getSource() )
               {
                  // ����ʽ
                  final String trClass = index % 2 == 1 ? "odd" : "even";
                  // �Ƿ���ڹ�������
                  final Object extended = KANUtil.getValue( object, "extended" );

                  rs.append( "<tr ondblclick=kanlist_dbclick(this,null)" + " class='" + trClass + "'>" );
                  //��Ա��½����ʾ
                  if ( !KANConstants.ROLE_EMPLOYEE.equals( EmployeeSecurityAction.getRole( request, null ) ) )
                  {
                     rs.append( "<td>" );

                     if ( KANUtil.filterEmpty( ( String ) extended ) == null || ( ( String ) extended ).trim().equals( "2" ) )
                     {

                        Object owner = KANUtil.getValue( object, "owner" );
                        if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_DELETE, owner == null ? "" : ( String ) owner, request, null ) )
                        {
                           rs.append( "<input type=\"checkbox\" id=\"kanList_chkSelectRecord_" + KANUtil.getValue( object, "encodedId" ) + "\" name=\"chkSelectRow[]\" value=\""
                                 + KANUtil.getValue( object, "encodedId" ) + "\" />" );
                        }
                     }

                     rs.append( "</td>" );
                  }

                  // ������
                  for ( ListDetailVO listDetailVO : listDTO.getListDetailVOs() )
                  {
                     if ( listDetailVO.getDisplay() == null || !listDetailVO.getDisplay().trim().equals( "2" ) )
                     {
                        // ��ʼ���ֶζ���
                        final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( listDetailVO.getColumnId() );
                        if ( isShowColumnId( accessAction, null, columnVO, request ) )
                        {
                           continue;
                        }

                        if ( columnVO != null && columnVO.getColumnId().equals( "8" ) )
                        {
                           rs.append( appendSalaryInfo( request, pagedListHolder.getObject(), object, listDetailVO, 2 ) );
                           continue;
                        }

                        // ��ʼ���б��ֶ���ʽ
                        String valueStyle = "";
                        if ( listDetailVO.getFontSize() != null && !listDetailVO.getFontSize().trim().equals( "" ) && !listDetailVO.getFontSize().trim().equals( "0" ) )
                        {
                           valueStyle = valueStyle + "font-size: " + listDetailVO.getFontSize() + "px;";
                        }
                        if ( valueStyle != null && !valueStyle.trim().equals( "" ) )
                        {
                           valueStyle = " style=\"" + valueStyle + "\" ";
                        }

                        // ��ʼ�����ӷ�ʽ
                        String target = "target=\"_self\"";
                        if ( listDetailVO.getLinkedTarget() != null && listDetailVO.getLinkedTarget().trim().equals( "2" ) )
                        {
                           target = "target=\"_blank\"";
                        }

                        // ��ʼ����̬����
                        String properties[] = null;
                        if ( listDetailVO.getProperties() != null && !listDetailVO.getProperties().trim().equals( "" ) )
                        {
                           properties = listDetailVO.getProperties().trim().split( "," );
                        }

                        rs.append( "<td align=\"" + listDetailVO.getDecodeAlignTemp() + "\">" );

                        // �����ǰ�ֶ��Ǵ����ӵ�
                        if ( listDetailVO.getIsLinked() != null && listDetailVO.getIsLinked().equals( ListDetailVO.TRUE ) && listDetailVO.getLinkedAction() != null
                              && !listDetailVO.getLinkedAction().trim().equals( "" ) )
                        {
                           // ��õ�ǰ�ֶε�����
                           String link = listDetailVO.getLinkedAction();

                           // ��������к��в���
                           if ( link.contains( "${" ) )
                           {
                              // �������滻�����еĲ���
                              if ( properties != null && properties.length > 0 )
                              {
                                 for ( int i = 0; i < properties.length; i++ )
                                 {
                                    final Object propertyValueObject = KANUtil.getValue( object, properties[ i ] );
                                    link = link.replace( "${" + i + "}", propertyValueObject != null ? propertyValueObject.toString() : "" );
                                 }
                              }
                           }

                           // �����ǰ������Ϊ��������ID
                           if ( !link.contains( "&id=" ) )
                           {
                              link = link + "&id=" + KANUtil.getValue( object, "encodedId" );
                           }

                           // ������ӵ�ַ
                           rs.append( "<a href=\"" + link + "\" " + target + ">" );
                        }

                        if ( columnVO != null )
                        {
                           // �����Ҫ������ʽ
                           if ( valueStyle != null && !valueStyle.trim().equals( "" ) )
                           {
                              rs.append( "<span " + valueStyle + ">" );
                           }

                           rs.append( getColumnValue( request, columnVO, listDetailVO, object ) );

                           // �����Ҫ������ʽ
                           if ( valueStyle != null && !valueStyle.trim().equals( "" ) )
                           {
                              rs.append( "</span>" );
                           }
                        }

                        // �����ǰ�ֶ��Ǵ����ӵ�
                        if ( listDetailVO.getIsLinked() != null && listDetailVO.getIsLinked().equals( ListDetailVO.TRUE ) && listDetailVO.getLinkedAction() != null
                              && !listDetailVO.getLinkedAction().trim().equals( "" ) )
                        {
                           rs.append( "</a>" );
                        }

                        // ������ڸ�������
                        if ( listDetailVO.getAppendContent() != null && !listDetailVO.getAppendContent().trim().equals( "" ) )
                        {
                           // ��õ�ǰ�ֶεĸ�������
                           String appendContent = listDetailVO.getAppendContent();

                           // ��������к��в���
                           if ( appendContent.contains( "${" ) )
                           {
                              // �������滻���������еĲ���
                              if ( properties != null && properties.length > 0 )
                              {
                                 for ( int i = 0; i < properties.length; i++ )
                                 {
                                    if ( properties[ i ].equals( "isLink" ) )
                                    {
                                       if ( hasSubmit )
                                       {
                                          final Object propertyValueObject = KANUtil.getValue( object, properties[ i ] );
                                          appendContent = appendContent.replace( "${" + i + "}", propertyValueObject != null ? propertyValueObject.toString() : "" );
                                       }
                                       else
                                       {
                                          appendContent = appendContent.replace( "${" + i + "}", "" );
                                       }
                                    }
                                    else if ( properties[ i ].equals( "isShowHandle" ) && contractAccessAction.contains( accessAction ) )//�Ͷ���ͬҳ����ְ�������ӡȨ�޵�������
                                    {
                                       final Object propertyValueObject = KANUtil.getValue( object, properties[ i ] );
                                       String property = propertyValueObject != null ? propertyValueObject.toString() : "";
                                       String[] operateStr = property.split( "@" );
                                       String operateNew = "";
                                       if ( hasPrint )
                                       {
                                          operateNew = operateNew + operateStr[ 0 ];
                                       }
                                       if ( hasOperate && operateStr.length > 1 )
                                       {
                                          operateNew = operateNew + operateStr[ 1 ];
                                       }
                                       appendContent = appendContent.replace( "${" + i + "}", operateNew );
                                    }
                                    else
                                    {
                                       final Object propertyValueObject = KANUtil.getValue( object, properties[ i ] );
                                       appendContent = appendContent.replace( "${" + i + "}", propertyValueObject != null ? propertyValueObject.toString() : "" );
                                    }
                                 }
                              }
                           }

                           rs.append( appendContent );
                        }

                        rs.append( "</td>" );
                     }
                  }

                  rs.append( "</tr>" );
                  index++;
               }

               rs.append( "</tbody>" );
            }

            if ( listHeaderVO.getUsePagination() != null && listHeaderVO.getUsePagination().trim().equals( "1" ) )
            {
               // ����Table Foot
               rs.append( "<tfoot>" );
               rs.append( "<tr class=\"total\">" );
               rs.append( "<td colspan=\"" + ( listDTO.getListDetailVOs().size() + appendTdSize + 1 ) + "\" class=\"left\">" );
               rs.append( "<label>&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.total" ) + "�� " + pagedListHolder.getHolderSize() + " " );
               rs.append( "<label>&nbsp;&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.current" ) + "��" + pagedListHolder.getIndexStart() + " - "
                     + pagedListHolder.getIndexEnd() + "</label> " );
               rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getFirstPage() + "', null, null, 'tableWrapper', null, "
                     + ( useFixColumn ? "'useFixColumn();'" : "'null'" ) + ");\">" + KANUtil.getProperty( request.getLocale(), "page.first" ) + "</a></label> " );
               rs.append( "<label>&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getPreviousPage() + "', null, null, 'tableWrapper', null, "
                     + ( useFixColumn ? "'useFixColumn();'" : "'null'" ) + ");\">" + KANUtil.getProperty( request.getLocale(), "page.previous" ) + "</a></label> " );
               rs.append( "<label>&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getNextPage() + "', null, null, 'tableWrapper', null, "
                     + ( useFixColumn ? "'useFixColumn();'" : "'null'" ) + " );\">" + KANUtil.getProperty( request.getLocale(), "page.next" ) + "</a></label> " );
               rs.append( "<label>&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getLastPage() + "', null, null, 'tableWrapper', null, "
                     + ( useFixColumn ? "'useFixColumn();'" : "'null'" ) + ");\">" + KANUtil.getProperty( request.getLocale(), "page.last" ) + "</a></label> " );
               rs.append( "<label>&nbsp;&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.pagination" ) + "��" + pagedListHolder.getRealPage() + "/"
                     + pagedListHolder.getPageCount() + "</label>&nbsp;" );
               rs.append( "<label>&nbsp;&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.jump.to" )
                     + "��<input type=\"text\" id=\"forwardPage_render\" class=\"forwardPage_render\" style=\"width:23px;\" value=\"" + pagedListHolder.getRealPage()
                     + "\" onkeydown=\"if(event.keyCode == 13){pageForward('" + ( useFixColumn ? "1" : "2" ) + "');}\" />" + KANUtil.getProperty( request.getLocale(), "page.page" )
                     + "</label>&nbsp;" );
               rs.append( "</td>" );
               rs.append( "</tr>" );
               rs.append( "</tfoot>" );
            }

         }

         rs.append( "</table>" );
      }

      return rs.toString();
   }

   public static String generateListJS( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();
      // ��ʼ��ModuleDTO
      final ModuleDTO moduleDTO = KANConstants.getModuleDTOByAccessAction( accessAction );

      // �˵�ѡ��JS���
      if ( moduleDTO != null && moduleDTO.getModuleVO() != null )
      {
         if ( moduleDTO.getModuleVO().getLevelOneModuleName() != null && !moduleDTO.getModuleVO().getLevelOneModuleName().trim().equals( "" ) )
         {
            rs.append( "$('#" + moduleDTO.getModuleVO().getLevelOneModuleName() + "').addClass('current');" );
         }
         if ( moduleDTO.getModuleVO().getLevelTwoModuleName() != null && !moduleDTO.getModuleVO().getLevelTwoModuleName().trim().equals( "" ) )
         {
            rs.append( "$('#" + moduleDTO.getModuleVO().getLevelTwoModuleName() + "').addClass('selected');" );
         }
         if ( moduleDTO.getModuleVO().getLevelThreeModuleName() != null && !moduleDTO.getModuleVO().getLevelThreeModuleName().trim().equals( "" ) )
         {
            rs.append( "$('#" + moduleDTO.getModuleVO().getLevelThreeModuleName() + "').addClass('selected');" );
         }
      }

      // �б��ʼ��JS���
      rs.append( "kanList_init();kanCheckbox_init();" );

      return rs.toString();
   }

   // �Ƿ񰴿ͻ�ģ�嵼��
   private static String importByClientId( final PagedListHolder pagedListHolder ) throws KANException
   {
      // PagedListHolder������������clientId
      if ( pagedListHolder != null && pagedListHolder.getObject() != null && KANUtil.filterEmpty( ( String ) KANUtil.getValue( pagedListHolder.getObject(), "clientId" ) ) != null )
      {
         return ( String ) KANUtil.getValue( pagedListHolder.getObject(), "clientId" );
      }

      if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         int count = 0;
         String firstClientId = ( String ) KANUtil.getValue( pagedListHolder.getSource().get( 0 ), "clientId" );
         for ( Object object : pagedListHolder.getSource() )
         {
            if ( KANUtil.filterEmpty( firstClientId ) != null && KANUtil.filterEmpty( ( String ) KANUtil.getValue( object, "clientId" ) ) != null )
            {
               if ( !firstClientId.equals( ( String ) KANUtil.getValue( object, "clientId" ) ) )
               {
                  return null;
               }
               else
               {
                  count++;
               }
            }
         }

         if ( count == pagedListHolder.getSource().size() )
         {
            return firstClientId;
         }
      }

      return null;
   }

   // ������ͨ��Excel�ļ�
   // Add by siuvan.xia @ 2014-07-09
   @SuppressWarnings("unchecked")
   public static XSSFWorkbook generateCommonListExcel( final HttpServletRequest request, final String fileName, final Boolean isDTO ) throws KANException
   {
      // ��ʼ��������
      final XSSFWorkbook workbook = new XSSFWorkbook();

      try
      {
         // ��ȡholderName
         final String holderName = ( String ) request.getAttribute( "holderName" );

         // ��ȡ����Դ
         final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( holderName );

         // ��ȡ������ͷ������
         final String[] nameZHArray = ( String[] ) request.getAttribute( "nameZHArray" );

         // ��ȡ������ͷϵͳ��
         final String[] nameSysArray = ( String[] ) request.getAttribute( "nameSysArray" );

         // ��������
         final Font font = workbook.createFont();
         font.setFontName( "Calibri" );
         font.setFontHeightInPoints( ( short ) 11 );

         // ������Ԫ����ʽ
         final CellStyle cellStyleLeft = workbook.createCellStyle();
         cellStyleLeft.setFont( font );
         cellStyleLeft.setAlignment( CellStyle.ALIGN_LEFT );

         // ������Ԫ����ʽ
         final CellStyle cellStyleCenter = workbook.createCellStyle();
         cellStyleCenter.setFont( font );
         cellStyleCenter.setAlignment( CellStyle.ALIGN_CENTER );

         // ������Ԫ����ʽ
         final CellStyle cellStyleRight = workbook.createCellStyle();
         cellStyleRight.setFont( font );
         cellStyleRight.setAlignment( CellStyle.ALIGN_RIGHT );

         // �������
         final Sheet sheet = workbook.createSheet( fileName );
         // ���ñ��Ĭ���п��Ϊ15���ֽ�
         sheet.setDefaultColumnWidth( 15 );

         // ����Excel Header Row
         final Row rowHeader = sheet.createRow( 0 );

         // ���ɱ�ͷ
         if ( nameZHArray != null && nameZHArray.length > 0 )
         {
            // ���Ա�ʶHeader�����
            int headerColumnIndex = 0;
            for ( int i = 0; i < nameZHArray.length; i++ )
            {
               final Cell cell = rowHeader.createCell( headerColumnIndex );
               cell.setCellValue( nameZHArray[ i ] );
               cell.setCellStyle( cellStyleLeft );
               headerColumnIndex++;
            }
         }

         // ���ɱ���
         if ( nameSysArray != null && nameSysArray.length > 0 && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
         {
            // ���Ա�ʶBody�����
            int bodyRowIndex = 1;

            // ������
            for ( Object object : pagedListHolder.getSource() )
            {
               if ( isDTO )
               {
                  // ����Detail��Ϣ��ʾ
                  if ( object instanceof SpecialDTO )
                  {
                     object = KANUtil.getValue( object, "headerVO" );
                  }
               }

               // ���Ա�ʶBody�����
               int bodyColumnIndex = 0;

               // ����Excel Body Row
               final Row rowBody = sheet.createRow( bodyRowIndex );

               // ������
               for ( String column : nameSysArray )
               {
                  if ( column.contains( "." ) )
                  {
                     String value = ( ( Map< String, String > ) KANUtil.getValue( object, "dynaColumns" ) ).get( column.split( "\\." )[ 1 ] );
                     if ( KANUtil.filterEmpty( value ) != null && value.contains( " 00:00:00" ) )
                     {
                        value = KANUtil.formatDate( value, "yyyy-MM-dd", true );
                     }
                     rowBody.createCell( bodyColumnIndex ).setCellValue( value );
                  }
                  else if ( column.startsWith( "salaryItem_" ) )
                  {
                     String tempBase = KANUtil.formatNumber( "0.00", ( String ) KANUtil.getValue( object, "accountId" ) );
                     String itemId = column.split( "_" ).length > 1 ? column.split( "_" )[ 1 ] : "0";
                     final List< MappingVO > salaryMappingVOs = ( List< MappingVO > ) KANUtil.getValue( object, "salarys" );
                     if ( salaryMappingVOs != null && salaryMappingVOs.size() > 0 )
                     {
                        for ( MappingVO tempMappingVO : salaryMappingVOs )
                        {
                           if ( tempMappingVO.getMappingId().equals( itemId ) )
                           {
                              // ����ǽ�����ɣ�
                              if ( "10155".equals( itemId ) )
                              {
                                 tempBase = Double.parseDouble(tempMappingVO.getMappingValue())==0 ? "Yes"
                                       : KANUtil.formatNumber( tempMappingVO.getMappingValue(), ( String ) KANUtil.getValue( object, "accountId" ) );
                              }
                              else
                              {
                                 tempBase = KANUtil.formatNumber( tempMappingVO.getMappingValue(), ( String ) KANUtil.getValue( object, "accountId" ) );
                              }
                              break;
                           }
                        }
                     }

                     rowBody.createCell( bodyColumnIndex ).setCellValue( tempBase );
                  }
                  else
                  {
                     rowBody.createCell( bodyColumnIndex ).setCellValue( KANUtil.getFilterEmptyValue( object, column ) );
                  }
                  bodyColumnIndex++;
               }

               bodyRowIndex++;
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return workbook;
   }

   public static XSSFWorkbook generateSpecialListExcel( final HttpServletRequest request, final String javaObjectName, final String templateType, final String templateId )
         throws KANException
   {
      return generateSpecialListExcelAddExtendColumns( request, javaObjectName, templateType, templateId, null );
   }

   // ��������Excel�ļ�
   // AddedExtendListDTO �������㵥��ʱ�򣬵������б���ʾ���ж༸��
   @SuppressWarnings("unchecked")
   public static XSSFWorkbook generateSpecialListExcelAddExtendColumns( final HttpServletRequest request, final String javaObjectName, final String templateType,
         final String templateId, final ListDTO addedExtendListDTO ) throws KANException
   {
      try
      {
         // ��ʼ��corpId
         final String corpId = KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) );

         // ������籣��
         boolean isSBBill = javaObjectName.equals( SBBillViewAction.JAVA_OBJECT_NAME );

         ListDTO listDTO = null;
         // ��ȡListDTO ���Ϊ�գ����Ǻ�ҳ�浼��ƥ��
         if ( addedExtendListDTO == null )
         {
            listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, corpId );
         }
         //�����Ϊ�գ��򵼳��к�ҳ����ʾ��һ����LISTDTO��Ϊ�������ġ�
         else
         {
            listDTO = addedExtendListDTO;
         }

         // ��ʼ��ģ�����
         Object templateObject = null;

         // ����ģ��
         if ( "1".equals( templateType ) )
         {
            // ��ȡBankTemplateDTO
            templateObject = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getBankTemplateDTOByTemplateHeaderId( templateId );
         }
         // ��˰ģ��
         else if ( "2".equals( templateType ) )
         {
            templateObject = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTaxTemplateDTOByTemplateHeaderId( templateId );
         }

         // ��ʼ��PagedListHolder
         final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );

         final String groupColumn = ( String ) KANUtil.getValue( pagedListHolder.getObject(), "groupColumn" );
         boolean show = true;
         if ( KANUtil.filterEmpty( groupColumn, "0" ) != null )
         {
            show = false;
         }

         // ��ʼ��������
         final XSSFWorkbook workbook = new XSSFWorkbook();

         if ( listDTO != null && pagedListHolder != null )
         {
            //���ƥ��
            Pattern pattern = Pattern.compile( "^(-)?(([1-9]{1}\\d*)|([0]{1}))\\.(\\d){1,4}" );
            // ��������
            final Font font = workbook.createFont();
            font.setFontName( "Calibri" );
            font.setFontHeightInPoints( ( short ) 11 );

            // ������Ԫ����ʽ
            final CellStyle cellStyleLeft = workbook.createCellStyle();
            cellStyleLeft.setFont( font );
            cellStyleLeft.setAlignment( XSSFCellStyle.ALIGN_LEFT );

            final CellStyle cellStyleNumberic = workbook.createCellStyle();
            cellStyleNumberic.setDataFormat( workbook.createDataFormat().getFormat( "##0.00" ) );
            cellStyleNumberic.setAlignment( XSSFCellStyle.ALIGN_RIGHT );
            // ������Ԫ����ʽ
            final CellStyle cellStyleCenter = workbook.createCellStyle();
            cellStyleCenter.setFont( font );
            cellStyleCenter.setAlignment( XSSFCellStyle.ALIGN_CENTER );

            // ������Ԫ����ʽ
            final CellStyle cellStyleRight = workbook.createCellStyle();
            cellStyleRight.setFont( font );
            cellStyleRight.setAlignment( XSSFCellStyle.ALIGN_RIGHT );

            // �������
            final Sheet sheet = workbook.createSheet( listDTO.getListName( request ) );

            // ���ñ��Ĭ���п��Ϊ15���ֽ�
            sheet.setDefaultColumnWidth( 15 );

            // ��ʼ�����ϲ�����ListDetailVO��ListDetailVO�б�
            final List< ListDetailVO > mergeListDetailVOs = new ArrayList< ListDetailVO >();

            // ���Ա�Ƕ����У�Ա��ID��Ա�����������ģ�
            int employeeIdColumnNum = 0;
            int employeeNameZHColumnNum = 0;

            // �����б��ֶ�
            if ( listDTO.getListDetailVOs() != null && listDTO.getListDetailVOs().size() > 0 )
            {
               // ��ʼ��clientId
               final String corpIdTemp = KANUtil.filterEmpty( importByClientId( pagedListHolder ) );

               // ������ͻ�����ƥ��
               if ( corpIdTemp != null && templateObject == null )
               {
                  templateObject = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getMappingDTOByCondition( corpIdTemp, listDTO.getListHeaderVO().getListHeaderId() );
               }

               // ����ģ�����
               if ( templateObject != null )
               {
                  final Method[] methods = templateObject.getClass().getMethods();

                  List< Object > objects = null;

                  for ( Method method : methods )
                  {
                     if ( method.getReturnType() == List.class )
                     {
                        objects = ( List< Object > ) method.invoke( templateObject );
                     }
                  }

                  if ( objects != null && objects.size() > 0 )
                  {
                     for ( Object object : objects )
                     {
                        // ��ʼ��ListDetailVO
                        final ListDetailVO listDetailVO = new ListDetailVO();
                        listDetailVO.setPropertyName( ( String ) KANUtil.getValue( object, "propertyName" ) );
                        listDetailVO.setNameZH( ( String ) KANUtil.getValue( object, "nameZH" ) );
                        listDetailVO.setNameEN( ( String ) KANUtil.getValue( object, "nameEN" ) );
                        listDetailVO.setColumnIndex( ( String ) KANUtil.getValue( object, "columnIndex" ) );
                        listDetailVO.setColumnWidth( ( String ) KANUtil.getValue( object, "columnWidth" ) );
                        listDetailVO.setColumnWidthType( "1" );
                        listDetailVO.setFontSize( ( String ) KANUtil.getValue( object, "fontSize" ) );
                        listDetailVO.setAlign( ( String ) KANUtil.getValue( object, "align" ) );
                        listDetailVO.setIsDecoded( ( String ) KANUtil.getValue( object, "isDecoded" ) );
                        listDetailVO.setDatetimeFormat( ( String ) KANUtil.getValue( object, "datetimeFormat" ) );
                        listDetailVO.setAccuracy( ( String ) KANUtil.getValue( object, "accuracy" ) );
                        listDetailVO.setRound( ( String ) KANUtil.getValue( object, "round" ) );

                        mergeListDetailVOs.add( listDetailVO );
                     }
                  }
               }
               // ��ʹ��ģ��Ĭ�ϰ������б���
               else
               {
                  // װ����ListDetailVO
                  mergeListDetailVOs.addAll( listDTO.getListDetailVOs() );

                  // ������ڴ�ListDetailVO
                  if ( listDTO.getSubListDTOs() != null && listDTO.getSubListDTOs().size() > 0 )
                  {
                     // ��ʼ��Sub ListDetailVO
                     final List< ListDetailVO > subListDetailVOs = mergeListDetailVO( request, listDTO.getSubListDTOs() );

                     if ( subListDetailVOs != null && subListDetailVOs.size() > 0 )
                     {
                        mergeListDetailVOs.addAll( subListDetailVOs );
                     }
                  }

                  // ����
                  if ( mergeListDetailVOs.size() > 0 )
                  {
                     Collections.sort( mergeListDetailVOs, new ComparatorListDetail() );
                  }
               }

               // ����Excel Header Row
               final Row rowHeader = sheet.createRow( 0 );

               // ���Ա�ʶHeader�����
               int headerColumnIndex = 0;

               for ( ListDetailVO listDetailVO : mergeListDetailVOs )
               {
                  // ��ʼ��ListDetailVO
                  listDetailVO.reset( null, request );
                  if ( isShowColumnId( null, javaObjectName, listDetailVO.getPropertyName(), request ) )
                  {
                     continue;
                  }
                  if ( !show
                        && ( ( groupColumn.equals( "1" ) && !ArrayUtils.contains( CROUP_BY_CLIENT_SHOW_COLUMN, listDetailVO.getPropertyName() ) ) || ( groupColumn.equals( "2" ) && !ArrayUtils.contains( CROUP_BY_SOLUTION_SHOW_COLUMN, listDetailVO.getPropertyName() ) ) ) )
                  {
                     if ( listDetailVO.getLength() == 0 )
                     {
                        continue;
                     }
                  }

                  if ( KANUtil.encodeString( listDetailVO.getPropertyName() ) != null && listDetailVO.getPropertyName().equals( "employeeId" ) )
                     employeeIdColumnNum = headerColumnIndex;

                  if ( KANUtil.encodeString( listDetailVO.getPropertyName() ) != null && listDetailVO.getPropertyName().equals( "employeeNameZH" ) )
                     employeeNameZHColumnNum = headerColumnIndex;

                  // ��ʼ��Excel�п�����û�����̶��п�����
                  if ( listDetailVO.getColumnWidthType() != null && listDetailVO.getColumnWidthType().trim().equals( "2" ) && listDetailVO.getColumnWidth() != null
                        && listDetailVO.getColumnWidth().trim().matches( "[0-9]*" ) )
                  {
                     // ����Excel�п�ȡ��
                     final BigDecimal columnWidth = new BigDecimal( Integer.valueOf( listDetailVO.getColumnWidth() ) * 30 ).setScale( 0, BigDecimal.ROUND_HALF_UP );
                     // ����Excel�п�
                     sheet.setColumnWidth( headerColumnIndex, columnWidth == null ? 0 : columnWidth.intValue() );
                  }

                  // ��ʼ��Excel��ͷ��
                  String excelTitle = "";
                  if ( isSBBill && listDetailVO.isSBItem() )
                  {
                     excelTitle = "����";
                     final Cell cellBase = rowHeader.createCell( headerColumnIndex );
                     final XSSFRichTextString textBase = new XSSFRichTextString( excelTitle );
                     cellBase.setCellValue( textBase );
                     cellBase.setCellStyle( cellStyleLeft );
                     headerColumnIndex++;

                     excelTitle = "����%";
                     final Cell cellRate = rowHeader.createCell( headerColumnIndex );
                     final XSSFRichTextString textRate = new XSSFRichTextString( excelTitle );
                     cellRate.setCellValue( textRate );
                     cellRate.setCellStyle( cellStyleLeft );
                     headerColumnIndex++;
                  }

                  excelTitle = getColumnName( request, listDetailVO );
                  final Cell cell = rowHeader.createCell( headerColumnIndex );
                  final XSSFRichTextString text = new XSSFRichTextString( excelTitle );
                  cell.setCellValue( text );
                  cell.setCellStyle( cellStyleLeft );

                  headerColumnIndex++;
               }

               // ��������Excel Body
               if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
               {
                  // ���Ա�ʶBody�����
                  int bodyRowIndex = 1;

                  // ������
                  for ( Object object : pagedListHolder.getSource() )
                  {
                     // ��ʼ��Object - DTO[Header]
                     Object objectHeader = null;
                     // ��ʼ��List< Object > - DTO[Details]
                     List< ? > objectDetails = null;
                     if ( object instanceof SpecialDTO )
                     {
                        // ��ȡSpecialDTO
                        final SpecialDTO< Object, List< ? > > specialDTO = ( SpecialDTO< Object, List< ? > > ) object;

                        objectDetails = specialDTO.getDetailVOs();
                        objectHeader = specialDTO.getHeaderVO();
                        // ���ʻ���ֵ    
                        ( ( ActionForm ) objectHeader ).reset( null, request );
                     }

                     // ����Excel Body Row
                     final Row rowBody = sheet.createRow( bodyRowIndex );

                     // ���Ա�ʶBody�����
                     int bodyColumnIndex = 0;

                     // ������
                     for ( ListDetailVO listDetailVO : mergeListDetailVOs )
                     {
                        if ( isShowColumnId( null, javaObjectName, listDetailVO.getPropertyName(), request ) )
                        {
                           continue;
                        }
                        if ( !show
                              && ( ( groupColumn.equals( "1" ) && !ArrayUtils.contains( CROUP_BY_CLIENT_SHOW_COLUMN, listDetailVO.getPropertyName() ) ) || ( groupColumn.equals( "2" ) && !ArrayUtils.contains( CROUP_BY_SOLUTION_SHOW_COLUMN, listDetailVO.getPropertyName() ) ) ) )
                        {
                           if ( listDetailVO.getLength() == 0 )
                           {
                              continue;
                           }
                        }

                        // ��ʼ��Excel��Ԫ��ֵ
                        String columnValue = "";

                        // ��ȡ����
                        if ( isSBBill && listDetailVO.isSBItem() )
                        {
                           columnValue = getColumnValue( request, listDetailVO, objectHeader, objectDetails, listDetailVO.filterCompany() ? "baseCompany" : "basePersonal" );
                           // ��ʼ��XSSFCell
                           final Cell cellBase = rowBody.createCell( bodyColumnIndex );
                           // ���õ�Ԫ�����
                           cellBase.setCellStyle( cellStyleLeft );

                           if ( listDetailVO.getAlign() != null )
                           {
                              if ( listDetailVO.getAlign().trim().equals( "2" ) )
                              {
                                 cellBase.setCellStyle( cellStyleCenter );
                              }
                              else if ( listDetailVO.getAlign().trim().equals( "3" ) )
                              {
                                 cellBase.setCellStyle( cellStyleRight );
                              }
                           }

                           if ( StringUtils.isNotBlank( columnValue ) )
                           {
                              Matcher matcher = pattern.matcher( columnValue );
                              if ( matcher.matches() )
                              {
                                 cellBase.setCellType( Cell.CELL_TYPE_NUMERIC );
                                 cellBase.setCellValue( Double.parseDouble( columnValue ) );
                                 cellBase.setCellStyle( cellStyleNumberic );
                              }
                              else
                              {
                                 final XSSFRichTextString textBase = new XSSFRichTextString( columnValue );
                                 cellBase.setCellValue( textBase );
                              }
                           }

                           bodyColumnIndex++;

                           columnValue = getColumnValue( request, listDetailVO, objectHeader, objectDetails, listDetailVO.filterCompany() ? "rateCompany" : "ratePersonal" );
                           // ��ʼ��XSSFCell
                           final Cell cellRate = rowBody.createCell( bodyColumnIndex );
                           // ���õ�Ԫ�����
                           cellRate.setCellStyle( cellStyleLeft );

                           if ( listDetailVO.getAlign() != null )
                           {
                              if ( listDetailVO.getAlign().trim().equals( "2" ) )
                              {
                                 cellRate.setCellStyle( cellStyleCenter );
                              }
                              else if ( listDetailVO.getAlign().trim().equals( "3" ) )
                              {
                                 cellRate.setCellStyle( cellStyleRight );
                              }
                           }

                           if ( StringUtils.isNotBlank( columnValue ) )
                           {
                              Matcher matcher = pattern.matcher( columnValue );
                              if ( matcher.matches() )
                              {
                                 cellRate.setCellType( Cell.CELL_TYPE_NUMERIC );
                                 cellRate.setCellValue( Double.parseDouble( columnValue ) );
                                 cellRate.setCellStyle( cellStyleNumberic );
                              }
                              else
                              {
                                 final XSSFRichTextString textRate = new XSSFRichTextString( columnValue );
                                 cellRate.setCellValue( textRate );
                              }
                           }

                           bodyColumnIndex++;
                        }

                        columnValue = getColumnValue( request, listDetailVO, objectHeader, objectDetails, null );

                        // ��ʼ��XSSFCell
                        final Cell cell = rowBody.createCell( bodyColumnIndex );

                        // ���õ�Ԫ�����
                        cell.setCellStyle( cellStyleLeft );
                        if ( listDetailVO.getAlign() != null )
                        {
                           if ( listDetailVO.getAlign().trim().equals( "2" ) )
                           {
                              cell.setCellStyle( cellStyleCenter );
                           }
                           else if ( listDetailVO.getAlign().trim().equals( "3" ) )
                           {
                              cell.setCellStyle( cellStyleRight );
                           }
                        }

                        if ( StringUtils.isNotBlank( columnValue ) )
                        {
                           Matcher matcher = pattern.matcher( columnValue );
                           if ( matcher.matches() )
                           {
                              cell.setCellType( Cell.CELL_TYPE_NUMERIC );
                              cell.setCellValue( Double.parseDouble( columnValue ) );
                              cell.setCellStyle( cellStyleNumberic );
                           }
                           else
                           {
                              final XSSFRichTextString text = new XSSFRichTextString( columnValue );
                              cell.setCellValue( text );
                           }
                        }

                        bodyColumnIndex++;
                     }

                     bodyRowIndex++;
                  }
               }
            }

            // ����Ա��ID��Ա������
            sheet.createFreezePane( employeeIdColumnNum + 1, 0, employeeIdColumnNum + 1, 0 );
            sheet.createFreezePane( employeeNameZHColumnNum + 1, 0, employeeNameZHColumnNum + 1, 0 );
         }

         return workbook;
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   public static XSSFWorkbook generateListExcel( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      // ���Log Start����
      LogFactory.getLog( accessAction ).info( "Excel Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") Start." );

      // ��ȡ��ǰ��Ҫ���ɿؼ���������TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );
      // ��ʼ��PagedListHolder
      final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );

      // ��ʼ��������
      final XSSFWorkbook workbook = new XSSFWorkbook();

      if ( tableDTO != null && tableDTO.getListDTOs() != null && pagedListHolder != null && tableDTO.getListDTOs().size() > 0 )
      {
         // ��ʼ��ListDTO
         final ListDTO listDTO = tableDTO.getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );

         // ��������
         final Font font = workbook.createFont();
         font.setFontName( "Calibri" );
         font.setFontHeightInPoints( ( short ) 11 );

         // ������Ԫ����ʽ
         final CellStyle cellStyleLeft = workbook.createCellStyle();
         cellStyleLeft.setFont( font );
         cellStyleLeft.setAlignment( XSSFCellStyle.ALIGN_LEFT );

         // ������Ԫ����ʽ
         final CellStyle cellStyleCenter = workbook.createCellStyle();
         cellStyleCenter.setFont( font );
         cellStyleCenter.setAlignment( XSSFCellStyle.ALIGN_CENTER );

         // ������Ԫ����ʽ
         final CellStyle cellStyleRight = workbook.createCellStyle();
         cellStyleRight.setFont( font );
         cellStyleRight.setAlignment( XSSFCellStyle.ALIGN_RIGHT );

         // �������
         final Sheet sheet = workbook.createSheet( listDTO.getListName( request ) );

         // ���ñ��Ĭ���п��Ϊ15���ֽ�
         sheet.setDefaultColumnWidth( 15 );

         if ( listDTO.getListDetailVOs() != null && listDTO.getListDetailVOs().size() > 0 )
         {
            // ����Excel Header Row
            final Row rowHeader = sheet.createRow( 0 );

            // ���Ա�ʶHeader�����
            int headerColumnIndex = 0;

            for ( ListDetailVO listDetailVO : listDTO.getListDetailVOs() )
            {
               // ��ʼ��ListDetailVO
               listDetailVO.reset( null, request );
               final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( listDetailVO.getColumnId() );
               if ( columnVO == null || columnVO.getColumnId().equals( "1" ) )
               {
                  continue;
               }
               if ( StringUtils.equals( listDetailVO.getProperties(), "isShowHandle" ) && "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT".equals( accessAction ) )
               {
                  continue;
               }
               if ( isShowColumnId( accessAction, null, columnVO, request ) )
               {
                  continue;
               }
               if ( columnVO != null && columnVO.getColumnId().equals( "8" ) )
               {
                  exportExcelAppendSalaryInfo( request, pagedListHolder.getObject(), null, listDetailVO, sheet, rowHeader, cellStyleLeft, cellStyleCenter, cellStyleRight, headerColumnIndex, 1 );
                  continue;
               }
               final Cell cell = rowHeader.createCell( headerColumnIndex );
               final XSSFRichTextString text = new XSSFRichTextString( getColumnName( request, listDetailVO ) );
               cell.setCellValue( text );
               cell.setCellStyle( cellStyleLeft );

               // ��ʼ��Excel�п�����û�����̶��п�����
               if ( listDetailVO.getColumnWidthType() != null && listDetailVO.getColumnWidthType().trim().equals( "2" ) && listDetailVO.getColumnWidth() != null
                     && listDetailVO.getColumnWidth().trim().matches( "[0-9]*" ) )
               {
                  // ����Excel�п�ȡ��
                  final BigDecimal columnWidth = new BigDecimal( Integer.valueOf( listDetailVO.getColumnWidth() ) * 30 ).setScale( 0, BigDecimal.ROUND_HALF_UP );
                  // ����Excel�п�
                  sheet.setColumnWidth( headerColumnIndex, columnWidth.intValue() );
               }

               headerColumnIndex++;
            }

            // ��������Excel Body
            if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               // ���Ա�ʶBody�����
               int bodyRowIndex = 1;

               // ������
               for ( Object object : pagedListHolder.getSource() )
               {
                  // ����Excel Body Row
                  final Row rowBody = sheet.createRow( bodyRowIndex );

                  // ���Ա�ʶBody�����
                  int bodyColumnIndex = 0;

                  // ������
                  for ( ListDetailVO listDetailVO : listDTO.getListDetailVOs() )
                  {
                     // ��ʼ���ֶζ���
                     final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( listDetailVO.getColumnId() );
                     if ( columnVO == null || columnVO.getColumnId().equals( "1" ) )
                     {
                        continue;
                     }
                     if ( StringUtils.equals( listDetailVO.getProperties(), "isShowHandle" ) && "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT".equals( accessAction ) )
                     {
                        continue;
                     }
                     if ( isShowColumnId( accessAction, null, columnVO, request ) )
                     {
                        continue;
                     }
                     if ( columnVO != null && columnVO.getColumnId().equals( "8" ) )
                     {
                        exportExcelAppendSalaryInfo( request, pagedListHolder.getObject(), object, listDetailVO, sheet, rowBody, cellStyleLeft, cellStyleCenter, cellStyleRight, bodyColumnIndex, 2 );
                        continue;
                     }
                     final Cell cell = rowBody.createCell( bodyColumnIndex );
                     final XSSFRichTextString text = new XSSFRichTextString( getColumnValue( request, columnVO, listDetailVO, object ) );
                     // ���õ�Ԫ��ֵ
                     cell.setCellValue( text );

                     // ���õ�Ԫ�����
                     cell.setCellStyle( cellStyleLeft );
                     if ( listDetailVO.getAlign() != null )
                     {
                        if ( listDetailVO.getAlign().trim().equals( "2" ) )
                        {
                           cell.setCellStyle( cellStyleCenter );
                        }
                        else if ( listDetailVO.getAlign().trim().equals( "3" ) )
                        {
                           cell.setCellStyle( cellStyleRight );
                        }
                     }

                     bodyColumnIndex++;
                  }

                  bodyRowIndex++;
               }
            }
         }
      }

      // ���Log End����
      LogFactory.getLog( accessAction ).info( "Excel Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") End." );

      return workbook;
   }

   /**  
    * generatePositionListExcelByBranch
    *	�����ŵĲ㼶��ϵ����Excel
    *	@param request
    *	@param accessAction
    *	@return
    * Add By��Jack  
    * Add Date��2015-2-4  
    * @throws KANException 
    */
   public static XSSFWorkbook generatePositionListExcelByBranch( HttpServletRequest request, String accessAction ) throws KANException
   {
      // ��Constants�еõ���ǰAccount��BranchDTO���б�
      final List< BranchDTO > branchDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getBranchDTOsByCorpId( BaseAction.getCorpId( request, null ) );
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionDTOsByCorpId( BaseAction.getCorpId( request, null ) );

      // ��ʼ��������
      final XSSFWorkbook workbook = new XSSFWorkbook();

      if ( branchDTOs != null && branchDTOs.size() > 0 )
      {
         // ������� - ���ñ���
         final Sheet sheet = workbook.createSheet( "ְλ�������Ų㼶��ϵ��" );

         // ���ñ��Ĭ���п��Ϊ15���ֽ�
         sheet.setDefaultColumnWidth( 15 );

         final Map< String, BranchDTO > branchMap = new HashMap< String, BranchDTO >();

         /**
          *  ���ɱ�ͷ
          */
         // Ĭ��5��
         int branch_num = 5;

         final Row row = sheet.createRow( 0 );

         // ǰ2�����ְλ��Ϣ
         final Cell nameCell_first = row.createCell( 0 );
         nameCell_first.setCellStyle( getFirstCellStyleByLevel( workbook ) );
         nameCell_first.setCellValue( "ְλ����" );

         final Cell idCell_first = row.createCell( 1 );
         idCell_first.setCellStyle( getHeaderCellStyleByLevel( workbook ) );
         idCell_first.setCellValue( "ְλID" );

         // �������������ְλ��Ӧ��ְ����Ϣ
         final Cell nameCell_second = row.createCell( 2 );
         nameCell_second.setCellStyle( getHeaderCellStyleByLevel( workbook ) );
         nameCell_second.setCellValue( "ְ������" );

         final Cell idCell_second = row.createCell( 3 );
         idCell_second.setCellStyle( getHeaderCellStyleByLevel( workbook ) );
         idCell_second.setCellValue( "ְ��ID" );

         // ��ʼ�����г��ȵļ���
         Set< String > branchSizeSet = new HashSet< String >();

         // excel������Ϣ
         getPositionSheetByBranch( sheet, workbook, branchDTOs, positionDTOs, 0, branchMap, request, branchSizeSet );

         // ����������ʾ
         for ( int i = 0; i < branch_num; i++ )
         {
            final Cell nameCell_branch = row.createCell( 4 + i * 2 );
            nameCell_second.setCellStyle( getHeaderCellStyleByLevel( workbook ) );
            nameCell_branch.setCellValue( "�������ƣ�Level:" + ( i + 1 ) + "��" );

            final Cell idCell_branch = row.createCell( 5 + i * 2 );
            idCell_second.setCellStyle( getHeaderCellStyleByLevel( workbook ) );
            idCell_branch.setCellValue( "����ID��Level:" + ( i + 1 ) + "��" );
         }
      }

      return workbook;
   }

   /**  
    * getBranchNode
    * �����Ų㼶��ϵ����ְλExcel
    * @param sheet - ְλ��
    * @param workbook - ������
    * @param branchDTOs - ������
    * @param positionDTOs - ְλ��
    * @param level - ��ǰ�㼶
    * @param rowNum - ��ǰ�к�
    * @param branchMap - ְλ���ϼ����ż���
    * @param request 
    * @param branchSizeSet 
    * Add By��Jack  
    * Add Date��2015-2-2  
    * @throws KANException 
    */
   private static void getPositionSheetByBranch( final Sheet sheet, XSSFWorkbook workbook, final List< BranchDTO > branchDTOs, List< PositionDTO > positionDTOs, final int level,
         final Map< String, BranchDTO > branchMap, HttpServletRequest request, Set< String > branchSizeSet ) throws KANException
   {
      if ( level == 0 )
      {
         branchMap.clear();
      }
      // ��ʼ�����ż�λ�����ֵ
      int branch_num = 0;

      if ( branchDTOs != null && branchDTOs.size() > 0 )
      {

         for ( BranchDTO branchDTO : branchDTOs )
         {
            // ��ʼ���ò����¶�Ӧ��ְλ
            List< PositionDTO > positionDTOs_inBranch = new ArrayList< PositionDTO >();

            // ��ѯ�ò����¶�Ӧ��ְλ
            if ( branchDTO != null && branchDTO.getBranchVO() != null && branchDTO.getBranchVO().getBranchId() != null )
            {
               fetchPositionDTOsByBranchId( positionDTOs_inBranch, branchDTO.getBranchVO().getBranchId(), positionDTOs );
            }

            // ��ò��ż�λ�����ֵ
            if ( positionDTOs_inBranch != null && positionDTOs_inBranch.size() > 0 )
            {
               branchSizeSet.add( positionDTOs_inBranch.size() + "" );

               if ( positionDTOs_inBranch.size() > branch_num )
               {
                  branch_num = positionDTOs_inBranch.size();
               }
            }

            // ������Ŵ��ڶ�Ӧְλ
            if ( positionDTOs_inBranch.size() > 0 )
            {
               for ( PositionDTO positionDTO : positionDTOs_inBranch )
               {
                  final PositionVO positionVO = positionDTO.getPositionVO();
                  if ( positionVO != null )
                  {
                     // ���ְλ��Ӧְ��
                     PositionGradeVO positionGradeVO = new PositionGradeVO();
                     final String positionGradeId = positionVO.getPositionGradeId();

                     if ( KANUtil.filterEmpty( positionGradeId ) != null )
                     {
                        positionGradeVO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionGradeVOByPositionGradeId( positionGradeId );
                     }

                     // ����Excel Body Cell
                     final Row row = sheet.createRow( sheet.getPhysicalNumberOfRows() );

                     // ǰ2�����ְλ��Ϣ
                     final Cell nameCell_first = row.createCell( 0 );
                     nameCell_first.setCellStyle( getFirstCellStyleByLevel( workbook ) );
                     nameCell_first.setCellValue( positionVO.getTitleZH() );

                     final Cell idCell_first = row.createCell( 1 );
                     idCell_first.setCellStyle( getFirstCellStyleByLevel( workbook ) );
                     idCell_first.setCellValue( positionVO.getPositionId() );

                     // �������������ְλ��Ӧ��ְ����Ϣ
                     final Cell nameCell_second = row.createCell( 2 );
                     nameCell_second.setCellStyle( getSecondCellStyleByLevel( workbook ) );
                     nameCell_second.setCellValue( positionGradeVO.getGradeNameZH() );

                     final Cell idCell_second = row.createCell( 3 );
                     idCell_second.setCellStyle( getSecondCellStyleByLevel( workbook ) );
                     idCell_second.setCellValue( positionGradeId );

                     // ���ݲ��ŵĲ㼶��ϵ������ʽ
                     final Cell nameCell = row.createCell( ( level + 2 ) * 2 );
                     nameCell.setCellStyle( getCellStyleByLevel( workbook, level ) );
                     nameCell.setCellValue( branchDTO.getBranchVO().getNameZH() );

                     final Cell idCell = row.createCell( ( level + 2 ) * 2 + 1 );
                     idCell.setCellStyle( getCellStyleByLevel( workbook, level ) );
                     idCell.setCellValue( branchDTO.getBranchVO().getBranchId() );

                     // ����ϼ�����
                     if ( level > 0 )
                     {
                        for ( int i = 0; i < level; i++ )
                        {
                           // ���ݲ��ŵĲ㼶��ϵ������ʽ
                           final Cell nameCell_temp = row.createCell( ( i + 2 ) * 2 );
                           nameCell_temp.setCellStyle( getCellStyleByLevel( workbook, i ) );
                           nameCell_temp.setCellValue( branchMap.get( String.valueOf( i ) ).getBranchVO().getNameZH() );

                           final Cell idCell_temp = row.createCell( ( i + 2 ) * 2 + 1 );
                           idCell_temp.setCellStyle( getCellStyleByLevel( workbook, i ) );
                           idCell_temp.setCellValue( branchMap.get( String.valueOf( i ) ).getBranchVO().getBranchId() );
                        }
                     }

                  }
               }
            }
            else
            {
               // ����Excel Body Cell
               final Row row = sheet.createRow( sheet.getPhysicalNumberOfRows() );

               // ���ݲ��ŵĲ㼶��ϵ������ʽ
               final Cell nameCell = row.createCell( ( level + 2 ) * 2 );
               nameCell.setCellStyle( getCellStyleByLevel( workbook, level ) );
               nameCell.setCellValue( branchDTO.getBranchVO().getNameZH() );

               final Cell idCell = row.createCell( ( level + 2 ) * 2 + 1 );
               idCell.setCellStyle( getCellStyleByLevel( workbook, level ) );
               idCell.setCellValue( branchDTO.getBranchVO().getBranchId() );

               // ����ϼ�����
               if ( level > 0 )
               {
                  for ( int i = 0; i < level; i++ )
                  {
                     // ���ݲ��ŵĲ㼶��ϵ������ʽ
                     final Cell nameCell_temp = row.createCell( ( i + 2 ) * 2 );
                     nameCell_temp.setCellStyle( getCellStyleByLevel( workbook, i ) );
                     nameCell_temp.setCellValue( branchMap.get( String.valueOf( i ) ).getBranchVO().getNameZH() );

                     final Cell idCell_temp = row.createCell( ( i + 2 ) * 2 + 1 );
                     idCell_temp.setCellStyle( getCellStyleByLevel( workbook, i ) );
                     idCell_temp.setCellValue( branchMap.get( String.valueOf( i ) ).getBranchVO().getBranchId() );
                  }
               }
            }

            if ( branchDTO.getBranchDTOs() != null && branchDTO.getBranchDTOs().size() > 0 )
            {
               branchMap.put( String.valueOf( level ), branchDTO );
               getPositionSheetByBranch( sheet, workbook, branchDTO.getBranchDTOs(), positionDTOs, level + 1, branchMap, request, branchSizeSet );
            }

         }

      }

   }

   /**  
    * getPositionDTOsByBranchId
    *	��ò����¶�Ӧ��ְλ����
    * @param positionDTOs_inBranch - ���صļ���
    *	@param branchId - ��Ӧ�Ĳ���ID
    *	@param positionDTOs - ����ְλ
    *	@return
    * Add By��Jack  
    * Add Date��2015-2-4  
    */
   private static void fetchPositionDTOsByBranchId( List< PositionDTO > positionDTOs_inBranch, String branchId, List< PositionDTO > positionDTOs )
   {
      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         for ( PositionDTO positionDTO : positionDTOs )
         {
            if ( positionDTO.getPositionVO() != null && positionDTO.getPositionVO().getPositionId() != null )
            {
               if ( branchId.equals( positionDTO.getPositionVO().getBranchId() ) )
               {
                  positionDTOs_inBranch.add( positionDTO );
               }
            }

            // ���ְλ�ĴӶ������ͬ����֤
            if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
            {
               fetchPositionDTOsByBranchId( positionDTOs_inBranch, branchId, positionDTO.getPositionDTOs() );
            }
         }
      }
   }

   /**  
    * generatePositionListExcelByPosition
    *	��ְλ�Ĳ㼶��ϵ����Excel
    *	@param request
    *	@param accessAction
    *	@return
    *	@throws KANException
    * Add By��Jack  
    * Add Date��2015-2-4  
    */
   public static XSSFWorkbook generatePositionListExcelByPosition( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      // ��Constants�еõ���ǰAccount��PositionDTO���б�
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionDTOsByCorpId( BaseAction.getCorpId( request, null ) );

      // ��ʼ��������
      final XSSFWorkbook workbook = new XSSFWorkbook();

      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         // ������� - ���ñ���
         final XSSFSheet sheet = workbook.createSheet( "ְλ����ְλ�ȼ���" );

         // ���ñ��Ĭ���п��Ϊ15���ֽ�
         sheet.setDefaultColumnWidth( 15 );

         final Map< String, PositionDTO > positionMap = new HashMap< String, PositionDTO >();

         final XSSFCellStyle titleXSSFCellStyle = workbook.createCellStyle();
         titleXSSFCellStyle.setAlignment( XSSFCellStyle.ALIGN_CENTER );
         final XSSFFont font = workbook.createFont();
         font.setFontName( "Calibri" );
         font.setFontHeightInPoints( ( short ) 14 );
         font.setColor( IndexedColors.LIGHT_BLUE.getIndex() );
         font.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );
         titleXSSFCellStyle.setFont( font );

         final XSSFRow fisrtTitleRow = sheet.createRow( 0 );

         final XSSFCell positionIdCell = fisrtTitleRow.createCell( 0 );
         positionIdCell.setCellValue( "Position ID" );
         positionIdCell.setCellStyle( titleXSSFCellStyle );

         final XSSFCell positionTitleCell = fisrtTitleRow.createCell( 1 );
         positionTitleCell.setCellValue( "Title" );
         positionTitleCell.setCellStyle( titleXSSFCellStyle );

         final XSSFCell level1Cell = fisrtTitleRow.createCell( 2 );
         level1Cell.setCellValue( "Level 1" );
         level1Cell.setCellStyle( titleXSSFCellStyle );

         final XSSFCell level2Cell = fisrtTitleRow.createCell( 3 );
         level2Cell.setCellValue( "Level 2" );
         level2Cell.setCellStyle( titleXSSFCellStyle );

         final XSSFCell level3Cell = fisrtTitleRow.createCell( 4 );
         level3Cell.setCellValue( "Level 3" );
         level3Cell.setCellStyle( titleXSSFCellStyle );

         getPositionSheet( sheet, workbook, positionDTOs, 0, positionMap );
      }

      return workbook;
   }

   /**  
    * getPositionNode
    *	����ְλExcel
    *	@param sheet - ְλ��
    * @param workbook - ������
    *	@param positionDTOs - ְλ��
    *	@param level - ��ǰ�㼶
    *	@param rowNum - ��ǰ�к�
    * @param positionMap - ְλ���ϼ�ְλ����
    * Add By��Jack  
    * Add Date��2015-2-2  
    */
   private static void getPositionSheet( final Sheet sheet, XSSFWorkbook workbook, final List< PositionDTO > positionDTOs, final int level,
         final Map< String, PositionDTO > positionMap )
   {
      if ( level == 0 )
      {
         positionMap.clear();
      }

      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         for ( PositionDTO positionDTO : positionDTOs )
         {
            // ����Excel Body Cell
            final Row row = sheet.createRow( sheet.getPhysicalNumberOfRows() );

            final Cell idCell_first = row.createCell( 0 );
            idCell_first.setCellStyle( getFirstCellStyleByLevel( workbook ) );
            idCell_first.setCellValue( positionDTO.getPositionVO().getPositionId() );

            // ��һ�е�Ԫ�����ְλID��Ϣ
            final Cell nameCell_first = row.createCell( 1 );
            nameCell_first.setCellStyle( getFirstCellStyleByLevel( workbook ) );
            nameCell_first.setCellValue( positionDTO.getPositionVO().getTitleZH() );

            // ���ݲ��ŵĲ㼶��ϵ������ʽ
            String cellValue = "";
            cellValue = positionDTO.getPositionVO().getTitleZH();
            String staffNames = getStaffNamesByPositionVO( KANConstants.getKANAccountConstants( positionDTO.getPositionVO().getAccountId() ), positionDTO.getPositionVO() );
            cellValue = cellValue + "Ա����" + ( ( KANUtil.filterEmpty( staffNames ) == null ) ? "��" : staffNames );
            final Cell nameCell = row.createCell( level + 2 );
            nameCell.setCellStyle( getCellStyleByLevel( workbook, level ) );
            nameCell.setCellValue( cellValue );

            // ����ϼ�ְλ
            if ( level > 0 )
            {
               for ( int i = 0; i < level; i++ )
               {
                  String parentCellValue = "";
                  // ���ݲ��ŵĲ㼶��ϵ������ʽ
                  final Cell nameCell_temp = row.createCell( i + 2 );
                  nameCell_temp.setCellStyle( getCellStyleByLevel( workbook, i ) );
                  parentCellValue = positionMap.get( String.valueOf( i ) ).getPositionVO().getTitleZH();
                  String parentStaffNames = getStaffNamesByPositionVO( KANConstants.getKANAccountConstants( positionDTO.getPositionVO().getAccountId() ), positionMap.get( String.valueOf( i ) ).getPositionVO() );
                  parentCellValue = parentCellValue + "Ա����" + ( ( KANUtil.filterEmpty( parentStaffNames ) == null ) ? "��" : parentStaffNames );

                  nameCell_temp.setCellValue( parentCellValue );
               }
            }

            if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
            {
               positionMap.put( String.valueOf( level ), positionDTO );
               getPositionSheet( sheet, workbook, positionDTO.getPositionDTOs(), level + 1, positionMap );
            }
         }
      }
   }

   /**  
    * getHeaderCellStyleByLevel
    *	��ͷ��ʽ
    *	@param workbook
    *	@return
    * Add By��Jack  
    * Add Date��2015-2-11  
    */
   private static CellStyle getHeaderCellStyleByLevel( XSSFWorkbook workbook )
   {
      // ��������
      final Font font = workbook.createFont();
      font.setFontName( "Calibri" );
      font.setFontHeightInPoints( ( short ) 11 );

      // ������Ԫ����ʽ(����)
      final CellStyle cellStyle = workbook.createCellStyle();
      cellStyle.setFont( font );
      cellStyle.setAlignment( XSSFCellStyle.ALIGN_CENTER );

      return cellStyle;
   }

   /**  
    * getFirstCellStyleByLevel
    *	ְλ�������ţ�ǰ������ʽ
    *	@param workbook
    *	@return
    * Add By��Jack  
    * Add Date��2015-2-11  
    */
   private static CellStyle getFirstCellStyleByLevel( XSSFWorkbook workbook )
   {
      // ��������
      final Font font = workbook.createFont();
      font.setFontName( "����_GB2312" );
      // �����С
      font.setFontHeightInPoints( ( short ) 10 );
      // ��ɫ����
      font.setColor( IndexedColors.LIGHT_BLUE.getIndex() );
      // �Ӵ�
      font.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );

      // ������Ԫ����ʽ
      final CellStyle cellStyle = workbook.createCellStyle();
      cellStyle.setFont( font );
      cellStyle.setAlignment( XSSFCellStyle.ALIGN_CENTER );
      //      cellStyle.setFillForegroundColor( IndexedColors.LAVENDER.getIndex() );
      //      cellStyle.setFillPattern( CellStyle.SOLID_FOREGROUND );

      return cellStyle;
   }

   /**  
    * getSecondCellStyleByLevel
    *	ְλ�������ţ�3��4����ʽ
    *	@param workbook
    *	@return
    * Add By��Jack  
    * Add Date��2015-2-11  
    */
   private static CellStyle getSecondCellStyleByLevel( XSSFWorkbook workbook )
   {
      // ��������
      final Font font = workbook.createFont();
      font.setFontName( "����_GB2312" );
      // �����С
      font.setFontHeightInPoints( ( short ) 10 );
      // ��ɫ����
      font.setColor( IndexedColors.LAVENDER.getIndex() );
      // �Ӵ�
      font.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );

      // ������Ԫ����ʽ
      final CellStyle cellStyle = workbook.createCellStyle();
      cellStyle.setFont( font );
      cellStyle.setAlignment( XSSFCellStyle.ALIGN_CENTER );
      //      cellStyle.setFillForegroundColor( IndexedColors.LAVENDER.getIndex() );
      //      cellStyle.setFillPattern( CellStyle.SOLID_FOREGROUND );

      return cellStyle;
   }

   private static CellStyle getCellStyleByLevel( XSSFWorkbook workbook, int level )
   {
      // ��������
      final Font font = workbook.createFont();
      font.setFontName( "Calibri" );
      font.setFontHeightInPoints( ( short ) 11 );

      // ������Ԫ����ʽ
      final CellStyle cellStyle = workbook.createCellStyle();
      cellStyle.setFont( font );

      // ���ñ�����ɫ
      if ( level % 2 == 0 )
      {
         cellStyle.setFillForegroundColor( IndexedColors.GREY_25_PERCENT.getIndex() );
      }
      else if ( level % 2 != 0 )
      {
         cellStyle.setFillForegroundColor( IndexedColors.LIME.getIndex() );
      }

      cellStyle.setFillPattern( CellStyle.SOLID_FOREGROUND );

      return cellStyle;
   }

   private static String getColumnName( final HttpServletRequest request, final ListDetailVO listDetailVO ) throws KANException
   {
      // �б��ֶ���ʾ����
      String columnName = "";

      if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
      {
         columnName = listDetailVO.getNameZH();
      }
      else
      {
         columnName = listDetailVO.getNameEN();
      }

      return columnName;
   }

   @SuppressWarnings("unchecked")
   private static String getColumnValue( final HttpServletRequest request, final ColumnVO columnVO, final ListDetailVO listDetailVO, final Object object ) throws KANException
   {
      // ��ʼ����ǰColumn��Value
      String value = "";
      if ( columnVO == null )
      {
         // ������ڸ�������
         if ( listDetailVO.getAppendContent() != null && !listDetailVO.getAppendContent().trim().equals( "" ) && listDetailVO.getProperties() != null
               && !listDetailVO.getProperties().trim().equals( "" ) )
         {
            // ��õ�ǰ�ֶεĸ�������
            String appendContent = listDetailVO.getAppendContent();
            String properties[] = listDetailVO.getProperties().trim().split( "," );

            // ��������к��в���
            if ( appendContent.contains( "${" ) )
            {
               // �������滻���������еĲ���
               if ( properties != null && properties.length > 0 )
               {
                  for ( int i = 0; i < properties.length; i++ )
                  {
                     value = ( String ) KANUtil.getValue( object, properties[ i ] );
                  }
               }
            }
         }
      }
      else
      {
         // ��ʼ��ClientId
         final String corptId = BaseAction.getCorpId( request, null );

         // �б��ֶ���
         String fieldName = columnVO.getNameDB();
         if ( listDetailVO.getIsDecoded() != null && listDetailVO.getIsDecoded().equals( ListDetailVO.TRUE ) && fieldName != null && !fieldName.equals( "" ) )
         {
            fieldName = "decode" + String.valueOf( fieldName.toCharArray()[ 0 ] ).toUpperCase() + String.valueOf( fieldName.toCharArray(), 1, fieldName.length() - 1 );
         }

         // �����ϵͳ������ֶ�
         if ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
         {
            value = ( String ) KANUtil.getValue( object, fieldName );
         }
         // ������û��Զ�����ֶ�
         else
         {
            try
            {
               // ������ݶ���Ϊ��
               if ( object != null )
               {
                  // ��Object��Remark1�ֶλ�ȡJason�ַ���
                  final Object jsonString = KANUtil.getValue( object, "remark1" );
                  if ( jsonString != null && !( ( String ) jsonString ).trim().equals( "" ) )
                  {
                     // ��ʼ��Jason���� - ���е�ǰ�ֶε�ֵ
                     final JSONObject jsonObject = JSONObject.fromObject( URLDecoder.decode( ( String ) jsonString, "GBK" ).replace( "[{", "{" ).replace( "}]", "}" ) );

                     if ( jsonObject != null && jsonObject.containsKey( columnVO.getNameDB() ) )
                     {
                        value = jsonObject.getString( columnVO.getNameDB() );
                     }
                  }

                  // ����������Ҫ����
                  if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getInputType().trim().equals( "2" ) && listDetailVO.getIsDecoded() != null
                        && listDetailVO.getIsDecoded().equals( ListDetailVO.TRUE ) )
                  {
                     // ��ʼ��MappingVO�б�
                     List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
                     // ���������� - ϵͳ����
                     if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "1" ) )
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

                                 if ( KANUtil.filterEmpty( corptId ) != null )
                                 {
                                    parameters = new String[] { request.getLocale().getLanguage(), corptId };
                                 }
                                 else
                                 {
                                    parameters = new String[] { request.getLocale().getLanguage() };
                                 }

                                 mappingVOs = ( List< MappingVO > ) KANUtil.getValue( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ), accountOption.getMappingTemp(), parameters );
                                 // ��ӿյ�MappingVO����
                                 mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
                              }
                           }
                        }
                     }
                     // ���������� - �û��Զ���
                     else if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "3" ) )
                     {
                        // ��ʼ��OptionDTO
                        final OptionDTO optionDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getColumnOptionDTOByOptionHeaderId( columnVO.getOptionValue() );

                        if ( optionDTO != null )
                        {
                           mappingVOs = optionDTO.getOptions( request.getLocale().getLanguage() );
                        }
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
                     // ���������� - Ԥ��
                     else if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "5" ) )
                     {
                        mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
                     }

                     // ������ֵDecode
                     if ( mappingVOs != null && mappingVOs.size() > 0 )
                     {
                        for ( MappingVO mappingVO : mappingVOs )
                        {
                           if ( KANUtil.filterEmpty( mappingVO.getMappingId() ) != null && mappingVO.getMappingId().trim().equals( value ) )
                           {
                              value = mappingVO.getMappingValue();
                           }
                        }
                     }
                  }

               }
            }
            catch ( final Exception e )
            {
               throw new KANException( e );
            }
         }
         // �����ַ�������
         if ( value == null || value.trim().equalsIgnoreCase( "null" ) )
         {
            value = "";
         }

         // ����û��������ڸ�ʽ��
         if ( listDetailVO.getDatetimeFormat() != null && !listDetailVO.getDatetimeFormat().trim().equals( "" ) && !listDetailVO.getDatetimeFormat().trim().equals( "0" )
               && value != null && !value.trim().equals( "" ) )
         {
            value = new SimpleDateFormat( listDetailVO.getDecodeDatetimeFormatTemp() ).format( KANUtil.createDate( value ) );
         }

         // ����û�����С����ʽ��
         if ( listDetailVO.getAccuracy() != null && !listDetailVO.getAccuracy().trim().equals( "" ) && !listDetailVO.getAccuracy().trim().equals( "0" ) && value != null
               && !value.trim().equals( "" ) && value.replace( ".", "" ).replace( "-", "" ).matches( "[0-9]*" ) )
         {
            // Ĭ��Ϊ��������
            int roundType = BigDecimal.ROUND_HALF_UP;

            if ( listDetailVO.getRound() != null )
            {
               // ��ȡ
               if ( listDetailVO.getRound().trim().equals( "2" ) )
               {
                  roundType = BigDecimal.ROUND_DOWN;
               }
               // ���������Ͻ�λ��������λ��������ȡ
               else if ( listDetailVO.getRound().trim().equals( "3" ) )
               {
                  roundType = BigDecimal.ROUND_CEILING;
               }
            }

            value = new BigDecimal( value ).setScale( Integer.valueOf( listDetailVO.getDecodeAccuracyTemp() ), roundType ).toString();
         }
      }
      return value;
   }

   // ��ȡ�����б����ֵ
   private static String getColumnValue( final HttpServletRequest request, final ListDetailVO listDetailVO, final Object objectHeader, List< ? > objectDetails, final String special )
         throws KANException
   {
      try
      {
         // ��ʼ������ֵ
         String value = "";

         // ��ʼ��fieldName
         String fieldName = listDetailVO.getPropertyName();

         // �����Ҫ����
         if ( listDetailVO.getIsDecoded() != null && listDetailVO.getIsDecoded().equals( ListDetailVO.TRUE ) && KANUtil.filterEmpty( fieldName ) != null )
         {
            fieldName = "decode" + String.valueOf( fieldName.toCharArray()[ 0 ] ).toUpperCase() + String.valueOf( fieldName.toCharArray(), 1, fieldName.length() - 1 );
         }

         // ��ȡ���԰���_����ȡ�ĳ���ֵ
         final int length = fieldName.split( "_" ).length;

         /* Ӱ�����ܵ��������,�Ƶ�ѭ������ȥ��*/
         // ��ʼ��Object - DTO[Header]
         /* Object objectHeader = null;

          // ��ʼ��List< Object > - DTO[Details]
          List< ? > objectDetails = null;

          if ( object instanceof SpecialDTO )
          {
             // ��ȡSpecialDTO
             final SpecialDTO< Object, List< ? > > specialDTO = ( SpecialDTO< Object, List< ? > > ) object;

             objectDetails = specialDTO.getDetailVOs();
             objectHeader = specialDTO.getHeaderVO();

             // ���ʻ���ֵ   
             ( ( ActionForm ) objectHeader ).reset( null, request );
          }*/

         // ��ȡheader��ֵ
         if ( length == 1 )
         {
            // ����.����fieldName���Զ����ֶ�
            if ( fieldName.contains( "." ) )
               value = getSpecialListDefineColumnValue( objectHeader, fieldName, request );
            else
               value = ( String ) KANUtil.getValue( objectHeader, fieldName );
         }
         // ��ȡdetail��ֵ
         else if ( length > 1 )
         {
            // ��ȡ��ǰitemId
            final String itemId = fieldName.split( "_" )[ 1 ];

            // ����objectDetails
            if ( objectDetails != null && objectDetails.size() > 0 )
            {
               for ( Object objectDetail : objectDetails )
               {
                  // �ҵ���Ŀ��Ӧ��objectDetail
                  if ( KANUtil.filterEmpty( itemId ) != null && KANUtil.filterEmpty( ( String ) KANUtil.getValue( objectDetail, "itemId" ) ) != null
                        && ( ( String ) KANUtil.getValue( objectDetail, "itemId" ) ).equals( itemId ) )
                  {
                     // ���length���ȡ�2��
                     if ( length == 2 )
                     {
                        value = ( String ) KANUtil.getValue( objectDetail, "amountPersonal" );
                     }
                     // ���length���ȡ�3��
                     else if ( length == 3 )
                     {
                        if ( fieldName.split( "_" )[ 2 ].equals( "p" ) )
                        {
                           value = ( String ) KANUtil.getValue( objectDetail, special != null ? special : "amountPersonal" );
                        }
                        else
                        {
                           value = ( String ) KANUtil.getValue( objectDetail, special != null ? special : "amountCompany" );
                        }
                     }

                     break;
                  }
                  else
                  {
                     value = KANUtil.formatNumber( value, BaseAction.getAccountId( request, null ) );
                  }
               }
            }
         }

         // �����ַ�������
         if ( value == null || value.trim().equalsIgnoreCase( "null" ) )
         {
            value = "";
         }

         // ����û��������ڸ�ʽ��
         if ( KANUtil.filterEmpty( listDetailVO.getDatetimeFormat(), "0" ) != null && KANUtil.filterEmpty( value ) != null )
         {
            value = new SimpleDateFormat( listDetailVO.getDecodeDatetimeFormatTemp() ).format( KANUtil.createDate( value ) );
         }

         // ����û�����С����ʽ��
         if ( KANUtil.filterEmpty( listDetailVO.getAccuracy(), "0" ) != null && KANUtil.filterEmpty( value ) != null
               && value.replace( ".", "" ).replace( "-", "" ).matches( "[0-9]*" ) )
         {
            // Ĭ��Ϊ��������
            int roundType = BigDecimal.ROUND_HALF_UP;

            if ( listDetailVO.getRound() != null )
            {
               // ��ȡ
               if ( listDetailVO.getRound().trim().equals( "2" ) )
               {
                  roundType = BigDecimal.ROUND_DOWN;
               }
               // ���������Ͻ�λ��������λ��������ȡ
               else if ( listDetailVO.getRound().trim().equals( "3" ) )
               {
                  roundType = BigDecimal.ROUND_CEILING;
               }
            }

            value = new BigDecimal( value ).setScale( Integer.valueOf( listDetailVO.getDecodeAccuracyTemp() ), roundType ).toString();
         }

         return value;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // �ϲ��ӱ�DetailVO
   @SuppressWarnings("unchecked")
   private static List< ListDetailVO > mergeListDetailVO( final HttpServletRequest request, final List< ListDTO > listDTOs ) throws KANException
   {
      try
      {
         // ��ʼ��PagedListHolder
         final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );
         // ��ʼ�����п�Ŀ
         final List< ItemVO > items = ( List< ItemVO > ) ( ( PagedListHolder ) request.getAttribute( "pagedListHolder" ) ).getAdditionalObject();

         if ( items != null && items.size() > 0 )
         {
            return fetchListDetailVOByItemVO( items, listDTOs );
         }
         else
         {
            // ����PagedListHolder
            if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               for ( Object objectDTO : pagedListHolder.getSource() )
               {
                  if ( objectDTO instanceof SpecialDTO )
                  {
                     final SpecialDTO< Object, List< ? > > specialDTO = ( SpecialDTO< Object, List< ? >> ) objectDTO;
                     return fetchListDetailVO( specialDTO, listDTOs );
                  }
               }
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   // itemId������items
   private static boolean existsList( final List< ItemVO > items, final String itemId )
   {
      if ( KANUtil.filterEmpty( itemId ) != null )
      {
         for ( ItemVO itemVO : items )
         {
            if ( KANUtil.filterEmpty( itemVO.getItemId() ) != null && KANUtil.filterEmpty( itemVO.getItemId() ).equals( itemId ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   // ���������jack��
   private static List< ListDetailVO > fetchListDetailVOByItemVO( final List< ItemVO > items, final List< ListDTO > listDTOs )
   {
      // ��ʼ��ListDetailVO List
      final List< ListDetailVO > listDetailVOs = new ArrayList< ListDetailVO >();

      if ( items != null && items.size() > 0 )
      {
         if ( listDTOs != null && listDTOs.size() > 0 )
         {
            for ( ListDTO listDTO : listDTOs )
            {
               if ( listDTO != null && listDTO.getListDetailVOs().size() > 0 )
               {
                  for ( ListDetailVO listDetailVO : listDTO.getListDetailVOs() )
                  {
                     if ( KANUtil.filterEmpty( listDetailVO.getPropertyName() ) != null && listDetailVO.getPropertyName().split( "_" ).length > 1
                           && existsList( items, listDetailVO.getPropertyName().split( "_" )[ 1 ] ) )
                     {
                        listDetailVOs.add( listDetailVO );
                     }
                  }
               }
            }
         }

      }

      return listDetailVOs;
   }

   @SuppressWarnings("unchecked")
   // �����siuvan��
   private static List< ListDetailVO > fetchListDetailVO( final SpecialDTO< Object, List< ? > > specialDTO, final List< ListDTO > listDTOs ) throws KANException
   {
      // ��ʼ��ListDetailVO List
      final List< ListDetailVO > listDetailVOs = new ArrayList< ListDetailVO >();

      // ��ʶ�Ƿ���ʾ
      if ( specialDTO.getFlags() != null )
      {
         Map< String, Integer > flags = ( Map< String, Integer > ) specialDTO.getFlags();

         if ( listDTOs != null && listDTOs.size() > 0 )
         {
            for ( ListDTO listDTO : listDTOs )
            {
               if ( listDTO != null && listDTO.getListDetailVOs().size() > 0 )
               {
                  for ( ListDetailVO listDetailVO : listDTO.getListDetailVOs() )
                  {
                     if ( KANUtil.filterEmpty( listDetailVO.getPropertyName() ) != null && listDetailVO.getPropertyName().split( "_" ).length > 1
                           && flags.get( listDetailVO.getPropertyName().split( "_" )[ 1 ] ) == 1 )
                     {
                        listDetailVOs.add( listDetailVO );
                     }
                  }
               }
            }
         }
      }

      return listDetailVOs;
   }

   // �ڲ��� - ����columnIndex
   private static class ComparatorListDetail implements Comparator< ListDetailVO >
   {
      @Override
      public int compare( final ListDetailVO o1, final ListDetailVO o2 )
      {
         if ( KANUtil.filterEmpty( o1.getColumnIndex() ) == null || KANUtil.filterEmpty( o2.getColumnIndex() ) == null )
         {
            return 1;
         }

         return Integer.valueOf( o1.getColumnIndex() ) - Integer.valueOf( o2.getColumnIndex() );
      }
   }

   private static boolean isShowColumnId( final String accessAction, final String objectName, final ColumnVO columnVO, final HttpServletRequest request ) throws KANException
   {
      if ( columnVO == null )
      {
         return false;
      }
      return isShowColumnId( accessAction, objectName, columnVO.getNameDB(), request );
   }

   private static boolean isShowColumnId( final String accessAction, final String objectName, final String columnId, final HttpServletRequest request ) throws KANException
   {

      boolean returnValue = false;
      String[] clientAccessAction = new String[] { "HRO_BIZ_EMPLOYEE", "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT", "HRO_BIZ_ATTENDANCE_TIMESHEET_BATCH", "JAVA_OBJECT_SBBILL",
            "JAVA_OBJECT_SETTLEMENT", "JAVA_OBJECT_PAYSLIP", "HRO_BIZ_ATTENDANCE_TIMESHEET_REPORT_EXPORT", "HRO_CB_BILL" };
      String[] clientObjectName = new String[] { "com.kan.hro.domain.biz.sb.SBDTO", "com.kan.hro.domain.biz.payment.PayslipDTO", "com.kan.hro.domain.biz.settlement.OrderDTO",
            "com.kan.hro.domain.biz.settlement.SettlementDTO" };
      String[] clientColumnName = new String[] { "CLIENTID", "CLIENTNO", "CLIENTNUMBER", "CLIENTNAME", "VENDORNAMEZH", "VENDORNAMEEH", "CLIENTNAMEZH", "CLIENTNAMEEN", "ORDERID",
            "ENTITYID", "BUSINESSTYPEID" };
      String[] employeeAccessAction = new String[] { "HRO_BIZ_EMPLOYEE", "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT", "JAVA_OBJECT_PAYSLIP", "JAVA_OBJECT_SBBILL" };
      String[] employeeObjectName = new String[] { "com.kan.hro.domain.biz.sb.SBDTO", "com.kan.hro.domain.biz.payment.PayslipDTO", "com.kan.hro.domain.biz.settlement.OrderDTO",
            "com.kan.hro.domain.biz.settlement.SettlementDTO" };
      String[] employeeColumnName = new String[] { "ORDERID", "CLIENTNO", "CLIENTNUMBER", "TEMPLATEID", "MODIFYBY", "CLIENTID", "CLIENTNAMEZH", "ENTITYID", "CONTRACTSTATUS" };

      if ( StringUtils.equals( BaseAction.getRole( request, null ), KANConstants.ROLE_CLIENT ) )
      {
         if ( ( ArrayUtils.contains( clientAccessAction, accessAction ) || ArrayUtils.contains( clientObjectName, objectName ) ) && columnId != null
               && ArrayUtils.contains( clientColumnName, columnId.toUpperCase() ) )
         {
            if ( ( "JAVA_OBJECT_SETTLEMENT".equals( accessAction ) || "com.kan.hro.domain.biz.settlement.OrderDTO".equals( objectName ) ) && "clientId".equals( columnId ) )
            {
               returnValue = false;
            }
            else
            {
               returnValue = true;
            }
         }
      }
      else if ( StringUtils.equals( BaseAction.getRole( request, null ), KANConstants.ROLE_EMPLOYEE ) )
      {
         if ( "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT".equals( accessAction ) && columnId != null && "CONTRACTID".equals( columnId.toUpperCase() ) )
         {
            return false;
         }
         if ( ( ArrayUtils.contains( employeeAccessAction, accessAction ) || ArrayUtils.contains( employeeObjectName, objectName ) ) && columnId != null
               && ArrayUtils.contains( employeeColumnName, columnId.toUpperCase() ) )
         {
            returnValue = true;
         }
      }
      return returnValue;
   }

   @SuppressWarnings("unchecked")
   public static XSSFWorkbook generateBranchExcel( final HttpServletRequest request ) throws KANException
   {
      // ���Log Start����
      LogFactory.getLog( "generateBranchExcel" ).info( "Excel Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") Start." );

      // ��ʼ��PagedListHolder
      final List< StaffDTO > staffDTOs = ( List< StaffDTO > ) request.getAttribute( "staffDTOs" );

      final boolean isPosition = new Boolean( "isPosition".equalsIgnoreCase( ( String ) request.getAttribute( "isPosition" ) ) );

      // ��ʼ��������
      final XSSFWorkbook workbook = new XSSFWorkbook();

      if ( staffDTOs != null && staffDTOs.size() > 0 )
      {
         // ��������
         final Font font = workbook.createFont();
         font.setFontName( "Calibri" );
         font.setFontHeightInPoints( ( short ) 11 );

         // ������Ԫ����ʽ
         final CellStyle cellStyleLeft = workbook.createCellStyle();
         cellStyleLeft.setFont( font );
         cellStyleLeft.setAlignment( XSSFCellStyle.ALIGN_LEFT );

         // ������Ԫ����ʽ
         final CellStyle cellStyleCenter = workbook.createCellStyle();
         cellStyleCenter.setFont( font );
         cellStyleCenter.setAlignment( XSSFCellStyle.ALIGN_CENTER );

         // ������Ԫ����ʽ
         final CellStyle cellStyleRight = workbook.createCellStyle();
         cellStyleRight.setFont( font );
         cellStyleRight.setAlignment( XSSFCellStyle.ALIGN_RIGHT );

         // �������
         final Sheet sheet = workbook.createSheet( "sheet1" );
         // ���ñ��Ĭ���п��Ϊ15���ֽ�
         sheet.setDefaultColumnWidth( 15 );

         // ����Excel Header Row
         final Row rowHeader = sheet.createRow( 0 );

         // ��ͷ
         int headerColumnIndex = 0;
         Cell cell = rowHeader.createCell( headerColumnIndex );
         cell.setCellValue( "Ա������" );
         cell.setCellStyle( cellStyleLeft );
         headerColumnIndex++;

         cell = rowHeader.createCell( headerColumnIndex );
         cell.setCellValue( "���֤��" );
         cell.setCellStyle( cellStyleLeft );
         headerColumnIndex++;

         cell = rowHeader.createCell( headerColumnIndex );
         cell.setCellValue( "��ϵ�绰" );
         cell.setCellStyle( cellStyleLeft );
         headerColumnIndex++;

         cell = rowHeader.createCell( headerColumnIndex );
         cell.setCellValue( "��ϵ����" );
         cell.setCellStyle( cellStyleLeft );
         headerColumnIndex++;

         if ( isPosition )
         {
            // ����ǵ���ְλ
            cell = rowHeader.createCell( headerColumnIndex );
            final int longestPosition = getLongestBranch( staffDTOs, isPosition );
            sheet.addMergedRegion( new CellRangeAddress( 0, 0, headerColumnIndex, headerColumnIndex + ( longestPosition == 0 ? longestPosition : longestPosition - 1 ) ) );
            cell.setCellValue( "ְλ" );
            cell.setCellStyle( cellStyleLeft );
            headerColumnIndex++;
         }
         else
         {
            // ����ǵ�������
            cell = rowHeader.createCell( headerColumnIndex );
            cell.setCellValue( "ְλ" );
            cell.setCellStyle( cellStyleLeft );
            headerColumnIndex++;

            cell = rowHeader.createCell( headerColumnIndex );
            final int longestBranch = getLongestBranch( staffDTOs, isPosition );
            sheet.addMergedRegion( new CellRangeAddress( 0, 0, headerColumnIndex, headerColumnIndex + ( longestBranch == 0 ? longestBranch : longestBranch - 1 ) ) );
            cell.setCellValue( "����" );
            cell.setCellStyle( cellStyleLeft );
            headerColumnIndex++;
         }
         // ����Ϣ
         int bodyRowIndex = 1;
         for ( StaffDTO staffDTO : staffDTOs )
         {
            for ( PositionDTO positionDTO : staffDTO.getPositionDTOs() )
            {
               int bodyColumnIndex = 0;
               final Row rowBody = sheet.createRow( bodyRowIndex );
               cell = rowBody.createCell( bodyColumnIndex );
               cell.setCellValue( staffDTO.getStaffVO().getNameZH() );
               cell.setCellStyle( cellStyleLeft );
               bodyColumnIndex++;

               cell = rowBody.createCell( bodyColumnIndex );
               cell.setCellValue( staffDTO.getStaffVO().getCertificateNumber() );
               cell.setCellStyle( cellStyleLeft );
               bodyColumnIndex++;

               cell = rowBody.createCell( bodyColumnIndex );
               cell.setCellValue( staffDTO.getStaffVO().getBizMobile() );
               cell.setCellStyle( cellStyleLeft );
               bodyColumnIndex++;

               cell = rowBody.createCell( bodyColumnIndex );
               cell.setCellValue( staffDTO.getStaffVO().getBizEmail() );
               cell.setCellStyle( cellStyleLeft );
               bodyColumnIndex++;
               if ( isPosition )
               {
                  // ְλs
                  final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) );
                  // ��һ�����Լ���ְλ��ֻ��д�Լ�
                  int index = 0;
                  for ( PositionVO positionVO : positionDTO.getParentPositionVOS() )
                  {
                     cell = rowBody.createCell( bodyColumnIndex );
                     if ( index == 0 )
                     {
                        cell.setCellValue( staffDTO.getStaffVO().getNameZH() );
                     }
                     else
                     {
                        cell.setCellValue( getStaffNamesByPositionVO( accountConstants, positionVO ) );
                     }
                     cell.setCellStyle( cellStyleLeft );
                     index++;
                     bodyColumnIndex++;
                  }
               }
               else
               {
                  // ְλ
                  cell = rowBody.createCell( bodyColumnIndex );
                  cell.setCellValue( positionDTO.getPositionVO().getTitleZH() );
                  cell.setCellStyle( cellStyleLeft );
                  bodyColumnIndex++;
                  // ����s
                  for ( BranchVO branchVO : positionDTO.getParentBranchVOs() )
                  {
                     cell = rowBody.createCell( bodyColumnIndex );
                     cell.setCellValue( branchVO.getNameZH() );
                     cell.setCellStyle( cellStyleLeft );
                     bodyColumnIndex++;
                  }
               }

            }
            bodyRowIndex++;
         }

      }

      // ���Log End����
      LogFactory.getLog( "general branch excel" ).info( "Excel Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") End." );

      return workbook;
   }

   private static int getLongestBranch( final List< StaffDTO > staffDTOs, final boolean isPosition )
   {
      int count = 0;
      for ( StaffDTO staffDTO : staffDTOs )
      {
         for ( PositionDTO positionDTO : staffDTO.getPositionDTOs() )
         {
            int tempCount = isPosition ? positionDTO.getParentPositionVOS().size() : positionDTO.getParentBranchVOs().size();
            if ( tempCount > count )
            {
               count = tempCount;
            }
         }
      }
      return count;
   }

   private static String getStaffNamesByPositionVO( final KANAccountConstants accountConstants, final PositionVO positionVO )
   {
      String result = "";
      if ( positionVO != null && accountConstants != null )
      {
         final PositionDTO positionDTO = accountConstants.getPositionDTOByPositionId( positionVO.getPositionId() );
         if ( positionDTO != null && positionDTO.getPositionDTOs() != null && positionDTO.getPositionStaffRelationVOs().size() > 0 )
         {
            for ( PositionStaffRelationVO positionStaffRelationVO : positionDTO.getPositionStaffRelationVOs() )
            {
               final StaffDTO staffDTO = accountConstants.getStaffDTOByStaffId( positionStaffRelationVO.getStaffId() );
               if ( staffDTO != null )
               {
                  result += staffDTO.getStaffVO().getNameZH();
                  result += "1".equals( positionStaffRelationVO.getStaffType() ) ? "/" : "������,";
               }
            }
         }
      }

      return KANUtil.filterEmpty( result ) == null ? "" : result.substring( 0, result.length() - 1 );
   }

   // �Ƿ�ʹ�ù̶���
   private static boolean useFixColumn( final String javaObjectName )
   {
      if ( javaObjectName.equals( PayslipViewAction.JAVA_OBJECT_NAME ) || javaObjectName.equals( PayslipViewAction.JAVA_OBJECT_NAME_REPORT )
            || javaObjectName.equals( PayslipViewAction.JAVA_OBJECT_NAME_REPORT_ICLICK ) )
         return true;
      return false;
   }

   // ʹ��JAVA����������б���ȡ�Զ����ֶ�ֵ
   private static String getSpecialListDefineColumnValue( final Object objectHeader, final String fieldName, final HttpServletRequest request ) throws KANException
   {
      String key = fieldName.split( "\\." )[ 1 ];
      // ��ȡԱ���Զ����ֶ�
      final String employeeRemark1 = ( String ) KANUtil.getValue( objectHeader, "employeeRemark1" );
      // ��ȡ�Ͷ���ͬ�Զ����ֶ�
      final String contractRemark1 = ( String ) KANUtil.getValue( objectHeader, "contractRemark1" );
      // ��ʼ��employeeReportVO
      final EmployeeReportVO employeeReportVO = new EmployeeReportVO();
      employeeReportVO.reset( null, request );
      employeeReportVO.setRemark1( employeeRemark1 );
      employeeReportVO.setContractRemark1( contractRemark1 );

      // �Զ���Map��ʽ
      final Map< String, Object > columnMap = employeeReportVO.getDynaColumns();
      return columnMap.get( key ) == null ? "" : columnMap.get( key ).toString();
   }

   @SuppressWarnings("unchecked")
   // iclick ����Ա��Ϣ���� ���ӹ�����Ϣ
   private static void exportExcelAppendSalaryInfo( final HttpServletRequest request, final Object searchObject, final Object reusltObject, final ListDetailVO listDetailVO,
         final Sheet sheet, final Row row, final CellStyle cellStyleLeft, final CellStyle cellStyleCenter, final CellStyle cellStyleRight, int columnIndex, int flag )
         throws KANException
   {
      final List< MappingVO > salarys = ( List< MappingVO > ) KANUtil.getValue( searchObject, "salarys" );
      if ( salarys != null )
      {
         if ( flag == 1 )
         {
            for ( MappingVO mappingVO : salarys )
            {
               final Cell cell = row.createCell( columnIndex );
               final XSSFRichTextString text = new XSSFRichTextString( mappingVO.getMappingValue() );
               cell.setCellStyle( cellStyleLeft );

               // ��ʼ��Excel�п�����û�����̶��п�����
               if ( listDetailVO.getColumnWidthType() != null && listDetailVO.getColumnWidthType().trim().equals( "2" ) && listDetailVO.getColumnWidth() != null
                     && listDetailVO.getColumnWidth().trim().matches( "[0-9]*" ) )
               {
                  // ����Excel�п�ȡ��
                  final BigDecimal columnWidth = new BigDecimal( Integer.valueOf( listDetailVO.getColumnWidth() ) * 30 ).setScale( 0, BigDecimal.ROUND_HALF_UP );
                  // ����Excel�п�
                  sheet.setColumnWidth( columnIndex, columnWidth.intValue() );
               }

               cell.setCellValue( text );
               cell.setCellStyle( cellStyleLeft );

               columnIndex++;
            }
         }
         else if ( flag == 2 )
         {
            final IClickEmployeeReportView ice = ( IClickEmployeeReportView ) reusltObject;
            for ( MappingVO mp1 : salarys )
            {
               final Cell cell = row.createCell( columnIndex );
               String tdValue = "";
               for ( MappingVO mp2 : ice.getSalarys() )
               {
                  if ( mp2.getMappingId().equals( mp1.getMappingId() ) )
                  {
                     tdValue = mp2.getMappingValue();
                     break;
                  }
               }
               if ( KANUtil.filterEmpty( tdValue ) == null )
               {
                  tdValue = KANUtil.formatNumber( "0", BaseAction.getAccountId( request, null ) );
               }
               final XSSFRichTextString text = new XSSFRichTextString( tdValue );
               // ���õ�Ԫ�����
               cell.setCellStyle( cellStyleLeft );
               // ���õ�Ԫ��ֵ
               cell.setCellValue( text );

               if ( listDetailVO.getAlign() != null )
               {
                  if ( listDetailVO.getAlign().trim().equals( "2" ) )
                  {
                     cell.setCellStyle( cellStyleCenter );
                  }
                  else if ( listDetailVO.getAlign().trim().equals( "3" ) )
                  {
                     cell.setCellStyle( cellStyleRight );
                  }
               }

               columnIndex++;
            }
         }
      }

   }

   @SuppressWarnings("unchecked")
   private static String appendSalaryInfo( final HttpServletRequest request, final Object searchObject, final Object reusltObject, final ListDetailVO listDetailVO, final int flag )
         throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      final List< MappingVO > salarys = ( List< MappingVO > ) KANUtil.getValue( searchObject, "salarys" );
      if ( salarys != null )
      {
         if ( flag == 1 )
         {
            for ( MappingVO mappingVO : salarys )
            {
               rs.append( "<th class=\"header-nosort\"><span>" + mappingVO.getMappingValue() + "</span></th>" );
            }
         }
         else if ( flag == 2 )
         {
            final IClickEmployeeReportView ice = ( IClickEmployeeReportView ) reusltObject;
            for ( MappingVO mp1 : salarys )
            {
               String tdValue = "";
               rs.append( "<td class=\"right\">" );
               for ( MappingVO mp2 : ice.getSalarys() )
               {
                  if ( mp2.getMappingId().equals( mp1.getMappingId() ) )
                  {
                     tdValue = mp2.getMappingValue();
                     break;
                  }
               }
               if ( KANUtil.filterEmpty( tdValue ) == null )
               {
                  tdValue = KANUtil.formatNumber( "0", BaseAction.getAccountId( request, null ) );
               }
               rs.append( tdValue + "</td>" );
            }
         }
      }

      return rs.toString();
   }

}
