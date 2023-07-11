package minesweeper

import kotlin.random.Random

/**
 * Project: Minesweeper
 * Stage 5/5: Battle!
 */

enum class Cell(val char: Char) {
    SAFE('.'),
    SAFE_EXPLORED_NO_MINES_AROUND('/'),
    SAFE_EXPLORED_MINES_AROUND('A'),
    SAFE_MARKED('*'),
    MINE('X'),
    MINE_MARKED('Y')
}

private const val SPACE = ' '
private const val SIZE = 9
private const val COMMAND_IS_FREE = "free"
private const val COMMAND_IS_MINE = "mine"
private const val MSG_STEP_ON_MINE = "You stepped on a mine and failed!"
private const val MSG_CONGRATS = "Congratulations! You found all the mines!"

fun main() {
    print("How many mines do you want on the field? ")
    val nbrOfMines = readLine()!!.toInt()
    val mineField = MineField(nbrOfMines)

    // mineField.print()
    mineField.printAllCellsAsUnexplored()

    var isWon: Boolean
    var isFailed = false
    var isFirstFreeCommand = true
    var nbrOfMarkedMines = 0

    do {
        print("Set/unset mines marks or claim a cell as free: ")
        val (x, y, command) = readLine()!!.split(SPACE)
        val (column, row) = Pair(x.toInt() - 1, y.toInt() - 1)

        var exploredCell = mineField.get(row, column)
        when (command) {
            COMMAND_IS_FREE -> {
                if (isFirstFreeCommand && exploredCell == Cell.MINE) {
                    mineField.moveMineFrom(row, column)
                    exploredCell = mineField.get(row, column)
                    isFirstFreeCommand = false
                }
                when (exploredCell) {
                    Cell.SAFE, Cell.SAFE_MARKED -> {
                        mineField.exploreCell(row, column)
                    }

                    Cell.MINE, Cell.MINE_MARKED -> {
                        isFailed = true
                    }

                    else -> {}
                }
            }

            COMMAND_IS_MINE -> {
                when (exploredCell) {
                    Cell.SAFE -> {
                        mineField.set(row, column, Cell.SAFE_MARKED)
                    }

                    Cell.SAFE_MARKED -> {
                        mineField.set(row, column, Cell.SAFE)
                    }

                    Cell.MINE -> {
                        mineField.set(row, column, Cell.MINE_MARKED)
                        nbrOfMarkedMines++
                    }

                    Cell.MINE_MARKED -> {
                        mineField.set(row, column, Cell.MINE)
                        nbrOfMarkedMines--
                    }

                    else -> {}
                }
            }
        }

        // mineField.print()
        mineField.printButHideMines()

        if (isFailed) {
            println(MSG_STEP_ON_MINE)
        }

        isWon = mineField.areAllMinesMarked() || mineField.areAllSafeCellsExplored()
        if (isWon) {
            println(MSG_CONGRATS)
        }

    } while (!isWon && !isFailed)
}

class MineField(_numberOfMines: Int) {
    private val field = MutableList(SIZE) { MutableList(SIZE) { Cell.SAFE } }
    private val nbrOfMines = _numberOfMines
    private var nbrOfEmptyCellsExplored = 0

    init {
        var n = 1
        while (n <= nbrOfMines) {
            val row = Random.nextInt(0, SIZE)
            val column = Random.nextInt(0, SIZE)
            if (field[row][column] == Cell.SAFE) {
                field[row][column] = Cell.MINE
                n++
            }
        }
    }

    fun get(row: Int, column: Int) = field[row][column]

    fun set(row: Int, column: Int, cell: Cell) {
        field[row][column] = cell
    }

    /**
     * Move randomly the mine to another cell if field[row][column] == MINE
     */
    fun moveMineFrom(row: Int, column: Int) {
        if (field[row][column] == Cell.MINE) {
            do {
                val r = Random.nextInt(0, SIZE)
                val c = Random.nextInt(0, SIZE)
                if ((r != row || c != column) && (field[r][c] != Cell.MINE)) {
                    if (field[r][c] == Cell.SAFE) {
                        field[r][c] = Cell.MINE
                        field[row][column] = Cell.SAFE
                        break
                    }
                }
            } while (true)
        }
    }

