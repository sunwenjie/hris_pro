package com.kan.base.web.renders.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.MappingVO;
import com.kan.base.tag.AuthConstants;
import com.kan.base.tag.AuthUtils;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.management.BusinessContractTemplateAction;
import com.kan.base.web.actions.management.LaborContractTemplateAction;

public class EntityRender
{
   // Code reviewed by Kevin Jin at 2013-08-19

   /**  
    * GetBusinessContractEntitiesManagement
    *	Code reviewed by Kevin Jin at 2013-08-19
    *	Modify by Jack an 2014-2-26
    *	@param request
    *	@param entityIdArray
    *	@return
    *	@throws KANException
    */
   public static String getBusinessContractEntitiesManagement( final HttpServletRequest request, final String[] entityIdArray ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      // 常量中获得所有 EntityVOs
      String accountId = BaseAction.getAccountId(request, null);
      KANAccountConstants accountConstants = KANConstants.getKANAccountConstants("".equals(accountId) ? null : accountId);
      String language = request.getLocale().getLanguage();
      final List< MappingVO > entities = accountConstants.getEntities(language);
      final String businessContractTemplateId = ( String ) request.getAttribute( "businessContractTemplateId" );

      rs.append( "<ol id=\"mannageEntityOL\" class=\"auto\">" );

      if ( entityIdArray != null && entityIdArray.length > 0 )
      {
         // 遍历EntityIds 生成EntityVO 编辑区域
         for ( String entityId : entityIdArray )
         {
            // 遍历All EntityVOs查询对应的EntityVO类
            for ( MappingVO mappingVO : entities )
            {
               // 如果找到对应的 Entity MappingVO
               if ( entityId.equals( mappingVO.getMappingId() ) )
               {
                  final String entityName = mappingVO.getMappingValue();

                  // 生成编辑 EntityVO的区域
                  rs.append( "<li id=\"mannageEntity_" + entityId + "\">" );
                  rs.append( "<input type=\"hidden\" id=\"entityIdArray\" name=\"entityIdArray\" value=\"" + entityId + "\">" );
                  if ( AuthUtils.hasAuthority( BusinessContractTemplateAction.accessAction, AuthConstants.RIGHT_NEW, "", request, null ) || AuthUtils.hasAuthority( BusinessContractTemplateAction.accessAction, AuthConstants.RIGHT_MODIFY, "", request, null )){
                	  rs.append( "<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" name=\"disable_img\">" );
                      rs.append( "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" style=\"display: none;\" onClick=\"removeExtraObject('businessContractTemplateAction.do?proc=modify_object_ajax_tab&actionFlag=delObject&entityId="
                            + entityId + "&businessContractTemplateId=" + businessContractTemplateId + "', this, '#numberOfEntity');\"/>" );
                  }
                  rs.append( " &nbsp;&nbsp; " + entityName + "</li>" );
                  continue;
               }
            }
         }
      }
      rs.append( "</ol>" );

      return rs.toString();
   }

   /**  
    * GetLaborContractEntitiesManagement
    * Add by Jack an 2014-2-26
    *	@param request
    *	@param entityIdArray
    *	@return
    *	@throws KANException
    */
   public static String getLaborContractEntitiesManagement( final HttpServletRequest request, final String[] entityIdArray ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      // 常量中获得所有 EntityVOs
      String accountId = BaseAction.getAccountId(request, null );
      KANAccountConstants accountConstants = KANConstants.getKANAccountConstants("".equals(accountId) ? null : accountId);
      String language = request.getLocale().getLanguage();
      String corpId = BaseAction.getCorpId(request, null );
      final List< MappingVO > entities = accountConstants.getEntities(language,"".equals(corpId) ? null : corpId);
      final String laborContractTemplateId = ( String ) request.getAttribute( "laborContractTemplateId" );

      rs.append( "<ol id=\"mannageEntityOL\" class=\"auto\">" );

      if ( entityIdArray != null && entityIdArray.length > 0 )
      {
         // 遍历EntityIds 生成EntityVO 编辑区域
         for ( String entityId : entityIdArray )
         {
            // 遍历All EntityVOs查询对应的EntityVO类
            for ( MappingVO mappingVO : entities )
            {
               // 如果找到对应的 Entity MappingVO
               if ( entityId.equals( mappingVO.getMappingId() ) )
               {
                  final String entityName = mappingVO.getMappingValue();

                  // 生成编辑 EntityVO的区域
                  rs.append( "<li id=\"mannageEntity_" + entityId + "\">" );
                  rs.append( "<input type=\"hidden\" id=\"entityIdArray\" name=\"entityIdArray\" value=\"" + entityId + "\">" );
                  if ( AuthUtils.hasAuthority( LaborContractTemplateAction.accessAction, AuthConstants.RIGHT_NEW, "", request, null ) || AuthUtils.hasAuthority( LaborContractTemplateAction.accessAction, AuthConstants.RIGHT_MODIFY, "", request, null )){
                	  rs.append( "<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" name=\"disable_img\">" );
                      rs.append( "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" style=\"display: none;\" onClick=\"removeExtraObject('laborContractTemplateAction.do?proc=modify_object_ajax_tab&actionFlag=delObject&actionFlag=delObject&entityId="
                            + entityId + "&laborContractTemplateId=" + laborContractTemplateId + "', this, '#numberOfEntity');\"/>" );
                  }
                  rs.append( " &nbsp;&nbsp; " + entityName + "</li>" );
                  continue;
               }
            }
         }
      }
      rs.append( "</ol>" );

      return rs.toString();
   }

}
