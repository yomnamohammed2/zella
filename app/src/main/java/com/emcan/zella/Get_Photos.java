package com.emcan.zella;


import com.emcan.zella.Beans.Additions_Model;
import com.emcan.zella.Beans.Remove_Response;

import java.util.ArrayList;

public interface Get_Photos {

//    public void  get_Photos(ArrayList<Sub_Cat_Images_Model.Images> images);
//    public void  get_Comments(ArrayList<Review> reviews);
    public void  get_Additions(ArrayList<Additions_Model.Addition> additions);
    public void  get_Removes(ArrayList<Remove_Response.Removes> removes);

}
