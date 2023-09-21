import java.util.Scanner;

public class Pawntastic {
	
	int rank;
	int mark, mark2;
	int distance; // 1 = 1 space, 2 = 2 spaces
	char init;
	int enpassant = 10;
	boolean ec = false;
	boolean takes = false;
	int x;
	char[] order = {'a','b','c','d','e','f','g','h'};
	
	public boolean isValid(String input, int[][] board, boolean white) {
		char[] in = input.toCharArray();
		x = board.length;
		if (in.length < 2) {
			return false;
		}
		if ((white && in[1] == '1') || (!white && in[1] == '8')) {
			return false;
		}
		if (!white && enpassant < 0) {
			enpassant = 10;
		}
		else if (white && enpassant > 0 && enpassant < x) {
			enpassant = 10;
		}
		
		char file = in[0];
		
		
		if (in[1] == 'x') {
		  init = file;
		  file = in[2];
		  mark = 0;
		  while (order[mark] != file) {
			 mark++; 
		  }
		  mark2 = 0;
		  while (order[mark2] != init) {
				 mark2++; 
			  }
		  
		  if (mark >= x || mark2 >= x || in.length != 4 || Character.getNumericValue(in[3]) < 1 || Character.getNumericValue(in[3]) > x + 1) {
			  return false;
		  }
		  rank = x - Character.getNumericValue(in[3]);
		  if (white) {
			if (board[rank+1][mark2] == 1 && board[rank][mark] == 2) {
				takes = true;
				return true;
			}
			else if (enpassant*-1 == mark && board[rank+1][mark2] == 1 && board[rank+1][mark] == 2) {
				ec = true;
				return true;
			}
			else {
				
				return false;
			}
		  }
		  else {
			  if (board[rank-1][mark2] == 2 && board[rank][mark] == 1) {
					takes = true;
					return true;
				} 
			  else if (enpassant == mark && board[rank-1][mark2] == 2 && board[rank-1][mark] == 1) {
					ec = true;
					return true;
				}
			  else {
					return false;
				}  
		  }
		  
		  
		}
		else {
		  if (in.length != 2) {
			  return false;
		  }
		  mark = 0;
		  while (order[mark] != file) {
			  mark++;
		  }
		  if (mark >= x || !Character.isDigit(in[1]) || Character.getNumericValue(in[1]) < 1 || Character.getNumericValue(in[1]) > x + 1) {
			 return false; 
		  }
		  rank = x - Character.getNumericValue(in[1]);
		  if (white) {
			if (board[rank][mark] == 0 && board[rank+1][mark] == 1) {
			   distance = 1;
			   return true;
			}
			else if (board[rank][mark] == 0 && board[rank+2][mark] == 1 && board[rank+1][mark] == 0 && rank + 2 == x - 2) {
			   distance = 2;
			   enpassant = mark;
			   return true;
			}
			else {
			  return false;
			}
		  }
		  else {
			  if (board[rank][mark] == 0 && board[rank-1][mark] == 2) {
				   distance = 1;
				   return true;
				}
				else if (board[rank][mark] == 0 && board[rank-2][mark] == 2 && board[rank-1][mark] == 0 && rank - 2 == 1) {
				   distance = 2;
				   enpassant = mark * -1;
				   return true;
				}
				else {
				  return false;
				}  
		  }
		  
		  
		  
		  
		}
		
	}
	
	public boolean win(boolean white) {
		if (white) {
		  if (rank == 0) {
			  System.out.println();
			  System.out.println("White wins");
			  return true;
		  }
		  else {
			  return false;
		  }
		}
		else {
		  if (rank == x - 1) {
			  System.out.println();
			  System.out.println("Black wins");
			  return true;
		  }
		  else {
			return false;  
		  }
		}
	}
	
