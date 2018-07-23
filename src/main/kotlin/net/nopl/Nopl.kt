package net.nopl

import org.apache.poi.xssf.streaming.SXSSFSheet
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import java.io.File
import java.io.FileOutputStream

class Nopl {

    /**
     *  Saves the created workbook to a file.
     *  @param fileName file name
     *
     *  @return File
     */
    private fun saveWorkBook(workbook: SXSSFWorkbook, fileName: String): File {
        val file = File("$fileName.xlsx")
        FileOutputStream(file).use { workbook.write(it) }
        return file
    }

    /**
     *  Writing data to the workbook, a new workbook is created, into which the record will be created.
     *  @param list     in collection format
     *  @param startCell    coordinates of the new table
     *
     */
    fun writeTable(list: Collection<*>, startCell: StartCell = StartCell()) {
        val sxssfWorkbook = SXSSFWorkbook()
        val obj = list.firstOrNull() ?: return
        val wb = sxssfWorkbook.createSheet(obj::class.java.name)
        WriteToSXSSFSheet(wb).write(list, startCell)
        saveWorkBook(sxssfWorkbook, obj::class.java.name)
    }

    /**
     * Writing data to the workbook. When you send your own workbook,
     *  a new page will be created in which the recording will take place.
     * @param list      in collection format
     * @param workbook      workbook
     * @param startCell     coordinates of the new table
     *
     */
    fun writeTable(list: Collection<*>, workbook: SXSSFWorkbook, startCell: StartCell = StartCell()) {
        val obj = list.firstOrNull() ?: return
        val wb = workbook.createSheet(obj::class.java.name)
        WriteToSXSSFSheet(wb).write(list, startCell)
    }

    /**
     * The new data will be added to the existing sheet, so as not to spoil the existing data on the sheet,
     *      specify the coordinates of the desired cell (the count starts with 0).
     *      If you overlay one data to another, the old ones will be retouched
     *
     * @param list      in collection format
     * @param workSheet     worksheet
     * @param startCell     coordinates of the new table
     */
    fun writeTable(list: Collection<*>, workSheet: SXSSFSheet, startCell: StartCell = StartCell()) {
        WriteToSXSSFSheet(workSheet).write(list, startCell)
    }
}