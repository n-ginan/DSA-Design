data class Singly<T:Any> (
    var value: T,
    var nextNode: Singly<T>? = null
)