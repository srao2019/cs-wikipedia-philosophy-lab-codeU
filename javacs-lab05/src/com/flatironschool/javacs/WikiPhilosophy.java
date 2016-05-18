package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

public class WikiPhilosophy {
	
	final static WikiFetcher wf = new WikiFetcher();
	
	/**
	 * Tests a conjecture about Wikipedia and Philosophy.
	 * 
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 * 
	 * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
        // some example code to get you started
		Boolean done = false;

		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		while(!done){
			if(done)
				break;
			Elements paragraphs = wf.fetchWikipedia(url);
			Element firstPara = paragraphs.get(0);
			
			//Elements links = firstPara.getElementsByTag("a");

			Iterable<Node> iter = new WikiNodeIterable(firstPara);
			
			for (Node n: iter) {
				if(isValid(n,url)){
					Character first = n.childNode(0).toString().charAt(0);
					if(first.isLowerCase(first)){
						url="https://en.wikipedia.org";
						url=url.concat(n.attr("href"));
						if(n.childNode(0).toString().equals("philosophy"))
							done = true;
						break;
					}
				}
					
			}
		}
       System.out.println(url);
	}
	
	private static boolean isValid(Node n,String currUrl){
		if(!(n.hasAttr("href")))
			return false;
		if(n.attr("href").equals(currUrl))
			return false;
		if(n.hasAttr("i"))
			return false;
		return true;
		
		
	}
	
}
