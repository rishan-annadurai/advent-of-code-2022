import java.io.File 

abstract class AMove() {
    // the score of this move
    abstract val score: Int 

    // returns the move against which this move would lose
    abstract fun getWinnerOpponent(): AMove
    
    // returns the move against which this move would win
    abstract fun getLoserOpponent(): AMove

    // returns the score of opposing a move
    fun getScoreAgainst(opponent:AMove): Int {
        if (opponent::class == getLoserOpponent()::class) {
            return 6
        }
        else if (opponent::class == getWinnerOpponent()::class) {
            return 0
        }
        else {
            return 3
        }
    }
}

class Rock() : AMove() {
    override val score : Int = 1
    override fun getWinnerOpponent(): AMove = Paper()
    override fun getLoserOpponent(): AMove = Scissors()
}

class Paper() : AMove() {
    override val score : Int = 2
    override fun getWinnerOpponent(): AMove = Scissors()
    override fun getLoserOpponent(): AMove = Rock()
}

class Scissors() : AMove() {
    override val score : Int = 3
    override fun getWinnerOpponent(): AMove = Rock()
    override fun getLoserOpponent(): AMove = Paper()
}


fun part1MoveFactory(encryption:Char): AMove {
    if (encryption == 'A' || encryption == 'X') {
        return Rock()
    }
    else if (encryption == 'B' || encryption == 'Y') {
        return Paper()
    }
    else if (encryption == 'C' || encryption == 'Z') {
        return Scissors()
    }
    else throw IllegalArgumentException("encryption $encryption was invalid")
}

fun part2MoveFactory(encryptedOpponentMove:Char, encryptedMyMove:Char): AMove {
    if (encryptedMyMove == 'X') { 
        return part1MoveFactory(encryptedOpponentMove).getLoserOpponent()
    }
    else if (encryptedMyMove == 'Y') { 
        return part1MoveFactory(encryptedOpponentMove)
    }
    else if (encryptedMyMove == 'Z') { 
        return part1MoveFactory(encryptedOpponentMove).getWinnerOpponent()
    }
    else throw IllegalArgumentException("encryption $encryptedMyMove was invalid")
}

fun getRoundScore(opponent:AMove, you:AMove): Int = you.score + you.getScoreAgainst(opponent)

fun main() {
    val fileName = "input.txt"
    val lines : List<String> = File(fileName).readLines()
    
    val part1Scores : List<Int> = lines.map { line -> getRoundScore(part1MoveFactory(line[0]), part1MoveFactory(line[2])) } 
    println("Part 1 total score: " + part1Scores.sum())

    val part2Scores : List<Int> = lines.map { line -> getRoundScore(part1MoveFactory(line[0]), part2MoveFactory(line[0], line[2])) } 
    println("Part 2 total score: " + part2Scores.sum())
}

