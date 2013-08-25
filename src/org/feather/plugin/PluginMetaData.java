package org.feather.plugin;

public class PluginMetaData {
	
	private String id, name, description;
	private String[] authors, dependencies;
	private int version;
	
	public PluginMetaData(String id, String name, String description, String[] authors, String[] dependencies, int version) {
		this.id = id;
		this.dependencies = dependencies;
		this.name = name;
		this.description = description;
		this.authors = authors;
		this.version = version;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String[] getAuthors() {
		return authors;
	}
	
	public String[] getDependencies() {
		return dependencies;
	}
	
	public int getVersion() {
		return version;
	}

}
