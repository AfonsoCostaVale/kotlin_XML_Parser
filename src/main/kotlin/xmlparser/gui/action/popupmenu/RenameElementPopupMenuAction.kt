package xmlparser.gui.action.popupmenu

import xmlparser.core.element.XmlElement
import xmlparser.gui.IAction
import xmlparser.gui.IActionPopupMenu
import xmlparser.gui.view.ElementView
import javax.swing.JOptionPane

class RenameElementPopupMenuAction : IActionPopupMenu
{
    override val displayName: String = "Rename"
    override fun getAction(elementView: ElementView): IAction {
        val text = JOptionPane.showInputDialog("text")
        return RenameElementAction(elementView.xmlElement, text)
    }

    class RenameElementAction(private val xmlElement: XmlElement, private val newName: String) : IAction {

        override val name: String = "Rename Element ${xmlElement.name} to $newName"

        private val oldName: String = xmlElement.name

        override fun execute() {
            xmlElement.name = newName
        }

        override fun undo() {
            xmlElement.name = oldName
        }
    }
}