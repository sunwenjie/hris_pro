package com.kan.base.domain.system;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：WorkflowModuleVO  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2013-6-26 下午05:45:12  
*   
*/
public class WorkflowModuleVO extends BaseVO
{

   /**  
    * Serial Version UID
    */
   private static final long serialVersionUID = -16874315848654L;

   private String workflowModuleId;

   private String systemId;

   private String moduleId;

   private String scopeType;

   //中文名
   private String nameZH;

   //英文名
   private String nameEN;

   private String rightIds;

   // 包含的展示审核对象的jsp页面
   private String includeViewObjJsp;

   private String description;

   /**
    * For Application
    */
   private String moduleTitle;

   private List< MappingVO > scopeTypes;

   // rightIdsArray:权限列表MappingVO
   private String[] rightIdsArray;

   public String getWorkflowModuleId()
   {
      return workflowModuleId;
   }

   public void setWorkflowModuleId( String workflowModuleId )
   {
      this.workflowModuleId = workflowModuleId;
   }

   public String getSystemId()
   {
      return systemId;
   }

   public void setSystemId( String systemId )
   {
      this.systemId = systemId;
   }

   public String getModuleId()
   {
      return moduleId;
   }

   public void setModuleId( String moduleId )
   {
      this.moduleId = moduleId;
   }

   public String getNameZH()
   {
      return nameZH;
   }

   public void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public String getNameEN()
   {
      return nameEN;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public String getModuleTitle()
   {
      return moduleTitle;
   }

   public void setModuleTitle( String moduleTitle )
   {
      this.moduleTitle = moduleTitle;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getIncludeViewObjJsp()
   {
      return includeViewObjJsp;
   }

   public void setIncludeViewObjJsp( String includeViewObjJsp )
   {
      this.includeViewObjJsp = includeViewObjJsp;
   }

   public String getScopeType()
   {
      return scopeType;
   }

   public void setScopeType( String scopeType )
   {
      this.scopeType = scopeType;
   }

   public String getRightIds()
   {
      return rightIds;
   }

   public void setRightIds( String rightIds )
   {
      this.rightIds = rightIds;
      this.rightIdsArray = KANUtil.jasonArrayToStringArray( rightIds );
   }

   public String[] getRightIdsArray()
   {
      return rightIdsArray;
   }

   public void setRightIdsArray( String[] rightIdsArray )
   {
      this.rightIdsArray = rightIdsArray;
      this.rightIds = KANUtil.toJasonArray( rightIdsArray );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( workflowModuleId == null || workflowModuleId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( workflowModuleId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public String getEncodedModuleId() throws KANException
   {
      if ( moduleId == null || moduleId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( moduleId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public List< MappingVO > getScopeTypes()
   {
      return scopeTypes;
   }

   public void setScopeTypes( List< MappingVO > scopeTypes )
   {
      this.scopeTypes = scopeTypes;
   }

   public String getDecodeScopeType()
   {
      return decodeField( this.scopeType, scopeTypes );
   }

   @Override
   public void update( final Object object )
   {
      final WorkflowModuleVO workflowModelVO = ( WorkflowModuleVO ) object;
      this.moduleId = workflowModelVO.getModuleId();
      this.scopeType = workflowModelVO.getScopeType();
      this.nameZH = workflowModelVO.getNameZH();
      this.nameEN = workflowModelVO.getNameEN();
      this.rightIds = workflowModelVO.getRightIds();
      this.includeViewObjJsp = workflowModelVO.getIncludeViewObjJsp();
      this.description = workflowModelVO.getDescription();
      super.setStatus( workflowModelVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.moduleId = "";
      this.scopeType = "";
      this.nameZH = "";
      this.nameEN = "";
      this.rightIds = "";
      this.includeViewObjJsp = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      // TODO Auto-generated method stub
      super.reset( mapping, request );
      this.scopeTypes = KANUtil.getMappings( this.getLocale(), "sys.module.workflow.scopeType" );
   }
}
