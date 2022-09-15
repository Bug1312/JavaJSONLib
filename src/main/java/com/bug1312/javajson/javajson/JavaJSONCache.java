package com.bug1312.javajson.javajson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;

public class JavaJSONCache implements ISelectiveResourceReloadListener {

	protected static Map<IUseJavaJSON, ResourceLocation> reloadableModels = new HashMap<>();
	protected static Map<ResourceLocation, JavaJSONParsed> bakedCache = new HashMap<>();
	protected static List<ResourceLocation> unbakedCache = new ArrayList<>();	

	protected static void init() {
		if (Minecraft.getInstance() != null && Minecraft.getInstance().getResourceManager() != null) {
			IReloadableResourceManager manager = (IReloadableResourceManager) Minecraft.getInstance().getResourceManager();
			manager.registerReloadListener(new JavaJSONCache());
		}
	}
	
	public static void register(IUseJavaJSON part, ResourceLocation model) {
		JavaJSONCache.reloadableModels.put(part, model);
		part.reload();
	}
		
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
		JavaJSONCache.bakedCache.clear();
		
		for (ResourceLocation location : JavaJSONCache.unbakedCache) JavaJSONParser.loadModel(location);
		
		for (Map.Entry<ResourceLocation, JavaJSONParsed> entry : JavaJSONCache.bakedCache.entrySet()) entry.getValue().load();
		for (Map.Entry<IUseJavaJSON, ResourceLocation> entry : JavaJSONCache.reloadableModels.entrySet()) entry.getKey().reload();
	}
	
}
