/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   Flyable.java                                       :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: razaccar <razaccar@student.42lausanne.ch>  +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2026/09/16 06:03:42 by razaccar          #+#    #+#             */
/*   Updated: 2026/10/13 20:51:08 by razaccar         ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

package razaccar.avaj.simulator;

import razaccar.avaj.simulator.WeatherTower;

public abstract class Flyable {
   
    protected WeatherTower weatherTower;
    
    public abstract void updateConditions() throws SimulationException ;

    public void registerTower(WeatherTower tower) throws SimulationException {
        if (this.weatherTower == null)
            this.weatherTower = tower;
        weatherTower.register(this);
    }
}
