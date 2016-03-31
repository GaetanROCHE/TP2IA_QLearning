package agent.strategy;

import java.util.List;
import java.util.Random;

import agent.rlagent.RLAgent;
import environnement.Action;
import environnement.Etat;
/**
 * Strategie qui renvoit une action aleatoire avec probabilite epsilon, une action gloutonne (qui suit la politique de l'agent) sinon
 * Cette classe a acces a un RLAgent par l'intermediaire de sa classe mere.
 * @author lmatignon
 *
 */
public class StrategyGreedy extends StrategyExploration{
	//TODO

	Double epsilon;

	private Random rand=new Random();
	
	
	
	public StrategyGreedy(RLAgent agent,double epsilon) {
		super(agent);
		//TODO
		this.epsilon = epsilon;
	}

	/**
	 * @brief elle a une probabilité epsilon de renvoyer une action aléatoire
	 * et une probabilité 1-epsilon de suivre la politique.
	 * @return action selectionnee par la strategie d'exploration
	 */
	@Override
	public Action getAction(Etat _e) {
		//VOTRE CODE
		rand = new Random();
		//System.out.println("actions possibles : " + this.getAgent().getEnv().getActionsPossibles(_e).size());
		//System.out.println("actions de politiques possibles : " + this.getAgent().getPolitique(_e).size());
		//getAction renvoi null si _e absorbant
		if(this.getAgent().getEnv().estAbsorbant()){
			//System.out.println("top");
			return null;
		}
		else{
			if(rand.nextDouble() < epsilon){
				int choix = rand.nextInt(this.getAgent().getEnv().getActionsPossibles(_e).size());
				return this.getAgent().getEnv().getActionsPossibles(_e).get(choix);
			}else{
				int alea = rand.nextInt(this.getAgent().getPolitique(_e).size());
				return this.getAgent().getPolitique(_e).get(alea);
			}

		}
	}



	public void setEpsilon(double epsilon) {
		//VOTRE CODE
		this.epsilon = epsilon;
	}



}
