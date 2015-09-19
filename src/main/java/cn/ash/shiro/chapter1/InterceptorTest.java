package cn.ash.shiro.chapter1;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.junit.Test;

public class InterceptorTest {

	@Test
	public void testMapPluginIntercept() {
		Map map = new HashMap();
		map = (Map) new AlwaysMapPlugin().plugin(map);
		assertEquals("always", map.get("anything"));
	}

	@Intercepts({ @Signature(type = Map.class, method = "get", args = { Object.class }) })
	public static class AlwaysMapPlugin implements Interceptor {

		public Object intercept(Invocation invocation) throws Throwable {
			return "always";
		}

		public Object plugin(Object target) {
			return Plugin.wrap(target, this);
		}

		public void setProperties(Properties properties) {

		}
	}
}
