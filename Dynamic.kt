@Suppress("UNCHECKED_CAST")

class Dynamic<T>(capacity: Int){

    private var capacity = capacity //size of the array
    private var elements = 0 //values inside the array
    private var array: Array<T?> = arrayOfNulls<Any>(capacity) as Array<T?>

    public fun add(value: T?) { //update capacities and elements
        for (i in 0 until array.size) {
            if (array[i] == null) {
                array[i] = value
                break
            }
        }
    }
    
    public fun addAll(vararg values: T?) {
        var count = 0
        when {
            values.size >= array.size -> {
                array = arrayOfNulls<Any>(array.size + (values.size - array.size)) as Array<T?>
                capacity = array.size + (values.size - array.size)
                for (i in array.indexOf(null) until array.size) {
                    array[i] = values[count]
                    count++
                }
            }
            elements + values.size >= array.size -> {
                array = arrayOfNulls<Any>(array.size + (elements + values.size - array.size)) as Array<T?>
                capacity = array.size + (elements + values.size - array.size)
                for (i in array.indexOf(null) until array.size) {
                    array[i] = values[count]
                    count++
                }
            }
        }
    }
    
    public fun insertAt(index: Int, value: T?) : Unit { array[index] = value }
    
    public fun contains(value: T?) : Boolean {
        for (it in array) {
            if (it == value) {
                return true
            }
        }
        return false
    }
    
    public fun isEmpty() : Boolean {
        for (it in array) {
            if (it != null) {
                return false
            }
        }
        return true
    }

    public fun elements() : Int {
        for (i in 0 until array.size) {
            when {
                array[i] == null -> return i
                i + 1 == array.size -> return i+1
            }
        }
        return -1
    }

    public fun capacity() : Int = capacity

    public fun get(value: Int) : T? = array[value]

    public fun setIndex(index: Int, value: T?) {array[index] = value}

    public fun remove() {

    }

}
