fun main() {

    open class MyFile(val name: String, val type: String, val onDir: MyFile?) {
        var size = 0
        var subItens = mutableListOf<MyFile>()
        override fun toString(): String {
            return "$name ${if (type == "dir") "(dir [$size])" else "(file, $size)"}"
        }
    }

    var fileSystem = MyFile("/", "dir", null)
    var fsPointer = fileSystem

    fun moveTo(dir: String): MyFile {
        return when (dir) {
            "/" -> fileSystem
            ".." -> fsPointer.onDir ?: fileSystem
            else -> fsPointer.subItens.first { it.name == dir && it.type == "dir"}
        }
    }

    fun includeDir(fsPointer: MyFile, dirName: String) {
        val dir = fsPointer.subItens.find { it.name == dirName }
        dir ?: fsPointer.subItens.add(MyFile(dirName, "dir", fsPointer))
    }

    fun includeFile(fsPointer: MyFile, line: String) {
        val terms = line.split(" ")
        val size = terms[0].toInt()
        val fileName = terms[1]
        var file = fsPointer.subItens.find { it.name == fileName }
        if (file == null) {
            file = MyFile(fileName, "file", fsPointer)
            file.size = size
            fsPointer.subItens.add(file)
            var pointer = file.onDir
            while (pointer != null) {
                pointer.size += size
                pointer = pointer.onDir
            }
        }
    }

    fun getListing(input: List<String>, index: Int): List<String> {
        val listing = mutableListOf<String>()
        var i = index
        while (i < input.size && input[i].first() != '$') {
            listing.add(input[i])
            i++
        }
        return listing
    }

    fun processListing(fsPointer: MyFile, listing: List<String>) {
        listing.forEach {line ->
            if (line.startsWith("dir "))
                includeDir(fsPointer, line.substringAfter("dir "))
            else
                includeFile(fsPointer, line)
        }
    }

    fun printFSTree(dir: MyFile = fileSystem, spacing: Int = 0) {
        println("${" ".repeat(spacing)}- $dir${if (dir == fsPointer) "  <===" else ""}")
        dir.subItens.forEach {printFSTree(it, spacing + 3) }
    }

    fun walkTree(dir: MyFile = fileSystem,
                 predicate: (MyFile) -> Boolean,
                 result: MutableList<MyFile> = mutableListOf()
    ): MutableList<MyFile> {
        if (predicate(dir))
            result.add(dir)
        dir.subItens.forEach { walkTree(it, predicate, result)}
        return result
    }

    fun processCommands(input: List<String>, printingTree: Boolean = true) {
        var index = 0
        while (index <= input.lastIndex) {
            if (printingTree) println(input[index])
            if (input[index].startsWith("$ cd "))
                fsPointer = moveTo(input[index].substringAfter("$ cd "))
            else { //input[index].startsWith("$ ls")
                val listing = getListing(input, index + 1)
                processListing(fsPointer, listing)
                index += listing.size
            }
            if (printingTree) {
                printFSTree()
                println("======")
            }
            index++
        }
    }

    fun part1(input: List<String>): Int {
        fileSystem = MyFile("/", "dir", null)
        fsPointer = fileSystem
        processCommands(input, printingTree = false)

        val filesDirs = walkTree(fileSystem, { it.type == "dir" && it.size < 100_000 } )
        return filesDirs.sumOf { it.size }
    }

    fun part2(input: List<String>): Int {
        fileSystem = MyFile("/", "dir", null)
        fsPointer = fileSystem
        processCommands(input, printingTree = false)

        val totalSpace = 70_000_000
        val freeSpace = totalSpace - fileSystem.size
        val spaceToUpdate = 30_000_000
        val spaceNeeded = spaceToUpdate - freeSpace
        val filesDirs = walkTree(fileSystem, { it.type == "dir" && it.size >= spaceNeeded } )
        val dirToDelete = filesDirs.minBy { it.size }
        return dirToDelete.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))

}
