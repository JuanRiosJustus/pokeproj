package Pokedex;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Pokedex {
	private Crawler crawl;
	private Document document;
	private ArrayList<String> links;
	private HashSet<String> checks;
	private String pokemon;
	private boolean success;
	public Pokedex() {
		success = false;
		crawl = new Crawler();
		links = crawl.getPokemonLinks();
		checks = new HashSet<String>();
		getRawData();
	}
	
	private void getRawData()
	{
		try {
			final String FILENAME = "pokemon.txt";
			FileWriter fw = new FileWriter(FILENAME);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("FORMAT: \t NAME \t POKEDEX NUMBER \t TYPE \t SPECIES \t ABILITY \t HEIGHT \t WEIGHT \t EVOLUTION/STAGE");
			bw.newLine();
			for (int i = 0; i < links.size(); i++) {
				try {
					document = Jsoup.connect(links.get(i)).get();
					success = true;
					pokemon = document.text();	
				} catch (Exception ex) {
					success = false;
				}

				if (success && !checks.contains(pokemon.substring(0,pokemon.indexOf(" ")))) {
					pokemon = document.text();	
					String name, data = null, entry = null, evo = null, moves = null, extra = null;
					name = pokemon.substring(0,pokemon.indexOf(" ")).trim();
					try { data = pokemon.substring(pokemon.indexOf("Pokédex data"), pokemon.indexOf("The ranges shown")); } catch (Exception ex) {}
					try { entry = pokemon.substring(pokemon.indexOf("Locations Q&A") + 13, pokemon.indexOf("Evolution chart #")); } catch (Exception ex) {}
					try { evo = pokemon.substring(pokemon.indexOf("Evolution chart #"), pokemon.indexOf("In other generations:")); } catch (Exception ex) {}
					try { moves = pokemon.substring(pokemon.indexOf("Moves learnt by level up"), pokemon.indexOf("— Shiny — — See all")); } catch (Exception ex) {}
					try { extra = pokemon.substring(pokemon.lastIndexOf("Evolution chart"), pokemon.indexOf("Name origin"));
					} catch (Exception ex) { }
					
					//name //pokedexnumber //generation // speciea //typing // abilities //
					String entirety = name + (name.length() < 8 ? "\t" : "") + "\t" + Parser.getPokedexNumber(data) + "\t" +
							// generation
					Parser.getGeneration(pokemon) + "\t" + 
							// Typing
					Parser.getTyping(data) + "\t" + (Parser.getTyping(data).length() < 8 ? "\t\t" : "\t") +
							// species 
					Parser.getSpecies(data) + 
					(Parser.getSpecies(data).length() < 16 ? "\t\t" : (Parser.getSpecies(data).length() <= 20 ? "\t" : "")) +
							// abilities
					Parser.getAbilities(data) +
							// height
					(Parser.getAbilities(data).length() <= 12 ? (Parser.getAbilities(data).length() <= 8 ? "\t\t\t\t\t" : "\t\t\t\t") : (Parser.getAbilities(data).length() <= 16 ? "\t\t\t\t" : 
					(Parser.getAbilities(data).length() <= 20 ? "\t\t\t" : (Parser.getAbilities(data).length() <= 24 ? "\t\t\t" : (Parser.getAbilities(data).length() <= 28 ? "\t\t" : ""
					+ (Parser.getAbilities(data).length() <= 32 ? "\t" : "\t")))))) + Parser.getHeight(data) + 
					(Parser.getHeight(data).length() < 12 ? "\t" : "\t\t" ) + Parser.getWeight(data) + "\t\t" +
					(evo != null ? Parser.getEvolution(evo, name) : Parser.getEvolution(extra, name));
					System.out.println(entirety);
					checks.add(name);
					bw.write(entirety);
					bw.newLine();
					bw.write(entry != null ? entry.substring(entry.indexOf("Pokédex entries") + 16) : "UNKNOWN DATA");
					bw.newLine();
				}
			}
			bw.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	private void toFile()
	{
		
	}
	
}
