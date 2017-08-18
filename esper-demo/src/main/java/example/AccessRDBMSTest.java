package example;

import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * 
 * 
 * @author luonanqin
 */

class Student {
	private int sid;
	private String name;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

class StudentListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
		 Map<String, Object> map = 	(HashMap<String, Object>) newEvents[0].getUnderlying();
			System.out.println(newEvents.length+"/"+map.toString());
		}
	}
}

public class AccessRDBMSTest {

	public static void main(String[] args) throws InterruptedException {
		Configuration config = new Configuration();
		config.configure("esper.examples.cfg.xml");
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);

		EPAdministrator admin = epService.getEPAdministrator();
		EPRuntime runtime = epService.getEPRuntime();
		String epl1 = "select id,sid, name from " + Student.class.getName() + ",sql:test['select id from test1 where id=${sid}']";
		System.out.println(epl1);
		EPStatement state1 = admin.createEPL(epl1);
		state1.addListener(new StudentListener());

		Student s1 = new Student();
		s1.setSid(3);
		s1.setName("name1");
		runtime.sendEvent(s1);
		
		Student s2 = new Student();
		s2.setSid(4);
		s2.setName("name2");
		runtime.sendEvent(s2);
		
		Student s3 = new Student();
		s3.setSid(5);
		s3.setName("name3");
		runtime.sendEvent(s3);
	}
}
