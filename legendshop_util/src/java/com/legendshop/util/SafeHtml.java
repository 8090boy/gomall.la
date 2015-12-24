/*
 * 
 * LegendShop 多用户商城系统
 * 
 *  版权所有,并保留所有权利。
 * 
 */
package com.legendshop.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.htmlparser.Attribute;
import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TextNode;

/**
 * 
 * LegendShop 版权所有 2009-2011,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得LegendShop商业授权之前，您不能将本软件应用于商业用途，否则LegendShop将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.legendesign.net
 * ----------------------------------------------------------------------------
 */
public class SafeHtml {

	/** The welcome tags. */
	private static Set welcomeTags;

	/** The welcome attributes. */
	private static Set welcomeAttributes;

	/** The allowed protocols. */
	private static Set allowedProtocols;

	static {
		welcomeTags = new HashSet();
		welcomeAttributes = new HashSet();
		allowedProtocols = new HashSet();

		splitAndTrim("u, a, img, i, u, li, ul, font, br, p, b, hr,pre", welcomeTags);
		splitAndTrim("src, href, size, face, color, target, rel", welcomeAttributes);
		splitAndTrim("http://, https://, mailto:, ftp://", allowedProtocols);
	}

	/**
	 * Split and trim.
	 * 
	 * @param s1
	 *            the s1
	 * @param data
	 *            the data
	 */
	private static void splitAndTrim(String s1, Set data) {

		if (s1 == null) {
			return;
		}

		String[] tags = s1.toUpperCase().split(",");

		for (int i = 0; i < tags.length; i++) {
			data.add(tags[i].trim());
		}
	}

