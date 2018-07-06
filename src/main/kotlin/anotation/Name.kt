package anotation

@Target(AnnotationTarget.FIELD)
@MustBeDocumented
annotation class Name(val name : String)