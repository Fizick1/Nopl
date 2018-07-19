package anotation

@Target(AnnotationTarget.FIELD)
@MustBeDocumented
annotation class Output(val output: Boolean)