package net.nopl.anotation

@Target(AnnotationTarget.FIELD)
@MustBeDocumented
annotation class TableName(val name: String)