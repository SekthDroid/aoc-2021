fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.toInt() }.sumOfBigger()
    }

    fun part2(input: List<String>): Int {
        return input.map { it.toInt() }
            .windowed(3)
            .map { it.sum() }
            .sumOfBigger()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    println(part1(testInput))
    check(part1(testInput) == 7)

    println(part2(testInput))
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

private fun List<Int>.sumOfBigger(): Int = foldIndexed(0) { index, acc, i ->
    if (getOrElse(index + 1) { 0 } > i) {
        acc + 1
    } else {
        acc
    }
}
