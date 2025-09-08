package tpaoc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Advent Of Code.
 */
public class TpAdventOfCode
{
	/*----- Répertoire contenant les données de chaque puzzle -----*/
	private static final String PATH_DATA = "src" + File.separator + "data"+ File.separator;


	/**
	 * Day00.
	 */
	public static void day00 ()
		{
		String fichier = "day00.txt";
		try (Scanner scanner = new Scanner(new FileInputStream(PATH_DATA + fichier)))
			{
			/*----- Lecture du fichier -----*/
			while (scanner.hasNextLine())
				{
				String ch = scanner.nextLine(); ch = ch.trim();
				System.out.println(ch);
				}
			}
		catch (FileNotFoundException fne) { System.err.println(fichier + " est absent ! "); }
		}


	/**
	 * Programme principal
	 */
	public static void main(String[] args)
		{
		day00();
		}

} /*----- Fin de la classe TpAdventOfCode -----*/
