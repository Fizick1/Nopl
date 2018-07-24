package net.nopl.anotation

@Target(AnnotationTarget.FIELD)
@MustBeDocumented
annotation class ColumnName(val name: String)