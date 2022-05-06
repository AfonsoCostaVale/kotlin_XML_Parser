package xmlparser.core.element

import xmlparser.core.Visitable
import xmlparser.core.Visitor
import xmlparser.core.utils.createFilledString

/**
 * Represents a xml context that can be assigned to a context or can be part of a context.
 */
class XmlElement(val name: String, val value: Any = "") : Visitable
{
    val children: MutableList<XmlElement> = mutableListOf()
    private val attributes: MutableList<XmlElementAttribute> = mutableListOf()

    /**
     * Adds a child to the xml element.
     */
    fun addChild(xmlElement: XmlElement) = children.add(xmlElement)

    /**
     * Adds an attribute to the xml element.
     */
    fun addAttribute(xmlElementAttribute: XmlElementAttribute) = attributes.add(xmlElementAttribute)

    /**
     * Clones without children.
     */
    fun cloneWithoutChildren(): XmlElement
    {
        val clonedXmlElement = XmlElement(name, value)
        clonedXmlElement.attributes.addAll(attributes)
        return clonedXmlElement
    }

    /**
     * Dumps the xml element.
     */
    fun dump(intent: Int = -1, intentOffset: Int = 0): String
    {
        var content = "<$name"

        for(attribute in attributes)
            content += " ${attribute.name}=\"${attribute.value}\""

        content += ">$value"

        if(children.isNotEmpty())
        {
            content += if(intent > -1) "\n" else ""
            val spacing: String = createFilledString(intent + intentOffset, ' ')

            for(child in children)
            {
                content += spacing
                content += child.dump(intent, intent + intentOffset)
            }

            content += createFilledString(intentOffset, ' ')
        }

        content += "</$name>" + if(intent > -1) "\n" else ""

        return content
    }

    override fun accept(visitor: Visitor) {
        if(visitor.visit(this))
            children.forEach {
                it.accept(visitor)
            }
        visitor.endVisit(this)
    }

    override fun toString(): String {
        return dump(4)
    }
}

//TODO find out which members of the class should be public to be filtered by the visitor accept function
