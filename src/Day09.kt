fun main() {
    fun part1(input: List<String>): Int {
        val items = mutableListOf<Pair<Int, List<Int>>>()

        for ((yIndex, i) in input.withIndex()) {
            for ((xIndex, value) in i.withIndex()) {
                items.add(
                    value.digitToInt() to listOf(
                        input.getPointAt(xIndex, yIndex - 1),
                        input.getPointAt(xIndex, yIndex + 1),
                        input.getPointAt(xIndex - 1, yIndex),
                        input.getPointAt(xIndex + 1, yIndex),
                    )
                )
            }
        }

        return items
            .asSequence()
            .filter { it.isLowPoint() }
            .sumOf { it.first + 1 }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15) {
        "Failed to resolve part1"
    }

    /*check(part2(testInput) == 5) {
        "Failed to resolve part2"
    }*/

    val input = readInput("Day09")
    println(part1(input))
    // println(part2(input))
}

fun Pair<Int, List<Int>>.isLowPoint(): Boolean {
    for (i in second) {
        if (first >= i) {
            return false
        }
    }
    return true
}

fun List<String>.getPointAt(x: Int, y: Int): Int {
    return if (x < 0 || y < 0 || x >= first().length || y >= size) {
        9
    } else {
        this[y][x].digitToInt()
    }
}