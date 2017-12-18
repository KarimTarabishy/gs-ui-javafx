package org.graphstream.ui.javafx.util;

import java.util.HashMap;
import java.util.Map;

import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.TextStyle;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class FontCache {
	
	protected static Font defFont = new Font("SansSerif", 11);
	
	protected static HashMap<String, FontSlot> cache = new HashMap<>();
	
	public static Font getDefaultFont() {
		return defFont;
	}
	
	public static Font getDefaultFont(TextStyle style, int size) {
		return getFont( "SansSerif", style, size );
	}

	public static Font getFont(String name, TextStyle style, int size) {
		if ( cache.get(name) == null ) {
			FontSlot slot = new FontSlot(name, style, size);
			cache.put(name, slot);
			return slot.getFont(style, size);
		}
		else {
			return cache.get(name).getFont(style, size);
		}
	}
}

class FontSlot {
	
	HashMap<Integer, Font> normal = null ;
	HashMap<Integer, Font> bold = null ;
	HashMap<Integer, Font> italic = null ;
	HashMap<Integer, Font> boldItalic = null ;
	
	private String name ;
	
	public FontSlot(String name, TextStyle style, int size) {
		this.name = name ;
		insert(style, size);
	}

	private Font insert(TextStyle style, int size) {
		return insert(mapFromStyle(style), toJavaStyleBold(style), toJavaStylePosture(style), size);
	}

	private Font insert(Map<Integer, Font> map, FontWeight styleBold, FontPosture styleItalic, int size) {
		if (map.get(size) == null) {
			Font font = Font.font(name, styleBold, styleItalic, size);
			map.put(size, font);
			return font ;
		}
		else {
			return map.get(size);
		}
	}
	
	public Font getFont(TextStyle style, int size) {
		Map<Integer, Font> map = mapFromStyle(style);
		
		if (map.get(size) == null) {
			return insert( map, toJavaStyleBold(style), toJavaStylePosture(style), size );
		}
		else {
			return map.get(size);
		}
	}

	protected FontWeight toJavaStyleBold(StyleConstants.TextStyle style) {
		switch (style) {
		case BOLD:
			return FontWeight.BOLD;
		case ITALIC:
			return FontWeight.NORMAL;
		case BOLD_ITALIC:
			return FontWeight.BOLD;
		case NORMAL:
		default:
			return FontWeight.NORMAL;
		}
	}
	
	protected FontPosture toJavaStylePosture(StyleConstants.TextStyle style) {
		switch (style) {
		case BOLD:
			return FontPosture.REGULAR;
		case ITALIC:
			return FontPosture.ITALIC;
		case BOLD_ITALIC:
			return FontPosture.ITALIC;
		case NORMAL:
		default:
			return FontPosture.REGULAR;
		}
	}

	private Map<Integer, Font> mapFromStyle(TextStyle style) {
		switch (style) {
			case BOLD:
				if (bold == null) {
					bold = new HashMap<Integer, Font>();
				}
				return bold ;
			case ITALIC:
				if (italic == null) {
					italic = new HashMap<Integer, Font>();
				}
				return italic ;
			case BOLD_ITALIC:
				if (boldItalic == null) {
					boldItalic = new HashMap<Integer, Font>();
				}
				return boldItalic ;
			case NORMAL:
				if (normal == null) {
					normal = new HashMap<Integer, Font>();
				}
				return normal ;
			default:
				if (normal == null) {
					normal = new HashMap<Integer, Font>();
				}
				return normal ;
		}
	}
}