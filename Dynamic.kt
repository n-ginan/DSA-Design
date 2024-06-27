@Suppress("UNCHECKED_CAST")

class Dynamic<T>(initialCapacity: Int) : Iterable<Any?> {

    private var capacity = initialCapacity //size of the array
    private var elements = 0 //values inside the array
    private var array: Array<Any?> = arrayOfNulls<Any?>(capacity)

    override fun iterator(): Iterator<Any?> = array.iterator()

    fun add(value: T?) {
        when {
            array.contains(null) -> array[array.indexOf(null)] = value
            array[capacity - 1] != null -> {
                capacity += 1
                val newArr: Array<Any?> = arrayOfNulls<Any?>(capacity)
                for (i in newArr.indices) {
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

    fun add(index: Int, value: T?) { array[index] = value }

    fun addAll(vararg values: T?) {
        var count = 0
        when {
            values.size >= capacity -> {
                array = arrayOfNulls<Any?>(capacity + (values.size - capacity))
                capacity += (values.size - capacity)
                for (i in array.indexOf(null) until capacity) {
                    array[i] = values[count]
                    count++
                }
            }
            elements + values.size >= capacity -> {
                array = arrayOfNulls<Any?>(capacity + (elements + values.size - capacity))
                capacity += (elements + values.size - capacity)
                for (i in array.indexOf(null) until capacity) {
                    array[i] = values[count]
                    count++
                }
            }
        }
    }


    fun contains(value: T?) : Boolean {
        for (it in array) {
            if (it == value) {
                return true
            }
        }
        return false
    }

    fun containsAll(col: Collection<T>) : Boolean {

        val arrMap = mutableMapOf<Any?, Int>()
        val colMap = mutableMapOf<Any?, Int>()

        for (it in array) {
            arrMap[it] = arrMap.getOrDefault(it, 0) + 1
        }

        for (it in col) {
            colMap[it] = colMap.getOrDefault(it, 0) + 1
        }

        return arrMap == colMap
    }

    inline fun <T : Any?> display(arr: Dynamic<T>, action: (Any?) -> Unit) {
        for (i in 0 until arr.capacity()) {
            action(arr.get(i))
        }
    }

    inline fun <T : Any?> displayOnly(arr: Dynamic<T>, value: Any?, predicate: (Any?) -> Unit) {
        for (i in 0 until arr.capacity()) {
            if (arr.get(i) == value) {
                predicate(value)
            }
        }
    }
    

    fun indexOf(value: Any) : Any {
        for (i in array.indices) {
            if (array[i] == value) return i
        }
        return when(value) {
            is String -> ""
            is Number -> -1
            else -> ""
        }
    }

    fun lastIndexOf(value: Any) : Int {
        for (i in capacity - 1 downTo 0) {
            if (array[i] == value) return i
        }
        return -1
    }
    
    fun isEmpty() : Boolean {
        for (it in array) {
            if (it != null) {
                return false
            }
        }
        return true
    }

    fun elements() : Int {
        for (i in 0 until capacity) {
            when {
                array[i] == null -> return i
                i + 1 == capacity -> return i+1
            }
        }
        return -1
    }

    fun capacity() : Int = this.capacity

    fun get(value: Int) : Any? = array[value]

    fun showContents() : String {
        if (capacity == 0) {
            return "[]"
        }
        val builder = StringBuilder("[")
        for (i in 0 until capacity) {
            if (i == capacity - 1) {
                builder.append("${array[i]}]")
                break
            }
            builder.append("${array[i]}, ")
        }
        return builder.toString()
    }

    fun setIndex(index: Int, value: T?) {array[index] = value}

    fun exchangeAll(firstIndex: Int, lastIndex: Int, vararg value: Any?) {
        require(value.size == lastIndex - firstIndex + 1) {"The size of the arguments should be equal to the provided indexes"}
        for (i in firstIndex..lastIndex) {
            array[i] = value[i - firstIndex]
        }
    }

    fun removeAt(index: Int) {
        for (i in index until capacity - 1) {
            array[i] = array[i + 1]
        }
        --capacity
    }

    fun removeAll(value: Any) {
        val arr: Array<Any?> = array.filter{it != value}.toTypedArray()
        capacity = arr.size
        array = arrayOfNulls<Any?>(capacity)
        for (i in arr.indices) {
            array[i] = arr[i]
        }
    }

    fun removeRange(firstIndex: Int, lastIndex: Int) {
        val newArr: Array<Any?> = arrayOfNulls<Any?>(capacity)
        for (i in newArr.indices) {
            if (i in firstIndex..lastIndex) {
                continue
            }
            newArr[i] = array[i]
        }
        capacity = newArr.size - newArr.filter{it == null}.toTypedArray().size
        array = newArr.filterNotNull().toTypedArray()
    }

    fun clear() { array = arrayOfNulls<Any?>(this.capacity) }

}
