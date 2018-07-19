import org.apache.poi.xssf.streaming.SXSSFRow
import org.apache.poi.xssf.streaming.SXSSFSheet


class WriteToSXSSFSheet(private val sheet: SXSSFSheet) {
    /**
     *  Empty line
     */
    val EmptyRow = SXSSFRow(this.sheet)

    /**
     *  Preparing to record values.
     *  @param rowValues list with values
     *  @param startCell object with coordinates of the table location
     */
    fun writeTableValues(rowValues: List<String>, startCell: StartCell) {
        try {
            this.sheet.changeRowNum(writeValuesHorizontally(rowValues, startCell.startColumn), startCell.startRow + 1)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     *  Records values in a sheet, writes horizontally.
     *  @param rowValues list with values
     *  @param startColumnNum the line number with which to output the data
     *
     *  @return SXSSFRow
     */
    private fun writeValuesHorizontally(rowValues: List<String>, startColumnNum: Int): SXSSFRow {
        return SXSSFRow(this.sheet).apply {
            rowValues.forEachIndexed { index, value ->
                createCell(startColumnNum + index).setCellValue(value)
            }
        }
    }


    /**
     *  Preparing to record column names.
     *  @param columns list with column names
     *  @param startCell coordinate with the number of the initial column
     */
    fun writeColumnNames(columns: List<String>, startCell: StartCell) {
        try {
            this.sheet.changeRowNum(writeNamesHorizontally(columns, startCell.startColumn), startCell.startRow)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     *  Records column names in a sheet, writes horizontally.
     *  @param columns list with column names
     *  @param startColumnNum coordinate with the number of the initial column
     *
     *  @return SXSSFRow
     */
    private fun writeNamesHorizontally(columns: List<String>, startColumnNum: Int) = SXSSFRow(sheet).apply {
        columns.forEachIndexed { index, colName ->
            createCell(startColumnNum + index).setCellValue(colName)
        }
    }
}