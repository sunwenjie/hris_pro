package com.kan.base.domain.security;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class PositionVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 4742760621841606184L;

   /**
    * For DB
    */
   private String positionId;

   private String locationId;

   private String branchId;

   private String positionGradeId;

   private String titleZH;

   private String titleEN;

   private String description;

   private String skill;

   private String note;

   private String attachment;

   private String parentPositionId;

   private String isVacant;

   private String isIndependentDisplay;

   private String needPublish;

   /**
    * For Application
    */
   @JsonIgnore
   private String parentPositionName;
   @JsonIgnore
   private String[] positionIdArray = new String[] {};
   @JsonIgnore
   private String[] groupIdArray = new String[] {};
   @JsonIgnore
   private String[] staffIdArray = new String[] {};
   @JsonIgnore
   private String[] moduleIdArray = new String[] {};
   @JsonIgnore
   private String[] rightIdArray = new String[] {};
   @JsonIgnore
   private String[] ruleIdArray = new String[] {};
   @JsonIgnore
   private String[] attachmentArray = new String[] {};
   @JsonIgnore
   private String[] employeeIdArray = new String[] {};
   @JsonIgnore
   private List< MappingVO > locations = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > branchs = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > positionGroups = new ArrayList< MappingVO >();
   @JsonIgnore
   private List< MappingVO > positionGrades = new ArrayList< MappingVO >();
   @JsonIgnore
   private String staffId;
   @JsonIgnore
   private String staffIds;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );

      try
      {
         if ( BaseAction.getRole( request, null ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            this.locations = KANConstants.getKANAccountConstants( this.getAccountId() ).getLocations( this.getLocale().getLanguage(), super.getCorpId() );
            this.branchs = KANConstants.getKANAccountConstants( this.getAccountId() ).getBranchs( this.getLocale().getLanguage(), super.getCorpId() );
            this.positionGrades = KANConstants.getKANAccountConstants( this.getAccountId() ).getPositionGrades( this.getLocale().getLanguage(), super.getCorpId() );
         }
         else
         {
            this.locations = KANConstants.getKANAccountConstants( this.getAccountId() ).getLocations( this.getLocale().getLanguage(), super.getCorpId() );
            this.branchs = KANConstants.getKANAccountConstants( this.getAccountId() ).getBranchs( this.getLocale().getLanguage(), super.getCorpId() );
            this.positionGrades = KANConstants.getKANAccountConstants( this.getAccountId() ).getPositionGrades( this.getLocale().getLanguage() );
         }
      }
      catch ( KANException e )
      {
         e.printStackTrace();
      }

      if ( this.locations != null )
      {
         this.locations.add( 0, super.getEmptyMappingVO() );
      }

      if ( this.branchs != null )
      {
         this.branchs.add( 0, super.getEmptyMappingVO() );
      }

      if ( this.positionGrades != null )
      {
         this.positionGrades.add( 0, super.getEmptyMappingVO() );
      }
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( positionId == null || positionId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( positionId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void update( Object object )
   {
      final PositionVO positionVO = ( PositionVO ) object;
      this.locationId = positionVO.getLocationId();
      this.branchId = positionVO.getBranchId();
      this.positionGradeId = positionVO.getPositionGradeId();
      this.titleZH = positionVO.getTitleZH();
      this.titleEN = positionVO.getTitleEN();
      this.description = positionVO.getDescription();
      this.skill = positionVO.getSkill();
      this.note = positionVO.getNote();
      this.attachment = positionVO.getAttachment();
      this.parentPositionId = positionVO.getParentPositionId();
      this.isVacant = positionVO.getIsVacant();
      this.isIndependentDisplay = positionVO.getIsIndependentDisplay();
      this.needPublish = positionVO.getNeedPublish();
      super.setStatus( positionVO.getStatus() );
      super.setModifyDate( new Date() );
      // For Application
      this.positionIdArray = positionVO.getPositionIdArray();
      this.groupIdArray = positionVO.getGroupIdArray();
      this.staffIdArray = positionVO.getStaffIdArray();
      this.moduleIdArray = positionVO.getModuleIdArray();
      this.rightIdArray = positionVO.getRightIdArray();
      this.ruleIdArray = positionVO.getRuleIdArray();
      this.attachmentArray = positionVO.getAttachmentArray();
      this.employeeIdArray = positionVO.getEmployeeIdArray();
   }

   public void reset()
   {
      this.locationId = "";
      this.branchId = "";
      this.positionGradeId = "";
      this.titleZH = "";
      this.titleEN = "";
      this.description = "";
      this.skill = "";
      this.note = "";
      this.attachment = "";
      this.parentPositionId = "";
      this.isVacant = "0";
      this.isIndependentDisplay = "";
      this.needPublish = "0";
      super.setStatus( "0" );
      // For Application
      this.parentPositionName = "";
      this.positionIdArray = new String[] {};
      this.groupIdArray = new String[] {};
      this.staffIdArray = new String[] {};
      this.moduleIdArray = new String[] {};
      this.rightIdArray = new String[] {};
      this.ruleIdArray = new String[] {};
      this.attachmentArray = new String[] {};
      this.employeeIdArray = new String[] {};
   }

   public String getPositionId()
   {
      return positionId;
   }

   public void setPositionId( String positionId )
   {
      this.positionId = positionId;
   }

   public String getLocationId()
   {
      return locationId;
   }

   public void setLocationId( String locationId )
   {
      this.locationId = locationId;
   }

   public String getBranchId()
   {
      return branchId;
   }

   public void setBranchId( String branchId )
   {
      this.branchId = branchId;
   }

   public String getPositionGradeId()
   {
      return positionGradeId;
   }

   public void setPositionGradeId( String positionGradeId )
   {
      this.positionGradeId = positionGradeId;
   }

   public String getTitleZH()
   {
      return titleZH;
   }

   public void setTitleZH( String titleZH )
   {
      this.titleZH = titleZH;
   }

   public String getTitleEN()
   {
      return titleEN;
   }

   public void setTitleEN( String titleEN )
   {
      this.titleEN = titleEN;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getSkill()
   {
      return skill;
   }

   public void setSkill( String skill )
   {
      this.skill = skill;
   }

   public String getNote()
   {
      return note;
   }

   public void setNote( String note )
   {
      this.note = note;
   }

   public String getAttachment()
   {
      return attachment;
   }

   public void setAttachment( String attachment )
   {
      this.attachment = attachment;
      this.attachmentArray = KANUtil.jasonArrayToStringArray( attachment );
   }

   public String getParentPositionId()
   {
      return KANUtil.filterEmpty( parentPositionId ) == null ? "0" : parentPositionId;
   }

   public void setParentPositionId( String parentPositionId )
   {
      this.parentPositionId = parentPositionId;
   }

   public String getIsVacant()
   {
      if ( KANUtil.filterEmpty( isVacant ) == null )
      {
         return "0";
      }
      return KANUtil.filterEmpty( isVacant );
   }

   public void setIsVacant( String isVacant )
   {
      this.isVacant = isVacant;
   }

   public String getNeedPublish()
   {
      return needPublish;
   }

   public void setNeedPublish( String needPublish )
   {
      this.needPublish = needPublish;
   }

   public List< MappingVO > getLocations()
   {
      return locations;
   }

   public void setLocations( List< MappingVO > locations )
   {
      this.locations = locations;
   }

   public List< MappingVO > getBranchs()
   {
      return branchs;
   }

   public void setBranchs( List< MappingVO > branchs )
   {
      this.branchs = branchs;
   }

   public List< MappingVO > getPositionGroups()
   {
      return positionGroups;
   }

   public void setPositionGroups( List< MappingVO > positionGroups )
   {
      this.positionGroups = positionGroups;
   }

   public List< MappingVO > getPositionGrades()
   {
      return positionGrades;
   }

   public void setPositionGrades( List< MappingVO > positionGrades )
   {
      this.positionGrades = positionGrades;
   }

   public String[] getGroupIdArray()
   {
      return groupIdArray;
   }

   public void setGroupIdArray( String[] groupIdArray )
   {
      this.groupIdArray = groupIdArray;
   }

   public String[] getStaffIdArray()
   {
      return staffIdArray;
   }

   public void setStaffIdArray( String[] staffIdArray )
   {
      this.staffIdArray = staffIdArray;
   }

   public String[] getModuleIdArray()
   {
      return moduleIdArray;
   }

   public void setModuleIdArray( String[] moduleIdArray )
   {
      this.moduleIdArray = moduleIdArray;
   }

   public String[] getRightIdArray()
   {
      return rightIdArray;
   }

   public void setRightIdArray( String[] rightIdArray )
   {
      this.rightIdArray = rightIdArray;
   }

   public String[] getRuleIdArray()
   {
      return ruleIdArray;
   }

   public void setRuleIdArray( String[] ruleIdArray )
   {
      this.ruleIdArray = ruleIdArray;
   }

   public String getParentPositionName()
   {
      return parentPositionName;
   }

   public void setParentPositionName( String parentPositionName )
   {
      this.parentPositionName = parentPositionName;
   }

   public String[] getPositionIdArray()
   {
      return positionIdArray;
   }

   public void setPositionIdArray( String[] positionIdArray )
   {
      this.positionIdArray = positionIdArray;
   }

   public String[] getAttachmentArray()
   {
      return attachmentArray;
   }

   public void setAttachmentArray( String[] attachmentArray )
   {
      this.attachmentArray = attachmentArray;
      this.attachment = KANUtil.toJasonArray( attachmentArray );
   }

   public String[] getEmployeeIdArray()
   {
      return employeeIdArray;
   }

   public void setEmployeeIdArray( String[] employeeIdArray )
   {
      this.employeeIdArray = employeeIdArray;
   }

   public String getDecodePositionGradeId()
   {
      if ( KANUtil.filterEmpty( positionGradeId ) != null )
      {
         return decodeField( positionGradeId, positionGrades );
      }
      return "";
   }

   public String getDecodeBranchId()
   {
      if ( KANUtil.filterEmpty( branchId ) != null )
      {
         return decodeField( branchId, KANConstants.getKANAccountConstants( this.getAccountId() ).getBranchs( this.getLocale().getLanguage(), super.getCorpId() ), true );
      }
      return "";
   }

   public String getDecodeBranchIdIsTemp()
   {
      if ( KANUtil.filterEmpty( branchId ) != null )
      {
         return decodeField( branchId, KANConstants.getKANAccountConstants( this.getAccountId() ).getBranchs( this.getLocale().getLanguage(), super.getCorpId() ), true );
      }
      return "";
   }

   public String getStaffIds()
   {
      return staffIds;
   }

   public void setStaffIds( String staffIds )
   {
      this.staffIds = staffIds;
   }

   public String getStaffId()
   {
      return staffId;
   }

   public void setStaffId( String staffId )
   {
      this.staffId = staffId;
   }

   public String getIsIndependentDisplay()
   {
      return isIndependentDisplay;
   }

   public void setIsIndependentDisplay( String isIndependentDisplay )
   {
      this.isIndependentDisplay = isIndependentDisplay;
   }

   public String getPositionName()
   {

      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getTitleZH();
         }
         else
         {
            return this.getTitleEN();
         }
      }
      else
      {
         return this.getTitleZH();
      }
   }
}
