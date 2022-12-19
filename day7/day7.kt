import java.io.File
import kotlin.collections.ArrayDeque


fun main() {
    val fileName:String = "input.txt"
    val lines:List<String> = File(fileName).readLines()

    val fileSystem:FileSystem = FileSystem()
    fileSystem.processCommands(lines)

    val outermostDirectory:ElvenDirectory = fileSystem.getOutermostDirectory()
    val directorySizes:List<Int> = outermostDirectory.getDirectories().map{ it -> it.getTotalSize() }
    val deletableDirectories:List<Int> = directorySizes.filter{ it -> it <= 100000 }
    
    println("Part 1 answer: " + deletableDirectories.sum())


    val unusedSpace:Int = 70000000 - outermostDirectory.getTotalSize()
    val sortedDirectorySizes:List<Int> = directorySizes.sorted()
    val smallestDeletableDirectory:Int = sortedDirectorySizes.asSequence().find{ it -> it + unusedSpace >= 30000000 }!!
    
    println("Part 2 answer: " + smallestDeletableDirectory)
}

class FileSystem() {
    val directoriesVisited:ArrayDeque<ElvenDirectory> = ArrayDeque<ElvenDirectory>()

    fun getCurrentDirectory(): ElvenDirectory {
        return this.directoriesVisited.last()
    }

    fun getOutermostDirectory():ElvenDirectory {
        return this.directoriesVisited.first()
    }

    fun processCommands(commands:List<String>) {
        for (command in commands) {
            val tokenized:List<String> = command.split(" ")

            if (tokenized[0] == "$") {
                // cd
                val firstToken:String = tokenized[1]
                if (firstToken == "cd") {
                    val secondToken:String = tokenized[2]

                    if (secondToken == "..") {
                        this.directoriesVisited.removeLast()
                    }
                    else if (secondToken == "/") {
                        val outermostDirectory:ElvenDirectory = ElvenDirectory(secondToken)
                        this.directoriesVisited.addLast(outermostDirectory)
                    }
                    else {
                            this.directoriesVisited.addLast(
                                this.getCurrentDirectory().getDirectoryNamed(secondToken)!!)
                    }
                }
            }
            else if (tokenized.size > 1) {
                val firstToken:String = tokenized[0]
                val secondToken:String = tokenized[1]
                
                if (firstToken == "dir") {
                    val directory:ElvenDirectory = ElvenDirectory(secondToken)
                    this.getCurrentDirectory().addDirectory(directory)
                }
                else
                {
                    val file:ElvenFile = ElvenFile(secondToken, firstToken.toInt())
                    this.getCurrentDirectory().addFile(file)
                }
            }
        }
    }
}

data class ElvenFile(val name: String, val size: Int)

class ElvenDirectory(val name: String) {
    private val files:MutableList<ElvenFile> = mutableListOf()
    private val directories:MutableList<ElvenDirectory> = mutableListOf()

    fun addFile(file:ElvenFile) {
        this.files.add(file)
    }

    fun addDirectory(directory:ElvenDirectory) {
        this.directories.add(directory)
    }

    fun getDirectoryNamed(name:String): ElvenDirectory? {
        return this.directories.find { it.name == name }
    }

    fun getDirectories(): List<ElvenDirectory> {
        val subdirectories:List<ElvenDirectory> = this.directories.flatMap{ it -> it.getDirectories() }
        return subdirectories + this.directories
    }

    fun getTotalSize(): Int {
        val fileSize:Int = this.files.map{ it -> it.size }.sum()
        val subDirectoryFileSizes:Int = this.directories.map{ it -> it.getTotalSize() }.sum()
        return fileSize + subDirectoryFileSizes
    }
}

