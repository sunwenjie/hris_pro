package com.kan.base.domain.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SickLeaveSalaryDTO implements Serializable
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 5859559749107385348L;

   //��������
   private SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO;

   //���ٴӱ���
   private List< SickLeaveSalaryDetailVO > sickLeaveSalaryDetailVOs = new ArrayList< SickLeaveSalaryDetailVO >();

   public SickLeaveSalaryHeaderVO getSickLeaveSalaryHeaderVO()
   {
      return sickLeaveSalaryHeaderVO;
   }

   public void setSickLeaveSalaryHeaderVO( SickLeaveSalaryHeaderVO sickLeaveSalaryHeaderVO )
   {
      this.sickLeaveSalaryHeaderVO = sickLeaveSalaryHeaderVO;
   }

   public List< SickLeaveSalaryDetailVO > getSickLeaveSalaryDetailVOs()
   {
      return sickLeaveSalaryDetailVOs;
   }

   public void setSickLeaveSalaryDetailVOs( List< SickLeaveSalaryDetailVO > sickLeaveSalaryDetailVOs )
   {
      this.sickLeaveSalaryDetailVOs = sickLeaveSalaryDetailVOs;
   }

}
