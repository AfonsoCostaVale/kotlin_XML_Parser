package xmlparser.core

import xmlparser.core.notificationtype.NotificationTypeContext

/**
 * Represents a Xml context.
 */
open class XmlContext(version: String = "1.0", encoding: String = "UTF-8", standalone: String = "no") : IVisitable, IObservable<(XmlContext, NotificationTypeContext) -> Unit> {
    val xmlHeader: XmlHeader = XmlHeader(version, encoding, standalone)
    var rootXmlElement: XmlElement? = null
    set(rootXmlElement)
    {
        field = rootXmlElement
        notifyObservers { it(this, NotificationTypeContext.CHANGE_ROOT) }
    }

    final override val observers: MutableList<(XmlContext, NotificationTypeContext) -> Unit> = mutableListOf()

    /**
     * Dumps the context.
     */
    fun dump(intent: Int = -1): String {
        var content: String = xmlHeader.dump() + if (intent > -1) "\n" else ""

        if (rootXmlElement != null)
            content += rootXmlElement!!.dump(intent)

        return content
    }

    /**
     * Deep copies XmlContext.
     */
    fun deepCopy(): XmlContext
    {
        val clonedXmlContext = XmlContext(xmlHeader.version, xmlHeader.encoding, xmlHeader.standalone)
        if (rootXmlElement != null)
            clonedXmlContext.rootXmlElement = rootXmlElement!!.deepCopy()
        return clonedXmlContext
    }

    final override fun accept(visitor: IVisitor) {
        if(visitor.visit(this) && rootXmlElement != null)
            rootXmlElement!!.accept(visitor)
        visitor.endVisit(this)
    }

    final override fun toString(): String {
        return dump(4)
    }

}