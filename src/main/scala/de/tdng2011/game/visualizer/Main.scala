package de.tdng2011.game.visualizer

import swing.event.ButtonClicked
import swing._

object Main extends SwingApplication {
  def startup(args: Array[String]) {
    if (args.size > 0) {
      new Client(args(0))
    } else {
      val dialog = new MainFrame {
        title = "Connect"
        val inputField = new TextField {
          text = "localhost"
          columns = 20
          selectAll
        }
        val sound = new CheckBox {
          text = "sound"
          selected = true
        }
        val halfSize = new CheckBox {
          text = "halfsize"
          selected = false
        }
        val connectionButton = new Button("Connect!") {
          reactions += {
            case x: ButtonClicked => {
              new Client(inputField.text, sound.selected, halfSize.selected)
              close
            }
          }
        }
        defaultButton = connectionButton
        contents = new FlowPanel(inputField, sound, halfSize, connectionButton)
        peer.setLocationRelativeTo(null)
        visible = true
      }
    }
  }
}