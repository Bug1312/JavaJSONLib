package com.swdteam.javajson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.swdteam.javajson.JavaJSONParsed.ModelInformation;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class JavaJSONParser {

	private static Gson GSON = new Gson();
	public static JavaJSONRenderer NULL_PART = new JavaJSONRenderer();
	
	public static JavaJSONParsed loadModel(ResourceLocation location) {
		if(!JavaJSONCache.unbakedCache.contains(location)) JavaJSONCache.unbakedCache.add(location);
		if (JavaJSONCache.bakedCache.containsKey(location)) return JavaJSONCache.bakedCache.get(location);
		
		JavaJSONParsed newModel = new JavaJSONParsed(location).load();
		JavaJSONCache.bakedCache.put(location, newModel);

		return newModel;
	}

	public static JavaJSONParsed.ModelInformation getModelInfo(ResourceLocation location) {
		try {
			InputStream stream = Minecraft.getInstance().getResourceManager().getResource(location).getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));			
			StringBuilder builder = new StringBuilder();
			String file = null;

			while ((file = reader.readLine()) != null) builder.append(file); 
			file = builder.toString();

			JavaJSONFile parsedFile = GSON.fromJson(file, JavaJSONFile.class);
			JavaJSONModel model = null;			

			if (parsedFile.getParent() != null) model = getModelInfo(parsedFile.getParent()).getModel();
			else {
				JavaJSONModel generatedModel = new JavaJSONModel(parsedFile.texWidth, parsedFile.texHeight, parsedFile.scale, parsedFile.fontData);

				for (JavaJSONFile.Group group : parsedFile.groups) {
					JavaJSONRenderer renderer = new JavaJSONRenderer(generatedModel);
					
					renderer.setPos(group.pivot[0], group.pivot[1], group.pivot[2]);
					renderer.xRot = group.getRotation().x() + (float) Math.toRadians(180);
					renderer.yRot = group.getRotation().y() + (float) Math.toRadians(180);
					renderer.zRot = group.getRotation().z();
					
					if (group.cubes != null) for (JavaJSONFile.Cube cube : group.cubes) renderer.texOffs(cube.uv[0], cube.uv[1]).addBox(cube.origin[0], cube.origin[1], cube.origin[2], cube.size[0], cube.size[1], cube.size[2], cube.inflate, cube.mirror);
						
					addChildren(generatedModel, group, renderer);

					generatedModel.renderList.add(renderer);
					generatedModel.partsList.put(group.name, renderer);
					model = generatedModel;
				}
			}
			
			return new ModelInformation(model, parsedFile.getTexture(), parsedFile.getLightMap());
			
		} catch (Exception e) { e.printStackTrace(); }
		
		return new ModelInformation();
	}

	private static void addChildren(JavaJSONModel model, JavaJSONFile.Group parentGroup, JavaJSONRenderer parsedModel) {
		if (parentGroup.children != null && parentGroup.children.size() > 0) {
			for (JavaJSONFile.Group group : parentGroup.children) {
				JavaJSONRenderer renderer = new JavaJSONRenderer(model);
				
				renderer.setPos(group.pivot[0], group.pivot[1], group.pivot[2]);
				renderer.xRot = group.getRotation().x();
				renderer.yRot = group.getRotation().y();
				renderer.zRot = group.getRotation().z();

				if (group.cubes != null) for (JavaJSONFile.Cube cube : group.cubes) renderer.texOffs(cube.uv[0], cube.uv[1]).addBox(cube.origin[0], cube.origin[1], cube.origin[2], cube.size[0], cube.size[1], cube.size[2], cube.inflate, cube.mirror);

				addChildren(model, group, renderer);

				parsedModel.addChild(renderer);
				model.partsList.put(group.name, renderer);
			}
		}
	}
}
