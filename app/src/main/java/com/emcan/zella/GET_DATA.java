package com.emcan.zella;



import com.emcan.zella.Beans.Additions_Model;
import com.emcan.zella.Beans.Branch_Model;
import com.emcan.zella.Beans.Prices_Model;
import com.emcan.zella.Beans.Remove_Response;

import java.util.ArrayList;

public interface GET_DATA {

    public void  get_ID(Branch_Model.Branch branch);
    public void  remove_ID();

    public void  get_add(Additions_Model.Addition addition, int i);
    public void  get_without(Remove_Response.Removes remove_id, int i);

    public void  getSize(Prices_Model.Price size);
    public void  get_Price(ArrayList<Prices_Model.Price> prices_models);


}
