package com.kan.base.web.renders.util;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.define.SearchDTO;
import com.kan.base.domain.define.SearchDetailVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.system.ModuleDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.web.actions.biz.employee.EmployeeSecurityAction;

public class SearchRender
{

   public static String generateSpecialSearch( final HttpServletRequest request, final String accessAction, final String javaObjectName, final String formName )
         throws KANException
   {
      // 初始化corpId
      final String corpId = KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) );

      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();

      // 初始化ModuleDTO
      final ModuleDTO moduleDTO = KANConstants.getModuleDTOByAccessAction( accessAction );

      // 获取SearchDTO
      final SearchDTO searchDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getSearchDTOByJavaObjectName( javaObjectName, corpId );

      // 获取ListDTO
      final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, corpId );

      // 初始化PagedListHolder
      final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );

      // 初始化ActionForm
      final Object actionForm = ( Object ) request.getAttribute( formName );

      // 如果当前SearchHeaderVO不为空
      if ( searchDTO != null && searchDTO.getSearchHeaderVO() != null )
      {
         String searchName = "";
         if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            searchName = searchDTO.getSearchHeaderVO().getNameZH();
         }
         else
         {
            searchName = searchDTO.getSearchHeaderVO().getNameEN();
         }

         // 初始化搜索对应的链接
         String listAction = "";
         if ( moduleDTO != null && moduleDTO.getModuleVO() != null && moduleDTO.getModuleVO().getListAction() != null && !moduleDTO.getModuleVO().getListAction().equals( "" ) )
         {
            listAction = moduleDTO.getModuleVO().getListAction();
         }

         rs.append( "<div class=\"box searchForm toggableForm\" id=\"Search-Information\">" );
         rs.append( "<div class=\"head\">" );
         rs.append( "<label>" + searchName + "</label>" );
         rs.append( "</div>" );
         rs.append( "<a class=\"toggle tiptip\" title=\"" + KANUtil.getProperty( request.getLocale(), "public.hideoptions" ) + "\">&gt;</a>" );
         // SubAction处理
         String subAction = ( String ) KANUtil.getValue( actionForm, "subAction" );
         if ( subAction == null || subAction.trim().equalsIgnoreCase( "null" ) )
         {
            subAction = "";
         }
         // 如果存在搜索字段
         if ( searchDTO.getSearchDetailVOs() != null && searchDTO.getSearchDetailVOs().size() > 0 )
         {
            // 初始化Search Div样式
            String searchDivStyle = "";

            // 如果不是搜索优先，则隐藏Search Div
            if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.isSearchFirst() )
            {
               searchDivStyle = " style=\"display: none;\" ";
            }
            rs.append( "<div id=\"searchDiv\" class=\"inner\" " + searchDivStyle + ">" );
            rs.append( "<div class=\"top\"> " );
            rs.append( "<input type=\"button\" id=\"searchBtn\" name=\"searchBtn\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.search" )
                  + "\" onclick=\"submitForm('list_form', 'searchObject', null, null, null, null);\" />" );
            rs.append( "<input type=\"button\" class=\"reset\" id=\"resetBtn\" name=\"resetBtn\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.reset" )
                  + "\" onclick=\"resetForm();\" />" );
            rs.append( "</div>" );
            rs.append( "<form name=\"" + formName + "\" method=\"post\" action=\"" + listAction + "\" class=\"list_form\">" );
            rs.append( "<input type=\"hidden\" id=\"id\" name=\"id\" value=\"\" /> " );
            rs.append( "<input type=\"hidden\" id=\"sortColumn\" name=\"sortColumn\" value=\"" + pagedListHolder.getSortColumn() + "\" /> " );
            rs.append( "<input type=\"hidden\" id=\"sortOrder\" name=\"sortOrder\" value=\"" + pagedListHolder.getSortOrder() + "\" />" );
            rs.append( "<input type=\"hidden\" id=\"page\" name=\"page\" value=\"" + pagedListHolder.getPage() + "\" />" );
            rs.append( "<input type=\"hidden\" id=\"selectedIds\" name=\"selectedIds\" value=\"" + pagedListHolder.getSelectedIds() + "\" />" );
            rs.append( "<input type=\"hidden\" id=\"subAction\" name=\"subAction\" value=\"" + subAction + "\" />" );
            rs.append( "<fieldset>" );
            rs.append( "<ol class=\"auto\">" );

            // 非InHouse 或具有HR职能
            if ( !BaseAction.getRole( request, null ).equals( "2" ) || BaseAction.isHRFunction( request, null ) )
            {
               // 遍历搜索字段
               for ( SearchDetailVO searchDetailVO : searchDTO.getSearchDetailVOs() )
               {
                  // 如果当前字段为显示
                  if ( searchDetailVO.getDisplay() == null || !searchDetailVO.getDisplay().trim().equals( "2" ) )
                  {
                     // 初始化propertyName
                     final String propertyName = searchDetailVO.getPropertyName();

                     if ( isShowColumnId( accessAction, propertyName, request ) )
                     {
                        continue;
                     }

                     // 初始化Clomun Name
                     String columnName = "";
                     if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
                     {
                        columnName = searchDetailVO.getNameZH();
                     }
                     else
                     {
                        columnName = searchDetailVO.getNameEN();
                     }

                     // 初始化控件的ID和Name
                     String id_name = propertyName;

                     // 初始化当前Column的Value
                     String value = "";
                     if ( actionForm != null )
                     {
                        value = ( String ) KANUtil.getValue( actionForm, id_name );
                     }

                     if ( value == null || value.trim().equalsIgnoreCase( "null" ) )
                     {
                        value = "";
                     }

                     rs.append( "<li>" );
                     rs.append( "<label>" + columnName + "</label>" );

                     rs.append( "<input type=\"text\" id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + id_name + "\"  value=\"" + value + "\" />" );
                     rs.append( "</li>" );
                  }
               }
            }

            // 获取月份
            //            final List< MappingVO > monthlys = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getLast12Months( request.getLocale().getLanguage() );
            //
            //            rs.append( "<li>" );
            //            rs.append( "<label>月份</label>" );
            //            rs.append( "<select id=\"monthly\" name=\"monthly\" class=\"monthly\">" );
            //            if ( monthlys != null && monthlys.size() > 0 )
            //            {
            //               for ( MappingVO mappingVO : monthlys )
            //               {
            //                  // 是否需要选中
            //                  String selected = "";
            //                  String value = ( String ) KANUtil.getValue( actionForm, "monthly" );
            //                  if ( value != null && value.trim().equals( mappingVO.getMappingId() ) )
            //                  {
            //                     selected = " selected ";
            //                  }
            //                  rs.append( "<option value=\"" + mappingVO.getMappingId() + "\" " + selected + ">" + mappingVO.getMappingValue() + "</option>" );
            //               }
            //            }
            //            rs.append( "</select>" );
            //            rs.append( "</li>" );

            // 特殊下拉框
            rs.append( "<div id=\"special_select\">" );

            rs.append( "</div>" );
            rs.append( "</ol>" );
            rs.append( "</fieldset>" );
            rs.append( "</form>" );
            rs.append( "</div>" );
         }else{
            rs.append( "<form name=\"" + formName + "\" method=\"post\" action=\"" + listAction + "\" class=\"list_form\">" );
            rs.append( "<input type=\"hidden\" id=\"id\" name=\"id\" value=\"\" /> " );
            rs.append( "<input type=\"hidden\" id=\"sortColumn\" name=\"sortColumn\" value=\"" + pagedListHolder.getSortColumn() + "\" /> " );
            rs.append( "<input type=\"hidden\" id=\"sortOrder\" name=\"sortOrder\" value=\"" + pagedListHolder.getSortOrder() + "\" />" );
            rs.append( "<input type=\"hidden\" id=\"page\" name=\"page\" value=\"" + pagedListHolder.getPage() + "\" />" );
            rs.append( "<input type=\"hidden\" id=\"selectedIds\" name=\"selectedIds\" value=\"" + pagedListHolder.getSelectedIds() + "\" />" );
            rs.append( "<input type=\"hidden\" id=\"subAction\" name=\"subAction\" value=\"" + subAction + "\" />" );
            rs.append( "</form>" );
         }
         rs.append( "</div>" );
      }

      return rs.toString();
   }

   @SuppressWarnings("unchecked")
   public static String generateSearch( final HttpServletRequest request, final String accessAction, final String formName ) throws KANException
   {
      //雇员登陆不显示查询列表
      if ( KANConstants.ROLE_EMPLOYEE.equals( EmployeeSecurityAction.getRole( request, null ) ) )
      {
         return "";
      }
      // 初始化ClientId
      final String corpId = BaseAction.getCorpId( request, null );

      // 获取当前需要生成控件管理界面的TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );
      // 初始化ModuleDTO
      final ModuleDTO moduleDTO = KANConstants.getModuleDTOByAccessAction( accessAction );
      // 初始化PagedListHolder
      final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );
      // 初始化ActionForm
      final Object actionForm = ( Object ) request.getAttribute( formName );
      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();

      // 初始化Jason对象 - 含有当前字段的值
      JSONObject jsonObject = null;
      try
      {
         // 初始化JSON String
         String jsonString = "{"+( String ) KANUtil.getValue( actionForm, "remark1" )+"}";

         if ( KANUtil.filterEmpty( jsonString ,"{null}" ) != null )
         {
            // 去除%
            jsonString = jsonString.replaceAll( "%", "" );

            jsonObject = JSONObject.fromObject( URLDecoder.decode( ( String ) jsonString, "GBK" ) );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 初始化ListDTO
      final ListDTO listDTO = tableDTO.getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );

      // 遍历Search 
      if ( tableDTO != null && listDTO != null && listDTO.getSearchDTO() != null )
      {
         // 初始化SearchDTO
         final SearchDTO searchDTO = listDTO.getSearchDTO();

         // 如果当前SearchHeaderVO不为空
         if ( searchDTO.getSearchHeaderVO() != null )
         {
            // 初始化Search Name
            String searchName = "";
            if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
            {
               searchName = searchDTO.getSearchHeaderVO().getNameZH();
            }
            else
            {
               searchName = searchDTO.getSearchHeaderVO().getNameEN();
            }

            // 初始化搜索对应的链接
            String listAction = "";
            if ( moduleDTO != null && moduleDTO.getModuleVO() != null && moduleDTO.getModuleVO().getListAction() != null && !moduleDTO.getModuleVO().getListAction().equals( "" ) )
            {
               listAction = moduleDTO.getModuleVO().getListAction();
            }
            // SubAction处理
            String subAction = ( String ) KANUtil.getValue( actionForm, "subAction" );
            if ( subAction == null || subAction.trim().equalsIgnoreCase( "null" ) )
            {
               subAction = "";
            }

            rs.append( "<div class=\"box searchForm toggableForm\" id=\"Search-Information\">" );
            rs.append( "<div class=\"head\">" );
            rs.append( "<label>" + searchName + "</label>" );
            rs.append( "</div>" );
            rs.append( "<a class=\"toggle tiptip\" title=\"" + KANUtil.getProperty( request.getLocale(), "public.hideoptions" ) + "\">&gt;</a>" );

            // 如果存在搜索字段
            if ( searchDTO.getSearchDetailVOs() != null && searchDTO.getSearchDetailVOs().size() > 0 )
            {
               // 初始化Search Div样式
               String searchDivStyle = "";

               // 如果不是搜索优先，则隐藏Search Div
               if ( !listDTO.isSearchFirst() )
               {
                  searchDivStyle = " style=\"display: none;\" ";
               }

              
               rs.append( "<div id=\"searchDiv\" class=\"inner\" " + searchDivStyle + ">" );
               rs.append( "<div class=\"top\"> " );
               rs.append( "<input type=\"button\" id=\"searchBtn\" name=\"searchBtn\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.search" )
                     + "\" onclick=\"submitForm('list_form', 'searchObject', null, null, null, null);\" />" );
               rs.append( "<input type=\"button\" class=\"reset\" id=\"resetBtn\" name=\"resetBtn\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.reset" )
                     + "\" onclick=\"resetForm();\" />" );
               rs.append( "</div>" );
               rs.append( "<form name=\"" + formName + "\" method=\"post\" action=\"" + listAction + "\" class=\"list_form\">" );
               rs.append( "<input type=\"hidden\" id=\"id\" name=\"id\" value=\"\" /> " );
               rs.append( "<input type=\"hidden\" id=\"sortColumn\" name=\"sortColumn\" value=\"" + pagedListHolder.getSortColumn() + "\" /> " );
               rs.append( "<input type=\"hidden\" id=\"sortOrder\" name=\"sortOrder\" value=\"" + pagedListHolder.getSortOrder() + "\" />" );
               rs.append( "<input type=\"hidden\" id=\"page\" name=\"page\" value=\"" + pagedListHolder.getPage() + "\" />" );
               rs.append( "<input type=\"hidden\" id=\"selectedIds\" name=\"selectedIds\" value=\"" + pagedListHolder.getSelectedIds() + "\" />" );
               rs.append( "<input type=\"hidden\" id=\"subAction\" name=\"subAction\" value=\"" + subAction + "\" />" );
               rs.append( "<fieldset>" );
               rs.append( "<ol class=\"auto\">" );

               // 遍历搜索字段
               for ( SearchDetailVO searchDetailVO : searchDTO.getSearchDetailVOs() )
               {
                  if ( searchDetailVO.getDisplay() == null || !searchDetailVO.getDisplay().trim().equals( "2" ) )
                  {
                     // 初始化字段对象
                     final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( searchDetailVO.getColumnId() );

                     if ( columnVO == null )
                     {
                        continue;
                     }

                     // 初始化Clomun Name
                     String columnName = "";
                     if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
                     {
                        columnName = searchDetailVO.getNameZH();
                     }
                     else
                     {
                        columnName = searchDetailVO.getNameEN();
                     }

                     // 初始化控件的ID和Name
                     String id_name = "";
                     if ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
                     {
                        id_name = columnVO.getNameDB();
                     }
                     else
                     {
                        id_name = accessAction + "_" + columnVO.getNameDB();
                     }

                     if ( isShowColumnId( accessAction, id_name, request ) )
                     {
                        continue;
                     }

                     // 初始化控件的Length
                     String maxLenght = "";
                     if ( columnVO.getValidateLengthMax() != null && !columnVO.getValidateLengthMax().trim().equals( "" ) && !columnVO.getValidateLengthMax().trim().equals( "0" ) )
                     {
                        maxLenght = columnVO.getValidateLengthMax();
                     }

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

                                 if ( KANUtil.filterEmpty( corpId ) != null )
                                 {
                                    parameters = new String[] { request.getLocale().getLanguage(), corpId };
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
                        mappingVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getColumnOptionDTOByOptionHeaderId( columnVO.getOptionValue() ).getOptions( request.getLocale().getLanguage() );
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

                     // 当前Input的Style
                     String styleInput = "";
                     if ( columnVO.getCssStyle() != null && !columnVO.getCssStyle().trim().equals( "" ) )
                     {
                        styleInput = columnVO.getCssStyle();
                     }

                     // 初始化当前Column的Value
                     String value = "";
                     if ( actionForm != null )
                     {
                        if ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
                        {
                           value = ( String ) KANUtil.getValue( actionForm, id_name );
                        }
                        else
                        {
                           if ( jsonObject != null && jsonObject.containsKey( columnVO.getNameDB() ) )
                           {
                              value = jsonObject.getString( columnVO.getNameDB() );
                           }
                        }
                     }
                     if ( value == null || value.trim().equalsIgnoreCase( "null" ) )
                     {
                        value = "";
                     }

                     rs.append( "<li>" );
                     rs.append( "<label>" + columnName + "</label>" );

                     // 日期型Column的处理
                     String datetime = "";
                     String dateClass = "";
                     if ( columnVO.getValueType() != null && columnVO.getValueType().equals( "3" ) )
                     {
                        datetime = " onFocus=\"WdatePicker({lang:'auto'})\" ";
                        dateClass = " Wdate ";
                     }

                     // 如果当前字段是文本框
                     if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "1" ) )
                     {
                        rs.append( "<input type=\"text\" id=\"" + id_name + "\" name=\"" + id_name + "\" maxlength=\"" + maxLenght + "\" class=\"" + accessAction + "_" + id_name
                              + dateClass + "\" style=\"" + styleInput + "\" value=\"" + value + "\" " + datetime + "/>" );
                     }
                     // 如果当前字段是下拉框
                     else if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "2" ) )
                     {
                        rs.append( "<select id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + accessAction + "_" + id_name + "\" style=\"" + styleInput + "\">" );

                        if ( mappingVOs != null && mappingVOs.size() > 0 )
                        {
                           for ( MappingVO mappingVO : mappingVOs )
                           {
                              // 是否需要选中
                              String selected = "";
                              if ( value != null && value.trim().equals( mappingVO.getMappingId() ) )
                              {
                                 selected = " selected ";
                              }
                              rs.append( "<option id=\"option_" + accessAction + "_" + id_name + "_" + mappingVO.getMappingId() + "\" value=\"" + mappingVO.getMappingId() + "\" "
                                    + selected + ">" + mappingVO.getMappingValue() + "</option>" );
                           }
                        }

                        rs.append( "</select>" );
                     }
                     // 如果当前字段是单选框
                     else if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "3" ) )
                     {
                        if ( mappingVOs != null && mappingVOs.size() > 0 )
                        {
                           rs.append( "<span>" );
                           for ( MappingVO mappingVO : mappingVOs )
                           {
                              // 是否需要选中
                              String checked = "";
                              if ( value != null && value.trim().equals( mappingVO.getMappingId() ) )
                              {
                                 checked = "checked ";
                              }
                              rs.append( "<input type=\"radio\" id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + accessAction + "_" + id_name + "\" style=\""
                                    + styleInput + "\" value=\"" + value + "\" " + checked + " />" + mappingVO.getMappingValue() + " &nbsp; " );
                           }
                           rs.append( "</span>" );
                        }
                     }
                     // 如果当前字段是复选框
                     else if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "4" ) )
                     {
                        if ( mappingVOs != null && mappingVOs.size() > 0 )
                        {
                           rs.append( "<span>" );
                           for ( MappingVO mappingVO : mappingVOs )
                           {
                              // 是否需要选中
                              String checked = "";
                              if ( value != null && value.trim().equals( mappingVO.getMappingId() ) )
                              {
                                 checked = "checked ";
                              }
                              rs.append( "<input type=\"checkbox\" id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + accessAction + "_" + id_name + "\" style=\""
                                    + styleInput + "\" value=\"" + value + "\" " + checked + " />" + mappingVO.getMappingValue() + " &nbsp; " );
                           }
                           rs.append( "</span>" );
                        }

                     }
                     // 如果当前字段是文本域
                     else if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "5" ) )
                     {
                        rs.append( "<textarea id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + accessAction + "_" + id_name + "\" style=\"" + styleInput + "\" />"
                              + value + "</textarea>" );
                     }

                     rs.append( "</li>" );
                  }
               }
               rs.append( "</ol>" );
               rs.append( "</fieldset>" );
               rs.append( "</form>" );
               rs.append( "</div>" );
            }else{
               rs.append( "<form name=\"" + formName + "\" method=\"post\" action=\"" + listAction + "\" class=\"list_form\">" );
               rs.append( "<input type=\"hidden\" id=\"id\" name=\"id\" value=\"\" /> " );
               rs.append( "<input type=\"hidden\" id=\"sortColumn\" name=\"sortColumn\" value=\"" + pagedListHolder.getSortColumn() + "\" /> " );
               rs.append( "<input type=\"hidden\" id=\"sortOrder\" name=\"sortOrder\" value=\"" + pagedListHolder.getSortOrder() + "\" />" );
               rs.append( "<input type=\"hidden\" id=\"page\" name=\"page\" value=\"" + pagedListHolder.getPage() + "\" />" );
               rs.append( "<input type=\"hidden\" id=\"selectedIds\" name=\"selectedIds\" value=\"" + pagedListHolder.getSelectedIds() + "\" />" );
               rs.append( "<input type=\"hidden\" id=\"subAction\" name=\"subAction\" value=\"" + subAction + "\" />" );
               rs.append( "</form>" );
            }
            rs.append( "</div>" );
         }
      }

      return rs.toString();
   }

   public static String generateSpecialSearchReset( final HttpServletRequest request, final String javaObjectName ) throws KANException
   {
      // 初始化corpId
      final String corpId = KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) );

      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();

      // 初始化SearchDTO
      final SearchDTO searchDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getSearchDTOByJavaObjectName( javaObjectName, corpId );
      rs.append( "function resetForm(){" );

      // 遍历Search 
      if ( searchDTO != null && ( !BaseAction.getRole( request, null ).equals( "2" ) || BaseAction.isHRFunction( request, null ) ) )
      {
         // 如果存在搜索字段
         if ( searchDTO.getSearchDetailVOs() != null && searchDTO.getSearchDetailVOs().size() > 0 )
         {
            for ( SearchDetailVO searchDetailVO : searchDTO.getSearchDetailVOs() )
            {
               rs.append( "$('." + searchDetailVO.getPropertyName() + "').val('');" );
            }
         }

      }
      rs.append( "};" );

      return rs.toString();
   }

   public static String generateSearchReset( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      // 获取当前需要生成控件管理界面的TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );
      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();
      // 初始化ListDTO
      final ListDTO listDTO = tableDTO.getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );

      // 遍历Search 
      if ( tableDTO != null && listDTO != null && listDTO.getSearchDTO() != null )
      {
         // 初始化SearchDTO
         final SearchDTO searchDTO = listDTO.getSearchDTO();

         // 如果存在搜索字段
         if ( searchDTO.getSearchDetailVOs() != null && searchDTO.getSearchDetailVOs().size() > 0 )
         {
            rs.append( "function resetForm(){" );
            // 遍历搜索字段
            for ( SearchDetailVO searchDetailVO : searchDTO.getSearchDetailVOs() )
            {
               // 初始化字段对象
               final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( searchDetailVO.getColumnId() );

               // 初始化控件的ID和Name
               String id_name = "";
               if ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
               {
                  id_name = columnVO.getNameDB();
               }
               else
               {
                  id_name = accessAction + "_" + columnVO.getNameDB();
               }

               // 初始化Reset的Value
               String value = "";

               // 文本框和文本域Reset为空，其他Reset为“0”
               if ( columnVO.getInputType() != null && ( columnVO.getInputType().trim().equals( "1" ) || columnVO.getInputType().trim().equals( "5" ) ) )
               {
                  value = "";
               }
               else
               {
                  value = "0";
               }
               rs.append( "$('." + accessAction + "_" + id_name + "').val('" + value + "');" );
            }
            rs.append( "};" );
         }
      }

      return rs.toString();
   }

   private static boolean isShowColumnId( final String accessAction, final String columnId, final HttpServletRequest request ) throws KANException
   {

      boolean returnValue = false;
      String[] clientAccessAction = new String[] { "HRO_BIZ_EMPLOYEE", "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT", "HRO_BIZ_ATTENDANCE_TIMESHEET_BATCH", "JAVA_OBJECT_SBBILL",
            "JAVA_OBJECT_SETTLEMENT", "JAVA_OBJECT_PAYSLIP", "HRO_BIZ_ATTENDANCE_TIMESHEET_REPORT_EXPORT", "HRO_CB_BILL" };
      String[] clientColumnName = new String[] { "CLIENTID", "CLIENTNO", "CLIENTNUMBER", "VENDORNAMEZH", "VENDORNAMEEH", "CLIENTNAMEZH", "CLIENTNAMEEN", "ORDERID", "ENTITYID",
            "BUSINESSTYPEID" };
      String[] employeeAccessAction = new String[] { "HRO_BIZ_EMPLOYEE", "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT" };
      String[] employeeColumnName = new String[] { "ORDERID", "CLIENTNO" };
      if ( StringUtils.equals( BaseAction.getRole( request, null ), KANConstants.ROLE_CLIENT ) )
      {
         if ( ArrayUtils.contains( clientAccessAction, accessAction ) && columnId != null && ArrayUtils.contains( clientColumnName, columnId.toUpperCase() ) )
         {
            returnValue = true;
         }
      }
      else if ( StringUtils.equals( BaseAction.getRole( request, null ), KANConstants.ROLE_EMPLOYEE ) )
      {
         if ( ArrayUtils.contains( employeeAccessAction, accessAction ) && columnId != null && ArrayUtils.contains( employeeColumnName, columnId ) )
         {
            returnValue = true;
         }
      }
      return returnValue;
   }
}