	public int[][] move(int[][] board, boolean white) {
		
		if (white) {
		  if (takes) {
			  takes = false;
			  board[rank][mark] = 1;
			  board[rank+1][mark2] = 0;
		  }
		  else if (ec) {
			  ec = false;
			  board[rank+1][mark] = 0;
			  board[rank][mark] = 1;
			  board[rank+1][mark2] = 0;
		  }
		  else {
		  board[rank][mark] = 1;
		  board[rank+distance][mark] = 0;
		  }
		}
		else {
			if (takes) {
				takes = false;
				board[rank][mark] = 2;
				board[rank-1][mark2] = 0;
			}
			else if (ec) {
				ec = false;
				board[rank-1][mark] = 0;
				board[rank][mark] = 2;
				board[rank-1][mark2] = 0;
			}
			else {
			board[rank][mark] = 2;
			board[rank-distance][mark] = 0;	
			}
		}
		
		return board;
	}
	
	public void print(String input, int[][] board, boolean white) {
		if (white) {
			System.out.println();
			System.out.println("♙: " + input);
			System.out.println();
		}
		else {
			System.out.println();
			System.out.println("♟: " + input);
			System.out.println();
		}
		for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
				  if (j == 0) {
					  System.out.print(board.length - i);
				  }
				  if (board[i][j] == 1) {
					System.out.print(" ♙");  
				  }
				  else if (board[i][j] == 2) {
					System.out.print(" ♟");   
				  }
				  else {
					System.out.print(" ·");   
				  }
				}
				System.out.println();
			}
			System.out.print(" ");
			for (int i = 0; i < board.length; i++) {
				System.out.print(" " + order[i]);
			}
		
			
		}
	

	public static void main(String[] args) {
		
		Pawntastic one = new Pawntastic();
		
		Scanner s = new Scanner(System.in);
		System.out.print("Pawntastic by Elias Haddad, Astha Singh, Mason Wischhover\nNote: en passant capture is still TODO\nChoose your game:\n 4.  Tiny 4x4 Pawntastic\n 5.  Very small 5x5 Pawntastic\n 8.  Standard 8x8 Pawntastic\nEnter choice number to play\nYour choice? ");
		// System.out.println("♟");
		// System.out.println("♙");
		boolean white = true;
		boolean win = false;
		boolean loop = true;
		String move;
		String string = "0";
		
		
		while (loop) {
		string = s.nextLine();
		if (string.equals("4")) {
			System.out.println("4 · · · · \n3 ♟ ♟ ♟ ♟ \n2 ♙ ♙ ♙ ♙ \n1 · · · · \n  a b c d ");	
			loop = false;
		}
		else if (string.equals("5")) {
			System.out.println("5 · · · · · \n4 ♟ ♟ ♟ ♟ ♟ \n3 · · · · · \n2 ♙ ♙ ♙ ♙ ♙ \n1 · · · · · \n  a b c d e ");
			loop = false;
		}
		else if (string.equals("8")) {
			for (int i = 0; i < 8; i++) {
				if (i == 1) {
					System.out.println("7 ♟ ♟ ♟ ♟ ♟ ♟ ♟ ♟ ");
				}
				else if (i == 6) {
					System.out.println("2 ♙ ♙ ♙ ♙ ♙ ♙ ♙ ♙ ");
				}
				else {
					System.out.println((8 - i) + " · · · · · · · ·");
				}	
			}
			System.out.println("  a b c d e f g h ");
			loop = false;
		}
		else {
			System.out.println("Invalid input. Please enter either a 4, 5, or 8");
		}
		}
		
		System.out.println();
		int input = Integer.parseInt(string);
		int[][] board = new int[input][input];
		for (int i = 0; i < input; i++) {
		  board[1][i] = 2;
		  board[input-2][i] = 1;
		}
		
		while (!win) {
		 if (white) {
			System.out.println();
			System.out.print("Input a move for white: ");
			move = s.nextLine();
			if (one.isValid(move,board,white)) {
			  one.move(board, white);
			  one.print(move, board, white);
			  if (one.win(white)) {
				 win = true; 
			  }
			}
			else {
			  System.out.println("Invalid move. please try again");
			  continue;
			}
			white = false;
			
			
		}
		 else  {
			System.out.println();
			System.out.print("Input a move for black: ");
			move = s.nextLine();
			if (one.isValid(move,board,white)) {
				one.move(board, white);
				one.print(move, board, white);
				if (one.win(white)) {
					 win = true; 
				  }
			}
			else {
			    System.out.println("Invalid move. please try again");
				continue;
			}
			white = true;
			
		}
		}	
	}
}