    /**
     * Return true if all the mines are marked.
     */
    fun areAllMinesMarked(): Boolean {
        for (r in 0 until SIZE) {
            for (c in 0 until SIZE) {
                if (field[r][c] == Cell.MINE) return false
            }
        }
        return true
    }

    /**
     * Return true if all the safe cells are explored.
     */
    fun areAllSafeCellsExplored(): Boolean {
        for (r in 0 until SIZE) {
            for (c in 0 until SIZE) {
                if (field[r][c] == Cell.SAFE) return false
            }
        }
        return true
    }

    /**
     * Return the number of mines around a cell.
     */
    private fun getNbrOfMinesAround(row: Int, column: Int): Int {
        var nbrOfMines = 0
        for (r in -1..1) {
            for (c in -1..1) {
                if ((row + r in 0 until SIZE) && (column + c in 0 until SIZE)) {
                    if (!((row + r == row) && (column + c == column))) {
                        if (field[row + r][column + c] in listOf(Cell.MINE, Cell.MINE_MARKED)) {
                            nbrOfMines++
                        }
                    }
                }
            }
        }
        return nbrOfMines
    }

    /**
     * Explore a cell.
     * 1. If the cell is empty and has no mines around, all the surrounding cells, including the marked ones, can be
     * explored, and it should be done automatically. Also, if next to the explored cell there is another empty one
     * with no mines around, all the surrounding cells  should be explored as well, and so on, until no more can be
     * explored automatically.
     *
     * 2.  If a cell is empty and has mines around it, only that cell is explored, revealing a number of mines around it.
     */
    fun exploreCell(row: Int, column: Int) {
        if ((row in 0 until SIZE) && (column in 0 until SIZE)) {
            val cell = field[row][column]
            if (cell in listOf(Cell.SAFE, Cell.SAFE_MARKED)) {
                nbrOfEmptyCellsExplored++
                if (getNbrOfMinesAround(row, column) != 0) {
                    field[row][column] = Cell.SAFE_EXPLORED_MINES_AROUND
                } else {
                    field[row][column] = Cell.SAFE_EXPLORED_NO_MINES_AROUND
                    // Explore adjacent cells
                    for (r in -1..1) {
                        for (c in -1..1) {
                            if (!((row + r == row) && (column + c == column))) {
                                exploreCell(row + r, column + c)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun printHead() {
        print(" |")
        for (i in 0 until SIZE) print(i + 1)
        println("|")
    }

    private fun printLine() {
        print("—|")
        for (i in 0 until SIZE) print('—')
        println("|")
    }

    fun print() {
        println()
        printHead()
        printLine()
        for (r in 0 until SIZE) {
            println("${r + 1}|${field[r].map { it.char }.joinToString("")}|")
        }
        printLine()
    }

    fun printButHideMines() {
        println()
        printHead()
        printLine()
        for (r in 0 until SIZE) {
            print("${r + 1}|")
            for (c in 0 until SIZE) {
                when (field[r][c]) {
                    Cell.MINE -> print(Cell.SAFE.char)
                    Cell.MINE_MARKED -> print(Cell.SAFE_MARKED.char)
                    Cell.SAFE_EXPLORED_MINES_AROUND -> print('0' + getNbrOfMinesAround(r, c))
                    else -> print(field[r][c].char)
                }
            }
            println("|")
        }
        printLine()
    }

    fun printAllCellsAsUnexplored() {
        println()
        printHead()
        printLine()
        for (r in 0 until SIZE) {
            print("${r + 1}|")
            for (c in 0 until SIZE) {
                print(Cell.SAFE.char)
            }
            println("|")
        }
        printLine()
    }
}