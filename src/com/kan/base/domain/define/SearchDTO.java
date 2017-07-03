package com.kan.base.domain.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：SearchDTO  
* 类描述：  搜索字段详细信息
* 创建人：Jack  
* 创建时间：2013-6-30 下午08:38:30  
* 修改人：Jack  
* 修改时间：2013-6-30 下午08:38:30  
* 修改备注：  
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

   // 当前SearchHeaderVO
   private SearchHeaderVO searchHeaderVO = new SearchHeaderVO();

   // 包含的SearchDetailVO
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
