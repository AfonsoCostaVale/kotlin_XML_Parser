package xml

import testbed.Point
import kotlin.reflect.KClass
import kotlin.reflect.full.*
import kotlin.reflect.javaType

class XmlContext(version: String = "1.0", encoding: String = "UTF-8", standalone: String = "no")
{
    private val xmlHeader: XmlHeader = XmlHeader(version, encoding, standalone)
    private val xmlElements: MutableList<XmlElement> = mutableListOf()

    inline fun <reified T : Any>addXmlElement(element: T) {
        val kClass: KClass<T> = T::class

        addXmlElement(kClass, element)
    }

    fun <T : Any>addXmlElement(kClass: KClass<T>, element: T)
    {
        val xmlElement = createXmlElement(kClass, element)

        addXmlElementChildren(kClass, element, xmlElement)

        addXmlElement(xmlElement)
    }

    fun addXmlElement(xmlElement: XmlElement)
    {
        xmlElements.add(xmlElement)
    }

    fun dump(intent: Int = -1): String
    {
        var content: String = xmlHeader.dump() + if(intent > -1) "\n" else ""

        for(element in xmlElements)
            content += element.dump(intent)

        return content
    }

    private fun <T : Any>createXmlElement(kClass: KClass<T>, element: T): XmlElement
    {
        val elementName: String = if(kClass.hasAnnotation<XmlElementName>())
        {
            if(kClass.findAnnotation<XmlElementName>()?.name == null)
                throw InvalidXmlAnnotationException("XmlElementName", "Invalid name")

            kClass.findAnnotation<XmlElementName>()?.name!!
        } else {
            if(kClass.simpleName == null)
                throw InvalidXmlElementException("Class doesn't have a name")

            kClass.simpleName!!
        }

        val elementContent = kClass.declaredMemberProperties.filter{it.hasAnnotation<XmlElementContent>() }

        return if(elementContent.isNotEmpty()) {
            if(elementContent.size > 1)
                throw InvalidXmlAnnotationException("XmlElementContent", "Can't be used more than one time in one class")

            XmlElement(elementName, elementContent[0].call(element)!!)
        } else {
            XmlElement(elementName)
        }
    }

    private fun <T : Any>addXmlElementChildren(kClass: KClass<T>, element: T, xmlElement: XmlElement)
    {
        val properties = kClass.declaredMemberProperties.filter{!it.hasAnnotation<XmlElementContent>() && !it.hasAnnotation<XmlIgnore>()}
        properties.forEach{
            if(it.call(element) != null)
            {
                if(it.name == "position")
                {
                    val propertyXmlElement = createXmlElementChildren(it.call(element)!!)
                    xmlElement.addChild(propertyXmlElement)
                }
                else
                {
                    val propertyXmlElement = XmlElement(it.name, it.call(element)!!)
                    xmlElement.addChild(propertyXmlElement)
                }
            }
            else
            {
                val propertyXmlElement = XmlElement(it.name)
                xmlElement.addChild(propertyXmlElement)
            }
        }
    }

    private fun createXmlElementChildren(element: Any):XmlElement
    {
        val kClass: KClass<Any> = element::class as KClass<Any>
        val xmlElement = createXmlElement(kClass, element)

        addXmlElementChildren(kClass, element, xmlElement)

        return xmlElement
    }
}