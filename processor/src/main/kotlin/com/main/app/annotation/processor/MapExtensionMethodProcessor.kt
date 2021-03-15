package com.main.app.annotation.processor

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.asTypeName
import com.sun.tools.javac.code.Attribute
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

@SupportedAnnotationTypes("com.main.app.annotation.processor.MapTo")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedOptions(MapExtensionMethodProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class MapExtensionMethodProcessor : AbstractProcessor() {
    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        for (annotation in annotations) {
            roundEnv.getElementsAnnotatedWith(annotation).forEach {
                if (it.kind === ElementKind.CLASS) {
                    createMapMethod(it as TypeElement)
                }
            }

        }
        return true
    }

    private fun createMapMethod(element: TypeElement) {
        val packageName = processingEnv.elementUtils.getPackageOf(element).toString()
        val members = processingEnv.elementUtils.getAllMembers(element).filter { it.kind == ElementKind.FIELD }
        val modelName = element.simpleName.toString()
        val extensionFileName = "${modelName}Extensions"
        val annotation = element.annotationMirrors.find { (it as Attribute.Compound).type == MapTo::class.java }
        val newType = annotation!!.annotationType
//        val extensionMethod = FileSpec.builder(packageName, extensionFileName)
//            .addFunction(FunSpec.builder("toMap")
//                .receiver(element.asType().asTypeName())
//                .addStatement(prepareMapMethodBody(members, "${newType.qualifiedName}"))
//                .returns(newType)
//                .build())
//            .build()


//        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
//        val file = File(kaptKotlinGeneratedDir, "$packageName.$extensionFileName")
//
//        file.writeText(fileContent)

//        extensionMethod.writeTo(System.out)
    }

    private fun prepareMapMethodBody(members: List<Element>, modelName: String): String {
        var buildMethodBody = "return ${modelName}("
        members.forEach {
                member ->
            buildMethodBody += "${member.simpleName},"
        }
        buildMethodBody = buildMethodBody.dropLast(1)
        buildMethodBody += ");"
        return buildMethodBody
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}