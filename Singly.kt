data class Singly<T:Any> (
    var value: T,
    var nextNode: Singly<T>? = null
) {
    override fun toString() : String {
        return if (nextNode != null) {
            "$value -> ${nextNode.toString()}"
        } else {
            "$value"
        }
    }
}