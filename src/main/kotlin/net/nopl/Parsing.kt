package net.nopl

import net.nopl.anotation.Output
import net.nopl.anotation.TableName


interface Parsing {

    /**
     *  Array separator
     */
    private val arraySeparator: String
        get() = " , "

    /**
     *  Retrieving column values from an object,
     *      if you do not want the values to be displayed,
     *      mark them with the annotation "@Output", and they will not be taken into account.
     *
     * @param obj object in which column values are stored
     *
     * @return List<String>
     */
    fun getColumns(obj: Any) = obj::class.java.declaredFields.filter { field ->
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
    fun getRowValueList(obj: Any) = obj::class.java.declaredFields.filter { field ->
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