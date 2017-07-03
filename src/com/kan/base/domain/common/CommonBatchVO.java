package com.kan.base.domain.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：CommonBatchVO  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2014-4-20 下午03:10:30  
* 修改人：Jixiang  
* 修改时间：2014-4-20 下午03:10:30  
* 修改备注：  
* @version   
*   
*/
public class CommonBatchVO extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = 18856445877558L;

   private String batchId;

   private String role;

   private String roleId;

   private String importExcelName;

   private String accessAction;

   private String description;

   private String owner;

   //for application
   private List< String > inList;
   private List< String > notInList;

   public List< String > getInList()
   {
      return inList;
   }

   public void setInList( List< String > inList )
   {
      this.inList = inList;
   }

   public List< String > getNotInList()
   {
      return notInList;
   }

   public void setNotInList( List< String > notInList )
   {
      this.notInList = notInList;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( this.batchId );
   }

   @Override
   public void reset() throws KANException
   {
      this.role = "";
      this.roleId = "";
      this.importExcelName = "";
      this.accessAction = "";
      this.description = "";
   }

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      // TODO Auto-generated method stub
      super.reset( mapping, request );
      setStatuses( KANUtil.getMappings( request.getLocale(), "def.common.batch.status" ) );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      CommonBatchVO commonBatchVO = ( CommonBatchVO ) object;
      this.role = commonBatchVO.getRole();
      this.roleId = commonBatchVO.getRoleId();
      this.importExcelName = commonBatchVO.getImportExcelName();
      this.accessAction = commonBatchVO.getAccessAction();
      this.description = commonBatchVO.getDescription();
      setStatus( commonBatchVO.getStatus() );
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getImportExcelName()
   {
      return importExcelName;
   }

   public String getSubStrDescription() throws KANException
   {
      String result = "";
      if ( KANUtil.filterEmpty( this.description ) != null )
      {
         if ( description.length() > 15 )
         {
            result = description.substring( 0, 20 ) + "...";
         }
         else
         {
            result = description;
         }
      }
      return result;
   }

   public void setImportExcelName( String importExcelName )
   {
      this.importExcelName = importExcelName;
   }

   public String getAccessAction()
   {
      return accessAction;
   }

   public void setAccessAction( String accessAction )
   {
      this.accessAction = accessAction;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getRole()
   {
      return role;
   }

   public void setRole( String role )
   {
      this.role = role;
   }

   public String getRoleId()
   {
      return roleId;
   }

   public void setRoleId( String roleId )
   {
      this.roleId = roleId;
   }

   public String decodeCreateDate()
   {
      return decodeDatetime( getCreateDate() );
   }

   public String decodeCreateBy()
   {
      return decodeUserId( getCreateBy() );
   }

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
   }

}
