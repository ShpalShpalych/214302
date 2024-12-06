/* Напишите многомодульную систему, которая управляет пользователями
 в рамках крупного проекта. Реализуйте классы для различных ролей (User, Admin, Guest, Moderator)
  с разными уровнями доступа и возможностью динамически изменять права.*/

fun main() {
    val userManager = UserManager()

    val admin = Admin("admin", "admin123")
    val moderator = Moderator("moderator", "mod123")
    val guest = Guest("guest")

    userManager.registerUser(admin)
    userManager.registerUser(moderator)
    userManager.registerUser(guest)

    println("Введите имя пользователя")
    var name = readLine()
    println("Введите пароль")
    var password = readLine()

    val user = userManager.login(name.toString(), password.toString())
    if (user != null) {
        println("Login successful for ${user.username}")
    } else {
        println("Login failed")
    }

    userManager.changePermissions(moderator, listOf("READ", "WRITE", "DELETE"))
    userManager.changePassword(admin, "newAdminPass")
    userManager.printUserPermissions(moderator)
}


