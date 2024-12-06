class UserManager {
    private val users = mutableListOf<User>()

    fun registerUser(user: User) {
        users.add(user)
    }

    fun login(username: String, password: String): User? {
        return users.find { it.username == username && it.password == password }
    }

    fun changePassword(user: User, newPassword: String) {
        user.password = newPassword
    }

    fun changePermissions(user: User, newPermissions: List<String>) {
        user.permissions = newPermissions
    }

    fun printUserPermissions(user: User) {
        println("${user.username} has permissions: ${user.permissions.joinToString(", ")}")
    }
}
