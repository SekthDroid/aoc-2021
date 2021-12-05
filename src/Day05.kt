import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { readVent(it) }
            .filterNot { it.isDiagonal() }
            .run(::calculateOverlaps)
    }

    fun part2(input: List<String>): Int {
        return input.map { readVent(it) }.run(::calculateOverlaps)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5) {
        "Failed to resolve part1"
    }

    check(part2(testInput) == 12) {
        "Failed to resolve part2"
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

private fun calculateOverlaps(ventDirections: List<VentDirection>): Int {
    return ventDirections.map { it.toTrace() }
        .flatten()
        .groupBy { it }
        .count{ it.value.size > 1 }
}

private class VentDirection(val start: Pair<Int, Int>, val end: Pair<Int, Int>) {

    fun isDiagonal(): Boolean {
        return abs(start.first - end.first) == abs(start.second - end.second)
    }

    fun toTrace(): List<Pair<Int, Int>> {
        return start.pathTo(end)
    }
}

private fun readVent(input: String): VentDirection {
    return input.split(" -> ")
        .map {
            it.split(",").mapToInt().run { first() to last() }
        }
        .run {
            VentDirection(first(), last())
        }
}