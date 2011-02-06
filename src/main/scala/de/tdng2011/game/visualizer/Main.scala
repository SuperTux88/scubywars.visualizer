package de.tdng2011.game.visualizer

import swing.event.ButtonClicked
import swing._

object Main extends SwingApplication{
  def startup(args: Array[String]) {
    val dialog = new MainFrame {
      title = "Connect"
      val inputField = new TextField {
        text = "localhost"
        columns = 20
        selectAll
      }
      val connectionButton = new Button("Connect!") {
        reactions += {
          case x:ButtonClicked => {
            new Client(inputField.text)
            close
          }
        }
      }
      defaultButton = connectionButton
      contents = new FlowPanel(inputField, connectionButton)
      peer.setLocationRelativeTo(null)
      visible = true
    }
  }
}