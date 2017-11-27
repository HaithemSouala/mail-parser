/**
 * 
 */
package fr.gfi.poc;

import java.io.IOException;
import java.util.Date;

/**
 * @author frus66684
 *
 */
public class Runner {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();

		System.out.println("Starting: " + new Date(startTime));
		System.out.println("********************************** ");

		Parser.parser();

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("**********************************");
		System.out.println(String.format("Ending: %s:  Executed in %sms", new Date(stopTime), elapsedTime));
	}

}
