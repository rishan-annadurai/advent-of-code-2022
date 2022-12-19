import java.io.File

fun main() {
    val fileName = "input.txt"
    val lines : List<String> = File(fileName).readLines()
    val split : List<List<String>> = lines.split { it.isBlank() }
    val calories : List<List<Int>> = split.map { 
        it -> it.map { 
            it2 -> it2.toInt() 
            } 
    }

    val sumCalories : List<Int> = calories.map { it -> it.sum() }
    val sortedCaloriesDescending : List<Int> = sumCalories.sortedDescending()
    
    println("Elf with most calories: " + sortedCaloriesDescending[0])
    
    println("Sum of calories of top 3 elves: " + sortedCaloriesDescending.subList(0, 3).sum())
}


// used from https://stackoverflow.com/questions/67232143/kotlin-functional-way-to-split-list-into-grouped-lists
fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> =
    fold(mutableListOf(mutableListOf<T>())) { acc, t ->
        if (predicate(t)) acc.add(mutableListOf())
        else acc.last().add(t)
        acc
    }.filterNot { it.isEmpty() }

// fun List<String>.split(): List<List<String>> {
//     val result = listOf()
//     for (s:String in this) {
//         if (s.isEmpty()) {

//         }
//     }

// }