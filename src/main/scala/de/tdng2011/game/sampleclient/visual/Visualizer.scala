package de.tdng2011.game.sampleclient.visual

import actors.Actor
import de.tdng2011.game.library.{Shot, Player, Vec2}

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 23.01.11
 * Time: 00:56
 * To change this template use File | Settings | File Templates.
 */

object Visualizer extends Actor {

  import java.util.Random
  import swing._
  import java.awt.{ Color, Graphics2D, Font }
  import math._
  val lineLength = 30 // todo monster / player radius * 2

  var currentEntityDescriptions = List[Any]()

  val stars = for(x <- 1 to 50) yield (new Random().nextInt(WorldDefs.size),new Random().nextInt(WorldDefs.size))

  var frame = new MainFrame {
    title = "Scubywars"
    var mainPanel = new BoxPanel(scala.swing.Orientation.Horizontal) {
      focusable = true
      background = Color.BLACK
      preferredSize = new Dimension(WorldDefs.size + 400, WorldDefs.size)
    }

    var gameFieldPanel = new Panel {
      focusable = true
      background = Color.BLACK
      preferredSize = new Dimension(mainPanel.size.width + 600, WorldDefs.size)

      override def paintComponent(g: Graphics2D) {
        super.paintComponent(g)
        for ((x,y) <- stars){
          g.setColor(Color.WHITE)
          g.drawString("*",x,y)
        }
        for (description <- currentEntityDescriptions) {
          description match {
            case x : Player => {
              drawPlayer(g, x)
            }

            case x : Shot => {
              drawShot(g, x)
            }

            case barbrastreisand => {
              println("whuhuhuhu")
            }
          }
        }
      }
    }

    var statsPanel = new Panel() {
      focusable = true
      background = Color.BLACK

      preferredSize = new Dimension(mainPanel.size.width - gameFieldPanel.size.width, mainPanel.size.height)
    }

    mainPanel.peer.add(gameFieldPanel.peer)
    mainPanel.peer.add(statsPanel.peer)

    contents = mainPanel

    centerOnScreen
    resizable_=(false)
    visible_=(true)
  }

  def drawPlayer(g: Graphics2D, player : Player) {

      val ahead = Vec2(1, 0).rotate(player.direction)
      val posPeak = player.pos + ahead * (lineLength / 2)

      val aheadLeft = Vec2(1, 0).rotate((player.direction + sin(60) + Pi).floatValue)
      val aheadRight = Vec2(1, 0).rotate((player.direction - sin(60) + Pi).floatValue)

      val posLeft = posPeak + aheadLeft * lineLength
      val posRight = posPeak + aheadRight * lineLength

      val x1 = posPeak.x.toInt
      val y1 = posPeak.y.toInt

      val x2 = posRight.x.toInt
      val y2 = posRight.y.toInt

      val x3 = posLeft.x.toInt
      val y3 = posLeft.y.toInt

      val oldFont = g.getFont

      g.setColor(Color.YELLOW)
      g.setFont(new Font("Arial", Font.PLAIN, 20))
      g.drawString(player.publicId+"", x1 + 20, y1 + 20)

      g.setColor(Color.GREEN)
      g.setFont(oldFont)
      g.drawLine(x1, y1, x3, y3)
      g.drawLine(x1, y1, x2, y2)
  }

  def drawShot(g: Graphics2D, shot : Shot) {
    g.setColor(Color.RED)
    g.fillOval(shot.pos.x.toInt - (shot.radius / 2), shot.pos.y.toInt - (shot.radius / 2), shot.radius * 2, shot.radius * 2)
  }


  def act = {
    loop {
      react {
        case x : List[Any] => {
          currentEntityDescriptions = x
          frame.repaint()
        }

        case barbrastreisand => {
          println("whuhuhuhu")
        }
      }
    }
  }
}