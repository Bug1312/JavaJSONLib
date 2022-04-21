package com.swdteam.javajson;

import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.Color;

class JavaJSONFile {

	@Deprecated @SerializedName("fontData") private List<FontData> _fontData = Collections.emptyList();
	
	@SerializedName("parent") 		   	private    String parent;
	@SerializedName("texture") 			private    String texture;
	@SerializedName("lightmap") 		private    String lightmap;
	@SerializedName("texture_width") 	protected  int texWidth;
	@SerializedName("texture_height") 	protected  int texHeight;
	@SerializedName("scale") 			protected  float scale = 1;
	@SerializedName("font_data") 		protected  List<FontData> fontData = _fontData;
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
		@SerializedName("rotation")   private 	float[] rotation = { 0, 0, 0 };
		@SerializedName("children")   protected List<Group> children = Collections.emptyList();
		@SerializedName("cubes")	  protected List<Cube> cubes = Collections.emptyList();
			
		protected Vector3f getRotation() {
			float x = (float) Math.toRadians(rotation[0]);
			float y = (float) Math.toRadians(rotation[1]);
			float z = (float) Math.toRadians(rotation[2]);
			
			return new Vector3f(x, y, z);
		}
		
	}
	
	protected static class FontData {

		@Deprecated @SerializedName("string")	private String _string;
		@Deprecated @SerializedName("x")		private float _x;
		@Deprecated @SerializedName("y")		private float _y;
		@Deprecated @SerializedName("z")		private float _z;
		@Deprecated @SerializedName("rot_x")	private float _rotationX;
		@Deprecated @SerializedName("rot_y")	private float _rotationY;
		@Deprecated @SerializedName("rot_z")	private float _rotationZ;
		
		@SerializedName("content")	protected String value;
		@SerializedName("color")	private   String color = "#FFFFFF";
		@SerializedName("centered") protected boolean[] centered = { true, true };
		@SerializedName("scale")  	protected float scale = 1;
		@SerializedName("origin") 	protected float[] origin = { 0, 0, 0 };
		@SerializedName("rotation") protected float[] rotation = { 0, 0, 0 };
					
		protected boolean deprecatedPos = false;
	
		protected void setupDeprecation() {
			float[] _origin = {_x, _y, _z};
			float[] _rotation = {_rotationX, _rotationY, _rotationZ};
			
			if(value == null) value = _string;
			if(rotation == null) rotation = _rotation;
			if(origin == null) {
				origin = _origin;
//				deprecatedPos = true;
			}
		}
		
		
		protected int getColor() {
			return Color.parseColor(color).getValue();
		}
		
//		@SerializedName("content")	protected String value = _string;
//		@SerializedName("color")	private   String color = "#FFFFFF";
//		@SerializedName("centered") protected boolean[] centered = { true, false };
//		@SerializedName("scale")  	protected float scale = 1;
//		@SerializedName("origin") 	protected float[] origin = { _x, _y, _z };
//		@SerializedName("rotation") protected float[] rotation = { _rotationX, _rotationY, _rotationZ };
		
	}
	
}
