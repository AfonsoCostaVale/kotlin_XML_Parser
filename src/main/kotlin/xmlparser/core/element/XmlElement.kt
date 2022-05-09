package xmlparser.core.element

import xmlparser.core.Visitable
import xmlparser.core.Visitor
import xmlparser.core.utils.createFilledString

/**
 * Represents a xml context that can be assigned to a context or can be part of a context.
 */
class XmlElement(var name: String, var value: Any = "") : Visitable
{
    init {
        require(name != ""){"Element name cannot be empty."}
    }

    private val children: MutableList<XmlElement> = mutableListOf()
    private val attributes: MutableList<XmlElementAttribute> = mutableListOf()
    private var father: XmlElement? = null

    /**
     * Adds a child to the xml element.
     */
    fun addChild(name: String, value: Any = "") = addChild(XmlElement(name, value))
    /**
     * Adds a child to the xml element.
     */
    fun addChild(xmlElement: XmlElement)
    {
        xmlElement.father = this
        children.add(xmlElement)
    }

    fun removeChild(xmlElement: XmlElement) = children.remove(xmlElement)

    /**
     * Gets the attributes of the xml element.
     */
    fun attributes() = attributes
    /**
     * Adds an attribute to the xml element.
     */
    fun addAttribute(xmlElementAttribute: XmlElementAttribute) = attributes.add(xmlElementAttribute)

    /**
     * Gets Children of xml element.
     */
    fun children() = children
    /**
     * Tells if the xml element has children.
     */
    fun hasChildren() = children.isNotEmpty()

    /**
     * Tells the number of children of the xml element.
     */
    fun childrenCount() = children.count()

    /**
     * Gets the father of the xml element.
     */
    fun father() = father

    /**
     * Tells if xml element has a father.
     */
    fun hasFather() = father != null

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
    /**
     * Deep copies XmlEntity.
     */
    fun deepCopy(): XmlElement
    {
        val clonedXmlElement = XmlElement(name, value)
        clonedXmlElement.attributes.addAll(attributes)
        children.forEach{
            clonedXmlElement.addChild(it.deepCopy())
        }
        return clonedXmlElement
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