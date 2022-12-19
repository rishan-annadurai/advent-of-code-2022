import java.io.File 

fun main() {
    var sum:Int = 0
    val fileName:String = "input.txt"
    val lines:List<String> = File(fileName).readLines()
    
    val cycles:MutableList<Int> = mutableListOf(20, 60, 100, 140, 180, 220)
    val pixels:MutableList<Boolean> = mutableListOf() 

    val result:Pair<Int, Int> = lines.fold(Pair(1, 0)) {acc, instruction -> 
        val tokens = instruction.split(" ")
        var result:Pair<Int, Int> 

        val x:Int = acc.first
        val cycle:Int = acc.second % 40

        when(tokens[0]) {
            "noop" -> {
                for (i in 0..1) {
                    if (cycles.contains(acc.second + i)) {
                        sum += ((acc.second + i) * acc.first)
                        cycles.remove(acc.second + i)
                    }
                }

                if (x - 1 == cycle || x == cycle || x + 1 == cycle) pixels.add(true) else pixels.add(false)

                result = Pair(acc.first, acc.second + 1)}

            "addx" -> {
                for (i in 0..2) {
                    if (cycles.contains(acc.second + i)) {
                        sum += ((acc.second + i) * acc.first)
                        cycles.remove(acc.second + i)
                    }
                }

                for (i in 0..1) {
                    if (x - 1 == (cycle + i) || x == (cycle + i) || x + 1 == (cycle + i)) {
                        pixels.add(true) 
                    }
                    else {
                        pixels.add(false)
                    }
                }
                
                result = Pair(acc.first + tokens[1].toInt(), acc.second + 2)}
            else -> throw Exception()
        }

        result
    }

    println(result)
    println(sum)
    render(pixels)
}


fun render(pixels:MutableList<Boolean>) {
    val screen:List<List<Boolean>> = pixels.chunked(40)
    print(screen.size)

    val screenAsChar:List<List<Char>> = screen.map { it.map { it2 -> convertToPixel(it2)}  }

    screenAsChar.forEach{ println(it) }
}

fun convertToPixel(value:Boolean): Char {
    return if (value) '#' else '.'
}