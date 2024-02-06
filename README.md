# Chess Explorer - General Description
This application has been developed as part of the course on Object-Oriented Programming at UniMoRe.\
This application final goal is to be able to show the entries of a chess database and let the user select the game that he wants to analyze.\
After being selected, the user can move thorugh the various moves of the game and insert new line and comments.
# Class Implementation
The main class is the "Chessboard" class. It contains a vector of 64 object "ChessSquare". Each of those object represent a single chessboard square, with his color and the possible pieces placed on it.\
When the program loads it will initialize the "Chessboard" by loading the color of the squares and the pieces in the correct position.\
Each piece is loaded as a bmp in the "ChessSquare" and, by passing the Canva, each square draws it's own piece and square color.\
Each piece, besides it's bitmap is also represented by it's object. The concept of polymorphism is used. The main superclass is called "ChessPiece" and represents a generic chesspiece. An interface called "ChessOption" is used to declare the methods that are required for the correct operations, those methods are:
- GetPossibleMoves -> To get the legal move of the piece on the specific chessboard position
- MoveTo -> To move the piece from one chesssquare to another one
- CheckPawnPromotion -> To verify if the piece is a pawn and can be promoted
<!-- -->
Then, different classes are using this interface and superclass to implement ad-hoc methods. Those classes are:
- NBQ_Pieces -> This class represents the Knight, the Bishop and the Queen
- Pawn -> This class represent the pawn
- Rook -> This class represent the rook
- King -> This class represent the king

## Graphical Implmentation
<img align="left" width="160" src="https://github.com/NiubShock/ChessExplorer/assets/37460778/105cb33a-10e4-43bc-bf97-8ee21698b74e" />
<img align="left" width="160" src="https://github.com/NiubShock/ChessExplorer/assets/37460778/ae808afb-f0ad-4e86-b4ef-313e13b0758f" />
<img align="left" width="160" src="https://github.com/NiubShock/ChessExplorer/assets/37460778/2a721779-8ffb-4a0d-adcc-27140febf95e" />
<img align="left" width="160" src="https://github.com/NiubShock/ChessExplorer/assets/37460778/341e5c94-67f0-4f11-92da-835b3da83f60" />
<img align="left" width="170" src="https://github.com/NiubShock/ChessExplorer/assets/37460778/42802934-f85a-440c-a5bb-0cf3af0b104a" />
The first image shows the chessboard at the start of the game. The intial turn is for white as per the rules.</br>
The second board shows the legal moves of the selected piece (pawn on e5). These moves are passed as a list from the function GetPossibleMoves.</br>
The third image shows a check, when a player gives a check the king square becomes red. By selecting any pieces the legal moves returned will be only those that can parry or remove the check.</br>
The forth image shows the promotion possibilities, by clicking on one of them the pawn will become that piece instead. If the promotion arise a new check the king will be again on a red square and the legal moves must be those that remove/parry the check, as in the picture five.

## Chess Game Open Point
There are two main open points that will be implemented in the future:
1. En-Passant
2. Castle
