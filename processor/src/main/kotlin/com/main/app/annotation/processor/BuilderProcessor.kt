package com.main.app.annotation.processor

import java.io.PrintWriter
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

@SupportedAnnotationTypes("com.main.app.annotation.processor.Builder")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
class BuilderProcessor : AbstractProcessor() {
    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        for (annotation in annotations) {
            roundEnv.getElementsAnnotatedWith(annotation).forEach {
                if (it.kind === ElementKind.CLASS) {
                    createBuilderClass(it as TypeElement)
                }
            }

        }
        return true
    }

    private fun createBuilderClass(element: TypeElement) {
        val packageName = processingEnv.elementUtils.getPackageOf(element).toString()
        val members = processingEnv.elementUtils.getAllMembers(element).filter { it.kind == ElementKind.FIELD }
        val modelName = element.simpleName.toString()
        val builderClassName = "$packageName.${modelName}Builder"
        val builderFile = processingEnv.filer.createSourceFile(builderClassName)
//        PrintWriter(System.out).use {
//                out ->
//            out.println("package $packageName;")
//            out.println()
//            out.println("public class ${modelName}Builder {")
//            out.println()
//            members.forEach{
//                    member ->
//                out.println("    public ${member.asType()} ${member.simpleName};")
//            }
//            out.println()
//            out.println("    public $modelName build() {")
//            out.println(prepareBuildMethodBody(members, modelName))
//            out.println("    }")
//            out.println("}")
//        }

        builderFile.openWriter().buffered().use {
            out ->
            out.write("package $packageName;")
            out.newLine()
            out.newLine()
            out.write("class ${modelName}Builder {")
            out.newLine()
            out.newLine()
            members.forEach{
                member ->
                out.write("    public ${member.asType()} ${member.simpleName};")
                out.newLine()
            }
            out.newLine()
            out.write("    public $modelName build() {")
            out.newLine()
            out.write(prepareBuildMethodBody(members, modelName))
            out.newLine()
            out.write("    }")
            out.newLine()
            out.write("}")
        }
    }

    private fun prepareBuildMethodBody(members: List<Element>, modelName: String): String {
        var buildMethodBody = "        return new ${modelName}("
        members.forEach {
            member ->
            buildMethodBody += "${member.simpleName},"
        }
        buildMethodBody = buildMethodBody.dropLast(1)
        buildMethodBody += ");"
        return buildMethodBody
    }
}