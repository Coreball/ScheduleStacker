package coreball.schedulestacker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Coreball on 22 Jan 2018.
 */
public class Glue {

	// Primary data structure for storing values
	private HashMap<String, ArrayList<String>[]>[] sticky;
	/*
	 * 1 - Array of HashMap - Represents the 1-8 TYPES of classes
	 * 2 - HashMap of String and array - Represents class name and periods offered
	 * 3 - Array of ArrayList - Represents class period and list of teachers in period
	 * 4 - ArrayList of String - Represents all teachers teaching said class per period
	 */

	public Glue() {

	}

}
