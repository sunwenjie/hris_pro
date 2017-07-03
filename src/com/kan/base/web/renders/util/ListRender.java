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
      // 初始化corpId
      final String corpId = KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) );

      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();

      // 初始化ListDTO
      final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, corpId );

      // 初始化ModuleDTO
      final ModuleDTO moduleDTO = KANConstants.getModuleDTOByAccessAction( accessAction );

      rs.append( "<div class=\"box noHeader\" id=\"search-results\">" );
      rs.append( "<div class=\"inner\">" );
      rs.append( "<div class=\"top\">" );
      //jason.ji add 2014-05-30 結算明細頁面特殊處理，添加LIST按鈕
      if ( "com.kan.hro.domain.biz.settlement.SettlementDTO".equals( javaObjectName ) && "JAVA_OBJECT_SETTLEMENT".equals( accessAction ) )
      {
         rs.append( "<input type=\"button\" class=\"reset\" name=\"btnList\" id=\"btnList\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.list" )
               + "\" onclick=\"link('orderBillHeaderViewAction.do?proc=list_object');\"/>" );
      }
      rs.append( "<a id=\"filterRecords\" name=\"filterRecords\">" + KANUtil.getProperty( request.getLocale(), "set.filerts" ) + "</a>" );

      // 判断列表是否需要添加导出功能
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

      // 导出apc(仅限“iClick”的工资单和工资报表)
      if ( BaseAction.getAccountId( request, null ).equals( "100017" )
            && ( javaObjectName.equals( PayslipViewAction.JAVA_OBJECT_NAME ) || javaObjectName.equals( PayslipViewAction.JAVA_OBJECT_NAME_REPORT ) ) )
      {
         rs.append( "<a id=\"exportApc\" name=\"exportApc\" class=\"commonTools\" title=\"" + KANUtil.getProperty( request.getLocale(), "img.title.tips.export.apc" )
               + "\" onclick=\"exportApc();\"><img src=\"images/appicons/txt_16.png\" style=\"margin-right:10px;\" /></a>&nbsp;" );
      }

      // 快速设置列表顺序
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

      // 加入快速设置列表顺序
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
      // 添加Log Start跟踪
      LogFactory.getLog( accessAction ).info( "List Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") Start." );

      // 获取当前需要生成控件管理界面的TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );

      // 初始化ListDTO
      final ListDTO listDTO = tableDTO.getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );

      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();
      final String successMessage = ( String ) request.getAttribute( "MESSAGE" );
      final String successClass = ( String ) request.getAttribute( "MESSAGE_CLASS" );

      // 初始化ModuleDTO
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

      // 初始化添加按钮的链接（跳转至添加页面）
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

      // 添加快速创建员工按钮
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
      //雇员登陆不显示查询条件
      if ( !KANConstants.ROLE_EMPLOYEE.equals( EmployeeSecurityAction.getRole( request, null ) ) )
      {
         rs.append( "<a id=\"filterRecords\" name=\"filterRecords\">" + KANUtil.getProperty( request.getLocale(), "set.filerts" ) + "</a>" );
      }

      // 判断列表是否需要添加导出功能
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

      // 加入导入Popup
      rs.append( generateImportPopup( request, tableDTO ) );

      // 快速设置列表顺序
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

      // 加入快速设置列表顺序
      final String listAction = ( moduleDTO != null && moduleDTO.getModuleVO() != null ) ? moduleDTO.getModuleVO().getListAction() : "";
      rs.append( generateQuickColumnIndexPopup( request, listDTO.getListDetailVOs(), listAction ) );

      // 添加Log End跟踪
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

      // 获得Role判断是否供应商登录
      final String roleId = BaseAction.getRole( request, null );

      // 如果供应商登录则支持导入
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
      // 标识是否启用
      boolean use = false;

      // 如果ListDTO存在子对象
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
            // 如果数据不为空
            else if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               for ( Object source : pagedListHolder.getSource() )
               {
                  // 初始化SpecialDTO
                  final SpecialDTO< Object, List< ? > > specialDTO = ( SpecialDTO< Object, List< ? >> ) source;

                  final List< ? > detailVOs = specialDTO.getDetailVOs();

                  if ( detailVOs != null && detailVOs.size() > 0 )
                  {
                     for ( Object objectDetailVO : detailVOs )
                     {
                        final String itemType = ( String ) KANUtil.getValue( objectDetailVO, "itemType" );

                        // 为社保科目
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

   // 生成普通Table的Title
   // Add by Siuvan Xia at 2014-5-27
   private static String generateCommonTableTitle( final HttpServletRequest request, final PagedListHolder pagedListHolder, final List< ListDetailVO > listDetailVOs,
         final String pageAccessAction, final String javaObjectName ) throws KANException
   {
      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();

      // 找不到这个方法
      //final String groupColumn = ( String ) KANUtil.getValue( pagedListHolder.getObject(), "groupColumn" );
      final String groupColumn = "0";
      boolean show = true;
      if ( KANUtil.filterEmpty( groupColumn, "0" ) != null )
      {
         show = false;
      }

      rs.append( "<tr>" );
      // 遍历生成
      for ( ListDetailVO listDetailVO : listDetailVOs )
      {
         // 是否显示
         if ( listDetailVO.getDisplay() == null || !listDetailVO.getDisplay().trim().equals( "2" ) )
         {
            if ( isShowColumnId( pageAccessAction, null, listDetailVO.getPropertyName(), request ) )
            {
               continue;
            }

            // 刷新国际化
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

               // 显示宽度
               String thStyle = "";
               if ( KANUtil.filterEmpty( listDetailVO.getColumnWidth() ) != null )
               {
                  // 初始化Width Type，默认是百分比
                  String widthType = "%";
                  if ( listDetailVO.getColumnWidthType() != null )
                  {
                     // 列宽按照象素显示
                     if ( listDetailVO.getColumnWidthType().trim().equals( "2" ) )
                     {
                        widthType = "px";
                     }
                  }
                  thStyle = "width: " + listDetailVO.getColumnWidth() + widthType + ";";
               }

               // 字体大小
               String valueStyle = "";
               if ( KANUtil.filterEmpty( listDetailVO.getFontSize() ) != null && !listDetailVO.getFontSize().trim().equals( "0" ) )
               {
                  valueStyle = valueStyle + "font-size: " + listDetailVO.getFontSize() + "px;";
               }
               if ( KANUtil.filterEmpty( valueStyle ) != null )
               {
                  valueStyle = " style=\"" + valueStyle + "\" ";
               }

               // 是否排序
               boolean sort = true;
               if ( KANUtil.filterEmpty( listDetailVO.getSort() ) != null && listDetailVO.getSort().trim().equals( "2" ) )
               {
                  sort = false;
               }

               // 列标题
               rs.append( "<th " + thStyle + " class=\"" );

               // 排序设置
               if ( sort )
               {
                  rs.append( "header " );
               }
               else
               {
                  rs.append( "header-nosort " );
               }

               rs.append( pagedListHolder.getCurrentSortClass( listDetailVO.getPropertyName() ) + "\">" );

               // 无关联的情况不添加链接
               if ( sort )
               {
                  rs.append( "<a onclick=\"submitForm('list_form', null, null, '" + listDetailVO.getPropertyName() + "', '"
                        + pagedListHolder.getNextSortOrder( listDetailVO.getPropertyName() ) + "', 'tableWrapper');\">" );
               }

               // 如果需要设置样式
               if ( KANUtil.filterEmpty( valueStyle ) != null )
               {
                  rs.append( "<span " + valueStyle + ">" );
               }

               // 表头
               rs.append( listDetailVO.getHeaderName( request ) );

               // 如果需要设置样式
               if ( KANUtil.filterEmpty( valueStyle ) != null )
               {
                  rs.append( "</span>" );
               }

               // 无关联的情况不添加链接
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
            // 是否显示
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

   // 生成特殊Table的Title
   // Add by Siuvan Xia at 2014-5-27
   private static String generateSpecialTableTitle( final HttpServletRequest request, final PagedListHolder pagedListHolder, final List< ListDetailVO > listDetailVOs,
         final String pageAccessAction, final String javaObjectName ) throws KANException
   {
      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();

      // 初始化要合并的列数
      int colspan = getSBItemNumber( listDetailVOs );

      // 标记“公司”、“个人”已经生成
      boolean exists_c = false;
      boolean exists_p = false;

      // 标记是社保单
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
      // 遍历生成非社保科目部分
      for ( ListDetailVO listDetailVO : listDetailVOs )
      {
         // 是否显示
         if ( listDetailVO.getDisplay() == null || !listDetailVO.getDisplay().trim().equals( "2" ) )
         {
            // 刷新国际化
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
               // 如果当前非社保，合并行“rowspan”
               if ( !listDetailVO.isSBItem() )
               {
                  // 显示宽度
                  String thStyle = "";
                  if ( KANUtil.filterEmpty( listDetailVO.getColumnWidth() ) != null )
                  {
                     // 初始化Width Type，默认是百分比
                     String widthType = "%";
                     if ( listDetailVO.getColumnWidthType() != null )
                     {
                        // 列宽按照象素显示
                        if ( listDetailVO.getColumnWidthType().trim().equals( "2" ) )
                        {
                           widthType = "px";
                        }
                     }
                     thStyle = "width: " + listDetailVO.getColumnWidth() + widthType + ";";
                  }

                  // 字体大小
                  String valueStyle = "";
                  if ( KANUtil.filterEmpty( listDetailVO.getFontSize() ) != null && !listDetailVO.getFontSize().trim().equals( "0" ) )
                  {
                     valueStyle = valueStyle + "font-size: " + listDetailVO.getFontSize() + "px;";
                  }
                  if ( KANUtil.filterEmpty( valueStyle ) != null )
                  {
                     valueStyle = " style=\"" + valueStyle + "\" ";
                  }

                  // 是否排序
                  boolean sort = true;
                  if ( listDetailVO.getLength() > 1 || ( KANUtil.filterEmpty( listDetailVO.getSort() ) != null && listDetailVO.getSort().trim().equals( "2" ) ) )
                  {
                     sort = false;
                  }

                  // 列标题
                  rs.append( "<th style=\"" + thStyle + "\" class=\"" );

                  // 排序设置
                  if ( sort )
                  {
                     rs.append( "header " );
                  }
                  else
                  {
                     rs.append( "header-nosort " );
                  }

                  rs.append( pagedListHolder.getCurrentSortClass( listDetailVO.getPropertyName() ) + "\"" + " rowspan=\"" + ( isSBBill ? "3" : "2" ) + "\" >" );

                  // 无关联的情况不添加链接
                  if ( sort )
                  {
                     rs.append( "<a onclick=\"submitForm('list_form', null, null, '" + listDetailVO.getPropertyName() + "', '"
                           + pagedListHolder.getNextSortOrder( listDetailVO.getPropertyName() ) + "', 'tableWrapper');\">" );
                  }

                  // 如果需要设置样式
                  if ( KANUtil.filterEmpty( valueStyle ) != null )
                  {
                     rs.append( "<span " + valueStyle + ">" );
                  }

                  // 表头
                  rs.append( listDetailVO.getHeaderName( request ) );

                  // 如果需要设置样式
                  if ( KANUtil.filterEmpty( valueStyle ) != null )
                  {
                     rs.append( "</span>" );
                  }

                  // 无关联的情况不添加链接
                  if ( sort )
                  {
                     rs.append( "</a>" );
                  }

                  rs.append( "</th>" );
               }
               // 如果社保科目，合并列“colspan”
               else
               {
                  // 如果是公司
                  if ( !exists_c && "c".equals( listDetailVO.getLastCharOfPropertyName() ) )
                  {
                     // 更改标记
                     exists_c = true;

                     // 初始化扁平列名
                     String companyTitle = request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "公司" : "Company";

                     rs.append( "<th class=\"header-nosort center\" colspan=\"" + colspan + "\" >" );
                     rs.append( companyTitle );
                     rs.append( "</th>" );
                  }
                  else if ( !exists_p && "p".equals( listDetailVO.getLastCharOfPropertyName() ) )
                  {
                     // 更改标记
                     exists_p = true;

                     // 初始化扁平列名
                     String personalTitle = request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? "个人" : "Personal";

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
      // 遍历生成社保科目部分
      for ( ListDetailVO listDetailVO : listDetailVOs )
      {
         // 是否显示
         if ( listDetailVO.getDisplay() == null || !listDetailVO.getDisplay().trim().equals( "2" ) )
         {
            // 刷新国际化
            listDetailVO.reset( null, request );

            // 社保科目
            if ( KANUtil.filterEmpty( listDetailVO.getPropertyName() ) != null && listDetailVO.isSBItem() )
            {
               // 显示宽度
               String thStyle = "";
               if ( KANUtil.filterEmpty( listDetailVO.getColumnWidth() ) != null )
               {
                  // 初始化Width Type，默认是百分比
                  String widthType = "%";
                  if ( listDetailVO.getColumnWidthType() != null )
                  {
                     // 列宽按照象素显示
                     if ( listDetailVO.getColumnWidthType().trim().equals( "2" ) )
                     {
                        widthType = "px";
                     }
                  }
                  thStyle = "width: " + listDetailVO.getColumnWidth() + widthType + ";";
               }

               // 字体大小
               String valueStyle = "";
               if ( KANUtil.filterEmpty( listDetailVO.getFontSize() ) != null && !listDetailVO.getFontSize().trim().equals( "0" ) )
               {
                  valueStyle = valueStyle + "font-size: " + listDetailVO.getFontSize() + "px;";
               }
               if ( KANUtil.filterEmpty( valueStyle ) != null )
               {
                  valueStyle = " style=\"" + valueStyle + "\" ";
               }

               // 列标题
               rs.append( "<th " + thStyle + " class=\"header-nosort center\"" );
               rs.append( isSBBill ? "colspan=\"3\">" : ">" );

               // 如果需要设置样式
               if ( KANUtil.filterEmpty( valueStyle ) != null )
               {
                  rs.append( "<span " + valueStyle + ">" );
               }

               // 表头
               rs.append( listDetailVO.getHeaderName( request ) );

               // 如果需要设置样式
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
         // 遍历生成社保科目部分
         for ( ListDetailVO listDetailVO : listDetailVOs )
         {
            // 是否显示
            if ( listDetailVO.getDisplay() == null || !listDetailVO.getDisplay().trim().equals( "2" ) )
            {
               // 刷新国际化
               listDetailVO.reset( null, request );

               // 社保科目
               if ( KANUtil.filterEmpty( listDetailVO.getPropertyName() ) != null && listDetailVO.isSBItem() )
               {
                  // 列标题
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

   // 获取特殊ListDTO合并后的ListDetailVO列表
   private static List< ListDetailVO > getListDetailVOs( final HttpServletRequest request, final ListDTO listDTO ) throws KANException
   {
      // 初始化（合并主从ListDetailVO）ListDetailVO列表
      final List< ListDetailVO > mergeListDetailVOs = new ArrayList< ListDetailVO >();

      // 装入主ListDetailVO
      mergeListDetailVOs.addAll( listDTO.getListDetailVOs() );

      // 如果存在从ListDetailVO
      if ( listDTO.getSubListDTOs() != null && listDTO.getSubListDTOs().size() > 0 )
      {

         // 初始化Sub ListDetailVO
         final List< ListDetailVO > subListDetailVOs = mergeListDetailVO( request, listDTO.getSubListDTOs() );

         if ( subListDetailVOs != null && subListDetailVOs.size() > 0 )
         {
            mergeListDetailVOs.addAll( subListDetailVOs );
         }

         // 排序
         if ( mergeListDetailVOs.size() > 0 )
         {
            Collections.sort( mergeListDetailVOs, new ComparatorListDetail() );
         }
      }

      return mergeListDetailVOs;
   }

   // 生成特殊的table
   public static String generateSpecialListTable( final HttpServletRequest request, final String javaObjectName, final String pageAccessAction ) throws KANException
   {
      boolean hasExport = true;
      if ( KANUtil.filterEmpty( pageAccessAction ) != null )
      {
         hasExport = AuthUtils.hasAuthority( ( String ) request.getAttribute( "pageAccessAction" ), AuthConstants.RIGHT_EXPORT, "", request, null );
      }

      // 初始化corpId
      final String corpId = KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) );

      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();

      // 获取ListDTO
      final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, corpId );

      // 初始化PagedListHolder
      final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );

      // 标记是社保单
      boolean isSBBill = javaObjectName.equals( SBBillViewAction.JAVA_OBJECT_NAME );

      // 找不到方法
      //final String groupColumn = ( String ) KANUtil.getValue( pagedListHolder.getObject(), "groupColumn" );
      final String groupColumn = "0";
      boolean show = true;
      if ( KANUtil.filterEmpty( groupColumn, "0" ) != null )
      {
         show = false;
      }

      // 是否启用扁平
      final boolean isUseFlat = isUseFlat( pagedListHolder, listDTO );

      int columnSize = 0;
      // 遍历生成Table
      if ( listDTO != null && pagedListHolder != null )
      {
         rs.append( "<table class=\"table hover\" id=\"resultTable\">" );

         // 初始化（合并主从ListDetailVO）ListDetailVO列表
         final List< ListDetailVO > mergeListDetailVOs = new ArrayList< ListDetailVO >();

         // 存在列表字段
         if ( listDTO.getListDetailVOs() != null && listDTO.getListDetailVOs().size() > 0 )
         {
            // 装入主ListDetailVO
            mergeListDetailVOs.addAll( getListDetailVOs( request, listDTO ) );

            // 初始化ListHeaderVO
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
            // 遍历生成Table Body
            if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               rs.append( "<tbody>" );

               // Index 用以判断当前的行号，区分列表不同行的样式
               int index = 0;
               // 遍历行
               for ( Object object : pagedListHolder.getSource() )
               {

                  // 初始化Object - DTO[Header]
                  Object objectHeader = null;
                  // 初始化List< Object > - DTO[Details]
                  List< ? > objectDetails = null;
                  if ( object instanceof SpecialDTO )
                  {
                     // 获取SpecialDTO
                     @SuppressWarnings("unchecked")
                     final SpecialDTO< Object, List< ? > > specialDTO = ( SpecialDTO< Object, List< ? > > ) object;

                     objectDetails = specialDTO.getDetailVOs();
                     objectHeader = specialDTO.getHeaderVO();
                     // 国际化传值    
                     ( ( ActionForm ) objectHeader ).reset( null, request );
                  }

                  // 行样式
                  final String trClass = index % 2 == 1 ? "odd" : "even";
                  rs.append( "<tr class='" + trClass + "'>" );

                  // 遍历列
                  for ( ListDetailVO listDetailVO : mergeListDetailVOs )
                  {
                     if ( listDetailVO.getDisplay() == null || !listDetailVO.getDisplay().trim().equals( "2" ) )
                     {
                        // 初始化属性名称
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
                        // 获取SpecialDTO
                        if ( object instanceof SpecialDTO )
                        {
                           @SuppressWarnings("unchecked")
                           final SpecialDTO< Object, List< ? > > specialDTO = ( SpecialDTO< Object, List< ? > > ) object;
                           objHeaderVO = specialDTO.getHeaderVO();
                        }

                        // 行宽
                        String valueStyle = "";
                        if ( KANUtil.filterEmpty( listDetailVO.getFontSize() ) != null && !listDetailVO.getFontSize().trim().equals( "0" ) )
                        {
                           valueStyle = valueStyle + "font-size: " + listDetailVO.getFontSize() + "px;";
                        }
                        if ( KANUtil.filterEmpty( valueStyle ) != null )
                        {
                           valueStyle = " style=\"" + valueStyle + "\" ";
                        }

                        // 初始化链接方式
                        String target = "target=\"_self\"";
                        if ( listDetailVO.getLinkedTarget() != null && listDetailVO.getLinkedTarget().trim().equals( "2" ) )
                        {
                           target = "target=\"_blank\"";
                        }

                        // 初始化动态参数
                        String properties[] = null;
                        if ( listDetailVO.getProperties() != null && !listDetailVO.getProperties().trim().equals( "" ) )
                        {
                           properties = listDetailVO.getProperties().trim().split( "," );
                        }

                        // 个税小于“0”的警告
                        String tdClass = "";

                        if ( javaObjectName.equals( IncomeTaxAction.JAVA_OBJECT_NAME ) && listDetailVO.getPropertyName().equals( "taxAmountPersonal" ) )
                        {
                           tdClass = "class=\"highlight\"";
                        }

                        // Added by siuvan @2014-08-07
                        // 如果是社保科目，需从Object中得到 “基数”、“比例” 再获取 “金额”
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

                           // 如果需要设置TD CLASS
                           if ( KANUtil.filterEmpty( tdClass ) != null && Float.valueOf( value ) < 0 )
                           {
                              rs.append( tdClass );
                           }

                           rs.append( ">" );

                           // 如果当前字段是带链接的
                           if ( listDetailVO.getIsLinked() != null && listDetailVO.getIsLinked().equals( ListDetailVO.TRUE ) && listDetailVO.getLinkedAction() != null
                                 && !listDetailVO.getLinkedAction().trim().equals( "" ) )
                           {
                              // 获得当前字段的链接
                              String link = listDetailVO.getLinkedAction();

                              // 如果链接中含有参数
                              if ( link.contains( "${" ) )
                              {
                                 // 遍历并替换链接中的参数
                                 if ( properties != null && properties.length > 0 )
                                 {
                                    for ( int i = 0; i < properties.length; i++ )
                                    {
                                       final Object propertyValueObject = KANUtil.getValue( objHeaderVO, properties[ i ] );
                                       link = link.replace( "${" + i + "}", propertyValueObject != null ? propertyValueObject.toString() : "" );
                                    }
                                 }
                              }

                              // 如果当前链接中为传入主键ID
                              if ( !link.contains( "&id=" ) )
                              {
                                 link = link + "&id=" + KANUtil.getValue( objHeaderVO, "encodedId" );
                              }

                              // 添加链接地址
                              rs.append( "<a href=\"" + link + "\" " + target + ">" );
                           }

                           // 如果需要设置样式
                           if ( KANUtil.filterEmpty( valueStyle ) != null )
                           {
                              rs.append( "<span " + valueStyle + ">" );
                           }

                           rs.append( value );

                           // 如果需要设置样式
                           if ( KANUtil.filterEmpty( valueStyle ) != null )
                           {
                              rs.append( "</span>" );
                           }

                           // 如果当前字段是带链接的
                           if ( listDetailVO.getIsLinked() != null && listDetailVO.getIsLinked().equals( ListDetailVO.TRUE ) && listDetailVO.getLinkedAction() != null
                                 && !listDetailVO.getLinkedAction().trim().equals( "" ) )
                           {
                              rs.append( "</a>" );
                           }
                        }

                        // 如果存在附加内容
                        if ( listDetailVO.getAppendContent() != null && !listDetailVO.getAppendContent().trim().equals( "" ) )
                        {
                           // 获得当前字段的附加内容
                           String appendContent = listDetailVO.getAppendContent();

                           // 如果链接中含有参数
                           if ( appendContent.contains( "${" ) )
                           {
                              // 遍历并替换附加内容中的参数
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

            // 生成Table Foot
            if ( listHeaderVO.getUsePagination() != null && listHeaderVO.getUsePagination().trim().equals( "1" ) )
            {
               rs.append( "<tfoot>" );
               rs.append( "<tr class=\"total\">" );
               rs.append( "<td colspan=\"" + ( ( columnSize == 0 ? mergeListDetailVOs.size() : columnSize ) + 1 ) + "\" class=\"left\">" );
               rs.append( "<label>&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.total" ) + "： " + pagedListHolder.getHolderSize() + " " );
               rs.append( "<label>&nbsp;&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.current" ) + "：" + pagedListHolder.getIndexStart() + " - "
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
               rs.append( "<label>&nbsp;&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.pagination" ) + "：" + pagedListHolder.getRealPage() + "/"
                     + pagedListHolder.getPageCount() + "</label>&nbsp;" );
               rs.append( "<label>&nbsp;&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.jump.to" )
                     + "：<input type=\"text\" id=\"forwardPage_render\" class=\"forwardPage_render\" style=\"width:23px;\" value=\"" + pagedListHolder.getRealPage()
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

   // 快速设置列顺序  自定义 - 列表 - detail
   public static String generateQuickColumnIndexPopup( final HttpServletRequest request, final String listHeaderId, final String linkAction ) throws KANException
   {
      // 初始化ListDTO
      final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByListHeaderId( listHeaderId );

      // 初始化ListDetailVO列表
      final List< ListDetailVO > listDetailVOs = new ArrayList< ListDetailVO >();

      if ( listDTO != null && listDTO.getListHeaderVO() != null )
      {
         // 如果启用java对象
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

   // 快速设置列顺序 具体页面
   public static String generateQuickColumnIndexPopup( final HttpServletRequest request, final List< ListDetailVO > listDetailVOs, final String linkAction ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      if ( listDetailVOs != null && listDetailVOs.size() > 0 )
      {
         rs.append( "<div class=\"modal midsize content hide\" id=\"indexQuickModuleId\">" );
         rs.append( "<div class=\"modal-header\" id=\"indexQuickHeader\"> " );
         rs.append( "<a class=\"close\" data-dismiss=\"modal\" onclick=\"closeBox();\">×</a>" );
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
            // 如果是社保科目，筛选公司部分
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
         rs.append( "<a id=\"upMove\" class=\"upMove\" title=\"↑\" onmousedown=\"setTimeStart('up');\" onmouseup=\"clearTimeout(x);\" onclick=\"listObj=document.getElementById('columns');upListItem();clearTimeout(x);\" ><img id=\"_up_link\" src='images/up.png' /></a>" );
         rs.append( "<br/><br/><br/><br/>" );
         rs.append( "<a id=\"downMove\" class=\"downMove\" title=\"↓\" onmousedown=\"setTimeStart('down');\" onmouseup=\"clearTimeout(x);\" onclick=\"listObj=document.getElementById('columns');downListItem();clearTimeout(x);\"><img id=\"_down_link\" src='images/down.png' /></a>" );
         rs.append( "</div>" );
         rs.append( "</div>" );
         rs.append( "</li>" );
         rs.append( "</ol>" );
         rs.append( "</fieldset>" );
         rs.append( "</form>" );
         rs.append( "</div>" );
         rs.append( "</div>" );

         // js 代码
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
         // 判断是否有导入功能
         if ( hasImportRight( request, listDTO.getListHeaderVO().getTableId() ) )
         {
            // 初始化ModuleDTO
            final ImportDTO importDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getImportDTOByTableId( listDTO.getListHeaderVO().getTableId(), ( String ) BaseAction.getCorpId( request, null ) );

            if ( importDTO != null && importDTO.getImportHeaderVO() != null && KANUtil.filterEmpty( importDTO.getImportHeaderVO().getImportHeaderId() ) != null )
            {
               final ImportHeaderVO importHeaderVO = importDTO.getImportHeaderVO();
               rs.append( "<a id=\"importExcel\" name=\"importExcel\" class=\"commonTools\" title=\""
                     + ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? importHeaderVO.getNameZH() : importHeaderVO.getNameEN() )
                     + "\" onclick=\"$('#importDIVModalId').show();$('#shield').show();\" ><img id=\"_importExcel_link\" src='images/import.png' /></a>" );
               // 导出模板按钮和导入按钮
               rs.append( "<div class=\"modal hide\" id=\"importDIVModalId\">" );
               rs.append( "<div class=\"modal-header\">" );
               rs.append( "<a class=\"close\" data-dismiss=\"modal\"  onclick=\"$('#importDIVModalId').hide();$('#shield').hide();window.location.reload();\">×</a>" );
               rs.append( "<label> " + importHeaderVO.getNameZH() + "</label>" );
               rs.append( " </div>" );

               rs.append( "<div class=\"modal-body\">" );

               if ( KANUtil.filterEmpty( importHeaderVO.getDescription() ) != null )
               {
                  rs.append( "<div id=\"importDescription\" class=\"message success importDescription\" style=\"font-size: 10px;\"> <div style=\"margin-right: 25px;\"> "
                        + importHeaderVO.getDescription() + " </div> <div><a class=\"messageCloseButton\"  onclick=\"$('#importDescription').hide();\">×</a></div></div>" );
               }
               rs.append( "<input type=\"button\" id=\"importExcelFile2\" value=\"上传文件\" onclick=\"uploadObjectExcel.submit();\" class='save'>" );
               rs.append( "<a href=\"#\" onclick=\"link('downloadFileAction.do?proc=exportImportTemplate&fileType=excel&importHeaderId=" + importHeaderVO.getEncodedId()
                     + "')\" class='commonlink'>下载模板</a>" );
               rs.append( "<ol id=\"attachmentsOL\" class=\"auto\"></ol>" );
               rs.append( "<div id=\"uploadProgressMsgDIV\"><div id=\"msgTitle\"></div><div id=\"msgContent\"></div></div>" );
               rs.append( "</div>" );
               String importConfirmMsg = null;
               if ( importDTO.getPrimaryKeyImportDetailVO() != null )
               {
                  importConfirmMsg = "导入文件包含“" + importDTO.getPrimaryKeyImportDetailVO().getNameZH() + "”，" + tableDTO.getTableVO().getNameZH() + "信息将会被覆盖";
                  rs.append( "<div class=\"modal-footer\"><font color='red'>*</font>" + importConfirmMsg + "</div>" );
               }
               else
               {
                  importConfirmMsg = "";
               }
               rs.append( "</div>" );
               // js 代码
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

      // 获取当前需要生成控件管理界面的TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );
      // 初始化PagedListHolder
      final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );
      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();

      int appendTdSize = 0;

      // 遍历Column Group
      if ( tableDTO != null && tableDTO.getListDTOs() != null && pagedListHolder != null && tableDTO.getListDTOs().size() > 0 )
      {
         // 初始化ListDTO
         final ListDTO listDTO = tableDTO.getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );
         rs.append( "<table class=\"table hover\" id=\"resultTable\">" );

         if ( listDTO != null && listDTO.getListDetailVOs() != null && listDTO.getListDetailVOs().size() > 0 )
         {
            // 初始化ListHeaderVO
            final ListHeaderVO listHeaderVO = listDTO.getListHeaderVO();

            // 遍历生成Table Head
            rs.append( "<thead>" );
            rs.append( "<tr>" );
            //雇员登陆不显示
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
                  // 初始化ListDetailVO
                  listDetailVO.reset( null, request );

                  // 初始化字段对象
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

                  // 初始化列表字段样式
                  String thStyle = "";
                  if ( listDetailVO.getColumnWidth() != null && !listDetailVO.getColumnWidth().trim().equals( "" ) )
                  {
                     // 初始化Width Type，默认是百分比
                     String widthType = "%";
                     if ( listDetailVO.getColumnWidthType() != null )
                     {
                        // 列宽按照象素显示
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
                  // 非系统字段不能排序
                  if ( ( columnVO != null && !KANConstants.SUPER_ACCOUNT_ID.equals( columnVO.getAccountId() ) )
                        || ( listDetailVO != null && listDetailVO.getSort() != null && listDetailVO.getSort().trim().equals( "2" ) ) )
                  {
                     sort = false;
                  }

                  // 列标题
                  rs.append( "<th " + thStyle + " class=\"" );

                  // 排序设置
                  if ( sort )
                  {
                     rs.append( "header " );
                  }
                  else
                  {
                     rs.append( "header-nosort " );
                  }

                  // 无关联的情况不操作
                  if ( columnVO != null )
                  {
                     rs.append( pagedListHolder.getCurrentSortClass( columnVO.getNameDB() ) );
                  }
                  rs.append( "\">" );

                  // 无关联的情况不添加链接
                  if ( columnVO != null && sort )
                  {
                     rs.append( "<a onclick=\"submitForm('list_form', null, null, '" + columnVO.getNameDB() + "', '" + pagedListHolder.getNextSortOrder( columnVO.getNameDB() )
                           + "', 'tableWrapper');\">" );
                  }

                  // 如果需要设置样式
                  if ( valueStyle != null && !valueStyle.trim().equals( "" ) )
                  {
                     rs.append( "<span " + valueStyle + ">" );
                  }

                  // 调用内部方法获得列名
                  rs.append( getColumnName( request, listDetailVO ) );

                  // 如果需要设置样式
                  if ( valueStyle != null && !valueStyle.trim().equals( "" ) )
                  {
                     rs.append( "</span>" );
                  }

                  // 无关联的情况不添加链接
                  if ( columnVO != null && sort )
                  {
                     rs.append( "</a>" );
                  }
                  rs.append( "</th>" );
               }
            }

            rs.append( "</tr>" );
            rs.append( "</thead>" );

            // 遍历生成Table Body
            if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               rs.append( "<tbody>" );

               // Index 用以判断当前的行号，区分列表不同行的样式
               int index = 0;
               // 遍历行
               for ( Object object : pagedListHolder.getSource() )
               {
                  // 行样式
                  final String trClass = index % 2 == 1 ? "odd" : "even";
                  // 是否存在关联数据
                  final Object extended = KANUtil.getValue( object, "extended" );

                  rs.append( "<tr ondblclick=kanlist_dbclick(this,null)" + " class='" + trClass + "'>" );
                  //雇员登陆不显示
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

                  // 遍历列
                  for ( ListDetailVO listDetailVO : listDTO.getListDetailVOs() )
                  {
                     if ( listDetailVO.getDisplay() == null || !listDetailVO.getDisplay().trim().equals( "2" ) )
                     {
                        // 初始化字段对象
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

                        // 初始化列表字段样式
                        String valueStyle = "";
                        if ( listDetailVO.getFontSize() != null && !listDetailVO.getFontSize().trim().equals( "" ) && !listDetailVO.getFontSize().trim().equals( "0" ) )
                        {
                           valueStyle = valueStyle + "font-size: " + listDetailVO.getFontSize() + "px;";
                        }
                        if ( valueStyle != null && !valueStyle.trim().equals( "" ) )
                        {
                           valueStyle = " style=\"" + valueStyle + "\" ";
                        }

                        // 初始化链接方式
                        String target = "target=\"_self\"";
                        if ( listDetailVO.getLinkedTarget() != null && listDetailVO.getLinkedTarget().trim().equals( "2" ) )
                        {
                           target = "target=\"_blank\"";
                        }

                        // 初始化动态参数
                        String properties[] = null;
                        if ( listDetailVO.getProperties() != null && !listDetailVO.getProperties().trim().equals( "" ) )
                        {
                           properties = listDetailVO.getProperties().trim().split( "," );
                        }

                        rs.append( "<td align=\"" + listDetailVO.getDecodeAlignTemp() + "\">" );

                        // 如果当前字段是带链接的
                        if ( listDetailVO.getIsLinked() != null && listDetailVO.getIsLinked().equals( ListDetailVO.TRUE ) && listDetailVO.getLinkedAction() != null
                              && !listDetailVO.getLinkedAction().trim().equals( "" ) )
                        {
                           // 获得当前字段的链接
                           String link = listDetailVO.getLinkedAction();

                           // 如果链接中含有参数
                           if ( link.contains( "${" ) )
                           {
                              // 遍历并替换链接中的参数
                              if ( properties != null && properties.length > 0 )
                              {
                                 for ( int i = 0; i < properties.length; i++ )
                                 {
                                    final Object propertyValueObject = KANUtil.getValue( object, properties[ i ] );
                                    link = link.replace( "${" + i + "}", propertyValueObject != null ? propertyValueObject.toString() : "" );
                                 }
                              }
                           }

                           // 如果当前链接中为传入主键ID
                           if ( !link.contains( "&id=" ) )
                           {
                              link = link + "&id=" + KANUtil.getValue( object, "encodedId" );
                           }

                           // 添加链接地址
                           rs.append( "<a href=\"" + link + "\" " + target + ">" );
                        }

                        if ( columnVO != null )
                        {
                           // 如果需要设置样式
                           if ( valueStyle != null && !valueStyle.trim().equals( "" ) )
                           {
                              rs.append( "<span " + valueStyle + ">" );
                           }

                           rs.append( getColumnValue( request, columnVO, listDetailVO, object ) );

                           // 如果需要设置样式
                           if ( valueStyle != null && !valueStyle.trim().equals( "" ) )
                           {
                              rs.append( "</span>" );
                           }
                        }

                        // 如果当前字段是带链接的
                        if ( listDetailVO.getIsLinked() != null && listDetailVO.getIsLinked().equals( ListDetailVO.TRUE ) && listDetailVO.getLinkedAction() != null
                              && !listDetailVO.getLinkedAction().trim().equals( "" ) )
                        {
                           rs.append( "</a>" );
                        }

                        // 如果存在附加内容
                        if ( listDetailVO.getAppendContent() != null && !listDetailVO.getAppendContent().trim().equals( "" ) )
                        {
                           // 获得当前字段的附加内容
                           String appendContent = listDetailVO.getAppendContent();

                           // 如果链接中含有参数
                           if ( appendContent.contains( "${" ) )
                           {
                              // 遍历并替换附加内容中的参数
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
                                    else if ( properties[ i ].equals( "isShowHandle" ) && contractAccessAction.contains( accessAction ) )//劳动合同页面离职处理跟打印权限单独处理
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
               // 生成Table Foot
               rs.append( "<tfoot>" );
               rs.append( "<tr class=\"total\">" );
               rs.append( "<td colspan=\"" + ( listDTO.getListDetailVOs().size() + appendTdSize + 1 ) + "\" class=\"left\">" );
               rs.append( "<label>&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.total" ) + "： " + pagedListHolder.getHolderSize() + " " );
               rs.append( "<label>&nbsp;&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.current" ) + "：" + pagedListHolder.getIndexStart() + " - "
                     + pagedListHolder.getIndexEnd() + "</label> " );
               rs.append( "<label>&nbsp;&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getFirstPage() + "', null, null, 'tableWrapper', null, "
                     + ( useFixColumn ? "'useFixColumn();'" : "'null'" ) + ");\">" + KANUtil.getProperty( request.getLocale(), "page.first" ) + "</a></label> " );
               rs.append( "<label>&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getPreviousPage() + "', null, null, 'tableWrapper', null, "
                     + ( useFixColumn ? "'useFixColumn();'" : "'null'" ) + ");\">" + KANUtil.getProperty( request.getLocale(), "page.previous" ) + "</a></label> " );
               rs.append( "<label>&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getNextPage() + "', null, null, 'tableWrapper', null, "
                     + ( useFixColumn ? "'useFixColumn();'" : "'null'" ) + " );\">" + KANUtil.getProperty( request.getLocale(), "page.next" ) + "</a></label> " );
               rs.append( "<label>&nbsp;<a onclick=\"submitForm('list_form', null, '" + pagedListHolder.getLastPage() + "', null, null, 'tableWrapper', null, "
                     + ( useFixColumn ? "'useFixColumn();'" : "'null'" ) + ");\">" + KANUtil.getProperty( request.getLocale(), "page.last" ) + "</a></label> " );
               rs.append( "<label>&nbsp;&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.pagination" ) + "：" + pagedListHolder.getRealPage() + "/"
                     + pagedListHolder.getPageCount() + "</label>&nbsp;" );
               rs.append( "<label>&nbsp;&nbsp;" + KANUtil.getProperty( request.getLocale(), "page.jump.to" )
                     + "：<input type=\"text\" id=\"forwardPage_render\" class=\"forwardPage_render\" style=\"width:23px;\" value=\"" + pagedListHolder.getRealPage()
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
      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();
      // 初始化ModuleDTO
      final ModuleDTO moduleDTO = KANConstants.getModuleDTOByAccessAction( accessAction );

      // 菜单选中JS添加
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

      // 列表初始化JS添加
      rs.append( "kanList_init();kanCheckbox_init();" );

      return rs.toString();
   }

   // 是否按客户模板导出
   private static String importByClientId( final PagedListHolder pagedListHolder ) throws KANException
   {
      // PagedListHolder搜索条件存在clientId
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

   // 生成普通的Excel文件
   // Add by siuvan.xia @ 2014-07-09
   @SuppressWarnings("unchecked")
   public static XSSFWorkbook generateCommonListExcel( final HttpServletRequest request, final String fileName, final Boolean isDTO ) throws KANException
   {
      // 初始化工作薄
      final XSSFWorkbook workbook = new XSSFWorkbook();

      try
      {
         // 获取holderName
         final String holderName = ( String ) request.getAttribute( "holderName" );

         // 获取数据源
         final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( holderName );

         // 获取导出表头中文名
         final String[] nameZHArray = ( String[] ) request.getAttribute( "nameZHArray" );

         // 获取导出表头系统名
         final String[] nameSysArray = ( String[] ) request.getAttribute( "nameSysArray" );

         // 创建字体
         final Font font = workbook.createFont();
         font.setFontName( "Calibri" );
         font.setFontHeightInPoints( ( short ) 11 );

         // 创建单元格样式
         final CellStyle cellStyleLeft = workbook.createCellStyle();
         cellStyleLeft.setFont( font );
         cellStyleLeft.setAlignment( CellStyle.ALIGN_LEFT );

         // 创建单元格样式
         final CellStyle cellStyleCenter = workbook.createCellStyle();
         cellStyleCenter.setFont( font );
         cellStyleCenter.setAlignment( CellStyle.ALIGN_CENTER );

         // 创建单元格样式
         final CellStyle cellStyleRight = workbook.createCellStyle();
         cellStyleRight.setFont( font );
         cellStyleRight.setAlignment( CellStyle.ALIGN_RIGHT );

         // 创建表格
         final Sheet sheet = workbook.createSheet( fileName );
         // 设置表格默认列宽度为15个字节
         sheet.setDefaultColumnWidth( 15 );

         // 生成Excel Header Row
         final Row rowHeader = sheet.createRow( 0 );

         // 生成表头
         if ( nameZHArray != null && nameZHArray.length > 0 )
         {
            // 用以标识Header列序号
            int headerColumnIndex = 0;
            for ( int i = 0; i < nameZHArray.length; i++ )
            {
               final Cell cell = rowHeader.createCell( headerColumnIndex );
               cell.setCellValue( nameZHArray[ i ] );
               cell.setCellStyle( cellStyleLeft );
               headerColumnIndex++;
            }
         }

         // 生成表体
         if ( nameSysArray != null && nameSysArray.length > 0 && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
         {
            // 用以标识Body行序号
            int bodyRowIndex = 1;

            // 遍历行
            for ( Object object : pagedListHolder.getSource() )
            {
               if ( isDTO )
               {
                  // 加上Detail信息显示
                  if ( object instanceof SpecialDTO )
                  {
                     object = KANUtil.getValue( object, "headerVO" );
                  }
               }

               // 用以标识Body列序号
               int bodyColumnIndex = 0;

               // 生成Excel Body Row
               final Row rowBody = sheet.createRow( bodyRowIndex );

               // 遍历列
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
                              // 如果是奖金（提成）
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

   // 生成特殊Excel文件
   // AddedExtendListDTO 导出结算单的时候，导出的列比显示的列多几项
   @SuppressWarnings("unchecked")
   public static XSSFWorkbook generateSpecialListExcelAddExtendColumns( final HttpServletRequest request, final String javaObjectName, final String templateType,
         final String templateId, final ListDTO addedExtendListDTO ) throws KANException
   {
      try
      {
         // 初始化corpId
         final String corpId = KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) );

         // 标记是社保单
         boolean isSBBill = javaObjectName.equals( SBBillViewAction.JAVA_OBJECT_NAME );

         ListDTO listDTO = null;
         // 获取ListDTO 如果为空，则是和页面导出匹配
         if ( addedExtendListDTO == null )
         {
            listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, corpId );
         }
         //如果不为空，则导出列和页面显示不一样，LISTDTO则为传过来的。
         else
         {
            listDTO = addedExtendListDTO;
         }

         // 初始化模板对象
         Object templateObject = null;

         // 工资模板
         if ( "1".equals( templateType ) )
         {
            // 获取BankTemplateDTO
            templateObject = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getBankTemplateDTOByTemplateHeaderId( templateId );
         }
         // 个税模板
         else if ( "2".equals( templateType ) )
         {
            templateObject = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTaxTemplateDTOByTemplateHeaderId( templateId );
         }

         // 初始化PagedListHolder
         final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );

         final String groupColumn = ( String ) KANUtil.getValue( pagedListHolder.getObject(), "groupColumn" );
         boolean show = true;
         if ( KANUtil.filterEmpty( groupColumn, "0" ) != null )
         {
            show = false;
         }

         // 初始化工作薄
         final XSSFWorkbook workbook = new XSSFWorkbook();

         if ( listDTO != null && pagedListHolder != null )
         {
            //金额匹配
            Pattern pattern = Pattern.compile( "^(-)?(([1-9]{1}\\d*)|([0]{1}))\\.(\\d){1,4}" );
            // 创建字体
            final Font font = workbook.createFont();
            font.setFontName( "Calibri" );
            font.setFontHeightInPoints( ( short ) 11 );

            // 创建单元格样式
            final CellStyle cellStyleLeft = workbook.createCellStyle();
            cellStyleLeft.setFont( font );
            cellStyleLeft.setAlignment( XSSFCellStyle.ALIGN_LEFT );

            final CellStyle cellStyleNumberic = workbook.createCellStyle();
            cellStyleNumberic.setDataFormat( workbook.createDataFormat().getFormat( "##0.00" ) );
            cellStyleNumberic.setAlignment( XSSFCellStyle.ALIGN_RIGHT );
            // 创建单元格样式
            final CellStyle cellStyleCenter = workbook.createCellStyle();
            cellStyleCenter.setFont( font );
            cellStyleCenter.setAlignment( XSSFCellStyle.ALIGN_CENTER );

            // 创建单元格样式
            final CellStyle cellStyleRight = workbook.createCellStyle();
            cellStyleRight.setFont( font );
            cellStyleRight.setAlignment( XSSFCellStyle.ALIGN_RIGHT );

            // 创建表格
            final Sheet sheet = workbook.createSheet( listDTO.getListName( request ) );

            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth( 15 );

            // 初始化（合并主从ListDetailVO）ListDetailVO列表
            final List< ListDetailVO > mergeListDetailVOs = new ArrayList< ListDetailVO >();

            // 用以标记冻结列：员工ID、员工姓名（中文）
            int employeeIdColumnNum = 0;
            int employeeNameZHColumnNum = 0;

            // 存在列表字段
            if ( listDTO.getListDetailVOs() != null && listDTO.getListDetailVOs().size() > 0 )
            {
               // 初始化clientId
               final String corpIdTemp = KANUtil.filterEmpty( importByClientId( pagedListHolder ) );

               // 如果按客户导出匹配
               if ( corpIdTemp != null && templateObject == null )
               {
                  templateObject = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getMappingDTOByCondition( corpIdTemp, listDTO.getListHeaderVO().getListHeaderId() );
               }

               // 存在模板对象
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
                        // 初始化ListDetailVO
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
               // 不使用模板默认按配置列表导出
               else
               {
                  // 装入主ListDetailVO
                  mergeListDetailVOs.addAll( listDTO.getListDetailVOs() );

                  // 如果存在从ListDetailVO
                  if ( listDTO.getSubListDTOs() != null && listDTO.getSubListDTOs().size() > 0 )
                  {
                     // 初始化Sub ListDetailVO
                     final List< ListDetailVO > subListDetailVOs = mergeListDetailVO( request, listDTO.getSubListDTOs() );

                     if ( subListDetailVOs != null && subListDetailVOs.size() > 0 )
                     {
                        mergeListDetailVOs.addAll( subListDetailVOs );
                     }
                  }

                  // 排序
                  if ( mergeListDetailVOs.size() > 0 )
                  {
                     Collections.sort( mergeListDetailVOs, new ComparatorListDetail() );
                  }
               }

               // 生成Excel Header Row
               final Row rowHeader = sheet.createRow( 0 );

               // 用以标识Header列序号
               int headerColumnIndex = 0;

               for ( ListDetailVO listDetailVO : mergeListDetailVOs )
               {
                  // 初始化ListDetailVO
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

                  // 初始化Excel列宽，针对用户定义固定列宽的情况
                  if ( listDetailVO.getColumnWidthType() != null && listDetailVO.getColumnWidthType().trim().equals( "2" ) && listDetailVO.getColumnWidth() != null
                        && listDetailVO.getColumnWidth().trim().matches( "[0-9]*" ) )
                  {
                     // 换算Excel列宽并取整
                     final BigDecimal columnWidth = new BigDecimal( Integer.valueOf( listDetailVO.getColumnWidth() ) * 30 ).setScale( 0, BigDecimal.ROUND_HALF_UP );
                     // 设置Excel列宽
                     sheet.setColumnWidth( headerColumnIndex, columnWidth == null ? 0 : columnWidth.intValue() );
                  }

                  // 初始化Excel表头名
                  String excelTitle = "";
                  if ( isSBBill && listDetailVO.isSBItem() )
                  {
                     excelTitle = "基数";
                     final Cell cellBase = rowHeader.createCell( headerColumnIndex );
                     final XSSFRichTextString textBase = new XSSFRichTextString( excelTitle );
                     cellBase.setCellValue( textBase );
                     cellBase.setCellStyle( cellStyleLeft );
                     headerColumnIndex++;

                     excelTitle = "比例%";
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

               // 遍历生成Excel Body
               if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
               {
                  // 用以标识Body行序号
                  int bodyRowIndex = 1;

                  // 遍历行
                  for ( Object object : pagedListHolder.getSource() )
                  {
                     // 初始化Object - DTO[Header]
                     Object objectHeader = null;
                     // 初始化List< Object > - DTO[Details]
                     List< ? > objectDetails = null;
                     if ( object instanceof SpecialDTO )
                     {
                        // 获取SpecialDTO
                        final SpecialDTO< Object, List< ? > > specialDTO = ( SpecialDTO< Object, List< ? > > ) object;

                        objectDetails = specialDTO.getDetailVOs();
                        objectHeader = specialDTO.getHeaderVO();
                        // 国际化传值    
                        ( ( ActionForm ) objectHeader ).reset( null, request );
                     }

                     // 生成Excel Body Row
                     final Row rowBody = sheet.createRow( bodyRowIndex );

                     // 用以标识Body列序号
                     int bodyColumnIndex = 0;

                     // 遍历列
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

                        // 初始化Excel单元格值
                        String columnValue = "";

                        // 获取基数
                        if ( isSBBill && listDetailVO.isSBItem() )
                        {
                           columnValue = getColumnValue( request, listDetailVO, objectHeader, objectDetails, listDetailVO.filterCompany() ? "baseCompany" : "basePersonal" );
                           // 初始化XSSFCell
                           final Cell cellBase = rowBody.createCell( bodyColumnIndex );
                           // 设置单元格对齐
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
                           // 初始化XSSFCell
                           final Cell cellRate = rowBody.createCell( bodyColumnIndex );
                           // 设置单元格对齐
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

                        // 初始化XSSFCell
                        final Cell cell = rowBody.createCell( bodyColumnIndex );

                        // 设置单元格对齐
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

            // 冻结员工ID，员工姓名
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
      // 添加Log Start跟踪
      LogFactory.getLog( accessAction ).info( "Excel Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") Start." );

      // 获取当前需要生成控件管理界面的TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );
      // 初始化PagedListHolder
      final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );

      // 初始化工作薄
      final XSSFWorkbook workbook = new XSSFWorkbook();

      if ( tableDTO != null && tableDTO.getListDTOs() != null && pagedListHolder != null && tableDTO.getListDTOs().size() > 0 )
      {
         // 初始化ListDTO
         final ListDTO listDTO = tableDTO.getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );

         // 创建字体
         final Font font = workbook.createFont();
         font.setFontName( "Calibri" );
         font.setFontHeightInPoints( ( short ) 11 );

         // 创建单元格样式
         final CellStyle cellStyleLeft = workbook.createCellStyle();
         cellStyleLeft.setFont( font );
         cellStyleLeft.setAlignment( XSSFCellStyle.ALIGN_LEFT );

         // 创建单元格样式
         final CellStyle cellStyleCenter = workbook.createCellStyle();
         cellStyleCenter.setFont( font );
         cellStyleCenter.setAlignment( XSSFCellStyle.ALIGN_CENTER );

         // 创建单元格样式
         final CellStyle cellStyleRight = workbook.createCellStyle();
         cellStyleRight.setFont( font );
         cellStyleRight.setAlignment( XSSFCellStyle.ALIGN_RIGHT );

         // 创建表格
         final Sheet sheet = workbook.createSheet( listDTO.getListName( request ) );

         // 设置表格默认列宽度为15个字节
         sheet.setDefaultColumnWidth( 15 );

         if ( listDTO.getListDetailVOs() != null && listDTO.getListDetailVOs().size() > 0 )
         {
            // 生成Excel Header Row
            final Row rowHeader = sheet.createRow( 0 );

            // 用以标识Header列序号
            int headerColumnIndex = 0;

            for ( ListDetailVO listDetailVO : listDTO.getListDetailVOs() )
            {
               // 初始化ListDetailVO
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

               // 初始化Excel列宽，针对用户定义固定列宽的情况
               if ( listDetailVO.getColumnWidthType() != null && listDetailVO.getColumnWidthType().trim().equals( "2" ) && listDetailVO.getColumnWidth() != null
                     && listDetailVO.getColumnWidth().trim().matches( "[0-9]*" ) )
               {
                  // 换算Excel列宽并取整
                  final BigDecimal columnWidth = new BigDecimal( Integer.valueOf( listDetailVO.getColumnWidth() ) * 30 ).setScale( 0, BigDecimal.ROUND_HALF_UP );
                  // 设置Excel列宽
                  sheet.setColumnWidth( headerColumnIndex, columnWidth.intValue() );
               }

               headerColumnIndex++;
            }

            // 遍历生成Excel Body
            if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               // 用以标识Body行序号
               int bodyRowIndex = 1;

               // 遍历行
               for ( Object object : pagedListHolder.getSource() )
               {
                  // 生成Excel Body Row
                  final Row rowBody = sheet.createRow( bodyRowIndex );

                  // 用以标识Body列序号
                  int bodyColumnIndex = 0;

                  // 遍历列
                  for ( ListDetailVO listDetailVO : listDTO.getListDetailVOs() )
                  {
                     // 初始化字段对象
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
                     // 设置单元格值
                     cell.setCellValue( text );

                     // 设置单元格对齐
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

      // 添加Log End跟踪
      LogFactory.getLog( accessAction ).info( "Excel Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") End." );

      return workbook;
   }

   /**  
    * generatePositionListExcelByBranch
    *	按部门的层级关系生成Excel
    *	@param request
    *	@param accessAction
    *	@return
    * Add By：Jack  
    * Add Date：2015-2-4  
    * @throws KANException 
    */
   public static XSSFWorkbook generatePositionListExcelByBranch( HttpServletRequest request, String accessAction ) throws KANException
   {
      // 从Constants中得到当前Account的BranchDTO的列表
      final List< BranchDTO > branchDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getBranchDTOsByCorpId( BaseAction.getCorpId( request, null ) );
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionDTOsByCorpId( BaseAction.getCorpId( request, null ) );

      // 初始化工作薄
      final XSSFWorkbook workbook = new XSSFWorkbook();

      if ( branchDTOs != null && branchDTOs.size() > 0 )
      {
         // 创建表格 - 设置表名
         final Sheet sheet = workbook.createSheet( "职位表（按部门层级关系）" );

         // 设置表格默认列宽度为15个字节
         sheet.setDefaultColumnWidth( 15 );

         final Map< String, BranchDTO > branchMap = new HashMap< String, BranchDTO >();

         /**
          *  生成表头
          */
         // 默认5级
         int branch_num = 5;

         final Row row = sheet.createRow( 0 );

         // 前2列填充职位信息
         final Cell nameCell_first = row.createCell( 0 );
         nameCell_first.setCellStyle( getFirstCellStyleByLevel( workbook ) );
         nameCell_first.setCellValue( "职位名称" );

         final Cell idCell_first = row.createCell( 1 );
         idCell_first.setCellStyle( getHeaderCellStyleByLevel( workbook ) );
         idCell_first.setCellValue( "职位ID" );

         // 第三、四列填充职位对应的职级信息
         final Cell nameCell_second = row.createCell( 2 );
         nameCell_second.setCellStyle( getHeaderCellStyleByLevel( workbook ) );
         nameCell_second.setCellValue( "职级名称" );

         final Cell idCell_second = row.createCell( 3 );
         idCell_second.setCellStyle( getHeaderCellStyleByLevel( workbook ) );
         idCell_second.setCellValue( "职级ID" );

         // 初始化所有长度的集合
         Set< String > branchSizeSet = new HashSet< String >();

         // excel表体信息
         getPositionSheetByBranch( sheet, workbook, branchDTOs, positionDTOs, 0, branchMap, request, branchSizeSet );

         // 部门名称显示
         for ( int i = 0; i < branch_num; i++ )
         {
            final Cell nameCell_branch = row.createCell( 4 + i * 2 );
            nameCell_second.setCellStyle( getHeaderCellStyleByLevel( workbook ) );
            nameCell_branch.setCellValue( "部门名称（Level:" + ( i + 1 ) + "）" );

            final Cell idCell_branch = row.createCell( 5 + i * 2 );
            idCell_second.setCellStyle( getHeaderCellStyleByLevel( workbook ) );
            idCell_branch.setCellValue( "部门ID（Level:" + ( i + 1 ) + "）" );
         }
      }

      return workbook;
   }

   /**  
    * getBranchNode
    * 按部门层级关系生成职位Excel
    * @param sheet - 职位表
    * @param workbook - 工作簿
    * @param branchDTOs - 部门组
    * @param positionDTOs - 职位组
    * @param level - 当前层级
    * @param rowNum - 当前行号
    * @param branchMap - 职位的上级部门集合
    * @param request 
    * @param branchSizeSet 
    * Add By：Jack  
    * Add Date：2015-2-2  
    * @throws KANException 
    */
   private static void getPositionSheetByBranch( final Sheet sheet, XSSFWorkbook workbook, final List< BranchDTO > branchDTOs, List< PositionDTO > positionDTOs, final int level,
         final Map< String, BranchDTO > branchMap, HttpServletRequest request, Set< String > branchSizeSet ) throws KANException
   {
      if ( level == 0 )
      {
         branchMap.clear();
      }
      // 初始化部门级位数最大值
      int branch_num = 0;

      if ( branchDTOs != null && branchDTOs.size() > 0 )
      {

         for ( BranchDTO branchDTO : branchDTOs )
         {
            // 初始化该部门下对应的职位
            List< PositionDTO > positionDTOs_inBranch = new ArrayList< PositionDTO >();

            // 查询该部门下对应的职位
            if ( branchDTO != null && branchDTO.getBranchVO() != null && branchDTO.getBranchVO().getBranchId() != null )
            {
               fetchPositionDTOsByBranchId( positionDTOs_inBranch, branchDTO.getBranchVO().getBranchId(), positionDTOs );
            }

            // 获得部门级位数最大值
            if ( positionDTOs_inBranch != null && positionDTOs_inBranch.size() > 0 )
            {
               branchSizeSet.add( positionDTOs_inBranch.size() + "" );

               if ( positionDTOs_inBranch.size() > branch_num )
               {
                  branch_num = positionDTOs_inBranch.size();
               }
            }

            // 如果部门存在对应职位
            if ( positionDTOs_inBranch.size() > 0 )
            {
               for ( PositionDTO positionDTO : positionDTOs_inBranch )
               {
                  final PositionVO positionVO = positionDTO.getPositionVO();
                  if ( positionVO != null )
                  {
                     // 获得职位对应职级
                     PositionGradeVO positionGradeVO = new PositionGradeVO();
                     final String positionGradeId = positionVO.getPositionGradeId();

                     if ( KANUtil.filterEmpty( positionGradeId ) != null )
                     {
                        positionGradeVO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionGradeVOByPositionGradeId( positionGradeId );
                     }

                     // 生成Excel Body Cell
                     final Row row = sheet.createRow( sheet.getPhysicalNumberOfRows() );

                     // 前2列填充职位信息
                     final Cell nameCell_first = row.createCell( 0 );
                     nameCell_first.setCellStyle( getFirstCellStyleByLevel( workbook ) );
                     nameCell_first.setCellValue( positionVO.getTitleZH() );

                     final Cell idCell_first = row.createCell( 1 );
                     idCell_first.setCellStyle( getFirstCellStyleByLevel( workbook ) );
                     idCell_first.setCellValue( positionVO.getPositionId() );

                     // 第三、四列填充职位对应的职级信息
                     final Cell nameCell_second = row.createCell( 2 );
                     nameCell_second.setCellStyle( getSecondCellStyleByLevel( workbook ) );
                     nameCell_second.setCellValue( positionGradeVO.getGradeNameZH() );

                     final Cell idCell_second = row.createCell( 3 );
                     idCell_second.setCellStyle( getSecondCellStyleByLevel( workbook ) );
                     idCell_second.setCellValue( positionGradeId );

                     // 根据部门的层级关系设置样式
                     final Cell nameCell = row.createCell( ( level + 2 ) * 2 );
                     nameCell.setCellStyle( getCellStyleByLevel( workbook, level ) );
                     nameCell.setCellValue( branchDTO.getBranchVO().getNameZH() );

                     final Cell idCell = row.createCell( ( level + 2 ) * 2 + 1 );
                     idCell.setCellStyle( getCellStyleByLevel( workbook, level ) );
                     idCell.setCellValue( branchDTO.getBranchVO().getBranchId() );

                     // 填充上级部门
                     if ( level > 0 )
                     {
                        for ( int i = 0; i < level; i++ )
                        {
                           // 根据部门的层级关系设置样式
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
               // 生成Excel Body Cell
               final Row row = sheet.createRow( sheet.getPhysicalNumberOfRows() );

               // 根据部门的层级关系设置样式
               final Cell nameCell = row.createCell( ( level + 2 ) * 2 );
               nameCell.setCellStyle( getCellStyleByLevel( workbook, level ) );
               nameCell.setCellValue( branchDTO.getBranchVO().getNameZH() );

               final Cell idCell = row.createCell( ( level + 2 ) * 2 + 1 );
               idCell.setCellStyle( getCellStyleByLevel( workbook, level ) );
               idCell.setCellValue( branchDTO.getBranchVO().getBranchId() );

               // 填充上级部门
               if ( level > 0 )
               {
                  for ( int i = 0; i < level; i++ )
                  {
                     // 根据部门的层级关系设置样式
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
    *	获得部门下对应的职位集合
    * @param positionDTOs_inBranch - 返回的集合
    *	@param branchId - 对应的部门ID
    *	@param positionDTOs - 所有职位
    *	@return
    * Add By：Jack  
    * Add Date：2015-2-4  
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

            // 如果职位的从对象存在同样验证
            if ( positionDTO.getPositionDTOs() != null && positionDTO.getPositionDTOs().size() > 0 )
            {
               fetchPositionDTOsByBranchId( positionDTOs_inBranch, branchId, positionDTO.getPositionDTOs() );
            }
         }
      }
   }

   /**  
    * generatePositionListExcelByPosition
    *	按职位的层级关系生成Excel
    *	@param request
    *	@param accessAction
    *	@return
    *	@throws KANException
    * Add By：Jack  
    * Add Date：2015-2-4  
    */
   public static XSSFWorkbook generatePositionListExcelByPosition( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      // 从Constants中得到当前Account的PositionDTO的列表
      final List< PositionDTO > positionDTOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionDTOsByCorpId( BaseAction.getCorpId( request, null ) );

      // 初始化工作薄
      final XSSFWorkbook workbook = new XSSFWorkbook();

      if ( positionDTOs != null && positionDTOs.size() > 0 )
      {
         // 创建表格 - 设置表名
         final XSSFSheet sheet = workbook.createSheet( "职位表（按职位等级）" );

         // 设置表格默认列宽度为15个字节
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
    *	生成职位Excel
    *	@param sheet - 职位表
    * @param workbook - 工作簿
    *	@param positionDTOs - 职位组
    *	@param level - 当前层级
    *	@param rowNum - 当前行号
    * @param positionMap - 职位的上级职位集合
    * Add By：Jack  
    * Add Date：2015-2-2  
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
            // 生成Excel Body Cell
            final Row row = sheet.createRow( sheet.getPhysicalNumberOfRows() );

            final Cell idCell_first = row.createCell( 0 );
            idCell_first.setCellStyle( getFirstCellStyleByLevel( workbook ) );
            idCell_first.setCellValue( positionDTO.getPositionVO().getPositionId() );

            // 第一列单元格填充职位ID信息
            final Cell nameCell_first = row.createCell( 1 );
            nameCell_first.setCellStyle( getFirstCellStyleByLevel( workbook ) );
            nameCell_first.setCellValue( positionDTO.getPositionVO().getTitleZH() );

            // 根据部门的层级关系设置样式
            String cellValue = "";
            cellValue = positionDTO.getPositionVO().getTitleZH();
            String staffNames = getStaffNamesByPositionVO( KANConstants.getKANAccountConstants( positionDTO.getPositionVO().getAccountId() ), positionDTO.getPositionVO() );
            cellValue = cellValue + "员工：" + ( ( KANUtil.filterEmpty( staffNames ) == null ) ? "无" : staffNames );
            final Cell nameCell = row.createCell( level + 2 );
            nameCell.setCellStyle( getCellStyleByLevel( workbook, level ) );
            nameCell.setCellValue( cellValue );

            // 填充上级职位
            if ( level > 0 )
            {
               for ( int i = 0; i < level; i++ )
               {
                  String parentCellValue = "";
                  // 根据部门的层级关系设置样式
                  final Cell nameCell_temp = row.createCell( i + 2 );
                  nameCell_temp.setCellStyle( getCellStyleByLevel( workbook, i ) );
                  parentCellValue = positionMap.get( String.valueOf( i ) ).getPositionVO().getTitleZH();
                  String parentStaffNames = getStaffNamesByPositionVO( KANConstants.getKANAccountConstants( positionDTO.getPositionVO().getAccountId() ), positionMap.get( String.valueOf( i ) ).getPositionVO() );
                  parentCellValue = parentCellValue + "员工：" + ( ( KANUtil.filterEmpty( parentStaffNames ) == null ) ? "无" : parentStaffNames );

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
    *	表头样式
    *	@param workbook
    *	@return
    * Add By：Jack  
    * Add Date：2015-2-11  
    */
   private static CellStyle getHeaderCellStyleByLevel( XSSFWorkbook workbook )
   {
      // 创建字体
      final Font font = workbook.createFont();
      font.setFontName( "Calibri" );
      font.setFontHeightInPoints( ( short ) 11 );

      // 创建单元格样式(居中)
      final CellStyle cellStyle = workbook.createCellStyle();
      cellStyle.setFont( font );
      cellStyle.setAlignment( XSSFCellStyle.ALIGN_CENTER );

      return cellStyle;
   }

   /**  
    * getFirstCellStyleByLevel
    *	职位表（按部门）前两列样式
    *	@param workbook
    *	@return
    * Add By：Jack  
    * Add Date：2015-2-11  
    */
   private static CellStyle getFirstCellStyleByLevel( XSSFWorkbook workbook )
   {
      // 创建字体
      final Font font = workbook.createFont();
      font.setFontName( "仿宋_GB2312" );
      // 字体大小
      font.setFontHeightInPoints( ( short ) 10 );
      // 红色字体
      font.setColor( IndexedColors.LIGHT_BLUE.getIndex() );
      // 加粗
      font.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );

      // 创建单元格样式
      final CellStyle cellStyle = workbook.createCellStyle();
      cellStyle.setFont( font );
      cellStyle.setAlignment( XSSFCellStyle.ALIGN_CENTER );
      //      cellStyle.setFillForegroundColor( IndexedColors.LAVENDER.getIndex() );
      //      cellStyle.setFillPattern( CellStyle.SOLID_FOREGROUND );

      return cellStyle;
   }

   /**  
    * getSecondCellStyleByLevel
    *	职位表（按部门）3、4列样式
    *	@param workbook
    *	@return
    * Add By：Jack  
    * Add Date：2015-2-11  
    */
   private static CellStyle getSecondCellStyleByLevel( XSSFWorkbook workbook )
   {
      // 创建字体
      final Font font = workbook.createFont();
      font.setFontName( "仿宋_GB2312" );
      // 字体大小
      font.setFontHeightInPoints( ( short ) 10 );
      // 红色字体
      font.setColor( IndexedColors.LAVENDER.getIndex() );
      // 加粗
      font.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );

      // 创建单元格样式
      final CellStyle cellStyle = workbook.createCellStyle();
      cellStyle.setFont( font );
      cellStyle.setAlignment( XSSFCellStyle.ALIGN_CENTER );
      //      cellStyle.setFillForegroundColor( IndexedColors.LAVENDER.getIndex() );
      //      cellStyle.setFillPattern( CellStyle.SOLID_FOREGROUND );

      return cellStyle;
   }

   private static CellStyle getCellStyleByLevel( XSSFWorkbook workbook, int level )
   {
      // 创建字体
      final Font font = workbook.createFont();
      font.setFontName( "Calibri" );
      font.setFontHeightInPoints( ( short ) 11 );

      // 创建单元格样式
      final CellStyle cellStyle = workbook.createCellStyle();
      cellStyle.setFont( font );

      // 设置背景颜色
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
      // 列表字段显示名称
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
      // 初始化当前Column的Value
      String value = "";
      if ( columnVO == null )
      {
         // 如果存在附加内容
         if ( listDetailVO.getAppendContent() != null && !listDetailVO.getAppendContent().trim().equals( "" ) && listDetailVO.getProperties() != null
               && !listDetailVO.getProperties().trim().equals( "" ) )
         {
            // 获得当前字段的附加内容
            String appendContent = listDetailVO.getAppendContent();
            String properties[] = listDetailVO.getProperties().trim().split( "," );

            // 如果链接中含有参数
            if ( appendContent.contains( "${" ) )
            {
               // 遍历并替换附加内容中的参数
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
         // 初始化ClientId
         final String corptId = BaseAction.getCorpId( request, null );

         // 列表字段名
         String fieldName = columnVO.getNameDB();
         if ( listDetailVO.getIsDecoded() != null && listDetailVO.getIsDecoded().equals( ListDetailVO.TRUE ) && fieldName != null && !fieldName.equals( "" ) )
         {
            fieldName = "decode" + String.valueOf( fieldName.toCharArray()[ 0 ] ).toUpperCase() + String.valueOf( fieldName.toCharArray(), 1, fieldName.length() - 1 );
         }

         // 如果是系统定义的字段
         if ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
         {
            value = ( String ) KANUtil.getValue( object, fieldName );
         }
         // 如果是用户自定义的字段
         else
         {
            try
            {
               // 如果数据对象不为空
               if ( object != null )
               {
                  // 从Object的Remark1字段获取Jason字符串
                  final Object jsonString = KANUtil.getValue( object, "remark1" );
                  if ( jsonString != null && !( ( String ) jsonString ).trim().equals( "" ) )
                  {
                     // 初始化Jason对象 - 含有当前字段的值
                     final JSONObject jsonObject = JSONObject.fromObject( URLDecoder.decode( ( String ) jsonString, "GBK" ).replace( "[{", "{" ).replace( "}]", "}" ) );

                     if ( jsonObject != null && jsonObject.containsKey( columnVO.getNameDB() ) )
                     {
                        value = jsonObject.getString( columnVO.getNameDB() );
                     }
                  }

                  // 下拉框并且需要解码
                  if ( KANUtil.filterEmpty( columnVO.getInputType() ) != null && columnVO.getInputType().trim().equals( "2" ) && listDetailVO.getIsDecoded() != null
                        && listDetailVO.getIsDecoded().equals( ListDetailVO.TRUE ) )
                  {
                     // 初始化MappingVO列表
                     List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
                     // 下拉框类型 - 系统常量
                     if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "1" ) )
                     {
                        // 获得系统常量选项列表
                        final List< MappingVO > systemOptions = KANUtil.getMappings( request.getLocale(), "def.column.option.type.system" );
                        // 遍历系统常量选项
                        if ( systemOptions != null && systemOptions.size() > 0 )
                        {
                           for ( MappingVO systemOption : systemOptions )
                           {
                              // 获得系统常量选项
                              if ( systemOption.getMappingId() != null && systemOption.getMappingId().trim().equals( columnVO.getOptionValue() ) )
                              {
                                 mappingVOs = KANUtil.getMappings( request.getLocale(), systemOption.getMappingTemp() );
                              }
                           }
                        }
                     }
                     // 下拉框类型 - 账户常量
                     else if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "2" ) )
                     {
                        // 获得账户常量选项列表
                        final List< MappingVO > accountOptions = KANUtil.getMappings( request.getLocale(), "def.column.option.type.account" );
                        // 遍历账户常量选项
                        if ( accountOptions != null && accountOptions.size() > 0 )
                        {
                           for ( MappingVO accountOption : accountOptions )
                           {
                              // 获得账户常量选项
                              if ( accountOption.getMappingId() != null && accountOption.getMappingId().trim().equals( columnVO.getOptionValue() ) )
                              {
                                 // 初始化Parameter Array
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
                                 // 添加空的MappingVO对象
                                 mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
                              }
                           }
                        }
                     }
                     // 下拉框类型 - 用户自定义
                     else if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "3" ) )
                     {
                        // 初始化OptionDTO
                        final OptionDTO optionDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getColumnOptionDTOByOptionHeaderId( columnVO.getOptionValue() );

                        if ( optionDTO != null )
                        {
                           mappingVOs = optionDTO.getOptions( request.getLocale().getLanguage() );
                        }
                     }
                     // 下拉框类型 - 直接值
                     else if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "4" ) )
                     {
                        // 如果用户定义的直接值并且不为空
                        if ( columnVO.getOptionValue() != null && !columnVO.getOptionValue().trim().equals( "" ) )
                        {
                           // 将用户定义的直接值转为JSONObject
                           final JSONObject optionsJSONObject = JSONObject.fromObject( columnVO.getOptionValue().replace( "[{", "{" ).replace( "}]", "}" ) );
                           // 遍历JSONObject
                           final Iterator< ? > keyIterator = optionsJSONObject.keys();
                           while ( keyIterator.hasNext() )
                           {
                              final String key = ( String ) keyIterator.next();
                              // 初始化MappingVO
                              final MappingVO mappingVO = new MappingVO();
                              mappingVO.setMappingId( key );
                              mappingVO.setMappingValue( optionsJSONObject.getString( key ) );
                              // 添加MappingVO至List
                              mappingVOs.add( mappingVO );
                           }
                        }
                     }
                     // 下拉框类型 - 预留
                     else if ( columnVO.getOptionType() != null && columnVO.getOptionType().trim().equals( "5" ) )
                     {
                        mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
                     }

                     // 下拉框值Decode
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
         // 例外字符传处理
         if ( value == null || value.trim().equalsIgnoreCase( "null" ) )
         {
            value = "";
         }

         // 如果用户设置日期格式化
         if ( listDetailVO.getDatetimeFormat() != null && !listDetailVO.getDatetimeFormat().trim().equals( "" ) && !listDetailVO.getDatetimeFormat().trim().equals( "0" )
               && value != null && !value.trim().equals( "" ) )
         {
            value = new SimpleDateFormat( listDetailVO.getDecodeDatetimeFormatTemp() ).format( KANUtil.createDate( value ) );
         }

         // 如果用户设置小数格式化
         if ( listDetailVO.getAccuracy() != null && !listDetailVO.getAccuracy().trim().equals( "" ) && !listDetailVO.getAccuracy().trim().equals( "0" ) && value != null
               && !value.trim().equals( "" ) && value.replace( ".", "" ).replace( "-", "" ).matches( "[0-9]*" ) )
         {
            // 默认为四舍五入
            int roundType = BigDecimal.ROUND_HALF_UP;

            if ( listDetailVO.getRound() != null )
            {
               // 截取
               if ( listDetailVO.getRound().trim().equals( "2" ) )
               {
                  roundType = BigDecimal.ROUND_DOWN;
               }
               // 带符号向上进位，正数进位，负数截取
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

   // 获取特殊列表的列值
   private static String getColumnValue( final HttpServletRequest request, final ListDetailVO listDetailVO, final Object objectHeader, List< ? > objectDetails, final String special )
         throws KANException
   {
      try
      {
         // 初始化返回值
         String value = "";

         // 初始化fieldName
         String fieldName = listDetailVO.getPropertyName();

         // 如果需要解译
         if ( listDetailVO.getIsDecoded() != null && listDetailVO.getIsDecoded().equals( ListDetailVO.TRUE ) && KANUtil.filterEmpty( fieldName ) != null )
         {
            fieldName = "decode" + String.valueOf( fieldName.toCharArray()[ 0 ] ).toUpperCase() + String.valueOf( fieldName.toCharArray(), 1, fieldName.length() - 1 );
         }

         // 获取属性按“_”截取的长度值
         final int length = fieldName.split( "_" ).length;

         /* 影响性能的罪魁祸首,移到循环外面去了*/
         // 初始化Object - DTO[Header]
         /* Object objectHeader = null;

          // 初始化List< Object > - DTO[Details]
          List< ? > objectDetails = null;

          if ( object instanceof SpecialDTO )
          {
             // 获取SpecialDTO
             final SpecialDTO< Object, List< ? > > specialDTO = ( SpecialDTO< Object, List< ? > > ) object;

             objectDetails = specialDTO.getDetailVOs();
             objectHeader = specialDTO.getHeaderVO();

             // 国际化传值   
             ( ( ActionForm ) objectHeader ).reset( null, request );
          }*/

         // 摄取header中值
         if ( length == 1 )
         {
            // 含“.”的fieldName是自定义字段
            if ( fieldName.contains( "." ) )
               value = getSpecialListDefineColumnValue( objectHeader, fieldName, request );
            else
               value = ( String ) KANUtil.getValue( objectHeader, fieldName );
         }
         // 摄取detail中值
         else if ( length > 1 )
         {
            // 获取当前itemId
            final String itemId = fieldName.split( "_" )[ 1 ];

            // 存在objectDetails
            if ( objectDetails != null && objectDetails.size() > 0 )
            {
               for ( Object objectDetail : objectDetails )
               {
                  // 找到科目对应的objectDetail
                  if ( KANUtil.filterEmpty( itemId ) != null && KANUtil.filterEmpty( ( String ) KANUtil.getValue( objectDetail, "itemId" ) ) != null
                        && ( ( String ) KANUtil.getValue( objectDetail, "itemId" ) ).equals( itemId ) )
                  {
                     // 如果length长度“2”
                     if ( length == 2 )
                     {
                        value = ( String ) KANUtil.getValue( objectDetail, "amountPersonal" );
                     }
                     // 如果length长度“3”
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

         // 例外字符传处理
         if ( value == null || value.trim().equalsIgnoreCase( "null" ) )
         {
            value = "";
         }

         // 如果用户设置日期格式化
         if ( KANUtil.filterEmpty( listDetailVO.getDatetimeFormat(), "0" ) != null && KANUtil.filterEmpty( value ) != null )
         {
            value = new SimpleDateFormat( listDetailVO.getDecodeDatetimeFormatTemp() ).format( KANUtil.createDate( value ) );
         }

         // 如果用户设置小数格式化
         if ( KANUtil.filterEmpty( listDetailVO.getAccuracy(), "0" ) != null && KANUtil.filterEmpty( value ) != null
               && value.replace( ".", "" ).replace( "-", "" ).matches( "[0-9]*" ) )
         {
            // 默认为四舍五入
            int roundType = BigDecimal.ROUND_HALF_UP;

            if ( listDetailVO.getRound() != null )
            {
               // 截取
               if ( listDetailVO.getRound().trim().equals( "2" ) )
               {
                  roundType = BigDecimal.ROUND_DOWN;
               }
               // 带符号向上进位，正数进位，负数截取
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

   // 合并从表DetailVO
   @SuppressWarnings("unchecked")
   private static List< ListDetailVO > mergeListDetailVO( final HttpServletRequest request, final List< ListDTO > listDTOs ) throws KANException
   {
      try
      {
         // 初始化PagedListHolder
         final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );
         // 初始化所有科目
         final List< ItemVO > items = ( List< ItemVO > ) ( ( PagedListHolder ) request.getAttribute( "pagedListHolder" ) ).getAdditionalObject();

         if ( items != null && items.size() > 0 )
         {
            return fetchListDetailVOByItemVO( items, listDTOs );
         }
         else
         {
            // 存在PagedListHolder
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

   // itemId存在于items
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

   // 这个方法是jack的
   private static List< ListDetailVO > fetchListDetailVOByItemVO( final List< ItemVO > items, final List< ListDTO > listDTOs )
   {
      // 初始化ListDetailVO List
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
   // 这个是siuvan的
   private static List< ListDetailVO > fetchListDetailVO( final SpecialDTO< Object, List< ? > > specialDTO, final List< ListDTO > listDTOs ) throws KANException
   {
      // 初始化ListDetailVO List
      final List< ListDetailVO > listDetailVOs = new ArrayList< ListDetailVO >();

      // 标识是否显示
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

   // 内部类 - 排序columnIndex
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
      // 添加Log Start跟踪
      LogFactory.getLog( "generateBranchExcel" ).info( "Excel Generate (" + BaseAction.getAccountId( request, null ) + ", " + BaseAction.getUsername( request, null ) + ") Start." );

      // 初始化PagedListHolder
      final List< StaffDTO > staffDTOs = ( List< StaffDTO > ) request.getAttribute( "staffDTOs" );

      final boolean isPosition = new Boolean( "isPosition".equalsIgnoreCase( ( String ) request.getAttribute( "isPosition" ) ) );

      // 初始化工作薄
      final XSSFWorkbook workbook = new XSSFWorkbook();

      if ( staffDTOs != null && staffDTOs.size() > 0 )
      {
         // 创建字体
         final Font font = workbook.createFont();
         font.setFontName( "Calibri" );
         font.setFontHeightInPoints( ( short ) 11 );

         // 创建单元格样式
         final CellStyle cellStyleLeft = workbook.createCellStyle();
         cellStyleLeft.setFont( font );
         cellStyleLeft.setAlignment( XSSFCellStyle.ALIGN_LEFT );

         // 创建单元格样式
         final CellStyle cellStyleCenter = workbook.createCellStyle();
         cellStyleCenter.setFont( font );
         cellStyleCenter.setAlignment( XSSFCellStyle.ALIGN_CENTER );

         // 创建单元格样式
         final CellStyle cellStyleRight = workbook.createCellStyle();
         cellStyleRight.setFont( font );
         cellStyleRight.setAlignment( XSSFCellStyle.ALIGN_RIGHT );

         // 创建表格
         final Sheet sheet = workbook.createSheet( "sheet1" );
         // 设置表格默认列宽度为15个字节
         sheet.setDefaultColumnWidth( 15 );

         // 生成Excel Header Row
         final Row rowHeader = sheet.createRow( 0 );

         // 表头
         int headerColumnIndex = 0;
         Cell cell = rowHeader.createCell( headerColumnIndex );
         cell.setCellValue( "员工姓名" );
         cell.setCellStyle( cellStyleLeft );
         headerColumnIndex++;

         cell = rowHeader.createCell( headerColumnIndex );
         cell.setCellValue( "身份证号" );
         cell.setCellStyle( cellStyleLeft );
         headerColumnIndex++;

         cell = rowHeader.createCell( headerColumnIndex );
         cell.setCellValue( "联系电话" );
         cell.setCellStyle( cellStyleLeft );
         headerColumnIndex++;

         cell = rowHeader.createCell( headerColumnIndex );
         cell.setCellValue( "联系邮箱" );
         cell.setCellStyle( cellStyleLeft );
         headerColumnIndex++;

         if ( isPosition )
         {
            // 如果是导出职位
            cell = rowHeader.createCell( headerColumnIndex );
            final int longestPosition = getLongestBranch( staffDTOs, isPosition );
            sheet.addMergedRegion( new CellRangeAddress( 0, 0, headerColumnIndex, headerColumnIndex + ( longestPosition == 0 ? longestPosition : longestPosition - 1 ) ) );
            cell.setCellValue( "职位" );
            cell.setCellStyle( cellStyleLeft );
            headerColumnIndex++;
         }
         else
         {
            // 如果是导出部门
            cell = rowHeader.createCell( headerColumnIndex );
            cell.setCellValue( "职位" );
            cell.setCellStyle( cellStyleLeft );
            headerColumnIndex++;

            cell = rowHeader.createCell( headerColumnIndex );
            final int longestBranch = getLongestBranch( staffDTOs, isPosition );
            sheet.addMergedRegion( new CellRangeAddress( 0, 0, headerColumnIndex, headerColumnIndex + ( longestBranch == 0 ? longestBranch : longestBranch - 1 ) ) );
            cell.setCellValue( "部门" );
            cell.setCellStyle( cellStyleLeft );
            headerColumnIndex++;
         }
         // 表信息
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
                  // 职位s
                  final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) );
                  // 第一个是自己的职位，只用写自己
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
                  // 职位
                  cell = rowBody.createCell( bodyColumnIndex );
                  cell.setCellValue( positionDTO.getPositionVO().getTitleZH() );
                  cell.setCellStyle( cellStyleLeft );
                  bodyColumnIndex++;
                  // 部门s
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

      // 添加Log End跟踪
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
                  result += "1".equals( positionStaffRelationVO.getStaffType() ) ? "/" : "（代）,";
               }
            }
         }
      }

      return KANUtil.filterEmpty( result ) == null ? "" : result.substring( 0, result.length() - 1 );
   }

   // 是否使用固定列
   private static boolean useFixColumn( final String javaObjectName )
   {
      if ( javaObjectName.equals( PayslipViewAction.JAVA_OBJECT_NAME ) || javaObjectName.equals( PayslipViewAction.JAVA_OBJECT_NAME_REPORT )
            || javaObjectName.equals( PayslipViewAction.JAVA_OBJECT_NAME_REPORT_ICLICK ) )
         return true;
      return false;
   }

   // 使用JAVA对象的特殊列表，获取自定义字段值
   private static String getSpecialListDefineColumnValue( final Object objectHeader, final String fieldName, final HttpServletRequest request ) throws KANException
   {
      String key = fieldName.split( "\\." )[ 1 ];
      // 获取员工自定义字段
      final String employeeRemark1 = ( String ) KANUtil.getValue( objectHeader, "employeeRemark1" );
      // 获取劳动合同自定义字段
      final String contractRemark1 = ( String ) KANUtil.getValue( objectHeader, "contractRemark1" );
      // 初始化employeeReportVO
      final EmployeeReportVO employeeReportVO = new EmployeeReportVO();
      employeeReportVO.reset( null, request );
      employeeReportVO.setRemark1( employeeRemark1 );
      employeeReportVO.setContractRemark1( contractRemark1 );

      // 自定义Map形式
      final Map< String, Object > columnMap = employeeReportVO.getDynaColumns();
      return columnMap.get( key ) == null ? "" : columnMap.get( key ).toString();
   }

   @SuppressWarnings("unchecked")
   // iclick 的人员信息报表 附加工资信息
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

               // 初始化Excel列宽，针对用户定义固定列宽的情况
               if ( listDetailVO.getColumnWidthType() != null && listDetailVO.getColumnWidthType().trim().equals( "2" ) && listDetailVO.getColumnWidth() != null
                     && listDetailVO.getColumnWidth().trim().matches( "[0-9]*" ) )
               {
                  // 换算Excel列宽并取整
                  final BigDecimal columnWidth = new BigDecimal( Integer.valueOf( listDetailVO.getColumnWidth() ) * 30 ).setScale( 0, BigDecimal.ROUND_HALF_UP );
                  // 设置Excel列宽
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
               // 设置单元格对齐
               cell.setCellStyle( cellStyleLeft );
               // 设置单元格值
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