	/**
	 * Given an input, analyze each HTML tag and remove unsecure attributes from
	 * them.
	 * 
	 * @param contents
	 *            The content to verify
	 * @return the content, secure.
	 */
	public String ensureAllAttributesAreSafe(String contents) {
		StringBuffer sb = new StringBuffer(contents.length());

		try {
			Lexer lexer = new Lexer(contents);
			Node node;

			while ((node = lexer.nextNode()) != null) {
				if (node instanceof Tag) {
					Tag tag = (Tag) node;

					this.checkAndValidateAttributes(tag, false);

					sb.append(tag.toHtml());
				} else {
					sb.append(node.toHtml());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Problems while parsing HTML", e);
		}

		return sb.toString();
	}

	/**
	 * Given an input, makes it safe for HTML displaying. Removes any not
	 * allowed HTML tag or attribute, as well unwanted Javascript statements
	 * inside the tags.
	 * 
	 * @param contents
	 *            the input to analyze
	 * @return the modified and safe string
	 */
	public String makeSafe(String contents) {
		if (contents == null || contents.length() == 0) {
			return contents;
		}

		StringBuffer sb = new StringBuffer(contents.length());

		try {
			Lexer lexer = new Lexer(contents);
			Node node;

			while ((node = lexer.nextNode()) != null) {
				boolean isTextNode = node instanceof TextNode;

				if (isTextNode) {
					// Text nodes are raw data, so we just
					// strip off all possible html content
					String text = node.toHtml();

					if (text.indexOf('>') > -1 || text.indexOf('<') > -1) {
						StringBuffer tmp = new StringBuffer(text);

						replaceAll(tmp, "<", "&lt;");
						replaceAll(tmp, ">", "&gt;");
						replaceAll(tmp, "\"", "&quot;");

						node.setText(tmp.toString());
					}
				}

				if (isTextNode || (node instanceof Tag && this.isTagWelcome(node))) {
					sb.append(node.toHtml());
				} else {
					StringBuffer tmp = new StringBuffer(node.toHtml());

					replaceAll(tmp, "<", "&lt;");
					replaceAll(tmp, ">", "&gt;");

					sb.append(tmp.toString());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while parsing HTML", e);
		}

		return sb.toString();
	}

	/**
	 * Returns true if a given tag is allowed. Also, it checks and removes any
	 * unwanted attribute the tag may contain.
	 * 
	 * @param node
	 *            The tag node to analyze
	 * @return true if it is a valid tag.
	 */
	private boolean isTagWelcome(Node node) {
		Tag tag = (Tag) node;

		if (!welcomeTags.contains(tag.getTagName())) {
			return false;
		}

		this.checkAndValidateAttributes(tag, true);

		return true;
	}

	/**
	 * Given a tag, check its attributes, removing those unwanted or not secure.
	 * 
	 * @param tag
	 *            The tag to analyze
	 * @param checkIfAttributeIsWelcome
	 *            true if the attribute name should be matched against the list
	 *            of welcome attributes, set in the main configuration file.
	 */
	private void checkAndValidateAttributes(Tag tag, boolean checkIfAttributeIsWelcome) {
		Vector newAttributes = new Vector();

		for (Iterator iter = tag.getAttributesEx().iterator(); iter.hasNext();) {
			Attribute a = (Attribute) iter.next();

			String name = a.getName();

			if (name == null) {
				newAttributes.add(a);
			} else {
				name = name.toUpperCase();

				if (a.getValue() == null) {
					newAttributes.add(a);
					continue;
				}

				String value = a.getValue().toLowerCase();

				if (checkIfAttributeIsWelcome && !this.isAttributeWelcome(name)) {
					continue;
				}

				if (!this.isAttributeSafe(name, value)) {
					continue;
				}

				if (a.getValue().indexOf("&#") > -1) {
					a.setValue(a.getValue().replaceAll("&#", "&amp;#"));
				}

				newAttributes.add(a);
			}
		}

		tag.setAttributesEx(newAttributes);
	}

	/**
	 * Check if the given attribute name is in the list of allowed attributes.
	 * 
	 * @param name
	 *            the attribute name
	 * @return true if it is an allowed attribute name
	 */
	private boolean isAttributeWelcome(String name) {
		return welcomeAttributes.contains(name);
	}

	/**
	 * Check if the attribute is safe, checking either its name and value.
	 * 
	 * @param name
	 *            the attribute name
	 * @param value
	 *            the attribute value
	 * @return true if it is a safe attribute
	 */
	private boolean isAttributeSafe(String name, String value) {
		if (name.length() >= 2 && name.charAt(0) == 'O' && name.charAt(1) == 'N') {
			return false;
		}

		if (value.indexOf('\n') > -1 || value.indexOf('\r') > -1 || value.indexOf('\0') > -1) {
			return false;
		}

		if (("HREF".equals(name) || "SRC".equals(name))) {
			if (!this.isHrefValid(value)) {
				return false;
			}
		} else if ("STYLE".equals(name)) {
			// It is much more a try to not allow constructions
			// like style="background-color: url(javascript:xxxx)" than anything
			// else
			if (value.indexOf('(') > -1) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if a given address is valid.
	 * 
	 * @param href
	 *            The address to check
	 * @return true if it is valid
	 */
	private boolean isHrefValid(String href) {
		boolean htmlLinksAllowRelative = false;
		if (htmlLinksAllowRelative && href.length() > 0 && href.charAt(0) == '/') {
			return true;
		}

		for (Iterator iter = allowedProtocols.iterator(); iter.hasNext();) {
			String protocol = iter.next().toString().toLowerCase();

			if (href.startsWith(protocol)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Replaces some string with another value.
	 * 
	 * @param sb
	 *            the StrinbBuilder with the contents to work on
	 * @param what
	 *            the string to be replaced
	 * @param with
	 *            the new value
	 * @return the new string
	 */
	public static String replaceAll(StringBuffer sb, String what, String with) {
		int pos = sb.indexOf(what);

		while (pos > -1) {
			sb.replace(pos, pos + what.length(), with);
			pos = sb.indexOf(what);
		}

		return sb.toString();
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		SafeHtml safe = new SafeHtml();
		String content = "<html><table><tr><td>aaa<script type=\"text/javascript\">function b(){alert(21);}b();</script><p>测试</p></td></tr></table></html>";
		String result = safe.makeSafe(content);
		String result1 = safe.ensureAllAttributesAreSafe(content);
		System.out.println(result);
		System.out.println(result1);
	}
}
