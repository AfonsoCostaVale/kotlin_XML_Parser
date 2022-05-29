package xmlparser.gui.legacy.controllers

import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.observableListOf
import xmlparser.core.element.XmlElement
import xmlparser.core.element.XmlElementAttribute
import xmlparser.gui.legacy.ActionStack
import xmlparser.gui.legacy.IAction
import xmlparser.gui.legacy.actions.view.add.AddAttributeXmlEntityAction
import xmlparser.gui.legacy.actions.view.add.AddXmlEntityAction
import xmlparser.gui.legacy.actions.view.add.RemoveAttributeXmlEntityAction
import xmlparser.gui.legacy.actions.view.add.UpdateAttributeXmlEntityAction
import xmlparser.gui.legacy.views.AddElementView

class AddElementController : Controller() {

    var xmlElementFather: XmlElement? = null
        private set
    var attributes: ObservableList<XmlElementAttribute>? = null
        private set

    private var actions = mutableListOf<IAction>()

    fun setContext(xmlElementFather: XmlElement?)
    {
        this.xmlElementFather = xmlElementFather
        this.attributes = observableListOf()
        this.find(AddElementView::class).setContext()
    }

    fun removeAttribute(xmlElementAttribute: XmlElementAttribute) {
        val removeAttributeXmlEntityAction = RemoveAttributeXmlEntityAction(xmlElementAttribute, attributes!!)
        actions.add(removeAttributeXmlEntityAction)
        ActionStack.doAction(removeAttributeXmlEntityAction)
    }

    fun addAttribute(name: String, value: String) {
        val xmlElementAttribute = XmlElementAttribute(name, value)
        val addAttributeXmlEntityAction = AddAttributeXmlEntityAction(xmlElementAttribute, attributes!!)
        actions.add(addAttributeXmlEntityAction)
        ActionStack.doAction(addAttributeXmlEntityAction)
    }

    fun registerAttributeUpdate(xmlElementAttribute: XmlElementAttribute, oldXmlElementAttribute: XmlElementAttribute)
    {
        val updateAttributeXmlEntityAction = UpdateAttributeXmlEntityAction(xmlElementAttribute,
            oldXmlElementAttribute, this.find(AddElementView::class))
        actions.add(updateAttributeXmlEntityAction)
        ActionStack.doAction(updateAttributeXmlEntityAction)
    }

    fun createChild(name: String, value: String)
    {
        val xmlElementChild = XmlElement(name, value)
        attributes?.forEach {
            xmlElementChild.addAttribute(it)
        }
        val addXmlEntityAction = AddXmlEntityAction(this@AddElementController.find(MainController::class).xmlContext,
            this@AddElementController.find(MainController::class).treeTableViewXmlElementObserver, xmlElementFather,xmlElementChild)
        ActionStack.doAction(addXmlEntityAction)
    }

    fun undo() = this@AddElementController.find(MainController::class).undo()
    fun redo() = this@AddElementController.find(MainController::class).redo()

    fun onClose()
    {
        xmlElementFather = null
        attributes = null
        actions.forEach{
            ActionStack.removeAction(it)
        }
        actions.clear()
    }
}