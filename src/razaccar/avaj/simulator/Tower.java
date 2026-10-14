/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   Tower.java                                         :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: razaccar <razaccar@student.42lausanne.ch>  +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2026/09/16 06:10:02 by razaccar          #+#    #+#             */
/*   Updated: 2026/10/13 20:50:23 by razaccar         ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

package razaccar.avaj.simulator;

import java.util.*;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class Tower {
    
    private List<Flyable> observers = new ArrayList<>();

    public Tower() {}

    public void register(Flyable flyable) throws SimulationException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter("simulation.txt", StandardCharsets.UTF_8, true))) {
            String line = "Tower: " + flyable + " registered to weather tower.\n";
            output.write(line);
            output.close();
        } catch (IOException e) {
            throw new SimulationException("Failed to write simulation output.", e);
        }
        observers.add(flyable);
    }

    public void unregister(Flyable flyable) throws SimulationException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter("simulation.txt", StandardCharsets.UTF_8, true))) {
            String line = "Tower: " + flyable + " unregistered from weather tower.\n";
            output.write(line);
            output.close();
        } catch (IOException e) {
            throw new SimulationException("Failed to write simulation output.", e);
        }
        observers.remove(flyable);
    }

    protected void conditionChanged() throws SimulationException {
        for (Flyable flyable : new ArrayList<>(observers))
            flyable.updateConditions();
    }
}
