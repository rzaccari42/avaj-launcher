/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   WeatherProvider.java                               :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: razaccar <razaccar@student.42lausanne.ch>  +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2026/09/24 16:46:28 by razaccar          #+#    #+#             */
/*   Updated: 2026/10/19 15:11:06 by razaccar         ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

package razaccar.avaj.simulator;

import razaccar.avaj.simulator.Coordinates;

public final class WeatherProvider {

    private static final WeatherProvider INSTANCE = new WeatherProvider();
    private String[] weather = {"SUN", "FOG", "RAIN", "SNOW"};
    private static final int HORIZONTAL_REGION_SIZE = 10;
    private static final int VERTICAL_REGION_SIZE = 20;

    private WeatherProvider() {}

    public static WeatherProvider getInstance() {
        return INSTANCE;
    }

    public String getCurrentWeather(Coordinates coordinates) {
        int longitudeRegion = coordinates.getLongitude() / HORIZONTAL_REGION_SIZE;
        int latitudeRegion = coordinates.getLatitude() / HORIZONTAL_REGION_SIZE;
        int heightRegion = coordinates.getHeight() / VERTICAL_REGION_SIZE;

        // Combine region coordinates using long arithmetic.
        long hash = longitudeRegion * 73_856L
                  ^ latitudeRegion * 19_349L
                  ^ heightRegion * 83_492L;
        // Mix higher bits into lower bits.
        hash ^= hash >>> 8;
        int index = (int)Math.floorMod(hash, (long)weather.length);

        return weather[index];
    }
}
