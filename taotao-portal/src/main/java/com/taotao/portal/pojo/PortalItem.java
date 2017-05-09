package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

/**
 * Created by Administrator on 2017/4/24.
 */
public class PortalItem extends TbItem{
        public String[] getImages(){
            String images = this.getImage();
            if(null != images && !"".equals(images)){
                String[] imgs = images.split(",");
                return imgs;
            }
            return null;
        }
}
