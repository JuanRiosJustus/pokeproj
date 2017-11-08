package Pokedex;

import java.util.ArrayList;

public class Parser {
	static ArrayList<String> types = new ArrayList<String>();
	static
	{
		types.add("Normal");
		types.add("Flying");
		types.add("Flying");
		types.add("Poison");
		types.add("Ground");
		types.add("Rock");
		types.add("Bug");
		types.add("Ghost");
		types.add("Steel");
		types.add("Fire");
		types.add("Water");
		types.add("Grass");
		types.add("Electric");
		types.add("Psychic");
		types.add("Ice");
		types.add("Dragon");
		types.add("Dark");
		types.add("Fairy");
	}
	/**
	 *  Find the frequency of a string 
	 * @param string the string to be looked at
	 * @param substring the substring to be found within the string
	 * @return the amount of times the substring appears
	 */
	private static int getStringFrequency(String string, String substring) {
		int lastIndex = 0;
		int count = 0;

		while(lastIndex != -1){
		    lastIndex = string.indexOf(substring,lastIndex);
		    if(lastIndex != -1){
		        count ++;
		        lastIndex += substring.length();
		    }
		}
		return count;
	}
	/**
	 * Find the specific occurrence of a string within a string
	 * @param str The string to be looked at
	 * @param substr The String occurrence we're looking for
	 * @param n the occurrence of the substring
	 * @return the specific occurrence of a string
	 */
	private static int nthOccurrenceOf(String str, String substr, int n) {
	    int pos = str.indexOf(substr);
	    while (--n > 0 && pos != -1) {
	        pos = str.indexOf(substr, pos + 1);
	    }
	    return pos;
	}
	private static int frequencyBefore(String str, String strToCount, String strUntil) {
		if (str == null || strToCount == null || strUntil == null) return -1;
		int i = str.indexOf(strUntil);
		int j = 0;
		int count = 0;
		while(j != i) {
			// check that current str substring is the string to count
			if (str.substring(j, j + strToCount.length()).equals(strToCount)) {
				count++;
			}
			j++;
		}
		return count;
		
	}
	
	public static String getPokedexNumber(String str) {
		String pokeId = str.substring(nthOccurrenceOf(str," ", 4),nthOccurrenceOf(str," ", 5));
		return pokeId.trim();
	}
	public static String getTyping(String str) {
		StringBuilder pokeTypes = new StringBuilder(str.substring(nthOccurrenceOf(str, " ", 6), nthOccurrenceOf(str, "Species", 1)));
		return pokeTypes.toString().substring(1);
	}
	public static String getHeight(String str) {
		StringBuilder pokeHi = new StringBuilder(str.substring(nthOccurrenceOf(str, "Pokémon Height", 1) + 15, nthOccurrenceOf(str, "Weight", 1)));
		return pokeHi.toString();
	}
	
	public static String getWeight(String str) {
		StringBuilder pokeWi = new StringBuilder(str.substring(nthOccurrenceOf(str, "Weight", 1) + 7, nthOccurrenceOf(str, "Abilities", 1)));
		return pokeWi.toString();
	}
	public static String getSpecies(String str) {
		StringBuilder pokeSpe = new StringBuilder(str.substring(nthOccurrenceOf(str, "Species", 1) + 8,nthOccurrenceOf(str, "Pokémon Height", 1)));
		pokeSpe.append("Pokemon");
		return pokeSpe.toString();
	}
	public static String getGeneration(String str) {
		StringBuilder pokeGen = new StringBuilder(str.substring(str.indexOf("Generation"), str.indexOf("Generation") + 12));
		return (pokeGen.toString() == null ? "null" : pokeGen.toString());
	}
	public static String getAbilities(String str) {
		StringBuilder pokeMo = new StringBuilder(str.substring(str.indexOf("Abilities"),str.indexOf("Local")));
		return pokeMo.toString().substring(0).replace("Abilities", "").replace("(hidden ability)", "");
	}
	public static void getEntry(String str) {
		StringBuilder pokeEn = new StringBuilder(str.substring(str.indexOf("Pokédex entries")));
		System.out.println(pokeEn.toString().substring(16));
	}
	// TODO
	public static String getEvolution(String str, String pkmn) {
		if (frequencyBefore(str,"→", pkmn) == 0) {
			//System.out.println(pkmn + " is the basic form.");
			return "Basic form";
		} else if (frequencyBefore(str,"→", pkmn) == 1) {
			//System.out.println(pkmn + " is the first stage evolution");
			return "First stage";
		} else if (frequencyBefore(str,"→", pkmn) == 2) {
			//System.out.println(pkmn + " is the second stage evolution");
			return "Second stage";
		} else {
			//System.out.println(pkmn + " either a legendary or a special form");
			return "Special form or Legendary";
		}
	}
}
