package com.kan.wx.domain;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import net.sf.json.JSONObject;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class AnswerVO extends BaseVO
{
   /**
    * SerialVersionUID
    */
   private static final long serialVersionUID = -1947201392027919898L;

   // For DB
   private String answerId;
   private String headerId;
   private String weChatId;
   private String answer;
   private String submitDate;

   // For app
   private String employeeNameZH;
   private String employeeNameEN;
   private String buFunction;
   private String department;
   private String bizEmail;
   private String employeeRemark1;

   // 部门
   private List< MappingVO > branchs = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      if ( KANUtil.filterEmpty( getAccountId() ) == null )
      {
         super.setAccountId( "100017" );
         super.setCorpId( "300115" );
      }
      super.reset( mapping, request );
      this.branchs = KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( request.getLocale().getLanguage(), super.getCorpId() );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( answerId );
   }

   @Override
   public void reset() throws KANException
   {
      // TODO Auto-generated method stub
   }

   @Override
   public void update( Object object ) throws KANException
   {
      // TODO Auto-generated method stub
   }

   public String getAnswerId()
   {
      return answerId;
   }

   public void setAnswerId( String answerId )
   {
      this.answerId = answerId;
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getWeChatId()
   {
      return weChatId;
   }

   public void setWeChatId( String weChatId )
   {
      this.weChatId = weChatId;
   }

   public String getAnswer()
   {
      return answer;
   }

   public void setAnswer( String answer )
   {
      this.answer = answer;
   }

   public String getSubmitDate()
   {
      return KANUtil.formatDate( submitDate, "yyyy-MM-dd hh:mm:ss" );
   }

   public void setSubmitDate( String submitDate )
   {
      this.submitDate = submitDate;
   }

   public String getEmployeeNameZH()
   {
      return employeeNameZH;
   }

   public void setEmployeeNameZH( String employeeNameZH )
   {
      this.employeeNameZH = employeeNameZH;
   }

   public String getEmployeeNameEN()
   {
      return employeeNameEN;
   }

   public void setEmployeeNameEN( String employeeNameEN )
   {
      this.employeeNameEN = employeeNameEN;
   }

   public String getBuFunction()
   {
      return buFunction;
   }

   public void setBuFunction( String buFunction )
   {
      this.buFunction = buFunction;
   }

   public String getDepartment()
   {
      return department;
   }

   public void setDepartment( String department )
   {
      this.department = department;
   }

   public String getBizEmail()
   {
      return bizEmail;
   }

   public void setBizEmail( String bizEmail )
   {
      this.bizEmail = bizEmail;
   }

   public String getEmployeeRemark1()
   {
      return employeeRemark1;
   }

   public void setEmployeeRemark1( String employeeRemark1 )
   {
      this.employeeRemark1 = employeeRemark1;
   }

   public List< MappingVO > getBranchs()
   {
      return branchs;
   }

   public void setBranchs( List< MappingVO > branchs )
   {
      this.branchs = branchs;
   }

   public String getDecodeDepartment()
   {
      return decodeBranchIds( department );
   }

   public String getDecodeBuFunction()
   {
      return decodeBranchIds( buFunction );
   }

   public String decodeBranchIds( final String branchIds )
   {
      String returnStr = "";
      if ( KANUtil.filterEmpty( branchIds ) != null && branchs != null && branchs.size() > 0 )
      {
         for ( String branchId : branchIds.split( "," ) )
         {
            if ( KANUtil.filterEmpty( returnStr ) == null )
            {
               returnStr = decodeField( branchId, branchs, true );
            }
            else
            {
               returnStr = returnStr + "、" + decodeField( branchId, branchs, true );
            }
         }
      }

      return returnStr;
   }

   // 特殊 办公地点
   public String getDecodeLocation() throws KANException
   {
      if ( KANUtil.filterEmpty( this.employeeRemark1 ) == null )
         return "";
      final JSONObject o = JSONObject.fromObject( this.employeeRemark1 );
      final TableDTO tableDTO = KANConstants.getKANAccountConstants( super.getAccountId() ).getTableDTOByAccessAction( "HRO_BIZ_EMPLOYEE_IN_HOUSE" );
      if ( o != null && o.get( "bangongdidian" ) != null && tableDTO != null )
      {
         final List< MappingVO > mappingVOs = KANUtil.getColumnOptionValues( super.getLocale(), tableDTO.getColumnVOByColumnId( "11257" ), super.getAccountId(), super.getCorpId() );
         return decodeField( o.get( "bangongdidian" ).toString(), mappingVOs );
      }

      return "";
   }

   public String getShortName()
   {
      if ( KANUtil.filterEmpty( this.employeeRemark1 ) == null )
         return "";
      final JSONObject o = JSONObject.fromObject( this.employeeRemark1 );
      if ( o != null && o.get( "jiancheng" ) != null )
      {
         return o.getString( "jiancheng" );
      }

      return "";
   }

}
