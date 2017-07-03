package com.kan.base.web.renders.util;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnGroupDTO;
import com.kan.base.domain.define.ColumnGroupVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.OptionDTO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.system.ModuleDTO;
import com.kan.base.tag.AuthConstants;
import com.kan.base.tag.AuthUtils;
import com.kan.base.util.CachedUtil;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.web.actions.biz.employee.EmployeeContractAction;

/**
 * Render for New or Modify Page
 * 
 * @author Kevin
 */
public class ManageRender
{
   @SuppressWarnings("unchecked")
   public static String generateManage( final HttpServletRequest request, final String accessAction, final String formName, final boolean containsSystemColumns )
         throws KANException
   {
      // ��ʼ��corpId
      final String corpId = BaseAction.getCorpId( request, null );

      // ��ȡ��ǰ��Ҫ���ɿؼ���������TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );

      // ��ʼ��ModuleDTO
      final ModuleDTO moduleDTO = KANConstants.getModuleDTOByAccessAction( accessAction );

      // ��ʼ��ActionForm
      final Object actionForm = ( Object ) request.getAttribute( formName );

      // ��ʼ��Jason���� - ���е�ǰ�ֶε�ֵ
      JSONObject jsonObject = null;
      try
      {
         // ��Action Form��Remark1�ֶλ�ȡJason�ַ���
         if ( actionForm != null )
         {
            final Object jsonString = KANUtil.getValue( actionForm, "remark1" );
            if ( jsonString != null && !( ( String ) jsonString ).trim().equals( "" ) )
            {
               jsonObject = JSONObject.fromObject( URLDecoder.decode( ( String ) jsonString, "GBK" ).replace( "[{", "{" ).replace( "}]", "}" ) );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();
      final String message = ( String ) request.getAttribute( BaseAction.MESSAGE );
      final String messageClass = ( String ) request.getAttribute( BaseAction.MESSAGE_CLASS );

      // ����Column Group
      if ( tableDTO != null && tableDTO.getColumnGroupDTOs() != null && tableDTO.getColumnGroupDTOs().size() > 0 )
      {
         // ��ʼ��Table Name
         String tableName = "";
         if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            tableName = tableDTO.getTableVO().getNameZH();
         }
         else
         {
            tableName = tableDTO.getTableVO().getNameEN();
         }

         // ��ʼ������
         String addAction = "";
         if ( moduleDTO != null && moduleDTO.getModuleVO() != null && moduleDTO.getModuleVO().getNewAction() != null && !moduleDTO.getModuleVO().getNewAction().equals( "" ) )
         {
            addAction = moduleDTO.getModuleVO().getNewAction();
         }

         // ���ID���ܶ���
         final Object encodedIdObject = KANUtil.getValue( actionForm, "encodedId" );
         String encodedId = "";
         String idString = "";
         // ��������ȡֵ��Ϊ��
         if ( encodedIdObject != null )
         {
            encodedId = encodedIdObject.toString();

            // ��������ַ�����Ϊ��
            if ( encodedId != null && !encodedId.trim().equals( "" ) )
            {
               idString = "<label class=\"recordId\"> &nbsp; (ID: " + KANUtil.decodeString( encodedId ) + ")</label>";
            }
         }

         rs.append( "<div class=\"box\">" );
         rs.append( "<div class=\"head\">" );
         rs.append( "<label id=\"pageTitle\">" + tableName + "</label>" + idString );
         rs.append( "</div>" );
         rs.append( "<div class=\"inner\">" );
         rs.append( "<div id=\"messageWrapper\">" );

         if ( message != null && !message.trim().equals( "" ) )
         {
            rs.append( "<div class=\"message " + messageClass + " fadable\">" );
            rs.append( message );
            rs.append( "<a onclick=\"$('div.fadable').remove();\" class=\"messageCloseButton\">&nbsp;</a>" );
            rs.append( "</div>" );
         }

         rs.append( "</div>" );

         // ������Ͷ���ͬ����ʼ������div.top
         if ( EmployeeContractAction.getAccessAction( request, null ).equalsIgnoreCase( accessAction ) )
         {
            rs.append( "<div class=\"top\" style=\"display: none;\">" );
         }
         else
         {
            rs.append( "<div class=\"top\">" );
         }

         // ��ʼ��Sub Action
         final String subAction = ( String ) KANUtil.getValue( actionForm, "subAction" );
         // ��ʼ��Owner
         final String owner = ( String ) KANUtil.getValue( actionForm, "owner" );

         // �½��ͱ༭Ȩ�޿���
         if ( KANUtil.filterEmpty( subAction ) != null )
         {
            if ( KANUtil.filterEmpty( subAction ).trim().equals( "viewObject" ) && AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_NEW, "", request, null ) )
            {
               // ��ʼ����Ӱ�ť�����ӣ���ת�����ҳ�棩
               String toNewAction = "";

               if ( moduleDTO != null && moduleDTO.getModuleVO() != null && moduleDTO.getModuleVO().getToNewAction() != null
                     && !moduleDTO.getModuleVO().getToNewAction().equals( "" ) )
               {
                  toNewAction = moduleDTO.getModuleVO().getToNewAction();
               }

               // �����������
               if ( toNewAction.contains( "${" ) )
               {
                  String property = toNewAction.substring( toNewAction.indexOf( "{" ) + 1, toNewAction.indexOf( "}" ) );
                  String proprtyValue = ( String ) KANUtil.getValue( actionForm, property );
                  toNewAction = toNewAction.replace( "${" + property + "}", proprtyValue != null ? proprtyValue : "" );
               }

               rs.append( "<input type=\"button\" class=\"\" name=\"btnAdd\" id=\"btnAdd\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.add" )
                     + "\" onclick=\"link('" + toNewAction + "');\" /> " );
            }

            if ( ( KANUtil.filterEmpty( subAction ).trim().equals( "createObject" ) && AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_NEW, "", request, null ) )
                  || ( KANUtil.filterEmpty( subAction ).trim().equals( "viewObject" ) && AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_MODIFY, owner, request, null ) ) )
            {
               rs.append( "<input type=\"button\" class=\"\" name=\"btnEdit\" id=\"btnEdit\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.save" ) + "\" /> " );
            }
         }

         if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_SUBMIT, "", request, null ) )
         {
            rs.append( "<input type=\"button\" class=\"function\" name=\"btnSubmit\" id=\"btnSubmit\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.submit" )
                  + "\" style=\"display: none;\" /> " );
         }
         if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_PREVIOUS, "", request, null ) )
         {
            rs.append( "<input type=\"button\" class=\"\" name=\"btnPrevious\" id=\"btnPrevious\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.back" )
                  + "\" style=\"display: none;\" /> " );
         }
         if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_NEXT, "", request, null ) )
         {
            rs.append( "<input type=\"button\" class=\"\" name=\"btnNext\" id=\"btnNext\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.next" )
                  + "\" style=\"display: none;\" /> " );
         }
         if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_SEALED, "", request, null ) )
         {
            rs.append( "<input type=\"button\" class=\"function\" name=\"btnSealed\" id=\"btnSealed\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.sealed" )
                  + "\" style=\"display: none;\" /> " );
         }
         if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_ARCHIVE, "", request, null ) )
         {
            rs.append( "<input type=\"button\" class=\"function\" name=\"btnArchive\" id=\"btnArchive\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.archive" )
                  + "\" style=\"display: none;\" /> " );
         }
         if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_APPROVE, "", request, null ) )
         {
            rs.append( "<input type=\"button\" class=\"function\" name=\"btnRatify\" id=\"btnRatify\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.approve" )
                  + "\" style=\"display: none;\" /> " );
         }
         if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_CONFIRM, "", request, null ) )
         {
            rs.append( "<input type=\"button\" class=\"function\" name=\"btnConfirm\" id=\"btnConfirm\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.confirm" )
                  + "\" style=\"display: none;\" /> " );
         }
         if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_BACK, "", request, null ) )
         {
            rs.append( "<input type=\"button\" class=\"function\" name=\"btnRollback\" id=\"btnRollback\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.return" )
                  + "\" style=\"display: none;\" /> " );
         }
         if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_SICK_LEAVE, "", request, null ) )
         {
            rs.append( "<input type=\"button\" class=\"function\" name=\"btnRetrieve\" id=\"btnRetrieve\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.retrieve" )
                  + "\" style=\"display: none;\" /> " );
         }
         if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_STOP, "", request, null ) )
         {
            rs.append( "<input type=\"button\" class=\"function\" name=\"btnStop\" id=\"btnStop\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.stop" )
                  + "\" style=\"display: none;\" /> " );
         }

         rs.append( "<input type=\"button\" class=\"function\" name=\"btnRenew\" id=\"btnRenew\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.renew" )
               + "\" style=\"display: none;\" /> " );

         if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_CANCEL, "", request, null ) )
         {
            rs.append( "<input type=\"button\" class=\"reset\" name=\"btnCancel\" id=\"btnCancel\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.cancel" )
                  + "\" style=\"display: none;\" /> " );
         }
         if ( AuthUtils.hasAuthority( accessAction, AuthConstants.RIGHT_LIST, "", request, null ) )
         {
            rs.append( "<input type=\"button\" class=\"reset\" name=\"btnList\" id=\"btnList\" value=\"" + KANUtil.getProperty( request.getLocale(), "button.list" ) + "\" /> " );
         }
         rs.append( "</div>" );
         rs.append( "<form name=\"" + formName + "\" method=\"post\" action=\"" + addAction + "\" class=\"manage_primary_form\">" );

         // ���Cache�����д���Token������������ֶ�
         if ( CachedUtil.exists( request, BaseAction.getUserToken( request, null ) + "_" + BaseAction.TOKEN_NAME ) )
         {
            rs.append( BaseAction.addToken( request ) );
         }

         rs.append( "<input type=\"hidden\" id=\"id\" name=\"id\" value=\"" + encodedId + "\" />" );
         rs.append( "<input type=\"hidden\" id=\"subAction\" name=\"subAction\" class=\"subAction\" value=\"" + KANUtil.getValue( actionForm, "subAction" ) + "\" /> " );
         rs.append( "<fieldset>" );
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li class=\"required\"><label><em>* </em>" + KANUtil.getProperty( request.getLocale(), "required.field" ) + "</label>" );

         // ��ʼ��Table Remark
         String tableRemark = "";
         if ( KANUtil.filterEmpty( tableDTO.getTableVO().getComments() ) != null )
         {
            tableRemark = tableDTO.getTableVO().getComments();
         }
         rs.append( "<span class=\"highlight\">" + tableRemark + "</span>" );

         rs.append( "</li>" );
         rs.append( "</ol>" );

         for ( ColumnGroupDTO columnGroupDTO : tableDTO.getColumnGroupDTOs() )
         {
            boolean containsColumnGroup = false;

            // ��������ֶ��� - ���ÿ�ʼ��ǩ
            if ( columnGroupDTO.getColumnGroupVO() != null && columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
            {
               final ColumnGroupVO columnGroupVO = columnGroupDTO.getColumnGroupVO();

               if ( KANUtil.filterEmpty( columnGroupVO.getAccountId() ) != null
                     && ( KANUtil.filterEmpty( columnGroupVO.getAccountId() ).equals( KANConstants.SUPER_ACCOUNT_ID )
                           || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( columnGroupVO.getCorpId() ) == null ) || ( KANUtil.filterEmpty( corpId ) != null
                           && KANUtil.filterEmpty( columnGroupVO.getCorpId() ) != null && ( KANUtil.filterEmpty( corpId ).equals( KANUtil.filterEmpty( columnGroupVO.getCorpId() ) ) ) ) ) )
               {
                  containsColumnGroup = true;
               }

               // ��ʼ��ColumnGroup Id
               String columnGroupId = "";
               if ( columnGroupVO != null && columnGroupVO.getGroupId() != null && !columnGroupVO.getGroupId().trim().equals( "" ) )
               {
                  columnGroupId = accessAction + "_" + "CG" + columnGroupVO.getGroupId();
               }

               // ��ʼ��ColumnGroup Name
               String columnGroupName = "";
               if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
               {
                  columnGroupName = columnGroupVO.getNameZH();
               }
               else
               {
                  columnGroupName = columnGroupVO.getNameEN();
               }

               // ��ʼ��ColumnGroup��ʽ
               String style = "";
               if ( columnGroupVO.getUseBorder() != null && columnGroupVO.getUseBorder().trim().equals( "1" ) )
               {
                  style = style + "border: 1px solid #aaa; -webkit-border-radius: 3px; -moz-border-radius: 3px; border-radius: 3px;";
               }
               if ( columnGroupVO.getUseMargin() != null && columnGroupVO.getUseMargin().trim().equals( "1" ) )
               {
                  style = style + "margin: 2px 10px 20px 10px;";
               }
               if ( columnGroupVO.getUsePadding() != null && columnGroupVO.getUsePadding().trim().equals( "1" ) )
               {
                  style = style + "padding: 10px 10px 0px 10px;";
               }
               if ( columnGroupVO.getIsDisplayed() != null && columnGroupVO.getIsDisplayed().trim().equals( "2" ) )
               {
                  style = style + "display: none;";
               }
               if ( !style.trim().equals( "" ) )
               {
                  style = " style=\"" + style + "\" ";
               }

               if ( columnGroupVO.getUseName() != null && columnGroupVO.getUseName().trim().equals( "1" ) )
               {
                  rs.append( "<ol class=\"auto\">" );
                  rs.append( "<li><label><a id=\"" + columnGroupId + "_Link\">" + columnGroupName + "</a></label></li>" );
                  rs.append( "</ol>" );
               }
               rs.append( "<div id=\"" + columnGroupId + "\" " + style + ">" );
            }

            // ����Column
            if ( columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
            {
               rs.append( "<ol class=\"auto\">" );
               for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
               {
                  if ( KANUtil.filterEmpty( columnVO.getAccountId() ) != null
                        && ( KANUtil.filterEmpty( columnVO.getAccountId() ).equals( KANConstants.SUPER_ACCOUNT_ID )
                              || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( columnVO.getCorpId() ) == null ) || ( KANUtil.filterEmpty( corpId ) != null
                              && KANUtil.filterEmpty( columnVO.getCorpId() ) != null && KANUtil.filterEmpty( corpId ).equals( KANUtil.filterEmpty( columnVO.getCorpId() ) ) ) ) )
                  {
                     // ������ʾ����
                     if ( columnVO.getDisplayType() != null && columnVO.getDisplayType().trim().equalsIgnoreCase( "5" ) )
                     {
                        rs.append( "</ol>" );
                        rs.append( "<ol class=\"static\">" );
                     }

                     // ��ʼ��Clomun Name
                     String columnName = "";
                     if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
                     {
                        columnName = KANUtil.filterEmpty( columnVO.getManagerNameZH() ) == null ? columnVO.getNameZH() : columnVO.getManagerNameZH();
                     }
                     else
                     {
                        columnName = KANUtil.filterEmpty( columnVO.getManagerNameEN() ) == null ? columnVO.getNameEN() : columnVO.getManagerNameEN();
                     }
                     // ���ColumnNameΪ�գ�ʹ��Column��ϵͳ����ʼ��
                     if ( columnName == null || columnName.equals( "" ) )
                     {
                        columnName = columnVO.getNameSys();
                     }

                     // ��ǰLi��Style
                     String styleLi = "";
                     if ( columnVO.getDisplayType() != null
                           && ( columnVO.getDisplayType().trim().equalsIgnoreCase( "2" ) || columnVO.getDisplayType().trim().equalsIgnoreCase( "3" ) ) )
                     {
                        styleLi = " style=\"display: none;\" ";
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

                     rs.append( "<li id=\"" + id_name + "LI\"" + styleLi + ">" );
                     rs.append( "<label>" );

                     // ���Column����
                     rs.append( columnName );

                     // ��ʼ��Title
                     String title = "";
                     if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
                     {
                        title = columnVO.getTitleZH();
                     }
                     else
                     {
                        title = columnVO.getTitleEN();
                     }

                     // �����ǰ�ֶ�������ʾ
                     if ( columnVO.getUseTitle() != null && columnVO.getUseTitle().trim().equalsIgnoreCase( ColumnVO.TRUE ) )
                     {
                        rs.append( " <img src=\"images/tips.png\" title=\"" + title + "\" />" );
                     }

                     // �����ǰ�ֶ�Ϊ������
                     if ( columnVO.getIsRequired() != null && columnVO.getIsRequired().trim().equalsIgnoreCase( ColumnVO.TRUE ) )
                     {
                        rs.append( "<em> *</em>" );
                     }

                     rs.append( "</label>" );

                     // ��ʼ���ؼ���Length
                     String maxLenght = "";
                     if ( columnVO.getValidateLengthMax() != null && !columnVO.getValidateLengthMax().trim().equals( "" ) && !columnVO.getValidateLengthMax().trim().equals( "0" ) )
                     {
                        maxLenght = columnVO.getValidateLengthMax();
                     }

                     // ��ǰ�ֶ��Ƿ�ֻ��
                     String readOnly = "";
                     if ( columnVO.getEditable() != null && columnVO.getEditable().trim().equalsIgnoreCase( ColumnVO.FALSE ) )
                     {
                        readOnly = " readonly ";
                     }

                     // ��ǰInput��Style
                     String styleInput = "";
                     if ( columnVO.getCssStyle() != null && !columnVO.getCssStyle().trim().equals( "" ) )
                     {
                        styleInput = columnVO.getCssStyle();
                     }

                     // ��ǰInput��JS
                     String jsInput = "";
                     if ( columnVO.getJsEvent() != null && !columnVO.getJsEvent().trim().equals( "" ) )
                     {
                        jsInput = " " + columnVO.getJsEvent() + " ";
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

                     // ��ʼ����ǰColumn��Value
                     String value = "";
                     // ��ʼ�����ػ����ڸ�ʽ
                     String formatDate = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).OPTIONS_DATE_FORMAT;
                     if ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
                     {
                        value = ( String ) KANUtil.getValue( actionForm, columnVO.getNameDB() );
                     }
                     else
                     {
                        if ( jsonObject != null && jsonObject.containsKey( columnVO.getNameDB() ) )
                        {
                           value = jsonObject.getString( columnVO.getNameDB() );
                        }
                     }
                     if ( value == null || value.trim().equalsIgnoreCase( "null" ) )
                     {
                        value = "";
                     }

                     // ������Column�Ĵ���
                     String dateClass = "";
                     if ( columnVO.getValueType() != null && columnVO.getValueType().equals( "3" ) )
                     {
                        if ( KANUtil.filterEmpty( jsInput ) == null || !jsInput.trim().contains( "onFocus" ) )
                        {
                           jsInput = " onFocus=\"WdatePicker({dateFmt:'" + formatDate + "'})\" ";
                        }

                        dateClass = " Wdate ";
                        value = value.equals( "" ) ? "" : KANUtil.formatDate( value, formatDate, true );
                     }

                     // ������ӳ���ʾ
                     if ( columnVO.getDisplayType() != null && columnVO.getDisplayType().trim().equalsIgnoreCase( "3" ) )
                     {
                        rs.append( "<input type=\"hidden\" id=\"temp_" + id_name + "\" name=\"temp_" + id_name + "\" class=\"temp_" + id_name + "\" value=\"" + value + "\" />" );
                     }
                     else
                     {
                        // ����ǻ�����ʾ
                        if ( columnVO.getDisplayType() != null && columnVO.getDisplayType().trim().equalsIgnoreCase( "4" ) )
                        {
                           rs.append( "<input type=\"hidden\" id=\"temp_" + id_name + "\" name=\"temp_" + id_name + "\" class=\"temp_" + id_name + "\" value=\"" + value + "\" />" );
                        }

                        // �����ǰ�ֶ����ı���
                        if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "1" ) )
                        {
                           rs.append( "<input type=\"text\" id=\"" + id_name + "\" name=\"" + id_name + "\" maxlength=\"" + maxLenght + "\" class=\"" + id_name + " " + dateClass
                                 + "\" " + readOnly + " style=\"" + styleInput + "\" value=\"" + value + "\" " + jsInput + " />" );
                        }
                        // �����ǰ�ֶ���������
                        else if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "2" ) )
                        {
                           rs.append( "<select id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + id_name + "\" " + readOnly + " style=\"" + styleInput + "\" " + jsInput
                                 + " >" );

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
                                 rs.append( "<option id=\"option_" + id_name + "_" + mappingVO.getMappingId() + "\" value=\"" + mappingVO.getMappingId() + "\" " + selected + ">"
                                       + mappingVO.getMappingValue() + "</option>" );
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
                                 rs.append( "<input type=\"radio\" id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + id_name + "\" " + readOnly + " style=\""
                                       + styleInput + "\" value=\"" + mappingVO.getMappingId() + "\" " + checked + " " + jsInput + " />" + mappingVO.getMappingValue() + " &nbsp; " );
                              }
                              rs.append( "</span>" );
                           }
                        }
                        // �����ǰ�ֶ��Ǹ�ѡ��
                        else if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "4" ) )
                        {
                           if ( mappingVOs != null && mappingVOs.size() > 0 )
                           {
                              List< String > valueList = KANUtil.jasonArrayToStringList( ( String ) KANUtil.getValue( actionForm, columnVO.getNameDB() ) );
                              rs.append( "<span>" );
                              for ( MappingVO mappingVO : mappingVOs )
                              {
                                 // �Ƿ���Ҫѡ��
                                 String checked = "";

                                 if ( valueList != null && valueList.size() > 0 && valueList.contains( mappingVO.getMappingId() ) )
                                 {
                                    checked = "checked ";
                                 }

                                 rs.append( "<input type=\"checkbox\" id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + id_name + "\" " + readOnly + " style=\""
                                       + styleInput + "\" value=\"" + mappingVO.getMappingId() + "\" " + checked + " " + jsInput + " />" + mappingVO.getMappingValue() + " &nbsp; " );

                              }
                              rs.append( "</span>" );
                           }

                        }
                        // �����ǰ�ֶ����ı���
                        else if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "5" ) )
                        {
                           rs.append( "<textarea id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + id_name + "\" " + readOnly + " style=\"" + styleInput + "\" "
                                 + jsInput + ">" + value + "</textarea>" );
                        }
                     }

                     rs.append( "</li>" );

                     // ������ʾ����
                     if ( columnVO.getDisplayType() != null && columnVO.getDisplayType().trim().equalsIgnoreCase( "5" ) )
                     {
                        rs.append( "</ol>" );
                        rs.append( "<ol class=\"auto\">" );
                     }
                  }
               }
               rs.append( "</ol>" );
            }

            // ��������ֶ��� - ���ý�����ǩ
            if ( containsColumnGroup )
            {
               rs.append( "</div>" );
            }
         }

         // ���������Ϣ����
         rs.append( "<div id=\"special_info\"></div>" );
         rs.append( "</fieldset>" );
         rs.append( "</form>" );
         rs.append( "<div id=\"append_info\"></div>" );
         rs.append( "</div>" );
         rs.append( "</div>" );

         // ��ȡ��ǰSubAction - ����ҳ��һ�����ɣ�����ҳ��㼶һ�࣬���ó�ͻ��
         rs.append( "<script type='text/javascript'>function getSubAction(){return $('.manage_primary_form input#subAction').val();};</script>" );
      }

      return rs.toString();
   }

   public static String generateManageJS( final HttpServletRequest request, final String accessAction, final String initCallBack, final String editCallBack,
         final String submitCallBack ) throws KANException
   {
      return generateManageJS( request, accessAction, initCallBack, editCallBack, submitCallBack, null );
   }

   public static String generateManageJS( final HttpServletRequest request, final String accessAction, final String initCallBack, final String editCallBack,
         final String submitCallBack, final String submitAdditionalCallBack ) throws KANException
   {
      // ��ʼ��corpId
      final String corpId = BaseAction.getCorpId( request, null );
      // ��ȡ��ǰ��Ҫ���ɿؼ���������TableDTO
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getTableDTOByAccessAction( accessAction );
      // ��ʼ��ModuleDTO
      final ModuleDTO moduleDTO = KANConstants.getModuleDTOByAccessAction( accessAction );
      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();

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

      // ��ʼ������
      String listAction = "";
      String modifyAction = "";
      if ( moduleDTO != null && moduleDTO.getModuleVO() != null && moduleDTO.getModuleVO().getListAction() != null && !moduleDTO.getModuleVO().getListAction().equals( "" ) )
      {
         listAction = moduleDTO.getModuleVO().getListAction();
      }
      if ( moduleDTO != null && moduleDTO.getModuleVO() != null && moduleDTO.getModuleVO().getModifyAction() != null && !moduleDTO.getModuleVO().getModifyAction().equals( "" ) )
      {
         modifyAction = moduleDTO.getModuleVO().getModifyAction();
      }

      // ��ʼ��Table Name
      String tableName = "";
      if ( tableDTO != null && tableDTO.getTableVO() != null )
      {
         if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            tableName = tableDTO.getTableVO().getNameZH();
         }
         else
         {
            tableName = tableDTO.getTableVO().getNameEN();
         }
      }

      // ��ʼ��Validation
      String validation = "";
      // ����Column Group��Column
      if ( tableDTO != null && tableDTO.getColumnGroupDTOs() != null && tableDTO.getColumnGroupDTOs().size() > 0 )
      {
         for ( ColumnGroupDTO columnGroupDTO : tableDTO.getColumnGroupDTOs() )
         {

            final ColumnGroupVO columnGroupVO = columnGroupDTO.getColumnGroupVO();

            if ( columnGroupVO != null
                  && KANUtil.filterEmpty( columnGroupVO.getAccountId() ) != null
                  && ( KANUtil.filterEmpty( columnGroupVO.getAccountId() ).equals( KANConstants.SUPER_ACCOUNT_ID )
                        || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( columnGroupVO.getCorpId() ) == null ) || ( KANUtil.filterEmpty( corpId ) != null
                        && KANUtil.filterEmpty( columnGroupVO.getCorpId() ) != null && ( KANUtil.filterEmpty( corpId ).equals( KANUtil.filterEmpty( columnGroupVO.getCorpId() ) ) ) ) ) )
            {

               // ����ֶ�����Ҫ�Զ���ʾ������
               if ( columnGroupVO.getIsFlexable() != null && columnGroupVO.getIsFlexable().trim().equals( "1" ) )
               {
                  if ( columnGroupVO.getGroupId() != null && !columnGroupVO.getGroupId().trim().equals( "" ) )
                  {
                     // ��ʼ��ColumnGroup Id
                     final String columnGroupId = accessAction + "_" + "CG" + columnGroupVO.getGroupId();

                     rs.append( "$('#" + columnGroupId + "_Link').click( function () { if($('#" + columnGroupId + "').is(':visible')) { $('#" + columnGroupId
                           + "').hide();} else { $('#" + columnGroupId + "').show();}});" );
                  }
               }

               for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
               {
                  if ( KANUtil.filterEmpty( columnVO.getAccountId() ) != null
                        && ( KANUtil.filterEmpty( columnVO.getAccountId() ).equals( KANConstants.SUPER_ACCOUNT_ID )
                              || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( columnVO.getCorpId() ) == null ) || ( KANUtil.filterEmpty( corpId ) != null
                              && KANUtil.filterEmpty( columnVO.getCorpId() ) != null && KANUtil.filterEmpty( corpId ).equals( KANUtil.filterEmpty( columnVO.getCorpId() ) ) ) ) )
                  {

                     // �ֶ���ʾ���ͷǲ���ʾ��������JS��֤
                     if ( columnVO != null && KANUtil.filterEmpty( columnVO.getDisplayType() ) != null && !columnVO.getDisplayType().trim().equals( "2" ) )
                     {
                        // ��ʼ���ؼ���ID��Name�Լ�ThinkingId
                        String id_name = "";
                        String thinkingId = "";
                        if ( columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
                        {
                           id_name = columnVO.getNameDB();
                           thinkingId = columnVO.getThinkingId();
                        }
                        else
                        {
                           id_name = accessAction + "_" + columnVO.getNameDB();
                           thinkingId = accessAction + "_" + columnVO.getThinkingId();
                        }

                        // �ֶ���Ҫʹ�����빦��
                        if ( columnVO.getUseThinking() != null && columnVO.getUseThinking().equals( "1" ) )
                        {
                           if ( columnVO.getThinkingId() != null && !columnVO.getThinkingId().equals( "" ) && columnVO.getThinkingAction() != null
                                 && !columnVO.getThinkingAction().equals( "" ) )
                           {
                              rs.append( "kanThinking_column('" + id_name + "', '" + thinkingId + "', '" + columnVO.getThinkingAction() + "'" );
                              if ( columnVO.getJsEvent() != null && !columnVO.getJsEvent().trim().isEmpty() )
                              {
                                 rs.append( ", " + columnVO.getJsEvent() );
                              }
                              rs.append( ");" );
                           }
                        }

                        // �ֶ���Ҫ��֤
                        if ( columnVO.getValidateType() != null && !columnVO.getValidateType().equals( "" ) && !columnVO.getValidateType().equals( "0" ) )
                        {
                           // ��ʼ���Ƿ����
                           String isRequired = "false";
                           if ( columnVO.getIsRequired() != null && columnVO.getIsRequired().equals( "1" ) )
                           {
                              isRequired = "true";
                           }

                           // ��ʼ����֤����
                           String validateType = "common";
                           if ( columnVO.getValidateType().equals( "2" ) )
                           {
                              validateType = "numeric";
                           }
                           else if ( columnVO.getValidateType().equals( "3" ) )
                           {
                              validateType = "character";
                           }
                           else if ( columnVO.getValidateType().equals( "4" ) )
                           {
                              validateType = "currency";
                           }
                           else if ( columnVO.getValidateType().equals( "5" ) )
                           {
                              validateType = "passwrod";
                           }
                           else if ( columnVO.getValidateType().equals( "6" ) )
                           {
                              validateType = "phone";
                           }
                           else if ( columnVO.getValidateType().equals( "7" ) )
                           {
                              validateType = "mobile";
                           }
                           else if ( columnVO.getValidateType().equals( "8" ) )
                           {
                              validateType = "email";
                           }
                           else if ( columnVO.getValidateType().equals( "9" ) )
                           {
                              validateType = "website";
                           }
                           else if ( columnVO.getValidateType().equals( "10" ) )
                           {
                              validateType = "ip";
                           }
                           else if ( columnVO.getValidateType().equals( "11" ) )
                           {
                              validateType = "idcard";
                           }
                           else if ( columnVO.getValidateType().equals( "12" ) )
                           {
                              validateType = "select";
                           }
                           else
                           {
                              validateType = "common";
                           }

                           // ��ʼ���ֶγ�������
                           String maxlength = "0";
                           if ( columnVO.getValidateLengthMax() != null && !columnVO.getValidateLengthMax().equals( "" ) )
                           {
                              maxlength = columnVO.getValidateLengthMax();
                           }

                           // ��ʼ���ֶγ�������
                           String minlength = "0";
                           if ( columnVO.getValidateLengthMin() != null && !columnVO.getValidateLengthMin().equals( "" ) )
                           {
                              minlength = columnVO.getValidateLengthMin();
                           }

                           // ��ʼ���ֶδ�С(����)
                           String maxRange = "0";
                           if ( columnVO.getValidateRangeMax() != null && !columnVO.getValidateRangeMax().equals( "" ) )
                           {
                              maxRange = columnVO.getValidateRangeMax();
                           }

                           // ��ʼ���ֶδ�С(����)
                           String minRange = "0";
                           if ( columnVO.getValidateRangeMin() != null && !columnVO.getValidateRangeMin().equals( "" ) )
                           {
                              minRange = columnVO.getValidateRangeMin();
                           }

                           validation = validation + "flag = flag + validate('" + id_name + "', " + isRequired + ", '" + validateType + "', " + maxlength + ", " + minlength + ", "
                                 + maxRange + ", " + minRange + ");";

                        }
                     }
                  }
               }
            }
         }
      }

      // ��ʼ��JS���
      rs.append( "if(getSubAction() != 'createObject'){ disableForm('manage_primary_form'); $('.manage_primary_form input.subAction').val('viewObject');" );
      rs.append( "$('#pageTitle').html('" + tableName + " " + KANUtil.getProperty( request.getLocale(), "oper.info" ) + "');" );
      rs.append( "$('#btnEdit').val('" + KANUtil.getProperty( request.getLocale(), "button.edit" ) + "'); }else if(getSubAction() == 'createObject') {" );
      rs.append( "$('#pageTitle').html('" + KANUtil.getProperty( request.getLocale(), "oper.new" ) + " " + tableName + "');" );
      rs.append( "$('#decodeModifyBy,#decodeModifyDate').attr('disabled', 'disabled'); $('#decodeModifyDate').val(''); }" );
      // ��ʼ��ȡ����ť�¼�
      rs.append( "$('#btnList').click( function () {if(agreest()) link('" + listAction + "');} );" );
      // ��ʼ���༭��ť�¼�
      rs.append( "$('#btnEdit').click( function () {if(getSubAction() == 'viewObject'){ enableForm('manage_primary_form'); $('.manage_primary_form input#subAction').val('modifyObject');" );
      rs.append( "$('#btnEdit').val('" + KANUtil.getProperty( request.getLocale(), "button.save" ) + "'); $('.manage_primary_form').attr('action', '" + modifyAction + "');" );
      rs.append( "$('#pageTitle').html('" + tableName + " " + KANUtil.getProperty( request.getLocale(), "oper.edit" ) + "');" );
      rs.append( "$('li #warning_img').each(function(){$(this).show();}); $('li #disable_img').each(function(){$(this).hide();}); $('#decodeModifyBy').attr('disabled', 'disabled'); $('#decodeModifyDate').attr('disabled', 'disabled');"
            + ( editCallBack != null && !editCallBack.trim().equals( "" ) ? editCallBack : "" ) + "}else{" );
      rs.append( "var flag = 0;" );

      // �������Submit Callback����ֱ�ӵ��ã����û������ֶ�������
      if ( submitCallBack != null && !submitCallBack.trim().equals( "" ) )
      {
         rs.append( submitCallBack );
      }
      else
      {
         rs.append( "flag = validate_manage_primary_form();" );
      }

      rs.append( "if(flag == 0){enableForm('manage_primary_form');submit('manage_primary_form');}" );
      rs.append( "}});" );

      // ���JS��֤
      rs.append( "validate_manage_primary_form = function(){" );
      rs.append( "var flag = 0;" );
      rs.append( validation );

      // �������Submit Additional Callback����ֱ�Ӹ�����ȥ
      if ( submitAdditionalCallBack != null && !submitAdditionalCallBack.trim().equals( "" ) )
      {
         rs.append( submitAdditionalCallBack );
      }

      rs.append( "return flag;};" );

      // ���Callback
      if ( initCallBack != null && !initCallBack.trim().equals( "" ) )
      {
         rs.append( initCallBack );
      }
      return rs.toString();
   }
}
