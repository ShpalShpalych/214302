/* Напишите программу, которая позволяет пользователю вводить выражения
 с переменными (например, "x + y * 2") и сохранять переменные для последующего использования.
 Программа должна поддерживать операции присваивания и выводить результат вычисления.*/

import java.util.*

fun parseExpression(expression: String): List<String> {
    val tokens = mutableListOf<String>()
    var num = StringBuilder()

    for (char in expression) {
        when {
            char.isLetterOrDigit() -> num.append(char)
            char in "+-*/()" -> {
                if (num.isNotEmpty()) {
                    tokens.add(num.toString())
                    num = StringBuilder()
                }
                tokens.add(char.toString())
            }
        }
    }
    if (num.isNotEmpty()) {
        tokens.add(num.toString())
    }
    return tokens
}

fun precedence(op: String): Int {
    return when (op) {
        "+", "-" -> 1
        "*", "/" -> 2
        else -> 0
    }
}

fun infixToPostfix(expression: List<String>): List<String> {
    val stack = Stack<String>()
    val output = mutableListOf<String>()

    for (token in expression) {
        when {
            token.all { it.isLetterOrDigit() } -> output.add(token)
            token == "(" -> stack.push(token)
            token == ")" -> {
                while (stack.isNotEmpty() && stack.peek() != "(") {
                    output.add(stack.pop())
                }
                stack.pop()
            }
            else -> {
                while (stack.isNotEmpty() && precedence(stack.peek()) >= precedence(token)) {
                    output.add(stack.pop())
                }
                stack.push(token)
            }
        }
    }

    while (stack.isNotEmpty()) {
        output.add(stack.pop())
    }

    return output
}

fun evaluatePostfix(postfix: List<String>, variables: Map<String, Double>): Double {
    val stack = Stack<Double>()

    for (token in postfix) {
        when {
            token.toDoubleOrNull() != null -> stack.push(token.toDouble())
            token in variables -> stack.push(variables[token])
            else -> {
                val b = stack.pop()
                val a = stack.pop()
                when (token) {
                    "+" -> stack.push(a + b)
                    "-" -> stack.push(a - b)
                    "*" -> stack.push(a * b)
                    "/" -> stack.push(a / b)
                }
            }
        }
    }
    return stack.pop()
}

fun main() {
    print("Введите выражение: ")
    val expression = readlnOrNull() ?: return

    val tokens = parseExpression(expression)
    val postfix = infixToPostfix(tokens)

    val variables = mutableMapOf<String, Double>()

    for (token in postfix) {
        if (token.first().isLetter() && token !in variables) {
            print("Введите значение для $token: ")
            val value = readlnOrNull()?.toDoubleOrNull() ?: return
            variables[token] = value
        }
    }

    val result = evaluatePostfix(postfix, variables)
    println("Результат выражения: $result")
}