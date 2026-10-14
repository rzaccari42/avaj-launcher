/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   Helicopter.java                                    :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: razaccar <razaccar@student.42lausanne.ch>  +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2026/09/29 16:50:37 by razaccar          #+#    #+#             */
/*   Updated: 2026/10/13 21:48:19 by razaccar         ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

package razaccar.avaj.simulator;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import razaccar.avaj.simulator.Coordinates;

public class Helicopter extends Aircraft {

    public Helicopter(long id, String name, Coordinates coordinates) {
        super(id, name, coordinates);
    }

    @Override
    public void updateConditions() throws SimulationException {
        String message;
        int longitude, latitude, height;
        boolean landed = false;

        String weather = weatherTower.getWeather(coordinates);
        switch(weather) {
            case "SUN":
                message = "Ciel ensoleillé, je prends de la hauteur.";
                longitude = coordinates.getLongitude() + 10;
                latitude = coordinates.getLatitude();
                height = coordinates.getHeight() + 2;
                break;
            case "FOG":
                message = "Vision trouble, attention!";
                longitude = coordinates.getLongitude() + 1;
                latitude = coordinates.getLatitude();
                height = coordinates.getHeight();
                break;
            case "RAIN":
                message = "J'active les essuie-glaces.";
                longitude = coordinates.getLongitude() + 5;
                latitude = coordinates.getLatitude();
                height = coordinates.getHeight();
                break;
            case "SNOW":
                message = "Il neige, je reduis l'altitude.";
                longitude = coordinates.getLongitude();
                latitude = coordinates.getLatitude();
                height = coordinates.getHeight() - 12;
                break;
            default:
                throw new SimulationException("Unknown weather type: " + weather);
        }
        if (height <= 0)
            landed = true;
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
