fun main() {

    open class MyFile(val name: String, val type: String, val onDir: MyFile?) {
        var size = 0
        var subItens = mutableListOf<MyFile>()
        override fun toString(): String {
            return "$name ${if (type == "dir") "(dir)" else "(file, $size)"}"
        }
    }

    val fileSystem = MyFile("/", "dir", null)
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

    fun processCommands(input: List<String>) {
        var index = 0
        while (index <= input.lastIndex) {
            if (input[index].startsWith("$ cd "))
                fsPointer = moveTo(input[index].substringAfter("$ cd "))
            else { //input[index].startsWith("$ ls")
                index++
                while (index < input.lastIndex && !input[index].startsWith("$")) {
                    if (input[index].startsWith("dir "))
                        includeDir(fsPointer, input[index].substringAfter("dir "))
                    else
                        includeFile(fsPointer, input[index])
                    index++
                }
            }
            index++
        }
    }

    fun part1(input: List<String>): Int {
        processCommands(input)
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 23)
    check(part2(testInput) == 23)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
