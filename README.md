# Minesweeper
A project of JetBrains Academy's [Kotlin Core track](https://hyperskill.org/tracks/18). 

Implementation by pack.heng@gmail.com.

## 1. Description
Minesweeper is a game of logic where the player is presented with a field full of
hidden mines. The goal is to mark the positions of all mines without setting any of
them off. It's not a game of wild guessing: it offers hints showing the number of
mines around each cell. One wrong move, and game over!

The game starts with an unexplored minefield that has a user-defined number of mines.

The player can:
- Mark unexplored cells as cells that potentially have a mine, and also remove those marks. Any empty cell can be marked, not just the cells that contain a mine. The mark is removed by marking the previously marked cell.

- Explore a cell if they think it does not contain a mine.

There are three possibilities after exploring a cell:
1. If the cell is empty and has no mines around, all the cells around it, including the marked ones, can be explored, and it should be done automatically. Also, if next to the explored cell there is another empty one with no mines around, all the cells around it should be explored as well, and so on, until no more can be explored automatically.
2. If a cell is empty and has mines around it, only that cell is explored, revealing a number of mines around it.
3. If the explored cell contains a mine, the game ends and the player loses.

There are two possible ways to win:
1. Marking all the cells that have mines correctly.
2. Opening all the safe cells so that only those with unexplored mines are left.

The following symbols represent each cell’s state:
- . as unexplored cells
- / as explored free cells without mines around it
- Numbers from 1 to 8 as explored free cells with 1 to 8 mines around them, respectively
- X as mines
- \* as unexplored marked cells

## 2. Examples
The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.

**Example 1**: _the user loses after exploring a cell that contains a mine_
<pre>
How many mines do you want on the field? > 10

 │123456789│
—│—————————│
1│.........│
2│.........│
3│.........│
4│.........│
5│.........│
6│.........│
7│.........│
8│.........│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 3 2 free

 │123456789│
—│—————————│
1│.1///1...│
2│.1//12...│
3│11//1....│
4│////1....│
5│11111....│
6│.........│
7│.........│
8│.........│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 1 1 free

 │123456789│
—│—————————│
1│11///1...│
2│.1//12...│
3│11//1....│
4│////1....│
5│11111....│
6│.........│
7│.........│
8│.........│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 1 2 mine

 │123456789│
—│—————————│
1│11///1...│
2│*1//12...│
3│11//1....│
4│////1....│
5│11111....│
6│.........│
7│.........│
8│.........│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 8 8 free

 │123456789│
—│—————————│
1│11///1...│
2│*1//12...│
3│11//1....│
4│////1....│
5│11111....│
6│.........│
7│.........│
8│.......1.│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 7 8 free

 │123456789│
—│—————————│
1│11///1...│
2│*1//12...│
3│11//1....│
4│////1....│
5│11111....│
6│.........│
7│.........│
8│......11.│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 6 8 free

 │123456789│
—│—————————│
1│11///1...│
2│*1//12...│
3│11//1....│
4│////1....│
5│11111....│
6│.........│
7│.........│
8│.....211.│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 2 7 free

 │123456789│
—│—————————│
1│11///1...│
2│*1//12...│
3│11//1....│
4│////1....│
5│11111....│
6│.........│
7│.3.......│
8│.....211.│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 5 6 free

 │123456789│
—│—————————│
1│11///1X..│
2│X1//12...│
3│11//1X...│
4│////1....│
5│11111....│
6│.X..X....│
7│.3X...X..│
8│.X..X211.│
9│...X.....│
—│—————————│
You stepped on a mine and failed!
</pre>

**Example 2**: _the user wins by marking all mines correctly_

How many mines do you want on the field? > 8
<pre>
 │123456789│
—│—————————│
1│.........│
2│.........│
3│.........│
4│.........│
5│.........│
6│.........│
7│.........│
8│.........│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 5 5 free

 │123456789│
—│—————————│
1│..1//1...│
2│111//2...│
3│/////1...│
4│/////11..│
5│//////1..│
6│/111//1..│
7│23.1//111│
8│..21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 2 1 mine

 │123456789│
—│—————————│
1│.*1//1...│
2│111//2...│
3│/////1...│
4│/////11..│
5│//////1..│
6│/111//1..│
7│23.1//111│
8│..21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 3 7 mine

 │123456789│
