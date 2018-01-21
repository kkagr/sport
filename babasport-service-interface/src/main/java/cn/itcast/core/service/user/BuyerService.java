package cn.itcast.core.service.user;

import cn.itcast.core.bean.user.Buyer;

public interface BuyerService {
	
	//查询用户对象 通过用户名
	public Buyer selectBuyerByusername(String username);

}
