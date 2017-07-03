package com.kan.hro.domain.biz.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientGroupDTO implements Serializable
{

   /**  
   * Serial Version UID  
   */
   private static final long serialVersionUID = 3249842900421942863L;

   // ��Ӧ����
   private ClientGroupVO clientGroupVO;

   // ���ſͻ� - BaseView
   private List< ClientBaseView > clientBaseViews = new ArrayList< ClientBaseView >();

   // ���ſͻ� - DTO
   private List< ClientDTO > clientDTOs = new ArrayList< ClientDTO >();

   public ClientGroupVO getClientGroupVO()
   {
      return clientGroupVO;
   }

   public void setClientGroupVO( ClientGroupVO clientGroupVO )
   {
      this.clientGroupVO = clientGroupVO;
   }

   public List< ClientBaseView > getClientBaseViews()
   {
      return clientBaseViews;
   }

   public void setClientBaseViews( List< ClientBaseView > clientBaseViews )
   {
      this.clientBaseViews = clientBaseViews;
   }

   public List< ClientDTO > getClientDTOs()
   {
      return clientDTOs;
   }

   public void setClientDTOs( List< ClientDTO > clientDTOs )
   {
      this.clientDTOs = clientDTOs;
   }

}
