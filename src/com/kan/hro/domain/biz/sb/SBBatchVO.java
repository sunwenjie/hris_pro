package com.kan.hro.domain.biz.sb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�BatchVO  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-9-11  
 */
public class SBBatchVO extends BaseVO
{

   // serialVersionUID  
   private static final long serialVersionUID = 8535391816205528726L;

   // ����Id
   private String batchId;

   // ����ʵ��Id
   private String entityId;

   // ҵ������Id
   private String businessTypeId;

   // �籣����Id��������������籣���в���
   private String cityId;

   // ����Id
   private String orderId;

   // ����Э��Id
   private String contractId;

   // �˵��·�
   private String monthly;

   // ��ʼʱ�� - ָBatch���е�ʱ��
   private String startDate;

   // ����ʱ�� - ָBatch���е�ʱ��
   private String endDate;

   // ����
   private String description;

   //�籣��������
   private String sbType;

   /**
    * For App
    */
   // �ͻ�����
   private String clientName;

   // �������
   private String clientNumber;

   // flag �籣ʵ�ʽ��ɱ�ʶ��0������1δ����
   private String flag;

   // ������Ա�����ϣ�MappingVO��mappingId��Ӧ��ԱID��mappingValue��Ӧ��Ա��������mappingTemp��Ӧ��ԱӢ������
   private List< MappingVO > employees = new ArrayList< MappingVO >();

   // ����ʵ��
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   // ҵ������
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   // �·�
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   // ��Ӧ��
   private List< MappingVO > vendors = new ArrayList< MappingVO >();

   //�籣�������� 1������ 2�籣 3����
   private List< MappingVO > sbTypes = new ArrayList< MappingVO >();

   // �籣״̬
   private List< MappingVO > sbStatuses = new ArrayList< MappingVO >();

   // �籣״̬������ʽ
   private String[] sbStatusArray = new String[] {};

   private List< MappingVO > flags = new ArrayList< MappingVO >();

   // ��Ӧ��ID
   private String vendorId;

   // ���˷��û���
   private String amountPersonal;

   // ��˾���û���
   private String amountCompany;

   // �����ͻ�����
   private String countClientId;

   // �����籣��������
   private String countHeaderId;

   // ��������Э������
   private String countContractId;

   // ������������
   private String countOrderId;

   // ������Ŀ����
   private String countItemId;

   private String provinceId;

   private String cityIdTemp;

   // ����״̬ - �籣������ϸ����С״̬
   private String additionalStatus;

   private List< MappingVO > provinces = new ArrayList< MappingVO >();

   /**
    * ҳ����ת���ֶ�
    */
   // �籣����Id
   private String headerId;

   // �籣������ϸId
   private String detailId;

   // ��ǰҳ��״̬
   private String statusFlag;

   // ��ǰҳ������
   private String pageFlag;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.sb.header.status" ) );
      this.sbStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.contract.sb.statuses" );
      this.sbTypes = KANUtil.getMappings( this.getLocale(), "def.socialBenefit.solution.sbType" );
      //ֻ�������걨�ӱ����������ɡ����걨�˱�
      if ( this.sbStatuses != null )
      {
         Iterator< MappingVO > iterator = sbStatuses.iterator();
         while ( iterator.hasNext() )
         {
            MappingVO m = ( MappingVO ) iterator.next();
            if ( "0".equals( m.getMappingId() ) || "1".equals( m.getMappingId() ) || "4".equals( m.getMappingId() ) || "6".equals( m.getMappingId() )
                  || "7".equals( m.getMappingId() ) )
            {
               iterator.remove();
            }
         }
      }

