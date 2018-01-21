package cn.itcast.core.service.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.Product;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.service.CmsService;
import cn.itcast.core.service.StaticPageService;
import cn.itcast.core.service.product.SerachService;

/**
 * 接收MQ中的消息 
 * @author lx
 *
 */
public class CustomMessageListener  implements MessageListener{

	@Autowired
	private StaticPageService staticPageService;
	@Autowired
	private CmsService cmsService;
	//静态化
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		ActiveMQTextMessage amtm  = (ActiveMQTextMessage)message;
		try {
			String id = amtm.getText();
			System.out.println("CMS接收到的ID:" + id);
			
			
			Map<String,Object> root = new HashMap<>();
			//商品
			Product product = cmsService.selectProductById(Long.parseLong(id));
			
			root.put("product", product);
			//结果集   9，9，14，14，29，29，29
			List<Sku> skus = cmsService.selectSkuListByProductIdWithStock(Long.parseLong(id));
		
			//去掉重复的
			Set<Color> colors = new HashSet<Color>();
			for (Sku sku : skus) {
				colors.add(sku.getColor());
			}
			root.put("skus", skus);
			root.put("colors", colors);
			
			staticPageService.index(root, id);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
