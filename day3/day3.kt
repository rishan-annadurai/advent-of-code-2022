import java.io.File

fun main() {
    val fileName:String = "input.txt"
    val lines:List<String> = File(fileName).readLines()
    
    val duplicateItems:List<Char> = lines.map{ getDuplicateItem(it) }
    
    val itemTypes:String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val duplicateItemPriorities:List<Int> = duplicateItems.map{ itemTypes.indexOf(it) + 1 }
    
    println("Part 1 answer: " + duplicateItemPriorities.sum()) 

    val rucksacks:List<List<String>> = lines.chunked(3)
    val badges:List<Char> = rucksacks.map{ getBadgeItem(it) }
    val badgePriorities:List<Int> = badges.map{ itemTypes.indexOf(it) + 1 }
    
    println("Part 2 answer: " + badgePriorities.sum()) 
}


fun getDuplicateItem(compartments:String): Char {
    val numItemsPerCompartment:Int = compartments.length/2
    val compartment1:List<Char> = compartments.subSequence(0, numItemsPerCompartment).toList()
    val compartment2:List<Char> = compartments.subSequence(numItemsPerCompartment, compartments.length).toList()
    return compartment1.intersect(compartment2).first()
}

fun getBadgeItem(rucksacks:List<String>): Char {
    val group1:List<Char> = rucksacks[0].toList()
    val group2:List<Char> = rucksacks[1].toList()
    val group3:List<Char> = rucksacks[2].toList()
    return group1.intersect(group2).intersect(group3).first()
}