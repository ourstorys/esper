package example.context;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import example.model.ESB;

public class ContextPropertiesTest2 {

	public static void main(String[] args) {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();
		EPRuntime runtime = epService.getEPRuntime();

		String esb = ESB.class.getName();
		// 创建context
		String epl1 = "create context esbtest partition by id and price from " + esb;
		// context.id针对不同的esb的id,price建立一个context，如果事件的id和price相同，则context.id也相同，即表明事件进入了同一个context
//		String epl2 = "context esbtest select context.id,context.name,context.key1,context.key2 from " + esb;
		String epl2 = "context esbtest select id,remark,price from "+ esb +".win:length_batch(2)";

		admin.createEPL(epl1);
		EPStatement state = admin.createEPL(epl2);
		state.addListener(new ContextPropertiesListener2());

		ESB e1 = new ESB();
		e1.setId(1);
		e1.setPrice(20);
		e1.setRemark("==23423sdf");
		System.out.println("sendEvent: id=1, price=20");
		runtime.sendEvent(e1);

		ESB e2 = new ESB();
		e2.setId(2);
		e2.setPrice(30);
		System.out.println("sendEvent: id=2, price=30");
		runtime.sendEvent(e2);

		ESB e3 = new ESB();
		e3.setId(1);
		e3.setPrice(20);
		e1.setRemark("---1122sa23423sdf");
		System.out.println("sendEvent: id=1, price=20");
		runtime.sendEvent(e3);

		ESB e4 = new ESB();
		e4.setId(4);
		e4.setPrice(20);
		System.out.println("sendEvent: id=4, price=20");
		runtime.sendEvent(e4);
	}
}
