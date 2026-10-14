/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   WeatherProvider.java                               :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: razaccar <razaccar@student.42lausanne.ch>  +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2026/09/24 16:46:28 by razaccar          #+#    #+#             */
/*   Updated: 2026/10/06 21:12:49 by razaccar         ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

package razaccar.avaj.simulator;

import razaccar.avaj.simulator.Coordinates;

public final class WeatherProvider {

   private static final WeatherProvider INSTANCE = new WeatherProvider();
   private String[] weather = {"SUN", "FOG", "RAIN", "SNOW"};

   private WeatherProvider() {}

   public static WeatherProvider getInstance() {
       return INSTANCE;
   }

   // temporary deterministic generation
   public String getCurrentWeather(Coordinates coordinates) {
       long sum = coordinates.getHeight() + coordinates.getLongitude() + coordinates.getLatitude();
       long idx = sum % weather.length;

       return weather[(int)idx];
   }
}
