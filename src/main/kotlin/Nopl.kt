import anotation.Name
import org.apache.poi.xssf.streaming.SXSSFSheet
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import java.io.File
import java.io.FileOutputStream


class Nopl {


    fun toSheet(obj: Any, sxssfSheet: SXSSFSheet) {
        return SXSSFWorkbookWrite().parse(sxssfSheet, obj)
//        SXSSFWorkbookWrite().parse(obj)
    }


    fun write(fileName: String, obj: Any): File {
        val workbook = SXSSFWorkbook()
        val sheet = workbook.createSheet("tust")

        toSheet(obj, sheet)

        val file = File("$fileName.xlsx")
        FileOutputStream(file).use { workbook.write(it) }
        return file
    }


}


fun main(args: Array<String>) {


    val testClass = listOf(
            Testus("gena", 99),
            Testus("тук", 56),
            Testus("кек", 12),
            Testus("гек", 73))

    Nopl().write("eeeee", testClass)
//    Nopl().write("eeeee",  Testus("гек", 73))


}

class Testus(
//        @Name("Имя")
        val name: String,
//        @Name("Возраст")
        val old: Int
)