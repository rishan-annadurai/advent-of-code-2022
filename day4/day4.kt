import java.io.File

fun main() {
    val fileName:String = "input.txt"
    val lines:List<String> = File(fileName).readLines()
    val overlaps:List<String> = lines.filter { getOverlap(it) }
    println("Part 1: " + overlaps.size)

    val overlapsAtAll:List<String> = lines.filter { getOverlapAtAll(it) }
    print("Part 2: " + overlapsAtAll.size)
}

fun getOverlap(assignmentPair:String): Boolean {
    val assignments:List<String> = assignmentPair.split(',')
    val assignment1:List<String> = assignments[0].split('-')
    val assignment2:List<String> = assignments[1].split('-')
    
    val is1Overlap2:Boolean =
        assignment1[0].toInt() <= assignment2[0].toInt() && 
        assignment1[1].toInt() >= assignment2[1].toInt()
    val is2Overlap1:Boolean = 
        assignment1[1].toInt() <= assignment2[1].toInt() && 
        assignment1[0].toInt() >= assignment2[0].toInt()

    return is1Overlap2 || is2Overlap1
}

fun getOverlapAtAll(assignmentPair:String): Boolean {
    val assignments:List<String> = assignmentPair.split(',')
    val assignment1:List<String> = assignments[0].split('-')
    val assignment2:List<String> = assignments[1].split('-')
    
    val is1NotOverlap2:Boolean = 
        assignment1[0].toInt() < assignment2[0].toInt() && 
        assignment1[1].toInt() < assignment2[1].toInt() &&
        assignment1[1].toInt() < assignment2[0].toInt()
    val is2NotOverlap1:Boolean = 
        assignment1[0].toInt() > assignment2[0].toInt() && 
        assignment1[1].toInt() > assignment2[1].toInt() &&
        assignment1[0].toInt() > assignment2[1].toInt()

    return !is1NotOverlap2 && !is2NotOverlap1
}