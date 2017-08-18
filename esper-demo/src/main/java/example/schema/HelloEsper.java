package example.schema;

import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class HelloEsper {
	public static void main(String[] args) {
		//
		// /* 设置配置信息 */
		// Configuration config = new Configuration();
		// config.addEventType("myEvent", MyEvent.class); //添加事件类型定义

		/* 创建引擎实例 */
		EPServiceProvider provider = EPServiceProviderManager.getDefaultProvider();

		/* 创建statement的管理接口实例 */
		EPAdministrator admin = provider.getEPAdministrator();
		admin.createEPL("create schema myEvent(id int,remark string)");// 通过create
																						// schema语法注册
																						// myEvent事件。
		EPStatement statement = admin.createEPL("select id, remark from myEvent"); // 创建EPL查询语句实例，功能：查询所有进入的myEvent事件
		statement.addListener(new UpdateListener() {
			// 为statement实例添加监听
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				System.out.println("----------------");
				for (EventBean eb : newEvents) {
					System.out.println("id:" + eb.get("id") + " remark:" + eb.get("remark"));
				}
				
			}
		});

		/* 引擎实例运行接口，负责为引擎实例接收数据并发送给引擎处理 */
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", 1);
		map.put("remark", "remark12312");
		provider.getEPRuntime().sendEvent(map, "myEvent");
		EPRuntime er = provider.getEPRuntime();
//		er.sendEvent(new MyEvent(1, "aaa")); // 发送事件
//		EPRuntime er1 = provider.getEPRuntime();
//		er1.sendEvent(new MyEvent(12, "aaa")); // 发送事件
	}

}
