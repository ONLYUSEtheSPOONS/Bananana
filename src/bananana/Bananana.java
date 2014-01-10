/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bananana;

import environment.ApplicationStarter;

/**
 *
 * @author doughill
 */
public class Bananana {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        ApplicationStarter.run("Bananana?", new BanananaEnvironment());
    }
}
