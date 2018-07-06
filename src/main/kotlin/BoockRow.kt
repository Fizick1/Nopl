import org.apache.poi.xssf.streaming.SXSSFRow
import org.apache.poi.xssf.streaming.SXSSFSheet
import java.awt.List
import java.util.*


class BookRow(val sheet: SXSSFSheet) {

    fun writeField(value: String, fieldName: String, startСolumn: Int = 0): SXSSFRow? {
        return SXSSFRow(sheet).apply {
            createCell(startСolumn).setCellValue(fieldName)
            createCell(startСolumn + 1).setCellValue(value)
        }
    }

    fun writeField(value: Int, fieldName: String, startСolumn: Int = 0): SXSSFRow? {
        return SXSSFRow(sheet).apply {
            createCell(startСolumn).setCellValue(fieldName)
            createCell(startСolumn + 1).setCellValue(value.toString())
        }
    }

    fun writeField() = SXSSFRow(sheet)




}