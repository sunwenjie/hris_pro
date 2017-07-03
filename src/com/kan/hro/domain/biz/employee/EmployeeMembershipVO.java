package com.kan.hro.domain.biz.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class EmployeeMembershipVO extends BaseVO
{

   /**
    * Serial Version UID
    */

   private static final long serialVersionUID = -3986770799631492651L;

   /**
    * For DB
    */

   // 雇员社会活动Id
   private String employeeMembershipId;

   // 员工Id
   private String employeeId;

   // 社会活动Id（绑定设置 - 资质 - 社会活动）
   private String membershipId;

   // 开始时间
   private String activeFrom;

   // 结束时间（不填表示当前进行中）   
   private String activeTo;

   // 描述
   private String description;

   /**
    * For Application
    */
   private List< MappingVO > membershipIds = new ArrayList< MappingVO >();

   // 社会活动
   private String membershipName;
   private String membershipNameZH;
   private String membershipNameEN;
   /**
    * 雇员姓名
    */
   private String employeeName;

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.membershipIds = KANConstants.getKANAccountConstants( super.getAccountId() ).getMemberships( super.getLocale().getLanguage(), super.getCorpId() );
   }

   @Override
   public void reset() throws KANException
   {
      this.employeeId = "";
      this.membershipId = "";
      this.activeFrom = "";
      this.activeTo = "";
      this.description = "";
      super.setStatus( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final EmployeeMembershipVO employeeMembershipVO = ( EmployeeMembershipVO ) object;
      this.membershipId = employeeMembershipVO.getMembershipId();
      this.activeFrom = employeeMembershipVO.getActiveFrom();
      this.activeTo = employeeMembershipVO.getActiveTo();
      this.description = employeeMembershipVO.getDescription();
      super.setStatus( employeeMembershipVO.getStatus() );
      super.setModifyBy( employeeMembershipVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public List< MappingVO > getMembershipIds()
   {
      return membershipIds;
   }

   public void setMembershipIds( List< MappingVO > membershipIds )
   {
      this.membershipIds = membershipIds;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getEmployeeMembershipId()
   {
      return employeeMembershipId;
   }

   public void setEmployeeMembershipId( String employeeMembershipId )
   {
      this.employeeMembershipId = employeeMembershipId;
   }

   public String getMembershipId()
   {
      return membershipId;
   }

   public void setMembershipId( String membershipId )
   {
      this.membershipId = membershipId;
   }

   public String getActiveFrom()
   {
      return KANUtil.formatDate( this.activeFrom, "yyyy-MM-dd", true );
   }

   public void setActiveFrom( String activeFrom )
   {
      this.activeFrom = activeFrom;
   }

   public String getActiveTo()
   {
      return KANUtil.formatDate( this.activeTo, "yyyy-MM-dd", true );
   }

   public void setActiveTo( String activeTo )
   {
      this.activeTo = activeTo;
   }

   public String getMembershipName()
   {
      return membershipName;
   }

   public void setMembershipName( String membershipName )
   {
      this.membershipName = membershipName;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeMembershipId );
   }

   public String getMembershipNameZH()
   {
      return membershipNameZH;
   }

   public void setMembershipNameZH( String membershipNameZH )
   {
      this.membershipNameZH = membershipNameZH;
   }

   public String getMembershipNameEN()
   {
      return membershipNameEN;
   }

   public void setMembershipNameEN( String membershipNameEN )
   {
      this.membershipNameEN = membershipNameEN;
   }

   public String getMembershipNameByLocale()
   {
      if ( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
      {
         return membershipNameZH;
      }
      else
      {
         return membershipNameEN;
      }
   }

   public String getDecodeMembershipName()
   {
      return decodeField( membershipId, membershipIds );
   }

   public String getEmployeeName()
   {
      return employeeName;
   }

   public void setEmployeeName( String employeeName )
   {
      this.employeeName = employeeName;
   }

   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( this.employeeId );
   }

   // Tab 显示
   public String getRemark()
   {
      String ret = getDecodeMembershipName() + " " + getActiveFrom() + " ~ ";

      if ( KANUtil.filterEmpty( activeTo ) != null )
      {
         ret = ret + getActiveTo();
      }
      else
      {
         ret = ret + ( super.getLocale() != null && "zh".equalsIgnoreCase( super.getLocale().getLanguage() ) ? "现在" : "now" );
      }

      return ret;
   }
}
