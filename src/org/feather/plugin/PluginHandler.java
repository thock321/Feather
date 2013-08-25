package org.feather.plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PluginHandler {
	
	private static final String PLUGIN_DIR = "./Data/plugins/";
	
	private Map<String, Plugin> plugins = new HashMap<String, Plugin>();
	
	public void loadPlugins() throws ClassNotFoundException, IOException {
		loadPlugins(PLUGIN_DIR);
	}
	
	private void loadPlugins(String pluginDir) throws ClassNotFoundException, IOException {
		File pluginLoc = new File(pluginDir);
		for (File file : pluginLoc.listFiles()) {
			if (file.isDirectory()) {
				loadPlugins(file.getParent());
			} else {
				if (!file.toString().endsWith(".jar")) {
					continue;
				}
				Plugin plugin = new Plugin(file);
				plugins.put(plugin.getName(), plugin);
			}
			
		}
	}

}
