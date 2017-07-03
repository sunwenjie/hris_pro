package com.kan.base.domain.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�SearchDTO  
* ��������  �����ֶ���ϸ��Ϣ
* �����ˣ�Jack  
* ����ʱ�䣺2013-6-30 ����08:38:30  
* �޸��ˣ�Jack  
* �޸�ʱ�䣺2013-6-30 ����08:38:30  
* �޸ı�ע��  
* @version   
*   
*/
public class SearchDTO implements Serializable
{

   /**  
   *  Serial Version UID
   *  
   * @since Ver 1.1  
   */

   private static final long serialVersionUID = 2044584991801164525L;

   // ��ǰSearchHeaderVO
   private SearchHeaderVO searchHeaderVO = new SearchHeaderVO();

   // ������SearchDetailVO
   private List< SearchDetailVO > searchDetailVOs = new ArrayList< SearchDetailVO >();

   public SearchHeaderVO getSearchHeaderVO()
   {
      return searchHeaderVO;
   }

   public void setSearchHeaderVO( SearchHeaderVO searchHeaderVO )
   {
      this.searchHeaderVO = searchHeaderVO;
   }

   public List< SearchDetailVO > getSearchDetailVOs()
   {
      return searchDetailVOs;
   }

   public void setSearchDetailVOs( List< SearchDetailVO > searchDetailVOs )
   {
      this.searchDetailVOs = searchDetailVOs;
   }

}
