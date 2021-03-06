package xmlparser.examples.plugins.calendar.component

import xmlparser.editor.view.ElementView
import xmlparser.editor.view.component.IComponent
import javax.swing.JPanel

class CalendarElementValueComponent : IComponent<ElementView, JPanel> {

    override fun accept(view: ElementView): Boolean = view.xmlElement.name == "Calendar"

    override fun component(view: ElementView): JPanel? {
        return null
    }
}