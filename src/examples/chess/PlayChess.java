package examples.chess;

import gameTheory.GameAction;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import tactical.HomunculusPlayer;
import tactical.PlayerType;

public class PlayChess
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		//double params[] = {0.2, 0.2, 0.6, 0.6, 0.85, 0.95, 0.2, 0.1, 0.4, 0.2, 0.1, 2.0, 0.5, 0.1, 1.5, 0.5, 15, 5.0, 5.0, 1.5, 0.75};
		ChessStyle strat = new ChessStyle();

		
		ChessState game = new ChessState(strat, strat);
		PlayerType nextUp = PlayerType.PLAYER_1;	
		Scanner read = new Scanner(System.in);
		
		System.out.println("\t" + strat.toString());
			
		for (int turn = 1; turn <= 200; turn++)
		{
			GameAction move = null;
			LinkedList<GameAction> moveList = game.getActions(nextUp);
			if (moveList == null || moveList.isEmpty())
			{
				break;
			}
			
			if (nextUp.equals(PlayerType.PLAYER_2))
			{
				double time = 0;
				double totalTime = 0;
				int depth;
				for(depth = 3; time <= 1.5; depth++)
				{
					long startTime = System.currentTimeMillis();
					HomunculusPlayer bot = new HomunculusPlayer(depth, nextUp);
					move = bot.AlphaBetaPruning(game);	
					long endTime = System.currentTimeMillis();
					time = (double)((endTime - startTime)) / 1000;
					totalTime += time;
				}				
				System.out.println(turn + ". " + nextUp + ": " + move + " (time=" + totalTime + "'', depth=" + (depth-1) + ")");	
				
			}
			else
			{
				String textMove = new String();
				do
				{
					System.out.print(turn + ". " + nextUp + ": ");
					textMove = read.nextLine();	
					move = extractMove(textMove, moveList);
				}while (move == null);				
			}

		
			
			game.Play(move);	
			System.out.println("\tUtil1: " + game.GetValue(PlayerType.PLAYER_1));
			
			
			if (nextUp.equals(PlayerType.PLAYER_1))
			{
				nextUp = PlayerType.PLAYER_2;
			}
			else
			{
				nextUp = PlayerType.PLAYER_1;
			}
		}
		game.printBoard();		
		
	}

	public static GameAction extractMove(String text, LinkedList<GameAction> list)
	{
		ChessMove move = null;
		for (GameAction action : list)
		{
			move = (ChessMove) action;
			if (move.toString().equals(text))
			{
				break;
			}
			else
			{
				move = null;
			}
		}
		
		return move;
	}

}