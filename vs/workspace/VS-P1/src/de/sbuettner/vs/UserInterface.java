package de.sbuettner.vs;

//05.04.16

/**
*
* @author Peter Altenberd
* (Translated into English by Ronald Moore)
* Computer Science Dept.                   Fachbereich Informatik
* Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class UserInterface {

  BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

  public String input() throws IOException {
    return stdIn.readLine();
  }

  public void output(String out) {
    System.out.print(out);
  }

  @Override
  public void finalize() throws IOException {
    stdIn.close();
  }
}
