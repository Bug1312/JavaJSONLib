package com.swdteam.javajson;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

class JavaJSONFile {
	
	@SerializedName("parent") 		   	private    String parent;
	@SerializedName("texture") 			private    String texture;
	@SerializedName("lightmap") 		private    String lightmap;
	@SerializedName("alphamap") 		private    String alphamap;
	@SerializedName("texture_width") 	protected  int texWidth;
	@SerializedName("texture_height") 	protected  int texHeight;
	@SerializedName("scale") 			protected  float scale = 1;
	@SerializedName("font_data") 		protected  List<FontData> fontData = Collections.emptyList();
	@SerializedName("groups") 			protected  final List<Group> groups = Collections.emptyList();
	
	protected ResourceLocation getParent() {
		if (parent != null && parent.length() > 0) return new ResourceLocation(parent + ".json");
		else return null;
	}
	
	protected ResourceLocation getTexture()  { return getTexture(texture ); }
	protected ResourceLocation getLightMap() { return getTexture(lightmap); }
	protected ResourceLocation getAlphaMap() { return getTexture(alphamap); }
	
	private static ResourceLocation getTexture(String tex) {
		if (tex == null) return null;
		if (tex.equals("generated")) return null; // Convert deprecated value
		
		int colonPos = tex.indexOf(':') + 1;
		String modId = tex.substring(0, colonPos);
		String path = tex.substring(colonPos);
		
		return new ResourceLocation(modId + "textures/" + path + ".png");
	}
		
	protected class Cube {

		@SerializedName("uv") 		protected int[] uv;
		@SerializedName("origin") 	protected float[] origin;
		@SerializedName("size") 	protected float[] size;
		@SerializedName("inflate") 	protected float inflate;
		@SerializedName("mirror") 	protected boolean mirror;
		
		public Cube(int uvX, int uvY, float x, float y, float z, float w, float h, float d, float inflate, boolean mirror) {
			this.uv = new int[] {uvX, uvY};
			this.origin = new float[] {x, y, z};
			this.size = new float[] {w, h, d};
			this.inflate = inflate;
			this.mirror = mirror;
		}
	}
	
	protected class Group {

		@SerializedName("group_name") protected String name;
		@SerializedName("pivot") 	  protected float[] pivot = { 0, 0, 0 };
		@SerializedName("rotation")   private 	float[] rotation = { 0, 0, 0 };
		@SerializedName("children")   protected List<Group> children = Collections.emptyList();
		@SerializedName("cubes")	  protected List<Cube> cubes = Collections.emptyList();
		@SerializedName("font_data")  protected List<FontData> fontData = Collections.emptyList();

		protected Vector3f getRotation() {
			float x = (float) Math.toRadians(rotation[0]);
			float y = (float) Math.toRadians(rotation[1]);
			float z = (float) Math.toRadians(rotation[2]);
			
			return new Vector3f(x, y, z);
		}
		
	}
	
	protected static class FontData {
		
		@SerializedName("content")	protected String value;
		@SerializedName("color")	private   String color = "#FFFFFF";
		@SerializedName("glow")     protected boolean glow = false;
		@SerializedName("centered") protected boolean[] centered = { true, true };
		@SerializedName("scale")  	protected float scale = 1;
		@SerializedName("origin") 	protected float[] origin = {0, 0, 0};
		@SerializedName("rotation") protected float[] rotation = {0, 0, 0};
				
		protected Color getColor() {
			return new Color(net.minecraft.util.text.Color.parseColor(color).getValue(), false);
		}
		
	}
	
}
