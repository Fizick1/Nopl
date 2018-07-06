import anotation.Name
import org.apache.poi.xssf.streaming.SXSSFRow
import org.apache.poi.xssf.streaming.SXSSFSheet
import java.awt.List
import java.util.*

class SXSSFWorkbookWrite {

    fun parse(sxssfSheet: SXSSFSheet, obj: Any) {
        val objClass = obj::class.java
        objClass.declaredFields.forEachIndexed { index, field ->
            field.isAccessible = true
            val anotationNameval = field.annotations.find { it is Name }
            val fieldName = if (anotationNameval == null) field.name else (anotationNameval as Name).name
//            parse(sxssfSheet, field.get(obj), fieldName, index)
            parse(sxssfSheet, field.get(obj), fieldName, index)
        }
    }


    fun parse(sxssfSheet: SXSSFSheet, field: Any, fieldName: String, startRow: Int = 0) {
        val row = when (field) {
            is String -> {
                println("ddddd")
                BookRow(sxssfSheet).writeField(field, fieldName)
            }
            is Int -> {
                println("33333010101")
                BookRow(sxssfSheet).writeField(field, fieldName)
            }
            is Collection<*> -> {
                println("dsfsdsfsdfs1111")
                parse(sxssfSheet, field)
            }
            is List ->{
                println("ggggggg")
                BookRow(sxssfSheet).writeField()
            }
//            is Collection<*> -> BookRow(sxssfSheet).writeField()
            else -> {
                println(fieldName)
                println("22231231312312321312   333")
                BookRow(sxssfSheet).writeField()
            }
        }
        sxssfSheet.changeRowNum(row, startRow)
    }

    fun parse(sxssfSheet: SXSSFSheet, classs: Collection<*>): SXSSFRow {
        println("ccccc")
        println(classs.size)
        var test = classs.first()
        if (test != null) test = test::class.java else return SXSSFRow(null)

        test.declaredFields.forEach {
            parse(it)


        }


        return SXSSFRow(null)
    }


    fun parse(obj: Any?) {
        println("3333")
        if (obj == null) return

        val objClass = obj::class.java
        println(objClass.declaredFields.count())
        objClass.declaredFields.forEachIndexed { index, field ->
            field.isAccessible = true
            val a = field.get(objClass)
            when (a) {
                is String -> println("строка")
                is Int -> println("число")
                is Collection<*> -> println("1234")
                else -> println("ккккк")
            }

        }


    }


}