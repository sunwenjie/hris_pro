package com.kan.base.domain.management;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class PositionVO extends BaseVO
{

   /**
    * Serial VersionUID
    */
   private static final long serialVersionUID = -6491913361155110721L;

   // 职位ID
   private String positionId;

   // 职级
   private String positionGradeId;

   // 职位中文名称
   private String titleZH;

   // 职位英文名称
   private String titleEN;

   // 职位描述
   private String description;

   // 技能
   private String skill;

   // 备注
   private String note;

   // 附件
   private String attachment;

   // 父节点ID
   private String parentPositionId;

   // 父节点名称
   private String parentPositionName;

   /**
    * For App
    */
   // 职组内包含的职位
   private String[] skillIdArray;

   // 删除position用
   private String[] positionIdArray;

   // 附件列表
   private String[] attachmentArray;

   private List< MappingVO > positionGrades = new ArrayList< MappingVO >();

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

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );

      this.positionGrades = KANConstants.getKANAccountConstants( this.getAccountId() ).getEmployeePositionGrades( this.getLocale().getLanguage() );
      if ( this.positionGrades != null )
      {
         this.positionGrades.add( 0, super.getEmptyMappingVO() );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.titleZH = "";
      this.titleEN = "";
      this.description = "";
      this.skill = "";
      this.note = "";
      this.attachment = "";
      this.positionGradeId = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final PositionVO positionVO = ( PositionVO ) object;
      this.positionGradeId = positionVO.getPositionGradeId();
      this.titleZH = positionVO.titleZH;
      this.titleEN = positionVO.titleEN;
      this.description = positionVO.description;
      this.skill = positionVO.skill;
      this.note = positionVO.note;
      this.attachment = positionVO.attachment;
      super.setStatus( positionVO.getStatus() );
      super.setModifyBy( positionVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public String getParentPositionName()
   {
      return parentPositionName;
   }

   public void setParentPositionName( String parentPositionName )
   {
      this.parentPositionName = parentPositionName;
   }

   public String getPositionId()
   {
      return positionId;
   }

   public void setPositionId( String positionId )
   {
      this.positionId = positionId;
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
      this.skillIdArray = KANUtil.jasonArrayToStringArray( skill );
   }

   public String getPositionGradeId()
   {
      return positionGradeId;
   }

   public void setPositionGradeId( String positionGradeId )
   {
      this.positionGradeId = positionGradeId;
   }

   public String getNote()
   {
      return note;
   }

   public void setNote( String note )
   {
      this.note = note;
   }

   public String getParentPositionId()
   {
      return KANUtil.filterEmpty( parentPositionId ) == null ? "0" : parentPositionId;
   }

   public void setParentPositionId( String parentPositionId )
   {
      this.parentPositionId = parentPositionId;
   }

   public String[] getSkillIdArray()
   {
      return skillIdArray;
   }

   public void setSkillIdArray( String[] skillIdArray )
   {
      this.skillIdArray = skillIdArray;
      this.skill = KANUtil.toJasonArray( skillIdArray );
   }

   public String[] getPositionIdArray()
   {
      return positionIdArray;
   }

   public void setPositionIdArray( String[] positionIdArray )
   {
      this.positionIdArray = positionIdArray;
   }

   public void setAttachment( String attachment )
   {
      this.attachment = attachment;
      this.attachmentArray = KANUtil.jasonArrayToStringArray( attachment );
   }

   public String getAttachment()
   {
      return attachment;
   }

   public void setAttachmentArray( String[] attachmentArray )
   {
      this.attachmentArray = attachmentArray;
      this.attachment = KANUtil.toJasonArray( attachmentArray );
   }

   public String[] getAttachmentArray()
   {
      return attachmentArray;
   }

   public List< MappingVO > getPositionGrades()
   {
      return positionGrades;
   }

   public void setPositionGrades( List< MappingVO > positionGrades )
   {
      this.positionGrades = positionGrades;
   }

}
