/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   Balloon.java                                       :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: razaccar <razaccar@student.42lausanne.ch>  +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2026/09/29 16:50:37 by razaccar          #+#    #+#             */
/*   Updated: 2026/10/13 21:49:05 by razaccar         ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

package razaccar.avaj.simulator;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import razaccar.avaj.simulator.Coordinates;

public class Balloon extends Aircraft {

    public Balloon(long id, String name, Coordinates coordinates) {
        super(id, name, coordinates);
    }

    @Override
    public void updateConditions() throws SimulationException  {
        String message;
        int longitude, latitude, height;
        boolean landed = false;

        String weather = weatherTower.getWeather(coordinates);
        switch(weather) {
            case "SUN":
                message = "Il fait tarpin beau.";
                longitude = coordinates.getLongitude() + 2;
                latitude = coordinates.getLatitude();
                height = coordinates.getHeight() + 4;
                break;
            case "FOG":
                message = "On voit R zebi!";
                longitude = coordinates.getLongitude();
                latitude = coordinates.getLatitude();
                height = coordinates.getHeight() - 3;
                break;
            case "RAIN":
                message = "Il pleut, rentrons.";
                longitude = coordinates.getLongitude();
                latitude = coordinates.getLatitude();
                height = coordinates.getHeight() - 5;
                break;
            case "SNOW":
                message = "C'est tout blanc!";
                longitude = coordinates.getLongitude();
                latitude = coordinates.getLatitude();
                height = coordinates.getHeight() - 15;
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
