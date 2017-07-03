package com.kan.base.web.renders.workflow;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnGroupDTO;
import com.kan.base.domain.define.ColumnGroupVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class WorkflowRender
{

   public static String generateJSForWorkflowApprovalPageDefineColumn( final HttpServletRequest request, final String accessAction ) throws KANException
   {
      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();
      final String accountId = BaseAction.getAccountId( request, null );
      final String corpId = BaseAction.getCorpId( request, null );
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
      final TableDTO tableDTO = accountConstants.getTableDTOByAccessAction( accessAction );

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
               {
                  // ����ֶ�����Ҫ�Զ���ʾ������
                  if ( columnGroupVO.getIsFlexable() != null && columnGroupVO.getIsFlexable().trim().equals( "1" ) )
                  {
                     if ( columnGroupVO.getGroupId() != null && !columnGroupVO.getGroupId().trim().equals( "" ) )
                     {
                        // ��ʼ��ColumnGroup Id
                        final String columnGroupId = accessAction + "_" + "CG" + columnGroupVO.getGroupId();

                        rs.append( "$('." + columnGroupId + "_Link').click( function () { if($('." + columnGroupId + "').is(':visible')) { $('#" + columnGroupId
                              + "').hide();} else { $('#" + columnGroupId + "').show();}});" );
                     }
                  }
               }
            }
         }
      }

      return rs.toString();
   }

   public static String generateHtmlForWorkflowApprovalPageDefineColumn( final HttpServletRequest request, final String accessAction, final Object originalActionForm,
         final Object passActionForm, final boolean update ) throws KANException
   {
      // ��ʼ��StringBuffer
      final StringBuffer rs = new StringBuffer();

      final String accountId = BaseAction.getAccountId( request, null );
      final String corpId = BaseAction.getCorpId( request, null );
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
      final TableDTO tableDTO = accountConstants.getTableDTOByAccessAction( accessAction );
      // ��ʼ��Jason���� - ���е�ǰ�ֶε�ֵ
      JSONObject originalJsonObject = null;
      JSONObject passJsonObject = null;
      try
      {
         // ��Action Form��Remark1�ֶλ�ȡJason�ַ���
         if ( originalActionForm != null )
         {
            final Object originalJsonString = KANUtil.getValue( originalActionForm, "remark1" );
            if ( originalJsonString != null && !( ( String ) originalJsonString ).trim().equals( "" ) )
            {
               originalJsonObject = JSONObject.fromObject( URLDecoder.decode( ( String ) originalJsonString, "GBK" ).replace( "[{", "{" ).replace( "}]", "}" ) );
            }
         }
         if ( passActionForm != null )
         {
            final Object passJsonString = KANUtil.getValue( passActionForm, "remark1" );
            if ( passJsonString != null && !( ( String ) passJsonString ).trim().equals( "" ) )
            {
               passJsonObject = JSONObject.fromObject( URLDecoder.decode( ( String ) passJsonString, "GBK" ).replace( "[{", "{" ).replace( "}]", "}" ) );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      if ( tableDTO != null && tableDTO.getColumnGroupDTOs() != null && tableDTO.getColumnGroupDTOs().size() > 0 )
      {
         for ( ColumnGroupDTO columnGroupDTO : tableDTO.getColumnGroupDTOs() )
         {
            // ��������ֶ��� - ���ÿ�ʼ��ǩ
            if ( columnGroupDTO != null && columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
            {
               final ColumnGroupVO columnGroupVO = columnGroupDTO.getColumnGroupVO();
               // �����ϵͳ����
               if ( groupContainDefineColumnVO( columnGroupDTO, corpId ) )
               {
                  if ( columnGroupVO != null )
                  {
                     // ��ʼ��ColumnGroup Id
                     String columnGroupClass = "";
                     if ( columnGroupVO != null && columnGroupVO.getGroupId() != null && !columnGroupVO.getGroupId().trim().equals( "" ) )
                     {
                        columnGroupClass = accessAction + "_" + "CG" + columnGroupVO.getGroupId();
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
                        style = style + "padding: 10px 10px 10px 10px;";
                     }
                     if ( !style.trim().equals( "" ) )
                     {
                        style = " style=\"" + style + "\" ";
                     }

                     if ( columnGroupVO.getUseName() != null && columnGroupVO.getUseName().trim().equals( "1" ) )
                     {
                        rs.append( "<ol class=\"auto\">" );
                        rs.append( "<li><label><a class=\"" + columnGroupClass + "_Link\">" + columnGroupName + "</a></label></li>" );
                        rs.append( "</ol>" );
                     }

                     rs.append( "<div class=\"" + columnGroupClass + "\" " + style + ">" );
                  }

                  // ����Column
                  if ( columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
                  {
                     rs.append( "<ol class=\"auto\">" );
                     for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
                     {
                        if ( KANUtil.filterEmpty( columnVO.getAccountId() ) != null
                              && ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( columnVO.getCorpId() ) == null ) || ( KANUtil.filterEmpty( corpId ) != null
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
                           // ��ʼ����ǰColumn��Value
                           String value = "";
                           // ��ʼ�����ػ����ڸ�ʽ
                           String formatDate = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).OPTIONS_DATE_FORMAT;

                           String passValue = "";
                           String originalValue = "";

                           if ( passJsonObject != null && passJsonObject.containsKey( columnVO.getNameDB() ) )
                           {
                              passValue = passJsonObject.getString( columnVO.getNameDB() );
                           }

                           if ( originalJsonObject != null && originalJsonObject.containsKey( columnVO.getNameDB() ) )
                           {
                              originalValue = originalJsonObject.getString( columnVO.getNameDB() );
                           }

                           String labelStyle = "";
                           if ( update )
                           {
                              if ( originalValue != null && !originalValue.equals( passValue ) )
                              {
                                 id_name = id_name + " error important";
                                 labelStyle = "style=\"color:red;\"";
                              }
                           }

                           // ���Column����
                           rs.append( "<label " + labelStyle + ">" + columnName + "</label>" );

                           if ( update )
                           {
                              value = passValue;
                           }
                           else
                           {
                              value = originalValue;
                           }

                           if ( value == null || value.trim().equalsIgnoreCase( "null" ) )
                           {
                              value = "";
                           }

                           // ������Column�Ĵ���
                           if ( columnVO.getValueType() != null && columnVO.getValueType().equals( "3" ) )
                           {
                              value = value.equals( "" ) ? "" : KANUtil.formatDate( value, formatDate, true );
                           }

                           final List< MappingVO > mappingVOs = getMappingVOsByCondition( request, columnVO );
                           // �����ǰ�ֶ����ı���
                           if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "1" ) )
                           {
                              rs.append( "<input disabled=\"disabled\" type=\"text\" id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + id_name + "\" style=\"\" value=\""
                                    + value + "\" />" );
                           }
                           // �����ǰ�ֶ���������
                           else if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "2" ) )
                           {
                              if ( mappingVOs != null && mappingVOs.size() > 0 )
                              {
                                 for ( MappingVO mappingVO : mappingVOs )
                                 {
                                    if ( value != null && value.trim().equals( mappingVO.getMappingId() ) )
                                    {
                                       value = mappingVO.getMappingValue();
                                    }
                                 }
                              }
                              rs.append( "<input disabled=\"disabled\" type=\"text\" id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + id_name + "\" style=\"\" value=\""
                                    + value + "\" />" );
                           }
                           // �����ǰ�ֶ����ı���
                           else if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "5" ) )
                           {
                              rs.append( "<textarea disabled=\"disabled\" id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + id_name + "\" >" + value + "</textarea>" );
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

                     rs.append( "</ol>" );
                  }

                  if ( columnGroupVO != null )
                  {
                     rs.append( "</div>" );
                  }
               }
            }
         }
      }

      return rs.toString();
   }

   @SuppressWarnings("unchecked")
   private static List< MappingVO > getMappingVOsByCondition( final HttpServletRequest request, final ColumnVO columnVO ) throws KANException
   {
      // ��ʼ��MappingVO�б�
      List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

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
      else if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "2" ) )
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

                  if ( KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) ) != null )
                  {
                     parameters = new String[] { request.getLocale().getLanguage(), BaseAction.getCorpId( request, null ) };
                  }
                  else
                  {
                     parameters = new String[] { request.getLocale().getLanguage() };
                  }

                  mappingVOs = ( List< MappingVO > ) KANUtil.getValue( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ), accountOption.getMappingTemp(), parameters );
                  // ��ӿյ�MappingVO����
                  if ( mappingVOs != null )
                     mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
                  break;
               }
            }
         }
      }
      // ���������� - �û��Զ���
      else if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "3" ) )
      {
         mappingVOs = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getColumnOptionDTOByOptionHeaderId( columnVO.getOptionValue() ).getOptions( request.getLocale().getLanguage() );
      }

      return mappingVOs;
   }

   public static boolean groupContainDefineColumnVO( final ColumnGroupDTO columnGroupDTO, final String corpId )
   {
      if ( columnGroupDTO != null && columnGroupDTO.getColumnVOs().size() > 0 )
      {
         if ( columnGroupDTO.getColumnGroupVO() == null )
         {
            for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
            {
               if ( columnVO != null && !columnVO.getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) )
                  return true;
            }
         }
         else if ( columnGroupDTO.getColumnGroupVO() != null )
         {
            if ( !columnGroupDTO.getColumnGroupVO().getAccountId().equals( KANConstants.SUPER_ACCOUNT_ID ) && columnGroupDTO.getColumnGroupVO().getCorpId().equals( corpId ) )
               return true;
         }
      }

      return false;
   }
}
