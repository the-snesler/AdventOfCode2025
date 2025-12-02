package util

@Suppress("unused")
fun increment(pass: String): String {
    val lowercase = util.Constants.lowercase
    val password = StringBuilder(pass)
    for ((i, char) in password.withIndex().reversed()) {
        if (char == 'z') {
            password[i] = 'a'
        } else {
            password[i] = lowercase[lowercase.indexOf(char) + 1]
            break
        }
    }
    return password.toString()
}
