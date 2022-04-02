package com.swdteam.javajson;

import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

class JavaJSONFile {

	@SerializedName("parent") 		   	private    String parent;
	@SerializedName("texture") 			private    String texture;
	@SerializedName("lightmap") 		private    String lightmap;
	@SerializedName("texture_width") 	protected  int texWidth;
	@SerializedName("texture_height") 	protected  int texHeight;
	@SerializedName("scale") 			protected  float scale = 1;
	@SerializedName("groups") 			protected  final List<Group> groups = Collections.emptyList();
	
	protected ResourceLocation getParent() {
		if (parent != null && parent.length() > 0) return new ResourceLocation(parent + ".json");
		else return null;
	}
	
	protected ResourceLocation getTexture() { return getTexture(texture); }
	protected ResourceLocation getLightMap() { return getTexture(lightmap); }
	
	private static ResourceLocation getTexture(String tex) {
		if (tex == null) return null;
		
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
		@SerializedName("rotation")   private 	List<Float> rotation = Collections.emptyList();
		@SerializedName("children")   protected List<Group> children = Collections.emptyList();
		@SerializedName("cubes")	  protected List<Cube> cubes = Collections.emptyList();
			
		protected Vector3f getRotation() {
			float x = (float) Math.toRadians(rotation.get(0));
			float y = (float) Math.toRadians(rotation.get(1));
			float z = (float) Math.toRadians(rotation.get(2));
			
			return new Vector3f(x,y,z);
		}
		
	}
	
}
