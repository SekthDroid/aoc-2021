private const val One = '1'
private const val Zero = '0'

fun main() {
    fun part1(input: List<String>): Int {
        return input.findRate(true).toInt(2) * input.findRate(false).toInt(2)
    }

    fun part2(input: List<String>): Int {
        return input.find(true).toInt(2) * input.find(false).toInt(2)
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198) {
        "Failed to resolve part1"
    }

    check(part2(testInput) == 230) {
        "Failed to resolve part2"
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.findRate(omega: Boolean): String {
    val inputLength = this.first().length
    var reminder = 0
    var rate = ""
    while (reminder < inputLength) {
        var ones = 0
        var zeros = 0
        for (i in this) {
            if (i[reminder] == One) ones++ else zeros++
        }

        val candidate = when (omega) {
            true -> if (ones > zeros) One else Zero
            else -> if (ones < zeros) One else Zero
        }
        rate += candidate
        reminder++
    }

    return rate
}

private fun List<String>.find(oxygen: Boolean): String {
    val inputLength = this.first().length
    var filtered = this
    var reminder = 0
    while (reminder < inputLength) {
        var ones = 0
        var zeros = 0
        for (i in filtered) {
            if (i[reminder] == One) {
                ones++
            } else {
                zeros++
            }
        }
        val candidate = when (oxygen) {
            true -> if (ones >= zeros) One else Zero
            else -> if (zeros <= ones) Zero else One
        }
        filtered = filtered.filter { it[reminder] == candidate }
        if (filtered.size == 1) reminder = inputLength
        reminder++
    }

    return filtered.first()
}