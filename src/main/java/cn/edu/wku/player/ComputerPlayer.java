package cn.edu.wku.player;

import cn.edu.wku.util.ChessBoard;
import cn.edu.wku.util.Move;

public class ComputerPlayer extends Player{

    // Privately kept chessboard for the algorithm to analyze
    private final ChessBoard ownBoard = new ChessBoard();

    public ComputerPlayer(boolean isBlack, String name) {
        super(isBlack, name);
    }

    @Override
    public void sendMove(Move lastMove) {
        // Record the move to the chessboard
        ownBoard.dropChess(lastMove, !getColor());
    }

    @Override
    public Move getMove(Move lastMove) {
        // Record the move to the chessboard
        ownBoard.dropChess(lastMove, !getColor());
        // Get next move and record
        Move nextMove = getNextMove();
        ownBoard.dropChess(nextMove, getColor());
        // Submit move
        return nextMove;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    /**Score the 1020 quintuplets of 15*15 respectively.
     *The score of a quintuplets is the score that the quintuplets contributes to each position,
     *and the score of a position is the sum of the number of all quintuplets in it.
     *The position with the highest score among all empty positions is the placement position.
     */
    private Move getNextMove(){
        int[][] chessBoard = ownBoard.getBoardAsArray();

        //Initialize the score array every time
        int[][] score = new int[19][19];

        int goalX = -1;
        int goalY = -1;

        //The number of black chess pieces in the quintuple
        int humanChessNum = 0;
        //The number of white chess in the quintuple
        int machineChessNum = 0;
        //Temporary variable for quintuple score
        int tupleScoreTmp = 0;

        int maxScore = -1;
        //1. Scan 19 horizontal lines
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 15; j++){
                int k = j;
                //The number of chess pieces in each 5-tuple
                while(k < j + 5){
                    if(chessBoard[i][k] == 2) machineChessNum++;
                    else if(chessBoard[i][k] == 1) humanChessNum++;
                    k++;
                }
                tupleScoreTmp = tupleScore(humanChessNum, machineChessNum);
                //Add a score for each position of the five-tuple
                for(k = j; k < j + 5; k++){
                    score[i][k] += tupleScoreTmp;
                }
                //set to 0
                humanChessNum = 0;
                machineChessNum = 0;
                tupleScoreTmp = 0;
            }
        }