—│—————————│
1│.*1//1...│
2│111//2...│
3│/////1...│
4│/////11..│
5│//////1..│
6│/111//1..│
7│23*1//111│
8│..21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 2 8 mine

 │123456789│
—│—————————│
1│.*1//1...│
2│111//2...│
3│/////1...│
4│/////11..│
5│//////1..│
6│/111//1..│
7│23*1//111│
8│.*21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 1 8 mine

 │123456789│
—│—————————│
1│.*1//1...│
2│111//2...│
3│/////1...│
4│/////11..│
5│//////1..│
6│/111//1..│
7│23*1//111│
8│**21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 7 3 mine

 │123456789│
—│—————————│
1│.*1//1...│
2│111//2...│
3│/////1*..│
4│/////11..│
5│//////1..│
6│/111//1..│
7│23*1//111│
8│**21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 8 3 free

 │123456789│
—│—————————│
1│.*1//1...│
2│111//2...│
3│/////1*1.│
4│/////11..│
5│//////1..│
6│/111//1..│
7│23*1//111│
8│**21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 9 3 free

 │123456789│
—│—————————│
1│.*1//1...│
2│111//2.31│
3│/////1*1/│
4│/////111/│
5│//////111│
6│/111//1..│
7│23*1//111│
8│**21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 8 6 mine

 │123456789│
—│—————————│
1│.*1//1...│
2│111//2.31│
3│/////1*1/│
4│/////111/│
5│//////111│
6│/111//1*.│
7│23*1//111│
8│**21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 7 2 free

 │123456789│
—│—————————│
1│.*1//1...│
2│111//2231│
3│/////1*1/│
4│/////111/│
5│//////111│
6│/111//1*.│
7│23*1//111│
8│**21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 7 1 mine

 │123456789│
—│—————————│
1│.*1//1*..│
2│111//2231│
3│/////1*1/│
4│/////111/│
5│//////111│
6│/111//1*.│
7│23*1//111│
8│**21/////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 9 1 mine

 │123456789│
—│—————————│
1│.*1//1*.*│
2│111//2231│
3│/////1*1/│
4│/////111/│
5│//////111│
6│/111//1*.│
7│23*1//111│
8│**21/////│
9│..1//////│
—│—————————│
Congratulations! You found all the mines!
</pre>

**Example 3**: _the user wins by exploring all safe cells_
<pre>
How many mines do you want on the field? > 5

 │123456789│
—│—————————│
1│.........│
2│.........│
3│.........│
4│.........│
5│.........│
6│.........│
7│.........│
8│.........│
9│.........│
—│—————————│
Set/unset mines marks or claim a cell as free: > 5 5 free

 │123456789│
—│—————————│
1│/////////│
2│/////111/│
3│111//1.1/│
4│..1//1.21│
5│111//1...│
6│/////1.21│
7│/////111/│
8│111//////│
9│..1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 1 9 free

 │123456789│
—│—————————│
1│/////////│
2│/////111/│
3│111//1.1/│
4│..1//1.21│
5│111//1...│
6│/////1.21│
7│/////111/│
8│111//////│
9│1.1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 1 4 free

 │123456789│
—│—————————│
1│/////////│
2│/////111/│
3│111//1.1/│
4│1.1//1.21│
5│111//1...│
6│/////1.21│
7│/////111/│
8│111//////│
9│1.1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 7 4 free

 │123456789│
—│—————————│
1│/////////│
2│/////111/│
3│111//1.1/│
4│1.1//1121│
5│111//1...│
6│/////1.21│
7│/////111/│
8│111//////│
9│1.1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 7 5 free

 │123456789│
—│—————————│
1│/////////│
2│/////111/│
3│111//1.1/│
4│1.1//1121│
5│111//11..│
6│/////1.21│
7│/////111/│
8│111//////│
9│1.1//////│
—│—————————│
Set/unset mines marks or claim a cell as free: > 8 5 free

 │123456789│
—│—————————│
1│/////////│
2│/////111/│
3│111//1.1/│
4│1.1//1121│
5│111//112.│
6│/////1.21│
7│/////111/│
8│111//////│
9│1.1//////│
—│—————————│
Congratulations! You found all the mines!
</pre>