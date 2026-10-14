/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   Main.java                                          :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: razaccar <razaccar@student.42lausanne.ch>  +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2026/09/16 06:09:47 by razaccar          #+#    #+#             */
/*   Updated: 2026/10/13 22:05:39 by razaccar         ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

package razaccar.avaj.simulator;

import java.util.*;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    
    private AircraftFactory factory = AircraftFactory.getInstance();
    private List<Flyable> aircrafts = new ArrayList<>();
    private WeatherTower tower = new WeatherTower();
    private static int iterations;

    private Main() {}

    private Flyable parseAircraft(String line) throws ScenarioException {
        String[] entry  = line.split(" ", 5);

        String[] types = { "Helicopter", "JetPlane", "Balloon" };
        for (int i = 0; i < types.length; i++) {
            if (entry[0].equalsIgnoreCase(types[i])) {
                entry[0] = types[i];
                break;
            }
            if (i == types.length - 1)
                throw new ScenarioException("Invalid aircraft type.");
        }

        String type = entry[0];
        String name = entry[1];
        int longitude = parsePositiveInt(entry[2]);
        int latitude = parsePositiveInt(entry[3]);
        int height = parsePositiveInt(entry[4]);
        if (height > 100)
                throw new ScenarioException("Invalid height value: must be in the 0-100 range.");

        Coordinates coordinates = new Coordinates(longitude, latitude, height);
        return factory.newAircraft(type, name, coordinates);
    }

    private int parsePositiveInt(String s) throws ScenarioException {
        try { 
            int value = Integer.parseInt(s.trim());
            if (value < 0)
                throw new ScenarioException("Invalid value '" + value + "': non-negative integer expected.");
            return value;
        } catch (NumberFormatException e) {
            throw new ScenarioException("Failed to parse integer value: " + s, e);
        }
    }

    private void loadScenario(String pathname) throws ScenarioException {
        String entry = null;
        int lineNumber = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(pathname, StandardCharsets.UTF_8))) {
            iterations = parsePositiveInt(reader.readLine());
            lineNumber++;
            while ((entry = reader.readLine()) != null) {
                aircrafts.add(parseAircraft(entry));
                lineNumber++;
            }
        } catch (IOException e) {
            throw new ScenarioException("Failed to read scenario file.", e);
        } catch (ScenarioException e) {
            throw new ScenarioException(pathname + ":" + lineNumber + " [" + entry + "]", e);
        }
    }

    private void runSimulation() throws SimulationException {
        try (FileWriter writer = new FileWriter("simulation.txt", StandardCharsets.UTF_8, false)) {}
        catch (IOException e) { throw new SimulationException("Failed to open/create simulation output file.", e); }

        for (Flyable aircraft : aircrafts)
            aircraft.registerTower(tower);
        while (iterations-- > 0) 
            tower.changeWeather();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Error: invalid number of arguments.");
            System.err.println("[java] [program] [scenario .txt file]");
            return;
        }

        Main program = new Main();
        try {
            program.loadScenario(args[0]);
            program.runSimulation();
        } catch (ScenarioException e) {
            System.err.println("Scenario loading failed.\n");
            System.err.println(e.getMessage());
            if (e.getCause() != null)
                System.err.println("[ERROR] " + e.getCause().toString());
            return;
        } catch (SimulationException e) {
            System.err.println("Simulation execution failed.\n");
            System.err.println(e.getMessage());
            if (e.getCause() != null)
                System.err.println("[ERROR] " + e.getCause().toString());
            return;
        }
    }
}
