package com.kan.base.domain.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**
 * @author Kevin Jin 
 */
public class RuleVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -1236822228026569017L;

   /**
    * For DB
    */
   private String ruleId;

   private String ruleType;

   private String nameZH;

   private String nameEN;

   /**
    * For Application
    */
   private List< MappingVO > ruleTypes = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.ruleTypes = KANUtil.getMappings( this.getLocale(), "sys.rule.types" );
   }

   public String getDecodeRuleType()
   {
      return decodeField( ruleType, ruleTypes );
   }

   @Override
   public void update( final Object object )
   {
      final RuleVO ruleVO = ( RuleVO ) object;
      this.ruleType = ruleVO.getRuleType();
      this.nameZH = ruleVO.getNameZH();
      this.nameEN = ruleVO.getNameEN();
      super.setStatus( ruleVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset()
   {
      this.ruleType = "0";
      this.nameZH = "";
      this.nameEN = "";
      super.setStatus( "0" );
   }

   public String getRuleId()
   {
      return ruleId;
   }

   public void setRuleId( String ruleId )
   {
      this.ruleId = ruleId;
   }

   public String getRuleType()
   {
      return ruleType;
   }

   public void setRuleType( String ruleType )
   {
      this.ruleType = ruleType;
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

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( ruleId );
   }

   public List< MappingVO > getRuleTypes()
   {
      return ruleTypes;
   }

   public void setRuleTypes( List< MappingVO > ruleTypes )
   {
      this.ruleTypes = ruleTypes;
   }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ruleId == null) ? 0 : ruleId.hashCode());
		result = prime * result + ((ruleType == null) ? 0 : ruleType.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuleVO other = (RuleVO) obj;
		if (ruleId == null) {
			if (other.ruleId != null)
				return false;
		} else if (!ruleId.equals(other.ruleId))
			return false;
		if (ruleType == null) {
			if (other.ruleType != null)
				return false;
		} else if (!ruleType.equals(other.ruleType))
			return false;
		return true;
	}

}