      this.provinces = KANConstants.LOCATION_DTO.getProvinces( this.getLocale().getLanguage() );
      this.monthlies = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast4Months( this.getLocale().getLanguage(), super.getCorpId() );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );

      // ���"��ѡ��"ѡ��
      if ( this.provinces != null )
      {
         this.provinces.add( 0, getEmptyMappingVO() );
      }

      if ( this.monthlies != null )
      {
         this.monthlies.add( 0, getEmptyMappingVO() );
      }

      if ( this.entitys != null )
      {
         this.entitys.add( 0, getEmptyMappingVO() );
      }

      if ( this.businessTypes != null )
      {
         this.businessTypes.add( 0, getEmptyMappingVO() );
      }

      // ��Ա��������
      if ( this.employees != null && this.employees.size() > 1 )
      {
         Collections.sort( employees, new Comparator< MappingVO >()
         {
            @Override
            public int compare( MappingVO o1, MappingVO o2 )
            {
               // ������������
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  return o1.getMappingValue().compareTo( o2.getMappingValue() );
               }
               // Ӣ������
               else
               {
                  if ( o1.getMappingTemp() == null || o2.getMappingTemp() == null )
                     return 0;
                  return o1.getMappingTemp().compareTo( o2.getMappingTemp() );
               }
            }
         } );
      }

      flags = KANUtil.getMappings( getLocale(), "sb.status.flag" );
   }

   @Override
   public void update( final Object object )
   {
      final SBBatchVO sbBatchVO = ( SBBatchVO ) object;
      this.entityId = sbBatchVO.getEntityId();
      this.businessTypeId = sbBatchVO.getBusinessTypeId();
      this.cityId = sbBatchVO.getCityId();
      this.orderId = sbBatchVO.getOrderId();
      this.contractId = sbBatchVO.getContractId();
      this.monthly = sbBatchVO.getMonthly();
      this.description = sbBatchVO.getDescription();
      super.setStatus( sbBatchVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.entityId = "0";
      this.businessTypeId = "0";
      this.cityId = "0";
      this.orderId = "";
      this.contractId = "";
      this.monthly = "0";
      this.startDate = "";
      this.endDate = "";
      this.description = "";
      super.setClientId( "" );
      super.setStatus( "0" );
   }

   public String getDecodeCityId()
   {
      return KANConstants.LOCATION_DTO.getCityName( cityId, super.getLocale().getLanguage() );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( batchId );
   }

   // ����ʵ��ID����
   public String getEncodedEntityId() throws KANException
   {
      return encodedField( entityId );
   }

   // ҵ�����ͱ���
   public String getEncodedBusinessTypeId() throws KANException
   {
      return encodedField( businessTypeId );
   }

   // ����ID����
   public String getEncodedOrderId() throws KANException
   {
      return encodedField( orderId );
   }

   // ��÷���ʵ������
   public String getDecodeEntityId() throws KANException
   {
      return decodeField( this.entityId, this.entitys );
   }

   // ���ҵ����������
   public String getDecodeBusinessTypeId() throws KANException
   {
      return decodeField( this.businessTypeId, this.businessTypes );
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getEntityId()
   {
      return entityId;
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

   public String getCityId()
   {
      return cityId;
   }

   public void setCityId( String cityId )
   {
      this.cityId = cityId;
   }

   public String getOrderId()
   {
      return KANUtil.filterEmpty( orderId );
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getStartDate()
   {
      return KANUtil.filterEmpty( decodeDatetime( this.startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return KANUtil.filterEmpty( decodeDatetime( this.endDate ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getEntitys()
   {
      return entitys;
   }

   public void setEntitys( List< MappingVO > entitys )
   {
      this.entitys = entitys;
   }

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
   }

   public String getProvinceId()
   {
      return provinceId;
   }

   public void setProvinceId( String provinceId )
   {
      this.provinceId = provinceId;
   }

   public String getCityIdTemp()
   {
      return cityIdTemp;
   }

   public void setCityIdTemp( String cityIdTemp )
   {
      this.cityIdTemp = cityIdTemp;
   }

   public List< MappingVO > getProvinces()
   {
      return provinces;
   }

   public void setProvinces( List< MappingVO > provinces )
   {
      this.provinces = provinces;
   }

   public String getContractId()
   {
      return KANUtil.filterEmpty( contractId );
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getDetailId()
   {
      return detailId;
   }

   public void setDetailId( String detailId )
   {
      this.detailId = detailId;
   }

   public String getStatusFlag()
   {
      return statusFlag;
   }

   public void setStatusFlag( String statusFlag )
   {
      this.statusFlag = statusFlag;
   }

   public String getPageFlag()
   {
      return pageFlag;
   }

   public void setPageFlag( String pageFlag )
   {
      this.pageFlag = pageFlag;
   }

   public List< MappingVO > getMonthlies()
   {
      return monthlies;
   }

   public void setMonthlies( List< MappingVO > monthlies )
   {
      this.monthlies = monthlies;
   }

   public String getAmountPersonal()
   {
      return amountPersonal;
   }

   public void setAmountPersonal( String amountPersonal )
   {
      this.amountPersonal = amountPersonal;
   }

   public String getDecodeAmountPersonal()
   {
      return formatNumber( amountPersonal );
   }

   public String getAmountCompany()
   {
      return amountCompany;
   }

   public void setAmountCompany( String amountCompany )
   {
      this.amountCompany = amountCompany;
   }

   public String getDecodeAmountCompany()
   {
      return formatNumber( amountCompany );
   }

   public String getClientName()
   {
      return clientName;
   }

   public void setClientName( String clientName )
   {
      this.clientName = clientName;
   }

   public String getCountClientId()
   {
      return countClientId;
   }

   public void setCountClientId( String countClientId )
   {
      this.countClientId = countClientId;
   }

   public String getCountHeaderId()
   {
      return countHeaderId;
   }

   public void setCountHeaderId( String countHeaderId )
   {
      this.countHeaderId = countHeaderId;
   }

   public String getCountContractId()
   {
      return countContractId;
   }

   public void setCountContractId( String countContractId )
   {
      this.countContractId = countContractId;
   }

   public String getCountOrderId()
   {
      return countOrderId;
   }

   public void setCountOrderId( String countOrderId )
   {
      this.countOrderId = countOrderId;
   }

   public String getCountItemId()
   {
      return countItemId;
   }

   public void setCountItemId( String countItemId )
   {
      this.countItemId = countItemId;
   }

   public final String getAdditionalStatus()
   {
      return additionalStatus;
   }

   public final void setAdditionalStatus( String additionalStatus )
   {
      this.additionalStatus = additionalStatus;
   }

   public final String getDecodeAdditionalStatus()
   {
      return decodeField( this.additionalStatus, KANUtil.getMappings( this.getLocale(), "business.sb.header.status" ) );
   }

   public List< MappingVO > getVendors()
   {
      return vendors;
   }

   public void setVendors( List< MappingVO > vendors )
   {
      this.vendors = vendors;
   }

   public String getVendorId()
   {
      return vendorId;
   }

   public void setVendorId( String vendorId )
   {
      this.vendorId = vendorId;
   }

   public List< MappingVO > getEmployees()
   {
      return employees;
   }

   public void setEmployees( List< MappingVO > employees )
   {
      this.employees = employees;
   }

   // ���Ա������
   public String getCountEmployeeId()
   {
      return String.valueOf( employees.size() );
   }

   // ���Ա�����������ģ�����
   public String getEmployeeNameZHList()
   {
      final StringBuffer str = new StringBuffer();

      if ( employees != null && employees.size() > 0 )
      {
         for ( MappingVO mappingVO : employees )
         {
            str.append( mappingVO.getMappingValue() + "��" );
         }
         return str.substring( 0, str.length() - 1 );
      }

      return str.toString();
   }

   // ���Ա��������Ӣ�ģ�����
   public String getEmployeeNameENList()
   {
      final StringBuffer str = new StringBuffer();

      if ( employees != null && employees.size() > 0 )
      {
         for ( MappingVO mappingVO : employees )
         {
            str.append( mappingVO.getMappingValue() + "��" );
         }
         return str.substring( 0, str.length() - 1 );
      }

      return str.toString();
   }

   // ���Ա����������
   public String getEmployeeListSize()
   {
      return String.valueOf( this.employees.size() );
   }

   // ���Ա����������
   public String getEmployeeNameList()
   {
      final StringBuffer str = new StringBuffer();

      if ( employees != null && employees.size() > 0 )
      {

         // ���С�ڻ��ߵ���500����
         if ( employees.size() <= 500 )
         {
            if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               for ( MappingVO mappingVO : employees )
               {
                  str.append( mappingVO.getMappingValue() + "��" );
               }
               return str.substring( 0, str.length() - 1 );
            }
            else
            {
               for ( MappingVO mappingVO : employees )
               {
                  str.append( mappingVO.getMappingTemp() + "��" );
               }
               return str.substring( 0, str.length() - 1 );
            }
         }
         // �������500����ֻѡȡǰ��500����
         else
         {
            if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               for ( int i = 0; i < 500; i++ )
               {
                  str.append( employees.get( i ).getMappingValue() + "��" );
               }
               return str.substring( 0, str.length() - 1 ) + "...��";
            }
            else
            {
               for ( int i = 0; i < 500; i++ )
               {
                  str.append( employees.get( i ).getMappingValue() + "��" );
               }
               return str.substring( 0, str.length() - 1 ) + "...";
            }
         }
      }

      return str.toString();
   }

   // ��ò��ֹ�Ա��ǰ5����
   public String getEmployeeNameTop5List()
   {
      if ( employees != null )
      {
         if ( employees.size() <= 5 )
         {
            return getEmployeeNameList();
         }
         else
         {
            final StringBuffer str = new StringBuffer();

            if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               for ( int i = 0; i < 5; i++ )
               {
                  str.append( employees.get( i ).getMappingValue() + "��" );
               }
               return str.substring( 0, str.length() - 1 ) + "...��";
            }
            else
            {
               for ( int i = 0; i < 5; i++ )
               {
                  str.append( employees.get( i ).getMappingTemp() + "��" );
               }
               return str.substring( 0, str.length() - 1 ) + "...";
            }

         }
      }
      return null;
   }

   public String getClientNumber()
   {
      return clientNumber;
   }

   public void setClientNumber( String clientNumber )
   {
      this.clientNumber = clientNumber;
   }

   public List< MappingVO > getSbStatuses()
   {
      return sbStatuses;
   }

   public void setSbStatuses( List< MappingVO > sbStatuses )
   {
      this.sbStatuses = sbStatuses;
   }

   public List< MappingVO > getSbTypes()
   {
      return sbTypes;
   }

   public void setSbTypes( List< MappingVO > sbTypes )
   {
      this.sbTypes = sbTypes;
   }

   // ����籣��������
   public String getDecodeSBType() throws KANException
   {
      return decodeField( this.sbType, this.sbTypes );
   }

   public String[] getSbStatusArray()
   {
      return sbStatusArray;
   }

   public void setSbStatusArray( String[] sbStatusArray )
   {
      this.sbStatusArray = sbStatusArray;
   }

   public String getSbType()
   {
      return sbType;
   }

   public void setSbType( String sbType )
   {
      this.sbType = sbType;
   }

   public String getFlag()
   {
      return flag;
   }

   public void setFlag( String flag )
   {
      this.flag = flag;
   }

   public List< MappingVO > getFlags()
   {
      return flags;
   }

   public void setFlags( List< MappingVO > flags )
   {
      this.flags = flags;
   }

}
