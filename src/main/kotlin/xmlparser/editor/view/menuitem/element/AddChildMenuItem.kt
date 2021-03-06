package xmlparser.editor.view.menuitem.element

import xmlparser.core.XmlElement
import xmlparser.editor.controller.ActionStack
import xmlparser.editor.controller.action.AddChildAction
import xmlparser.editor.view.ElementView
import xmlparser.editor.view.menuitem.IMenuItem
import javax.swing.JMenuItem
import javax.swing.JOptionPane

class AddChildMenuItem : IMenuItem<ElementView> {
    override fun menuItem(view: ElementView): JMenuItem {
        val jMenuItem = JMenuItem("Add child")
        jMenuItem.addActionListener {
            val text = JOptionPane.showInputDialog("text")
            ActionStack.doAction(AddChildAction(view.xmlElement, XmlElement(text)))
        }
        return jMenuItem
    }
}