package xmlparser.examples.plugins.musiclibrary.component

import xmlparser.editor.view.ElementView
import xmlparser.editor.view.component.IComponent
import javax.swing.JPanel

class SongElementValueComponent : IComponent<ElementView, JPanel> {
    override fun accept(view: ElementView): Boolean = view.xmlElement.name == "Song"

    override fun component(view: ElementView): JPanel? {
        return null
    }
}