@Suppress("UNCHECKED_CAST")

class Dynamic<T>(capacity: Int){

    private var capacity = capacity //size of the array
    private var elements = 0 //values inside the array
    private var array: Array<T?> = arrayOfNulls<Any>(this.capacity) as Array<T?>

    public fun add(value: T?) {
        when {
            array.contains(null) -> array[array.indexOf(null)] = value
            array[array.size - 1] != null -> {
                capacity += 1
                var newArr: Array<T?> = arrayOfNulls<Any>(capacity) as Array<T?>
                for (i in 0 until newArr.size) {
                    if (i + 1 == newArr.size) {
                        newArr[i] = value
                        break
                    }
                    newArr[i] = array[i]
                }
                array = newArr
            }
        }
    }
    
    public fun addAll(vararg values: T?) {
        var count = 0
        when {
            values.size >= capacity -> {
                array = arrayOfNulls<Any>(capacity + (values.size - capacity)) as Array<T?>
                capacity = capacity + (values.size - capacity)
                for (i in array.indexOf(null) until capacity) {
                    array[i] = values[count]
                    count++
                }
            }
            elements + values.size >= capacity -> {
                array = arrayOfNulls<Any>(capacity + (elements + values.size - capacity)) as Array<T?>
                capacity = capacity + (elements + values.size - capacity)
                for (i in array.indexOf(null) until capacity) {
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
        for (i in 0 until capacity) {
            when {
                array[i] == null -> return i
                i + 1 == capacity -> return i+1
            }
        }
        return -1
    }

    public fun capacity() : Int = this.capacity

    public fun get(value: Int) : T? = array[value]

    public fun setIndex(index: Int, value: T?) {array[index] = value}

    public fun remove(index: Int) {
        for (i in index until capacity - 1) {
            array[i] = array[i + 1]
        }
        --capacity
    }

}
