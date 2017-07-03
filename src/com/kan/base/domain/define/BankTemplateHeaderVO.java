package com.kan.base.domain.define;

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

public class BankTemplateHeaderVO extends BaseVO
{

   /**  
   * Serial Version UID:
   */
   private static final long serialVersionUID = -3134520756967281264L;

   // ����ģ������ID
   private String templateHeaderId;

   // ����ID
   private String bankId;

   // ����ģ��������
   private String nameZH;

   // ����ģ��Ӣ����
   private String nameEN;

   // ����
   private String description;

   /**
    * For Application
    */
   @JsonIgnore
   // �����б�
   private List< MappingVO > banks = new ArrayList< MappingVO >();

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.banks = KANConstants.getKANAccountConstants( getAccountId() ).getBanks( getLocale().getLanguage(), getCorpId() );
      if ( banks != null )
      {
         banks.add( 0, getEmptyMappingVO() );
      }
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( templateHeaderId );
   }

   @Override
   public void reset() throws KANException
   {
      this.bankId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final BankTemplateHeaderVO bankTemplateHeaderVO = ( BankTemplateHeaderVO ) object;
      this.bankId = bankTemplateHeaderVO.getBankId();
      this.nameZH = bankTemplateHeaderVO.getNameZH();
      this.nameEN = bankTemplateHeaderVO.getNameEN();
      this.description = bankTemplateHeaderVO.getDescription();
      super.setStatus( bankTemplateHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDecodeBank()
   {
      return decodeField( bankId, banks );
   }

   public String getTemplateHeaderId()
   {
      return templateHeaderId;
   }

   public void setTemplateHeaderId( String templateHeaderId )
   {
      this.templateHeaderId = templateHeaderId;
   }

   public String getBankId()
   {
      return bankId;
   }

   public void setBankId( String bankId )
   {
      this.bankId = bankId;
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

   public List< MappingVO > getBanks()
   {
      return banks;
   }

   public void setBanks( List< MappingVO > banks )
   {
      this.banks = banks;
   }

}
