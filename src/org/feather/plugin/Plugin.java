package org.feather.plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Plugin {
	
	public static class PluginComponent {

		private Method[] methods;
		private Field[] fields;
		
		public PluginComponent(Class<?> pluginClass) {
			methods = pluginClass.getMethods();
			for (Method method : methods) {
				method.setAccessible(true);
			}
			fields = pluginClass.getFields();
			for (Field field : fields) {
				field.setAccessible(true);
			}
		}
		
	}
	
	private Map<String, PluginComponent> components = new HashMap<String, PluginComponent>();
	
	private String name;
	
	public Plugin(File plugin) throws IOException, ClassNotFoundException {
		JarFile jar = null;
		name = plugin.getName();
		try {
			jar = new JarFile(plugin);
			URLClassLoader loader = new URLClassLoader(new URL[] {plugin.toURI().toURL()}, getClass().getClassLoader());
			for (Enumeration<JarEntry> entries = jar.entries(); entries.hasMoreElements();) {
				Class<?> toLoad = Class.forName(entries.toString(), true, loader);
				components.put(toLoad.getName(), new PluginComponent(toLoad));
			}
		} finally {
			jar.close();
		}
	}
	
	public String getName() {
		return name;
	}

}
