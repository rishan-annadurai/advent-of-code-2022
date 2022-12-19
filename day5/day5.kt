import java.io.File
import kotlin.collections.ArrayDeque

fun main() {
    val fileName:String = "input.txt"
    val lines:List<String> = File(fileName).readLines()
    val input:List<List<String>> = lines.split{ it.isBlank() }

    val instructions:List<List<String>> = input[1].map { it -> it.split(" ").toList() }

    val move:List<Int>  = instructions.map{ it -> it[1].toInt() }
    val from:List<Int>  = instructions.map{ it -> it[3].toInt() }
    val to:List<Int>  = instructions.map{ it -> it[5].toInt() }
    
    val numCrateStacks:Int = input[0].last().trimEnd().last().digitToInt()
    val crates:List<List<String>> = input[0].map{ it -> it.chunked("[x] ".length) }

    val supplyStackPart1 = SupplyStackPart1(numCrateStacks, crates.subList(0, crates.size - 1).asReversed())

    move.zip(from.zip(to)).forEach{ 
        it -> supplyStackPart1.moveCrates(it.first, it.second.first, it.second.second) }

    println("Part 1 answer: " + supplyStackPart1.getTopCrateItems().filter { it:Char -> it.isLetter() })


    val supplyStackPart2 = SupplyStackPart2(numCrateStacks, crates.subList(0, crates.size - 1).asReversed())

    move.zip(from.zip(to)).forEach{ 
        it -> supplyStackPart2.moveCrates(it.first, it.second.first, it.second.second) }

    println("Part 2 answer: " + supplyStackPart2.getTopCrateItems().filter { it:Char -> it.isLetter() })    
}


open class SupplyStackPart1 {
    val crateStacks:MutableList<CrateStack> = mutableListOf()

    constructor(numCrates:Int, crateLayers:List<List<String>>) {
        for (i in numCrates downTo 1 step 1) {
            crateStacks.add(CrateStack())
        }

        crateLayers.forEach { 
            it -> it.forEachIndexed{ index, crate -> crateStacks[index].addCrate(crate) } 
        }
    }

    open fun moveCrates(amount:Int, fromCrateNum:Int, toCrateNum:Int) {
        if (amount > 0) {
            val crate:String = crateStacks[fromCrateNum - 1].removeCrate()
            crateStacks[toCrateNum - 1].addCrate(crate)
            this.moveCrates(amount - 1, fromCrateNum, toCrateNum)
        }
    }

    override fun toString():String {
        val s:StringBuilder = StringBuilder()
        
        crateStacks.forEachIndexed { index, it -> s.appendLine("Crate ${index + 1}: " + it.toString())} 
        return s.toString()
    }

    fun getTopCrateItems(): String {
        val s:StringBuilder = StringBuilder()
        crateStacks.forEach{ it -> s.append(it.getCrate())}
        return s.toString()
    }
}


class SupplyStackPart2(numCrates:Int, crateLayers:List<List<String>>) : SupplyStackPart1(numCrates, crateLayers) {
    override fun moveCrates(amount:Int, fromCrateNum:Int, toCrateNum:Int) {
        val cratesToMove:MutableList<String> = mutableListOf()

        for (i in amount downTo 1 step 1) {
            val crate:String = super.crateStacks[fromCrateNum - 1].removeCrate()
            cratesToMove.add(crate)
        }

        cratesToMove.asReversed().forEach{ crate -> super.crateStacks[toCrateNum - 1].addCrate(crate) }
    }
}


class CrateStack {
    val crateStack:ArrayDeque<String> = ArrayDeque<String>()

    fun addCrate(crate:String) {
        if (!crate.isBlank()) {
            val trimmedCrate:String = crate.filter { it -> !it.isWhitespace() }
            crateStack.addLast(trimmedCrate)
        }
    }

    fun removeCrate(): String {
        return this.crateStack.removeLast()
    }

    fun getCrate(): String {
        return this.crateStack.last()
    }

    override fun toString():String {
        return crateStack.toString()
    }

}

// used from https://CrateStackoverflow.com/questions/67232143/kotlin-functional-way-to-split-list-into-grouped-lists
fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> =
    fold(mutableListOf(mutableListOf<T>())) { acc, t ->
        if (predicate(t)) acc.add(mutableListOf())
        else acc.last().add(t)
        acc
    }.filterNot { it.isEmpty() }