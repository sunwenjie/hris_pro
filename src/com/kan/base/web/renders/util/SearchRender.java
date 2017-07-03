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
      // ��ʼ��corpId
      final String corpId = KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) );

      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();

      // ��ʼ��ModuleDTO
      final ModuleDTO moduleDTO = KANConstants.getModuleDTOByAccessAction( accessAction );

      // ��ȡSearchDTO
      final SearchDTO searchDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getSearchDTOByJavaObjectName( javaObjectName, corpId );

      // ��ȡListDTO
      final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, corpId );

      // ��ʼ��PagedListHolder
      final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );

      // ��ʼ��ActionForm
      final Object actionForm = ( Object ) request.getAttribute( formName );

      // �����ǰSearchHeaderVO��Ϊ��
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

         // ��ʼ��������Ӧ������
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
         // SubAction����
         String subAction = ( String ) KANUtil.getValue( actionForm, "subAction" );
         if ( subAction == null || subAction.trim().equalsIgnoreCase( "null" ) )
         {
            subAction = "";
         }
         // ������������ֶ�
         if ( searchDTO.getSearchDetailVOs() != null && searchDTO.getSearchDetailVOs().size() > 0 )
         {
            // ��ʼ��Search Div��ʽ
            String searchDivStyle = "";

            // ��������������ȣ�������Search Div
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

            // ��InHouse �����HRְ��
            if ( !BaseAction.getRole( request, null ).equals( "2" ) || BaseAction.isHRFunction( request, null ) )
            {
               // ���������ֶ�
               for ( SearchDetailVO searchDetailVO : searchDTO.getSearchDetailVOs() )
               {
                  // �����ǰ�ֶ�Ϊ��ʾ
                  if ( searchDetailVO.getDisplay() == null || !searchDetailVO.getDisplay().trim().equals( "2" ) )
                  {
                     // ��ʼ��propertyName
                     final String propertyName = searchDetailVO.getPropertyName();

                     if ( isShowColumnId( accessAction, propertyName, request ) )
                     {
                        continue;
                     }

                     // ��ʼ��Clomun Name
                     String columnName = "";
                     if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
                     {
                        columnName = searchDetailVO.getNameZH();
                     }
                     else
                     {
                        columnName = searchDetailVO.getNameEN();
                     }

                     // ��ʼ���ؼ���ID��Name
                     String id_name = propertyName;

                     // ��ʼ����ǰColumn��Value
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

            // ��ȡ�·�
            //            final List< MappingVO > monthlys = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getLast12Months( request.getLocale().getLanguage() );
            //
            //            rs.append( "<li>" );
            //            rs.append( "<label>�·�</label>" );
            //            rs.append( "<select id=\"monthly\" name=\"monthly\" class=\"monthly\">" );
            //            if ( monthlys != null && monthlys.size() > 0 )
            //            {
            //               for ( MappingVO mappingVO : monthlys )
            //               {
            //                  // �Ƿ���Ҫѡ��
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

            // ����������
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
      //��Ա��½����ʾ��ѯ�б�
      if ( KANConstants.ROLE_EMPLOYEE.equals( EmployeeSecurityAction.getRole( request, null ) ) )
      {
         return "";
      }
      // ��ʼ��ClientId
      final String corpId = BaseAction.getCorpId( request, null );

      // ��ȡ��ǰ��Ҫ���ɿؼ���������TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );
      // ��ʼ��ModuleDTO
      final ModuleDTO moduleDTO = KANConstants.getModuleDTOByAccessAction( accessAction );
      // ��ʼ��PagedListHolder
      final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );
      // ��ʼ��ActionForm
      final Object actionForm = ( Object ) request.getAttribute( formName );
      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();

      // ��ʼ��Jason���� - ���е�ǰ�ֶε�ֵ
      JSONObject jsonObject = null;
      try
      {
         // ��ʼ��JSON String
         String jsonString = "{"+( String ) KANUtil.getValue( actionForm, "remark1" )+"}";

         if ( KANUtil.filterEmpty( jsonString ,"{null}" ) != null )
         {
            // ȥ��%
            jsonString = jsonString.replaceAll( "%", "" );

            jsonObject = JSONObject.fromObject( URLDecoder.decode( ( String ) jsonString, "GBK" ) );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ʼ��ListDTO
      final ListDTO listDTO = tableDTO.getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );

      // ����Search 
      if ( tableDTO != null && listDTO != null && listDTO.getSearchDTO() != null )
      {
         // ��ʼ��SearchDTO
         final SearchDTO searchDTO = listDTO.getSearchDTO();

         // �����ǰSearchHeaderVO��Ϊ��
         if ( searchDTO.getSearchHeaderVO() != null )
         {
            // ��ʼ��Search Name
            String searchName = "";
            if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
            {
               searchName = searchDTO.getSearchHeaderVO().getNameZH();
            }
            else
            {
               searchName = searchDTO.getSearchHeaderVO().getNameEN();
            }

            // ��ʼ��������Ӧ������
            String listAction = "";
            if ( moduleDTO != null && moduleDTO.getModuleVO() != null && moduleDTO.getModuleVO().getListAction() != null && !moduleDTO.getModuleVO().getListAction().equals( "" ) )
            {
               listAction = moduleDTO.getModuleVO().getListAction();
            }
            // SubAction����
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

            // ������������ֶ�
            if ( searchDTO.getSearchDetailVOs() != null && searchDTO.getSearchDetailVOs().size() > 0 )
            {
               // ��ʼ��Search Div��ʽ
               String searchDivStyle = "";

               // ��������������ȣ�������Search Div
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

               // ���������ֶ�
               for ( SearchDetailVO searchDetailVO : searchDTO.getSearchDetailVOs() )
               {
                  if ( searchDetailVO.getDisplay() == null || !searchDetailVO.getDisplay().trim().equals( "2" ) )
                  {
                     // ��ʼ���ֶζ���
                     final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( searchDetailVO.getColumnId() );

                     if ( columnVO == null )
                     {
                        continue;
                     }

                     // ��ʼ��Clomun Name
                     String columnName = "";
                     if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
                     {
                        columnName = searchDetailVO.getNameZH();
                     }
                     else
                     {
                        columnName = searchDetailVO.getNameEN();
                     }

                     // ��ʼ���ؼ���ID��Name
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

                     // ��ʼ���ؼ���Length
                     String maxLenght = "";
                     if ( columnVO.getValidateLengthMax() != null && !columnVO.getValidateLengthMax().trim().equals( "" ) && !columnVO.getValidateLengthMax().trim().equals( "0" ) )
                     {
                        maxLenght = columnVO.getValidateLengthMax();
                     }

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

                                 if ( KANUtil.filterEmpty( corpId ) != null )
                                 {
                                    parameters = new String[] { request.getLocale().getLanguage(), corpId };
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

                     // ��ǰInput��Style
                     String styleInput = "";
                     if ( columnVO.getCssStyle() != null && !columnVO.getCssStyle().trim().equals( "" ) )
                     {
                        styleInput = columnVO.getCssStyle();
                     }

                     // ��ʼ����ǰColumn��Value
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

                     // ������Column�Ĵ���
                     String datetime = "";
                     String dateClass = "";
                     if ( columnVO.getValueType() != null && columnVO.getValueType().equals( "3" ) )
                     {
                        datetime = " onFocus=\"WdatePicker({lang:'auto'})\" ";
                        dateClass = " Wdate ";
                     }

                     // �����ǰ�ֶ����ı���
                     if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "1" ) )
                     {
                        rs.append( "<input type=\"text\" id=\"" + id_name + "\" name=\"" + id_name + "\" maxlength=\"" + maxLenght + "\" class=\"" + accessAction + "_" + id_name
                              + dateClass + "\" style=\"" + styleInput + "\" value=\"" + value + "\" " + datetime + "/>" );
                     }
                     // �����ǰ�ֶ���������
                     else if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "2" ) )
                     {
                        rs.append( "<select id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + accessAction + "_" + id_name + "\" style=\"" + styleInput + "\">" );

                        if ( mappingVOs != null && mappingVOs.size() > 0 )
                        {
                           for ( MappingVO mappingVO : mappingVOs )
                           {
                              // �Ƿ���Ҫѡ��
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
                     // �����ǰ�ֶ��ǵ�ѡ��
                     else if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "3" ) )
                     {
                        if ( mappingVOs != null && mappingVOs.size() > 0 )
                        {
                           rs.append( "<span>" );
                           for ( MappingVO mappingVO : mappingVOs )
                           {
                              // �Ƿ���Ҫѡ��
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
                     // �����ǰ�ֶ��Ǹ�ѡ��
                     else if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "4" ) )
                     {
                        if ( mappingVOs != null && mappingVOs.size() > 0 )
                        {
                           rs.append( "<span>" );
                           for ( MappingVO mappingVO : mappingVOs )
                           {
                              // �Ƿ���Ҫѡ��
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
                     // �����ǰ�ֶ����ı���
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
      // ��ʼ��corpId
      final String corpId = KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) );

      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();

      // ��ʼ��SearchDTO
      final SearchDTO searchDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getSearchDTOByJavaObjectName( javaObjectName, corpId );
      rs.append( "function resetForm(){" );

      // ����Search 
      if ( searchDTO != null && ( !BaseAction.getRole( request, null ).equals( "2" ) || BaseAction.isHRFunction( request, null ) ) )
      {
         // ������������ֶ�
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
      // ��ȡ��ǰ��Ҫ���ɿؼ���������TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );
      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();
      // ��ʼ��ListDTO
      final ListDTO listDTO = tableDTO.getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );

      // ����Search 
      if ( tableDTO != null && listDTO != null && listDTO.getSearchDTO() != null )
      {
         // ��ʼ��SearchDTO
         final SearchDTO searchDTO = listDTO.getSearchDTO();

         // ������������ֶ�
         if ( searchDTO.getSearchDetailVOs() != null && searchDTO.getSearchDetailVOs().size() > 0 )
         {
            rs.append( "function resetForm(){" );
            // ���������ֶ�
            for ( SearchDetailVO searchDetailVO : searchDTO.getSearchDetailVOs() )
            {
               // ��ʼ���ֶζ���
               final ColumnVO columnVO = tableDTO.getColumnVOByColumnId( searchDetailVO.getColumnId() );

               // ��ʼ���ؼ���ID��Name
               String id_name = "";
               if ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
               {
                  id_name = columnVO.getNameDB();
               }
               else
               {
                  id_name = accessAction + "_" + columnVO.getNameDB();
               }

               // ��ʼ��Reset��Value
               String value = "";

               // �ı�����ı���ResetΪ�գ�����ResetΪ��0��
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
