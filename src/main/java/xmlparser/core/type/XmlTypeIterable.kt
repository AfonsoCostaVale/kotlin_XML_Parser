package xmlparser.core.type

import xmlparser.core.XmlContext
import xmlparser.core.element.XmlElement

fun Iterable<Any>.createXmlElement(xmlContext: XmlContext, elementName: String): XmlElement
{
    val xmlElement = XmlElement(elementName)
    this.forEach{
        xmlContext.addXmlElementChild(xmlElement, it, "item")
    }
    return xmlElement
}

fun Iterable<Any>.createXmlElement(xmlContext: XmlContext): XmlElement
{
    val xmlElement = XmlElement("iterable")
    this.forEach{
        xmlContext.addXmlElementChild(xmlElement, it, "item")
    }
    return xmlElement
}