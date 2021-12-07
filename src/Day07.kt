import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        return calculateMinimalConsumption(input, true)
    }

    fun part2(input: List<String>): Int {
        return calculateMinimalConsumption(input, false)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37) {
        "Failed to resolve part1"
    }

    check(part2(testInput) == 168) {
        "Failed to resolve part2"
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

private fun calculateMinimalConsumption(input: List<String>, cheap: Boolean): Int {
    val positions = input.first()
        .split(",")
        .mapToInt()
        .groupBy { it }

    val consumptions = mutableListOf<Int>()
    for (i in 0..positions.maxOf { it.key }) {
        consumptions.add(
            positions.map { it.key.consumptionTo(i, cheap) * it.value.size }.sum()
        )
    }
    return consumptions.minOf { it }
}


fun Int.consumptionTo(another: Int, cheap: Boolean = true): Int {
    val abs = max(this, another) - min(this, another)
    if (cheap) return abs

    return (0..abs).foldIndexed(0) { index, acc, i ->
        acc + index
    }
}