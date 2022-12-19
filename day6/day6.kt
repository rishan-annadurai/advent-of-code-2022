import java.io.File

fun main() {
    val fileName:String = "input.txt"
    val line:String = File(fileName).readLines()[0]
    
    println("Part 1 answer: " + getNumCharsToMarker(4, line))
    println("Part 2 answer: " + getNumCharsToMarker(14, line))
}


fun getNumCharsToMarker(numDistinctChars:Int, datastream:String): Int {
    val windows:List<String> = datastream.windowed(numDistinctChars, 1, false)
    val indexOfPattern:Int = windows.asSequence().indexOfFirst{ it.toSet().size == it.length }
    return indexOfPattern + numDistinctChars
}