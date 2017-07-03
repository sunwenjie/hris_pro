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

public class BusinessTypeRender
{

   /**  
    * GetBusinessTypeManagement
    *	�����ͬҵ�������޸�
    *	@param request
    *	@param businessTypeIdArray
    *	@return
    *	@throws KANException
    */
   public static String getBusinessTypeManagement( final HttpServletRequest request, final String[] businessTypeIdArray ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      // �����л������ BusinessTypeVOs
      String accountId = BaseAction.getAccountId( request, null );
      KANAccountConstants accountConstants = KANConstants.getKANAccountConstants("".equals(accountId) ? null : accountId);
      String language = request.getLocale().getLanguage();
      final List< MappingVO > businessTypes = accountConstants.getBusinessTypes(language);
      final String businessContractTemplateId = ( String ) request.getAttribute( "businessContractTemplateId" );

      rs.append( "<ol id=\"mannageBusinessTypeOL\" class=\"auto\">" );

      // ���BusinessTypeIds ��Ϊ��,ת��Ϊ�ַ�������
      if ( businessTypeIdArray != null && businessTypeIdArray.length > 0 )
      {
         // ����BusinessTypeIds ����BusinessTypeVO �༭����
         for ( String businessTypeId : businessTypeIdArray )
         {
            // ����All BusinessTypeVOs��ѯ��Ӧ��BusinessTypeVO��
            for ( MappingVO mappingVO : businessTypes )
            {
               // ����ҵ���Ӧ�� BusinessType MappingVO
               if ( businessTypeId.equals( mappingVO.getMappingId() ) )
               {
                  final String businessTypeName = mappingVO.getMappingValue();

                  // ���ɱ༭ BusinessTypeVO������
                  rs.append( "<li id=\"mannageBusinessType_" + businessTypeId + "\">" );
                  rs.append( "<input type=\"hidden\" id=\"businessTypeIdArray\" name=\"businessTypeIdArray\" value=\"" + businessTypeId + "\">" );
                  if ( AuthUtils.hasAuthority( BusinessContractTemplateAction.accessAction, AuthConstants.RIGHT_NEW, "", request, null ) || AuthUtils.hasAuthority( BusinessContractTemplateAction.accessAction, AuthConstants.RIGHT_MODIFY, "", request, null )){
                	  rs.append( "<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" name=\"disable_img\">" );
                      rs.append( "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" style=\"display: none;\" onClick=\"removeExtraObject('businessContractTemplateAction.do?proc=modify_object_ajax_tab&actionFlag=delObject&businessTypeId="
                            + businessTypeId + "&businessContractTemplateId=" + businessContractTemplateId + "', this, '#numberOfBusinessType');\"/>" );
                  }
                  rs.append( " &nbsp;&nbsp; " + businessTypeName + "</li>" );
                  continue;
               }
            }
         }
      }
      rs.append( "</ol>" );

      return rs.toString();
   }

   /**  
    * GetLaborBusinessTypeManagement
    *	�Ͷ���ͬ/����Э��ҵ�������޸�
    *	@param request
    *	@param businessTypeIdArray
    *	@return
    *	@throws KANException
    */
   public static String getLaborBusinessTypeManagement( final HttpServletRequest request, final String[] businessTypeIdArray ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      // �����л������ BusinessTypeVOs
      String accountId = BaseAction.getAccountId(request, null);
      String language = request.getLocale().getLanguage();
      String corpId = BaseAction.getCorpId(request, null );
      KANAccountConstants accountConstants = KANConstants.getKANAccountConstants("".equals(accountId) ? null : accountId);
      final List< MappingVO > businessTypes = accountConstants.getBusinessTypes(language,"".equals(corpId) ? null : corpId);
      final String laborContractTemplateId = ( String ) request.getAttribute( "laborContractTemplateId" );

      rs.append( "<ol id=\"mannageBusinessTypeOL\" class=\"auto\">" );

      // ���BusinessTypeIds ��Ϊ��,ת��Ϊ�ַ�������
      if ( businessTypeIdArray != null && businessTypeIdArray.length > 0 )
      {
         // ����BusinessTypeIds ����BusinessTypeVO �༭����
         for ( String businessTypeId : businessTypeIdArray )
         {
            // ����All BusinessTypeVOs��ѯ��Ӧ��BusinessTypeVO��
            for ( MappingVO mappingVO : businessTypes )
            {
               // ����ҵ���Ӧ�� BusinessType MappingVO
               if ( businessTypeId.equals( mappingVO.getMappingId() ) )
               {
                  final String businessTypeName = mappingVO.getMappingValue();

                  // ���ɱ༭ BusinessTypeVO������
                  rs.append( "<li id=\"mannageBusinessType_" + businessTypeId + "\">" );
                  rs.append( "<input type=\"hidden\" id=\"businessTypeIdArray\" name=\"businessTypeIdArray\" value=\"" + businessTypeId + "\">" );
                  if ( AuthUtils.hasAuthority( LaborContractTemplateAction.accessAction, AuthConstants.RIGHT_NEW, "", request, null ) || AuthUtils.hasAuthority( LaborContractTemplateAction.accessAction, AuthConstants.RIGHT_MODIFY, "", request, null )){
                	  rs.append( "<img src=\"images/disable-btn.png\" width=\"12px\" height=\"12px\" id=\"disable_img\" name=\"disable_img\">" );
                      rs.append( "<img src=\"images/warning-btn.png\" width=\"12px\" height=\"12px\" id=\"warning_img\" name=\"warning_img\" style=\"display: none;\" onClick=\"removeExtraObject('laborContractTemplateAction.do?proc=modify_object_ajax_tab&actionFlag=delObject&businessTypeId="
                            + businessTypeId + "&laborContractTemplateId=" + laborContractTemplateId + "', this, '#numberOfBusinessType');\"/>" );
                  }
                  rs.append( " &nbsp;&nbsp; " + businessTypeName + "</li>" );
                  continue;
               }
            }
         }
      }
      rs.append( "</ol>" );

      return rs.toString();
   }

}
