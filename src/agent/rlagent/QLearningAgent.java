package agent.rlagent;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import environnement.Action;
import environnement.Environnement;
import environnement.Etat;

import javax.swing.*;

/**
 * 
 * @author laetitiamatignon
 *
 */
public class QLearningAgent extends RLAgent{
	//TODO
	// pour chaque etat on associe une liste d'action avec la valeur associée
	HashMap<Etat, HashMap<Action, Double>> map;
	
	/**
	 * 
	 * @param alpha
	 * @param gamma
	 */
	public QLearningAgent(double alpha, double gamma,
			Environnement _env) {
		super(alpha, gamma,_env);
		//TODO
		map = new HashMap<>();
	}


	
	
	/**
	 * renvoi la (les) action(s) de plus forte(s) valeur(s) dans l'etat e
	 *  
	 *  renvoi liste vide si aucunes actions possibles dans l'etat 
	 */
	@Override
	public List<Action> getPolitique(Etat e) {
		//TODO
		List<Action> res = new ArrayList<>();
		Double valueMax = this.getValeur(e);
		if(map.get(e) != null) {
			System.out.println("valeur max = " + valueMax);
			for (Map.Entry<Action, Double> entry : map.get(e).entrySet()) {
				if (entry.getValue().equals(valueMax)) {
					res.add(entry.getKey());
				}
			}
		}
		else{
			for(Action a : this.env.getActionsPossibles(e)) {
				res.add(a);
				this.setQValeur(e,a,0);
			}
		}
		return res;
	}
	/**
	 * @return la valeur d'un etat
	 */
	@Override
	public double getValeur(Etat e) {
		Double res = 0.;
		if(map.get(e) != null)
			for(Double value : map.get(e).values())
				if (res < value)
					res = value;
		return res < 0 ? 0.0 : res;
	}

	/**
	 * 
	 * @param e
	 * @param a
	 * @return Q valeur du couple (e,a)
	 */
	@Override
	public double getQValeur(Etat e, Action a) {
		if((map.get(e) != null) && (map.get(e).get(a) != null)){
			return map.get(e).get(a);
		}
		setQValeur(e, a, 0.0);
		return map.get(e).get(a);
	}
	
	/**
	 * setter sur Q-valeur
	 */
	@Override
	public void setQValeur(Etat e, Action a, double d) {
		//TODO
		if(map.get(e)==null) {
			HashMap<Action, Double> couple = new HashMap<>();
			couple.put(a,d);
			map.put(e, couple);
		}
		else {
			map.get(e).put(a, d);
		}
		//mise a jour vmin et vmax pour affichage gradient de couleur
		//...

		if(vmin>d)
			vmin = d;
		if(vmax<d)
			vmax = d;
		map.get(e).put(a, d);
		
		this.notifyObs();
	}
	
	
	/**
	 *
	 * mise a jour de la Q-valeur du couple (e,a) apres chaque interaction <etat e,action a, etatsuivant esuivant, recompense reward>
	 * la mise a jour s'effectue lorsque l'agent est notifie par l'environnement apres avoir realise une action.
	 * @param e
	 * @param a
	 * @param esuivant
	 * @param reward
	 */
	@Override
	public void endStep(Etat e, Action a, Etat esuivant, double reward) {
		//TODO
		this.setQValeur(e, a, (1-alpha)*this.getQValeur(e,a) + alpha*(reward + gamma * this.getValeur(esuivant)));
	}

	@Override
	public Action getAction(Etat e) {
		this.actionChoisie = this.stratExplorationCourante.getAction(e);
		return this.actionChoisie;
	}

	/**
	 * reinitialise les Q valeurs
	 */
	@Override
	public void reset() {
		super.reset();
		this.episodeNb =0;
		//TODO
		map = new HashMap<>();
		
		this.notifyObs();
	}




	



	


}
