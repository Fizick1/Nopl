package net.nopl

import net.nopl.anotation.Output
import net.nopl.anotation.TableName
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import java.io.File
import java.io.FileOutputStream


class Nopl {

    /**
     *  A workbook in which data will be recorded.
     */
    private val workbook: SXSSFWorkbook = SXSSFWorkbook()

    /**
     *  Array separator
     */
    private val arraySeparator = " , "


    /**
     *  Saves the created workbook to a file.
     *  @param fileName file name
     *
     *  @return File
     */
    private fun saveWorckBook(fileName: String): File {
        val file = File("$fileName.xlsx")
        FileOutputStream(file).use { this.workbook.write(it) }
        return file
    }

    /**
     *  Preparation of the object for recording, the names of the columns and their data are recorded separately,
     *      so it is important to keep the order.
     *  @param list
     *  @param startCell
     *
     */
    fun writeTable(list: Collection<*>, startCell: StartCell = StartCell()) {
        val wb = workbook.createSheet("test")
        val obj = list.firstOrNull() ?: return

        WriteToSXSSFSheet(wb).writeColumnNames(getColumns(obj), startCell)

        list.forEachIndexed { index, it ->
            if (it != null)
                WriteToSXSSFSheet(wb).writeTableValues(getRowValueList(it), StartCell(startCell.startColumn, startCell.startRow + index))
            else
                WriteToSXSSFSheet(wb).EmptyRow
        }
        saveWorckBook(obj::class.java.name)
    }

    /**
     *  Retrieving column values from an object,
     *      if you do not want the values to be displayed,
     *      mark them with the annotation "@Output", and they will not be taken into account.
     *
     * @param obj object in which column values are stored
     *
     * @return List<String>
     */
    private fun getColumns(obj: Any) = obj::class.java.declaredFields.filter { field ->
        field.isAccessible = true
        val tableOutput = field.annotations.find { it is Output }
        tableOutput == null || (tableOutput as Output).output
    }.map { field ->
        field.isAccessible = true
        val tableNameAnotation = field.annotations.find { it is TableName }
        if (tableNameAnotation != null)
            (tableNameAnotation as TableName).name
        else
            field.name
    }

    /**
     *  Retrieving the column names from the object,
     *      if you do not want the columns to be displayed,
     *      mark them with annotation "@Output" and they will not be taken into account.
     *
     *  @param obj object in which column names are stored
     *
     *  @return List<String>
     *
     *  @see Output
     */
    private fun getRowValueList(obj: Any) = obj::class.java.declaredFields.filter { field ->
        field.isAccessible = true
        val tableOutput = field.annotations.find { it is Output }
        tableOutput == null || (tableOutput as Output).output
    }.map { field ->
        field.isAccessible = true
        val value = field.get(obj)
        when (value) {
            is String -> value.toString()
            is Char -> value.toString()
            is Number -> value.toString()
            is Collection<*> -> value.joinToString(separator = arraySeparator)
            else -> "Unknown type"
        }
    }
}