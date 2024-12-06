open class User(val username: String, var password: String) : PermissionHolder {
    override var permissions = listOf("READ")

}

class Admin(username: String, password: String) : User(username, password) {
    init {
        permissions = listOf("READ", "WRITE", "DELETE")
    }
}

class Moderator(username: String, password: String) : User(username, password) {
    init {
        permissions = listOf("READ", "WRITE")
    }
}

class Guest(username: String) : User(username, "guest") {
    init {
        permissions = listOf("READ")
    }
}
