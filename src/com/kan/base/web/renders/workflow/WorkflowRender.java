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
      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();
      final String accountId = BaseAction.getAccountId( request, null );
      final String corpId = BaseAction.getCorpId( request, null );
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
      final TableDTO tableDTO = accountConstants.getTableDTOByAccessAction( accessAction );

      // 遍历Column Group和Column
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
                  // 如果字段组需要自动显示或隐藏
                  if ( columnGroupVO.getIsFlexable() != null && columnGroupVO.getIsFlexable().trim().equals( "1" ) )
                  {
                     if ( columnGroupVO.getGroupId() != null && !columnGroupVO.getGroupId().trim().equals( "" ) )
                     {
                        // 初始化ColumnGroup Id
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
      // 初始化StringBuffer
      final StringBuffer rs = new StringBuffer();

      final String accountId = BaseAction.getAccountId( request, null );
      final String corpId = BaseAction.getCorpId( request, null );
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
      final TableDTO tableDTO = accountConstants.getTableDTOByAccessAction( accessAction );
      // 初始化Jason对象 - 含有当前字段的值
      JSONObject originalJsonObject = null;
      JSONObject passJsonObject = null;
      try
      {
         // 从Action Form的Remark1字段获取Jason字符串
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
            // 如果存在字段组 - 设置开始标签
            if ( columnGroupDTO != null && columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
            {
               final ColumnGroupVO columnGroupVO = columnGroupDTO.getColumnGroupVO();
               // 如果非系统分组
               if ( groupContainDefineColumnVO( columnGroupDTO, corpId ) )
               {
                  if ( columnGroupVO != null )
                  {
                     // 初始化ColumnGroup Id
                     String columnGroupClass = "";
                     if ( columnGroupVO != null && columnGroupVO.getGroupId() != null && !columnGroupVO.getGroupId().trim().equals( "" ) )
                     {
                        columnGroupClass = accessAction + "_" + "CG" + columnGroupVO.getGroupId();
                     }

                     // 初始化ColumnGroup Name
                     String columnGroupName = "";
                     if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
                     {
                        columnGroupName = columnGroupVO.getNameZH();
                     }
                     else
                     {
                        columnGroupName = columnGroupVO.getNameEN();
                     }

                     // 初始化ColumnGroup样式
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

                  // 遍历Column
                  if ( columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
                  {
                     rs.append( "<ol class=\"auto\">" );
                     for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
                     {
                        if ( KANUtil.filterEmpty( columnVO.getAccountId() ) != null
                              && ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( columnVO.getCorpId() ) == null ) || ( KANUtil.filterEmpty( corpId ) != null
                                    && KANUtil.filterEmpty( columnVO.getCorpId() ) != null && KANUtil.filterEmpty( corpId ).equals( KANUtil.filterEmpty( columnVO.getCorpId() ) ) ) ) )
                        {
                           // 独立显示处理
                           if ( columnVO.getDisplayType() != null && columnVO.getDisplayType().trim().equalsIgnoreCase( "5" ) )
                           {
                              rs.append( "</ol>" );
                              rs.append( "<ol class=\"static\">" );
                           }

                           // 初始化Clomun Name
                           String columnName = "";
                           if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
                           {
                              columnName = KANUtil.filterEmpty( columnVO.getManagerNameZH() ) == null ? columnVO.getNameZH() : columnVO.getManagerNameZH();
                           }
                           else
                           {
                              columnName = KANUtil.filterEmpty( columnVO.getManagerNameEN() ) == null ? columnVO.getNameEN() : columnVO.getManagerNameEN();
                           }

                           // 如果ColumnName为空，使用Column的系统名初始化
                           if ( columnName == null || columnName.equals( "" ) )
                           {
                              columnName = columnVO.getNameSys();
                           }

                           // 当前Li的Style
                           String styleLi = "";
                           if ( columnVO.getDisplayType() != null
                                 && ( columnVO.getDisplayType().trim().equalsIgnoreCase( "2" ) || columnVO.getDisplayType().trim().equalsIgnoreCase( "3" ) ) )
                           {
                              styleLi = " style=\"display: none;\" ";
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

                           rs.append( "<li id=\"" + id_name + "LI\"" + styleLi + ">" );
                           // 初始化当前Column的Value
                           String value = "";
                           // 初始化本地化日期格式
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

                           // 添加Column名称
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

                           // 日期型Column的处理
                           if ( columnVO.getValueType() != null && columnVO.getValueType().equals( "3" ) )
                           {
                              value = value.equals( "" ) ? "" : KANUtil.formatDate( value, formatDate, true );
                           }

                           final List< MappingVO > mappingVOs = getMappingVOsByCondition( request, columnVO );
                           // 如果当前字段是文本框
                           if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "1" ) )
                           {
                              rs.append( "<input disabled=\"disabled\" type=\"text\" id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + id_name + "\" style=\"\" value=\""
                                    + value + "\" />" );
                           }
                           // 如果当前字段是下拉框
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
                           // 如果当前字段是文本域
                           else if ( columnVO.getInputType() != null && columnVO.getInputType().trim().equals( "5" ) )
                           {
                              rs.append( "<textarea disabled=\"disabled\" id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + id_name + "\" >" + value + "</textarea>" );
                           }
                        }

                        rs.append( "</li>" );

                        // 独立显示处理
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
      // 初始化MappingVO列表
      List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

      // 下拉框类型 - 系统常量
      if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "1" ) )
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
                  break;
               }
            }
         }
      }
      // 下拉框类型 - 账户常量
      else if ( KANUtil.filterEmpty( columnVO.getOptionType() ) != null && columnVO.getOptionType().trim().equals( "2" ) )
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

                  if ( KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) ) != null )
                  {
                     parameters = new String[] { request.getLocale().getLanguage(), BaseAction.getCorpId( request, null ) };
                  }
                  else
                  {
                     parameters = new String[] { request.getLocale().getLanguage() };
                  }

                  mappingVOs = ( List< MappingVO > ) KANUtil.getValue( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ), accountOption.getMappingTemp(), parameters );
                  // 添加空的MappingVO对象
                  if ( mappingVOs != null )
                     mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
                  break;
               }
            }
         }
      }
      // 下拉框类型 - 用户自定义
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
