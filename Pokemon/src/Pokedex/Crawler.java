package Pokedex;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Crawler {
	
	private Document document;
	private Elements links;
	private String pokemondb;
	private ArrayList<String> hrefLinks;
	
	public Crawler() {
		pokemondb = "https://pokemondb.net/pokedex/all";
		hrefLinks = new ArrayList<String>();
		collectLinks(pokemondb);
	}
	/**
	 * Collects all the links of the pokemon pages
	 * @param url urls to take links from.
	 */
	private void collectLinks(String url) {
		try {
			// Get data all links from the site.
			document = Jsoup.connect(url).get();
			links = document.select("a[href]");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// Add all the links that have to do with pokemon to hrefLinks
		for (int i = 0; i < links.size(); i++) {
			if (links.get(i).toString().contains("#"))
			{
				String pokemon = collectLink(links.get(i).toString());
				hrefLinks.add(pokemon);
			}
		}
	}
	/**
	 * parse the given string into a usable link.
	 * @param href the string to be parsed.
	 * @return the link to the pokemon.
	 */
	private String collectLink(String href)
	{
		return href = "https://pokemondb.net/pokedex/" + 
				href.substring(href.indexOf(">")+1, href.lastIndexOf("<")).replace(" ", "-");
	}
	/**
	 * Get all the usable links to pokemon sites.
	 * @return usable links for the pokemon sites.
	 */
	public ArrayList<String> getPokemonLinks() {
		return hrefLinks;
	}
}
