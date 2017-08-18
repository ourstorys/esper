package example.context;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class ContextPropertiesListener2 implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			EventBean event = newEvents[0];
			System.out.println(event.get("price")+"--------"+event.get("id")+ event.get("remark")
					
					/*"context.name " + event.get("name") + ", context.id " + event.get("id")
					+ ", context.key1 " + event.get("key1") + ", context.key2 " + event.get("key2")+event.toString()*/);
		}
		
	}
}