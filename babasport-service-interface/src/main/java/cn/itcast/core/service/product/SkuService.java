package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.core.bean.product.Sku;

public interface SkuService {
	
	//查询SKu结果集
	public List<Sku> selectSkuListByProductId(Long productId);
	
	public void updateSkuById(Sku sku);

}
