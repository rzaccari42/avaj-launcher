/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   AircraftFactory.java                               :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: razaccar <razaccar@student.42lausanne.ch>  +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2026/10/07 02:09:37 by razaccar          #+#    #+#             */
/*   Updated: 2026/10/11 22:30:35 by razaccar         ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

package razaccar.avaj.simulator;

public class AircraftFactory {

    private static final AircraftFactory INSTANCE = new AircraftFactory();
    int id = 0;

    private AircraftFactory() {}

    public static AircraftFactory getInstance() { return INSTANCE; }

    public Flyable newAircraft(String type, String name, Coordinates coordinates) {
        switch (type) {
            case "Helicopter":
                return new Helicopter(id++, name, coordinates);
            case "JetPlane":
                return new JetPlane(id++, name, coordinates);
            case "Balloon":
                return new Balloon(id++, name, coordinates);
            default:
                throw new IllegalArgumentException("Unknown aircraft type: " + type);
        }
    }
}
