/* Реализуйте программу, которая принимает математическое выражение
 в виде строки, разбивает его на компоненты и использует лямбда-выражения
  для его вычисления. Программа должна поддерживать сложные выражения
  с вложенными скобками.*/

fun main() {
    println("Введите математическое выражение:")
    val expression = readLine()!!

    try {
        val result = evaluateExpression(expression)
        println("Результат: $result")
    } catch (e: Exception) {
        println("Ошибка: ${e.message}")
    }
}

fun evaluateExpression(expression: String): Double {
    // Удаляем все пробелы из выражения
    val cleanedExpression = expression.replace("\\s".toRegex(), "")

    // Парсим выражение в список токенов
    val tokens = tokenize(cleanedExpression)

    // Вычисляем результат
    return parse(tokens).first()
}

fun tokenize(expression: String): List<String> {
    val regex = "(\\d+(\\.\\d*)?|[+\\-*/()^])".toRegex()
    return regex.findAll(expression).map { it.value }.toList()
}

fun parse(tokens: List<String>): List<Double> {
    val values = mutableListOf<Double>()
    val ops = mutableListOf<String>()

    var i = 0
    while (i < tokens.size) {
        val token = tokens[i]
        when {
            token.isNumber() -> {
                values.add(token.toDouble())
            }
            token == "(" -> {
                ops.add(token)
            }
            token == ")" -> {
                while (ops.isNotEmpty() && ops.last() != "(") {
                    applyOperator(ops.removeAt(ops.size - 1), values)
                }
                ops.removeAt(ops.size - 1) // Убираем открывающую скобку
            }
            token in listOf("+", "-", "*", "/", "^") -> {
                while (ops.isNotEmpty() && precedence(ops.last()) >= precedence(token)) {
                    applyOperator(ops.removeAt(ops.size - 1), values)
                }
                ops.add(token)
            }
        }
        i++
    }

    while (ops.isNotEmpty()) {
        applyOperator(ops.removeAt(ops.size - 1), values)
    }

    return values
}

fun String.isNumber() = this.toDoubleOrNull() != null

fun precedence(op: String): Int {
    return when (op) {
        "+", "-" -> 1
        "*", "/" -> 2
        "^" -> 3
        else -> 0
    }
}

fun applyOperator(operator: String, values: MutableList<Double>) {
    val right = values.removeAt(values.size - 1)
    val left = values.removeAt(values.size - 1)

    val result = when (operator) {
        "+" -> left + right
        "-" -> left - right
        "*" -> left * right
        "/" -> left / right
        "^" -> Math.pow(left, right)
        else -> throw IllegalArgumentException("Неподдерживаемая операция: $operator")
    }

    values.add(result)
}