        //2. Scan 19 vertical lines
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 15; j++){
                int k = j;
                while(k < j + 5){
                    if(chessBoard[k][i] == 2) machineChessNum++;
                    else if(chessBoard[k][i] == 1) humanChessNum++;
                    k++;
                }
                tupleScoreTmp = tupleScore(humanChessNum, machineChessNum);

                for(k = j; k < j + 5; k++){
                    score[k][i] += tupleScoreTmp;
                }

                humanChessNum = 0;
                machineChessNum = 0;
                tupleScoreTmp = 0;
            }
        }

        //3. Scan the upper right corner to the upper left corner
        for(int i = 18; i >= 4; i--){
            for(int k = i, j = 0; j < 19 && k >= 0; j++, k--){
                int m = k;
                int n = j;
                while(m > k - 5 && k - 5 >= -1){
                    if(chessBoard[m][n] == 2) machineChessNum++;
                    else if(chessBoard[m][n] == 1)humanChessNum++;
                    m--;
                    n++;
                }

                if(m == k - 5){
                    tupleScoreTmp = tupleScore(humanChessNum, machineChessNum);
                    for(m = k, n = j; m > k - 5 ; m--, n++){
                        score[m][n] += tupleScoreTmp;
                    }
                }

                humanChessNum = 0;
                machineChessNum = 0;
                tupleScoreTmp = 0;
            }
        }

        //4. Scan the upper right corner to the lower left corner
        for(int i = 1; i < 19; i++){
            for(int k = i, j = 18; j >= 0 && k < 15; j--, k++){
                int m = k;
                int n = j;
                while(m < k + 5 && k + 5 <= 19){
                    if(chessBoard[n][m] == 2) machineChessNum++;
                    else if(chessBoard[n][m] == 1)humanChessNum++;
                    m++;
                    n--;
                }

                if(m == k + 5){
                    tupleScoreTmp = tupleScore(humanChessNum, machineChessNum);
                    for(m = k, n = j; m < k + 5; m++, n--){
                        score[n][m] += tupleScoreTmp;
                    }
                }

                humanChessNum = 0;
                machineChessNum = 0;
                tupleScoreTmp = 0;
            }
        }

        //5. Scan the upper left corner to the upper right corner
        for(int i = 0; i < 15; i++){
            for(int k = i, j = 0; j < 19 && k < 19; j++, k++){
                int m = k;
                int n = j;
                while(m < k + 5 && k + 5 <= 19){
                    if(chessBoard[m][n] == 2) machineChessNum++;
                    else if(chessBoard[m][n] == 1)humanChessNum++;
                    m++;
                    n++;
                }

                if(m == k + 5){
                    tupleScoreTmp = tupleScore(humanChessNum, machineChessNum);
                    for(m = k, n = j; m < k + 5; m++, n++){
                        score[m][n] += tupleScoreTmp;
                    }
                }

                humanChessNum = 0;
                machineChessNum = 0;
                tupleScoreTmp = 0;
            }
        }

        //6. Scan the upper left corner to the lower right corner
        for(int i = 1; i < 15; i++){
            for(int k = i, j = 0; j < 19 && k < 19; j++, k++){
                int m = k;
                int n = j;
                while(m < k + 5 && k + 5 <= 19){
                    if(chessBoard[n][m] == 2) machineChessNum++;
                    else if(chessBoard[n][m] == 1)humanChessNum++;
                    m++;
                    n++;
                }

                if(m == k + 5){
                    tupleScoreTmp = tupleScore(humanChessNum, machineChessNum);
                    for(m = k, n = j; m < k + 5; m++, n++){
                        score[n][m] += tupleScoreTmp;
                    }
                }

                humanChessNum = 0;
                machineChessNum = 0;
                tupleScoreTmp = 0;
            }
        }

        //Find the position with the highest score from the empty positions
        for(int i = 0; i < 19; i++){
            for(int j = 0; j < 19; j++){
                if(chessBoard[i][j] == 0 && score[i][j] > maxScore){
                    goalX = i;
                    goalY = j;
                    maxScore = score[i][j];
                }
            }
        }
        return new Move(goalX, goalY);
    }

    //Various five-tuple situation scoring table
    private int tupleScore(int humanChessNum, int machineChessNum){
        //1. There is both a human and a machine, and the judgment is 0
        if(humanChessNum > 0 && machineChessNum > 0){
            return 0;
        }
        //2. All are empty, there is no place, the judgement is 7
        if(humanChessNum == 0 && machineChessNum == 0){
            return 7;
        }
        //3. The machine drops 1 pieces, and the judgement is 35
        if(machineChessNum == 1){
            return 35;
        }
        //4. The machine drops 2 pieces, the judgement is 800
        if(machineChessNum == 2){
            return 800;
        }
        //5. The machine drops 3 pieces, and the judgement is 15000
        if(machineChessNum == 3){
            return 15000;
        }
        //6. The machine drops 4 pieces, and the judgement is 800,000
        if(machineChessNum == 4){
            return 800000;
        }
        //7. Humans get 1 pieces, and the judgement is 15
        if(humanChessNum == 1){
            return 15;
        }
        //8. Humans have 2 pieces, and the judgement is 400
        if(humanChessNum == 2){
            return 400;
        }
        //9. Humans have 3 pieces, and the judgment is 1800
        if(humanChessNum == 3){
            return 1800;
        }
        //10. Humans have 4 pieces, and the judgement is 100000
        if(humanChessNum == 4){
            return 100000;
        }
        //If the other results are definitely wrong. This line of code is impossible to execute
        return -1;
    }
}
