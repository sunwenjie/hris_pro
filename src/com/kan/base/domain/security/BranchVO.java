package com.kan.base.domain.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class BranchVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -7062021385051946220L;

   // 部门ID
   private String branchId;

   // 上级部门Id
   private String parentBranchId;

   // 法务实体Id
   private String entityId;

   // 业务类型Id
   private String businessTypeId;

   // 成本中心Id
   private String settlementBranch;

   // 部门编号，0-6位数字
   private String branchCode;

   // 部门中文名称（简称）
   private String nameZH;

   // 部门英文名称（简称）
   private String nameEN;

   // 部门描述
   private String description;

   // for app
   @JsonIgnore
   private String parentBranchName;
   @JsonIgnore
   // 部门员工ID
   private List< String > staffIdsInBranch = new ArrayList< String >();
   @JsonIgnore
   // 法务实体
   private List< MappingVO > entities = new ArrayList< MappingVO >();
   @JsonIgnore
   // 业务类型
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();
   // 成本中心
   @JsonIgnore
   private List< MappingVO > branchs = new ArrayList< MappingVO >();
   @JsonIgnore
   // 复制branch
   private List< MappingVO > copyBranchVOs = new ArrayList< MappingVO >();
   @JsonIgnore
   private String[] copyBranchIdArray;
   @JsonIgnore
   private String[] copyBranchNameZHArray;
   @JsonIgnore
   private String[] copyBranchNameENArray;

   public List< MappingVO > getEntities()
   {
      return entities;
   }

   public void setEntities( List< MappingVO > entities )
   {
      this.entities = entities;
   }

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
   }

   public List< MappingVO > getBranchs()
   {
      return branchs;
   }

   public void setBranchs( List< MappingVO > branchs )
   {
      this.branchs = branchs;
   }

   public List< MappingVO > getCopyBranchVOs()
   {
      return copyBranchVOs;
   }

   public void setCopyBranchVOs( List< MappingVO > copyBranchVOs )
   {
      this.copyBranchVOs = copyBranchVOs;
   }

   public String[] getCopyBranchIdArray()
   {
      return copyBranchIdArray;
   }

   public void setCopyBranchIdArray( String[] copyBranchIdArray )
   {
      this.copyBranchIdArray = copyBranchIdArray;
   }

   public String[] getCopyBranchNameZHArray()
   {
      return copyBranchNameZHArray;
   }

   public void setCopyBranchNameZHArray( String[] copyBranchNameZHArray )
   {
      this.copyBranchNameZHArray = copyBranchNameZHArray;
   }

   public String[] getCopyBranchNameENArray()
   {
      return copyBranchNameENArray;
   }

   public void setCopyBranchNameENArray( String[] copyBranchNameENArray )
   {
      this.copyBranchNameENArray = copyBranchNameENArray;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( branchId );
   }

   @Override
   public void update( Object object )
   {
      final BranchVO branchVO = ( BranchVO ) object;
      this.branchCode = branchVO.getBranchCode();
      this.parentBranchId = branchVO.getParentBranchId();
      this.entityId = branchVO.getEntityId();
      this.businessTypeId = branchVO.getBusinessTypeId();
      this.settlementBranch = branchVO.getSettlementBranch();
      this.nameZH = branchVO.getNameZH();
      this.nameEN = branchVO.getNameEN();
      this.description = branchVO.getDescription();
      super.setStatus( branchVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.parentBranchId = "";
      this.branchCode = "";
      this.nameZH = "";
      this.nameEN = "";
      this.description = "";
      this.entityId = "0";
      this.businessTypeId = "0";
      this.settlementBranch = "0";
      super.setStatus( "0" );
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );

      this.entities = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( super.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( super.getLocale().getLanguage(), super.getCorpId() );
      this.branchs = KANConstants.getKANAccountConstants( super.getAccountId() ).getBranchs( super.getLocale().getLanguage(), super.getCorpId() );
      if ( this.entities != null )
      {
         this.entities.add( 0, super.getEmptyMappingVO() );
      }
      if ( this.businessTypes != null )
      {
         this.businessTypes.add( 0, super.getEmptyMappingVO() );
      }
      if ( this.branchs != null )
      {
         this.branchs.add( 0, super.getEmptyMappingVO() );
      }
   }

   public String getBranchId()
   {
      return branchId;
   }

   public void setBranchId( String branchId )
   {
      this.branchId = branchId;
   }

   public String getBranchCode()
   {
      return branchCode;
   }

   public void setBranchCode( String branchCode )
   {
      this.branchCode = branchCode;
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

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getParentBranchId()
   {
      return KANUtil.filterEmpty( parentBranchId ) == null ? "0" : parentBranchId;
   }

   public void setParentBranchId( String parentBranchId )
   {
      this.parentBranchId = parentBranchId;
   }

   public String getParentBranchName()
   {
      return parentBranchName;
   }

   public void setParentBranchName( String parentBranchName )
   {
      this.parentBranchName = parentBranchName;
   }

   public String getEntityId()
   {
      return entityId;
   }

   public String getDecodeEntityId()
   {
      return decodeField( entityId, entities );
   }

   public void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   public String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = businessTypeId;
   }

   public String getSettlementBranch()
   {
      return settlementBranch;
   }

   public void setSettlementBranch( String settlementBranch )
   {
      this.settlementBranch = settlementBranch;
   }

   public List< String > getStaffIdsInBranch()
   {
      return staffIdsInBranch;
   }

   public void setStaffIdsInBranch( List< String > staffIdsInBranch )
   {
      this.staffIdsInBranch = staffIdsInBranch;
   }

   public String getBranchName()
   {

      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getNameZH();
         }
         else
         {
            return this.getNameEN();
         }
      }
      else
      {
         return this.getNameZH();
      }
   }
}
