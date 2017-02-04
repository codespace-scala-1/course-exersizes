TODO:
parse IP: String to Array[Byte]
      * requirements: - Input String: "2.112.10.234"
      * OutPut: Array[Byte] = {2,112,10,-22}
      * and to see result print OutPut

            TODO:    Tasks
        *   1.V_TODO how write 234 in type Byte?
        *     0 _127  -  прямой счет 0000 0000, 0000 0001, 0000 00010
        * Using value in app| In bitwise
        *         0           0000 0000
        *         1           0000 0001
        *         2           0000 0010
        *         127         0111 1111
        *         128(?/-0)   1000 0000  (_) - value in Scala
        *         129(-1)     1000 0001
        *         130(-2)     1000 0010
        *         255(-127)   1111 1111
        *   - organise using MyParse class,
        *     that allow convert -22 (234) to 234 in process getting value Array[Int]
        *   2. TDD in developed - TODO write test (by requirements and step by step relize implimentation
        */