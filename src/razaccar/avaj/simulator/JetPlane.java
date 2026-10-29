/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   JetPlane.java                                      :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: razaccar <razaccar@student.42lausanne.ch>  +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2026/09/29 16:50:37 by razaccar          #+#    #+#             */
/*   Updated: 2026/10/19 14:45:13 by razaccar         ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

package razaccar.avaj.simulator;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import razaccar.avaj.simulator.Coordinates;

public class JetPlane extends Aircraft {

    public JetPlane(long id, String name, Coordinates coordinates) {
        super(id, name, coordinates);
    }

    @Override
    public void updateConditions() throws SimulationException {
        String message;
        int longitude, latitude, height;
        boolean landed = false;

        String weather = weatherTower.getWeather(coordinates);
        try {
            switch(weather) {
                case "SUN":
                    message = "Conditions météo optimales.";
                    longitude = coordinates.getLongitude();
                    latitude = Math.addExact(coordinates.getLatitude(), 10);
                    height = Math.addExact(coordinates.getHeight(), 2);
                    break;
                case "FOG":
                    message = "C'est tout gris.";
                    longitude = coordinates.getLongitude();
                    latitude = Math.addExact(coordinates.getLatitude(), 1);
                    height = coordinates.getHeight();
                    break;
                case "RAIN":
                    message = "Il pleut, je change de latitude.";
                    longitude = coordinates.getLongitude();
                    latitude = Math.addExact(coordinates.getLatitude(), 5);
                    height = coordinates.getHeight();
                    break;
                case "SNOW":
                    message = "L'hiver approche...";
                    longitude = coordinates.getLongitude();
                    latitude = coordinates.getLatitude();
                    height = Math.subtractExact(coordinates.getHeight(), 7);
                    break;
                default:
                    throw new SimulationException("Unknown weather type: " + weather);
            }
        } catch (ArithmeticException e) {
            throw new SimulationException("min or max coordinate value reached.", e);
        }
        if (height <= 0) {
            height = 0;
            landed = true;
        }
        if (height > 100)
            height = 100;

        try (BufferedWriter output = new BufferedWriter(new FileWriter("simulation.txt", StandardCharsets.UTF_8, true))) {
            output.write(this + ": " + message + "\n");
            if (landed)
                output.write(this + ": " + "landing.\n");
        } catch (IOException e) {
            throw new SimulationException("Failed to write simulation output.", e);
        }

        if (landed)
            weatherTower.unregister(this);
        this.coordinates = new Coordinates(longitude, latitude, height);
    } 
}
