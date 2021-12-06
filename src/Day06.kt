fun main() {
    fun part1(input: List<String>): Int {
        var lanternFish = input.first().split(",").mapToInt()
        // println(lanternFish)

        // println("Initial state: $lanternFish")
        repeat(80) {
            lanternFish = lanternFish.increaseOneDay()
            // println("After ${it.inc()} day: ${lanternFish.joinToString(",")}")
        }

        return lanternFish.count()
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934) {
        "Failed to resolve part1"
    }

    val input = readInput("Day06")
    println(part1(input))
    // println(part2(input))
}

fun Int.addOneDay(): Int {
    return when (this) {
        0 -> 6
        else -> this - 1
    }
}

private fun List<Int>.increaseOneDay(): List<Int> {
    val state = mutableListOf<Int>()
    var newBorns = 0
    for (fish in this) {
        val previous = fish
        val newInternalTimer = fish.addOneDay()
        if (newInternalTimer == 6 && previous != 7) {
            newBorns++
        }
        state.add(newInternalTimer)
    }

    return state + (0 until newBorns).map { 8 }
}