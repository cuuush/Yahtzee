package game;



public class Player {
	public final String name;
	private int[] dice;
	private boolean[] completedBonuses = new boolean[13];
	private int[] score = new int[13];
	
	private boolean rolled;
	private boolean reRolled;
	private boolean choseBonus;
	/*
	 *Ones				Any combination										The sum of dice with the number 1	
	 *Twos				Any combination										The sum of dice with the number 2	
	 *Threes			Any combination										The sum of dice with the number 3	
	 *Fours				Any combination										The sum of dice with the number 4	
	 *Fives				Any combination										The sum of dice with the number 5	
	 *Sixes				Any combination										The sum of dice with the number 6
	 *Three-Of-A-Kind	At least three dice the same						Sum of all dice
	 *Four-Of-A-Kind	At least four dice the same							Sum of all dice	
	 *Full House		Three of one number and two of another				25	
	 *Small Straight	Four sequential dice (1-2-3-4, 2-3-4-5, or 3-4-5-6)	30	
	 *Large Straight	Five sequential dice (1-2-3-4-5 or 2-3-4-5-6)		40	
	 *Yaht zee			All five dice the same								50	
	 *Chance			Any combination										Sum of all dice
	 * 
	 */
	public Player(String name){
		this.name = name;
	}

	/**
	 * Prompts the player to choose an action
	 */
	public void act() {
		roll();
		reRoll();
		reRoll();
		reRoll();
		chooseBonus();
		
			
	}
	
	public boolean isFinished()
	{
		for (boolean b : completedBonuses) {
			if(!b) //if bonus is not completed
				return false; // player still has bonuses
		}
		return true;
	}
	
	
	/**
	 * Updates dice with new values
	 */
	public void roll(){
		dice = DiceUtil.getRandomDice();
		UI.printRoll(dice);
	}

	/**
	 * Lets the user reroll designated dice
	 */
	public void reRoll(){
		int[] newDice = UI.promptForReRoll(dice);
		dice = DiceUtil.populateDiceArray(newDice);
		UI.printReRoll(dice);
	}
	

	/**
	 * Let the player choose a bonus
	 */
	public void chooseBonus(){
		int[] possibleBonuses = DiceUtil.calculateScores(dice);
		UI.displayBonuses(possibleBonuses, completedBonuses);
		int choice = UI.promptToChooseBonus();
		
		if(choice == 11 && completedBonuses[11] && score[11] != 0) //yathzee twice!
			score[11] += 100;
		
		else if (!completedBonuses[choice]) {
			score[choice] = possibleBonuses[choice];
			completedBonuses[choice] = true;
		} else {
			UI.invalidBonusChoice();
			chooseBonus();
		}
	}
	
	public int getNumericScore()
	{
		
		int upper = 0;
		int sum = 0;
		
		for (int i = 0; i < 6; i++) {
			upper += score[i];
		}
		
		if(upper >= 63)
			sum += 35;
		
		for(int x = 0; x < score.length; x++)
		{
			sum += score[x];
		}
		return sum;
	}
	


}
