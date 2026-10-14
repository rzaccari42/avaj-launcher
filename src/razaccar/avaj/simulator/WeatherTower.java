/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   WeatherTower.java                                  :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: razaccar <razaccar@student.42lausanne.ch>  +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2026/09/16 06:10:30 by razaccar          #+#    #+#             */
/*   Updated: 2026/10/13 20:42:30 by razaccar         ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

package razaccar.avaj.simulator;

import razaccar.avaj.simulator.Coordinates;
import razaccar.avaj.simulator.WeatherProvider;

public class WeatherTower extends Tower {

    public String getWeather(Coordinates coordinates) {
        return WeatherProvider.getInstance().getCurrentWeather(coordinates);
    }

    public void changeWeather() throws SimulationException {
        conditionChanged();
    } 
}
