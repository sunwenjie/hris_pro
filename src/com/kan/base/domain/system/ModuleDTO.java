package com.kan.base.domain.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ȫ��ModuleDTO
 * @author Kevin
 */
public class ModuleDTO implements Serializable
{
   /**  
   * Serial Version UID
   *  
   * @since Ver 1.1  
   */

   private static final long serialVersionUID = -5737362422742171158L;

   private ModuleVO moduleVO = new ModuleVO();

   // ��������Module��
   private List< ModuleDTO > moduleDTOs = new ArrayList< ModuleDTO >();

   /**
    * For Application 
    */
   public ModuleVO getModuleVO()
   {
      return moduleVO;
   }

   public void setModuleVO( ModuleVO moduleVO )
   {
      this.moduleVO = moduleVO;
   }

   public List< ModuleDTO > getModuleDTOs()
   {
      return moduleDTOs;
   }

   public void setModuleDTOs( List< ModuleDTO > moduleDTOs )
   {
      this.moduleDTOs = moduleDTOs;
   }

   public static List< Map< String, Object >> iterator( List< ModuleDTO > moduleDTOs, IModuleDTOIterator interfaceDO )
   {
      List< Map< String, Object >> retList = new ArrayList< Map< String, Object > >();
      List< Integer > indexs = new ArrayList< Integer >();
      List< ModuleDTO > stackModules = new ArrayList< ModuleDTO >();
      for ( int i = 0; i < moduleDTOs.size(); i++ )
      {
         if ( stackModules.size() > 0 )
         {
            indexs.set( 0, i );
            ModuleDTO moduleDTO = moduleDTOs.get( i );
            stackModules.set( 0, moduleDTO );
         }
         else
         {
            indexs.add( 0 );
            ModuleDTO moduleDTO = moduleDTOs.get( i );
            stackModules.add( moduleDTO );
         }
         //�ȸ�����
         Map< String, Object > returnMap = interfaceDO.doReturn( stackModules );
         if ( returnMap != null )
         {
            retList.add( returnMap );
         }
         while ( true )
         {
            //���ж��Ƿ��������ֵܽڵ�
            if ( stackModules.size() == indexs.size() )
            //�������ֵܽڵ�
            {
            // stackModules ���һ��DTO��DTOs��size �Ƿ������
               boolean hasChild = stackModules.get( stackModules.size() - 1 ).getModuleDTOs().size() > 0;
               if ( hasChild == false )
               {
                  int stackSize = stackModules.size();
                  if ( stackSize > 1 )//����ջ��Ԫ��
                  {
                     //��ǰ�ڵ�����ڹ������ڵ���±�
                     int childIndex = indexs.get( stackSize - 1 );
                     //��ǰ�ڵ�Ĺ������ڵ�
                     ModuleDTO parentDTO = stackModules.get( stackSize - 2 );
                     //���ڵ���ӽڵ�����-1 ���� ��ǰ�ڵ�����ڹ������ڵ���±�  ��˵�����ֵܽڵ�
                     if ( parentDTO.getModuleDTOs().size() - 1 > childIndex )
                     {
                        //ջ���滻
                        indexs.set( stackSize - 1, childIndex + 1 );
                        ModuleDTO nextChildDTO = parentDTO.getModuleDTOs().get( childIndex + 1 );
                        stackModules.set( stackSize - 1, nextChildDTO );
                        //�ȸ�����
                        Map< String, Object > returnMap2 = interfaceDO.doReturn( stackModules );
                        if ( returnMap2 != null )
                        {
                           retList.add( returnMap2 );
                        }
                     }
                     else
                     //û���ֵܽڵ�
                     {
                        //��ջ
                        stackModules.remove( stackSize - 1 );
                        // ջ��Ԫ�ز���ջ�����Ǳ��Ϊ-1 �� �´�ѭ�������ֵܽڵ�
                        indexs.set( indexs.size() - 1, -1 );
                     }

                  }
                  else
                  //��ջ��Ԫ��
                  {
                     if(stackModules.size() != indexs.size()){
                        //System.out.println("-----0101-------");
                     }else{
                        //System.out.println("-----0102-------");
                     }
                     break;
                  }
               }
               else
               {
                  ModuleDTO mDTO = stackModules.get( stackModules.size() - 1 );
                  //��ջ
                  indexs.add( 0 );
                  stackModules.add( mDTO.getModuleDTOs().get( 0 ) );
                  //�ȸ�����
                  Map< String, Object > returnMap2 = interfaceDO.doReturn( stackModules );
                  if ( returnMap2 != null )
                  {
                     retList.add( returnMap2 );
                  }
               }
            }else{
               
               if(indexs.get( indexs.size()-1 )==-1){
                  //System.out.println("-------ֱ�����ֵܽڵ�-------");
               }else{
                  //System.out.println("-------����ֱ�����ֵܽڵ�-------");
               }
               
               
               //���ֵܽڵ�
               int stackSize = stackModules.size();
               if ( stackSize > 1 )//����ջ��Ԫ��
               {
                  //��ǰ�ڵ�����ڹ������ڵ���±�
                  int childIndex = indexs.get( stackSize - 1 );
                  //��ǰ�ڵ�Ĺ������ڵ�
                  ModuleDTO parentDTO = stackModules.get( stackSize - 2 );
                  //���ڵ���ӽڵ�����-1 ���� ��ǰ�ڵ�����ڹ������ڵ���±�  ��˵�����ֵܽڵ�
                  if ( parentDTO.getModuleDTOs().size() - 1 > childIndex )
                  {
                     //ջ���滻
                     indexs.remove( stackSize );
                     indexs.set( stackSize - 1, childIndex + 1 );
                     ModuleDTO nextChildDTO = parentDTO.getModuleDTOs().get( childIndex + 1 );
                     stackModules.set( stackSize - 1, nextChildDTO );
                     //�ȸ�����
                     Map< String, Object > returnMap2 = interfaceDO.doReturn( stackModules );
                     if ( returnMap2 != null )
                     {
                        retList.add( returnMap2 );
                     }
                  }
                  else
                  //û���ֵܽڵ�
                  {
                     //��ջ
                     stackModules.remove( stackSize - 1 );
                     // ջ��Ԫ�ز���ջ�����Ǳ��Ϊ-1 �� �´�ѭ�������ֵܽڵ�
                     indexs.remove( stackSize-1 );
                     indexs.set( indexs.size() - 1, -1 );
                  }

               }
               else
               //��ջ��Ԫ��
               {
                  if(stackModules.size() != indexs.size()){
                     indexs.remove( stackSize-1 );
                     //System.out.println("-----0201-------");
                  }else{
                     //System.out.println("-----0202-------");
                  }
                  break;
               }
            }
            

         }
      }
      return retList;
   }

}
