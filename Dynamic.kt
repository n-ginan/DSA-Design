@Suppress("UNCHECKED_CAST")

data class Dynamic<T>(var initialCapacity: Int) : Iterable<Any?> {

    private var elements = 0 //values inside the array
    private var array: Array<Any?> = arrayOfNulls<Any?>(initialCapacity)

    override fun iterator(): Iterator<Any?> = array.iterator()

    fun add(value: Any?) {
        when {
            array.contains(null) -> array[array.indexOf(null)] = value
            array[initialCapacity - 1] != null -> {
                initialCapacity += 1
                val newArr: Array<Any?> = arrayOfNulls<Any?>(initialCapacity)
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

    fun addAll(vararg values: Any?) {
        var count = 0
        when {
            values.size >= initialCapacity -> {
                array = arrayOfNulls<Any?>(initialCapacity + (values.size - initialCapacity))
                initialCapacity += (values.size - initialCapacity)
                for (i in array.indexOf(null) until initialCapacity) {
                    array[i] = values[count]
                    count++
                }
            }
            elements + values.size >= initialCapacity -> {
                array = arrayOfNulls<Any?>(initialCapacity + (elements + values.size - initialCapacity))
                initialCapacity += (elements + values.size - initialCapacity)
                for (i in array.indexOf(null) until initialCapacity) {
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

//    inline fun <T : Any?> display(arr: Dynamic<T>, action: (Any?) -> Unit) {
//        for (i in 0 until arr.initialCapacity()) {
//            action(arr.get(i))
//        }
//    }

    inline fun <T : Any?> displayOnly(arr: Dynamic<T>, value: Any?, predicate: (Any?) -> Unit) {
        for (i in 0 until arr.initialCapacity()) {
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
        for (i in initialCapacity - 1 downTo 0) {
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
        for (i in 0 until initialCapacity) {
            when {
                array[i] == null -> return i
                i + 1 == initialCapacity -> return i+1
            }
        }
        return -1
    }

    fun initialCapacity() : Int = this.initialCapacity

    fun get(value: Int) : Any? = array[value]

    fun showContents() : String {
        if (initialCapacity == 0) {
            return "[]"
        }
        val builder = StringBuilder("[")
        for (i in 0 until initialCapacity) {
            if (i == initialCapacity - 1) {
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

    fun referenceToValue() {

        val numberList = mutableListOf<Any?>()
        val stringList = mutableListOf<String>()
        var count = 0

        for (i in 0 until initialCapacity) {
            when(array[i]) {
                is String -> stringList.add(array[i].toString())
                is Number, is Char, is Boolean -> numberList.add(array[i])
            }
        }

        for (string in stringList) {
            array[count] = string
            count++
        }

        count = 0

        for (number in numberList) {
            array[stringList.size + count] = number
            count++
        }

    }

    fun valueToReference() {

        val numberList = mutableListOf<Any?>()
        val stringList = mutableListOf<String>()
        var count = 0

        for (i in 0 until initialCapacity) {
            when(array[i]) {
                is String -> stringList.add(array[i].toString())
                is Number, is Char, is Boolean -> numberList.add(array[i])
            }
        }

        for (number in numberList) {
            array[count] = number
            count++
        }

        count = 0

        for (string in stringList) {
            array[count + numberList.size] = string
            count++
        }

    }

    fun removeAt(index: Int) {

        for (i in index until initialCapacity - 1) {
            array[i] = array[i + 1]
        }

        --initialCapacity
    }

    fun removeAll(value: Any) {
        val arr: Array<Any?> = array.filter{it != value}.toTypedArray()
        initialCapacity = arr.size
        array = arrayOfNulls<Any?>(initialCapacity)
        for (i in arr.indices) {
            array[i] = arr[i]
        }
    }

    fun removeRange(firstIndex: Int, lastIndex: Int) {
        val newArr: Array<Any?> = arrayOfNulls<Any?>(initialCapacity)
        for (i in newArr.indices) {
            if (i in firstIndex..lastIndex) {
                continue
            }
            newArr[i] = array[i]
        }
        initialCapacity = newArr.size - newArr.filter{it == null}.toTypedArray().size
        array = newArr.filterNotNull().toTypedArray()
    }

    fun clear() { array = arrayOfNulls<Any?>(this.initialCapacity) }

}
