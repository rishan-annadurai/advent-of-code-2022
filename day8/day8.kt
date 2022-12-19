import java.io.File

fun main() {
    val fileName:String = "input.txt"
    val lines:List<String> = File(fileName).readLines()

    val map:List<List<Int>> = lines.map{ line -> line.chunked(1).map{ char -> char.toInt() } }
    
    println("Part 1 answer: " + getNumVisibleTrees(map))

    println("Part 2 answer: " + getScenicScore(map))
}

fun getNumVisibleTrees(map:List<List<Int>>):Int {
    val numVisibleOnGridEdge:Int = (2 * map.size) + (2 * (map.size - 2))
    val numVisibleInterior:Int = getNumVisibleTreesInterior(map)

    return numVisibleOnGridEdge + numVisibleInterior
}

fun getNumVisibleTreesInterior(map:List<List<Int>>): Int {
    val visibleTreesInterior:MutableList<Int> = mutableListOf()

    for (i in 1..(map.size - 2)) {
        for (j in 1..(map[i].size - 2)) {
            val currentTree:Int = map[i][j]

            val treeRow:List<Int> = map[i]
            val leftTrees:List<Int> = treeRow.subList(0, j)
            val rightTrees:List<Int> = treeRow.subList(j + 1, map[i].size)

            val treeColumn:List<Int> = map.map{ it -> it[j]}
            val topTrees:List<Int> = treeColumn.subList(0, i)
            val bottomTrees:List<Int> = treeColumn.subList(i + 1, treeColumn.size)

            if (leftTrees.max() < currentTree || rightTrees.max() < currentTree || 
            topTrees.max() < currentTree || bottomTrees.max() < currentTree) {
                visibleTreesInterior.add(currentTree)
            }
        }
    }

    return visibleTreesInterior.size
}


fun getScenicScore(map:List<List<Int>>): Int {
    val scenicScores:MutableList<Int> = mutableListOf()

    for (i in 1..(map.size - 2)) {
        for (j in 1..(map[i].size - 2)) {
            val currentTree:Int = map[i][j]

            val treeRow:List<Int> = map[i]
            val leftTrees:List<Int> = treeRow.subList(0, j)
            val rightTrees:List<Int> = treeRow.subList(j + 1, map[i].size)
            
            val treeColumn:List<Int> = map.map{ it -> it[j]}
            val topTrees:List<Int> = treeColumn.subList(0, i)
            val bottomTrees:List<Int> = treeColumn.subList(i + 1, treeColumn.size)
            
            val leftScore:List<Int> = leftTrees.asReversed().asSequence().takeWhileInclusive{ it < currentTree}.toList()
            val rightScore:List<Int> = rightTrees.asSequence().takeWhileInclusive{ it < currentTree}.toList()
            val topScore:List<Int> = topTrees.asReversed().asSequence().takeWhileInclusive{ it < currentTree}.toList()
            val bottomScore:List<Int> = bottomTrees.asSequence().takeWhileInclusive{ it < currentTree}.toList()

            scenicScores.add(leftScore.size * rightScore.size * topScore.size * bottomScore.size)
        }
    }
    
    return scenicScores.max()
}


// used from https://jivimberg.io/blog/2018/06/02/implementing-takewhileinclusive-in-kotlin/
fun <T> Sequence<T>.takeWhileInclusive(
        predicate: (T) -> Boolean
): Sequence<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}