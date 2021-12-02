fun main() {
    fun part1(input: List<String>): Int {
        return input.fold(Position(), ::calculateLegacyInstruction).total()
    }

    fun part2(input: List<String>): Int {
        return input.fold(Position(), ::calculateModernInstruction).total()
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150) {
        "Failed to resolve part1"
    }
    check(part2(testInput) == 900) {
        "Failed to resolve part2"
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

private fun calculateLegacyInstruction(currentPosition: Position, command: String): Position = with(currentPosition) {
    val (operation, unit) = command.split(" ")
    val value = unit.toInt()
    return when (operation) {
        "forward" -> copy(horizontal = horizontal + value)
        "down" -> copy(depth = depth + value)
        else -> copy(depth = depth - value)
    }
}

private fun calculateModernInstruction(currentPosition: Position, command: String): Position = with(currentPosition) {
    val (operation, unit) = command.split(" ")
    val value = unit.toInt()
    return when (operation) {
        "forward" -> copy(horizontal = horizontal + value, depth = depth + aim * value)
        "down" -> copy(aim = aim + value)
        else -> copy(aim = aim - value)
    }
}

data class Position(val horizontal: Int = 0, val depth: Int = 0, val aim: Int = 0) {
    fun total(): Int {
        return horizontal * depth
    }
}