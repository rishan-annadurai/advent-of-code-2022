import java.io.File
import kotlin.math.abs

fun main() {
    val fileName:String = "input.txt"
    val lines:List<String> = File(fileName).readLines()

    val knot:ElvenRope = ElvenRope(2)
    lines.forEach{ knot.move(it) }
    println("Part 1 answer: " + knot.getPositionsVisitedByTail().toSet().size)

    val knotPart2:ElvenRope = ElvenRope(10)
    lines.forEach{ knotPart2.move(it) }
    println("Part 2 answer: " + knotPart2.getPositionsVisitedByTail().toSet().size)
}

class ElvenRope {
    val s = Pair(0, 0)
    val knots:MutableList<Pair<Int,Int>> = mutableListOf()

    val visitedByTail:MutableList<Pair<Int, Int>> = mutableListOf()
    
    constructor(numKnots:Int) {
        for (i in 1..numKnots) {
            knots.add(s)
        }

        visitedByTail.add(this.knots[numKnots - 1])
    }

    fun move(command:String) {
        val (direction, steps) = command.split(" ")

        when (direction) {
            "R" -> moveRight(steps.toInt())
            "U" -> moveUp(steps.toInt())
            "L" -> moveLeft(steps.toInt())
            "D" -> moveDown(steps.toInt())
        }
    }

    fun updateTail(knot:Int) {
        if (!this.isTouching(knot)) {
            this.moveTail(knot)
            if (knot + 1 == this.knots.size) {
                visitedByTail.add(this.knots[knot])
            }
        }
    }

    fun moveHelper() {
        for (i in 1..this.knots.size - 1) {
            updateTail(i)
        }
    }

    fun moveRight(steps:Int) {
        if (steps > 0) {
            this.knots[0] = Pair(this.knots[0].first, this.knots[0].second + 1)
            moveHelper()
            moveRight(steps - 1)
        }
    }

    fun moveUp(steps:Int) {
        if (steps > 0) {
            this.knots[0] = Pair(this.knots[0].first + 1, this.knots[0].second)
            moveHelper()
            moveUp(steps - 1)
        }
    }

    fun moveLeft(steps:Int) {
        if (steps > 0) {
            this.knots[0] = Pair(this.knots[0].first, this.knots[0].second - 1)
            moveHelper()
            moveLeft(steps - 1)
        }
    }

    fun moveDown(steps:Int) {
        if (steps > 0) {
            this.knots[0] = Pair(this.knots[0].first - 1, this.knots[0].second)
            moveHelper()
            moveDown(steps - 1)
        }
    }

    fun isTouching(knot:Int): Boolean {
        val isOverlapping:Boolean = this.knots[knot] == this.knots[knot - 1]
        val isAdjacent:Boolean = abs(this.knots[knot].first - this.knots[knot - 1].first) <= 1 &&
                                    abs(this.knots[knot].second - this.knots[knot - 1].second) <= 1

        return isOverlapping || isAdjacent
    }

    fun moveTail(knot:Int) {
        if (this.knots[knot - 1].second == this.knots[knot].second) {
            when (this.knots[knot - 1].first - this.knots[knot].first) {
                2 -> this.knots[knot] = Pair(this.knots[knot].first + 1, this.knots[knot].second)
                -2 -> this.knots[knot] = Pair(this.knots[knot].first - 1, this.knots[knot].second)
            }
        }
        else if (this.knots[knot - 1].first == this.knots[knot].first) {
            when (this.knots[knot - 1].second - this.knots[knot].second) {
                2 -> this.knots[knot] = Pair(this.knots[knot].first, this.knots[knot].second + 1)
                -2 -> this.knots[knot] = Pair(this.knots[knot].first, this.knots[knot].second - 1)
            }
        }
        else {
            when (this.knots[knot - 1].first - this.knots[knot].first) {
                2 -> when (this.knots[knot - 1].second - this.knots[knot].second) {
                    1 -> this.knots[knot] = Pair(this.knots[knot].first + 1, this.knots[knot].second + 1)
                    -1 -> this.knots[knot] = Pair(this.knots[knot].first + 1, this.knots[knot].second - 1)
                    2 -> this.knots[knot] = Pair(this.knots[knot].first + 1, this.knots[knot].second + 1)
                    -2 -> this.knots[knot] = Pair(this.knots[knot].first + 1, this.knots[knot].second - 1)
                }

                -2 -> when (this.knots[knot - 1].second - this.knots[knot].second) {
                    1 -> this.knots[knot] = Pair(this.knots[knot].first - 1, this.knots[knot].second + 1)
                    -1 -> this.knots[knot] = Pair(this.knots[knot].first - 1, this.knots[knot].second - 1)
                    2 -> this.knots[knot] = Pair(this.knots[knot].first - 1, this.knots[knot].second + 1)
                    -2 -> this.knots[knot] = Pair(this.knots[knot].first - 1, this.knots[knot].second - 1)
                }
                else -> {
                    when (this.knots[knot - 1].second - this.knots[knot].second) {
                        2 -> when (this.knots[knot - 1].first - this.knots[knot].first) {
                            1 -> this.knots[knot] = Pair(this.knots[knot].first + 1, this.knots[knot].second + 1)
                            -1 -> this.knots[knot] = Pair(this.knots[knot].first - 1, this.knots[knot].second + 1)
                        }

                        -2 -> when (this.knots[knot - 1].first - this.knots[knot].first) {
                            1 -> this.knots[knot] = Pair(this.knots[knot].first + 1, this.knots[knot].second - 1)
                            -1 -> this.knots[knot] = Pair(this.knots[knot].first - 1, this.knots[knot].second - 1)
                        }
                    }
                }
            }
        }
        
        if (knot + 1 == this.knots.size) {
            visitedByTail.add(this.knots[knot])
        }
    }

    fun getPositionsVisitedByTail(): List<Pair<Int, Int>> {
        return this.visitedByTail.map{ it -> it.copy() } 
    }
}